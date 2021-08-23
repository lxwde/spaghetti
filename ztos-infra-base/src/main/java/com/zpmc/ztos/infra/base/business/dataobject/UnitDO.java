package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.business.enums.argo.*;
import com.zpmc.ztos.infra.base.business.enums.inventory.*;
import com.zpmc.ztos.infra.base.business.equipments.EqBaseOrderItem;
import com.zpmc.ztos.infra.base.business.equipments.EquipGrade;
import com.zpmc.ztos.infra.base.business.equipments.Equipment;
import com.zpmc.ztos.infra.base.business.equipments.EquipmentState;
import com.zpmc.ztos.infra.base.business.inventory.*;
import com.zpmc.ztos.infra.base.business.model.CarrierVisit;
import com.zpmc.ztos.infra.base.business.model.GoodsBase;
import com.zpmc.ztos.infra.base.business.model.ScopedBizUnit;
import com.zpmc.ztos.infra.base.business.model.SpecialStow;
import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;
import com.zpmc.ztos.infra.base.common.scopes.Complex;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

public class UnitDO extends DatabaseEntity implements Serializable {

    private Long unitGkey;
    private String unitId;
    private String unitForeignHostKey;
    private UnitVisitStateEnum unitVisitState;
    private Boolean unitNeedsReview;
    private Boolean unitArePlacardsMismatched;
    private Date unitCreateTime;
    private UnitCategoryEnum unitCategory;
    private FreightKindEnum unitFreightKind;
    private DrayStatusEnum unitDrayStatus;
    private VslDeckRqmntEnum unitDeckRqmnt;
    private Boolean unitRequiresPower;
    private Boolean unitIsPowered;
    private Boolean unitWantPowered;
    private Date unitPowerRqstTime;
    private Boolean unitIsAlarmOn;
    private Boolean unitIsOog;
    private Long unitOogBackCm;
    private Long unitOogFrontCm;
    private Long unitOogLeftCm;
    private Long unitOogRightCm;
    private Long unitOogTopCm;
    private Boolean unitValidateReleaseGroup;
    private Boolean unitIsTransferableRelease;
    private Double unitGoodsAndCtrWtKg;
    private Double unitGoodsAndCtrWtKgAdvised;
    private Double unitGoodsAndCtrWtKgGateMeasured;
    private Double unitGoodsAndCtrWtKgYardMeasured;
    private Double unitGoodsAndCtrWtKgQcMeasured;
    private Double unitGoodsAndCtrWtKgVerfiedGross;
    private GrossWeightSourceEnum unitGrossWeightSource;
    private String unitVgmEntity;
    private Date unitVgmVerifiedDate;
    private Boolean unitIgnorePayloadWeights;
    private Boolean unitIgnorePayloadHeights;
    private Boolean unitIsStowplanPosted;
    private String unitSealNbr1;
    private String unitSealNbr2;
    private String unitSealNbr3;
    private String unitSealNbr4;
    private Boolean unitIsCtrSealed;
    private Boolean unitIsBundle;
    private Date unitImportDeliveryOrderExpiryDate;
    private Date unitTimeDenormalizeCalc;
    private Date unitTimeLastStateChange;
    private Boolean unitStoppedVessel;
    private Boolean unitStoppedRail;
    private Boolean unitStoppedRoad;
    private String unitImpedimentVessel;
    private String unitImpedimentRail;
    private String unitImpedimentRoad;
    private String unitRemark;
    private String unitWayBillNbr;
    private Date unitWayBillDate;
    private String unitExportReleaseNbr;
    private Date unitExportReleaseDate;
    private String unitFlexString01;
    private String unitFlexString02;
    private String unitFlexString03;
    private String unitFlexString04;
    private String unitFlexString05;
    private String unitFlexString06;
    private String unitFlexString07;
    private String unitFlexString08;
    private String unitFlexString09;
    private String unitFlexString10;
    private String unitFlexString11;
    private String unitFlexString12;
    private String unitFlexString13;
    private String unitFlexString14;
    private String unitFlexString15;
    private Long unitTouchCtr;
    private InbondEnum unitInbond;
    private ExamEnum unitExam;
    private String unitAcryEquipIds;
    private String unitCustomsId;
    private Date unitChanged;
    private Double unitCargoQuantity;
    private ServiceQuantityUnitEnum unitCargoQuantityUnit;
    private EqUnitRoleEnum unitEqRole;
    private EqDamageSeverityEnum unitDamageSeverity;
    private String unitSparcsDamageCode;
    private String unitBadNbr;
    private Boolean unitIsFolded;
    private Boolean unitIsReserved;
    private PlacardedEnum unitPlacarded;
    private CarrierVisit unitDeclaredIbCv;
    private Complex unitComplex;
    private GoodsBase unitGoods;
    private SpecialStow unitSpecialStow;
    private SpecialStow unitSpecialStow2;
    private SpecialStow unitSpecialStow3;
    private ScopedBizUnit unitLineOperator;
    private UnitFacilityVisit unitActiveUfv;
//    private ImportDeliveryOrder unitImportDeliveryOrder;
    private ScopedBizUnit unitAgent1;
    private ScopedBizUnit unitAgent2;
    private Unit unitRelatedUnit;
    private UnitCombo unitCombo;
    private Equipment unitEquipment;
    private Unit unitCarriageUnit;
    private EquipmentState unitEquipmentState;
    private UnitEquipDamages unitDamages;
//    private EquipCondition unitConditionID;
    private EqBaseOrderItem unitArrivalOrderItem;
    private EqBaseOrderItem unitDepartureOrderItem;
//    private MnrStatus unitMnrStatus;
    private EquipGrade unitGradeID;
    private Set unitUfvSet;
    private Set unitReeferRecordSet;
    private Set unitObservedPlacards;
    private Routing unitRouting;

