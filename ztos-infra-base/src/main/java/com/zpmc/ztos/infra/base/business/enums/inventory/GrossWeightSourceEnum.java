package com.zpmc.ztos.infra.base.business.enums.inventory;

import org.apache.commons.lang.enums.EnumUtils;

import java.util.EnumSet;
import java.util.List;

public enum GrossWeightSourceEnum {
    EDI(1, "EDI", "", "", "EDI"),
    VGM(2, "VGM", "", "", "VGM"),
    SCALE(3, "SCALE", "", "", "SCALE"),
    USER(4, "USER", "", "", "USER"),
    GATE(5, "GATE", "", "", "GATE"),
    COMPUTED(6, "COMPUTED", "", "", "COMPUTED");
    private final int key;
    private final String code;
    private final String desc;
    private final String color;
    private final String displayName;

    GrossWeightSourceEnum(int key, String code, String desc, String color, String displayName) {
        this.key = key;
        this.code = code;
        this.desc = desc;
        this.color = color;
        this.displayName = displayName;
    }

    public static List getEnumList() {
        return EnumUtils.getEnumList(GrossWeightSourceEnum.class);
    }

    public static EnumSet getEnumSet() {
        return EnumSet.allOf(GrossWeightSourceEnum.class);
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