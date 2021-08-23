package com.zpmc.ztos.infra.base.business.inventory;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.business.dataobject.UnitFacilityVisitDO;
import com.zpmc.ztos.infra.base.business.enums.argo.*;
import com.zpmc.ztos.infra.base.business.enums.framework.LifeCycleStateEnum;
import com.zpmc.ztos.infra.base.business.enums.framework.PredicateVerbEnum;
import com.zpmc.ztos.infra.base.business.enums.inventory.*;
import com.zpmc.ztos.infra.base.business.equipments.*;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.business.model.*;
import com.zpmc.ztos.infra.base.business.plans.WorkInstruction;
import com.zpmc.ztos.infra.base.business.predicate.Disjunction;
import com.zpmc.ztos.infra.base.business.predicate.PredicateFactory;
import com.zpmc.ztos.infra.base.common.callbacks.CarinaPersistenceCallback;
import com.zpmc.ztos.infra.base.common.configs.ArgoConfig;
import com.zpmc.ztos.infra.base.common.configs.InventoryConfig;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.common.database.PersistenceTemplate;
import com.zpmc.ztos.infra.base.common.events.AuditEvent;
import com.zpmc.ztos.infra.base.common.events.Event;
import com.zpmc.ztos.infra.base.common.events.EventManager;
import com.zpmc.ztos.infra.base.common.events.EventType;
import com.zpmc.ztos.infra.base.common.exceptions.BizFailure;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.helps.ContextHelper;
import com.zpmc.ztos.infra.base.common.model.*;
import com.zpmc.ztos.infra.base.common.scopes.Complex;
import com.zpmc.ztos.infra.base.common.scopes.Facility;
import com.zpmc.ztos.infra.base.common.scopes.Yard;
import com.zpmc.ztos.infra.base.common.utils.*;
import com.zpmc.ztos.infra.base.utils.QueryUtils;
import com.zpmc.ztos.infra.base.utils.StringUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.log4j.Logger;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.io.Serializable;
import java.util.*;

public class UnitFacilityVisit extends UnitFacilityVisitDO {

    public static final int TS1_ADVISED = 0;
    public static final int TS2_INBOUND = 1;
    public static final int TS3_ECIN = 2;
    public static final int TS4_YARD = 3;
    public static final int TS5_ECOUT = 4;
    public static final int TS6_LOADED = 5;
    public static final int TS7_DEPARTED = 6;
    public static final int TS9_RETIRED = 7;
    private static final Long ZERO = 0L;
    private boolean _isNewlyCreated;
    private static final Logger LOGGER = Logger.getLogger(UnitFacilityVisit.class);
    private static final BizFieldList BIZ_FIELD_LIST_ALL;
    private static final BizFieldList BIZ_FIELD_LIST_NOP;
    static final String LINE_STORAGE = "LINE_STORAGE";
    static final String STORAGE = "STORAGE";
    static final String REEFER = "REEFER";
    static final String POWER = "POWER";
    private static final String UFV = "UFV";
    private static final String TBD_UNIT = "TBD";

    protected static UnitFacilityVisit createUnitFacilityVisit(Unit inUnit, Facility inFacility, LocPosition inInboundPosition, CarrierVisit inObCv) throws BizViolation {
        if (!ObjectUtils.equals((Object)inFacility.getFcyComplex(), (Object)inUnit.getUnitComplex())) {
            throw BizFailure.create((IPropertyKey) IInventoryPropertyKeys.UNITS__FCY_NOT_IN_CPX, null, (Object)inFacility, (Object)inUnit.getUnitComplex());
        }
        if (inInboundPosition == null || !inInboundPosition.isCarrierPosition()) {
            throw BizFailure.create((String)("invalid inbound position (must be a carrier position) " + (Object)inInboundPosition));
        }
        HashSet<UnitFacilityVisit> unitUfvSet = (HashSet<UnitFacilityVisit>) inUnit.getUnitUfvSet();
        if (unitUfvSet != null) {
            for (Object e : unitUfvSet) {
                UnitFacilityVisit ufv = (UnitFacilityVisit)e;
                if (!inFacility.equals((Object)ufv.getUfvFacility()) || UnitVisitStateEnum.DEPARTED.equals((Object)ufv.getUfvVisitState()) || UnitVisitStateEnum.RETIRED.equals((Object)ufv.getUfvVisitState())) continue;
                throw BizViolation.create((IPropertyKey) IInventoryPropertyKeys.NON_DEPARTED_UFV_EXIST_CANNOT_CREATE_ADVISED, null);
            }
        }
        UnitFacilityVisit ufv = new UnitFacilityVisit();
        ufv.setUfvFacility(inFacility);
        ufv.setUfvArrivePosition(inInboundPosition);
        CarrierVisit carrierVisit = inInboundPosition.resolveInboundCarrierVisit();
        Facility ibCvFacility = carrierVisit.getCvFacility();
        if (!carrierVisit.isGenericCv() && !LocTypeEnum.TRAIN.equals((Object)carrierVisit.getCvCarrierMode()) && ibCvFacility.isFcyNonOperational().booleanValue()) {
            CarrierVisit genCarrier = CarrierVisit.getGenericCarrierVisit((Complex)inUnit.getUnitComplex());
            ufv.setUfvActualIbCv(genCarrier);
        } else {
            ufv.setUfvActualIbCv(carrierVisit);
        }
        Facility obCvFacility = inObCv.getCvFacility();
        if (!inObCv.isGenericCv() && !LocTypeEnum.TRAIN.equals((Object)carrierVisit.getCvCarrierMode()) && obCvFacility.isFcyNonOperational().booleanValue()) {
            CarrierVisit genCarrier = CarrierVisit.getGenericCarrierVisit((Complex)inUnit.getUnitComplex());
            ufv.setUfvActualObCv(genCarrier);
            ufv.setUfvIntendedObCv(genCarrier);
        } else {
            ufv.setUfvActualObCv(inObCv);
            ufv.setUfvIntendedObCv(inObCv);
        }
        ufv.setUfvRestowType(RestowTypeEnum.NONE);
        ufv.setUfvHandlingReason(HandlingReasonEnum.VESSELOP);
        ufv.setUfvYardStowageType(YardStowRqmntEnum.EITHER);
        ufv.setUfvUnit(inUnit);
        if (unitUfvSet == null) {
            unitUfvSet = new HashSet<UnitFacilityVisit>();
            inUnit.setUnitUfvSet(unitUfvSet);
        }
        unitUfvSet.add(ufv);
        for (Object o : inFacility.getFcyYrdSet()) {
            Yard yard = (Yard)o;
            if (LifeCycleStateEnum.OBSOLETE.equals((Object)yard.getLifeCycleState())) {
                if (!LOGGER.isDebugEnabled()) continue;
                LOGGER.debug((Object)("Ignoring obsoleted yard while creating UFV:" + yard.getYrdId()));
                continue;
            }
            UnitYardVisit.createUnitYardVisit(yard, ufv);
        }
        ufv.updateLastKnownPosition(inInboundPosition, null);
        Long moveCount = 0L;
        ufv.setUfvMoveCount(moveCount);
        HibernateApi.getInstance().save((Object)ufv);
        ufv.setNewlyCreated(true);
        return ufv;
    }

    public static UnitFacilityVisit hydrate(Serializable inPrimaryKey) {
        return (UnitFacilityVisit)HibernateApi.getInstance().load(UnitFacilityVisit.class, inPrimaryKey);
    }

    public UnitFacilityVisit() {
        this.setUfvVisitState(UnitVisitStateEnum.ADVISED);
        this.setUfvTransitState(UfvTransitStateEnum.S10_ADVISED);
        this.setUfvIsSparcsStopped(Boolean.FALSE);
        this.setUfvHasChanged(Boolean.FALSE);
        this.setUfvVerifiedForLoad(Boolean.FALSE);
        this.setUfvVerifiedYardPosition(Boolean.FALSE);
        this.setUfvAnomalyForExpDkng(Boolean.FALSE);
        this.setUfvVisibleInSparcs(Boolean.FALSE);
        this.setUfvCreateTime(ArgoUtils.timeNow());
        this.setUfvHorizon(new Long("9999999999"));
        this.setUfvMoveCount(0L);
        this.setUfvIsRASPriority(Boolean.FALSE);
    }

    public void updateObCv(final CarrierVisit inObCv) {
        try {
            this.getUfvUnit().applyUpdateOnUnitCombo(new AbstractUnitUpdate(){

                @Override
                public void apply(Unit inUnit) throws BizViolation {
                    UnitFacilityVisit ufv = UnitFacilityVisit.this.getUfvUnit().equals(inUnit) ? UnitFacilityVisit.this : inUnit.getUfvForFacilityNewest(UnitFacilityVisit.this.getUfvFacility());
                    ufv.doUpdateObCv(inObCv);
                }

                @Override
                public boolean allowUpdate(Unit inUnit) {
                    return EqUnitRoleEnum.PAYLOAD.equals((Object)inUnit.getUnitEqRole());
                }
            });
        }
        catch (BizViolation inBizViolation) {
            LOGGER.error((Object)"Failed while updating obCv");
        }
    }

    private void doUpdateObCv(CarrierVisit inObCv) {
        Facility obCvFcy;
        Routing unitRouting = this.getUfvUnit().getUnitRouting();
        UnitCategoryEnum category = this.getUfvUnit().getUnitCategory();
        boolean isYetToBeLoaded = this.isTransitStatePriorTo(UfvTransitStateEnum.S60_LOADED);
        boolean isCvDepartingComplex = inObCv == null || inObCv.getCvNextFacility() == null;
        Facility facility = obCvFcy = inObCv != null ? inObCv.getCvFacility() : null;
        if (obCvFcy == null || !obCvFcy.isFcyNonOperational().booleanValue()) {
            this.setUfvActualObCv(inObCv);
            if (isYetToBeLoaded) {
                this.setUfvIntendedObCv(inObCv);
            }
        } else {
            CarrierVisit decalredObCv = unitRouting.getRtgDeclaredCv();
            boolean isIntendedObCvSameAsDeclaredObCv = decalredObCv.equals((Object)this.getUfvIntendedObCv());
            boolean isActualObCvSameAsDeclaredObCv = decalredObCv.equals((Object)this.getUfvActualObCv());
            if (isIntendedObCvSameAsDeclaredObCv) {
                this.setUfvIntendedObCv(inObCv);
            }
            if (isActualObCvSameAsDeclaredObCv) {
                this.setUfvActualObCv(inObCv);
            }
            if (!(UnitCategoryEnum.EXPORT.equals((Object)category) || UnitCategoryEnum.TRANSSHIP.equals((Object)category) || UnitCategoryEnum.DOMESTIC.equals((Object)category))) {
                unitRouting.setRtgDeclaredCv(inObCv);
            }
            if (this.getUfvActualObCv() == null) {
                this.setUfvActualObCv(CarrierVisit.getGenericCarrierVisit((Complex)this.getUfvFacility().getFcyComplex()));
            }
            if (isYetToBeLoaded && this.getUfvIntendedObCv() == null) {
                this.setUfvIntendedObCv(CarrierVisit.getGenericCarrierVisit((Complex)this.getUfvFacility().getFcyComplex()));
            }
        }
        if (isCvDepartingComplex && !UnitCategoryEnum.EXPORT.equals((Object)category) && !UnitCategoryEnum.TRANSSHIP.equals((Object)category) && !UnitCategoryEnum.DOMESTIC.equals((Object)category)) {
            unitRouting.setRtgDeclaredCv(inObCv);
        }
        if (inObCv != null && inObCv.getCvCvd() != null) {
            unitRouting.setRtgCarrierService(inObCv.getCvCvd().getCvdService());
        } else {
            unitRouting.setRtgCarrierService(null);
        }
        UnitEventExtractManager.updateEventOBCarrierVisit(this);
    }

    public CarrierVisit getUfvObCv() {
        return this.getUfvActualObCv();
    }

    @Nullable
    public String getInboundCarrierInVoyNbrTrainId() {
        return this.getLineVoyNbrOrTrainId(CarrierDirectionEnum.IB, this.getUfvActualIbCv());
    }

    @Nullable
    public String getInboundCarrierOutVoyNbrTrainId() {
        return this.getLineVoyNbrOrTrainId(CarrierDirectionEnum.OB, this.getUfvActualIbCv());
    }

    @Nullable
    public String getOutboundCarrierInVoyNbrTrainId() {
        return this.getLineVoyNbrOrTrainId(CarrierDirectionEnum.IB, this.getUfvActualObCv());
    }

    @Nullable
    public String getOutboundCarrierOutVoyNbrTrainId() {
        return this.getLineVoyNbrOrTrainId(CarrierDirectionEnum.OB, this.getUfvActualObCv());
    }

    @Nullable
    private String getLineVoyNbrOrTrainId(CarrierDirectionEnum inDirection, CarrierVisit inCv) {
        Unit inUnit = this.getUfvUnit();
        ScopedBizUnit line = inUnit.getUnitLineOperator();
        if (line == null || inCv == null || inCv.getCvCvd() == null) {
            return null;
        }
        return inCv.getCvCvd().getCarrierLineVoyNbrOrTrainId(line, inDirection);
    }

    public void setUfvObCv(Object inGkey) {
        CarrierVisit cv = CarrierVisit.findCarrierVisitByGkey((Serializable)((Serializable)inGkey));
        this.updateObCv(cv);
    }

    public void updateActualIbCv(CarrierVisit inIbCv) {
        boolean isDeclaredSameAsActual = ObjectUtils.equals((Object)this.getUfvActualIbCv(), (Object)this.getUfvUnit().getUnitDeclaredIbCv());
        this.setUfvActualIbCv(inIbCv);
        if (isDeclaredSameAsActual) {
            this.getUfvUnit().setUnitDeclaredIbCv(inIbCv);
        }
    }

    public void updateHasActiveAlarm(Boolean inActive) {
        this.setUfvHasActiveAlarm(inActive);
    }

    public void updateLastKnownPositionForCargo(LocPosition inPosition) {
        this.updateLastKnownPosition(inPosition, null);
    }

    private boolean checkPositionUpdateOk(@NotNull LocPosition inNewPosition, @NotNull Unit inUnit) {
        UnitFacilityVisit ufv = inUnit.getUnitActiveUfvNowActive();
        if (ufv != null && ufv.calculateTransitStateValue() > this.calculateTransitStateValue()) {
            return false;
        }
        return !inUnit.isChassis() || !inNewPosition.isVesselPosition() && !inNewPosition.isRailPosition();
    }

    private LocPosition getClonePosition(Unit inUnit, LocPosition inPosition, boolean inIsAllFieldsClone) throws BizViolation {
        LocPosition clonedPosition = inPosition;
        Unit curUnit = this.getUfvUnit();
        UnitFacilityVisit ufv = inUnit.getUnitActiveUfv();
        if (ufv != null) {
            LocPosition currentPosition = inUnit.getUnitActiveUfv().getUfvLastKnownPosition();
            boolean hasCarriage = curUnit.getUnitEqRole() == EqUnitRoleEnum.CARRIAGE || curUnit.getUnitCarriageUnit() != null || curUnit.getUnitRelatedUnit() != null && curUnit.getUnitRelatedUnit().getUnitCarriageUnit() != null;
            EqUnitRoleEnum role = inUnit.getUnitEqRole();
            boolean useIndividualFields = role == EqUnitRoleEnum.ACCESSORY || role == EqUnitRoleEnum.PAYLOAD || role == EqUnitRoleEnum.PRIMARY;
            clonedPosition = LocPosition.copyUpdatePosition((LocPosition)currentPosition, (LocPosition)inPosition, (boolean)useIndividualFields, (boolean)hasCarriage, (boolean)inIsAllFieldsClone);
        }
        return clonedPosition;
    }

    protected void updateLastKnownPosition(final LocPosition inPosition, final Date inMoveTime) {
        boolean hasCarriage;
        final Unit curUnit = this.getUfvUnit();
        LocPosition currentPosition = this.getUfvLastKnownPosition();
        boolean bl = hasCarriage = curUnit.getUnitCarriageUnit() != null || EqUnitRoleEnum.CARRIAGE.equals((Object)curUnit.getUnitEqRole());
        if (curUnit.getUnitCombo() != null) {
            try {
                curUnit.applyUpdateOnUnitCombo(new AbstractUnitUpdate(){

                    @Override
                    public void apply(Unit inUnit) throws BizViolation {
                        boolean isAllFieldsClone = inUnit.equals(curUnit) || inUnit.getUnitRelatedUnit() != null && curUnit.equals(inUnit.getUnitRelatedUnit()) && curUnit.getUnitEqRole() == EqUnitRoleEnum.PRIMARY;
                        LocPosition clonePosition = UnitFacilityVisit.this.getClonePosition(inUnit, inPosition, isAllFieldsClone);
                        UnitFacilityVisit ufv = UnitFacilityVisit.this.getEqUfv(inUnit);
                        if (ufv != null) {
                            ufv.doUpdateLastKnownPosition(clonePosition, inMoveTime);
                        }
                    }

                    @Override
                    public boolean allowUpdate(Unit inUnit) {
                        LocPosition curPos = inUnit.findCurrentPosition();
                        boolean corePositionChanged = inPosition.isCorePositionChanged(curPos);
                        if (corePositionChanged) {
                            return UnitFacilityVisit.this.checkPositionUpdateOk(curPos, inUnit);
                        }
                        boolean individualPositionChanged = inPosition.isIndividualPositionChanged(curPos);
                        if (individualPositionChanged) {
                            return inUnit.getUnitRelatedUnit() != null && curUnit.equals(inUnit.getUnitRelatedUnit());
                        }
                        return false;
                    }

                    @Override
                    public boolean applyOnAll(Unit inUnit) {
                        return true;
                    }
                });
            }
            catch (BizViolation inBizViolation) {
                LOGGER.error((Object)"Failed updating last known position", (Throwable)inBizViolation);
            }
        } else {
            this.doUpdateLastKnownPosition(inPosition, inMoveTime);
        }
    }

    protected void updateXpsLastKnownPosition(final LocPosition inPosition, final Date inMoveTime) {
        final Unit curUnit = this.getUfvUnit();
        if (curUnit.getUnitCombo() != null) {
            try {
                curUnit.applyUpdateOnUnitCombo(new AbstractUnitUpdate(){

                    @Override
                    public void apply(Unit inUnit) throws BizViolation {
                        boolean isAllFieldsClone = inUnit.getUnitRelatedUnit() != null && curUnit.equals(inUnit.getUnitRelatedUnit()) && curUnit.getUnitEqRole() == EqUnitRoleEnum.PRIMARY;
                        LocPosition clonePosition = UnitFacilityVisit.this.getClonePosition(inUnit, inPosition, isAllFieldsClone);
                        UnitFacilityVisit ufv = UnitFacilityVisit.this.getEqUfv(inUnit);
                        if (ufv != null) {
                            ufv.doUpdateLastKnownPosition(clonePosition, inMoveTime);
                        }
                    }

                    @Override
                    public boolean allowUpdate(Unit inUnit) {
                        return UnitFacilityVisit.this.allowPrimaryUnitUpdate(inUnit, curUnit, inPosition);
                    }
                });
            }
            catch (BizViolation inBizViolation) {
                LOGGER.error((Object)"Failed updating last known position", (Throwable)inBizViolation);
            }
        } else {
            this.doUpdateLastKnownPosition(inPosition, inMoveTime);
        }
    }

    @Nullable
    public String findSlotOnCarriage() {
        String slotOnCarriage;
        LocPosition lastKnownPos = this.getUfvLastKnownPosition();
        if (lastKnownPos != null && (slotOnCarriage = lastKnownPos.getPosSlotOnCarriage()) != null) {
            try {
                int i = Integer.parseInt(slotOnCarriage);
                Unit carriageUnit = this.getUfvUnit().getUnitCarriageUnit();
                if (carriageUnit == null && (EqUnitRoleEnum.ACCESSORY.equals((Object)this.getUfvUnit().getUnitEqRole()) || EqUnitRoleEnum.PAYLOAD.equals((Object)this.getUfvUnit().getUnitEqRole()))) {
                    carriageUnit = this.getUfvUnit().getUeInRole(EqUnitRoleEnum.CARRIAGE);
                }
                if (carriageUnit != null) {
                    EquipType equipType = carriageUnit.getPrimaryEq().getEqEquipType();
                    return equipType.getChsSlotLabel(i);
                }
            }
            catch (NumberFormatException numberFormatException) {
                // empty catch block
            }
            return slotOnCarriage;
        }
        return null;
    }

    private void doUpdateLastKnownPosition(LocPosition inPosition, Date inMoveTime) {
        LocPosition oldPosition = this.getUfvLastKnownPosition();
        String oldOrientation = oldPosition == null ? null : oldPosition.getPosOrientation();
        String newOrientation = inPosition.getPosOrientation();
        LOGGER.debug((Object)this.getUfvUnit().getUnitId());
        LOGGER.debug((Object)("old position is " + (Object)oldPosition + " " + oldOrientation));
        LOGGER.debug((Object)("new position is " + (Object)inPosition + " " + newOrientation));
        if (!inPosition.isSamePositionAndOrientation(oldPosition)) {
            boolean isFromXps;
            Set ueSet;
            AbstractBin oldBlockBin;
            boolean doNotUpdateDoorDirection;
            boolean bl = doNotUpdateDoorDirection = StringUtils.isEmpty((String)newOrientation) && StringUtils.isEmpty((String)oldOrientation);
            if (oldPosition != null && !doNotUpdateDoorDirection && (StringUtils.isEmpty((String)newOrientation) || inPosition.isSameOrientation(oldPosition)) && (oldBlockBin = this.getResidingYardBlock()) != null && inPosition.getPosBin() != null) {
                AbstractBin newBlockBin = null;
                if (LocTypeEnum.YARD.equals((Object)inPosition.getPosLocType()) && (newBlockBin = inPosition.getPosBin().findAncestorBinAtLevel(Long.valueOf(2L))) != null && newBlockBin.equals((Object)oldBlockBin)) {
                    doNotUpdateDoorDirection = true;
                    LOGGER.debug((Object)("sameOrientation is true for " + inPosition.toString()));
                }
            }
            if (inMoveTime == null) {
                inMoveTime = ArgoUtils.timeNow();
            }
            this.setUfvTimeOfLastMove(inMoveTime);
            this.setUfvLastKnownPosition(inPosition);
            Unit unit = this.getUfvUnit();
            if (UnitVisitStateEnum.ACTIVE.equals((Object)unit.getUnitVisitState()) && (ueSet = unit.getUnitUeSet()) != null) {
                for (Object anUeSet : ueSet) {
                    UnitEquipment ue = (UnitEquipment)anUeSet;
                    if (!ue.hasNotBeenDetached()) continue;
                    EquipmentState eqs = ue.ensureEquipmentState();
                    eqs.setEqsTimeLastMove(inMoveTime);
                    eqs.setEqsLastPosLocType(inPosition.getPosLocType());
                    eqs.setEqsLastPosName(inPosition.getPosName());
                    eqs.setEqsLastFacility(this.getUfvFacility());
                }
            }
            if (FreightKindEnum.BBK.equals((Object)unit.getUnitFreightKind())) {
                IInventoryCargoManager cargoMgr = (IInventoryCargoManager) Roastery.getBean((String)"inventoryCargoManager");
                try {
                    cargoMgr.moveUnitCallback(this);
                }
                catch (BizViolation inBizViolation) {
                    throw BizFailure.create((IPropertyKey) IFrameworkPropertyKeys.FAILURE__NAVIS, (Throwable)inBizViolation);
                }
            }
            if ((isFromXps = DataSourceEnum.XPS.equals((Object) ContextHelper.getThreadDataSource())) && inPosition.isProtectedYardPosition() && DoorDirectionEnum.UNKNOWN.getKey().equals(newOrientation)) {
                LOGGER.debug((Object)("Orientation is set to " + newOrientation + " from " + oldOrientation + "Setting EC Alarm to correct the Door Direction manually"));
//                try
                {
                    String oldPos = oldPosition == null ? null : oldPosition.toString();
                    String eventDescription = String.format("Unit Door Direction set to UNKNOWN, after Position changed from " + oldPos + " to " + inPosition.toString() + ", Please Correct it manually", new Object[0]);
                    EventType eventType = EventType.resolveIEventType((IEventType) EventEnum.UNKNOWN_DOOR_DIRECTION_ENCOUNTERED);
     //               InventoryServicesUtils.recordServiceEventForUnitException(this.getUfvUnit(), (IEntity)this, eventType, inPosition, eventDescription);
                }
//                catch (BizViolation inBizViolation)
//                {
//                    LOGGER.error((Object)"Exception recording Unit service event.");
//                }
            }
            if (!doNotUpdateDoorDirection && !isFromXps) {
                DoorDirectionEnum newDD = this.calculateDoorDirForUfvFromPosition(inPosition);
                if (newDD == null) {
                    LOGGER.warn((Object)("Not changing the UFV DoorDirection to NULL for " + this + " as part of updating last known position."));
                } else if (newDD == DoorDirectionEnum.ANY) {
                    LOGGER.warn((Object)("Not changing the UFV DoorDirection to ANY for " + this + " as part of updating last known position."));
                } else {
                    this.setUfvDoorDirection(newDD);
                }
            }
        }
    }

    @Nullable
    private DoorDirectionEnum calculateDoorDirForUfvFromPosition(LocPosition inPosition) {
        if (inPosition.getPosOrientation() == null) {
            LOGGER.debug((Object)"calculateDoorDirForUfvFromPosition() inPosition has null orientation.");
            return null;
        }
        DoorDirectionEnum ddEnumCalculated = DoorDirectionEnum.UNKNOWN;
        ArrayList<DoorDirectionEnum> cardinalDoorDirections = new ArrayList<DoorDirectionEnum>(Arrays.asList(new DoorDirectionEnum[]{DoorDirectionEnum.EAST, DoorDirectionEnum.WEST, DoorDirectionEnum.SOUTH, DoorDirectionEnum.NORTH}));
        ArrayList<DoorDirectionEnum> relativeDoorDirections = new ArrayList<DoorDirectionEnum>(Arrays.asList(new DoorDirectionEnum[]{DoorDirectionEnum.AFT, DoorDirectionEnum.FWD}));
        DoorDirectionEnum ddEnumOfInPos = DoorDirectionEnum.getEnum(inPosition.getPosOrientation());
        LOGGER.debug((Object)("ddEnumOfInPos is " + (Object)((Object)ddEnumOfInPos)));
        if (ddEnumOfInPos == null) {
            LOGGER.debug((Object)"inPosition has untranslatable orientation.");
            return DoorDirectionEnum.UNKNOWN;
        }
        if (relativeDoorDirections.contains((Object)ddEnumOfInPos)) {
            boolean stackDirectionFound = false;
            if (inPosition.isYardPosition() && inPosition.getPosBin() != null) {
                LOGGER.debug((Object)"position is yard position");
                CompassDirectionEnum yardBlockDirection = null;
                try {
                    AbstractBin blockBin = inPosition.getPosBin().findAncestorBinAtLevel(Long.valueOf(2L));
                    LOGGER.debug((Object)("blockBin is " + (Object)blockBin));
                    IArgoYardUtils yardUtil = (IArgoYardUtils)Roastery.getBean((String)"argoYardUtils");
                    yardBlockDirection = yardUtil.getStackBlockDirection(blockBin);
                    LOGGER.debug((Object)("yardBlockDirection is " + (Object)yardBlockDirection));
                }
                catch (Exception npe) {
                    LOGGER.info((Object)"Unable to find valid Bin Display for this Yard Position");
                }
                if (yardBlockDirection != null && !yardBlockDirection.equals((Object) CompassDirectionEnum.UNKNOWN)) {
                    stackDirectionFound = true;
                    if (ddEnumOfInPos.equals((Object) DoorDirectionEnum.FWD)) {
                        if (yardBlockDirection.equals((Object) CompassDirectionEnum.EAST)) {
                            ddEnumCalculated = DoorDirectionEnum.EAST;
                        } else if (yardBlockDirection.equals((Object) CompassDirectionEnum.WEST)) {
                            ddEnumCalculated = DoorDirectionEnum.WEST;
                        } else if (yardBlockDirection.equals((Object) CompassDirectionEnum.NORTH)) {
                            ddEnumCalculated = DoorDirectionEnum.NORTH;
                        } else if (yardBlockDirection.equals((Object) CompassDirectionEnum.SOUTH)) {
                            ddEnumCalculated = DoorDirectionEnum.SOUTH;
                        }
                    } else if (yardBlockDirection.equals((Object) CompassDirectionEnum.EAST)) {
                        ddEnumCalculated = DoorDirectionEnum.WEST;
                    } else if (yardBlockDirection.equals((Object) CompassDirectionEnum.WEST)) {
                        ddEnumCalculated = DoorDirectionEnum.EAST;
                    } else if (yardBlockDirection.equals((Object) CompassDirectionEnum.NORTH)) {
                        ddEnumCalculated = DoorDirectionEnum.SOUTH;
                    } else if (yardBlockDirection.equals((Object) CompassDirectionEnum.SOUTH)) {
                        ddEnumCalculated = DoorDirectionEnum.NORTH;
                    }
                }
            }
            if (!stackDirectionFound) {
                LOGGER.debug((Object)"stack direction not found");
                if (ddEnumOfInPos.equals((Object) DoorDirectionEnum.FWD)) {
                    ddEnumCalculated = DoorDirectionEnum.FWD;
                } else if (ddEnumOfInPos.equals((Object) DoorDirectionEnum.AFT)) {
                    ddEnumCalculated = DoorDirectionEnum.AFT;
                }
            }
        } else {
            ddEnumCalculated = cardinalDoorDirections.contains((Object)ddEnumOfInPos) ? ddEnumOfInPos : DoorDirectionEnum.UNKNOWN;
        }
        return ddEnumCalculated;
    }

    public boolean doorsFacingFwd(CompassDirectionEnum inBlockDirection) {
        DoorDirectionEnum ctrDoorDir = this.getUfvDoorDirection();
        if (DoorDirectionEnum.FWD.equals((Object)ctrDoorDir)) {
            return true;
        }
        if (CompassDirectionEnum.EAST.equals((Object)inBlockDirection) && DoorDirectionEnum.EAST.equals((Object)ctrDoorDir)) {
            return true;
        }
        if (CompassDirectionEnum.WEST.equals((Object)inBlockDirection) && DoorDirectionEnum.WEST.equals((Object)ctrDoorDir)) {
            return true;
        }
        if (CompassDirectionEnum.NORTH.equals((Object)inBlockDirection) && DoorDirectionEnum.NORTH.equals((Object)ctrDoorDir)) {
            return true;
        }
        return CompassDirectionEnum.SOUTH.equals((Object)inBlockDirection) && DoorDirectionEnum.SOUTH.equals((Object)ctrDoorDir);
    }

