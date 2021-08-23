package com.zpmc.ztos.infra.base.business.enums.vessel;

import org.apache.commons.lang.enums.EnumUtils;

import java.util.EnumSet;
import java.util.List;

public enum ShipLcgsRefEnum {
    MIDSHIPS(1, "MIDSHIPS", "", "", "MIDSHIPS"),
    AFT_PERPENDICULAR(2, "AFT_PERPENDICULAR", "", "", "AFT_PERPENDICULAR"),
    FWD_PERPENDICULAR(3, "FWD_PERPENDICULAR", "", "", "FWD_PERPENDICULAR");
    private final int key;
    private final String code;
    private final String desc;
    private final String color;
    private final String displayName;

    ShipLcgsRefEnum(int key, String code, String desc, String color, String displayName) {
        this.key = key;
        this.code = code;
        this.desc = desc;
        this.color = color;
        this.displayName = displayName;
    }

    public static List getEnumList() {
        return EnumUtils.getEnumList(ShipLcgsRefEnum.class);
    }

    public static EnumSet getEnumSet() {
        return EnumSet.allOf(ShipLcgsRefEnum.class);
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