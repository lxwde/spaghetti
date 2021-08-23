package com.zpmc.ztos.infra.base.business.enums.yard;

import org.apache.commons.lang.enums.EnumUtils;

import java.util.EnumSet;
import java.util.List;

public enum YardStowRqmntEnum {
    IMDG_1(1, "IMDG_1", "", "", "IMDG_1"),
    IMDG_11(2, "IMDG_11", "", "", "IMDG_11"),
    IMDG_12(3, "IMDG_12", "", "", "IMDG_12"),
    IMDG_13(4, "IMDG_13", "", "", "IMDG_13"),
    IMDG_14(5, "IMDG_14", "", "", "IMDG_14"),
    IMDG_15(6, "IMDG_15", "", "", "IMDG_15"),
    IMDG_16(7, "IMDG_16", "", "", "IMDG_16"),
    IMDG_2(8, "IMDG_2", "", "", "IMDG_2"),
    IMDG_21(9, "IMDG_21", "", "", "IMDG_21"),
    IMDG_22(10, "IMDG_22", "", "", "IMDG_22"),
    IMDG_23(11, "IMDG_23", "", "", "IMDG_23"),
    IMDG_3(12, "IMDG_3", "", "", "IMDG_3"),
    IMDG_31(13, "IMDG_31", "", "", "IMDG_31"),
    IMDG_32(14, "IMDG_32", "", "", "IMDG_32"),
    IMDG_33(15, "IMDG_33", "", "", "IMDG_33"),
    IMDG_41(16, "IMDG_41", "", "", "IMDG_41"),
    IMDG_42(17, "IMDG_42", "", "", "IMDG_42"),
    IMDG_43(18, "IMDG_43", "", "", "IMDG_43"),
    IMDG_51(19, "IMDG_51", "", "", "IMDG_51"),
    IMDG_52(20, "IMDG_52", "", "", "IMDG_52"),
    IMDG_61(21, "IMDG_61", "", "", "IMDG_61"),
    IMDG_62(22, "IMDG_62", "", "", "IMDG_62"),
    IMDG_7(23, "IMDG_7", "", "", "IMDG_7"),
    IMDG_8(24, "IMDG_8", "", "", "IMDG_8"),
    IMDG_9(25, "IMDG_9", "", "", "IMDG_9"),
    IMDG_X(26, "IMDG_X", "", "", "IMDG_X"),
    ;


    private final int key;

    private final String code;

    private final String desc;

    private final String color;

    private final String displayName;


    YardStowRqmntEnum(int key, String code, String desc, String color, String displayName) {
        this.key = key;
        this.code = code;
        this.desc = desc;
        this.color = color;
        this.displayName = displayName;
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


    public static List getEnumList() {
        return EnumUtils.getEnumList(YardStowRqmntEnum.class);
    }

    public static EnumSet getEnumSet() {
        return EnumSet.allOf(YardStowRqmntEnum.class);
    }

}
