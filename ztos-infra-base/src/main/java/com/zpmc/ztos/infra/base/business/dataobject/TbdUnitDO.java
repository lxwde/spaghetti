package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.business.enums.argo.*;
import com.zpmc.ztos.infra.base.business.enums.inventory.CarrierIncompatibilityReasonEnum;
import com.zpmc.ztos.infra.base.business.enums.inventory.EqDamageSeverityEnum;
import com.zpmc.ztos.infra.base.business.enums.inventory.UnitVisitStateEnum;
import com.zpmc.ztos.infra.base.business.equipments.EqBaseOrderItem;
import com.zpmc.ztos.infra.base.business.equipments.EquipGrade;
import com.zpmc.ztos.infra.base.business.equipments.EquipType;
import com.zpmc.ztos.infra.base.business.model.*;
import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;
import com.zpmc.ztos.infra.base.common.scopes.Facility;

import java.io.Serializable;
import java.util.Date;

public class TbdUnitDO extends DatabaseEntity implements Serializable {
    private Long tbduGkey;
    private Long tbduPkey;
    private Date tbduCreateTime;
    private Long tbduTruckTransactionGkey;
    private UnitCategoryEnum tbduCategory;
    private FreightKindEnum tbduFreightKind;
    private UnitVisitStateEnum tbduVisitState;
    private RestowTypeEnum tbduRestowType;
    private EquipClassEnum tbduClass;
    private String tbduPickInstruction;
    private String tbduSerialRanges;
    private String tbduBlNbr;
    private String tbduBkgNbr;
    private EqDamageSeverityEnum tbduDamageSeverity;
    private Boolean tbduRequiresPower;
    private Double tbduTemp1C;
    private Double tbduGoodsAndCtrWtKg;
    private Boolean tbduIsOog;
    private Long tbduOogBackCm;
    private Long tbduOogFrontCm;
    private Long tbduOogLeftCm;
    private Long tbduOogRightCm;
    private Long tbduOogTopCm;
    private Boolean tbduIsHazardous;
    private String tbduImdgTypes;
    private DrayStatusEnum tbduDrayStatus;
    private String tbduRemark;
    private String tbduFlexString01;
    private String tbduFlexString02;
    private String tbduFlexString03;
    private String tbduFlexString04;
    private Date tbduFlexDate01;
    private Date tbduFlexDate02;
    private Date tbduFlexDate03;
    private Date tbduFlexDate04;
    private String tbduStowFactor;
    private Boolean tbduIsPendingDelete;
    private String tbduYardPosition;
    private CarrierIncompatibilityReasonEnum tbduCarrierIncompatibleReason;
    private RailConeStatusEnum tbduObsoleteRailConeStatus;
    private Facility tbduFacility;
    private SpecialStow tbduSpecialStow;
    private EquipType tbduEquipType;
    private EquipGrade tbduGradeID;
    private Commodity tbduCommodity;
    private ScopedBizUnit tbduLineOperator;
    private ScopedBizUnit tbduEqOperator;
    private ScopedBizUnit tbduConsigneeBzu;
    private CarrierVisit tbduIbCv;
    private CarrierVisit tbduObCv;
    private RoutingPoint tbduPOD1;
    private RoutingPoint tbduPOD2;
    private Group tbduRoutingGroup;
    private ScopedBizUnit tbduTruckingCompany;
    private EqBaseOrderItem tbduDepartureOrderItem;

    public Serializable getPrimaryKey() {
        return this.getTbduGkey();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        Long id = this.getTbduGkey();
        if (id == null) {
            return false;
        }
        if (!(other instanceof TbdUnitDO)) {
            return false;
        }
        TbdUnitDO that = (TbdUnitDO)other;
        return ((Object)id).equals(that.getTbduGkey());
    }

    public int hashCode() {
        Long id = this.getTbduGkey();
        return id == null ? System.identityHashCode(this) : ((Object)id).hashCode();
    }

    public Long getTbduGkey() {
        return this.tbduGkey;
    }

    protected void setTbduGkey(Long tbduGkey) {
        this.tbduGkey = tbduGkey;
    }

    public Long getTbduPkey() {
        return this.tbduPkey;
    }

    protected void setTbduPkey(Long tbduPkey) {
        this.tbduPkey = tbduPkey;
    }