    public void updateSlotOnCarriage(final String inSlotOnCarriage) {
        final Unit curUnit = this.getUfvUnit();
        try {
            this.getUfvUnit().applyUpdateOnUnitCombo(new AbstractUnitUpdate(){

                @Override
                public void apply(Unit inUnit) throws BizViolation {
                    UnitFacilityVisit ufv = UnitFacilityVisit.this.getEqUfv(inUnit);
                    if (ufv != null) {
                        ufv.doUpdateSlotOnCarriage(inSlotOnCarriage);
                    }
                }

                @Override
                public boolean allowUpdate(Unit inUnit) {
                    EqUnitRoleEnum role = inUnit.getUnitEqRole();
                    if (EqUnitRoleEnum.PAYLOAD.equals((Object)role) || EqUnitRoleEnum.PRIMARY.equals((Object)role) || EqUnitRoleEnum.ACCESSORY.equals((Object)role)) {
                        if (UnitFacilityVisit.this.getUfvUnit().getUnitGkey().equals(inUnit.getUnitGkey())) {
                            return true;
                        }
                        if (inUnit.getUnitRelatedUnit() != null && curUnit.equals(inUnit.getUnitRelatedUnit())) {
                            return true;
                        }
                    }
                    return false;
                }
            });
        }
        catch (BizViolation inBizViolation) {
            LOGGER.error((Object)"Failed updating slot on carriage", (Throwable)inBizViolation);
        }
    }

    private void doUpdateSlotOnCarriage(String inSlotOnCarriage) throws BizViolation {
        this.getUfvLastKnownPosition().updatePosSlotOnCarriage(inSlotOnCarriage);
    }

    public void updateYardSlot(String inSlot) throws BizViolation {
        LocPosition pos = this.getUfvLastKnownPosition();
        if (pos.isYardPosition()) {
            EquipBasicLengthEnum len = this.getBasicLength();
            LocPosition newPos = LocPosition.createYardPosition((Yard)pos.resolveYard(), (String)inSlot, (String)pos.getPosOrientation(), (EquipBasicLengthEnum)len, (boolean)false);
            this.setUfvLastKnownPosition(newPos);
        }
    }

    public void updateDeliveryAppointmentTime(Date inDate) {
        this.setUfvDlvTimeAppntmnt(inDate);
    }

    public void updateDeliveryAppointmentNbr(Long inUfvGapptNbr) {
        this.setUfvGapptNbr(inUfvGapptNbr);
    }

    public void move(LocPosition inNewPos, MoveInfoBean inMoveInfo) throws BizViolation {
        this.move(inNewPos, inMoveInfo, false, true);
    }

    protected void move(LocPosition inNewPos, MoveInfoBean inMoveInfo, Boolean inAttachChassis) throws BizViolation {
        this.move(inNewPos, inMoveInfo, inAttachChassis, true);
    }

    protected void move(LocPosition inNewPos, MoveInfoBean inMoveInfo, Boolean inAttachChassis, Boolean inIsAppliedToCombo) throws BizViolation {
        if (!this.isActive()) {
            throw BizViolation.create((IPropertyKey) IInventoryPropertyKeys.MOVE__UFV_NOT_ACTIVE, null, (Object)this, (Object)inNewPos);
        }
        LocPosition currentPos = this.getUfvLastKnownPosition();
        boolean isUnitMovedInSameCarrier = false;
        if (currentPos != null && inNewPos != null && LocTypeEnum.RAILCAR.equals((Object)currentPos.getPosLocType()) && LocTypeEnum.RAILCAR.equals((Object)inNewPos.getPosLocType())) {
            isUnitMovedInSameCarrier = ((IArgoRailManager)Roastery.getBean((String)"argoRailManager")).isSameTrainVisit(currentPos, inNewPos);
        }
        if (currentPos.isCarrierPosition() && inNewPos.isCarrierPosition() && !currentPos.getPosLocId().equals(inNewPos.getPosLocId()) && !isUnitMovedInSameCarrier) {
            Yard moveYard = inMoveInfo != null ? Yard.loadByGkey((Serializable)inMoveInfo.getYardGkey()) : ContextHelper.getThreadYard();
            LocPosition phonyYardBlock = LocPosition.resolvePosition((Facility)this.getUfvFacility(), (LocTypeEnum) LocTypeEnum.YARD, (String)moveYard.getYrdId(), (String) LocPosition.getPhonyYardSlotId(), null, (EquipBasicLengthEnum)this.getBasicLength());
            this.move(currentPos, phonyYardBlock, inMoveInfo, inAttachChassis, inIsAppliedToCombo);
            this.move(phonyYardBlock, inNewPos, inMoveInfo, inAttachChassis, inIsAppliedToCombo);
        } else {
            this.move(currentPos, inNewPos, inMoveInfo, inAttachChassis, inIsAppliedToCombo);
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private void move(LocPosition inCurrentPos, LocPosition inNewPos, MoveInfoBean inMoveInfo, Boolean inAttachChassis, boolean inIsAppliedToCombo) throws BizViolation {
        if (inNewPos.isYardPosition()) {
            if (inCurrentPos.isCarrierPosition() || this.isPartialDischarge(inNewPos, inMoveInfo) || inCurrentPos.isLandsideTransferPoint() && !this.isDischargeRecorded().booleanValue() && this.getUfvTimeIn() == null) {
                this.dischargeFromCarrier(inNewPos, inMoveInfo, false);
            } else {
                if (!inCurrentPos.isYardPosition()) throw BizViolation.create((IPropertyKey) IInventoryPropertyKeys.MOVE__INVALID_MOVE_KIND, null, (Object)this, (Object)inCurrentPos, (Object)inNewPos);
                this.moveInYard(inCurrentPos, inNewPos, inMoveInfo, inIsAppliedToCombo);
            }
            this.getUfvUnit().updateDenormalizedFields();
            return;
        } else {
            if (!inNewPos.isCarrierPosition()) throw BizViolation.create((IPropertyKey) IInventoryPropertyKeys.MOVE__INVALID_MOVE_KIND, null, (Object)this, (Object)inCurrentPos, (Object)inNewPos);
            if (inCurrentPos.isYardPosition()) {
                this.loadToCarrier(inNewPos, inMoveInfo, inAttachChassis, false);
                return;
            } else {
                if (!inCurrentPos.isCarrierPosition()) throw BizViolation.create((IPropertyKey) IInventoryPropertyKeys.MOVE__INVALID_MOVE_KIND, null, (Object)this, (Object)inCurrentPos, (Object)inNewPos);
                this.shiftOnCarrier(inCurrentPos, inNewPos, inMoveInfo, false);
            }
        }
    }

    private boolean isPartialDischarge(@NotNull LocPosition inNewPos, @Nullable MoveInfoBean inMoveInfoBean) {
        MoveEvent lastMoveEvent = MoveEvent.getLastMoveEvent(this);
        if (lastMoveEvent == null || !WiMoveKindEnum.VeslDisch.equals((Object)lastMoveEvent.getMveMoveKind())) {
            return false;
        }
        boolean isPartialDsch = this.isCurrentMoveOfKind(WiMoveKindEnum.VeslDisch, inMoveInfoBean);
        return isPartialDsch && lastMoveEvent.getMveTimePut() == null && (inNewPos.isApron() || inNewPos.isQcTransferZonePosition());
    }

    private boolean isCurrentMoveOfKind(@NotNull WiMoveKindEnum inMoveKind, @Nullable MoveInfoBean inMoveInfoBean) {
        if (inMoveInfoBean != null) {
            return inMoveKind.equals((Object)inMoveInfoBean.getMoveKind());
        }
        List<WorkInstruction> wiList = this.getCurrentWiList(false);
        if (!wiList.isEmpty()) {
            return inMoveKind.equals((Object)wiList.get(0).getWiMoveKind());
        }
        return false;
    }

    private void dischargeFromCarrier(LocPosition inNewPos, MoveInfoBean inMoveInfo, Boolean inIsAppliedToCombo) throws BizViolation {
        boolean isFromXps = DataSourceEnum.XPS.equals((Object)ContextHelper.getThreadDataSource());
        LocPosition currentPos = this.getUfvLastKnownPosition();
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug((Object)("Discharging from Carrier, CurrentPosition: " + (Object)currentPos));
        }
        LogUtils.forceLogAtDebug((Logger)LOGGER, (Object)("Discharging from Carrier, NewPosition: " + (Object)inNewPos));
        CarrierVisit cv = currentPos.isLandsideTransferPoint() || this.isPartialDischarge(inNewPos, inMoveInfo) ? this.getUfvArrivePosition().resolveInboundCarrierVisit() : (this.isTransitStateBeyond(UfvTransitStateEnum.S40_YARD) ? currentPos.resolveOutboundCarrierVisit() : currentPos.resolveInboundCarrierVisit());
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug((Object)("Discharging from Carrier, Resolved CarrierVisit: " + (Object)cv));
        }
        if (cv == null) {
            throw BizFailure.create((String)("null carrier visit in Position: " + (Object)currentPos));
        }
        if (!this.isForFacility(cv.getCvFacility()) && !isFromXps) {
            throw BizViolation.create((IPropertyKey) IInventoryPropertyKeys.MOVE__DISCH_CV_NOT_FOR_FCY, null, (Object)this, (Object)cv);
        }
        Unit unit = this.getUfvUnit();
        IUnitManager manager = (IUnitManager)Roastery.getBean((String)"unitManager");
        if (!(cv.isAtFacility() || cv.hasDepartedFacility() || isFromXps || manager.isBreakBulkTruckMove(unit, currentPos).booleanValue())) {
            throw BizViolation.create((IPropertyKey) IInventoryPropertyKeys.MOVE__DISCH_CV_NOT_HERE, null, (Object)this, (Object)cv);
        }
        LocTypeEnum carrierMode = cv.getCvCarrierMode();
        if (FreightKindEnum.BBK.equals((Object)unit.getUnitFreightKind()) && LocTypeEnum.CONTAINER.equals((Object)currentPos.getPosLocType())) {
            this.registerMove(EventEnum.CARGO_STRIP, inNewPos, cv, inMoveInfo, inIsAppliedToCombo, false);
        } else if (FreightKindEnum.BBK.equals((Object)unit.getUnitFreightKind()) && LocTypeEnum.CONTAINER.equals((Object)inNewPos.getPosLocType())) {
            this.registerMove(EventEnum.CARGO_STUFF, inNewPos, cv, inMoveInfo, inIsAppliedToCombo, false);
        } else if (LocTypeEnum.VESSEL.equals((Object)carrierMode)) {
            this.registerMove(EventEnum.UNIT_DISCH, inNewPos, cv, inMoveInfo, inIsAppliedToCombo, false);
            LOGGER.warn((Object)("Recording event of type " + EventEnum.UNIT_DISCH.getId() + " on " + unit));
        } else if (LocTypeEnum.TRAIN.equals((Object)cv.getCvCarrierMode()) || LocTypeEnum.RAILCAR.equals((Object)cv.getCvCarrierMode())) {
            WorkInstruction wi;
            WorkInstruction currentWi = null;
            List<WorkInstruction> wiList = this.getCurrentWiList();
            if (!wiList.isEmpty() && WiMoveKindEnum.RailDisch.equals((Object)(wi = wiList.get(0)).getWiMoveKind())) {
                currentWi = wi;
            }
            if (currentWi != null && inMoveInfo != null && inMoveInfo.getCheIndexFetch() == 0L) {
                inMoveInfo.setCheIndexFetch(currentWi.getMvhsFetchCheIndex());
            }
            this.registerMove(EventEnum.UNIT_DERAMP, inNewPos, cv, inMoveInfo, inIsAppliedToCombo, false);
            DrayStatusEnum drayStatus = unit.getUnitDrayStatus();
            if (DrayStatusEnum.TRANSFER.equals((Object)drayStatus)) {
                unit.setUnitDrayStatus(null);
                LOGGER.warn((Object)("DischargeFromCarrier: drayStatus was cleared on Unit that re-entered: " + this));
            }
            this.adjustEdoTallyOnRailDischarge();
        } else if (LocTypeEnum.TRUCK.equals((Object)cv.getCvCarrierMode())) {
            if (Boolean.TRUE.equals(this.getUfvReturnToYard())) {
                this.registerMove(EventEnum.UNIT_DELIVER_REJECTED, inNewPos, cv, inMoveInfo, inIsAppliedToCombo, false);
                this.setUfvReturnToYard(false);
            } else {
                this.registerMove(EventEnum.UNIT_RECEIVE, inNewPos, cv, inMoveInfo, inIsAppliedToCombo, true);
            }
        } else {
            LOGGER.error((Object)("dischargeFromCarrier: unexpected carrierMode: " + (Object)carrierMode));
        }
        CarrierVisit obcvFromFutureFacility = unit.getUnitRouting().getRtgDeclaredCv();
        Facility futureFacility = obcvFromFutureFacility.getCvFacility();
        Facility currentFacility = inNewPos.resolveFacility();
        if (futureFacility != null && !futureFacility.equals((Object)currentFacility)) {
//            FacilityRelay facilityRelay = FacilityRelay.findFacilityRelay((Facility)currentFacility, (Facility)futureFacility);
//            if (facilityRelay == null) {
//                LOGGER.error((Object)("dischargeFromCarrier: no relay set up from " + (Object)currentFacility + " to " + (Object)futureFacility));
//            } else if (unit.getUfvForFacilityCompletedOnly(futureFacility) == null) {
//                Complex complex = currentFacility.getFcyComplex();
//                LocTypeEnum mode = facilityRelay.getFcyrelayCarrierModeDefault();
//                CarrierVisit obcvFromCurrentFacility = CarrierVisit.findOrCreateGenericCv((Complex)complex, (LocTypeEnum)mode);
//                UnitFacilityVisit nextUfv = unit.getUfvForFacilityLiveOnly(futureFacility);
//                if (nextUfv == null) {
//                    LocPosition futureIbPosition = LocPosition.createLocPosition((ILocation)obcvFromCurrentFacility, null, null);
//                    UnitFacilityVisit.createUnitFacilityVisit(unit, futureFacility, futureIbPosition, obcvFromFutureFacility);
//                } else if (!obcvFromFutureFacility.isGenericCv()) {
//                    if (nextUfv.getUfvActualObCv().isGenericCv()) {
//                        nextUfv.setUfvActualObCv(obcvFromFutureFacility);
//                    }
//                    if (nextUfv.getUfvIntendedObCv().isGenericCv()) {
//                        nextUfv.setUfvIntendedObCv(obcvFromFutureFacility);
//                    }
//                }
//                CarrierVisit intendedCv = this.getUfvIntendedObCv();
//                if (obcvFromFutureFacility.equals((Object)intendedCv)) {
//                    this.setUfvIntendedObCv(obcvFromCurrentFacility);
//                    this.setUfvActualObCv(obcvFromCurrentFacility);
//                }
//            }
        }
    }

    private void adjustEdoTallyOnRailDischarge() {
        String unitId = this.getUfvUnit().getUnitId();
        CarrierVisit ufvObCv = this.getUfvIntendedObCv();
        if (ufvObCv != null) {
            if (LocTypeEnum.TRAIN.equals((Object)ufvObCv.getLocType())) {
                EqBaseOrderItem eqBaseOrderItem;
                IArgoEquipmentOrderManager equipOrdMgr = (IArgoEquipmentOrderManager)Roastery.getBean((String)"equipmentOrderManager");
                UnitEquipment unitEquipment = this.getUfvUnit().getUnitPrimaryUe();
                if (unitEquipment != null && (eqBaseOrderItem = unitEquipment.getUeDepartureOrderItem()) != null) {
                    LOGGER.info((Object)("Decrementing EDO Tally on a Rail Discharge from Cached for " + unitId));
                    equipOrdMgr.decrementTally((Serializable)eqBaseOrderItem.getEqboiGkey());
                }
            } else {
                LOGGER.info((Object)("Unit " + unitId + " was discharged from Train/Rail as a result of cache update. Unit was not intended to depart by Train"));
            }
        }
    }

    protected Boolean isDischargeRecorded() {
        UnitFacilityVisit mveUfv;
        LocPosition currentPos = this.getUfvLastKnownPosition();
        CarrierVisit cv = this.getUfvArrivePosition().resolveInboundCarrierVisit();
        if (cv == null) {
            return Boolean.FALSE;
        }
        Unit unit = this.getUfvUnit();
        if (FreightKindEnum.BBK.equals((Object)unit.getUnitFreightKind())) {
            return Boolean.FALSE;
        }
        EventManager em = (EventManager)Roastery.getBean((String)"eventManager");
        LocTypeEnum carrierMode = cv.getCvCarrierMode();
        EventType resolvedEventType = null;
        if (LocTypeEnum.VESSEL.equals((Object)carrierMode)) {
            resolvedEventType = EventType.resolveIEventType((IEventType) EventEnum.UNIT_DISCH);
        } else if (LocTypeEnum.TRAIN.equals((Object)carrierMode) || LocTypeEnum.RAILCAR.equals((Object)carrierMode)) {
            resolvedEventType = EventType.resolveIEventType((IEventType) EventEnum.UNIT_DERAMP);
        } else if (LocTypeEnum.TRUCK.equals((Object)carrierMode)) {
            resolvedEventType = EventType.resolveIEventType((IEventType) EventEnum.UNIT_RECEIVE);
        } else {
            return Boolean.FALSE;
        }
        MoveEvent event = MoveEvent.getLastMoveEvent(this, resolvedEventType);
        if (event == null) {
            return Boolean.FALSE;
        }
        if (event instanceof MoveEvent && (mveUfv = event.getMveUfv()) != null && mveUfv.getUfvGkey().equals(this.getUfvGkey())) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public void doPartialDischarge() throws BizViolation {
        Yard yard = ContextHelper.getThreadYard();
        String posSlot = this.getFinalPlannedPosition() != null ? this.getFinalPlannedPosition().getPosSlot() : null;
        LocPosition yardPos = LocPosition.createYardPosition((Yard)yard, (String)posSlot, null, (EquipBasicLengthEnum)this.getBasicLength(), (boolean)false);
        this.doPartialDischarge(yardPos);
    }

    public void doPartialDischarge(LocPosition inNewPos) throws BizViolation {
        this.doPartialDischarge(inNewPos, null);
    }

    public void doPartialDischarge(LocPosition inNewPos, @Nullable WorkInstruction inWorkInstruction) throws BizViolation {
        CarrierVisit ibCv;
        if (!this.isDischargeableTransitState()) {
            throw BizViolation.create((IPropertyKey) IInventoryPropertyKeys.UNITS__WRONG_UNIT_STATE, null, (Object)this.getUfvUnit().getUnitId(), (Object)"discharge");
        }
        Yard yard = ContextHelper.getThreadYard();
        WorkInstruction currentWi = null;
        if (inWorkInstruction == null) {
            WorkInstruction wi;
            List<WorkInstruction> wiList = this.getCurrentWiList();
            if (!wiList.isEmpty() && (WiMoveKindEnum.VeslDisch.equals((Object)(wi = wiList.get(0)).getWiMoveKind()) || WiMoveKindEnum.RailDisch.equals((Object)wi.getWiMoveKind()))) {
                currentWi = wi;
            }
        } else {
            currentWi = inWorkInstruction;
        }
        Date timeNow = ArgoUtils.timeNow();
        MoveInfoBean moveInfo = currentWi != null ? MoveInfoBean.createMoveInfoBean(currentWi, inNewPos, timeNow) : new MoveInfoBean(yard.getYrdGkey());
        CarrierVisit carrierVisit = ibCv = currentWi == null || !WiMoveKindEnum.VeslDisch.equals((Object)currentWi.getWiMoveKind()) ? this.getUfvActualIbCv() : InventoryControlUtils.getEffectiveInboundCarrierVisitForDischarge(this);
        if (ibCv == null) {
            throw BizFailure.create((String)("Inbound carrier visit could not be determined for unit: " + this));
        }
        EventEnum evtKind = EventEnum.UNIT_DISCH;
        if (LocTypeEnum.VESSEL.equals((Object)ibCv.getCvCarrierMode())) {
            moveInfo.setMoveKind(WiMoveKindEnum.VeslDisch);
        } else if (LocTypeEnum.TRAIN.equals((Object)ibCv.getCvCarrierMode())) {
            moveInfo.setMoveKind(WiMoveKindEnum.RailDisch);
            evtKind = EventEnum.UNIT_DERAMP;
        } else {
            throw BizViolation.create((IPropertyKey) IInventoryPropertyKeys.MOVE__INVALID_CARRIER_MODE, null, (Object)evtKind, (Object)ibCv.getCvCarrierMode());
        }
        moveInfo.setPartialDischarge(true);
        LocPosition lastKnownPosition = this.getUfvLastKnownPosition();
        this.advanceTransitState(UfvTransitStateEnum.S30_ECIN, moveInfo.getTimeDispatch());
        MoveEvent.recordMoveEvent(this, lastKnownPosition, inNewPos, ibCv, moveInfo, evtKind);
        HibernateApi.getInstance().flush();
    }

    public void updateUfvTransitState(boolean inUnitReset) throws BizViolation {
        if (this.isTransitState(UfvTransitStateEnum.S30_ECIN) && inUnitReset) {
            Date timeNow = ArgoUtils.timeNow();
            this.innerSetTransitStateAndTimestamps(UfvTransitStateEnum.S20_INBOUND, timeNow);
            HibernateApi.getInstance().flush();
        } else {
            if (this.isTransitStateBeyond(UfvTransitStateEnum.S20_INBOUND) && !UfvTransitStateEnum.S60_LOADED.equals((Object)this.getUfvTransitState())) {
                throw BizViolation.create((IPropertyKey) IInventoryPropertyKeys.UNITS__WRONG_UNIT_STATE, null, (Object)this.getUfvUnit().getUnitId(), (Object)"discharge");
            }
            Yard yard = ContextHelper.getThreadYard();
            MoveInfoBean moveInfo = new MoveInfoBean(yard.getYrdGkey());
            Date timeNow = ArgoUtils.timeNow();
            moveInfo.setTimeDischarge(timeNow);
            this.advanceTransitState(UfvTransitStateEnum.S30_ECIN, timeNow);
            HibernateApi.getInstance().flush();
        }
    }

    private void loadToCarrier(LocPosition inNewPos, MoveInfoBean inMoveInfo, Boolean inAttachChassis, Boolean inIsAppliedToCombo) throws BizViolation {
        final CarrierVisit cv = this.getCarrierVisit(inNewPos);
        LocTypeEnum carrierMode = cv.getCvCarrierMode();
        if (LocTypeEnum.VESSEL.equals((Object)carrierMode)) {
            if (!Boolean.TRUE.equals(inAttachChassis)) {
                if (!DataSourceEnum.USER_LCL.equals((Object)ContextHelper.getThreadDataSource())) {
                    LOGGER.warn((Object)("UFV: inAttachChassis is " + inAttachChassis + " dismounting container: " + this.getUfvUnit().getUnitId()));
                    LOGGER.warn((Object)"Dismounting unit in loadToCarrier!");
                    Serializable yardKey = ContextHelper.getThreadYardKey();
                    if (yardKey == null) {
                        yardKey = inMoveInfo.getYardGkey();
                    }
                    UserContext systemUser = ContextHelper.getSystemUserContextForScope((IScopeEnum)ScopeEnum.YARD, (Serializable)yardKey);
                    TransactionParms tp = TransactionParms.getBoundParms();
                    tp.setUserContext(systemUser);
                    LOGGER.warn((Object)"UFV...switching to system user before dismount so it is not treated as a ricochet update");
                } else {
                    LOGGER.warn((Object)"It is bypassing it as it not coming via XPS so applying user as current logged in user");
                    UserContext currentUC = TransactionParms.getBoundParms().getUserContext();
                    TransactionParms.getBoundParms().setUserContext(currentUC);
                }
                this.getUfvUnit().dismount();
            }
            this.getUfvUnit().detachAccessoriesOnPayload("Dismount");
            if (this.stripAndLoadCargo()) {
                IInventoryCargoManager cargoMgr = (IInventoryCargoManager)Roastery.getBean((String)"inventoryCargoManager");
                cargoMgr.stripUnitAndLoadLots(this, inNewPos);
            } else {
                this.registerMove(EventEnum.UNIT_LOAD, inNewPos, cv, inMoveInfo, inIsAppliedToCombo, inAttachChassis);
            }
        } else if (LocTypeEnum.TRAIN.equals((Object)cv.getCvCarrierMode()) || LocTypeEnum.RAILCAR.equals((Object)cv.getCvCarrierMode())) {
            if (!Boolean.TRUE.equals(inAttachChassis)) {
                if (!DataSourceEnum.USER_LCL.equals((Object)ContextHelper.getThreadDataSource())) {
                    LOGGER.warn((Object)("UFV: inAttachChassis is " + inAttachChassis + " dismounting container: " + this.getUfvUnit().getUnitId()));
                    LOGGER.warn((Object)"Dismounting unit in loadToCarrier!");
                    Serializable yardKey = ContextHelper.getThreadYardKey();
                    if (yardKey == null) {
                        yardKey = inMoveInfo.getYardGkey();
                    }
                    UserContext systemUser = ContextHelper.getSystemUserContextForScope((IScopeEnum)ScopeEnum.YARD, (Serializable)yardKey);
                    TransactionParms tp = TransactionParms.getBoundParms();
                    tp.setUserContext(systemUser);
                    LOGGER.warn((Object)"UFV...switching to system user before dismount so it is not treated as a ricochet update");
                } else {
                    LOGGER.warn((Object)"It is bypassing it as it not coming via XPS so applying user as current logged in user");
                    UserContext currentUC = TransactionParms.getBoundParms().getUserContext();
                    TransactionParms.getBoundParms().setUserContext(currentUC);
                }
                this.getUfvUnit().dismount();
            }
            this.registerMove(EventEnum.UNIT_RAMP, inNewPos, cv, inMoveInfo, inIsAppliedToCombo, inAttachChassis);
            this.adjustEdoTallyOnRailLoad();
            DrayStatusEnum drayStatus = this.getUfvUnit().getUnitDrayStatus();
            if (drayStatus != null && drayStatus.equals((Object) DrayStatusEnum.RETURN)) {
                this.getUfvUnit().adjustOrderTallyForReturnToShipper();
            }
        } else if (LocTypeEnum.TRUCK.equals((Object)cv.getCvCarrierMode())) {
            this.registerMove(EventEnum.UNIT_DELIVER, inNewPos, cv, inMoveInfo, inIsAppliedToCombo, true);
        } else {
            LOGGER.error((Object)("loadToCarrier: unexpected carrierMode: " + (Object)carrierMode));
        }
        this.getUfvUnit().applyUpdateOnUnitCombo(new AbstractUnitUpdate(){

            @Override
            public void apply(Unit inUnit) throws BizViolation {
                UnitFacilityVisit ufv = UnitFacilityVisit.this.getEqUfv(inUnit);
                if (ufv != null) {
                    ufv.setUfvActualObCv(cv);
                }
            }

            @Override
            public boolean applyOnAll(Unit inUnit) {
                if (LocTypeEnum.TRUCK.equals((Object)cv.getCvCarrierMode()) && EqUnitRoleEnum.CARRIAGE.equals((Object)inUnit.getUnitEqRole()) && inUnit.isChassisBundle()) {
                    return true;
                }
                return super.applyOnAll(inUnit);
            }
        });
    }

    private CarrierVisit getCarrierVisit(LocPosition inNewPos) throws BizViolation {
        boolean isFromXps = DataSourceEnum.XPS.equals((Object)ContextHelper.getThreadDataSource());
        CarrierVisit cv = inNewPos.resolveOutboundCarrierVisit();
        if (cv == null) {
            throw BizFailure.create((String)("null carrier visit in Position: " + (Object)inNewPos));
        }
        if (!this.isForFacility(cv.getCvFacility()) && !isFromXps) {
            throw BizViolation.create((IPropertyKey) IInventoryPropertyKeys.MOVE__LOAD_CV_NOT_FOR_FCY, null, (Object)this, (Object)cv);
        }
        if (!cv.isAtFacility() && !isFromXps) {
            throw BizViolation.create((IPropertyKey) IInventoryPropertyKeys.MOVE__LOAD_CV_NOT_HERE, null, (Object)this, (Object)cv);
        }
        return cv;
    }

    private boolean stripAndLoadCargo() {
        EquipIsoGroupEnum isoGroup;
        IInventoryCargoManager cargoMgr;
        boolean stripAndStuff = false;
        if (!FreightKindEnum.BBK.equals((Object)this.getUfvUnit().getUnitFreightKind()) && (cargoMgr = (IInventoryCargoManager)Roastery.getBean((String)"inventoryCargoManager")).hasUnitAttachedCargoLots(this.getUfvUnit()) && this.getUfvUnit().getUnitPrimaryUe() != null && EquipIsoGroupEnum.CR.equals((Object)(isoGroup = this.getUfvUnit().getUnitPrimaryUe().getUeEquipment().getEqEquipType().getEqtypIsoGroup()))) {
            stripAndStuff = true;
        }
        return stripAndStuff;
    }

    private void adjustEdoTallyOnRailLoad() {
        String unitId = this.getUfvUnit().getUnitId();
        CarrierVisit ufvObCv = this.getUfvIntendedObCv();
        if (ufvObCv != null) {
            if (LocTypeEnum.TRAIN.equals((Object)ufvObCv.getLocType())) {
                EqBaseOrderItem eqBaseOrderItem;
                IArgoEquipmentOrderManager equipOrdMgr = (IArgoEquipmentOrderManager)Roastery.getBean((String)"equipmentOrderManager");
                UnitEquipment unitEquipment = this.getUfvUnit().getUnitPrimaryUe();
                if (unitEquipment != null && (eqBaseOrderItem = unitEquipment.getUeDepartureOrderItem()) != null) {
                    LOGGER.info((Object)("Adjusting EDO Tally on a Rail load from Cached for " + unitId));
                    if (UnitCategoryEnum.IMPORT.equals((Object)this.getUfvUnit().getUnitCategory())) {
                        equipOrdMgr.incrementTally((Serializable)eqBaseOrderItem.getEqboiGkey());
                    } else {
                        equipOrdMgr.adjustTally((Serializable)unitEquipment.getUeGkey());
                    }
                }
            } else {
                LOGGER.info((Object)("Unit " + unitId + " was loaded to Train/Rail as a result of cache update. Unit was not intended to depart by Train"));
            }
        } else {
            LOGGER.info((Object)("Failed to find Intended OB Cv for " + unitId));
        }
    }

    private boolean isWheeledOrTransferZone(LocPosition inLocPosition) {
        return inLocPosition.isTransferZone() || inLocPosition.isWheeled();
    }

    private void moveInYard(LocPosition inCurrentPos, LocPosition inNewPos, MoveInfoBean inMoveInfo, Boolean inIsAppliedToCombo) throws BizViolation {
        Yard inYard = inNewPos.resolveYard();
        if (inYard == null) {
            throw BizFailure.create((String)("inNewPos <" + (Object)inNewPos + "> is not a yard position"));
        }
        if (!this.isForFacility(inNewPos.resolveFacility())) {
            throw BizFailure.create((String)("move: yard " + (Object)inYard + " not part of facility " + this));
        }
        EventEnum evtToRecord = EventEnum.UNIT_YARD_MOVE;
        if (inMoveInfo != null && WiMoveKindEnum.YardShift.equals((Object)inMoveInfo.getMoveKind())) {
            evtToRecord = EventEnum.UNIT_YARD_SHIFT;
        }
        if (inMoveInfo != null && this.isPlanNotInYardButDroppedInYard(inNewPos)) {
            inMoveInfo.setMoveKind(WiMoveKindEnum.YardMove);
        }
        if (inCurrentPos != null && inNewPos != null && this.isWheeledOrTransferZone(inCurrentPos) && this.isWheeledOrTransferZone(inNewPos) && ArgoUtils.isValidPosSlotOnCarriage((String)inCurrentPos.getPosSlotOnCarriage()) && !ArgoUtils.isValidPosSlotOnCarriage((String)inNewPos.getPosSlotOnCarriage())) {
            LOGGER.debug((Object)"Slot on carriage is updated from prev pos as move identified as Long Shuffle, moving from one TZ to another TZ");
            inNewPos.updatePosSlotOnCarriage(inCurrentPos.getPosSlotOnCarriage());
        }
        if (!inCurrentPos.isSamePosition(inNewPos)) {
            this.registerMove(evtToRecord, inNewPos, null, inMoveInfo, inIsAppliedToCombo, true);
        } else if (LOGGER.isDebugEnabled()) {
            LOGGER.debug((Object)("inCurrentPos <" + (Object)inCurrentPos + "> " + "and inNewPos <" + (Object)inNewPos + "> are same"));
        }
    }

    private void shiftOnCarrier(LocPosition inCurrentPos, LocPosition inNewPos, MoveInfoBean inMoveInfo, Boolean inIsAppliedToCombo) throws BizViolation {
        CarrierVisit cv = this.getCarrierVisit(inNewPos);
        if (inCurrentPos.isTruckPosition() && inNewPos.isTruckPosition() && inCurrentPos.getPosLocGkey().equals(inNewPos.getPosLocGkey())) {
            this.updateLastKnownPosition(inNewPos, null);
            LOGGER.info((Object)"Skip recording UNIT_SHIFT_ON_CARRIER event as the move is within the same truck");
            return;
        }
        if (inCurrentPos.getPosSlot() != null && inNewPos.getPosSlot() != null && inCurrentPos.isCarrierPosition() && inNewPos.isCarrierPosition() && inCurrentPos.getPosLocGkey().equals(inNewPos.getPosLocGkey()) && inCurrentPos.getPosSlot().equals(inNewPos.getPosSlot())) {
            if (StringUtils.isNotEmpty((String)inCurrentPos.getPosSlotOnCarriage()) && StringUtils.isNotEmpty((String)inNewPos.getPosSlotOnCarriage()) && inCurrentPos.getPosSlotOnCarriage() != inNewPos.getPosSlotOnCarriage() || StringUtils.isEmpty((String)inCurrentPos.getPosSlotOnCarriage()) && StringUtils.isNotEmpty((String)inNewPos.getPosSlotOnCarriage())) {
                LOGGER.debug((Object)"Only slot on carriage is updated for this and container is in transit, Skip recording UNIT_SHIFT_ON_CARRIER event");
                this.updateLastKnownPosition(inNewPos, null);
            }
            return;
        }
        this.registerMove(EventEnum.UNIT_SHIFT_ON_CARRIER, inNewPos, cv, inMoveInfo, inIsAppliedToCombo, false);
        boolean isUnitMovedInSameCarrier = false;
        if (LocTypeEnum.RAILCAR.equals((Object)inCurrentPos.getPosLocType()) && LocTypeEnum.RAILCAR.equals((Object)inNewPos.getPosLocType()) && inCurrentPos.getPosLocGkey().equals(inNewPos.getPosLocGkey()) && (isUnitMovedInSameCarrier = ((IArgoRailManager)Roastery.getBean((String)"argoRailManager")).isSameTrainVisit(inCurrentPos, inNewPos))) {
            LOGGER.info((Object)"Skip updating the UFV Actual OB CV as the move is in the same railcar of the train");
            return;
        }
        this.setUfvActualObCv(cv);
    }

    private void registerMove(final EventEnum inEventEnum, final LocPosition inNewPos, final CarrierVisit inCarrierVisit, final MoveInfoBean inMoveInfo, final Boolean inIsAppliedToCombo, final Boolean inIsAttachedCarriage) throws BizViolation {
        final Unit curUnit = this.getUfvUnit();
        if (curUnit.getUnitCombo() != null) {
            curUnit.applyUpdateOnUnitCombo(new AbstractUnitUpdate(){

                @Override
                public void apply(Unit inUnit) throws BizViolation {
                    UnitFacilityVisit ufv = UnitFacilityVisit.this.getEqUfv(inUnit);
                    if (ufv != null && (inIsAttachedCarriage.booleanValue() || UnitFacilityVisit.this.checkPositionUpdateOk(inNewPos, inUnit))) {
                        LocPosition clonePosition = UnitFacilityVisit.this.getClonePosition(inUnit, inNewPos, true);
                        ufv.doRegisterMove(inEventEnum, clonePosition, inCarrierVisit, inMoveInfo);
                    }
                }

                @Override
                public boolean allowUpdate(Unit inUnit) {
                    if (inIsAppliedToCombo.booleanValue()) {
                        return true;
                    }
                    if (inIsAttachedCarriage.booleanValue()) {
                        return UnitFacilityVisit.this.allowPrimaryAndCarriageUnitUpdate(inUnit, curUnit, inNewPos);
                    }
                    return UnitFacilityVisit.this.allowPrimaryUnitUpdate(inUnit, curUnit, inNewPos);
                }

                @Override
                public boolean applyOnAll(Unit inUnit) {
                    if (EqUnitRoleEnum.CARRIAGE.equals((Object)inUnit.getUnitEqRole()) && inUnit.isChassisBundle()) {
                        return true;
                    }
                    return super.applyOnAll(inUnit);
                }
            });
        } else {
            this.doRegisterMove(inEventEnum, inNewPos, inCarrierVisit, inMoveInfo);
        }
    }

    private void doRegisterMove(EventEnum inEventEnum, LocPosition inNewPos, CarrierVisit inCarrierVisit, MoveInfoBean inMoveInfo) throws BizViolation {
        Unit unit = this.getUfvUnit();
        LocPosition oldPos = this.getUfvLastKnownPosition();
        Date now = ArgoUtils.timeNow();
        this.doUpdateLastKnownPosition(inNewPos, now);
        boolean toYard = inNewPos.isYardPosition() && !inNewPos.isLandsideTransferPoint();
        boolean fmYard = oldPos.isYardPosition() && !oldPos.isLandsideTransferPoint() && !oldPos.isQcPlatformPosition();
        boolean enteringYard = toYard && !fmYard;
        boolean leavingYard = !inNewPos.isYardPosition() && oldPos.isYardPosition();
        boolean isIft = UnitFacilityVisit.isIft(inNewPos, oldPos);
        boolean isUnitShiftOnSameVessel = false;
        if (EventEnum.UNIT_SHIFT_ON_CARRIER.equals((Object)inEventEnum) && RestowTypeEnum.RESTOW.equals((Object)this.getUfvRestowType()) && UfvTransitStateEnum.S30_ECIN.equals((Object)this.getUfvTransitState()) && inNewPos.isVesselPosition() && oldPos.isVesselPosition()) {
            isUnitShiftOnSameVessel = true;
        }
        if (enteringYard) {
            UfvTransitStateEnum yardState = UfvTransitStateEnum.S40_YARD;
            if (this.getUfvTimeIn() == null) {
                if (oldPos.isRailPosition() && inNewPos.isRampBin()) {
                    this.innerSetTimestamps(UfvTransitStateEnum.S30_ECIN, now);
                    this.innerSetTransitStateAndTimestamps(yardState, now);
                } else {
                    this.innerSetTransitStateAndTimestamps(yardState, now);
                }
            } else {
                this.innerSetTransitState(yardState, false);
            }
        } else if (leavingYard || isUnitShiftOnSameVessel) {
            this.innerSetTransitStateAndTimestamps(UfvTransitStateEnum.S60_LOADED, now);
        }
        if (inMoveInfo == null) {
            WorkInstruction wi = this.getNextWorkInstruction();
            inMoveInfo = wi != null ? MoveInfoBean.createMoveInfoBean(wi, inNewPos, ArgoUtils.timeNow()) : MoveInfoBean.createDefaultMoveInfoBean(MoveEvent.eventEnum2MoveKind(inEventEnum), ArgoUtils.timeNow());
        } else if (inMoveInfo.getMoveKind() == null) {
            inMoveInfo.setMoveKind(MoveEvent.eventEnum2MoveKind(inEventEnum));
        }
        EventEnum eventToRecord = inEventEnum;
        if (unit.getUnitEqRole() != null && unit.getUnitEqRole() != EqUnitRoleEnum.PRIMARY) {
            eventToRecord = (EventEnum)UnitEventMap.getRoleSpecificEvent((IEventType)inEventEnum, unit.getUnitEqRole());
        }
        MoveEvent event = MoveEvent.recordMoveEvent(this, oldPos, inNewPos, inCarrierVisit, inMoveInfo, eventToRecord);
        if (enteringYard) {
            unit.toggleRoadAvailability();
        }
        if (enteringYard || isIft) {
            UnitEventExtractManager.createStorageEvent(event);
            UnitEventExtractManager.createLineStorageEvent(event);
        } else if (leavingYard) {
            UnitEventExtractManager.updateStorageEvent(event);
            UnitEventExtractManager.updateLineStorageEvent(event);
        }
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info((Object)("registerMove, kind: " + (Object)inEventEnum + ", " + LocPosition.formPosChangeString((String)this.getUfvUnit().getUnitId(), (LocPosition)oldPos, (LocPosition)inNewPos)));
        }
    }

