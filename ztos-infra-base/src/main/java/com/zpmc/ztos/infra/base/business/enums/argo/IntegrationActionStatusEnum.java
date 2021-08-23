package com.zpmc.ztos.infra.base.business.enums.argo;

import org.apache.commons.lang.enums.EnumUtils;

import java.util.EnumSet;
import java.util.List;

public enum IntegrationActionStatusEnum {
    ERROR(1, "ERROR", "", "", "ERROR"),
    RETRIED_ERROR(2, "RETRIED_ERROR", "", "", "RETRIED_ERROR"),
    RETRIED_SUCCESS(3, "RETRIED_SUCCESS", "", "", "RETRIED_SUCCESS"),
    HANDLED_MANUALLY(4, "HANDLED_MANUALLY", "", "", "HANDLED_MANUALLY");
    private final int key;
    private final String code;
    private final String desc;
    private final String color;
    private final String displayName;

    IntegrationActionStatusEnum(int key, String code, String desc, String color, String displayName) {
        this.key = key;
        this.code = code;
        this.desc = desc;
        this.color = color;
        this.displayName = displayName;
    }

    public static List getEnumList() {
        return EnumUtils.getEnumList(IntegrationActionStatusEnum.class);
    }

    public static EnumSet getEnumSet() {
        return EnumSet.allOf(IntegrationActionStatusEnum.class);
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