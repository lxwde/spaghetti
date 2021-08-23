package com.zpmc.ztos.infra.base.business.enums.cargo;

import org.apache.commons.lang.enums.EnumUtils;

import java.util.EnumSet;
import java.util.List;

public enum CargoDamageSeverityEnum {
    NONE(1, "NONE", "", "", "NONE"),
    MINOR(2, "MINOR", "", "", "MINOR"),
    MAJOR(3, "MAJOR", "", "", "MAJOR"),
    REPAIRED(4, "REPAIRED", "", "", "REPAIRED");
    private final int key;
    private final String code;
    private final String desc;
    private final String color;
    private final String displayName;

    CargoDamageSeverityEnum(int key, String code, String desc, String color, String displayName) {
        this.key = key;
        this.code = code;
        this.desc = desc;
        this.color = color;
        this.displayName = displayName;
    }

    public static List getEnumList() {
        return EnumUtils.getEnumList(CargoDamageSeverityEnum.class);
    }

    public static EnumSet getEnumSet() {
        return EnumSet.allOf(CargoDamageSeverityEnum.class);
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