package com.zpmc.ztos.infra.base.business.enums.framework;

import org.apache.commons.lang.enums.EnumUtils;

import java.util.EnumSet;
import java.util.List;

public enum DbTypeEnum {
    STRING(1, "STRING", "", "", "STRING"),
    INTEGER(2, "INTEGER", "", "", "INTEGER"),
    FLOAT(3, "FLOAT", "", "", "FLOAT"),
    DATE(4, "DATE", "", "", "DATE");
    private final int key;
    private final String code;
    private final String desc;
    private final String color;
    private final String displayName;

    DbTypeEnum(int key, String code, String desc, String color, String displayName) {
        this.key = key;
        this.code = code;
        this.desc = desc;
        this.color = color;
        this.displayName = displayName;
    }

    public static List getEnumList() {
        return EnumUtils.getEnumList(DbTypeEnum.class);
    }

    public static EnumSet getEnumSet() {
        return EnumSet.allOf(DbTypeEnum.class);
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