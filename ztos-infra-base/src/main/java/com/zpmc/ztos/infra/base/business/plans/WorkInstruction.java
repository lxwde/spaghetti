package com.zpmc.ztos.infra.base.business.plans;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.business.dataobject.WorkInstructionDO;
import com.zpmc.ztos.infra.base.business.enums.argo.*;
import com.zpmc.ztos.infra.base.business.enums.inventory.*;
import com.zpmc.ztos.infra.base.business.equipments.Che;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.business.inventory.*;
import com.zpmc.ztos.infra.base.business.model.*;
import com.zpmc.ztos.infra.base.business.predicate.PredicateFactory;
import com.zpmc.ztos.infra.base.business.xps.XpsPkeyGenerator;
import com.zpmc.ztos.infra.base.common.configs.ArgoConfig;
import com.zpmc.ztos.infra.base.common.configs.InventoryConfig;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.common.enums.AtomizedEnum;
import com.zpmc.ztos.infra.base.common.events.AuditEvent;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.helps.BinModelHelper;
import com.zpmc.ztos.infra.base.common.helps.ContextHelper;
import com.zpmc.ztos.infra.base.common.model.*;
import com.zpmc.ztos.infra.base.common.scopes.Facility;
import com.zpmc.ztos.infra.base.common.scopes.Yard;
import com.zpmc.ztos.infra.base.common.utils.ArgoUtils;
import com.zpmc.ztos.infra.base.common.utils.SparcsInventoryUtils;
import com.zpmc.ztos.infra.base.utils.DateUtils;
import com.zpmc.ztos.infra.base.utils.QueryUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.io.Serializable;
import java.util.*;

public class WorkInstruction extends WorkInstructionDO {
    private static final Logger LOGGER = Logger.getLogger(WorkInstruction.class);
    private static final BizFieldList BIZ_FIELD_LIST;
    public static final Long DETACHED_WI_PKEY;
    private static final Set<WiMoveStageEnum> COMPLETED_WI_MOVESTAGES_SET;
    private static final String UFV = "UFV";
    private static final String TBD_UNIT = "TBD";

    public WorkInstruction() {
        this.setWiMoveKind(WiMoveKindEnum.Other);
        this.setWiDoorDirection(DoorDirectionEnum.ANY);
        this.setWiDeckingRestrict(DeckingRestrictionEnum.NONE);
        this.setWiTwinWith(TwinWithEnum.NONE);
        this.setWiTwinIntendedFetch(Boolean.FALSE);
        this.setWiTwinIntendedCarry(Boolean.FALSE);
        this.setWiTwinIntendedPut(Boolean.FALSE);
        this.setWiEcStateFetch(WiEcStateEnum.NONE);
        this.setWiEcStateDispatchRequest(Boolean.FALSE);
        this.setWiEcStateItvDispatchRequest(Boolean.FALSE);
        this.setWiEcStateDispatch(Boolean.FALSE);
        this.setWiEcStateItvDispatch(Boolean.FALSE);
        this.setWiSuspendState(WiSuspendStateEnum.NONE);
        this.setWiIsLocked(Boolean.FALSE);
        this.setWiIsConfirmed(Boolean.FALSE);
        this.setWiIsDefiniteMove(Boolean.FALSE);
        this.setWiIsExecutable(Boolean.FALSE);
        this.setWiIsIncrementCraneLane(Boolean.FALSE);
        this.setWiIsASCAbortedOnceAlready(Boolean.FALSE);
        this.setWiIsBeingRehandled(Boolean.FALSE);
        this.setWiIsBeingDeleted(Boolean.FALSE);
        this.setWiIsShouldBeSent(Boolean.FALSE);
        this.setWiIsSequencedByPWP(Boolean.FALSE);
        this.setWiIsHostRequestPriority(Boolean.FALSE);
        this.setWiIsSkipHostUpdate(Boolean.FALSE);
        this.setWiIsIgnoreGateHold(Boolean.FALSE);
        this.setWiMoveStage(WiMoveStageEnum.PLANNED);
        this.setWiConfirmedMoveStage(WiMoveStageEnum.PLANNED);
        this.setWiEcHold(EcHoldEnum.NONE);
        this.setWiCreateTool(SparcsToolEnum.ARROW);
        this.setWiIsImminentMove(Boolean.FALSE);
        this.setWiIsAlmostImminent(Boolean.FALSE);
        this.setWiIsAlmostCurrent(Boolean.FALSE);
        this.setWiIsBeingCarried(Boolean.FALSE);
        this.setWiIsCarryByStraddle(Boolean.FALSE);
        this.setWiIsDispatchHung(Boolean.FALSE);
        this.setWiIsStrictLoad(Boolean.FALSE);
        this.setWiIsTandemWithNext(Boolean.FALSE);
        this.setWiIsTandemWithPrevious(Boolean.FALSE);
    }

    public BizFieldList getBizFieldList() {
        return BIZ_FIELD_LIST;
    }

    @Nullable
    public LocPosition getWiToPosition() {
        if (!this.isWiMoveComplete()) {
            return this.getWiPosition();
        }
        WorkInstruction nextWi = this.getNextWorkInstruction();
        if (nextWi != null && nextWi.isWiMoveComplete()) {
            return nextWi.getWiPosition();
        }
        UnitFacilityVisit ufv = this.getWiUfv();
        return ufv == null ? null : ufv.getUfvLastKnownPosition();
    }

    public Boolean isUfvSwappableForVesselLoad(@NotNull WorkInstruction inLoadWiToBeLoaded) {
        DoorDirectionEnum loadingPlannedDoors;
        if (this.isWiMoveComplete() || inLoadWiToBeLoaded.isWiMoveComplete()) {
            return false;
        }
        if (!this.getWiPosition().isVesselPosition() || !inLoadWiToBeLoaded.getWiPosition().isVesselPosition()) {
            return false;
        }
        UnitFacilityVisit thisUfv = this.getWiUfv();
        UnitFacilityVisit thatUfv = inLoadWiToBeLoaded.getWiUfv();
        if (thisUfv == null || thatUfv == null) {
            return false;
        }
        Boolean ufvCheck = thisUfv.isUfvSwappableForVesselLoad(thatUfv, false);
        if (Boolean.FALSE.equals(ufvCheck)) {
            return ufvCheck;
        }
        DoorDirectionEnum thisPlannedDoors = this.getWiDoorDirection();
        if (thisPlannedDoors.equals((Object)(loadingPlannedDoors = inLoadWiToBeLoaded.getWiDoorDirection()))) {
            if (DoorDirectionEnum.UNKNOWN.equals((Object)loadingPlannedDoors) || DoorDirectionEnum.UNKNOWN.equals((Object)thisPlannedDoors)) {
                LOGGER.warn((Object)("isUfvSwappableForVesselLoad found unexpected planned doors: " + thisPlannedDoors.getName() + "(" + this + ") versus " + loadingPlannedDoors.getName() + "(" + thatUfv + ")"));
                return false;
            }
        } else if (DoorDirectionEnum.ANY.equals((Object)thisPlannedDoors) || DoorDirectionEnum.ANY.equals((Object)loadingPlannedDoors)) {
            LOGGER.warn((Object)("isUfvSwappableForVesselLoad found always acceptable planned doors: " + thisPlannedDoors.getName() + "(" + this + ") versus " + loadingPlannedDoors.getName() + "(" + thatUfv + ")"));
        } else {
            LOGGER.info((Object)("isUfvSwappableForVesselLoad found incompatible planned orientations: " + thisPlannedDoors.getName() + "(" + this + ") versus " + loadingPlannedDoors.getName() + "(" + thatUfv + ")"));
            return false;
        }
        return true;
    }

    @Nullable
    public LocPosition getWiFromPosition() {
        if (this.isWiMoveComplete()) {
            return this.getWiPosition();
        }
        WorkInstruction prevWi = this.getPreviousWorkInstruction();
        if (prevWi != null && !prevWi.isWiMoveComplete()) {
            return prevWi.getWiPosition();
        }
        UnitFacilityVisit ufv = this.getWiUfv();
        return ufv == null ? null : ufv.getUfvLastKnownPosition();
    }

    public boolean isWiMoveComplete() {
        return WiMoveStageEnum.COMPLETE.equals((Object)this.getWiMoveStage()) || WiMoveStageEnum.COMPLETE.equals((Object)this.getWiConfirmedMoveStage());
    }

    @Nullable
    public Date getValidatedEstimatedMoveTime() {
        Date emt = this.getWiEstimatedMoveTime();
        boolean isStale = false;
        if (emt != null && !this.isWiMoveComplete() && emt.before(this.getStaleEmtThreshold())) {
            if (WiMoveKindEnum.YardMove.equals((Object)this.getWiMoveKind()) || WiMoveKindEnum.YardShift.equals((Object)this.getWiMoveKind()) || this.getWiWorkQueue() != null && WqTypeEnum.YARD.equals((Object)this.getWiWorkQueue().getWqType())) {
                isStale = emt.before(WorkInstruction.getStaleTimeHorizonThreshold());
            } else if (WiMoveKindEnum.VeslDisch.equals((Object)this.getWiMoveKind()) || WiMoveKindEnum.VeslLoad.equals((Object)this.getWiMoveKind()) || WiMoveKindEnum.RailLoad.equals((Object)this.getWiMoveKind())) {
                isStale = !Boolean.TRUE.equals(this.getWiEcStateDispatch());
            } else if (WiMoveKindEnum.RailDisch.equals((Object)this.getWiMoveKind())) {
                LocPosition ufvPos = this.getWiUfv().getUfvLastKnownPosition();
                isStale = ufvPos.isCarrierPosition();
            }
        }
        if (!isStale) {
            return emt;
        }
        LOGGER.warn((Object)("Stale EMT value: " + emt + " for " + this));
        return null;
    }

    private Date getStaleEmtThreshold() {
        if (WiMoveKindEnum.VeslLoad.equals((Object)this.getWiMoveKind()) || WiMoveKindEnum.VeslDisch.equals((Object)this.getWiMoveKind())) {
            return WorkInstruction.getStaleEmtThresholdForSchedulers();
        }
        return WorkInstruction.getStaleEmtThresholdForGenerators();
    }

    private static Date getStaleEmtThresholdForGenerators() {
        return DateUtils.addMinutes((Date) ArgoUtils.timeNow(), (int)(-1 * (5 + (int) ArgoConfig.UPDATE_WI_ESTIMATED_MOVE_TIME_JOB_FREQ.getDefaultValue())));
    }

    private static Date getStaleEmtThresholdForSchedulers() {
        return DateUtils.addMinutes((Date)ArgoUtils.timeNow(), (int)-5);
    }

    private static Date getStaleTimeHorizonThreshold() {
        return DateUtils.addMinutes((Date)ArgoUtils.timeNow(), (int)-60);
    }

    public void completeForMockTestUnit() {
        this.updateWiPosition(this.getWiFromPosition());
        this.updateWiMoveStage(WiMoveStageEnum.COMPLETE);
    }

    public boolean isTwinLiftAtQCIntended() {
        boolean isIntendedTwinLiftAtQC = false;
        WiMoveKindEnum theMK = this.getWiMoveKind();
        if (WiMoveKindEnum.VeslLoad.equals((Object)theMK)) {
            isIntendedTwinLiftAtQC = this.getWiTwinIntendedPut();
        } else if (WiMoveKindEnum.VeslDisch.equals((Object)theMK)) {
            isIntendedTwinLiftAtQC = this.getWiTwinIntendedFetch();
        }
        return isIntendedTwinLiftAtQC;
    }

    public static WorkInstruction createWorkInstruction() {
        WorkInstruction wi = new WorkInstruction();
        wi.setWiFacility(ContextHelper.getThreadFacility());
        return wi;
    }

    public static WorkInstruction createWorkInstruction(UnitYardVisit inUyv, Double inMoveNumber, WorkQueue inWq, int inSequence) {
        return WorkInstruction.createWorkInstruction(inUyv, inMoveNumber, inWq, inSequence, LocPosition.createLocPosition((ILocation)inUyv.getUyvYard(), null, null));
    }

    public static WorkInstruction createWorkInstruction(UnitYardVisit inUyv, Double inMoveNumber, WorkQueue inWq, int inSequence, LocPosition inPosition) {
        return WorkInstruction.createWorkInstruction(inUyv, new MoveNumberParams(inMoveNumber), inWq, inSequence, inPosition);
    }

    public static WorkInstruction createWorkInstruction(UnitYardVisit inUyv, @NotNull MoveNumberParams inMoveNumberParams, WorkQueue inWq, int inSequence, LocPosition inPosition) {
        return WorkInstruction.createWorkInstruction(inUyv, inMoveNumberParams, inWq, inSequence, inPosition, null);
    }

