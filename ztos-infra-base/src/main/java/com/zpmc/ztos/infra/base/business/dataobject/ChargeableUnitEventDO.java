package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.business.enums.argo.BizRoleEnum;
import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;
import com.zpmc.ztos.infra.base.common.scopes.Facility;
import com.zpmc.ztos.infra.base.common.scopes.Operator;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

public class ChargeableUnitEventDO extends DatabaseEntity implements Serializable {
    private Long bexuGkey;
    private Long bexuBatchId;
    private String bexuEventType;
    private Long bexuSourceGkey;
    private Long bexuUfvGkey;
    private String bexuFacilityId;
    private String bexuComplexId;
    private String bexuEqId;
    private String bexuEqSubClass;
    private String bexuLineOperatorId;
    private String bexuFreightKind;
    private String bexuIsoCode;
    private String bexuIsoGroup;
    private String bexuIsoLength;
    private String bexuIsoHeight;
    private String bexuCategory;
    private Date bexuPaidThruDay;
    private String bexuDrayStatus;
    private Boolean bexuIsOog;
    private Boolean bexuIsRefrigerated;
    private Boolean bexuIsHazardous;
    private Boolean bexuIsBundle;
    private String bexuImdgClass;
    private String bexuFireCode;
    private String bexuFireCodeClass;
    private String bexuCommodityId;
    private String bexuSpecialStow;
    private String bexuPod1;
    private String bexuPol1;
    private String bexuBundleUnitId;
    private Long bexuBundleUfvGkey;
    private String bexuBlNbr;
    private String bexuGuaranteeParty;
    private Date bexuGuaranteeThruDay;
    private String bexuBookingNbr;
    private String bexuRestowType;
    private Long bexuUnitGkey;
    private String bexuOpl;
    private String bexuFinalDestination;
    private Date bexuUfvTimeIn;
    private Date bexuUfvTimeOut;
    private Double bexuTempRequiredC;
    private Date bexuTimeOfLoading;
    private String bexuConsigneeId;
    private String bexuShipperId;
    private Date bexuTimeFirstFreeDay;
    private Date bexuTimeDischargeComplete;
    private Double bexuCargoQuantity;
    private String bexuCargoQuantityUnit;
    private String bexuIbintendedLocType;
    private String bexuIbintendedId;
    private String bexuIbintendedVisitId;
    private String bexuIbintendedCarrierName;
    private String bexuIbintendedCallNbr;
    private Date bexuIbintendedCarrierETA;
    private Date bexuIbintendedCarrierATA;
    private Date bexuIbintendedCarrierATD;
    private String bexuIbintendedVesselType;
    private String bexuIbintendedCarrierLineId;
    private String bexuIbintendedServiceId;
    private String bexuIbintendedVesselClassId;
    private String bexuIbLocType;
    private String bexuIbId;
    private String bexuIbVisitId;
    private String bexuIbCarrierName;
    private String bexuIbCallNbr;
    private Date bexuIbCarrierETA;
    private Date bexuIbCarrierATA;
    private Date bexuIbCarrierATD;
    private String bexuIbVesselType;
    private String bexuIbCarrierLineId;
    private String bexuIbServiceId;
    private String bexuIbVesselClassId;
    private String bexuIbVesselLloydsId;
    private String bexuObintendedLocType;
    private String bexuObintendedId;
    private String bexuObintendedVisitId;
    private String bexuObintendedCarrierName;
    private String bexuObintendedCallNbr;
    private Date bexuObintendedCarrierETA;
    private Date bexuObintendedCarrierATA;
    private Date bexuObintendedCarrierATD;
    private String bexuObintendedVesselType;
    private String bexuObintendedCarrierLineId;
    private String bexuObintendedServiceId;
    private String bexuObintendedVesselClassId;
    private String bexuObLocType;
    private String bexuObId;
    private String bexuObVisitId;
    private String bexuObCarrierName;
    private String bexuObCallNbr;
    private Date bexuObCarrierETA;
    private Date bexuObCarrierATA;
    private Date bexuObCarrierATD;
    private String bexuObVesselType;
    private String bexuObCarrierLineId;
    private String bexuObServiceId;
    private String bexuObVesselClassId;
    private String bexuObVesselLloydsId;
    private Date bexuEventStartTime;
    private Date bexuEventEndTime;
    private Date bexuRuleStartDay;
    private Date bexuRuleEndDay;
    private String bexuPayeeCustomerId;
    private BizRoleEnum bexuPayeeRole;
    private Date bexuFirstAvailability;
    private String bexuQuayCheId;
    private String bexuFmPosLocType;
    private String bexuFmPosLocId;
    private String bexuToPosLocType;
    private String bexuToPosLocId;
    private String bexuServiceOrder;
    private String bexuRestowReason;
    private String bexuRestowAccount;
    private Long bexuRehandleCount;
    private Double bexuQuantity;
    private String bexuQuantityUnit;
    private String bexuFmPositionSlot;
    private String bexuFmPositionName;
    private String bexuToPositionSlot;
    private String bexuToPositionName;
    private Boolean bexuIsLocked;
    private String bexuStatus;
    private String bexuLastDraftInvNbr;
    private String bexuNotes;
    private Boolean bexuIsOverrideValue;
    private String bexuOverrideValueType;
    private Double bexuOverrideValue;
    private String bexuGuaranteeId;
    private Long bexuGuaranteeGkey;
    private String bexuGnteInvProcessingStatus;
    private String bexuFlexString01;
    private String bexuFlexString02;
    private String bexuFlexString03;
    private String bexuFlexString04;
    private String bexuFlexString05;
    private String bexuFlexString06;
    private String bexuFlexString07;
    private String bexuFlexString08;
    private String bexuFlexString09;
    private String bexuFlexString10;
    private String bexuFlexString11;
    private String bexuFlexString12;
    private String bexuFlexString13;
    private String bexuFlexString14;
    private String bexuFlexString15;
    private String bexuFlexString16;
    private String bexuFlexString17;
    private String bexuFlexString18;
    private String bexuFlexString19;
    private String bexuFlexString20;
    private String bexuFlexString21;
    private String bexuFlexString22;
    private String bexuFlexString23;
    private String bexuFlexString24;
    private String bexuFlexString25;
    private String bexuFlexString26;
    private String bexuFlexString27;
    private String bexuFlexString28;
    private String bexuFlexString29;
    private String bexuFlexString30;
    private String bexuFlexString31;
    private String bexuFlexString32;
    private String bexuFlexString33;
    private String bexuFlexString34;
    private String bexuFlexString35;
    private String bexuFlexString36;
    private String bexuFlexString37;
    private String bexuFlexString38;
    private String bexuFlexString39;
    private String bexuFlexString40;
    private Date bexuFlexDate01;
    private Date bexuFlexDate02;
    private Date bexuFlexDate03;
    private Date bexuFlexDate04;
    private Date bexuFlexDate05;
    private Long bexuFlexLong01;
    private Long bexuFlexLong02;
    private Long bexuFlexLong03;
    private Long bexuFlexLong04;
    private Long bexuFlexLong05;
    private Double bexuFlexDouble01;
    private Double bexuFlexDouble02;
    private Double bexuFlexDouble03;
    private Double bexuFlexDouble04;
    private Double bexuFlexDouble05;
    private String bexuEqOwnerId;
    private String bexuEqRole;
    private Double bexuVerifiedGrossMass;
    private String bexuVgmVerifierEntity;
    private Date bexuCreated;
    private String bexuCreator;
    private Date bexuChanged;
    private String bexuChanger;
    private Facility bexuFacility;
    private Operator bexuTerminalOperator;
    private Set bexuGuarantees;

