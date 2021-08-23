package com.zpmc.ztos.infra.base.business.enums.inventory;

import org.apache.commons.lang.enums.EnumUtils;

import java.util.EnumSet;
import java.util.List;

public enum YardStowRqmntEnum {
    EITHER(1, "EITHER", "", "", "EITHER"),
    WHEELS(2, "WHEELS", "", "", "WHEELS"),
    GROUND(3, "GROUND", "", "", "GROUND"),
    TRANSFER_RACK(4, "TRANSFER_RACK", "", "", "TRANSFER_RACK"),
    TRANSFER_DIRECT(5, "TRANSFER_DIRECT", "", "", "TRANSFER_DIRECT");
    private final int key;
    private final String code;
    private final String desc;
    private final String color;
    private final String displayName;

    YardStowRqmntEnum(int key, String code, String desc, String color, String displayName) {
        this.key = key;
        this.code = code;
        this.desc = desc;
        this.color = color;
        this.displayName = displayName;
    }

    public static List getEnumList() {
        return EnumUtils.getEnumList(YardStowRqmntEnum.class);
    }

    public static EnumSet getEnumSet() {
        return EnumSet.allOf(YardStowRqmntEnum.class);
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