    public Date getTbduCreateTime() {
        return this.tbduCreateTime;
    }

    protected void setTbduCreateTime(Date tbduCreateTime) {
        this.tbduCreateTime = tbduCreateTime;
    }

    public Long getTbduTruckTransactionGkey() {
        return this.tbduTruckTransactionGkey;
    }

    protected void setTbduTruckTransactionGkey(Long tbduTruckTransactionGkey) {
        this.tbduTruckTransactionGkey = tbduTruckTransactionGkey;
    }

    public UnitCategoryEnum getTbduCategory() {
        return this.tbduCategory;
    }

    protected void setTbduCategory(UnitCategoryEnum tbduCategory) {
        this.tbduCategory = tbduCategory;
    }

    public FreightKindEnum getTbduFreightKind() {
        return this.tbduFreightKind;
    }

    protected void setTbduFreightKind(FreightKindEnum tbduFreightKind) {
        this.tbduFreightKind = tbduFreightKind;
    }

    public UnitVisitStateEnum getTbduVisitState() {
        return this.tbduVisitState;
    }

    protected void setTbduVisitState(UnitVisitStateEnum tbduVisitState) {
        this.tbduVisitState = tbduVisitState;
    }

    public RestowTypeEnum getTbduRestowType() {
        return this.tbduRestowType;
    }

    protected void setTbduRestowType(RestowTypeEnum tbduRestowType) {
        this.tbduRestowType = tbduRestowType;
    }

    public EquipClassEnum getTbduClass() {
        return this.tbduClass;
    }

    protected void setTbduClass(EquipClassEnum tbduClass) {
        this.tbduClass = tbduClass;
    }

    public String getTbduPickInstruction() {
        return this.tbduPickInstruction;
    }

    protected void setTbduPickInstruction(String tbduPickInstruction) {
        this.tbduPickInstruction = tbduPickInstruction;
    }

    public String getTbduSerialRanges() {
        return this.tbduSerialRanges;
    }

    protected void setTbduSerialRanges(String tbduSerialRanges) {
        this.tbduSerialRanges = tbduSerialRanges;
    }

    public String getTbduBlNbr() {
        return this.tbduBlNbr;
    }

    protected void setTbduBlNbr(String tbduBlNbr) {
        this.tbduBlNbr = tbduBlNbr;
    }

    public String getTbduBkgNbr() {
        return this.tbduBkgNbr;
    }

    protected void setTbduBkgNbr(String tbduBkgNbr) {
        this.tbduBkgNbr = tbduBkgNbr;
    }

    public EqDamageSeverityEnum getTbduDamageSeverity() {
        return this.tbduDamageSeverity;
    }

    protected void setTbduDamageSeverity(EqDamageSeverityEnum tbduDamageSeverity) {
        this.tbduDamageSeverity = tbduDamageSeverity;
    }

    public Boolean getTbduRequiresPower() {
        return this.tbduRequiresPower;
    }

    protected void setTbduRequiresPower(Boolean tbduRequiresPower) {
        this.tbduRequiresPower = tbduRequiresPower;
    }

    public Double getTbduTemp1C() {
        return this.tbduTemp1C;
    }

    protected void setTbduTemp1C(Double tbduTemp1C) {
        this.tbduTemp1C = tbduTemp1C;
    }

    public Double getTbduGoodsAndCtrWtKg() {
        return this.tbduGoodsAndCtrWtKg;
    }

    protected void setTbduGoodsAndCtrWtKg(Double tbduGoodsAndCtrWtKg) {
        this.tbduGoodsAndCtrWtKg = tbduGoodsAndCtrWtKg;
    }

    public Boolean getTbduIsOog() {
        return this.tbduIsOog;
    }

    protected void setTbduIsOog(Boolean tbduIsOog) {
        this.tbduIsOog = tbduIsOog;
    }

    public Long getTbduOogBackCm() {
        return this.tbduOogBackCm;
    }

    protected void setTbduOogBackCm(Long tbduOogBackCm) {
        this.tbduOogBackCm = tbduOogBackCm;
    }

    public Long getTbduOogFrontCm() {
        return this.tbduOogFrontCm;
    }

