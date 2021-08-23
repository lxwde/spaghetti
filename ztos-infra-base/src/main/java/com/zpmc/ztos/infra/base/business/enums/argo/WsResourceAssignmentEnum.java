package com.zpmc.ztos.infra.base.business.enums.argo;

import com.zpmc.ztos.infra.base.common.enums.AtomizedEnum;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

//public enum WsResourceAssignmentEnum {
//    POOLED(1, "POOLED", "", "", "POOLED"),
//    ASSIGNED(2, "ASSIGNED", "", "", "ASSIGNED");
//    private final int key;
//    private final String code;
//    private final String desc;
//    private final String color;
//    private final String displayName;
//
//    WsResourceAssignmentEnum(int key, String code, String desc, String color, String displayName) {
//        this.key = key;
//        this.code = code;
//        this.desc = desc;
//        this.color = color;
//        this.displayName = displayName;
//    }
//
//    public static List getEnumList() {
//        return EnumUtils.getEnumList(WsResourceAssignmentEnum.class);
//    }
//
//    public static EnumSet getEnumSet() {
//        return EnumSet.allOf(WsResourceAssignmentEnum.class);
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

public class WsResourceAssignmentEnum
        extends AtomizedEnum {
    public static final WsResourceAssignmentEnum POOLED = new WsResourceAssignmentEnum("POOLED", "atom.WsResourceAssignmentEnum.POOLED.description", "atom.WsResourceAssignmentEnum.POOLED.code", "", "", "");
    public static final WsResourceAssignmentEnum ASSIGNED = new WsResourceAssignmentEnum("ASSIGNED", "atom.WsResourceAssignmentEnum.ASSIGNED.description", "atom.WsResourceAssignmentEnum.ASSIGNED.code", "", "", "");

    public static WsResourceAssignmentEnum getEnum(String inName) {
        return (WsResourceAssignmentEnum) WsResourceAssignmentEnum.getEnum(WsResourceAssignmentEnum.class, (String)inName);
    }

    public static Map getEnumMap() {
        return WsResourceAssignmentEnum.getEnumMap(WsResourceAssignmentEnum.class);
    }

    public static List getEnumList() {
        return WsResourceAssignmentEnum.getEnumList(WsResourceAssignmentEnum.class);
    }

    public static Collection getList() {
        return WsResourceAssignmentEnum.getEnumList(WsResourceAssignmentEnum.class);
    }

    public static Iterator iterator() {
        return WsResourceAssignmentEnum.iterator(WsResourceAssignmentEnum.class);
    }

    protected WsResourceAssignmentEnum(String inKey, String inDescriptionResKey, String inCodeResKey, String inBackgroundColor, String inForegroundColor, String inIconIdPath) {
        super(inKey, inDescriptionResKey, inCodeResKey, inBackgroundColor, inForegroundColor, inIconIdPath);
    }

    public String getMappingClassName() {
        return "com.navis.argo.persistence.atoms.UserTypeWsResourceAssignmentEnum";
    }
}
