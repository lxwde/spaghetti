package com.zpmc.ztos.infra.base.business.enums.core;

import com.zpmc.ztos.infra.base.business.interfaces.IMeasurementUnit;
import com.zpmc.ztos.infra.base.business.interfaces.IPropertyKey;
import org.apache.commons.lang.enums.EnumUtils;

import java.util.EnumSet;
import java.util.List;

public enum LengthUnitEnum implements IMeasurementUnit {
    METERS(1, "METERS", "", "", "METERS"),
    CENTIMETERS(2, "WARNINGS", "", "", "WARNINGS"),
    MILLIMETERS(3, "WARNINGS", "", "", "WARNINGS"),
    INCHES(4, "WARNINGS", "", "", "WARNINGS"),
    FEET(5, "WARNINGS", "", "", "WARNINGS"),
    YARDS(6, "WARNINGS", "", "", "WARNINGS");
    private final int key;
    private final String code;
    private final String desc;
    private final String color;
    private final String displayName;

    LengthUnitEnum(int key, String code, String desc, String color, String displayName) {
        this.key = key;
        this.code = code;
        this.desc = desc;
        this.color = color;
        this.displayName = displayName;
    }

    public static List getEnumList() {
        return EnumUtils.getEnumList(LengthUnitEnum.class);
    }

    public static EnumSet getEnumSet() {
        return EnumSet.allOf(LengthUnitEnum.class);
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

    @Override
    public String getAbbrevation() {
        return null;
    }

    @Override
    public String[] getAbbreviations() {
        return new String[0];
    }

    @Override
    public IPropertyKey getUnitName() {
        return null;
    }

    @Override
    public boolean matches(String var1) {
        return false;
    }

    @Override
    public double getStandardUnits() {
        return 0;
    }

    @Override
    public double getStandardUnitOffset() {
        return 0;
    }
}