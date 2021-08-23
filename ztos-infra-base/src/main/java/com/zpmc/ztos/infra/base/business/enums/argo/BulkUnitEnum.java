package com.zpmc.ztos.infra.base.business.enums.argo;

import org.apache.commons.lang.enums.EnumUtils;

import java.util.EnumSet;
import java.util.List;

public enum BulkUnitEnum {
    GALLONS(1, "GALLONS", "加仑", "", "GALLONS"),
    LITERS(2, "LITERS", "升", "", "LITERS"),
    CUBICMETER(3, "CUBICMETER", "立方米", "", "CUBICMETER"),
    KILOS(4, "KILOS", "公斤", "", "KILOS"),
    METRIC_TONNES(5, "METRIC_TONNES", "公吨", "", "METRIC_TONNES"),
    SHORT_TONS(6, "SHORT_TONS", "短吨(美)", "", "SHORT_TONS"),
    LONG_TONS(7, "LONG_TONS", "长吨(英)", "", "LONG_TONS"),
    GRAMS(8, "GRAMS", "克", "", "GRAMS"),
    POUNDS(9, "POUNDS", "磅", "", "POUNDS");
    private final int key;
    private final String code;
    private final String desc;
    private final String color;
    private final String displayName;

    BulkUnitEnum(int key, String code, String desc, String color, String displayName) {
        this.key = key;
        this.code = code;
        this.desc = desc;
        this.color = color;
        this.displayName = displayName;
    }

    public static List getEnumList() {
        return EnumUtils.getEnumList(BulkUnitEnum.class);
    }

    public static EnumSet getEnumSet() {
        return EnumSet.allOf(BulkUnitEnum.class);
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