    public Serializable getPrimaryKey() {
        return this.getBexuGkey();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        Long id = this.getBexuGkey();
        if (id == null) {
            return false;
        }
        if (!(other instanceof ChargeableUnitEventDO)) {
            return false;
        }
        ChargeableUnitEventDO that = (ChargeableUnitEventDO)other;
        return ((Object)id).equals(that.getBexuGkey());
    }

    public int hashCode() {
        Long id = this.getBexuGkey();
        return id == null ? System.identityHashCode(this) : ((Object)id).hashCode();
    }

    public Long getBexuGkey() {
        return this.bexuGkey;
    }

    public void setBexuGkey(Long bexuGkey) {
        this.bexuGkey = bexuGkey;
    }

    public Long getBexuBatchId() {
        return this.bexuBatchId;
    }

    public void setBexuBatchId(Long bexuBatchId) {
        this.bexuBatchId = bexuBatchId;
    }

    public String getBexuEventType() {
        return this.bexuEventType;
    }

    public void setBexuEventType(String bexuEventType) {
        this.bexuEventType = bexuEventType;
    }

    public Long getBexuSourceGkey() {
        return this.bexuSourceGkey;
    }

    public void setBexuSourceGkey(Long bexuSourceGkey) {
        this.bexuSourceGkey = bexuSourceGkey;
    }

    public Long getBexuUfvGkey() {
        return this.bexuUfvGkey;
    }

    public void setBexuUfvGkey(Long bexuUfvGkey) {
        this.bexuUfvGkey = bexuUfvGkey;
    }

    public String getBexuFacilityId() {
        return this.bexuFacilityId;
    }

    public void setBexuFacilityId(String bexuFacilityId) {
        this.bexuFacilityId = bexuFacilityId;
    }

    public String getBexuComplexId() {
        return this.bexuComplexId;
    }

    public void setBexuComplexId(String bexuComplexId) {
        this.bexuComplexId = bexuComplexId;
    }

    public String getBexuEqId() {
        return this.bexuEqId;
    }

    public void setBexuEqId(String bexuEqId) {
        this.bexuEqId = bexuEqId;
    }

    public String getBexuEqSubClass() {
        return this.bexuEqSubClass;
    }

    public void setBexuEqSubClass(String bexuEqSubClass) {
        this.bexuEqSubClass = bexuEqSubClass;
    }

    public String getBexuLineOperatorId() {
        return this.bexuLineOperatorId;
    }

    public void setBexuLineOperatorId(String bexuLineOperatorId) {
        this.bexuLineOperatorId = bexuLineOperatorId;
    }

    public String getBexuFreightKind() {
        return this.bexuFreightKind;
    }

    public void setBexuFreightKind(String bexuFreightKind) {
        this.bexuFreightKind = bexuFreightKind;
    }

    public String getBexuIsoCode() {
        return this.bexuIsoCode;
    }

    public void setBexuIsoCode(String bexuIsoCode) {
        this.bexuIsoCode = bexuIsoCode;
    }

    public String getBexuIsoGroup() {
        return this.bexuIsoGroup;
    }

    public void setBexuIsoGroup(String bexuIsoGroup) {
        this.bexuIsoGroup = bexuIsoGroup;
    }

    public String getBexuIsoLength() {
        return this.bexuIsoLength;
    }

    public void setBexuIsoLength(String bexuIsoLength) {
        this.bexuIsoLength = bexuIsoLength;
    }

    public String getBexuIsoHeight() {
        return this.bexuIsoHeight;
    }

    public void setBexuIsoHeight(String bexuIsoHeight) {
        this.bexuIsoHeight = bexuIsoHeight;
    }

    public String getBexuCategory() {
        return this.bexuCategory;
    }

    public void setBexuCategory(String bexuCategory) {
        this.bexuCategory = bexuCategory;
    }

    public Date getBexuPaidThruDay() {
        return this.bexuPaidThruDay;
    }

    public void setBexuPaidThruDay(Date bexuPaidThruDay) {
        this.bexuPaidThruDay = bexuPaidThruDay;
    }

    public String getBexuDrayStatus() {
        return this.bexuDrayStatus;
    }

    public void setBexuDrayStatus(String bexuDrayStatus) {
        this.bexuDrayStatus = bexuDrayStatus;
    }

    public Boolean getBexuIsOog() {
        return this.bexuIsOog;
    }

    public void setBexuIsOog(Boolean bexuIsOog) {
        this.bexuIsOog = bexuIsOog;
    }

    public Boolean getBexuIsRefrigerated() {
        return this.bexuIsRefrigerated;
    }

    public void setBexuIsRefrigerated(Boolean bexuIsRefrigerated) {
        this.bexuIsRefrigerated = bexuIsRefrigerated;
    }

    public Boolean getBexuIsHazardous() {
        return this.bexuIsHazardous;
    }

    public void setBexuIsHazardous(Boolean bexuIsHazardous) {
        this.bexuIsHazardous = bexuIsHazardous;
    }

    public Boolean getBexuIsBundle() {
        return this.bexuIsBundle;
    }

    public void setBexuIsBundle(Boolean bexuIsBundle) {
        this.bexuIsBundle = bexuIsBundle;
    }

    public String getBexuImdgClass() {
        return this.bexuImdgClass;
    }

    public void setBexuImdgClass(String bexuImdgClass) {
        this.bexuImdgClass = bexuImdgClass;
    }

    public String getBexuFireCode() {
        return this.bexuFireCode;
    }

    public void setBexuFireCode(String bexuFireCode) {
        this.bexuFireCode = bexuFireCode;
    }

