package com.zpmc.ztos.infra.base.business.enums.road;

import org.apache.commons.lang.enums.EnumUtils;

import java.util.EnumSet;
import java.util.List;

public enum TruckVisitStatusGroupEnum {
    ALL(1, "ALL", "", "", "ALL"),
    ACTIVE_COMPLETE(2, "ACTIVE_COMPLETE", "", "", "ACTIVE_COMPLETE"),
    IN_PROGRESS(3, "IN_PROGRESS", "", "", "IN_PROGRESS");
    private final int key;
    private final String code;
    private final String desc;
    private final String color;
    private final String displayName;

    TruckVisitStatusGroupEnum(int key, String code, String desc, String color, String displayName) {
        this.key = key;
        this.code = code;
        this.desc = desc;
        this.color = color;
        this.displayName = displayName;
    }

    public static List getEnumList() {
        return EnumUtils.getEnumList(TruckVisitStatusGroupEnum.class);
    }

    public static EnumSet getEnumSet() {
        return EnumSet.allOf(TruckVisitStatusGroupEnum.class);
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