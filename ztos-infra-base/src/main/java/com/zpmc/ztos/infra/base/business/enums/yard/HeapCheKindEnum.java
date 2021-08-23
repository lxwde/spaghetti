package com.zpmc.ztos.infra.base.business.enums.yard;

import org.apache.commons.lang.enums.EnumUtils;

import java.util.EnumSet;
import java.util.List;

public enum HeapCheKindEnum {
    STRADDLE_CARRIER(1, "STRADDLE_CARRIER", "", "", "STRADDLE_CARRIER"),
    FORKLIFT(2, "FORKLIFT", "", "", "FORKLIFT"),
    REACH_STACKER(3, "REACH_STACKER", "", "", "REACH_STACKER"),
    UNKNOWN(4, "UNKNOWN", "", "", "UNKNOWN");
    private final int key;
    private final String code;
    private final String desc;
    private final String color;
    private final String displayName;

    HeapCheKindEnum(int key, String code, String desc, String color, String displayName) {
        this.key = key;
        this.code = code;
        this.desc = desc;
        this.color = color;
        this.displayName = displayName;
    }

    public static List getEnumList() {
        return EnumUtils.getEnumList(HeapCheKindEnum.class);
    }

    public static EnumSet getEnumSet() {
        return EnumSet.allOf(HeapCheKindEnum.class);
    }

    public int getKey() {
        return key;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public String getColor() {
        return color;
    }

    public String getDisplayName() {
        return displayName;
    }

}