    public String getBexuFireCodeClass() {
        return this.bexuFireCodeClass;
    }

    public void setBexuFireCodeClass(String bexuFireCodeClass) {
        this.bexuFireCodeClass = bexuFireCodeClass;
    }

    public String getBexuCommodityId() {
        return this.bexuCommodityId;
    }

    public void setBexuCommodityId(String bexuCommodityId) {
        this.bexuCommodityId = bexuCommodityId;
    }

    public String getBexuSpecialStow() {
        return this.bexuSpecialStow;
    }

    public void setBexuSpecialStow(String bexuSpecialStow) {
        this.bexuSpecialStow = bexuSpecialStow;
    }

    public String getBexuPod1() {
        return this.bexuPod1;
    }

    public void setBexuPod1(String bexuPod1) {
        this.bexuPod1 = bexuPod1;
    }

    public String getBexuPol1() {
        return this.bexuPol1;
    }

    public void setBexuPol1(String bexuPol1) {
        this.bexuPol1 = bexuPol1;
    }

    public String getBexuBundleUnitId() {
        return this.bexuBundleUnitId;
    }

    public void setBexuBundleUnitId(String bexuBundleUnitId) {
        this.bexuBundleUnitId = bexuBundleUnitId;
    }

    public Long getBexuBundleUfvGkey() {
        return this.bexuBundleUfvGkey;
    }

    public void setBexuBundleUfvGkey(Long bexuBundleUfvGkey) {
        this.bexuBundleUfvGkey = bexuBundleUfvGkey;
    }

    public String getBexuBlNbr() {
        return this.bexuBlNbr;
    }

    public void setBexuBlNbr(String bexuBlNbr) {
        this.bexuBlNbr = bexuBlNbr;
    }

    public String getBexuGuaranteeParty() {
        return this.bexuGuaranteeParty;
    }

    public void setBexuGuaranteeParty(String bexuGuaranteeParty) {
        this.bexuGuaranteeParty = bexuGuaranteeParty;
    }

    public Date getBexuGuaranteeThruDay() {
        return this.bexuGuaranteeThruDay;
    }

    public void setBexuGuaranteeThruDay(Date bexuGuaranteeThruDay) {
        this.bexuGuaranteeThruDay = bexuGuaranteeThruDay;
    }

    public String getBexuBookingNbr() {
        return this.bexuBookingNbr;
    }

    public void setBexuBookingNbr(String bexuBookingNbr) {
        this.bexuBookingNbr = bexuBookingNbr;
    }

    public String getBexuRestowType() {
        return this.bexuRestowType;
    }

    public void setBexuRestowType(String bexuRestowType) {
        this.bexuRestowType = bexuRestowType;
    }

    public Long getBexuUnitGkey() {
        return this.bexuUnitGkey;
    }

    public void setBexuUnitGkey(Long bexuUnitGkey) {
        this.bexuUnitGkey = bexuUnitGkey;
    }

    public String getBexuOpl() {
        return this.bexuOpl;
    }

    public void setBexuOpl(String bexuOpl) {
        this.bexuOpl = bexuOpl;
    }

    public String getBexuFinalDestination() {
        return this.bexuFinalDestination;
    }

    public void setBexuFinalDestination(String bexuFinalDestination) {
        this.bexuFinalDestination = bexuFinalDestination;
    }

    public Date getBexuUfvTimeIn() {
        return this.bexuUfvTimeIn;
    }

    public void setBexuUfvTimeIn(Date bexuUfvTimeIn) {
        this.bexuUfvTimeIn = bexuUfvTimeIn;
    }

    public Date getBexuUfvTimeOut() {
        return this.bexuUfvTimeOut;
    }

    public void setBexuUfvTimeOut(Date bexuUfvTimeOut) {
        this.bexuUfvTimeOut = bexuUfvTimeOut;
    }

    public Double getBexuTempRequiredC() {
        return this.bexuTempRequiredC;
    }

    public void setBexuTempRequiredC(Double bexuTempRequiredC) {
        this.bexuTempRequiredC = bexuTempRequiredC;
    }

    public Date getBexuTimeOfLoading() {
        return this.bexuTimeOfLoading;
    }

    public void setBexuTimeOfLoading(Date bexuTimeOfLoading) {
        this.bexuTimeOfLoading = bexuTimeOfLoading;
    }

    public String getBexuConsigneeId() {
        return this.bexuConsigneeId;
    }

    public void setBexuConsigneeId(String bexuConsigneeId) {
        this.bexuConsigneeId = bexuConsigneeId;
    }

    public String getBexuShipperId() {
        return this.bexuShipperId;
    }

    public void setBexuShipperId(String bexuShipperId) {
        this.bexuShipperId = bexuShipperId;
    }

    public Date getBexuTimeFirstFreeDay() {
        return this.bexuTimeFirstFreeDay;
    }

    public void setBexuTimeFirstFreeDay(Date bexuTimeFirstFreeDay) {
        this.bexuTimeFirstFreeDay = bexuTimeFirstFreeDay;
    }

    public Date getBexuTimeDischargeComplete() {
        return this.bexuTimeDischargeComplete;
    }

    public void setBexuTimeDischargeComplete(Date bexuTimeDischargeComplete) {
        this.bexuTimeDischargeComplete = bexuTimeDischargeComplete;
    }

    public Double getBexuCargoQuantity() {
        return this.bexuCargoQuantity;
    }

    public void setBexuCargoQuantity(Double bexuCargoQuantity) {
        this.bexuCargoQuantity = bexuCargoQuantity;
    }

    public String getBexuCargoQuantityUnit() {
        return this.bexuCargoQuantityUnit;
    }

    public void setBexuCargoQuantityUnit(String bexuCargoQuantityUnit) {
        this.bexuCargoQuantityUnit = bexuCargoQuantityUnit;
    }

    public String getBexuIbintendedLocType() {
        return this.bexuIbintendedLocType;
    }

    public void setBexuIbintendedLocType(String bexuIbintendedLocType) {
        this.bexuIbintendedLocType = bexuIbintendedLocType;
    }

    public String getBexuIbintendedId() {
        return this.bexuIbintendedId;
    }

    public void setBexuIbintendedId(String bexuIbintendedId) {
        this.bexuIbintendedId = bexuIbintendedId;
    }

    public String getBexuIbintendedVisitId() {
        return this.bexuIbintendedVisitId;
    }

    public void setBexuIbintendedVisitId(String bexuIbintendedVisitId) {
        this.bexuIbintendedVisitId = bexuIbintendedVisitId;
    }

    public String getBexuIbintendedCarrierName() {
        return this.bexuIbintendedCarrierName;
    }

