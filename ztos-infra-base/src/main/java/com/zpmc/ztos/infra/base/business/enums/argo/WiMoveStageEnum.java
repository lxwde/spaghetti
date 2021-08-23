package com.zpmc.ztos.infra.base.business.enums.argo;

import com.zpmc.ztos.infra.base.common.enums.AtomizedEnum;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

//public enum WiMoveStageEnum {
//    NONE(1, "NONE", "", "", "NONE"),
//    PLANNED(2, "PLANNED", "", "", "PLANNED"),
//    FETCH_UNDERWAY(3, "FETCH_UNDERWAY", "", "", "FETCH_UNDERWAY"),
//    CARRY_READY(4, "CARRY_READY", "", "", "CARRY_READY"),
//    CARRY_UNDERWAY(5, "CARRY_UNDERWAY", "", "", "CARRY_UNDERWAY"),
//    CARRY_COMPLETE(6, "CARRY_COMPLETE", "", "", "CARRY_COMPLETE"),
//    PUT_UNDERWAY(7, "PUT_UNDERWAY", "", "", "PUT_UNDERWAY"),
//    PUT_COMPLETE(8, "PUT_COMPLETE", "", "", "PUT_COMPLETE"),
//    COMPLETE(9, "COMPLETE", "", "", "COMPLETE");
//    private final int key;
//    private final String code;
//    private final String desc;
//    private final String color;
//    private final String displayName;
//
//    WiMoveStageEnum(int key, String code, String desc, String color, String displayName) {
//        this.key = key;
//        this.code = code;
//        this.desc = desc;
//        this.color = color;
//        this.displayName = displayName;
//    }
//
//    public static List getEnumList() {
//        return EnumUtils.getEnumList(WiMoveStageEnum.class);
//    }
//
//    public static EnumSet getEnumSet() {
//        return EnumSet.allOf(WiMoveStageEnum.class);
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

public class WiMoveStageEnum
        extends AtomizedEnum {
    public static final WiMoveStageEnum NONE = new WiMoveStageEnum("NONE", "atom.WiMoveStageEnum.NONE.description", "atom.WiMoveStageEnum.NONE.code", "", "", "");
    public static final WiMoveStageEnum PLANNED = new WiMoveStageEnum("PLANNED", "atom.WiMoveStageEnum.PLANNED.description", "atom.WiMoveStageEnum.PLANNED.code", "", "", "");
    public static final WiMoveStageEnum FETCH_UNDERWAY = new WiMoveStageEnum("FETCH_UNDERWAY", "atom.WiMoveStageEnum.FETCH_UNDERWAY.description", "atom.WiMoveStageEnum.FETCH_UNDERWAY.code", "", "", "");
    public static final WiMoveStageEnum CARRY_READY = new WiMoveStageEnum("CARRY_READY", "atom.WiMoveStageEnum.CARRY_READY.description", "atom.WiMoveStageEnum.CARRY_READY.code", "", "", "");
    public static final WiMoveStageEnum CARRY_UNDERWAY = new WiMoveStageEnum("CARRY_UNDERWAY", "atom.WiMoveStageEnum.CARRY_UNDERWAY.description", "atom.WiMoveStageEnum.CARRY_UNDERWAY.code", "", "", "");
    public static final WiMoveStageEnum CARRY_COMPLETE = new WiMoveStageEnum("CARRY_COMPLETE", "atom.WiMoveStageEnum.CARRY_COMPLETE.description", "atom.WiMoveStageEnum.CARRY_COMPLETE.code", "", "", "");
    public static final WiMoveStageEnum PUT_UNDERWAY = new WiMoveStageEnum("PUT_UNDERWAY", "atom.WiMoveStageEnum.PUT_UNDERWAY.description", "atom.WiMoveStageEnum.PUT_UNDERWAY.code", "", "", "");
    public static final WiMoveStageEnum PUT_COMPLETE = new WiMoveStageEnum("PUT_COMPLETE", "atom.WiMoveStageEnum.PUT_COMPLETE.description", "atom.WiMoveStageEnum.PUT_COMPLETE.code", "", "", "");
    public static final WiMoveStageEnum COMPLETE = new WiMoveStageEnum("COMPLETE", "atom.WiMoveStageEnum.COMPLETE.description", "atom.WiMoveStageEnum.COMPLETE.code", "", "", "");

    public static WiMoveStageEnum getEnum(String inName) {
        return (WiMoveStageEnum) WiMoveStageEnum.getEnum(WiMoveStageEnum.class, (String)inName);
    }

    public static Map getEnumMap() {
        return WiMoveStageEnum.getEnumMap(WiMoveStageEnum.class);
    }

    public static List getEnumList() {
        return WiMoveStageEnum.getEnumList(WiMoveStageEnum.class);
    }

    public static Collection getList() {
        return WiMoveStageEnum.getEnumList(WiMoveStageEnum.class);
    }

    public static Iterator iterator() {
        return WiMoveStageEnum.iterator(WiMoveStageEnum.class);
    }

    protected WiMoveStageEnum(String inKey, String inDescriptionResKey, String inCodeResKey, String inBackgroundColor, String inForegroundColor, String inIconIdPath) {
        super(inKey, inDescriptionResKey, inCodeResKey, inBackgroundColor, inForegroundColor, inIconIdPath);
    }

    public String getMappingClassName() {
        return "com.navis.argo.persistence.atoms.UserTypeWiMoveStageEnum";
    }
}