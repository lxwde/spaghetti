package com.zpmc.ztos.infra.base.business.enums.core;

import org.apache.commons.lang.enums.EnumUtils;

import java.util.EnumSet;
import java.util.List;

public enum TimeUnitEnum {
    MILLISECONDS(1, "MILLISECONDS", "", "", "MILLISECONDS"),
    SECONDS(2, "WARNINGS", "", "", "WARNINGS"),
    MINUTES(3, "WARNINGS", "", "", "WARNINGS"),
    HOURS(4, "WARNINGS", "", "", "WARNINGS"),
    DAYS(5, "WARNINGS", "", "", "WARNINGS"),
    WEEKS(6, "WARNINGS", "", "", "WARNINGS"),
    MONTHS(7, "WARNINGS", "", "", "WARNINGS"),
    YEARS(8, "WARNINGS", "", "", "WARNINGS");
    private final int key;
    private final String code;
    private final String desc;
    private final String color;
    private final String displayName;

    TimeUnitEnum(int key, String code, String desc, String color, String displayName) {
        this.key = key;
        this.code = code;
        this.desc = desc;
        this.color = color;
        this.displayName = displayName;
    }

    public static List getEnumList() {
        return EnumUtils.getEnumList(TimeUnitEnum.class);
    }

    public static EnumSet getEnumSet() {
        return EnumSet.allOf(TimeUnitEnum.class);
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