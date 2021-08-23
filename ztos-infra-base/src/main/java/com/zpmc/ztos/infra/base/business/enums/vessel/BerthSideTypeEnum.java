package com.zpmc.ztos.infra.base.business.enums.vessel;

import org.apache.commons.lang.enums.EnumUtils;

import java.util.EnumSet;
import java.util.List;

public enum BerthSideTypeEnum {
    UNKNOWN(1, "UNKNOWN", "", "", "UNKNOWN"),
    STARBOARD(2, "STARBOARD", "", "", "STARBOARD"),
    PORTSIDE(3, "PORTSIDE", "", "", "PORTSIDE"),
    STERNSIDETO(4, "STERNSIDETO", "", "", "STERNSIDETO");
    private final int key;
    private final String code;
    private final String desc;
    private final String color;
    private final String displayName;

    BerthSideTypeEnum(int key, String code, String desc, String color, String displayName) {
        this.key = key;
        this.code = code;
        this.desc = desc;
        this.color = color;
        this.displayName = displayName;
    }

    public static List getEnumList() {
        return EnumUtils.getEnumList(BerthSideTypeEnum.class);
    }

    public static EnumSet getEnumSet() {
        return EnumSet.allOf(BerthSideTypeEnum.class);
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