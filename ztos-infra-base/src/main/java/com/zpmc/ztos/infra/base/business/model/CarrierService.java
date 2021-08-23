package com.zpmc.ztos.infra.base.business.model;

import com.zpmc.ztos.infra.base.business.dataobject.CarrierServiceDO;
import com.zpmc.ztos.infra.base.business.enums.argo.LocTypeEnum;
import com.zpmc.ztos.infra.base.business.enums.framework.LifeCycleStateEnum;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.business.predicate.PredicateFactory;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.common.events.AuditEvent;
import com.zpmc.ztos.infra.base.common.exceptions.BizFailure;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.model.FieldChange;
import com.zpmc.ztos.infra.base.common.model.FieldChanges;
import com.zpmc.ztos.infra.base.common.model.ValueObject;
import com.zpmc.ztos.infra.base.utils.QueryUtils;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.io.Serializable;

public class CarrierService extends CarrierServiceDO {


    public CarrierService() {
        this.setSrvcLifeCycleState(LifeCycleStateEnum.ACTIVE);
    }

    public void setLifeCycleState(LifeCycleStateEnum inLifeCycleState) {
        this.setSrvcLifeCycleState(inLifeCycleState);
    }

    public LifeCycleStateEnum getLifeCycleState() {
        return this.getSrvcLifeCycleState();
    }

    public IMetafieldId getScopeFieldId() {
        return IArgoRefField.SRVC_SCOPE;
    }

    public IMetafieldId getNaturalKeyField() {
        return IArgoRefField.SRVC_ID;
    }

    public static CarrierService hydrate(Serializable inPrimaryKey) {
        return (CarrierService) HibernateApi.getInstance().load(CarrierService.class, inPrimaryKey);
    }

    public static CarrierService findCarrierService(String inServiceId) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"CarrierService").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoRefField.SRVC_ID, (Object)inServiceId));
        return (CarrierService)HibernateApi.getInstance().getUniqueEntityByDomainQuery(dq);
    }

    public static CarrierService findCarrierServiceProxy(String inServiceId) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"CarrierService").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoRefField.SRVC_ID, (Object)inServiceId));
        Serializable[] srvcGkey = HibernateApi.getInstance().findPrimaryKeysByDomainQuery(dq);
        if (srvcGkey == null || srvcGkey.length == 0) {
            return null;
        }
        if (srvcGkey.length == 1) {
            return (CarrierService)HibernateApi.getInstance().load(CarrierService.class, srvcGkey[0]);
        }
        throw BizFailure.create((IPropertyKey) IFrameworkPropertyKeys.FRAMEWORK__NON_UNIQUE_RESULT, null, (Object)new Long(srvcGkey.length), (Object)dq);
    }

    public static CarrierService findOrCreateCarrierService(String inServiceId, String inServiceName, LocTypeEnum inMode, CarrierItinerary inItinerary) {
        CarrierService service = CarrierService.findCarrierService(inServiceId);
        if (service == null) {
            service = CarrierService.createCarrierService(inServiceId, inServiceName, inMode, inItinerary);
        }
        return service;
    }

    public static CarrierService resolveByPkey(Serializable inPkey) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"CarrierService").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoRefField.SRVC_PKEY, (Object)inPkey));
        return (CarrierService)HibernateApi.getInstance().getUniqueEntityByDomainQuery(dq);
    }

    public static CarrierService createCarrierService(String inServiceId, String inServiceName, LocTypeEnum inMode, CarrierItinerary inItinerary) {
        CarrierService service = new CarrierService();
        service.setSrvcId(inServiceId);
        service.setSrvcName(inServiceName);
        service.setSrvcItinerary(inItinerary);
        service.setSrvcCarrierMode(inMode);
        HibernateApi.getInstance().saveOrUpdate((Object)service);
        return service;
    }

    public ValueObject getSrvcItinVao() {
        MetafieldIdList fields = new MetafieldIdList();
        fields.add(IArgoRefField.ITIN_ID);
        fields.add(IArgoBizMetafield.ITIN_CALL_VAA);
        return this.getSrvcItinerary().getValueObject(fields);
    }

    public void setFieldValue(IMetafieldId inFieldId, Object inFieldValue) {
        if (ICarrierField.SRVC_ID.equals((Object)inFieldId)) {
            this.ensureItineraryExists(inFieldValue + "-STD");
            this.updateItineraryIfNeeded(inFieldValue + "-STD");
        }
        if (ICarrierField.SRVC_ITIN_VAO.equals((Object)inFieldId)) {
            this.ensureItineraryExists("ITN-STD");
            CarrierItinerary itinerary = this.getSrvcItinerary();
            itinerary.updateRoute(inFieldValue);
        } else {
            super.setFieldValue(inFieldId, inFieldValue);
        }
    }

    private void ensureItineraryExists(String inItinId) {
        CarrierItinerary itinerary = this.getSrvcItinerary();
        if (itinerary == null) {
            itinerary = CarrierItinerary.findCarrierItinerary(inItinId);
            if (itinerary == null) {
                itinerary = CarrierItinerary.createCarrierItinerary(inItinId, null);
            }
            this.setSrvcItinerary(itinerary);
        }
    }

    private void updateItineraryIfNeeded(String inItinId) {
        CarrierItinerary itinerary = this.getSrvcItinerary();
        if (itinerary != null && itinerary.getItinId() != null && !inItinId.equals(itinerary.getItinId())) {
  //          itinerary.setItinId(inItinId);
        }
    }

    public BizViolation validateChanges(FieldChanges inFieldChanges) {
        BizViolation bizViolation = super.validateChanges(inFieldChanges);
        if (!this.isUniqueInClass(ICarrierField.SRVC_ID)) {
            FieldChange fc = inFieldChanges.getFieldChange(ICarrierField.SRVC_ID);
            Object id = fc == null ? null : fc.getNewValue();
            bizViolation = BizViolation.create((IPropertyKey) IArgoPropertyKeys.CARRIER_SERVICE_WITH_SAME_ID_ALREADY_EXISTS, (BizViolation)bizViolation, (Object)id);
        }
        return bizViolation;
    }

    @Nullable
    public BizViolation validateDeletion() {
        BizViolation bv = super.validateDeletion();
        if (VisitDetails.visitDetailsExistsForCarrierService(this)) {
            bv = BizViolation.create((IPropertyKey) IArgoPropertyKeys.VISIT_EXISTS_FOR_CARRIER_SERVICE, (BizViolation)bv);
        }
        return bv;
    }

    public static CarrierService find(Object inPrimaryKey) {
        return (CarrierService)HibernateApi.getInstance().get(CarrierService.class, (Serializable)inPrimaryKey);
    }

    public AuditEvent vetAuditEvent(AuditEvent inAuditEvent) {
        return inAuditEvent;
    }

}
