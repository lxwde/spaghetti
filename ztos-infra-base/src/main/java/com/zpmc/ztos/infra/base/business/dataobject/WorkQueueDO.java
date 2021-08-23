package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.business.enums.argo.LocTypeEnum;
import com.zpmc.ztos.infra.base.business.enums.inventory.WqTypeEnum;
import com.zpmc.ztos.infra.base.business.model.PointOfWork;
import com.zpmc.ztos.infra.base.business.plans.WorkQueue;
import com.zpmc.ztos.infra.base.business.plans.WorkShift;
import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;
import com.zpmc.ztos.infra.base.common.scopes.Yard;

import java.io.Serializable;
import java.util.Date;

public abstract class WorkQueueDO extends DatabaseEntity implements Serializable {
    private Long wqGkey;
    private Long wqPkey;
    private Long wqPowPkey;
    private Long wqCycleCompanionPkey;
    private Long wqFirstRelatedShiftPkey;
    private Double wqOrder;
    private WqTypeEnum wqType;
    private Long wqFission;
    private Boolean wqhasWisSentToTLS;
    private Boolean wqSaveCompletedMoves;
    private Boolean wqIsBlue;
    private Boolean wqPermanent;
    private Boolean wqServiceOrderQueue;
    private Boolean wqManualSequenceMode;
    private Boolean wqYardLoadbackQueue;
    private Boolean wqAllowCntr40;
    private Boolean wqAllowCntr20;
    private LocTypeEnum wqPosLocType;
    private String wqPosLocId;
    private String wqCode;
    private String wqDeck;
    private String wqRow;
    private String wqName;
    private Date wqDeleteReadinessTime;
    private Long wqDoubleCycleFromSequence;
    private Long wqDoubleCycleToSequence;
    private String wqNote;
    private Long wqComputed20sProjectionCount;
    private Long wqComputed40sProjectionCount;
    private Long wqVesselLcg;
    private Boolean wqUseWqProd;
    private Long wqProductivityStd;
    private Long wqProductivityDual;
    private Long wqProductivityTwin;
    private Long wqMaxTeuCap;
    private Long wqPercentCapToStartPriority;
    private Long wqPenaltyForExceedingPerCap;
    private Long wqTlsSortKey;
    private Long wqProductivityTandem;
    private Long wqProductivityQuad;
    private Long wqAbsBerthCallSlaveId;
    private Date wqLastActivationChange;
    private PointOfWork wqPow;
    private Yard wqYard;
    private WorkQueue wqCycleCompanion;
    private WorkShift wqFirstRelatedShift;

    public Serializable getPrimaryKey() {
        return this.getWqGkey();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        Long id = this.getWqGkey();
        if (id == null) {
            return false;
        }
        if (!(other instanceof WorkQueueDO)) {
            return false;
        }
        WorkQueueDO that = (WorkQueueDO)other;
        return ((Object)id).equals(that.getWqGkey());
    }

    public int hashCode() {
        Long id = this.getWqGkey();
        return id == null ? System.identityHashCode(this) : ((Object)id).hashCode();
    }

    public Long getWqGkey() {
        return this.wqGkey;
    }

    protected void setWqGkey(Long wqGkey) {
        this.wqGkey = wqGkey;
    }

    public Long getWqPkey() {
        return this.wqPkey;
    }

    protected void setWqPkey(Long wqPkey) {
        this.wqPkey = wqPkey;
    }

    public Long getWqPowPkey() {
        return this.wqPowPkey;
    }

    protected void setWqPowPkey(Long wqPowPkey) {
        this.wqPowPkey = wqPowPkey;
    }

    public Long getWqCycleCompanionPkey() {
        return this.wqCycleCompanionPkey;
    }

    protected void setWqCycleCompanionPkey(Long wqCycleCompanionPkey) {
        this.wqCycleCompanionPkey = wqCycleCompanionPkey;
    }

    public Long getWqFirstRelatedShiftPkey() {
        return this.wqFirstRelatedShiftPkey;
    }

    protected void setWqFirstRelatedShiftPkey(Long wqFirstRelatedShiftPkey) {
        this.wqFirstRelatedShiftPkey = wqFirstRelatedShiftPkey;
    }

    public Double getWqOrder() {
        return this.wqOrder;
    }

    protected void setWqOrder(Double wqOrder) {
        this.wqOrder = wqOrder;
    }

    public WqTypeEnum getWqType() {
        return this.wqType;
    }

    protected void setWqType(WqTypeEnum wqType) {
        this.wqType = wqType;
    }

