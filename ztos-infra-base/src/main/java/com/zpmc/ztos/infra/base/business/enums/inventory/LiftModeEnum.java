package com.zpmc.ztos.infra.base.business.enums.inventory;

import com.zpmc.ztos.infra.base.common.enums.AtomizedEnum;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

//public enum LiftModeEnum {
//    UNKNOWN(1, "UNKNOWN", "", "", "UNKNOWN"),
//    SINGLE(2, "SINGLE", "", "", "SINGLE"),
//    TWIN(3, "TWIN", "", "", "TWIN"),
//    TANDEM(4, "TANDEM", "", "", "TANDEM"),
//    QUAD(5, "QUAD", "", "", "QUAD");
//    private final int key;
//    private final String code;
//    private final String desc;
//    private final String color;
//    private final String displayName;
//
//    LiftModeEnum(int key, String code, String desc, String color, String displayName) {
//        this.key = key;
//        this.code = code;
//        this.desc = desc;
//        this.color = color;
//        this.displayName = displayName;
//    }
//
//    public static List getEnumList() {
//        return EnumUtils.getEnumList(LiftModeEnum.class);
//    }
//
//    public static EnumSet getEnumSet() {
//        return EnumSet.allOf(LiftModeEnum.class);
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

public class LiftModeEnum
        extends AtomizedEnum {
    public static final LiftModeEnum UNKNOWN = new LiftModeEnum("UNKNOWN", "atom.LiftModeEnum.UNKNOWN.description", "atom.LiftModeEnum.UNKNOWN.code", "", "", "");
    public static final LiftModeEnum SINGLE = new LiftModeEnum("SINGLE", "atom.LiftModeEnum.SINGLE.description", "atom.LiftModeEnum.SINGLE.code", "", "", "");
    public static final LiftModeEnum TWIN = new LiftModeEnum("TWIN", "atom.LiftModeEnum.TWIN.description", "atom.LiftModeEnum.TWIN.code", "", "", "");
    public static final LiftModeEnum TANDEM = new LiftModeEnum("TANDEM", "atom.LiftModeEnum.TANDEM.description", "atom.LiftModeEnum.TANDEM.code", "", "", "");
    public static final LiftModeEnum QUAD = new LiftModeEnum("QUAD", "atom.LiftModeEnum.QUAD.description", "atom.LiftModeEnum.QUAD.code", "", "", "");

    public static LiftModeEnum getEnum(String inName) {
        return (LiftModeEnum) LiftModeEnum.getEnum(LiftModeEnum.class, (String)inName);
    }

    public static Map getEnumMap() {
        return LiftModeEnum.getEnumMap(LiftModeEnum.class);
    }

    public static List getEnumList() {
        return LiftModeEnum.getEnumList(LiftModeEnum.class);
    }

    public static Collection getList() {
        return LiftModeEnum.getEnumList(LiftModeEnum.class);
    }

    public static Iterator iterator() {
        return LiftModeEnum.iterator(LiftModeEnum.class);
    }

    protected LiftModeEnum(String inKey, String inDescriptionResKey, String inCodeResKey, String inBackgroundColor, String inForegroundColor, String inIconIdPath) {
        super(inKey, inDescriptionResKey, inCodeResKey, inBackgroundColor, inForegroundColor, inIconIdPath);
    }

    public String getMappingClassName() {
        return "com.navis.inventory.persistence.atoms.UserTypeLiftModeEnum";
    }
}