    public void setBexuIbintendedCarrierName(String bexuIbintendedCarrierName) {
        this.bexuIbintendedCarrierName = bexuIbintendedCarrierName;
    }

    public String getBexuIbintendedCallNbr() {
        return this.bexuIbintendedCallNbr;
    }

    public void setBexuIbintendedCallNbr(String bexuIbintendedCallNbr) {
        this.bexuIbintendedCallNbr = bexuIbintendedCallNbr;
    }

    public Date getBexuIbintendedCarrierETA() {
        return this.bexuIbintendedCarrierETA;
    }

    public void setBexuIbintendedCarrierETA(Date bexuIbintendedCarrierETA) {
        this.bexuIbintendedCarrierETA = bexuIbintendedCarrierETA;
    }

    public Date getBexuIbintendedCarrierATA() {
        return this.bexuIbintendedCarrierATA;
    }

    public void setBexuIbintendedCarrierATA(Date bexuIbintendedCarrierATA) {
        this.bexuIbintendedCarrierATA = bexuIbintendedCarrierATA;
    }

    public Date getBexuIbintendedCarrierATD() {
        return this.bexuIbintendedCarrierATD;
    }

    public void setBexuIbintendedCarrierATD(Date bexuIbintendedCarrierATD) {
        this.bexuIbintendedCarrierATD = bexuIbintendedCarrierATD;
    }

    public String getBexuIbintendedVesselType() {
        return this.bexuIbintendedVesselType;
    }

    public void setBexuIbintendedVesselType(String bexuIbintendedVesselType) {
        this.bexuIbintendedVesselType = bexuIbintendedVesselType;
    }

    public String getBexuIbintendedCarrierLineId() {
        return this.bexuIbintendedCarrierLineId;
    }

    public void setBexuIbintendedCarrierLineId(String bexuIbintendedCarrierLineId) {
        this.bexuIbintendedCarrierLineId = bexuIbintendedCarrierLineId;
    }

    public String getBexuIbintendedServiceId() {
        return this.bexuIbintendedServiceId;
    }

    public void setBexuIbintendedServiceId(String bexuIbintendedServiceId) {
        this.bexuIbintendedServiceId = bexuIbintendedServiceId;
    }

    public String getBexuIbintendedVesselClassId() {
        return this.bexuIbintendedVesselClassId;
    }

    public void setBexuIbintendedVesselClassId(String bexuIbintendedVesselClassId) {
        this.bexuIbintendedVesselClassId = bexuIbintendedVesselClassId;
    }

    public String getBexuIbLocType() {
        return this.bexuIbLocType;
    }

    public void setBexuIbLocType(String bexuIbLocType) {
        this.bexuIbLocType = bexuIbLocType;
    }

    public String getBexuIbId() {
        return this.bexuIbId;
    }

    public void setBexuIbId(String bexuIbId) {
        this.bexuIbId = bexuIbId;
    }

    public String getBexuIbVisitId() {
        return this.bexuIbVisitId;
    }

    public void setBexuIbVisitId(String bexuIbVisitId) {
        this.bexuIbVisitId = bexuIbVisitId;
    }

    public String getBexuIbCarrierName() {
        return this.bexuIbCarrierName;
    }

    public void setBexuIbCarrierName(String bexuIbCarrierName) {
        this.bexuIbCarrierName = bexuIbCarrierName;
    }

    public String getBexuIbCallNbr() {
        return this.bexuIbCallNbr;
    }

    public void setBexuIbCallNbr(String bexuIbCallNbr) {
        this.bexuIbCallNbr = bexuIbCallNbr;
    }

    public Date getBexuIbCarrierETA() {
        return this.bexuIbCarrierETA;
    }

    public void setBexuIbCarrierETA(Date bexuIbCarrierETA) {
        this.bexuIbCarrierETA = bexuIbCarrierETA;
    }

    public Date getBexuIbCarrierATA() {
        return this.bexuIbCarrierATA;
    }

    public void setBexuIbCarrierATA(Date bexuIbCarrierATA) {
        this.bexuIbCarrierATA = bexuIbCarrierATA;
    }

    public Date getBexuIbCarrierATD() {
        return this.bexuIbCarrierATD;
    }

    public void setBexuIbCarrierATD(Date bexuIbCarrierATD) {
        this.bexuIbCarrierATD = bexuIbCarrierATD;
    }

    public String getBexuIbVesselType() {
        return this.bexuIbVesselType;
    }

    public void setBexuIbVesselType(String bexuIbVesselType) {
        this.bexuIbVesselType = bexuIbVesselType;
    }

    public String getBexuIbCarrierLineId() {
        return this.bexuIbCarrierLineId;
    }

    public void setBexuIbCarrierLineId(String bexuIbCarrierLineId) {
        this.bexuIbCarrierLineId = bexuIbCarrierLineId;
    }

    public String getBexuIbServiceId() {
        return this.bexuIbServiceId;
    }

    public void setBexuIbServiceId(String bexuIbServiceId) {
        this.bexuIbServiceId = bexuIbServiceId;
    }

    public String getBexuIbVesselClassId() {
        return this.bexuIbVesselClassId;
    }

    public void setBexuIbVesselClassId(String bexuIbVesselClassId) {
        this.bexuIbVesselClassId = bexuIbVesselClassId;
    }

    public String getBexuIbVesselLloydsId() {
        return this.bexuIbVesselLloydsId;
    }

    public void setBexuIbVesselLloydsId(String bexuIbVesselLloydsId) {
        this.bexuIbVesselLloydsId = bexuIbVesselLloydsId;
    }

    public String getBexuObintendedLocType() {
        return this.bexuObintendedLocType;
    }

    public void setBexuObintendedLocType(String bexuObintendedLocType) {
        this.bexuObintendedLocType = bexuObintendedLocType;
    }

    public String getBexuObintendedId() {
        return this.bexuObintendedId;
    }

    public void setBexuObintendedId(String bexuObintendedId) {
        this.bexuObintendedId = bexuObintendedId;
    }

    public String getBexuObintendedVisitId() {
        return this.bexuObintendedVisitId;
    }

    public void setBexuObintendedVisitId(String bexuObintendedVisitId) {
        this.bexuObintendedVisitId = bexuObintendedVisitId;
    }

    public String getBexuObintendedCarrierName() {
        return this.bexuObintendedCarrierName;
    }

    public void setBexuObintendedCarrierName(String bexuObintendedCarrierName) {
        this.bexuObintendedCarrierName = bexuObintendedCarrierName;
    }

    public String getBexuObintendedCallNbr() {
        return this.bexuObintendedCallNbr;
    }

