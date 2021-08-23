package com.zpmc.ztos.infra.base.common.database;

import com.alibaba.fastjson.support.geo.Geometry;
import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.business.enums.framework.LifeCycleStateEnum;
import com.zpmc.ztos.infra.base.business.enums.framework.MessageLevelEnum;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.business.model.MetafieldIdFactory;
import com.zpmc.ztos.infra.base.business.model.MetafieldIdList;
import com.zpmc.ztos.infra.base.business.predicate.PredicateFactory;
import com.zpmc.ztos.infra.base.common.callbacks.CarinaPersistenceCallback;
import com.zpmc.ztos.infra.base.common.consts.FrameworkConfigConsts;
import com.zpmc.ztos.infra.base.common.enums.AtomizedEnum;
import com.zpmc.ztos.infra.base.common.exceptions.BizFailure;
import com.zpmc.ztos.infra.base.common.model.*;
import com.zpmc.ztos.infra.base.common.utils.*;
import com.zpmc.ztos.infra.base.utils.QueryUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.hibernate.*;
import org.hibernate.metadata.ClassMetadata;

import org.hibernate.type.Type;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;
import org.springframework.util.ObjectUtils;

import java.io.Serializable;
import java.util.*;

public class HibernateApi extends BaseHibernateApi implements IHibernateDeleteApi, IDetailedDiagnostics {

    private boolean _obsoletableDeleteEnabled = true;
    private FlushMode _defaultFlushMode = FlushMode.AUTO;
    private IEntityScoper _entityScoper;
    private static final Logger LOGGER = Logger.getLogger(HibernateApi.class);
    private static final Logger HQL_LOGGER = Logger.getLogger((String)"com.navis.HQL");
    private static final int QTASK_SCALAR = 0;
    private static final int QTASK_OBJECT = 1;
    private static final int QTASK_COUNT = 2;
    private static final int QTASK_KEYS = 3;
    private static final int QTASK_RECAP = 4;
    private static final int QTASK_EXISTS = 5;
    private static final int QTASK_RECAP_SUMMARY = 6;
    static final int UPDATE_BATCH_SIZE = 1000;

    public static HibernateApi getInstance() {
        return (HibernateApi) Roastery.getBean("hibernateApi");
    }

    @Deprecated
    public static Serializable[] findPrimaryKeys(final IDomainQuery inDq, UserContext inUserContext) {
        PersistenceTemplate pt = new PersistenceTemplate(inUserContext);
        final Object[] resultHolder = new Object[1];
        IMessageCollector collector = pt.invoke(new CarinaPersistenceCallback(){

            @Override
            public void doInTransaction() {
                resultHolder[0] = HibernateApi.getInstance().findPrimaryKeysByDomainQuery(inDq);
            }
        });
        if (collector.containsMessageLevel(MessageLevelEnum.SEVERE)) {
            String message = "Failed to retrieve primary keys for domain query >" + inDq + "< " + collector;
            throw BizFailure.create(message);
        }
        return (Serializable[])resultHolder[0];
    }

    public DatabaseEntity downcast(DatabaseEntity inEntity, Class inClass) {
        Object result;
        if (inClass.isInstance(inEntity)) {
            return inEntity;
        }
        Serializable primaryKey = inEntity.getPrimaryKey();
        try {
            Session session = SessionFactoryUtils.getSession(this.getSessionFactory(), false);
            result = session.load(inClass, primaryKey);
        }
        catch (HibernateException ex) {
            LOGGER.warn((Object)("downcast: ex = " + (Object)((Object)ex)));
            return null;
        }
        if (inClass.isInstance(result)) {
            return (DatabaseEntity)result;
        }
        return null;
    }

    public String extractOrderClause(Ordering[] inOrderings, String inAlias) {
        StringBuilder orderClause = new StringBuilder();
        for (int i = 0; i < inOrderings.length; ++i) {
            Ordering ordering = inOrderings[i];
            if (i == 0) {
                orderClause.append(" order by ");
            } else {
                orderClause.append(", ");
            }
            if (StringUtils.isNotEmpty((String)inAlias)) {
                orderClause.append(inAlias).append(".");
            }
            IMetafieldId fieldId = ordering.getFieldId();
            if (ordering.isAscending()) {
                orderClause.append(fieldId.getFieldId()).append(" asc");
                continue;
            }
            orderClause.append(fieldId.getFieldId()).append(" desc");
        }
        return orderClause.toString();
    }

    @Deprecated
    public int findCount(String inQuery, Object[] inObjects, Type[] inTypes) {
        int count = 0;
        Session session = SessionFactoryUtils.getSession(this.getSessionFactory(), false);
        if (StringUtils.isEmpty((String)inQuery)) {
            return count;
        }
        try {
            Query qCount = null;
            String lowerCase = inQuery.toLowerCase();
            if (lowerCase.startsWith("select")) {
                int indexFrom;
                int indexSelect = lowerCase.indexOf("select ");
                String resultColumns = inQuery.substring(indexSelect + 7, indexFrom = lowerCase.indexOf(" from "));
                if (StringUtils.isNotEmpty((String)resultColumns)) {
                    String lowerResultColumns = resultColumns.toLowerCase();
                    int countIndex = lowerResultColumns.indexOf("count");
                    int distinctIndex = lowerResultColumns.indexOf("distinct");
                    if (countIndex == -1) {
                        String newQuery;
                        if (distinctIndex == -1) {
                            newQuery = StringUtils.replaceOnce((String)inQuery, (String)resultColumns, (String)"count(*)");
                        } else {
                            StringBuilder sb = new StringBuilder();
                            sb.append("count(");
                            int commaIndex = resultColumns.indexOf(44, distinctIndex);
                            if (commaIndex == -1) {
                                commaIndex = resultColumns.length();
                            }
                            sb.append(resultColumns.substring(distinctIndex, commaIndex));
                            sb.append(")");
                            newQuery = StringUtils.replaceOnce((String)inQuery, (String)resultColumns, (String)sb.toString());
                        }
                        qCount = session.createQuery(newQuery);
                    } else {
                        qCount = session.createQuery(inQuery);
                    }
                }
            } else {
                qCount = session.createQuery("select count(*) from " + inQuery);
            }
            this.setQueryParameters(qCount, inTypes, inObjects);
            List clist = qCount.list();
            count = ((Long)clist.get(0)).intValue();
            LOGGER.debug((Object)(inQuery + " total count:" + count));
        }
        catch (HibernateException ex) {
            throw this.convertHibernateException(ex);
        }
        return count;
    }

