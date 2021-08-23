package com.zpmc.ztos.infra.base.business.enums.argo;

import org.apache.commons.lang.enums.EnumUtils;

import java.util.EnumSet;
import java.util.List;

public enum CaKindEnum {
    NONE(1, "NONE", "", "", "NONE"),
    BREAK(2, "BREAK", "", "", "BREAK"),
    MAINTENANCE(3, "MAINTENANCE", "", "", "MAINTENANCE"),
    SPECIAL_HANDLING(4, "SPECIAL_HANDLING", "", "", "SPECIAL_HANDLING"),
    DOWN_TIME(5, "DOWN_TIME", "", "", "DOWN_TIME"),
    USER_DEFINED(6, "USER_DEFINED", "", "", "USER_DEFINED"),
    HATCH_MOVE(7, "HATCH_MOVE", "", "", "HATCH_MOVE"),
    BREAK_BULK_MOVE(8, "BREAK_BULK_MOVE", "", "", "BREAK_BULK_MOVE"),
    LATE_START(9, "LATE_START", "", "", "LATE_START"),
    PRIMARY_BREAK(10, "PRIMARY_BREAK", "", "", "PRIMARY_BREAK"),
    WAITING_LASHING(11, "WAITING_LASHING", "", "", "WAITING_LASHING"),
    WAITING_CHE(12, "WAITING_CHE", "", "", "WAITING_CHE"),
    WAITING_INSTRUCTIONS(13, "WAITING_INSTRUCTIONS", "", "", "WAITING_INSTRUCTIONS"),
    BAD_WEATHER(14, "BAD_WEATHER", "", "", "BAD_WEATHER"),
    VESSEL_DELAY(15, "VESSEL_DELAY", "", "", "VESSEL_DELAY"),
    BOOM(16, "BOOM", "", "", "BOOM"),
    SPREADER_CHANGE(17, "SPREADER_CHANGE", "", "", "SPREADER_CHANGE"),
    SECONDARY_BREAK(18, "SECONDARY_BREAK", "", "", "SECONDARY_BREAK"),
    TIMED_BREAK(19, "TIMED_BREAK", "", "", "TIMED_BREAK"),
    CHE_FAILURE(20, "CHE_FAILURE", "", "", "CHE_FAILURE"),
    WORKSPACE(21, "WORKSPACE", "", "", "WORKSPACE"),
    CHANGE_SHIFT(22, "CHANGE_SHIFT", "", "", "CHANGE_SHIFT"),
    HATCH_COVER(23, "HATCH_COVER", "", "", "HATCH_COVER"),
    CRANE_BREAK(24, "CRANE_BREAK", "", "", "CRANE_BREAK");
    private final int key;
    private final String code;
    private final String desc;
    private final String color;
    private final String displayName;

    CaKindEnum(int key, String code, String desc, String color, String displayName) {
        this.key = key;
        this.code = code;
        this.desc = desc;
        this.color = color;
        this.displayName = displayName;
    }

    public static List getEnumList() {
        return EnumUtils.getEnumList(CaKindEnum.class);
    }

    public static EnumSet getEnumSet() {
        return EnumSet.allOf(CaKindEnum.class);
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