    public void setBexuObintendedCallNbr(String bexuObintendedCallNbr) {
        this.bexuObintendedCallNbr = bexuObintendedCallNbr;
    }

    public Date getBexuObintendedCarrierETA() {
        return this.bexuObintendedCarrierETA;
    }

    public void setBexuObintendedCarrierETA(Date bexuObintendedCarrierETA) {
        this.bexuObintendedCarrierETA = bexuObintendedCarrierETA;
    }

    public Date getBexuObintendedCarrierATA() {
        return this.bexuObintendedCarrierATA;
    }

    public void setBexuObintendedCarrierATA(Date bexuObintendedCarrierATA) {
        this.bexuObintendedCarrierATA = bexuObintendedCarrierATA;
    }

    public Date getBexuObintendedCarrierATD() {
        return this.bexuObintendedCarrierATD;
    }

    public void setBexuObintendedCarrierATD(Date bexuObintendedCarrierATD) {
        this.bexuObintendedCarrierATD = bexuObintendedCarrierATD;
    }

    public String getBexuObintendedVesselType() {
        return this.bexuObintendedVesselType;
    }

    public void setBexuObintendedVesselType(String bexuObintendedVesselType) {
        this.bexuObintendedVesselType = bexuObintendedVesselType;
    }

    public String getBexuObintendedCarrierLineId() {
        return this.bexuObintendedCarrierLineId;
    }

    public void setBexuObintendedCarrierLineId(String bexuObintendedCarrierLineId) {
        this.bexuObintendedCarrierLineId = bexuObintendedCarrierLineId;
    }

    public String getBexuObintendedServiceId() {
        return this.bexuObintendedServiceId;
    }

    public void setBexuObintendedServiceId(String bexuObintendedServiceId) {
        this.bexuObintendedServiceId = bexuObintendedServiceId;
    }

    public String getBexuObintendedVesselClassId() {
        return this.bexuObintendedVesselClassId;
    }

    public void setBexuObintendedVesselClassId(String bexuObintendedVesselClassId) {
        this.bexuObintendedVesselClassId = bexuObintendedVesselClassId;
    }

    public String getBexuObLocType() {
        return this.bexuObLocType;
    }

    public void setBexuObLocType(String bexuObLocType) {
        this.bexuObLocType = bexuObLocType;
    }

    public String getBexuObId() {
        return this.bexuObId;
    }

    public void setBexuObId(String bexuObId) {
        this.bexuObId = bexuObId;
    }

    public String getBexuObVisitId() {
        return this.bexuObVisitId;
    }

    public void setBexuObVisitId(String bexuObVisitId) {
        this.bexuObVisitId = bexuObVisitId;
    }

    public String getBexuObCarrierName() {
        return this.bexuObCarrierName;
    }

    public void setBexuObCarrierName(String bexuObCarrierName) {
        this.bexuObCarrierName = bexuObCarrierName;
    }

    public String getBexuObCallNbr() {
        return this.bexuObCallNbr;
    }

    public void setBexuObCallNbr(String bexuObCallNbr) {
        this.bexuObCallNbr = bexuObCallNbr;
    }

    public Date getBexuObCarrierETA() {
        return this.bexuObCarrierETA;
    }

    public void setBexuObCarrierETA(Date bexuObCarrierETA) {
        this.bexuObCarrierETA = bexuObCarrierETA;
    }

    public Date getBexuObCarrierATA() {
        return this.bexuObCarrierATA;
    }

    public void setBexuObCarrierATA(Date bexuObCarrierATA) {
        this.bexuObCarrierATA = bexuObCarrierATA;
    }

    public Date getBexuObCarrierATD() {
        return this.bexuObCarrierATD;
    }

    public void setBexuObCarrierATD(Date bexuObCarrierATD) {
        this.bexuObCarrierATD = bexuObCarrierATD;
    }

    public String getBexuObVesselType() {
        return this.bexuObVesselType;
    }

    public void setBexuObVesselType(String bexuObVesselType) {
        this.bexuObVesselType = bexuObVesselType;
    }

    public String getBexuObCarrierLineId() {
        return this.bexuObCarrierLineId;
    }

    public void setBexuObCarrierLineId(String bexuObCarrierLineId) {
        this.bexuObCarrierLineId = bexuObCarrierLineId;
    }

    public String getBexuObServiceId() {
        return this.bexuObServiceId;
    }

    public void setBexuObServiceId(String bexuObServiceId) {
        this.bexuObServiceId = bexuObServiceId;
    }

    public String getBexuObVesselClassId() {
        return this.bexuObVesselClassId;
    }

    public void setBexuObVesselClassId(String bexuObVesselClassId) {
        this.bexuObVesselClassId = bexuObVesselClassId;
    }

    public String getBexuObVesselLloydsId() {
        return this.bexuObVesselLloydsId;
    }

    public void setBexuObVesselLloydsId(String bexuObVesselLloydsId) {
        this.bexuObVesselLloydsId = bexuObVesselLloydsId;
    }

    public Date getBexuEventStartTime() {
        return this.bexuEventStartTime;
    }

    public void setBexuEventStartTime(Date bexuEventStartTime) {
        this.bexuEventStartTime = bexuEventStartTime;
    }

    public Date getBexuEventEndTime() {
        return this.bexuEventEndTime;
    }

    public void setBexuEventEndTime(Date bexuEventEndTime) {
        this.bexuEventEndTime = bexuEventEndTime;
    }

    public Date getBexuRuleStartDay() {
        return this.bexuRuleStartDay;
    }

    public void setBexuRuleStartDay(Date bexuRuleStartDay) {
        this.bexuRuleStartDay = bexuRuleStartDay;
    }

    public Date getBexuRuleEndDay() {
        return this.bexuRuleEndDay;
    }

    public void setBexuRuleEndDay(Date bexuRuleEndDay) {
        this.bexuRuleEndDay = bexuRuleEndDay;
    }

    public String getBexuPayeeCustomerId() {
        return this.bexuPayeeCustomerId;
    }

    public void setBexuPayeeCustomerId(String bexuPayeeCustomerId) {
        this.bexuPayeeCustomerId = bexuPayeeCustomerId;
    }

    public BizRoleEnum getBexuPayeeRole() {
        return this.bexuPayeeRole;
    }

    public void setBexuPayeeRole(BizRoleEnum bexuPayeeRole) {
        this.bexuPayeeRole = bexuPayeeRole;
    }

    public Date getBexuFirstAvailability() {
        return this.bexuFirstAvailability;
    }

    public void setBexuFirstAvailability(Date bexuFirstAvailability) {
        this.bexuFirstAvailability = bexuFirstAvailability;
    }

