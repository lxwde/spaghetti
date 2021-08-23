package com.zpmc.ztos.infra.base.business.enums.core;

import org.apache.commons.lang.enums.EnumUtils;

import java.util.EnumSet;
import java.util.List;

public enum GroupTypeEnum {
    DIST_CENTER(1, "DIST_CENTER", "", "", "DIST_CENTER"),
    CARRIER(2, "WARNINGS", "", "", "WARNINGS"),
    SUPPLIER(3, "WARNINGS", "", "", "WARNINGS"),
    CONSIGNEE(4, "WARNINGS", "", "", "WARNINGS");
    private final int key;
    private final String code;
    private final String desc;
    private final String color;
    private final String displayName;

    GroupTypeEnum(int key, String code, String desc, String color, String displayName) {
        this.key = key;
        this.code = code;
        this.desc = desc;
        this.color = color;
        this.displayName = displayName;
    }

    public static List getEnumList() {
        return EnumUtils.getEnumList(GroupTypeEnum.class);
    }

    public static EnumSet getEnumSet() {
        return EnumSet.allOf(GroupTypeEnum.class);
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