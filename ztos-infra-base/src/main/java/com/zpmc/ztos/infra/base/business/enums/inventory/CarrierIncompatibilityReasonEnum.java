package com.zpmc.ztos.infra.base.business.enums.inventory;

import com.zpmc.ztos.infra.base.common.enums.AtomizedEnum;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

//public enum CarrierIncompatibilityReasonEnum {
//    NONE(1, "NONE", "", "", "NONE"),
//    PIN_MISMATCHED(2, "PIN_MISMATCHED", "", "", "PIN_MISMATCHED"),
//    RAIL_CONES_NEEDED(3, "RAIL_CONES_NEEDED", "", "", "RAIL_CONES_NEEDED"),
//    RAIL_CONES_NEED_REMOVING(4, "RAIL_CONES_NEED_REMOVING", "", "", "RAIL_CONES_NEED_REMOVING"),
//    RAIL_CONES_NEED_UNLOCKING(5, "RAIL_CONES_NEED_UNLOCKING", "", "", "RAIL_CONES_NEED_UNLOCKING"),
//    RAIL_CONES_NEED_LOCKING(6, "RAIL_CONES_NEED_LOCKING", "", "", "RAIL_CONES_NEED_LOCKING");
//    private final int key;
//    private final String code;
//    private final String desc;
//    private final String color;
//    private final String displayName;
//
//    CarrierIncompatibilityReasonEnum(int key, String code, String desc, String color, String displayName) {
//        this.key = key;
//        this.code = code;
//        this.desc = desc;
//        this.color = color;
//        this.displayName = displayName;
//    }
//
//    public static List getEnumList() {
//        return EnumUtils.getEnumList(CarrierIncompatibilityReasonEnum.class);
//    }
//
//    public static EnumSet getEnumSet() {
//        return EnumSet.allOf(CarrierIncompatibilityReasonEnum.class);
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

public class CarrierIncompatibilityReasonEnum
        extends AtomizedEnum {
    public static final CarrierIncompatibilityReasonEnum NONE = new CarrierIncompatibilityReasonEnum("NONE", "atom.CarrierIncompatibilityReasonEnum.NONE.description", "atom.CarrierIncompatibilityReasonEnum.NONE.code", "", "", "");
    public static final CarrierIncompatibilityReasonEnum PIN_MISMATCHED = new CarrierIncompatibilityReasonEnum("PIN_MISMATCHED", "atom.CarrierIncompatibilityReasonEnum.PIN_MISMATCHED.description", "atom.CarrierIncompatibilityReasonEnum.PIN_MISMATCHED.code", "", "", "");
    public static final CarrierIncompatibilityReasonEnum RAIL_CONES_NEEDED = new CarrierIncompatibilityReasonEnum("RAIL_CONES_NEEDED", "atom.CarrierIncompatibilityReasonEnum.RAIL_CONES_NEEDED.description", "atom.CarrierIncompatibilityReasonEnum.RAIL_CONES_NEEDED.code", "", "", "");
    public static final CarrierIncompatibilityReasonEnum RAIL_CONES_NEED_REMOVING = new CarrierIncompatibilityReasonEnum("RAIL_CONES_NEED_REMOVING", "atom.CarrierIncompatibilityReasonEnum.RAIL_CONES_NEED_REMOVING.description", "atom.CarrierIncompatibilityReasonEnum.RAIL_CONES_NEED_REMOVING.code", "", "", "");
    public static final CarrierIncompatibilityReasonEnum RAIL_CONES_NEED_UNLOCKING = new CarrierIncompatibilityReasonEnum("RAIL_CONES_NEED_UNLOCKING", "atom.CarrierIncompatibilityReasonEnum.RAIL_CONES_NEED_UNLOCKING.description", "atom.CarrierIncompatibilityReasonEnum.RAIL_CONES_NEED_UNLOCKING.code", "", "", "");
    public static final CarrierIncompatibilityReasonEnum RAIL_CONES_NEED_LOCKING = new CarrierIncompatibilityReasonEnum("RAIL_CONES_NEED_LOCKING", "atom.CarrierIncompatibilityReasonEnum.RAIL_CONES_NEED_LOCKING.description", "atom.CarrierIncompatibilityReasonEnum.RAIL_CONES_NEED_LOCKING.code", "", "", "");

    public static CarrierIncompatibilityReasonEnum getEnum(String inName) {
        return (CarrierIncompatibilityReasonEnum) CarrierIncompatibilityReasonEnum.getEnum(CarrierIncompatibilityReasonEnum.class, (String)inName);
    }

    public static Map getEnumMap() {
        return CarrierIncompatibilityReasonEnum.getEnumMap(CarrierIncompatibilityReasonEnum.class);
    }

    public static List getEnumList() {
        return CarrierIncompatibilityReasonEnum.getEnumList(CarrierIncompatibilityReasonEnum.class);
    }

    public static Collection getList() {
        return CarrierIncompatibilityReasonEnum.getEnumList(CarrierIncompatibilityReasonEnum.class);
    }

    public static Iterator iterator() {
        return CarrierIncompatibilityReasonEnum.iterator(CarrierIncompatibilityReasonEnum.class);
    }

    protected CarrierIncompatibilityReasonEnum(String inKey, String inDescriptionResKey, String inCodeResKey, String inBackgroundColor, String inForegroundColor, String inIconIdPath) {
        super(inKey, inDescriptionResKey, inCodeResKey, inBackgroundColor, inForegroundColor, inIconIdPath);
    }

    public String getMappingClassName() {
        return "com.navis.inventory.persistence.atoms.UserTypeCarrierIncompatibilityReasonEnum";
    }
}
