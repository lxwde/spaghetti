package com.zpmc.ztos.infra.base.common.callbacks;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.business.model.SavedPredicate;
import com.zpmc.ztos.infra.base.common.model.*;
import com.zpmc.ztos.infra.base.utils.QueryUtils;
import org.apache.log4j.Logger;
import org.dom4j.Element;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class CrudDelegate {
    private static final Logger LOGGER = Logger.getLogger(CrudDelegate.class);
    private static final BizPortalBizDelegate BIZ_DELEGATE = new BizPortalBizDelegate();

    public static BizResponse processBizRequest(BizRequest inRequest) {
        return BIZ_DELEGATE.processBizRequest(inRequest);
    }

    public static BizResponse executeBizRequest(BizRequest inBizRequest, String inApiName) {
        inBizRequest.setApiTarget(inApiName);
        return CrudDelegate.processBizRequest(inBizRequest);
    }

    public static BizResponse requestCreate(UserContext inUserContext, String inEntityName, Serializable inPrimaryKey, FieldChanges inFieldDefs) {
        return BIZ_DELEGATE.requestCreate(inUserContext, inEntityName, inPrimaryKey, inFieldDefs);
    }

    public static BizResponse requestUpdate(UserContext inUserContext, String inEntityName, Serializable inPrimaryKey, FieldChanges inFieldChanges) {
        return BIZ_DELEGATE.requestUpdate(inUserContext, inEntityName, inPrimaryKey, inFieldChanges);
    }

    public static BizResponse requestPopulate(UserContext inUserContext, String inEntityName, FieldChanges inFieldChanges) {
        return BIZ_DELEGATE.requestPopulate(inUserContext, inEntityName, inFieldChanges);
    }

    public static BizResponse requestDelete(UserContext inUserContext, String inEntityName, Serializable inPrimaryKey) {
        return BIZ_DELEGATE.requestDelete(inUserContext, inEntityName, inPrimaryKey);
    }

    @Deprecated
    public static BizResponse requestBulkDelete(String inEntityName, Object[] inDeleteKeys) {
        return BIZ_DELEGATE.requestBulkDelete(inEntityName, inDeleteKeys);
    }

    public static BizResponse requestCrudOperation(UserContext inUserContext, CrudOperation inCrudOperation) {
        return BIZ_DELEGATE.requestCrudOperation(inUserContext, inCrudOperation);
    }

    public static List retrieveValueObjectsForClass(UserContext inUserContext, String inEntityName, String inDescriptionField) {
        return BIZ_DELEGATE.retrieveValueObjectsForClass(inUserContext, inEntityName, inDescriptionField);
    }

    @Deprecated
    public static List retrieveValueObjectsForClass(String inEntityName, String inDescriptionField) {
        return CrudDelegate.retrieveValueObjectsForClass(null, inEntityName, inDescriptionField);
    }

    public static BizResponse requestEntityQuery(UserContext inUserContext, String inEntityName, Serializable inPrimaryKey, String[] inDesiredFields) {
        return BIZ_DELEGATE.requestEntityQuery(inUserContext, inEntityName, inPrimaryKey, inDesiredFields);
    }

    public static BizResponse doDomainQuery(UserContext inUserContext, IDataQuery inDataQuery) {
        BizRequest request = new BizRequest(inUserContext);
        request.setQueryTarget("crudFacade", "processQuery");
        request.setDataQuery(inDataQuery);
        return BIZ_DELEGATE.processBizRequest(request);
    }

    public static List<IValueHolder> doDomainQueryInMultipleThreads(@NotNull UserContext inUserContext, @NotNull IMessageCollector inOutMc, @NotNull String inThreadGroupName, @NotNull IMetafieldId inPkMetafieldId, @NotNull IDomainQuery inDomainQuery, @Nullable Integer inMaxQueryDurationInSeconds, @Nullable Integer inThreadNumber, @Nullable Integer inBatchSize) {
//        MultiThreadBatchedDomainQueryExecutor executor = new MultiThreadBatchedDomainQueryExecutor(inOutMc, inMaxQueryDurationInSeconds, inThreadNumber, inBatchSize);
//        if (inOutMc.hasError()) {
//            return Collections.emptyList();
//        }
//        return executor.executeDomainQuery(inUserContext, inOutMc, inThreadGroupName, inPkMetafieldId, inDomainQuery);
        return null;
    }

    public static List<IValueHolder> doDomainQueryInMultipleThreads(@NotNull UserContext inUserContext, @NotNull IMessageCollector inOutMc, @NotNull String inThreadGroupName, @NotNull IMetafieldId inPkMetafieldId, @NotNull IDomainQuery inDomainQuery) {
        return CrudDelegate.doDomainQueryInMultipleThreads(inUserContext, inOutMc, inThreadGroupName, inPkMetafieldId, inDomainQuery, null, null, null);
    }

    public static BizResponse doFinderQuery(UserContext inUserContext, FinderQuery inFinderQuery) {
        BizRequest request = new BizRequest(inUserContext);
        request.setDataQuery(inFinderQuery);
        request.setApiTarget(inFinderQuery.getFinderApiName());
        return BIZ_DELEGATE.processBizRequest(request);
    }

    @Nullable
    public static ValueObject retrieveValueObjectForEntity(UserContext inUserContext, String inEntityName, Serializable inPrimaryKey, String[] inDesiredFields) {
        BizResponse response = CrudDelegate.requestEntityQuery(inUserContext, inEntityName, inPrimaryKey, inDesiredFields);
        return response.getValueObject(inEntityName);
    }

    @Deprecated
    @Nullable
    public static ValueObject retrieveValueObjectForEntity(String inEntityName, Serializable inPrimaryKey, String[] inDesiredFields) {
        return CrudDelegate.retrieveValueObjectForEntity(null, inEntityName, inPrimaryKey, inDesiredFields);
    }

    public static List retrieveValueObjectsForClass(UserContext inUserContext, String inEntityName, String[] inDesiredFields) {
        IDomainQuery dq = QueryUtils.createDomainQuery(inEntityName).addDqFields(inDesiredFields);
        BizResponse response = CrudDelegate.doDomainQuery(inUserContext, dq);
        return response.getValueObjects(inEntityName);
    }

    public static Element retrieveXmlDataTable(String inEntityName, String[] inDesiredFields) {
        IDomainQuery dq = QueryUtils.createDomainQuery(inEntityName).addDqFields(inDesiredFields);
        BizResponse response = CrudDelegate.doDomainQuery(null, dq);
        return response.getClassElement(inEntityName);
    }

    public static BizResponse requestQueryAll(String inEntityName, String[] inDesiredFields, IBounded inBounds, Ordering[] inOrderings) {
        return BIZ_DELEGATE.requestQueryAll(inEntityName, inDesiredFields, inBounds, inOrderings);
    }

    public static BizResponse requestMasterAndDetail(String inMasterClass, Serializable inMasterPrimaryKey, String inRelationshipField, String[] inMasterFields, String[] inDetailFields) {
        LOGGER.error((Object)("requestMasterAndDetail: NO LONGER SUPPORTED, NEEDS REWRITE OR ELIMINATION. " + inMasterClass));
        return new BizResponse();
    }

    @Nullable
    public static BizResponse updateConfigSetting(UserContext inUserContext, String inConfigId, Number inScopeLevel, Serializable inPrimaryKey, Serializable inValue) {
        if (inScopeLevel == null) {
            LOGGER.error((Object)("updateConfigSetting: failed to update " + inConfigId + ", no scope specified"));
            return null;
        }
        FieldChanges fcs = new FieldChanges();
        fcs.setFieldChange(IConfigSettingField.CNFIG_VALUE, inValue);
        fcs.setFieldChange(IConfigSettingField.CNFIG_SCOPE, inScopeLevel);
        fcs.setFieldChange(IConfigSettingField.CNFIG_SCOPE_GKEY, inPrimaryKey);
        CrudOperation co = new CrudOperation();
        co.setPrimaryKeys(new String[]{inConfigId});
        co.setTask(2);
        co.setFieldChanges(fcs);
        BizRequest br = new BizRequest(inUserContext);
        br.setUpdateTarget("configProvider", "updateOneConfigSetting");
        br.addCrudOperation(co);
        BizResponse response = CrudDelegate.processBizRequest(br);
        return response;
    }

    public static ICrudBizDelegate getBizDelegate() {
        return BIZ_DELEGATE;
    }

    @Nullable
    public static IPredicate createExecutablePredicate(UserContext inUserContext, IValueHolder inPredicateValueHolder, Map inPredicateParms) {
        SavedPredicate sp = new SavedPredicate(inPredicateValueHolder);
        IPredicate predicate = sp.getExecutablePredicate(inUserContext, inPredicateParms);
        return predicate;
    }

}
