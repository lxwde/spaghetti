package com.zpmc.ztos.infra.base.business.enums.inventory;

import org.apache.commons.lang.enums.EnumUtils;

import java.util.EnumSet;
import java.util.List;

public enum WiSuspendStateEnum {
    NONE(1, "NONE", "", "", "NONE"),
    SUSPEND(2, "SUSPEND", "", "", "SUSPEND"),
    BYPASS(3, "BYPASS", "", "", "BYPASS"),
    SYSTEM_BYPASS(4, "SYSTEM_BYPASS", "", "", "SYSTEM_BYPASS"),
    CANCEL_REQUEST(5, "CANCEL_REQUEST", "", "", "CANCEL_REQUEST");
    private final int key;
    private final String code;
    private final String desc;
    private final String color;
    private final String displayName;

    WiSuspendStateEnum(int key, String code, String desc, String color, String displayName) {
        this.key = key;
        this.code = code;
        this.desc = desc;
        this.color = color;
        this.displayName = displayName;
    }

    public static List getEnumList() {
        return EnumUtils.getEnumList(WiSuspendStateEnum.class);
    }

    public static EnumSet getEnumSet() {
        return EnumSet.allOf(WiSuspendStateEnum.class);
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