package com.zpmc.ztos.infra.base.business.enums.road;

import org.apache.commons.lang.enums.EnumUtils;

import java.util.EnumSet;
import java.util.List;

public enum TruckVisitStatusEnum {
    OK(1, "OK", "", "", "OK"),
    TROUBLE(2, "TROUBLE", "", "", "TROUBLE"),
    COMPLETE(3, "COMPLETE", "", "", "COMPLETE"),
    CANCEL(4, "CANCEL", "", "", "CANCEL"),
    CLOSED(5, "CLOSED", "", "", "CLOSED");
    private final int key;
    private final String code;
    private final String desc;
    private final String color;
    private final String displayName;

    TruckVisitStatusEnum(int key, String code, String desc, String color, String displayName) {
        this.key = key;
        this.code = code;
        this.desc = desc;
        this.color = color;
        this.displayName = displayName;
    }

    public static List getEnumList() {
        return EnumUtils.getEnumList(TruckVisitStatusEnum.class);
    }

    public static EnumSet getEnumSet() {
        return EnumSet.allOf(TruckVisitStatusEnum.class);
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