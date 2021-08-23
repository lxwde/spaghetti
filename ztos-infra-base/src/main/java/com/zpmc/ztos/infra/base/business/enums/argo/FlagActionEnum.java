package com.zpmc.ztos.infra.base.business.enums.argo;

import org.apache.commons.lang.enums.EnumUtils;

import java.util.EnumSet;
import java.util.List;

public enum FlagActionEnum {
    ADD_HOLD(1, "ADD_HOLD", "", "", "ADD_HOLD"),
    RELEASE_HOLD(2, "RELEASE_HOLD", "", "", "RELEASE_HOLD"),
    GRANT_PERMISSION(3, "GRANT_PERMISSION", "", "", "GRANT_PERMISSION"),
    CANCEL_PERMISSION(4, "CANCEL_PERMISSION", "", "", "CANCEL_PERMISSION");
    private final int key;
    private final String code;
    private final String desc;
    private final String color;
    private final String displayName;

    FlagActionEnum(int key, String code, String desc, String color, String displayName) {
        this.key = key;
        this.code = code;
        this.desc = desc;
        this.color = color;
        this.displayName = displayName;
    }

    public static List getEnumList() {
        return EnumUtils.getEnumList(FlagActionEnum.class);
    }

    public static EnumSet getEnumSet() {
        return EnumSet.allOf(FlagActionEnum.class);
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