    public Serializable getPrimaryKey() {
        return this.getUnitGkey();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        Long id = this.getUnitGkey();
        if (id == null) {
            return false;
        }
        if (!(other instanceof UnitDO)) {
            return false;
        }
        UnitDO that = (UnitDO)other;
        return ((Object)id).equals(that.getUnitGkey());
    }

    public int hashCode() {
        Long id = this.getUnitGkey();
        return id == null ? System.identityHashCode(this) : ((Object)id).hashCode();
    }

    public Long getUnitGkey() {
        return this.unitGkey;
    }

    protected void setUnitGkey(Long unitGkey) {
        this.unitGkey = unitGkey;
    }

    public String getUnitId() {
        return this.unitId;
    }

    protected void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getUnitForeignHostKey() {
        return this.unitForeignHostKey;
    }

    protected void setUnitForeignHostKey(String unitForeignHostKey) {
        this.unitForeignHostKey = unitForeignHostKey;
    }

    public UnitVisitStateEnum getUnitVisitState() {
        return this.unitVisitState;
    }

    protected void setUnitVisitState(UnitVisitStateEnum unitVisitState) {
        this.unitVisitState = unitVisitState;
    }

    public Boolean getUnitNeedsReview() {
        return this.unitNeedsReview;
    }

    protected void setUnitNeedsReview(Boolean unitNeedsReview) {
        this.unitNeedsReview = unitNeedsReview;
    }

    public Boolean getUnitArePlacardsMismatched() {
        return this.unitArePlacardsMismatched;
    }

    protected void setUnitArePlacardsMismatched(Boolean unitArePlacardsMismatched) {
        this.unitArePlacardsMismatched = unitArePlacardsMismatched;
    }

    public Date getUnitCreateTime() {
        return this.unitCreateTime;
    }

    protected void setUnitCreateTime(Date unitCreateTime) {
        this.unitCreateTime = unitCreateTime;
    }

    public UnitCategoryEnum getUnitCategory() {
        return this.unitCategory;
    }

    public void setUnitCategory(UnitCategoryEnum unitCategory) {
        this.unitCategory = unitCategory;
    }

    public FreightKindEnum getUnitFreightKind() {
        return this.unitFreightKind;
    }

    protected void setUnitFreightKind(FreightKindEnum unitFreightKind) {
        this.unitFreightKind = unitFreightKind;
    }

    public DrayStatusEnum getUnitDrayStatus() {
        return this.unitDrayStatus;
    }

