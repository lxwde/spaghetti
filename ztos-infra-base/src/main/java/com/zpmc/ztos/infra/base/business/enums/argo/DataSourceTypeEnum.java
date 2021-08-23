package com.zpmc.ztos.infra.base.business.enums.argo;

import org.apache.commons.lang.enums.EnumUtils;

import java.util.EnumSet;
import java.util.List;

public enum DataSourceTypeEnum {
    BRIDGE_MODEL(1, "BRIDGE_MODEL", "", "", "BRIDGE_MODEL"),
    DATABASE(2, "DATABASE", "", "", "DATABASE"),
    SHADOW(3, "SHADOW", "", "", "SHADOW");
    private final int key;
    private final String code;
    private final String desc;
    private final String color;
    private final String displayName;

    DataSourceTypeEnum(int key, String code, String desc, String color, String displayName) {
        this.key = key;
        this.code = code;
        this.desc = desc;
        this.color = color;
        this.displayName = displayName;
    }

    public static List getEnumList() {
        return EnumUtils.getEnumList(DataSourceTypeEnum.class);
    }

    public static EnumSet getEnumSet() {
        return EnumSet.allOf(DataSourceTypeEnum.class);
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