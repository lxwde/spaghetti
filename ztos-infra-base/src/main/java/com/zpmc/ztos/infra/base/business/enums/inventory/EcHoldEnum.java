package com.zpmc.ztos.infra.base.business.enums.inventory;

import org.apache.commons.lang.enums.EnumUtils;

import java.util.EnumSet;
import java.util.List;

public enum EcHoldEnum {
    NOT_YET_EVALUATED(1, "NOT_YET_EVALUATED", "", "", "NOT_YET_EVALUATED"),
    NONE(2, "NONE", "", "", "NONE"),
    MEN_WORKING(3, "MEN_WORKING", "", "", "MEN_WORKING"),
    ON_POWER(4, "ON_POWER", "", "", "ON_POWER"),
    FAIL_TO_DECK(5, "FAIL_TO_DECK", "", "", "FAIL_TO_DECK"),
    OVERSTOWED(6, "OVERSTOWED", "", "", "OVERSTOWED"),
    SUSPENDED(7, "SUSPENDED", "", "", "SUSPENDED"),
    YARD_SEQUENCE(8, "YARD_SEQUENCE", "", "", "YARD_SEQUENCE"),
    LOAD_SEQUENCE(9, "LOAD_SEQUENCE", "", "", "LOAD_SEQUENCE"),
    DEPENDENT(10, "DEPENDENT", "", "", "DEPENDENT"),
    PENDING_MOVE(11, "PENDING_MOVE", "", "", "PENDING_MOVE"),
    CHE_LIFT_HEIGHT(12, "CHE_LIFT_HEIGHT", "", "", "CHE_LIFT_HEIGHT"),
    CHE_LIMITATION(13, "CHE_LIMITATION", "", "", "CHE_LIMITATION"),
    REJECTED(14, "REJECTED", "", "", "REJECTED"),
    MULTIPLE_PLANS(15, "MULTIPLE_PLANS", "", "", "MULTIPLE_PLANS"),
    NO_FRAMES(16, "NO_FRAMES", "", "", "NO_FRAMES"),
    INFEASIBLE_JOB(17, "INFEASIBLE_JOB", "", "", "INFEASIBLE_JOB"),
    BUFFER_FULL(18, "BUFFER_FULL", "", "", "BUFFER_FULL"),
    MAX_TTS_PER_FETCH_RTG(19, "MAX_TTS_PER_FETCH_RTG", "", "", "MAX_TTS_PER_FETCH_RTG"),
    MAX_TTS_WITH_NO_RTGS(20, "MAX_TTS_WITH_NO_RTGS", "", "", "MAX_TTS_WITH_NO_RTGS");
    private final int key;
    private final String code;
    private final String desc;
    private final String color;
    private final String displayName;

    EcHoldEnum(int key, String code, String desc, String color, String displayName) {
        this.key = key;
        this.code = code;
        this.desc = desc;
        this.color = color;
        this.displayName = displayName;
    }

    public static List getEnumList() {
        return EnumUtils.getEnumList(EcHoldEnum.class);
    }

    public static EnumSet getEnumSet() {
        return EnumSet.allOf(EcHoldEnum.class);
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