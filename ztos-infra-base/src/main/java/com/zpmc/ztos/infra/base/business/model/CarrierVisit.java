package com.zpmc.ztos.infra.base.business.model;

import com.zpmc.ztos.infra.base.business.dataobject.CarrierVisitDO;
import com.zpmc.ztos.infra.base.business.enums.argo.*;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.business.predicate.PredicateFactory;
import com.zpmc.ztos.infra.base.business.xps.XpsDbManagerId;
import com.zpmc.ztos.infra.base.common.configs.ArgoConfig;
import com.zpmc.ztos.infra.base.common.database.HiberCache;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.common.events.AuditEvent;
import com.zpmc.ztos.infra.base.common.exceptions.BizFailure;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.helps.ContextHelper;
import com.zpmc.ztos.infra.base.common.model.*;
import com.zpmc.ztos.infra.base.common.scopes.Complex;
import com.zpmc.ztos.infra.base.common.scopes.Facility;
import com.zpmc.ztos.infra.base.common.type.AggregateFunctionType;
import com.zpmc.ztos.infra.base.common.utils.ArgoUtils;
import com.zpmc.ztos.infra.base.utils.QueryUtils;
import com.zpmc.ztos.infra.base.utils.StringUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class CarrierVisit extends CarrierVisitDO implements IAuditable,
        ILocation,
        INaturallyKeyedEntity {

    private static final String GEN_TRUCK = "GEN_TRUCK";
    private static final String GEN_VESSEL = "GEN_VESSEL";
    private static final String GEN_TRAIN = "GEN_TRAIN";
    private static final String GEN_RAILCAR = "GEN_RAILCAR";
    private static final String GEN_UNKNOWN = "GEN_CARRIER";
    private static final Logger LOGGER = Logger.getLogger((String) CarrierVisit.class.getName());

    public CarrierVisit() {
        this.setCvVisitPhase(CarrierVisitPhaseEnum.CREATED);
    }

    public static CarrierVisit findOrCreateVesselVisit(Facility inFacility, String inVesselVisitId) {
        Complex complex = inFacility.getFcyComplex();
        if (GEN_VESSEL.equals(inVesselVisitId)) {
            return CarrierVisit.getGenericVesselVisit(complex);
        }
        CarrierVisit cv = CarrierVisit.findCv(complex, inFacility, LocTypeEnum.VESSEL, inVesselVisitId, null);
        if (cv == null) {
            cv = CarrierVisit.createCv(complex, inFacility, LocTypeEnum.VESSEL, inVesselVisitId, 0L);
        }
        return cv;
    }

    public static CarrierVisit findVesselVisit(Facility inFacility, String inVesselVisitId) {
        return CarrierVisit.findCv(inFacility.getFcyComplex(), inFacility, LocTypeEnum.VESSEL, inVesselVisitId, null);
    }

    public static CarrierVisit findTrainVisit(Complex inComplex, Facility inFacility, String inTrainId) {
        return CarrierVisit.findCv(inComplex, inFacility, LocTypeEnum.TRAIN, inTrainId, null);
    }

    public static CarrierVisit findCarrierVisit(Facility inFacility, LocTypeEnum inMode, String inCvId) throws BizViolation {
        if (inCvId == null) {
            throw BizViolation.create((IPropertyKey) IArgoPropertyKeys.INFO, null, (Object)"null carrier visit ID");
        }
        if (LocTypeEnum.VESSEL.equals((Object)inMode)) {
            return CarrierVisit.findVesselVisit(inFacility, inCvId);
        }
        if (LocTypeEnum.TRUCK.equals((Object)inMode)) {
            return CarrierVisit.findOrCreateActiveTruckVisit(inFacility, inCvId);
        }
        if (LocTypeEnum.TRAIN.equals((Object)inMode)) {
            return CarrierVisit.findOrCreateTrainVisit(inFacility, inCvId);
        }
        throw BizViolation.create((IPropertyKey) IArgoPropertyKeys.INFO, null, (Object)("carrier mode not supported: " + (Object)((Object)inMode)));
    }

    public INaturallyKeyedEntity findByNaturalKey(String inNaturalKey) {
        String[] s = inNaturalKey.split("/");
        LocTypeEnum mode = LocTypeEnum.getEnum(s[0]);
        String visitId = s[1];
        Facility facility = null;
        if (s.length > 2) {
            facility = Facility.findFacility(s[2]);
        }
        if (CarrierVisit.isGenericId(visitId)) {
            return CarrierVisit.getGenericCarrierVisitForMode(ContextHelper.getThreadComplex(), mode);
        }
        try {
            return CarrierVisit.findCarrierVisit(facility, mode, visitId);
        }
        catch (BizViolation inBizViolation) {
            LOGGER.error((Object)("findByNaturalKey: could not resolve: " + inNaturalKey));
            return null;
        }
    }

    public String getNaturalKey() {
        String fcyId = this.getCvFacility() == null ? null : this.getCvFacility().getFcyId();
        StringBuilder s = new StringBuilder().append(this.getCvCarrierMode().getKey()).append("/").append(this.getCvId());
        if (fcyId != null) {
            s.append("/").append(fcyId);
        }
        return s.toString();
    }

    public void skipToPhase(CarrierVisitPhaseEnum inDesiredPhase) {
        if (this.getCvVisitPhase().equals((Object)inDesiredPhase)) {
            return;
        }
        if (CarrierVisitPhaseEnum.CLOSED.equals((Object)inDesiredPhase)) {
            this.skipToPhase(CarrierVisitPhaseEnum.DEPARTED);
        }
        if (CarrierVisitPhaseEnum.DEPARTED.equals((Object)inDesiredPhase)) {
            this.skipToPhase(CarrierVisitPhaseEnum.COMPLETE);
        }
        if (CarrierVisitPhaseEnum.COMPLETE.equals((Object)inDesiredPhase)) {
            this.skipToPhase(CarrierVisitPhaseEnum.WORKING);
        }
        if (CarrierVisitPhaseEnum.WORKING.equals((Object)inDesiredPhase)) {
            this.skipToPhase(CarrierVisitPhaseEnum.ARRIVED);
        }
        if (CarrierVisitPhaseEnum.ARRIVED.equals((Object)inDesiredPhase)) {
            this.skipToPhase(CarrierVisitPhaseEnum.INBOUND);
        }
        this.safelyUpdateVisitPhase(inDesiredPhase);
    }

    public static CarrierVisit findOrCreateTrainVisit(Facility inFacility, String inTrainVisitId) {
        Complex complex = inFacility.getFcyComplex();
        CarrierVisit cv = CarrierVisit.findCv(complex, inFacility, LocTypeEnum.TRAIN, inTrainVisitId, null);
        if (cv == null) {
            cv = CarrierVisit.createCv(complex, inFacility, LocTypeEnum.TRAIN, inTrainVisitId, 0L);
        }
        return cv;
    }

    public static CarrierVisit findOrCreateTrainVisit(Complex inComplex, String inTrainVisitId) {
        CarrierVisit cv = CarrierVisit.findCv(inComplex, null, LocTypeEnum.TRAIN, inTrainVisitId, null);
        if (cv == null) {
            cv = CarrierVisit.createCv(inComplex, null, LocTypeEnum.TRAIN, inTrainVisitId, 0L);
        }
        return cv;
    }

    public static CarrierVisit findOrCreateActiveTruckVisit(Facility inFacility, String inLicenseOrBat) {
        CarrierVisit cv = CarrierVisit.findNewestNumberedCarrierVisit(inFacility, LocTypeEnum.TRUCK, inLicenseOrBat);
        if (cv == null || cv.hasDepartedFacility() || CarrierVisitPhaseEnum.CANCELED.equals((Object)cv.getCvVisitPhase())) {
            cv = CarrierVisit.createNumberedCarrierVisit(inFacility, LocTypeEnum.TRUCK, inLicenseOrBat);
        }
        return cv;
    }

    public static CarrierVisit findOrCreateTruckVisitForEdi(Facility inFacility, String inCarrierId) {
        CarrierVisit cv = null;
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"CarrierVisit").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoField.CV_CARRIER_MODE, (Object)((Object) LocTypeEnum.TRUCK))).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoField.CV_ID, (Object)inCarrierId)).addDqOrdering(Ordering.desc((IMetafieldId) IArgoField.CV_VISIT_NBR));
        if (inFacility == null) {
            dq.addDqPredicate(PredicateFactory.isNull((IMetafieldId) IArgoField.CV_FACILITY));
        } else {
            dq.addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoField.CV_FACILITY, (Object)inFacility.getFcyGkey()));
            dq.addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoField.CV_COMPLEX, (Object)inFacility.getFcyComplex().getCpxGkey()));
        }
        Serializable[] primaryKeys = Roastery.getHibernateApi().findPrimaryKeysByDomainQuery(dq);
        if (primaryKeys != null && primaryKeys.length >= 1 && ((cv = CarrierVisit.hydrate(primaryKeys[0])).hasDepartedFacility() || CarrierVisitPhaseEnum.CANCELED.equals((Object)cv.getCvVisitPhase()))) {
            cv = CarrierVisit.createCv(inFacility.getFcyComplex(), inFacility, LocTypeEnum.TRUCK, inCarrierId, cv.getCvVisitNbr() + 1L);
        }
        if (cv == null) {
            cv = CarrierVisit.createCv(inFacility.getFcyComplex(), inFacility, LocTypeEnum.TRUCK, inCarrierId, 1L);
        }
        return cv;
    }

    public static CarrierVisit createActiveTruckVisit(Facility inFacility, String inLicenseOrBat) {
        return CarrierVisit.createNumberedCarrierVisit(inFacility, LocTypeEnum.TRUCK, inLicenseOrBat);
    }

    public static CarrierVisit getGenericVesselVisit(Complex inComplex) {
        return CarrierVisit.findOrCreateGenericCv(inComplex, LocTypeEnum.VESSEL);
    }

    public static CarrierVisit getGenericTruckVisit(Complex inComplex) {
        return CarrierVisit.findOrCreateGenericCv(inComplex, LocTypeEnum.TRUCK);
    }

    public static CarrierVisit getGenericTrainVisit(Complex inComplex) {
        return CarrierVisit.findOrCreateGenericCv(inComplex, LocTypeEnum.TRAIN);
    }

    public static CarrierVisit getGenericRailcarVisit(Complex inComplex) {
        return CarrierVisit.findOrCreateGenericCv(inComplex, LocTypeEnum.TRAIN);
    }

    public static CarrierVisit getGenericCarrierVisit(Complex inComplex) {
        return CarrierVisit.findOrCreateGenericCv(inComplex, LocTypeEnum.UNKNOWN);
    }

    public static CarrierVisit loadByGkey(Long inGkey) {
        return (CarrierVisit)Roastery.getHibernateApi().load(CarrierVisit.class, (Serializable)inGkey);
    }

    public static CarrierVisit getGenericCarrierVisitForMode(Complex inComplex, LocTypeEnum inMode) {
        if (LocTypeEnum.TRUCK.equals((Object)inMode)) {
            return CarrierVisit.getGenericTruckVisit(inComplex);
        }
        if (LocTypeEnum.TRAIN.equals((Object)inMode)) {
            return CarrierVisit.getGenericTrainVisit(inComplex);
        }
        if (LocTypeEnum.VESSEL.equals((Object)inMode)) {
            return CarrierVisit.getGenericVesselVisit(inComplex);
        }
        return CarrierVisit.getGenericCarrierVisit(inComplex);
    }

    public static LocTypeEnum getLocTypeEnumByCarrierMode(CarrierModeEnum inCarrierModeEnum) {
        LocTypeEnum locType = null;
        if (CarrierModeEnum.TRUCK.equals((Object)inCarrierModeEnum)) {
            locType = LocTypeEnum.TRUCK;
        } else if (CarrierModeEnum.TRAIN.equals((Object)inCarrierModeEnum)) {
            locType = LocTypeEnum.TRAIN;
        } else if (CarrierModeEnum.VESSEL.equals((Object)inCarrierModeEnum)) {
            locType = LocTypeEnum.VESSEL;
        } else if (CarrierModeEnum.UNKNOWN.equals((Object)inCarrierModeEnum)) {
            locType = LocTypeEnum.UNKNOWN;
        }
        return locType;
    }

    protected static CarrierVisit createNumberedCarrierVisit(Facility inFacility, LocTypeEnum inMode, String inCarrierId) {
        Long maxVisitNbr = CarrierVisit.findMaxVisitNumber(inFacility, inMode, inCarrierId);
        long newVisitNbr = maxVisitNbr + 1L;
        return CarrierVisit.createCv(inFacility.getFcyComplex(), inFacility, inMode, inCarrierId, newVisitNbr);
    }

    protected static CarrierVisit findNewestNumberedCarrierVisit(Facility inFacility, LocTypeEnum inMode, String inCarrierId) {
        CarrierVisit cv = null;
        Long maxVisitNbr = CarrierVisit.findMaxVisitNumber(inFacility, inMode, inCarrierId);
        if (maxVisitNbr != 0L) {
            cv = CarrierVisit.findCv(inFacility.getFcyComplex(), inFacility, inMode, inCarrierId, maxVisitNbr);
        }
        return cv;
    }

    public BizViolation validateChanges(FieldChanges inChanges) {
        BizViolation bv = super.validateChanges(inChanges);
        Facility vvdFacility = this.getCvFacility();
        DataSourceEnum src = ContextHelper.getThreadDataSource();
        if (!DataSourceEnum.PURGE_JOB.equals((Object)src)) {
            IArgoRailManager rm;
            CarrierVisit cv;
            if (vvdFacility != null && this.getPrimaryKey() != null && ContextHelper.isUpdateFromHumanUser()) {
                Facility currentFcy = ContextHelper.getThreadFacility();
                if (LocTypeEnum.VESSEL.equals((Object)this.getCvCarrierMode()) || LocTypeEnum.TRAIN.equals((Object)this.getCvCarrierMode())) {
                    if (inChanges.hasFieldChange(IArgoField.CV_FACILITY)) {
                        Facility prevFcyValue = (Facility)inChanges.getFieldChange(IArgoField.CV_FACILITY).getPriorValue();
                        if (prevFcyValue != null) {
                            try {
                                this.isUpdateAllowedForCurrectFcy(currentFcy, prevFcyValue, prevFcyValue.isFcyOperational());
                            }
                            catch (BizViolation inBizViolation) {
                                bv = inBizViolation.appendToChain(bv);
                            }
                        }
                    } else {
                        try {
                            this.isUpdateAllowedForCurrectFcy(currentFcy, vvdFacility, vvdFacility.isFcyOperational());
                        }
                        catch (BizViolation inBizViolation) {
                            bv = inBizViolation.appendToChain(bv);
                        }
                    }
                }
            }
            if ((inChanges.hasFieldChange(IArgoField.CV_ID) || inChanges.hasFieldChange(IArgoField.CV_FACILITY)) && !DataSourceEnum.SNX.equals((Object)ContextHelper.getThreadDataSource()) && (cv = CarrierVisit.findCv(this.getCvComplex(), this.getCvFacility(), this.getCvCarrierMode(), this.getCvId(), this.getCvVisitNbr())) != null && !ObjectUtils.equals((Object)this.getPrimaryKey(), (Object)cv.getPrimaryKey())) {
                bv = BizViolation.create((IPropertyKey) IArgoPropertyKeys.CARRIER_VISIT_WITH_FACILITY_ALREADY_EXIST, (BizViolation)bv, (Object)this.getCvId(), (Object)this.getCvFacility().getFcyId(), (Object)((Object)this.getCvCarrierMode()));
            }
            BizViolation fcyValidationResult = null;
            if (inChanges.hasFieldChange(IArgoField.CV_FACILITY) && LocTypeEnum.TRAIN.equals((Object)this.getCvCarrierMode()) && !ArgoPrivs.RAIL_TRAIN_REROUTE.isAllowed(ContextHelper.getThreadUserContext()) && (fcyValidationResult = (rm = (IArgoRailManager)Roastery.getBean((String)"argoRailManager")).validateFacilityChange(this)) != null) {
                bv = fcyValidationResult.appendToChain(bv);
            }
            if (inChanges.hasFieldChange(IArgoField.CV_CUSTOMS_ID) && this.getCvCustomsId() != null) {
                IDomainQuery dq = QueryUtils.createDomainQuery((String)"CarrierVisit").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoField.CV_COMPLEX, (Object)this.getCvComplex().getCpxGkey())).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoField.CV_CARRIER_MODE, (Object)((Object)this.getCvCarrierMode()))).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoField.CV_CUSTOMS_ID, (Object)this.getCvCustomsId())).addDqPredicate(PredicateFactory.ne((IMetafieldId) IArgoField.CV_GKEY, (Object)this.getCvGkey()));
                if (this.getCvFacility() == null) {
                    dq.addDqPredicate(PredicateFactory.isNull((IMetafieldId) IArgoField.CV_FACILITY));
                } else {
                    dq.addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoField.CV_FACILITY, (Object)this.getCvFacility().getFcyGkey()));
                }
                if (this.getCvVisitNbr() != null && this.getCvVisitNbr() != 0L) {
                    dq.addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoField.CV_VISIT_NBR, (Object)this.getCvVisitNbr()));
                }
                if (Roastery.getHibernateApi().existsByDomainQuery(dq)) {
                    bv = BizViolation.create((IPropertyKey) IArgoPropertyKeys.NON_UNIQUE_CARRIER_CUSTOMS_ID, (BizViolation)bv, (Object)this.getCvCustomsId(), (Object)((Object)this.getCvCarrierMode()));
                }
            }
            if (ContextHelper.isUpdateFromHumanUser() && (inChanges.hasFieldChange(IArgoField.CV_A_T_A) || inChanges.hasFieldChange(IArgoField.CV_A_T_D)) && this.getCvATA() != null && this.getCvATD() != null && (this.getCvATA().after(this.getCvATD()) || this.getCvATA().compareTo(this.getCvATD()) == 0)) {
                bv = BizViolation.create((IPropertyKey) IArgoPropertyKeys.CARRIER_VISIT_ATA_AFTER_OR_EQUALS_ATD, (BizViolation)bv, (Object) IArgoField.CV_A_T_A, (Object) IArgoField.CV_A_T_D);
            }
            if (GEN_TRUCK.equals(this.getCvId()) && this.getCvFacility() != null) {
                throw BizFailure.create((String)"attempt to create GEN_TRUCK with non-null facility!");
            }
            if (GEN_VESSEL.equals(this.getCvId()) && this.getCvFacility() != null) {
                throw BizFailure.create((String)"attempt to create GEN_VESSEL with non-null facility!");
            }
        }
        return bv;
    }

    public void preProcessDelete(FieldChanges inOutMoreChanges) {
        if (this.isGenericCv()) {
            throw BizFailure.create((String)"attempt to delete GEN Carrier!");
        }
    }

    private void isUpdateAllowedForCurrectFcy(Facility inCurrentFacility, Facility inCvFaclity, boolean inIsFcyOperational) throws BizViolation {
        if (ContextHelper.isUpdateFromHumanUser() && inCurrentFacility != null && !inCvFaclity.equals(inCurrentFacility) && inIsFcyOperational && !ArgoPrivs.ALLOW_VESSEL_TRAIN_MULTIFACILITY_EDIT.isAllowed(ContextHelper.getThreadUserContext())) {
            throw BizViolation.create((IPropertyKey) IArgoPropertyKeys.CARRIER_VISIT_NOT_IN_CURRENT_FACILITY, null, (Object)this.getCvId(), (Object)inCurrentFacility.getFcyId());
        }
    }

    public boolean isGenericCv() {
        return this.getCvFacility() == null;
    }

    public boolean leavesComplexOnDeparture() {
        return this.getCvNextFacility() == null;
    }

    public boolean isInbound() {
        CarrierVisitPhaseEnum phase = this.getCvVisitPhase();
        if (CarrierVisitPhaseEnum.CREATED.equals((Object)phase)) {
            return true;
        }
        return CarrierVisitPhaseEnum.INBOUND.equals((Object)phase);
    }

    public boolean isAtFacility() {
        CarrierVisitPhaseEnum phase = this.getCvVisitPhase();
        if (CarrierVisitPhaseEnum.ARRIVED.equals((Object)phase)) {
            return true;
        }
        if (CarrierVisitPhaseEnum.WORKING.equals((Object)phase)) {
            return true;
        }
        return CarrierVisitPhaseEnum.COMPLETE.equals((Object)phase);
    }

    public boolean hasDepartedFacility() {
        CarrierVisitPhaseEnum phase = this.getCvVisitPhase();
        if (CarrierVisitPhaseEnum.DEPARTED.equals((Object)phase)) {
            return true;
        }
        if (CarrierVisitPhaseEnum.CLOSED.equals((Object)phase)) {
            return true;
        }
        return CarrierVisitPhaseEnum.ARCHIVED.equals((Object)phase);
    }

    public void arriveToFacility(Date inATA) {
        this.safelyUpdateVisitPhase(CarrierVisitPhaseEnum.ARRIVED);
        if (inATA != null) {
            this.setCvATA(inATA);
        } else {
            this.setCvATA(ArgoUtils.timeNow());
        }
    }

    public void departFromFacility(Date inATD) {
        this.safelyUpdateVisitPhase(CarrierVisitPhaseEnum.DEPARTED);
        if (inATD != null) {
            this.setCvATD(inATD);
        } else {
            this.setCvATD(ArgoUtils.timeNow());
        }
    }

    public void safelyUpdateVisitPhase(CarrierVisitPhaseEnum inNewPhase) {
        if (inNewPhase.equals((Object)this.getCvVisitPhase())) {
            return;
        }
        if (!this.isValidPhaseTransition(inNewPhase)) {
            BizViolation bv = BizViolation.create((IPropertyKey) IArgoPropertyKeys.INVALID_PHASE_TRANSITION, null, (Object)((Object)this.getCvVisitPhase()), (Object)((Object)inNewPhase), (Object)this.getCvId());
            LOGGER.error((Object)("safelyUpdateVisitPhase: " + bv.getMessage()));
            throw BizFailure.create((IPropertyKey) IArgoPropertyKeys.INVALID_PHASE_TRANSITION, (Throwable)bv);
        }
        this.setCvVisitPhase(inNewPhase);
    }

    public boolean isValidPhaseTransition(CarrierVisitPhaseEnum inNewPhase) {
        if (this.isGenericCv()) {
            throw new IllegalArgumentException("attempt to change the phase of a generic CarrierVisit: " + this);
        }
        if (this.getCvCarrierMode().equals((Object) LocTypeEnum.TRUCK) && inNewPhase.equals((Object) CarrierVisitPhaseEnum.CANCELED) && (this.getCvVisitPhase().equals((Object) CarrierVisitPhaseEnum.ARRIVED) || this.getCvVisitPhase().equals((Object) CarrierVisitPhaseEnum.WORKING) || this.getCvVisitPhase().equals((Object) CarrierVisitPhaseEnum.COMPLETE) || this.getCvVisitPhase().equals((Object) CarrierVisitPhaseEnum.DEPARTED))) {
            return true;
        }
        boolean isVessel = LocTypeEnum.VESSEL.equals((Object)this.getCvCarrierMode());
        CarrierVisitPhaseEnum[] validPhaseTransitions = isVessel ? CarrierVisitPhaseRules.getValidPhaseTransitions(this.getCvVisitPhase(), ContextHelper.getThreadUserContext()) : CarrierVisitPhaseRules.getValidPhaseTransitionsForRail(this.getCvVisitPhase(), ContextHelper.getThreadUserContext());
        boolean isValid = false;
        for (int i = 0; i < validPhaseTransitions.length; ++i) {
            if (!inNewPhase.equals((Object)validPhaseTransitions[i])) continue;
            isValid = true;
            break;
        }
        return isValid;
    }

    public String toString() {
        return this.getCvId();
    }

    public void addVisitDetail(VisitDetails inVisitDetails) {
        if (this.isGenericCv()) {
            throw new IllegalArgumentException("attempt to 'add Visit Detail' to a generic CarrierVisit: " + this);
        }
        if (this.getCvCvd() != null) {
            LOGGER.error((Object)("addVisitDetail: " + this + " already has VisitDetails: " + this.getCvCvd()));
            throw BizFailure.create((IPropertyKey)IFrameworkPropertyKeys.CRUD__DUPLICATE_NATURAL_KEY, null, (Object) IArgoField.CV_ID, (Object)this.getCvId());
        }
        this.setCvCvd(inVisitDetails);
        inVisitDetails.setCvdCv(this);
    }

    public void updateCvFacility(Facility inCvFacility) {
        super.setCvFacility(inCvFacility);
    }

    @Override
    public void setCvNextFacility(Facility inCvNextFacility) {
        super.setCvNextFacility(inCvNextFacility);
    }

    @Override
    public void setCvVisitPhase(CarrierVisitPhaseEnum inCvVisitPhase) {
        super.setCvVisitPhase(inCvVisitPhase);
    }

    @Override
    public void setCvATD(Date inCvATD) {
        super.setCvATD(inCvATD);
    }

    @Override
    public void setCvATA(Date inCvATA) {
        super.setCvATA(inCvATA);
    }

    public void updateCvId(String inCvId) {
        if (inCvId == null) {
            return;
        }
        if (this.getCvId().equals(inCvId)) {
            return;
        }
        super.setCvId(inCvId);
        Long maxVisitNbr = CarrierVisit.findMaxVisitNumber(this.getCvFacility(), this.getCvCarrierMode(), inCvId);
        super.setCvVisitNbr(maxVisitNbr + 1L);
    }

    private static CarrierVisit createCv(Complex inComplex, Facility inFacility, LocTypeEnum inMode, String inCvId, long inVisitNbr) {
        CarrierVisit cv = new CarrierVisit();
        cv.setCvComplex(inComplex);
        cv.setCvFacility(inFacility);
        cv.setCvCarrierMode(inMode);
        cv.setCvId(inCvId);
        cv.setCvVisitNbr(new Long(inVisitNbr));
        HibernateApi.getInstance().save((Object)cv);
        return cv;
    }

    public static CarrierVisit findOrCreateGenericCv(Complex inComplex, LocTypeEnum inCarrierType) {
        String cvKey = CarrierVisit.getGenericCvKey(inCarrierType);
        CarrierVisit cv = CarrierVisit.findCv(inComplex, null, inCarrierType, cvKey, null);
        if (cv == null) {
            cv = CarrierVisit.createCv(inComplex, null, inCarrierType, cvKey, 0L);
        }
        return cv;
    }

    private static CarrierVisit findCv(Complex inComplex, Facility inFacility, LocTypeEnum inMode, String inCvId, Long inVisitNbr) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"CarrierVisit").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoField.CV_COMPLEX, (Object)inComplex.getCpxGkey())).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoField.CV_CARRIER_MODE, (Object)((Object)inMode))).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoField.CV_ID, (Object)inCvId));
        if (inFacility == null) {
            dq.addDqPredicate(PredicateFactory.isNull((IMetafieldId) IArgoField.CV_FACILITY));
        } else {
            dq.addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoField.CV_FACILITY, (Object)inFacility.getFcyGkey()));
        }
        if (inVisitNbr != null && inVisitNbr != 0L) {
            dq.addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoField.CV_VISIT_NBR, (Object)inVisitNbr));
        }
        return (CarrierVisit)Roastery.getHibernateApi().getUniqueEntityByDomainQuery(dq);
    }

    public static List<CarrierVisit> findCvInComplex(Complex inComplex, String inCvId) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"CarrierVisit").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoField.CV_COMPLEX, (Object)inComplex.getCpxGkey())).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoField.CV_CARRIER_MODE, (Object)((Object) LocTypeEnum.VESSEL))).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoField.CV_ID, (Object)inCvId));
        return Roastery.getHibernateApi().findEntitiesByDomainQuery(dq);
    }

    private static Long findMaxVisitNumber(Facility inFacility, LocTypeEnum inMode, String inCarrierId) {
        Long maxVisitNbr = null;
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"CarrierVisit").addDqAggregateField(AggregateFunctionType.MAX, IArgoField.CV_VISIT_NBR).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoField.CV_FACILITY, (Object)inFacility.getFcyGkey())).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoField.CV_CARRIER_MODE, (Object)((Object)inMode))).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoField.CV_ID, (Object)inCarrierId));
        IQueryResult qr = Roastery.getHibernateApi().findValuesByDomainQuery(dq);
        if (qr.getTotalResultCount() > 0) {
            maxVisitNbr = (Long)qr.getValue(0, 0);
        }
        if (maxVisitNbr == null) {
            maxVisitNbr = new Long(0L);
        }
        return maxVisitNbr;
    }

    public static String getGenericCvKey(LocTypeEnum inCvCarrierMode) {
        if (inCvCarrierMode == LocTypeEnum.TRUCK) {
            return GEN_TRUCK;
        }
        if (inCvCarrierMode == LocTypeEnum.VESSEL) {
            return GEN_VESSEL;
        }
        if (inCvCarrierMode == LocTypeEnum.TRAIN) {
            return GEN_TRAIN;
        }
        if (inCvCarrierMode == LocTypeEnum.RAILCAR) {
            return GEN_RAILCAR;
        }
        return GEN_UNKNOWN;
    }

    public boolean isPointInItinerary(RoutingPoint inPoint) {
        boolean inItinerary = false;
        if (this.getCvCvd() != null) {
            inItinerary = this.getCvCvd().isPointInItinerary(inPoint);
        }
        return inItinerary;
    }

    public boolean isPointAfterCall(RoutingPoint inPod) {
        boolean isAfter = false;
        if (this.getCvCvd() != null) {
            isAfter = this.getCvCvd().isPointAfterCall(inPod);
        }
        return isAfter;
    }

    public boolean isPointBeforeCall(RoutingPoint inPol) {
        boolean isBefore = false;
        if (this.getCvCvd() != null) {
            isBefore = this.getCvCvd().isPointBeforeCall(inPol);
        }
        return isBefore;
    }

    public LogicalEntityEnum getLogicalEntityType() {
        if (LocTypeEnum.VESSEL.equals((Object)this.getCvCarrierMode())) {
            return LogicalEntityEnum.VV;
        }
        return LogicalEntityEnum.NA;
    }

    public String getLogEntityId() {
        return this.getCvId();
    }

    public static MetafieldIdList getPredicateFields(LogicalEntityEnum inEntity) {
        MetafieldIdList fields = new MetafieldIdList();
        fields.add(IArgoField.CV_CARRIER_MODE);
        fields.add(IArgoField.CV_NEXT_FACILITY);
        fields.add(IArgoField.CV_A_T_A);
        fields.add(IArgoField.CV_VISIT_PHASE);
        return fields;
    }

    public static MetafieldIdList getPathsToGuardians(LogicalEntityEnum inEntity) {
        MetafieldIdList fields = new MetafieldIdList();
        return fields;
    }

    public static MetafieldIdList getPathsToBizUnits(LogicalEntityEnum inEntity) {
        MetafieldIdList fields = new MetafieldIdList();
        return fields;
    }

    public static MetafieldIdList getUpdateFields(LogicalEntityEnum inEntity) {
        MetafieldIdList fields = new MetafieldIdList();
        fields.add(IArgoField.CV_CARRIER_MODE);
        fields.add(IArgoField.CV_NEXT_FACILITY);
        fields.add(IArgoField.CV_A_T_A);
        return fields;
    }

    public String getHumanReadableKey() {
        return this.getCvId();
    }

    public static CarrierVisit findCarrierVisitByGkey(Serializable inCvgkey) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"CarrierVisit").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoField.CV_GKEY, (Object)inCvgkey));
        return (CarrierVisit)HibernateApi.getInstance().getUniqueEntityByDomainQuery(dq);
    }

    public String getCarrierCountryName() {
        String result = null;
        VisitDetails visitDetails = this.getCvCvd();
        if (visitDetails != null) {
            result = visitDetails.getCarrierCountryName();
        }
        return result;
    }

    public String getCarrierDocumentationNbr() {
        String result = null;
        VisitDetails visitDetails = this.getCvCvd();
        if (visitDetails != null) {
            result = visitDetails.getCarrierDocumentationNbr();
        }
        return result;
    }

    public ScopedBizUnit getCarrierOperator() {
        return this.getCvCvd() == null ? null : this.getCvCvd().getCarrierOperator();
    }

    public String getCarrierOperatorId() {
        ScopedBizUnit carrierOperator = this.getCarrierOperator();
        if (carrierOperator != null) {
            return carrierOperator.getBzuId();
        }
        return null;
    }

    public String getCarrierOperatorName() {
        ScopedBizUnit carrierOperator = this.getCarrierOperator();
        if (carrierOperator != null) {
            return carrierOperator.getBzuName();
        }
        return null;
    }

    @Override
    public String getCarrierVehicleId() {
        return this.getCvCvd() == null ? null : this.getCvCvd().getCarrierVehicleId();
    }

    public String getCarrierVisitPosition() {
        return this.getCvCvd() == null ? null : this.getCvCvd().getCarrierVisitPosition();
    }

    public String getCarrierVehicleName() {
        return this.getCvCvd() == null ? null : this.getCvCvd().getCarrierVehicleName();
    }

    public String getCvCarrierIdVehicleNameAndFacility() {
        return this.getCvCvd() == null ? this.getCvId() : this.getCvCvd().getCvdCarrierIdVehicleNameAndFacility();
    }

    public Object getCarrierVesselClassType() {
        return this.getCvCvd() == null ? null : this.getCvCvd().getCarrierVesselClassType();
    }

    public Object getCarrierVesselClassification() {
        return this.getCvCvd() == null ? null : this.getCvCvd().getCvdClassification();
    }

    public Object getCarrierVesselClass() {
        return this.getCvCvd() == null ? null : this.getCvCvd().getCarrierVesselClass();
    }

    public Object getCvClassification() {
        return this.getCvCvd() == null ? null : this.getCvCvd().getCvdClassification();
    }

    public String getCarrierIbVoyNbrOrTrainId() {
        return this.getCvCvd() == null ? null : this.getCvCvd().getCarrierIbVoyNbrOrTrainId();
    }

    public String getCarrierIbVisitCallNbr() {
        return this.getCvCvd() == null ? null : this.getCvCvd().getCarrierIbVisitCallNbr();
    }

    public String getCarrierObVoyNbrOrTrainId() {
        return this.getCvCvd() == null ? null : this.getCvCvd().getCarrierObVoyNbrOrTrainId();
    }

    public String getCarrierObVisitCallNbr() {
        return this.getCvCvd() == null ? null : this.getCvCvd().getCarrierObVisitCallNbr();
    }

    public Date getCarrierStartWorkTime() {
        return this.getCvCvd() == null ? null : this.getCvCvd().getCarrierStartWorkTime();
    }

    public com.zpmc.ztos.infra.base.common.events.AuditEvent vetAuditEvent(AuditEvent inAuditEvent) {
        return inAuditEvent;
    }

    @Override
    public LocTypeEnum getLocType() {
        return this.getCvCarrierMode();
    }

    @Override
    public String getLocId() {
        return this.getCvId();
    }

    @Override
    public Long getLocGkey() {
        return this.getCvGkey();
    }

    @Override
    public Facility getLocFacility() {
        return this.getCvFacility();
    }

    @Override
    public CarrierVisit getInboundCv() {
        return this.resolveCarrierVisit(true);
    }

    @Override
    public CarrierVisit getOutboundCv() {
        return this.resolveCarrierVisit(false);
    }

    public CarrierVisit resolveOutboundCarrierVisit() {
        return this.resolveCarrierVisit(false);
    }

    public static CarrierVisit resolveCv(Facility inFacility, CarrierModeEnum inMode, String inCvId) throws BizViolation {
        boolean cvGeneric;
        boolean bl = cvGeneric = StringUtils.isEmpty((String)inCvId) || CarrierVisit.isGenericId(inCvId);
        if (CarrierModeEnum.VESSEL.equals((Object)inMode)) {
            if (cvGeneric) {
                return CarrierVisit.getGenericVesselVisit(inFacility.getFcyComplex());
            }
            return CarrierVisit.findOrCreateVesselVisit(inFacility, inCvId);
        }
        if (CarrierModeEnum.TRUCK.equals((Object)inMode)) {
            if (cvGeneric) {
                return CarrierVisit.getGenericTruckVisit(inFacility.getFcyComplex());
            }
            return CarrierVisit.findOrCreateActiveTruckVisit(inFacility, inCvId);
        }
        if (CarrierModeEnum.TRAIN.equals((Object)inMode)) {
            if (cvGeneric) {
                return CarrierVisit.getGenericTrainVisit(inFacility.getFcyComplex());
            }
            return CarrierVisit.findOrCreateTrainVisit(inFacility, inCvId);
        }
        if (CarrierModeEnum.UNKNOWN.equals((Object)inMode)) {
            return CarrierVisit.getGenericCarrierVisit(inFacility.getFcyComplex());
        }
        throw BizFailure.create((IPropertyKey) IArgoPropertyKeys.CARRIER_MODE_NOT_SUPPORTED, null, (Object)inMode.getKey());
    }

    private CarrierVisit resolveCarrierVisit(boolean inWantInbound) {
        VisitDetails cvd = this.getCvCvd();
        if (cvd == null) {
            return this;
        }
        if (inWantInbound) {
            return cvd.getInboundCv();
        }
        return cvd.getOutboundCv();
    }

    @Override
    public void verifyMoveToAllowed(IEntity inMovingEntity, String inSlot) throws BizViolation {
        VisitDetails cvd = this.getCvCvd();
        if (cvd != null) {
            cvd.verifyLoadAllowed(inMovingEntity, inSlot);
        }
    }

    @Override
    public void verifyMoveFromAllowed(IEntity inMovingEntity, String inSlot) throws BizViolation {
        VisitDetails cvd = this.getCvCvd();
        if (cvd != null) {
            cvd.verifyDischargeAllowed(inMovingEntity);
        }
    }

    public void preProcessInsertOrUpdate(FieldChanges inOutMoreChanges) {
        super.preProcessInsertOrUpdate(inOutMoreChanges);
        VisitDetails cvd = this.getCvCvd();
        if (cvd != null) {
            if (this.getCvOperator() == null) {
                this.setSelfAndFieldChange(IArgoField.CV_OPERATOR, cvd.getCarrierOperator(), inOutMoreChanges);
            } else if (!ObjectUtils.equals((Object)this.getCvOperator(), (Object)cvd.getCarrierOperator())) {
                this.setSelfAndFieldChange(IArgoField.CV_OPERATOR, cvd.getCarrierOperator(), inOutMoreChanges);
            }
        }
    }

    public void preProcessUpdate(FieldChanges inChanges, FieldChanges inOutMoreChanges) {
        super.preProcessUpdate(inChanges, inOutMoreChanges);
        CarrierVisitPhaseEnum visitPhase = this.getCvVisitPhase();
        boolean isPhaseChange = false;
        boolean isATDChange = false;
        Object oldValue = null;
        IMetafieldId atdFieldId = null;
        if (this.getCvCarrierMode() != null && this.getCvFacility() != null && LocTypeEnum.VESSEL.equals((Object)this.getCvCarrierMode())) {
            MetafieldIdList fieldList = inChanges.getFieldIds();
            for (IMetafieldId fieldId : fieldList) {
                if (fieldId.getFieldId().equals(IArgoField.CV_VISIT_PHASE.getFieldId())) {
                    if (!CarrierVisitPhaseEnum.CLOSED.equals(inChanges.getFieldChange(0).getPriorValue()) || !CarrierVisitPhaseEnum.DEPARTED.equals(inChanges.getFieldChange(0).getNewValue())) continue;
                    isPhaseChange = true;
                    continue;
                }
                if (!fieldId.getFieldId().equals(IArgoField.CV_A_T_D.getFieldId())) continue;
                oldValue = inChanges.getFieldChange(fieldId).getPriorValue();
                atdFieldId = fieldId;
                isATDChange = true;
            }
            if (isPhaseChange && isATDChange) {
                inOutMoreChanges.setFieldChange(atdFieldId, oldValue);
            }
        }
    }

    public void updateCvOperator(ScopedBizUnit inOperator) {
        this.setCvOperator(inOperator);
    }

    public boolean isNewPhaseRecallingVisit(CarrierVisitPhaseEnum inNewPhase) {
        if (inNewPhase == null) {
            return false;
        }
        CarrierVisitPhaseEnum currentPhase = this.getCvVisitPhase();
        return inNewPhase.equals((Object) CarrierVisitPhaseRules.getPreviousPhaseTransition(currentPhase)) && (CarrierVisitPhaseEnum.DEPARTED.equals((Object)currentPhase) || CarrierVisitPhaseEnum.INBOUND.equals((Object)currentPhase));
    }

    public void validateEmptyPickupAllowed(ScopedBizUnit inLine, Date inEffectiveDate) throws BizViolation {
        VisitDetails cvd = this.getCvCvd();
        if (cvd != null) {
            cvd.validateEmptyPickupAllowed(inLine, inEffectiveDate);
        }
    }

    public void validateExportReceiveAllowed(ScopedBizUnit inLine, Date inEffectiveDate) throws BizViolation {
        VisitDetails cvd = this.getCvCvd();
        if (cvd != null) {
            cvd.validateExportReceiveAllowed(inLine, inEffectiveDate);
        }
    }

    public void validateLineAllowed(ScopedBizUnit inLine) throws BizViolation {
        VisitDetails cvd = this.getCvCvd();
        if (cvd != null) {
            cvd.validateLineAllowed(inLine);
        }
    }

    public void validatePastCutOff(ScopedBizUnit inLine, boolean inIsHazardous, boolean inIsReefer) throws BizViolation {
        VisitDetails cvd = this.getCvCvd();
        if (cvd != null) {
            cvd.validatePastCutOff(inLine, inIsHazardous, inIsReefer);
        }
    }

    public void validatePastCutOff(ScopedBizUnit inLine, boolean inIsHazardous, boolean inIsReefer, Date inApptDate) throws BizViolation {
        VisitDetails cvd = this.getCvCvd();
        if (cvd != null) {
            cvd.validatePastCutOff(inLine, inIsHazardous, inIsReefer, inApptDate);
        }
    }

    public CarrierVisit tryRollingLateCargo(ScopedBizUnit inLine, boolean inIsHazardous, boolean inIsLiveReefer, BizViolation inLateViolation) throws BizViolation {
        CarrierVisit newCv = null;
        VisitDetails cvd = this.getCvCvd();
        if (cvd != null) {
            newCv = cvd.tryRollingLateCargo(inLine, inIsHazardous, inIsLiveReefer, inLateViolation);
        }
        return newCv;
    }

    public Boolean isCarrierVisitDrayingOff() {
        VisitDetails cvd = this.getCvCvd();
        Boolean isDrayingOff = Boolean.FALSE;
        if (cvd != null) {
            isDrayingOff = cvd.isCarrierVisitDrayingOff();
        }
        return isDrayingOff;
    }

    public CarrierVisit findOrCreateNextVisit() {
        VisitDetails cvd;
        CarrierVisit nextVisit = null;
        if (this.getCvNextFacility() != null && (cvd = this.getCvCvd()) != null) {
            nextVisit = cvd.findOrCreateNextVisit();
        }
        return nextVisit;
    }

    public BizViolation validateDeletion() {
        BizViolation bv = super.validateDeletion();
        if (ContextHelper.isUpdateFromHumanUser() && (LocTypeEnum.VESSEL.equals((Object)this.getCvCarrierMode()) || LocTypeEnum.TRAIN.equals((Object)this.getCvCarrierMode())) && this.isVisitPhaseBadForDeletion(this.getCvVisitPhase())) {
            bv = BizViolation.create((IPropertyKey) IArgoPropertyKeys.CVD_BAD_PHASE_FOR_DELETION, (BizViolation)bv, (Object)this.getCvId(), (Object)((Object)this.getCvVisitPhase()), (Object)new Object[]{CarrierVisitPhaseEnum.CREATED, CarrierVisitPhaseEnum.CANCELED});
        }
        return bv;
    }

    private boolean isVisitPhaseBadForDeletion(CarrierVisitPhaseEnum inCarrierVisitPhaseEnum) {
        return !CarrierVisitPhaseEnum.CREATED.equals((Object)inCarrierVisitPhaseEnum) && !CarrierVisitPhaseEnum.CANCELED.equals((Object)inCarrierVisitPhaseEnum);
    }

    public boolean isVisitPhaseActive() {
        CarrierVisitPhaseEnum phase = this.getCvVisitPhase();
        return phase.equals((Object) CarrierVisitPhaseEnum.CREATED) || phase.equals((Object) CarrierVisitPhaseEnum.INBOUND) || phase.equals((Object) CarrierVisitPhaseEnum.ARRIVED) || phase.equals((Object) CarrierVisitPhaseEnum.WORKING);
    }

    public LocPosition createInboundPosition(LocPosition inOutboundPosition) {
        VisitDetails cvd = this.getCvCvd();
        if (cvd != null) {
            return cvd.createInboundPosition(inOutboundPosition);
        }
        return LocPosition.createLocPosition(this, inOutboundPosition.getPosSlot(), inOutboundPosition.getPosOrientation());
    }

    public static boolean isGenericId(String inCvId) {
        return GEN_TRUCK.equals(inCvId) || GEN_VESSEL.equals(inCvId) || GEN_TRAIN.equals(inCvId) || GEN_RAILCAR.equals(inCvId) || GEN_UNKNOWN.equals(inCvId);
    }

    public static CarrierVisit hydrate(Serializable inPrimaryKey) {
        return (CarrierVisit)HibernateApi.getInstance().load(CarrierVisit.class, inPrimaryKey);
    }

    public boolean acceptsEdos() {
        VisitDetails cvd;
        boolean accepts;
        boolean bl = accepts = LocTypeEnum.TRUCK.equals((Object)this.getCvCarrierMode()) || LocTypeEnum.TRAIN.equals((Object)this.getCvCarrierMode()) || LocTypeEnum.UNKNOWN.equals((Object)this.getCvCarrierMode());
        if (!accepts && (cvd = this.getCvCvd()) != null) {
            accepts = cvd.acceptsEdos();
        }
        return accepts;
    }

    public boolean acceptsElos() {
        boolean accepts = LocTypeEnum.VESSEL.equals((Object)this.getCvCarrierMode());
        return accepts;
    }

    public Class getArchiveClass() {
//        ArgoDatabasePurgeBean purgeBean = (ArgoDatabasePurgeBean)Roastery.getBean((String)"argoDatabasePurgeBean");
//        IPurgeDriverManager manager = purgeBean.getDrivingPurgeManager();
//        if (manager != null) {
//            return manager.getArchiveCounterPart(this.getEntityName());
//        }
        return ArchiveCarrierVisit.class;
    }

    public boolean doArchive() {
        UserContext userContext = ContextHelper.getThreadUserContext();
        return ArgoConfig.ARCHIVE_CARRIER_VISITS_PRIOR_TO_PURGE.isOn(userContext);
    }

    public IBinModel getCarrierBinModel() {
        VisitDetails cvd = this.getCvCvd();
        return cvd == null ? null : cvd.getCarrierBinModel();
    }

    public int getTableId() {
        int tableId;
        VisitDetails cvd = this.getCvCvd();
        int n = tableId = cvd == null ? 0 : XpsDbManagerId.getTableId(cvd.getClass());
        if (tableId == -1) {
            Class clazz = HiberCache.entityName2EntityClass((String)cvd.getEntityName());
            if (clazz == null) {
                LOGGER.error((Object)("HiberCache.entityName2EntityClass return null for " + cvd.getEntityName()));
            } else {
                tableId = XpsDbManagerId.getTableId((Class)clazz);
            }
        }
        return tableId;
    }
}
