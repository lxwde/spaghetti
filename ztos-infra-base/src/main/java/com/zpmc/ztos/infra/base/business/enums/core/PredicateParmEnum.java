package com.zpmc.ztos.infra.base.business.enums.core;

import org.apache.commons.lang.enums.EnumUtils;

import java.util.EnumSet;
import java.util.List;

public enum PredicateParmEnum {
    NO_PARM(1, "NO_PARM", "", "", "NO_PARM"),
    REQUIRED_PARM(2, "WARNINGS", "", "", "WARNINGS"),
    OPTIONAL_PARM(3, "WARNINGS", "", "", "WARNINGS");
    private final int key;
    private final String code;
    private final String desc;
    private final String color;
    private final String displayName;

    PredicateParmEnum(int key, String code, String desc, String color, String displayName) {
        this.key = key;
        this.code = code;
        this.desc = desc;
        this.color = color;
        this.displayName = displayName;
    }

    public static List getEnumList() {
        return EnumUtils.getEnumList(PredicateParmEnum.class);
    }

    public static EnumSet getEnumSet() {
        return EnumSet.allOf(PredicateParmEnum.class);
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