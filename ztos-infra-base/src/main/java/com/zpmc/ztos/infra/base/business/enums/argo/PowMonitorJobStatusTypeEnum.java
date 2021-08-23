package com.zpmc.ztos.infra.base.business.enums.argo;

import org.apache.commons.lang.enums.EnumUtils;

import java.util.EnumSet;
import java.util.List;

public enum PowMonitorJobStatusTypeEnum {
    COMPLETED(1, "COMPLETED", "", "", "COMPLETED"),
    TO_GO(2, "TO_GO", "", "", "TO_GO"),
    HANDLED_BY_CRANE(3, "HANDLED_BY_CRANE", "", "", "HANDLED_BY_CRANE"),
    PM_AT_CRANE(4, "PM_AT_CRANE", "", "", "PM_AT_CRANE"),
    PM_TO_CRANE(5, "PM_TO_CRANE", "", "", "PM_TO_CRANE"),
    AT_PARALLEL_BUFFER(6, "AT_PARALLEL_BUFFER", "", "", "AT_PARALLEL_BUFFER"),
    PM_AT_YARD_DESTINATION(7, "PM_AT_YARD_DESTINATION", "", "", "PM_AT_YARD_DESTINATION"),
    PM_TO_YARD_DESTINATION(8, "PM_TO_YARD_DESTINATION", "", "", "PM_TO_YARD_DESTINATION");
    private final int key;
    private final String code;
    private final String desc;
    private final String color;
    private final String displayName;

    PowMonitorJobStatusTypeEnum(int key, String code, String desc, String color, String displayName) {
        this.key = key;
        this.code = code;
        this.desc = desc;
        this.color = color;
        this.displayName = displayName;
    }

    public static List getEnumList() {
        return EnumUtils.getEnumList(PowMonitorJobStatusTypeEnum.class);
    }

    public static EnumSet getEnumSet() {
        return EnumSet.allOf(PowMonitorJobStatusTypeEnum.class);
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