    public Long getWqFission() {
        return this.wqFission;
    }

    protected void setWqFission(Long wqFission) {
        this.wqFission = wqFission;
    }

    public Boolean getWqhasWisSentToTLS() {
        return this.wqhasWisSentToTLS;
    }

    protected void setWqhasWisSentToTLS(Boolean wqhasWisSentToTLS) {
        this.wqhasWisSentToTLS = wqhasWisSentToTLS;
    }

    public Boolean getWqSaveCompletedMoves() {
        return this.wqSaveCompletedMoves;
    }

    protected void setWqSaveCompletedMoves(Boolean wqSaveCompletedMoves) {
        this.wqSaveCompletedMoves = wqSaveCompletedMoves;
    }

    public Boolean getWqIsBlue() {
        return this.wqIsBlue;
    }

    protected void setWqIsBlue(Boolean wqIsBlue) {
        this.wqIsBlue = wqIsBlue;
    }

    public Boolean getWqPermanent() {
        return this.wqPermanent;
    }

    protected void setWqPermanent(Boolean wqPermanent) {
        this.wqPermanent = wqPermanent;
    }

    public Boolean getWqServiceOrderQueue() {
        return this.wqServiceOrderQueue;
    }

    protected void setWqServiceOrderQueue(Boolean wqServiceOrderQueue) {
        this.wqServiceOrderQueue = wqServiceOrderQueue;
    }

    public Boolean getWqManualSequenceMode() {
        return this.wqManualSequenceMode;
    }

    protected void setWqManualSequenceMode(Boolean wqManualSequenceMode) {
        this.wqManualSequenceMode = wqManualSequenceMode;
    }

    public Boolean getWqYardLoadbackQueue() {
        return this.wqYardLoadbackQueue;
    }

    protected void setWqYardLoadbackQueue(Boolean wqYardLoadbackQueue) {
        this.wqYardLoadbackQueue = wqYardLoadbackQueue;
    }

    public Boolean getWqAllowCntr40() {
        return this.wqAllowCntr40;
    }

    protected void setWqAllowCntr40(Boolean wqAllowCntr40) {
        this.wqAllowCntr40 = wqAllowCntr40;
    }

    public Boolean getWqAllowCntr20() {
        return this.wqAllowCntr20;
    }

    protected void setWqAllowCntr20(Boolean wqAllowCntr20) {
        this.wqAllowCntr20 = wqAllowCntr20;
    }

    public LocTypeEnum getWqPosLocType() {
        return this.wqPosLocType;
    }

    protected void setWqPosLocType(LocTypeEnum wqPosLocType) {
        this.wqPosLocType = wqPosLocType;
    }

    public String getWqPosLocId() {
        return this.wqPosLocId;
    }

    protected void setWqPosLocId(String wqPosLocId) {
        this.wqPosLocId = wqPosLocId;
    }

    public String getWqCode() {
        return this.wqCode;
    }

    protected void setWqCode(String wqCode) {
        this.wqCode = wqCode;
    }

    public String getWqDeck() {
        return this.wqDeck;
    }

    protected void setWqDeck(String wqDeck) {
        this.wqDeck = wqDeck;
    }

    public String getWqRow() {
        return this.wqRow;
    }

    protected void setWqRow(String wqRow) {
        this.wqRow = wqRow;
    }

    public String getWqName() {
        return this.wqName;
    }

    protected void setWqName(String wqName) {
        this.wqName = wqName;
    }

    public Date getWqDeleteReadinessTime() {
        return this.wqDeleteReadinessTime;
    }

    protected void setWqDeleteReadinessTime(Date wqDeleteReadinessTime) {
        this.wqDeleteReadinessTime = wqDeleteReadinessTime;
    }

    public Long getWqDoubleCycleFromSequence() {
        return this.wqDoubleCycleFromSequence;
    }

    protected void setWqDoubleCycleFromSequence(Long wqDoubleCycleFromSequence) {
        this.wqDoubleCycleFromSequence = wqDoubleCycleFromSequence;
    }

    public Long getWqDoubleCycleToSequence() {
        return this.wqDoubleCycleToSequence;
    }

    protected void setWqDoubleCycleToSequence(Long wqDoubleCycleToSequence) {
        this.wqDoubleCycleToSequence = wqDoubleCycleToSequence;
    }

    public String getWqNote() {
        return this.wqNote;
    }

    protected void setWqNote(String wqNote) {
        this.wqNote = wqNote;
    }

    public Long getWqComputed20sProjectionCount() {
        return this.wqComputed20sProjectionCount;
    }