    public String getBexuQuayCheId() {
        return this.bexuQuayCheId;
    }

    public void setBexuQuayCheId(String bexuQuayCheId) {
        this.bexuQuayCheId = bexuQuayCheId;
    }

    public String getBexuFmPosLocType() {
        return this.bexuFmPosLocType;
    }

    public void setBexuFmPosLocType(String bexuFmPosLocType) {
        this.bexuFmPosLocType = bexuFmPosLocType;
    }

    public String getBexuFmPosLocId() {
        return this.bexuFmPosLocId;
    }

    public void setBexuFmPosLocId(String bexuFmPosLocId) {
        this.bexuFmPosLocId = bexuFmPosLocId;
    }

    public String getBexuToPosLocType() {
        return this.bexuToPosLocType;
    }

    public void setBexuToPosLocType(String bexuToPosLocType) {
        this.bexuToPosLocType = bexuToPosLocType;
    }

    public String getBexuToPosLocId() {
        return this.bexuToPosLocId;
    }

    public void setBexuToPosLocId(String bexuToPosLocId) {
        this.bexuToPosLocId = bexuToPosLocId;
    }

    public String getBexuServiceOrder() {
        return this.bexuServiceOrder;
    }

    public void setBexuServiceOrder(String bexuServiceOrder) {
        this.bexuServiceOrder = bexuServiceOrder;
    }

    public String getBexuRestowReason() {
        return this.bexuRestowReason;
    }

    public void setBexuRestowReason(String bexuRestowReason) {
        this.bexuRestowReason = bexuRestowReason;
    }

    public String getBexuRestowAccount() {
        return this.bexuRestowAccount;
    }

    public void setBexuRestowAccount(String bexuRestowAccount) {
        this.bexuRestowAccount = bexuRestowAccount;
    }

    public Long getBexuRehandleCount() {
        return this.bexuRehandleCount;
    }

    public void setBexuRehandleCount(Long bexuRehandleCount) {
        this.bexuRehandleCount = bexuRehandleCount;
    }

    public Double getBexuQuantity() {
        return this.bexuQuantity;
    }

    public void setBexuQuantity(Double bexuQuantity) {
        this.bexuQuantity = bexuQuantity;
    }

    public String getBexuQuantityUnit() {
        return this.bexuQuantityUnit;
    }

    public void setBexuQuantityUnit(String bexuQuantityUnit) {
        this.bexuQuantityUnit = bexuQuantityUnit;
    }

    public String getBexuFmPositionSlot() {
        return this.bexuFmPositionSlot;
    }

    public void setBexuFmPositionSlot(String bexuFmPositionSlot) {
        this.bexuFmPositionSlot = bexuFmPositionSlot;
    }

    public String getBexuFmPositionName() {
        return this.bexuFmPositionName;
    }

    public void setBexuFmPositionName(String bexuFmPositionName) {
        this.bexuFmPositionName = bexuFmPositionName;
    }

    public String getBexuToPositionSlot() {
        return this.bexuToPositionSlot;
    }

    public void setBexuToPositionSlot(String bexuToPositionSlot) {
        this.bexuToPositionSlot = bexuToPositionSlot;
    }

    public String getBexuToPositionName() {
        return this.bexuToPositionName;
    }

    public void setBexuToPositionName(String bexuToPositionName) {
        this.bexuToPositionName = bexuToPositionName;
    }

    public Boolean getBexuIsLocked() {
        return this.bexuIsLocked;
    }

    public void setBexuIsLocked(Boolean bexuIsLocked) {
        this.bexuIsLocked = bexuIsLocked;
    }

    public String getBexuStatus() {
        return this.bexuStatus;
    }

    public void setBexuStatus(String bexuStatus) {
        this.bexuStatus = bexuStatus;
    }

    public String getBexuLastDraftInvNbr() {
        return this.bexuLastDraftInvNbr;
    }

    public void setBexuLastDraftInvNbr(String bexuLastDraftInvNbr) {
        this.bexuLastDraftInvNbr = bexuLastDraftInvNbr;
    }

    public String getBexuNotes() {
        return this.bexuNotes;
    }

    public void setBexuNotes(String bexuNotes) {
        this.bexuNotes = bexuNotes;
    }

    public Boolean getBexuIsOverrideValue() {
        return this.bexuIsOverrideValue;
    }

    public void setBexuIsOverrideValue(Boolean bexuIsOverrideValue) {
        this.bexuIsOverrideValue = bexuIsOverrideValue;
    }

    public String getBexuOverrideValueType() {
        return this.bexuOverrideValueType;
    }

    public void setBexuOverrideValueType(String bexuOverrideValueType) {
        this.bexuOverrideValueType = bexuOverrideValueType;
    }

    public Double getBexuOverrideValue() {
        return this.bexuOverrideValue;
    }

    public void setBexuOverrideValue(Double bexuOverrideValue) {
        this.bexuOverrideValue = bexuOverrideValue;
    }

    public String getBexuGuaranteeId() {
        return this.bexuGuaranteeId;
    }

    public void setBexuGuaranteeId(String bexuGuaranteeId) {
        this.bexuGuaranteeId = bexuGuaranteeId;
    }

    public Long getBexuGuaranteeGkey() {
        return this.bexuGuaranteeGkey;
    }

    public void setBexuGuaranteeGkey(Long bexuGuaranteeGkey) {
        this.bexuGuaranteeGkey = bexuGuaranteeGkey;
    }

    public String getBexuGnteInvProcessingStatus() {
        return this.bexuGnteInvProcessingStatus;
    }

    public void setBexuGnteInvProcessingStatus(String bexuGnteInvProcessingStatus) {
        this.bexuGnteInvProcessingStatus = bexuGnteInvProcessingStatus;
    }

    public String getBexuFlexString01() {
        return this.bexuFlexString01;
    }

    public void setBexuFlexString01(String bexuFlexString01) {
        this.bexuFlexString01 = bexuFlexString01;
    }

    public String getBexuFlexString02() {
        return this.bexuFlexString02;
    }

    public void setBexuFlexString02(String bexuFlexString02) {
        this.bexuFlexString02 = bexuFlexString02;
    }

    public String getBexuFlexString03() {
        return this.bexuFlexString03;
    }

    public void setBexuFlexString03(String bexuFlexString03) {
        this.bexuFlexString03 = bexuFlexString03;
    }

    public String getBexuFlexString04() {
        return this.bexuFlexString04;
    }

    public void setBexuFlexString04(String bexuFlexString04) {
        this.bexuFlexString04 = bexuFlexString04;
    }

    public String getBexuFlexString05() {
        return this.bexuFlexString05;
    }

