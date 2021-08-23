package com.zpmc.ztos.infra.base.common.model;

import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.business.model.MetafieldIdFactory;
import com.zpmc.ztos.infra.base.business.model.MetafieldIdList;
import com.zpmc.ztos.infra.base.business.predicate.PredicateFactory;
import com.zpmc.ztos.infra.base.common.database.HiberCache;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.common.enums.AtomizedEnum;
import com.zpmc.ztos.infra.base.common.exceptions.BizFailure;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.utils.AtomizedEnumUtils;
import com.zpmc.ztos.infra.base.common.utils.DateUtil;

import com.zpmc.ztos.infra.base.utils.QueryUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.enums.Enum;

import org.apache.log4j.Logger;
import org.hibernate.EntityMode;
import org.hibernate.HibernateException;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.property.Getter;
import org.hibernate.type.ComponentType;
import org.hibernate.type.Type;
import org.hibernate.util.ReflectHelper;
//import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

//public abstract class DatabaseEntity extends CommonDO implements IEntity {
public abstract class DatabaseEntity implements IEntity, IModelExtensibleHibernatingEntity {

    private Map _entityCustomDynamicFields;
    private static final Logger LOGGER = Logger.getLogger(DatabaseEntity.class);
    private static final Logger DEV_LOGGER = Logger.getLogger((String)("dev." + DatabaseEntity.class));


