package com.zpmc.ztos.infra.base.business.enums.road;

import org.apache.commons.lang.enums.EnumUtils;

import java.util.EnumSet;
import java.util.List;

public enum AppointmentTypeEnum {
    TRUCK_VISIT_APPT(1, "TRUCK_VISIT_APPT", "", "", "TRUCK_VISIT_APPT"),
    CTR_APPT(2, "CTR_APPT", "", "", "CTR_APPT");
    private final int key;
    private final String code;
    private final String desc;
    private final String color;
    private final String displayName;

    AppointmentTypeEnum(int key, String code, String desc, String color, String displayName) {
        this.key = key;
        this.code = code;
        this.desc = desc;
        this.color = color;
        this.displayName = displayName;
    }

    public static List getEnumList() {
        return EnumUtils.getEnumList(AppointmentTypeEnum.class);
    }

    public static EnumSet getEnumSet() {
        return EnumSet.allOf(AppointmentTypeEnum.class);
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