    @Nullable
    public WorkInstruction getNextWorkInstruction() {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"WorkInstruction").addDqPredicate(PredicateFactory.eq((IMetafieldId) UnitField.WI_UFV_GKEY, (Object)this.getUfvGkey())).addDqPredicate(PredicateFactory.not((IPredicate)PredicateFactory.in((IMetafieldId)IMovesField.WI_MOVE_STAGE, Arrays.asList(new WiMoveStageEnum[]{WiMoveStageEnum.PUT_COMPLETE, WiMoveStageEnum.COMPLETE})))).addDqOrdering(Ordering.asc((IMetafieldId)IMovesField.WI_MOVE_NUMBER)).addDqOrdering(Ordering.asc((IMetafieldId)IMovesField.WI_GKEY));
        dq.setMaxResults(1);
        List entities = HibernateApi.getInstance().findEntitiesByDomainQuery(dq);
        if (entities == null || entities.isEmpty()) {
            return null;
        }
        return (WorkInstruction)entities.get(0);
    }

    @Nullable
    public WorkInstruction getLastWorkInstruction() {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"WorkInstruction").addDqPredicate(PredicateFactory.eq((IMetafieldId) UnitField.WI_UFV_GKEY, (Object)this.getUfvGkey())).addDqPredicate(PredicateFactory.not((IPredicate)PredicateFactory.in((IMetafieldId)IMovesField.WI_MOVE_STAGE, Arrays.asList(new WiMoveStageEnum[]{WiMoveStageEnum.PUT_COMPLETE, WiMoveStageEnum.COMPLETE})))).addDqOrdering(Ordering.desc((IMetafieldId)IMovesField.WI_MOVE_NUMBER));
        dq.setMaxResults(1);
        List entities = HibernateApi.getInstance().findEntitiesByDomainQuery(dq);
        if (entities == null || entities.isEmpty()) {
            return null;
        }
        return (WorkInstruction)entities.get(0);
    }

    public WorkInstruction getCompletedWorkInstruction() {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"WorkInstruction").addDqPredicate(PredicateFactory.eq((IMetafieldId) UnitField.WI_UFV_GKEY, (Object)this.getUfvGkey())).addDqPredicate(PredicateFactory.in((IMetafieldId)IMovesField.WI_MOVE_STAGE, Arrays.asList(new WiMoveStageEnum[]{WiMoveStageEnum.PUT_COMPLETE, WiMoveStageEnum.COMPLETE}))).addDqOrdering(Ordering.desc((IMetafieldId)IMovesField.WI_MOVE_NUMBER));
        dq.setMaxResults(1);
        List entities = HibernateApi.getInstance().findEntitiesByDomainQuery(dq);
        if (entities == null || entities.isEmpty()) {
            return null;
        }
        return (WorkInstruction)entities.get(0);
    }

    public void incrementMoveCount() {
        Long ufvMoveCount = this.getUfvMoveCount();
        if (ufvMoveCount == null) {
            ufvMoveCount = 0L;
        }
        ufvMoveCount = ufvMoveCount + 1L;
        this.setUfvMoveCount(ufvMoveCount);
    }

    public void decrementMoveCount() {
        Long ufvMoveCount = this.getUfvMoveCount();
        if (ufvMoveCount == null) {
            ufvMoveCount = 0L;
        } else if (ufvMoveCount != 0L) {
            ufvMoveCount = ufvMoveCount - 1L;
        }
        this.setUfvMoveCount(ufvMoveCount);
    }

    private static boolean isIft(@NotNull LocPosition inNewPos, @NotNull LocPosition inOldPos) {
        boolean retValue = Boolean.FALSE;
        if (LocTypeEnum.YARD.equals((Object)inNewPos.getPosLocType()) && LocTypeEnum.YARD.equals((Object)inOldPos.getPosLocType())) {
            retValue = !inNewPos.getPosLocId().equals(inOldPos.getPosLocId());
        }
        return retValue;
    }

    public void moveOrCorrectPosition(LocPosition inNewPos, MoveInfoBean inMoveInfo) throws BizViolation {
        this.moveOrCorrectPosition(inNewPos, inMoveInfo, true);
    }

    public void moveOrCorrectPosition(LocPosition inNewPos, MoveInfoBean inMoveInfo, Boolean inIsAppliedToCombo) throws BizViolation {
        LocPosition currentPos = this.getUfvLastKnownPosition();
        if ((currentPos.isCarrierPosition() || currentPos.isLandsideTransferPoint() || currentPos.isQcPlatformPosition()) && inNewPos.isYardPosition() && !inNewPos.isLandsideTransferPoint()) {
            this.move(inNewPos, inMoveInfo, false, inIsAppliedToCombo);
        } else if (currentPos.isYardPosition() && inNewPos.isCarrierPosition()) {
            this.move(inNewPos, inMoveInfo, false, inIsAppliedToCombo);
        } else if (currentPos.isCarrierPosition() && inNewPos.isCarrierPosition() && !currentPos.getPosLocType().equals((Object)inNewPos.getPosLocType()) && currentPos.getPosLocType().equals((Object) LocTypeEnum.VESSEL) && inNewPos.getPosLocType().equals((Object) LocTypeEnum.RAILCAR)) {
            this.move(inNewPos, inMoveInfo, false, inIsAppliedToCombo);
        } else {
            this.correctPosition(inNewPos, true);
        }
    }

    public void correctPosition(LocPosition inNewPos, boolean inDisallowLocationChange) throws BizViolation {
        DataSourceEnum xpsDataSource;
        UnitEquipment carriageUe;
        LocTypeEnum newPosLocType;
        boolean isLocationChanging;
        Unit unit = this.getUfvUnit();
        LocPosition oldPos = this.getUfvLastKnownPosition();
        if (oldPos.equals((Object)inNewPos)) {
            return;
        }
        LocTypeEnum oldPosLocType = oldPos.getPosLocType();
        boolean bl = isLocationChanging = !ObjectUtils.equals((Object)oldPosLocType, (Object)(newPosLocType = inNewPos.getPosLocType())) || !ObjectUtils.equals((Object)oldPos.getPosLocGkey(), (Object)inNewPos.getPosLocGkey());
        if (isLocationChanging) {
            if (UnitFacilityVisit.isValidLocationChange(oldPosLocType, newPosLocType).booleanValue()) {
                isLocationChanging = false;
            }
            if (inNewPos.isTruckPosition()) {
                isLocationChanging = false;
            }
            if (oldPos.isTruckPosition() && inNewPos.isLandsideTransferPoint()) {
                isLocationChanging = false;
            }
        }
        if (inDisallowLocationChange && isLocationChanging) {
            String info = LocPosition.formPosChangeString((String)unit.getUnitId(), (LocPosition)oldPos, (LocPosition)inNewPos);
            throw BizViolation.create((IPropertyKey) IInventoryPropertyKeys.UNITS_ILLEGAL_LOCATION_CHANGE, null, (Object)info);
        }
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info((Object)("correctPosition: " + LocPosition.formPosChangeString((String)this.getUfvUnit().getUnitId(), (LocPosition)oldPos, (LocPosition)inNewPos)));
        }
        if (oldPos.isWheeled() && inNewPos.isGrounded() && unit.getUnitCarriageUe() != null && (carriageUe = unit.getUnitCarriageUe()).hasNotBeenDetached()) {
            unit.dismount();
        }
        if (DataSourceEnum.XPS.equals((Object)(xpsDataSource = ContextHelper.getThreadDataSource()))) {
            this.updateXpsLastKnownPosition(inNewPos, null);
        } else {
            this.updateLastKnownPosition(inNewPos, null);
        }
        CarrierVisit newPosCarrierVisit = inNewPos.resolveInboundCarrierVisit();
        if (unit.getInboundCv() != null && newPosCarrierVisit != null && unit.getInboundCv().equals((Object)newPosCarrierVisit)) {
            this.setUfvArrivePosition(inNewPos);
        }
        if (DataSourceEnum.XPS.equals((Object)xpsDataSource)) {
            this.recordPosCorrectionEvent(oldPos, inNewPos);
        } else {
            this.recordPosCorrectionEventOnCombo(oldPos);
        }
    }

    protected void recordPosCorrectionEventOnCombo(final LocPosition inOldPos) {
        final FieldChanges fcs = new FieldChanges();
        try {
            this.getUfvUnit().applyUpdateOnUnitCombo(new AbstractUnitUpdate(){

                @Override
                public void apply(Unit inUnit) throws BizViolation {
                    LocPosition newPos = inUnit.findCurrentPosition();
                    boolean corePositionChanged = inOldPos.isCorePositionChanged(newPos);
                    if (!inOldPos.equals((Object)newPos)) {
                        if (corePositionChanged) {
                            fcs.setFieldChange(new FieldChange(IInventoryField.POS_NAME, (Object)inOldPos.getPosName(), (Object)newPos.getPosName()));
                        }
                        fcs.setFieldChange(new FieldChange(UnitField.UFV_POS_ORIENTATION, (Object)inOldPos.getPosOrientation(), (Object)newPos.getPosOrientation()));
                        inUnit.recordUnitEvent((IEventType) EventEnum.UNIT_POSITION_CORRECTION, fcs, UnitEquipment.getMessage(IInventoryPropertyKeys.UNIT_POS_CORRECTION, null));
                    }
                }
            });
        }
        catch (BizViolation inBizViolation) {
            LOGGER.error((Object)"Failed updating last known position", (Throwable)inBizViolation);
        }
    }

    protected void recordPosCorrectionEvent(final LocPosition inOldPos, final LocPosition inNewPos) {
        final Unit curUnit = this.getUfvUnit();
        final FieldChanges fcs = new FieldChanges();
        try {
            this.getUfvUnit().applyUpdateOnUnitCombo(new AbstractUnitUpdate(){

                @Override
                public void apply(Unit inUnit) throws BizViolation {
                    LocPosition newPos = inUnit.findCurrentPosition();
                    boolean corePositionChanged = inOldPos.isCorePositionChanged(newPos);
                    if (!inOldPos.equals((Object)newPos)) {
                        if (corePositionChanged) {
                            fcs.setFieldChange(new FieldChange(IInventoryField.POS_NAME, (Object)inOldPos.buildPosName(), (Object)newPos.buildPosName()));
                        }
                        fcs.setFieldChange(new FieldChange(UnitField.UFV_POS_ORIENTATION, (Object)inOldPos.getPosOrientation(), (Object)newPos.getPosOrientation()));
                        inUnit.recordUnitEvent((IEventType) EventEnum.UNIT_POSITION_CORRECTION, fcs, UnitEquipment.getMessage(IInventoryPropertyKeys.UNIT_POS_CORRECTION, null));
                    }
                }

                @Override
                public boolean allowUpdate(Unit inUnit) {
                    return UnitFacilityVisit.this.allowPrimaryUnitUpdate(inUnit, curUnit, inNewPos);
                }
            });
        }
        catch (BizViolation inBizViolation) {
            LOGGER.error((Object)"Failed updating last known position", (Throwable)inBizViolation);
        }
    }

    private boolean allowPrimaryUnitUpdate(Unit inUnit, Unit inCurUnit, LocPosition inNewPos) {
        return inUnit.equals(inCurUnit) || inUnit.getUnitRelatedUnit() != null && inCurUnit.equals(inUnit.getUnitRelatedUnit()) && EqUnitRoleEnum.PRIMARY != inUnit.getUnitEqRole();
    }

    private boolean allowPrimaryAndCarriageUnitUpdate(Unit inUnit, Unit inCurUnit, LocPosition inNewPos) {
        return inUnit.equals(inCurUnit) || inUnit.getUnitRelatedUnit() != null && inCurUnit.equals(inUnit.getUnitRelatedUnit()) && EqUnitRoleEnum.PRIMARY != inUnit.getUnitEqRole() || inCurUnit.getUnitRelatedUnit() != null && (inUnit.equals(inCurUnit.getUnitRelatedUnit()) || inUnit.getUnitRelatedUnit().equals(inCurUnit.getUnitRelatedUnit())) && EqUnitRoleEnum.PRIMARY != inUnit.getUnitEqRole();
    }

    public void updateArrivePosition(LocPosition inArrivePosition) {
        this.setUfvArrivePosition(inArrivePosition);
        this.setUfvActualIbCv(inArrivePosition.resolveInboundCarrierVisit());
    }

    public void updatePositionForArrivingUnit(LocPosition inNewPos) {
        this.updateArrivePosition(inNewPos);
        this.updateLastKnownPosition(inNewPos, null);
    }

    public void updateLastKnownPositionOnTruck(LocPosition inNewPos) {
        if (inNewPos != null && this.getUfvLastKnownPosition() != null && this.getUfvLastKnownPosition().isTruckPosition() && inNewPos.isTruckPosition()) {
            this.setUfvLastKnownPosition(inNewPos);
        }
    }

    public void makeActive() throws BizViolation {
        if (!UnitVisitStateEnum.ADVISED.equals((Object)this.getUfvVisitState())) {
            throw BizViolation.create((IPropertyKey) IInventoryPropertyKeys.UNITS__MUST_BE_ADVISED_TO_ACTIVATE, null);
        }
        Unit ufvUnit = this.getUfvUnit();
        ufvUnit.checkAndAdjustTransitStateIfAllowed(this.getUfvFacility());
        this.advanceTransitState(UfvTransitStateEnum.S20_INBOUND, null);
    }

    public void revertInboundToAdvised() throws BizViolation {
        if (!UfvTransitStateEnum.S20_INBOUND.equals((Object)this.getUfvTransitState())) {
            throw BizViolation.create((IPropertyKey) IInventoryPropertyKeys.UNITS__REVERT_ACTIVE_TO_ADVISED, null, (Object)this.getUfvUnit().getUnitId(), (Object)((Object)this.getUfvTransitState()));
        }
        if (this.getUfvTimeIn() != null) {
            throw BizViolation.create((IPropertyKey) IInventoryPropertyKeys.UNITS__REVERT_ACTIVE_TO_ADVISED_IN_TIME_NOT_NULL, null);
        }
        this.innerSetVisitState(UnitVisitStateEnum.ADVISED);
        this.innerSetTransitState(UfvTransitStateEnum.S10_ADVISED, true);
    }

    public void makeDeparted() throws BizViolation {
        if (!UnitVisitStateEnum.ACTIVE.equals((Object)this.getUfvVisitState())) {
            throw BizViolation.create((IPropertyKey) IInventoryPropertyKeys.UNITS__WRONG_UNIT_STATE, null, (Object)this.getUfvUnit().getUnitId(), (Object)((Object)this.getUfvVisitState()));
        }
        this.advanceTransitState(UfvTransitStateEnum.S70_DEPARTED, null);
    }

    public void makeDeparted(Date inTimeStamp) throws BizViolation {
        if (!UnitVisitStateEnum.ACTIVE.equals((Object)this.getUfvVisitState())) {
            throw BizViolation.create((IPropertyKey) IInventoryPropertyKeys.UNITS__WRONG_UNIT_STATE, null, (Object)this.getUfvUnit().getUnitId(), (Object)((Object)this.getUfvVisitState()));
        }
        this.advanceTransitState(UfvTransitStateEnum.S70_DEPARTED, inTimeStamp);
    }

    protected void makeRetired() throws BizViolation {
        this.advanceTransitState(UfvTransitStateEnum.S99_RETIRED, null);
    }

    public void receiveIntoFacility(LocPosition inArrivePosition) throws BizViolation {
        Unit unit;
        DrayStatusEnum drayStatus;
        if (this.isTransitStateBeyond(UfvTransitStateEnum.S20_INBOUND)) {
            throw BizViolation.create((IPropertyKey) IInventoryPropertyKeys.UNITS__ALREADY_IN_FACILITY, null, (Object)this.getUfvUnit(), (Object)this.getUfvFacility());
        }
        if (inArrivePosition != null) {
            if (!inArrivePosition.isCarrierPosition()) {
                throw BizFailure.create((String)("receiveIntoFacility: supplied arrive position is not a position on a carrier <" + (Object)inArrivePosition + "> for " + this + (Object)this.getUfvFacility()));
            }
            CarrierVisit cv = inArrivePosition.resolveInboundCarrierVisit();
            if (cv == null) {
                throw BizFailure.create((String)("receiveIntoFacility: can't resolve CarrierVisit in arrive position " + this + " in facility " + (Object)this.getUfvFacility() + " for pos " + (Object)inArrivePosition));
            }
            if (!cv.isAtFacility()) {
                throw BizViolation.create((IPropertyKey) IInventoryPropertyKeys.UNITS__CARRIER_NOT_ARRIVED, null, (Object)this, (Object)cv, (Object)this.getUfvFacility());
            }
            this.setUfvArrivePosition(inArrivePosition);
            this.setUfvActualIbCv(cv);
            this.updateLastKnownPosition(inArrivePosition, null);
        }
        if (UnitVisitStateEnum.ADVISED.equals((Object)this.getUfvVisitState())) {
            this.makeActive();
        }
        if (LocTypeEnum.TRUCK.equals((Object)this.getUfvActualIbCv().getCvCarrierMode()) && (DrayStatusEnum.OFFSITE.equals((Object)(drayStatus = (unit = this.getUfvUnit()).getUnitDrayStatus())) || DrayStatusEnum.TRANSFER.equals((Object)drayStatus))) {
            unit.updateDrayStatus(null);
            DrayStatusEnum newDrayStatus = unit.getUnitDrayStatus();
            FieldChanges fcs = new FieldChanges();
            fcs.setFieldChange(new FieldChange(UnitField.UNIT_DRAY_STATUS, (Object)drayStatus, (Object)newDrayStatus));
            unit.recordUnitEvent((IEventType) EventEnum.UNIT_REROUTE, fcs, null);
            LOGGER.warn((Object)("receiveIntoFacility: drayStatus was cleared on Unit that re-entered: " + this));
        }
        this.advanceTransitState(UfvTransitStateEnum.S30_ECIN, null);
    }

    public void initiateDeliveryOrLoad() throws BizViolation {
        this.advanceTransitState(UfvTransitStateEnum.S50_ECOUT, null);
    }

    @Deprecated
    public void deliverOutOfFacility() throws BizViolation {
        Unit unit = this.getUfvUnit();
        if (!this.isActive()) {
            if (UnitVisitStateEnum.DEPARTED.equals((Object)this.getUfvVisitState()) && UnitVisitStateEnum.ACTIVE.equals((Object)unit.getUnitVisitState())) {
                unit.makeDeparted();
                return;
            }
            throw BizFailure.create((String)("attempt to deliver an inactive UFV: " + this));
        }
        LocPosition pos = this.getUfvLastKnownPosition();
        if (!pos.isCarrierPosition()) {
            throw BizViolation.create((IPropertyKey) IInventoryPropertyKeys.MOVE__CAN_NOT_DELIVER_FROM_YARD, null, (Object)this);
        }
        final CarrierVisit obCv = pos.resolveOutboundCarrierVisit();
        this.getUfvUnit().applyUpdateOnUnitCombo(new AbstractUnitUpdate(){

            @Override
            public void apply(Unit inUnit) throws BizViolation {
                UnitFacilityVisit ufv = UnitFacilityVisit.this.getEqUfv(inUnit);
                if (ufv != null) {
                    ufv.setUfvActualObCv(obCv);
                }
            }
        });
        if (obCv != null) {
            this.depart(obCv.findOrCreateNextVisit());
        }
    }

    public void depart(CarrierVisit inNextCarrierVisit) throws BizViolation {
        this.makeDeparted(this.getUfvActualObCv().getCvATD());
        Unit unit = this.getUfvUnit();
        DrayStatusEnum drayStatus = unit.getUnitDrayStatus();
        boolean unitIsDepartingComplex = true;
        if (inNextCarrierVisit != null) {
            unitIsDepartingComplex = false;
        } else {
            LocTypeEnum departMode = this.getUfvActualObCv().getCvCarrierMode();
            if (LocTypeEnum.TRUCK.equals((Object)departMode) && (DrayStatusEnum.TRANSFER.equals((Object)drayStatus) || DrayStatusEnum.OFFSITE.equals((Object)drayStatus))) {
                unitIsDepartingComplex = false;
            }
        }
        Facility thisFacility = this.getUfvFacility();
        Complex complex = thisFacility.getFcyComplex();
        if (unitIsDepartingComplex) {
            unit.makeDeparted();
        } else {
            this.getUfvUnit().applyUpdateOnUnitCombo(new AbstractUnitUpdate(){

                @Override
                public void apply(Unit inUnit) throws BizViolation {
                    if (!inUnit.equals(UnitFacilityVisit.this.getUfvUnit())) {
                        inUnit.makeDeparted();
                    }
                }
            });
            UnitFacilityVisit nextUfv = null;
            if (inNextCarrierVisit != null) {
                Routing unitRouting;
                Facility nextFacility = inNextCarrierVisit.getCvFacility();
                LocPosition pos = this.getUfvLastKnownPosition();
                LocPosition arrivePosition = inNextCarrierVisit.createInboundPosition(pos);
                nextUfv = unit.getUfvForFacilityLiveOnly(nextFacility);
                if (nextUfv == null) {
                    CarrierVisit declaredObCv = unit.getUnitRouting().getRtgDeclaredCv();
                    CarrierVisit obCv = declaredObCv != null && !declaredObCv.isGenericCv() && declaredObCv.getCvFacility().equals((Object)nextFacility) ? declaredObCv : CarrierVisit.getGenericCarrierVisit((Complex)this.getUfvUnit().getUnitComplex());
                    nextUfv = UnitFacilityVisit.createUnitFacilityVisit(unit, nextFacility, arrivePosition, obCv);
                } else {
                    nextUfv.updatePositionForArrivingUnit(arrivePosition);
                }
                nextUfv.advanceTransitState(UfvTransitStateEnum.S20_INBOUND, null);
                if (LocTypeEnum.VESSEL.equals((Object)inNextCarrierVisit.getCvCarrierMode()) && UnitCategoryEnum.THROUGH.equals((Object)unit.getUnitCategory()) && (unitRouting = unit.getUnitRouting()) != null && nextFacility != null && nextFacility.getFcyRoutingPoint().equals((Object)unitRouting.getRtgPOD1())) {
                    if (LOGGER.isInfoEnabled()) {
                        LOGGER.info((Object)("updating category of the unit " + unit.getUnitId() + "  from through to import"));
                    }
                    unit.updateCategory(UnitCategoryEnum.IMPORT);
                }
            } else if (DrayStatusEnum.OFFSITE.equals((Object)drayStatus)) {
                LocPosition arrivePosition = LocPosition.createTruckPosition((ILocation) CarrierVisit.getGenericTruckVisit((Complex)complex), null, null);
                nextUfv = unit.adviseToFacility(thisFacility, arrivePosition, this.getUfvIntendedObCv());
            } else {
                RoutingPoint point = null;
                Facility nextFacility = null;
                UnitCategoryEnum category = this.getUfvUnit().getUnitCategory();
                if (UnitCategoryEnum.TRANSSHIP.equals((Object)category) || UnitCategoryEnum.EXPORT.equals((Object)category)) {
                    nextFacility = unit.getUnitRouting().getRtgDeclaredCv().getCvFacility();
                } else if (UnitCategoryEnum.STORAGE.equals((Object)category) && this.getUfvUnit().getUnitRouting().getRtgGroup() != null) {
                    nextFacility = this.getUfvUnit().getUnitRouting().getRtgGroup().getGrpDestinationFacility();
                } else {
                    point = this.getUfvUnit().getUnitRouting().getRtgPOD1();
                }
                if (nextFacility == null && point != null) {
                    nextFacility = Facility.findFacilityByRoutingPoint((RoutingPoint)point, (Complex)complex, (Facility)thisFacility);
                }
                if (nextFacility != null) {
                    LocPosition arrivePosition = LocPosition.createTruckPosition((ILocation) CarrierVisit.getGenericTruckVisit((Complex)complex), null, null);
                    nextUfv = unit.adviseToFacility(nextFacility, arrivePosition, unit.getUnitRouting().getRtgDeclaredCv());
                    nextUfv.advanceTransitState(UfvTransitStateEnum.S20_INBOUND, null);
                }
            }
            if (nextUfv != null && InventoryConfig.COPY_UFV_FLEX_FIELDS.isOn(ContextHelper.getThreadUserContext())) {
                this.copyFlexFields(nextUfv);
            }
        }
    }

    private void copyFlexFields(UnitFacilityVisit inNextUfv) {
        inNextUfv.setUfvFlexString01(this.getUfvFlexString01());
        inNextUfv.setUfvFlexString02(this.getUfvFlexString02());
        inNextUfv.setUfvFlexString03(this.getUfvFlexString03());
        inNextUfv.setUfvFlexString04(this.getUfvFlexString04());
        inNextUfv.setUfvFlexString05(this.getUfvFlexString05());
        inNextUfv.setUfvFlexString06(this.getUfvFlexString06());
        inNextUfv.setUfvFlexString07(this.getUfvFlexString07());
        inNextUfv.setUfvFlexString08(this.getUfvFlexString08());
        inNextUfv.setUfvFlexString09(this.getUfvFlexString09());
        inNextUfv.setUfvFlexString10(this.getUfvFlexString10());
        inNextUfv.setUfvFlexDate01(this.getUfvFlexDate01());
        inNextUfv.setUfvFlexDate02(this.getUfvFlexDate02());
        inNextUfv.setUfvFlexDate03(this.getUfvFlexDate03());
        inNextUfv.setUfvFlexDate04(this.getUfvFlexDate04());
        inNextUfv.setUfvFlexDate05(this.getUfvFlexDate05());
        inNextUfv.setUfvFlexDate06(this.getUfvFlexDate06());
        inNextUfv.setUfvFlexDate07(this.getUfvFlexDate07());
        inNextUfv.setUfvFlexDate08(this.getUfvFlexDate08());
    }

    public boolean isActive() {
        return UnitVisitStateEnum.ACTIVE.equals((Object)this.getUfvVisitState());
    }

    public boolean isComplete() {
        return UnitVisitStateEnum.DEPARTED.equals((Object)this.getUfvVisitState()) || UnitVisitStateEnum.RETIRED.equals((Object)this.getUfvVisitState());
    }

    public boolean isFuture() {
        return UnitVisitStateEnum.ADVISED.equals((Object)this.getUfvVisitState());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public BizViolation validateChanges(FieldChanges inChanges) {
        if (ContextHelper.getThreadIsFromUpgrade()) {
            return null;
        }
        BizViolation bv = super.validateChanges(inChanges);
        CarrierVisit cv = null;
        Long arrivePosGkey = null;
        if (inChanges.hasFieldChange(UnitField.UFV_ARRIVE_POSITION)) {
            LocPosition pos = this.getUfvArrivePosition();
            try {
                UnitFacilityVisit.validatePosLocIdField(UnitField.UFV_ARRIVE_POS_LOC_ID, pos);
            }
            catch (BizViolation inBizViolation) {
                bv = inBizViolation.appendToChain(bv);
            }
            if (!pos.isCarrierPosition()) {
                bv = BizViolation.create((IPropertyKey) IInventoryPropertyKeys.UFV__NOT_A_CARRIER_POSITION, (BizViolation)bv, (Object)pos, (Object)this.getUfvUnit());
            } else {
                try {
                    cv = pos.resolveInboundCarrierVisit();
                    arrivePosGkey = pos.getPosLocGkey();
                }
                catch (Exception e) {
                    String unitId = this.getUfvUnit().getUnitId();
                    LOGGER.error((Object)("ERROR: It is likely that database is unexpectedly missing the carrier on which unit " + unitId + " entered the facility." + " This may mean  deletion of data happened that N4 is not aware of. This may require a fix by system admin/DBA"), (Throwable)e);
                }
                if (cv == null && !DataSourceEnum.XPS.equals((Object)ContextHelper.getThreadDataSource())) {
                    bv = BizViolation.create((IPropertyKey) IInventoryPropertyKeys.UFV__BAD_CARRIER_IN_POSITION, (BizViolation)bv, (Object)pos, (Object)this.getUfvUnit(), (Object)pos.getPosLocId());
                }
            }
        }
        if (inChanges.hasFieldChange(UnitField.UFV_LAST_KNOWN_POSITION)) {
            String oldPosStr;
            LocPosition pos;
            LocPosition lastpos = this.getUfvLastKnownPosition();
            if (lastpos != (pos = this.getUfvArrivePosition())) {
                try {
                    UnitFacilityVisit.validatePosLocIdField(UnitField.UFV_POS_LOC_ID, lastpos);
                }
                catch (BizViolation inBizViolation) {
                    bv = inBizViolation.appendToChain(bv);
                }
            }
            if (pos.isCarrierPosition()) {
                boolean isArrivePosSameAsLastKnownPos;
                Long lastKnownPosGkey = pos.getPosLocGkey();
                boolean bl = isArrivePosSameAsLastKnownPos = lastKnownPosGkey != null && ((Object)lastKnownPosGkey).equals(arrivePosGkey);
                if (cv == null || !isArrivePosSameAsLastKnownPos) {
                    try {
                        cv = pos.resolveInboundCarrierVisit();
                    }
                    catch (Exception e) {
                        String unitId = this.getUfvUnit().getUnitId();
                        LOGGER.error((Object)("ERROR: It is likely that database is unexpectedly missing the carrier on which unit " + unitId + " was last placed." + " This may mean external deletion of data that N4 is not aware of. This may require a fix by system admin/DBA"), (Throwable)e);
                    }
                    if (cv == null && !this.overrideBadCarrierPositionValidation()) {
                        bv = BizViolation.create((IPropertyKey) IInventoryPropertyKeys.UFV__BAD_CARRIER_IN_POSITION, (BizViolation)bv, (Object)pos, (Object)this.getUfvUnit(), (Object)pos.getPosLocId());
                    }
                }
            } else {
                Yard yard = pos.resolveYard();
                if (yard == null) {
                    bv = BizViolation.create((IPropertyKey) IInventoryPropertyKeys.UFV__INVALID_YARD_IN_POSITION, (BizViolation)bv, (Object)pos, (Object)this.getUfvUnit(), (Object)pos.getPosLocId());
                }
            }
            LocPosition newPos = (LocPosition)inChanges.getFieldChange(UnitField.UFV_LAST_KNOWN_POSITION).getNewValue();
            LocPosition oldPos = (LocPosition)inChanges.getFieldChange(UnitField.UFV_LAST_KNOWN_POSITION).getPriorValue();
            String newPosStr = newPos != null ? newPos.getPosName() : "unknown";
            String string = oldPosStr = oldPos != null ? oldPos.getPosName() : "unknown";
            if (LOGGER.isInfoEnabled()) {
                String unitId = this.getUfvUnit().getUnitId();
                LOGGER.info((Object)("Processing a position change for unit " + unitId + " from " + oldPosStr + " to " + newPosStr));
            }
            if (newPos != null && !newPos.isSamePosition(oldPos)) {
                IInventoryFieldUpdateProtection inventoryFieldUpdateProtection = (IInventoryFieldUpdateProtection)Roastery.getBean((String)"fieldUpdateProtection");
                try {
                    inventoryFieldUpdateProtection.validateProtectedPosition(this, newPos, oldPos);
                }
                catch (BizViolation inBizViolation) {
                    bv = inBizViolation.appendToChain(bv);
                    try {
                        String dataSourceStr;
                        String unitId = this.getUfvUnit().getUnitId();
                        String string2 = dataSourceStr = ContextHelper.getThreadDataSource() != null ? ContextHelper.getThreadDataSource().getName() : DataSourceEnum.UNKNOWN.getName();
                        if (newPos != null && newPos.isProtectedYardPosition()) {
                            newPosStr = '[' + newPosStr + ']';
                        }
                        if (oldPos != null && oldPos.isProtectedYardPosition()) {
                            oldPosStr = '[' + oldPosStr + ']';
                        }
                        BizViolation bvTemp = BizViolation.create((IPropertyKey) IInventoryPropertyKeys.UFV__PROTECTED_YARD_POSITION_TO_FROM, (Throwable)null, null, null, (Object[])new Object[]{unitId, newPosStr, oldPosStr, dataSourceStr});
                        LOGGER.error((Object)bvTemp.getLocalizedMessage(), (Throwable)bvTemp);
                        bv = inBizViolation.appendToChain(bvTemp);
                    }
                    catch (Exception e) {
                        LOGGER.error((Object)e);
                    }
                }
                finally {
                    ArgoUtils.clearSystemInternalUpdateInProgress();
                }
            }
        }
        if (inChanges.hasFieldChange(UnitField.UFV_GUARANTEE_THRU_DAY) || inChanges.hasFieldChange(UnitField.UFV_GUARANTEE_PARTY) || inChanges.hasFieldChange(UnitField.UFV_LINE_GUARANTEE_PARTY) || inChanges.hasFieldChange(UnitField.UFV_LINE_GUARANTEE_THRU_DAY)) {
            Date gtDay = this.getUfvGuaranteeThruDay();
            ScopedBizUnit gtParty = this.getUfvGuaranteeParty();
            if (gtDay == null && gtParty != null || gtDay != null && gtParty == null) {
                bv = BizViolation.create((IPropertyKey) IInventoryPropertyKeys.UFV__GUARANTEE_INCOMPLETE, (BizViolation)bv, (Object)this.getUfvUnit());
            }
            bv = this.validateGuaranteeDay(this, gtDay, bv, STORAGE);
            if (inChanges.hasFieldChange(UnitField.UFV_GUARANTEE_PARTY) && gtParty != null) {
                bv = this.getBizViolationForGuaranteeParty(bv, gtParty);
            }
            gtDay = this.getUfvLineGuaranteeThruDay();
            gtParty = this.getUfvLineGuaranteeParty();
            bv = this.validateGuaranteeDay(this, gtDay, bv, LINE_STORAGE);
            if (gtDay == null && gtParty != null || gtDay != null && gtParty == null) {
                bv = BizViolation.create((IPropertyKey) IInventoryPropertyKeys.UFV__GUARANTEE_INCOMPLETE, (BizViolation)bv, (Object)this.getUfvUnit());
            }
            if (inChanges.hasFieldChange(UnitField.UFV_LINE_GUARANTEE_PARTY) && gtParty != null) {
                bv = this.getBizViolationForGuaranteeParty(bv, gtParty);
            }
        }
        if (inChanges.hasFieldChange(UnitField.UFV_TIME_IN) || inChanges.hasFieldChange(UnitField.UFV_TIME_OUT)) {
            Date ufvTimIn = this.getUfvTimeIn();
            Date ufvTimOut = this.getUfvTimeOut();
            if (DateUtil.javaOrSqlDateBefore((Date)ufvTimOut, (Date)ufvTimIn)) {
                bv = BizViolation.create((IPropertyKey) IInventoryPropertyKeys.FACILITY_TIME_IN_DATE_IS_AFTER_TIME_OUT_DATE, (BizViolation)bv, (Object)this.getUfvTimeOut(), (Object)this.getUfvTimeIn());
            }
        }
        return bv;
    }

    private boolean overrideBadCarrierPositionValidation() {
        return DataSourceEnum.XPS.equals((Object)ContextHelper.getThreadDataSource()) || DataSourceEnum.AUTOMATION.equals((Object)ContextHelper.getThreadDataSource());
    }

    private BizViolation getBizViolationForGuaranteeParty(BizViolation inBv, ScopedBizUnit inGtParty) {
        CreditStatusEnum creditStatus = inGtParty.getBzuCreditStatus();
        if (creditStatus == null) {
            inBv = BizViolation.create((IPropertyKey) IInventoryPropertyKeys.UFV__GUARANTEE_PARTY_NOT_OAC, (BizViolation)inBv, (Object)this.getUfvUnit(), (Object)inGtParty.getBzuId(), (Object)"NULL");
        } else if (!CreditStatusEnum.OAC.equals((Object)creditStatus) && !InventoryConfig.BILLING_SKIP_VALIDATION_FOR_OAC_GUARANTEE_OF_SELF_SERVICE_GUARANTEE.isOn(ContextHelper.getThreadUserContext())) {
            inBv = BizViolation.create((IPropertyKey) IInventoryPropertyKeys.UFV__GUARANTEE_PARTY_NOT_OAC, (BizViolation)inBv, (Object)this.getUfvUnit(), (Object)inGtParty.getBzuId(), (Object)creditStatus);
        }
        return inBv;
    }

    private static void validatePosLocIdField(IMetafieldId inPosFieldId, LocPosition inLocPosition) throws BizViolation {
        IFieldValidator dv = Roastery.getMetafieldDictionary().getValidator();
        IMessageTranslator translator = TranslationUtils.getTranslationContext((UserContext)ContextHelper.getThreadUserContext()).getMessageTranslator();
        dv.validateString(inPosFieldId, inLocPosition.getPosLocId(), translator);
    }

    public BizViolation validateGuaranteeDay(UnitFacilityVisit inUfv, Date inGtd, BizViolation inBv, String inChargeFor) {
        BizViolation bv = inBv;
        if (STORAGE.equals(inChargeFor)) {
            Date ptd = inUfv.getUfvPaidThruDay();
            Date overrideLfd = inUfv.getUfvLastFreeDay();
            Date calculatedLfd = inUfv.getUfvCalculatedLastFreeDayDate(inChargeFor);
            Date outTime = inUfv.getUfvTimeOut();
            if (ptd != null && inGtd != null && inGtd.before(ptd)) {
                bv = this.getBizViolationForStorageViolations(inGtd, bv, IInventoryPropertyKeys.UFV__GUARANTEE_BEFORE_PTD, ptd);
            }
            if (overrideLfd != null && inGtd != null && inGtd.before(overrideLfd)) {
                bv = this.getBizViolationForStorageViolations(inGtd, bv, IInventoryPropertyKeys.UFV__GUARANTEE_BEFORE_LFD, overrideLfd);
            } else if (calculatedLfd != null && inGtd != null && inGtd.before(calculatedLfd)) {
                bv = this.getBizViolationForStorageViolations(inGtd, bv, IInventoryPropertyKeys.UFV__GUARANTEE_BEFORE_LFD, calculatedLfd);
            }
            if (outTime != null) {
                Date outDay = ArgoUtils.getLocalTimeStartOfDay((Date)outTime);
                if (inGtd != null && inGtd.after(outDay)) {
                    bv = this.getBizViolationForStorageViolations(inGtd, bv, IInventoryPropertyKeys.UFV__GUARANTEE_AFTER_OUT_TIME, outTime);
                }
            }
            return bv;
        }
        if (LINE_STORAGE.equals(inChargeFor)) {
            IPropertyKey ufvLineGuaranteeBeforePtd = IInventoryPropertyKeys.UFV__LINE_GUARANTEE_BEFORE_PTD;
            IPropertyKey ufvLineGuaranteeBeforeLfd = IInventoryPropertyKeys.UFV__LINE_GUARANTEE_BEFORE_LFD;
            IPropertyKey ufvLineGuaranteeAfterOutTime = IInventoryPropertyKeys.UFV__LINE_GUARANTEE_AFTER_OUT_TIME;
            Date ptd = inUfv.getUfvLinePaidThruDay();
            Date overrideLfd = inUfv.getUfvLineLastFreeDay();
            Date calculatedLfd = inUfv.getUfvCalculatedLastFreeDayDate(inChargeFor);
            Date outTime = inUfv.getUfvTimeOut();
            if (ptd != null && inGtd != null && inGtd.before(ptd)) {
                bv = this.getBizViolationForStorageViolations(inGtd, bv, ufvLineGuaranteeBeforePtd, ptd);
            }
            if (overrideLfd != null && inGtd != null && inGtd.before(overrideLfd)) {
                bv = this.getBizViolationForStorageViolations(inGtd, bv, ufvLineGuaranteeBeforeLfd, overrideLfd);
            } else if (calculatedLfd != null && inGtd != null && inGtd.before(calculatedLfd)) {
                bv = this.getBizViolationForStorageViolations(inGtd, bv, ufvLineGuaranteeBeforeLfd, calculatedLfd);
            }
            if (outTime != null) {
                Date outDay = ArgoUtils.getLocalTimeStartOfDay((Date)outTime);
                if (inGtd != null && inGtd.after(outDay)) {
                    bv = this.getBizViolationForStorageViolations(inGtd, bv, ufvLineGuaranteeAfterOutTime, outTime);
                }
            }
            return bv;
        }
        return bv;
    }

    private BizViolation getBizViolationForStorageViolations(Date inGtd, BizViolation inBv, IPropertyKey inUfvLineGuaranteeAfterOutTime, Date inOutTime) {
        inBv = BizViolation.create((IPropertyKey)inUfvLineGuaranteeAfterOutTime, (BizViolation)inBv, (Object)this.getUfvUnit(), (Object)ArgoUtils.convertDateToLocalDateOnly((Date)inGtd), (Object)ArgoUtils.convertDateToLocalDateOnly((Date)inOutTime));
        return inBv;
    }

    public BizViolation validateGuaranteeDay(UnitFacilityVisit inUfv, Date inGtd, BizViolation inBv) {
        return this.validateGuaranteeDay(inUfv, inGtd, inBv, STORAGE);
    }

    public boolean isForFacility(Facility inFacility) {
        return ObjectUtils.equals((Object)this.getUfvFacility(), (Object)inFacility);
    }

    public UnitYardVisit getUyvForYard(Yard inYard) {
        if (inYard == null) {
            throw BizFailure.create((String)"inYard is null");
        }
        List uyvList = this.getUfvUyvList();
        if (uyvList != null) {
            for (Object anUyvList : uyvList) {
                UnitYardVisit uyv = (UnitYardVisit)anUyvList;
                if (!ObjectUtils.equals((Object)inYard, (Object)uyv.getUyvYard())) continue;
                return uyv;
            }
        }
        LOGGER.error((Object)("findOrCreateUnitYardVisit: unexpectedly had to create UYV for " + this + " for yard " + inYard.getYrdId()));
        return UnitYardVisit.createUnitYardVisit(inYard, this);
    }

    @Nullable
    public LocPosition getLastYardPosition() {
        LocPosition posNow = this.getUfvLastKnownPosition();
        if (posNow.isYardPosition()) {
            return posNow;
        }
        MoveEvent event = MoveEvent.getLastRecordedYardMove(this);
        return event == null ? null : event.getMveToPosition();
    }

    @Nullable
    public CarrierVisit getInboundCarrierVisit() {
        CarrierVisit cv = null;
        LocPosition pos = this.getUfvArrivePosition();
        if (pos != null) {
            cv = pos.resolveInboundCarrierVisit();
        }
        return cv;
    }

    public boolean isInFacility() {
        return this.isTransitStateBeyond(UfvTransitStateEnum.S30_ECIN) && this.isTransitStatePriorTo(UfvTransitStateEnum.S60_LOADED);
    }

    public boolean isDepartingInland() {
        CarrierVisit cv = this.getUfvIntendedObCv();
        LocTypeEnum carrierMode = cv == null ? null : cv.getCvCarrierMode();
        return LocTypeEnum.TRUCK.equals((Object)carrierMode) || LocTypeEnum.TRAIN.equals((Object)carrierMode);
    }

    public Long getUfvDwellDays() {
        Date timeIn = this.getUfvTimeIn();
        if (timeIn != null) {
            UserContext threadUserContext = ContextHelper.getThreadUserContext();
            TimeZone userTZ = threadUserContext.getTimeZone();
            Calendar c = Calendar.getInstance(userTZ);
            c.setTime(timeIn);
            c.set(14, 0);
            c.set(13, 0);
            c.set(12, 0);
            c.set(11, 0);
            long dayIn = DateUtil.getDSTSafeCalendarTime((Calendar)c).getTime() / 86400000L;
            Date endDate = this.getUfvTimeOut();
            if (endDate == null) {
                endDate = ArgoUtils.timeNow();
            }
            c.setTime(endDate);
            c.set(14, 0);
            c.set(13, 0);
            c.set(12, 0);
            c.set(11, 0);
            long dayEnd = DateUtil.getDSTSafeCalendarTime((Calendar)c).getTime() / 86400000L;
            if (InventoryConfig.DWELL_DAYS_CALC.isOn(threadUserContext)) {
                return dayEnd - dayIn + 1L;
            }
            return dayEnd - dayIn;
        }
        return ZERO;
    }

    public Boolean getUnitFacilityVisitHasPlannedMove() {
        List yardVisits = this.getUfvUyvList();
        if (yardVisits != null && !yardVisits.isEmpty()) {
            for (Object yardVisit1 : yardVisits) {
                Boolean hasPlannedMove;
                UnitYardVisit yardVisit = (UnitYardVisit)yardVisit1;
                if (yardVisit == null || !(hasPlannedMove = yardVisit.getUnitYardVisitHasPlannedMove()).booleanValue()) continue;
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    public Long getUfvStorageDaysTotal(String inChargeFor) {
        IUnitStorageManager usm = (IUnitStorageManager)Roastery.getBean((String)"unitStorageManager");
        return Long.valueOf(usm.getStorageDaysTotal(this, inChargeFor));
    }

    public Long getUfvStorageDaysTotal() {
        return this.getUfvStorageDaysTotal(STORAGE);
    }

    public Long getUfvPowerDaysTotal() {
        return this.getUfvPowerDaysTotal(POWER);
    }

    public Long getUfvPowerDaysTotal(String inChargeFor) {
        IUnitStorageManager usm = (IUnitStorageManager)Roastery.getBean((String)"unitStorageManager");
        return Long.valueOf(usm.getPowerDaysTotal(this, inChargeFor));
    }

    public Long getUfvPowerHoursTotal() {
        return this.getUfvPowerHoursTotal(POWER);
    }

    public Long getUfvPowerHoursTotal(String inChargeFor) {
        IUnitStorageManager usm = (IUnitStorageManager)Roastery.getBean((String)"unitStorageManager");
        return Long.valueOf(usm.getPowerHoursTotal(this, inChargeFor));
    }

    public Long getUfvPowerDaysOwed(String inChargeFor) {
        IUnitStorageManager usm = (IUnitStorageManager)Roastery.getBean((String)"unitStorageManager");
        return Long.valueOf(usm.getPowerDaysOwed(this, inChargeFor));
    }

    public Long getUfvPowerDaysOwed() {
        return this.getUfvPowerDaysOwed(POWER);
    }

    public Long getUfvStorageDaysOwed(String inChargeFor) {
        IUnitStorageManager usm = (IUnitStorageManager)Roastery.getBean((String)"unitStorageManager");
        return Long.valueOf(usm.getStorageDaysOwed(this, inChargeFor));
    }

    public Long getUfvStorageDaysOwed() {
        return this.getUfvStorageDaysOwed(STORAGE);
    }

    public Long getUfvFreeDays() {
        return this.getUfvFreeDays(STORAGE);
    }

    public Long getUfvFreeDays(String inChargeFor) {
        IUnitStorageManager usm = (IUnitStorageManager)Roastery.getBean((String)"unitStorageManager");
        return Long.valueOf(usm.getFreeDays(this, inChargeFor));
    }

    public String getUfvCalculatedLastFreeDay() {
        IUnitStorageManager usm = (IUnitStorageManager)Roastery.getBean((String)"unitStorageManager");
        return usm.getLastFreeDayAsStringForUi(this, STORAGE);
    }

    public String getUfvCalculatedLineStorageLastFreeDay() {
        IUnitStorageManager usm = (IUnitStorageManager)Roastery.getBean((String)"unitStorageManager");
        return usm.getLastFreeDayAsStringForUi(this, LINE_STORAGE);
    }

    public String getUfvCalculatedPowerLastFreeDay() {
        IUnitStorageManager usm = (IUnitStorageManager)Roastery.getBean((String)"unitStorageManager");
        return usm.getLastFreeDayAsStringForUi(this, POWER);
    }

    public String getUfvCalculatedLastFreeDay(String inChargeFor) {
        IUnitStorageManager usm = (IUnitStorageManager)Roastery.getBean((String)"unitStorageManager");
        return usm.getLastFreeDayAsStringForUi(this, inChargeFor);
    }

    public Date getUfvCalculatedLastFreeDayDate() {
        return this.getUfvCalculatedLastFreeDayDate(STORAGE);
    }

    public Date getUfvCalculatedLastFreeDayDate(String inChargeFor) {
        IUnitStorageManager usm = (IUnitStorageManager)Roastery.getBean((String)"unitStorageManager");
        return usm.getLastFreeDay(this, inChargeFor);
    }

    public Date getUfvCalculatedFirstFreeDay(String inChargeFor) {
        IUnitStorageManager usm = (IUnitStorageManager)Roastery.getBean((String)"unitStorageManager");
        return usm.getFirstFreeDay(this, inChargeFor);
    }

    public IValueHolder getUfvStorageCalcDto() throws BizViolation {
        IUnitStorageManager usm = (IUnitStorageManager)Roastery.getBean((String)"unitStorageManager");
        try {
            return (IValueHolder) usm.getStorageCalculationDto(this, STORAGE);
        }
        catch (Throwable t) {
            LOGGER.error((Object) IInventoryPropertyKeys.UNITS__INVALID_STORAGE_DTO, t);
            throw BizViolation.create((IPropertyKey) IInventoryPropertyKeys.UNITS__INVALID_STORAGE_DTO, (Throwable)t, null, (IMetafieldId) IInventoryBizMetafield.UFV_STORAGE_CALC_DTO, null);
        }
    }

    public IValueHolder getUfvLineStorageCalcDto() throws BizViolation {
        IUnitStorageManager usm = (IUnitStorageManager)Roastery.getBean((String)"unitStorageManager");
        try {
            return (IValueHolder) usm.getStorageCalculationDto(this, LINE_STORAGE);
        }
        catch (Throwable t) {
            LOGGER.error((Object) IInventoryPropertyKeys.UNITS__INVALID_STORAGE_DTO, t);
            throw BizViolation.create((IPropertyKey) IInventoryPropertyKeys.UNITS__INVALID_STORAGE_DTO, (Throwable)t, null, (IMetafieldId) IInventoryBizMetafield.UFV_LINE_STORAGE_CALC_DTO, null);
        }
    }

    public IValueHolder getUfvPowerCalcDto() {
        IUnitStorageManager usm = (IUnitStorageManager)Roastery.getBean((String)"unitStorageManager");
        return (IValueHolder) usm.getStorageCalculationDto(this, POWER);
    }

    public boolean getUfvIsPowerConnected() {
        EventManager em = (EventManager)Roastery.getBean((String)"eventManager");
        EventType eventType = EventType.resolveIEventType((IEventType) EventEnum.UNIT_POWER_CONNECT);
  //      return em.hasEventTypeBeenApplied(eventType, (IServiceable)this.getUfvUnit());
        return false;
    }

    public String getPrimaryEqId() {
        UnitEquipment ue = this.getUfvUnit().getUnitPrimaryUe();
        return ue == null ? this.getUfvUnit().getUnitId() : ue.getUeEquipment().getEqIdFull();
    }

    public AuditEvent vetAuditEvent(AuditEvent inAuditEvent) {
        return inAuditEvent;
    }

    public final String toString() {
        return "UFV[" + this.getUfvGkey() + ':' + this.getUfvUnit().getUnitId() + ':' + this.getUfvVisitState().getKey() + ':' + this.getUfvFacility().getFcyId() + ']';
    }

    public static IPredicate formUfvDwellDaysPredicate(IMetafieldId inMetafieldId, PredicateVerbEnum inVerb, Object inValue) {
        IMetafieldId qualifyingMetafieldId = inMetafieldId.getMfidExcludeRightMostNode();
        IMetafieldId timeIn = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)qualifyingMetafieldId, (IMetafieldId) IInventoryField.UFV_TIME_IN);
        long dwellDays = 0L;
        if (inValue instanceof Long) {
            dwellDays = (Long)inValue;
        }
        Date adjustedTimeIn = new Date(ArgoUtils.timeNow().getTime() - dwellDays * 86400000L);
        if (PredicateVerbEnum.EQ.equals((Object)inVerb)) {
            Date dayAfterDwellTime = new Date(adjustedTimeIn.getTime() + 86400000L);
            AbstractJunction betweenPredicate = PredicateFactory.conjunction().add(PredicateFactory.createPredicate((UserContext)ContextHelper.getThreadUserContext(), (IMetafieldId)timeIn, (PredicateVerbEnum) PredicateVerbEnum.GE, (Object)adjustedTimeIn)).add(PredicateFactory.createPredicate((UserContext)ContextHelper.getThreadUserContext(), (IMetafieldId)timeIn, (PredicateVerbEnum) PredicateVerbEnum.LT, (Object)dayAfterDwellTime));
            return betweenPredicate;
        }
        PredicateVerbEnum dwellVerb = inVerb;
        if (PredicateVerbEnum.GE.equals((Object)inVerb)) {
            dwellVerb = PredicateVerbEnum.LE;
        } else if (PredicateVerbEnum.GT.equals((Object)inVerb)) {
            dwellVerb = PredicateVerbEnum.LT;
        } else if (PredicateVerbEnum.LE.equals((Object)inVerb)) {
            dwellVerb = PredicateVerbEnum.GE;
        } else if (PredicateVerbEnum.LT.equals((Object)inVerb)) {
            dwellVerb = PredicateVerbEnum.GT;
        }
        return PredicateFactory.createPredicate((UserContext)ContextHelper.getThreadUserContext(), (IMetafieldId)timeIn, (PredicateVerbEnum)dwellVerb, (Object)adjustedTimeIn);
    }

    public static String getUfvUeEqId() {
        return null;
    }

    @Nullable
    private static String getEquipmentFullId(String inDigits) {
        if (!inDigits.isEmpty()) {
            if (inDigits.length() >= 4 && inDigits.substring(0, 4).matches("[a-zA-Z]{4}")) {
                Equipment equipment = Equipment.findEquipment((String)inDigits);
                if (equipment != null) {
                    String fullCtrId = equipment.getEqIdFull();
                    return fullCtrId;
                }
                return inDigits + "%";
            }
            if (!Character.isDigit(inDigits.charAt(0))) {
                return inDigits + "%";
            }
        }
        return null;
    }

    public static IPredicate formUfvUeEqIdPredicate(IMetafieldId inMetafieldId, PredicateVerbEnum inVerb, Object inValue) {
        String searchDigits = (String)inValue;
        String strFullCtr = UnitFacilityVisit.getEquipmentFullId(searchDigits);
        searchDigits = strFullCtr == null ? "%" + searchDigits + "%" : strFullCtr;
        UserContext userContext = ContextHelper.getThreadUserContext();
        if (searchDigits instanceof String && StringUtils.isNotEmpty((String)searchDigits)) {
            IDomainQuery subQuery = QueryUtils.createDomainQuery((String)"Unit").addDqPredicate(PredicateFactory.createPredicate((UserContext)userContext, (IMetafieldId) UnitField.UE_EQ_ID_FULL, (PredicateVerbEnum)inVerb, (Object)searchDigits)).addDqPredicate(PredicateFactory.eqProperty((IMetafieldId) IInventoryField.UE_UNIT, (IMetafieldId)OuterQueryMetafieldId.valueOf((IMetafieldId) UnitField.UFV_UNIT_GKEY)));
            Disjunction disjunction = new Disjunction();
            disjunction.add(PredicateFactory.createPredicate((UserContext)userContext, (IMetafieldId) UnitField.UFV_UNIT_ID, (PredicateVerbEnum)inVerb, (Object)searchDigits));
            disjunction.add(PredicateFactory.subQueryExists((IDomainQuery)subQuery));
            return disjunction;
        }
        return PredicateFactory.createPredicate((UserContext)userContext, (IMetafieldId) UnitField.UFV_UNIT_ID, (PredicateVerbEnum)inVerb, (Object)searchDigits);
    }

    public static IPredicate formUfvPositionPlannedFinalPredicate(IMetafieldId inMetafieldId, PredicateVerbEnum inVerb, Object inValue) {
        IDomainQuery wiDq = QueryUtils.createDomainQuery((String)"WorkInstruction").addDqPredicate(PredicateFactory.eq((IMetafieldId) UnitField.WI_UFV_GKEY, (Object)OuterQueryMetafieldId.valueOf((IMetafieldId) UnitField.UFV_GKEY))).addDqPredicate(PredicateFactory.ne((IMetafieldId)IMovesField.WI_MOVE_STAGE, (Object) WiMoveStageEnum.COMPLETE)).setDqMaxResults(1);
        wiDq.addDqPredicate(PredicateFactory.createPredicate((UserContext)ContextHelper.getThreadUserContext(), (IMetafieldId) UnitField.WI_POS_NAME, (PredicateVerbEnum)inVerb, (Object)inValue));
        Disjunction disjunction = new Disjunction();
        disjunction.add(PredicateFactory.subQueryExists((IDomainQuery)wiDq));
        return disjunction;
    }

    public BizFieldList getBizFieldList() {
        UserContext uc = ContextHelper.getThreadUserContext();
        if (uc == null || InventoryConfig.SECURITY_VESSEL_OPERATOR_VIEW_UNITS.isOn(uc)) {
            return BIZ_FIELD_LIST_ALL;
        }
        return BIZ_FIELD_LIST_NOP;
    }

    public String getUfvReportActualIbCarrierString() {
        CarrierVisit ibCarrier = this.getUfvActualIbCv();
        return UnitFacilityVisit.getStringifiedCvId(ibCarrier);
    }

    private static String getStringifiedCvId(CarrierVisit inCarrier) {
        StringBuilder sb = new StringBuilder();
        if (inCarrier != null) {
            String truckOperator;
            LocTypeEnum mode = inCarrier.getCvCarrierMode();
            String id = inCarrier.getCvId();
            sb.append(id);
            if (LocTypeEnum.TRUCK.equals((Object)mode) && (truckOperator = inCarrier.getCarrierOperatorId()) != null) {
                sb.append(" (");
                sb.append(truckOperator);
                sb.append(")");
            }
        }
        return sb.toString();
    }

    public String getUfvReportActualObCarrierString() {
        CarrierVisit obCarrier = this.getUfvActualObCv();
        return UnitFacilityVisit.getStringifiedCvId(obCarrier);
    }

    protected void innerAlignVisitState(UfvTransitStateEnum inTransitState) throws BizViolation {
        UnitVisitStateEnum correctVisitState;
        UnitVisitStateEnum currentVisitState = this.getUfvVisitState();
        if (!currentVisitState.equals((Object)(correctVisitState = UnitFacilityVisit.getVisitStateForTransitState(inTransitState)))) {
            this.innerSetVisitState(correctVisitState);
        }
    }

    protected void advanceTransitState(UfvTransitStateEnum inNewState, Date inTimestamp) throws BizViolation {
        int stateNow = this.calculateTransitStateValue();
        if (!this.isTransitStateChangeRespresentingDischargeOfLoadedUnit(inNewState) && UnitFacilityVisit.getTransitStateValue(inNewState) < stateNow) {
            throw BizFailure.create((String)("invalid TransitState transition: from=" + stateNow + ", to=" + (Object)((Object)inNewState)));
        }
        this.innerSetTransitStateAndTimestamps(inNewState, inTimestamp);
        if (UfvTransitStateEnum.S70_DEPARTED.equals((Object)inNewState)) {
            UnitEventExtractManager.updateStorageEventEndTime(this, ChargeableUnitEventTypeEnum.STORAGE);
            UnitEventExtractManager.updateStorageEventEndTime(this, ChargeableUnitEventTypeEnum.LINE_STORAGE);
            UnitEventExtractManager.updateStorageEventEndTime(this, ChargeableUnitEventTypeEnum.REEFER);
            UnitEventExtractManager.updateRuleEndTime(this);
            UnitEventExtractManager.updateTimeOutForNonTimeBasedEvents(this);
            UnitEventExtractManager.updateEventOBCarrierVisit(this);
        } else if (UfvTransitStateEnum.S99_RETIRED.equals((Object)inNewState)) {
            UnitEventExtractManager.updateStorageEventEndTime(this, ChargeableUnitEventTypeEnum.STORAGE);
        }
    }

    private void innerSetTransitStateAndTimestamps(UfvTransitStateEnum inNewState, Date inTimestamp) throws BizViolation {
        if (inTimestamp == null) {
            inTimestamp = ArgoUtils.timeNow();
        }
        this.innerAlignVisitState(inNewState);
        this.innerSetTimestamps(inNewState, inTimestamp);
        this.innerSetTransitState(inNewState, false);
    }

    private void innerSetTimestamps(final UfvTransitStateEnum inNewState, final Date inTimestamp) throws BizViolation {
        this.getUfvUnit().applyUpdateOnUnitCombo(new AbstractUnitUpdate(){

            @Override
            public void apply(Unit inUnit) throws BizViolation {
                UnitFacilityVisit ufv = UnitFacilityVisit.this.getEqUfv(inUnit);
                if (ufv != null) {
                    ufv.doInnerSetTimestamps(inNewState, inTimestamp);
                }
            }

            @Override
            public boolean allowUpdate(Unit inUnit) {
                return !UfvTransitStateEnum.S99_RETIRED.equals((Object)inNewState) || inUnit.equals(UnitFacilityVisit.this.getUfvUnit());
            }
        });
    }

    private void doInnerSetTimestamps(UfvTransitStateEnum inNewState, Date inTimestamp) {
        if (UfvTransitStateEnum.S70_DEPARTED.equals((Object)inNewState)) {
            this.setUfvTimeOut(inTimestamp);
            this.setUfvTimeComplete(inTimestamp);
        } else if (UfvTransitStateEnum.S60_LOADED.equals((Object)inNewState)) {
            this.setUfvTimeOfLoading(inTimestamp);
            this.setUfvTimeOut(null);
        } else if (UfvTransitStateEnum.S50_ECOUT.equals((Object)inNewState)) {
            this.setUfvTimeEcOut(inTimestamp);
            this.setUfvTimeOfLoading(null);
            this.setUfvTimeOut(null);
        } else if (UfvTransitStateEnum.S40_YARD.equals((Object)inNewState)) {
            this.setUfvTimeIn(inTimestamp);
            this.setUfvTimeEcOut(null);
            this.setUfvTimeOfLoading(null);
            this.setUfvTimeOut(null);
        } else if (UfvTransitStateEnum.S30_ECIN.equals((Object)inNewState)) {
            if (!this.isTransitStateChangeRespresentingDischargeOfLoadedUnit(inNewState)) {
                this.setUfvTimeEcIn(inTimestamp);
                this.setUfvTimeIn(null);
            }
            this.setUfvTimeEcOut(null);
            this.setUfvTimeOfLoading(null);
            this.setUfvTimeOut(null);
        } else if (UfvTransitStateEnum.S20_INBOUND.equals((Object)inNewState) || UfvTransitStateEnum.S10_ADVISED.equals((Object)inNewState)) {
            this.setUfvTimeEcIn(null);
            this.setUfvTimeIn(null);
            this.setUfvTimeEcOut(null);
            this.setUfvTimeOfLoading(null);
            this.setUfvTimeOut(null);
        } else if (UfvTransitStateEnum.S99_RETIRED.equals((Object)inNewState)) {
            this.setUfvTimeComplete(inTimestamp);
        }
    }

    private void innerSetTransitState(final UfvTransitStateEnum inNewState, final boolean inSuppressInOutEvent) {
        try {
            this.getUfvUnit().applyUpdateOnUnitCombo(new AbstractUnitUpdate(){

                @Override
                public void apply(Unit inUnit) throws BizViolation {
                    UnitFacilityVisit ufv = UnitFacilityVisit.this.getEqUfv(inUnit);
                    if (ufv != null) {
                        ufv.doInnerSetTransitState(inNewState, inSuppressInOutEvent);
                    }
                }

                @Override
                public boolean allowUpdate(Unit inUnit) {
                    block4: {
                        int ufvTransitStateValue;
                        block5: {
                            UnitFacilityVisit ufv = inUnit.getUnitActiveUfvNowActive();
                            if (ufv == null) break block4;
                            ufvTransitStateValue = ufv.calculateTransitStateValue();
                            if (ufvTransitStateValue == 3) break block5;
                            if (ufvTransitStateValue != 2) break block4;
                        }
                        if (ufvTransitStateValue > UnitFacilityVisit.this.calculateTransitStateValue()) {
                            return false;
                        }
                    }
                    return !UfvTransitStateEnum.S99_RETIRED.equals((Object)inNewState) || inUnit.equals(UnitFacilityVisit.this.getUfvUnit());
                }

                @Override
                public boolean applyOnAll(Unit inUnit) {
                    if (EqUnitRoleEnum.CARRIAGE.equals((Object)inUnit.getUnitEqRole()) && inUnit.isChassisBundle()) {
                        return true;
                    }
                    return super.applyOnAll(inUnit);
                }
            });
        }
        catch (BizViolation inBizViolation) {
            LOGGER.error((Object)inBizViolation);
        }
    }

    private void doInnerSetTransitState(UfvTransitStateEnum inNewState, boolean inSuppressInOutEvent) {
        int stateOld = UnitFacilityVisit.getTransitStateValue(this.getUfvTransitState());
        int stateNew = UnitFacilityVisit.getTransitStateValue(inNewState);
        if (stateNew == stateOld) {
            return;
        }
        Unit unit = this.getUfvUnit();
        if (stateNew != 0 && stateNew != 6 && stateNew != 7 && (EqUnitRoleEnum.PRIMARY == unit.getUnitEqRole() || EqUnitRoleEnum.CARRIAGE == unit.getUnitEqRole())) {
            this.setUfvVisibleInSparcs(Boolean.TRUE);
        }
        if (stateNew == 6) {
            EquipmentPosition eqPos = this.calculateEquipmentPosition();
            Set ues = this.getUfvUnit().getUnitUeSet();
            for (Object ue1 : ues) {
                UnitEquipment ue = (UnitEquipment)ue1;
                if (!ue.hasNotBeenDetached()) continue;
                EquipmentState eqs = ue.ensureEquipmentState();
                eqs.setEqsLastPosLocType(eqPos.getPosLocType());
                eqs.setEqsLastPosName(eqPos.getPosName());
                eqs.setEqsLastFacility(this.getUfvFacility());
            }
        }
        boolean depOrderAssigned = this.getUfvUnit().isAllowedDepartureOrderAssign();
        boolean arrivalOrderAssigned = this.getUfvUnit().isAllowedArrivalOrderAssign();
        this.setUfvTransitState(inNewState);
        if (!inSuppressInOutEvent) {
            CarrierVisit cv;
            Facility obcvFacility;
            if (stateOld <= 1 && stateNew > 1 && stateNew <= 4) {
                LocTypeEnum mode = this.getUfvActualIbCv().getCvCarrierMode();
                if (LocTypeEnum.TRUCK.equals((Object)mode)) {
                    unit.recordUnitEvent((IEventType) EventEnum.UNIT_IN_GATE, null, null);
                } else if (LocTypeEnum.VESSEL.equals((Object)mode)) {
                    unit.recordUnitEvent((IEventType) EventEnum.UNIT_IN_VESSEL, null, null, this.getUfvTimeEcIn());
                } else {
                    unit.recordUnitEvent((IEventType) EventEnum.UNIT_IN_RAIL, null, null);
                }
            } else if (stateOld >= 2 && stateOld <= 5 && stateNew == 6 && (obcvFacility = (cv = this.getUfvActualObCv()).getCvFacility()) != null && obcvFacility.getFcyGkey().equals(this.getUfvFacility().getFcyGkey())) {
                Date eventTime;
                LocTypeEnum mode = this.getUfvActualObCv().getCvCarrierMode();
                Date date = eventTime = cv.getCvATD() != null ? cv.getCvATD() : null;
                if (LocTypeEnum.TRUCK.equals((Object)mode)) {
                    unit.recordUnitEvent((IEventType) EventEnum.UNIT_OUT_GATE, null, null, eventTime);
                } else if (LocTypeEnum.VESSEL.equals((Object)mode)) {
                    unit.recordUnitEvent((IEventType) EventEnum.UNIT_OUT_VESSEL, null, null, eventTime);
                } else {
                    unit.recordUnitEvent((IEventType) EventEnum.UNIT_OUT_RAIL, null, null, eventTime);
                }
            }
        }
        EqBaseOrder depOrder = this.getUfvUnit().getDepartureOrder();
        EqBaseOrder arrivalOrder = this.getUfvUnit().getArrivalOrder();
        boolean currentDepOrderAssigned = this.getUfvUnit().isAllowedDepartureOrderAssign();
        boolean currentArrvOrderAssigned = this.getUfvUnit().isAllowedArrivalOrderAssign();
        if (depOrderAssigned && !currentDepOrderAssigned) {
            depOrder.decrementTallyRcvCount();
        } else if (!depOrderAssigned && currentDepOrderAssigned) {
            depOrder.incrementTallyRcv(this.getUfvUnit());
        }
        if (stateNew != 6) {
            if (arrivalOrderAssigned && !currentArrvOrderAssigned) {
                arrivalOrder.decrementTallyRcvCount();
            } else if (!arrivalOrderAssigned && currentArrvOrderAssigned) {
                arrivalOrder.incrementTallyRcv(this.getUfvUnit());
            }
        }
    }

    public void updateUfvOptimalRailTZSlot(String inPosition) {
        this.setUfvOptimalRailTZSlot(inPosition);
    }

    public void updateRailConeStatus(RailConeStatusEnum inConeStatus) throws BizViolation {
        if (RailConeStatusEnum.UNKNOWN.equals((Object)inConeStatus)) {
            this.setUfvRailConeStatus(inConeStatus);
            return;
        }
        LocPosition posOnRailcar = this.getUfvLastKnownPosition();
        String unitId = this.getUfvUnit().getUnitId();
        if (!LocTypeEnum.RAILCAR.equals((Object)posOnRailcar.getPosLocType())) {
            throw BizViolation.create((IPropertyKey) IInventoryPropertyKeys.UFV_NOT_ON_RAIL_CAR_ON_BOARD, null, (Object)inConeStatus.getName(), (Object)unitId);
        }
        IRailcarVisitLocation railcarVisit = (IRailcarVisitLocation)posOnRailcar.resolveLocation();
        railcarVisit.validateConeStatusForCntr(posOnRailcar, inConeStatus, unitId);
        this.setUfvRailConeStatus(inConeStatus);
    }

    public static void updateFlexLoadMTFieldsForUpcomingLoadMove(final @NotNull WorkInstruction inWorkInstruction, final @NotNull long inUfvGkey) {
        UnitFacilityVisit verifyUfv = null;
        try {
            verifyUfv = (UnitFacilityVisit)HibernateApi.getInstance().load(UnitFacilityVisit.class, (Serializable) Long.valueOf(inUfvGkey));
        }
        catch (Exception ex) {
            LOGGER.error((Object)("Unable to load UFV for key: " + inUfvGkey + " due to error: " + ex.getMessage()));
            return;
        }
        if (!verifyUfv.treatAsFlexLoadEmpty()) {
            return;
        }
        final UserContext systemContext = ContextHelper.getSystemUserContextForScope((IScopeEnum)ScopeEnum.COMPLEX, (Serializable)ContextHelper.getThreadComplexKey());
        final Facility currentFacility = ContextHelper.getThreadFacility();
        PersistenceTemplate pt = new PersistenceTemplate(systemContext);
        UserContext currentUC = TransactionParms.getBoundParms().getUserContext();
        pt.invoke(new CarinaPersistenceCallback(){

            public void doInTransaction() {
                try {
                    UnitFacilityVisit ufv = (UnitFacilityVisit)HibernateApi.getInstance().load(UnitFacilityVisit.class, (Serializable) Long.valueOf(inUfvGkey));
                    LOGGER.debug((Object)"Switching to System Context");
                    TransactionParms.getBoundParms().setUserContext(systemContext);
                    FieldChanges fc = new FieldChanges();
                    if (ufv.getUfvUnit() != null && ufv.getUfvUnit().getUnitRouting() != null) {
                        RoutingPoint pod = ufv.getUfvUnit().getUnitRouting().getRtgPOD1();
                        RoutingPoint projectedPod = ufv.getUfvUnit().getUnitRouting().getRtgProjectedPOD();
                        if (projectedPod.equals((Object)pod)) {
                            LOGGER.debug((Object)"The POD and ProjectedPOD is the same.");
                        } else {
                            LOGGER.debug((Object)("Updating category and POD for " + ufv + " in preparation of eventual load... "));
                            fc.setFieldChange(UnitField.UFV_CATEGORY, (Object)UnitCategoryEnum.EXPORT);
                            IMetafieldId intendedPOD = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId) UnitField.UFV_ROUTING, (IMetafieldId) IInventoryField.RTG_P_O_D1);
                            fc.setFieldChange(intendedPOD, (Object)projectedPod);
                        }
                    } else {
                        LOGGER.debug((Object)"Could not determine unit routing. Not updating UFV fields for FlexLoadMT");
                        return;
                    }
                    CarrierVisit destinationCV = null;
                    if (inWorkInstruction.getWiCarrierLocType() != null && inWorkInstruction.getWiCarrierLocId() != null) {
                        try {
                            destinationCV = CarrierVisit.resolveCv((Facility)currentFacility, (CarrierModeEnum) InventoryControlUtils.getEquivalentCarrierModeEnum(inWorkInstruction.getWiCarrierLocType()), (String)inWorkInstruction.getWiCarrierLocId());
                        }
                        catch (BizViolation inBizViolation) {
                            LOGGER.debug((Object)"Unable to resolve CarrierVisit.");
                        }
                    }
                    if (destinationCV == null) {
                        destinationCV = CarrierVisit.hydrate((Serializable)inWorkInstruction.getWiToPosition().getPosLocGkey());
                    }
                    if (destinationCV != null) {
                        if (!destinationCV.equals((Object)ufv.getUfvObCv())) {
                            if (LOGGER.isDebugEnabled()) {
                                LOGGER.debug((Object)("Updating outbound CarrierVisit for " + ufv + " to " + (Object)destinationCV));
                            }
                            fc.setFieldChange(UnitField.UFV_INTENDED_OB_CV, (Object)destinationCV);
                            fc.setFieldChange(UnitField.UFV_ACTUAL_OB_CV, (Object)destinationCV);
                        } else if (LOGGER.isDebugEnabled()) {
                            LOGGER.debug((Object)("Outbound CarrierVisit for " + ufv + " is already " + (Object)destinationCV));
                        }
                    } else if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug((Object)("Unable to determine CarrierVisit for load: " + inWorkInstruction.describeWi()));
                    }
                    ufv.applyFieldChanges(fc);
                }
                catch (Exception ex) {
                    LOGGER.error((Object)("Unable to update FlexLoadMT fields for UFV due to error: " + ex.getMessage()));
                }
            }
        });
        LOGGER.debug((Object)"Switching back to original user context");
        TransactionParms.getBoundParms().setUserContext(currentUC);
    }

    private EquipmentPosition calculateEquipmentPosition() {
        EquipmentPosition eqPos;
        LocPosition pos = this.getUfvLastKnownPosition();
        if (this.isTransitState(UfvTransitStateEnum.S70_DEPARTED)) {
            Facility facility = this.getUfvFacility();
            String lastFacilitySnippet = " ex:" + facility.getFcyName();
            LocTypeEnum unitLocType = pos.getPosLocType();
            if (LocTypeEnum.VESSEL.equals((Object)unitLocType)) {
                CarrierVisit cv = pos.resolveOutboundCarrierVisit();
                String posName = "S-" + (cv == null ? pos.getPosLocId() : cv.getCarrierVehicleId()) + lastFacilitySnippet;
                eqPos = new EquipmentPosition(LocTypeEnum.SEA, posName);
            } else {
                RoutingPoint rp = facility.getFcyRoutingPoint();
                String posName = "C-" + rp.getPointId() + lastFacilitySnippet;
                eqPos = new EquipmentPosition(LocTypeEnum.COMMUNITY, posName);
            }
        } else {
            eqPos = new EquipmentPosition(pos.getPosLocType(), pos.getPosName());
        }
        return eqPos;
    }

    public int calculateTransitStateValue() {
        return UnitFacilityVisit.getTransitStateValue(this.getUfvTransitState());
    }

    public static int getTransitStateValue(UfvTransitStateEnum inState) {
        int state;
        if (UfvTransitStateEnum.S10_ADVISED.equals((Object)inState)) {
            state = 0;
        } else if (UfvTransitStateEnum.S20_INBOUND.equals((Object)inState)) {
            state = 1;
        } else if (UfvTransitStateEnum.S30_ECIN.equals((Object)inState)) {
            state = 2;
        } else if (UfvTransitStateEnum.S40_YARD.equals((Object)inState)) {
            state = 3;
        } else if (UfvTransitStateEnum.S50_ECOUT.equals((Object)inState)) {
            state = 4;
        } else if (UfvTransitStateEnum.S60_LOADED.equals((Object)inState)) {
            state = 5;
        } else if (UfvTransitStateEnum.S70_DEPARTED.equals((Object)inState)) {
            state = 6;
        } else if (UfvTransitStateEnum.S99_RETIRED.equals((Object)inState)) {
            state = 7;
        } else {
            LOGGER.error((Object)("getTransitStateValue: unknown state " + (Object)((Object)inState)));
            state = 0;
        }
        return state;
    }

    public boolean isTransitState(UfvTransitStateEnum inState) {
        return inState.equals((Object)this.getUfvTransitState());
    }

    public boolean isTransitStateBeyond(UfvTransitStateEnum inState) {
        return UnitFacilityVisit.getTransitStateValue(this.getUfvTransitState()) > UnitFacilityVisit.getTransitStateValue(inState);
    }

    public boolean isTransitStatePriorTo(UfvTransitStateEnum inState) {
        return UnitFacilityVisit.getTransitStateValue(this.getUfvTransitState()) < UnitFacilityVisit.getTransitStateValue(inState);
    }

    public boolean isTransitStateAtLeast(UfvTransitStateEnum inState) {
        return UnitFacilityVisit.getTransitStateValue(this.getUfvTransitState()) >= UnitFacilityVisit.getTransitStateValue(inState);
    }

    public boolean isTransitStateAtMost(UfvTransitStateEnum inState) {
        return UnitFacilityVisit.getTransitStateValue(this.getUfvTransitState()) <= UnitFacilityVisit.getTransitStateValue(inState);
    }

    public boolean isDischargeableTransitState() {
        return this.isTransitStateAtMost(UfvTransitStateEnum.S20_INBOUND) || UfvTransitStateEnum.S60_LOADED.equals((Object)this.getUfvTransitState());
    }

    private boolean isTransitStateChangeRespresentingDischargeOfLoadedUnit(UfvTransitStateEnum inNewState) {
        return UfvTransitStateEnum.S30_ECIN.equals((Object)inNewState) && UfvTransitStateEnum.S60_LOADED.equals((Object)this.getUfvTransitState());
    }

    private void innerSetVisitState(final UnitVisitStateEnum inNewState) throws BizViolation {
        this.getUfvUnit().applyUpdateOnUnitCombo(new AbstractUnitUpdate(){

            @Override
            public void apply(Unit inUnit) throws BizViolation {
                UnitFacilityVisit ufv = UnitFacilityVisit.this.getEqUfv(inUnit);
                if (ufv != null) {
                    ufv.doInnerSetVisitState(inNewState);
                }
            }

            @Override
            public boolean allowUpdate(Unit inUnit) {
                return !UnitVisitStateEnum.RETIRED.equals((Object)inNewState) || inUnit.equals(UnitFacilityVisit.this.getUfvUnit());
            }

            @Override
            public boolean applyOnAll(Unit inUnit) {
                if (EqUnitRoleEnum.CARRIAGE.equals((Object)inUnit.getUnitEqRole()) && inUnit.isChassisBundle()) {
                    return true;
                }
                return super.applyOnAll(inUnit);
            }
        });
    }

    private void doInnerSetVisitState(UnitVisitStateEnum inNewState) throws BizViolation {
        Unit ownerUnit = this.getUfvUnit();
        UnitFacilityVisit unitActiveUfv = ownerUnit.getUnitActiveUfv();
        Set ufvSet = ownerUnit.getUnitUfvSet();
        for (Object anUfvSet : ufvSet) {
            UnitFacilityVisit ufv = (UnitFacilityVisit)anUfvSet;
            if (!UnitVisitStateEnum.ACTIVE.equals((Object)ufv.getUfvVisitState()) || ObjectUtils.equals((Object)ufv, (Object)unitActiveUfv)) continue;
            if (unitActiveUfv == null) {
                ownerUnit.setUnitActiveUfv(ufv);
                LOGGER.error((Object)("updateUfvVisitState/INCOHERENT: unitActiveUfv updated to " + ufv));
                continue;
            }
            ufv.setUfvVisitState(UnitVisitStateEnum.RETIRED);
            LOGGER.error((Object)("updateUfvVisitState/INCOHERENT: excess active UFV made RETIRED! " + ufv));
        }
        UnitVisitStateEnum oldState = this.getUfvVisitState();
        if (!inNewState.equals((Object)oldState)) {
            UnitFacilityVisit activeUfv;
            if (UnitVisitStateEnum.ACTIVE.equals((Object)inNewState)) {
                activeUfv = ownerUnit.getUnitActiveUfvNowActive();
                if (activeUfv != null) {
                    throw BizViolation.create((IPropertyKey) IInventoryPropertyKeys.UNITS__ACTIVE_UFV_EXISTS, null, (Object)((Object)inNewState), (Object)activeUfv, (Object)activeUfv.getUfvFacility().getFcyId());
                }
                ownerUnit.setUnitActiveUfv(this);
                if (!ownerUnit.isActive()) {
                    ownerUnit.innerUpdateUnitVisitState(UnitVisitStateEnum.ACTIVE);
                }
                this.setUfvVisitState(UnitVisitStateEnum.ACTIVE);
                ownerUnit.updateDenormalizedFields();
            } else {
                this.setUfvVisitState(inNewState);
                if (!UnitVisitStateEnum.DEPARTED.equals((Object)inNewState) && UnitVisitStateEnum.RETIRED.equals((Object)inNewState)) {
                    activeUfv = ownerUnit.getUnitActiveUfvNowActive();
                    if (activeUfv != null && !activeUfv.getUfvFacility().equals((Object)ContextHelper.getThreadFacility())) {
                        throw BizViolation.create((IPropertyKey) IInventoryPropertyKeys.UNITS__ACTIVE_UFV_EXISTS, null, (Object)((Object)inNewState), (Object)activeUfv, (Object)activeUfv.getUfvFacility().getFcyId());
                    }
                    ownerUnit.innerUpdateUnitVisitState(UnitVisitStateEnum.RETIRED);
                }
            }
        }
    }

    public void rectify(RectifyParms inParms) throws BizViolation {
        boolean isPositionChanged;
        Date timeOfLastMove;
        Unit unit = this.getUfvUnit();
        UnitEquipment primaryUe = unit.getUnitPrimaryUe();
        UfvTransitStateEnum requestedTransitState = inParms.getUfvTransitState();
        UfvTransitStateEnum prevUfvTransitState = this.getUfvTransitState();
        UnitVisitStateEnum prevUfvVisitState = this.getUfvVisitState();
        Date prevUfvTimeOfLoading = this.getUfvTimeOfLoading();
        Date prevUfvTimeIn = this.getUfvTimeIn();
        Date prevUfvTimeOut = this.getUfvTimeOut();
        LOGGER.warn((Object)("rectifyUfv: user " + ContextHelper.getThreadUserId() + " rectifying " + this + " to transit state " + (Object)((Object)requestedTransitState)));
        int stage = UnitFacilityVisit.getTransitStateValue(requestedTransitState);
        if (inParms.isEraseHistory()) {
            this.eraseHistory(requestedTransitState, stage);
        }
        this.innerAlignVisitState(requestedTransitState);
        Date now = ArgoUtils.timeNow();
        Date timeIn = inParms.getTimeIn();
        Date timeOut = inParms.getTimeOut();
        Date timeOfLoading = inParms.getTimeOfLoading();
        Date date = timeOfLastMove = inParms.getTimeOfLastMove() != null ? inParms.getTimeOfLastMove() : this.getUfvTimeOfLastMove();
        if (timeOfLastMove != null && this.getUfvTimeOfLastMove() != null) {
            timeOfLastMove = timeOfLastMove.after(this.getUfvTimeOfLastMove()) ? timeOfLastMove : this.getUfvTimeOfLastMove();
        }
        Date timeEcIn = timeIn;
        Date timeEcOut = timeOut;
        timeOut = stage < 6 ? null : UnitFacilityVisit.getTimeToApply(this.getUfvTimeComplete(), now, timeOut);
        timeOfLoading = stage < 5 ? null : UnitFacilityVisit.getTimeToApply(this.getUfvTimeOfLoading(), now, timeOfLoading);
        timeEcOut = stage < 4 ? null : UnitFacilityVisit.getTimeToApply(this.getUfvTimeEcOut(), now, timeEcOut);
        timeIn = stage < 3 || UnitCategoryEnum.THROUGH.equals((Object)unit.getUnitCategory()) ? null : UnitFacilityVisit.getTimeToApply(this.getUfvTimeIn(), now, timeIn);
        timeEcIn = stage < 2 ? null : UnitFacilityVisit.getTimeToApply(this.getUfvTimeEcIn(), now, timeEcIn);
        if (stage < 1) {
            this.setUfvVisibleInSparcs(Boolean.FALSE);
        }
        this.setUfvTimeEcIn(timeEcIn);
        this.setUfvTimeIn(timeIn);
        this.setUfvTimeEcOut(timeEcOut);
        this.setUfvTimeOfLoading(timeOfLoading);
        this.setUfvTimeOfLastMove(timeOfLastMove);
        this.updateUfvComboTimeOutTimeComplete(timeOut);
        if (inParms.generateCue()) {
            ChargeableUnitEvent storageCue = UnitEventExtractManager.findExistingChargeableUnitStorageEventEndTimeIsNotNull(this.getUfvFacility(), this, ChargeableUnitEventTypeEnum.STORAGE);
            UnitEventExtractManager.updateStorageEventEndTime(this, storageCue);
            ChargeableUnitEvent lineStrgCue = UnitEventExtractManager.findExistingChargeableUnitStorageEventEndTimeIsNotNull(this.getUfvFacility(), this, ChargeableUnitEventTypeEnum.LINE_STORAGE);
            UnitEventExtractManager.updateStorageEventEndTime(this, lineStrgCue);
        }
        CarrierVisit prevUfvActualIbCv = this.getUfvActualIbCv();
        CarrierVisit prevUfvActualObCv = this.getUfvActualObCv();
        if (inParms.getIbCv() != null) {
            this.updateActualIbCv(inParms.getIbCv());
        }
        if (inParms.getObCv() != null) {
            if (this.getUfvUnit().isReservedForBooking() && !LocTypeEnum.TRUCK.equals((Object)inParms.getObCv().getCvCarrierMode())) {
                String eqonbr = primaryUe.getUeDepartureOrderItem() != null ? primaryUe.getUeDepartureOrderItem().getEqboiOrder().getEqboNbr() : "";
                throw BizViolation.create((IPropertyKey) IInventoryPropertyKeys.ERROR_OBCV_NOT_OF_TRUCK_AND_UNIT_IS_RESERVED, null, (Object)eqonbr, (Object)inParms.getObCv().getCvId());
            }
            this.updateObCv(inParms.getObCv());
        }
        LocPosition newPos = null;
        LocPosition requestedPosition = inParms.getPosition();
        if (requestedPosition != null) {
            if (UnitFacilityVisit.isPositionCoherent(requestedPosition, this, stage)) {
                newPos = requestedPosition;
            } else if (ArgoUtils.isSystemInternalUpdateTransaction()) {
                LOGGER.error((Object)("rectifyUfv: Provided position " + (Object)requestedPosition + " is incoherent for " + this));
            }
        }
        String requestedSlot = inParms.getSlot();
        LocPosition prevUfvLastKnownPosition = this.getUfvLastKnownPosition();
        if (!(newPos != null || requestedSlot == null && UnitFacilityVisit.isPositionCoherent(prevUfvLastKnownPosition, this, stage))) {
            if (stage >= 5) {
                CarrierVisit obCv = this.getUfvActualObCv();
                if (obCv == null) {
                    throw BizFailure.create((String)("Can not proceed: No Outbound carrier: " + this.toString()));
                }
                newPos = LocPosition.createLocPosition((ILocation)obCv, (String)requestedSlot, null);
            } else if (stage >= 3) {
                if (requestedSlot == null) {
                    newPos = this.getLastYardPosition();
                }
                if (newPos == null) {
                    Yard yard = ContextHelper.getThreadYard();
                    if (yard == null) {
                        Facility fcy;
                        Facility facility = fcy = ContextHelper.getThreadFacility() == null ? this.getUfvFacility() : ContextHelper.getThreadFacility();
                        if (fcy != null) {
                            yard = fcy.getActiveYard();
                        }
                    }
                    if (yard == null) {
                        throw BizFailure.create((String)("Can not create Yard position because could not resolve yard: " + this.toString()));
                    }
                    newPos = LocPosition.createYardPosition((Yard)yard, (String)requestedSlot, null, (EquipBasicLengthEnum)this.getBasicLength(), (boolean)inParms.isInsistOnValidYardBin());
                }
            } else {
                CarrierVisit ibCv = this.getUfvActualIbCv();
                if (ibCv == null) {
                    ibCv = unit.getUnitDeclaredIbCv();
                }
                if (ibCv == null) {
                    throw BizFailure.create((String)("Can not proceed: No Inbound carrier: " + this.toString()));
                }
                String slot = requestedSlot;
                if (stage <= 1 && requestedSlot == null) {
                    if (inParms.getIbCv() == null) {
                        slot = this.getUfvArrivePosition().getPosSlot();
                    }
                } else {
                    slot = requestedSlot != null ? requestedSlot : prevUfvLastKnownPosition.getPosSlot();
                }
                newPos = LocPosition.createLocPosition((ILocation)ibCv, (String)slot, null);
            }
        }
        boolean bl = isPositionChanged = newPos != null && !newPos.isSamePositionAndOrientation(prevUfvLastKnownPosition);
        if (isPositionChanged) {
            LOGGER.warn((Object)("rectifyUfv: moving " + this + " to " + newPos.getPosName()));
            this.updateLastKnownPosition(newPos, null);
            this.setUfvTimeOfLastMove(UnitFacilityVisit.getTimeToApply(null, now, inParms.getTimeOfLastMove()));
        } else if (inParms.getTimeOfLastMove() != null) {
            this.setUfvTimeOfLastMove(inParms.getTimeOfLastMove());
        }
        this.innerSetTransitState(requestedTransitState, true);
        UnitVisitStateEnum requestedUnitVisitState = inParms.getUnitVisitState();
        UnitVisitStateEnum unitVisitState = unit.getUnitVisitState();
        if (UfvTransitStateEnum.S99_RETIRED.equals((Object)requestedTransitState)) {
            this.setUfvVisitState(UnitVisitStateEnum.RETIRED);
            if (!UnitVisitStateEnum.RETIRED.equals((Object)unitVisitState)) {
                unit.innerUpdateUnitVisitState(UnitVisitStateEnum.RETIRED);
            }
        } else if (UfvTransitStateEnum.S70_DEPARTED.equals((Object)requestedTransitState)) {
            this.setUfvVisitState(UnitVisitStateEnum.DEPARTED);
            if (requestedUnitVisitState != null) {
                UnitFacilityVisit.verifyTransitStateFitsUnitVisitState(requestedTransitState, requestedUnitVisitState);
                unit.innerUpdateUnitVisitState(requestedUnitVisitState);
            } else if (UnitVisitStateEnum.ADVISED.equals((Object)unitVisitState)) {
                unit.innerUpdateUnitVisitState(UnitFacilityVisit.getVisitStateForTransitState(requestedTransitState));
            }
        } else if (UfvTransitStateEnum.S10_ADVISED.equals((Object)requestedTransitState)) {
            this.setUfvVisitState(UnitVisitStateEnum.ADVISED);
            if (requestedUnitVisitState != null) {
                UnitFacilityVisit.verifyTransitStateFitsUnitVisitState(requestedTransitState, requestedUnitVisitState);
                unit.innerUpdateUnitVisitState(requestedUnitVisitState);
            } else if (UnitVisitStateEnum.DEPARTED.equals((Object)unitVisitState) || UnitVisitStateEnum.RETIRED.equals((Object)unitVisitState)) {
                unit.innerUpdateUnitVisitState(UnitFacilityVisit.getVisitStateForTransitState(requestedTransitState));
            }
        } else {
            this.setUfvVisitState(UnitVisitStateEnum.ACTIVE);
            if (!UnitVisitStateEnum.ACTIVE.equals((Object)unitVisitState)) {
                unit.innerUpdateUnitVisitState(UnitVisitStateEnum.ACTIVE);
            }
        }
        if (newPos != null) {
            if (this.isTransitStateAtMost(UfvTransitStateEnum.S30_ECIN)) {
                this.setUfvArrivePosition(newPos);
            }
            this.innerAlignPositionOfAttachedEquipment();
        }
        if (inParms.getEventToRecord() != null) {
            FieldChanges fcs = new FieldChanges();
            UnitFacilityVisit.addFieldChange(fcs, IInventoryField.UFV_ACTUAL_IB_CV, (Object)prevUfvActualIbCv, (Object)this.getUfvActualIbCv());
            UnitFacilityVisit.addFieldChange(fcs, IInventoryField.UFV_ACTUAL_OB_CV, (Object)prevUfvActualObCv, (Object)this.getUfvActualObCv());
            if (isPositionChanged) {
                String oldPosName = prevUfvLastKnownPosition != null ? prevUfvLastKnownPosition.getPosName() : null;
                String newPosName = this.getUfvLastKnownPosition().getPosName();
                UnitFacilityVisit.addFieldChange(fcs, IInventoryField.POS_NAME, oldPosName, newPosName);
            }
            UnitFacilityVisit.addFieldChange(fcs, IInventoryField.UFV_TRANSIT_STATE, (Object)prevUfvTransitState, (Object)this.getUfvTransitState());
            UnitFacilityVisit.addFieldChange(fcs, IInventoryField.UFV_TIME_IN, prevUfvTimeIn, this.getUfvTimeIn());
            UnitFacilityVisit.addFieldChange(fcs, IInventoryField.UFV_TIME_OUT, prevUfvTimeOut, this.getUfvTimeOut());
            UnitFacilityVisit.addFieldChange(fcs, IInventoryField.UFV_TIME_OF_LOADING, prevUfvTimeOfLoading, this.getUfvTimeOfLoading());
            UnitFacilityVisit.addFieldChange(fcs, IInventoryField.UFV_VISIT_STATE, (Object)prevUfvVisitState, (Object)this.getUfvVisitState());
            if (fcs.getFieldChangeCount() > 0) {
                unit.recordUnitEventOnComboUnits((IEventType)inParms.getEventToRecord(), fcs, inParms.getEventNote());
            }
        }
        if (UnitFacilityVisit.getTransitStateValue(prevUfvTransitState) >= 5 && stage <= 3) {
            Set ufvSet = unit.getUnitUfvSet();
            HashSet<UnitFacilityVisit> toDelete = new HashSet<UnitFacilityVisit>();
            for (Object ufv : ufvSet) {
                if (this.equals(ufv) || UnitFacilityVisit.getTransitStateValue(((UnitFacilityVisit)ufv).getUfvTransitState()) > 1) continue;
                toDelete.add((UnitFacilityVisit)ufv);
            }
            for (UnitFacilityVisit ufv : toDelete) {
                unit.deleteUfv(ufv);
            }
        }
        if (inParms.isVisibleInSparcs() != null) {
            this.setUfvVisibleInSparcs(inParms.isVisibleInSparcs());
        }
    }

    private void eraseHistory(UfvTransitStateEnum inRequestedTransitState, int inStageValue) {
        boolean transitStateChanged;
        boolean bl = transitStateChanged = !this.getUfvTransitState().equals((Object)inRequestedTransitState);
        if (transitStateChanged) {
            IServicesManager srvcMgr = (IServicesManager)Roastery.getBean((String)"servicesManager");
            HashSet<Serializable> deletedEventGkeys = new HashSet<Serializable>();
            Unit unit = this.getUfvUnit();
            if (inStageValue < 6) {
                srvcMgr.purgeEventsAfterLastOccurrence(new String[]{EventEnum.UNIT_OUT_GATE.getId(), EventEnum.UNIT_OUT_RAIL.getId(), EventEnum.UNIT_OUT_VESSEL.getId()}, (IServiceable)unit, deletedEventGkeys, this.getUfvFacility());
            }
            if (inStageValue < 5) {
                srvcMgr.purgeEventsAfterLastOccurrence(new String[]{EventEnum.UNIT_LOAD.getId(), EventEnum.UNIT_RAMP.getId(), EventEnum.UNIT_DELIVER.getId()}, (IServiceable)unit, deletedEventGkeys);
            }
            if (inStageValue < 3) {
                srvcMgr.purgeAllEventsAfterLastOccurrence((IEventType) EventEnum.UNIT_YARD_MOVE, (IServiceable)unit, deletedEventGkeys);
            }
            if (inStageValue < 2 && !this.isDirectDelivery()) {
                srvcMgr.purgeEventsAfterLastOccurrence(new String[]{EventEnum.UNIT_RECEIVE.getId(), EventEnum.UNIT_DISCH.getId(), EventEnum.UNIT_DERAMP.getId(), EventEnum.UNIT_IN_GATE.getId(), EventEnum.UNIT_IN_RAIL.getId(), EventEnum.UNIT_IN_VESSEL.getId()}, (IServiceable)unit, deletedEventGkeys);
            }
            if (inStageValue < 1) {
                srvcMgr.purgeAllEventsAfterLastOccurrence((IEventType) EventEnum.UNIT_ACTIVATE, (IServiceable)unit, deletedEventGkeys);
            }
            UnitEventExtractManager.deleteExtractedEvents(deletedEventGkeys);
        }
    }

    private void innerAlignPositionOfAttachedEquipment() {
        try {
            this.getUfvUnit().applyUpdateOnUnitCombo(new AbstractUnitUpdate(){

                @Override
                public void apply(Unit inUnit) throws BizViolation {
                    UnitFacilityVisit ufv = UnitFacilityVisit.this.getEqUfv(inUnit);
                    if (ufv != null) {
                        ufv.doInnerAlignPositionOfAttachedEquipment();
                    }
                }
            });
        }
        catch (BizViolation inBizViolation) {
            LOGGER.error((Object)inBizViolation);
        }
    }

    private void doInnerAlignPositionOfAttachedEquipment() {
        EquipmentPosition eqPos = this.calculateEquipmentPosition();
        boolean isUnitActive = this.isActive();
        Set ueSet = this.getUfvUnit().getUnitUeSet();
        if (ueSet != null) {
            for (Object anUeSet : ueSet) {
                UnitEquipment ue = (UnitEquipment)anUeSet;
                if (!ue.hasNotBeenDetached()) continue;
                EquipmentState eqs = ue.getUeEquipmentState();
                if (!isUnitActive && !LocTypeEnum.UNKNOWN.equals((Object)eqs.getEqsLastPosLocType())) continue;
                eqs.setEqsLastPosLocType(eqPos.getPosLocType());
                eqs.setEqsLastPosName(eqPos.getPosName());
                eqs.setEqsLastFacility(this.getUfvFacility());
            }
        }
    }

    private UnitFacilityVisit getEqUfv(Unit inUnit) {
        if (this.getUfvUnit().equals(inUnit)) {
            return this;
        }
        UnitFacilityVisit ufv = null;
        if (inUnit.isLiveUnit()) {
            ufv = inUnit.getUfvForFacilityLiveOnly(this.getUfvFacility());
        }
        if (ufv == null) {
            ufv = inUnit.getUfvForFacilityCompletedOnly(this.getUfvFacility());
        }
        if (ufv == null) {
            LOGGER.warn((Object)("UFV null for unit <" + inUnit + "> and facility <" + (Object)this.getUfvFacility() + ">"));
        }
        return ufv;
    }

    private static boolean isPositionCoherent(LocPosition inPosition, UnitFacilityVisit inUfv, int inStageValue) {
        if (inStageValue == 7) {
            return true;
        }
        return inStageValue >= 5 ? ObjectUtils.equals((Object)inPosition.resolveOutboundCarrierVisit(), (Object)inUfv.getUfvActualObCv()) : (inStageValue >= 3 ? inPosition.isYardPosition() : ObjectUtils.equals((Object)inPosition.resolveInboundCarrierVisit(), (Object)inUfv.getUfvActualIbCv()));
    }

    private static Date getTimeToApply(Date inExistingTimeValue, Date inTimeNow, Date inSuppliedTime) {
        boolean hadExisting;
        boolean bl = hadExisting = inExistingTimeValue != null && inExistingTimeValue.before(inTimeNow);
        if (hadExisting) {
            return inExistingTimeValue;
        }
        if (inSuppliedTime != null) {
            return inSuppliedTime;
        }
        return inTimeNow;
    }

    public void setNewlyCreated(boolean inNewlyCreated) {
        this._isNewlyCreated = inNewlyCreated;
    }

    public boolean isNewlyCreated() {
        return this._isNewlyCreated;
    }

    public static MetafieldIdList getPredicateFields() {
        MetafieldIdList ids = new MetafieldIdList();
        ids.add(UnitField.getQualifiedField(UnitField.UFV_LINE_OPERATOR, "UnitFacilityVisit"));
        ids.add(UnitField.getQualifiedField(UnitField.UFV_PRIMARY_EQ_OPERATOR, "UnitFacilityVisit"));
        ids.add(UnitField.getQualifiedField(UnitField.UFV_PRIMARY_EQTYPE, "UnitFacilityVisit"));
        ids.add(UnitField.getQualifiedField(UnitField.UFV_RTG_TRUCKING_COMPANY, "UnitFacilityVisit"));
        ids.add(UnitField.getQualifiedField(UnitField.UFV_CATEGORY, "UnitFacilityVisit"));
        ids.add(UnitField.getQualifiedField(UnitField.UFV_FREIGHT_KIND, "UnitFacilityVisit"));
        ids.add(UnitField.getQualifiedField(UnitField.UFV_DRAY_STATUS, "UnitFacilityVisit"));
        ids.add(UnitField.getQualifiedField(UnitField.UFV_GROUP, "UnitFacilityVisit"));
        ids.add(UnitField.getQualifiedField(UnitField.UFV_REQUIRES_POWER, "UnitFacilityVisit"));
        ids.add(UnitField.getQualifiedField(UnitField.UFV_IS_OOG, "UnitFacilityVisit"));
        ids.add(UnitField.getQualifiedField(UnitField.UFV_IS_HAZARDOUS, "UnitFacilityVisit"));
        ids.add(UnitField.getQualifiedField(UnitField.UFV_ACTUAL_INBOUND_CV_OPERATOR, "UnitFacilityVisit"));
        ids.add(UnitField.getQualifiedField(UnitField.UFV_ACTUAL_OUTBOUND_CV_OPERATOR, "UnitFacilityVisit"));
        ids.add(UnitField.getQualifiedField(UnitField.UFV_TIME_IN, "UnitFacilityVisit"));
        ids.add(UnitField.getQualifiedField(UnitField.UFV_TIME_OUT, "UnitFacilityVisit"));
        ids.add(UnitField.getQualifiedField(UnitField.UFV_CURRENT_POSITION_TYPE, "UnitFacilityVisit"));
        ids.add(UnitField.getQualifiedField(UnitField.UFV_UNIT_ID, "UnitFacilityVisit"));
        ids.add(IServicesField.EVNT_EVENT_TYPE);
        ids.add(IServicesField.EVNT_APPLIED_TO_CLASS);
        ids.add(IServicesField.EVNT_CREATED);
        ids.add(IServicesField.EVNT_APPLIED_DATE);
        return ids;
    }

    @Nullable
    public static ScopedBizUnit findScopedBzUnit(String inId) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"ScopedBizUnit").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoRefField.BZU_ID, (Object)inId)).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoRefField.BZU_ROLE, (Object) BizRoleEnum.HAULIER));
        ScopedBizUnit result = (ScopedBizUnit)HibernateApi.getInstance().getUniqueEntityByDomainQuery(dq);
        return result;
    }

    public void updateRtgTruckingCompany(ScopedBizUnit inTruckingCompany) {
        Unit unit = this.getUfvUnit();
        if (unit == null) {
            LOGGER.warn((Object)"ufv not associated with any unit");
            return;
        }
        Routing rtg = unit.getUnitRouting();
        if (rtg == null) {
            LOGGER.warn((Object)(" ufv Unit is not associated with any routing, ignoring update on :" + unit.getUnitId()));
            return;
        }
        rtg.setRtgTruckingCompany(inTruckingCompany);
    }

    public void updateGoodsShipper(String inShipperId) {
        IMetafieldId gdsShipperBzu = IInventoryField.GDS_SHIPPER_BZU;
        this.updateGoodsShipperOrConsignee(inShipperId, gdsShipperBzu);
    }

    private void updateGoodsShipperOrConsignee(String inShipperId, IMetafieldId inGdsShipperBzu) {
//        Shipper bzu = Shipper.findOrCreateShipperByIdOrName((String)inShipperId);
//        if (bzu == null) {
//            String msg = "Could not locate entity with Id" + inShipperId;
//            LOGGER.warn((Object)msg);
//            throw BizFailure.create((IPropertyKey)IInventoryPropertyKeys.ERROR_SHIPPER_IDENTIFIACTION, null, (Object)inShipperId);
//        }
//        GoodsBase gds = (GoodsBase)this.getUfvUnit().getGoods();
//        if (gds == null) {
//            String msg = "Could not locate Goods to update";
//            LOGGER.warn((Object)msg);
//            throw BizFailure.create((IPropertyKey)IInventoryPropertyKeys.ERROR_SHIPPER_IDENTIFIACTION, null, (Object)msg);
//        }
//        gds.setFieldValue(inGdsShipperBzu, (Object)bzu);
    }

    public void updateGoodsConsignee(String inConsigneeId) {
        IMetafieldId gdsConsigneeBzu = IInventoryField.GDS_CONSIGNEE_BZU;
        this.updateGoodsShipperOrConsignee(inConsigneeId, gdsConsigneeBzu);
    }

    public void updateCommodity(Commodity inCmdy) {
        this.getUfvUnit().getUnitGoods().setGdsCommodity(inCmdy);
    }

    public void updateRoutingGroup(String inGrpId) {
        Unit unit = this.getUfvUnit();
        if (unit == null) {
            LOGGER.warn((Object)"ufv not associated with any unit");
            return;
        }
        Routing rtg = unit.getUnitRouting();
        if (rtg == null) {
            LOGGER.warn((Object)(" ufv Unit is not associated with any routing, ignoring update on :" + unit.getUnitId()));
            return;
        }
        Group newGrp = Group.findGroupProxy((String)inGrpId);
        if (newGrp == null) {
            LOGGER.warn((Object)(" ufv Unit is not associated with any routing group, ignoring update on :" + unit.getUnitId()));
            return;
        }
        rtg.setRtgGroup(newGrp);
    }

    public static UnitVisitStateEnum getVisitStateForTransitState(UfvTransitStateEnum inTransitState) {
        if (UfvTransitStateEnum.S10_ADVISED.equals((Object)inTransitState)) {
            return UnitVisitStateEnum.ADVISED;
        }
        if (UfvTransitStateEnum.S70_DEPARTED.equals((Object)inTransitState)) {
            return UnitVisitStateEnum.DEPARTED;
        }
        if (UfvTransitStateEnum.S99_RETIRED.equals((Object)inTransitState)) {
            return UnitVisitStateEnum.RETIRED;
        }
        return UnitVisitStateEnum.ACTIVE;
    }

    public static void verifyTransitStateFitsUnitVisitState(UfvTransitStateEnum inTransitState, UnitVisitStateEnum inVisitState) throws BizViolation {
        int transitStateValue = UnitFacilityVisit.getTransitStateValue(inTransitState);
        if (transitStateValue == 0) {
            UnitFacilityVisit.verifyVisitState(inTransitState, inVisitState, UnitVisitStateEnum.ADVISED, UnitVisitStateEnum.ACTIVE, null);
        } else if (transitStateValue < 6) {
            UnitFacilityVisit.verifyVisitState(inTransitState, inVisitState, UnitVisitStateEnum.ACTIVE, null, null);
        } else if (transitStateValue == 6) {
            UnitFacilityVisit.verifyVisitState(inTransitState, inVisitState, UnitVisitStateEnum.ACTIVE, UnitVisitStateEnum.DEPARTED, UnitVisitStateEnum.RETIRED);
        } else {
            UnitFacilityVisit.verifyVisitState(inTransitState, inVisitState, UnitVisitStateEnum.RETIRED, null, null);
        }
    }

    private static void addFieldChange(FieldChanges inFieldChanges, IMetafieldId inFieldId, Object inOldValue, Object inNewValue) {
        if (!ObjectUtils.equals((Object)inOldValue, (Object)inNewValue)) {
            inFieldChanges.setFieldChange(new FieldChange(inFieldId, inOldValue, inNewValue));
        }
    }

    private static void verifyVisitState(UfvTransitStateEnum inTransitState, UnitVisitStateEnum inVisitState, UnitVisitStateEnum inOk1, UnitVisitStateEnum inOk2, UnitVisitStateEnum inOk3) throws BizViolation {
        if (!inVisitState.equals((Object)inOk1)) {
            if (inOk2 == null) {
                throw BizViolation.create((IPropertyKey) IInventoryPropertyKeys.BAD_STATE_COMBO_1, null, (Object)((Object)inOk1), (Object)((Object)inTransitState));
            }
            if (!inVisitState.equals((Object)inOk2)) {
                if (inOk3 == null) {
                    throw BizViolation.create((IPropertyKey) IInventoryPropertyKeys.BAD_STATE_COMBO_2, null, (Object)((Object)inOk1), (Object)((Object)inOk2), (Object)((Object)inTransitState));
                }
                if (!inVisitState.equals((Object)inOk3)) {
                    Object[] parms = new Object[]{inOk1, inOk2, inOk3, inTransitState};
                    throw new BizViolation(IInventoryPropertyKeys.BAD_STATE_COMBO_3, null, null, null, parms);
                }
            }
        }
    }

    public EquipBasicLengthEnum getBasicLength() {
        return this.getUfvUnit().getBasicLength();
    }

    public void adviseBackToFacility() throws BizViolation {
        Unit unit = this.getUfvUnit();
        if (!UfvTransitStateEnum.S70_DEPARTED.equals((Object)this.getUfvTransitState())) {
            throw BizViolation.create((IPropertyKey) IInventoryPropertyKeys.RESURRECT_NOT_DEPARTED, null, (Object)unit.getUnitId());
        }
        if (LocTypeEnum.VESSEL.equals((Object)this.getUfvLastKnownPosition().getPosLocType())) {
            throw BizViolation.create((IPropertyKey) IInventoryPropertyKeys.RESURRECT_NOT_IN_COMMUNITY, null, (Object)unit.getUnitId());
        }
        UnitFacilityVisit activeUfv = unit.getUnitActiveUfvNowActive();
        if (activeUfv != null) {
            throw BizViolation.create((IPropertyKey) IInventoryPropertyKeys.RESURRECT_ACTIVE_UFV_EXISTS, null, (Object)unit.getUnitId(), (Object)activeUfv);
        }
        DrayStatusEnum originalDrayStatus = unit.getUnitDrayStatus();
        unit.updateDrayStatus(DrayStatusEnum.DRAYIN);
        FieldChanges fcs = new FieldChanges();
        fcs.setFieldChange(IInventoryField.UNIT_DRAY_STATUS, (Object)originalDrayStatus, (Object)unit.getUnitDrayStatus());
        unit.recordUnitEventOnComboUnits((IEventType) EventEnum.UNIT_BRING_BACK_INTO_YARD, fcs, "resurrect");
        Facility facility = this.getUfvFacility();
        LocPosition arrivePosition = LocPosition.createTruckPosition((ILocation) CarrierVisit.getGenericTruckVisit((Complex)facility.getFcyComplex()), null, null);
        UnitFacilityVisit nextUfv = UnitFacilityVisit.createUnitFacilityVisit(unit, facility, arrivePosition, unit.getUnitRouting().getRtgDeclaredCv());
        nextUfv.advanceTransitState(UfvTransitStateEnum.S20_INBOUND, null);
        Set ufvSet = unit.getUnitUfvSet();
        HashSet<UnitFacilityVisit> toDelete = new HashSet<UnitFacilityVisit>();
        for (Object ufv : ufvSet) {
            if (!UfvTransitStateEnum.S10_ADVISED.equals((Object)((UnitFacilityVisit)ufv).getUfvTransitState()) || ((UnitFacilityVisit)ufv).getUfvFacility().equals((Object)facility)) continue;
            toDelete.add((UnitFacilityVisit)ufv);
        }
        for (UnitFacilityVisit ufv : toDelete) {
            unit.deleteUfv(ufv);
        }
    }

    public void updateGoodsBl(String inBlNbr) throws BizViolation {
        Unit unit = this.getUfvUnit();
        if (unit == null) {
            LOGGER.warn((Object)"ufv not associated with any unit");
            return;
        }
        GoodsBase goods = unit.ensureGoods();
        if (StringUtils.isBlank((String)inBlNbr)) {
            LOGGER.warn((Object)(" Bl Nbr is blank, ignoring update on :" + goods));
            return;
        }
        goods.assignUnitBl(inBlNbr);
    }

    public void clearCasIdentificationInfo() {
        LOGGER.info((Object)("Clearing CAS identification (" + this.getUfvCasUnitReference() + ":" + this.getUfvCasTransactionReference() + ") for " + this.getPrimaryEqId()));
        this.setFieldValue(UnitField.UFV_CAS_UNIT_REFERENCE, null);
        this.setFieldValue(UnitField.UFV_CAS_TRANSACTION_REFERENCE, null);
    }

    public void preProcessDelete(FieldChanges inChanges) {
    }

    public void preProcessUpdate(FieldChanges inChanges, FieldChanges inOutMoreChanges) {
        RailConeStatusEnum newStatus;
        if (UnitFacilityVisit.unitStorageDetailsChanged(inChanges)) {
            this.getUfvUnit().recordUnitEvent((IEventType) EventEnum.UNIT_STORAGE_UPDATE, inChanges, "Unit Storage details updated");
        }
        if (UnitFacilityVisit.unitLineStorageDetailsChanged(inChanges)) {
            this.getUfvUnit().recordUnitEvent((IEventType) EventEnum.UNIT_LINE_STORAGE_UPDATE, inChanges, "Unit Line Storage details updated");
        }
        if (UnitFacilityVisit.unitPowerDetailsChanged(inChanges)) {
            this.getUfvUnit().recordUnitEvent((IEventType) EventEnum.UNIT_POWER_UPDATE, inChanges, "Unit Power details updated");
        }
        if (inChanges.hasFieldChange(IInventoryField.UFV_RESTOW_TYPE)) {
            this.getUfvUnit().recordUnitEvent((IEventType) EventEnum.UNIT_PROPERTY_UPDATE, inChanges, "Unit Restow details updated");
            this.updateUfvVerifiedForLoad(this.getUfvUnit(), inOutMoreChanges);
        }
        if (inChanges.hasFieldChange(IInventoryField.UFV_TIME_OUT)) {
            Long horizon = new Long("9999999999");
            if (this.getUfvTimeOut() != null) {
                horizon = DateUtil.getLongTimeOut((Date)this.getUfvTimeOut());
            }
            inOutMoreChanges.setFieldChange(IInventoryField.UFV_HORIZON, (Object)horizon);
        }
        if (inChanges.hasFieldChange(IInventoryField.UFV_TRANSIT_STATE) && UfvTransitStateEnum.S40_YARD.equals((Object)this.getUfvTransitState())) {
            boolean unitRequiresPower;
            Unit ufvUnit = this.getUfvUnit();
            boolean bl = unitRequiresPower = ufvUnit.getUnitRequiresPower() == null ? false : ufvUnit.getUnitRequiresPower();
            if (unitRequiresPower && !ufvUnit.hasReeferMonitorTimes()) {
                LineOperator line = LineOperator.resolveLineOprFromScopedBizUnit((ScopedBizUnit)this.getUfvUnit().getUnitLineOperator());
                if (line != null && line.isPropagateAllowed()) {
                    ufvUnit.propagateLineReeferMonitors(line, false);
                } else {
                    String lineId = line == null ? null : line.getBzuId();
                    ufvUnit.updateDefaultReeferMonitorTimes();
                    LOGGER.info((Object)("UFV preProcessUpdate: Line[" + lineId + "] has no reefer monitor times for Reefer Unit[" + ufvUnit + "]- using system default times[00:00,06:00,12:00,18:00]"));
                }
            }
        }
        if (inChanges.hasFieldChange(IInventoryField.UFV_RAIL_CONE_STATUS) && (newStatus = (RailConeStatusEnum)inChanges.getFieldChange(UnitField.UFV_RAIL_CONE_STATUS).getNewValue()) != null) {
            LocPosition curPosition = this.getUfvLastKnownPosition();
            if (curPosition != null && LocTypeEnum.RAILCAR.equals((Object)curPosition.getPosLocType())) {
                this.determineIncompatibilityReasonForAllDependentEntities(curPosition, inOutMoreChanges, true);
            } else {
                inOutMoreChanges.setFieldChange(IInventoryField.UFV_CARRIER_INCOMPATIBLE_REASON, (Object) CarrierIncompatibilityReasonEnum.NONE);
            }
        }
        if (inChanges.hasFieldChange(IInventoryField.UFV_LAST_KNOWN_POSITION)) {
            LocPosition newPos = (LocPosition)inChanges.getFieldChange(UnitField.UFV_LAST_KNOWN_POSITION).getNewValue();
            if (newPos != null && LocTypeEnum.RAILCAR.equals((Object)newPos.getPosLocType())) {
                this.determineIncompatibilityReasonForAllDependentEntities(newPos, inOutMoreChanges, false);
            } else {
                inOutMoreChanges.setFieldChange(IInventoryField.UFV_CARRIER_INCOMPATIBLE_REASON, (Object) CarrierIncompatibilityReasonEnum.NONE);
            }
            if (newPos != null && newPos.isApron()) {
                this.setSelfAndFieldChange(UnitField.UFV_CAS_UNIT_REFERENCE, null, inOutMoreChanges);
                this.setSelfAndFieldChange(UnitField.UFV_CAS_TRANSACTION_REFERENCE, null, inOutMoreChanges);
            }
        }
        if (inChanges.hasFieldChange(IInventoryField.UNIT_GOODS_AND_CTR_WT_KG_VERFIED_GROSS)) {
            inOutMoreChanges.setFieldChange(UnitField.UNIT_GROSS_WEIGHT_SOURCE, (Object)GrossWeightSourceEnum.USER);
        }
    }

    private void determineIncompatibilityReasonForAllDependentEntities(LocPosition inCurPosition, FieldChanges inOutMoreChanges, boolean inTopCtrChangesReqd) {
        DataSourceEnum threadDataSource = ContextHelper.getThreadDataSource();
        if (DataSourceEnum.EDI_CNST.equals((Object)threadDataSource)) {
            return;
        }
        CarrierIncompatibilityReasonEnum thisUfvIncompReason = this.determineIncompReason(inCurPosition, inTopCtrChangesReqd);
        if (CarrierIncompatibilityReasonEnum.NONE.equals((Object)thisUfvIncompReason)) {
            IArgoRailManager railManager = (IArgoRailManager)Roastery.getBean((String)"argoRailManager");
            boolean isPinCompatibile = true;
            try {
                EquipNominalLengthEnum eqtypNominalLength = this.getUfvUnit().getUnitPrimaryUe().getUeEquipment().getEqEquipType().getEqtypNominalLength();
                isPinCompatibile = railManager.isCarrierIncompatibleForLoad(this.getFinalPlannedPosition(), eqtypNominalLength);
            }
            catch (Exception e) {
                LOGGER.error((Object)"Unable to compute incompatibility reason for WI planned for Railcar slot ");
            }
            if (!isPinCompatibile) {
                inOutMoreChanges.setFieldChange(IInventoryField.UFV_CARRIER_INCOMPATIBLE_REASON, (Object) CarrierIncompatibilityReasonEnum.PIN_MISMATCHED);
            } else {
                inOutMoreChanges.setFieldChange(IInventoryField.UFV_CARRIER_INCOMPATIBLE_REASON, (Object) CarrierIncompatibilityReasonEnum.NONE);
            }
        } else {
            inOutMoreChanges.setFieldChange(IInventoryField.UFV_CARRIER_INCOMPATIBLE_REASON, (Object)thisUfvIncompReason);
        }
    }

    private CarrierIncompatibilityReasonEnum determineIncompReason(LocPosition inCurPosition, boolean inTopCtrChangesReqd) {
        CarrierIncompatibilityReasonEnum thisUfvIncompReason = CarrierIncompatibilityReasonEnum.NONE;
        if (inCurPosition == null || !LocTypeEnum.RAILCAR.equals((Object)inCurPosition.getPosLocType())) {
            return thisUfvIncompReason;
        }
        IRailcarVisitLocation railcarVisit = (IRailcarVisitLocation)inCurPosition.resolveLocation();
        Map reasonsMap = railcarVisit.determineCarrierIncompatibleReason(inCurPosition, (DatabaseEntity)this, null, this.getUfvRailConeStatus(), inTopCtrChangesReqd);
        HashMap<Long, FieldChanges> ufvChanges = new HashMap<Long, FieldChanges>();
        HashMap<Long, FieldChanges> tbdunitChanges = new HashMap<Long, FieldChanges>();
        for (Object entry : reasonsMap.entrySet()) {
            CarrierIncompatibilityReasonEnum dependentUfvIncompReason;
            String key = (String)((Map.Entry)entry).getKey();
            String entityPrefix = key.substring(0, 3);
            String entityGkeyStr = key.substring(3);
            long entityGkey = Long.parseLong(entityGkeyStr);
            if (UFV.equals(entityPrefix)) {
                if (entityGkey == this.getUfvGkey()) {
                    thisUfvIncompReason = (CarrierIncompatibilityReasonEnum)((Object)((Map.Entry)entry).getValue());
                    continue;
                }
                dependentUfvIncompReason = (CarrierIncompatibilityReasonEnum)((Object)((Map.Entry)entry).getValue());
                UnitFacilityVisit ufv2 = UnitFacilityVisit.hydrate(Long.valueOf(entityGkey));
                LocPosition toPos = ufv2.getFinalPlannedPosition();
                if (CarrierIncompatibilityReasonEnum.NONE.equals((Object)dependentUfvIncompReason) && toPos != null && LocTypeEnum.RAILCAR.equals((Object)toPos.getPosLocType())) {
                    this.checkIncompatibilityForLoad(ufv2, ufv2.getFinalPlannedPosition());
                    continue;
                }
                FieldChanges fieldChanges = new FieldChanges();
                fieldChanges.setFieldChange(IInventoryField.UFV_CARRIER_INCOMPATIBLE_REASON, (Object)dependentUfvIncompReason);
                ufvChanges.put(entityGkey, fieldChanges);
                UnitFacilityVisit.updateCarrierIncompatibilityFlag(ufvChanges);
                continue;
            }
            if (!TBD_UNIT.equals(entityPrefix)) continue;
            dependentUfvIncompReason = (CarrierIncompatibilityReasonEnum)((Object)((Map.Entry)entry).getValue());
            TbdUnit tbdUnit = TbdUnit.hydrate(Long.valueOf(entityGkey));
            if (CarrierIncompatibilityReasonEnum.NONE.equals((Object)dependentUfvIncompReason)) {
                tbdUnit.checkIncompatibilityForLoad(inCurPosition);
                continue;
            }
            FieldChanges fieldChanges = new FieldChanges();
            fieldChanges.setFieldChange(IInventoryField.TBDU_CARRIER_INCOMPATIBLE_REASON, (Object)dependentUfvIncompReason);
            tbdunitChanges.put(entityGkey, fieldChanges);
            TbdUnit.updateCarrierIncompatibilityFlag(tbdunitChanges);
        }
        return thisUfvIncompReason;
    }

    public void determineIncompatibilityReasonForAllDependentEntities(LocPosition inCurPosition, boolean inTopCtrChangesReqd) {
        CarrierIncompatibilityReasonEnum thisUfvIncompReason = this.determineIncompReason(inCurPosition, inTopCtrChangesReqd);
        if (CarrierIncompatibilityReasonEnum.NONE.equals((Object)thisUfvIncompReason)) {
            IArgoRailManager railManager = (IArgoRailManager)Roastery.getBean((String)"argoRailManager");
            boolean isPinCompatible = true;
            try {
                EquipNominalLengthEnum eqtypNominalLength = this.getUfvUnit().getUnitPrimaryUe().getUeEquipment().getEqEquipType().getEqtypNominalLength();
                isPinCompatible = railManager.isCarrierIncompatibleForLoad(this.getFinalPlannedPosition(), eqtypNominalLength);
            }
            catch (Exception e) {
                LOGGER.error((Object)"Unable to compute incompatibility reason for WI planned for Railcar slot ");
            }
            if (!isPinCompatible) {
                this.setUfvCarrierIncompatibleReason(CarrierIncompatibilityReasonEnum.PIN_MISMATCHED);
            } else {
                this.setUfvCarrierIncompatibleReason(CarrierIncompatibilityReasonEnum.NONE);
            }
        } else {
            this.setUfvCarrierIncompatibleReason(thisUfvIncompReason);
        }
    }

    private static boolean unitPowerDetailsChanged(FieldChanges inChanges) {
        return inChanges.hasFieldChange(UnitField.UFV_POWER_LAST_FREE_DAY) || inChanges.hasFieldChange(UnitField.UFV_POWER_GUARANTEE_THRU_DAY) || inChanges.hasFieldChange(UnitField.UFV_POWER_PAID_THRU_DAY) || inChanges.hasFieldChange(UnitField.UFV_POWER_GUARANTEE_PARTY);
    }

    private static boolean unitStorageDetailsChanged(FieldChanges inChanges) {
        return inChanges.hasFieldChange(UnitField.UFV_LAST_FREE_DAY) || inChanges.hasFieldChange(UnitField.UFV_GUARANTEE_THRU_DAY) || inChanges.hasFieldChange(UnitField.UFV_PAID_THRU_DAY) || inChanges.hasFieldChange(UnitField.UFV_GUARANTEE_PARTY);
    }

    private static boolean unitLineStorageDetailsChanged(FieldChanges inChanges) {
        return inChanges.hasFieldChange(UnitField.UFV_LINE_LAST_FREE_DAY) || inChanges.hasFieldChange(UnitField.UFV_LINE_GUARANTEE_THRU_DAY) || inChanges.hasFieldChange(UnitField.UFV_LINE_PAID_THRU_DAY) || inChanges.hasFieldChange(UnitField.UFV_LINE_GUARANTEE_PARTY);
    }

    @Nullable
    public LocPosition getFinalPlannedPosition() {
        List<WorkInstruction> wiList = this.getCurrentWiList();
        if (wiList.isEmpty()) {
            return null;
        }
        WorkInstruction wi = wiList.get(0);
        return wi.getWiPosition();
    }

    @Nullable
    public LocPosition getFinalPlannedPositionForMoveKind(@NotNull WiMoveKindEnum inWiMoveKind) {
        WorkInstruction wi = this.getFinalPlanForMoveKind(inWiMoveKind);
        return wi == null ? null : wi.getWiPosition();
    }

    @Nullable
    public WorkInstruction getFinalPlanForMoveKind(@NotNull WiMoveKindEnum inWiMoveKind) {
        List<WorkInstruction> wiList = this.getWorkInstsWithMoveKind(inWiMoveKind);
        return wiList.isEmpty() ? null : wiList.get(0);
    }

    public List<WorkInstruction> getCurrentWiList() {
        return this.getCurrentWiList(true);
    }

    public List<WorkInstruction> getCurrentWiList(boolean inDescOrder) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"WorkInstruction").addDqPredicate(PredicateFactory.eq((IMetafieldId) UnitField.WI_UFV_GKEY, (Object)this.getUfvGkey())).addDqPredicate(PredicateFactory.ne((IMetafieldId)IMovesField.WI_MOVE_STAGE, (Object) WiMoveStageEnum.COMPLETE));
        if (inDescOrder) {
            dq.addDqOrdering(Ordering.desc((IMetafieldId)IMovesField.WI_MOVE_NUMBER));
        } else {
            dq.addDqOrdering(Ordering.asc((IMetafieldId)IMovesField.WI_MOVE_NUMBER));
        }
        List wiList = HibernateApi.getInstance().findEntitiesByDomainQuery(dq);
        return wiList;
    }

    public List<WorkInstruction> getWorkInstsWithMoveKind(WiMoveKindEnum inMoveKind) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"WorkInstruction").addDqPredicate(PredicateFactory.eq((IMetafieldId) UnitField.WI_UFV_GKEY, (Object)this.getUfvGkey())).addDqPredicate(PredicateFactory.ne((IMetafieldId)IMovesField.WI_MOVE_STAGE, (Object) WiMoveStageEnum.COMPLETE)).addDqPredicate(PredicateFactory.eq((IMetafieldId)IMovesField.WI_MOVE_KIND, (Object)inMoveKind)).addDqOrdering(Ordering.desc((IMetafieldId)IMovesField.WI_MOVE_NUMBER));
        List wiList = HibernateApi.getInstance().findEntitiesByDomainQuery(dq);
        return wiList;
    }

    public boolean hasPlannedWi(WiMoveKindEnum[] inMoveKinds) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"WorkInstruction").addDqPredicate(PredicateFactory.eq((IMetafieldId) UnitField.WI_UFV_GKEY, (Object)this.getUfvGkey())).addDqPredicate(PredicateFactory.in((IMetafieldId)IMovesField.WI_MOVE_KIND, (Object[])inMoveKinds)).addDqPredicate(PredicateFactory.eq((IMetafieldId)IMovesField.WI_MOVE_STAGE, (Object) WiMoveStageEnum.PLANNED));
        return HibernateApi.getInstance().findCountByDomainQuery(dq) > 0;
    }

    public boolean hasIncompleteWiForCarrierVisit(WiMoveStageEnum[] inMoveStages, @NotNull CarrierVisit inCarrierVisit) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"WorkInstruction").addDqPredicate(PredicateFactory.eq((IMetafieldId) UnitField.WI_UFV_GKEY, (Object)this.getUfvGkey())).addDqPredicate(PredicateFactory.eq((IMetafieldId)IMovesField.WI_CARRIER_LOC_ID, (Object)inCarrierVisit.getCvId())).addDqPredicate(PredicateFactory.in((IMetafieldId)IMovesField.WI_MOVE_KIND, (Object[])new WiMoveKindEnum[]{WiMoveKindEnum.VeslLoad, WiMoveKindEnum.RailLoad})).addDqPredicate(PredicateFactory.not((IPredicate)PredicateFactory.in((IMetafieldId)IMovesField.WI_MOVE_STAGE, Arrays.asList(inMoveStages))));
        return HibernateApi.getInstance().findCountByDomainQuery(dq) > 0;
    }

    public boolean isCurrentlyHandledByChe() {
        Object[] states = new Object[]{WiMoveStageEnum.FETCH_UNDERWAY, WiMoveStageEnum.CARRY_READY, WiMoveStageEnum.CARRY_UNDERWAY, WiMoveStageEnum.CARRY_COMPLETE, WiMoveStageEnum.PUT_UNDERWAY, WiMoveStageEnum.PUT_COMPLETE};
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"WorkInstruction").addDqPredicate(PredicateFactory.eq((IMetafieldId) UnitField.WI_UFV_GKEY, (Object)this.getUfvGkey())).addDqPredicate(PredicateFactory.eq((IMetafieldId)IMovesField.WI_MOVE_KIND, (Object) WiMoveKindEnum.RailDisch)).addDqPredicate(PredicateFactory.in((IMetafieldId)IMovesField.WI_MOVE_STAGE, (Object[])states));
        return HibernateApi.getInstance().findCountByDomainQuery(dq) > 0;
    }

    @Nullable
    public Object getUfvInboundCarrierClass() {
        Object vesselClass = null;
        if (this.getUfvActualIbCv() != null) {
            vesselClass = this.getUfvActualIbCv().getCarrierVesselClass();
        }
        return vesselClass;
    }

    @Nullable
    public Object getUfvOutboundCarrierClass() {
        Object vesselClass = null;
        if (this.getUfvActualObCv() != null) {
            vesselClass = this.getUfvActualObCv().getCarrierVesselClass();
        }
        return vesselClass;
    }

    @Nullable
    public Object getUfvInboundCvClassification() {
        Object classification = null;
        if (this.getUfvActualIbCv() != null) {
            classification = this.getUfvActualIbCv().getCvClassification();
        }
        return classification;
    }

    @Nullable
    public Object getUfvOutboundCvClassification() {
        Object classification = null;
        if (this.getUfvActualObCv() != null) {
            classification = this.getUfvActualObCv().getCvClassification();
        }
        return classification;
    }

    @Nullable
    public Object getUfvInboundCarrierClassType() {
        Object vesselType = null;
        if (this.getUfvActualIbCv() != null) {
            vesselType = this.getUfvActualIbCv().getCarrierVesselClassType();
        }
        return vesselType;
    }

    @Nullable
    public Object getUfvOutboundCarrierClassType() {
        Object vesselType = null;
        if (this.getUfvActualObCv() != null) {
            vesselType = this.getUfvActualObCv().getCarrierVesselClassType();
        }
        return vesselType;
    }

    @Nullable
    public Boolean getUfvIsInboundCommonCarrier() {
        Boolean isCommonCarrier = Boolean.FALSE;
        if (this.getUfvActualIbCv() != null && this.getUfvActualIbCv().getCvCvd() != null) {
            isCommonCarrier = this.getUfvActualIbCv().getCvCvd().getCvdIsCommonCarrier();
        }
        return isCommonCarrier;
    }

    @Nullable
    public Boolean getUfvIsOutboundCommonCarrier() {
        Boolean isCommonCarrier = Boolean.FALSE;
        if (this.getUfvActualObCv() != null && this.getUfvActualObCv().getCvCvd() != null) {
            isCommonCarrier = this.getUfvActualObCv().getCvCvd().getCvdIsCommonCarrier();
        }
        return isCommonCarrier;
    }

    @Nullable
    public String getUfvRailcarPositionSlot() {
        String railcarPosition = null;
        LocPosition position = this.getUfvLastKnownPosition();
        if (position != null && LocTypeEnum.RAILCAR.equals((Object)position.getPosLocType())) {
            Long railcarVisitGkey = position.getPosLocGkey();
            IArgoRailManager railManager = (IArgoRailManager)Roastery.getBean((String)"argoRailManager");
            railcarPosition = railManager.getRailcarVisitPosition((Serializable)railcarVisitGkey);
        }
        return railcarPosition;
    }

    public Boolean getUfvIsCallingCsiCountry() {
        boolean isCalling = false;
        if (!LocTypeEnum.VESSEL.equals((Object)this.getUfvActualObCv().getCvCarrierMode())) {
            return false;
        }
        UnitCategoryEnum category = this.getUfvUnit().getUnitCategory();
        if (!UnitCategoryEnum.EXPORT.equals((Object)category) && !UnitCategoryEnum.TRANSSHIP.equals((Object)category)) {
            return false;
        }
        RoutingPoint pod = this.getUfvUnit().getUnitRouting().getRtgPOD1();
        if (pod == null) {
            return false;
        }
        String csiCountries = InventoryConfig.CSI_COUNTRIES.getSetting(ContextHelper.getThreadUserContext());
        if (csiCountries == null || csiCountries.isEmpty()) {
            return false;
        }
        csiCountries = csiCountries.replaceAll(" ", "");
        String comma = ",";
        String[] csiCountryCodes = csiCountries.split(comma);
        CarrierVisit obCv = this.getUfvActualObCv();
        VisitDetails cvd = obCv.getCvCvd();
        if (cvd != null && cvd.getCvdItinerary() != null) {
            RoutingPoint cvPoint = obCv.getCvFacility().getFcyRoutingPoint();
            String cvCallNbr = cvd.getCvdOutCallNumber();
            String cvCountryCode = cvPoint.getPointUnLoc().getUnlocCntry().getCntryCode();
            CarrierItinerary itinerary = cvd.getCvdItinerary();
            HashMap countriesVisited = itinerary.getCountriesVisited(cvPoint, cvCallNbr, pod);
            for (String csiCountryCode : csiCountryCodes) {
                if (csiCountryCode.equals(cvCountryCode) || !countriesVisited.containsKey(csiCountryCode)) continue;
                isCalling = true;
                break;
            }
        }
        return isCalling;
    }

    @Nullable
    public Double getRfreqTempRequiredInC() {
        Unit unit = this.getUfvUnit();
        GoodsBase goods = unit.getUnitGoods();
        if (goods == null) {
            return null;
        }
        ReeferRqmnts rfrRqmnts = goods.getGdsReeferRqmnts();
        return rfrRqmnts == null ? null : rfrRqmnts.getRfreqTempRequiredC();
    }

    public Double getRfreqTempRequiredInF() {
        Double tempC = this.getRfreqTempRequiredInC();
        if (tempC == null) {
            return null;
        }
//       return com.navis.framework.util.unit.UnitUtils.convertTo((double)tempC, (MeasurementUnit)TemperatureUnitEnum.C, (MeasurementUnit)TemperatureUnitEnum.F);
        return null;
    }

    @Nullable
    public Date getUfvStorageRuleStartTime() {
        IUnitStorageManager usm = (IUnitStorageManager)Roastery.getBean((String)"unitStorageManager");
        return usm.getRuleStartTimeFor(this, STORAGE);
    }

    @Nullable
    public Date getUfvStorageRuleEndTime() {
        IUnitStorageManager usm = (IUnitStorageManager)Roastery.getBean((String)"unitStorageManager");
        return usm.getRuleEndTimeFor(this, STORAGE);
    }

    @Nullable
    public Date getUfvPowerRuleStartTime() {
        IUnitStorageManager usm = (IUnitStorageManager)Roastery.getBean((String)"unitStorageManager");
        return usm.getRuleStartTimeFor(this, POWER);
    }

    @Nullable
    public Date getUfvPowerRuleEndTime() {
        IUnitStorageManager usm = (IUnitStorageManager)Roastery.getBean((String)"unitStorageManager");
        return usm.getRuleEndTimeFor(this, POWER);
    }

    public void applyFieldChanges(FieldChanges inFieldChanges) {
        Double rfreqTempRequiredC;
        ReeferRqmnts reeferRqmnts;
        Unit unit;
        GoodsBase unitGoods;
        FieldChange fieldChange;
        if (inFieldChanges.hasFieldChange(UnitField.UFV_GOODS_AND_CTR_WT_KG_VERFIED_GROSS) && (fieldChange = inFieldChanges.getFieldChange(UnitField.UFV_GOODS_AND_CTR_WT_KG_VERFIED_GROSS)).getNewValue() != null) {
//            try {
//                UnitUtils.validateUnitVgmWeight(this.getUfvUnit(), (Double)fieldChange.getNewValue(), true);
//            }
//            catch (BizViolation bv) {
//                throw BizFailure.wrap((Throwable)bv);
//            }
        }
        super.applyFieldChanges(inFieldChanges);
        if (inFieldChanges.hasFieldChange(UnitField.UFV_TEMP_REQUIRED_C) && (unitGoods = (unit = this.getUfvUnit()).getUnitGoods()) != null && (reeferRqmnts = unitGoods.getGdsReeferRqmnts()) != null && (rfreqTempRequiredC = reeferRqmnts.getRfreqTempRequiredC()) != null && !unit.isReefer()) {
            unit.updateRequiresPower(true);
        }
    }

    @Nullable
    public IPropertyKey isUfvReadyToLoadByHatchClerk(CarrierVisit inCv) throws BizViolation {
        boolean isDirectLoad;
        String unitId = this.getUfvUnit().getUnitId();
        BizViolation bv = null;
        IPropertyKey outPropKey = null;
        Boolean tmp = this.getUfvIsDirectIbToObMove();
        boolean bl = isDirectLoad = tmp != null && tmp != false;
        if (ArgoConfig.EC_FLAVOR.isSetTo("NONE", ContextHelper.getThreadUserContext())) {
            LOGGER.info((Object)"Skipped Work Instruction Move Stage validation on HC Load to Vessel because the facility uses NO Equipment Control");
            return outPropKey;
        }
        if (!isDirectLoad) {
            List<WorkInstruction> wiList;
            if (!LocTypeEnum.YARD.equals((Object)this.getUfvLastKnownPosition().getPosLocType())) {
                bv = BizViolation.create((IPropertyKey) IInventoryPropertyKeys.UNITS_CAN_ONLY_LOAD_FROM_YARD, bv, (Object)unitId);
            }
            if ((wiList = this.getCurrentWiList()) != null && !wiList.isEmpty()) {
                WorkInstruction currWi = wiList.get(0);
                if (LocTypeEnum.VESSEL.equals((Object)currWi.getWiCarrierLocType()) && currWi.getWiCarrierLocId() != null && currWi.getWiCarrierLocId().equals(inCv.getCvId())) {
                    if (currWi.getWiEcStateItvDispatch().booleanValue()) {
                        if (!WiMoveStageEnum.CARRY_COMPLETE.equals((Object)currWi.getWiMoveStage()) && !WiMoveStageEnum.CARRY_UNDERWAY.equals((Object)currWi.getWiMoveStage())) {
                            outPropKey = IInventoryPropertyKeys.UNIT_NOT_AT_QC;
                        }
                    } else if (!WiMoveStageEnum.CARRY_COMPLETE.equals((Object)currWi.getWiMoveStage()) && !WiMoveStageEnum.PUT_UNDERWAY.equals((Object)currWi.getWiMoveStage())) {
                        outPropKey = IInventoryPropertyKeys.UNIT_NOT_AT_QC;
                    }
                } else {
                    outPropKey = IInventoryPropertyKeys.UNIT_NO_LOAD_WI;
                }
            } else {
                outPropKey = IInventoryPropertyKeys.NO_WI_FOR_UNIT;
            }
        } else if (!LocTypeEnum.TRUCK.equals((Object)this.getUfvLastKnownPosition().getPosLocType())) {
            bv = BizViolation.create((IPropertyKey) IInventoryPropertyKeys.UNITS_CAN_ONLY_LOAD_FROM_TRUCK, bv, (Object)unitId);
        }
        if (bv != null) {
            throw bv;
        }
        return outPropKey;
    }

    @Nullable
    public LocPosition getTruckPositionIfAvailable() {
        LocPosition lastKnownPos = this.getUfvLastKnownPosition();
        if (lastKnownPos.isTruckPosition()) {
            return lastKnownPos;
        }
        LocPosition arrivePosition = this.getUfvArrivePosition();
        if (arrivePosition.isTruckPosition()) {
            return arrivePosition;
        }
        return null;
    }

    public Class getArchiveClass() {
//        return ArchiveUnitFacilityVisit.class;
        return null;
    }

    public boolean doArchive() {
        return true;
    }

    @Nullable
    public Long getUfvIbCvDischargeCompleteTimeOfDay() {
        Date dischargeDate;
        Calendar calendar = Calendar.getInstance(ContextHelper.getThreadUserTimezone());
        CarrierVisit cv = this.getUfvUnit().getUnitDeclaredIbCv();
        VisitDetails cvd = cv == null ? null : cv.getCvCvd();
        Long retValue = null;
        Date date = dischargeDate = cvd == null ? null : cvd.getCvdTimeDischargeComplete();
        if (dischargeDate != null) {
            calendar.setTime(dischargeDate);
            int hours = calendar.get(11);
            int minutes = calendar.get(12);
            retValue = (long)(hours * 100) + (long)minutes;
        }
        return retValue;
    }

    @Nullable
    public static IPredicate formUfvIbCvDischargeCompleteTimeOfDayPredicate(IMetafieldId inMetafieldId, PredicateVerbEnum inVerb, Object inValue) {
        if (PredicateVerbEnum.EQ.equals((Object)inVerb)) {
            return PredicateFactory.eq((IMetafieldId)inMetafieldId, (Object)inValue);
        }
        if (PredicateVerbEnum.GT.equals((Object)inVerb)) {
            return PredicateFactory.gt((IMetafieldId)inMetafieldId, (Object)inValue);
        }
        if (PredicateVerbEnum.GE.equals((Object)inVerb)) {
            return PredicateFactory.ge((IMetafieldId)inMetafieldId, (Object)inValue);
        }
        if (PredicateVerbEnum.LT.equals((Object)inVerb)) {
            return PredicateFactory.lt((IMetafieldId)inMetafieldId, (Object)inValue);
        }
        if (PredicateVerbEnum.LE.equals((Object)inVerb)) {
            return PredicateFactory.le((IMetafieldId)inMetafieldId, (Object)inValue);
        }
        return null;
    }

    protected UnitFacilityVisit clone(Unit inOutNewUnit) throws BizViolation {
        UnitFacilityVisit newUfv = UnitFacilityVisit.createUnitFacilityVisit(inOutNewUnit, this.getUfvFacility(), this.getUfvArrivePosition(), this.getUfvObCv());
        newUfv.setUfvActualIbCv(this.getUfvActualIbCv());
        newUfv.setUfvActualObCv(this.getUfvActualObCv());
        newUfv.setUfvArrivePosition(this.getUfvArrivePosition());
        newUfv.setUfvDlvTimeAppntmnt(this.getUfvDlvTimeAppntmnt());
        newUfv.setUfvHandlingReason(this.getUfvHandlingReason());
        newUfv.setUfvAnomalyForExpDkng(this.getUfvAnomalyForExpDkng());
        newUfv.setUfvFlexDate01(this.getUfvFlexDate01());
        newUfv.setUfvFlexDate02(this.getUfvFlexDate02());
        newUfv.setUfvFlexDate03(this.getUfvFlexDate03());
        newUfv.setUfvFlexDate04(this.getUfvFlexDate04());
        newUfv.setUfvFlexDate05(this.getUfvFlexDate05());
        newUfv.setUfvFlexDate06(this.getUfvFlexDate06());
        newUfv.setUfvFlexDate07(this.getUfvFlexDate07());
        newUfv.setUfvFlexDate08(this.getUfvFlexDate08());
        newUfv.setUfvFlexString01(this.getUfvFlexString01());
        newUfv.setUfvFlexString02(this.getUfvFlexString02());
        newUfv.setUfvFlexString03(this.getUfvFlexString03());
        newUfv.setUfvFlexString04(this.getUfvFlexString04());
        newUfv.setUfvFlexString05(this.getUfvFlexString05());
        newUfv.setUfvFlexString06(this.getUfvFlexString06());
        newUfv.setUfvFlexString07(this.getUfvFlexString07());
        newUfv.setUfvFlexString08(this.getUfvFlexString08());
        newUfv.setUfvFlexString09(this.getUfvFlexString09());
        newUfv.setUfvFlexString10(this.getUfvFlexString10());
        newUfv.setUfvGuaranteeParty(this.getUfvGuaranteeParty());
        newUfv.setUfvGuaranteeThruDay(this.getUfvGuaranteeThruDay());
        newUfv.setUfvHorizon(this.getUfvHorizon());
        newUfv.setUfvIntendedObCv(this.getUfvIntendedObCv());
        newUfv.setUfvIsSparcsStopped(this.getUfvIsSparcsStopped());
        newUfv.setUfvLastFreeDay(this.getUfvLastFreeDay());
        newUfv.setUfvLastKnownPosition(this.getUfvLastKnownPosition());
        newUfv.setUfvPaidThruDay(this.getUfvPaidThruDay());
        newUfv.setUfvPowerGuaranteeParty(this.getUfvPowerGuaranteeParty());
        newUfv.setUfvPowerGuaranteeThruDay(this.getUfvPowerGuaranteeThruDay());
        newUfv.setUfvPowerLastFreeDay(this.getUfvPowerLastFreeDay());
        newUfv.setUfvPowerPaidThruDay(this.getUfvPowerPaidThruDay());
        newUfv.setUfvRestowType(this.getUfvRestowType());
        newUfv.setUfvSequence(this.getUfvSequence());
        newUfv.setUfvSparcsNote(this.getUfvSparcsNote());
        newUfv.setUfvTimeComplete(this.getUfvTimeComplete());
        newUfv.setUfvTimeEcIn(this.getUfvTimeEcIn());
        newUfv.setUfvTimeEcOut(this.getUfvTimeEcOut());
        newUfv.setUfvTimeIn(this.getUfvTimeIn());
        newUfv.setUfvTimeInventory(this.getUfvTimeInventory());
        newUfv.setUfvTimeOfLastMove(this.getUfvTimeOfLastMove());
        newUfv.setUfvTimeOfLoading(this.getUfvTimeOfLoading());
        newUfv.setUfvTimeOut(this.getUfvTimeOut());
        newUfv.setUfvTransitState(this.getUfvTransitState());
        newUfv.setUfvValidateMarryErrors(this.getUfvValidateMarryErrors());
        newUfv.setUfvVerifiedForLoad(this.getUfvVerifiedForLoad());
        newUfv.setUfvVerifiedYardPosition(this.getUfvVerifiedYardPosition());
        newUfv.setUfvVisibleInSparcs(this.getUfvVisibleInSparcs());
        newUfv.setUfvVisitState(this.getUfvVisitState());
        newUfv.setUfvYardStowageType(this.getUfvYardStowageType());
        newUfv.setUfvIsRASPriority(this.getUfvIsRASPriority());
        if (this.equals(this.getUfvUnit().getUnitActiveUfvNowActive())) {
            inOutNewUnit.setUnitActiveUfv(newUfv);
        }
        return newUfv;
    }

    @Nullable
    public String getArrivalCarrierVehicleType() {
        LocPosition arrivePos = this.getUfvArrivePosition();
        if (arrivePos != null) {
            ILocation location = arrivePos.resolveLocation();
            CarrierVisit cv = arrivePos.resolveInboundCarrierVisit();
            if (cv != null && cv.getCvCvd() != null) {
                return cv.getCvCvd().getCarrierVehicleType(location);
            }
        }
        return null;
    }

    @Nullable
    public String getArrivalCarrierVehicleSeq() {
        LocPosition arrivePos = this.getUfvArrivePosition();
        if (arrivePos != null) {
            ILocation location = arrivePos.resolveLocation();
            CarrierVisit cv = arrivePos.resolveInboundCarrierVisit();
            if (cv != null && cv.getCvCvd() != null) {
                return cv.getCvCvd().getCarrierVehicleSeq(location);
            }
        }
        return null;
    }

    @Nullable
    public String getArrivalCarrierId() {
        CarrierVisit cv = this.getUfvActualIbCv();
        if (cv != null && LocTypeEnum.TRAIN.equals((Object)cv.getCvCarrierMode())) {
            return cv.getCvId();
        }
        return null;
    }

    @Nullable
    public String getArrivalCarrierOperator() {
        CarrierVisit cv = this.getUfvActualIbCv();
        if (cv != null && LocTypeEnum.TRAIN.equals((Object)cv.getCvCarrierMode())) {
            return cv.getCvOperator() == null ? null : cv.getCvOperator().getBzuId();
        }
        return null;
    }

    @Nullable
    public Date getArrivalCarrierLastMoveTime() {
        CarrierVisit cv = this.getUfvActualIbCv();
        if (cv != null && LocTypeEnum.TRAIN.equals((Object)cv.getCvCarrierMode())) {
            return cv.getCvCvd().getRailLastMoveTime();
        }
        return null;
    }

    @Nullable
    public String getArrivalCarrierVehicleDestination() {
        LocPosition arrivePos = this.getUfvArrivePosition();
        if (arrivePos != null) {
            ILocation location = arrivePos.resolveLocation();
            CarrierVisit cv = arrivePos.resolveInboundCarrierVisit();
            if (cv != null && cv.getCvCvd() != null) {
                return cv.getCvCvd().getCarrierVehicleDestination(location);
            }
        }
        return null;
    }

    @Nullable
    public String getArrivalCarrierVehicleFlatCarType() {
        LocPosition arrivePos = this.getUfvArrivePosition();
        if (arrivePos != null) {
            ILocation location = arrivePos.resolveLocation();
            CarrierVisit cv = arrivePos.resolveInboundCarrierVisit();
            if (cv != null && cv.getCvCvd() != null) {
                return cv.getCvCvd().getCarrierVehicleFlatCarType(location);
            }
        }
        return null;
    }

    @Nullable
    public String getArrivalCarrierDirection() {
        CarrierVisit cv = this.getUfvActualIbCv();
        if (cv != null && LocTypeEnum.TRAIN.equals((Object)cv.getCvCarrierMode())) {
            return cv.getCvCvd().getCarrierDirection();
        }
        return null;
    }

    @Nullable
    public Date getArrivalCarrierCreated() {
        CarrierVisit cv = this.getUfvActualIbCv();
        if (cv != null && LocTypeEnum.TRAIN.equals((Object)cv.getCvCarrierMode())) {
            return cv.getCvCvd().getCarrierVisitCreated();
        }
        return null;
    }

    @Nullable
    public String getArrivalCarrierCreator() {
        CarrierVisit cv = this.getUfvActualIbCv();
        if (cv != null && LocTypeEnum.TRAIN.equals((Object)cv.getCvCarrierMode())) {
            return cv.getCvCvd().getCarrierVisitCreator();
        }
        return null;
    }

    public void updateUfvIsDirectIbToObMove(Boolean inUfvIbToObCarrierDirect) {
        this.setUfvIsDirectIbToObMove(inUfvIbToObCarrierDirect);
    }

