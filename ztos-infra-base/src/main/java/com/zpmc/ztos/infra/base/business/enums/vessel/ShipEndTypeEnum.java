package com.zpmc.ztos.infra.base.business.enums.vessel;

import org.apache.commons.lang.enums.EnumUtils;

import java.util.EnumSet;
import java.util.List;

public enum ShipEndTypeEnum {
    UNKNOWN(1, "UNKNOWN", "", "", "UNKNOWN"),
    ENDFORE(2, "ENDFORE", "", "", "ENDFORE"),
    ENDAFT(3, "ENDAFT", "", "", "ENDAFT");
    private final int key;
    private final String code;
    private final String desc;
    private final String color;
    private final String displayName;

    ShipEndTypeEnum(int key, String code, String desc, String color, String displayName) {
        this.key = key;
        this.code = code;
        this.desc = desc;
        this.color = color;
        this.displayName = displayName;
    }

    public static List getEnumList() {
        return EnumUtils.getEnumList(ShipEndTypeEnum.class);
    }

    public static EnumSet getEnumSet() {
        return EnumSet.allOf(ShipEndTypeEnum.class);
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