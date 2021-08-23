package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.business.enums.argo.PowDispatchModeEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.PowUncompletedWorkSortEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.WiMoveKindEnum;
import com.zpmc.ztos.infra.base.business.enums.framework.LifeCycleStateEnum;
import com.zpmc.ztos.infra.base.business.equipments.Che;
import com.zpmc.ztos.infra.base.business.equipments.ChePool;
import com.zpmc.ztos.infra.base.business.model.CarrierVisit;
import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;
import com.zpmc.ztos.infra.base.common.scopes.Yard;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class PointOfWorkDO extends DatabaseEntity implements Serializable {
    private Long pointofworkGkey;
    private Long pointofworkPkey;
    private Long pointofworkOwnerPoolReference;
    private String pointofworkName;
    private String pointofworkClerk;
    private String pointofworkVesselVisitReference;
    private Long pointofworkCheId;
    private Float pointofworkCraneWidth;
    private Float pointofworkProduction;
    private Float pointofworkDualProduction;
    private Long pointofworkDispatchMode;
    private PowDispatchModeEnum pointofworkDispatchModeEnum;
    private Boolean pointofworkDoubleCycling;
    private Boolean pointofworkNeedSendVisitToAbb;
    private Boolean pointofworkIsBulkRunPow;
    private Boolean pointofworkIsRailPow;
    private Boolean pointofworkDoesTwoTwentiesLoad;
    private Boolean pointofworkDoesTwoTwentiesDischarge;
    private Boolean pointofworkIsDischargingToBuffer;
    private Boolean pointofworkIsRoadPow;
    private String pointofworkLaneName;
    private Long pointofworkQuayOrder;
    private Long pointofworkLoadRequest;
    private Long pointofworkLoadAck;
    private Long pointofworkYardCrane1Id;
    private Long pointofworkYardCrane2Id;
    private Long pointofworkYardCrane3Id;
    private Long pointofworkYardCrane4Id;
    private Long pointofworkYardCrane5Id;
    private Long pointofworkMaximumPms;
    private Long pointofworkPushRate;
    private Boolean pointofworkAutoPushRate;
    private Long pointofworkRelativePriority;
    private String pointofworkVoiceChannel;
    private Date pointofworkLastQuayOrRailAccess;
    private String pointofworkQcVesselVisitReference;
    private Long pointofworkQcLastMoveType;
    private WiMoveKindEnum pointofworkQcLastMoveTypeEnum;
    private String pointofworkQcVesselBay;
    private Long pointofworkRoadObjective;
    private String pointofworkBufferName;
    private Float pointofworkCraneReachLeft;
    private Float pointofworkCraneReachRight;
    private Boolean pointofworkDoNotMakeLate;
    private String pointofworkTerminalCode;
    private Float pointofworkTwinProduction;
    private Date pointofworkLastDispatchTime;
    private Long pointofworkMinimumPms;
    private Long pointofworkJobListPriority;
    private String pointofworkCheWaitingAtPow;
    private Float pointofworkTandemProduction;
    private Float pointofworkQuadProduction;
    private String pointofworkPowDriveDirection;
    private LifeCycleStateEnum pointofworkLifeCycleState;
    private PowUncompletedWorkSortEnum pointofworkUncompletedWorkSort;
    private Boolean pointofworkHasActiveAlarm;
    private Yard pointofworkYard;
    private ChePool pointofworkOwnerPool;
    private CarrierVisit pointofworkVesselVisit;
    private Che pointofworkYardCrane1;
    private Che pointofworkYardCrane2;
    private Che pointofworkYardCrane3;
    private Che pointofworkYardCrane4;
    private Che pointofworkYardCrane5;
    private CarrierVisit pointofworkQcVesselVisit;
    private List pointofworkAlarms;

    public Serializable getPrimaryKey() {
        return this.getPointofworkGkey();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        Long id = this.getPointofworkGkey();
        if (id == null) {
            return false;
        }
        if (!(other instanceof PointOfWorkDO)) {
            return false;
        }
        PointOfWorkDO that = (PointOfWorkDO)other;
        return ((Object)id).equals(that.getPointofworkGkey());
    }

    public int hashCode() {
        Long id = this.getPointofworkGkey();
        return id == null ? System.identityHashCode(this) : ((Object)id).hashCode();
    }

    public Long getPointofworkGkey() {
        return this.pointofworkGkey;
    }

    public void setPointofworkGkey(Long pointofworkGkey) {
        this.pointofworkGkey = pointofworkGkey;
    }

    public Long getPointofworkPkey() {
        return this.pointofworkPkey;
    }

    public void setPointofworkPkey(Long pointofworkPkey) {
        this.pointofworkPkey = pointofworkPkey;
    }

    public Long getPointofworkOwnerPoolReference() {
        return this.pointofworkOwnerPoolReference;
    }

    public void setPointofworkOwnerPoolReference(Long pointofworkOwnerPoolReference) {
        this.pointofworkOwnerPoolReference = pointofworkOwnerPoolReference;
    }

    public String getPointofworkName() {
        return this.pointofworkName;
    }

    public void setPointofworkName(String pointofworkName) {
        this.pointofworkName = pointofworkName;
    }

    public String getPointofworkClerk() {
        return this.pointofworkClerk;
    }

    public void setPointofworkClerk(String pointofworkClerk) {
        this.pointofworkClerk = pointofworkClerk;
    }

    public String getPointofworkVesselVisitReference() {
        return this.pointofworkVesselVisitReference;
    }

    public void setPointofworkVesselVisitReference(String pointofworkVesselVisitReference) {
        this.pointofworkVesselVisitReference = pointofworkVesselVisitReference;
    }

    public Long getPointofworkCheId() {
        return this.pointofworkCheId;
    }

    public void setPointofworkCheId(Long pointofworkCheId) {
        this.pointofworkCheId = pointofworkCheId;
    }

    public Float getPointofworkCraneWidth() {
        return this.pointofworkCraneWidth;
    }

    public void setPointofworkCraneWidth(Float pointofworkCraneWidth) {
        this.pointofworkCraneWidth = pointofworkCraneWidth;
    }

    public Float getPointofworkProduction() {
        return this.pointofworkProduction;
    }

    public void setPointofworkProduction(Float pointofworkProduction) {
        this.pointofworkProduction = pointofworkProduction;
    }

    public Float getPointofworkDualProduction() {
        return this.pointofworkDualProduction;
    }

    public void setPointofworkDualProduction(Float pointofworkDualProduction) {
        this.pointofworkDualProduction = pointofworkDualProduction;
    }

    public Long getPointofworkDispatchMode() {
        return this.pointofworkDispatchMode;
    }

    public void setPointofworkDispatchMode(Long pointofworkDispatchMode) {
        this.pointofworkDispatchMode = pointofworkDispatchMode;
    }

    public PowDispatchModeEnum getPointofworkDispatchModeEnum() {
        return this.pointofworkDispatchModeEnum;
    }

    public void setPointofworkDispatchModeEnum(PowDispatchModeEnum pointofworkDispatchModeEnum) {
        this.pointofworkDispatchModeEnum = pointofworkDispatchModeEnum;
    }

    public Boolean getPointofworkDoubleCycling() {
        return this.pointofworkDoubleCycling;
    }

    public void setPointofworkDoubleCycling(Boolean pointofworkDoubleCycling) {
        this.pointofworkDoubleCycling = pointofworkDoubleCycling;
    }

    public Boolean getPointofworkNeedSendVisitToAbb() {
        return this.pointofworkNeedSendVisitToAbb;
    }

    public void setPointofworkNeedSendVisitToAbb(Boolean pointofworkNeedSendVisitToAbb) {
        this.pointofworkNeedSendVisitToAbb = pointofworkNeedSendVisitToAbb;
    }

    public Boolean getPointofworkIsBulkRunPow() {
        return this.pointofworkIsBulkRunPow;
    }

    public void setPointofworkIsBulkRunPow(Boolean pointofworkIsBulkRunPow) {
        this.pointofworkIsBulkRunPow = pointofworkIsBulkRunPow;
    }

    public Boolean getPointofworkIsRailPow() {
        return this.pointofworkIsRailPow;
    }

    public void setPointofworkIsRailPow(Boolean pointofworkIsRailPow) {
        this.pointofworkIsRailPow = pointofworkIsRailPow;
    }

    public Boolean getPointofworkDoesTwoTwentiesLoad() {
        return this.pointofworkDoesTwoTwentiesLoad;
    }

    public void setPointofworkDoesTwoTwentiesLoad(Boolean pointofworkDoesTwoTwentiesLoad) {
        this.pointofworkDoesTwoTwentiesLoad = pointofworkDoesTwoTwentiesLoad;
    }

    public Boolean getPointofworkDoesTwoTwentiesDischarge() {
        return this.pointofworkDoesTwoTwentiesDischarge;
    }

    public void setPointofworkDoesTwoTwentiesDischarge(Boolean pointofworkDoesTwoTwentiesDischarge) {
        this.pointofworkDoesTwoTwentiesDischarge = pointofworkDoesTwoTwentiesDischarge;
    }

    public Boolean getPointofworkIsDischargingToBuffer() {
        return this.pointofworkIsDischargingToBuffer;
    }

    public void setPointofworkIsDischargingToBuffer(Boolean pointofworkIsDischargingToBuffer) {
        this.pointofworkIsDischargingToBuffer = pointofworkIsDischargingToBuffer;
    }

    public Boolean getPointofworkIsRoadPow() {
        return this.pointofworkIsRoadPow;
    }

    public void setPointofworkIsRoadPow(Boolean pointofworkIsRoadPow) {
        this.pointofworkIsRoadPow = pointofworkIsRoadPow;
    }

    public String getPointofworkLaneName() {
        return this.pointofworkLaneName;
    }

    public void setPointofworkLaneName(String pointofworkLaneName) {
        this.pointofworkLaneName = pointofworkLaneName;
    }

    public Long getPointofworkQuayOrder() {
        return this.pointofworkQuayOrder;
    }

    public void setPointofworkQuayOrder(Long pointofworkQuayOrder) {
        this.pointofworkQuayOrder = pointofworkQuayOrder;
    }

    public Long getPointofworkLoadRequest() {
        return this.pointofworkLoadRequest;
    }

    public void setPointofworkLoadRequest(Long pointofworkLoadRequest) {
        this.pointofworkLoadRequest = pointofworkLoadRequest;
    }

    public Long getPointofworkLoadAck() {
        return this.pointofworkLoadAck;
    }

    public void setPointofworkLoadAck(Long pointofworkLoadAck) {
        this.pointofworkLoadAck = pointofworkLoadAck;
    }

    public Long getPointofworkYardCrane1Id() {
        return this.pointofworkYardCrane1Id;
    }

    public void setPointofworkYardCrane1Id(Long pointofworkYardCrane1Id) {
        this.pointofworkYardCrane1Id = pointofworkYardCrane1Id;
    }

    public Long getPointofworkYardCrane2Id() {
        return this.pointofworkYardCrane2Id;
    }

    public void setPointofworkYardCrane2Id(Long pointofworkYardCrane2Id) {
        this.pointofworkYardCrane2Id = pointofworkYardCrane2Id;
    }

    public Long getPointofworkYardCrane3Id() {
        return this.pointofworkYardCrane3Id;
    }

    public void setPointofworkYardCrane3Id(Long pointofworkYardCrane3Id) {
        this.pointofworkYardCrane3Id = pointofworkYardCrane3Id;
    }

    public Long getPointofworkYardCrane4Id() {
        return this.pointofworkYardCrane4Id;
    }

    public void setPointofworkYardCrane4Id(Long pointofworkYardCrane4Id) {
        this.pointofworkYardCrane4Id = pointofworkYardCrane4Id;
    }

    public Long getPointofworkYardCrane5Id() {
        return this.pointofworkYardCrane5Id;
    }

    public void setPointofworkYardCrane5Id(Long pointofworkYardCrane5Id) {
        this.pointofworkYardCrane5Id = pointofworkYardCrane5Id;
    }

    public Long getPointofworkMaximumPms() {
        return this.pointofworkMaximumPms;
    }

    public void setPointofworkMaximumPms(Long pointofworkMaximumPms) {
        this.pointofworkMaximumPms = pointofworkMaximumPms;
    }

    public Long getPointofworkPushRate() {
        return this.pointofworkPushRate;
    }

    public void setPointofworkPushRate(Long pointofworkPushRate) {
        this.pointofworkPushRate = pointofworkPushRate;
    }

    public Boolean getPointofworkAutoPushRate() {
        return this.pointofworkAutoPushRate;
    }

    public void setPointofworkAutoPushRate(Boolean pointofworkAutoPushRate) {
        this.pointofworkAutoPushRate = pointofworkAutoPushRate;
    }

    public Long getPointofworkRelativePriority() {
        return this.pointofworkRelativePriority;
    }

    public void setPointofworkRelativePriority(Long pointofworkRelativePriority) {
        this.pointofworkRelativePriority = pointofworkRelativePriority;
    }

    public String getPointofworkVoiceChannel() {
        return this.pointofworkVoiceChannel;
    }

    public void setPointofworkVoiceChannel(String pointofworkVoiceChannel) {
        this.pointofworkVoiceChannel = pointofworkVoiceChannel;
    }

    public Date getPointofworkLastQuayOrRailAccess() {
        return this.pointofworkLastQuayOrRailAccess;
    }

    public void setPointofworkLastQuayOrRailAccess(Date pointofworkLastQuayOrRailAccess) {
        this.pointofworkLastQuayOrRailAccess = pointofworkLastQuayOrRailAccess;
    }

    public String getPointofworkQcVesselVisitReference() {
        return this.pointofworkQcVesselVisitReference;
    }

    public void setPointofworkQcVesselVisitReference(String pointofworkQcVesselVisitReference) {
        this.pointofworkQcVesselVisitReference = pointofworkQcVesselVisitReference;
    }

    public Long getPointofworkQcLastMoveType() {
        return this.pointofworkQcLastMoveType;
    }

    public void setPointofworkQcLastMoveType(Long pointofworkQcLastMoveType) {
        this.pointofworkQcLastMoveType = pointofworkQcLastMoveType;
    }

    public WiMoveKindEnum getPointofworkQcLastMoveTypeEnum() {
        return this.pointofworkQcLastMoveTypeEnum;
    }

    public void setPointofworkQcLastMoveTypeEnum(WiMoveKindEnum pointofworkQcLastMoveTypeEnum) {
        this.pointofworkQcLastMoveTypeEnum = pointofworkQcLastMoveTypeEnum;
    }

    public String getPointofworkQcVesselBay() {
        return this.pointofworkQcVesselBay;
    }

    public void setPointofworkQcVesselBay(String pointofworkQcVesselBay) {
        this.pointofworkQcVesselBay = pointofworkQcVesselBay;
    }

    public Long getPointofworkRoadObjective() {
        return this.pointofworkRoadObjective;
    }

    public void setPointofworkRoadObjective(Long pointofworkRoadObjective) {
        this.pointofworkRoadObjective = pointofworkRoadObjective;
    }

    public String getPointofworkBufferName() {
        return this.pointofworkBufferName;
    }

    public void setPointofworkBufferName(String pointofworkBufferName) {
        this.pointofworkBufferName = pointofworkBufferName;
    }

    public Float getPointofworkCraneReachLeft() {
        return this.pointofworkCraneReachLeft;
    }

    public void setPointofworkCraneReachLeft(Float pointofworkCraneReachLeft) {
        this.pointofworkCraneReachLeft = pointofworkCraneReachLeft;
    }

    public Float getPointofworkCraneReachRight() {
        return this.pointofworkCraneReachRight;
    }

    public void setPointofworkCraneReachRight(Float pointofworkCraneReachRight) {
        this.pointofworkCraneReachRight = pointofworkCraneReachRight;
    }

    public Boolean getPointofworkDoNotMakeLate() {
        return this.pointofworkDoNotMakeLate;
    }

    public void setPointofworkDoNotMakeLate(Boolean pointofworkDoNotMakeLate) {
        this.pointofworkDoNotMakeLate = pointofworkDoNotMakeLate;
    }

    public String getPointofworkTerminalCode() {
        return this.pointofworkTerminalCode;
    }

    public void setPointofworkTerminalCode(String pointofworkTerminalCode) {
        this.pointofworkTerminalCode = pointofworkTerminalCode;
    }

    public Float getPointofworkTwinProduction() {
        return this.pointofworkTwinProduction;
    }

    public void setPointofworkTwinProduction(Float pointofworkTwinProduction) {
        this.pointofworkTwinProduction = pointofworkTwinProduction;
    }

    public Date getPointofworkLastDispatchTime() {
        return this.pointofworkLastDispatchTime;
    }

    public void setPointofworkLastDispatchTime(Date pointofworkLastDispatchTime) {
        this.pointofworkLastDispatchTime = pointofworkLastDispatchTime;
    }

    public Long getPointofworkMinimumPms() {
        return this.pointofworkMinimumPms;
    }

    public void setPointofworkMinimumPms(Long pointofworkMinimumPms) {
        this.pointofworkMinimumPms = pointofworkMinimumPms;
    }

    public Long getPointofworkJobListPriority() {
        return this.pointofworkJobListPriority;
    }

    public void setPointofworkJobListPriority(Long pointofworkJobListPriority) {
        this.pointofworkJobListPriority = pointofworkJobListPriority;
    }

    public String getPointofworkCheWaitingAtPow() {
        return this.pointofworkCheWaitingAtPow;
    }

    public void setPointofworkCheWaitingAtPow(String pointofworkCheWaitingAtPow) {
        this.pointofworkCheWaitingAtPow = pointofworkCheWaitingAtPow;
    }

    public Float getPointofworkTandemProduction() {
        return this.pointofworkTandemProduction;
    }

    public void setPointofworkTandemProduction(Float pointofworkTandemProduction) {
        this.pointofworkTandemProduction = pointofworkTandemProduction;
    }

    public Float getPointofworkQuadProduction() {
        return this.pointofworkQuadProduction;
    }

    public void setPointofworkQuadProduction(Float pointofworkQuadProduction) {
        this.pointofworkQuadProduction = pointofworkQuadProduction;
    }

    public String getPointofworkPowDriveDirection() {
        return this.pointofworkPowDriveDirection;
    }

    public void setPointofworkPowDriveDirection(String pointofworkPowDriveDirection) {
        this.pointofworkPowDriveDirection = pointofworkPowDriveDirection;
    }

    public LifeCycleStateEnum getPointofworkLifeCycleState() {
        return this.pointofworkLifeCycleState;
    }

    public void setPointofworkLifeCycleState(LifeCycleStateEnum pointofworkLifeCycleState) {
        this.pointofworkLifeCycleState = pointofworkLifeCycleState;
    }

    public PowUncompletedWorkSortEnum getPointofworkUncompletedWorkSort() {
        return this.pointofworkUncompletedWorkSort;
    }

    public void setPointofworkUncompletedWorkSort(PowUncompletedWorkSortEnum pointofworkUncompletedWorkSort) {
        this.pointofworkUncompletedWorkSort = pointofworkUncompletedWorkSort;
    }

    public Boolean getPointofworkHasActiveAlarm() {
        return this.pointofworkHasActiveAlarm;
    }

    public void setPointofworkHasActiveAlarm(Boolean pointofworkHasActiveAlarm) {
        this.pointofworkHasActiveAlarm = pointofworkHasActiveAlarm;
    }

    public Yard getPointofworkYard() {
        return this.pointofworkYard;
    }

    public void setPointofworkYard(Yard pointofworkYard) {
        this.pointofworkYard = pointofworkYard;
    }

    public ChePool getPointofworkOwnerPool() {
        return this.pointofworkOwnerPool;
    }

    public void setPointofworkOwnerPool(ChePool pointofworkOwnerPool) {
        this.pointofworkOwnerPool = pointofworkOwnerPool;
    }

    public CarrierVisit getPointofworkVesselVisit() {
        return this.pointofworkVesselVisit;
    }

    public void setPointofworkVesselVisit(CarrierVisit pointofworkVesselVisit) {
        this.pointofworkVesselVisit = pointofworkVesselVisit;
    }

    public Che getPointofworkYardCrane1() {
        return this.pointofworkYardCrane1;
    }

    public void setPointofworkYardCrane1(Che pointofworkYardCrane1) {
        this.pointofworkYardCrane1 = pointofworkYardCrane1;
    }

    public Che getPointofworkYardCrane2() {
        return this.pointofworkYardCrane2;
    }

    public void setPointofworkYardCrane2(Che pointofworkYardCrane2) {
        this.pointofworkYardCrane2 = pointofworkYardCrane2;
    }

    public Che getPointofworkYardCrane3() {
        return this.pointofworkYardCrane3;
    }

    public void setPointofworkYardCrane3(Che pointofworkYardCrane3) {
        this.pointofworkYardCrane3 = pointofworkYardCrane3;
    }

    public Che getPointofworkYardCrane4() {
        return this.pointofworkYardCrane4;
    }

    public void setPointofworkYardCrane4(Che pointofworkYardCrane4) {
        this.pointofworkYardCrane4 = pointofworkYardCrane4;
    }

    public Che getPointofworkYardCrane5() {
        return this.pointofworkYardCrane5;
    }

    public void setPointofworkYardCrane5(Che pointofworkYardCrane5) {
        this.pointofworkYardCrane5 = pointofworkYardCrane5;
    }

    public CarrierVisit getPointofworkQcVesselVisit() {
        return this.pointofworkQcVesselVisit;
    }

    public void setPointofworkQcVesselVisit(CarrierVisit pointofworkQcVesselVisit) {
        this.pointofworkQcVesselVisit = pointofworkQcVesselVisit;
    }

    public List getPointofworkAlarms() {
        return this.pointofworkAlarms;
    }

    public void setPointofworkAlarms(List pointofworkAlarms) {
        this.pointofworkAlarms = pointofworkAlarms;
    }
}