    public void setUnitDrayStatus(DrayStatusEnum unitDrayStatus) {
        this.unitDrayStatus = unitDrayStatus;
    }

    public VslDeckRqmntEnum getUnitDeckRqmnt() {
        return this.unitDeckRqmnt;
    }

    protected void setUnitDeckRqmnt(VslDeckRqmntEnum unitDeckRqmnt) {
        this.unitDeckRqmnt = unitDeckRqmnt;
    }

    public Boolean getUnitRequiresPower() {
        return this.unitRequiresPower;
    }

    protected void setUnitRequiresPower(Boolean unitRequiresPower) {
        this.unitRequiresPower = unitRequiresPower;
    }

    public Boolean getUnitIsPowered() {
        return this.unitIsPowered;
    }

    protected void setUnitIsPowered(Boolean unitIsPowered) {
        this.unitIsPowered = unitIsPowered;
    }

    public Boolean getUnitWantPowered() {
        return this.unitWantPowered;
    }

    protected void setUnitWantPowered(Boolean unitWantPowered) {
        this.unitWantPowered = unitWantPowered;
    }

    public Date getUnitPowerRqstTime() {
        return this.unitPowerRqstTime;
    }

    protected void setUnitPowerRqstTime(Date unitPowerRqstTime) {
        this.unitPowerRqstTime = unitPowerRqstTime;
    }

    public Boolean getUnitIsAlarmOn() {
        return this.unitIsAlarmOn;
    }

    protected void setUnitIsAlarmOn(Boolean unitIsAlarmOn) {
        this.unitIsAlarmOn = unitIsAlarmOn;
    }

    public Boolean getUnitIsOog() {
        return this.unitIsOog;
    }

    protected void setUnitIsOog(Boolean unitIsOog) {
        this.unitIsOog = unitIsOog;
    }

    public Long getUnitOogBackCm() {
        return this.unitOogBackCm;
    }

    protected void setUnitOogBackCm(Long unitOogBackCm) {
        this.unitOogBackCm = unitOogBackCm;
    }

    public Long getUnitOogFrontCm() {
        return this.unitOogFrontCm;
    }

    protected void setUnitOogFrontCm(Long unitOogFrontCm) {
        this.unitOogFrontCm = unitOogFrontCm;
    }

    public Long getUnitOogLeftCm() {
        return this.unitOogLeftCm;
    }

    protected void setUnitOogLeftCm(Long unitOogLeftCm) {
        this.unitOogLeftCm = unitOogLeftCm;
    }

    public Long getUnitOogRightCm() {
        return this.unitOogRightCm;
    }

    protected void setUnitOogRightCm(Long unitOogRightCm) {
        this.unitOogRightCm = unitOogRightCm;
    }

    public Long getUnitOogTopCm() {
        return this.unitOogTopCm;
    }

    protected void setUnitOogTopCm(Long unitOogTopCm) {
        this.unitOogTopCm = unitOogTopCm;
    }

    public Boolean getUnitValidateReleaseGroup() {
        return this.unitValidateReleaseGroup;
    }

    protected void setUnitValidateReleaseGroup(Boolean unitValidateReleaseGroup) {
        this.unitValidateReleaseGroup = unitValidateReleaseGroup;
    }

    public Boolean getUnitIsTransferableRelease() {
        return this.unitIsTransferableRelease;
    }

    protected void setUnitIsTransferableRelease(Boolean unitIsTransferableRelease) {
        this.unitIsTransferableRelease = unitIsTransferableRelease;
    }

    public Double getUnitGoodsAndCtrWtKg() {
        return this.unitGoodsAndCtrWtKg;
    }

    protected void setUnitGoodsAndCtrWtKg(Double unitGoodsAndCtrWtKg) {
        this.unitGoodsAndCtrWtKg = unitGoodsAndCtrWtKg;
    }

    public Double getUnitGoodsAndCtrWtKgAdvised() {
        return this.unitGoodsAndCtrWtKgAdvised;
    }

    protected void setUnitGoodsAndCtrWtKgAdvised(Double unitGoodsAndCtrWtKgAdvised) {
        this.unitGoodsAndCtrWtKgAdvised = unitGoodsAndCtrWtKgAdvised;
    }

