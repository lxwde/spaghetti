package com.zpmc.ztos.infra.base.business.enums.road;

import org.apache.commons.lang.enums.EnumUtils;

import java.util.EnumSet;
import java.util.List;

public enum GateTaskTimingEnum {
    PRE_STAGE(1, "PRE_STAGE", "", "", "PRE_STAGE"),
    VALIDATION(2, "VALIDATION", "", "", "VALIDATION"),
    ON_ARRIVAL(3, "ON_ARRIVAL", "", "", "ON_ARRIVAL"),
    AREA_DONE(4, "AREA_DONE", "", "", "AREA_DONE"),
    ON_SUBMIT(5, "ON_SUBMIT", "", "", "ON_SUBMIT"),
    ON_TROUBLE(6, "ON_TROUBLE", "", "", "ON_TROUBLE");
    private final int key;
    private final String code;
    private final String desc;
    private final String color;
    private final String displayName;

    GateTaskTimingEnum(int key, String code, String desc, String color, String displayName) {
        this.key = key;
        this.code = code;
        this.desc = desc;
        this.color = color;
        this.displayName = displayName;
    }

    public static List getEnumList() {
        return EnumUtils.getEnumList(GateTaskTimingEnum.class);
    }

    public static EnumSet getEnumSet() {
        return EnumSet.allOf(GateTaskTimingEnum.class);
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