    protected void setTbduOogFrontCm(Long tbduOogFrontCm) {
        this.tbduOogFrontCm = tbduOogFrontCm;
    }

    public Long getTbduOogLeftCm() {
        return this.tbduOogLeftCm;
    }

    protected void setTbduOogLeftCm(Long tbduOogLeftCm) {
        this.tbduOogLeftCm = tbduOogLeftCm;
    }

    public Long getTbduOogRightCm() {
        return this.tbduOogRightCm;
    }

    protected void setTbduOogRightCm(Long tbduOogRightCm) {
        this.tbduOogRightCm = tbduOogRightCm;
    }

    public Long getTbduOogTopCm() {
        return this.tbduOogTopCm;
    }

    protected void setTbduOogTopCm(Long tbduOogTopCm) {
        this.tbduOogTopCm = tbduOogTopCm;
    }

    public Boolean getTbduIsHazardous() {
        return this.tbduIsHazardous;
    }

    protected void setTbduIsHazardous(Boolean tbduIsHazardous) {
        this.tbduIsHazardous = tbduIsHazardous;
    }

    public String getTbduImdgTypes() {
        return this.tbduImdgTypes;
    }

    protected void setTbduImdgTypes(String tbduImdgTypes) {
        this.tbduImdgTypes = tbduImdgTypes;
    }

    public DrayStatusEnum getTbduDrayStatus() {
        return this.tbduDrayStatus;
    }

    protected void setTbduDrayStatus(DrayStatusEnum tbduDrayStatus) {
        this.tbduDrayStatus = tbduDrayStatus;
    }

    public String getTbduRemark() {
        return this.tbduRemark;
    }

    protected void setTbduRemark(String tbduRemark) {
        this.tbduRemark = tbduRemark;
    }

    public String getTbduFlexString01() {
        return this.tbduFlexString01;
    }

    protected void setTbduFlexString01(String tbduFlexString01) {
        this.tbduFlexString01 = tbduFlexString01;
    }

    public String getTbduFlexString02() {
        return this.tbduFlexString02;
    }

    protected void setTbduFlexString02(String tbduFlexString02) {
        this.tbduFlexString02 = tbduFlexString02;
    }

    public String getTbduFlexString03() {
        return this.tbduFlexString03;
    }

    protected void setTbduFlexString03(String tbduFlexString03) {
        this.tbduFlexString03 = tbduFlexString03;
    }

    public String getTbduFlexString04() {
        return this.tbduFlexString04;
    }

    protected void setTbduFlexString04(String tbduFlexString04) {
        this.tbduFlexString04 = tbduFlexString04;
    }

    public Date getTbduFlexDate01() {
        return this.tbduFlexDate01;
    }

    protected void setTbduFlexDate01(Date tbduFlexDate01) {
        this.tbduFlexDate01 = tbduFlexDate01;
    }

    public Date getTbduFlexDate02() {
        return this.tbduFlexDate02;
    }

    protected void setTbduFlexDate02(Date tbduFlexDate02) {
        this.tbduFlexDate02 = tbduFlexDate02;
    }

    public Date getTbduFlexDate03() {
        return this.tbduFlexDate03;
    }

    protected void setTbduFlexDate03(Date tbduFlexDate03) {
        this.tbduFlexDate03 = tbduFlexDate03;
    }

    public Date getTbduFlexDate04() {
        return this.tbduFlexDate04;
    }

    protected void setTbduFlexDate04(Date tbduFlexDate04) {
        this.tbduFlexDate04 = tbduFlexDate04;
    }

    public String getTbduStowFactor() {
        return this.tbduStowFactor;
    }

    protected void setTbduStowFactor(String tbduStowFactor) {
        this.tbduStowFactor = tbduStowFactor;
    }

    public Boolean getTbduIsPendingDelete() {
        return this.tbduIsPendingDelete;
    }

    protected void setTbduIsPendingDelete(Boolean tbduIsPendingDelete) {
        this.tbduIsPendingDelete = tbduIsPendingDelete;
    }

    public String getTbduYardPosition() {
        return this.tbduYardPosition;
    }

    protected void setTbduYardPosition(String tbduYardPosition) {
        this.tbduYardPosition = tbduYardPosition;
    }

    public CarrierIncompatibilityReasonEnum getTbduCarrierIncompatibleReason() {
        return this.tbduCarrierIncompatibleReason;
    }

