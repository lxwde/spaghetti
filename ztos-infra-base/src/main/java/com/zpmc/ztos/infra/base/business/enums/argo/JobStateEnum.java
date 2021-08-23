package com.zpmc.ztos.infra.base.business.enums.argo;

import org.apache.commons.lang.enums.EnumUtils;

import java.util.EnumSet;
import java.util.List;

public enum JobStateEnum {
    IN_PROGRESS(1, "IN_PROGRESS", "", "", "IN_PROGRESS"),
    SUCCESS(2, "SUCCESS", "", "", "SUCCESS"),
    FAIL(3, "FAIL", "", "", "FAIL"),
    CANCEL(4, "CANCEL", "", "", "CANCEL"),
    WARNING(5, "WARNING", "", "", "WARNING");
    private final int key;
    private final String code;
    private final String desc;
    private final String color;
    private final String displayName;

    JobStateEnum(int key, String code, String desc, String color, String displayName) {
        this.key = key;
        this.code = code;
        this.desc = desc;
        this.color = color;
        this.displayName = displayName;
    }

    public static List getEnumList() {
        return EnumUtils.getEnumList(JobStateEnum.class);
    }

    public static EnumSet getEnumSet() {
        return EnumSet.allOf(JobStateEnum.class);
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