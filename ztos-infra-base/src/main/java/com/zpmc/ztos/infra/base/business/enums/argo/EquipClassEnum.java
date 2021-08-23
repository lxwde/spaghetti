package com.zpmc.ztos.infra.base.business.enums.argo;

import com.zpmc.ztos.infra.base.common.enums.AtomizedEnum;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

//public enum EquipClassEnum {
//    CONTAINER(1, "CONTAINER", "", "", "CONTAINER"),
//    CHASSIS(2, "CHASSIS", "", "", "CHASSIS"),
//    SEMITRAILER(3, "SEMITRAILER", "", "", "SEMITRAILER"),
//    MAFI(4, "MAFI", "", "", "MAFI"),
//    ACCESSORY(5, "ACCESSORY", "", "", "ACCESSORY");
//    private final int key;
//    private final String code;
//    private final String desc;
//    private final String color;
//    private final String displayName;
//
//    EquipClassEnum(int key, String code, String desc, String color, String displayName) {
//        this.key = key;
//        this.code = code;
//        this.desc = desc;
//        this.color = color;
//        this.displayName = displayName;
//    }
//
//    public static List getEnumList() {
//        return EnumUtils.getEnumList(EquipClassEnum.class);
//    }
//
//    public static EnumSet getEnumSet() {
//        return EnumSet.allOf(EquipClassEnum.class);
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

public class EquipClassEnum
        extends AtomizedEnum {
    public static final EquipClassEnum CONTAINER = new EquipClassEnum("CONTAINER", "atom.EquipClassEnum.CONTAINER.description", "atom.EquipClassEnum.CONTAINER.code", "bisque", "", "");
    public static final EquipClassEnum CHASSIS = new EquipClassEnum("CHASSIS", "atom.EquipClassEnum.CHASSIS.description", "atom.EquipClassEnum.CHASSIS.code", "lightblue", "", "");
    public static final EquipClassEnum SEMITRAILER = new EquipClassEnum("SEMITRAILER", "atom.EquipClassEnum.SEMITRAILER.description", "atom.EquipClassEnum.SEMITRAILER.code", "darkgreen", "", "");
    public static final EquipClassEnum MAFI = new EquipClassEnum("MAFI", "atom.EquipClassEnum.MAFI.description", "atom.EquipClassEnum.MAFI.code", "deeppink", "", "");
    public static final EquipClassEnum ACCESSORY = new EquipClassEnum("ACCESSORY", "atom.EquipClassEnum.ACCESSORY.description", "atom.EquipClassEnum.ACCESSORY.code", "darkturquoise", "", "");

    public static EquipClassEnum getEnum(String inName) {
        return (EquipClassEnum) EquipClassEnum.getEnum(EquipClassEnum.class, (String)inName);
    }

    public static Map getEnumMap() {
        return EquipClassEnum.getEnumMap(EquipClassEnum.class);
    }

    public static List getEnumList() {
        return EquipClassEnum.getEnumList(EquipClassEnum.class);
    }

    public static Collection getList() {
        return EquipClassEnum.getEnumList(EquipClassEnum.class);
    }

    public static Iterator iterator() {
        return EquipClassEnum.iterator(EquipClassEnum.class);
    }

    protected EquipClassEnum(String inKey, String inDescriptionResKey, String inCodeResKey, String inBackgroundColor, String inForegroundColor, String inIconIdPath) {
        super(inKey, inDescriptionResKey, inCodeResKey, inBackgroundColor, inForegroundColor, inIconIdPath);
    }

    public String getMappingClassName() {
        return "com.navis.argo.persistence.atoms.UserTypeEquipClassEnum";
    }
}
