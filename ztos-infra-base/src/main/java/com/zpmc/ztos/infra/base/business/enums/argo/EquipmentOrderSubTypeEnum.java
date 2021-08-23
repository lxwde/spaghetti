package com.zpmc.ztos.infra.base.business.enums.argo;

import com.zpmc.ztos.infra.base.common.enums.AtomizedEnum;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

//public enum EquipmentOrderSubTypeEnum {
//    BOOK(1, "BOOK", "", "", "BOOK"),
//    EQO(2, "EQO", "", "", "EQO"),
//    ERO(3, "ERO", "", "", "ERO"),
//    EDO(4, "EDO", "", "", "EDO"),
//    ELO(5, "ELO", "", "", "ELO"),
//    RAIL(6, "RAIL", "", "", "RAIL");
//    private final int key;
//    private final String code;
//    private final String desc;
//    private final String color;
//    private final String displayName;
//
//    EquipmentOrderSubTypeEnum(int key, String code, String desc, String color, String displayName) {
//        this.key = key;
//        this.code = code;
//        this.desc = desc;
//        this.color = color;
//        this.displayName = displayName;
//    }
//
//    public static List getEnumList() {
//        return EnumUtils.getEnumList(EquipmentOrderSubTypeEnum.class);
//    }
//
//    public static EnumSet getEnumSet() {
//        return EnumSet.allOf(EquipmentOrderSubTypeEnum.class);
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

public class EquipmentOrderSubTypeEnum
        extends AtomizedEnum {
    public static final EquipmentOrderSubTypeEnum BOOK = new EquipmentOrderSubTypeEnum("BOOK", "atom.EquipmentOrderSubTypeEnum.BOOK.description", "atom.EquipmentOrderSubTypeEnum.BOOK.code", "", "", "");
    public static final EquipmentOrderSubTypeEnum EQO = new EquipmentOrderSubTypeEnum("EQO", "atom.EquipmentOrderSubTypeEnum.EQO.description", "atom.EquipmentOrderSubTypeEnum.EQO.code", "", "", "");
    public static final EquipmentOrderSubTypeEnum ERO = new EquipmentOrderSubTypeEnum("ERO", "atom.EquipmentOrderSubTypeEnum.ERO.description", "atom.EquipmentOrderSubTypeEnum.ERO.code", "", "", "");
    public static final EquipmentOrderSubTypeEnum EDO = new EquipmentOrderSubTypeEnum("EDO", "atom.EquipmentOrderSubTypeEnum.EDO.description", "atom.EquipmentOrderSubTypeEnum.EDO.code", "", "", "");
    public static final EquipmentOrderSubTypeEnum ELO = new EquipmentOrderSubTypeEnum("ELO", "atom.EquipmentOrderSubTypeEnum.ELO.description", "atom.EquipmentOrderSubTypeEnum.ELO.code", "", "", "");
    public static final EquipmentOrderSubTypeEnum RAIL = new EquipmentOrderSubTypeEnum("RAIL", "atom.EquipmentOrderSubTypeEnum.RAIL.description", "atom.EquipmentOrderSubTypeEnum.RAIL.code", "", "", "");

    public static EquipmentOrderSubTypeEnum getEnum(String inName) {
        return (EquipmentOrderSubTypeEnum) EquipmentOrderSubTypeEnum.getEnum(EquipmentOrderSubTypeEnum.class, (String)inName);
    }

    public static Map getEnumMap() {
        return EquipmentOrderSubTypeEnum.getEnumMap(EquipmentOrderSubTypeEnum.class);
    }

    public static List getEnumList() {
        return EquipmentOrderSubTypeEnum.getEnumList(EquipmentOrderSubTypeEnum.class);
    }

    public static Collection getList() {
        return EquipmentOrderSubTypeEnum.getEnumList(EquipmentOrderSubTypeEnum.class);
    }

    public static Iterator iterator() {
        return EquipmentOrderSubTypeEnum.iterator(EquipmentOrderSubTypeEnum.class);
    }

    protected EquipmentOrderSubTypeEnum(String inKey, String inDescriptionResKey, String inCodeResKey, String inBackgroundColor, String inForegroundColor, String inIconIdPath) {
        super(inKey, inDescriptionResKey, inCodeResKey, inBackgroundColor, inForegroundColor, inIconIdPath);
    }

    public String getMappingClassName() {
        return "com.navis.argo.persistence.atoms.UserTypeEquipmentOrderSubTypeEnum";
    }
}

