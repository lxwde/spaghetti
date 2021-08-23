package com.zpmc.ztos.infra.base.business.enums.inventory;

import com.zpmc.ztos.infra.base.common.enums.AtomizedEnum;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

//public enum StorageStartEndDayRuleTypeEnum {
//    NO_RULE(-5, "NO_RULE", "", "", "NO_RULE"),
//    YARD_IN(-4, "YARD_IN", "", "", "YARD_IN"),
//    DIS_DONE(-3, "DIS_DONE", "", "", "DIS_DONE"),
//    FFD(-2, "FFD", "", "", "FFD"),
//    FAD(-1, "FAD", "", "", "FAD"),
//    COMPLEX_IN(0, "COMPLEX_IN", "", "", "COMPLEX_IN"),
//    COMPLEX_OUT(1, "COMPLEX_OUT", "", "", "COMPLEX_OUT"),
//    POWER_CONNECT(2, "POWER_CONNECT", "", "", "POWER_CONNECT"),
//    ATA(3, "ATA", "", "", "ATA"),
//    ATD(4, "ATD", "", "", "ATD"),
//    START_WORK(5, "START_WORK", "", "", "START_WORK"),
//    END_WORK(6, "END_WORK", "", "", "END_WORK"),
//    YARD_OUT(7, "YARD_OUT", "", "", "YARD_OUT"),
//    OB_CARRIER_WORK_START(8, "OB_CARRIER_WORK_START", "", "", "OB_CARRIER_WORK_START"),
//    ETA(9, "ETA", "", "", "ETA"),
//    POWER_DISCONNECT(10, "POWER_DISCONNECT", "", "", "POWER_DISCONNECT"),
//    TIME_LABOR_ON_BOARD(11, "TIME_LABOR_ON_BOARD", "", "", "TIME_LABOR_ON_BOARD"),
//    TIME_OF_LOADING(12, "TIME_OF_LOADING", "", "", "TIME_OF_LOADING"),
//    CUSTOM(13, "CUSTOM", "", "", "CUSTOM");
//    private final int key;
//    private final String code;
//    private final String desc;
//    private final String color;
//    private final String displayName;
//
//    StorageStartEndDayRuleTypeEnum(int key, String code, String desc, String color, String displayName) {
//        this.key = key;
//        this.code = code;
//        this.desc = desc;
//        this.color = color;
//        this.displayName = displayName;
//    }
//
//    public static List getEnumList() {
//        return EnumUtils.getEnumList(StorageStartEndDayRuleTypeEnum.class);
//    }
//
//    public static EnumSet getEnumSet() {
//        return EnumSet.allOf(StorageStartEndDayRuleTypeEnum.class);
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

