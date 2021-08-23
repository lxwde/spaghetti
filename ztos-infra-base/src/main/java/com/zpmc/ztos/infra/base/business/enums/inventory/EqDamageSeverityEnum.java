package com.zpmc.ztos.infra.base.business.enums.inventory;

import com.zpmc.ztos.infra.base.common.enums.AtomizedEnum;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

//public enum EqDamageSeverityEnum {
//    NONE(1, "NONE", "", "", "NONE"),
//    MINOR(2, "MINOR", "", "", "MINOR"),
//    MAJOR(3, "MAJOR", "", "", "MAJOR"),
//    REPAIRED(4, "REPAIRED", "", "", "REPAIRED");
//    private final int key;
//    private final String code;
//    private final String desc;
//    private final String color;
//    private final String displayName;
//
//    EqDamageSeverityEnum(int key, String code, String desc, String color, String displayName) {
//        this.key = key;
//        this.code = code;
//        this.desc = desc;
//        this.color = color;
//        this.displayName = displayName;
//    }
//
//    public static List getEnumList() {
//        return EnumUtils.getEnumList(EqDamageSeverityEnum.class);
//    }
//
//    public static EnumSet getEnumSet() {
//        return EnumSet.allOf(EqDamageSeverityEnum.class);
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

public class EqDamageSeverityEnum
        extends AtomizedEnum {
    public static final EqDamageSeverityEnum NONE = new EqDamageSeverityEnum("NONE", "atom.EqDamageSeverityEnum.NONE.description", "atom.EqDamageSeverityEnum.NONE.code", "", "", "");
    public static final EqDamageSeverityEnum MINOR = new EqDamageSeverityEnum("MINOR", "atom.EqDamageSeverityEnum.MINOR.description", "atom.EqDamageSeverityEnum.MINOR.code", "yellow", "", "");
    public static final EqDamageSeverityEnum MAJOR = new EqDamageSeverityEnum("MAJOR", "atom.EqDamageSeverityEnum.MAJOR.description", "atom.EqDamageSeverityEnum.MAJOR.code", "red", "", "");
    public static final EqDamageSeverityEnum REPAIRED = new EqDamageSeverityEnum("REPAIRED", "atom.EqDamageSeverityEnum.REPAIRED.description", "atom.EqDamageSeverityEnum.REPAIRED.code", "paleturquoise", "", "");

    public static EqDamageSeverityEnum getEnum(String inName) {
        return (EqDamageSeverityEnum) EqDamageSeverityEnum.getEnum(EqDamageSeverityEnum.class, (String)inName);
    }

    public static Map getEnumMap() {
        return EqDamageSeverityEnum.getEnumMap(EqDamageSeverityEnum.class);
    }

    public static List getEnumList() {
        return EqDamageSeverityEnum.getEnumList(EqDamageSeverityEnum.class);
    }

    public static Collection getList() {
        return EqDamageSeverityEnum.getEnumList(EqDamageSeverityEnum.class);
    }

    public static Iterator iterator() {
        return EqDamageSeverityEnum.iterator(EqDamageSeverityEnum.class);
    }

    protected EqDamageSeverityEnum(String inKey, String inDescriptionResKey, String inCodeResKey, String inBackgroundColor, String inForegroundColor, String inIconIdPath) {
        super(inKey, inDescriptionResKey, inCodeResKey, inBackgroundColor, inForegroundColor, inIconIdPath);
    }

    public String getMappingClassName() {
        return "com.navis.inventory.persistence.atoms.UserTypeEqDamageSeverityEnum";
    }
}
