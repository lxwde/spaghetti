package com.zpmc.ztos.infra.base.business.enums.yard;

import org.apache.commons.lang.enums.EnumUtils;

import java.util.EnumSet;
import java.util.List;

public enum YardBlockPurposeEnum {
    APRN(1, "APRN", "", "", "APRN"),
    BLCK(2, "BLCK", "", "", "BLCK"),
    CHSS(3, "CHSS", "", "", "CHSS"),
    CHSW(4, "CHSW", "", "", "CHSW"),
    COMM(5, "COMM", "", "", "COMM"),
    CUST(6, "CUST", "", "", "CUST"),
    DVRC(7, "DVRC", "", "", "DVRC"),
    CITC(8, "CITC", "", "", "CITC"),
    EQPC(9, "EQPC", "", "", "EQPC"),
    FDEC(10, "FDEC", "", "", "FDEC"),
    FLIP(11, "FLIP", "", "", "FLIP"),
    FRME(12, "FRME", "", "", "FRME"),
    GOUT(13, "GOUT", "", "", "GOUT"),
    GQCD(14, "GQCD", "", "", "GQCD"),
    GRND(15, "GRND", "", "", "GRND"),
    ITRM(16, "ITRM", "", "", "ITRM"),
    MNTC(17, "MNTC", "", "", "MNTC"),
    NLND(18, "NLND", "", "", "NLND"),
    RAIL(19, "RAIL", "", "", "RAIL"),
    RAMP(20, "RAMP", "", "", "RAMP"),
    RETR(21, "RETR", "", "", "RETR"),
    TIPC(22, "TIPC", "", "", "TIPC"),
    TQCD(23, "TQCD", "", "", "TQCD"),
    TRCK(24, "TRCK", "", "", "TRCK"),
    UNKNOWN(25, "UNKNOWN", "", "", "UNKNOWN");
    private final int key;
    private final String code;
    private final String desc;
    private final String color;
    private final String displayName;

    YardBlockPurposeEnum(int key, String code, String desc, String color, String displayName) {
        this.key = key;
        this.code = code;
        this.desc = desc;
        this.color = color;
        this.displayName = displayName;
    }

    public static List getEnumList() {
        return EnumUtils.getEnumList(YardBlockPurposeEnum.class);
    }

    public static EnumSet getEnumSet() {
        return EnumSet.allOf(YardBlockPurposeEnum.class);
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