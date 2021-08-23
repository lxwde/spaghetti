package com.zpmc.ztos.infra.base.business.enums.inventory;

import org.apache.commons.lang.enums.EnumUtils;

import java.util.EnumSet;
import java.util.List;

public enum CraneActivityViewEnum {
    CURRENT(1, "CURRENT", "", "", "CURRENT"),
    PAST(2, "PAST", "", "", "PAST");
    private final int key;
    private final String code;
    private final String desc;
    private final String color;
    private final String displayName;

    CraneActivityViewEnum(int key, String code, String desc, String color, String displayName) {
        this.key = key;
        this.code = code;
        this.desc = desc;
        this.color = color;
        this.displayName = displayName;
    }

    public static List getEnumList() {
        return EnumUtils.getEnumList(CraneActivityViewEnum.class);
    }

    public static EnumSet getEnumSet() {
        return EnumSet.allOf(CraneActivityViewEnum.class);
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