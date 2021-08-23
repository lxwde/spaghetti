package com.zpmc.ztos.infra.base.business.enums.argo;

import org.apache.commons.lang.enums.EnumUtils;

import java.util.EnumSet;
import java.util.List;

public enum InbondEnum {
    GOING_INBOND(1, "GOING_INBOND", "", "", "GOING_INBOND"),
    INBOND(2, "INBOND", "", "", "INBOND"),
    PARTIAL_INBOND(3, "PARTIAL_INBOND", "", "", "PARTIAL_INBOND"),
    CANCEL(4, "CANCEL", "", "", "CANCEL");
    private final int key;
    private final String code;
    private final String desc;
    private final String color;
    private final String displayName;

    InbondEnum(int key, String code, String desc, String color, String displayName) {
        this.key = key;
        this.code = code;
        this.desc = desc;
        this.color = color;
        this.displayName = displayName;
    }

    public static List getEnumList() {
        return EnumUtils.getEnumList(InbondEnum.class);
    }

    public static EnumSet getEnumSet() {
        return EnumSet.allOf(InbondEnum.class);
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