    public void setBexuFlexString05(String bexuFlexString05) {
        this.bexuFlexString05 = bexuFlexString05;
    }

    public String getBexuFlexString06() {
        return this.bexuFlexString06;
    }

    public void setBexuFlexString06(String bexuFlexString06) {
        this.bexuFlexString06 = bexuFlexString06;
    }

    public String getBexuFlexString07() {
        return this.bexuFlexString07;
    }

    public void setBexuFlexString07(String bexuFlexString07) {
        this.bexuFlexString07 = bexuFlexString07;
    }

    public String getBexuFlexString08() {
        return this.bexuFlexString08;
    }

    public void setBexuFlexString08(String bexuFlexString08) {
        this.bexuFlexString08 = bexuFlexString08;
    }

    public String getBexuFlexString09() {
        return this.bexuFlexString09;
    }

    public void setBexuFlexString09(String bexuFlexString09) {
        this.bexuFlexString09 = bexuFlexString09;
    }

    public String getBexuFlexString10() {
        return this.bexuFlexString10;
    }

    public void setBexuFlexString10(String bexuFlexString10) {
        this.bexuFlexString10 = bexuFlexString10;
    }

    public String getBexuFlexString11() {
        return this.bexuFlexString11;
    }

    public void setBexuFlexString11(String bexuFlexString11) {
        this.bexuFlexString11 = bexuFlexString11;
    }

    public String getBexuFlexString12() {
        return this.bexuFlexString12;
    }

    public void setBexuFlexString12(String bexuFlexString12) {
        this.bexuFlexString12 = bexuFlexString12;
    }

    public String getBexuFlexString13() {
        return this.bexuFlexString13;
    }

    public void setBexuFlexString13(String bexuFlexString13) {
        this.bexuFlexString13 = bexuFlexString13;
    }

    public String getBexuFlexString14() {
        return this.bexuFlexString14;
    }

    public void setBexuFlexString14(String bexuFlexString14) {
        this.bexuFlexString14 = bexuFlexString14;
    }

    public String getBexuFlexString15() {
        return this.bexuFlexString15;
    }

    public void setBexuFlexString15(String bexuFlexString15) {
        this.bexuFlexString15 = bexuFlexString15;
    }

    public String getBexuFlexString16() {
        return this.bexuFlexString16;
    }

    public void setBexuFlexString16(String bexuFlexString16) {
        this.bexuFlexString16 = bexuFlexString16;
    }

    public String getBexuFlexString17() {
        return this.bexuFlexString17;
    }

    public void setBexuFlexString17(String bexuFlexString17) {
        this.bexuFlexString17 = bexuFlexString17;
    }

    public String getBexuFlexString18() {
        return this.bexuFlexString18;
    }

    public void setBexuFlexString18(String bexuFlexString18) {
        this.bexuFlexString18 = bexuFlexString18;
    }

    public String getBexuFlexString19() {
        return this.bexuFlexString19;
    }

    public void setBexuFlexString19(String bexuFlexString19) {
        this.bexuFlexString19 = bexuFlexString19;
    }

    public String getBexuFlexString20() {
        return this.bexuFlexString20;
    }

    public void setBexuFlexString20(String bexuFlexString20) {
        this.bexuFlexString20 = bexuFlexString20;
    }

    public String getBexuFlexString21() {
        return this.bexuFlexString21;
    }

    public void setBexuFlexString21(String bexuFlexString21) {
        this.bexuFlexString21 = bexuFlexString21;
    }

    public String getBexuFlexString22() {
        return this.bexuFlexString22;
    }

    public void setBexuFlexString22(String bexuFlexString22) {
        this.bexuFlexString22 = bexuFlexString22;
    }

    public String getBexuFlexString23() {
        return this.bexuFlexString23;
    }

    public void setBexuFlexString23(String bexuFlexString23) {
        this.bexuFlexString23 = bexuFlexString23;
    }

    public String getBexuFlexString24() {
        return this.bexuFlexString24;
    }

    public void setBexuFlexString24(String bexuFlexString24) {
        this.bexuFlexString24 = bexuFlexString24;
    }

    public String getBexuFlexString25() {
        return this.bexuFlexString25;
    }

    public void setBexuFlexString25(String bexuFlexString25) {
        this.bexuFlexString25 = bexuFlexString25;
    }

    public String getBexuFlexString26() {
        return this.bexuFlexString26;
    }

    public void setBexuFlexString26(String bexuFlexString26) {
        this.bexuFlexString26 = bexuFlexString26;
    }

    public String getBexuFlexString27() {
        return this.bexuFlexString27;
    }

    public void setBexuFlexString27(String bexuFlexString27) {
        this.bexuFlexString27 = bexuFlexString27;
    }

    public String getBexuFlexString28() {
        return this.bexuFlexString28;
    }

    public void setBexuFlexString28(String bexuFlexString28) {
        this.bexuFlexString28 = bexuFlexString28;
    }

    public String getBexuFlexString29() {
        return this.bexuFlexString29;
    }

    public void setBexuFlexString29(String bexuFlexString29) {
        this.bexuFlexString29 = bexuFlexString29;
    }

    public String getBexuFlexString30() {
        return this.bexuFlexString30;
    }

    public void setBexuFlexString30(String bexuFlexString30) {
        this.bexuFlexString30 = bexuFlexString30;
    }

    public String getBexuFlexString31() {
        return this.bexuFlexString31;
    }

    public void setBexuFlexString31(String bexuFlexString31) {
        this.bexuFlexString31 = bexuFlexString31;
    }

    public String getBexuFlexString32() {
        return this.bexuFlexString32;
    }

    public void setBexuFlexString32(String bexuFlexString32) {
        this.bexuFlexString32 = bexuFlexString32;
    }

    public String getBexuFlexString33() {
        return this.bexuFlexString33;
    }

    public void setBexuFlexString33(String bexuFlexString33) {
        this.bexuFlexString33 = bexuFlexString33;
    }

    public String getBexuFlexString34() {
        return this.bexuFlexString34;
    }

    public void setBexuFlexString34(String bexuFlexString34) {
        this.bexuFlexString34 = bexuFlexString34;
    }

    public String getBexuFlexString35() {
        return this.bexuFlexString35;
    }

    public void setBexuFlexString35(String bexuFlexString35) {
        this.bexuFlexString35 = bexuFlexString35;
    }

    public String getBexuFlexString36() {
        return this.bexuFlexString36;
    }

    public void setBexuFlexString36(String bexuFlexString36) {
        this.bexuFlexString36 = bexuFlexString36;
    }

