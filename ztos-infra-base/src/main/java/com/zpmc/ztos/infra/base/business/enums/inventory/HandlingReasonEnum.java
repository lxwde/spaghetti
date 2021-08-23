package com.zpmc.ztos.infra.base.business.enums.inventory;

import org.apache.commons.lang.enums.EnumUtils;

import java.util.EnumSet;
import java.util.List;

public enum HandlingReasonEnum {
    CTROP(1, "CTROP", "", "", "CTROP"),
    LINEOP(2, "LINEOP", "", "", "LINEOP"),
    TERMINAL(3, "TERMINAL", "", "", "TERMINAL"),
    CUSTOMS(4, "CUSTOMS", "", "", "CUSTOMS"),
    VESSELOP(5, "VESSELOP", "", "", "VESSELOP");
    private final int key;
    private final String code;
    private final String desc;
    private final String color;
    private final String displayName;

    HandlingReasonEnum(int key, String code, String desc, String color, String displayName) {
        this.key = key;
        this.code = code;
        this.desc = desc;
        this.color = color;
        this.displayName = displayName;
    }

    public static List getEnumList() {
        return EnumUtils.getEnumList(HandlingReasonEnum.class);
    }

    public static EnumSet getEnumSet() {
        return EnumSet.allOf(HandlingReasonEnum.class);
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