package com.zpmc.ztos.infra.base.business.model;

import com.zpmc.ztos.infra.base.business.dataobject.VisitDetailsDO;
import com.zpmc.ztos.infra.base.business.enums.argo.CarrierDirectionEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.CarrierVisitPhaseEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.DataSourceEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.LocTypeEnum;
import com.zpmc.ztos.infra.base.business.enums.framework.LifeCycleStateEnum;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.business.predicate.PredicateFactory;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.helps.ContextHelper;
import com.zpmc.ztos.infra.base.common.model.FieldChanges;
import com.zpmc.ztos.infra.base.common.model.Roastery;
import com.zpmc.ztos.infra.base.common.model.ValueObject;
import com.zpmc.ztos.infra.base.common.scopes.Complex;
import com.zpmc.ztos.infra.base.utils.QueryUtils;
import org.apache.commons.lang.ObjectUtils;
import org.hibernate.CallbackException;
import org.hibernate.Session;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public abstract class VisitDetails extends VisitDetailsDO implements ILogicalEntity {

    protected VisitDetails() {
        this.setCvdDataSource(DataSourceEnum.UNKNOWN);
        this.setCvdInCallNumber("1");
        this.setCvdOutCallNumber("1");
        this.setCvdLifeCycleState(LifeCycleStateEnum.ACTIVE);
    }

    public CarrierItinerary createDeviantItinerary() {
        CarrierItinerary sourceItinerary = this.getCvdItinerary();
        CarrierVisit cv = this.getCvdCv();
        CarrierItinerary deviantItinerary = CarrierItinerary.makeDeviantItinerary(sourceItinerary, cv);
        this.setCvdItinerary(deviantItinerary);
        return deviantItinerary;
    }

    public Boolean getVvdHasDeviantItinerary() {
        CarrierItinerary itinerary = this.getCvdItinerary();
        boolean isDeviant = itinerary != null && this.getCvdItinerary().isDeviant();
        return isDeviant;
    }

    public CarrierItinerary revertToProformaItinerary() {
        CarrierItinerary deviantItinerary = this.getCvdItinerary();
        CarrierItinerary proformaItinerary = this.getCvdService().getSrvcItinerary();
        this.setCvdItinerary(proformaItinerary);
        HibernateApi.getInstance().delete((Object)deviantItinerary);
        return proformaItinerary;
    }

    public boolean onDelete(Session inSession) throws CallbackException {
        CarrierVisit cv = this.getCvdCv();
        CarrierItinerary it = this.getCvdItinerary();
        if (it != null && ObjectUtils.equals((Object)cv, (Object)it.getItinOwnerCv())) {
            HibernateApi.getInstance().delete((Object)it);
        }
        if (cv != null) {
            cv.setCvCvd(null);
            if (ContextHelper.isUpdateFromHumanUser()) {
                HibernateApi.getInstance().delete((Object)cv);
            }
        }
        this.setCvdCv(null);
        IServicesManager sm = (IServicesManager) Roastery.getBean((String)"servicesManager");
        sm.purgeEventAndFlagHistory(this);
        return false;
    }

//    @Override
//    public List getSupportedBathToGuardians() {
//        return null;
//    }

    public boolean onSave(Session inSession) throws CallbackException {
        return false;
    }

    public boolean onUpdate(Session inSession) throws CallbackException {
        return false;
    }

    public void onLoad(Session inSession, Serializable inId) {
    }

    public boolean isPointInItinerary(RoutingPoint inPoint) {
        boolean inItinerary = false;
        if (this.getCvdItinerary() != null) {
            inItinerary = this.getCvdItinerary().isPointInItinerary(inPoint);
        }
        return inItinerary;
    }

    public boolean isPointAfterCall(RoutingPoint inPod) {
        boolean isAfter = false;
        if (this.getCvdItinerary() != null) {
            RoutingPoint pol = this.getCvdCv().getCvFacility().getFcyRoutingPoint();
            isAfter = this.getCvdItinerary().isPointAfterCall(inPod, pol, this.getCvdOutCallNumber());
        }
        return isAfter;
    }

    public boolean isPointBeforeCall(RoutingPoint inPol) {
        boolean isBefore = false;
        if (this.getCvdItinerary() != null) {
            RoutingPoint pod = this.getCvdCv().getCvFacility().getFcyRoutingPoint();
            isBefore = this.getCvdItinerary().isPointAfterCall(inPol, pod, this.getCvdOutCallNumber());
        }
        return isBefore;
    }

    public ValueObject getCvdItinVao() {
        MetafieldIdList fields = new MetafieldIdList();
        fields.add(IArgoRefField.ITIN_ID);
        fields.add(IArgoBizMetafield.ITIN_CALL_VAA);
        return this.getCvdItinerary().getValueObject(fields);
    }

    public static VisitDetails hydrate(Serializable inPrimaryKey) {
        return (VisitDetails)HibernateApi.getInstance().load(VisitDetails.class, inPrimaryKey);
    }

    public void verifyLoadAllowed(IEntity inUfv, String inSlot) throws BizViolation {
    }

    public void verifyDischargeAllowed(IEntity inUfv) throws BizViolation {
    }

    public boolean isValidSlot(String inSlot) {
        return true;
    }

    public void verifySlot(String inSlot) throws BizViolation {
    }

    public String userSlotString2InternalSlotString(String inUserSlotString) throws BizViolation {
        return inUserSlotString;
    }

    public BizViolation validateChanges(FieldChanges inChanges) {
        BizViolation bv = super.validateChanges(inChanges);
        if (inChanges.hasFieldChange(IArgoField.CVD_SERVICE)) {
            bv = this.validateCarrierService(bv);
        }
        return bv;
    }

    private BizViolation validateCarrierService(BizViolation inBv) {
        CarrierService srvc = this.getCvdService();
        LocTypeEnum carrierMode = this.getCvdCv().getCvCarrierMode();
        if (srvc != null && !carrierMode.equals((Object)srvc.getSrvcCarrierMode())) {
            inBv = BizViolation.create((IPropertyKey)IArgoPropertyKeys.SRVC_CANT_APPLY_SRVC_AS_LOCMODE_DIFFERENT, (BizViolation)inBv, (Object)((Object)srvc.getSrvcCarrierMode()), (Object)this.getCvdCv());
        }
        return inBv;
    }

    @Override
    public BizViolation verifyApplyFlagToEntityAllowed() {
        CarrierVisitPhaseEnum visitPhase = this.getCvdCv().getCvVisitPhase();
        if (CarrierVisitPhaseEnum.DEPARTED.equals((Object)visitPhase) || CarrierVisitPhaseEnum.CLOSED.equals((Object)visitPhase) || CarrierVisitPhaseEnum.ARCHIVED.equals((Object)visitPhase) || CarrierVisitPhaseEnum.CANCELED.equals((Object)visitPhase)) {
            return BizViolation.create((IPropertyKey) IArgoPropertyKeys.CARRIERVISIT_DOESNT_ALLOW_APPLY_FLAG_TYPE, null, (Object)this.getCvdCv().getCvId(), (Object)((Object)visitPhase));
        }
        return null;
    }

    @Override
    public Complex getLogEntityComplex() {
        return this.getCvdCv().getCvComplex();
    }

    public String getSecondNaturalKey() {
        return null;
    }

    public Object resolveXpsPosition(String inLocSlot, String inLocOrientation) {
        return null;
    }

    public CarrierVisit getInboundCv() {
        return this.getCvdCv();
    }

    public CarrierVisit getOutboundCv() {
        return this.getCvdCv();
    }

    public abstract String getCarrierCountryName();

    public abstract String getCarrierDocumentationNbr();

    public abstract ScopedBizUnit getCarrierOperator();

    public abstract String getCarrierVehicleId();

    public abstract String getCarrierVehicleName();

    public abstract String getCvdCarrierIdVehicleNameAndFacility();

    public Object getCarrierVesselClassType() {
        return null;
    }

    public Object getCarrierVesselClass() {
        return null;
    }

    public Object getCvdClassification() {
        return null;
    }

    public abstract String getCarrierIbVoyNbrOrTrainId();

    public abstract String getCarrierIbVisitCallNbr();

    public abstract String getCarrierObVoyNbrOrTrainId();

    @Nullable
    public abstract String getCarrierLineVoyNbrOrTrainId(ScopedBizUnit var1, CarrierDirectionEnum var2);

    public abstract String getCarrierObVisitCallNbr();

    public abstract Date getCarrierStartWorkTime();

    public abstract Date getCarrierEndWorkTime();

    public abstract String getCarrierVisitPosition();

    public void validateEmptyPickupAllowed(ScopedBizUnit inLine, Date inEffectiveDate) throws BizViolation {
    }

    public void validateExportReceiveAllowed(ScopedBizUnit inLine, Date inEffectiveDate) throws BizViolation {
    }

    public void validatePastCutOff(ScopedBizUnit inLine, boolean inIsHazardous, boolean inIsLiveReefer) throws BizViolation {
    }

    public void validatePastCutOff(ScopedBizUnit inLine, boolean inIsHazardous, boolean inIsLiveReefer, Date inApptDate) throws BizViolation {
    }

    @Nullable
    public CarrierVisit tryRollingLateCargo(ScopedBizUnit inLine, boolean inIsHazardous, boolean inIsLiveReefer, BizViolation inLateViolation) throws BizViolation {
        throw inLateViolation;
    }

    public void validateLineAllowed(ScopedBizUnit inLine) throws BizViolation {
    }

    public Boolean isCarrierVisitDrayingOff() {
        return Boolean.FALSE;
    }

    public LocPosition createInboundPosition(LocPosition inOutboundPosition) {
        return LocPosition.createLocPosition(this.getCvdCv(), inOutboundPosition.getPosSlot(), inOutboundPosition.getPosOrientation());
    }

    public abstract CarrierVisit findOrCreateNextVisit();

    public abstract String getCarrierClassId();

    @Nullable
    public abstract String getLloydsId();

    @Nullable
    public abstract String getRadioCallSign();

    @Nullable
    public abstract String getCarrierTypeId();

    public Boolean getCvdIsCommonCarrier() {
        return Boolean.FALSE;
    }

    public abstract Date getCarrierTimeLaborOnBoard();

    public boolean acceptsEdos() {
//        return LocTypeEnum.TRUCK.equals((Object)this.getCvdCv().getCvCarrierMode());
        return false;
    }

    public List<? extends VisitDetails> findNextVisitsForService() {
        return Collections.emptyList();
    }

    @Nullable
    public String getCarrierVehicleType(ILocation inLocation) {
        return null;
    }

    @Nullable
    public String getCarrierVehicleSeq(ILocation inLocation) {
        return null;
    }

    @Nullable
    public String getCarrierVehicleDestination(ILocation inLocation) {
        return null;
    }

    @Nullable
    public String getCarrierVehicleFlatCarType(ILocation inLocation) {
        return null;
    }

    @Nullable
    public Date getRailLastMoveTime() {
        return null;
    }

    @Nullable
    public String getCarrierDirection() {
        return null;
    }

    @Nullable
    public Date getCarrierVisitCreated() {
        return null;
    }

    @Nullable
    public String getCarrierVisitCreator() {
        return null;
    }

    @Nullable
    public IBinModel getCarrierBinModel() {
        return null;
    }

    public static boolean visitDetailsExistsForCarrierService(CarrierService inCarrierService) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"VisitDetails").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoField.CVD_SERVICE, (Object)inCarrierService.getSrvcGkey()));
        return Roastery.getHibernateApi().existsByDomainQuery(dq);
    }

    public abstract Class getCvdClass();

    public void postEventCreation(IEvent inEventCreated) {
    }
}
