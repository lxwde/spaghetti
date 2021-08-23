package com.zpmc.ztos.infra.base.business.enums.argo;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

//public enum PropertyGroupEnum {
//    GENERAL(1, "GENERAL", "", "", "GENERAL"),
//    WEIGHT(2, "WEIGHT", "", "", "WEIGHT"),
//    GROSS_WEIGHT(3, "GROSS_WEIGHT", "", "", "GROSS_WEIGHT"),
//    INTENDED_CV(4, "INTENDED_CV", "", "", "INTENDED_CV"),
//    INTENDED_OB_SERVICE(5, "INTENDED_OB_SERVICE", "", "", "INTENDED_OB_SERVICE"),
//    INTENDED_OB_CLASSIFICATION(6, "INTENDED_OB_CLASSIFICATION", "", "", "INTENDED_OB_CLASSIFICATION"),
//    ACTUAL_IB_CV(7, "ACTUAL_IB_CV", "", "", "ACTUAL_IB_CV"),
//    ACTUAL_IB_SERVICE(8, "ACTUAL_IB_SERVICE", "", "", "ACTUAL_IB_SERVICE"),
//    ACTUAL_IB_CLASSIFICATION(9, "ACTUAL_IB_CLASSIFICATION", "", "", "ACTUAL_IB_CLASSIFICATION"),
//    ROUTING(10, "ROUTING", "", "", "ROUTING"),
//    ROUTING_POD(11, "ROUTING_POD", "", "", "ROUTING_POD"),
//    ROUTING_POL(12, "ROUTING_POL", "", "", "ROUTING_POL"),
//    ROUTING_POD2(13, "ROUTING_POD2", "", "", "ROUTING_POD2"),
//    ROUTING_ORIGIN(14, "ROUTING_ORIGIN", "", "", "ROUTING_ORIGIN"),
//    ROUTING_DESTINATION(15, "ROUTING_DESTINATION", "", "", "ROUTING_DESTINATION"),
//    ROUTING_OPL(16, "ROUTING_OPL", "", "", "ROUTING_OPL"),
//    ROUTING_OPT1(17, "ROUTING_OPT1", "", "", "ROUTING_OPT1"),
//    ROUTING_OPT2(18, "ROUTING_OPT2", "", "", "ROUTING_OPT2"),
//    ROUTING_OPT3(19, "ROUTING_OPT3", "", "", "ROUTING_OPT3"),
//    RELEASE_ROUTING(20, "RELEASE_ROUTING", "", "", "RELEASE_ROUTING"),
//    ROUTING_PINNBR(21, "ROUTING_PINNBR", "", "", "ROUTING_PINNBR"),
//    ROUTING_BONDED_DESTINATION(22, "ROUTING_BONDED_DESTINATION", "", "", "ROUTING_BONDED_DESTINATION"),
//    CATEGORY(23, "CATEGORY", "", "", "CATEGORY"),
//    FREIGHT_KIND(24, "FREIGHT_KIND", "", "", "FREIGHT_KIND"),
//    COMMODITY(25, "COMMODITY", "", "", "COMMODITY"),
//    HAZARDS(26, "HAZARDS", "", "", "HAZARDS"),
//    OOG(27, "OOG", "", "", "OOG"),
//    DAMAGE(28, "DAMAGE", "", "", "DAMAGE"),
//    REEFER(29, "REEFER", "", "", "REEFER"),
//    REEFER_TEMP_REQ(30, "REEFER_TEMP_REQ", "", "", "REEFER_TEMP_REQ"),
//    REEFER_TEMP_MAX(31, "REEFER_TEMP_MAX", "", "", "REEFER_TEMP_MAX"),
//    REEFER_TEMP_MIN(32, "REEFER_TEMP_MIN", "", "", "REEFER_TEMP_MIN"),
//    LINEOP(33, "LINEOP", "", "", "LINEOP"),
//    CTR_OPERATOR(34, "CTR_OPERATOR", "", "", "CTR_OPERATOR"),
//    EQUIP_TYPE(35, "EQUIP_TYPE", "", "", "EQUIP_TYPE"),
//    SEALS(36, "SEALS", "", "", "SEALS"),
//    POSITION(37, "POSITION", "", "", "POSITION"),
//    AGENTS(38, "AGENTS", "", "", "AGENTS"),
//    BUILD_DATE(39, "BUILD_DATE", "", "", "BUILD_DATE"),
//    CSC_EXPIRATION(40, "CSC_EXPIRATION", "", "", "CSC_EXPIRATION"),
//    HEIGHT_MM(41, "HEIGHT_MM", "", "", "HEIGHT_MM"),
//    STRENGTH_CODE(42, "STRENGTH_CODE", "", "", "STRENGTH_CODE"),
//    TARE_WEIGHT_KG(43, "TARE_WEIGHT_KG", "", "", "TARE_WEIGHT_KG"),
//    SERIAL_RANGE(44, "SERIAL_RANGE", "", "", "SERIAL_RANGE"),
//    UNIT_VISIT_STATE(45, "UNIT_VISIT_STATE", "", "", "UNIT_VISIT_STATE"),
//    INBOND_STATUS(46, "INBOND_STATUS", "", "", "INBOND_STATUS"),
//    EXAM_STATUS(47, "EXAM_STATUS", "", "", "EXAM_STATUS"),
//    DIRECT_IB_TO_OB_MOVE(48, "DIRECT_IB_TO_OB_MOVE", "", "", "DIRECT_IB_TO_OB_MOVE"),
//    UNIT_VERIFIED_GROSS_MASS(49, "UNIT_VERIFIED_GROSS_MASS", "", "", "UNIT_VERIFIED_GROSS_MASS");
//    private final int key;
//    private final String code;
//    private final String desc;
//    private final String color;
//    private final String displayName;
//
//    PropertyGroupEnum(int key, String code, String desc, String color, String displayName) {
//        this.key = key;
//        this.code = code;
//        this.desc = desc;
//        this.color = color;
//        this.displayName = displayName;
//    }
//
//    public static List getEnumList() {
//        return EnumUtils.getEnumList(PropertyGroupEnum.class);
//    }
//
//    public static EnumSet getEnumSet() {
//        return EnumSet.allOf(PropertyGroupEnum.class);
//    }
//
//    public int getKey() {
//        return key;
//    }
//
//    public String getCode() {
//        return code;
//    }
//
//    public String getDesc() {
//        return desc;
//    }
//
//    public String getColor() {
//        return color;
//    }
//
//    public String getDisplayName() {
//        return displayName;
//    }
//
//}


