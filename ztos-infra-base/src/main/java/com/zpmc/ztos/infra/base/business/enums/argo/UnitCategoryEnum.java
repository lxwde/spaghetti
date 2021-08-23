package com.zpmc.ztos.infra.base.business.enums.argo;

import com.zpmc.ztos.infra.base.common.enums.AtomizedEnum;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

//public enum UnitCategoryEnum {
//    IMPORT(1, "IMPORT", "", "", "IMPORT"),
//    EXPORT(2, "EXPORT", "", "", "EXPORT"),
//    TRANSSHIP(3, "TRANSSHIP", "", "", "TRANSSHIP"),
//    DOMESTIC(4, "DOMESTIC", "", "", "DOMESTIC"),
//    STORAGE(5, "STORAGE", "", "", "STORAGE"),
//    THROUGH(6, "THROUGH", "", "", "THROUGH");
//    private final int key;
//    private final String code;
//    private final String desc;
//    private final String color;
//    private final String displayName;
//
//    UnitCategoryEnum(int key, String code, String desc, String color, String displayName) {
//        this.key = key;
//        this.code = code;
//        this.desc = desc;
//        this.color = color;
//        this.displayName = displayName;
//    }
//
//    public static List getEnumList() {
//        return EnumUtils.getEnumList(UnitCategoryEnum.class);
//    }
//
//    public static EnumSet getEnumSet() {
//        return EnumSet.allOf(UnitCategoryEnum.class);
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

public class UnitCategoryEnum
        extends AtomizedEnum {
    public static final UnitCategoryEnum IMPORT = new UnitCategoryEnum("IMPRT", "atom.UnitCategoryEnum.IMPORT.description", "atom.UnitCategoryEnum.IMPORT.code", "darkgoldenrod", "", "");
    public static final UnitCategoryEnum EXPORT = new UnitCategoryEnum("EXPRT", "atom.UnitCategoryEnum.EXPORT.description", "atom.UnitCategoryEnum.EXPORT.code", "deepskyblue", "", "");
    public static final UnitCategoryEnum TRANSSHIP = new UnitCategoryEnum("TRSHP", "atom.UnitCategoryEnum.TRANSSHIP.description", "atom.UnitCategoryEnum.TRANSSHIP.code", "lightcoral", "", "");
    public static final UnitCategoryEnum DOMESTIC = new UnitCategoryEnum("DMSTC", "atom.UnitCategoryEnum.DOMESTIC.description", "atom.UnitCategoryEnum.DOMESTIC.code", "lightskyblue", "", "");
    public static final UnitCategoryEnum STORAGE = new UnitCategoryEnum("STRGE", "atom.UnitCategoryEnum.STORAGE.description", "atom.UnitCategoryEnum.STORAGE.code", "gainsboro", "", "");
    public static final UnitCategoryEnum THROUGH = new UnitCategoryEnum("THRGH", "atom.UnitCategoryEnum.THROUGH.description", "atom.UnitCategoryEnum.THROUGH.code", "lightslategray", "", "");

    public static UnitCategoryEnum getEnum(String inName) {
        return (UnitCategoryEnum) UnitCategoryEnum.getEnum(UnitCategoryEnum.class, (String)inName);
    }

    public static Map getEnumMap() {
        return UnitCategoryEnum.getEnumMap(UnitCategoryEnum.class);
    }

    public static List getEnumList() {
        return UnitCategoryEnum.getEnumList(UnitCategoryEnum.class);
    }

    public static Collection getList() {
        return UnitCategoryEnum.getEnumList(UnitCategoryEnum.class);
    }

    public static Iterator iterator() {
        return UnitCategoryEnum.iterator(UnitCategoryEnum.class);
    }

    protected UnitCategoryEnum(String inKey, String inDescriptionResKey, String inCodeResKey, String inBackgroundColor, String inForegroundColor, String inIconIdPath) {
        super(inKey, inDescriptionResKey, inCodeResKey, inBackgroundColor, inForegroundColor, inIconIdPath);
    }

    public String getMappingClassName() {
        return "com.navis.argo.persistence.atoms.UserTypeUnitCategoryEnum";
    }
}

