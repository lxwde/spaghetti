package com.zpmc.ztos.infra.base.business.enums.road;

import org.apache.commons.lang.enums.EnumUtils;

import java.util.EnumSet;
import java.util.List;

public enum GeneralTransactionTypeEnum {
    DC(1, "DC", "", "", "DC"),
    DF(2, "DF", "", "", "DF"),
    DM(3, "DM", "", "", "DM"),
    DB(4, "DB", "", "", "DB"),
    RC(5, "RC", "", "", "RC"),
    RF(6, "RF", "", "", "RF"),
    RM(7, "RM", "", "", "RM"),
    RB(8, "RB", "", "", "RB"),
    TC(9, "TC", "", "", "TC"),
    TV(10, "TV", "", "", "TV");
    private final int key;
    private final String code;
    private final String desc;
    private final String color;
    private final String displayName;

    GeneralTransactionTypeEnum(int key, String code, String desc, String color, String displayName) {
        this.key = key;
        this.code = code;
        this.desc = desc;
        this.color = color;
        this.displayName = displayName;
    }

    public static List getEnumList() {
        return EnumUtils.getEnumList(GeneralTransactionTypeEnum.class);
    }

    public static EnumSet getEnumSet() {
        return EnumSet.allOf(GeneralTransactionTypeEnum.class);
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