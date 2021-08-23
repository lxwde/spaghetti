package com.zpmc.ztos.infra.base.business.enums.inventory;

import com.zpmc.ztos.infra.base.common.enums.AtomizedEnum;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

//public enum DoorDirectionEnum {
//    UNKNOWN(1, "UNKNOWN", "", "", "UNKNOWN"),
//    ANY(2, "ANY", "", "", "ANY"),
//    FWD(3, "FWD", "", "", "FWD"),
//    AFT(4, "AFT", "", "", "AFT"),
//    NORTH(5, "NORTH", "", "", "NORTH"),
//    SOUTH(6, "SOUTH", "", "", "SOUTH"),
//    EAST(7, "EAST", "", "", "EAST"),
//    WEST(8, "WEST", "", "", "WEST");
//    private final int key;
//    private final String code;
//    private final String desc;
//    private final String color;
//    private final String displayName;
//
//    DoorDirectionEnum(int key, String code, String desc, String color, String displayName) {
//        this.key = key;
//        this.code = code;
//        this.desc = desc;
//        this.color = color;
//        this.displayName = displayName;
//    }
//
//    public static List getEnumList() {
//        return EnumUtils.getEnumList(DoorDirectionEnum.class);
//    }
//
//    public static EnumSet getEnumSet() {
//        return EnumSet.allOf(DoorDirectionEnum.class);
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

public class DoorDirectionEnum
        extends AtomizedEnum {
    public static final DoorDirectionEnum UNKNOWN = new DoorDirectionEnum("U", "atom.DoorDirectionEnum.UNKNOWN.description", "atom.DoorDirectionEnum.UNKNOWN.code", "", "", "");
    public static final DoorDirectionEnum ANY = new DoorDirectionEnum("Y", "atom.DoorDirectionEnum.ANY.description", "atom.DoorDirectionEnum.ANY.code", "", "", "");
    public static final DoorDirectionEnum FWD = new DoorDirectionEnum("F", "atom.DoorDirectionEnum.FWD.description", "atom.DoorDirectionEnum.FWD.code", "", "", "");
    public static final DoorDirectionEnum AFT = new DoorDirectionEnum("A", "atom.DoorDirectionEnum.AFT.description", "atom.DoorDirectionEnum.AFT.code", "", "", "");
    public static final DoorDirectionEnum NORTH = new DoorDirectionEnum("N", "atom.DoorDirectionEnum.NORTH.description", "atom.DoorDirectionEnum.NORTH.code", "", "", "");
    public static final DoorDirectionEnum SOUTH = new DoorDirectionEnum("S", "atom.DoorDirectionEnum.SOUTH.description", "atom.DoorDirectionEnum.SOUTH.code", "", "", "");
    public static final DoorDirectionEnum EAST = new DoorDirectionEnum("E", "atom.DoorDirectionEnum.EAST.description", "atom.DoorDirectionEnum.EAST.code", "", "", "");
    public static final DoorDirectionEnum WEST = new DoorDirectionEnum("W", "atom.DoorDirectionEnum.WEST.description", "atom.DoorDirectionEnum.WEST.code", "", "", "");

    public static DoorDirectionEnum getEnum(String inName) {
        return (DoorDirectionEnum) DoorDirectionEnum.getEnum(DoorDirectionEnum.class, (String)inName);
    }

    public static Map getEnumMap() {
        return DoorDirectionEnum.getEnumMap(DoorDirectionEnum.class);
    }

    public static List getEnumList() {
        return DoorDirectionEnum.getEnumList(DoorDirectionEnum.class);
    }

    public static Collection getList() {
        return DoorDirectionEnum.getEnumList(DoorDirectionEnum.class);
    }

    public static Iterator iterator() {
        return DoorDirectionEnum.iterator(DoorDirectionEnum.class);
    }

    protected DoorDirectionEnum(String inKey, String inDescriptionResKey, String inCodeResKey, String inBackgroundColor, String inForegroundColor, String inIconIdPath) {
        super(inKey, inDescriptionResKey, inCodeResKey, inBackgroundColor, inForegroundColor, inIconIdPath);
    }

    public String getMappingClassName() {
        return "com.navis.inventory.persistence.atoms.UserTypeDoorDirectionEnum";
    }
}