    protected void setTbduCarrierIncompatibleReason(CarrierIncompatibilityReasonEnum tbduCarrierIncompatibleReason) {
        this.tbduCarrierIncompatibleReason = tbduCarrierIncompatibleReason;
    }

    public RailConeStatusEnum getTbduObsoleteRailConeStatus() {
        return this.tbduObsoleteRailConeStatus;
    }

    protected void setTbduObsoleteRailConeStatus(RailConeStatusEnum tbduObsoleteRailConeStatus) {
        this.tbduObsoleteRailConeStatus = tbduObsoleteRailConeStatus;
    }

    public Facility getTbduFacility() {
        return this.tbduFacility;
    }

    protected void setTbduFacility(Facility tbduFacility) {
        this.tbduFacility = tbduFacility;
    }

    public SpecialStow getTbduSpecialStow() {
        return this.tbduSpecialStow;
    }

    protected void setTbduSpecialStow(SpecialStow tbduSpecialStow) {
        this.tbduSpecialStow = tbduSpecialStow;
    }

    public EquipType getTbduEquipType() {
        return this.tbduEquipType;
    }

    protected void setTbduEquipType(EquipType tbduEquipType) {
        this.tbduEquipType = tbduEquipType;
    }

    public EquipGrade getTbduGradeID() {
        return this.tbduGradeID;
    }

    protected void setTbduGradeID(EquipGrade tbduGradeID) {
        this.tbduGradeID = tbduGradeID;
    }

    public Commodity getTbduCommodity() {
        return this.tbduCommodity;
    }

    protected void setTbduCommodity(Commodity tbduCommodity) {
        this.tbduCommodity = tbduCommodity;
    }

    public ScopedBizUnit getTbduLineOperator() {
        return this.tbduLineOperator;
    }

    protected void setTbduLineOperator(ScopedBizUnit tbduLineOperator) {
        this.tbduLineOperator = tbduLineOperator;
    }

    public ScopedBizUnit getTbduEqOperator() {
        return this.tbduEqOperator;
    }

    protected void setTbduEqOperator(ScopedBizUnit tbduEqOperator) {
        this.tbduEqOperator = tbduEqOperator;
    }

    public ScopedBizUnit getTbduConsigneeBzu() {
        return this.tbduConsigneeBzu;
    }

    protected void setTbduConsigneeBzu(ScopedBizUnit tbduConsigneeBzu) {
        this.tbduConsigneeBzu = tbduConsigneeBzu;
    }

    public CarrierVisit getTbduIbCv() {
        return this.tbduIbCv;
    }

    protected void setTbduIbCv(CarrierVisit tbduIbCv) {
        this.tbduIbCv = tbduIbCv;
    }

    public CarrierVisit getTbduObCv() {
        return this.tbduObCv;
    }

    protected void setTbduObCv(CarrierVisit tbduObCv) {
        this.tbduObCv = tbduObCv;
    }

    public RoutingPoint getTbduPOD1() {
        return this.tbduPOD1;
    }

    protected void setTbduPOD1(RoutingPoint tbduPOD1) {
        this.tbduPOD1 = tbduPOD1;
    }

    public RoutingPoint getTbduPOD2() {
        return this.tbduPOD2;
    }

    protected void setTbduPOD2(RoutingPoint tbduPOD2) {
        this.tbduPOD2 = tbduPOD2;
    }

    public Group getTbduRoutingGroup() {
        return this.tbduRoutingGroup;
    }

    protected void setTbduRoutingGroup(Group tbduRoutingGroup) {
        this.tbduRoutingGroup = tbduRoutingGroup;
    }

    public ScopedBizUnit getTbduTruckingCompany() {
        return this.tbduTruckingCompany;
    }

    protected void setTbduTruckingCompany(ScopedBizUnit tbduTruckingCompany) {
        this.tbduTruckingCompany = tbduTruckingCompany;
    }

    public EqBaseOrderItem getTbduDepartureOrderItem() {
        return this.tbduDepartureOrderItem;
    }

    protected void setTbduDepartureOrderItem(EqBaseOrderItem tbduDepartureOrderItem) {
        this.tbduDepartureOrderItem = tbduDepartureOrderItem;
    }
}