    public static WorkInstruction createWorkInstruction(UnitYardVisit inUyv, @NotNull MoveNumberParams inMoveNumberParams, WorkQueue inWq, int inSequence, LocPosition inPosition, @Nullable Long inYardShiftTargetWiGkey) {
        WorkInstruction wi = WorkInstruction.createWorkInstruction();
        wi.setWiPkey(XpsPkeyGenerator.generate((int)0, (int)0x3FFFFFFF, (int)32, (String)"WorkInstruction", (IMetafieldId)IMovesField.WI_PKEY, (IMetafieldId)IMovesField.WI_FACILITY, (Serializable)ContextHelper.getThreadFacilityKey()));
        wi.setWiUyv(inUyv);
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info((Object)("createWorkInstruction: attached to container " + inUyv));
        }
        wi.setWiMoveNumber(inMoveNumberParams.getMoveNumber());
        if (inWq != null) {
            wi.setWiWqPkey(inWq.getWqPkey());
            wi.setWiWorkQueue(inWq);
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info((Object)("createWorkInstruction: attached to work queue " + inWq));
            }
        }
        wi.setWiOriginalFromQual(String.valueOf(wi.calculateOriginalFromQual(inPosition)));
        wi.setWiSequence(Long.valueOf(inSequence));
        wi.setWiPosition(inPosition);
        WorkInstruction targetShiftReplacementWi = WorkInstruction.getReplacementYardShiftTargetWi(inYardShiftTargetWiGkey, wi);
        if (targetShiftReplacementWi != null) {
            wi.setWiYardShiftTargetWi(targetShiftReplacementWi);
        }
        wi.setWiMoveKind(wi.calculateMoveKind());
        if (wi.getWiMoveKind() == WiMoveKindEnum.YardShift) {
            WorkInstruction contextWi = inMoveNumberParams.getContextWi();
            if (contextWi == null) {
                LOGGER.info((Object)("WI move kind of YardShift was calculated for " + wi.describeWi() + " but the context WI was null - unable to set updated WI move number"));
            } else {
                WiMoveStageEnum contextWiMoveStage = contextWi.getWiMoveStage();
                LOGGER.debug((Object)("WI move kind of YardShift is calculated for " + wi.describeWi() + " which have prev. Wi of " + contextWi.describeWiInclDispatchStateInfo()));
                Che carryChe = contextWi.getWiChe();
                if (WorkInstruction.isCarryOrPutCompleteStage(contextWiMoveStage) || WiMoveStageEnum.CARRY_UNDERWAY == contextWiMoveStage && carryChe != null && CheKindEnum.ASC.equals((Object)carryChe.getCheKindEnum())) {
                    wi.setWiMoveNumber(contextWi.calculateNewWiMoveNumberNext());
                } else {
                    wi.setWiMoveNumber(contextWi.calculateNewWiMoveNumberPrev(true));
                }
            }
        }
        HibernateApi.getInstance().save((Object)wi);
        List wiList = inUyv.ensureWiList();
        wiList.add(wi);
        if (!inPosition.isTransferZone()) {
            HibernateApi.getInstance().flush();
            WorkInstruction.reconcileFromLocQualForWorkInstructionChain(wi.getNextWorkInstruction());
            WorkInstruction.reconcileMoveKindForWorkInstructionChain(inUyv);
        }
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info((Object)("createWorkInstruction: attached to container " + inUyv));
        }
        return wi;
    }

    public static void reconcileMoveKindForWorkInstructionChain(@NotNull UnitYardVisit inUyv) {
        List wiList = inUyv.getUyvWiList();
        for (Object wi : wiList) {
            if (WiMoveStageEnum.COMPLETE.equals((Object)((WorkInstruction)wi).getWiMoveStage())) continue;
            ((WorkInstruction)wi).resetMoveKindIfNeeded();
        }
    }

    public static void reconcileFromLocQualForWorkInstructionChain(@Nullable WorkInstruction inWi) {
        if (inWi == null) {
            return;
        }
        List<WorkInstruction> wiList = WorkInstruction.getWiMoveChainSegmentFor(inWi);
        for (WorkInstruction wi : wiList) {
            if (WiMoveStageEnum.COMPLETE.equals((Object)wi.getWiMoveStage())) continue;
            wi.resetOriginalFromLocQualIfNeeded();
        }
    }

    public Double calculateNewWiMoveNumberPrev() {
        return this.calculateNewWiMoveNumberPrev(false);
    }

    public Double calculateNewWiMoveNumberPrev(boolean inIsIntendedForRehandle) {
        Double newPrevMoveNum;
        WorkInstruction chainWi;
        List wiMoveChain = this.getWiMoveChain();
        Double wiMoveNumber = this.getWiMoveNumber();
        Double prevExistingMoveNumber = 0.0;
        Iterator iterator = wiMoveChain.iterator();
        while (iterator.hasNext() && (chainWi = (WorkInstruction)iterator.next()).getWiMoveNumber() < wiMoveNumber) {
            prevExistingMoveNumber = chainWi.getWiMoveNumber();
        }
        if (prevExistingMoveNumber == 0.0 || wiMoveNumber - prevExistingMoveNumber > 1.0) {
            prevExistingMoveNumber = wiMoveNumber <= 0.0 ? 0.0 : Math.ceil(wiMoveNumber - 1.0);
        }
        Double retVal = newPrevMoveNum = Double.valueOf((prevExistingMoveNumber + wiMoveNumber) / 2.0);
        if (inIsIntendedForRehandle) {
            Double rehandleMoveNum;
            retVal = rehandleMoveNum = Double.valueOf((newPrevMoveNum + prevExistingMoveNumber) / 2.0);
        }
        return retVal;
    }

    public Double calculateNewWiMoveNumberNext() {
        Double wiNextMoveNumber;
        Double wiMoveNumber;
        List wiMoveChain = this.getWiMoveChain();
        Double nextExistingMoveNumber = wiMoveNumber = this.getWiMoveNumber();
        for (Object chainWi : wiMoveChain) {
            if (!(((WorkInstruction)chainWi).getWiMoveNumber() > wiMoveNumber)) continue;
            nextExistingMoveNumber = ((WorkInstruction)chainWi).getWiMoveNumber();
            break;
        }
        if (nextExistingMoveNumber.equals(wiMoveNumber)) {
            wiNextMoveNumber = Math.floor(wiMoveNumber + 1.0);
        } else {
            if (nextExistingMoveNumber - wiMoveNumber > 1.0) {
                nextExistingMoveNumber = Math.floor(wiMoveNumber + 1.0);
            }
            wiNextMoveNumber = (nextExistingMoveNumber + wiMoveNumber) / 2.0;
        }
        return wiNextMoveNumber;
    }

    @NotNull
    public List getWiMoveChain() {
        UnitYardVisit targetUyv = this.getWiUyv();
        if (targetUyv != null) {
            return targetUyv.getSortedMoveChain();
        }
        return new ArrayList();
    }

    @NotNull
    public static List<WorkInstruction> getWiMoveChainSegmentFor(@NotNull WorkInstruction inWi) {
        ArrayList<WorkInstruction> chainWis = new ArrayList<WorkInstruction>();
        LocPosition fromPos = inWi.getWiFromPosition();
        if (fromPos != null && fromPos.isTransferZone()) {
            List<WorkInstruction> previousWis = inWi.getPreviousWorkInstructions();
            for (WorkInstruction previousWi : previousWis) {
                chainWis.add(0, previousWi);
                LocPosition pos = previousWi.getWiFromPosition();
                if (pos == null || pos.isTransferZone()) continue;
                break;
            }
        }
        chainWis.add(inWi);
        LocPosition toPos = inWi.getWiToPosition();
        if (toPos != null && toPos.isTransferZone()) {
            List<WorkInstruction> nextWis = inWi.getNextWorkInstructions();
            for (WorkInstruction nextWi : nextWis) {
                chainWis.add(nextWi);
                LocPosition pos = nextWi.getWiToPosition();
                if (pos == null || pos.isTransferZone()) continue;
                break;
            }
        }
        return chainWis;
    }

    public static WorkInstruction findOrCreateWorkInstruction(UnitYardVisit inUyv, Double inMoveNumber, WorkQueue inWq, int inSequence) {
        WorkInstruction wi = WorkInstruction.findWorkInstruction(inUyv, inMoveNumber, inWq);
        if (wi == null) {
            wi = WorkInstruction.createWorkInstruction(inUyv, inMoveNumber, inWq, inSequence);
        }
        return wi;
    }

    @NotNull
    public static WorkInstruction findOrCreateWorkInstructionToPosition(UnitYardVisit inUyv, LocPosition inPosition, WorkQueue inWorkQueue) {
        return WorkInstruction.findOrCreateWorkInstructionToPosition(inUyv, inPosition, inWorkQueue, null);
    }

    @NotNull
    public static WorkInstruction findOrCreateWorkInstructionToPosition(UnitYardVisit inUyv, LocPosition inPosition, WorkQueue inWorkQueue, @Nullable Long inYardShiftTargetWiGkey) {
        WorkInstruction wi = null;
        WorkInstruction nextPlannedWi = null;
        List<WorkInstruction> uyvWiList = inUyv.getSortedMoveChain();
        if (uyvWiList != null && !uyvWiList.isEmpty()) {
            WorkInstruction firstMatchingWi = null;
            for (WorkInstruction aWi : uyvWiList) {
                if (WiMoveStageEnum.COMPLETE.equals((Object)aWi.getWiMoveStage()) || WiMoveStageEnum.PUT_COMPLETE.equals((Object)aWi.getWiMoveStage())) continue;
                if (nextPlannedWi == null && WiMoveStageEnum.PLANNED.equals((Object)aWi.getWiMoveStage())) {
                    nextPlannedWi = aWi;
                }
                if (aWi.getWiPosition().equals((Object)inPosition)) {
                    firstMatchingWi = aWi;
                    continue;
                }
                if (aWi.getWiPosition().getPosBin() == null || inPosition == null || !aWi.getWiPosition().getPosBin().equals((Object)inPosition.getPosBin())) continue;
                firstMatchingWi = aWi;
            }
            if (firstMatchingWi != null) {
                wi = firstMatchingWi;
            }
        }
        if (wi == null) {
            WorkInstruction contextWi = null;
            WiChainInsertionPoint insertionPoint = null;
            if (nextPlannedWi != null) {
                contextWi = nextPlannedWi;
                insertionPoint = WiChainInsertionPoint.PREV;
            } else if (uyvWiList != null && !uyvWiList.isEmpty()) {
                contextWi = uyvWiList.get(uyvWiList.size() - 1);
                insertionPoint = WiChainInsertionPoint.NEXT;
            } else {
                insertionPoint = WiChainInsertionPoint.NEW;
            }
            wi = WorkInstruction.createWorkInstruction(inUyv, new MoveNumberParams(insertionPoint, contextWi), inWorkQueue, inWorkQueue.getNextSequenceNbrForNewWi(), inPosition, inYardShiftTargetWiGkey);
        }
        return wi;
    }

    @Nullable
    private static WorkInstruction getReplacementYardShiftTargetWi(@Nullable Long inYardShiftTargetWiGkey, @NotNull WorkInstruction inWorkInstruction) {
        if (!(inYardShiftTargetWiGkey == null || inWorkInstruction.getWiYardShiftTargetWi() != null && inYardShiftTargetWiGkey.equals(inWorkInstruction.getWiYardShiftTargetWi().getWiGkey()))) {
            return (WorkInstruction)HibernateApi.getInstance().get(WorkInstruction.class, (Serializable)inYardShiftTargetWiGkey);
        }
        return null;
    }

    public static WorkInstruction createOrUpdateWorkInstructionToPosition(UnitYardVisit inUyv, Double inMoveNumber, WorkQueue inWq, LocPosition inPosition) {
        WorkInstruction wi = WorkInstruction.findWorkInstruction(inUyv, inMoveNumber, inWq);
        if (wi == null) {
            wi = WorkInstruction.createWorkInstructionToPosition(inUyv, inMoveNumber, inWq, inPosition);
        } else {
            WorkInstruction.updateWorkInstructionToPosition(wi, inPosition);
        }
        return wi;
    }

    public static WorkInstruction createWorkInstructionToPosition(UnitYardVisit inUyv, Double inMoveNumber, WorkQueue inWq, LocPosition inPosition) {
        return WorkInstruction.createWorkInstruction(inUyv, inMoveNumber, inWq, inWq.getNextSequenceNbrForNewWi(), inPosition);
    }

    public static WorkInstruction createWorkInstructionToPosition(WorkInstruction inContextWi, WiChainInsertionPoint inInsertionPoint, WorkQueue inWq, LocPosition inPosition) {
        return WorkInstruction.createWorkInstruction(inContextWi.getWiUyv(), new MoveNumberParams(inInsertionPoint, inContextWi), inWq, inWq.getNextSequenceNbrForNewWi(), inPosition);
    }

    public String describeWi() {
        StringBuilder logMsg = new StringBuilder("WorkInstruction[" + this.getWiGkey());
        UnitFacilityVisit ufv = this.getWiUfv();
        if (ufv != null) {
            logMsg.append(":" + ufv.getPrimaryEqId());
        } else {
            TbdUnit tbdu = this.getWiTbdUnit();
            if (tbdu != null) {
                logMsg.append(":" + tbdu.getTbduGkey());
            }
        }
        logMsg.append(":" + this.getWiMoveKind().getName());
        LocPosition fromPos = this.getWiFromPosition();
        if (fromPos != null) {
            logMsg.append(":" + (Object)fromPos);
        } else {
            logMsg.append(":null");
        }
        LocPosition toPos = this.getWiToPosition();
        if (toPos != null) {
            logMsg.append(">" + (Object)toPos);
        } else {
            logMsg.append(">null");
        }
        logMsg.append("]");
        return logMsg.toString();
    }

    public String describeWiInclDispatchStateInfo() {
        WorkAssignment itvWa;
        StringBuilder wiDesc = new StringBuilder();
        wiDesc.append(this.getWiMoveStage().getName() + " ");
        wiDesc.append(this.describeWi());
        if (this.isLadenDispatchRequested().booleanValue()) {
            wiDesc.append(", LadenDispatchRequested=true");
        }
        if (WiMoveKindEnum.VeslDisch.equals((Object)this.getWiMoveKind())) {
            wiDesc.append(", ActualSequence=" + this.getWiActualSequence());
        }
        if ((itvWa = this.getWiItvWorkAssignment()) != null) {
            wiDesc.append(", on " + itvWa.getWorkassignmentStatusEnum().getName() + " ITV " + itvWa.describeWa());
        } else {
            wiDesc.append(", on no ITV WA");
        }
        return wiDesc.toString();
    }

    public String toString() {
        return "WorkInstruction[" + this.getWiGkey() + "]";
    }

    private static WorkInstruction updateWorkInstructionToPosition(@NotNull WorkInstruction inWi, LocPosition inPosition) {
        inWi.setWiPosition(inPosition);
        inWi.setWiMoveStage(WiMoveStageEnum.PLANNED);
        HibernateApi.getInstance().saveOrUpdate((Object)inWi);
        if (!inPosition.isTransferZone()) {
            HibernateApi.getInstance().flush();
            WorkInstruction.reconcileFromLocQualForWorkInstructionChain(inWi.getNextWorkInstruction());
            WorkInstruction.reconcileMoveKindForWorkInstructionChain(inWi.getWiUyv());
        }
        return inWi;
    }

    @Nullable
    private Character calculateOriginalFromQual(LocPosition inWiToPos) {
        LocPosition arrivalPos;
        WorkInstruction preWi;
        WorkInstruction nextWi;
        if (inWiToPos != null && (nextWi = this.getNextWorkInstruction()) != null) {
            return Character.valueOf(nextWi.getWiOriginalFromQual().charAt(0));
        }
        LocPosition fromPos = this.getWiFromPosition();
        if (fromPos != null) {
            if ((fromPos.isQcPlatformPosition() || fromPos.isQcTransferZonePosition() || fromPos.isApron()) && inWiToPos != null && !inWiToPos.isVesselPosition()) {
                return SparcsInventoryUtils.getXpsLocationQualifier(LocTypeEnum.VESSEL);
            }
            if (fromPos.isRampBin() && inWiToPos != null && !inWiToPos.isRailPosition()) {
                return SparcsInventoryUtils.getXpsLocationQualifier(LocTypeEnum.RAILCAR);
            }
            if (!fromPos.isTransferZone()) {
                return SparcsInventoryUtils.getXpsLocationQualifier(fromPos.getPosLocType());
            }
        }
        if ((preWi = this.getPreviousWorkInstruction()) != null) {
            return Character.valueOf(preWi.getWiOriginalFromQual().charAt(0));
        }
        UnitFacilityVisit ufv = this.getWiUfv();
        if (ufv != null && (arrivalPos = ufv.getUfvArrivePosition()) != null) {
            return SparcsInventoryUtils.getXpsLocationQualifier(arrivalPos.getPosLocType());
        }
        return null;
    }

    public void updateWiTranGkey(Long inTranGkey) {
        this.setWiTranGkey(inTranGkey);
    }

    public void updateWiTvGkey(Long inTvGkey) {
        this.setWiTvGkey(inTvGkey);
    }

    public void updateWiIsSwappableDelivery(Boolean inIsSwappableDelivery) {
        this.setWiIsSwappableDelivery(inIsSwappableDelivery);
    }

    private WiMoveKindEnum calculateMoveKind() {
        WiMoveKindEnum calculatedMoveKind = WiMoveKindEnum.Other;
        LocTypeEnum fromType = SparcsInventoryUtils.getN4LocationQualifier(this.getWiOriginalFromQual().charAt(0), true);
        LocPosition toPosition = this.getNextTransferPosBeforeTransfer();
        LocTypeEnum toType = LocTypeEnum.UNKNOWN;
        if (this.getWiTvGkey() != null && !LocTypeEnum.TRUCK.equals((Object)fromType) && !LocTypeEnum.COMMUNITY.equals((Object)fromType)) {
            toType = LocTypeEnum.TRUCK;
        } else if (toPosition != null) {
            toType = toPosition.getPosLocType();
        }
        if (LocTypeEnum.RAILCAR.equals((Object)toType) || LocTypeEnum.TRAIN.equals((Object)toType)) {
            calculatedMoveKind = LocTypeEnum.VESSEL.equals((Object)fromType) ? WiMoveKindEnum.VeslDisch : WiMoveKindEnum.RailLoad;
        } else if (LocTypeEnum.TRUCK.equals((Object)toType) || LocTypeEnum.COMMUNITY.equals((Object)toType)) {
            calculatedMoveKind = LocTypeEnum.VESSEL.equals((Object)fromType) ? WiMoveKindEnum.VeslDisch : (LocTypeEnum.RAILCAR.equals((Object)fromType) || LocTypeEnum.TRAIN.equals((Object)fromType) ? WiMoveKindEnum.RailDisch : WiMoveKindEnum.Delivery);
        } else if (LocTypeEnum.VESSEL.equals((Object)toType)) {
            if (LocTypeEnum.VESSEL.equals((Object)fromType)) {
                String toPosLocId;
                LocPosition fromPosition = this.getPreviousTransferPosBeforeTransfer();
                String fromPosLocId = fromPosition != null ? fromPosition.getPosLocId() : "";
                String string = toPosLocId = toPosition != null ? toPosition.getPosLocId() : "";
                calculatedMoveKind = fromPosLocId.equals(toPosLocId) ? WiMoveKindEnum.ShiftOnBoard : WiMoveKindEnum.VeslDisch;
            } else {
                calculatedMoveKind = WiMoveKindEnum.VeslLoad;
            }
        } else if (LocTypeEnum.YARD.equals((Object)toType)) {
            if (LocTypeEnum.VESSEL.equals((Object)fromType)) {
                calculatedMoveKind = WiMoveKindEnum.VeslDisch;
            } else if (LocTypeEnum.RAILCAR.equals((Object)fromType) || LocTypeEnum.TRAIN.equals((Object)fromType)) {
                calculatedMoveKind = WiMoveKindEnum.RailDisch;
            } else if (LocTypeEnum.TRUCK.equals((Object)fromType) || LocTypeEnum.COMMUNITY.equals((Object)fromType)) {
                calculatedMoveKind = WiMoveKindEnum.Receival;
            } else if (LocTypeEnum.YARD.equals((Object)fromType)) {
                calculatedMoveKind = this.getWiYardShiftTargetWi() != null ? WiMoveKindEnum.YardShift : WiMoveKindEnum.YardMove;
            }
        }
        return calculatedMoveKind;
    }

    @Nullable
    private LocPosition getPreviousTransferPosBeforeTransfer() {
        LocPosition fromPosition = this.getWiFromPosition();
        if (fromPosition != null && !fromPosition.isTransferZone()) {
            return fromPosition;
        }
        List<WorkInstruction> previousWis = this.getPreviousWorkInstructions();
        fromPosition = null;
        for (WorkInstruction previousWi : previousWis) {
            LocPosition pos = previousWi.getWiFromPosition();
            if (pos == null || pos.isTransferZone()) continue;
            fromPosition = pos;
            break;
        }
        if (fromPosition != null) {
            return fromPosition;
        }
        UnitFacilityVisit ufv = this.getWiUfv();
        return ufv == null ? null : ufv.getUfvArrivePosition();
    }

    @NotNull
    public List<WorkInstruction> getPreviousWorkInstructions() {
        List<WorkInstruction> emptyList = Collections.emptyList();
        Double moveNumber = this.getWiMoveNumber() == null ? 1.0 : this.getWiMoveNumber();
        if (moveNumber == 0.0) {
            return emptyList;
        }
        List entities = null;
        if (this.getWiUyv() != null) {
            IDomainQuery dq = QueryUtils.createDomainQuery((String)"WorkInstruction").addDqPredicate(PredicateFactory.eq((IMetafieldId)IMovesField.WI_UYV, (Object)this.getWiUyv().getUyvGkey())).addDqPredicate(PredicateFactory.lt((IMetafieldId)IMovesField.WI_MOVE_NUMBER, (Object)moveNumber)).addDqOrdering(Ordering.desc((IMetafieldId)IMovesField.WI_MOVE_NUMBER));
            entities = HibernateApi.getInstance().findEntitiesByDomainQuery(dq);
        }
        if (entities == null || entities.isEmpty()) {
            return emptyList;
        }
        return entities;
    }

    @Nullable
    private LocPosition getNextTransferPosBeforeTransfer() {
        LocPosition toPosition = this.getWiToPosition();
        if (toPosition == null || !toPosition.isTransferZone()) {
            return toPosition;
        }
        List<WorkInstruction> nextWis = this.getNextWorkInstructions();
        for (WorkInstruction nextWi : nextWis) {
            LocPosition pos = nextWi.getWiToPosition();
            if (pos == null || pos.isTransferZone()) continue;
            toPosition = pos;
            break;
        }
        return toPosition;
    }

    @NotNull
    public List<WorkInstruction> getNextWorkInstructions() {
        List<WorkInstruction> emptyList = Collections.emptyList();
        Double moveNumber = this.getWiMoveNumber() == null ? 1.0 : this.getWiMoveNumber();
        if (moveNumber == 0.0) {
            return emptyList;
        }
        List entities = null;
        if (this.getWiUyv() != null) {
            IDomainQuery dq = QueryUtils.createDomainQuery((String)"WorkInstruction").addDqPredicate(PredicateFactory.eq((IMetafieldId)IMovesField.WI_UYV, (Object)this.getWiUyv().getUyvGkey())).addDqPredicate(PredicateFactory.gt((IMetafieldId)IMovesField.WI_MOVE_NUMBER, (Object)moveNumber)).addDqOrdering(Ordering.asc((IMetafieldId)IMovesField.WI_MOVE_NUMBER));
            entities = HibernateApi.getInstance().findEntitiesByDomainQuery(dq);
        }
        if (entities == null || entities.isEmpty()) {
            return emptyList;
        }
        return entities;
    }

    @Nullable
    public static WorkInstruction findWorkInstruction(UnitYardVisit inUyv, Double inMoveNumber, WorkQueue inWq) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"WorkInstruction").addDqPredicate(PredicateFactory.eq((IMetafieldId)IMovesField.WI_UYV, (Object)inUyv.getUyvGkey())).addDqPredicate(PredicateFactory.eq((IMetafieldId)IMovesField.WI_MOVE_NUMBER, (Object)inMoveNumber)).addDqPredicate(PredicateFactory.eq((IMetafieldId)IMovesField.WI_WQ_PKEY, (Object)inWq.getWqPkey()));
        return (WorkInstruction)HibernateApi.getInstance().getUniqueEntityByDomainQuery(dq);
    }

    @Nullable
    private static WorkInstruction findByPkey(@Nullable Yard inYard, @Nullable Long inWiPkey) {
        if (inYard == null) {
            return null;
        }
        return WorkInstruction.findByPkey(inYard.getYrdGkey(), inWiPkey);
    }

    @Nullable
    private static WorkInstruction findByPkey(@Nullable Long inYardGkey, @Nullable Long inWiPkey) {
        if (inYardGkey == null || inWiPkey == null) {
            return null;
        }
        return WorkInstruction.findByPkey((Serializable)inYardGkey, (Serializable)inWiPkey);
    }

    @Nullable
    public static WorkInstruction findByPkey(Serializable inYardGkey, Serializable inPkey) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"WorkInstruction").addDqPredicate(PredicateFactory.eq((IMetafieldId)IMovesField.WI_PKEY, (Object)inPkey)).addDqPredicate(PredicateFactory.eq((IMetafieldId)UnitField.WI_YARD, (Object)inYardGkey));
        Serializable[] matches = HibernateApi.getInstance().findPrimaryKeysByDomainQuery(dq);
        List<Serializable> matchGkeysList = Arrays.asList(matches);
        if (matchGkeysList.size() > 1) {
//            LOGGER.error((Object)(matchGkeysList.size() + " WorkInstruction found with pKey " + inPkey + " expected only 1 (yard gKey " + inYardGkey + ")"));
//            Collections.sort(matchGkeysList, new Comparator<Long>(){
//
//                @Override
//                public int compare(Long inWi1Gkey, Long inWi2Gkey) {
//                    return inWi2Gkey.compareTo(inWi1Gkey);
//                }
//            });
        }
        return matchGkeysList.isEmpty() ? null : WorkInstruction.hydrate(matchGkeysList.get(0));
    }

    public static void activateGateMovesForTruckVisit(Long inTvGkey) {
        List wis = WorkInstruction.findGateMovesForTruckVisit(inTvGkey);
        for (Object wi1 : wis) {
            WorkInstruction wi = (WorkInstruction)wi1;
            wi.setWiIsImminentMove(Boolean.TRUE);
        }
    }

    public static List<WorkInstruction> findWorkInstructionByUfvGkey(@NotNull Serializable inUfvGkey) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"WorkInstruction").addDqPredicate(PredicateFactory.eq((IMetafieldId) MovesCompoundField.WI_UYV_UFV, (Object)inUfvGkey));
        return HibernateApi.getInstance().findEntitiesByDomainQuery(dq);
    }

    public void startCarry() {
        this.setWiIsBeingCarried(Boolean.TRUE);
    }

    public void endCarry() {
        this.setWiIsConfirmed(Boolean.TRUE);
    }

    public static List findGateMovesForTruckVisit(Long inTvGkey) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"WorkInstruction").addDqPredicate(PredicateFactory.eq((IMetafieldId)IMovesField.WI_TV_GKEY, (Object)inTvGkey));
        return HibernateApi.getInstance().findEntitiesByDomainQuery(dq);
    }

    public static List findGateMovesForTruckTransaction(Long inTranGkey) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"WorkInstruction").addDqPredicate(PredicateFactory.eq((IMetafieldId)IMovesField.WI_TRAN_GKEY, (Object)inTranGkey));
        return HibernateApi.getInstance().findEntitiesByDomainQuery(dq);
    }

    public AuditEvent vetAuditEvent(AuditEvent inAuditEvent) {
        return inAuditEvent;
    }

    public boolean isGateReceive() {
        WiMoveKindEnum moveKind = this.getWiMoveKind();
        UserContext userContext = ContextHelper.getThreadUserContext();
        if (WiMoveKindEnum.Receival.equals((Object)moveKind)) {
            return true;
        }
        if (WiMoveKindEnum.VeslLoad.equals((Object)moveKind)) {
            UnitFacilityVisit ufv = this.getWiUfv();
            if (ufv != null) {
                return ufv.getUfvIsDirectIbToObMove() == null ? false : ufv.getUfvIsDirectIbToObMove();
            }
        } else if (WiMoveKindEnum.RailLoad.equals((Object)moveKind)) {
            boolean allowCarrierMode;
            CarrierVisit cv;
            UnitFacilityVisit ufv = this.getWiUfv();
            CarrierVisit carrierVisit = cv = ufv != null ? ufv.getUfvIntendedObCv() : null;
            if (ufv != null && cv != null && LocTypeEnum.TRUCK.equals((Object)cv.getCvCarrierMode())) {
                return ufv.getUfvIsDirectIbToObMove() == null ? false : ufv.getUfvIsDirectIbToObMove();
            }
            boolean allowPhase = cv != null && (CarrierVisitPhaseEnum.INBOUND.equals((Object)cv.getCvVisitPhase()) || CarrierVisitPhaseEnum.ARRIVED.equals((Object)cv.getCvVisitPhase()) || CarrierVisitPhaseEnum.WORKING.equals((Object)cv.getCvVisitPhase()));
            boolean isDirectDeliveryRail = InventoryConfig.DIRECT_DELIVERY_RAIL.isSetTo("ALWAYS", userContext);
            boolean bl = allowCarrierMode = cv != null && (LocTypeEnum.RAILCAR.equals((Object)cv.getCvCarrierMode()) || LocTypeEnum.TRAIN.equals((Object)cv.getCvCarrierMode()));
            if (cv != null && isDirectDeliveryRail && allowPhase && allowCarrierMode) {
                return true;
            }
        }
        return false;
    }

    public boolean isGateDelivery() {
        UserContext userContext = ContextHelper.getThreadUserContext();
        WiMoveKindEnum moveKind = this.getWiMoveKind();
        if (WiMoveKindEnum.Delivery.equals((Object)moveKind)) {
            return true;
        }
        if (WiMoveKindEnum.VeslDisch.equals((Object)moveKind)) {
            UnitFacilityVisit ufv = this.getWiUfv();
            if (ufv != null) {
                return ufv.getUfvIsDirectIbToObMove() == null ? false : ufv.getUfvIsDirectIbToObMove();
            }
        } else if (WiMoveKindEnum.RailDisch.equals((Object)moveKind)) {
            boolean allowCarrierMode;
            Unit unit = this.getWiUfv() != null ? this.getWiUfv().getUfvUnit() : null;
            CarrierVisit cv = unit != null ? unit.getUnitDeclaredIbCv() : null;
            boolean allowPhase = cv != null && (CarrierVisitPhaseEnum.ARRIVED.equals((Object)cv.getCvVisitPhase()) || CarrierVisitPhaseEnum.WORKING.equals((Object)cv.getCvVisitPhase()));
            boolean isDirectDeliveryRail = InventoryConfig.DIRECT_DELIVERY_RAIL.isSetTo("ALWAYS", userContext);
            boolean bl = allowCarrierMode = cv != null && (LocTypeEnum.RAILCAR.equals((Object)cv.getCvCarrierMode()) || LocTypeEnum.TRAIN.equals((Object)cv.getCvCarrierMode()));
            if (cv != null && isDirectDeliveryRail && allowPhase && allowCarrierMode) {
                return true;
            }
        }
        return false;
    }

    public boolean isGateMove() {
        return this.isGateReceive() || this.isGateDelivery();
    }

    public boolean isYardMove() {
        WiMoveKindEnum moveKind = this.getWiMoveKind();
        return WiMoveKindEnum.YardMove.equals((Object)moveKind) || WiMoveKindEnum.YardShift.equals((Object)moveKind);
    }

    public boolean isIFTDelivery() {
        boolean isIFTDelivery = false;
        WiMoveStageEnum currMoveStage = this.getWiMoveStage();
        if (WiMoveStageEnum.COMPLETE.equals((Object)currMoveStage)) {
            IArgoYardUtils yardUtils;
            AbstractBlock block;
            AbstractBin currPosBin;
            LocPosition unitCurrPos;
            UnitFacilityVisit ufv;
            UnitYardVisit uyv = this.getWiUyv();
            if (uyv != null && (ufv = uyv.getUyvUfv()) != null && (unitCurrPos = ufv.getLastYardPosition()) != null && (currPosBin = unitCurrPos.getPosBin()) != null && (block = BinModelHelper.getBlockFromModelBin((AbstractBin)currPosBin)) != null && (yardUtils = (IArgoYardUtils) Roastery.getBean((String)"argoYardUtils")) != null) {
                isIFTDelivery = yardUtils.isBlockIFTBin(block);
            }
        } else {
            IArgoYardUtils yardUtils;
            AbstractBlock block;
            AbstractBin wiBin;
            LocPosition wiPos = this.getWiPosition();
            if (wiPos != null && wiPos.isYardPosition() && (wiBin = wiPos.getPosBin()) != null && (block = BinModelHelper.getBlockFromModelBin((AbstractBin)wiBin)) != null && (yardUtils = (IArgoYardUtils)Roastery.getBean((String)"argoYardUtils")) != null) {
                isIFTDelivery = yardUtils.isBlockIFTBin(block);
            }
        }
        return isIFTDelivery;
    }

    public boolean isDispatchedToChassisOrTt() {
        boolean isWiItvWaCheUtr;
        WorkAssignment wiItvWorkAssignment;
        String wiAssignedChassId = this.getWiAssignedChassId();
        if (wiAssignedChassId != null && !wiAssignedChassId.isEmpty()) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug((Object) String.format("WI(%s) has assigned chassisId(%s)", this.toString(), wiAssignedChassId));
            }
            return true;
        }
        UnitFacilityVisit wiUfv = this.getWiUfv();
        if (wiUfv == null) {
            LOGGER.error((Object) String.format("unexpected null UFV for WI(%s)", this.toString()));
            return false;
        }
        Unit ufvUnit = wiUfv.getUfvUnit();
        if (ufvUnit == null) {
            LOGGER.error((Object) String.format("unexpected null Unit for UFV(%s)", wiUfv));
            return false;
        }
        String unitCurrentlyAttachedChassisId = ufvUnit.getUnitCurrentlyAttachedChassisId();
        if (unitCurrentlyAttachedChassisId != null) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug((Object) String.format("WI(%s) has UFV with currently attached chassisId(%s)", this.toString(), unitCurrentlyAttachedChassisId));
            }
            if (this.getWiTranGkeyOrNull() == null) {
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug((Object) String.format("WI(%s) has no truck transaction", this.toString()));
                }
                return true;
            }
        }
        if ((wiItvWorkAssignment = this.getWiItvWorkAssignment()) == null) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug((Object) String.format("WI(%s) has no ITV WA", this.toString()));
            }
            return false;
        }
        Che wiItvWaChe = wiItvWorkAssignment.getWorkassignmentChe();
        boolean bl = isWiItvWaCheUtr = wiItvWaChe != null && wiItvWaChe.isUtrChe();
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug((Object) String.format("WI(%s) has wiItvWaChe(%s) which isWiItvWaCheUtr(%s)", new Object[]{this.toString(), wiItvWaChe, isWiItvWaCheUtr}));
        }
        return isWiItvWaCheUtr;
    }

    public boolean isDispatchedToStraddleCarrier() {
        boolean isWiItvWaCheStrad;
        UnitFacilityVisit wiUfv = this.getWiUfv();
        if (wiUfv == null) {
            LOGGER.error((Object) String.format("unexpected null UFV for WI(%s)", this.toString()));
            return false;
        }
        Unit ufvUnit = wiUfv.getUfvUnit();
        if (ufvUnit == null) {
            LOGGER.error((Object) String.format("unexpected null Unit for UFV(%s)", wiUfv));
            return false;
        }
        WorkAssignment wiItvWorkAssignment = this.getWiItvWorkAssignment();
        if (wiItvWorkAssignment == null) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug((Object) String.format("WI(%s) has no ITV WA", this.toString()));
            }
            return false;
        }
        Che wiItvWaChe = wiItvWorkAssignment.getWorkassignmentChe();
        boolean bl = isWiItvWaCheStrad = wiItvWaChe != null && wiItvWaChe.isCheOfKind(CheKindEnum.SC);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug((Object) String.format("WI(%s) has wiItvWaChe(%s) which isWiItvWaCheStrad(%s)", new Object[]{this.toString(), wiItvWaChe, isWiItvWaCheStrad}));
        }
        return isWiItvWaCheStrad;
    }

    public boolean isForTbdUnit() {
        return this.getWiTbdUnit() != null;
    }

    @Override
    public Serializable getPrimaryKey() {
        return this.getWiGkey();
    }

    public UnitFacilityVisit getWiUfv() {
        UnitYardVisit uyv = this.getWiUyv();
        if (uyv != null) {
            return uyv.getUyvUfv();
        }
        return null;
    }

    public static List<WorkInstruction> findAssociatedWorkInstruction(Serializable inWaGkey) {
        IPredicate chePredicate = PredicateFactory.eq((IMetafieldId)IMovesField.WI_CHE_WORK_ASSIGNMENT, (Object)inWaGkey);
        IPredicate itvPredicate = PredicateFactory.eq((IMetafieldId)IMovesField.WI_ITV_WORK_ASSIGNMENT, (Object)inWaGkey);
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"WorkInstruction").addDqPredicate((IPredicate)PredicateFactory.disjunction().add(chePredicate).add(itvPredicate));
        return HibernateApi.getInstance().findEntitiesByDomainQuery(dq);
    }

    public BizViolation validateChanges(FieldChanges inChanges) {
        BizViolation bv = super.validateChanges(inChanges);
        FieldChange moveStage = inChanges.getFieldChange(IMovesField.WI_MOVE_STAGE);
        FieldChange uyvChange = inChanges.getFieldChange(IMovesField.WI_UYV);
        if (uyvChange == null && moveStage != null && DataSourceEnum.XPS.equals((Object) ContextHelper.getThreadDataSource())) {
            WiMoveStageEnum stageIs = (WiMoveStageEnum)moveStage.getNewValue();
            WiMoveStageEnum stageWas = (WiMoveStageEnum)moveStage.getPriorValue();
            if (WiMoveStageEnum.COMPLETE.equals((Object)stageWas) && !WiMoveStageEnum.COMPLETE.equals((Object)stageIs)) {
                LOGGER.error((Object)("WI MoveStage is already COMPLETE don't revert back to a previous MoveStage, " + this.describeWi()));
                bv = BizViolation.createFieldViolation((IPropertyKey) IInventoryPropertyKeys.INVALID_MOVE_STAGE_TRANSITION, (BizViolation)bv, (IMetafieldId)IMovesField.WI_MOVE_STAGE);
            }
        }
        return bv;
    }

    public void preProcessUpdate(FieldChanges inChanges, FieldChanges inOutMoreChanges) {
        WorkAssignment wa;
        WorkAssignment wa2;
        Long newWaPkey;
        Long newID;
        Che che;
        Che che2;
        Long newCheId;
        WorkInstruction wi;
        WorkInstruction wi2;
        Long newPkey;
        super.preProcessUpdate(inChanges, inOutMoreChanges);
        StringBuffer wiStatus = new StringBuffer();
        Priority logPriority = this.getPriorityForLoggingImportantFieldChange(wiStatus);
        FieldChange uyvChange = inChanges.getFieldChange(IMovesField.WI_UYV);
        if (uyvChange != null && uyvChange.getPriorValue() != null) {
            LOGGER.warn((Object)("WI uyv " + this.getWiUfv() + "changed from " + uyvChange.getPriorValue() + " to " + uyvChange.getNewValue().toString() + ". Wiping out WE fields"));
            FieldChanges fieldChanges = new FieldChanges();
            fieldChanges.setFieldChange(IMovesField.WI_MOVE_STAGE, (Object)WiMoveStageEnum.NONE);
            fieldChanges.setFieldChange(IMovesField.WI_CONFIRMED_MOVE_STAGE, (Object)WiMoveStageEnum.NONE);
            WorkInstruction.resetHistoryFields(fieldChanges);
            inOutMoreChanges.addFieldChanges(fieldChanges);
        }
        if (inChanges.hasFieldChange(IMovesField.WI_WQ_PKEY)) {
            if (!inChanges.hasFieldChange(IMovesField.WI_WORK_QUEUE)) {
                Long newWiWqPkey = (Long)inChanges.getFieldChange(IMovesField.WI_WQ_PKEY).getNewValue();
                WorkQueue wq = WorkQueue.findByPkey(this.getYardKeyConsideringChanges(inChanges), newWiWqPkey);
                this.setSelfAndFieldChange(IMovesField.WI_WORK_QUEUE, wq, inOutMoreChanges);
            }
        } else if (inChanges.hasFieldChange(IMovesField.WI_WORK_QUEUE)) {
            WorkQueue wq = (WorkQueue)inChanges.getFieldChange(IMovesField.WI_WORK_QUEUE).getNewValue();
            newPkey = wq == null ? null : wq.getWqPkey();
            this.setSelfAndFieldChange(IMovesField.WI_WQ_PKEY, newPkey, inOutMoreChanges);
        }
        if (inChanges.hasFieldChange(IMovesField.WI_YARD_SHIFT_TARGET_WI_PKEY)) {
            Long newYardShiftTargetWiPkey;
            if (!inChanges.hasFieldChange(IMovesField.WI_YARD_SHIFT_TARGET_WI) && !(newYardShiftTargetWiPkey = (Long)inChanges.getFieldChange(IMovesField.WI_YARD_SHIFT_TARGET_WI_PKEY).getNewValue()).equals(this.getWiYardShiftTargetWiPkey())) {
                wi2 = WorkInstruction.findByPkey(this.getYardConsideringChanges(inChanges), newYardShiftTargetWiPkey);
                this.setSelfAndFieldChange(IMovesField.WI_YARD_SHIFT_TARGET_WI, wi2, inOutMoreChanges);
            }
        } else if (inChanges.hasFieldChange(IMovesField.WI_YARD_SHIFT_TARGET_WI)) {
            wi = (WorkInstruction)inChanges.getFieldChange(IMovesField.WI_YARD_SHIFT_TARGET_WI).getNewValue();
            newPkey = wi == null ? null : wi.getWiPkey();
            this.setSelfAndFieldChange(IMovesField.WI_YARD_SHIFT_TARGET_WI_PKEY, newPkey, inOutMoreChanges);
        }
        if (inChanges.hasFieldChange(IMovesField.WI_CHE_INDEX)) {
            if (!inChanges.hasFieldChange(IMovesField.WI_CHE)) {
                newCheId = (Long)inChanges.getFieldChange(IMovesField.WI_CHE_INDEX).getNewValue();
                che2 = Che.findChe((Long)newCheId, (Yard)this.getYardConsideringChanges(inChanges));
                this.setSelfAndFieldChange(IMovesField.WI_CHE, (Object)che2, inOutMoreChanges);
            }
        } else if (inChanges.hasFieldChange(IMovesField.WI_CHE)) {
            che = (Che)inChanges.getFieldChange(IMovesField.WI_CHE).getNewValue();
            newID = che == null ? null : che.getCheId();
            this.setSelfAndFieldChange(IMovesField.WI_CHE_INDEX, newID, inOutMoreChanges);
        }
        if (inChanges.hasFieldChange(IMovesField.WI_ITV_ID)) {
            Che itv;
            Long newItvId = (Long)inChanges.getFieldChange(IMovesField.WI_ITV_ID).getNewValue();
            if (inChanges.hasFieldChange(IMovesField.WI_ITV)) {
                if (this.shouldLogSuspectUpdates()) {
                    itv = (Che)inChanges.getFieldChange(IMovesField.WI_ITV).getNewValue();
                    if (itv != null && !itv.getCheId().equals(newItvId)) {
                        LOGGER.warn((Object)("Change of " + wiStatus + " " + this.describeWi() + ", " + (Object)inChanges.getFieldChange(IMovesField.WI_ITV) + ", and UNMATCHING " + (Object)inChanges.getFieldChange(IMovesField.WI_ITV_ID)));
                    } else if (this.shouldLogSuspectUpdates() && LOGGER.isEnabledFor(logPriority)) {
                        LOGGER.log(logPriority, (Object)("Change of " + wiStatus + " " + this.describeWi() + ", " + (Object)inChanges.getFieldChange(IMovesField.WI_ITV) + ", and matching " + (Object)inChanges.getFieldChange(IMovesField.WI_ITV_ID)));
                    }
                }
            } else {
                itv = Che.findChe((Long)newItvId, (Yard)this.getYardConsideringChanges(inChanges));
                this.setSelfAndFieldChange(IMovesField.WI_ITV, (Object)itv, inOutMoreChanges);
                if (this.shouldLogSuspectUpdates() && LOGGER.isEnabledFor(logPriority)) {
                    LOGGER.log(logPriority, (Object)("Setting " + (Object)IMovesField.WI_ITV + " of " + wiStatus + " " + this.describeWi() + " to " + (Object)itv + " on change " + (Object)inChanges.getFieldChange(IMovesField.WI_ITV_ID)));
                }
            }
        } else if (inChanges.hasFieldChange(IMovesField.WI_ITV)) {
            Che itv = (Che)inChanges.getFieldChange(IMovesField.WI_ITV).getNewValue();
            newID = itv == null ? null : itv.getCheId();
            this.setSelfAndFieldChange(IMovesField.WI_ITV_ID, newID, inOutMoreChanges);
            if (this.shouldLogSuspectUpdates() && LOGGER.isEnabledFor(logPriority)) {
                LOGGER.log(logPriority, (Object)("Setting " + (Object)IMovesField.WI_ITV_ID + " of " + wiStatus + " " + this.describeWi() + " to " + newID + " on change " + (Object)inChanges.getFieldChange(IMovesField.WI_ITV)));
            }
        }
        if (inChanges.hasFieldChange(IMovesField.WI_CHE_WORK_ASSIGNMENT_PKEY)) {
            if (!inChanges.hasFieldChange(IMovesField.WI_CHE_WORK_ASSIGNMENT)) {
                newWaPkey = (Long)inChanges.getFieldChange(IMovesField.WI_CHE_WORK_ASSIGNMENT_PKEY).getNewValue();
                wa2 = WorkAssignment.findByPkey((Serializable)this.getYardKeyConsideringChanges(inChanges), (Long)newWaPkey);
                this.setSelfAndFieldChange(IMovesField.WI_CHE_WORK_ASSIGNMENT, (Object)wa2, inOutMoreChanges);
            }
        } else if (inChanges.hasFieldChange(IMovesField.WI_CHE_WORK_ASSIGNMENT)) {
            wa = (WorkAssignment)inChanges.getFieldChange(IMovesField.WI_CHE_WORK_ASSIGNMENT).getNewValue();
            newPkey = wa == null ? null : wa.getWorkassignmentPkey();
            this.setSelfAndFieldChange(IMovesField.WI_CHE_WORK_ASSIGNMENT_PKEY, newPkey, inOutMoreChanges);
        }
        if (inChanges.hasFieldChange(IMovesField.WI_ITV_WORK_ASSIGNMENT_PKEY)) {
            if (!inChanges.hasFieldChange(IMovesField.WI_ITV_WORK_ASSIGNMENT)) {
                newWaPkey = (Long)inChanges.getFieldChange(IMovesField.WI_ITV_WORK_ASSIGNMENT_PKEY).getNewValue();
                wa2 = WorkAssignment.findByPkey((Serializable)this.getYardKeyConsideringChanges(inChanges), (Long)newWaPkey);
                this.setSelfAndFieldChange(IMovesField.WI_ITV_WORK_ASSIGNMENT, (Object)wa2, inOutMoreChanges);
                if (wa2 != null && wa2.getWorkassignmentChe() != null) {
                    this.setSelfAndFieldChange(IMovesField.WI_ITV, (Object)wa2.getWorkassignmentChe(), inOutMoreChanges);
                }
            }
        } else if (inChanges.hasFieldChange(IMovesField.WI_ITV_WORK_ASSIGNMENT)) {
            wa = (WorkAssignment)inChanges.getFieldChange(IMovesField.WI_ITV_WORK_ASSIGNMENT).getNewValue();
            newPkey = wa == null ? null : wa.getWorkassignmentPkey();
            this.setSelfAndFieldChange(IMovesField.WI_ITV_WORK_ASSIGNMENT_PKEY, newPkey, inOutMoreChanges);
        }
        if (inChanges.hasFieldChange(IMovesField.WI_INTENDED_CHE_INDEX)) {
            if (!inChanges.hasFieldChange(IMovesField.WI_INTENDED_CHE) && !Objects.equals(newCheId = (Long)inChanges.getFieldChange(IMovesField.WI_INTENDED_CHE_INDEX).getNewValue(), this.getWiIntendedCheIndex())) {
                che2 = Che.findChe((Long)newCheId, (Yard)this.getYardConsideringChanges(inChanges));
                this.setSelfAndFieldChange(IMovesField.WI_INTENDED_CHE, (Object)che2, inOutMoreChanges);
            }
        } else if (inChanges.hasFieldChange(IMovesField.WI_INTENDED_CHE)) {
            che = (Che)inChanges.getFieldChange(IMovesField.WI_INTENDED_CHE).getNewValue();
            newID = che == null ? null : che.getCheId();
            this.setSelfAndFieldChange(IMovesField.WI_INTENDED_CHE_INDEX, newID, inOutMoreChanges);
        }
        if (inChanges.hasFieldChange(IMovesField.WI_ACTUAL_VESSEL_TWIN_WI_PKEY)) {
            Long newTwinPkey;
            if (!inChanges.hasFieldChange(IMovesField.WI_ACTUAL_VESSEL_TWIN_WI) && !(newTwinPkey = (Long)inChanges.getFieldChange(IMovesField.WI_ACTUAL_VESSEL_TWIN_WI_PKEY).getNewValue()).equals(this.getWiActualVesselTwinWiPkey())) {
                wi2 = WorkInstruction.findByPkey(this.getYardConsideringChanges(inChanges), newTwinPkey);
                this.setSelfAndFieldChange(IMovesField.WI_YARD_SHIFT_TARGET_WI, wi2, inOutMoreChanges);
            }
        } else if (inChanges.hasFieldChange(IMovesField.WI_ACTUAL_VESSEL_TWIN_WI)) {
            wi = (WorkInstruction)inChanges.getFieldChange(IMovesField.WI_ACTUAL_VESSEL_TWIN_WI).getNewValue();
            newPkey = wi == null ? null : wi.getWiPkey();
            this.setSelfAndFieldChange(IMovesField.WI_ACTUAL_VESSEL_TWIN_WI_PKEY, newPkey, inOutMoreChanges);
        }
        if (inChanges.hasFieldChange(IMovesField.WI_POSITION)) {
            LocPosition wiToPosition;
            LocPosition fromPositon = this.getWiFromPosition();
            CarrierIncompatibilityReasonEnum ufvIncompatibilityReasonEnum = null;
            if (fromPositon != null && LocTypeEnum.RAILCAR.equals((Object)fromPositon.getPosLocType())) {
                ufvIncompatibilityReasonEnum = this.determineIncompatibilityReasonForFromPosition(fromPositon, false);
            }
            if ((ufvIncompatibilityReasonEnum == null || CarrierIncompatibilityReasonEnum.NONE.equals((Object)ufvIncompatibilityReasonEnum) || CarrierIncompatibilityReasonEnum.RAIL_CONES_NEED_UNLOCKING.equals((Object)ufvIncompatibilityReasonEnum)) && (wiToPosition = this.getWiToPosition()) != null && LocTypeEnum.RAILCAR.equals((Object)wiToPosition.getPosLocType())) {
                this.determineIncompatibilityReasonForToPosition(wiToPosition, true);
                if (CarrierIncompatibilityReasonEnum.RAIL_CONES_NEED_UNLOCKING.equals((Object)ufvIncompatibilityReasonEnum)) {
                    this.updateWiUfvinCompReasonDeterminedByFromPosition(ufvIncompatibilityReasonEnum);
                }
            }
        }
        if (WiMoveKindEnum.VeslLoad.equals((Object)this.getWiMoveKind()) && inChanges.hasFieldChange(IMovesField.WI_MOVE_STAGE)) {
            WiMoveStageEnum stageIs = (WiMoveStageEnum)inChanges.getFieldChange(IMovesField.WI_MOVE_STAGE).getNewValue();
            UnitFacilityVisit ufv = this.getWiUfv();
            if (stageIs != null && stageIs != WiMoveStageEnum.NONE && stageIs != WiMoveStageEnum.PLANNED && stageIs != WiMoveStageEnum.COMPLETE && ufv != null) {
                UnitFacilityVisit.updateFlexLoadMTFieldsForUpcomingLoadMove(this, ufv.getUfvGkey());
            }
        }
    }

    private static void resetHistoryFields(FieldChanges inOutFieldChanges) {
        inOutFieldChanges.setFieldChange(IMovesField.MVHS_FETCH_CHE_INDEX, null);
        inOutFieldChanges.setFieldChange(IMovesField.MVHS_FETCH_CHE_ID, null);
        inOutFieldChanges.setFieldChange(IMovesField.MVHS_CARRY_CHE_INDEX, null);
        inOutFieldChanges.setFieldChange(IMovesField.MVHS_CARRY_CHE_ID, null);
        inOutFieldChanges.setFieldChange(IMovesField.MVHS_PUT_CHE_INDEX, null);
        inOutFieldChanges.setFieldChange(IMovesField.MVHS_PUT_CHE_ID, null);
        inOutFieldChanges.setFieldChange(IMovesField.MVHS_DIST_TO_START, null);
        inOutFieldChanges.setFieldChange(IMovesField.MVHS_DIST_OF_CARRY, null);
        inOutFieldChanges.setFieldChange(IMovesField.MVHS_TIME_CARRY_COMPLETE, null);
        inOutFieldChanges.setFieldChange(IMovesField.MVHS_TIME_DISPATCH, null);
        inOutFieldChanges.setFieldChange(IMovesField.MVHS_TIME_FETCH, null);
        inOutFieldChanges.setFieldChange(IMovesField.MVHS_TIME_PUT, null);
        inOutFieldChanges.setFieldChange(IMovesField.MVHS_TIME_CARRY_CHE_FETCH_READY, null);
        inOutFieldChanges.setFieldChange(IMovesField.MVHS_TIME_CARRY_CHE_PUT_READY, null);
        inOutFieldChanges.setFieldChange(IMovesField.MVHS_TIME_CARRY_CHE_DISPATCH, null);
        inOutFieldChanges.setFieldChange(IMovesField.MVHS_TIME_DISCHARGE, null);
        inOutFieldChanges.setFieldChange(IMovesField.MVHS_REHANDLE_COUNT, null);
        inOutFieldChanges.setFieldChange(IMovesField.MVHS_POW_PKEY, null);
        inOutFieldChanges.setFieldChange(IMovesField.MVHS_POOL_PKEY, null);
        inOutFieldChanges.setFieldChange(IMovesField.MVHS_TWIN_FETCH, null);
        inOutFieldChanges.setFieldChange(IMovesField.MVHS_TWIN_CARRY, null);
        inOutFieldChanges.setFieldChange(IMovesField.MVHS_TWIN_PUT, null);
        inOutFieldChanges.setFieldChange(IMovesField.MVHS_TANDEM_FETCH, null);
        inOutFieldChanges.setFieldChange(IMovesField.MVHS_TANDEM_PUT, null);
        inOutFieldChanges.setFieldChange(IMovesField.MVHS_T_Z_ARRIVAL_TIME, null);
    }

    private CarrierIncompatibilityReasonEnum determineIncompatibilityReasonForFromPosition(LocPosition inFromPosition, boolean inTopCtrChangsReqd) {
        IRailcarVisitLocation railcarVisit = (IRailcarVisitLocation)inFromPosition.resolveLocation();
        Map reasonsMap = null;
        UnitFacilityVisit wiUfv = this.getWiUfv();
        boolean isWiForTbdUnit = this.getWiTbdUnit() != null;
        TbdUnit wiTbdUnit = this.getWiTbdUnit();
        long wiEntityGkey = isWiForTbdUnit ? this.getWiTbdUnit().getTbduGkey() : this.getWiUfv().getUfvGkey();
        if (wiUfv != null) {
            reasonsMap = railcarVisit.determineCarrierIncompatibleReason(inFromPosition, (DatabaseEntity)wiUfv, (DatabaseEntity)this, wiUfv.getUfvRailConeStatus(), inTopCtrChangsReqd);
        } else if (wiTbdUnit != null) {
            reasonsMap = railcarVisit.determineCarrierIncompatibleReason(inFromPosition, null, (DatabaseEntity)this, RailConeStatusEnum.UNKNOWN, inTopCtrChangsReqd);
        }
        return WorkInstruction.updateIncompatibilityReason(reasonsMap, isWiForTbdUnit, wiEntityGkey);
    }

    public static CarrierIncompatibilityReasonEnum updateIncompatibilityReason(Map<String, AtomizedEnum> inIncompReasonMap, boolean inIsWiForTbdUnit, long inWiEntityGkey) {
        CarrierIncompatibilityReasonEnum thisWiEntityIncompReason = CarrierIncompatibilityReasonEnum.NONE;
        CarrierIncompatibilityReasonEnum dependentUfvIncompReason = CarrierIncompatibilityReasonEnum.NONE;
        HashMap<Long, FieldChanges> ufvChanges = new HashMap<Long, FieldChanges>();
        HashMap<Long, FieldChanges> tbdunitChanges = new HashMap<Long, FieldChanges>();
        for (Map.Entry<String, AtomizedEnum> entry : inIncompReasonMap.entrySet()) {
            String key = entry.getKey();
            String entityPrefix = key.substring(0, 3);
            String entityGkeyStr = key.substring(3);
            long entityGkey = Long.parseLong(entityGkeyStr);
            if (UFV.equals(entityPrefix)) {
                dependentUfvIncompReason = (CarrierIncompatibilityReasonEnum)entry.getValue();
                FieldChanges fieldChanges = new FieldChanges();
                fieldChanges.setFieldChange(IInventoryField.UFV_CARRIER_INCOMPATIBLE_REASON, (Object)dependentUfvIncompReason);
                ufvChanges.put(entityGkey, fieldChanges);
                UnitFacilityVisit.updateCarrierIncompatibilityFlag(ufvChanges);
                if (inIsWiForTbdUnit || entityGkey != inWiEntityGkey) continue;
                thisWiEntityIncompReason = dependentUfvIncompReason;
                continue;
            }
            if (!TBD_UNIT.equals(entityPrefix)) continue;
            dependentUfvIncompReason = (CarrierIncompatibilityReasonEnum)entry.getValue();
            TbdUnit tbdUnit = TbdUnit.hydrate(Long.valueOf(entityGkey));
            FieldChanges fieldChanges = new FieldChanges();
            fieldChanges.setFieldChange(IInventoryField.TBDU_CARRIER_INCOMPATIBLE_REASON, (Object)dependentUfvIncompReason);
            tbdunitChanges.put(entityGkey, fieldChanges);
            TbdUnit.updateCarrierIncompatibilityFlag(tbdunitChanges);
            if (!inIsWiForTbdUnit || entityGkey != inWiEntityGkey) continue;
            thisWiEntityIncompReason = dependentUfvIncompReason;
        }
        return thisWiEntityIncompReason;
    }

    private CarrierIncompatibilityReasonEnum determineIncompatibilityReasonForToPosition(LocPosition inCurPosition, boolean inTopCtrChangsReqd) {
        CarrierIncompatibilityReasonEnum inCompDuePins;
        IRailcarVisitLocation railcarVisit = (IRailcarVisitLocation)inCurPosition.resolveLocation();
        Map reasonsMap = null;
        UnitFacilityVisit wiUfv = this.getWiUfv();
        TbdUnit wiTbdUnit = this.getWiTbdUnit();
        boolean isWiForTbdUnit = wiTbdUnit != null;
        long wiEntityGkey = isWiForTbdUnit ? wiTbdUnit.getTbduGkey() : wiUfv.getUfvGkey();
        if (wiUfv != null) {
            CarrierIncompatibilityReasonEnum inCompDuePins2 = wiUfv.checkIncompatibilityForLoad(wiUfv, this.getWiToPosition());
            if (CarrierIncompatibilityReasonEnum.NONE.equals((Object)inCompDuePins2)) {
                reasonsMap = railcarVisit.determineCarrierIncompatibleReason(inCurPosition, (DatabaseEntity)wiUfv, (DatabaseEntity)this, wiUfv.getUfvRailConeStatus(), inTopCtrChangsReqd);
                return WorkInstruction.updateIncompatibilityReason(reasonsMap, isWiForTbdUnit, wiEntityGkey);
            }
        } else if (wiTbdUnit != null && CarrierIncompatibilityReasonEnum.NONE.equals((Object)(inCompDuePins = wiTbdUnit.checkIncompatibilityForLoad(inCurPosition)))) {
            reasonsMap = railcarVisit.determineCarrierIncompatibleReason(inCurPosition, (DatabaseEntity)wiTbdUnit, (DatabaseEntity)this, RailConeStatusEnum.UNKNOWN, inTopCtrChangsReqd);
            return WorkInstruction.updateIncompatibilityReason(reasonsMap, isWiForTbdUnit, wiEntityGkey);
        }
        return CarrierIncompatibilityReasonEnum.NONE;
    }

    public void preProcessInsert(FieldChanges inOutMoreChanges) {
        LocPosition wiToPosition;
        Date wiTimeCreated;
        Long wiActualVesselTwinWiPkey;
        Long wiIntendedCheIndex;
        Long wiItvWorkAssignmentPkey;
        Long wiCheWorkAssignmentPkey;
        Long wiItvId;
        Long wiCheIndex;
        WorkQueue wq;
        super.preProcessInsert(inOutMoreChanges);
        Long wqPkey = this.getWiWqPkey();
        if (wqPkey == null || wqPkey == 0L) {
            if (this.getWiWorkQueue() != null) {
                this.setSelfAndFieldChange(IMovesField.WI_WQ_PKEY, this.getWiWorkQueue().getWqPkey(), inOutMoreChanges);
            } else {
                LOGGER.error((Object)"WI processed insert where required WQ value is not specified in either WI_WORK_QUEUE or WI_WQ_PKEY");
            }
        } else if (this.getWiWorkQueue() == null && (wq = WorkQueue.findByPkey(this.getUyvYardGkey(), wqPkey)) != null) {
            this.setSelfAndFieldChange(IMovesField.WI_WORK_QUEUE, wq, inOutMoreChanges);
        }
        Long shiftTargetPkey = this.getWiYardShiftTargetWiPkey();
        if (shiftTargetPkey == null || shiftTargetPkey == 0L) {
            if (this.getWiYardShiftTargetWi() != null) {
                this.setSelfAndFieldChange(IMovesField.WI_YARD_SHIFT_TARGET_WI_PKEY, this.getWiYardShiftTargetWi().getWiPkey(), inOutMoreChanges);
            }
        } else if (this.getWiYardShiftTargetWi() == null) {
            this.setSelfAndFieldChange(IMovesField.WI_YARD_SHIFT_TARGET_WI, this.getShiftTargetFromTargetPkey(), inOutMoreChanges);
        }
        if ((wiCheIndex = this.getWiCheIndex()) == null || wiCheIndex == 0L) {
            if (this.getWiChe() != null) {
                this.setSelfAndFieldChange(IMovesField.WI_CHE_INDEX, this.getWiChe().getCheId(), inOutMoreChanges);
            }
        } else if (this.getWiChe() == null) {
            this.setSelfAndFieldChange(IMovesField.WI_CHE, (Object)this.getCheFromId(this.getWiCheIndex()), inOutMoreChanges);
        }
        if ((wiItvId = this.getWiItvId()) == null || wiItvId == 0L) {
            if (this.getWiItv() != null) {
                this.setSelfAndFieldChange(IMovesField.WI_ITV_ID, this.getWiItv().getCheId(), inOutMoreChanges);
            }
        } else if (this.getWiItv() == null) {
            this.setSelfAndFieldChange(IMovesField.WI_ITV, (Object)this.getCheFromId(this.getWiItvId()), inOutMoreChanges);
        }
        if ((wiCheWorkAssignmentPkey = this.getWiCheWorkAssignmentPkey()) == null || wiCheWorkAssignmentPkey == 0L) {
            if (this.getWiCheWorkAssignment() != null) {
                this.setSelfAndFieldChange(IMovesField.WI_CHE_WORK_ASSIGNMENT_PKEY, this.getWiCheWorkAssignment().getWorkassignmentPkey(), inOutMoreChanges);
            }
        } else if (this.getWiCheWorkAssignment() == null) {
            this.setSelfAndFieldChange(IMovesField.WI_CHE_WORK_ASSIGNMENT, (Object)WorkAssignment.findByPkey((Serializable)this.getUyvYardGkey(), (Long)wiCheWorkAssignmentPkey), inOutMoreChanges);
        }
        if ((wiItvWorkAssignmentPkey = this.getWiItvWorkAssignmentPkey()) == null || wiItvWorkAssignmentPkey == 0L) {
            if (this.getWiItvWorkAssignment() != null) {
                this.setSelfAndFieldChange(IMovesField.WI_ITV_WORK_ASSIGNMENT_PKEY, this.getWiItvWorkAssignment().getWorkassignmentPkey(), inOutMoreChanges);
            }
        } else if (this.getWiItvWorkAssignment() == null) {
            this.setSelfAndFieldChange(IMovesField.WI_ITV_WORK_ASSIGNMENT, (Object)WorkAssignment.findByPkey((Serializable)this.getUyvYardGkey(), (Long)wiItvWorkAssignmentPkey), inOutMoreChanges);
        }
        if ((wiIntendedCheIndex = this.getWiIntendedCheIndex()) == null || wiIntendedCheIndex == 0L) {
            if (this.getWiIntendedChe() != null) {
                this.setSelfAndFieldChange(IMovesField.WI_INTENDED_CHE_INDEX, this.getWiIntendedChe().getCheId(), inOutMoreChanges);
            }
        } else if (this.getWiIntendedChe() == null) {
            this.setSelfAndFieldChange(IMovesField.WI_INTENDED_CHE, (Object)this.getIntendCheFromIntendCheId(), inOutMoreChanges);
        }
        if ((wiActualVesselTwinWiPkey = this.getWiActualVesselTwinWiPkey()) == null || wiActualVesselTwinWiPkey == 0L) {
            if (this.getWiActualVesselTwinWi() != null) {
                this.setSelfAndFieldChange(IMovesField.WI_ACTUAL_VESSEL_TWIN_WI_PKEY, this.getWiActualVesselTwinWi().getWiPkey(), inOutMoreChanges);
            }
        } else if (this.getWiActualVesselTwinWi() == null) {
            this.setSelfAndFieldChange(IMovesField.WI_ACTUAL_VESSEL_TWIN_WI, this.getActualTwinFromActualTwinPkey(), inOutMoreChanges);
        }
        if ((wiTimeCreated = this.getWiTimeCreated()) == null) {
            this.setSelfAndFieldChange(IMovesField.WI_TIME_CREATED, new Date(), inOutMoreChanges);
        }
        LocPosition fromPositon = this.getWiFromPosition();
        CarrierIncompatibilityReasonEnum ufvIncompatibilityReasonEnum = null;
        if (fromPositon != null && LocTypeEnum.RAILCAR.equals((Object)fromPositon.getPosLocType())) {
            ufvIncompatibilityReasonEnum = this.determineIncompatibilityReasonForFromPosition(fromPositon, false);
        }
        if ((ufvIncompatibilityReasonEnum == null || CarrierIncompatibilityReasonEnum.NONE.equals((Object)ufvIncompatibilityReasonEnum) || CarrierIncompatibilityReasonEnum.RAIL_CONES_NEED_UNLOCKING.equals((Object)ufvIncompatibilityReasonEnum)) && (wiToPosition = this.getWiToPosition()) != null && LocTypeEnum.RAILCAR.equals((Object)wiToPosition.getPosLocType())) {
            this.determineIncompatibilityReasonForToPosition(wiToPosition, true);
            if (CarrierIncompatibilityReasonEnum.RAIL_CONES_NEED_UNLOCKING.equals((Object)ufvIncompatibilityReasonEnum)) {
                this.updateWiUfvinCompReasonDeterminedByFromPosition(ufvIncompatibilityReasonEnum);
            }
        }
    }

    public void preProcessDelete(FieldChanges inOutMoreChanges) {
        if (this.getWiPosition().getPosLocType().equals((Object)LocTypeEnum.RAILCAR)) {
            IRailcarVisitLocation railcarVisit = (IRailcarVisitLocation)this.getWiPosition().resolveLocation();
            if (this.getWiUyv() != null) {
                UnitFacilityVisit unitFacilityVisit = this.getWiUfv();
                if (unitFacilityVisit.getUfvCarrierIncompatibleReason() != null) {
                    railcarVisit.resetCarrierIncompatibleReasonForWiDeletion(this.getWiPosition(), (DatabaseEntity)this.getWiUfv());
                }
            } else {
                TbdUnit tbdUnit = this.getWiTbdUnit();
                if (tbdUnit.getTbduCarrierIncompatibleReason() != null) {
                    railcarVisit.resetCarrierIncompatibleReasonForWiDeletion(this.getWiPosition(), (DatabaseEntity)tbdUnit);
                }
            }
        }
//        InventoryServicesUtils.findAndClearAnyUnacknowledgedEcAlarm(this, ContextHelper.getThreadUserContext());
    }

    public static boolean wiKindAllowsMarriage(UnitFacilityVisit inUfv) {
        if (inUfv == null) {
            return false;
        }
        LocPosition lastPos = inUfv.getUfvLastKnownPosition();
        if (lastPos == null || !lastPos.isCarrierPosition()) {
            return false;
        }
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"WorkInstruction").addDqPredicate(PredicateFactory.eq((IMetafieldId)UnitField.WI_UFV, (Object)inUfv.getUfvGkey())).addDqPredicate(PredicateFactory.in((IMetafieldId)IMovesField.WI_MOVE_KIND, (Object[])new WiMoveKindEnum[]{WiMoveKindEnum.VeslDisch, WiMoveKindEnum.RailDisch, WiMoveKindEnum.Receival})).addDqPredicate(PredicateFactory.ne((IMetafieldId)IMovesField.WI_MOVE_STAGE, (Object)WiMoveStageEnum.COMPLETE));
        return HibernateApi.getInstance().existsByDomainQuery(dq);
    }

    public boolean isMovingToTransferZone() {
        return this.getWiToPosition() != null && this.getWiToPosition().isTransferZone();
    }

    public boolean isBeingCarried() {
        return this.getWiMoveStage() == WiMoveStageEnum.CARRY_UNDERWAY;
    }

    public boolean isBeingCarriedByAutomatedItv() {
        Che che = this.getCarryingChe();
        return che != null && che.isAutomatedItv();
    }

    @Nullable
    public Date getValidatedEmtTimeHorizon() {
        Long horizonInMins = this.getWiEmtTimeHorizonMin();
        Date createTime = this.getWiTimeCreated();
        if (horizonInMins == null || horizonInMins < 0L || createTime == null) {
            return null;
        }
        Date result = DateUtils.addMinutes((Date)createTime, (int)horizonInMins.intValue());
        if (result.before(WorkInstruction.getStaleTimeHorizonThreshold())) {
            LOGGER.debug((Object)("EMT time horizon expired: " + result + " for " + this));
            return null;
        }
        return result;
    }

    @Nullable
    public Che getCarryingChe() {
        Che che;
        Che itv;
        if (!this.isBeingCarried()) {
            LOGGER.info((Object)("Move stage of " + this.describeWi() + " is not " + (Object)WiMoveStageEnum.CARRY_UNDERWAY + " cannot find the carrying CHE"));
            return null;
        }
        WorkAssignment itvWa = this.getWiItvWorkAssignment();
        if (itvWa != null && (itv = itvWa.getWorkassignmentChe()) != null && !itv.isAutomatedItv()) {
            return itv;
        }
        String carryCheId = this.getMvhsCarryCheId();
        if (carryCheId != null && (che = Che.findCheByShortName((String)carryCheId, (Yard)ContextHelper.getThreadYard())) != null && che.isAutomatedItv()) {
            return che;
        }
        che = this.getWiChe();
        if (che != null && che.isAutomatedChe()) {
            return che;
        }
        return null;
    }

    public boolean isBypassed() {
        return WiSuspendStateEnum.BYPASS == this.getWiSuspendState() || WiSuspendStateEnum.SYSTEM_BYPASS == this.getWiSuspendState();
    }

    public boolean isSuspended() {
        return WiSuspendStateEnum.SUSPEND == this.getWiSuspendState();
    }

    public static boolean isCarryOrPutCompleteStage(WiMoveStageEnum inWiMoveStageEnum) {
        return inWiMoveStageEnum == WiMoveStageEnum.COMPLETE || inWiMoveStageEnum == WiMoveStageEnum.CARRY_COMPLETE || inWiMoveStageEnum == WiMoveStageEnum.PUT_COMPLETE;
    }

    public boolean isCarryOrPutComplete() {
        return WorkInstruction.isCarryOrPutCompleteStage(this.getWiMoveStage());
    }

    public void generateWqFromWqPkey() {
        WorkQueue targetWq;
        Long yardGkey = this.getWiYardGkey();
        if (yardGkey == null) {
            LOGGER.error((Object)("WI could not generate required WI_WQ constraint because yard could not be determined for wi: " + this.getWiGkey()));
            targetWq = null;
        } else {
            targetWq = WorkQueue.findByPkey(yardGkey, this.getWiWqPkey());
            if (targetWq == null) {
                this.assignToDummyWorkQueue();
                return;
            }
        }
        this.setWiWorkQueue(targetWq);
    }

    public void assignToDummyWorkQueue() {
        WorkQueue targetWq;
        Yard myYard = this.getUyvYard();
        if (myYard == null) {
            LOGGER.error((Object)("WI could not generate required WI_WQ constraint because yard could not be determined for wi: " + this.getWiGkey()));
            targetWq = null;
        } else {
            targetWq = WorkQueue.findOrCreateWorkQueue((Serializable)myYard.getYrdGkey(), myYard.getDummyWQName());
        }
        this.setWiWorkQueue(targetWq);
    }

    public void setShiftTargetFromTargetPkey() {
        this.setWiYardShiftTargetWi(this.getShiftTargetFromTargetPkey());
    }

    public void setCheFromCheId() {
        this.setCheFromCheId(this.getWiCheIndex());
    }

    public void setCheFromCheId(Long inId) {
        this.setWiChe(this.getCheFromId(inId));
        this.setWiCheIndex(inId);
    }

    public void setItvFromItvId() {
        this.setItvFromItvId(this.getWiItvId());
    }

    public void setItvFromItvId(Long inId) {
        this.setWiItv(this.getCheFromId(inId));
        this.setWiItvId(inId);
    }

    public void setCheWaFromCheWaPkey() {
        this.setWiCheWorkAssignment(WorkAssignment.findByPkey((Serializable)this.getUyvYardGkey(), (Long)this.getWiCheWorkAssignmentPkey()));
    }

    public void setItvWaFromItvWaPkey() {
        this.setWiItvWorkAssignment(WorkAssignment.findByPkey((Serializable)this.getUyvYardGkey(), (Long)this.getWiItvWorkAssignmentPkey()));
    }

    public void setIntendCheFromIntendCheId() {
        this.setWiIntendedChe(this.getIntendCheFromIntendCheId());
    }

    public void updateIntendedChe(@Nullable Che inIntendedChe) {
        if (inIntendedChe != null) {
            this.setWiIntendedCheIndex(inIntendedChe.getCheId());
            this.setIntendCheFromIntendCheId();
        } else {
            this.setWiIntendedCheIndex(null);
            this.setWiIntendedChe(null);
        }
    }

    public void setActualTwinFromActualTwinPkey() {
        this.setWiActualVesselTwinWi(this.getActualTwinFromActualTwinPkey());
    }

    public void setEstimatedMoveTime(Date inWiEstimateMoveTime) {
        this.setWiEstimatedMoveTime(inWiEstimateMoveTime);
    }

    public void setTimeOrigEstStart(Date inWiTimeOrigEstStart) {
        this.setWiTimeOrigEstStart(inWiTimeOrigEstStart);
    }

    public void updateWiActualVesselTwinWi(WorkInstruction inWi) {
        this.setWiActualVesselTwinWi(inWi);
    }

    public void updateWiYardShiftTargetWi(WorkInstruction inWi) {
        this.setWiYardShiftTargetWi(inWi);
    }

    public void updateWiCheWorkAssignment(WorkAssignment inWa) {
        this.setWiCheWorkAssignment(inWa);
        this.setWiCheWorkAssignmentPkey(inWa == null ? null : inWa.getWorkassignmentPkey());
    }

    public void updateWiItvWorkAssignment(WorkAssignment inWa) {
        this.setWiItvWorkAssignment(inWa);
        this.setWiItvWorkAssignmentPkey(inWa == null ? null : inWa.getWorkassignmentPkey());
    }

    public void updateWiWorkQueue(WorkQueue inWq) {
        this.setWiWorkQueue(inWq);
    }

    public void updateWiPosition(LocPosition inLocPosition) {
        this.setWiPosition(inLocPosition);
    }

    public void updateWiNominatedChe(Che inWiNominatedChe) {
        this.setWiNominatedChe(inWiNominatedChe);
    }

    @Nullable
    private Long getYardKeyConsideringChanges(FieldChanges inChanges) {
        Yard yard = this.getYardConsideringChanges(inChanges);
        return yard == null ? null : yard.getYrdGkey();
    }

    @Nullable
    private Yard getYardConsideringChanges(FieldChanges inChanges) {
        if (inChanges.hasFieldChange(IMovesField.WI_UYV)) {
            UnitYardVisit uyv = (UnitYardVisit)inChanges.getFieldChange(IMovesField.WI_UYV).getNewValue();
            return uyv == null ? null : uyv.getUyvYard();
        }
        return this.getUyvYard();
    }

    @Nullable
    private Yard getUyvYard() {
        UnitYardVisit uyv = this.getWiUyv();
        return uyv == null ? null : uyv.getUyvYard();
    }

    @Nullable
    private Long getUyvYardGkey() {
        Yard uyvYard = this.getUyvYard();
        return uyvYard == null ? null : uyvYard.getYrdGkey();
    }

    @Nullable
    private Long getWiYardGkey() {
        Yard uyvYard = null;
        UnitYardVisit uyv = this.getWiUyv();
        if (uyv == null) {
            TbdUnit tbdu = this.getWiTbdUnit();
            if (tbdu != null) {
                Facility fcy = tbdu.getTbduFacility();
                if (fcy != null) {
                    try {
                        uyvYard = fcy.getActiveYard();
                    }
                    catch (BizViolation inBizViolation) {
                        LOGGER.error((Object)("failed to get an active yard for tbdu WI with gkey : " + this.getWiGkey()));
                    }
                }
            } else {
                LOGGER.warn((Object)(" Bad WI, both UYV, and TBDUnit fields are null : " + this.getWiGkey()));
            }
        } else {
            uyvYard = uyv.getUyvYard();
        }
        return uyvYard == null ? null : uyvYard.getYrdGkey();
    }

    @Nullable
    private WorkInstruction getShiftTargetFromTargetPkey() {
        Long wiYardShiftTargetWiPkey = this.getWiYardShiftTargetWiPkey();
        return wiYardShiftTargetWiPkey == null ? null : WorkInstruction.findByPkey(this.getUyvYard(), wiYardShiftTargetWiPkey);
    }

    @Nullable
    private Che getCheFromId(Long inCheId) {
        return inCheId == null ? null : Che.findChe((Long)inCheId, (Yard)this.getUyvYard());
    }

    public void updateWiEcStateDispatch(boolean inValue) {
        this.setWiEcStateDispatch(inValue);
    }

    public void updateWiEcStateItvDispatch(boolean inValue) {
        this.setWiEcStateItvDispatch(inValue);
    }

    public void updateWiEcStateFetch(WiEcStateEnum inWiEcStateFetch) {
        this.setWiEcStateFetch(inWiEcStateFetch);
    }

    private static boolean isNullOrNotEqual(Object inValue1, Object inValue2) {
        if (inValue1 == null) {
            return true;
        }
        return !inValue1.equals(inValue2);
    }

    @Deprecated
    public void updateWiMoveHistory(MoveHistory inMoveHistory) {
        if (inMoveHistory != null) {
            if (WorkInstruction.isNullOrNotEqual(this.getMvhsFetchCheIndex(), inMoveHistory.getMvhsFetchCheIndex())) {
                this.setMvhsFetchCheIndex(inMoveHistory.getMvhsFetchCheIndex());
            }
            if (WorkInstruction.isNullOrNotEqual(this.getMvhsFetchCheId(), inMoveHistory.getMvhsFetchCheId())) {
                this.setMvhsFetchCheId(inMoveHistory.getMvhsFetchCheId());
            }
            if (WorkInstruction.isNullOrNotEqual(this.getMvhsCarryCheIndex(), inMoveHistory.getMvhsCarryCheIndex())) {
                this.setMvhsCarryCheIndex(inMoveHistory.getMvhsCarryCheIndex());
            }
            if (WorkInstruction.isNullOrNotEqual(this.getMvhsCarryCheId(), inMoveHistory.getMvhsCarryCheId())) {
                this.setMvhsCarryCheId(inMoveHistory.getMvhsCarryCheId());
            }
            if (WorkInstruction.isNullOrNotEqual(this.getMvhsPutCheIndex(), inMoveHistory.getMvhsPutCheIndex())) {
                this.setMvhsPutCheIndex(inMoveHistory.getMvhsPutCheIndex());
            }
            if (WorkInstruction.isNullOrNotEqual(this.getMvhsPutCheId(), inMoveHistory.getMvhsPutCheId())) {
                this.setMvhsPutCheId(inMoveHistory.getMvhsPutCheId());
            }
            if (WorkInstruction.isNullOrNotEqual(this.getMvhsDistToStart(), inMoveHistory.getMvhsDistToStart())) {
                this.setMvhsDistToStart(inMoveHistory.getMvhsDistToStart());
            }
            if (WorkInstruction.isNullOrNotEqual(this.getMvhsDistOfCarry(), inMoveHistory.getMvhsDistOfCarry())) {
                this.setMvhsDistOfCarry(inMoveHistory.getMvhsDistOfCarry());
            }
            if (WorkInstruction.isNullOrNotEqual(this.getMvhsTimeCarryComplete(), inMoveHistory.getMvhsTimeCarryComplete())) {
                this.setMvhsTimeCarryComplete(inMoveHistory.getMvhsTimeCarryComplete());
            }
            if (WorkInstruction.isNullOrNotEqual(this.getMvhsTimeDispatch(), inMoveHistory.getMvhsTimeDispatch())) {
                this.setMvhsTimeDispatch(inMoveHistory.getMvhsTimeDispatch());
            }
            if (WorkInstruction.isNullOrNotEqual(this.getMvhsTimeFetch(), inMoveHistory.getMvhsTimeFetch())) {
                this.setMvhsTimeFetch(inMoveHistory.getMvhsTimeFetch());
            }
            if (WorkInstruction.isNullOrNotEqual(this.getMvhsTimePut(), inMoveHistory.getMvhsTimePut())) {
                this.setMvhsTimePut(inMoveHistory.getMvhsTimePut());
            }
            if (WorkInstruction.isNullOrNotEqual(this.getMvhsTimeCarryCheFetchReady(), inMoveHistory.getMvhsTimeCarryCheFetchReady())) {
                this.setMvhsTimeCarryCheFetchReady(inMoveHistory.getMvhsTimeCarryCheFetchReady());
            }
            if (WorkInstruction.isNullOrNotEqual(this.getMvhsTimeCarryChePutReady(), inMoveHistory.getMvhsTimeCarryChePutReady())) {
                this.setMvhsTimeCarryChePutReady(inMoveHistory.getMvhsTimeCarryChePutReady());
            }
            if (WorkInstruction.isNullOrNotEqual(this.getMvhsTimeCarryCheDispatch(), inMoveHistory.getMvhsTimeCarryCheDispatch())) {
                this.setMvhsTimeCarryCheDispatch(inMoveHistory.getMvhsTimeCarryCheDispatch());
            }
            if (WorkInstruction.isNullOrNotEqual(this.getMvhsTimeDischarge(), inMoveHistory.getMvhsTimeDischarge())) {
                this.setMvhsTimeDischarge(inMoveHistory.getMvhsTimeDischarge());
            }
            if (WorkInstruction.isNullOrNotEqual(this.getMvhsRehandleCount(), inMoveHistory.getMvhsRehandleCount())) {
                this.setMvhsRehandleCount(inMoveHistory.getMvhsRehandleCount());
            }
            if (WorkInstruction.isNullOrNotEqual(this.getMvhsPowPkey(), inMoveHistory.getMvhsPowPkey())) {
                this.setMvhsPowPkey(inMoveHistory.getMvhsPowPkey());
            }
            if (WorkInstruction.isNullOrNotEqual(this.getMvhsPoolPkey(), inMoveHistory.getMvhsPoolPkey())) {
                this.setMvhsPoolPkey(inMoveHistory.getMvhsPoolPkey());
            }
            if (WorkInstruction.isNullOrNotEqual(this.getMvhsTwinFetch(), inMoveHistory.getMvhsTwinFetch())) {
                this.setMvhsTwinFetch(inMoveHistory.getMvhsTwinFetch());
            }
            if (WorkInstruction.isNullOrNotEqual(this.getMvhsTwinCarry(), inMoveHistory.getMvhsTwinCarry())) {
                this.setMvhsTwinCarry(inMoveHistory.getMvhsTwinCarry());
            }
            if (WorkInstruction.isNullOrNotEqual(this.getMvhsTwinPut(), inMoveHistory.getMvhsTwinPut())) {
                this.setMvhsTwinPut(inMoveHistory.getMvhsTwinPut());
            }
            if (WorkInstruction.isNullOrNotEqual(this.getMvhsTandemFetch(), inMoveHistory.getMvhsTandemFetch())) {
                this.setMvhsTandemFetch(inMoveHistory.getMvhsTandemFetch());
            }
            if (WorkInstruction.isNullOrNotEqual(this.getMvhsTandemPut(), inMoveHistory.getMvhsTandemPut())) {
                this.setMvhsTandemPut(inMoveHistory.getMvhsTandemPut());
            }
            if (WorkInstruction.isNullOrNotEqual(this.getMvhsTZArrivalTime(), inMoveHistory.getMvhsTZArrivalTime())) {
                this.setMvhsTZArrivalTime(inMoveHistory.getMvhsTZArrivalTime());
            }
            if (WorkInstruction.isNullOrNotEqual(this.getMvhsFetchCheDistance(), inMoveHistory.getMvhsFetchCheDistance())) {
                this.setMvhsFetchCheDistance(inMoveHistory.getMvhsFetchCheDistance());
            }
            if (WorkInstruction.isNullOrNotEqual(this.getMvhsCarryCheDistance(), inMoveHistory.getMvhsCarryCheDistance())) {
                this.setMvhsCarryCheDistance(inMoveHistory.getMvhsCarryCheDistance());
            }
            if (WorkInstruction.isNullOrNotEqual(this.getMvhsPutCheDistance(), inMoveHistory.getMvhsPutCheDistance())) {
                this.setMvhsPutCheDistance(inMoveHistory.getMvhsPutCheDistance());
            }
            if (WorkInstruction.isNullOrNotEqual(this.getMvhsTimeFetchCheDispatch(), inMoveHistory.getMvhsTimeFetchCheDispatch())) {
                this.setMvhsTimeFetchCheDispatch(inMoveHistory.getMvhsTimeFetchCheDispatch());
            }
            if (WorkInstruction.isNullOrNotEqual(this.getMvhsTimePutCheDispatch(), inMoveHistory.getMvhsTimePutCheDispatch())) {
                this.setMvhsTimePutCheDispatch(inMoveHistory.getMvhsTimePutCheDispatch());
            }
        }
    }

    public void updateMvhsFetchCheIndex(Long inMvhsFetchCheIndex) {
        this.setMvhsFetchCheIndex(inMvhsFetchCheIndex);
    }

    public void updateMvhsFetchCheId(String inMvhsFetchCheId) {
        this.setMvhsFetchCheId(inMvhsFetchCheId);
    }

    public void updateMvhsCarryCheIndex(Long inMvhsCarryCheIndex) {
        this.setMvhsCarryCheIndex(inMvhsCarryCheIndex);
    }

    public void updateMvhsCarryCheId(String inMvhsCarryCheId) {
        this.setMvhsCarryCheId(inMvhsCarryCheId);
    }

    public void updateMvhsPutCheIndex(Long inMvhsPutCheIndex) {
        this.setMvhsPutCheIndex(inMvhsPutCheIndex);
    }

    public void updateMvhsPutCheId(String inMvhsPutCheId) {
        this.setMvhsPutCheId(inMvhsPutCheId);
    }

    public void updateMvhsDistToStart(Long inMvhsDistToStart) {
        this.setMvhsDistToStart(inMvhsDistToStart);
    }

    public void updateMvhsDistOfCarry(Long inMvhsDistOfCarry) {
        this.setMvhsDistOfCarry(inMvhsDistOfCarry);
    }

    public void updateMvhsTimeCarryComplete(Date inMvhsTimeCarryComplete) {
        this.setMvhsTimeCarryComplete(inMvhsTimeCarryComplete);
    }

    public void updateMvhsTimeDispatch(Date inMvhsTimeDispatch) {
        this.setMvhsTimeDispatch(inMvhsTimeDispatch);
    }

    public void updateMvhsTimeFetch(Date inMvhsTimeFetch) {
        this.setMvhsTimeFetch(inMvhsTimeFetch);
    }

    public void updateMvhsTimePut(Date inMvhsTimePut) {
        this.setMvhsTimePut(inMvhsTimePut);
    }

    public void updateMvhsTimeCarryCheFetchReady(Date inMvhsTimeCarryCheFetchReady) {
        this.setMvhsTimeCarryCheFetchReady(inMvhsTimeCarryCheFetchReady);
    }

    public void updateMvhsTimeCarryChePutReady(Date inMvhsTimeCarryChePutReady) {
        this.setMvhsTimeCarryChePutReady(inMvhsTimeCarryChePutReady);
    }

    public void updateMvhsTimeCarryCheDispatch(Date inMvhsTimeCarryCheDispatch) {
        this.setMvhsTimeCarryCheDispatch(inMvhsTimeCarryCheDispatch);
    }

    public void updateMvhsTimeDischarge(Date inMvhsTimeDischarge) {
        this.setMvhsTimeDischarge(inMvhsTimeDischarge);
    }

    public void updateMvhsRehandleCount(Long inMvhsRehandleCount) {
        this.setMvhsRehandleCount(inMvhsRehandleCount);
    }

    public void updateMvhsPowPkey(Long inMvhsPowPkey) {
        this.setMvhsPowPkey(inMvhsPowPkey);
    }

    public void updateMvhsPoolPkey(Long inMvhsPoolPkey) {
        this.setMvhsPoolPkey(inMvhsPoolPkey);
    }

    public void updateMvhsTwinFetch(Boolean inMvhsTwinFetch) {
        this.setMvhsTwinFetch(inMvhsTwinFetch);
    }

    public void updateMvhsTwinCarry(Boolean inMvhsTwinCarry) {
        this.setMvhsTwinCarry(inMvhsTwinCarry);
    }

    public void updateMvhsTwinPut(Boolean inMvhsTwinPut) {
        this.setMvhsTwinPut(inMvhsTwinPut);
    }

    public void updateMvhsTandemFetch(Boolean inMvhsTandemFetch) {
        this.setMvhsTandemFetch(inMvhsTandemFetch);
    }

    public void updateMvhsTandemPut(Boolean inMvhsTandemPut) {
        this.setMvhsTandemPut(inMvhsTandemPut);
    }

    public void updateMvhsTZArrivalTime(Date inMvhsTZArrivalTime) {
        this.setMvhsTZArrivalTime(inMvhsTZArrivalTime);
    }

    public void updateMvhsTimeFetchCheDispatch(Date inMvhsFetchCheDispatchTime) {
        this.setMvhsTimeFetchCheDispatch(inMvhsFetchCheDispatchTime);
    }

    public void updateMvhsTimePutCheDispatch(Date inMvhsPutCheDispatchTime) {
        this.setMvhsTimePutCheDispatch(inMvhsPutCheDispatchTime);
    }

    public void updateMvhsFetchCheDistance(Long inMvhsFetchCheDistance) {
        this.setMvhsFetchCheDistance(inMvhsFetchCheDistance);
    }

    public void updateMvhsCarryCheDistance(Long inMvhsCarryCheDistance) {
        this.setMvhsCarryCheDistance(inMvhsCarryCheDistance);
    }

    public void updateMvhsPutCheDistance(Long inMvhsPutCheDistance) {
        this.setMvhsPutCheDistance(inMvhsPutCheDistance);
    }

    public void updateWiMoveStage(WiMoveStageEnum inMoveStage) {
        this.setWiMoveStage(inMoveStage);
    }

    public void updateWiTwinWith(TwinWithEnum inTwinWithEnum) {
        this.setWiTwinWith(inTwinWithEnum);
    }

    public void updateWiIsTandemWithNext(boolean inTandemWithNext) {
        this.setWiIsTandemWithNext(inTandemWithNext);
    }

    public void updateWiIsTandemWithPrevious(boolean inTandemWithPrevious) {
        this.setWiIsTandemWithPrevious(inTandemWithPrevious);
    }

    @Nullable
    private Che getIntendCheFromIntendCheId() {
        Long wiIntendedCheIndex = this.getWiIntendedCheIndex();
        return wiIntendedCheIndex == null ? null : Che.findChe((Long)wiIntendedCheIndex, (Yard)this.getUyvYard());
    }

    @Nullable
    private WorkInstruction getActualTwinFromActualTwinPkey() {
        return WorkInstruction.findByPkey(this.getUyvYard(), this.getWiActualVesselTwinWiPkey());
    }

    @Nullable
    public WorkInstruction getPreviousWorkInstruction() {
        Double moveNumber = this.getWiMoveNumber() == null ? 1.0 : this.getWiMoveNumber();
        if (moveNumber == 0.0) {
            return null;
        }
        List entities = null;
        if (this.getWiUyv() != null) {
            IDomainQuery dq = QueryUtils.createDomainQuery((String)"WorkInstruction").addDqPredicate(PredicateFactory.eq((IMetafieldId)IMovesField.WI_UYV, (Object)this.getWiUyv().getUyvGkey())).addDqPredicate(PredicateFactory.lt((IMetafieldId)IMovesField.WI_MOVE_NUMBER, (Object)moveNumber)).addDqOrdering(Ordering.desc((IMetafieldId)IMovesField.WI_MOVE_NUMBER));
            dq.setDqMaxResults(1);
            entities = HibernateApi.getInstance().findEntitiesByDomainQuery(dq);
        }
        if (entities == null || entities.isEmpty()) {
            return null;
        }
        return (WorkInstruction)entities.get(0);
    }

    public boolean isPreviousWorkInstructionExist() {
        Double moveNumber = this.getWiMoveNumber() == null ? 1.0 : this.getWiMoveNumber();
        if (moveNumber == 0.0) {
            return false;
        }
        if (this.getWiUyv() != null) {
            IDomainQuery dq = QueryUtils.createDomainQuery((String)"WorkInstruction").addDqPredicate(PredicateFactory.eq((IMetafieldId)IMovesField.WI_UYV, (Object)this.getWiUyv().getUyvGkey())).addDqPredicate(PredicateFactory.lt((IMetafieldId)IMovesField.WI_MOVE_NUMBER, (Object)moveNumber)).addDqOrdering(Ordering.desc((IMetafieldId)IMovesField.WI_MOVE_NUMBER));
            return HibernateApi.getInstance().existsByDomainQuery(dq);
        }
        return false;
    }

    @Nullable
    public LocPosition getPreviousWiPosition() {
        WorkInstruction wi = this.getPreviousWorkInstruction();
        return wi == null ? null : wi.getWiPosition();
    }

    @Nullable
    public WorkInstruction getNextWorkInstruction() {
        Double moveNumber = this.getWiMoveNumber() == null ? 1.0 : this.getWiMoveNumber();
        if (moveNumber == 0.0) {
            return null;
        }
        List entities = null;
        if (this.getWiUyv() != null) {
            IDomainQuery dq = QueryUtils.createDomainQuery((String)"WorkInstruction").addDqPredicate(PredicateFactory.eq((IMetafieldId)IMovesField.WI_UYV, (Object)this.getWiUyv().getUyvGkey())).addDqPredicate(PredicateFactory.gt((IMetafieldId)IMovesField.WI_MOVE_NUMBER, (Object)moveNumber)).addDqOrdering(Ordering.asc((IMetafieldId)IMovesField.WI_MOVE_NUMBER));
            dq.setDqMaxResults(1);
            entities = HibernateApi.getInstance().findEntitiesByDomainQuery(dq);
        }
        if (entities == null || entities.isEmpty()) {
            return null;
        }
        return (WorkInstruction)entities.get(0);
    }

    @Nullable
    public LocPosition getNextWiPosition() {
        WorkInstruction wi = this.getNextWorkInstruction();
        return wi == null ? null : wi.getWiPosition();
    }

    @Nullable
    public String getPrimaryEqId() {
        UnitYardVisit uyv = this.getWiUyv();
        return uyv == null ? null : this.getWiUyv().getUyvUfv().getPrimaryEqId();
    }

    public boolean isPrioritized() {
        WiEcStateEnum fetch = this.getWiEcStateFetch();
        return fetch.equals((Object)WiEcStateEnum.PRIORITY_FETCH) || fetch.equals((Object)WiEcStateEnum.PRIORITY_DISPATCH);
    }

    public Container getContainer() {
        return Container.findContainerByFullId((String)this.getWiUfv().getPrimaryEqId());
    }

    public Boolean isLadenDispatchRequested() {
        return this.getWiEcStateItvDispatchRequest();
    }

    public void setLadenDispatchRequested(Boolean inRequestLadenDispatch) {
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace((Object)("setLadenDispatchRequested(" + inRequestLadenDispatch + "), " + this));
        }
        this.setWiEcStateItvDispatchRequest(inRequestLadenDispatch);
    }

    public CarryPositionEnum getCarryPosition() {
        return SparcsInventoryUtils.getN4CarryPosition(this.getWiCheDispatchOrder());
    }

    public void updateCarryPosition(CarryPositionEnum inCarryPositionEnum) {
        this.setWiCheDispatchOrder(SparcsInventoryUtils.getXpsCarryPosition(inCarryPositionEnum));
    }

    @Nullable
    public Long getWiTranGkeyOrNull() {
        Long wiTranGkey = this.getWiTranGkey();
        if (wiTranGkey == null || wiTranGkey == 0L) {
            return null;
        }
        return wiTranGkey;
    }

    @Nullable
    public WorkInstruction getTwinCompanion() {
        return this.getTwinCompanion(true);
    }

    @Nullable
    public WorkInstruction getTwinCompanion(boolean inWithReverseCheck) {
        WorkInstruction companionWi = null;
        if (this.getWiTwinWith() == TwinWithEnum.NEXT) {
            companionWi = this.getNextWiInWq();
            if (companionWi != null && inWithReverseCheck && companionWi.getWiTwinWith() != TwinWithEnum.PREV) {
                companionWi = null;
            }
        } else if (this.getWiTwinWith() == TwinWithEnum.PREV && (companionWi = this.getPrevWiInWq()) != null && inWithReverseCheck && companionWi.getWiTwinWith() != TwinWithEnum.NEXT) {
            companionWi = null;
        }
        if (this.isWiInProgressDischarge() || companionWi != null && companionWi.isWiInProgressDischarge()) {
            LOGGER.warn((Object)("getTwinCompanion() should not be called for in progress vessel discharge because the twin information may no longer be correct [wi=" + this.describeWi() + " companionWi=" + (companionWi != null ? companionWi.describeWi() : "null") + "]"));
        }
        return companionWi;
    }

    public boolean isWiInProgressDischarge() {
        return WiMoveKindEnum.VeslDisch.equals((Object)this.getWiMoveKind()) && !WiMoveStageEnum.PLANNED.equals((Object)this.getWiMoveStage()) && !WiMoveStageEnum.FETCH_UNDERWAY.equals((Object)this.getWiMoveStage());
    }

    private static boolean isWiInProgressDischarge(@Nullable IValueHolder inWi) {
        if (inWi == null) {
            return false;
        }
        WiMoveStageEnum mvKind = (WiMoveStageEnum)inWi.getFieldValue(IMovesField.WI_MOVE_KIND);
        WiMoveStageEnum mvStage = (WiMoveStageEnum)inWi.getFieldValue(IMovesField.WI_MOVE_STAGE);
        return WiMoveKindEnum.VeslDisch.equals((Object)mvKind) && !WiMoveStageEnum.PLANNED.equals((Object)mvStage) && !WiMoveStageEnum.FETCH_UNDERWAY.equals((Object)mvStage);
    }

    public boolean isPartOfTandemSet() {
        return this.getWiIsTandemWithNext() != false || this.getWiIsTandemWithPrevious() != false;
    }

    public Set<WorkInstruction> getOtherWorkInstructionsInTandemSet() {
        WorkInstruction next;
        WorkInstruction prev;
        if (!this.isPartOfTandemSet()) {
            return Collections.emptySet();
        }
        List<WorkInstruction> wis = this.getWorkInstructionsBetweenSeq(Math.max(this.getWiSequence() - 3L, 1L), this.getWiSequence() + 3L);
        if (wis.isEmpty()) {
            return Collections.emptySet();
        }
        LinkedHashSet<WorkInstruction> tandemSet = new LinkedHashSet<WorkInstruction>();
        int indexOfThisWi = wis.indexOf(this);
        if (indexOfThisWi < 0) {
            LOGGER.warn((Object) String.format("%s is not available in the result set, the sequence of the work instruction might have been modified, returning empty set for getOtherWorkInstructionsInTandemSet.", this.describeWi()));
            return Collections.emptySet();
        }
        ListIterator<WorkInstruction> prevIterator = wis.listIterator(indexOfThisWi);
        while (prevIterator.hasPrevious() && (prev = prevIterator.previous()).getWiIsTandemWithNext().booleanValue()) {
            tandemSet.add(prev);
            if (prev.getWiIsTandemWithPrevious().booleanValue()) continue;
            break;
        }
        ListIterator<WorkInstruction> fwdIterator = wis.listIterator(indexOfThisWi + 1);
        while (fwdIterator.hasNext() && (next = fwdIterator.next()).getWiIsTandemWithPrevious().booleanValue()) {
            tandemSet.add(next);
            if (next.getWiIsTandemWithNext().booleanValue()) continue;
            break;
        }
        return tandemSet;
    }

    private List<WorkInstruction> getWorkInstructionsBetweenSeq(long inStartSeq, long inEndSeq) {
        IDomainQuery query = QueryUtils.createDomainQuery((String)"WorkInstruction").addDqPredicate(PredicateFactory.eq((IMetafieldId)IMovesField.WI_WORK_QUEUE, (Object)this.getWiWorkQueue().getWqGkey())).addDqPredicate(PredicateFactory.between((IMetafieldId)IMovesField.WI_SEQUENCE, (Comparable) Long.valueOf(inStartSeq), (Comparable) Long.valueOf(inEndSeq))).addDqOrdering(Ordering.asc((IMetafieldId)IMovesField.WI_SEQUENCE)).addDqOrdering(Ordering.desc((IMetafieldId)IMovesField.WI_TIME_CREATED));
        return HibernateApi.getInstance().findEntitiesByDomainQuery(query);
    }

    public Set<WorkInstruction> getAllWorkInstructionsInTandemSet() {
        if (!this.isPartOfTandemSet()) {
            LOGGER.debug((Object)"Both getWiIsTandemWithNext and getWiIsTandemWithPrevious is false, not a part of tandem");
            return Collections.emptySet();
        }
        Set<WorkInstruction> otherWis = this.getOtherWorkInstructionsInTandemSet();
        if (otherWis.isEmpty()) {
            return Collections.emptySet();
        }
        LinkedHashSet<WorkInstruction> tandemLiftSet = new LinkedHashSet<WorkInstruction>();
        tandemLiftSet.add(this);
        tandemLiftSet.addAll(otherWis);
        return tandemLiftSet;
    }

    public WorkInstruction getNextWiInWq() {
        List workInstructions = HibernateApi.getInstance().findEntitiesByDomainQuery(this.getWiForSeqInWqDomainQuery(this.getWiSequence() + 1L));
        return workInstructions.isEmpty() ? null : (WorkInstruction)workInstructions.get(0);
    }

    public WorkInstruction getPrevWiInWq() {
        List workInstructions = HibernateApi.getInstance().findEntitiesByDomainQuery(this.getWiForSeqInWqDomainQuery(this.getWiSequence() - 1L));
        return workInstructions.isEmpty() ? null : (WorkInstruction)workInstructions.get(0);
    }

    private IDomainQuery getWiForSeqInWqDomainQuery(long inSequence) {
        IDomainQuery query = QueryUtils.createDomainQuery((String)"WorkInstruction").addDqPredicate(PredicateFactory.eq((IMetafieldId)IMovesField.WI_WORK_QUEUE, (Object)this.getWiWorkQueue().getWqGkey())).addDqPredicate(PredicateFactory.eq((IMetafieldId)IMovesField.WI_SEQUENCE, (Object)inSequence)).addDqOrdering(Ordering.desc((IMetafieldId)IMovesField.WI_TIME_CREATED));
        query.setMaxResults(1);
        return query;
    }

    public boolean isMarkedForFetch() {
        WiEcStateEnum fetch = this.getWiEcStateFetch();
        return fetch.equals((Object)WiEcStateEnum.PRIORITY_FETCH) || fetch.equals((Object)WiEcStateEnum.PRIORITY_DISPATCH) || fetch.equals((Object)WiEcStateEnum.STANDARD_FETCH) || fetch.equals((Object)WiEcStateEnum.IMMINENT_MOVE) || fetch.equals((Object)WiEcStateEnum.AUTO_FETCH);
    }

    @Nullable
    public static WorkInstruction hydrate(@Nullable Serializable inPrimaryKey) {
        return (WorkInstruction)HibernateApi.getInstance().load(WorkInstruction.class, inPrimaryKey);
    }

    public void setTransferLocation(TransferLocationEnum inTransferLocation) {
        this.setWiPreferredTransferLocation(inTransferLocation);
    }

    public static String listOfWisAsString(List<WorkInstruction> inWiList, boolean inIncludeDispatchStateInfo) {
        StringBuilder wiDesc = new StringBuilder();
        for (WorkInstruction wi : inWiList) {
            if (wiDesc.length() > 0) {
                wiDesc.append(", ");
            }
            if (inIncludeDispatchStateInfo) {
                wiDesc.append(wi.describeWiInclDispatchStateInfo());
                continue;
            }
            wiDesc.append(wi.describeWi());
        }
        return wiDesc.toString();
    }

    public static String listOfWisAsString(List<WorkInstruction> inWiList) {
        return WorkInstruction.listOfWisAsString(inWiList, false);
    }

    public boolean shouldLogSuspectUpdates() {
        return FeatureIdFactory.valueOf((String)"AUTOMATION").isEnabled(ContextHelper.getThreadUserContext());
    }

    public Priority getPriorityForLoggingImportantFieldChange(StringBuffer inOutWiStatus) {
        Priority logPriority = Priority.DEBUG;
        inOutWiStatus.setLength(0);
        if (this.getWiMoveStage() != WiMoveStageEnum.NONE && this.getWiMoveStage() != WiMoveStageEnum.PLANNED && !this.mayConsiderComplete()) {
            inOutWiStatus.append(this.getWiMoveStage().getName());
            logPriority = Priority.WARN;
        } else if (this.getWiMoveStage() == WiMoveStageEnum.PLANNED && (this.getWiEcStateItvDispatch().booleanValue() || this.getWiEcStateDispatch().booleanValue())) {
            inOutWiStatus.append("<dispatched>");
            logPriority = Priority.WARN;
        } else {
            inOutWiStatus.append("<not in progress>");
        }
        return logPriority;
    }

    public static boolean isBeforeLiftOffTruck(WiMoveStageEnum inMoveStage) {
        return inMoveStage == null || WiMoveStageEnum.NONE.equals((Object)inMoveStage) || WiMoveStageEnum.PLANNED.equals((Object)inMoveStage) || WiMoveStageEnum.FETCH_UNDERWAY.equals((Object)inMoveStage) || WiMoveStageEnum.CARRY_READY.equals((Object)inMoveStage);
    }

    public void updateWiMoveKind(WiMoveKindEnum inWiMoveKind) {
        super.setWiMoveKind(inWiMoveKind);
    }

    public void updateWiCarrierLocType(LocTypeEnum inLocTypeEnum) {
        super.setWiCarrierLocType(inLocTypeEnum);
    }

    public void updateWiCarrierLocId(String inWiCarrierLocId) {
        super.setWiCarrierLocId(inWiCarrierLocId);
    }

    public void updateWiMoveNumber(Double inWiMoveNumber) {
        super.setWiMoveNumber(inWiMoveNumber);
    }

    public void updateWiSuspendState(WiSuspendStateEnum inWiSuspendState) {
        super.setWiSuspendState(inWiSuspendState);
    }

    public void updateWiItvDispatchOrder(Long inItvDispatchOrder) {
        super.setWiItvDispatchOrder(inItvDispatchOrder);
    }

    public void updateWiActualSequence(Long inWiActualSequence) {
        super.setWiActualSequence(inWiActualSequence);
    }

    public void updateWiSequence(Long inWiSequence) {
        super.setWiSequence(inWiSequence);
    }

    public void updateWiItv(Che inItv) {
        super.setWiItv(inItv);
    }

    public void updateHasActiveAlarm(Boolean inActive) {
        this.setWiHasActiveAlarm(inActive);
    }

    public void updateWiTwinIntendedPut(boolean inTwinIntendedPut) {
        super.setWiTwinIntendedPut(inTwinIntendedPut);
    }

    public void updateWiTwinIntendedCarry(boolean inTwinIntendedCarry) {
        super.setWiTwinIntendedCarry(inTwinIntendedCarry);
    }

    public void updateWiTwinIntendedFetch(boolean inTwinIntendedFetch) {
        super.setWiTwinIntendedFetch(inTwinIntendedFetch);
    }

    @Deprecated
    public MoveHistory getWiMoveHistory() {
        return new MoveHistory(this);
    }

    public void updateWiDoorDirection(DoorDirectionEnum inDoorDirectionEnum) {
        super.setWiDoorDirection(inDoorDirectionEnum);
    }

    public void updateWiDeckingRestriction(DeckingRestrictionEnum inDeckingRestrictionEnum) {
        super.setWiDeckingRestrict(inDeckingRestrictionEnum);
    }

    public void updateWiTimeModified(Date inTimeModified) {
        this.setWiTimeModified(inTimeModified);
    }

    public boolean mayConsiderComplete() {
        return COMPLETED_WI_MOVESTAGES_SET.contains((Object)this.getWiMoveStage());
    }

    public void swapLoadPlans(WorkInstruction inOutTargetWI) {
        LOGGER.debug((Object)("Swapping planned positions between " + this + " and " + inOutTargetWI));
        LocPosition thisWiPlanPos = this.getWiToPosition();
        LocPosition targetWiPlanPos = inOutTargetWI.getWiToPosition();
        this.updateWiPosition(targetWiPlanPos);
        inOutTargetWI.updateWiPosition(thisWiPlanPos);
    }

    public void swapWqSequenceNums(WorkInstruction inOutTargetWI) {
        LOGGER.debug((Object)("Swapping Work Queue sequence numbers between " + this + " and " + inOutTargetWI));
        Long thisWiSequence = this.getWiSequence();
        Long targetWiSequence = inOutTargetWI.getWiSequence();
        this.setWiSequence(targetWiSequence);
        inOutTargetWI.setWiSequence(thisWiSequence);
    }

    private void resetMoveKindIfNeeded() {
        WiMoveKindEnum newMoveKind = this.calculateMoveKind();
        if (!newMoveKind.equals((Object)this.getWiMoveKind())) {
            this.setWiMoveKind(newMoveKind);
        }
    }

    private void resetOriginalFromLocQualIfNeeded() {
        Character newLocQual = this.calculateOriginalFromQual(null);
        if (!newLocQual.equals(Character.valueOf(this.getWiOriginalFromQual().charAt(0)))) {
            this.setWiOriginalFromQual(String.valueOf(newLocQual));
        }
    }

    public boolean isMvhsFetchCheSet() {
        return this.getMvhsFetchCheIndex() != null && this.getMvhsFetchCheIndex() != 0L;
    }

    public boolean isMvhsCarryCheSet() {
        return this.getMvhsCarryCheIndex() != null && this.getMvhsCarryCheIndex() != 0L;
    }

    public boolean isMvhsPutCheSet() {
        return this.getMvhsPutCheIndex() != null && this.getMvhsPutCheIndex() != 0L;
    }

    public WorkInstruction getTwinLoadDischargeCompanion(Boolean inWithReverseCheck) {
        WorkInstruction companionWi = null;
        if (this.getWiTwinWith() == TwinWithEnum.NEXT) {
            companionWi = this.getTwinLoadDischargeNextWiInWq();
            if (companionWi != null && inWithReverseCheck.booleanValue() && companionWi.getWiTwinWith() != TwinWithEnum.PREV) {
                companionWi = null;
            }
        } else if (this.getWiTwinWith() == TwinWithEnum.PREV && (companionWi = this.getTwinLoadDischargePrevWiInWq()) != null && inWithReverseCheck.booleanValue() && companionWi.getWiTwinWith() != TwinWithEnum.NEXT) {
            companionWi = null;
        }
        if (this.isWiInProgressDischarge() || companionWi != null && companionWi.isWiInProgressDischarge()) {
            LOGGER.warn((Object)("getTwinLoadDischargeCompanion() should not be called for in progress vessel discharge because the twin information may no longer be correct [wi=" + this.describeWi() + " companionWi=" + (companionWi != null ? companionWi.describeWi() : "null") + "]"));
        }
        return companionWi;
    }

    public WorkInstruction getTwinLoadDischargeNextWiInWq() {
        List workInstructions = HibernateApi.getInstance().findEntitiesByDomainQuery(this.getTwinLoadDischargeWiForSeqInWqDomainQuery(this.getWiSequence() + 1L));
        return workInstructions.isEmpty() ? null : (WorkInstruction)workInstructions.get(0);
    }

    public WorkInstruction getTwinLoadDischargePrevWiInWq() {
        List workInstructions = HibernateApi.getInstance().findEntitiesByDomainQuery(this.getTwinLoadDischargeWiForSeqInWqDomainQuery(this.getWiSequence() - 1L));
        return workInstructions.isEmpty() ? null : (WorkInstruction)workInstructions.get(0);
    }

    private IDomainQuery getTwinLoadDischargeWiForSeqInWqDomainQuery(long inSequence) {
        IDomainQuery query = QueryUtils.createDomainQuery((String)"WorkInstruction").addDqPredicate(PredicateFactory.eq((IMetafieldId)IMovesField.WI_WORK_QUEUE, (Object)this.getWiWorkQueue().getWqGkey())).addDqPredicate(PredicateFactory.eq((IMetafieldId)IMovesField.WI_SEQUENCE, (Object)inSequence)).addDqPredicate(PredicateFactory.in((IMetafieldId)IMovesField.WI_MOVE_KIND, (Object[])new WiMoveKindEnum[]{WiMoveKindEnum.VeslDisch, WiMoveKindEnum.VeslLoad})).addDqOrdering(Ordering.desc((IMetafieldId)IMovesField.WI_TIME_CREATED));
        return query;
    }

    private void updateWiUfvinCompReasonDeterminedByFromPosition(CarrierIncompatibilityReasonEnum inCompatibilityReasonEnum) {
        HashMap<String, AtomizedEnum> ufvsIncompReason = new HashMap<String, AtomizedEnum>();
        TbdUnit wiTbdUnit = this.getWiTbdUnit();
        boolean isWiForTbdUnit = wiTbdUnit != null;
        long wiEntityGkey = isWiForTbdUnit ? wiTbdUnit.getTbduGkey() : this.getWiUfv().getUfvGkey();
        ufvsIncompReason.put(UFV + this.getWiUfv().getUfvGkey(), inCompatibilityReasonEnum);
        WorkInstruction.updateIncompatibilityReason(ufvsIncompReason, isWiForTbdUnit, wiEntityGkey);
    }

    public boolean isWiUpToDate() {
        boolean oldModification;
        WorkQueue workQueue = this.getWiWorkQueue();
        if (workQueue == null || !workQueue.getActive().booleanValue()) {
            return false;
        }
        Date lastActivationChange = workQueue.getWqLastActivationChange();
        if (lastActivationChange == null) {
            return false;
        }
        Date creationTime = this.getWiTimeCreated();
        Date modifiedTime = this.getWiTimeModified();
        boolean oldCreation = creationTime != null ? creationTime.before(lastActivationChange) : true;
        boolean bl = oldModification = modifiedTime != null ? modifiedTime.before(lastActivationChange) : true;
        return !oldCreation || !oldModification;
    }

    public List<Serializable> getAssociatedWiUfvGkeys() {
        ArrayList<Serializable> ufvGkeys = new ArrayList<Serializable>();
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"WorkInstruction").addDqField(UnitField.WI_UFV_GKEY).addDqPredicate(PredicateFactory.eq((IMetafieldId)IMovesField.WI_WORK_QUEUE, (Object)this.getWiWorkQueue().getWqGkey()));
        IQueryResult qr = HibernateApi.getInstance().findValuesByDomainQuery(dq);
        for (int i = 0; i < qr.getCurrentResultCount(); ++i) {
            Serializable unitGKey = (Serializable)qr.getValue(i, UnitField.WI_UFV_GKEY);
            ufvGkeys.add(unitGKey);
        }
        return ufvGkeys;
    }

    static {
        DETACHED_WI_PKEY = 1L;
        BIZ_FIELD_LIST = new BizFieldList();
        BIZ_FIELD_LIST.add(UnitField.WI_LINE_OPERATOR, BizRoleEnum.LINEOP);
        BIZ_FIELD_LIST.add(UnitField.WI_UFV_ACTUAL_INBOUND_CV_OPERATOR, BizRoleEnum.LINEOP);
        BIZ_FIELD_LIST.add(UnitField.WI_UFV_ACTUAL_INBOUND_CV_OPERATOR, BizRoleEnum.RAILROAD);
        BIZ_FIELD_LIST.add(UnitField.WI_UFV_ACTUAL_INBOUND_CV_OPERATOR, BizRoleEnum.HAULIER);
        BIZ_FIELD_LIST.add(UnitField.WI_UFV_INTENDED_OUTBOUND_CV_OPERATOR, BizRoleEnum.LINEOP);
        BIZ_FIELD_LIST.add(UnitField.WI_UFV_INTENDED_OUTBOUND_CV_OPERATOR, BizRoleEnum.RAILROAD);
        BIZ_FIELD_LIST.add(UnitField.WI_UFV_INTENDED_OUTBOUND_CV_OPERATOR, BizRoleEnum.HAULIER);
        BIZ_FIELD_LIST.add(UnitField.WI_UFV_RTG_TRUCKING_COMPANY, BizRoleEnum.HAULIER);
        COMPLETED_WI_MOVESTAGES_SET = new HashSet<WiMoveStageEnum>(Arrays.asList(new WiMoveStageEnum[]{WiMoveStageEnum.CARRY_COMPLETE, WiMoveStageEnum.PUT_COMPLETE, WiMoveStageEnum.COMPLETE}));
    }

    static class MoveNumberParams {
        private Double _moveNumber;
        private WorkInstruction _contextWi;
        private WiChainInsertionPoint _insertionPoint;

        MoveNumberParams(@NotNull Double inMoveNumber) {
            this._moveNumber = inMoveNumber;
        }

        MoveNumberParams(@NotNull WiChainInsertionPoint inInsertionPoint, @NotNull WorkInstruction inContextWi) {
            this._insertionPoint = inInsertionPoint;
            this._contextWi = inContextWi;
        }

        public Double getMoveNumber() {
            if (this._moveNumber != null) {
                return this._moveNumber;
            }
            if (this._insertionPoint == null) {
                LOGGER.warn((Object)"Attempt to get move number through null insertion point, defaulting to default move num");
                return WiChainInsertionPoint.getDefaultMoveNumber();
            }
            return this._insertionPoint.getMoveNumber(this._contextWi);
        }

        @Nullable
        public WorkInstruction getContextWi() {
            return this._contextWi;
        }

        public Double getMoveNumber(@NotNull WorkInstruction inContextWi) {
            return this._insertionPoint.getMoveNumber(inContextWi);
        }
    }

    public static enum WiChainInsertionPoint {
        PREV{

            @Override
            public Double getMoveNumber(@Nullable WorkInstruction inContextWi) {
                if (inContextWi == null) {
                    LOGGER.warn((Object)"Expected param for context WI was null for PREV insertion, returning default move num");
                    return DEFAULT_MOVE_NUMBER;
                }
                return inContextWi.calculateNewWiMoveNumberPrev();
            }
        }
        ,
        NEXT{

            @Override
            public Double getMoveNumber(@Nullable WorkInstruction inContextWi) {
                if (inContextWi == null) {
                    LOGGER.warn((Object)"Expected param for context WI was null for NEXT insertion, returning default move num");
                    return DEFAULT_MOVE_NUMBER;
                }
                return inContextWi.calculateNewWiMoveNumberNext();
            }
        }
        ,
        NEW{

            @Override
            public Double getMoveNumber(@Nullable WorkInstruction inContextWi) {
                return DEFAULT_MOVE_NUMBER;
            }
        };

        static final Double DEFAULT_MOVE_NUMBER;

        @NotNull
        public abstract Double getMoveNumber(@Nullable WorkInstruction var1);

        public static Double getDefaultMoveNumber() {
            return DEFAULT_MOVE_NUMBER;
        }

        static {
            DEFAULT_MOVE_NUMBER = 1.0;
        }
    }

}
