package com.zpmc.ztos.infra.base.business.enums.argo;

import org.apache.commons.lang.enums.EnumUtils;

import java.util.EnumSet;
import java.util.List;

public enum JobFrequencyEnum {
    ON_DEMAND(1, "ON_DEMAND", "", "", "ON_DEMAND"),
    BY_REPEAT_COUNT(2, "BY_REPEAT_COUNT", "", "", "BY_REPEAT_COUNT"),
    DAILY(3, "DAILY", "", "", "DAILY"),
    BY_DAY_OF_WEEK(4, "BY_DAY_OF_WEEK", "", "", "BY_DAY_OF_WEEK"),
    BY_DAY_OF_MONTH(5, "BY_DAY_OF_MONTH", "", "", "BY_DAY_OF_MONTH"),
    CRON_EXPRESSION(6, "CRON_EXPRESSION", "", "", "CRON_EXPRESSION");
    private final int key;
    private final String code;
    private final String desc;
    private final String color;
    private final String displayName;

    JobFrequencyEnum(int key, String code, String desc, String color, String displayName) {
        this.key = key;
        this.code = code;
        this.desc = desc;
        this.color = color;
        this.displayName = displayName;
    }

    public static List getEnumList() {
        return EnumUtils.getEnumList(JobFrequencyEnum.class);
    }

    public static EnumSet getEnumSet() {
        return EnumSet.allOf(JobFrequencyEnum.class);
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