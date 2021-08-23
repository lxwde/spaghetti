package com.zpmc.ztos.infra.base.business.enums.inventory;

import org.apache.commons.lang.enums.EnumUtils;

import java.util.EnumSet;
import java.util.List;

public enum FirstFreeDayRuleEnum {
    NO_RULE(1, "NO_RULE", "", "", "NO_RULE"),
    FFD(2, "FFD", "", "", "FFD"),
    FAD(3, "FAD", "", "", "FAD"),
    DISDONE(4, "DISDONE", "", "", "DISDONE"),
    YARD_IN(5, "YARD_IN", "", "", "YARD_IN"),
    YARD_IN_NO_EXTRA_DAYS(6, "YARD_IN_NO_EXTRA_DAYS", "", "", "YARD_IN_NO_EXTRA_DAYS"),
    YARD_IN_CARRIER_WORK(7, "YARD_IN_CARRIER_WORK", "", "", "YARD_IN_CARRIER_WORK"),
    YARD_IN_CARRIER_WORK_NO_EXTRA_DAYS(8, "YARD_IN_CARRIER_WORK_NO_EXTRA_DAYS", "", "", "YARD_IN_CARRIER_WORK_NO_EXTRA_DAYS");
    private final int key;
    private final String code;
    private final String desc;
    private final String color;
    private final String displayName;

    FirstFreeDayRuleEnum(int key, String code, String desc, String color, String displayName) {
        this.key = key;
        this.code = code;
        this.desc = desc;
        this.color = color;
        this.displayName = displayName;
    }

    public static List getEnumList() {
        return EnumUtils.getEnumList(FirstFreeDayRuleEnum.class);
    }

    public static EnumSet getEnumSet() {
        return EnumSet.allOf(FirstFreeDayRuleEnum.class);
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