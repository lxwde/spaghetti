package com.zpmc.ztos.infra.base.business.enums.argo;

import com.zpmc.ztos.infra.base.common.enums.AtomizedEnum;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

//public enum PowDispatchModeEnum {
//    STOP(1, "STOP", "", "", "STOP"),
//    MANUAL(2, "MANUAL", "", "", "MANUAL"),
//    AUTO(3, "AUTO", "", "", "AUTO"),
//    CUSTOM(4, "CUSTOM", "", "", "CUSTOM"),
//    TRUCKSONLY(5, "TRUCKSONLY", "", "", "TRUCKSONLY"),
//    A4(6, "A4", "", "", "A4"),
//    OPTIMIZE(7, "OPTIMIZE", "", "", "OPTIMIZE"),
//    OPTIMIZETRUCKS(8, "OPTIMIZETRUCKS", "", "", "OPTIMIZETRUCKS");
//    private final int key;
//    private final String code;
//    private final String desc;
//    private final String color;
//    private final String displayName;
//
//    PowDispatchModeEnum(int key, String code, String desc, String color, String displayName) {
//        this.key = key;
//        this.code = code;
//        this.desc = desc;
//        this.color = color;
//        this.displayName = displayName;
//    }
//
//    public static List getEnumList() {
//        return EnumUtils.getEnumList(PowDispatchModeEnum.class);
//    }
//
//    public static EnumSet getEnumSet() {
//        return EnumSet.allOf(PowDispatchModeEnum.class);
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

public class PowDispatchModeEnum
        extends AtomizedEnum {
    public static final PowDispatchModeEnum STOP = new PowDispatchModeEnum("STOP", "atom.PowDispatchModeEnum.STOP.description", "atom.PowDispatchModeEnum.STOP.code", "", "", "");
    public static final PowDispatchModeEnum MANUAL = new PowDispatchModeEnum("MANUAL", "atom.PowDispatchModeEnum.MANUAL.description", "atom.PowDispatchModeEnum.MANUAL.code", "", "", "");
    public static final PowDispatchModeEnum AUTO = new PowDispatchModeEnum("AUTO", "atom.PowDispatchModeEnum.AUTO.description", "atom.PowDispatchModeEnum.AUTO.code", "", "", "");
    public static final PowDispatchModeEnum CUSTOM = new PowDispatchModeEnum("CUSTOM", "atom.PowDispatchModeEnum.CUSTOM.description", "atom.PowDispatchModeEnum.CUSTOM.code", "", "", "");
    public static final PowDispatchModeEnum TRUCKSONLY = new PowDispatchModeEnum("TRUCKSONLY", "atom.PowDispatchModeEnum.TRUCKSONLY.description", "atom.PowDispatchModeEnum.TRUCKSONLY.code", "", "", "");
    public static final PowDispatchModeEnum A4 = new PowDispatchModeEnum("A4", "atom.PowDispatchModeEnum.A4.description", "atom.PowDispatchModeEnum.A4.code", "", "", "");
    public static final PowDispatchModeEnum OPTIMIZE = new PowDispatchModeEnum("OPTIMIZE", "atom.PowDispatchModeEnum.OPTIMIZE.description", "atom.PowDispatchModeEnum.OPTIMIZE.code", "", "", "");
    public static final PowDispatchModeEnum OPTIMIZETRUCKS = new PowDispatchModeEnum("OPTIMIZETRUCKS", "atom.PowDispatchModeEnum.OPTIMIZETRUCKS.description", "atom.PowDispatchModeEnum.OPTIMIZETRUCKS.code", "", "", "");

    public static PowDispatchModeEnum getEnum(String inName) {
        return (PowDispatchModeEnum) PowDispatchModeEnum.getEnum(PowDispatchModeEnum.class, (String)inName);
    }

    public static Map getEnumMap() {
        return PowDispatchModeEnum.getEnumMap(PowDispatchModeEnum.class);
    }

    public static List getEnumList() {
        return PowDispatchModeEnum.getEnumList(PowDispatchModeEnum.class);
    }

    public static Collection getList() {
        return PowDispatchModeEnum.getEnumList(PowDispatchModeEnum.class);
    }

    public static Iterator iterator() {
        return PowDispatchModeEnum.iterator(PowDispatchModeEnum.class);
    }

    protected PowDispatchModeEnum(String inKey, String inDescriptionResKey, String inCodeResKey, String inBackgroundColor, String inForegroundColor, String inIconIdPath) {
        super(inKey, inDescriptionResKey, inCodeResKey, inBackgroundColor, inForegroundColor, inIconIdPath);
    }

    public String getMappingClassName() {
        return "com.navis.argo.persistence.atoms.UserTypePowDispatchModeEnum";
    }
}

