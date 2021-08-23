package com.zpmc.ztos.infra.base.business.enums.yard;

import org.apache.commons.lang.enums.EnumUtils;

import java.util.EnumSet;
import java.util.List;

public enum DynamicTrafficModeEnum {
    FORE_TO_AFT(1, "FORE_TO_AFT", "", "", "FORE_TO_AFT"),
    AFT_TO_FORE(2, "AFT_TO_FORE", "", "", "AFT_TO_FORE"),
    NONE(3, "NONE", "", "", "NONE");
    private final int key;
    private final String code;
    private final String desc;
    private final String color;
    private final String displayName;

    DynamicTrafficModeEnum(int key, String code, String desc, String color, String displayName) {
        this.key = key;
        this.code = code;
        this.desc = desc;
        this.color = color;
        this.displayName = displayName;
    }

    public static List getEnumList() {
        return EnumUtils.getEnumList(DynamicTrafficModeEnum.class);
    }

    public static EnumSet getEnumSet() {
        return EnumSet.allOf(DynamicTrafficModeEnum.class);
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