package com.zpmc.ztos.infra.base.business.enums.argo;

import com.zpmc.ztos.infra.base.common.enums.AtomizedEnum;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

//public enum CheOperatingModeEnum {
//    UNKNOWN(1, "UNKNOWN", "unknown", "", "UNKNOWN"),
//    REQUIREHANDLER(2, "REQUIREHANDLER", "require handler", "", "REQUIREHANDLER"),
//    SELFCOMPLETE(3, "SELFCOMPLETE", "self complete", "", "SELFCOMPLETE"),
//    TRUCK(4, "TRUCK", "truck", "", "TRUCK"),
//    CRANE(5, "CRANE", "cran", "", "CRANE"),
//    TRUCKSHIFTCHASSIS(6, "TRUCKSHIFTCHASSIS", "truck shift chassis", "", "TRUCKSHIFTCHASSIS"),
//    TRUCKCOUPLED(7, "TRUCKCOUPLED", "truck coupled", "", "TRUCKCOUPLED");
//    private final int key;
//    private final String code;
//    private final String desc;
//    private final String color;
//    private final String displayName;
//
//    CheOperatingModeEnum(int key, String code, String desc, String color, String displayName) {
//        this.key = key;
//        this.code = code;
//        this.desc = desc;
//        this.color = color;
//        this.displayName = displayName;
//    }
//
//    public static List getEnumList() {
//        return EnumUtils.getEnumList(CheOperatingModeEnum.class);
//    }
//
//    public static EnumSet getEnumSet() {
//        return EnumSet.allOf(CheOperatingModeEnum.class);
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

public class CheOperatingModeEnum
        extends AtomizedEnum {
    public static final CheOperatingModeEnum UNKNOWN = new CheOperatingModeEnum("UNKNOWN", "atom.CheOperatingModeEnum.UNKNOWN.description", "atom.CheOperatingModeEnum.UNKNOWN.code", "", "", "");
    public static final CheOperatingModeEnum REQUIREHANDLER = new CheOperatingModeEnum("REQUIREHANDLER", "atom.CheOperatingModeEnum.REQUIREHANDLER.description", "atom.CheOperatingModeEnum.REQUIREHANDLER.code", "", "", "");
    public static final CheOperatingModeEnum SELFCOMPLETE = new CheOperatingModeEnum("SELFCOMPLETE", "atom.CheOperatingModeEnum.SELFCOMPLETE.description", "atom.CheOperatingModeEnum.SELFCOMPLETE.code", "", "", "");
    public static final CheOperatingModeEnum TRUCK = new CheOperatingModeEnum("TRUCK", "atom.CheOperatingModeEnum.TRUCK.description", "atom.CheOperatingModeEnum.TRUCK.code", "", "", "");
    public static final CheOperatingModeEnum CRANE = new CheOperatingModeEnum("CRANE", "atom.CheOperatingModeEnum.CRANE.description", "atom.CheOperatingModeEnum.CRANE.code", "", "", "");
    public static final CheOperatingModeEnum TRUCKSHIFTCHASSIS = new CheOperatingModeEnum("TRUCKSHIFTCHASSIS", "atom.CheOperatingModeEnum.TRUCKSHIFTCHASSIS.description", "atom.CheOperatingModeEnum.TRUCKSHIFTCHASSIS.code", "", "", "");
    public static final CheOperatingModeEnum TRUCKCOUPLED = new CheOperatingModeEnum("TRUCKCOUPLED", "atom.CheOperatingModeEnum.TRUCKCOUPLED.description", "atom.CheOperatingModeEnum.TRUCKCOUPLED.code", "", "", "");

    public static CheOperatingModeEnum getEnum(String inName) {
        return (CheOperatingModeEnum) CheOperatingModeEnum.getEnum(CheOperatingModeEnum.class, (String)inName);
    }

    public static Map getEnumMap() {
        return CheOperatingModeEnum.getEnumMap(CheOperatingModeEnum.class);
    }

    public static List getEnumList() {
        return CheOperatingModeEnum.getEnumList(CheOperatingModeEnum.class);
    }

    public static Collection getList() {
        return CheOperatingModeEnum.getEnumList(CheOperatingModeEnum.class);
    }

    public static Iterator iterator() {
        return CheOperatingModeEnum.iterator(CheOperatingModeEnum.class);
    }

    protected CheOperatingModeEnum(String inKey, String inDescriptionResKey, String inCodeResKey, String inBackgroundColor, String inForegroundColor, String inIconIdPath) {
        super(inKey, inDescriptionResKey, inCodeResKey, inBackgroundColor, inForegroundColor, inIconIdPath);
    }

    public String getMappingClassName() {
        return "com.navis.argo.persistence.atoms.UserTypeCheOperatingModeEnum";
    }
}