    public String getBexuFlexString37() {
        return this.bexuFlexString37;
    }

    public void setBexuFlexString37(String bexuFlexString37) {
        this.bexuFlexString37 = bexuFlexString37;
    }

    public String getBexuFlexString38() {
        return this.bexuFlexString38;
    }

    public void setBexuFlexString38(String bexuFlexString38) {
        this.bexuFlexString38 = bexuFlexString38;
    }

    public String getBexuFlexString39() {
        return this.bexuFlexString39;
    }

    public void setBexuFlexString39(String bexuFlexString39) {
        this.bexuFlexString39 = bexuFlexString39;
    }

    public String getBexuFlexString40() {
        return this.bexuFlexString40;
    }

    public void setBexuFlexString40(String bexuFlexString40) {
        this.bexuFlexString40 = bexuFlexString40;
    }

    public Date getBexuFlexDate01() {
        return this.bexuFlexDate01;
    }

    public void setBexuFlexDate01(Date bexuFlexDate01) {
        this.bexuFlexDate01 = bexuFlexDate01;
    }

    public Date getBexuFlexDate02() {
        return this.bexuFlexDate02;
    }

    public void setBexuFlexDate02(Date bexuFlexDate02) {
        this.bexuFlexDate02 = bexuFlexDate02;
    }

    public Date getBexuFlexDate03() {
        return this.bexuFlexDate03;
    }

    public void setBexuFlexDate03(Date bexuFlexDate03) {
        this.bexuFlexDate03 = bexuFlexDate03;
    }

    public Date getBexuFlexDate04() {
        return this.bexuFlexDate04;
    }

    public void setBexuFlexDate04(Date bexuFlexDate04) {
        this.bexuFlexDate04 = bexuFlexDate04;
    }

    public Date getBexuFlexDate05() {
        return this.bexuFlexDate05;
    }

    public void setBexuFlexDate05(Date bexuFlexDate05) {
        this.bexuFlexDate05 = bexuFlexDate05;
    }

    public Long getBexuFlexLong01() {
        return this.bexuFlexLong01;
    }

    public void setBexuFlexLong01(Long bexuFlexLong01) {
        this.bexuFlexLong01 = bexuFlexLong01;
    }

    public Long getBexuFlexLong02() {
        return this.bexuFlexLong02;
    }

    public void setBexuFlexLong02(Long bexuFlexLong02) {
        this.bexuFlexLong02 = bexuFlexLong02;
    }

    public Long getBexuFlexLong03() {
        return this.bexuFlexLong03;
    }

    public void setBexuFlexLong03(Long bexuFlexLong03) {
        this.bexuFlexLong03 = bexuFlexLong03;
    }

    public Long getBexuFlexLong04() {
        return this.bexuFlexLong04;
    }

    public void setBexuFlexLong04(Long bexuFlexLong04) {
        this.bexuFlexLong04 = bexuFlexLong04;
    }

    public Long getBexuFlexLong05() {
        return this.bexuFlexLong05;
    }

    public void setBexuFlexLong05(Long bexuFlexLong05) {
        this.bexuFlexLong05 = bexuFlexLong05;
    }

    public Double getBexuFlexDouble01() {
        return this.bexuFlexDouble01;
    }

    public void setBexuFlexDouble01(Double bexuFlexDouble01) {
        this.bexuFlexDouble01 = bexuFlexDouble01;
    }

    public Double getBexuFlexDouble02() {
        return this.bexuFlexDouble02;
    }

    public void setBexuFlexDouble02(Double bexuFlexDouble02) {
        this.bexuFlexDouble02 = bexuFlexDouble02;
    }

    public Double getBexuFlexDouble03() {
        return this.bexuFlexDouble03;
    }

    public void setBexuFlexDouble03(Double bexuFlexDouble03) {
        this.bexuFlexDouble03 = bexuFlexDouble03;
    }

    public Double getBexuFlexDouble04() {
        return this.bexuFlexDouble04;
    }

    public void setBexuFlexDouble04(Double bexuFlexDouble04) {
        this.bexuFlexDouble04 = bexuFlexDouble04;
    }

    public Double getBexuFlexDouble05() {
        return this.bexuFlexDouble05;
    }

    public void setBexuFlexDouble05(Double bexuFlexDouble05) {
        this.bexuFlexDouble05 = bexuFlexDouble05;
    }

    public String getBexuEqOwnerId() {
        return this.bexuEqOwnerId;
    }

    public void setBexuEqOwnerId(String bexuEqOwnerId) {
        this.bexuEqOwnerId = bexuEqOwnerId;
    }

    public String getBexuEqRole() {
        return this.bexuEqRole;
    }

    public void setBexuEqRole(String bexuEqRole) {
        this.bexuEqRole = bexuEqRole;
    }

    public Double getBexuVerifiedGrossMass() {
        return this.bexuVerifiedGrossMass;
    }

    public void setBexuVerifiedGrossMass(Double bexuVerifiedGrossMass) {
        this.bexuVerifiedGrossMass = bexuVerifiedGrossMass;
    }

    public String getBexuVgmVerifierEntity() {
        return this.bexuVgmVerifierEntity;
    }

    public void setBexuVgmVerifierEntity(String bexuVgmVerifierEntity) {
        this.bexuVgmVerifierEntity = bexuVgmVerifierEntity;
    }

    public Date getBexuCreated() {
        return this.bexuCreated;
    }

    public void setBexuCreated(Date bexuCreated) {
        this.bexuCreated = bexuCreated;
    }

    public String getBexuCreator() {
        return this.bexuCreator;
    }

    public void setBexuCreator(String bexuCreator) {
        this.bexuCreator = bexuCreator;
    }

    public Date getBexuChanged() {
        return this.bexuChanged;
    }

    public void setBexuChanged(Date bexuChanged) {
        this.bexuChanged = bexuChanged;
    }

    public String getBexuChanger() {
        return this.bexuChanger;
    }

    public void setBexuChanger(String bexuChanger) {
        this.bexuChanger = bexuChanger;
    }

    public Facility getBexuFacility() {
        return this.bexuFacility;
    }

    public void setBexuFacility(Facility bexuFacility) {
        this.bexuFacility = bexuFacility;
    }

    public Operator getBexuTerminalOperator() {
        return this.bexuTerminalOperator;
    }

    public void setBexuTerminalOperator(Operator bexuTerminalOperator) {
        this.bexuTerminalOperator = bexuTerminalOperator;
    }

    public Set getBexuGuarantees() {
        return this.bexuGuarantees;
    }

    public void setBexuGuarantees(Set bexuGuarantees) {
        this.bexuGuarantees = bexuGuarantees;
    }
}
