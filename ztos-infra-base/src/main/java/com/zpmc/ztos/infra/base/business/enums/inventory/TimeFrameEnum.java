package com.zpmc.ztos.infra.base.business.enums.inventory;

import org.apache.commons.lang.enums.EnumUtils;

import java.util.EnumSet;
import java.util.List;

public enum TimeFrameEnum {
    CURRENT(1, "CURRENT", "", "", "CURRENT"),
    IMMINENT(2, "IMMINENT", "", "", "IMMINENT"),
    FUTURE(3, "FUTURE", "", "", "FUTURE"),
    COMPOSITE(4, "COMPOSITE", "", "", "COMPOSITE");
    private final int key;
    private final String code;
    private final String desc;
    private final String color;
    private final String displayName;

    TimeFrameEnum(int key, String code, String desc, String color, String displayName) {
        this.key = key;
        this.code = code;
        this.desc = desc;
        this.color = color;
        this.displayName = displayName;
    }

    public static List getEnumList() {
        return EnumUtils.getEnumList(TimeFrameEnum.class);
    }

    public static EnumSet getEnumSet() {
        return EnumSet.allOf(TimeFrameEnum.class);
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