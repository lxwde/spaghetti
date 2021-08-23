package com.zpmc.ztos.infra.base.business.enums.argo;

import com.zpmc.ztos.infra.base.common.enums.AtomizedEnum;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

//public enum ChargeableUnitEventTypeEnum {
//    BASE(1, "BASE", "base evnet", "", "BASE"),
//    STORAGE(2, "STORAGE", "storage evnet", "", "STORAGE"),
//    REEFER(3, "REEFER", "reefer evnet", "", "REEFER"),
//    LINE_STORAGE(4, "LINE_STORAGE", "line_storage evnet", "", "LINE_STORAGE");
//    private final int key;
//    private final String code;
//    private final String desc;
//    private final String color;
//    private final String displayName;
//
//    ChargeableUnitEventTypeEnum(int key, String code, String desc, String color, String displayName) {
//        this.key = key;
//        this.code = code;
//        this.desc = desc;
//        this.color = color;
//        this.displayName = displayName;
//    }
//
//    public static List getEnumList() {
//        return EnumUtils.getEnumList(ChargeableUnitEventTypeEnum.class);
//    }
//
//    public static EnumSet getEnumSet() {
//        return EnumSet.allOf(ChargeableUnitEventTypeEnum.class);
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

public class ChargeableUnitEventTypeEnum
        extends AtomizedEnum {
    public static final ChargeableUnitEventTypeEnum BASE = new ChargeableUnitEventTypeEnum("BASE", "atom.ChargeableUnitEventTypeEnum.BASE.description", "atom.ChargeableUnitEventTypeEnum.BASE.code", "", "", "");
    public static final ChargeableUnitEventTypeEnum STORAGE = new ChargeableUnitEventTypeEnum("STORAGE", "atom.ChargeableUnitEventTypeEnum.STORAGE.description", "atom.ChargeableUnitEventTypeEnum.STORAGE.code", "", "", "");
    public static final ChargeableUnitEventTypeEnum REEFER = new ChargeableUnitEventTypeEnum("REEFER", "atom.ChargeableUnitEventTypeEnum.REEFER.description", "atom.ChargeableUnitEventTypeEnum.REEFER.code", "", "", "");
    public static final ChargeableUnitEventTypeEnum LINE_STORAGE = new ChargeableUnitEventTypeEnum("LINE_STORAGE", "atom.ChargeableUnitEventTypeEnum.LINE_STORAGE.description", "atom.ChargeableUnitEventTypeEnum.LINE_STORAGE.code", "", "", "");

    public static ChargeableUnitEventTypeEnum getEnum(String inName) {
        return (ChargeableUnitEventTypeEnum) ChargeableUnitEventTypeEnum.getEnum(ChargeableUnitEventTypeEnum.class, (String)inName);
    }

    public static Map getEnumMap() {
        return ChargeableUnitEventTypeEnum.getEnumMap(ChargeableUnitEventTypeEnum.class);
    }

    public static List getEnumList() {
        return ChargeableUnitEventTypeEnum.getEnumList(ChargeableUnitEventTypeEnum.class);
    }

    public static Collection getList() {
        return ChargeableUnitEventTypeEnum.getEnumList(ChargeableUnitEventTypeEnum.class);
    }

    public static Iterator iterator() {
        return ChargeableUnitEventTypeEnum.iterator(ChargeableUnitEventTypeEnum.class);
    }

    protected ChargeableUnitEventTypeEnum(String inKey, String inDescriptionResKey, String inCodeResKey, String inBackgroundColor, String inForegroundColor, String inIconIdPath) {
        super(inKey, inDescriptionResKey, inCodeResKey, inBackgroundColor, inForegroundColor, inIconIdPath);
    }

    public String getMappingClassName() {
        return "com.navis.argo.persistence.atoms.UserTypeChargeableUnitEventTypeEnum";
    }
}