public class PropertyGroupEnum
        extends AbstractPropertyGroupEnum {
    public static final PropertyGroupEnum GENERAL = new PropertyGroupEnum("GENERAL", "atom.PropertyGroupEnum.GENERAL.description", "atom.PropertyGroupEnum.GENERAL.code", "", "", "", "");
    public static final PropertyGroupEnum WEIGHT = new PropertyGroupEnum("WEIGHT", "atom.PropertyGroupEnum.WEIGHT.description", "atom.PropertyGroupEnum.WEIGHT.code", "", "", "", "com.navis.inventory.business.units.Unit");
    public static final PropertyGroupEnum GROSS_WEIGHT = new PropertyGroupEnum("GROSS_WEIGHT", "atom.PropertyGroupEnum.GROSS_WEIGHT.description", "atom.PropertyGroupEnum.GROSS_WEIGHT.code", "", "", "", "com.navis.inventory.business.units.Unit");
    public static final PropertyGroupEnum INTENDED_CV = new PropertyGroupEnum("INTENDED_CV", "atom.PropertyGroupEnum.INTENDED_CV.description", "atom.PropertyGroupEnum.INTENDED_CV.code", "", "", "", "com.navis.inventory.business.units.UnitFacilityVisit");
    public static final PropertyGroupEnum INTENDED_OB_SERVICE = new PropertyGroupEnum("INTENDED_OB_SERVICE", "atom.PropertyGroupEnum.INTENDED_OB_SERVICE.description", "atom.PropertyGroupEnum.INTENDED_OB_SERVICE.code", "", "", "", "com.navis.inventory.business.units.UnitFacilityVisit");
    public static final PropertyGroupEnum INTENDED_OB_CLASSIFICATION = new PropertyGroupEnum("INTENDED_OB_CLASSIFICATION", "atom.PropertyGroupEnum.INTENDED_OB_CLASSIFICATION.description", "atom.PropertyGroupEnum.INTENDED_OB_CLASSIFICATION.code", "", "", "", "com.navis.inventory.business.units.UnitFacilityVisit");
    public static final PropertyGroupEnum ACTUAL_IB_CV = new PropertyGroupEnum("ACTUAL_IB_CV", "atom.PropertyGroupEnum.ACTUAL_IB_CV.description", "atom.PropertyGroupEnum.ACTUAL_IB_CV.code", "", "", "", "com.navis.inventory.business.units.UnitFacilityVisit");
    public static final PropertyGroupEnum ACTUAL_IB_SERVICE = new PropertyGroupEnum("ACTUAL_IB_SERVICE", "atom.PropertyGroupEnum.ACTUAL_IB_SERVICE.description", "atom.PropertyGroupEnum.ACTUAL_IB_SERVICE.code", "", "", "", "com.navis.inventory.business.units.UnitFacilityVisit");
    public static final PropertyGroupEnum ACTUAL_IB_CLASSIFICATION = new PropertyGroupEnum("ACTUAL_IB_CLASSIFICATION", "atom.PropertyGroupEnum.ACTUAL_IB_CLASSIFICATION.description", "atom.PropertyGroupEnum.ACTUAL_IB_CLASSIFICATION.code", "", "", "", "com.navis.inventory.business.units.UnitFacilityVisit");
    public static final PropertyGroupEnum ROUTING = new PropertyGroupEnum("ROUTING", "atom.PropertyGroupEnum.ROUTING.description", "atom.PropertyGroupEnum.ROUTING.code", "", "", "", "com.navis.inventory.business.units.Unit");
    public static final PropertyGroupEnum ROUTING_POD = new PropertyGroupEnum("ROUTING_POD", "atom.PropertyGroupEnum.ROUTING_POD.description", "atom.PropertyGroupEnum.ROUTING_POD.code", "", "", "", "com.navis.argo.business.reference.RoutingPoint");
    public static final PropertyGroupEnum ROUTING_POL = new PropertyGroupEnum("ROUTING_POL", "atom.PropertyGroupEnum.ROUTING_POL.description", "atom.PropertyGroupEnum.ROUTING_POL.code", "", "", "", "com.navis.argo.business.reference.RoutingPoint");
    public static final PropertyGroupEnum ROUTING_POD2 = new PropertyGroupEnum("ROUTING_POD2", "atom.PropertyGroupEnum.ROUTING_POD2.description", "atom.PropertyGroupEnum.ROUTING_POD2.code", "", "", "", "com.navis.argo.business.reference.RoutingPoint");
    public static final PropertyGroupEnum ROUTING_ORIGIN = new PropertyGroupEnum("ROUTING_ORIGIN", "atom.PropertyGroupEnum.ROUTING_ORIGIN.description", "atom.PropertyGroupEnum.ROUTING_ORIGIN.code", "", "", "", "com.navis.inventory.business.units.GoodsBase");
    public static final PropertyGroupEnum ROUTING_DESTINATION = new PropertyGroupEnum("ROUTING_DESTINATION", "atom.PropertyGroupEnum.ROUTING_DESTINATION.description", "atom.PropertyGroupEnum.ROUTING_DESTINATION.code", "", "", "", "com.navis.inventory.business.units.GoodsBase");
    public static final PropertyGroupEnum ROUTING_OPL = new PropertyGroupEnum("ROUTING_OPL", "atom.PropertyGroupEnum.ROUTING_OPL.description", "atom.PropertyGroupEnum.ROUTING_OPL.code", "", "", "", "com.navis.argo.business.reference.RoutingPoint");
    public static final PropertyGroupEnum ROUTING_OPT1 = new PropertyGroupEnum("ROUTING_OPT1", "atom.PropertyGroupEnum.ROUTING_OPT1.description", "atom.PropertyGroupEnum.ROUTING_OPT1.code", "", "", "", "com.navis.argo.business.reference.RoutingPoint");
    public static final PropertyGroupEnum ROUTING_OPT2 = new PropertyGroupEnum("ROUTING_OPT2", "atom.PropertyGroupEnum.ROUTING_OPT2.description", "atom.PropertyGroupEnum.ROUTING_OPT2.code", "", "", "", "com.navis.argo.business.reference.RoutingPoint");
    public static final PropertyGroupEnum ROUTING_OPT3 = new PropertyGroupEnum("ROUTING_OPT3", "atom.PropertyGroupEnum.ROUTING_OPT3.description", "atom.PropertyGroupEnum.ROUTING_OPT3.code", "", "", "", "com.navis.argo.business.reference.RoutingPoint");
    public static final PropertyGroupEnum RELEASE_ROUTING = new PropertyGroupEnum("RELEASE_ROUTING", "atom.PropertyGroupEnum.RELEASE_ROUTING.description", "atom.PropertyGroupEnum.RELEASE_ROUTING.code", "", "", "", "com.navis.inventory.business.units.Unit");
    public static final PropertyGroupEnum ROUTING_PINNBR = new PropertyGroupEnum("ROUTING_PINNBR", "atom.PropertyGroupEnum.ROUTING_PINNBR.description", "atom.PropertyGroupEnum.ROUTING_PINNBR.code", "", "", "", "com.navis.inventory.business.units.Unit");
    public static final PropertyGroupEnum ROUTING_BONDED_DESTINATION = new PropertyGroupEnum("ROUTING_BONDED_DESTINATION", "atom.PropertyGroupEnum.ROUTING_BONDED_DESTINATION.description", "atom.PropertyGroupEnum.ROUTING_BONDED_DESTINATION.code", "", "", "", "com.navis.inventory.business.units.Unit");
    public static final PropertyGroupEnum CATEGORY = new PropertyGroupEnum("CATEGORY", "atom.PropertyGroupEnum.CATEGORY.description", "atom.PropertyGroupEnum.CATEGORY.code", "", "", "", "com.navis.inventory.business.units.Unit");
    public static final PropertyGroupEnum FREIGHT_KIND = new PropertyGroupEnum("FREIGHT_KIND", "atom.PropertyGroupEnum.FREIGHT_KIND.description", "atom.PropertyGroupEnum.FREIGHT_KIND.code", "", "", "", "com.navis.inventory.business.units.Unit");
    public static final PropertyGroupEnum COMMODITY = new PropertyGroupEnum("COMMODITY", "atom.PropertyGroupEnum.COMMODITY.description", "atom.PropertyGroupEnum.COMMODITY.code", "", "", "", "com.navis.inventory.business.units.GoodsBase");
    public static final PropertyGroupEnum HAZARDS = new PropertyGroupEnum("HAZARDS", "atom.PropertyGroupEnum.HAZARDS.description", "atom.PropertyGroupEnum.HAZARDS.code", "", "", "", "com.navis.inventory.business.units.GoodsBase");
    public static final PropertyGroupEnum OOG = new PropertyGroupEnum("OOG", "atom.PropertyGroupEnum.OOG.description", "atom.PropertyGroupEnum.OOG.code", "", "", "", "com.navis.inventory.business.units.Unit");
    public static final PropertyGroupEnum DAMAGE = new PropertyGroupEnum("DAMAGE", "atom.PropertyGroupEnum.DAMAGE.description", "atom.PropertyGroupEnum.DAMAGE.code", "", "", "", "com.navis.inventory.business.units.Unit");
    public static final PropertyGroupEnum REEFER = new PropertyGroupEnum("REEFER", "atom.PropertyGroupEnum.REEFER.description", "atom.PropertyGroupEnum.REEFER.code", "", "", "", "com.navis.inventory.business.units.GoodsBase");
    public static final PropertyGroupEnum REEFER_TEMP_REQ = new PropertyGroupEnum("REEFER_TEMP_REQ", "atom.PropertyGroupEnum.REEFER_TEMP_REQ.description", "atom.PropertyGroupEnum.REEFER_TEMP_REQ.code", "", "", "", "com.navis.inventory.business.units.GoodsBase");
    public static final PropertyGroupEnum REEFER_TEMP_MAX = new PropertyGroupEnum("REEFER_TEMP_MAX", "atom.PropertyGroupEnum.REEFER_TEMP_MAX.description", "atom.PropertyGroupEnum.REEFER_TEMP_MAX.code", "", "", "", "com.navis.inventory.business.units.GoodsBase");
    public static final PropertyGroupEnum REEFER_TEMP_MIN = new PropertyGroupEnum("REEFER_TEMP_MIN", "atom.PropertyGroupEnum.REEFER_TEMP_MIN.description", "atom.PropertyGroupEnum.REEFER_TEMP_MIN.code", "", "", "", "com.navis.inventory.business.units.GoodsBase");
    public static final PropertyGroupEnum LINEOP = new PropertyGroupEnum("LINEOP", "atom.PropertyGroupEnum.LINEOP.description", "atom.PropertyGroupEnum.LINEOP.code", "", "", "", "com.navis.inventory.business.units.Unit");
    public static final PropertyGroupEnum CTR_OPERATOR = new PropertyGroupEnum("CTR_OPERATOR", "atom.PropertyGroupEnum.CTR_OPERATOR.description", "atom.PropertyGroupEnum.CTR_OPERATOR.code", "", "", "", "com.navis.inventory.business.units.EquipmentState");
    public static final PropertyGroupEnum EQUIP_TYPE = new PropertyGroupEnum("EQUIP_TYPE", "atom.PropertyGroupEnum.EQUIP_TYPE.description", "atom.PropertyGroupEnum.EQUIP_TYPE.code", "", "", "", "com.navis.argo.business.reference.Equipment");
    public static final PropertyGroupEnum SEALS = new PropertyGroupEnum("SEALS", "atom.PropertyGroupEnum.SEALS.description", "atom.PropertyGroupEnum.SEALS.code", "", "", "", "com.navis.inventory.business.units.Unit");
    public static final PropertyGroupEnum POSITION = new PropertyGroupEnum("POSITION", "atom.PropertyGroupEnum.POSITION.description", "atom.PropertyGroupEnum.POSITION.code", "", "", "", "com.navis.inventory.business.units.UnitFacilityVisit");
    public static final PropertyGroupEnum AGENTS = new PropertyGroupEnum("AGENTS", "atom.PropertyGroupEnum.AGENTS.description", "atom.PropertyGroupEnum.AGENTS.code", "", "", "", "com.navis.inventory.business.units.Unit");
    public static final PropertyGroupEnum BUILD_DATE = new PropertyGroupEnum("BUILD_DATE", "atom.PropertyGroupEnum.BUILD_DATE.description", "atom.PropertyGroupEnum.BUILD_DATE.code", "", "", "", "com.navis.argo.business.reference.Equipment");
    public static final PropertyGroupEnum CSC_EXPIRATION = new PropertyGroupEnum("CSC_EXPIRATION", "atom.PropertyGroupEnum.CSC_EXPIRATION.description", "atom.PropertyGroupEnum.CSC_EXPIRATION.code", "", "", "", "com.navis.argo.business.reference.Equipment");
    public static final PropertyGroupEnum HEIGHT_MM = new PropertyGroupEnum("HEIGHT_MM", "atom.PropertyGroupEnum.HEIGHT_MM.description", "atom.PropertyGroupEnum.HEIGHT_MM.code", "", "", "", "com.navis.argo.business.reference.Equipment");
    public static final PropertyGroupEnum STRENGTH_CODE = new PropertyGroupEnum("STRENGTH_CODE", "atom.PropertyGroupEnum.STRENGTH_CODE.description", "atom.PropertyGroupEnum.STRENGTH_CODE.code", "", "", "", "com.navis.argo.business.reference.Equipment");
    public static final PropertyGroupEnum TARE_WEIGHT_KG = new PropertyGroupEnum("TARE_WEIGHT_KG", "atom.PropertyGroupEnum.TARE_WEIGHT_KG.description", "atom.PropertyGroupEnum.TARE_WEIGHT_KG.code", "", "", "", "com.navis.argo.business.reference.Equipment");
    public static final PropertyGroupEnum SERIAL_RANGE = new PropertyGroupEnum("SERIAL_RANGE", "atom.PropertyGroupEnum.SERIAL_RANGE.description", "atom.PropertyGroupEnum.SERIAL_RANGE.code", "", "", "", "com.navis.argo.business.reference.Equipment");
    public static final PropertyGroupEnum UNIT_VISIT_STATE = new PropertyGroupEnum("UNIT_VISIT_STATE", "atom.PropertyGroupEnum.UNIT_VISIT_STATE.description", "atom.PropertyGroupEnum.UNIT_VISIT_STATE.code", "", "", "", "com.navis.inventory.business.units.Unit");
    public static final PropertyGroupEnum INBOND_STATUS = new PropertyGroupEnum("INBOND_STATUS", "atom.PropertyGroupEnum.INBOND_STATUS.description", "atom.PropertyGroupEnum.INBOND_STATUS.code", "", "", "", "com.navis.inventory.business.units.Unit");
    public static final PropertyGroupEnum EXAM_STATUS = new PropertyGroupEnum("EXAM_STATUS", "atom.PropertyGroupEnum.EXAM_STATUS.description", "atom.PropertyGroupEnum.EXAM_STATUS.code", "", "", "", "com.navis.inventory.business.units.Unit");
    public static final PropertyGroupEnum DIRECT_IB_TO_OB_MOVE = new PropertyGroupEnum("DIRECT_IB_TO_OB_MOVE", "atom.PropertyGroupEnum.DIRECT_IB_TO_OB_MOVE.description", "atom.PropertyGroupEnum.DIRECT_IB_TO_OB_MOVE.code", "", "", "", "com.navis.inventory.business.units.Unit");
    public static final PropertyGroupEnum UNIT_VERIFIED_GROSS_MASS = new PropertyGroupEnum("UNIT_VERIFIED_GROSS_MASS", "atom.PropertyGroupEnum.UNIT_VERIFIED_GROSS_MASS.description", "atom.PropertyGroupEnum.UNIT_VERIFIED_GROSS_MASS.code", "", "", "", "com.navis.inventory.business.units.Unit");

    public static PropertyGroupEnum getEnum(String inName) {
        return (PropertyGroupEnum) PropertyGroupEnum.getEnum(PropertyGroupEnum.class, (String)inName);
    }

    public static Map getEnumMap() {
        return PropertyGroupEnum.getEnumMap(PropertyGroupEnum.class);
    }

    public static List getEnumList() {
        return PropertyGroupEnum.getEnumList(PropertyGroupEnum.class);
    }

    public static Collection getList() {
        return PropertyGroupEnum.getEnumList(PropertyGroupEnum.class);
    }

    public static Iterator iterator() {
        return PropertyGroupEnum.iterator(PropertyGroupEnum.class);
    }

    protected PropertyGroupEnum(String inKey, String inDescriptionResKey, String inCodeResKey, String inBackgroundColor, String inForegroundColor, String inIconIdPath, String inDomainClass) {
        super(inKey, inDescriptionResKey, inCodeResKey, inBackgroundColor, inForegroundColor, inIconIdPath, inDomainClass);
    }

    public String getMappingClassName() {
        return "com.navis.argo.persistence.atoms.UserTypePropertyGroupEnum";
    }
}