    public Double getUnitGoodsAndCtrWtKgGateMeasured() {
        return this.unitGoodsAndCtrWtKgGateMeasured;
    }

    protected void setUnitGoodsAndCtrWtKgGateMeasured(Double unitGoodsAndCtrWtKgGateMeasured) {
        this.unitGoodsAndCtrWtKgGateMeasured = unitGoodsAndCtrWtKgGateMeasured;
    }

    public Double getUnitGoodsAndCtrWtKgYardMeasured() {
        return this.unitGoodsAndCtrWtKgYardMeasured;
    }

    protected void setUnitGoodsAndCtrWtKgYardMeasured(Double unitGoodsAndCtrWtKgYardMeasured) {
        this.unitGoodsAndCtrWtKgYardMeasured = unitGoodsAndCtrWtKgYardMeasured;
    }

    public Double getUnitGoodsAndCtrWtKgQcMeasured() {
        return this.unitGoodsAndCtrWtKgQcMeasured;
    }

    protected void setUnitGoodsAndCtrWtKgQcMeasured(Double unitGoodsAndCtrWtKgQcMeasured) {
        this.unitGoodsAndCtrWtKgQcMeasured = unitGoodsAndCtrWtKgQcMeasured;
    }

    public Double getUnitGoodsAndCtrWtKgVerfiedGross() {
        return this.unitGoodsAndCtrWtKgVerfiedGross;
    }

    protected void setUnitGoodsAndCtrWtKgVerfiedGross(Double unitGoodsAndCtrWtKgVerfiedGross) {
        this.unitGoodsAndCtrWtKgVerfiedGross = unitGoodsAndCtrWtKgVerfiedGross;
    }

    public GrossWeightSourceEnum getUnitGrossWeightSource() {
        return this.unitGrossWeightSource;
    }

    protected void setUnitGrossWeightSource(GrossWeightSourceEnum unitGrossWeightSource) {
        this.unitGrossWeightSource = unitGrossWeightSource;
    }

    public String getUnitVgmEntity() {
        return this.unitVgmEntity;
    }

    protected void setUnitVgmEntity(String unitVgmEntity) {
        this.unitVgmEntity = unitVgmEntity;
    }

    public Date getUnitVgmVerifiedDate() {
        return this.unitVgmVerifiedDate;
    }

    protected void setUnitVgmVerifiedDate(Date unitVgmVerifiedDate) {
        this.unitVgmVerifiedDate = unitVgmVerifiedDate;
    }

    public Boolean getUnitIgnorePayloadWeights() {
        return this.unitIgnorePayloadWeights;
    }

    protected void setUnitIgnorePayloadWeights(Boolean unitIgnorePayloadWeights) {
        this.unitIgnorePayloadWeights = unitIgnorePayloadWeights;
    }

    public Boolean getUnitIgnorePayloadHeights() {
        return this.unitIgnorePayloadHeights;
    }

    protected void setUnitIgnorePayloadHeights(Boolean unitIgnorePayloadHeights) {
        this.unitIgnorePayloadHeights = unitIgnorePayloadHeights;
    }

    public Boolean getUnitIsStowplanPosted() {
        return this.unitIsStowplanPosted;
    }

    protected void setUnitIsStowplanPosted(Boolean unitIsStowplanPosted) {
        this.unitIsStowplanPosted = unitIsStowplanPosted;
    }

    public String getUnitSealNbr1() {
        return this.unitSealNbr1;
    }

    protected void setUnitSealNbr1(String unitSealNbr1) {
        this.unitSealNbr1 = unitSealNbr1;
    }

    public String getUnitSealNbr2() {
        return this.unitSealNbr2;
    }

    protected void setUnitSealNbr2(String unitSealNbr2) {
        this.unitSealNbr2 = unitSealNbr2;
    }

    public String getUnitSealNbr3() {
        return this.unitSealNbr3;
    }

    protected void setUnitSealNbr3(String unitSealNbr3) {
        this.unitSealNbr3 = unitSealNbr3;
    }

    public String getUnitSealNbr4() {
        return this.unitSealNbr4;
    }

    protected void setUnitSealNbr4(String unitSealNbr4) {
        this.unitSealNbr4 = unitSealNbr4;
    }

