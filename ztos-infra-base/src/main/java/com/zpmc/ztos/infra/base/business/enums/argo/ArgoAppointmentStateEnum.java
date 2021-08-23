package com.zpmc.ztos.infra.base.business.enums.argo;

import org.apache.commons.lang.enums.EnumUtils;

import java.util.EnumSet;
import java.util.List;

public enum ArgoAppointmentStateEnum {
    CANCEL(0, "CANCEL", "", "", "CANCEL"),
    CREATED(1, "CREATED", "", "", "CREATED"),
    USED(2, "USED", "", "", "USED"),
    EXPIRED(3, "EXPIRED", "", "", "EXPIRED"),
    USEDLATE(4, "USEDLATE", "", "", "USEDLATE"),
    LATE(5, "LATE", "", "", "LATE"),
    CLOSED(6, "CLOSED", "", "", "CLOSED");
    private final int key;
    private final String code;
    private final String desc;
    private final String color;
    private final String displayName;

    ArgoAppointmentStateEnum(int key, String code, String desc, String color, String displayName) {
        this.key = key;
        this.code = code;
        this.desc = desc;
        this.color = color;
        this.displayName = displayName;
    }

    public static List getEnumList() {
        return EnumUtils.getEnumList(ArgoAppointmentStateEnum.class);
    }

    public static EnumSet getEnumSet() {
        return EnumSet.allOf(ArgoAppointmentStateEnum.class);
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