package com.zpmc.ztos.infra.base.business.enums.road;

import org.apache.commons.lang.enums.EnumUtils;

import java.util.EnumSet;
import java.util.List;

public enum TruckActionEnum {
    RETRY_XPS(1, "RETRY_XPS", "", "", "RETRY_XPS"),
    RETRY(2, "RETRY", "", "", "RETRY"),
    RETRY_PIN(3, "RETRY_PIN", "", "", "RETRY_PIN"),
    SCAN_FAIL(4, "SCAN_FAIL", "", "", "SCAN_FAIL"),
    NO_INSPECTION(5, "NO_INSPECTION", "", "", "NO_INSPECTION"),
    TROUBLE_LANE(6, "TROUBLE_LANE", "", "", "TROUBLE_LANE"),
    TROUBLE_DESK(7, "TROUBLE_DESK", "", "", "TROUBLE_DESK"),
    GOTO(8, "GOTO", "", "", "GOTO"),
    EXIT(9, "EXIT", "", "", "EXIT"),
    GATE_FAILURE(10, "GATE_FAILURE", "", "", "GATE_FAILURE");
    private final int key;
    private final String code;
    private final String desc;
    private final String color;
    private final String displayName;

    TruckActionEnum(int key, String code, String desc, String color, String displayName) {
        this.key = key;
        this.code = code;
        this.desc = desc;
        this.color = color;
        this.displayName = displayName;
    }

    public static List getEnumList() {
        return EnumUtils.getEnumList(TruckActionEnum.class);
    }

    public static EnumSet getEnumSet() {
        return EnumSet.allOf(TruckActionEnum.class);
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