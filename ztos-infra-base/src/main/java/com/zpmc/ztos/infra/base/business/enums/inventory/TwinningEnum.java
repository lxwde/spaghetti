package com.zpmc.ztos.infra.base.business.enums.inventory;

import org.apache.commons.lang.enums.EnumUtils;

import java.util.EnumSet;
import java.util.List;

public enum TwinningEnum {
    NONE(1, "NONE", "", "", "NONE"),
    QC(2, "QC", "", "", "QC"),
    YC(3, "YC", "", "", "YC"),
    CRY(4, "CRY", "", "", "CRY"),
    QC_CRY(5, "QC_CRY", "", "", "QC_CRY"),
    YC_CRY(6, "YC_CRY", "", "", "YC_CRY"),
    FULL(7, "FULL", "", "", "FULL");
    private final int key;
    private final String code;
    private final String desc;
    private final String color;
    private final String displayName;

    TwinningEnum(int key, String code, String desc, String color, String displayName) {
        this.key = key;
        this.code = code;
        this.desc = desc;
        this.color = color;
        this.displayName = displayName;
    }

    public static List getEnumList() {
        return EnumUtils.getEnumList(TwinningEnum.class);
    }

    public static EnumSet getEnumSet() {
        return EnumSet.allOf(TwinningEnum.class);
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