package com.zpmc.ztos.infra.base.business.enums.inventory;

import org.apache.commons.lang.enums.EnumUtils;

import java.util.EnumSet;
import java.util.List;

public enum UfvTransitStateEnum {
    S10_ADVISED(1, "S10_ADVISED", "", "", "S10_ADVISED"),
    S20_INBOUND(2, "S20_INBOUND", "", "", "S20_INBOUND"),
    S30_ECIN(3, "S30_ECIN", "", "", "S30_ECIN"),
    S40_YARD(4, "S40_YARD", "", "", "S40_YARD"),
    S50_ECOUT(5, "S50_ECOUT", "", "", "S50_ECOUT"),
    S60_LOADED(6, "S60_LOADED", "", "", "S60_LOADED"),
    S70_DEPARTED(7, "S70_DEPARTED", "", "", "S70_DEPARTED"),
    S99_RETIRED(8, "S99_RETIRED", "", "", "S99_RETIRED");
    private final int key;
    private final String code;
    private final String desc;
    private final String color;
    private final String displayName;

    UfvTransitStateEnum(int key, String code, String desc, String color, String displayName) {
        this.key = key;
        this.code = code;
        this.desc = desc;
        this.color = color;
        this.displayName = displayName;
    }

    public static List getEnumList() {
        return EnumUtils.getEnumList(UfvTransitStateEnum.class);
    }

    public static EnumSet getEnumSet() {
        return EnumSet.allOf(UfvTransitStateEnum.class);
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