//    public boolean matches(ISavedQuery inFilter) {
//        boolean matches = true;
//        if (inFilter != null) {
//            IValueHolder predicateVao = inFilter.getQueryPredicateVao();
//            matches = new SavedPredicate(predicateVao).getExecutablePredicate().isSatisfiedBy((IValueSource)this);
//        }
//        return matches;
//    }

    private static Boolean isValidLocationChange(LocTypeEnum inOldPosLocType, LocTypeEnum inNewPosLocType) {
        return LocTypeEnum.RAILCAR.equals((Object)inOldPosLocType) && LocTypeEnum.RAILCAR.equals((Object)inNewPosLocType) || LocTypeEnum.TRAIN.equals((Object)inOldPosLocType) && LocTypeEnum.TRAIN.equals((Object)inNewPosLocType) || LocTypeEnum.TRAIN.equals((Object)inOldPosLocType) && LocTypeEnum.RAILCAR.equals((Object)inNewPosLocType) || LocTypeEnum.RAILCAR.equals((Object)inOldPosLocType) && LocTypeEnum.TRAIN.equals((Object)inNewPosLocType);
    }

    public String getLastFreeDateOverrideRecordedBy() {
        return Event.getEventMetaFieldLastRecordedBy((IServiceable)this.getUfvUnit(), (String) IInventoryField.UFV_LAST_FREE_DAY.getFieldId(), (Serializable)EventType.resolveIEventType((IEventType) EventEnum.UNIT_STORAGE_UPDATE).getEvnttypeGkey());
    }

    @Nullable
    public Boolean isUfvReadyToDischByHatchClerk() throws BizViolation {
        boolean isDirectDisch;
        boolean isTranActiveForUnit = false;
        Boolean tmp = this.getUfvIsDirectIbToObMove();
        boolean bl = isDirectDisch = tmp != null && tmp != false;
        if (isDirectDisch) {
            IArgoRoadManager argoRoadMgr = (IArgoRoadManager)Roastery.getBean((String)"argoRoadManager");
            isTranActiveForUnit = argoRoadMgr.doesUnitHaveActiveTransaction((Serializable)this.getUfvUnit().getUnitGkey());
        }
        return isTranActiveForUnit;
    }

    public Boolean isUfvSwappableForVesselLoad(@NotNull UnitFacilityVisit inUfvToBeLoaded) {
        return this.isUfvSwappableForVesselLoad(inUfvToBeLoaded, true);
    }

    public Boolean isUfvSwappableForVesselLoad(@NotNull UnitFacilityVisit inUfvToBeLoaded, boolean inCheckDoorDirections) {
        String loadUfvStowFactor;
        String plannedUfvStowFactor = this.obtainUfvStowFactor();
        if (!ObjectUtils.equals((Object)plannedUfvStowFactor, (Object)(loadUfvStowFactor = inUfvToBeLoaded.obtainUfvStowFactor()))) {
            LOGGER.info((Object)("isUfvSwappableForVesselLoad found incompatible stow factors: " + plannedUfvStowFactor + " versus " + loadUfvStowFactor));
            return false;
        }
        boolean ctrIsHazardous = inUfvToBeLoaded.getUfvUnit().getUnitIsHazard();
        boolean nextCtrIsHazardous = this.getUfvUnit().getUnitIsHazard();
        if (ctrIsHazardous || nextCtrIsHazardous) {
            LOGGER.debug((Object)("No Load Swap calculated for hazardous containers. Hazardous status for container [" + inUfvToBeLoaded.getUfvUnit().getUnitId() + "] being handled is [" + ctrIsHazardous + "] and Hazardous status for next planned container [" + this.getUfvUnit().getUnitId() + "] is [" + nextCtrIsHazardous + "]."));
            return false;
        }
        Double plannedWeightKg = this.getUfvUnit().getUnitGoodsAndCtrWtKg();
        Double loadUfvWeightKg = inUfvToBeLoaded.getUfvUnit().getUnitGoodsAndCtrWtKg();
        Double weightTolerance = InventoryControlUtils.findMarginWeight(plannedWeightKg > loadUfvWeightKg ? loadUfvWeightKg : plannedWeightKg);
        if (Math.abs(plannedWeightKg - loadUfvWeightKg) > weightTolerance) {
            LOGGER.info((Object)("isUfvSwappableForVesselLoad found incompatible weights: " + plannedWeightKg + "(" + this + ") versus " + loadUfvWeightKg + "(" + inUfvToBeLoaded + ")" + " not within tolerance " + weightTolerance));
            return false;
        }
        if (inCheckDoorDirections) {
            DoorDirectionEnum loadingPlannedDoors;
            WorkInstruction plannedWi = this.getFinalPlanForMoveKind(WiMoveKindEnum.VeslLoad);
            WorkInstruction loadUfvWi = inUfvToBeLoaded.getFinalPlanForMoveKind(WiMoveKindEnum.VeslLoad);
            if (plannedWi == null) {
                LOGGER.error((Object)("isUfvSwappableForVesselLoad found no load plan for the planned load " + this));
                return false;
            }
            if (loadUfvWi == null) {
                LOGGER.error((Object)("isUfvSwappableForVesselLoad found no load plan for the load to be swapped: " + inUfvToBeLoaded));
                return false;
            }
            DoorDirectionEnum thisPlannedDoors = plannedWi.getWiDoorDirection();
            if (thisPlannedDoors.equals((Object)(loadingPlannedDoors = loadUfvWi.getWiDoorDirection()))) {
                if (DoorDirectionEnum.UNKNOWN.equals((Object)loadingPlannedDoors) || DoorDirectionEnum.UNKNOWN.equals((Object)thisPlannedDoors)) {
                    LOGGER.warn((Object)("isUfvSwappableForVesselLoad found unexpected planned doors: " + thisPlannedDoors.getName() + "(" + this + ") versus " + loadingPlannedDoors.getName() + "(" + inUfvToBeLoaded + ")"));
                    return false;
                }
            } else if (DoorDirectionEnum.ANY.equals((Object)thisPlannedDoors) || DoorDirectionEnum.ANY.equals((Object)loadingPlannedDoors)) {
                LOGGER.warn((Object)("isUfvSwappableForVesselLoad found always acceptable planned doors: " + thisPlannedDoors.getName() + "(" + this + ") versus " + loadingPlannedDoors.getName() + "(" + inUfvToBeLoaded + ")"));
            } else {
                LOGGER.info((Object)("isUfvSwappableForVesselLoad found incompatible planned orientations: " + thisPlannedDoors.getName() + "(" + this + ") versus " + loadingPlannedDoors.getName() + "(" + inUfvToBeLoaded + ")"));
                return false;
            }
        }
        return true;
    }

    public String obtainUfvStowFactor() {
        String plannedUfvStowFactor = super.getUfvStowFactor() != null ? super.getUfvStowFactor() : "";
        return plannedUfvStowFactor;
    }

    public Boolean isUfvSwappableWithTbdUnitForVesselLoad(@NotNull TbdUnit inPlannedTbdUnit) {
        String loadUfvStowFactor = this.obtainUfvStowFactor();
        String plannedTbdUnitStowFactor = inPlannedTbdUnit.getTbduStowFactor();
        return ObjectUtils.equals((Object)loadUfvStowFactor, (Object)plannedTbdUnitStowFactor);
    }

    @Nullable
    public Date getUfvLineStorageRuleStartTime() {
        IUnitStorageManager usm = (IUnitStorageManager)Roastery.getBean((String)"unitStorageManager");
        return usm.getRuleStartTimeFor(this, LINE_STORAGE);
    }

    @Nullable
    public Date getUfvLineStorageRuleEndTime() {
        IUnitStorageManager usm = (IUnitStorageManager)Roastery.getBean((String)"unitStorageManager");
        return usm.getRuleEndTimeFor(this, LINE_STORAGE);
    }

    public Long getUfvLineStorageDaysTotal() {
        IUnitStorageManager usm = (IUnitStorageManager)Roastery.getBean((String)"unitStorageManager");
        return Long.valueOf(usm.getStorageDaysTotal(this, LINE_STORAGE));
    }

    public Boolean getUfvLineStorageIsPaid() {
        IUnitStorageManager usm = (IUnitStorageManager)Roastery.getBean((String)"unitStorageManager");
        Boolean isStoragePaid = Boolean.TRUE;
        try {
            usm.validateIsStorageOwed(this, ChargeableUnitEventTypeEnum.LINE_STORAGE.getKey());
        }
        catch (BizViolation inBizViolation) {
            isStoragePaid = Boolean.FALSE;
            LOGGER.debug((Object)("usm.validateIsLineStorageOwed thrown error- setting the return value to false" + (Object)((Object)inBizViolation)));
        }
        return isStoragePaid;
    }

    public Long getUfvLineStorageDaysOwed() {
        IUnitStorageManager usm = (IUnitStorageManager)Roastery.getBean((String)"unitStorageManager");
        return Long.valueOf(usm.getStorageDaysOwed(this, LINE_STORAGE));
    }

    public static IValueHolder getNextWiValueHolder(Serializable inUfvGkey, MetafieldIdList inFields) {
        UnitYardVisit uyv;
        WorkInstruction wi;
        UnitFacilityVisit ufv = (UnitFacilityVisit)HibernateApi.getInstance().get(UnitFacilityVisit.class, inUfvGkey);
        if (ufv != null && (wi = (uyv = ufv.getUyvForYard(ContextHelper.getThreadYard())).getNextWorkInstruction()) != null) {
            ValueObject vao = new ValueObject("WorkInstruction");
            vao.setEntityPrimaryKey(wi.getPrimaryKey());
            for (IMetafieldId fieldId : inFields) {
                vao.setFieldValue(fieldId, wi.getFieldValue(fieldId));
            }
            return vao;
        }
        return null;
    }

    public AbstractBin getResidingYardBlock() {
        AbstractBin block = null;
        if (LocTypeEnum.YARD.equals((Object)this.getUfvLastKnownPosition().getPosLocType()) && this.getUfvLastKnownPosition().getPosBin() != null) {
            block = this.getUfvLastKnownPosition().getPosBin().findAncestorBinAtLevel(Long.valueOf(2L));
        }
        return block;
    }

    public Long getUfvMonitorSlopTime() {
        long slopTime = 1800000L;
        return slopTime;
    }

    public boolean treatAsFlexLoadEmpty() {
//        ConfigProvider configProvider = (ConfigProvider)Roastery.getBean((String)"configProvider");
//        Boolean flexLoadEnabled = (Boolean)configProvider.getEffectiveSetting(ContextHelper.getThreadUserContext(), (IConfig)InventoryConfig.AUTOSTOW_ALLOW_REROUTING_EMPTIES);
//        LOGGER.debug((Object)("FLEXLOAD Setting Enabled? " + flexLoadEnabled));
//        if (flexLoadEnabled.booleanValue()) {
//            Routing unitRouting = this.getUfvUnit().getUnitRouting();
//            RoutingPoint projectedPOD = unitRouting.getRtgProjectedPOD();
//            LOGGER.debug((Object)("ProjectedPoD: " + (Object)projectedPOD));
//            if (projectedPOD != null) {
//                FreightKindEnum freightKindEnum = this.getUfvUnit().getUnitFreightKind();
//                if (!FreightKindEnum.MTY.equals((Object)freightKindEnum)) {
//                    LOGGER.warn((Object)("Received projectedPoD for unit " + this + " when it has freight kind " + (Object)freightKindEnum + ". NOT treating it as " + "FlexLoadMT"));
//                    return false;
//                }
//                LOGGER.debug((Object)("Treating unit " + this + " as FlexLoadMT"));
//                return true;
//            }
//        }
        LOGGER.debug((Object)("NOT Treating unit " + this + " as FlexLoadMT"));
        return false;
    }

    public void setTransferLocation(TransferLocationEnum inTransferLocation) {
        this.setUfvPreferredTransferLocation(inTransferLocation);
    }

    public void updateCurrentDoorDir(DoorDirectionEnum inCurrentDoorDir) {
        this.setUfvDoorDirection(inCurrentDoorDir);
    }

    public static void updateCarrierIncompatibilityFlag(final Map<Long, FieldChanges> inUfvChanges) {
        final UserContext systemContext = ContextHelper.getSystemUserContextForScope((IScopeEnum)ScopeEnum.COMPLEX, (Serializable)ContextHelper.getThreadComplexKey());
        PersistenceTemplate pt = new PersistenceTemplate(systemContext);
        UserContext currentUC = TransactionParms.getBoundParms().getUserContext();
        pt.invoke(new CarinaPersistenceCallback(){

            public void doInTransaction() {
                try {
                    TransactionParms.getBoundParms().setUserContext(systemContext);
                    if (inUfvChanges != null && !inUfvChanges.isEmpty()) {
                        for (Long aLong : inUfvChanges.keySet()) {
                            FieldChanges changes;
                            Long entityGkey = aLong;
                            if (entityGkey == null || (changes = (FieldChanges)inUfvChanges.get(entityGkey)) == null) continue;
                            UnitFacilityVisit unitFacilityVisit = (UnitFacilityVisit)HibernateApi.getInstance().load(UnitFacilityVisit.class, (Serializable)entityGkey);
                            if (unitFacilityVisit == null) {
                                LOGGER.warn((Object)("Failed to load an entity of type: " + UnitFacilityVisit.class.getName() + "with gkey: " + entityGkey));
                                continue;
                            }
                            CarrierIncompatibilityReasonEnum newValue = null;
                            if (changes.getFieldChange(IInventoryField.UFV_CARRIER_INCOMPATIBLE_REASON).getNewValue() != null) {
                                newValue = (CarrierIncompatibilityReasonEnum)((Object)changes.getFieldChange(IInventoryField.UFV_CARRIER_INCOMPATIBLE_REASON).getNewValue());
                            }
                            unitFacilityVisit.setFieldValue(IInventoryField.UFV_CARRIER_INCOMPATIBLE_REASON, (Object)newValue);
                        }
                    }
                }
                catch (Exception ex) {
                    LOGGER.error((Object)"Unable to set pin mismatch compatibility for Units");
                }
            }
        });
        TransactionParms.getBoundParms().setUserContext(currentUC);
    }

    public boolean isDirectDelivery() {
        return !(!Boolean.TRUE.equals(this.getUfvIsDirectIbToObMove()) && !this.getUfvUnit().isDirectDelivery(this) || this.getLastYardPosition() != null && !this.getLastYardPosition().isPhonyYardPosition());
    }

    public void updateUfvTimeIn(Date inDate) {
        this.setUfvTimeIn(inDate);
    }

    protected void updateUfvComboTimeOutTimeComplete(final Date inTimeOut) {
        Unit curUnit = this.getUfvUnit();
        if (curUnit.getUnitCombo() != null) {
            try {
                curUnit.applyUpdateOnUnitCombo(new AbstractUnitUpdate(){

                    @Override
                    public void apply(Unit inUnit) throws BizViolation {
                        UnitFacilityVisit ufv = UnitFacilityVisit.this.getEqUfv(inUnit);
                        if (ufv != null) {
                            ufv.setUfvTimeOut(inTimeOut);
                            ufv.setUfvTimeComplete(inTimeOut);
                        }
                    }

                    @Override
                    public boolean allowUpdate(Unit inUnit) {
                        return true;
                    }

                    @Override
                    public boolean applyOnAll(Unit inUnit) {
                        return true;
                    }
                });
            }
            catch (BizViolation inBizViolation) {
                LOGGER.error((Object)"Failed updating UfvTimeComplete", (Throwable)inBizViolation);
            }
        } else {
            this.setUfvTimeOut(inTimeOut);
            this.setUfvTimeComplete(inTimeOut);
        }
    }

    public void updateUfvTimeOfLoading(Date inDate) {
        this.setUfvTimeOfLoading(inDate);
    }

    public void updateUfvRestowType(RestowTypeEnum inRestowTypeEnum) {
        this.setUfvRestowType(inRestowTypeEnum);
    }

    public void updateUfvVisibleInSparcs(Boolean inVisibleInSparcs) {
        this.setUfvVisibleInSparcs(inVisibleInSparcs);
    }

    public void updateUfvCasTransactionReference(String inUfvCasTransRef) {
        this.setUfvCasTransactionReference(inUfvCasTransRef);
    }

    public CarrierIncompatibilityReasonEnum checkIncompatibilityForLoad(UnitFacilityVisit inUfv, LocPosition inNewPos) {
        CarrierIncompatibilityReasonEnum reasonEnum = CarrierIncompatibilityReasonEnum.NONE;
        IArgoRailManager railManager = (IArgoRailManager)Roastery.getBean((String)"argoRailManager");
        EquipNominalLengthEnum eqtypNominalLength = inUfv.getUfvUnit().getUnitPrimaryUe().getUeEquipment().getEqEquipType().getEqtypNominalLength();
        try {
            boolean isCompatibile = railManager.isCarrierIncompatibleForLoad(inNewPos, eqtypNominalLength);
            FieldChanges fieldChanges = new FieldChanges();
            HashMap<Long, FieldChanges> ufvChanges = new HashMap<Long, FieldChanges>();
            if (!isCompatibile) {
                reasonEnum = CarrierIncompatibilityReasonEnum.PIN_MISMATCHED;
            }
            fieldChanges.setFieldChange(IInventoryField.UFV_CARRIER_INCOMPATIBLE_REASON, (Object)reasonEnum);
            ufvChanges.put(inUfv.getUfvGkey(), fieldChanges);
            UnitFacilityVisit.updateCarrierIncompatibilityFlag(ufvChanges);
        }
        catch (BizViolation inBizViolation) {
            LOGGER.error((Object)("Unable to compute incompatibility reason for WI planned for Railcar slot " + inNewPos.getPosSlot()));
            return reasonEnum;
        }
        return reasonEnum;
    }

    private boolean isPlanNotInYardButDroppedInYard(@NotNull LocPosition inNewPos) {
        WorkInstruction workInstruction = this.getNextWorkInstruction();
        if (workInstruction == null) {
            return false;
        }
        if (!LocTypeEnum.YARD.equals((Object)workInstruction.getWiToPosition().getPosLocType())) {
            boolean isYardPosition = inNewPos.isYardPosition();
            if (isYardPosition && LOGGER.isDebugEnabled()) {
                LOGGER.debug((Object) String.format("%s is planned to %s but dropped in Yard ", new Object[]{workInstruction.toString(), workInstruction.getWiToPosition().getPosLocType()}));
            }
            return isYardPosition;
        }
        return false;
    }

    private void updateUfvVerifiedForLoad(Unit inUnit, FieldChanges inOutMoreChanges) {
        UnitFacilityVisit activeUfv;
        if (inUnit != null && (activeUfv = inUnit.getUnitActiveUfv()) != null) {
            if (RestowTypeEnum.RESTOW.equals((Object)this.getUfvRestowType())) {
                inOutMoreChanges.setFieldChange(IInventoryField.UFV_VERIFIED_FOR_LOAD, (Object) Boolean.TRUE);
            } else {
                inOutMoreChanges.setFieldChange(IInventoryField.UFV_VERIFIED_FOR_LOAD, (Object) Boolean.FALSE);
            }
        }
    }

    static {
        BIZ_FIELD_LIST_NOP = new BizFieldList();
        BIZ_FIELD_LIST_NOP.add(UnitField.UFV_LINE_OPERATOR, BizRoleEnum.LINEOP);
        BIZ_FIELD_LIST_NOP.add(UnitField.UFV_ACTUAL_INBOUND_CV_OPERATOR, BizRoleEnum.RAILROAD);
        BIZ_FIELD_LIST_NOP.add(UnitField.UFV_ACTUAL_INBOUND_CV_OPERATOR, BizRoleEnum.HAULIER);
        BIZ_FIELD_LIST_NOP.add(UnitField.UFV_INTENDED_OUTBOUND_CV_OPERATOR, BizRoleEnum.HAULIER);
        BIZ_FIELD_LIST_NOP.add(UnitField.UFV_INTENDED_OUTBOUND_CV_OPERATOR, BizRoleEnum.RAILROAD);
        BIZ_FIELD_LIST_NOP.add(UnitField.UFV_RTG_TRUCKING_COMPANY, BizRoleEnum.HAULIER, false);
        BIZ_FIELD_LIST_NOP.add(UnitField.UFV_SHIPPER_BZU, BizRoleEnum.SHIPPER);
        BIZ_FIELD_LIST_NOP.add(UnitField.UFV_CONSIGNEE_BZU, BizRoleEnum.SHIPPER);
        BIZ_FIELD_LIST_NOP.add(UnitField.UFV_AGENT1, BizRoleEnum.AGENT);
        BIZ_FIELD_LIST_NOP.add(UnitField.UFV_AGENT2, BizRoleEnum.AGENT);
        BIZ_FIELD_LIST_ALL = new BizFieldList();
        BIZ_FIELD_LIST_ALL.add(UnitField.UFV_LINE_OPERATOR, BizRoleEnum.LINEOP);
        BIZ_FIELD_LIST_ALL.add(UnitField.UFV_ACTUAL_INBOUND_CV_OPERATOR, BizRoleEnum.VESSELOP);
        BIZ_FIELD_LIST_ALL.add(UnitField.UFV_ACTUAL_INBOUND_CV_OPERATOR, BizRoleEnum.RAILROAD);
        BIZ_FIELD_LIST_ALL.add(UnitField.UFV_ACTUAL_INBOUND_CV_OPERATOR, BizRoleEnum.HAULIER);
        BIZ_FIELD_LIST_ALL.add(UnitField.UFV_INTENDED_OUTBOUND_CV_OPERATOR, BizRoleEnum.VESSELOP);
        BIZ_FIELD_LIST_ALL.add(UnitField.UFV_INTENDED_OUTBOUND_CV_OPERATOR, BizRoleEnum.RAILROAD);
        BIZ_FIELD_LIST_ALL.add(UnitField.UFV_INTENDED_OUTBOUND_CV_OPERATOR, BizRoleEnum.HAULIER);
        BIZ_FIELD_LIST_ALL.add(UnitField.UFV_RTG_TRUCKING_COMPANY, BizRoleEnum.HAULIER, false);
        BIZ_FIELD_LIST_ALL.add(UnitField.UFV_SHIPPER_BZU, BizRoleEnum.SHIPPER);
        BIZ_FIELD_LIST_ALL.add(UnitField.UFV_CONSIGNEE_BZU, BizRoleEnum.SHIPPER);
        BIZ_FIELD_LIST_ALL.add(UnitField.UFV_AGENT1, BizRoleEnum.AGENT);
        BIZ_FIELD_LIST_ALL.add(UnitField.UFV_AGENT2, BizRoleEnum.AGENT);
    }

    private static class EquipmentPosition {
        private LocTypeEnum _posLocType;
        private String _posName;

        private EquipmentPosition(LocTypeEnum inPosLocType, String inPosName) {
            this._posLocType = inPosLocType;
            this._posName = inPosName;
        }

        public LocTypeEnum getPosLocType() {
            return this._posLocType;
        }

        public String getPosName() {
            return this._posName;
        }
    }

}
