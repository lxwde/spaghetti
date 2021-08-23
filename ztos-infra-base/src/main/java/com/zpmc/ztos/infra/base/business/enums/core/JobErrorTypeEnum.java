package com.zpmc.ztos.infra.base.business.enums.core;

import org.apache.commons.lang.enums.EnumUtils;

import java.util.EnumSet;
import java.util.List;

public enum JobErrorTypeEnum {
    MULTIPLE_EXECUTIONS(1, "MULTIPLE_EXECUTIONS", "", "", "MULTIPLE_EXECUTIONS"),
    MULTIPLE_TRIGGERS(2, "WARNINGS", "", "", "WARNINGS"),
    MULTIPLE_SCHEDULERS(3, "WARNINGS", "", "", "WARNINGS"),
    MULTIPLE_JOB_GROUPS(4, "WARNINGS", "", "", "WARNINGS"),
    NO_TRIGGERS(5, "WARNINGS", "", "", "WARNINGS"),
    NAME_DUPLICATES(6, "WARNINGS", "", "", "WARNINGS"),
    RECOVERY_TRIGGERS(7, "WARNINGS", "", "", "WARNINGS"),
    RECOVERY_EXECUTIONS(8, "WARNINGS", "", "", "WARNINGS"),
    NOT_SCHEDULED_BUT_EXECUTED(9, "WARNINGS", "", "", "WARNINGS"),
    SCHEDULED(10, "WARNINGS", "", "", "WARNINGS"),
    EXECUTING(11, "WARNINGS", "", "", "WARNINGS");
    private final int key;
    private final String code;
    private final String desc;
    private final String color;
    private final String displayName;

    JobErrorTypeEnum(int key, String code, String desc, String color, String displayName) {
        this.key = key;
        this.code = code;
        this.desc = desc;
        this.color = color;
        this.displayName = displayName;
    }

    public static List getEnumList() {
        return EnumUtils.getEnumList(JobErrorTypeEnum.class);
    }

    public static EnumSet getEnumSet() {
        return EnumSet.allOf(JobErrorTypeEnum.class);
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