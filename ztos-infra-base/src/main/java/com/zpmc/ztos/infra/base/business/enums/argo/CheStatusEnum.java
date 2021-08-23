package com.zpmc.ztos.infra.base.business.enums.argo;

import com.zpmc.ztos.infra.base.common.enums.AtomizedEnum;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

//public enum CheStatusEnum {
//    UNKNOWN(1, "UNKNOWN", "unknown", "", "UNKNOWN"),
//    WORKING(2, "WORKING", "working", "", "WORKING"),
//    UNAVAIL(3, "UNAVAIL", "unavailable", "", "UNAVAIL"),
//    WANTTOQUIT(4, "WANTTOQUIT", "want to quit", "", "WANTTOQUIT"),
//    OUTOFSERVICE(5, "OUTOFSERVICE", "out of service", "", "OUTOFSERVICE"),
//    ONBREAK(6, "ONBREAK", "on break", "", "ONBREAK"),
//    OFFLINEBREAKDOWN(7, "OFFLINEBREAKDOWN", "offline break down", "", "OFFLINEBREAKDOWN"),
//    OFFLINEMAINTENANCE(8, "OFFLINEMAINTENANCE", "offline maintenance", "", "OFFLINEMAINTENANCE"),
//    OFFLINESAFETY(9, "OFFLINESAFETY", "offline safety", "", "OFFLINESAFETY"),
//    ONLINEREPAIR(10, "ONLINEREPAIR", "online repair", "", "ONLINEREPAIR"),
//    ONLINEINIT(11, "ONLINEINIT", "online init", "", "ONLINEINIT"),
//    REHANDLE(12, "REHANDLE", "rehandle", "", "REHANDLE"),
//    MANUAL(13, "MANUAL", "manual", "", "MANUAL");
//    private final int key;
//    private final String code;
//    private final String desc;
//    private final String color;
//    private final String displayName;
//
//    CheStatusEnum(int key, String code, String desc, String color, String displayName) {
//        this.key = key;
//        this.code = code;
//        this.desc = desc;
//        this.color = color;
//        this.displayName = displayName;
//    }
//
//    public static List getEnumList() {
//        return EnumUtils.getEnumList(CheStatusEnum.class);
//    }
//
//    public static EnumSet getEnumSet() {
//        return EnumSet.allOf(CheStatusEnum.class);
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

public class CheStatusEnum
        extends AtomizedEnum {
    public static final CheStatusEnum UNKNOWN = new CheStatusEnum("UNKNOWN", "atom.CheStatusEnum.UNKNOWN.description", "atom.CheStatusEnum.UNKNOWN.code", "", "", "");
    public static final CheStatusEnum WORKING = new CheStatusEnum("WORKING", "atom.CheStatusEnum.WORKING.description", "atom.CheStatusEnum.WORKING.code", "", "", "");
    public static final CheStatusEnum UNAVAIL = new CheStatusEnum("UNAVAIL", "atom.CheStatusEnum.UNAVAIL.description", "atom.CheStatusEnum.UNAVAIL.code", "", "", "");
    public static final CheStatusEnum WANTTOQUIT = new CheStatusEnum("WANTTOQUIT", "atom.CheStatusEnum.WANTTOQUIT.description", "atom.CheStatusEnum.WANTTOQUIT.code", "", "", "");
    public static final CheStatusEnum OUTOFSERVICE = new CheStatusEnum("OUTOFSERVICE", "atom.CheStatusEnum.OUTOFSERVICE.description", "atom.CheStatusEnum.OUTOFSERVICE.code", "", "", "");
    public static final CheStatusEnum ONBREAK = new CheStatusEnum("ONBREAK", "atom.CheStatusEnum.ONBREAK.description", "atom.CheStatusEnum.ONBREAK.code", "", "", "");
    public static final CheStatusEnum OFFLINEBREAKDOWN = new CheStatusEnum("OFFLINEBREAKDOWN", "atom.CheStatusEnum.OFFLINEBREAKDOWN.description", "atom.CheStatusEnum.OFFLINEBREAKDOWN.code", "", "", "");
    public static final CheStatusEnum OFFLINEMAINTENANCE = new CheStatusEnum("OFFLINEMAINTENANCE", "atom.CheStatusEnum.OFFLINEMAINTENANCE.description", "atom.CheStatusEnum.OFFLINEMAINTENANCE.code", "", "", "");
    public static final CheStatusEnum OFFLINESAFETY = new CheStatusEnum("OFFLINESAFETY", "atom.CheStatusEnum.OFFLINESAFETY.description", "atom.CheStatusEnum.OFFLINESAFETY.code", "", "", "");
    public static final CheStatusEnum ONLINEREPAIR = new CheStatusEnum("ONLINEREPAIR", "atom.CheStatusEnum.ONLINEREPAIR.description", "atom.CheStatusEnum.ONLINEREPAIR.code", "", "", "");
    public static final CheStatusEnum ONLINEINIT = new CheStatusEnum("ONLINEINIT", "atom.CheStatusEnum.ONLINEINIT.description", "atom.CheStatusEnum.ONLINEINIT.code", "", "", "");
    public static final CheStatusEnum REHANDLE = new CheStatusEnum("REHANDLE", "atom.CheStatusEnum.REHANDLE.description", "atom.CheStatusEnum.REHANDLE.code", "", "", "");
    public static final CheStatusEnum MANUAL = new CheStatusEnum("MANUAL", "atom.CheStatusEnum.MANUAL.description", "atom.CheStatusEnum.MANUAL.code", "", "", "");

    public static CheStatusEnum getEnum(String inName) {
        return (CheStatusEnum) CheStatusEnum.getEnum(CheStatusEnum.class, (String)inName);
    }

    public static Map getEnumMap() {
        return CheStatusEnum.getEnumMap(CheStatusEnum.class);
    }

    public static List getEnumList() {
        return CheStatusEnum.getEnumList(CheStatusEnum.class);
    }

    public static Collection getList() {
        return CheStatusEnum.getEnumList(CheStatusEnum.class);
    }

    public static Iterator iterator() {
        return CheStatusEnum.iterator(CheStatusEnum.class);
    }

    protected CheStatusEnum(String inKey, String inDescriptionResKey, String inCodeResKey, String inBackgroundColor, String inForegroundColor, String inIconIdPath) {
        super(inKey, inDescriptionResKey, inCodeResKey, inBackgroundColor, inForegroundColor, inIconIdPath);
    }

    public String getMappingClassName() {
        return "com.navis.argo.persistence.atoms.UserTypeCheStatusEnum";
    }
}
