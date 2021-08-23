package com.zpmc.ztos.infra.base.business.enums.argo;

import org.apache.commons.lang.enums.EnumUtils;

import java.util.EnumSet;
import java.util.List;

public enum CraneDelayCategoryEnum {
    FORCE_MAJEURE(1, "FORCE_MAJEURE", "", "", "FORCE_MAJEURE"),
    NON_TERMINAL(2, "NON_TERMINAL", "", "", "NON_TERMINAL"),
    TERMINAL(3, "TERMINAL", "", "", "TERMINAL");
    private final int key;
    private final String code;
    private final String desc;
    private final String color;
    private final String displayName;

    CraneDelayCategoryEnum(int key, String code, String desc, String color, String displayName) {
        this.key = key;
        this.code = code;
        this.desc = desc;
        this.color = color;
        this.displayName = displayName;
    }

    public static List getEnumList() {
        return EnumUtils.getEnumList(CraneDelayCategoryEnum.class);
    }

    public static EnumSet getEnumSet() {
        return EnumSet.allOf(CraneDelayCategoryEnum.class);
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