    public Boolean getUnitIsCtrSealed() {
        return this.unitIsCtrSealed;
    }

    protected void setUnitIsCtrSealed(Boolean unitIsCtrSealed) {
        this.unitIsCtrSealed = unitIsCtrSealed;
    }

    public Boolean getUnitIsBundle() {
        return this.unitIsBundle;
    }

    protected void setUnitIsBundle(Boolean unitIsBundle) {
        this.unitIsBundle = unitIsBundle;
    }

    public Date getUnitImportDeliveryOrderExpiryDate() {
        return this.unitImportDeliveryOrderExpiryDate;
    }

    protected void setUnitImportDeliveryOrderExpiryDate(Date unitImportDeliveryOrderExpiryDate) {
        this.unitImportDeliveryOrderExpiryDate = unitImportDeliveryOrderExpiryDate;
    }

    public Date getUnitTimeDenormalizeCalc() {
        return this.unitTimeDenormalizeCalc;
    }

    protected void setUnitTimeDenormalizeCalc(Date unitTimeDenormalizeCalc) {
        this.unitTimeDenormalizeCalc = unitTimeDenormalizeCalc;
    }

    public Date getUnitTimeLastStateChange() {
        return this.unitTimeLastStateChange;
    }

    protected void setUnitTimeLastStateChange(Date unitTimeLastStateChange) {
        this.unitTimeLastStateChange = unitTimeLastStateChange;
    }

    public Boolean getUnitStoppedVessel() {
        return this.unitStoppedVessel;
    }

    protected void setUnitStoppedVessel(Boolean unitStoppedVessel) {
        this.unitStoppedVessel = unitStoppedVessel;
    }

    public Boolean getUnitStoppedRail() {
        return this.unitStoppedRail;
    }

    protected void setUnitStoppedRail(Boolean unitStoppedRail) {
        this.unitStoppedRail = unitStoppedRail;
    }

    public Boolean getUnitStoppedRoad() {
        return this.unitStoppedRoad;
    }

    protected void setUnitStoppedRoad(Boolean unitStoppedRoad) {
        this.unitStoppedRoad = unitStoppedRoad;
    }

    public String getUnitImpedimentVessel() {
        return this.unitImpedimentVessel;
    }

    protected void setUnitImpedimentVessel(String unitImpedimentVessel) {
        this.unitImpedimentVessel = unitImpedimentVessel;
    }

    public String getUnitImpedimentRail() {
        return this.unitImpedimentRail;
    }

    protected void setUnitImpedimentRail(String unitImpedimentRail) {
        this.unitImpedimentRail = unitImpedimentRail;
    }

    public String getUnitImpedimentRoad() {
        return this.unitImpedimentRoad;
    }

    protected void setUnitImpedimentRoad(String unitImpedimentRoad) {
        this.unitImpedimentRoad = unitImpedimentRoad;
    }

    public String getUnitRemark() {
        return this.unitRemark;
    }

    protected void setUnitRemark(String unitRemark) {
        this.unitRemark = unitRemark;
    }

    public String getUnitWayBillNbr() {
        return this.unitWayBillNbr;
    }

    protected void setUnitWayBillNbr(String unitWayBillNbr) {
        this.unitWayBillNbr = unitWayBillNbr;
    }

    public Date getUnitWayBillDate() {
        return this.unitWayBillDate;
    }

    protected void setUnitWayBillDate(Date unitWayBillDate) {
        this.unitWayBillDate = unitWayBillDate;
    }

    public String getUnitExportReleaseNbr() {
        return this.unitExportReleaseNbr;
    }

    protected void setUnitExportReleaseNbr(String unitExportReleaseNbr) {
        this.unitExportReleaseNbr = unitExportReleaseNbr;
    }

    public Date getUnitExportReleaseDate() {
        return this.unitExportReleaseDate;
    }

    protected void setUnitExportReleaseDate(Date unitExportReleaseDate) {
        this.unitExportReleaseDate = unitExportReleaseDate;
    }

    public String getUnitFlexString01() {
        return this.unitFlexString01;
    }

