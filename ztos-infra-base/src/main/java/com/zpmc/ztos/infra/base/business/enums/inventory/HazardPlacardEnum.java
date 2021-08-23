package com.zpmc.ztos.infra.base.business.enums.inventory;

import org.apache.commons.lang.enums.EnumUtils;

import java.util.EnumSet;
import java.util.List;

public enum HazardPlacardEnum {
    IMDG_11(1, "IMDG_11", "", "", "IMDG_11"),
    IMDG_12(2, "IMDG_12", "", "", "IMDG_12"),
    IMDG_13(3, "IMDG_13", "", "", "IMDG_13"),
    IMDG_14(4, "IMDG_14", "", "", "IMDG_14"),
    IMDG_15(5, "IMDG_15", "", "", "IMDG_15"),
    IMDG_16(6, "IMDG_16", "", "", "IMDG_16"),
    IMDG_2(7, "IMDG_2", "", "", "IMDG_2"),
    IMDG_21(8, "IMDG_21", "", "", "IMDG_21"),
    IMDG_22(9, "IMDG_22", "", "", "IMDG_22"),
    IMDG_23(10, "IMDG_23", "", "", "IMDG_23"),
    IMDG_3(11, "IMDG_3", "", "", "IMDG_3"),
    IMDG_31(12, "IMDG_31", "", "", "IMDG_31"),
    IMDG_32(13, "IMDG_32", "", "", "IMDG_32"),
    IMDG_33(14, "IMDG_33", "", "", "IMDG_33"),
    IMDG_41(15, "IMDG_41", "", "", "IMDG_41"),
    IMDG_42(16, "IMDG_42", "", "", "IMDG_42"),
    IMDG_43(17, "IMDG_43", "", "", "IMDG_43"),
    IMDG_51(18, "IMDG_51", "", "", "IMDG_51"),
    IMDG_52(19, "IMDG_52", "", "", "IMDG_52"),
    IMDG_61(20, "IMDG_61", "", "", "IMDG_61"),
    IMDG_62(21, "IMDG_62", "", "", "IMDG_62"),
    IMDG_7(22, "IMDG_7", "", "", "IMDG_7"),
    IMDG_8(23, "IMDG_8", "", "", "IMDG_8"),
    IMDG_9(24, "IMDG_9", "", "", "IMDG_9");
    private final int key;
    private final String code;
    private final String desc;
    private final String color;
    private final String displayName;

    HazardPlacardEnum(int key, String code, String desc, String color, String displayName) {
        this.key = key;
        this.code = code;
        this.desc = desc;
        this.color = color;
        this.displayName = displayName;
    }

    public static List getEnumList() {
        return EnumUtils.getEnumList(HazardPlacardEnum.class);
    }

    public static EnumSet getEnumSet() {
        return EnumSet.allOf(HazardPlacardEnum.class);
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