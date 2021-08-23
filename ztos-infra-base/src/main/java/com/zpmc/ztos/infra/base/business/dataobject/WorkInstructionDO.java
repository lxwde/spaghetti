package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.business.enums.argo.LocTypeEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.TransferLocationEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.WiMoveKindEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.WiMoveStageEnum;
import com.zpmc.ztos.infra.base.business.enums.inventory.*;
import com.zpmc.ztos.infra.base.business.equipments.Che;
import com.zpmc.ztos.infra.base.business.inventory.TbdUnit;
import com.zpmc.ztos.infra.base.business.inventory.UnitYardVisit;
import com.zpmc.ztos.infra.base.business.model.LocPosition;
import com.zpmc.ztos.infra.base.business.plans.WorkAssignment;
import com.zpmc.ztos.infra.base.business.plans.WorkInstruction;
import com.zpmc.ztos.infra.base.business.plans.WorkQueue;
import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;
import com.zpmc.ztos.infra.base.common.scopes.Facility;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public abstract class WorkInstructionDO extends DatabaseEntity implements Serializable {

    private Long wiGkey;
    private Long wiPkey;
    private Long wiWqPkey;
    private Long wiArPkey;
    private Long wiArGkey;
    private Long wiYardShiftTargetWiPkey;
    private Long wiCheIndex;
    private Long wiItvId;
    private Long wiCheWorkAssignmentPkey;
    private Long wiItvWorkAssignmentPkey;
    private Long wiIntendedCheIndex;
    private Double wiMoveNumber;
    private Long wiSequence;
    private Long wiCraneLane;
    private Long wiSetAside;
    private DoorDirectionEnum wiDoorDirection;
    private DeckingRestrictionEnum wiDeckingRestrict;
    private TwinWithEnum wiTwinWith;
    private Boolean wiTwinIntendedFetch;
    private Boolean wiTwinIntendedCarry;
    private Boolean wiTwinIntendedPut;
    private WiEcStateEnum wiEcStateFetch;
    private Boolean wiEcStateDispatchRequest;
    private Boolean wiEcStateItvDispatchRequest;
    private Boolean wiEcStateDispatch;
    private Boolean wiEcStateItvDispatch;
    private WiSuspendStateEnum wiSuspendState;
    private Boolean wiIsLocked;
    private Boolean wiIsConfirmed;
    private Boolean wiIsDefiniteMove;
    private Boolean wiIsExecutable;
    private Boolean wiIsASCAbortedOnceAlready;
    private Boolean wiIsIncrementCraneLane;
    private Boolean wiIsBeingRehandled;
    private Boolean wiIsBeingDeleted;
    private Boolean wiIsShouldBeSent;
    private Boolean wiIsSequencedByPWP;
    private Boolean wiIsHostRequestPriority;
    private Boolean wiIsSkipHostUpdate;
    private WiMoveStageEnum wiMoveStage;
    private WiMoveStageEnum wiConfirmedMoveStage;
    private EcHoldEnum wiEcHold;
    private String wiEqMovesKey;
    private String wiEqUsesKey;
    private WiMoveKindEnum wiMoveKind;
    private LocTypeEnum wiCarrierLocType;
    private String wiCarrierLocId;
    private Long mvhsFetchCheIndex;
    private String mvhsFetchCheId;
    private Long mvhsCarryCheIndex;
    private String mvhsCarryCheId;
    private Long mvhsPutCheIndex;
    private String mvhsPutCheId;
    private Long mvhsDistToStart;
    private Long mvhsDistOfCarry;
    private Date mvhsTimeCarryComplete;
    private Date mvhsTimeDispatch;
    private Date mvhsTimeFetch;
    private Date mvhsTimePut;
    private Date mvhsTimeCarryCheFetchReady;
    private Date mvhsTimeCarryChePutReady;
    private Date mvhsTimeCarryCheDispatch;
    private Date mvhsTimeDischarge;
    private Long mvhsRehandleCount;
    private Long mvhsPowPkey;
    private Long mvhsPoolPkey;
    private Boolean mvhsTwinFetch;
    private Boolean mvhsTwinCarry;
    private Boolean mvhsTwinPut;
    private Boolean mvhsTandemFetch;
    private Boolean mvhsTandemPut;
    private Date mvhsTZArrivalTime;
    private Long mvhsFetchCheDistance;
    private Long mvhsCarryCheDistance;
    private Long mvhsPutCheDistance;
    private Date mvhsTimeFetchCheDispatch;
    private Date mvhsTimePutCheDispatch;
    private Date wiEstimatedMoveTime;
    private Date wiTimeOrigEstStart;
    private String wiRestowAccount;
    private String wiServiceOrder;
    private String wiRestowReason;
    private Long wiRoadTruckCounter;
    private Long wiCheDispatchOrder;
    private Long wiItvDispatchOrder;
    private Long wiTruckVisitRef;
    private Long wiPoolLevel;
    private Long wiMsgRef;
    private Long wiPlannerCreate;
    private Long wiPlannerModify;
    private Date wiTimeCreated;
    private Date wiTimeModified;
    private SparcsToolEnum wiCreateTool;
    private Boolean wiIsImminentMove;
    private Boolean wiIsAlmostImminent;
    private Boolean wiIsAlmostCurrent;
    private Boolean wiIsBeingCarried;
    private Boolean wiIsCarryByStraddle;
    private Boolean wiIsDispatchHung;
    private Boolean wiIsIgnoreGateHold;
    private Long wiTvGkey;
    private Long wiTranGkey;
    private String wiOriginalFromQual;
    private String wiAssignedChassId;
    private Boolean wiIsSwappableDelivery;
    private Boolean wiIsN4SpecifiedQueue;
    private Integer wiGateTranSeq;
    private Integer wiCraneLaneTier;
    private String wiYardMoveFilter;
    private Long wiActualSequence;
    private Long wiActualVesselTwinWiPkey;
    private Boolean wiIsStrictLoad;
    private TransferLocationEnum wiPreferredTransferLocation;
    private Boolean wiIsTandemWithNext;
    private Boolean wiIsTandemWithPrevious;
    private Long wiXpsObjectVersion;
    private Long wiEmtTimeHorizonMin;
    private Boolean wiHasActiveAlarm;
    private String wiPairWith;
    private Facility wiFacility;
    private UnitYardVisit wiUyv;
    private TbdUnit wiTbdUnit;
    private WorkQueue wiWorkQueue;
    private WorkInstruction wiYardShiftTargetWi;
    private Che wiChe;
    private Che wiItv;
    private WorkAssignment wiCheWorkAssignment;
    private WorkAssignment wiItvWorkAssignment;
    private Che wiIntendedChe;
    private Che wiNominatedChe;
    private WorkInstruction wiActualVesselTwinWi;
    private List wiAlarms;
    private LocPosition wiPosition;

    public Serializable getPrimaryKey() {
        return this.getWiGkey();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        Long id = this.getWiGkey();
        if (id == null) {
            return false;
        }
        if (!(other instanceof WorkInstructionDO)) {
            return false;
        }
        WorkInstructionDO that = (WorkInstructionDO)other;
        return ((Object)id).equals(that.getWiGkey());
    }

    public int hashCode() {
        Long id = this.getWiGkey();
        return id == null ? System.identityHashCode(this) : ((Object)id).hashCode();
    }

    public Long getWiGkey() {
        return this.wiGkey;
    }

    protected void setWiGkey(Long wiGkey) {
        this.wiGkey = wiGkey;
    }

    public Long getWiPkey() {
        return this.wiPkey;
    }

    protected void setWiPkey(Long wiPkey) {
        this.wiPkey = wiPkey;
    }

    public Long getWiWqPkey() {
        return this.wiWqPkey;
    }

    protected void setWiWqPkey(Long wiWqPkey) {
        this.wiWqPkey = wiWqPkey;
    }

    public Long getWiArPkey() {
        return this.wiArPkey;
    }

    protected void setWiArPkey(Long wiArPkey) {
        this.wiArPkey = wiArPkey;
    }

    public Long getWiArGkey() {
        return this.wiArGkey;
    }

    protected void setWiArGkey(Long wiArGkey) {
        this.wiArGkey = wiArGkey;
    }

    public Long getWiYardShiftTargetWiPkey() {
        return this.wiYardShiftTargetWiPkey;
    }

    protected void setWiYardShiftTargetWiPkey(Long wiYardShiftTargetWiPkey) {
        this.wiYardShiftTargetWiPkey = wiYardShiftTargetWiPkey;
    }

    public Long getWiCheIndex() {
        return this.wiCheIndex;
    }

    protected void setWiCheIndex(Long wiCheIndex) {
        this.wiCheIndex = wiCheIndex;
    }

    public Long getWiItvId() {
        return this.wiItvId;
    }

    protected void setWiItvId(Long wiItvId) {
        this.wiItvId = wiItvId;
    }

    public Long getWiCheWorkAssignmentPkey() {
        return this.wiCheWorkAssignmentPkey;
    }

    protected void setWiCheWorkAssignmentPkey(Long wiCheWorkAssignmentPkey) {
        this.wiCheWorkAssignmentPkey = wiCheWorkAssignmentPkey;
    }

    public Long getWiItvWorkAssignmentPkey() {
        return this.wiItvWorkAssignmentPkey;
    }

    protected void setWiItvWorkAssignmentPkey(Long wiItvWorkAssignmentPkey) {
        this.wiItvWorkAssignmentPkey = wiItvWorkAssignmentPkey;
    }

    public Long getWiIntendedCheIndex() {
        return this.wiIntendedCheIndex;
    }

    protected void setWiIntendedCheIndex(Long wiIntendedCheIndex) {
        this.wiIntendedCheIndex = wiIntendedCheIndex;
    }

    public Double getWiMoveNumber() {
        return this.wiMoveNumber;
    }

    protected void setWiMoveNumber(Double wiMoveNumber) {
        this.wiMoveNumber = wiMoveNumber;
    }

    public Long getWiSequence() {
        return this.wiSequence;
    }

    protected void setWiSequence(Long wiSequence) {
        this.wiSequence = wiSequence;
    }

    public Long getWiCraneLane() {
        return this.wiCraneLane;
    }

    protected void setWiCraneLane(Long wiCraneLane) {
        this.wiCraneLane = wiCraneLane;
    }

    public Long getWiSetAside() {
        return this.wiSetAside;
    }

    protected void setWiSetAside(Long wiSetAside) {
        this.wiSetAside = wiSetAside;
    }

    public DoorDirectionEnum getWiDoorDirection() {
        return this.wiDoorDirection;
    }

    protected void setWiDoorDirection(DoorDirectionEnum wiDoorDirection) {
        this.wiDoorDirection = wiDoorDirection;
    }

    public DeckingRestrictionEnum getWiDeckingRestrict() {
        return this.wiDeckingRestrict;
    }

    protected void setWiDeckingRestrict(DeckingRestrictionEnum wiDeckingRestrict) {
        this.wiDeckingRestrict = wiDeckingRestrict;
    }

    public TwinWithEnum getWiTwinWith() {
        return this.wiTwinWith;
    }

    protected void setWiTwinWith(TwinWithEnum wiTwinWith) {
        this.wiTwinWith = wiTwinWith;
    }

    public Boolean getWiTwinIntendedFetch() {
        return this.wiTwinIntendedFetch;
    }

    protected void setWiTwinIntendedFetch(Boolean wiTwinIntendedFetch) {
        this.wiTwinIntendedFetch = wiTwinIntendedFetch;
    }

    public Boolean getWiTwinIntendedCarry() {
        return this.wiTwinIntendedCarry;
    }

    protected void setWiTwinIntendedCarry(Boolean wiTwinIntendedCarry) {
        this.wiTwinIntendedCarry = wiTwinIntendedCarry;
    }

    public Boolean getWiTwinIntendedPut() {
        return this.wiTwinIntendedPut;
    }

    protected void setWiTwinIntendedPut(Boolean wiTwinIntendedPut) {
        this.wiTwinIntendedPut = wiTwinIntendedPut;
    }

    public WiEcStateEnum getWiEcStateFetch() {
        return this.wiEcStateFetch;
    }

    protected void setWiEcStateFetch(WiEcStateEnum wiEcStateFetch) {
        this.wiEcStateFetch = wiEcStateFetch;
    }

    public Boolean getWiEcStateDispatchRequest() {
        return this.wiEcStateDispatchRequest;
    }

    protected void setWiEcStateDispatchRequest(Boolean wiEcStateDispatchRequest) {
        this.wiEcStateDispatchRequest = wiEcStateDispatchRequest;
    }

    public Boolean getWiEcStateItvDispatchRequest() {
        return this.wiEcStateItvDispatchRequest;
    }

    protected void setWiEcStateItvDispatchRequest(Boolean wiEcStateItvDispatchRequest) {
        this.wiEcStateItvDispatchRequest = wiEcStateItvDispatchRequest;
    }

    public Boolean getWiEcStateDispatch() {
        return this.wiEcStateDispatch;
    }

    protected void setWiEcStateDispatch(Boolean wiEcStateDispatch) {
        this.wiEcStateDispatch = wiEcStateDispatch;
    }

    public Boolean getWiEcStateItvDispatch() {
        return this.wiEcStateItvDispatch;
    }

    protected void setWiEcStateItvDispatch(Boolean wiEcStateItvDispatch) {
        this.wiEcStateItvDispatch = wiEcStateItvDispatch;
    }

    public WiSuspendStateEnum getWiSuspendState() {
        return this.wiSuspendState;
    }

    protected void setWiSuspendState(WiSuspendStateEnum wiSuspendState) {
        this.wiSuspendState = wiSuspendState;
    }

    public Boolean getWiIsLocked() {
        return this.wiIsLocked;
    }

    protected void setWiIsLocked(Boolean wiIsLocked) {
        this.wiIsLocked = wiIsLocked;
    }

    public Boolean getWiIsConfirmed() {
        return this.wiIsConfirmed;
    }

    protected void setWiIsConfirmed(Boolean wiIsConfirmed) {
        this.wiIsConfirmed = wiIsConfirmed;
    }

    public Boolean getWiIsDefiniteMove() {
        return this.wiIsDefiniteMove;
    }

    protected void setWiIsDefiniteMove(Boolean wiIsDefiniteMove) {
        this.wiIsDefiniteMove = wiIsDefiniteMove;
    }

    public Boolean getWiIsExecutable() {
        return this.wiIsExecutable;
    }

    protected void setWiIsExecutable(Boolean wiIsExecutable) {
        this.wiIsExecutable = wiIsExecutable;
    }

    public Boolean getWiIsASCAbortedOnceAlready() {
        return this.wiIsASCAbortedOnceAlready;
    }

    protected void setWiIsASCAbortedOnceAlready(Boolean wiIsASCAbortedOnceAlready) {
        this.wiIsASCAbortedOnceAlready = wiIsASCAbortedOnceAlready;
    }

    public Boolean getWiIsIncrementCraneLane() {
        return this.wiIsIncrementCraneLane;
    }

    protected void setWiIsIncrementCraneLane(Boolean wiIsIncrementCraneLane) {
        this.wiIsIncrementCraneLane = wiIsIncrementCraneLane;
    }

    public Boolean getWiIsBeingRehandled() {
        return this.wiIsBeingRehandled;
    }

    protected void setWiIsBeingRehandled(Boolean wiIsBeingRehandled) {
        this.wiIsBeingRehandled = wiIsBeingRehandled;
    }

    public Boolean getWiIsBeingDeleted() {
        return this.wiIsBeingDeleted;
    }

    protected void setWiIsBeingDeleted(Boolean wiIsBeingDeleted) {
        this.wiIsBeingDeleted = wiIsBeingDeleted;
    }

    public Boolean getWiIsShouldBeSent() {
        return this.wiIsShouldBeSent;
    }

    protected void setWiIsShouldBeSent(Boolean wiIsShouldBeSent) {
        this.wiIsShouldBeSent = wiIsShouldBeSent;
    }

    public Boolean getWiIsSequencedByPWP() {
        return this.wiIsSequencedByPWP;
    }

    protected void setWiIsSequencedByPWP(Boolean wiIsSequencedByPWP) {
        this.wiIsSequencedByPWP = wiIsSequencedByPWP;
    }

    public Boolean getWiIsHostRequestPriority() {
        return this.wiIsHostRequestPriority;
    }

    protected void setWiIsHostRequestPriority(Boolean wiIsHostRequestPriority) {
        this.wiIsHostRequestPriority = wiIsHostRequestPriority;
    }

    public Boolean getWiIsSkipHostUpdate() {
        return this.wiIsSkipHostUpdate;
    }

    protected void setWiIsSkipHostUpdate(Boolean wiIsSkipHostUpdate) {
        this.wiIsSkipHostUpdate = wiIsSkipHostUpdate;
    }

    public WiMoveStageEnum getWiMoveStage() {
        return this.wiMoveStage;
    }

    protected void setWiMoveStage(WiMoveStageEnum wiMoveStage) {
        this.wiMoveStage = wiMoveStage;
    }

    public WiMoveStageEnum getWiConfirmedMoveStage() {
        return this.wiConfirmedMoveStage;
    }

    protected void setWiConfirmedMoveStage(WiMoveStageEnum wiConfirmedMoveStage) {
        this.wiConfirmedMoveStage = wiConfirmedMoveStage;
    }

    public EcHoldEnum getWiEcHold() {
        return this.wiEcHold;
    }

    protected void setWiEcHold(EcHoldEnum wiEcHold) {
        this.wiEcHold = wiEcHold;
    }

    public String getWiEqMovesKey() {
        return this.wiEqMovesKey;
    }

    protected void setWiEqMovesKey(String wiEqMovesKey) {
        this.wiEqMovesKey = wiEqMovesKey;
    }

    public String getWiEqUsesKey() {
        return this.wiEqUsesKey;
    }

    protected void setWiEqUsesKey(String wiEqUsesKey) {
        this.wiEqUsesKey = wiEqUsesKey;
    }

    public WiMoveKindEnum getWiMoveKind() {
        return this.wiMoveKind;
    }

    protected void setWiMoveKind(WiMoveKindEnum wiMoveKind) {
        this.wiMoveKind = wiMoveKind;
    }

    public LocTypeEnum getWiCarrierLocType() {
        return this.wiCarrierLocType;
    }

    protected void setWiCarrierLocType(LocTypeEnum wiCarrierLocType) {
        this.wiCarrierLocType = wiCarrierLocType;
    }

    public String getWiCarrierLocId() {
        return this.wiCarrierLocId;
    }

    protected void setWiCarrierLocId(String wiCarrierLocId) {
        this.wiCarrierLocId = wiCarrierLocId;
    }

    public Long getMvhsFetchCheIndex() {
        return this.mvhsFetchCheIndex;
    }

    protected void setMvhsFetchCheIndex(Long mvhsFetchCheIndex) {
        this.mvhsFetchCheIndex = mvhsFetchCheIndex;
    }

    public String getMvhsFetchCheId() {
        return this.mvhsFetchCheId;
    }

    protected void setMvhsFetchCheId(String mvhsFetchCheId) {
        this.mvhsFetchCheId = mvhsFetchCheId;
    }

    public Long getMvhsCarryCheIndex() {
        return this.mvhsCarryCheIndex;
    }

    protected void setMvhsCarryCheIndex(Long mvhsCarryCheIndex) {
        this.mvhsCarryCheIndex = mvhsCarryCheIndex;
    }

    public String getMvhsCarryCheId() {
        return this.mvhsCarryCheId;
    }

    protected void setMvhsCarryCheId(String mvhsCarryCheId) {
        this.mvhsCarryCheId = mvhsCarryCheId;
    }

    public Long getMvhsPutCheIndex() {
        return this.mvhsPutCheIndex;
    }

    protected void setMvhsPutCheIndex(Long mvhsPutCheIndex) {
        this.mvhsPutCheIndex = mvhsPutCheIndex;
    }

    public String getMvhsPutCheId() {
        return this.mvhsPutCheId;
    }

    protected void setMvhsPutCheId(String mvhsPutCheId) {
        this.mvhsPutCheId = mvhsPutCheId;
    }

    public Long getMvhsDistToStart() {
        return this.mvhsDistToStart;
    }

    protected void setMvhsDistToStart(Long mvhsDistToStart) {
        this.mvhsDistToStart = mvhsDistToStart;
    }

    public Long getMvhsDistOfCarry() {
        return this.mvhsDistOfCarry;
    }

    protected void setMvhsDistOfCarry(Long mvhsDistOfCarry) {
        this.mvhsDistOfCarry = mvhsDistOfCarry;
    }

    public Date getMvhsTimeCarryComplete() {
        return this.mvhsTimeCarryComplete;
    }

    protected void setMvhsTimeCarryComplete(Date mvhsTimeCarryComplete) {
        this.mvhsTimeCarryComplete = mvhsTimeCarryComplete;
    }

    public Date getMvhsTimeDispatch() {
        return this.mvhsTimeDispatch;
    }

    protected void setMvhsTimeDispatch(Date mvhsTimeDispatch) {
        this.mvhsTimeDispatch = mvhsTimeDispatch;
    }

    public Date getMvhsTimeFetch() {
        return this.mvhsTimeFetch;
    }

    protected void setMvhsTimeFetch(Date mvhsTimeFetch) {
        this.mvhsTimeFetch = mvhsTimeFetch;
    }

    public Date getMvhsTimePut() {
        return this.mvhsTimePut;
    }

    protected void setMvhsTimePut(Date mvhsTimePut) {
        this.mvhsTimePut = mvhsTimePut;
    }

    public Date getMvhsTimeCarryCheFetchReady() {
        return this.mvhsTimeCarryCheFetchReady;
    }

    protected void setMvhsTimeCarryCheFetchReady(Date mvhsTimeCarryCheFetchReady) {
        this.mvhsTimeCarryCheFetchReady = mvhsTimeCarryCheFetchReady;
    }

    public Date getMvhsTimeCarryChePutReady() {
        return this.mvhsTimeCarryChePutReady;
    }

    protected void setMvhsTimeCarryChePutReady(Date mvhsTimeCarryChePutReady) {
        this.mvhsTimeCarryChePutReady = mvhsTimeCarryChePutReady;
    }

    public Date getMvhsTimeCarryCheDispatch() {
        return this.mvhsTimeCarryCheDispatch;
    }

    protected void setMvhsTimeCarryCheDispatch(Date mvhsTimeCarryCheDispatch) {
        this.mvhsTimeCarryCheDispatch = mvhsTimeCarryCheDispatch;
    }

    public Date getMvhsTimeDischarge() {
        return this.mvhsTimeDischarge;
    }

    protected void setMvhsTimeDischarge(Date mvhsTimeDischarge) {
        this.mvhsTimeDischarge = mvhsTimeDischarge;
    }

    public Long getMvhsRehandleCount() {
        return this.mvhsRehandleCount;
    }

    protected void setMvhsRehandleCount(Long mvhsRehandleCount) {
        this.mvhsRehandleCount = mvhsRehandleCount;
    }

    public Long getMvhsPowPkey() {
        return this.mvhsPowPkey;
    }

    protected void setMvhsPowPkey(Long mvhsPowPkey) {
        this.mvhsPowPkey = mvhsPowPkey;
    }

    public Long getMvhsPoolPkey() {
        return this.mvhsPoolPkey;
    }

    protected void setMvhsPoolPkey(Long mvhsPoolPkey) {
        this.mvhsPoolPkey = mvhsPoolPkey;
    }

    public Boolean getMvhsTwinFetch() {
        return this.mvhsTwinFetch;
    }

    protected void setMvhsTwinFetch(Boolean mvhsTwinFetch) {
        this.mvhsTwinFetch = mvhsTwinFetch;
    }

    public Boolean getMvhsTwinCarry() {
        return this.mvhsTwinCarry;
    }

    protected void setMvhsTwinCarry(Boolean mvhsTwinCarry) {
        this.mvhsTwinCarry = mvhsTwinCarry;
    }

    public Boolean getMvhsTwinPut() {
        return this.mvhsTwinPut;
    }

    protected void setMvhsTwinPut(Boolean mvhsTwinPut) {
        this.mvhsTwinPut = mvhsTwinPut;
    }

    public Boolean getMvhsTandemFetch() {
        return this.mvhsTandemFetch;
    }

    protected void setMvhsTandemFetch(Boolean mvhsTandemFetch) {
        this.mvhsTandemFetch = mvhsTandemFetch;
    }

    public Boolean getMvhsTandemPut() {
        return this.mvhsTandemPut;
    }

    protected void setMvhsTandemPut(Boolean mvhsTandemPut) {
        this.mvhsTandemPut = mvhsTandemPut;
    }

    public Date getMvhsTZArrivalTime() {
        return this.mvhsTZArrivalTime;
    }

    protected void setMvhsTZArrivalTime(Date mvhsTZArrivalTime) {
        this.mvhsTZArrivalTime = mvhsTZArrivalTime;
    }

    public Long getMvhsFetchCheDistance() {
        return this.mvhsFetchCheDistance;
    }

    protected void setMvhsFetchCheDistance(Long mvhsFetchCheDistance) {
        this.mvhsFetchCheDistance = mvhsFetchCheDistance;
    }

    public Long getMvhsCarryCheDistance() {
        return this.mvhsCarryCheDistance;
    }

    protected void setMvhsCarryCheDistance(Long mvhsCarryCheDistance) {
        this.mvhsCarryCheDistance = mvhsCarryCheDistance;
    }

    public Long getMvhsPutCheDistance() {
        return this.mvhsPutCheDistance;
    }

    protected void setMvhsPutCheDistance(Long mvhsPutCheDistance) {
        this.mvhsPutCheDistance = mvhsPutCheDistance;
    }

    public Date getMvhsTimeFetchCheDispatch() {
        return this.mvhsTimeFetchCheDispatch;
    }

    protected void setMvhsTimeFetchCheDispatch(Date mvhsTimeFetchCheDispatch) {
        this.mvhsTimeFetchCheDispatch = mvhsTimeFetchCheDispatch;
    }

    public Date getMvhsTimePutCheDispatch() {
        return this.mvhsTimePutCheDispatch;
    }

    protected void setMvhsTimePutCheDispatch(Date mvhsTimePutCheDispatch) {
        this.mvhsTimePutCheDispatch = mvhsTimePutCheDispatch;
    }

    public Date getWiEstimatedMoveTime() {
        return this.wiEstimatedMoveTime;
    }

    protected void setWiEstimatedMoveTime(Date wiEstimatedMoveTime) {
        this.wiEstimatedMoveTime = wiEstimatedMoveTime;
    }

    public Date getWiTimeOrigEstStart() {
        return this.wiTimeOrigEstStart;
    }

    protected void setWiTimeOrigEstStart(Date wiTimeOrigEstStart) {
        this.wiTimeOrigEstStart = wiTimeOrigEstStart;
    }

    public String getWiRestowAccount() {
        return this.wiRestowAccount;
    }

    protected void setWiRestowAccount(String wiRestowAccount) {
        this.wiRestowAccount = wiRestowAccount;
    }

    public String getWiServiceOrder() {
        return this.wiServiceOrder;
    }

    protected void setWiServiceOrder(String wiServiceOrder) {
        this.wiServiceOrder = wiServiceOrder;
    }

    public String getWiRestowReason() {
        return this.wiRestowReason;
    }

    protected void setWiRestowReason(String wiRestowReason) {
        this.wiRestowReason = wiRestowReason;
    }

    public Long getWiRoadTruckCounter() {
        return this.wiRoadTruckCounter;
    }

    protected void setWiRoadTruckCounter(Long wiRoadTruckCounter) {
        this.wiRoadTruckCounter = wiRoadTruckCounter;
    }

    public Long getWiCheDispatchOrder() {
        return this.wiCheDispatchOrder;
    }

    protected void setWiCheDispatchOrder(Long wiCheDispatchOrder) {
        this.wiCheDispatchOrder = wiCheDispatchOrder;
    }

    public Long getWiItvDispatchOrder() {
        return this.wiItvDispatchOrder;
    }

    protected void setWiItvDispatchOrder(Long wiItvDispatchOrder) {
        this.wiItvDispatchOrder = wiItvDispatchOrder;
    }

    public Long getWiTruckVisitRef() {
        return this.wiTruckVisitRef;
    }

    protected void setWiTruckVisitRef(Long wiTruckVisitRef) {
        this.wiTruckVisitRef = wiTruckVisitRef;
    }

    public Long getWiPoolLevel() {
        return this.wiPoolLevel;
    }

    protected void setWiPoolLevel(Long wiPoolLevel) {
        this.wiPoolLevel = wiPoolLevel;
    }

    public Long getWiMsgRef() {
        return this.wiMsgRef;
    }

    protected void setWiMsgRef(Long wiMsgRef) {
        this.wiMsgRef = wiMsgRef;
    }

    public Long getWiPlannerCreate() {
        return this.wiPlannerCreate;
    }

    protected void setWiPlannerCreate(Long wiPlannerCreate) {
        this.wiPlannerCreate = wiPlannerCreate;
    }

    public Long getWiPlannerModify() {
        return this.wiPlannerModify;
    }

    protected void setWiPlannerModify(Long wiPlannerModify) {
        this.wiPlannerModify = wiPlannerModify;
    }

    public Date getWiTimeCreated() {
        return this.wiTimeCreated;
    }

    protected void setWiTimeCreated(Date wiTimeCreated) {
        this.wiTimeCreated = wiTimeCreated;
    }

    public Date getWiTimeModified() {
        return this.wiTimeModified;
    }

    protected void setWiTimeModified(Date wiTimeModified) {
        this.wiTimeModified = wiTimeModified;
    }

    public SparcsToolEnum getWiCreateTool() {
        return this.wiCreateTool;
    }

    protected void setWiCreateTool(SparcsToolEnum wiCreateTool) {
        this.wiCreateTool = wiCreateTool;
    }

    public Boolean getWiIsImminentMove() {
        return this.wiIsImminentMove;
    }

    protected void setWiIsImminentMove(Boolean wiIsImminentMove) {
        this.wiIsImminentMove = wiIsImminentMove;
    }

    public Boolean getWiIsAlmostImminent() {
        return this.wiIsAlmostImminent;
    }

    protected void setWiIsAlmostImminent(Boolean wiIsAlmostImminent) {
        this.wiIsAlmostImminent = wiIsAlmostImminent;
    }

    public Boolean getWiIsAlmostCurrent() {
        return this.wiIsAlmostCurrent;
    }

    protected void setWiIsAlmostCurrent(Boolean wiIsAlmostCurrent) {
        this.wiIsAlmostCurrent = wiIsAlmostCurrent;
    }

    public Boolean getWiIsBeingCarried() {
        return this.wiIsBeingCarried;
    }

    protected void setWiIsBeingCarried(Boolean wiIsBeingCarried) {
        this.wiIsBeingCarried = wiIsBeingCarried;
    }

    public Boolean getWiIsCarryByStraddle() {
        return this.wiIsCarryByStraddle;
    }

    protected void setWiIsCarryByStraddle(Boolean wiIsCarryByStraddle) {
        this.wiIsCarryByStraddle = wiIsCarryByStraddle;
    }

    public Boolean getWiIsDispatchHung() {
        return this.wiIsDispatchHung;
    }

    protected void setWiIsDispatchHung(Boolean wiIsDispatchHung) {
        this.wiIsDispatchHung = wiIsDispatchHung;
    }

    public Boolean getWiIsIgnoreGateHold() {
        return this.wiIsIgnoreGateHold;
    }

    protected void setWiIsIgnoreGateHold(Boolean wiIsIgnoreGateHold) {
        this.wiIsIgnoreGateHold = wiIsIgnoreGateHold;
    }

    public Long getWiTvGkey() {
        return this.wiTvGkey;
    }

    protected void setWiTvGkey(Long wiTvGkey) {
        this.wiTvGkey = wiTvGkey;
    }

    public Long getWiTranGkey() {
        return this.wiTranGkey;
    }

    protected void setWiTranGkey(Long wiTranGkey) {
        this.wiTranGkey = wiTranGkey;
    }

    public String getWiOriginalFromQual() {
        return this.wiOriginalFromQual;
    }

    protected void setWiOriginalFromQual(String wiOriginalFromQual) {
        this.wiOriginalFromQual = wiOriginalFromQual;
    }

    public String getWiAssignedChassId() {
        return this.wiAssignedChassId;
    }

    protected void setWiAssignedChassId(String wiAssignedChassId) {
        this.wiAssignedChassId = wiAssignedChassId;
    }

    public Boolean getWiIsSwappableDelivery() {
        return this.wiIsSwappableDelivery;
    }

    protected void setWiIsSwappableDelivery(Boolean wiIsSwappableDelivery) {
        this.wiIsSwappableDelivery = wiIsSwappableDelivery;
    }

    public Boolean getWiIsN4SpecifiedQueue() {
        return this.wiIsN4SpecifiedQueue;
    }

    protected void setWiIsN4SpecifiedQueue(Boolean wiIsN4SpecifiedQueue) {
        this.wiIsN4SpecifiedQueue = wiIsN4SpecifiedQueue;
    }

    public Integer getWiGateTranSeq() {
        return this.wiGateTranSeq;
    }

    protected void setWiGateTranSeq(Integer wiGateTranSeq) {
        this.wiGateTranSeq = wiGateTranSeq;
    }

    public Integer getWiCraneLaneTier() {
        return this.wiCraneLaneTier;
    }

    protected void setWiCraneLaneTier(Integer wiCraneLaneTier) {
        this.wiCraneLaneTier = wiCraneLaneTier;
    }

    public String getWiYardMoveFilter() {
        return this.wiYardMoveFilter;
    }

    protected void setWiYardMoveFilter(String wiYardMoveFilter) {
        this.wiYardMoveFilter = wiYardMoveFilter;
    }

    public Long getWiActualSequence() {
        return this.wiActualSequence;
    }

    protected void setWiActualSequence(Long wiActualSequence) {
        this.wiActualSequence = wiActualSequence;
    }

    public Long getWiActualVesselTwinWiPkey() {
        return this.wiActualVesselTwinWiPkey;
    }

    protected void setWiActualVesselTwinWiPkey(Long wiActualVesselTwinWiPkey) {
        this.wiActualVesselTwinWiPkey = wiActualVesselTwinWiPkey;
    }

    public Boolean getWiIsStrictLoad() {
        return this.wiIsStrictLoad;
    }

    protected void setWiIsStrictLoad(Boolean wiIsStrictLoad) {
        this.wiIsStrictLoad = wiIsStrictLoad;
    }

    public TransferLocationEnum getWiPreferredTransferLocation() {
        return this.wiPreferredTransferLocation;
    }

    protected void setWiPreferredTransferLocation(TransferLocationEnum wiPreferredTransferLocation) {
        this.wiPreferredTransferLocation = wiPreferredTransferLocation;
    }

    public Boolean getWiIsTandemWithNext() {
        return this.wiIsTandemWithNext;
    }

    protected void setWiIsTandemWithNext(Boolean wiIsTandemWithNext) {
        this.wiIsTandemWithNext = wiIsTandemWithNext;
    }

    public Boolean getWiIsTandemWithPrevious() {
        return this.wiIsTandemWithPrevious;
    }

    protected void setWiIsTandemWithPrevious(Boolean wiIsTandemWithPrevious) {
        this.wiIsTandemWithPrevious = wiIsTandemWithPrevious;
    }

    public Long getWiXpsObjectVersion() {
        return this.wiXpsObjectVersion;
    }

    protected void setWiXpsObjectVersion(Long wiXpsObjectVersion) {
        this.wiXpsObjectVersion = wiXpsObjectVersion;
    }

    public Long getWiEmtTimeHorizonMin() {
        return this.wiEmtTimeHorizonMin;
    }

    protected void setWiEmtTimeHorizonMin(Long wiEmtTimeHorizonMin) {
        this.wiEmtTimeHorizonMin = wiEmtTimeHorizonMin;
    }

    public Boolean getWiHasActiveAlarm() {
        return this.wiHasActiveAlarm;
    }

    protected void setWiHasActiveAlarm(Boolean wiHasActiveAlarm) {
        this.wiHasActiveAlarm = wiHasActiveAlarm;
    }

    public String getWiPairWith() {
        return this.wiPairWith;
    }

    protected void setWiPairWith(String wiPairWith) {
        this.wiPairWith = wiPairWith;
    }

    public Facility getWiFacility() {
        return this.wiFacility;
    }

    protected void setWiFacility(Facility wiFacility) {
        this.wiFacility = wiFacility;
    }

    public UnitYardVisit getWiUyv() {
        return this.wiUyv;
    }

    protected void setWiUyv(UnitYardVisit wiUyv) {
        this.wiUyv = wiUyv;
    }

    public TbdUnit getWiTbdUnit() {
        return this.wiTbdUnit;
    }

    protected void setWiTbdUnit(TbdUnit wiTbdUnit) {
        this.wiTbdUnit = wiTbdUnit;
    }

    public WorkQueue getWiWorkQueue() {
        return this.wiWorkQueue;
    }

    protected void setWiWorkQueue(WorkQueue wiWorkQueue) {
        this.wiWorkQueue = wiWorkQueue;
    }

    public WorkInstruction getWiYardShiftTargetWi() {
        return this.wiYardShiftTargetWi;
    }

    protected void setWiYardShiftTargetWi(WorkInstruction wiYardShiftTargetWi) {
        this.wiYardShiftTargetWi = wiYardShiftTargetWi;
    }

    public Che getWiChe() {
        return this.wiChe;
    }

    protected void setWiChe(Che wiChe) {
        this.wiChe = wiChe;
    }

    public Che getWiItv() {
        return this.wiItv;
    }

    protected void setWiItv(Che wiItv) {
        this.wiItv = wiItv;
    }

    public WorkAssignment getWiCheWorkAssignment() {
        return this.wiCheWorkAssignment;
    }

    protected void setWiCheWorkAssignment(WorkAssignment wiCheWorkAssignment) {
        this.wiCheWorkAssignment = wiCheWorkAssignment;
    }

    public WorkAssignment getWiItvWorkAssignment() {
        return this.wiItvWorkAssignment;
    }

    protected void setWiItvWorkAssignment(WorkAssignment wiItvWorkAssignment) {
        this.wiItvWorkAssignment = wiItvWorkAssignment;
    }

    public Che getWiIntendedChe() {
        return this.wiIntendedChe;
    }

    protected void setWiIntendedChe(Che wiIntendedChe) {
        this.wiIntendedChe = wiIntendedChe;
    }

    public Che getWiNominatedChe() {
        return this.wiNominatedChe;
    }

    protected void setWiNominatedChe(Che wiNominatedChe) {
        this.wiNominatedChe = wiNominatedChe;
    }

    public WorkInstruction getWiActualVesselTwinWi() {
        return this.wiActualVesselTwinWi;
    }

    protected void setWiActualVesselTwinWi(WorkInstruction wiActualVesselTwinWi) {
        this.wiActualVesselTwinWi = wiActualVesselTwinWi;
    }

    public List getWiAlarms() {
        return this.wiAlarms;
    }

    protected void setWiAlarms(List wiAlarms) {
        this.wiAlarms = wiAlarms;
    }

    public LocPosition getWiPosition() {
        return this.wiPosition;
    }

    protected void setWiPosition(LocPosition wiPosition) {
        this.wiPosition = wiPosition;
    }

}