    protected void setUnitFlexString01(String unitFlexString01) {
        this.unitFlexString01 = unitFlexString01;
    }

    public String getUnitFlexString02() {
        return this.unitFlexString02;
    }

    protected void setUnitFlexString02(String unitFlexString02) {
        this.unitFlexString02 = unitFlexString02;
    }

    public String getUnitFlexString03() {
        return this.unitFlexString03;
    }

    protected void setUnitFlexString03(String unitFlexString03) {
        this.unitFlexString03 = unitFlexString03;
    }

    public String getUnitFlexString04() {
        return this.unitFlexString04;
    }

    protected void setUnitFlexString04(String unitFlexString04) {
        this.unitFlexString04 = unitFlexString04;
    }

    public String getUnitFlexString05() {
        return this.unitFlexString05;
    }

    protected void setUnitFlexString05(String unitFlexString05) {
        this.unitFlexString05 = unitFlexString05;
    }

    public String getUnitFlexString06() {
        return this.unitFlexString06;
    }

    protected void setUnitFlexString06(String unitFlexString06) {
        this.unitFlexString06 = unitFlexString06;
    }

    public String getUnitFlexString07() {
        return this.unitFlexString07;
    }

    protected void setUnitFlexString07(String unitFlexString07) {
        this.unitFlexString07 = unitFlexString07;
    }

    public String getUnitFlexString08() {
        return this.unitFlexString08;
    }

    protected void setUnitFlexString08(String unitFlexString08) {
        this.unitFlexString08 = unitFlexString08;
    }

    public String getUnitFlexString09() {
        return this.unitFlexString09;
    }

    protected void setUnitFlexString09(String unitFlexString09) {
        this.unitFlexString09 = unitFlexString09;
    }

    public String getUnitFlexString10() {
        return this.unitFlexString10;
    }

    protected void setUnitFlexString10(String unitFlexString10) {
        this.unitFlexString10 = unitFlexString10;
    }

    public String getUnitFlexString11() {
        return this.unitFlexString11;
    }

    protected void setUnitFlexString11(String unitFlexString11) {
        this.unitFlexString11 = unitFlexString11;
    }

    public String getUnitFlexString12() {
        return this.unitFlexString12;
    }

    protected void setUnitFlexString12(String unitFlexString12) {
        this.unitFlexString12 = unitFlexString12;
    }

    public String getUnitFlexString13() {
        return this.unitFlexString13;
    }

    protected void setUnitFlexString13(String unitFlexString13) {
        this.unitFlexString13 = unitFlexString13;
    }

    public String getUnitFlexString14() {
        return this.unitFlexString14;
    }

    protected void setUnitFlexString14(String unitFlexString14) {
        this.unitFlexString14 = unitFlexString14;
    }

    public String getUnitFlexString15() {
        return this.unitFlexString15;
    }

    protected void setUnitFlexString15(String unitFlexString15) {
        this.unitFlexString15 = unitFlexString15;
    }

    public Long getUnitTouchCtr() {
        return this.unitTouchCtr;
    }

    protected void setUnitTouchCtr(Long unitTouchCtr) {
        this.unitTouchCtr = unitTouchCtr;
    }

    public InbondEnum getUnitInbond() {
        return this.unitInbond;
    }

    protected void setUnitInbond(InbondEnum unitInbond) {
        this.unitInbond = unitInbond;
    }

    public ExamEnum getUnitExam() {
        return this.unitExam;
    }

    protected void setUnitExam(ExamEnum unitExam) {
        this.unitExam = unitExam;
    }

    public String getUnitAcryEquipIds() {
        return this.unitAcryEquipIds;
    }

    protected void setUnitAcryEquipIds(String unitAcryEquipIds) {
        this.unitAcryEquipIds = unitAcryEquipIds;
    }

    public String getUnitCustomsId() {
        return this.unitCustomsId;
    }

    protected void setUnitCustomsId(String unitCustomsId) {
        this.unitCustomsId = unitCustomsId;
    }

    public Date getUnitChanged() {
        return this.unitChanged;
    }

    protected void setUnitChanged(Date unitChanged) {
        this.unitChanged = unitChanged;
    }

    public Double getUnitCargoQuantity() {
        return this.unitCargoQuantity;
    }