    @Nullable
    public Object convertPropertyValue(IMetafieldId inMetafieldId, Object inRawValue, Type inFieldType, boolean inDoFormatForUI) {
        Object fieldValue;
        if (inRawValue instanceof Collection) {
            fieldValue = EntityCollectionUtils.convertPersistentCollection2Array((Collection)inRawValue, inDoFormatForUI);
        } else if (inRawValue instanceof IEntity) {
            if (inFieldType != null && inFieldType.isComponentType()) {
                fieldValue = inRawValue.toString();
            } else if (inDoFormatForUI) {
                IEntity relatedEntity = (IEntity)inRawValue;
                fieldValue = relatedEntity.getHumanReadableKey();
            } else {
                IEntity relatedEntity = (IEntity)inRawValue;
                fieldValue = relatedEntity.getPrimaryKey();
            }
        } else {
            fieldValue = inRawValue;
        }
        if (fieldValue != null && !(fieldValue instanceof Serializable)) {
            LOGGER.warn((Object)("convertPropertyValue: illegal non-serializeable property: " + inMetafieldId + " : " + fieldValue));
        }
        return fieldValue;
    }

    @Deprecated
    public List findValueObjectsByDomainQuery(IDomainQuery inDomainQuery) {
        IQueryResult dqr = this.findValuesByDomainQuery(inDomainQuery);
        return dqr.getRetrievedResults();
    }

    @Deprecated
    public IQueryResult findVAOsByDomainQuery(IDomainQuery inDomainQuery) {
        return this.findValuesByDomainQuery(inDomainQuery);
    }

    public List findEntitiesByDomainQuery(IDomainQuery inDomainQuery) {
        ResultHolder dqr = this.executeDomainQuery(1, inDomainQuery);
        return dqr.getResults();
    }

    public int findCountByDomainQuery(IDomainQuery inDomainQuery) {
        ResultHolder dqr = this.executeDomainQuery(2, inDomainQuery);
        return dqr.getTotalResults();
    }

    public boolean existsByDomainQuery(IDomainQuery inDomainQuery) {
        ResultHolder dqr = this.executeDomainQuery(5, inDomainQuery);
        return dqr.getTotalResults() > 0;
    }

    public Map findRecapByDomainQuery(IDomainQuery inDomainQuery) {
        ResultHolder dqr = this.executeDomainQuery(4, inDomainQuery);
        List results = dqr.getResults();
        LinkedHashMap resultMap = new LinkedHashMap();
        for (Object result : results) {
            Object[] row = (Object[])result;
            this.addToResults(0, row, resultMap);
        }
        return resultMap;
    }

    public List findSumRecapAsListByDomainQuery(IDomainQuery inDomainQuery) {
        if (inDomainQuery.getMetafieldIds().getSize() < 3) {
            LOGGER.error((Object)"Errors not enough metafields for recap sum.");
            throw new IllegalArgumentException("Errors not enough metafields for recap sum.");
        }
        ResultHolder dqr = this.executeDomainQuery(6, inDomainQuery);
        List results = dqr.getResults();
        return results;
    }

    public List findRecapAsListByDomainQuery(IDomainQuery inDomainQuery) {
        ResultHolder dqr = this.executeDomainQuery(4, inDomainQuery);
        List results = dqr.getResults();
        return results;
    }

    public Serializable[] findPrimaryKeysByDomainQuery(IDomainQuery inDomainQuery) {
        ResultHolder dqr = this.executeDomainQuery(3, inDomainQuery);
 //       return dqr.getResults().toArray(new Serializable[dqr.getResults().size()]);
        return null;
    }

    @Nullable
    public IEntity getUniqueEntityByDomainQuery(IDomainQuery inDomainQuery) {
        List matches = this.findEntitiesByDomainQuery(inDomainQuery);
        if (matches.size() == 1) {
            IEntity entity = (IEntity)matches.get(0);
            return entity;
        }
        if (matches.isEmpty()) {
            return null;
        }
        throw BizFailure.create(IFrameworkPropertyKeys.FRAMEWORK__NON_UNIQUE_RESULT, null, matches.size(), inDomainQuery);
    }

    public void setEntityScoping(boolean inEnabled) {
        TransactionParms tp = TransactionParms.getBoundParms();
        if (tp != null) {
            tp.setScopingEnabled(inEnabled);
        }
    }

    public void setEntityScoper(IEntityScoper inEntityScoper) {
        this._entityScoper = inEntityScoper;
    }

    @Override
    public void deleteByDomainQuery(IDomainQuery inDq) {
        List list = this.findEntitiesByDomainQuery(inDq);
        for (Object entity : list) {
            this.delete(entity);
        }
    }

    private void deleteFromSession(Session inSession, Object inObject) {
        if (inObject instanceof IEntity) {
//            IEntityDeleteHandler handler = (IEntityDeleteHandler)Roastery.getBean("entityDeleteHandler");
//            handler.preprocessDelete((IEntity)inObject);
        }
        inSession.delete(inObject);
    }

    @Override
    public void delete(Object inObject, boolean inDatabaseDelete) {
        if (!inDatabaseDelete) {
            this.delete(inObject);
        } else {
            Session session = SessionFactoryUtils.getSession(this.getSessionFactory(), false);
            try {
                this.deleteFromSession(session, inObject);
            }
            catch (HibernateException ex) {
                LOGGER.error((Object)("Errors Deleting IEntity " + inObject), (Throwable)ex);
                throw this.convertHibernateException(ex);
            }
        }
    }

    @Override
    public void delete(Object inObject) {
        Session session = SessionFactoryUtils.getSession(this.getSessionFactory(), false);
        try {
            if (this.getObsoletableDeleteEnabled() && inObject instanceof IObsoleteable) {
                IObsoleteable obsoleteObject = (IObsoleteable)inObject;
                obsoleteObject.setLifeCycleState(LifeCycleStateEnum.OBSOLETE);
                session.update(inObject);
            } else {
                this.deleteFromSession(session, inObject);
            }
        }
        catch (HibernateException ex) {
            LOGGER.error((Object)("Errors Deleting IEntity " + inObject), (Throwable)ex);
            throw this.convertHibernateException(ex);
        }
    }

