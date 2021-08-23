package com.zpmc.ztos.infra.base.business.enums.argo;

import org.apache.commons.lang.enums.EnumUtils;

import java.util.EnumSet;
import java.util.List;

public enum CaPositioningEnum {
    FIXED_START(1, "FIXED_START", "", "", "FIXED_START"),
    PRE_WQ(2, "PRE_WQ", "", "", "PRE_WQ"),
    POST_WQ(3, "POST_WQ", "", "", "POST_WQ"),
    PRE_WI(4, "PRE_WI", "", "", "PRE_WI"),
    POST_WI(5, "POST_WI", "", "", "POST_WI");
    private final int key;
    private final String code;
    private final String desc;
    private final String color;
    private final String displayName;

    CaPositioningEnum(int key, String code, String desc, String color, String displayName) {
        this.key = key;
        this.code = code;
        this.desc = desc;
        this.color = color;
        this.displayName = displayName;
    }

    public static List getEnumList() {
        return EnumUtils.getEnumList(CaPositioningEnum.class);
    }

    public static EnumSet getEnumSet() {
        return EnumSet.allOf(CaPositioningEnum.class);
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