    protected void setUnitCargoQuantity(Double unitCargoQuantity) {
        this.unitCargoQuantity = unitCargoQuantity;
    }

    public ServiceQuantityUnitEnum getUnitCargoQuantityUnit() {
        return this.unitCargoQuantityUnit;
    }

    protected void setUnitCargoQuantityUnit(ServiceQuantityUnitEnum unitCargoQuantityUnit) {
        this.unitCargoQuantityUnit = unitCargoQuantityUnit;
    }

    public EqUnitRoleEnum getUnitEqRole() {
        return this.unitEqRole;
    }

    public void setUnitEqRole(EqUnitRoleEnum unitEqRole) {
        this.unitEqRole = unitEqRole;
    }

    public EqDamageSeverityEnum getUnitDamageSeverity() {
        return this.unitDamageSeverity;
    }

    protected void setUnitDamageSeverity(EqDamageSeverityEnum unitDamageSeverity) {
        this.unitDamageSeverity = unitDamageSeverity;
    }

    public String getUnitSparcsDamageCode() {
        return this.unitSparcsDamageCode;
    }

    protected void setUnitSparcsDamageCode(String unitSparcsDamageCode) {
        this.unitSparcsDamageCode = unitSparcsDamageCode;
    }

    public String getUnitBadNbr() {
        return this.unitBadNbr;
    }

    protected void setUnitBadNbr(String unitBadNbr) {
        this.unitBadNbr = unitBadNbr;
    }

    public Boolean getUnitIsFolded() {
        return this.unitIsFolded;
    }

    protected void setUnitIsFolded(Boolean unitIsFolded) {
        this.unitIsFolded = unitIsFolded;
    }

    public Boolean getUnitIsReserved() {
        return this.unitIsReserved;
    }

    protected void setUnitIsReserved(Boolean unitIsReserved) {
        this.unitIsReserved = unitIsReserved;
    }

    public PlacardedEnum getUnitPlacarded() {
        return this.unitPlacarded;
    }

    protected void setUnitPlacarded(PlacardedEnum unitPlacarded) {
        this.unitPlacarded = unitPlacarded;
    }

    public CarrierVisit getUnitDeclaredIbCv() {
        return this.unitDeclaredIbCv;
    }

    public void setUnitDeclaredIbCv(CarrierVisit unitDeclaredIbCv) {
        this.unitDeclaredIbCv = unitDeclaredIbCv;
    }

    public Complex getUnitComplex() {
        return this.unitComplex;
    }

    protected void setUnitComplex(Complex unitComplex) {
        this.unitComplex = unitComplex;
    }

    public GoodsBase getUnitGoods() {
        return this.unitGoods;
    }

    protected void setUnitGoods(GoodsBase unitGoods) {
        this.unitGoods = unitGoods;
    }

    public SpecialStow getUnitSpecialStow() {
        return this.unitSpecialStow;
    }

    protected void setUnitSpecialStow(SpecialStow unitSpecialStow) {
        this.unitSpecialStow = unitSpecialStow;
    }

    public SpecialStow getUnitSpecialStow2() {
        return this.unitSpecialStow2;
    }

    protected void setUnitSpecialStow2(SpecialStow unitSpecialStow2) {
        this.unitSpecialStow2 = unitSpecialStow2;
    }

    public SpecialStow getUnitSpecialStow3() {
        return this.unitSpecialStow3;
    }

    protected void setUnitSpecialStow3(SpecialStow unitSpecialStow3) {
        this.unitSpecialStow3 = unitSpecialStow3;
    }

    public ScopedBizUnit getUnitLineOperator() {
        return this.unitLineOperator;
    }

    public void setUnitLineOperator(ScopedBizUnit unitLineOperator) {
        this.unitLineOperator = unitLineOperator;
    }

    public UnitFacilityVisit getUnitActiveUfv() {
        return this.unitActiveUfv;
    }

    protected void setUnitActiveUfv(UnitFacilityVisit unitActiveUfv) {
        this.unitActiveUfv = unitActiveUfv;
    }

//    public ImportDeliveryOrder getUnitImportDeliveryOrder() {
//        return this.unitImportDeliveryOrder;
//    }
//
//    protected void setUnitImportDeliveryOrder(ImportDeliveryOrder unitImportDeliveryOrder) {
//        this.unitImportDeliveryOrder = unitImportDeliveryOrder;
//    }

