package com.zpmc.ztos.infra.base.business.enums.yard;

import org.apache.commons.lang.enums.EnumUtils;

import java.util.EnumSet;
import java.util.List;

public enum IconIdLogicalBlockEnum {
    APRN(1, "APRN", "", "", "APRN"),
    BLCK(2, "BLCK", "", "", "BLCK"),
    CHSS(3, "CHSS", "", "", "CHSS"),
    CHSW(4, "CHSW", "", "", "CHSW"),
    COMM(5, "COMM", "", "", "COMM"),
    CUST(6, "CUST", "", "", "CUST"),
    DVRC(7, "DVRC", "", "", "DVRC"),
    EQPC(8, "EQPC", "", "", "EQPC"),
    FDEC(9, "FDEC", "", "", "FDEC"),
    FLIP(10, "FLIP", "", "", "FLIP"),
    FRME(11, "FRME", "", "", "FRME"),
    GOUT(12, "GOUT", "", "", "GOUT"),
    GQCD(13, "GQCD", "", "", "GQCD"),
    GRND(14, "GRND", "", "", "GRND"),
    ITRM(15, "ITRM", "", "", "ITRM"),
    MNTC(16, "MNTC", "", "", "MNTC"),
    NLND(17, "NLND", "", "", "NLND"),
    RAIL(18, "RAIL", "", "", "RAIL"),
    RAMP(19, "RAMP", "", "", "RAMP"),
    RETR(20, "RETR", "", "", "RETR"),
    TIPC(21, "TIPC", "", "", "TIPC"),
    TQCD(22, "TQCD", "", "", "TQCD"),
    TRCK(23, "TRCK", "", "", "TRCK"),
    CITC(24, "CITC", "", "", "CITC"),
    UNKNOWN(25, "UNKNOWN", "", "", "UNKNOWN");
    private final int key;
    private final String code;
    private final String desc;
    private final String color;
    private final String displayName;

    IconIdLogicalBlockEnum(int key, String code, String desc, String color, String displayName) {
        this.key = key;
        this.code = code;
        this.desc = desc;
        this.color = color;
        this.displayName = displayName;
    }

    public static List getEnumList() {
        return EnumUtils.getEnumList(IconIdLogicalBlockEnum.class);
    }

    public static EnumSet getEnumSet() {
        return EnumSet.allOf(IconIdLogicalBlockEnum.class);
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