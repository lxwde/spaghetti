package com.zpmc.ztos.infra.base.common.callbacks;

import com.zpmc.ztos.infra.base.business.enums.framework.MessageLevelEnum;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.business.model.MetafieldIdList;
import com.zpmc.ztos.infra.base.business.predicate.PredicateFactory;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.common.database.PersistenceTemplate;
import com.zpmc.ztos.infra.base.common.model.*;
import com.zpmc.ztos.infra.base.utils.QueryUtils;
import org.apache.log4j.Logger;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BizPortalBizDelegate implements ICrudBizDelegate {
    private static final Logger LOGGER = Logger.getLogger(BizPortalBizDelegate.class);

    public BizResponse processBizRequest(BizRequest inRequest) {
//        BizPortalClient bizPortal = new BizPortalClient();
//        return bizPortal.processRequest(inRequest);
        return null;
    }

    @Override
    public BizResponse requestCrudOperation(UserContext inUserContext, CrudOperation inCrudOperation) {
        BizRequest request = new BizRequest(inUserContext);
        request.setUpdateTarget("crudFacade", "processCrud");
        request.addCrudOperation(inCrudOperation);
        BizResponse response = this.processBizRequest(request);
        return response;
    }

    @Override
    public BizResponse requestCreate(UserContext inUserContext, String inClass, Serializable inPrimaryKey, FieldChanges inFieldDefs) {
        CrudOperation crudOperation = inPrimaryKey == null ? new CrudOperation(null, 1, inClass, inFieldDefs, null) : new CrudOperation(null, 1, inClass, inFieldDefs, new Serializable[]{inPrimaryKey});
        return this.requestCrudOperation(inUserContext, crudOperation);
    }

    @Override
    public BizResponse requestCreate(ITranslationContext inRequestContext, String inClass, Serializable inPrimaryKey, FieldChanges inOutFieldChanges) {
        this.unaliasFieldChanges(inRequestContext, inOutFieldChanges);
        return this.requestCreate(inRequestContext.getUserContext(), inClass, inPrimaryKey, inOutFieldChanges);
    }

    @Override
    public BizResponse requestUpdate(UserContext inUserContext, String inClass, Serializable inPrimaryKey, FieldChanges inFieldChanges) {
        CrudOperation crudOperation = new CrudOperation(null, 2, inClass, inFieldChanges, new Serializable[]{inPrimaryKey});
        return this.requestCrudOperation(inUserContext, crudOperation);
    }

    private void unaliasFieldChanges(ITranslationContext inRequestContext, FieldChanges inOutFieldChanges) {
        IMetafieldDictionary mfd = inRequestContext.getIMetafieldDictionary();
        for (IMetafieldId metafieldId : inOutFieldChanges.getFieldIds()) {
            IMetafield metafield = mfd.findMetafield(metafieldId);
            if (metafield != null && metafield.getAliases() != null) {
                FieldChange fc = inOutFieldChanges.getFieldChange(metafieldId);
                inOutFieldChanges.removeFieldChange(metafieldId);
                FieldChange unaliasedFc = new FieldChange((IMetafieldId) metafield.getAliases(), fc.getPriorValue(), fc.getNewValue());
                inOutFieldChanges.setFieldChange(unaliasedFc);
                continue;
            }
            if (metafield != null) continue;
            inOutFieldChanges.removeFieldChange(metafieldId);
        }
    }

    @Override
    public BizResponse requestUpdate(ITranslationContext inRequestContext, String inClass, Serializable inPrimaryKey, FieldChanges inFieldChanges) {
        this.unaliasFieldChanges(inRequestContext, inFieldChanges);
        CrudOperation crudOperation = new CrudOperation(null, 2, inClass, inFieldChanges, new Serializable[]{inPrimaryKey});
        return this.requestCrudOperation(inRequestContext.getUserContext(), crudOperation);
    }

    @Override
    public BizResponse requestPopulate(UserContext inUserContext, String inClass, FieldChanges inFieldChanges) {
        CrudOperation crudOperation = new CrudOperation(null, 5, inClass, inFieldChanges, null);
        BizRequest request = new BizRequest(inUserContext);
        request.setQueryTarget("crudFacade", "processCrud");
        request.addCrudOperation(crudOperation);
        BizResponse response = this.processBizRequest(request);
        return response;
    }

    @Override
    public BizResponse requestDelete(UserContext inUserContext, String inClass, Serializable inPrimaryKey) {
        CrudOperation crudOperation = new CrudOperation(null, 3, inClass, null, new Serializable[]{inPrimaryKey});
        return this.requestCrudOperation(inUserContext, crudOperation);
    }

    @Override
    @Deprecated
    public BizResponse requestBulkDelete(String inClass, Object[] inDeleteKeys) {
        CrudOperation crudOperation = new CrudOperation(null, 3, inClass, null, inDeleteKeys);
        return this.requestCrudOperation(null, crudOperation);
    }

    @Override
    public BizResponse requestBulkDelete(UserContext inUserContext, String inEntityName, Object[] inDeleteKeys) {
        CrudOperation crudOperation = new CrudOperation(inUserContext, 3, inEntityName, null, inDeleteKeys);
        return this.requestCrudOperation(inUserContext, crudOperation);
    }

    @Override
    public BizResponse requestEntityQuery(UserContext inUserContext, String inEntityName, Serializable inPrimaryKey, String[] inDesiredFields) {
        IDomainQuery dq = QueryUtils.createDomainQuery(inEntityName).addDqPredicate(PredicateFactory.pkEq(inPrimaryKey)).addDqFields(inDesiredFields).setFullLeftOuterJoin(true);
        return this.doDomainQuery(inUserContext, dq);
    }

    @Override
    public BizResponse requestEntityQuery(UserContext inUserContext, String inEntityName, Serializable inPrimaryKey, MetafieldIdList inDesiredFields) {
        IDomainQuery dq = QueryUtils.createDomainQuery(inEntityName).addDqPredicate(PredicateFactory.pkEq(inPrimaryKey)).addDqFields(inDesiredFields).setFullLeftOuterJoin(true);
        if (ScopeCoordinates.GLOBAL_SCOPE.equals(inUserContext.getBroadestAllowedScope())) {
            dq.setScopingEnabled(false);
        }
        return this.doDomainQuery(inUserContext, dq);
    }

    @Override
    public List retrieveValueObjectsForClass(UserContext inUserContext, String inEntityName, String inDescriptionField) {
        IDomainQuery dq = QueryUtils.createDomainQuery(inEntityName).addDqField(inDescriptionField);
        BizResponse response = this.doDomainQuery(inUserContext, dq);
        return response.getValueObjects(inEntityName);
    }

    @Override
    @Nullable
    public ValueObject retrieveValueObjectForEntity(UserContext inUserContext, String inEntityName, Serializable inPrimaryKey, String[] inDesiredFields) {
        BizResponse response = this.requestEntityQuery(inUserContext, inEntityName, inPrimaryKey, inDesiredFields);
        return response.getValueObject(inEntityName);
    }

    @Override
    @Nullable
    public ValueObject retrieveValueObjectForEntity(ITranslationContext inRequestContext, String inEntityName, Serializable inPrimaryKey, MetafieldIdList inOutDesiredFields) {
        List aliasedFields = this.unAliasMetafieldList(inRequestContext, inOutDesiredFields);
        BizResponse response = this.requestEntityQuery(inRequestContext.getUserContext(), inEntityName, inPrimaryKey, inOutDesiredFields);
        ValueObject valueObject = response.getValueObject(inEntityName);
        if (valueObject == null) {
            return null;
        }
        for (Object metafield : aliasedFields) {
            IMetafieldId aliasId = (IMetafieldId) ((IMetafield)metafield).getAliases();
            Object value = valueObject.getFieldValue(aliasId);
            valueObject.unsetFieldValue(aliasId);
            valueObject.setFieldValue(((IMetafield)metafield).getMetafieldId(), value);
        }
        return valueObject;
    }

    private List unAliasMetafieldList(ITranslationContext inRequestContext, MetafieldIdList inOutMetafieldIdList) {
        ArrayList<IMetafield> aliasedFields = new ArrayList<IMetafield>();
        IMetafieldDictionary mfd = inRequestContext.getIMetafieldDictionary();
        int i = 0;
        Iterator<IMetafieldId> iterator = inOutMetafieldIdList.iterator();
        while (iterator.hasNext()) {
            IMetafieldId id;
            IMetafieldId aliasedId = id = iterator.next();
            IMetafield metafield = mfd.findMetafield(id);
            if (metafield != null && metafield.getAliases() != null) {
                aliasedId = (IMetafieldId) metafield.getAliases();
                aliasedFields.add(metafield);
                inOutMetafieldIdList.set(i, aliasedId);
            }
            ++i;
        }
        return aliasedFields;
    }

    @Override
    public List retrieveValueObjectsForClass(UserContext inUserContext, String inEntityName, String[] inDesiredFields) {
        IDomainQuery dq = QueryUtils.createDomainQuery(inEntityName).addDqFields(inDesiredFields);
        BizResponse response = this.doDomainQuery(inUserContext, dq);
        return response.getValueObjects(inEntityName);
    }

    @Override
    public BizResponse requestQueryAll(String inEntityName, String[] inDesiredFields, IBounded inBounds, Ordering[] inOrderings) {
        IDomainQuery dq = QueryUtils.createDomainQuery(inEntityName).addDqFields(inDesiredFields).addDqOrderings(inOrderings);
        if (inBounds != null) {
            dq.setDqFirstResult(inBounds.getFirstResult());
            dq.setDqMaxResults(inBounds.getMaxResults());
        }
        return this.doDomainQuery(null, dq);
    }

    private BizResponse doDomainQuery(UserContext inUserContext, IDataQuery inDataQuery) {
        BizRequest request = new BizRequest(inUserContext);
        request.setQueryTarget("crudFacade", "processQuery");
        request.setDataQuery(inDataQuery);
        return this.processBizRequest(request);
    }

    @Override
    public BizResponse processQuery(ITranslationContext inRequestContext, IExecutableQuery inQuery) {
        UserContext uc = inRequestContext.getUserContext();
        BizRequest request = new BizRequest(uc);
        request.setDataQuery(inQuery);
        if (inQuery instanceof FinderQuery) {
            FinderQuery fq = (FinderQuery)inQuery;
            request.setApiTarget(fq.getFinderApiName());
        } else {
            request.setQueryTarget("crudFacade", "processQuery");
        }
        return this.processBizRequest(request);
    }

    @Override
    public int processQueryCount(ITranslationContext inRequestContext, final IDomainQuery inQuery) {
        final Object[] resultHolder = new Object[1];
        PersistenceTemplate pt = new PersistenceTemplate(inRequestContext.getUserContext());
        IMessageCollector collector = pt.invoke(new CarinaPersistenceCallback(){

            @Override
            public void doInTransaction() {
                resultHolder[0] = HibernateApi.getInstance().findCountByDomainQuery(inQuery);
            }
        });
        if (collector.containsMessageLevel(MessageLevelEnum.SEVERE)) {
            String message = "Failed to count primary keys for domain query >" + inQuery + "< " + collector;
            LOGGER.error((Object)message);
            return -1;
        }
        return (Integer)resultHolder[0];
    }
}
