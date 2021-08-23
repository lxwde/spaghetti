package com.zpmc.ztos.infra.base.business.enums.argo;

import org.apache.commons.lang.enums.EnumUtils;

import java.util.EnumSet;
import java.util.List;

public enum FlagStatusEnum {
    ACTIVE(1, "ACTIVE", "", "", "ACTIVE"),
    RELEASED(2, "RELEASED", "", "", "RELEASED"),
    GRANTED(3, "GRANTED", "", "", "GRANTED"),
    REQUIRED(4, "REQUIRED", "", "", "REQUIRED"),
    CANCELED(5, "CANCELED", "", "", "CANCELED"),
    PREREQUISITE(6, "PREREQUISITE", "", "", "PREREQUISITE");
    private final int key;
    private final String code;
    private final String desc;
    private final String color;
    private final String displayName;

    FlagStatusEnum(int key, String code, String desc, String color, String displayName) {
        this.key = key;
        this.code = code;
        this.desc = desc;
        this.color = color;
        this.displayName = displayName;
    }

    public static List getEnumList() {
        return EnumUtils.getEnumList(FlagStatusEnum.class);
    }

    public static EnumSet getEnumSet() {
        return EnumSet.allOf(FlagStatusEnum.class);
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