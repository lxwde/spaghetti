package com.zpmc.ztos.infra.base.business.enums.argo;

import com.zpmc.ztos.infra.base.common.enums.AtomizedEnum;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

//public enum WsWorkStateEnum {
//    UNINITIALIZED(1, "UNINITIALIZED", "", "", "UNINITIALIZED"),
//    PLANNED(2, "PLANNED", "", "", "PLANNED"),
//    WORKING(3, "WORKING", "", "", "WORKING"),
//    SUSPENDED(4, "SUSPENDED", "", "", "SUSPENDED"),
//    COMPLETED(5, "COMPLETED", "", "", "COMPLETED"),
//    ISSPECIALDEFAULT(6, "ISSPECIALDEFAULT", "", "", "ISSPECIALDEFAULT"),
//    ISTEMPLATE(7, "ISTEMPLATE", "", "", "ISTEMPLATE"),
//    ISPROPOSED(8, "ISPROPOSED", "", "", "ISPROPOSED");
//    private final int key;
//    private final String code;
//    private final String desc;
//    private final String color;
//    private final String displayName;
//
//    WsWorkStateEnum(int key, String code, String desc, String color, String displayName) {
//        this.key = key;
//        this.code = code;
//        this.desc = desc;
//        this.color = color;
//        this.displayName = displayName;
//    }
//
//    public static List getEnumList() {
//        return EnumUtils.getEnumList(WsWorkStateEnum.class);
//    }
//
//    public static EnumSet getEnumSet() {
//        return EnumSet.allOf(WsWorkStateEnum.class);
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

public class WsWorkStateEnum
        extends AtomizedEnum {
    public static final WsWorkStateEnum UNINITIALIZED = new WsWorkStateEnum("UNINITIALIZED", "atom.WsWorkStateEnum.UNINITIALIZED.description", "atom.WsWorkStateEnum.UNINITIALIZED.code", "", "", "");
    public static final WsWorkStateEnum PLANNED = new WsWorkStateEnum("PLANNED", "atom.WsWorkStateEnum.PLANNED.description", "atom.WsWorkStateEnum.PLANNED.code", "", "", "");
    public static final WsWorkStateEnum WORKING = new WsWorkStateEnum("WORKING", "atom.WsWorkStateEnum.WORKING.description", "atom.WsWorkStateEnum.WORKING.code", "", "", "");
    public static final WsWorkStateEnum SUSPENDED = new WsWorkStateEnum("SUSPENDED", "atom.WsWorkStateEnum.SUSPENDED.description", "atom.WsWorkStateEnum.SUSPENDED.code", "", "", "");
    public static final WsWorkStateEnum COMPLETED = new WsWorkStateEnum("COMPLETED", "atom.WsWorkStateEnum.COMPLETED.description", "atom.WsWorkStateEnum.COMPLETED.code", "", "", "");
    public static final WsWorkStateEnum ISSPECIALDEFAULT = new WsWorkStateEnum("ISSPECIALDEFAULT", "atom.WsWorkStateEnum.ISSPECIALDEFAULT.description", "atom.WsWorkStateEnum.ISSPECIALDEFAULT.code", "", "", "");
    public static final WsWorkStateEnum ISTEMPLATE = new WsWorkStateEnum("ISTEMPLATE", "atom.WsWorkStateEnum.ISTEMPLATE.description", "atom.WsWorkStateEnum.ISTEMPLATE.code", "", "", "");
    public static final WsWorkStateEnum ISPROPOSED = new WsWorkStateEnum("ISPROPOSED", "atom.WsWorkStateEnum.ISPROPOSED.description", "atom.WsWorkStateEnum.ISPROPOSED.code", "", "", "");

    public static WsWorkStateEnum getEnum(String inName) {
        return (WsWorkStateEnum) WsWorkStateEnum.getEnum(WsWorkStateEnum.class, (String)inName);
    }

    public static Map getEnumMap() {
        return WsWorkStateEnum.getEnumMap(WsWorkStateEnum.class);
    }

    public static List getEnumList() {
        return WsWorkStateEnum.getEnumList(WsWorkStateEnum.class);
    }

    public static Collection getList() {
        return WsWorkStateEnum.getEnumList(WsWorkStateEnum.class);
    }

    public static Iterator iterator() {
        return WsWorkStateEnum.iterator(WsWorkStateEnum.class);
    }

    protected WsWorkStateEnum(String inKey, String inDescriptionResKey, String inCodeResKey, String inBackgroundColor, String inForegroundColor, String inIconIdPath) {
        super(inKey, inDescriptionResKey, inCodeResKey, inBackgroundColor, inForegroundColor, inIconIdPath);
    }

    public String getMappingClassName() {
        return "com.navis.argo.persistence.atoms.UserTypeWsWorkStateEnum";
    }
}
