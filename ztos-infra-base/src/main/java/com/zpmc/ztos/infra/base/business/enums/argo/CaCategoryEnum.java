package com.zpmc.ztos.infra.base.business.enums.argo;

import org.apache.commons.lang.enums.EnumUtils;

import java.util.EnumSet;
import java.util.List;

public enum CaCategoryEnum {
    TERMINAL(1, "TERMINAL", "", "", "TERMINAL"),
    NON_TERMINAL(2, "NON_TERMINAL", "", "", "NON_TERMINAL"),
    FORCE_MAJEURE(3, "FORCE_MAJEURE", "", "", "FORCE_MAJEURE");
    private final int key;
    private final String code;
    private final String desc;
    private final String color;
    private final String displayName;

    CaCategoryEnum(int key, String code, String desc, String color, String displayName) {
        this.key = key;
        this.code = code;
        this.desc = desc;
        this.color = color;
        this.displayName = displayName;
    }

    public static List getEnumList() {
        return EnumUtils.getEnumList(CaCategoryEnum.class);
    }

    public static EnumSet getEnumSet() {
        return EnumSet.allOf(CaCategoryEnum.class);
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