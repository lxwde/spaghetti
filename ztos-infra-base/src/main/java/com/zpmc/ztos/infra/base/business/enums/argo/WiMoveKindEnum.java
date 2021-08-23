package com.zpmc.ztos.infra.base.business.enums.argo;

import com.zpmc.ztos.infra.base.common.enums.AtomizedEnum;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

//public enum WiMoveKindEnum {
//    Receival(1, "Receival", "", "", "Receival"),
//    Delivery(2, "Delivery", "", "", "Delivery"),
//    VeslDisch(3, "VeslDisch", "", "", "VeslDisch"),
//    VeslLoad(4, "VeslLoad", "", "", "VeslLoad"),
//    RailDisch(5, "RailDisch", "", "", "RailDisch"),
//    RailLoad(6, "RailLoad", "", "", "RailLoad"),
//    ShiftOnBoard(7, "ShiftOnBoard", "", "", "ShiftOnBoard"),
//    YardMove(8, "YardMove", "", "", "YardMove"),
//    YardShift(9, "YardShift", "", "", "YardShift"),
//    Other(10, "Other", "", "", "Other");
//    private final int key;
//    private final String code;
//    private final String desc;
//    private final String color;
//    private final String displayName;
//
//    WiMoveKindEnum(int key, String code, String desc, String color, String displayName) {
//        this.key = key;
//        this.code = code;
//        this.desc = desc;
//        this.color = color;
//        this.displayName = displayName;
//    }
//
//    public static List getEnumList() {
//        return EnumUtils.getEnumList(WiMoveKindEnum.class);
//    }
//
//    public static EnumSet getEnumSet() {
//        return EnumSet.allOf(WiMoveKindEnum.class);
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


public class WiMoveKindEnum
        extends AtomizedEnum {
    public static final WiMoveKindEnum Receival = new WiMoveKindEnum("RECV", "atom.WiMoveKindEnum.Receival.description", "atom.WiMoveKindEnum.Receival.code", "darkorange", "", "");
    public static final WiMoveKindEnum Delivery = new WiMoveKindEnum("DLVR", "atom.WiMoveKindEnum.Delivery.description", "atom.WiMoveKindEnum.Delivery.code", "chartreuse", "", "");
    public static final WiMoveKindEnum VeslDisch = new WiMoveKindEnum("DSCH", "atom.WiMoveKindEnum.VeslDisch.description", "atom.WiMoveKindEnum.VeslDisch.code", "darkgoldenrod", "", "");
    public static final WiMoveKindEnum VeslLoad = new WiMoveKindEnum("LOAD", "atom.WiMoveKindEnum.VeslLoad.description", "atom.WiMoveKindEnum.VeslLoad.code", "deepskyblue", "", "");
    public static final WiMoveKindEnum RailDisch = new WiMoveKindEnum("RDSC", "atom.WiMoveKindEnum.RailDisch.description", "atom.WiMoveKindEnum.RailDisch.code", "crimson", "", "");
    public static final WiMoveKindEnum RailLoad = new WiMoveKindEnum("RLOD", "atom.WiMoveKindEnum.RailLoad.description", "atom.WiMoveKindEnum.RailLoad.code", "darkviolet", "", "");
    public static final WiMoveKindEnum ShiftOnBoard = new WiMoveKindEnum("SHOB", "atom.WiMoveKindEnum.ShiftOnBoard.description", "atom.WiMoveKindEnum.ShiftOnBoard.code", "sandybrown", "", "");
    public static final WiMoveKindEnum YardMove = new WiMoveKindEnum("YARD", "atom.WiMoveKindEnum.YardMove.description", "atom.WiMoveKindEnum.YardMove.code", "palegreen", "", "");
    public static final WiMoveKindEnum YardShift = new WiMoveKindEnum("SHFT", "atom.WiMoveKindEnum.YardShift.description", "atom.WiMoveKindEnum.YardShift.code", "mediumseagreen", "", "");
    public static final WiMoveKindEnum Other = new WiMoveKindEnum("OTHR", "atom.WiMoveKindEnum.Other.description", "atom.WiMoveKindEnum.Other.code", "lightslategray", "", "");

    public static WiMoveKindEnum getEnum(String inName) {
        return (WiMoveKindEnum) WiMoveKindEnum.getEnum(WiMoveKindEnum.class, (String)inName);
    }

    public static Map getEnumMap() {
        return WiMoveKindEnum.getEnumMap(WiMoveKindEnum.class);
    }

    public static List getEnumList() {
        return WiMoveKindEnum.getEnumList(WiMoveKindEnum.class);
    }

    public static Collection getList() {
        return WiMoveKindEnum.getEnumList(WiMoveKindEnum.class);
    }

    public static Iterator iterator() {
        return WiMoveKindEnum.iterator(WiMoveKindEnum.class);
    }

    protected WiMoveKindEnum(String inKey, String inDescriptionResKey, String inCodeResKey, String inBackgroundColor, String inForegroundColor, String inIconIdPath) {
        super(inKey, inDescriptionResKey, inCodeResKey, inBackgroundColor, inForegroundColor, inIconIdPath);
    }

    public String getMappingClassName() {
        return "com.navis.argo.persistence.atoms.UserTypeWiMoveKindEnum";
    }
}

