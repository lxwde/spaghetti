package com.zpmc.ztos.infra.base.business.enums.vessel;

import org.apache.commons.lang.enums.EnumUtils;

import java.util.EnumSet;
import java.util.List;

public enum VesselTypeEnum {
    CELL(1, "CELL", "", "", "CELL"),
    BARGE(2, "BARGE", "", "", "BARGE"),
    BBULK(3, "BBULK", "", "", "BBULK"),
    BULK(4, "BULK", "", "", "BULK"),
    RORO(5, "RORO", "", "", "RORO"),
    PSNGR(6, "PSNGR", "", "", "PSNGR"),
    TANKER(7, "TANKER", "", "", "TANKER"),
    UNKNOWN(8, "UNKNOWN", "", "", "UNKNOWN");
    private final int key;
    private final String code;
    private final String desc;
    private final String color;
    private final String displayName;

    VesselTypeEnum(int key, String code, String desc, String color, String displayName) {
        this.key = key;
        this.code = code;
        this.desc = desc;
        this.color = color;
        this.displayName = displayName;
    }

    public static List getEnumList() {
        return EnumUtils.getEnumList(VesselTypeEnum.class);
    }

    public static EnumSet getEnumSet() {
        return EnumSet.allOf(VesselTypeEnum.class);
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