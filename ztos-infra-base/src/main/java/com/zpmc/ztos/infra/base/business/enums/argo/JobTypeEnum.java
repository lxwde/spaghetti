package com.zpmc.ztos.infra.base.business.enums.argo;

import org.apache.commons.lang.enums.EnumUtils;

import java.util.EnumSet;
import java.util.List;

public enum JobTypeEnum {
    REPORTING(1, "REPORTING", "", "", "REPORTING"),
    EDI(2, "EDI", "", "", "EDI"),
    SNX(3, "SNX", "", "", "SNX"),
    GROOVY(4, "GROOVY", "", "", "GROOVY");
    private final int key;
    private final String code;
    private final String desc;
    private final String color;
    private final String displayName;

    JobTypeEnum(int key, String code, String desc, String color, String displayName) {
        this.key = key;
        this.code = code;
        this.desc = desc;
        this.color = color;
        this.displayName = displayName;
    }

    public static List getEnumList() {
        return EnumUtils.getEnumList(JobTypeEnum.class);
    }

    public static EnumSet getEnumSet() {
        return EnumSet.allOf(JobTypeEnum.class);
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