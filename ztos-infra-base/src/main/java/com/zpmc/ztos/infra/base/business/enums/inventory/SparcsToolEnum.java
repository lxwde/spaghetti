package com.zpmc.ztos.infra.base.business.enums.inventory;

import org.apache.commons.lang.enums.EnumUtils;

import java.util.EnumSet;
import java.util.List;

public enum SparcsToolEnum {
    NO(1, "NO", "", "", "NO"),
    ARROW(2, "ARROW", "", "", "ARROW"),
    INFO(3, "INFO", "", "", "INFO"),
    MAGNIFIER(4, "MAGNIFIER", "", "", "MAGNIFIER"),
    CORRECTION(5, "CORRECTION", "", "", "CORRECTION"),
    CONFIRM(6, "CONFIRM", "", "", "CONFIRM"),
    NONCON_CARGO(7, "NONCON_CARGO", "", "", "NONCON_CARGO"),
    NONCON_CARGO_PLAN(8, "NONCON_CARGO_PLAN", "", "", "NONCON_CARGO_PLAN"),
    FILL(9, "FILL", "", "", "FILL"),
    RIGHT_TIER(10, "RIGHT_TIER", "", "", "RIGHT_TIER"),
    LEFT_TIER(11, "LEFT_TIER", "", "", "LEFT_TIER"),
    RIGHT_STACK(12, "RIGHT_STACK", "", "", "RIGHT_STACK"),
    LEFT_STACK(13, "LEFT_STACK", "", "", "LEFT_STACK"),
    CENTER_TIER(14, "CENTER_TIER", "", "", "CENTER_TIER"),
    CENTER_STACK(15, "CENTER_STACK", "", "", "CENTER_STACK"),
    POWER_FLOW(16, "POWER_FLOW", "", "", "POWER_FLOW"),
    POWER_SELECT_TWINLIFT(17, "POWER_SELECT_TWINLIFT", "", "", "POWER_SELECT_TWINLIFT"),
    DRAG(18, "DRAG", "", "", "DRAG"),
    LIST(19, "LIST", "", "", "LIST"),
    LIVE_RECAP(20, "LIVE_RECAP", "", "", "LIVE_RECAP"),
    PROJECTIONS(21, "PROJECTIONS", "", "", "PROJECTIONS"),
    LEFT_TWINLIFT(22, "LEFT_TWINLIFT", "", "", "LEFT_TWINLIFT"),
    RIGHT_TWINLIFT(23, "RIGHT_TWINLIFT", "", "", "RIGHT_TWINLIFT"),
    LEFT_PAIRED20S_LIFT(24, "LEFT_PAIRED20S_LIFT", "", "", "LEFT_PAIRED20S_LIFT"),
    RIGHT_PAIRED20S_LIFT(25, "RIGHT_PAIRED20S_LIFT", "", "", "RIGHT_PAIRED20S_LIFT"),
    PS_PREV_PORTCALL(26, "PS_PREV_PORTCALL", "", "", "PS_PREV_PORTCALL"),
    PS_NEXT_PORTCALL(27, "PS_NEXT_PORTCALL", "", "", "PS_NEXT_PORTCALL"),
    DAMAGED_SLOT(28, "DAMAGED_SLOT", "", "", "DAMAGED_SLOT"),
    POWER_SELECT_TANDEMLIFT(29, "POWER_SELECT_TANDEMLIFT", "", "", "POWER_SELECT_TANDEMLIFT"),
    POWER_SELECT_QUADLIFT(30, "POWER_SELECT_QUADLIFT", "", "", "POWER_SELECT_QUADLIFT"),
    LEFT_TANDEMLIFT(31, "LEFT_TANDEMLIFT", "", "", "LEFT_TANDEMLIFT"),
    RIGHT_TANDEMLIFT(32, "RIGHT_TANDEMLIFT", "", "", "RIGHT_TANDEMLIFT"),
    LEFT_QUADLIFT(33, "LEFT_QUADLIFT", "", "", "LEFT_QUADLIFT"),
    RIGHT_QUADLIFT(34, "RIGHT_QUADLIFT", "", "", "RIGHT_QUADLIFT");
    private final int key;
    private final String code;
    private final String desc;
    private final String color;
    private final String displayName;

    SparcsToolEnum(int key, String code, String desc, String color, String displayName) {
        this.key = key;
        this.code = code;
        this.desc = desc;
        this.color = color;
        this.displayName = displayName;
    }

    public static List getEnumList() {
        return EnumUtils.getEnumList(SparcsToolEnum.class);
    }

    public static EnumSet getEnumSet() {
        return EnumSet.allOf(SparcsToolEnum.class);
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