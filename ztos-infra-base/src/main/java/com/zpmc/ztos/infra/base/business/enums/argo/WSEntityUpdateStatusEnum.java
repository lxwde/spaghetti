package com.zpmc.ztos.infra.base.business.enums.argo;

import org.apache.commons.lang.enums.EnumUtils;

import java.util.EnumSet;
import java.util.List;

public enum WSEntityUpdateStatusEnum {
    FAILED(1, "FAILED", "", "", "FAILED"),
    SUCCESSFUL(2, "SUCCESSFUL", "", "", "SUCCESSFUL"),
    ENTITY_NOT_FOUND(3, "ENTITY_NOT_FOUND", "", "", "ENTITY_NOT_FOUND");
    private final int key;
    private final String code;
    private final String desc;
    private final String color;
    private final String displayName;

    WSEntityUpdateStatusEnum(int key, String code, String desc, String color, String displayName) {
        this.key = key;
        this.code = code;
        this.desc = desc;
        this.color = color;
        this.displayName = displayName;
    }

    public static List getEnumList() {
        return EnumUtils.getEnumList(WSEntityUpdateStatusEnum.class);
    }

    public static EnumSet getEnumSet() {
        return EnumSet.allOf(WSEntityUpdateStatusEnum.class);
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