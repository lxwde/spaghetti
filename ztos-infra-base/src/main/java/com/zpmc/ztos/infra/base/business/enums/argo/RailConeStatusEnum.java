package com.zpmc.ztos.infra.base.business.enums.argo;

import com.zpmc.ztos.infra.base.common.enums.AtomizedEnum;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

//public enum RailConeStatusEnum {
//    UNKNOWN(1, "UNKNOWN", "", "", "UNKNOWN"),
//    NO_CONES(2, "NO_CONES", "", "", "NO_CONES"),
//    UNLOCKED_CONES(3, "UNLOCKED_CONES", "", "", "UNLOCKED_CONES"),
//    LOCKED_CONES(4, "LOCKED_CONES", "", "", "LOCKED_CONES");
//    private final int key;
//    private final String code;
//    private final String desc;
//    private final String color;
//    private final String displayName;
//
//    RailConeStatusEnum(int key, String code, String desc, String color, String displayName) {
//        this.key = key;
//        this.code = code;
//        this.desc = desc;
//        this.color = color;
//        this.displayName = displayName;
//    }
//
//    public static List getEnumList() {
//        return EnumUtils.getEnumList(RailConeStatusEnum.class);
//    }
//
//    public static EnumSet getEnumSet() {
//        return EnumSet.allOf(RailConeStatusEnum.class);
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

public class RailConeStatusEnum
        extends AtomizedEnum {
    public static final RailConeStatusEnum UNKNOWN = new RailConeStatusEnum("UNKNOWN", "atom.RailConeStatusEnum.UNKNOWN.description", "atom.RailConeStatusEnum.UNKNOWN.code", "", "", "");
    public static final RailConeStatusEnum NO_CONES = new RailConeStatusEnum("NO_CONES", "atom.RailConeStatusEnum.NO_CONES.description", "atom.RailConeStatusEnum.NO_CONES.code", "", "", "");
    public static final RailConeStatusEnum UNLOCKED_CONES = new RailConeStatusEnum("UNLOCKED_CONES", "atom.RailConeStatusEnum.UNLOCKED_CONES.description", "atom.RailConeStatusEnum.UNLOCKED_CONES.code", "", "", "");
    public static final RailConeStatusEnum LOCKED_CONES = new RailConeStatusEnum("LOCKED_CONES", "atom.RailConeStatusEnum.LOCKED_CONES.description", "atom.RailConeStatusEnum.LOCKED_CONES.code", "", "", "");

    public static RailConeStatusEnum getEnum(String inName) {
        return (RailConeStatusEnum) RailConeStatusEnum.getEnum(RailConeStatusEnum.class, (String)inName);
    }

    public static Map getEnumMap() {
        return RailConeStatusEnum.getEnumMap(RailConeStatusEnum.class);
    }

    public static List getEnumList() {
        return RailConeStatusEnum.getEnumList(RailConeStatusEnum.class);
    }

    public static Collection getList() {
        return RailConeStatusEnum.getEnumList(RailConeStatusEnum.class);
    }

    public static Iterator iterator() {
        return RailConeStatusEnum.iterator(RailConeStatusEnum.class);
    }

    protected RailConeStatusEnum(String inKey, String inDescriptionResKey, String inCodeResKey, String inBackgroundColor, String inForegroundColor, String inIconIdPath) {
        super(inKey, inDescriptionResKey, inCodeResKey, inBackgroundColor, inForegroundColor, inIconIdPath);
    }

    public String getMappingClassName() {
        return "com.navis.argo.persistence.atoms.UserTypeRailConeStatusEnum";
    }
}
