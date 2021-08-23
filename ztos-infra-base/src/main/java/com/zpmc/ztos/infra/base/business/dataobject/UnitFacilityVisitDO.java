package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.business.enums.argo.RailConeStatusEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.RestowTypeEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.TransferLocationEnum;
import com.zpmc.ztos.infra.base.business.enums.inventory.*;
import com.zpmc.ztos.infra.base.business.inventory.Unit;
import com.zpmc.ztos.infra.base.business.model.CarrierVisit;
import com.zpmc.ztos.infra.base.business.model.LocPosition;
import com.zpmc.ztos.infra.base.business.model.ScopedBizUnit;
import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;
import com.zpmc.ztos.infra.base.common.scopes.Facility;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class UnitFacilityVisitDO extends DatabaseEntity implements Serializable {
    private Long ufvGkey;
    private Long ufvHorizon;
    private UnitVisitStateEnum ufvVisitState;
    private UfvTransitStateEnum ufvTransitState;
    private Date ufvCreateTime;
    private Date ufvTimeOfLastMove;
    private Date ufvTimeOfLoading;
    private Date ufvTimeEcIn;
    private Date ufvTimeEcOut;
    private Date ufvTimeIn;
    private Date ufvTimeOut;
    private Date ufvDlvTimeAppntmnt;
    private Long ufvGapptNbr;
    private Date ufvTimeInventory;
    private Date ufvTimeComplete;
    private Long ufvSequence;
    private RestowTypeEnum ufvRestowType;
    private HandlingReasonEnum ufvHandlingReason;
    private YardStowRqmntEnum ufvYardStowageType;
    private Boolean ufvIsSparcsStopped;
    private String ufvSparcsNote;
    private Boolean ufvHasChanged;
    private Boolean ufvVerifiedForLoad;
    private Boolean ufvVerifiedYardPosition;
    private Boolean ufvAnomalyForExpDkng;
    private Date ufvLastFreeDay;
    private Date ufvPaidThruDay;
    private Date ufvGuaranteeThruDay;
    private Date ufvPowerLastFreeDay;
    private Date ufvPowerPaidThruDay;
    private Date ufvPowerGuaranteeThruDay;
    private Date ufvLineLastFreeDay;
    private Date ufvLinePaidThruDay;
    private Date ufvLineGuaranteeThruDay;
    private Boolean ufvVisibleInSparcs;
    private String ufvFlexString01;
    private String ufvFlexString02;
    private String ufvFlexString03;
    private String ufvFlexString04;
    private String ufvFlexString05;
    private String ufvFlexString06;
    private String ufvFlexString07;
    private String ufvFlexString08;
    private String ufvFlexString09;
    private String ufvFlexString10;
    private Date ufvFlexDate01;
    private Date ufvFlexDate02;
    private Date ufvFlexDate03;
    private Date ufvFlexDate04;
    private Date ufvFlexDate05;
    private Date ufvFlexDate06;
    private Date ufvFlexDate07;
    private Date ufvFlexDate08;
    private Boolean ufvIsDirectIbToObMove;
    private String ufvValidateMarryErrors;
    private String ufvStowFactor;
    private Boolean ufvReturnToYard;
    private Long ufvMoveCount;
    private Boolean ufvIsRASPriority;
    private String ufvHousekeepingCurrentSlot;
    private String ufvHousekeepingFutureSlot;
    private Long ufvHousekeepingCurrentScore;
    private Long ufvHousekeepingFutureScore;
    private Date ufvHousekeepingTimeStamp;
    private String ufvStackingFactor;
    private String ufvSectionFactor;
    private String ufvSegregationFactor;
    private String ufvCasUnitReference;
    private String ufvCasTransactionReference;
    private TransferLocationEnum ufvPreferredTransferLocation;
    private DoorDirectionEnum ufvDoorDirection;
    private String ufvOptimalRailTZSlot;
    private CarrierIncompatibilityReasonEnum ufvCarrierIncompatibleReason;
    private RailConeStatusEnum ufvRailConeStatus;
    private Date ufvChanged;
    private Boolean ufvHasActiveAlarm;
    private Unit ufvUnit;
    private Facility ufvFacility;
    private CarrierVisit ufvIntendedObCv;
    private CarrierVisit ufvActualIbCv;
    private CarrierVisit ufvActualObCv;
    private ScopedBizUnit ufvGuaranteeParty;
    private ScopedBizUnit ufvPowerGuaranteeParty;
    private ScopedBizUnit ufvLineGuaranteeParty;
    private Set ufvUfvMoveEventUniqueIds;
    private List ufvUyvList;
    private List ufvAlarms;
    private LocPosition ufvArrivePosition;
    private LocPosition ufvLastKnownPosition;

    public Serializable getPrimaryKey() {
        return this.getUfvGkey();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        Long id = this.getUfvGkey();
        if (id == null) {
            return false;
        }
        if (!(other instanceof UnitFacilityVisitDO)) {
            return false;
        }
        UnitFacilityVisitDO that = (UnitFacilityVisitDO)other;
        return ((Object)id).equals(that.getUfvGkey());
    }

    public int hashCode() {
        Long id = this.getUfvGkey();
        return id == null ? System.identityHashCode(this) : ((Object)id).hashCode();
    }

    public Long getUfvGkey() {
        return this.ufvGkey;
    }

    protected void setUfvGkey(Long ufvGkey) {
        this.ufvGkey = ufvGkey;
    }

    public Long getUfvHorizon() {
        return this.ufvHorizon;
    }

    protected void setUfvHorizon(Long ufvHorizon) {
        this.ufvHorizon = ufvHorizon;
    }

    public UnitVisitStateEnum getUfvVisitState() {
        return this.ufvVisitState;
    }

    protected void setUfvVisitState(UnitVisitStateEnum ufvVisitState) {
        this.ufvVisitState = ufvVisitState;
    }

    public UfvTransitStateEnum getUfvTransitState() {
        return this.ufvTransitState;
    }

    protected void setUfvTransitState(UfvTransitStateEnum ufvTransitState) {
        this.ufvTransitState = ufvTransitState;
    }

    public Date getUfvCreateTime() {
        return this.ufvCreateTime;
    }

    protected void setUfvCreateTime(Date ufvCreateTime) {
        this.ufvCreateTime = ufvCreateTime;
    }

    public Date getUfvTimeOfLastMove() {
        return this.ufvTimeOfLastMove;
    }

    protected void setUfvTimeOfLastMove(Date ufvTimeOfLastMove) {
        this.ufvTimeOfLastMove = ufvTimeOfLastMove;
    }

    public Date getUfvTimeOfLoading() {
        return this.ufvTimeOfLoading;
    }

    protected void setUfvTimeOfLoading(Date ufvTimeOfLoading) {
        this.ufvTimeOfLoading = ufvTimeOfLoading;
    }

    public Date getUfvTimeEcIn() {
        return this.ufvTimeEcIn;
    }

    protected void setUfvTimeEcIn(Date ufvTimeEcIn) {
        this.ufvTimeEcIn = ufvTimeEcIn;
    }

    public Date getUfvTimeEcOut() {
        return this.ufvTimeEcOut;
    }

    protected void setUfvTimeEcOut(Date ufvTimeEcOut) {
        this.ufvTimeEcOut = ufvTimeEcOut;
    }

    public Date getUfvTimeIn() {
        return this.ufvTimeIn;
    }

    public void setUfvTimeIn(Date ufvTimeIn) {
        this.ufvTimeIn = ufvTimeIn;
    }

    public Date getUfvTimeOut() {
        return this.ufvTimeOut;
    }

    protected void setUfvTimeOut(Date ufvTimeOut) {
        this.ufvTimeOut = ufvTimeOut;
    }

    public Date getUfvDlvTimeAppntmnt() {
        return this.ufvDlvTimeAppntmnt;
    }

    protected void setUfvDlvTimeAppntmnt(Date ufvDlvTimeAppntmnt) {
        this.ufvDlvTimeAppntmnt = ufvDlvTimeAppntmnt;
    }

    public Long getUfvGapptNbr() {
        return this.ufvGapptNbr;
    }

    protected void setUfvGapptNbr(Long ufvGapptNbr) {
        this.ufvGapptNbr = ufvGapptNbr;
    }

    public Date getUfvTimeInventory() {
        return this.ufvTimeInventory;
    }

    protected void setUfvTimeInventory(Date ufvTimeInventory) {
        this.ufvTimeInventory = ufvTimeInventory;
    }

    public Date getUfvTimeComplete() {
        return this.ufvTimeComplete;
    }

    protected void setUfvTimeComplete(Date ufvTimeComplete) {
        this.ufvTimeComplete = ufvTimeComplete;
    }

    public Long getUfvSequence() {
        return this.ufvSequence;
    }

    protected void setUfvSequence(Long ufvSequence) {
        this.ufvSequence = ufvSequence;
    }

    public RestowTypeEnum getUfvRestowType() {
        return this.ufvRestowType;
    }

    public void setUfvRestowType(RestowTypeEnum ufvRestowType) {
        this.ufvRestowType = ufvRestowType;
    }

    public HandlingReasonEnum getUfvHandlingReason() {
        return this.ufvHandlingReason;
    }

    protected void setUfvHandlingReason(HandlingReasonEnum ufvHandlingReason) {
        this.ufvHandlingReason = ufvHandlingReason;
    }

    public YardStowRqmntEnum getUfvYardStowageType() {
        return this.ufvYardStowageType;
    }

    protected void setUfvYardStowageType(YardStowRqmntEnum ufvYardStowageType) {
        this.ufvYardStowageType = ufvYardStowageType;
    }

    public Boolean getUfvIsSparcsStopped() {
        return this.ufvIsSparcsStopped;
    }

    protected void setUfvIsSparcsStopped(Boolean ufvIsSparcsStopped) {
        this.ufvIsSparcsStopped = ufvIsSparcsStopped;
    }

    public String getUfvSparcsNote() {
        return this.ufvSparcsNote;
    }

    protected void setUfvSparcsNote(String ufvSparcsNote) {
        this.ufvSparcsNote = ufvSparcsNote;
    }

    public Boolean getUfvHasChanged() {
        return this.ufvHasChanged;
    }

    protected void setUfvHasChanged(Boolean ufvHasChanged) {
        this.ufvHasChanged = ufvHasChanged;
    }

    public Boolean getUfvVerifiedForLoad() {
        return this.ufvVerifiedForLoad;
    }

    protected void setUfvVerifiedForLoad(Boolean ufvVerifiedForLoad) {
        this.ufvVerifiedForLoad = ufvVerifiedForLoad;
    }

    public Boolean getUfvVerifiedYardPosition() {
        return this.ufvVerifiedYardPosition;
    }

    protected void setUfvVerifiedYardPosition(Boolean ufvVerifiedYardPosition) {
        this.ufvVerifiedYardPosition = ufvVerifiedYardPosition;
    }

    public Boolean getUfvAnomalyForExpDkng() {
        return this.ufvAnomalyForExpDkng;
    }

    protected void setUfvAnomalyForExpDkng(Boolean ufvAnomalyForExpDkng) {
        this.ufvAnomalyForExpDkng = ufvAnomalyForExpDkng;
    }

    public Date getUfvLastFreeDay() {
        return this.ufvLastFreeDay;
    }

    protected void setUfvLastFreeDay(Date ufvLastFreeDay) {
        this.ufvLastFreeDay = ufvLastFreeDay;
    }

    public Date getUfvPaidThruDay() {
        return this.ufvPaidThruDay;
    }

    protected void setUfvPaidThruDay(Date ufvPaidThruDay) {
        this.ufvPaidThruDay = ufvPaidThruDay;
    }

    public Date getUfvGuaranteeThruDay() {
        return this.ufvGuaranteeThruDay;
    }

    protected void setUfvGuaranteeThruDay(Date ufvGuaranteeThruDay) {
        this.ufvGuaranteeThruDay = ufvGuaranteeThruDay;
    }

    public Date getUfvPowerLastFreeDay() {
        return this.ufvPowerLastFreeDay;
    }

    protected void setUfvPowerLastFreeDay(Date ufvPowerLastFreeDay) {
        this.ufvPowerLastFreeDay = ufvPowerLastFreeDay;
    }

    public Date getUfvPowerPaidThruDay() {
        return this.ufvPowerPaidThruDay;
    }

    protected void setUfvPowerPaidThruDay(Date ufvPowerPaidThruDay) {
        this.ufvPowerPaidThruDay = ufvPowerPaidThruDay;
    }

    public Date getUfvPowerGuaranteeThruDay() {
        return this.ufvPowerGuaranteeThruDay;
    }

    protected void setUfvPowerGuaranteeThruDay(Date ufvPowerGuaranteeThruDay) {
        this.ufvPowerGuaranteeThruDay = ufvPowerGuaranteeThruDay;
    }

    public Date getUfvLineLastFreeDay() {
        return this.ufvLineLastFreeDay;
    }

    protected void setUfvLineLastFreeDay(Date ufvLineLastFreeDay) {
        this.ufvLineLastFreeDay = ufvLineLastFreeDay;
    }

    public Date getUfvLinePaidThruDay() {
        return this.ufvLinePaidThruDay;
    }

    protected void setUfvLinePaidThruDay(Date ufvLinePaidThruDay) {
        this.ufvLinePaidThruDay = ufvLinePaidThruDay;
    }

    public Date getUfvLineGuaranteeThruDay() {
        return this.ufvLineGuaranteeThruDay;
    }

    protected void setUfvLineGuaranteeThruDay(Date ufvLineGuaranteeThruDay) {
        this.ufvLineGuaranteeThruDay = ufvLineGuaranteeThruDay;
    }

    public Boolean getUfvVisibleInSparcs() {
        return this.ufvVisibleInSparcs;
    }

    public void setUfvVisibleInSparcs(Boolean ufvVisibleInSparcs) {
        this.ufvVisibleInSparcs = ufvVisibleInSparcs;
    }

    public String getUfvFlexString01() {
        return this.ufvFlexString01;
    }

    protected void setUfvFlexString01(String ufvFlexString01) {
        this.ufvFlexString01 = ufvFlexString01;
    }

    public String getUfvFlexString02() {
        return this.ufvFlexString02;
    }

    protected void setUfvFlexString02(String ufvFlexString02) {
        this.ufvFlexString02 = ufvFlexString02;
    }

    public String getUfvFlexString03() {
        return this.ufvFlexString03;
    }

    protected void setUfvFlexString03(String ufvFlexString03) {
        this.ufvFlexString03 = ufvFlexString03;
    }

    public String getUfvFlexString04() {
        return this.ufvFlexString04;
    }

    protected void setUfvFlexString04(String ufvFlexString04) {
        this.ufvFlexString04 = ufvFlexString04;
    }

    public String getUfvFlexString05() {
        return this.ufvFlexString05;
    }

    protected void setUfvFlexString05(String ufvFlexString05) {
        this.ufvFlexString05 = ufvFlexString05;
    }

    public String getUfvFlexString06() {
        return this.ufvFlexString06;
    }

    protected void setUfvFlexString06(String ufvFlexString06) {
        this.ufvFlexString06 = ufvFlexString06;
    }

    public String getUfvFlexString07() {
        return this.ufvFlexString07;
    }

    protected void setUfvFlexString07(String ufvFlexString07) {
        this.ufvFlexString07 = ufvFlexString07;
    }

    public String getUfvFlexString08() {
        return this.ufvFlexString08;
    }

    protected void setUfvFlexString08(String ufvFlexString08) {
        this.ufvFlexString08 = ufvFlexString08;
    }

    public String getUfvFlexString09() {
        return this.ufvFlexString09;
    }

    protected void setUfvFlexString09(String ufvFlexString09) {
        this.ufvFlexString09 = ufvFlexString09;
    }

    public String getUfvFlexString10() {
        return this.ufvFlexString10;
    }

    protected void setUfvFlexString10(String ufvFlexString10) {
        this.ufvFlexString10 = ufvFlexString10;
    }

    public Date getUfvFlexDate01() {
        return this.ufvFlexDate01;
    }

    protected void setUfvFlexDate01(Date ufvFlexDate01) {
        this.ufvFlexDate01 = ufvFlexDate01;
    }

    public Date getUfvFlexDate02() {
        return this.ufvFlexDate02;
    }

    protected void setUfvFlexDate02(Date ufvFlexDate02) {
        this.ufvFlexDate02 = ufvFlexDate02;
    }

    public Date getUfvFlexDate03() {
        return this.ufvFlexDate03;
    }

    protected void setUfvFlexDate03(Date ufvFlexDate03) {
        this.ufvFlexDate03 = ufvFlexDate03;
    }

    public Date getUfvFlexDate04() {
        return this.ufvFlexDate04;
    }

    protected void setUfvFlexDate04(Date ufvFlexDate04) {
        this.ufvFlexDate04 = ufvFlexDate04;
    }

    public Date getUfvFlexDate05() {
        return this.ufvFlexDate05;
    }

    protected void setUfvFlexDate05(Date ufvFlexDate05) {
        this.ufvFlexDate05 = ufvFlexDate05;
    }

    public Date getUfvFlexDate06() {
        return this.ufvFlexDate06;
    }

    protected void setUfvFlexDate06(Date ufvFlexDate06) {
        this.ufvFlexDate06 = ufvFlexDate06;
    }

    public Date getUfvFlexDate07() {
        return this.ufvFlexDate07;
    }

    protected void setUfvFlexDate07(Date ufvFlexDate07) {
        this.ufvFlexDate07 = ufvFlexDate07;
    }

    public Date getUfvFlexDate08() {
        return this.ufvFlexDate08;
    }

    protected void setUfvFlexDate08(Date ufvFlexDate08) {
        this.ufvFlexDate08 = ufvFlexDate08;
    }

    public Boolean getUfvIsDirectIbToObMove() {
        return this.ufvIsDirectIbToObMove;
    }

    protected void setUfvIsDirectIbToObMove(Boolean ufvIsDirectIbToObMove) {
        this.ufvIsDirectIbToObMove = ufvIsDirectIbToObMove;
    }

    public String getUfvValidateMarryErrors() {
        return this.ufvValidateMarryErrors;
    }

    protected void setUfvValidateMarryErrors(String ufvValidateMarryErrors) {
        this.ufvValidateMarryErrors = ufvValidateMarryErrors;
    }

    public String getUfvStowFactor() {
        return this.ufvStowFactor;
    }

    protected void setUfvStowFactor(String ufvStowFactor) {
        this.ufvStowFactor = ufvStowFactor;
    }

    public Boolean getUfvReturnToYard() {
        return this.ufvReturnToYard;
    }

    protected void setUfvReturnToYard(Boolean ufvReturnToYard) {
        this.ufvReturnToYard = ufvReturnToYard;
    }

    public Long getUfvMoveCount() {
        return this.ufvMoveCount;
    }

    protected void setUfvMoveCount(Long ufvMoveCount) {
        this.ufvMoveCount = ufvMoveCount;
    }

    public Boolean getUfvIsRASPriority() {
        return this.ufvIsRASPriority;
    }

    protected void setUfvIsRASPriority(Boolean ufvIsRASPriority) {
        this.ufvIsRASPriority = ufvIsRASPriority;
    }

    public String getUfvHousekeepingCurrentSlot() {
        return this.ufvHousekeepingCurrentSlot;
    }

    protected void setUfvHousekeepingCurrentSlot(String ufvHousekeepingCurrentSlot) {
        this.ufvHousekeepingCurrentSlot = ufvHousekeepingCurrentSlot;
    }

    public String getUfvHousekeepingFutureSlot() {
        return this.ufvHousekeepingFutureSlot;
    }

    protected void setUfvHousekeepingFutureSlot(String ufvHousekeepingFutureSlot) {
        this.ufvHousekeepingFutureSlot = ufvHousekeepingFutureSlot;
    }

    public Long getUfvHousekeepingCurrentScore() {
        return this.ufvHousekeepingCurrentScore;
    }

    protected void setUfvHousekeepingCurrentScore(Long ufvHousekeepingCurrentScore) {
        this.ufvHousekeepingCurrentScore = ufvHousekeepingCurrentScore;
    }

    public Long getUfvHousekeepingFutureScore() {
        return this.ufvHousekeepingFutureScore;
    }

    protected void setUfvHousekeepingFutureScore(Long ufvHousekeepingFutureScore) {
        this.ufvHousekeepingFutureScore = ufvHousekeepingFutureScore;
    }

    public Date getUfvHousekeepingTimeStamp() {
        return this.ufvHousekeepingTimeStamp;
    }

    protected void setUfvHousekeepingTimeStamp(Date ufvHousekeepingTimeStamp) {
        this.ufvHousekeepingTimeStamp = ufvHousekeepingTimeStamp;
    }

    public String getUfvStackingFactor() {
        return this.ufvStackingFactor;
    }

    protected void setUfvStackingFactor(String ufvStackingFactor) {
        this.ufvStackingFactor = ufvStackingFactor;
    }

    public String getUfvSectionFactor() {
        return this.ufvSectionFactor;
    }

    protected void setUfvSectionFactor(String ufvSectionFactor) {
        this.ufvSectionFactor = ufvSectionFactor;
    }

    public String getUfvSegregationFactor() {
        return this.ufvSegregationFactor;
    }

    protected void setUfvSegregationFactor(String ufvSegregationFactor) {
        this.ufvSegregationFactor = ufvSegregationFactor;
    }

    public String getUfvCasUnitReference() {
        return this.ufvCasUnitReference;
    }

    protected void setUfvCasUnitReference(String ufvCasUnitReference) {
        this.ufvCasUnitReference = ufvCasUnitReference;
    }

    public String getUfvCasTransactionReference() {
        return this.ufvCasTransactionReference;
    }

    protected void setUfvCasTransactionReference(String ufvCasTransactionReference) {
        this.ufvCasTransactionReference = ufvCasTransactionReference;
    }

    public TransferLocationEnum getUfvPreferredTransferLocation() {
        return this.ufvPreferredTransferLocation;
    }

    protected void setUfvPreferredTransferLocation(TransferLocationEnum ufvPreferredTransferLocation) {
        this.ufvPreferredTransferLocation = ufvPreferredTransferLocation;
    }

    public DoorDirectionEnum getUfvDoorDirection() {
        return this.ufvDoorDirection;
    }

    protected void setUfvDoorDirection(DoorDirectionEnum ufvDoorDirection) {
        this.ufvDoorDirection = ufvDoorDirection;
    }

    public String getUfvOptimalRailTZSlot() {
        return this.ufvOptimalRailTZSlot;
    }

    protected void setUfvOptimalRailTZSlot(String ufvOptimalRailTZSlot) {
        this.ufvOptimalRailTZSlot = ufvOptimalRailTZSlot;
    }

    public CarrierIncompatibilityReasonEnum getUfvCarrierIncompatibleReason() {
        return this.ufvCarrierIncompatibleReason;
    }

    protected void setUfvCarrierIncompatibleReason(CarrierIncompatibilityReasonEnum ufvCarrierIncompatibleReason) {
        this.ufvCarrierIncompatibleReason = ufvCarrierIncompatibleReason;
    }

    public RailConeStatusEnum getUfvRailConeStatus() {
        return this.ufvRailConeStatus;
    }

    protected void setUfvRailConeStatus(RailConeStatusEnum ufvRailConeStatus) {
        this.ufvRailConeStatus = ufvRailConeStatus;
    }

    public Date getUfvChanged() {
        return this.ufvChanged;
    }

    protected void setUfvChanged(Date ufvChanged) {
        this.ufvChanged = ufvChanged;
    }

    public Boolean getUfvHasActiveAlarm() {
        return this.ufvHasActiveAlarm;
    }

    protected void setUfvHasActiveAlarm(Boolean ufvHasActiveAlarm) {
        this.ufvHasActiveAlarm = ufvHasActiveAlarm;
    }

    public Unit getUfvUnit() {
        return this.ufvUnit;
    }

    protected void setUfvUnit(Unit ufvUnit) {
        this.ufvUnit = ufvUnit;
    }

    public Facility getUfvFacility() {
        return this.ufvFacility;
    }

    protected void setUfvFacility(Facility ufvFacility) {
        this.ufvFacility = ufvFacility;
    }

    public CarrierVisit getUfvIntendedObCv() {
        return this.ufvIntendedObCv;
    }

    protected void setUfvIntendedObCv(CarrierVisit ufvIntendedObCv) {
        this.ufvIntendedObCv = ufvIntendedObCv;
    }

    public CarrierVisit getUfvActualIbCv() {
        return this.ufvActualIbCv;
    }

    public void setUfvActualIbCv(CarrierVisit ufvActualIbCv) {
        this.ufvActualIbCv = ufvActualIbCv;
    }

    public CarrierVisit getUfvActualObCv() {
        return this.ufvActualObCv;
    }

    protected void setUfvActualObCv(CarrierVisit ufvActualObCv) {
        this.ufvActualObCv = ufvActualObCv;
    }

    public ScopedBizUnit getUfvGuaranteeParty() {
        return this.ufvGuaranteeParty;
    }

    protected void setUfvGuaranteeParty(ScopedBizUnit ufvGuaranteeParty) {
        this.ufvGuaranteeParty = ufvGuaranteeParty;
    }

    public ScopedBizUnit getUfvPowerGuaranteeParty() {
        return this.ufvPowerGuaranteeParty;
    }

    protected void setUfvPowerGuaranteeParty(ScopedBizUnit ufvPowerGuaranteeParty) {
        this.ufvPowerGuaranteeParty = ufvPowerGuaranteeParty;
    }

    public ScopedBizUnit getUfvLineGuaranteeParty() {
        return this.ufvLineGuaranteeParty;
    }

    protected void setUfvLineGuaranteeParty(ScopedBizUnit ufvLineGuaranteeParty) {
        this.ufvLineGuaranteeParty = ufvLineGuaranteeParty;
    }

    public Set getUfvUfvMoveEventUniqueIds() {
        return this.ufvUfvMoveEventUniqueIds;
    }

    protected void setUfvUfvMoveEventUniqueIds(Set ufvUfvMoveEventUniqueIds) {
        this.ufvUfvMoveEventUniqueIds = ufvUfvMoveEventUniqueIds;
    }

    public List getUfvUyvList() {
        return this.ufvUyvList;
    }

    public void setUfvUyvList(List ufvUyvList) {
        this.ufvUyvList = ufvUyvList;
    }

    public List getUfvAlarms() {
        return this.ufvAlarms;
    }

    protected void setUfvAlarms(List ufvAlarms) {
        this.ufvAlarms = ufvAlarms;
    }

    public LocPosition getUfvArrivePosition() {
        return this.ufvArrivePosition;
    }

    public void setUfvArrivePosition(LocPosition ufvArrivePosition) {
        this.ufvArrivePosition = ufvArrivePosition;
    }

    public LocPosition getUfvLastKnownPosition() {
        return this.ufvLastKnownPosition;
    }

    public void setUfvLastKnownPosition(LocPosition ufvLastKnownPosition) {
        this.ufvLastKnownPosition = ufvLastKnownPosition;
    }
}
