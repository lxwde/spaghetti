package com.zpmc.ztos.infra.base.business.enums.yard;

import org.apache.commons.lang.enums.EnumUtils;

import java.util.EnumSet;
import java.util.List;

public enum BerthRangeTypeEnum {
    DIVISION(1, "DIVISION", "", "", "DIVISION"),
    TERMINAL(2, "TERMINAL", "", "", "TERMINAL"),
    USER(3, "USER", "", "", "USER"),
    UNKNOWN(4, "UNKNOWN", "", "", "UNKNOWN");
    private final int key;
    private final String code;
    private final String desc;
    private final String color;
    private final String displayName;

    BerthRangeTypeEnum(int key, String code, String desc, String color, String displayName) {
        this.key = key;
        this.code = code;
        this.desc = desc;
        this.color = color;
        this.displayName = displayName;
    }

    public static List getEnumList() {
        return EnumUtils.getEnumList(BerthRangeTypeEnum.class);
    }

    public static EnumSet getEnumSet() {
        return EnumSet.allOf(BerthRangeTypeEnum.class);
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