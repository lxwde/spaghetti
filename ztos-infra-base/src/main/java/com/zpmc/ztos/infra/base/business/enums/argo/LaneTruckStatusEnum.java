package com.zpmc.ztos.infra.base.business.enums.argo;

import org.apache.commons.lang.enums.EnumUtils;

import java.util.EnumSet;
import java.util.List;

public enum LaneTruckStatusEnum {
    FAILURE(1, "FAILURE", "", "", "FAILURE"),
    TROUBLE(2, "TROUBLE", "", "", "TROUBLE"),
    CHECKS_FAILED(3, "CHECKS_FAILED", "", "", "CHECKS_FAILED"),
    ALLOCATED(4, "ALLOCATED", "", "", "ALLOCATED"),
    ASSIST(5, "ASSIST", "", "", "ASSIST"),
    WAITING(6, "WAITING", "", "", "WAITING"),
    KIOSK(7, "KIOSK", "", "", "KIOSK"),
    PROCESSING(8, "PROCESSING", "", "", "PROCESSING"),
    EMPTY(9, "EMPTY", "", "", "EMPTY"),
    LEAVING(10, "LEAVING", "", "", "LEAVING"),
    BARRIER(11, "BARRIER", "", "", "BARRIER"),
    SENT_TO_TROUBLE(12, "SENT_TO_TROUBLE", "", "", "SENT_TO_TROUBLE");
    private final int key;
    private final String code;
    private final String desc;
    private final String color;
    private final String displayName;

    LaneTruckStatusEnum(int key, String code, String desc, String color, String displayName) {
        this.key = key;
        this.code = code;
        this.desc = desc;
        this.color = color;
        this.displayName = displayName;
    }

    public static List getEnumList() {
        return EnumUtils.getEnumList(LaneTruckStatusEnum.class);
    }

    public static EnumSet getEnumSet() {
        return EnumSet.allOf(LaneTruckStatusEnum.class);
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