package com.zpmc.ztos.infra.base.business.enums.road;

import org.apache.commons.lang.enums.EnumUtils;

import java.util.EnumSet;
import java.util.List;

public enum TranSubTypeEnum {
    DC(1, "DC", "", "", "DC"),
    DE(2, "DE", "", "", "DE"),
    DI(3, "DI", "", "", "DI"),
    DM(4, "DM", "", "", "DM"),
    DB(5, "DB", "", "", "DB"),
    RC(6, "RC", "", "", "RC"),
    RE(7, "RE", "", "", "RE"),
    RI(8, "RI", "", "", "RI"),
    RM(9, "RM", "", "", "RM"),
    TC(10, "TC", "", "", "TC"),
    RB(11, "RB", "", "", "RB"),
    UK(12, "UK", "", "", "UK");
    private final int key;
    private final String code;
    private final String desc;
    private final String color;
    private final String displayName;

    TranSubTypeEnum(int key, String code, String desc, String color, String displayName) {
        this.key = key;
        this.code = code;
        this.desc = desc;
        this.color = color;
        this.displayName = displayName;
    }

    public static List getEnumList() {
        return EnumUtils.getEnumList(TranSubTypeEnum.class);
    }

    public static EnumSet getEnumSet() {
        return EnumSet.allOf(TranSubTypeEnum.class);
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