package com.zpmc.ztos.infra.base.business.enums.argo;

import org.apache.commons.lang.enums.EnumUtils;

import java.util.EnumSet;
import java.util.List;

public enum DataClearStatusEnum {
    ENABLED(1, "enable", "", "", "ENABLED"),
    DISABLED(2, "disable", "", "", "DISABLED"),
    SUSPENDED(3, "suspended", "", "", "SUSPENDED"),
    IN_PROGRESS(4, "inProgress", "", "", "IN_PROGRESS");
    private final int key;
    private final String code;
    private final String desc;
    private final String color;
    private final String displayName;

    DataClearStatusEnum(int key, String code, String desc, String color, String displayName) {
        this.key = key;
        this.code = code;
        this.desc = desc;
        this.color = color;
        this.displayName = displayName;
    }

    public static List getEnumList() {
        return EnumUtils.getEnumList(DataClearStatusEnum.class);
    }

    public static EnumSet getEnumSet() {
        return EnumSet.allOf(DataClearStatusEnum.class);
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