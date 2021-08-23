package com.zpmc.ztos.infra.base.business.enums.argo;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

//public enum OrderPurposeEnum {
//    REPO(1, "REPO", "", "", "REPO"),
//    OFFHIRE(2, "OFFHIRE", "", "", "OFFHIRE"),
//    OFFSITE_REPAIR(3, "OFFSITE_REPAIR", "", "", "OFFSITE_REPAIR"),
//    IFT(4, "IFT", "", "", "IFT");
//    private final int key;
//    private final String code;
//    private final String desc;
//    private final String color;
//    private final String displayName;
//
//    OrderPurposeEnum(int key, String code, String desc, String color, String displayName) {
//        this.key = key;
//        this.code = code;
//        this.desc = desc;
//        this.color = color;
//        this.displayName = displayName;
//    }
//
//    public static List getEnumList() {
//        return EnumUtils.getEnumList(OrderPurposeEnum.class);
//    }
//
//    public static EnumSet getEnumSet() {
//        return EnumSet.allOf(OrderPurposeEnum.class);
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

public class OrderPurposeEnum
        extends SystemOrderPurposeEnum {
    public static final OrderPurposeEnum REPO = new OrderPurposeEnum("REPO", "atom.OrderPurposeEnum.REPO.description", "atom.OrderPurposeEnum.REPO.code", "", "", "", "REPO", "", "REPO");
    public static final OrderPurposeEnum OFFHIRE = new OrderPurposeEnum("OFFHIRE", "atom.OrderPurposeEnum.OFFHIRE.description", "atom.OrderPurposeEnum.OFFHIRE.code", "", "", "", "OFFHIRE", "", "OFFHIRE");
    public static final OrderPurposeEnum OFFSITE_REPAIR = new OrderPurposeEnum("OFFSITE_REPAIR", "atom.OrderPurposeEnum.OFFSITE_REPAIR.description", "atom.OrderPurposeEnum.OFFSITE_REPAIR.code", "", "", "", "OFFSITE_REPAIR", "", "OFFSITE_REPAIR");
    public static final OrderPurposeEnum IFT = new OrderPurposeEnum("IFT", "atom.OrderPurposeEnum.IFT.description", "atom.OrderPurposeEnum.IFT.code", "", "", "", "IFT", "", "IFT");

    public static OrderPurposeEnum getEnum(String inName) {
        return (OrderPurposeEnum) OrderPurposeEnum.getEnum(OrderPurposeEnum.class, (String)inName);
    }

    public static Map getEnumMap() {
        return OrderPurposeEnum.getEnumMap(OrderPurposeEnum.class);
    }

    public static List getEnumList() {
        return OrderPurposeEnum.getEnumList(OrderPurposeEnum.class);
    }

    public static Collection getList() {
        return OrderPurposeEnum.getEnumList(OrderPurposeEnum.class);
    }

    public static Iterator iterator() {
        return OrderPurposeEnum.iterator(OrderPurposeEnum.class);
    }

    protected OrderPurposeEnum(String inKey, String inDescriptionResKey, String inCodeResKey, String inBackgroundColor, String inForegroundColor, String inIconIdPath, String inDescription, String inReplacedBy, String inExternalId) {
        super(inKey, inDescriptionResKey, inCodeResKey, inBackgroundColor, inForegroundColor, inIconIdPath, inDescription, inReplacedBy, inExternalId);
    }

    public String getMappingClassName() {
        return "com.navis.argo.persistence.atoms.UserTypeOrderPurposeEnum";
    }
}

