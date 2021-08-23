package com.zpmc.ztos.infra.base.business.enums.argo;

import org.apache.commons.lang.enums.EnumUtils;

import java.util.EnumSet;
import java.util.List;

public enum DisplayModeEnum {
    EDITABLE(1, "EDITABLE", "editable", "", "EDITABLE"),
    VIEW_ONLY(2, "VIEW_ONLY", "view only", "", "VIEW_ONLY"),
    HIDDEN(3, "HIDDEN", "hidden", "", "HIDDEN"),
    NONE(4, "NONE", "none", "", "NONE");
    private final int key;
    private final String code;
    private final String desc;
    private final String color;
    private final String displayName;

    DisplayModeEnum(int key, String code, String desc, String color, String displayName) {
        this.key = key;
        this.code = code;
        this.desc = desc;
        this.color = color;
        this.displayName = displayName;
    }

    public static List getEnumList() {
        return EnumUtils.getEnumList(DisplayModeEnum.class);
    }

    public static EnumSet getEnumSet() {
        return EnumSet.allOf(DisplayModeEnum.class);
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