    public void batchUpdate(String inClassName, Object[] inPrimaryKeys, FieldChanges inChanges) {
        if (inClassName == null) {
            throw new IllegalArgumentException("class name may not be null.");
        }
        if (inPrimaryKeys == null) {
            throw new IllegalArgumentException("array of primary keys may not be null.");
        }
        if (inChanges == null || inChanges.getFieldChangeCount() == 0) {
            throw new IllegalArgumentException("field changes must contain at least one field change.");
        }
        if (inPrimaryKeys.length == 0) {
            return;
        }
        this.batchUpdateInternal(inClassName, inPrimaryKeys, inChanges, 1000);
    }

    int batchUpdateInternal(String inClassName, Object[] inPrimaryKeys, FieldChanges inChanges, int inBatchSize) {
        assert (inBatchSize > 0);
        int numberOfAllKeys = inPrimaryKeys.length;
        int batchStartPosition = 0;
        int batchCount = 0;
        while (batchStartPosition < numberOfAllKeys) {
            int currentBatchSize = Math.min(inBatchSize, numberOfAllKeys - batchStartPosition);
            Object[] primaryKeysForCurrentBatch = new Object[currentBatchSize];
            System.arraycopy(inPrimaryKeys, batchStartPosition, primaryKeysForCurrentBatch, 0, currentBatchSize);
            this.updateSingleBatch(inClassName, primaryKeysForCurrentBatch, inChanges);
            batchStartPosition += inBatchSize;
            ++batchCount;
        }
        return batchCount;
    }

    private void updateSingleBatch(String inClassName, Object[] inPrimaryKeys, FieldChanges inChanges) {
        Session session = SessionFactoryUtils.getSession(this.getSessionFactory(), false);
        StringBuilder hql = new StringBuilder();
        hql.append("update ");
        hql.append(inClassName);
        hql.append(" set");
        int parmNumber = 0;
        Iterator<FieldChange> changesIterator = inChanges.getIterator();
        while (changesIterator.hasNext()) {
            FieldChange fc = changesIterator.next();
            if (parmNumber > 0) {
                hql.append(',');
            }
            hql.append(' ');
            hql.append(fc.getMetafieldId().getQualifiedId());
            hql.append(" = :p").append(parmNumber++);
        }
        hql.append(" where id in (:primarykeys)");
        Query query = session.createQuery(hql.toString()).setParameterList("primarykeys", inPrimaryKeys);
        parmNumber = 0;
        Iterator<FieldChange> changesIterator2 = inChanges.getIterator();
        while (changesIterator2.hasNext()) {
            FieldChange fc = changesIterator2.next();
            query.setParameter("p" + parmNumber++, fc.getNewValue());
        }
        query.executeUpdate();
    }

    public void batchInsert(String inClassName, FieldChanges inChanges) {
        Session session = SessionFactoryUtils.getSession(this.getSessionFactory(), false);
        StringBuilder hql = new StringBuilder();
        hql.append("insert into ");
        hql.append(inClassName);
        hql.append(" (");
        int parmNumber = 0;
        Iterator<FieldChange> changesIterator = inChanges.getIterator();
        while (changesIterator.hasNext()) {
            FieldChange fc = changesIterator.next();
            if (parmNumber > 0) {
                hql.append(',');
            }
            hql.append(' ');
            hql.append(fc.getMetafieldId().getQualifiedId());
            hql.append(" = :p").append(parmNumber++);
        }
        hql.append(")");
        Query query = session.createQuery(hql.toString());
        parmNumber = 0;
        Iterator<FieldChange> changesIterator2 = inChanges.getIterator();
        while (changesIterator2.hasNext()) {
            FieldChange fc = changesIterator2.next();
            query.setParameter("p" + parmNumber++, fc.getNewValue());
        }
        query.executeUpdate();
    }

    @Override
    public void batchDelete(String inClassName, Object[] inPrimaryKeys) {
        Session session = SessionFactoryUtils.getSession(this.getSessionFactory(), false);
        StringBuilder hql = new StringBuilder();
        hql.append("delete ");
        hql.append(inClassName);
        hql.append(" where id in (:primarykeys)");
        Query query = session.createQuery(hql.toString()).setParameterList("primarykeys", inPrimaryKeys);
        query.executeUpdate();
    }

    public boolean getObsoletableDeleteEnabled() {
        return this._obsoletableDeleteEnabled;
    }

    public void setDefaultFlushModeName(String inFlushModeName) {
        //this._defaultFlushMode = FlushMode.parse((String)inFlushModeName);

    }

    public String getDefaultFlushModeName() {
        return this._defaultFlushMode.toString();
    }

