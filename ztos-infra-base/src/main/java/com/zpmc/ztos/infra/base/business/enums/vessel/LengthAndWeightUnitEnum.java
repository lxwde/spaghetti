package com.zpmc.ztos.infra.base.business.enums.vessel;

import org.apache.commons.lang.enums.EnumUtils;

import java.util.EnumSet;
import java.util.List;

public enum LengthAndWeightUnitEnum {
    UNKNOWN(1, "UNKNOWN", "", "", "UNKNOWN"),
    MILLIMETER(2, "MILLIMETER", "", "", "MILLIMETER"),
    CENTIMETER(3, "CENTIMETER", "", "", "CENTIMETER"),
    METER(4, "METER", "", "", "METER"),
    MILLIGRAMS(5, "MILLIGRAMS", "", "", "MILLIGRAMS"),
    GRAMS(6, "GRAMS", "", "", "GRAMS"),
    KILOGRAM(7, "KILOGRAM", "", "", "KILOGRAM"),
    TONNE(8, "TONNE", "", "", "TONNE");
    private final int key;
    private final String code;
    private final String desc;
    private final String color;
    private final String displayName;

    LengthAndWeightUnitEnum(int key, String code, String desc, String color, String displayName) {
        this.key = key;
        this.code = code;
        this.desc = desc;
        this.color = color;
        this.displayName = displayName;
    }

    public static List getEnumList() {
        return EnumUtils.getEnumList(LengthAndWeightUnitEnum.class);
    }

    public static EnumSet getEnumSet() {
        return EnumSet.allOf(LengthAndWeightUnitEnum.class);
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