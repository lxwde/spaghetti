package com.zpmc.ztos.infra.base.business.enums.core;

import org.apache.commons.lang.enums.EnumUtils;

import java.util.EnumSet;
import java.util.List;

public enum JobGroupStatusTypeEnum {
    ALL_UP(1, "ALL_UP", "", "", "ALL_UP"),
    PARTIAL_DOWN(2, "WARNINGS", "", "", "WARNINGS"),
    DOWN(3, "WARNINGS", "", "", "WARNINGS"),
    NO_NODES(4, "WARNINGS", "", "", "WARNINGS"),
    UNKNOWN(5, "WARNINGS", "", "", "WARNINGS");
    private final int key;
    private final String code;
    private final String desc;
    private final String color;
    private final String displayName;

    JobGroupStatusTypeEnum(int key, String code, String desc, String color, String displayName) {
        this.key = key;
        this.code = code;
        this.desc = desc;
        this.color = color;
        this.displayName = displayName;
    }

    public static List getEnumList() {
        return EnumUtils.getEnumList(JobGroupStatusTypeEnum.class);
    }

    public static EnumSet getEnumSet() {
        return EnumSet.allOf(JobGroupStatusTypeEnum.class);
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