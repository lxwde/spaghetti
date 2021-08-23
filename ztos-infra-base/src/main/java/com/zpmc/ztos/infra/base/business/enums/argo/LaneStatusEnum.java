package com.zpmc.ztos.infra.base.business.enums.argo;

import org.apache.commons.lang.enums.EnumUtils;

import java.util.EnumSet;
import java.util.List;

public enum LaneStatusEnum {
    OPEN(1, "OPEN", "", "", "OPEN"),
    CLOSED(2, "CLOSED", "", "", "CLOSED"),
    LOCKED(3, "LOCKED", "", "", "LOCKED"),
    RESERVED(4, "RESERVED", "", "", "RESERVED");
    private final int key;
    private final String code;
    private final String desc;
    private final String color;
    private final String displayName;

    LaneStatusEnum(int key, String code, String desc, String color, String displayName) {
        this.key = key;
        this.code = code;
        this.desc = desc;
        this.color = color;
        this.displayName = displayName;
    }

    public static List getEnumList() {
        return EnumUtils.getEnumList(LaneStatusEnum.class);
    }

    public static EnumSet getEnumSet() {
        return EnumSet.allOf(LaneStatusEnum.class);
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