    public void setFlushModeToDefaultMode() {
        this.setFlushMode(this._defaultFlushMode);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug((Object)("setDefaultFlushMode: mode set to " + (Object)this._defaultFlushMode));
        }
    }

    public void setObsoletableDeleteEnabled(boolean inObsoletableDeleteEnabled) {
        this._obsoletableDeleteEnabled = inObsoletableDeleteEnabled;
    }

    @Override
    public String getDetailedDiagnostics() {
        return HiberCache.getDebugString();
    }

    @NotNull
    public IQueryResult findValuesByDomainQuery(IDomainQuery inDomainQuery) {
        IQueryResult result = this.attemptScalarQuery(inDomainQuery);
        if (result == null) {
            result = this.resortToFullHydrationQuery(inDomainQuery);
        }
        return result;
    }

    public IQueryResult attemptScalarQuery(IDomainQuery inDomainQuery) {
        boolean pkFieldWillNotConflictWithDistinct;
        String queryEntityName = inDomainQuery.getQueryEntityName();
        ClassMetadata meta = HiberCache.getClassMetadata(queryEntityName);
        if (meta == null) {
            LOGGER.error((Object)("Could not resolve class from Hibernate Metadata for entity <" + queryEntityName + '>'));
            boolean customEnabled = ExtensionModelUtils.schemaExtensionsEnabled();
            throw new IllegalArgumentException("IEntity <" + queryEntityName + "> does not exist in the system. Any customized entities will be available only when model extensions are enabled. Currently data model extension enabling =" + customEnabled + ".");
        }
        IMetafieldId pkFieldId = MetafieldIdFactory.valueOf(meta.getIdentifierPropertyName());
        MetafieldIdList originalFields = inDomainQuery.getMetafieldIds();
        MetafieldIdList dbSelectFields = new MetafieldIdList();
        MetafieldIdList syntheticFields = new MetafieldIdList();
        MetafieldIdList outputFields = new MetafieldIdList();
        for (int i = 0; i < originalFields.getSize(); ++i) {
            String precedingProperty;
            IMetafieldId metafieldId = originalFields.get(i);
            if (metafieldId == IMetafieldId.PRIMARY_KEY || org.apache.commons.lang.ObjectUtils.equals((Object)metafieldId, (Object)pkFieldId)) {
                outputFields.add(metafieldId);
                dbSelectFields.add(metafieldId);
                continue;
            }
            String lastNode = metafieldId.getFieldId();
            Type fieldType = HiberCache.getFieldType(lastNode);
            if (HiberCache.isSyntheticField(lastNode) || fieldType == null) {
                String sourceBean;
                IMetafieldDictionary metafieldDictionary = Roastery.getMetafieldDictionary();
                IMetafield field = metafieldDictionary.findMetafield(MetafieldIdFactory.valueOf(lastNode));
                String string = sourceBean = field == null ? null : field.getSourceBean();
                if (sourceBean == null) {
                    if (HQL_LOGGER.isInfoEnabled()) {
                        HQL_LOGGER.info((Object)("findValuesByDomainQuery: can not do scalar query because of field: " + metafieldId.getQualifiedId()));
                    }
                    return null;
                }
                MetafieldIdList dependsSet = field.getDepends();
                IMetafieldId qualifyingId = metafieldId.getMfidExcludeRightMostNode();
                IEntityId entityId = metafieldId.getQualifiedMetafieldId().getEntityId();
                for (IMetafieldId dependsOnFieldId : dependsSet) {
                    if (HiberCache.isSyntheticField(dependsOnFieldId.getFieldId())) {
                        return null;
                    }
                    if (entityId != null) {
                        String dependOnEntityName = Roastery.getMetafieldDictionary().findMetafield(dependsOnFieldId).getEntityName();
                        if (ObjectUtils.nullSafeEquals((Object)entityId.getEntityName(), (Object)dependOnEntityName)) {
                            dependsOnFieldId = MetafieldIdFactory.getEntityAwareMetafieldId(entityId, dependsOnFieldId);
                        }
                    }
                    IMetafieldId qualifiedDependsOnFieldId = MetafieldIdFactory.getCompoundMetafieldId(qualifyingId, dependsOnFieldId);
                    outputFields.add(dependsOnFieldId);
                    dbSelectFields.add(qualifiedDependsOnFieldId);
                }
                outputFields.add(metafieldId);
                dbSelectFields.add(IMetafieldId.PRIMARY_KEY);
                syntheticFields.add(metafieldId);
                continue;
            }
            if (fieldType.isCollectionType()) {
                if (LOGGER.isInfoEnabled()) {
                    LOGGER.info((Object)("findValuesByDomainQuery: can not do scalar query because of field: " + metafieldId.getQualifiedId()));
                }
                return null;
            }
            if (fieldType.isAssociationType() || fieldType.isEntityType()) {
                if (inDomainQuery.isFormatResultsForUI()) {
                    return null;
                }
                String entityName = HiberCache.getFullEntityNameFromClassName(fieldType);
                ClassMetadata associatedClassMeta = HiberCache.getClassMetadata(entityName);
                if (associatedClassMeta != null) {
                    IMetafieldId convertedId = MetafieldIdFactory.getCompoundMetafieldId(metafieldId, MetafieldIdFactory.valueOf(associatedClassMeta.getIdentifierPropertyName()));
                    outputFields.add(metafieldId);
                    dbSelectFields.add(convertedId);
                    continue;
                }
                LOGGER.error((Object)("no associated meta found for field type-" + (Object)fieldType));
                continue;
            }
            IMetafieldId qualifyingMetafieldId = metafieldId.getMfidExcludeRightMostNode();
            if (qualifyingMetafieldId != null && !HibernateApi.doesPropertyBelongToPreceedingNode(metafieldId, lastNode, precedingProperty = qualifyingMetafieldId.getFieldId())) {
                LOGGER.info((Object)("findValuesByDomainQuery: subclass field forces hydration query: " + metafieldId));
                return null;
            }
            outputFields.add(metafieldId);
            dbSelectFields.add(metafieldId);
        }
        boolean bl = pkFieldWillNotConflictWithDistinct = !inDomainQuery.areFieldsDistinct() || dbSelectFields.getSize() == 0;
        if (pkFieldWillNotConflictWithDistinct && !inDomainQuery.hasAggregateField() && !inDomainQuery.isDistinctReplacement()) {
            outputFields.add(pkFieldId);
            dbSelectFields.add(pkFieldId);
        }
        IDomainQuery executableQuery = (IDomainQuery)inDomainQuery.createEnhanceableClone();
        executableQuery.replaceSelectedFields(dbSelectFields);
        ResultHolder rh = this.executeDomainQuery(0, executableQuery);
        ArrayBackedQueryResult queryResult = new ArrayBackedQueryResult(queryEntityName, pkFieldId, outputFields, rh.getResults(), rh.getTotalResults(), rh);
        boolean formatForUI = inDomainQuery.isFormatResultsForUI();
        Type[] fieldTypes = new Type[dbSelectFields.getSize()];
        for (int i = 0; i < fieldTypes.length; ++i) {
            String lastNode = dbSelectFields.get(i).getFieldId();
            fieldTypes[i] = HiberCache.getFieldType(lastNode);
        }
        IMetafieldDictionary metafieldDictionary = Roastery.getMetafieldDictionary();
        for (int row = queryResult.getFirstResult(); row <= queryResult.getLastResult(); ++row) {
            HashMap<String, Object> synthesizerCache = null;
            for (int column = 0; column < dbSelectFields.getSize(); ++column) {
                IMetafieldId fieldId = outputFields.get(column);
                if (syntheticFields.contains(fieldId)) {
                    IMetafield field = metafieldDictionary.findMetafield(fieldId.getMfidRightMostNode());
                    String sourceBeanName = field.getSourceBean();
                    Object synthesizer = null;
                    if (synthesizerCache != null) {
                        synthesizer = synthesizerCache.get(sourceBeanName);
                    }
                    if (synthesizer == null) {
                        synthesizer = Roastery.getBean(sourceBeanName);
                        if (synthesizer == null || !(synthesizer instanceof IFieldSynthesizer)) {
                            LOGGER.error((Object)(sourceBeanName + "  source bean is not an implementation of IFieldSynthesizer for field " + fieldId));
                            continue;
                        }
                        if (synthesizerCache == null) {
                            synthesizerCache = new HashMap<String, Object>();
                        }
                        synthesizerCache.put(sourceBeanName, synthesizer);
                    }
                    IValueHolder vh = queryResult.getValueHolder(row);
                    Object synthesizedValue = null;
                    try {
                        synthesizedValue = ((IFieldSynthesizer)synthesizer).synthesizeFieldValue(fieldId.getMfidRightMostNode(), field.getDepends(), vh);
                    }
                    catch (Exception e) {
                        LOGGER.error((Object)(sourceBeanName + " IFieldSynthesizer bean cannot compute field " + fieldId + "  " + e));
                    }
                    queryResult.setValue(row, column, synthesizedValue);
                    continue;
                }
                Object rawValue = queryResult.getValue(row, column);
                Object value = this.convertPropertyValue(dbSelectFields.get(column), rawValue, fieldTypes[column], formatForUI);
                if (value == rawValue) continue;
                queryResult.setValue(row, column, value);
            }
        }
        return queryResult;
    }

    private static boolean doesPropertyBelongToPreceedingNode(IMetafieldId inMetafieldId, String inLastNode, String inPrecedingProperty) {
        Class precedingClass = HiberCache.getFieldClass(inPrecedingProperty);
        Class fieldOwnerClass = HiberCache.getEntityClassForField(inLastNode);
        if (org.apache.commons.lang.ObjectUtils.equals((Object)fieldOwnerClass, (Object)precedingClass)) {
            return true;
        }
        return precedingClass != null && fieldOwnerClass != null && precedingClass.isAssignableFrom(fieldOwnerClass);
    }

    private IQueryResult resortToFullHydrationQuery(IDomainQuery inDomainQuery) {
        List<Object[]> valueArrayList;
        ResultHolder rh;
        String queryEntityName = inDomainQuery.getQueryEntityName();
        MetafieldIdList originalFields = inDomainQuery.getMetafieldIds();
        boolean formatForUI = inDomainQuery.isFormatResultsForUI();
        ClassMetadata meta = HiberCache.getClassMetadata(queryEntityName);
        IMetafieldId pkFieldId = MetafieldIdFactory.valueOf(meta.getIdentifierPropertyName());
        MetafieldIdList outputFields = new MetafieldIdList(originalFields);
        outputFields.add(pkFieldId);
        Boolean disableBatch = Boolean.parseBoolean(System.getProperty("com.navis.framework.finderQuery.disablebatch"));
        FlushMode sessionMode = this.getFlushMode();
   //     if (disableBatch.booleanValue() || inDomainQuery.isSelectForUpdate() || inDomainQuery.hasAggregateField() || FlushMode.MANUAL != sessionMode && FlushMode.NEVER != sessionMode || pkFieldId == null || inDomainQuery.isQueryBounded() && (long)inDomainQuery.getMaxResults() <= FrameworkConfigConsts.HYDRATION_QUERY_BATCH_SIZE.getValue(TransactionParms.getBoundParms().getUserContext())) {
        if (disableBatch.booleanValue() || inDomainQuery.isSelectForUpdate() || inDomainQuery.hasAggregateField() || FlushMode.MANUAL != sessionMode && FlushMode.MANUAL != sessionMode || pkFieldId == null || inDomainQuery.isQueryBounded() && (long)inDomainQuery.getMaxResults() <= FrameworkConfigConsts.HYDRATION_QUERY_BATCH_SIZE.getValue(TransactionParms.getBoundParms().getUserContext())) {
            rh = this.executeDomainQuery(1, inDomainQuery);
            valueArrayList = this.getFinderValues(inDomainQuery, formatForUI, outputFields, rh);
        } else {
            rh = this.executeDomainQuery(3, inDomainQuery);
            valueArrayList = this.executeHydrationByBatches(rh, inDomainQuery, pkFieldId, outputFields);
        }
        return new ArrayBackedQueryResult(queryEntityName, pkFieldId, outputFields, valueArrayList, rh.getTotalResults(), rh);
    }

    private List<Object[]> getFinderValues(IDomainQuery inDomainQuery, boolean inFormatForUI, MetafieldIdList inOutputFields, ResultHolder inRh) {
        ArrayList<Object[]> valueArrayList = new ArrayList<Object[]>();
        for (Object row : inRh.getResults()) {
            Object[] values;
            if (row instanceof IEntity) {
                IEntity entity = (IEntity)row;
                if (entity instanceof IDynamicHibernatingEntity) {
                    ((IDynamicHibernatingEntity)entity).setEntityName(inDomainQuery.getQueryEntityName());
                }
                values = entity.extractFieldValueArray(inOutputFields, inFormatForUI);
            } else if (row instanceof Object[]) {
                values = this.extractValuesFromEntitySetFromExplicitJoinQuery(inOutputFields, (Object[])row, inFormatForUI);
            } else {
                LOGGER.error((Object)"Hibernate Query returned unknown type");
                values = new Object[inOutputFields.getSize()];
            }
            valueArrayList.add(values);
        }
        return valueArrayList;
    }

    @NotNull
    private List<Object[]> executeHydrationByBatches(@NotNull ResultHolder inKeysRh, final @NotNull IDomainQuery inOriginalQuery, final @NotNull IMetafieldId inPkField, final @NotNull MetafieldIdList inOutputFieldList) {
        List<Object[]> values;
        boolean shouldPreserveOrdering;
        ArrayList keys = new ArrayList();
        List queryKeys = inKeysRh.getResults();
        if (queryKeys != null && !queryKeys.isEmpty()) {
            keys.addAll(new LinkedHashSet(queryKeys));
        }
        int count = keys.size();
        int pkIdx = inOutputFieldList.indexOf(inPkField);
        boolean bl = shouldPreserveOrdering = inOriginalQuery.getOrderings() != null && !inOriginalQuery.getOrderings().isEmpty();
        if (count > 0) {
            int i;
            final List[] gKeyList = new List[1];
            int batchSize = (int) FrameworkConfigConsts.HYDRATION_QUERY_BATCH_SIZE.getValue(TransactionParms.getBoundParms().getUserContext());
            BatchHydratedQueryResult resultValues = new BatchHydratedQueryResult(queryKeys, pkIdx, batchSize, shouldPreserveOrdering);
            int batches = count / batchSize + (count % batchSize > 0 ? 1 : 0);
            int startIdx = i = 0;
            while (i < batches) {
                int endIdx = i == batches - 1 ? count : startIdx + batchSize;
                gKeyList[0] = keys.subList(startIdx, endIdx);
                PersistenceTemplate template = new PersistenceTemplate(TransactionParms.getBoundParms().getUserContext());
                IMessageCollector mc = template.invoke(new CarinaPersistenceCallback(){

                    @Override
                    public void doInTransaction() {
//                        this.getHibernateApi().setFlushMode(FlushMode.NEVER);
                        this.getHibernateApi().setFlushMode(FlushMode.MANUAL);
                        IDomainQuery dq = (IDomainQuery)inOriginalQuery.createEnhanceableClone();
                        dq.addDqPredicate(PredicateFactory.in(inPkField, gKeyList[0]));
                        ResultHolder rh = this.getHibernateApi().executeDomainQuery(1, dq);
                        List valueArrayList = HibernateApi.this.getFinderValues(dq, dq.isFormatResultsForUI(), inOutputFieldList, rh);
                        this.setResult(valueArrayList);
                    }
                });
                if (mc.hasError()) {
//                    throw new MessageCollectorWrapperException(mc);
                }
                resultValues.addBatchToResults((List)template.getResult());
                ++i;
                startIdx = endIdx;
            }
            resultValues.finish();
            values = resultValues.getResultList();
        } else {
            values = Collections.emptyList();
        }
//        return values;
        return null;
    }

    private void addToResults(int inIndex, Object[] inRow, Map inParentMap) {
        if (inIndex == inRow.length - 2) {
            inParentMap.put(inRow[inIndex], inRow[inIndex + 1]);
        } else {
            LinkedHashMap subMap = (LinkedHashMap)inParentMap.get(inRow[inIndex]);
            if (subMap == null) {
                subMap = new LinkedHashMap();
                inParentMap.put(inRow[inIndex], subMap);
            }
            this.addToResults(inIndex + 1, inRow, subMap);
        }
    }

    private Object[] extractValuesFromEntitySetFromExplicitJoinQuery(MetafieldIdList inFieldIds, Object[] inEntitySet, boolean inFormatForUi) {
        Object[] values = new Object[inFieldIds.getSize()];
        int j = -1;
        block0: for (IMetafieldId fieldId : inFieldIds) {
            DatabaseEntity joinedEntity;
            ++j;
            boolean foundRootClass = false;
            Class rootClass = HiberCache.getEntityClassForField(fieldId.getMfidLeftMostNode().getFieldId());
            if (rootClass != null) {
                for (Object anInEntitySet : inEntitySet) {
                    joinedEntity = (DatabaseEntity)anInEntitySet;
                    if (joinedEntity == null || !rootClass.isAssignableFrom(joinedEntity.getClass())) continue;
                    values[j] = joinedEntity.getFieldValue(fieldId, inFormatForUi);
                    foundRootClass = true;
                    break;
                }
            }
            if (foundRootClass) continue;
            LOGGER.info((Object)("extractValuesFromEntitySetFromExplicitJoinQuery for bizmetafield : " + fieldId));
            for (Object anInEntitySet : inEntitySet) {
                Object value;
                joinedEntity = (DatabaseEntity)anInEntitySet;
                if (joinedEntity == null || (value = joinedEntity.getFieldValue(fieldId, inFormatForUi)) == null) continue;
                values[j] = value;
                continue block0;
            }
        }
        return values;
    }

    private ResultHolder executeDomainQuery(int inQueryTask, IDomainQuery inDomainQuery) {
        String queryType;
        TransactionParms tp;
        IDomainQuery domainQueryClone = (IDomainQuery)inDomainQuery.createEnhanceableClone();
        String sqlAlias = "e";
        if (this._entityScoper != null && (tp = TransactionParms.getBoundParms()) != null && tp.isScopingEnabled()) {
            UserContext userContext = tp.getUserContext();
            domainQueryClone = this._entityScoper.enhanceDomainQuery(domainQueryClone, userContext);
        }
        KeyValuePair[] subs = domainQueryClone.getFieldValues();
        Type[] types = new Type[subs.length];
        Object[] values = new Object[subs.length];
        for (int i = 0; i < subs.length; ++i) {
            Object value;
            int index;
            String fieldId = subs[i].getKey();
            if (fieldId == null) {
                LOGGER.error((Object)("executeDomainQuery: fieldId is null, dq= " + domainQueryClone));
            }
            if ((index = fieldId.lastIndexOf(46)) > 0) {
                fieldId = fieldId.substring(index + 1);
            }
            if ((value = subs[i].getValue()) == null) {
                throw BizFailure.create("Value cannot be empty for the field: " + fieldId);
            }
            Type type = null;
            try {
                Class<?> valueClass = value.getClass();
  //              type = Geometry.class.isAssignableFrom(valueClass) ? Hibernate.custom(GeometryUserType.class) : TypeFactory.heuristicType((String)valueClass.getName());
            }
            catch (MappingException e) {
                LOGGER.error((Object)("findByDomainQuery: Could not determine Type of argument for field " + fieldId));
            }
            types[i] = type;
            values[i] = value;
        }
        StringBuilder hql = new StringBuilder();
        switch (inQueryTask) {
            case 0: {
                hql.append(domainQueryClone.toHqlSelectString("e")).append(' ').append(domainQueryClone.toHqlObjectQueryString("e"));
                queryType = "scalar query: ";
                break;
            }
            case 1: {
                if (domainQueryClone.areFieldsDistinct()) {
                    if (domainQueryClone.isDistinctReplacement()) {
                        IDomainQuery outerDomainQuery = QueryUtils.createDomainQuery(domainQueryClone.getEntityId());
                        List orderings = domainQueryClone.getOrderings();
                        domainQueryClone.setOrderings(Collections.emptyList());
                        domainQueryClone.clearDqFields().addDqField(IMetafieldId.PRIMARY_KEY);
                        domainQueryClone.setDqFieldsDistinct(false);
                        domainQueryClone.addDqPredicate(PredicateFactory.eqProperty(IMetafieldId.PRIMARY_KEY, OuterQueryMetafieldId.valueOf(IMetafieldId.PRIMARY_KEY)));
                        outerDomainQuery.addDqPredicate(PredicateFactory.subQueryExists(domainQueryClone));
                        if (orderings != null && !orderings.isEmpty()) {
                            Ordering[] movedOrderings = new Ordering[orderings.size()];
                            orderings.toArray(movedOrderings);
                            outerDomainQuery.addDqOrderings(movedOrderings);
                        }
                        domainQueryClone = outerDomainQuery;
                    } else {
                        hql.append("select distinct(e) ");
                    }
                }
                hql.append(domainQueryClone.toHqlObjectQueryString("e"));
                queryType = "hydrating query: ";
                break;
            }
            case 3: {
                hql.append("select e.id ").append(domainQueryClone.toHqlObjectQueryString("e"));
                queryType = "primary key query: ";
                break;
            }
            case 4: {
                hql.append(domainQueryClone.toHqlRecapString("e"));
                queryType = "recap query: ";
                break;
            }
            case 6: {
                hql.append(domainQueryClone.toHqlRecapSumString("e"));
                queryType = "recap summary query: ";
                break;
            }
            case 2: {
                queryType = "count query: ";
                break;
            }
            case 5: {
                hql.append(domainQueryClone.toHqlExistsString("e"));
                queryType = "exists query: ";
                break;
            }
            default: {
                throw BizFailure.create("unknown query task: " + inQueryTask);
            }
        }
        if (HQL_LOGGER.isDebugEnabled()) {
            StringBuilder b = new StringBuilder();
            b.append("HQL ").append(queryType).append((CharSequence)hql);
            HQL_LOGGER.debug((Object)b.toString());
        }
        Session session = null;
        IQueryFilter domainQueryFilter = null;
        try {
            ResultHolder result;
            int totalResults;
            IDiagnosticEvent diagnostic;
            session = SessionFactoryUtils.getSession(this.getSessionFactory(), false);
            List queryResults = null;
            int firstResult = 0;
            if (inQueryTask != 2 && inQueryTask != 5) {
                String hqlQueryString = hql.toString();
                Query q = session.createQuery(hqlQueryString);
                diagnostic = this.recordQueryEvent(domainQueryClone, hqlQueryString, values);
                if (domainQueryClone.isQueryBounded()) {
                    firstResult = domainQueryClone.getFirstResult();
                    q.setFirstResult(firstResult);
                    q.setMaxResults(domainQueryClone.getMaxResults());
                }
                if (domainQueryClone.isSelectForUpdate()) {
                    q.setLockMode("e", LockMode.UPGRADE);
                }
                q.setTimeout(domainQueryClone.getSelectTimeOutSecs());
                q.setComment(domainQueryClone.getDevComment());
                this.setQueryParameters(q, types, values);
                domainQueryFilter = domainQueryClone.getFilter();
                if (domainQueryFilter != null) {
                    this.enableFilter(session, domainQueryFilter);
                }
                CodeTimer ct = new CodeTimer(HQL_LOGGER, Level.DEBUG, 1000L);
                queryResults = q.list();
                ct.report("execute query");
                if (diagnostic != null) {
                    diagnostic.setDuration(ct.getTotalMillis());
                }
            }
            if (inQueryTask == 5) {
                this.convertCustomUserTypes(types, values);
                String hqlExistsString = domainQueryClone.toHqlExistsString("e");
                Query q = session.createQuery(hqlExistsString);
                q.setComment(domainQueryClone.getDevComment());
                this.setQueryParameters(q, types, values);
                IDiagnosticEvent diagnostic2 = this.recordQueryEvent(domainQueryClone, hqlExistsString, values);
                queryResults = q.list();
                if (diagnostic2 != null) {
                    diagnostic2.endDuration();
                }
                totalResults = queryResults.size();
            } else if (domainQueryClone.isQueryBounded() || inQueryTask == 2) {
                if (domainQueryClone.isTotalCountRequired()) {
                    if (domainQueryClone.getMaxResults() != 1) {
                        this.convertCustomUserTypes(types, values);
                        String countString = domainQueryClone.toHqlCountString("e");
                        diagnostic = this.recordQueryEvent(domainQueryClone, countString, values);
                       // Object count = session.iterate(countString, values, types).next();
                        Object count = null;
                        if (diagnostic != null) {
                            diagnostic.endDuration();
                        }
                        totalResults = ((Number)count).intValue();
                    } else {
                        totalResults = queryResults.size();
                    }
                } else {
                    totalResults = -1;
                }
            } else {
                totalResults = queryResults.size();
            }
            ResultHolder resultHolder = result = new ResultHolder(queryResults, domainQueryClone.isQueryBounded(), firstResult, domainQueryClone.getMaxResults(), totalResults);
            return resultHolder;
        }
        catch (HibernateException ex) {
            RuntimeException convertedEx = this.convertHibernateException(ex);
//            if (convertedEx instanceof CustomDataAccessException) {
//                BizFailure bf = BizFailure.create(((CustomDataAccessException)((Object)convertedEx)).getPropertyKey(), null);
//                LOGGER.error((Object)("executeDomainQuery: " + bf.getStackTraceString()));
//                throw bf;
//            }
            BizFailure bf = BizFailure.create(IFrameworkPropertyKeys.HIBERNATE__QUERY_FAILURE, convertedEx, hql.toString(), convertedEx.getMessage(), domainQueryClone.toString());
            LOGGER.error((Object)("executeDomainQuery: " + bf.getStackTraceString()));
            throw bf;
        }
        finally {
            if (domainQueryFilter != null) {
                session.disableFilter(domainQueryFilter.getFilterDefinition());
            }
        }
    }

    @Nullable
    private IDiagnosticEvent recordQueryEvent(IDomainQuery inDq, String inSql, Object[] inParms) {
        try {
            IDiagnosticRequestManager requestManager = DiagnosticUtils.shouldRecordDiagnostic();
            if (requestManager != null) {
//                QueryRequestEvent event = new QueryRequestEvent(inDq, inSql, inParms);
//                requestManager.recordEvent(event);
//                return event;
            }
        }
        catch (Throwable e) {
            LOGGER.error((Object)("Error recording diagnostic event " + inSql + " because of " + e));
        }
        return null;
    }

    private void setQueryParameters(Query inQuery, Type[] inTypes, Object[] inValues) {
        if (inValues == null || inTypes == null) {
            return;
        }
        LOGGER.debug((Object)("setQueryParameters: Binding " + inValues.length + " query parameter(s)"));
        for (int i = 0; i < inValues.length; ++i) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug((Object)("  " + i + ": value='" + inValues[i] + "' type=" + inTypes[i].getName()));
            }
            if (inValues[i] instanceof AtomizedEnum) {
                Type persistentType = this.getPersistentTypeForEnum((AtomizedEnum)inValues[i]);
                inQuery.setParameter(i, inValues[i], persistentType);
                continue;
            }
            inQuery.setParameter(i, inValues[i], inTypes[i]);
        }
    }

    private Type getPersistentTypeForEnum(AtomizedEnum inEnum) {
        try {
            String persistentClassName = inEnum.getMappingClassName();
            Class<?> persistentType = inEnum.getClass().getClassLoader().loadClass(persistentClassName);
      //      return Hibernate.custom(persistentType);
            return null;
        }
        catch (Exception e) {
            throw BizFailure.create(IFrameworkPropertyKeys.FAILURE__CAN_NOT_RESOLVE_ATOM, e, "Can't convert enum class");
        }
    }

    private void convertCustomUserTypes(Type[] inOutTypes, Object[] inValues) {
        if (inValues == null || inOutTypes == null) {
            return;
        }
        for (int i = 0; i < inValues.length; ++i) {
            Type persistentType;
            if (inValues[i] instanceof AtomizedEnum) {
                inOutTypes[i] = persistentType = this.getPersistentTypeForEnum((AtomizedEnum)inValues[i]);
                continue;
            }
            if (!(inValues[i] instanceof Geometry)) continue;
   //         inOutTypes[i] = persistentType = Hibernate.custom(GeometryUserType.class);
        }
    }

    private void enableFilter(Session inSession, IQueryFilter inDomainQueryFilter) {
        Object filteredValue = inDomainQueryFilter.getFilteredValue();
 //       if (!AllLifeCycleStatesEnum.ALL.equals(filteredValue))
        {
            String filterDefinition = inDomainQueryFilter.getFilterDefinition();
            String filterParameter = inDomainQueryFilter.getFilterParameterName();
            inSession.enableFilter(filterDefinition).setParameter(filterParameter, filteredValue);
        }
    }

    private static final class ResultHolder
            implements IBounded {
        private final List _results;
        private final boolean _wasQueryBounded;
        private final int _firstResult;
        private final int _maxResults;
        private final int _totalResults;

        private ResultHolder(List inResults, boolean inWasQueryBounded, int inFirstResult, int inMaxResults, int inTotalResults) {
            this._results = inResults;
            this._wasQueryBounded = inWasQueryBounded;
            this._firstResult = inFirstResult;
            this._maxResults = inMaxResults;
            this._totalResults = inTotalResults;
        }

        @Override
        public int getMaxResults() {
            return this._maxResults;
        }

        @Override
        public int getFirstResult() {
            return this._firstResult;
        }

        @Override
        public boolean isQueryBounded() {
            return this._wasQueryBounded;
        }

        public boolean isWasQueryBounded() {
            return this._wasQueryBounded;
        }

        public int getTotalResults() {
            return this._totalResults;
        }

        public List getResults() {
            return this._results;
        }
    }

    static class BatchHydratedQueryResult<T> {
        private final int _pkeyIdx;
        private final int _batchSize;
        private final int _resultSize;
        private final boolean _preserveOrdering;
        private final List<Serializable> _keys;
        private final List<T[]> _resultList;
        private final Queue<T[]> _waitingList;
        private int _keysCurrentIdx;

        BatchHydratedQueryResult(@NotNull List<Serializable> inKeys, int inPkeyIdx, int inBatchSize, boolean inQueryHasOrdering) {
            this._keys = inKeys;
            this._pkeyIdx = inPkeyIdx;
            this._resultSize = this._keys.size();
            this._resultList = new ArrayList<T[]>(this._resultSize);
            this._waitingList = new LinkedList<T[]>();
            this._batchSize = inBatchSize;
            this._preserveOrdering = inQueryHasOrdering && this._resultSize > this._batchSize;
        }

        public void addBatchToResults(@NotNull List<T[]> inBatchResults) {
            if (this._preserveOrdering) {
                T[] entityValues;
                int batchNextIdx;
                int currentBatchSize = inBatchResults.size();
                for (batchNextIdx = 0; batchNextIdx < currentBatchSize; ++batchNextIdx) {
                    entityValues = inBatchResults.get(batchNextIdx);
                    if (!this._keys.get(this._keysCurrentIdx).equals(entityValues[this._pkeyIdx])) break;
                    ++this._keysCurrentIdx;
                }
                this._resultList.addAll(inBatchResults.subList(0, batchNextIdx));
                if (batchNextIdx < currentBatchSize) {
                    this._waitingList.addAll(inBatchResults.subList(batchNextIdx, currentBatchSize));
                }
                while ((entityValues = this._waitingList.peek()) != null && this._keys.get(this._keysCurrentIdx).equals(entityValues[this._pkeyIdx])) {
                    this._resultList.add(this._waitingList.poll());
                    ++this._keysCurrentIdx;
                }
            } else {
                this._resultList.addAll(inBatchResults);
            }
        }

        public void finish() {
            if (this._resultList.size() + this._waitingList.size() != this._keys.size()) {
                LogUtils.forceLogAtInfo(LOGGER, "Data change detected while query is running: this=" + this);
            }
            while (!this._waitingList.isEmpty() && this._keysCurrentIdx < this._keys.size()) {
                Serializable keyListKey = this._keys.get(this._keysCurrentIdx);
                Iterator iter = this._waitingList.iterator();
                while (iter.hasNext()) {
                    Object[] waitingListEntityValues = (Object[])iter.next();
                    Object waitingListEntityKey = waitingListEntityValues[this._pkeyIdx];
                    if (!keyListKey.equals(waitingListEntityKey)) continue;
                    //this._resultList.add(waitingListEntityValues);
                    iter.remove();
                    break;
                }
                ++this._keysCurrentIdx;
            }
        }

        public List<T[]> getResultList() {
            return this._resultList;
        }

        public String toString() {
            StringBuilder buf = new StringBuilder(200);
            buf.append("Query [_preserveOrdering]: ").append(this._preserveOrdering).append('\n');
            buf.append("Batch [size =").append(this._batchSize).append("] PKey idx =").append(this._pkeyIdx).append('\n');
            buf.append("Keys [size =").append(this._keys == null ? null : Integer.valueOf(this._keys.size()));
            buf.append("]  Results [size =").append(this._resultList == null ? null : Integer.valueOf(this._resultList.size()));
            buf.append("]  Waiting list [size =").append(this._waitingList == null ? null : Integer.valueOf(this._waitingList.size())).append("]\n");
            return buf.toString();
        }
    }
}
