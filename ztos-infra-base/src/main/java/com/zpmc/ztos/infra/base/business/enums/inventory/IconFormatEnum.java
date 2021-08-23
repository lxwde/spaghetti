package com.zpmc.ztos.infra.base.business.enums.inventory;

import org.apache.commons.lang.enums.EnumUtils;

import java.util.EnumSet;
import java.util.List;

public enum IconFormatEnum {
    STANDARD(1, "STANDARD", "", "", "STANDARD"),
    EQUIPMENT_ID(2, "EQUIPMENT_ID", "", "", "EQUIPMENT_ID"),
    SEQUENCE(3, "SEQUENCE", "", "", "SEQUENCE"),
    MULTI_LIFT(4, "MULTI_LIFT", "", "", "MULTI_LIFT");
    private final int key;
    private final String code;
    private final String desc;
    private final String color;
    private final String displayName;

    IconFormatEnum(int key, String code, String desc, String color, String displayName) {
        this.key = key;
        this.code = code;
        this.desc = desc;
        this.color = color;
        this.displayName = displayName;
    }

    public static List getEnumList() {
        return EnumUtils.getEnumList(IconFormatEnum.class);
    }

    public static EnumSet getEnumSet() {
        return EnumSet.allOf(IconFormatEnum.class);
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