    protected void setWqComputed20sProjectionCount(Long wqComputed20sProjectionCount) {
        this.wqComputed20sProjectionCount = wqComputed20sProjectionCount;
    }

    public Long getWqComputed40sProjectionCount() {
        return this.wqComputed40sProjectionCount;
    }

    protected void setWqComputed40sProjectionCount(Long wqComputed40sProjectionCount) {
        this.wqComputed40sProjectionCount = wqComputed40sProjectionCount;
    }

    public Long getWqVesselLcg() {
        return this.wqVesselLcg;
    }

    protected void setWqVesselLcg(Long wqVesselLcg) {
        this.wqVesselLcg = wqVesselLcg;
    }

    public Boolean getWqUseWqProd() {
        return this.wqUseWqProd;
    }

    protected void setWqUseWqProd(Boolean wqUseWqProd) {
        this.wqUseWqProd = wqUseWqProd;
    }

    public Long getWqProductivityStd() {
        return this.wqProductivityStd;
    }

    protected void setWqProductivityStd(Long wqProductivityStd) {
        this.wqProductivityStd = wqProductivityStd;
    }

    public Long getWqProductivityDual() {
        return this.wqProductivityDual;
    }

    protected void setWqProductivityDual(Long wqProductivityDual) {
        this.wqProductivityDual = wqProductivityDual;
    }

    public Long getWqProductivityTwin() {
        return this.wqProductivityTwin;
    }

    protected void setWqProductivityTwin(Long wqProductivityTwin) {
        this.wqProductivityTwin = wqProductivityTwin;
    }

    public Long getWqMaxTeuCap() {
        return this.wqMaxTeuCap;
    }

    protected void setWqMaxTeuCap(Long wqMaxTeuCap) {
        this.wqMaxTeuCap = wqMaxTeuCap;
    }

    public Long getWqPercentCapToStartPriority() {
        return this.wqPercentCapToStartPriority;
    }

    protected void setWqPercentCapToStartPriority(Long wqPercentCapToStartPriority) {
        this.wqPercentCapToStartPriority = wqPercentCapToStartPriority;
    }

    public Long getWqPenaltyForExceedingPerCap() {
        return this.wqPenaltyForExceedingPerCap;
    }

    protected void setWqPenaltyForExceedingPerCap(Long wqPenaltyForExceedingPerCap) {
        this.wqPenaltyForExceedingPerCap = wqPenaltyForExceedingPerCap;
    }

    public Long getWqTlsSortKey() {
        return this.wqTlsSortKey;
    }

    protected void setWqTlsSortKey(Long wqTlsSortKey) {
        this.wqTlsSortKey = wqTlsSortKey;
    }

    public Long getWqProductivityTandem() {
        return this.wqProductivityTandem;
    }

    protected void setWqProductivityTandem(Long wqProductivityTandem) {
        this.wqProductivityTandem = wqProductivityTandem;
    }

    public Long getWqProductivityQuad() {
        return this.wqProductivityQuad;
    }

    protected void setWqProductivityQuad(Long wqProductivityQuad) {
        this.wqProductivityQuad = wqProductivityQuad;
    }

    public Long getWqAbsBerthCallSlaveId() {
        return this.wqAbsBerthCallSlaveId;
    }

    protected void setWqAbsBerthCallSlaveId(Long wqAbsBerthCallSlaveId) {
        this.wqAbsBerthCallSlaveId = wqAbsBerthCallSlaveId;
    }

    public Date getWqLastActivationChange() {
        return this.wqLastActivationChange;
    }

    protected void setWqLastActivationChange(Date wqLastActivationChange) {
        this.wqLastActivationChange = wqLastActivationChange;
    }

    public PointOfWork getWqPow() {
        return this.wqPow;
    }

    protected void setWqPow(PointOfWork wqPow) {
        this.wqPow = wqPow;
    }

    public Yard getWqYard() {
        return this.wqYard;
    }

    protected void setWqYard(Yard wqYard) {
        this.wqYard = wqYard;
    }

    public WorkQueue getWqCycleCompanion() {
        return this.wqCycleCompanion;
    }

    protected void setWqCycleCompanion(WorkQueue wqCycleCompanion) {
        this.wqCycleCompanion = wqCycleCompanion;
    }

    public WorkShift getWqFirstRelatedShift() {
        return this.wqFirstRelatedShift;
    }

    protected void setWqFirstRelatedShift(WorkShift wqFirstRelatedShift) {
        this.wqFirstRelatedShift = wqFirstRelatedShift;
    }

}
