package com.zpmc.ztos.infra.base.business.enums.inventory;

import org.apache.commons.lang.enums.EnumUtils;

import java.util.EnumSet;
import java.util.List;

public enum RailInspectionOperationsEnum {
    LOAD(1, "LOAD", "", "", "LOAD"),
    DISCHARGE(2, "DISCHARGE", "", "", "DISCHARGE"),
    INSPECTION(3, "INSPECTION", "", "", "INSPECTION"),
    JOBLIST(4, "JOBLIST", "", "", "JOBLIST");
    private final int key;
    private final String code;
    private final String desc;
    private final String color;
    private final String displayName;

    RailInspectionOperationsEnum(int key, String code, String desc, String color, String displayName) {
        this.key = key;
        this.code = code;
        this.desc = desc;
        this.color = color;
        this.displayName = displayName;
    }

    public static List getEnumList() {
        return EnumUtils.getEnumList(RailInspectionOperationsEnum.class);
    }

    public static EnumSet getEnumSet() {
        return EnumSet.allOf(RailInspectionOperationsEnum.class);
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