    @Override
    public ValueObject getValueObject(MetafieldIdList inMetafieldIdList) {
        int i;
        MetafieldIdList fieldsToExtract;
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug((Object)("getValueObject: " + this.getEntityName() + ':' + this.getPrimaryKey()));
        }
        if ((fieldsToExtract = inMetafieldIdList) == null || fieldsToExtract.getSize() == 0) {
            ClassMetadata meta = HiberCache.getClassMetadata(this.getEntityName());
            String[] properties = meta.getPropertyNames();
            fieldsToExtract = new MetafieldIdList(properties.length);
            for (i = 0; i < properties.length; ++i) {
                String field = properties[i];
                Type fieldType = HiberCache.getFieldType(field);
                if (fieldType == null || fieldType.isEntityType() || fieldType.isCollectionType()) continue;
                fieldsToExtract.add(MetafieldIdFactory.valueOf(field));
            }
            fieldsToExtract.add(this.getPrimaryKeyMetaFieldId());
        }
        ValueObject vao = new ValueObject(this.getEntityName());
        Object[] values = this.extractFieldValueArray(fieldsToExtract, false);
        for (i = 0; i < values.length; ++i) {
            vao.setFieldValue(fieldsToExtract.get(i), values[i]);
        }
        vao.setEntityPrimaryKey(this.getPrimaryKey());
        return vao;
    }

    @Override
    public ValueObject getValueObject() {
        return this.getValueObject(new MetafieldIdList());
    }

    @Override
    public ValueObject getDefaultCreateValues(IDefaultCreateValuesContext inContext) {
        return this.getValueObject();
    }

    protected void preProcessValueObjectExtract() {
    }

    @Override
    public void preDelete() {
    }

    @Override
    @Nullable
    public String getHumanReadableKey() {
        Serializable pk = this.getPrimaryKey();
        return pk != null ? this.getEntityName() + ':' + pk.toString() : this.getEntityName() + ":??";
    }

    @Override
    public String getEntityName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public Serializable getPrimaryKey() {
        try {
            return this.getClassMetadata().getIdentifier((Object)this,
                    EntityMode.POJO);
        }
        catch (HibernateException e) {
            throw new BizFailure(IFrameworkPropertyKeys.HIBERNATE__GENERIC, e, new Object[]{"getPrimaryKey()", this, this.getClass()});
        }
    }

    @Override
    @Nullable
    public Object getField(IMetafieldId inMetafieldId) {
        IMetafieldId qualifiyingMetafieldId = inMetafieldId.getQualifyingMetafieldId();
        if (qualifiyingMetafieldId == null) {
            String propertyName = inMetafieldId.getFieldId();
            Type fieldType = HiberCache.getFieldType(propertyName);
            if (inMetafieldId == IMetafieldId.PRIMARY_KEY) {
                return this.getPrimaryKey();
            }
            if (HiberCache.isPrimaryKeyField(propertyName)) {
                return this.getPrimaryKey();
            }
            if (fieldType == null) {
                return DatabaseEntity.getSyntheticField(this, inMetafieldId);
            }
            ClassMetadata classMetadata = HiberCache.getClassMetadata(this.getEntityName());
            if (classMetadata == null) {
                throw BizFailure.create(IFrameworkPropertyKeys.HIBERNATE__CLASS_NOT_HIBERNATING, null, propertyName, this, this.getClass());
            }
            try {
                 return classMetadata.getPropertyValue((Object)this, propertyName, EntityMode.POJO);
                //return classMetadata.getPropertyValue((Object)this, propertyName);
            }
            catch (HibernateException e) {
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug((Object)("getField: Hibernate threw, carina assumes this had to do with fetch of subclass property: " + (Object)((Object)e)));
                }
                return null;
            }
        }
        ClassMetadata classMetadata = HiberCache.getClassMetadata(this.getEntityName());
        Object sourceEntity = null;
        if (classMetadata == null) {
            throw BizFailure.create("Class metadata was not found for " + inMetafieldId);
        }
        try {
            sourceEntity = classMetadata.getPropertyValue((Object)this, qualifiyingMetafieldId.getFieldId(), EntityMode.POJO);
        }
        catch (HibernateException e) {
            return null;
        }
        if (sourceEntity == null) {
            return null;
        }
        if (sourceEntity instanceof DatabaseEntity) {
            DatabaseEntity nextEntity = (DatabaseEntity)sourceEntity;
            return nextEntity.getField(inMetafieldId.getMfidExcludeLeftMostNode());
        }
        Type propertyType = classMetadata.getPropertyType(qualifiyingMetafieldId.getFieldId());
        if (propertyType == null || !propertyType.isComponentType()) {
            LOGGER.error((Object)("getField: could not extract field " + inMetafieldId + " from Entity " + this));
            return null;
        }
        ComponentType compType = (ComponentType)propertyType;
        IMetafieldId fieldId = inMetafieldId.getQualifiedMetafieldId();
        boolean isRelationshipField = fieldId.getQualifyingMetafieldId() != null;
        String soughtProperty = isRelationshipField ? fieldId.getQualifyingMetafieldId().getFieldId() : fieldId.getFieldId();
        String[] subnames = compType.getPropertyNames();
        for (int j = 0; j < subnames.length; ++j) {
            if (!soughtProperty.equals(subnames[j])) continue;
            Object value = compType.getPropertyValue(sourceEntity, j, EntityMode.POJO);
            if (value == null || !isRelationshipField) {
                return value;
            }
            return ((IEntity)value).getField(fieldId.getQualifiedMetafieldId());
        }
        LOGGER.error((Object)("getField: could not find component property: " + fieldId));
        return null;
    }

    private static Object getSyntheticField(IValueSource inSourceEntity, IMetafieldId inFieldId) {
        String sourceBeanName;
        IMetafieldDictionary metafieldDictionary = Roastery.getMetafieldDictionary();
        IMetafield field = metafieldDictionary.findMetafield(inFieldId.getMfidRightMostNode());
        String string = sourceBeanName = field == null ? null : field.getSourceBean();
        if (sourceBeanName != null) {
            Object bean = Roastery.getBean(sourceBeanName);
            if (bean instanceof IFieldSynthesizer) {
                try {
                    Object synthesizedValue = ((IFieldSynthesizer)bean).synthesizeFieldValue(field.getMetafieldId(), field.getDepends(), inSourceEntity);
                    return synthesizedValue;
                }
                catch (Exception e) {
                    LOGGER.error((Object)("getSyntheticField: Error invoking method to calculate synthetic field " + inFieldId + "  " + e));
                }
            } else {
                LOGGER.error((Object)(sourceBeanName + "  source bean is not an implementation of IFieldSynthesizer for field " + inFieldId));
            }
            return null;
        }
        String propertyName = inFieldId.getFieldId();
        try {
            Getter getter = ReflectHelper.getGetter(inSourceEntity.getClass(), (String)propertyName);
            return getter.get((Object)inSourceEntity);
        }
        catch (HibernateException e) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug((Object)("getField: Hibernate threw an exception, carina assumes this had to do with fetch of subclass-synthetic property: " + (Object)((Object)e)));
            }
            return null;
        }

    }

    @Override
    public Object getFieldValue(IMetafieldId inMetafieldId) {
        return this.getFieldValue(inMetafieldId, false);
    }

    @Nullable
    public Object getFieldValue(IMetafieldId inMetafieldId, boolean inFormatForUI) {
        String propertyName = inMetafieldId.getFieldId();
        Type fieldType = HiberCache.getFieldType(propertyName);
        Object rawValue = this.getField(inMetafieldId);
        return HibernateApi.getInstance().convertPropertyValue(inMetafieldId, rawValue, fieldType, inFormatForUI);
    }

    @Override
    public Object[] extractFieldValueArray(MetafieldIdList inRequestedFields, boolean inFormatResultsForUI) {
        this.preProcessValueObjectExtract();
        String entityName = this.getEntityName();
        Object[] values = new Object[inRequestedFields.getSize()];
        int index = 0;
        for (IMetafieldId metafield : inRequestedFields) {
            Object fieldValue = this.getFieldValue(metafield, inFormatResultsForUI);
            values[index++] = fieldValue;
        }
        return values;
    }

    @Override
    @Nullable
    public IMetafieldId getPrimaryKeyMetaFieldId() {
        return MetafieldIdFactory.valueOf(this.getClassMetadata().getIdentifierPropertyName());
    }

    @Override
    @Nullable
    public Long getFieldLong(IMetafieldId inMetaFieldId) {
        Object value = this.getFieldValue(inMetaFieldId);
        if (value == null) {
            return null;
        }
        if (value instanceof Long) {
            return (Long)value;
        }
        throw new BizFailure(IFrameworkPropertyKeys.FRAMEWORK__WRONG_TYPE_FOR_FIELD, null, new Object[]{this, inMetaFieldId.getQualifiedId(), Long.class});
    }

    @Override
    @Nullable
    public String getFieldString(IMetafieldId inMetaFieldId) {
        Object value = this.getFieldValue(inMetaFieldId);
        if (value == null) {
            return null;
        }
        if (value instanceof String) {
            return (String)value;
        }
        throw new BizFailure(IFrameworkPropertyKeys.FRAMEWORK__WRONG_TYPE_FOR_FIELD, null, new Object[]{this, inMetaFieldId.getQualifiedId(), String.class});
    }

    @Override
    @Nullable
    public Date getFieldDate(IMetafieldId inMetaFieldId) {
        Object value = this.getFieldValue(inMetaFieldId);
        if (value == null) {
            return null;
        }
        if (value instanceof Date) {
            return (Date)value;
        }
        throw new BizFailure(IFrameworkPropertyKeys.FRAMEWORK__WRONG_TYPE_FOR_FIELD, null, new Object[]{this, inMetaFieldId.getQualifiedId(), Date.class});
    }

    @Override
    public void setPrimaryKey(Serializable inPrimaryKey) {
        try {
//            this.getClassMetadata().setIdentifier((Object)this, inPrimaryKey, EntityMode.POJO);
        }
        catch (HibernateException e) {
            throw new BizFailure(IFrameworkPropertyKeys.HIBERNATE__GENERIC, e, new Object[]{"setIsPrimaryKey()", this, this.getClass()});
        }
    }

    protected boolean shouldCheckDirty() {
        return true;
    }

    private void checkStalenessByDirtyCheck(FieldChanges inFieldChanges) throws BizViolation {
        if (!this.shouldCheckDirty()) {
            return;
        }
        BizViolation bv = null;
        if (this.getPrimaryKey() == null) {
            return;
        }
        IMetafieldId primaryKey = this.getPrimaryKeyMetaFieldId();
        Iterator<FieldChange> iterator = inFieldChanges.getIterator();
        while (iterator.hasNext()) {
            FieldChange fc = iterator.next();
            IMetafieldId fieldId = fc.getMetafieldId();
            if (fieldId == primaryKey || HiberCache.isSyntheticField(fieldId.getFieldId()) || !fc.isPriorValueKnown()) continue;
            Object currentValue = this.getFieldValue(fieldId);
            Object priorValue = fc.getPriorValue();
            Object newValue = fc.getNewValue();
            boolean collision = false;
            if (currentValue != null && priorValue != null && currentValue.getClass().isArray() && priorValue.getClass().isArray()) {
                if (currentValue instanceof byte[] && priorValue instanceof byte[] && !ArrayUtils.isEquals((Object)currentValue, (Object)priorValue)) {
                    collision = !ArrayUtils.isEquals((Object)currentValue, (Object)newValue);
                }
            } else if (!ObjectUtils.equals((Object)currentValue, (Object)priorValue)) {
                boolean bl = collision = !ObjectUtils.equals((Object)currentValue, (Object)newValue);
            }
            if (!collision) continue;
            bv = BizViolation.createFieldViolation(IFrameworkPropertyKeys.FAILURE__UPDATE_STALE, bv, fieldId, priorValue, newValue, currentValue);
        }
        if (bv != null) {
            throw bv;
        }
    }

    @Override
    public void applyFieldChanges(FieldChanges inFieldChanges) {
        try {
            this.checkStalenessByDirtyCheck(inFieldChanges);
        }
        catch (BizViolation bv) {
            throw BizFailure.wrap(bv);
        }
        Iterator<FieldChange> iterator = inFieldChanges.getIterator();
        while (iterator.hasNext()) {
            FieldChange fieldChange = iterator.next();
            this.setFieldValue(fieldChange.getMetafieldId(), fieldChange.getNewValue());
        }
    }

    /*
     * WARNING - void declaration
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public void setFieldValue(IMetafieldId inFieldId, Object inFieldValue) {
        Type propertyType;
        IMetafieldId qualifiyingId = inFieldId.getQualifyingMetafieldId();
        if (qualifiyingId == null) {
            this.internalSetFieldValue(inFieldId, inFieldValue);
            return;
        }
        ClassMetadata classMetadata = HiberCache.getClassMetadata(this.getEntityName());
        try {
            propertyType = classMetadata.getPropertyType(qualifiyingId.getFieldId());
        }
        catch (HibernateException e) {
            throw BizFailure.create("Could not find a property definition for " + qualifiyingId + " in the class metadata for " + this);
        }
        Object nextEntity = classMetadata.getPropertyValue((Object)this, qualifiyingId.getFieldId(), EntityMode.POJO);
//        if (nextEntity == null) {
//            void var9_14;
//            if (!propertyType.isComponentType()) throw BizFailure.create("Attempt to update a field of a non-existent related entity. field <" + inFieldId + "> base entity <" + this + '>');
//            LOGGER.error((Object)("getFinalEntity: NULL COMPONENT: adding to " + this + " at sourceId = " + qualifiyingId));
//            ComponentType compType = (ComponentType)propertyType;
//            Class compClass = compType.getReturnedClass();
//            if (compClass.isAssignableFrom(Map.class)) {
//                HashMap hashMap = new HashMap();
//            } else {
//                try {
//                    Object t = compClass.newInstance();
//                }
//                catch (Exception e) {
//                    throw BizFailure.wrap(e);
//                }
//            }
//            this.internalSetFieldValue(inFieldId.getMfidLeftMostNode(), var9_14);
//            this.internalSetComponentProperty(var9_14, inFieldId.getFieldId(), inFieldValue, (ComponentType)propertyType);
//            return;
//        }
        if (nextEntity instanceof DatabaseEntity) {
            ((DatabaseEntity)nextEntity).setFieldValue(inFieldId.getMfidExcludeLeftMostNode(), inFieldValue);
            return;
        } else {
            IMetafieldId componentFieldId = inFieldId.getMfidExcludeLeftMostNode();
            qualifiyingId = componentFieldId.getQualifyingMetafieldId();
            if (qualifiyingId != null) {
                Object elementEntity = this.getField(inFieldId.getMfidExcludeRightMostNode());
                if (elementEntity == null) {
                    throw BizFailure.create("Attempt to update a field of a null entity element of a component. field <" + inFieldId + "> base entity <" + this + '>');
                }
                if (!(elementEntity instanceof DatabaseEntity)) {
                    throw BizFailure.create("We don't support nested components field <" + inFieldId + "> base entity <" + this + '>');
                }
                ((DatabaseEntity)elementEntity).setFieldValue(componentFieldId.getMfidRightMostNode(), inFieldValue);
                return;
            } else {
                this.internalSetComponentProperty(nextEntity, componentFieldId.getFieldId(), inFieldValue, (ComponentType)propertyType);
            }
        }
    }

    private ClassMetadata getClassMetadata() {
        return HiberCache.getClassMetadata(this.getEntityName());
    }

    public boolean isUniqueInClass(IMetafieldId inUniqueField, boolean inIsScopingEnabled) {
        Object fieldValue = this.getFieldValue(inUniqueField);
        if (fieldValue == null) {
            return false;
        }
//        IDomainQuery dq = this.getDomainQuery(inUniqueField, fieldValue);
//        dq.setScopingEnabled(inIsScopingEnabled);
//        return !HibernateApi.getInstance().existsByDomainQuery(dq);
        return false;
    }

    public boolean isUniqueInClass(IMetafieldId inUniqueField) {
        Object fieldValue = this.getFieldValue(inUniqueField);
        if (fieldValue == null) {
            return false;
        }
//        IDomainQuery dq = this.getDomainQuery(inUniqueField, fieldValue);
//        return !HibernateApi.getInstance().existsByDomainQuery(dq);
        return false;
    }

    public BizViolation validateUniqueKeys(BizViolation inViolation) {
        IEntityUniquenessAware uniqueness;
        IMetafieldId[] uniqueKeys;
        if (this instanceof IEntityUniquenessAware && !this.isUniqueInClass(new MetafieldIdList(uniqueKeys = (uniqueness = (IEntityUniquenessAware)((Object)this)).getUniqueKeyFieldIds()))) {
            return BizViolation.createFieldViolation(IFrameworkPropertyKeys.CRUD__DUPLICATE_NATURAL_KEY, inViolation, uniqueness.getPrimaryBusinessKey(), this.getFieldValue(uniqueness.getPrimaryBusinessKey()));
        }
        return inViolation;
    }

    public boolean isUniqueInClass(MetafieldIdList inUniqueFields) {
        if (inUniqueFields == null || inUniqueFields.getSize() == 0) {
            return false;
        }
        IDomainQuery dq = QueryUtils.createDomainQuery(this.getEntityName());
        for (IMetafieldId uniqueField : inUniqueFields) {
            Object fieldValue = this.getFieldValue(uniqueField);
            dq.addDqPredicate(PredicateFactory.eq(uniqueField, fieldValue));
        }
        Serializable pkValue = this.getPrimaryKey();
        if (pkValue != null) {
            dq.addDqPredicate(PredicateFactory.not(PredicateFactory.pkEq(pkValue)));
        }
        return !HibernateApi.getInstance().existsByDomainQuery(dq);
    }

    public BizViolation checkRequiredField(BizViolation inBizViolationChain, IMetafieldId inFieldId) {
        Object v = this.getFieldValue(inFieldId);
        if (v == null || v instanceof String && ((String)v).isEmpty()) {
            return BizViolation.createFieldViolation(IFrameworkPropertyKeys.VALIDATION__REQUIRED_FIELD, inBizViolationChain, inFieldId);
        }
        return inBizViolationChain;
    }

    @Override
    @Nullable
    public BizViolation validateChanges(FieldChanges inChanges) {
        BizViolation violations = null;
        IMetafieldDictionary meta = Roastery.getMetafieldDictionary();
        IFieldValidator dv = meta.getValidator();
        Iterator<FieldChange> iterator = inChanges.getIterator();
        while (iterator.hasNext()) {
            FieldChange f = iterator.next();
            try {
                dv.validateField(f.getMetafieldId(), f.getNewValue());
            }
            catch (BizViolation bizViolation) {
                violations = bizViolation.appendToChain(violations);
            }
        }
        return violations;
    }

    protected BizViolation checkUniqueFieldViolation(BizViolation inPriorViolation, FieldChanges inChanges, IMetafieldId inMetafieldId) {
        BizViolation bizViolation = inPriorViolation;
        if (inChanges.hasFieldChange(inMetafieldId) && !this.isUniqueInClass(inMetafieldId)) {
            bizViolation = BizViolation.createFieldViolation(IFrameworkPropertyKeys.CRUD__DUPLICATE_NATURAL_KEY, inPriorViolation, inMetafieldId, inChanges.getFieldChange(inMetafieldId).getNewValue());
        }
        return bizViolation;
    }

    @Override
    @Nullable
    public BizViolation validateDeletion() {
        return null;
    }

    @Override
    public void populate(Set inDrivingFields, FieldChanges inOutMoreChanges) {
    }

    @Override
    public void preProcessInsert(FieldChanges inOutMoreChanges) {
    }

    @Override
    public void preProcessUpdate(FieldChanges inChanges, FieldChanges inOutMoreChanges) {
    }

    @Override
    public void preProcessInsertOrUpdate(FieldChanges inOutMoreChanges) {
    }

    @Override
    public void preProcessDelete(FieldChanges inOutMoreChanges) {
    }

//    @Override
//    public void preDelete() {
//    }

    public void purge() {
        HibernateApi.getInstance().delete(this);
    }

    @Override
    public void setSelfAndFieldChange(IMetafieldId inMetaFieldId, Object inFieldValue, FieldChanges inOutChanges) {
        this.setFieldValue(inMetaFieldId, inFieldValue);
        inOutChanges.setFieldChange(inMetaFieldId, inFieldValue);
    }

    @Deprecated
    public boolean isUniqueInClass(String inUniqueField) {
        return this.isUniqueInClass(MetafieldIdFactory.valueOf(inUniqueField));
    }

    @Override
    @Deprecated
    public String getPrimaryKeyFieldId() {
        return this.getClassMetadata().getIdentifierPropertyName();
    }

    @Override
    @Deprecated
    @Nullable
    public Long getFieldLong(String inFieldId) {
        return this.getFieldLong(MetafieldIdFactory.valueOf(inFieldId));
    }

    @Override
    @Deprecated
    @Nullable
    public String getFieldString(String inFieldId) {
        return this.getFieldString(MetafieldIdFactory.valueOf(inFieldId));
    }

    @Override
    @Deprecated
    @Nullable
    public Date getFieldDate(String inFieldId) {
        return this.getFieldDate(MetafieldIdFactory.valueOf(inFieldId));
    }

    @Override
    @Deprecated
    @Nullable
    public Object getFieldValue(String inFieldId) {
        return this.getFieldValue(MetafieldIdFactory.valueOf(inFieldId));
    }

    @Override
    @Deprecated
    public void setSelfAndFieldChange(String inFieldId, Object inFieldValue, FieldChanges inOutChanges) {
        this.setFieldValue(inFieldId, inFieldValue);
        inOutChanges.setFieldChange(MetafieldIdFactory.valueOf(inFieldId), inFieldValue);
    }

    @Override
    @Deprecated
    public void setFieldValue(String inFieldId, Object inFieldValue) {
        this.setFieldValue(MetafieldIdFactory.valueOf(inFieldId), inFieldValue);
    }

    @Deprecated
    public BizViolation checkRequiredField(BizViolation inBizViolationChain, String inFieldId) {
        return this.checkRequiredField(inBizViolationChain, MetafieldIdFactory.valueOf(inFieldId));
    }

    @Override
    @Deprecated
    public ValueObject getValueObject(String[] inFields) {
        MetafieldIdList mfidList;
        if (inFields == null) {
            mfidList = new MetafieldIdList();
        } else {
            mfidList = new MetafieldIdList(inFields.length);
            for (int i = 0; i < inFields.length; ++i) {
                String field = inFields[i];
                mfidList.add(MetafieldIdFactory.valueOf(field));
            }
        }
        return this.getValueObject(mfidList);
    }

    @Deprecated
    public static Class getPrimaryKeyClass(Class inClass) {
        String fullClassName = inClass.getName();
        String entityName = HiberCache.getEntityNameFromClassName(fullClassName);
        ClassMetadata meta = HiberCache.getClassMetadata(entityName);
        Type pkeyType = meta.getIdentifierType();
        return pkeyType.getReturnedClass();
    }

    public String toString() {
        return this.getEntityName() + ":" + this.getPrimaryKey();
    }

    private IDomainQuery getDomainQuery(IMetafieldId inUniqueField, Object inFieldValue) {
        IDomainQuery dq = QueryUtils.createDomainQuery(this.getEntityName()).addDqPredicate(PredicateFactory.eq(inUniqueField, inFieldValue));
        Serializable pkValue = this.getPrimaryKey();
        if (pkValue != null) {
            dq.addDqPredicate(PredicateFactory.not(PredicateFactory.pkEq(pkValue)));
        }
        return dq;
    }

    private void internalSetFieldValue(IMetafieldId inFieldId, Object inFieldValue) {
        if (inFieldId.getMfidExcludeRightMostNode() != null) {
            throw BizFailure.create("Invalid input, only simple metafield id's are allowed <" + inFieldId + '>');
        }
        String fieldName = inFieldId.getFieldId();
        Type expectedType = HiberCache.getFieldType(fieldName);
        if (expectedType == null) {
            this.internalSetFieldByReflection(inFieldId, inFieldValue);
            return;
        }
        if (HiberCache.isPrimaryKeyField(fieldName)) {
            DEV_LOGGER.error((Object)("applyFieldChanges: Caller passed primary key as a field change: fieldId = " + fieldName + " : CARINA IS IGNORING THIS."));
            return;
        }
        Class expectedClass = expectedType.getReturnedClass();
        if (inFieldValue == null) {
            this.internalSetProperty(fieldName, null);
        } else if (expectedClass.isAssignableFrom(inFieldValue.getClass())) {
            this.internalSetProperty(fieldName, inFieldValue);
        } else if (expectedClass == Long.class && inFieldValue instanceof Integer) {
            Long l = ((Integer)inFieldValue).longValue();
            this.internalSetProperty(fieldName, l);
//        } else if (expectedType.isEntityType()) {
//            Object foreignEntity = HibernateApi.getInstance().load(expectedClass, (Serializable)inFieldValue);
//            Object oldFieldValue = this.getFieldValue(inFieldId);
//            if (oldFieldValue != null) {
//                Hibernate.initialize((Object)oldFieldValue);
//            }
//            Hibernate.initialize((Object)foreignEntity);
//            this.internalSetProperty(fieldName, foreignEntity);
//        } else if (expectedType.isCollectionType()) {
//            if (inFieldValue instanceof String && StringUtils.isEmpty((String)((String)inFieldValue))) {
//                this.internalSetProperty(fieldName, null);
//            } else {
//                CollectionMetadata collmeta;
//                CollectionType pctype = (CollectionType)expectedType;
//                SessionFactory sf = PersistenceUtils.getSessionFactory();
//                try {
//                    collmeta = sf.getCollectionMetadata(pctype.getRole());
//                }
//                catch (HibernateException e) {
//                    throw new BizFailure(IFrameworkPropertyKeys.HIBERNATE__GENERIC, e, new Object[]{"getCollectionMetadata()", this, this.getClass()});
//                }
//                Type elementType = collmeta.getElementType();
//                Class collectionElementClass = elementType.getReturnedClass();
//                AbstractCollection collection = expectedClass == List.class ? new ArrayList() : new HashSet();
//                if (!(inFieldValue instanceof Object[])) {
//                    throw new BizFailure(IFrameworkPropertyKeys.HIBERNATE__ATTEMPT_TO_SET_MANY_TO_ONE, null, new Object[]{fieldName});
//                }
//                Object[] pks = (Object[])inFieldValue;
//                if (DatabaseEntity.class.isAssignableFrom(collectionElementClass)) {
//                    for (int i = 0; i < pks.length; ++i) {
//                        Object e = HibernateApi.getInstance().load(collectionElementClass, (Serializable)pks[i]);
//                        collection.add(e);
//                    }
//                } else {
//                    collection.addAll(Arrays.asList(pks));
//                }
//                this.internalSetProperty(fieldName, collection);
//            }
        } else {
            if (!(inFieldValue instanceof String)) {
                throw new BizFailure(IFrameworkPropertyKeys.HIBERNATE__INVALID_FIELD_VALUE, null, new Object[]{fieldName, inFieldValue});
            }
            if (expectedClass == Long.class) {
                DatabaseEntity.giveConversionWarning(expectedClass, fieldName);
                this.internalSetProperty(fieldName, new Long((String)inFieldValue));
            } else if (expectedClass == Double.class) {
                DatabaseEntity.giveConversionWarning(expectedClass, fieldName);
                this.internalSetProperty(fieldName, new Double((String)inFieldValue));
            } else if (expectedClass == Boolean.class) {
                DatabaseEntity.giveConversionWarning(expectedClass, fieldName);
                this.internalSetProperty(fieldName, Boolean.valueOf((String)inFieldValue));
            } else if (expectedClass == Date.class) {
                try {
                    DatabaseEntity.giveConversionWarning(expectedClass, fieldName);
                    Date date = DateUtil.xmlDateStringToDate((String)inFieldValue);
                    this.internalSetProperty(fieldName, date);
                }
                catch (ParseException e) {
                    throw new BizFailure(IFrameworkPropertyKeys.FRAMEWORK__CAN_NOT_PARSE_DATE, e, new Object[]{fieldName, inFieldValue});
                }
            } else if (AtomizedEnum.class.isAssignableFrom(expectedClass) && inFieldValue instanceof String) {
                DatabaseEntity.giveConversionWarning(expectedClass, fieldName);
                Enum atomizedEnum = AtomizedEnumUtils.safeGetEnum(expectedClass, (String)inFieldValue);
                this.internalSetProperty(fieldName, (Object)atomizedEnum);
            } else {
                throw new BizFailure(IFrameworkPropertyKeys.HIBERNATE__CAN_NOT_SET_FROM_STRING, null, new Object[]{fieldName, inFieldValue});
            }
        }
    }

    private void internalSetProperty(String inPropertyName, Object inFieldValue) {
        ClassMetadata classMetaData = this.getClassMetadata();
        try {
           // classMetaData.setPropertyValue((Object)this, inPropertyName, inFieldValue, EntityMode.POJO);
        }
        catch (HibernateException e) {
            throw new BizFailure(IFrameworkPropertyKeys.HIBERNATE__PUT_FAILURE, e, new Object[]{inFieldValue, inPropertyName, this.getClass()});
        }
    }

    private void internalSetFieldByReflection(IMetafieldId inMetafieldId, Object inFieldValue) {
        String methodName = inMetafieldId.getFieldId();
        methodName = methodName.substring(0, 1).toUpperCase() + methodName.substring(1);
        methodName = "set" + methodName;
        Class[] parameters = new Class[]{Object.class};
        try {
            Class<?> c = this.getClass();
            Method m = c.getMethod(methodName, parameters);
            Object[] arguments = new Object[]{inFieldValue};
            try {
                m.invoke(this, arguments);
            }
            catch (Exception e) {
                throw BizFailure.wrap(e);
            }
        }
        catch (NoSuchMethodException e) {
            throw new BizFailure(IFrameworkPropertyKeys.CRUD__INVALID_FIELD_ID, null, new Object[]{inMetafieldId, this.getClass()});
        }
    }

    private static void giveConversionWarning(Class inExpectedClass, String inFieldId) {
        DEV_LOGGER.info((Object)("DatabaseEntity was passed a String when " + inExpectedClass + " was expected for " + inFieldId));
        DEV_LOGGER.info((Object)"This automatic conversion will no longer be supported in Carina 0.4.");
    }

    private void internalSetComponentProperty(Object inComponent, String inPropertyName, Object inFieldValue, ComponentType inComponentType) {
        String[] names = inComponentType.getPropertyNames();
        Type[] types = inComponentType.getSubtypes();
        Object[] values = inComponentType.getPropertyValues(inComponent, EntityMode.POJO);
        Object convertedValue = inFieldValue;
        for (int j = 0; j < names.length; ++j) {
            if (!inPropertyName.equals(names[j])) continue;
            Type expectedType = types[j];
            Class expectedClass = types[j].getReturnedClass();
            if (inFieldValue == null || expectedClass.isAssignableFrom(inFieldValue.getClass())) {
                values[j] = inFieldValue;
                continue;
            }
            if (expectedType.isEntityType()) {
//                if (Map.class.isAssignableFrom(expectedClass)) {
//                    String entityName = HiberCache.getFullEntityNameFromClassName(expectedType);
//                    convertedValue = HibernateApi.getInstance().load(entityName, (Serializable)inFieldValue);
//                } else {
//                    convertedValue = HibernateApi.getInstance().load(expectedClass, (Serializable)inFieldValue);
//                }
                values[j] = convertedValue;
                continue;
            }
            throw BizFailure.create("can not assign component property <" + inPropertyName + "> with value <" + inFieldValue + ">.  Value is wrong class, expected class is <" + expectedClass + ">");
        }
        if (inComponent instanceof Map) {
            ((Map)inComponent).put(inPropertyName, convertedValue);
        } else {
            inComponentType.setPropertyValues(inComponent, values, EntityMode.POJO);
        }
    }

    @Override
    @Nullable
    public Map getCustomFlexFields() {
        return this._entityCustomDynamicFields;
    }

    @Override
    public void setCustomFlexFields(Map inEntityCustomDynamicFields) {
        this._entityCustomDynamicFields = inEntityCustomDynamicFields;
    }

    @Override
    public boolean isModelExtensible() {
        return !this.getClass().isAssignableFrom(IDataModelNotExtensible.class);
    }

}
