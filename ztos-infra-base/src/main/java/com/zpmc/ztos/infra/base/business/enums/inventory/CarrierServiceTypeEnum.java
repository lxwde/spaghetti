package com.zpmc.ztos.infra.base.business.enums.inventory;

import org.apache.commons.lang.enums.EnumUtils;

import java.util.EnumSet;
import java.util.List;

public enum CarrierServiceTypeEnum {
    MAINLINE(1, "MAINLINE", "", "", "MAINLINE"),
    FEEDER(2, "FEEDER", "", "", "FEEDER"),
    BARGE(3, "BARGE", "", "", "BARGE"),
    RAIL(4, "RAIL", "", "", "RAIL");
    private final int key;
    private final String code;
    private final String desc;
    private final String color;
    private final String displayName;

    CarrierServiceTypeEnum(int key, String code, String desc, String color, String displayName) {
        this.key = key;
        this.code = code;
        this.desc = desc;
        this.color = color;
        this.displayName = displayName;
    }

    public static List getEnumList() {
        return EnumUtils.getEnumList(CarrierServiceTypeEnum.class);
    }

    public static EnumSet getEnumSet() {
        return EnumSet.allOf(CarrierServiceTypeEnum.class);
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