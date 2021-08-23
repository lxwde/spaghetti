package com.zpmc.ztos.infra.base.business.enums.argo;

import org.apache.commons.lang.enums.EnumUtils;

import java.util.EnumSet;
import java.util.List;

public enum CaPlanStatusEnum {
    PRE_PLANNED(1, "PRE_PLANNED", "", "", "PRE_PLANNED"),
    CLERK_STARTED(2, "CLERK_STARTED", "", "", "CLERK_STARTED"),
    CLERK_STOPPED(3, "CLERK_STOPPED", "", "", "CLERK_STOPPED");
    private final int key;
    private final String code;
    private final String desc;
    private final String color;
    private final String displayName;

    CaPlanStatusEnum(int key, String code, String desc, String color, String displayName) {
        this.key = key;
        this.code = code;
        this.desc = desc;
        this.color = color;
        this.displayName = displayName;
    }

    public static List getEnumList() {
        return EnumUtils.getEnumList(CaPlanStatusEnum.class);
    }

    public static EnumSet getEnumSet() {
        return EnumSet.allOf(CaPlanStatusEnum.class);
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