    public ScopedBizUnit getUnitAgent1() {
        return this.unitAgent1;
    }

    protected void setUnitAgent1(ScopedBizUnit unitAgent1) {
        this.unitAgent1 = unitAgent1;
    }

    public ScopedBizUnit getUnitAgent2() {
        return this.unitAgent2;
    }

    protected void setUnitAgent2(ScopedBizUnit unitAgent2) {
        this.unitAgent2 = unitAgent2;
    }

    public Unit getUnitRelatedUnit() {
        return this.unitRelatedUnit;
    }

    public void setUnitRelatedUnit(Unit unitRelatedUnit) {
        this.unitRelatedUnit = unitRelatedUnit;
    }

    public UnitCombo getUnitCombo() {
        return this.unitCombo;
    }

    public void setUnitCombo(UnitCombo unitCombo) {
        this.unitCombo = unitCombo;
    }

    public Equipment getUnitEquipment() {
        return this.unitEquipment;
    }

    public void setUnitEquipment(Equipment unitEquipment) {
        this.unitEquipment = unitEquipment;
    }

    public Unit getUnitCarriageUnit() {
        return this.unitCarriageUnit;
    }

    public void setUnitCarriageUnit(Unit unitCarriageUnit) {
        this.unitCarriageUnit = unitCarriageUnit;
    }

    public EquipmentState getUnitEquipmentState() {
        return this.unitEquipmentState;
    }

    protected void setUnitEquipmentState(EquipmentState unitEquipmentState) {
        this.unitEquipmentState = unitEquipmentState;
    }

    public UnitEquipDamages getUnitDamages() {
        return this.unitDamages;
    }

    protected void setUnitDamages(UnitEquipDamages unitDamages) {
        this.unitDamages = unitDamages;
    }

//    public EquipCondition getUnitConditionID() {
//        return this.unitConditionID;
//    }
//
//    protected void setUnitConditionID(EquipCondition unitConditionID) {
//        this.unitConditionID = unitConditionID;
//    }

    public EqBaseOrderItem getUnitArrivalOrderItem() {
        return this.unitArrivalOrderItem;
    }

    protected void setUnitArrivalOrderItem(EqBaseOrderItem unitArrivalOrderItem) {
        this.unitArrivalOrderItem = unitArrivalOrderItem;
    }

    public EqBaseOrderItem getUnitDepartureOrderItem() {
        return this.unitDepartureOrderItem;
    }

    protected void setUnitDepartureOrderItem(EqBaseOrderItem unitDepartureOrderItem) {
        this.unitDepartureOrderItem = unitDepartureOrderItem;
    }

//    public MnrStatus getUnitMnrStatus() {
//        return this.unitMnrStatus;
//    }
//
//    protected void setUnitMnrStatus(MnrStatus unitMnrStatus) {
//        this.unitMnrStatus = unitMnrStatus;
//    }

    public EquipGrade getUnitGradeID() {
        return this.unitGradeID;
    }

    protected void setUnitGradeID(EquipGrade unitGradeID) {
        this.unitGradeID = unitGradeID;
    }

    public Set getUnitUfvSet() {
        return this.unitUfvSet;
    }

    public void setUnitUfvSet(Set unitUfvSet) {
        this.unitUfvSet = unitUfvSet;
    }

    public Set getUnitReeferRecordSet() {
        return this.unitReeferRecordSet;
    }

    public void setUnitReeferRecordSet(Set unitReeferRecordSet) {
        this.unitReeferRecordSet = unitReeferRecordSet;
    }

    public Set getUnitObservedPlacards() {
        return this.unitObservedPlacards;
    }

    protected void setUnitObservedPlacards(Set unitObservedPlacards) {
        this.unitObservedPlacards = unitObservedPlacards;
    }

    public Routing getUnitRouting() {
        return this.unitRouting;
    }

    public void setUnitRouting(Routing unitRouting) {
        this.unitRouting = unitRouting;
    }

}
