package com.zpmc.ztos.infra.base.business.enums.yard;

import org.apache.commons.lang.enums.EnumUtils;

import java.util.EnumSet;
import java.util.List;

public enum YardBinAccessEnum {
    ONE_SIDE(1, "ONE_SIDE", "", "", "ONE_SIDE"),
    TWO_SIDES(2, "TWO_SIDES", "", "", "TWO_SIDES"),
    LOW(3, "LOW", "", "", "LOW"),
    HIGH(4, "HIGH", "", "", "HIGH"),
    ROW(5, "ROW", "", "", "ROW"),
    BLOCKED(6, "BLOCKED", "", "", "BLOCKED"),
    NONE(7, "NONE", "", "", "NONE"),
    UNKNOWN(8, "UNKNOWN", "", "", "UNKNOWN");
    private final int key;
    private final String code;
    private final String desc;
    private final String color;
    private final String displayName;

    YardBinAccessEnum(int key, String code, String desc, String color, String displayName) {
        this.key = key;
        this.code = code;
        this.desc = desc;
        this.color = color;
        this.displayName = displayName;
    }

    public static List getEnumList() {
        return EnumUtils.getEnumList(YardBinAccessEnum.class);
    }

    public static EnumSet getEnumSet() {
        return EnumSet.allOf(YardBinAccessEnum.class);
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