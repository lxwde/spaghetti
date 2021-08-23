package com.zpmc.ztos.infra.base.business.enums.inventory;

import org.apache.commons.lang.enums.EnumUtils;

import java.util.EnumSet;
import java.util.List;

public enum LoadingSlotStateEnum {
    EMPTY(1, "EMPTY", "", "", "EMPTY"),
    UNITINSLOT(2, "UNITINSLOT", "", "", "UNITINSLOT"),
    LOADINGUNITINSLOT(3, "LOADINGUNITINSLOT", "", "", "LOADINGUNITINSLOT"),
    LOADINGUNITPLANNEDTOSLOT(4, "LOADINGUNITPLANNEDTOSLOT", "", "", "LOADINGUNITPLANNEDTOSLOT"),
    MATCHUNITPLANNEDTOSLOT(5, "MATCHUNITPLANNEDTOSLOT", "", "", "MATCHUNITPLANNEDTOSLOT"),
    MISMATCHUNITPLANNEDTOSLOT(6, "MISMATCHUNITPLANNEDTOSLOT", "", "", "MISMATCHUNITPLANNEDTOSLOT"),
    TBDUNITPLANNEDTOSLOT(7, "TBDUNITPLANNEDTOSLOT", "", "", "TBDUNITPLANNEDTOSLOT"),
    MATCHTBDPLANNEDTOSLOT(8, "MATCHTBDPLANNEDTOSLOT", "", "", "MATCHTBDPLANNEDTOSLOT"),
    MISMATCHTBDPLANNEDTOSLOT(9, "MISMATCHTBDPLANNEDTOSLOT", "", "", "MISMATCHTBDPLANNEDTOSLOT");
    private final int key;
    private final String code;
    private final String desc;
    private final String color;
    private final String displayName;

    LoadingSlotStateEnum(int key, String code, String desc, String color, String displayName) {
        this.key = key;
        this.code = code;
        this.desc = desc;
        this.color = color;
        this.displayName = displayName;
    }

    public static List getEnumList() {
        return EnumUtils.getEnumList(LoadingSlotStateEnum.class);
    }

    public static EnumSet getEnumSet() {
        return EnumSet.allOf(LoadingSlotStateEnum.class);
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