public class StorageStartEndDayRuleTypeEnum
        extends AtomizedEnum {
    public static final StorageStartEndDayRuleTypeEnum NO_RULE = new StorageStartEndDayRuleTypeEnum("NO_RULE", "atom.StorageStartEndDayRuleTypeEnum.NO_RULE.description", "atom.StorageStartEndDayRuleTypeEnum.NO_RULE.code", "", "", "");
    public static final StorageStartEndDayRuleTypeEnum YARD_IN = new StorageStartEndDayRuleTypeEnum("YARD_IN", "atom.StorageStartEndDayRuleTypeEnum.YARD_IN.description", "atom.StorageStartEndDayRuleTypeEnum.YARD_IN.code", "", "", "");
    public static final StorageStartEndDayRuleTypeEnum DIS_DONE = new StorageStartEndDayRuleTypeEnum("DIS_DONE", "atom.StorageStartEndDayRuleTypeEnum.DIS_DONE.description", "atom.StorageStartEndDayRuleTypeEnum.DIS_DONE.code", "", "", "");
    public static final StorageStartEndDayRuleTypeEnum FFD = new StorageStartEndDayRuleTypeEnum("FFD", "atom.StorageStartEndDayRuleTypeEnum.FFD.description", "atom.StorageStartEndDayRuleTypeEnum.FFD.code", "", "", "");
    public static final StorageStartEndDayRuleTypeEnum FAD = new StorageStartEndDayRuleTypeEnum("FAD", "atom.StorageStartEndDayRuleTypeEnum.FAD.description", "atom.StorageStartEndDayRuleTypeEnum.FAD.code", "", "", "");
    public static final StorageStartEndDayRuleTypeEnum COMPLEX_IN = new StorageStartEndDayRuleTypeEnum("COMPLEX_IN", "atom.StorageStartEndDayRuleTypeEnum.COMPLEX_IN.description", "atom.StorageStartEndDayRuleTypeEnum.COMPLEX_IN.code", "", "", "");
    public static final StorageStartEndDayRuleTypeEnum COMPLEX_OUT = new StorageStartEndDayRuleTypeEnum("COMPLEX_OUT", "atom.StorageStartEndDayRuleTypeEnum.COMPLEX_OUT.description", "atom.StorageStartEndDayRuleTypeEnum.COMPLEX_OUT.code", "", "", "");
    public static final StorageStartEndDayRuleTypeEnum POWER_CONNECT = new StorageStartEndDayRuleTypeEnum("POWER_CONNECT", "atom.StorageStartEndDayRuleTypeEnum.POWER_CONNECT.description", "atom.StorageStartEndDayRuleTypeEnum.POWER_CONNECT.code", "", "", "");
    public static final StorageStartEndDayRuleTypeEnum ATA = new StorageStartEndDayRuleTypeEnum("ATA", "atom.StorageStartEndDayRuleTypeEnum.ATA.description", "atom.StorageStartEndDayRuleTypeEnum.ATA.code", "", "", "");
    public static final StorageStartEndDayRuleTypeEnum ATD = new StorageStartEndDayRuleTypeEnum("ATD", "atom.StorageStartEndDayRuleTypeEnum.ATD.description", "atom.StorageStartEndDayRuleTypeEnum.ATD.code", "", "", "");
    public static final StorageStartEndDayRuleTypeEnum START_WORK = new StorageStartEndDayRuleTypeEnum("START_WORK", "atom.StorageStartEndDayRuleTypeEnum.START_WORK.description", "atom.StorageStartEndDayRuleTypeEnum.START_WORK.code", "", "", "");
    public static final StorageStartEndDayRuleTypeEnum END_WORK = new StorageStartEndDayRuleTypeEnum("END_WORK", "atom.StorageStartEndDayRuleTypeEnum.END_WORK.description", "atom.StorageStartEndDayRuleTypeEnum.END_WORK.code", "", "", "");
    public static final StorageStartEndDayRuleTypeEnum YARD_OUT = new StorageStartEndDayRuleTypeEnum("YARD_OUT", "atom.StorageStartEndDayRuleTypeEnum.YARD_OUT.description", "atom.StorageStartEndDayRuleTypeEnum.YARD_OUT.code", "", "", "");
    public static final StorageStartEndDayRuleTypeEnum OB_CARRIER_WORK_START = new StorageStartEndDayRuleTypeEnum("OB_CARRIER_WORK_START", "atom.StorageStartEndDayRuleTypeEnum.OB_CARRIER_WORK_START.description", "atom.StorageStartEndDayRuleTypeEnum.OB_CARRIER_WORK_START.code", "", "", "");
    public static final StorageStartEndDayRuleTypeEnum ETA = new StorageStartEndDayRuleTypeEnum("ETA", "atom.StorageStartEndDayRuleTypeEnum.ETA.description", "atom.StorageStartEndDayRuleTypeEnum.ETA.code", "", "", "");
    public static final StorageStartEndDayRuleTypeEnum POWER_DISCONNECT = new StorageStartEndDayRuleTypeEnum("POWER_DISCONNECT", "atom.StorageStartEndDayRuleTypeEnum.POWER_DISCONNECT.description", "atom.StorageStartEndDayRuleTypeEnum.POWER_DISCONNECT.code", "", "", "");
    public static final StorageStartEndDayRuleTypeEnum TIME_LABOR_ON_BOARD = new StorageStartEndDayRuleTypeEnum("TIME_LABOR_ON_BOARD", "atom.StorageStartEndDayRuleTypeEnum.TIME_LABOR_ON_BOARD.description", "atom.StorageStartEndDayRuleTypeEnum.TIME_LABOR_ON_BOARD.code", "", "", "");
    public static final StorageStartEndDayRuleTypeEnum TIME_OF_LOADING = new StorageStartEndDayRuleTypeEnum("TIME_OF_LOADING", "atom.StorageStartEndDayRuleTypeEnum.TIME_OF_LOADING.description", "atom.StorageStartEndDayRuleTypeEnum.TIME_OF_LOADING.code", "", "", "");
    public static final StorageStartEndDayRuleTypeEnum CUSTOM = new StorageStartEndDayRuleTypeEnum("CUSTOM", "atom.StorageStartEndDayRuleTypeEnum.CUSTOM.description", "atom.StorageStartEndDayRuleTypeEnum.CUSTOM.code", "", "", "");

    public static StorageStartEndDayRuleTypeEnum getEnum(String inName) {
        return (StorageStartEndDayRuleTypeEnum) StorageStartEndDayRuleTypeEnum.getEnum(StorageStartEndDayRuleTypeEnum.class, (String)inName);
    }

    public static Map getEnumMap() {
        return StorageStartEndDayRuleTypeEnum.getEnumMap(StorageStartEndDayRuleTypeEnum.class);
    }

    public static List getEnumList() {
        return StorageStartEndDayRuleTypeEnum.getEnumList(StorageStartEndDayRuleTypeEnum.class);
    }

    public static Collection getList() {
        return StorageStartEndDayRuleTypeEnum.getEnumList(StorageStartEndDayRuleTypeEnum.class);
    }

    public static Iterator iterator() {
        return StorageStartEndDayRuleTypeEnum.iterator(StorageStartEndDayRuleTypeEnum.class);
    }

    protected StorageStartEndDayRuleTypeEnum(String inKey, String inDescriptionResKey, String inCodeResKey, String inBackgroundColor, String inForegroundColor, String inIconIdPath) {
        super(inKey, inDescriptionResKey, inCodeResKey, inBackgroundColor, inForegroundColor, inIconIdPath);
    }

    public String getMappingClassName() {
        return "com.navis.inventory.persistence.atoms.UserTypeStorageStartEndDayRuleTypeEnum";
    }
}

