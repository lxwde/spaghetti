package com.zpmc.ztos.infra.base.business.enums.argo;

import org.apache.commons.lang.enums.EnumUtils;

import java.util.EnumSet;
import java.util.List;

public enum SectionLengthsAllowedEnum {
    ALL(1, "ALL", "", "", "ALL"),
    ANY(2, "ANY", "", "", "ANY"),
    EVEN(3, "EVEN", "", "", "EVEN"),
    NONE(4, "NONE", "", "", "NONE"),
    ODD(5, "ODD", "", "", "ODD"),
    ONLY_20(6, "ONLY_20", "", "", "ONLY_20"),
    ONLY_40_45(7, "ONLY_40_45", "", "", "ONLY_40_45");
    private final int key;
    private final String code;
    private final String desc;
    private final String color;
    private final String displayName;

    SectionLengthsAllowedEnum(int key, String code, String desc, String color, String displayName) {
        this.key = key;
        this.code = code;
        this.desc = desc;
        this.color = color;
        this.displayName = displayName;
    }

    public static List getEnumList() {
        return EnumUtils.getEnumList(SectionLengthsAllowedEnum.class);
    }

    public static EnumSet getEnumSet() {
        return EnumSet.allOf(SectionLengthsAllowedEnum.class);
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