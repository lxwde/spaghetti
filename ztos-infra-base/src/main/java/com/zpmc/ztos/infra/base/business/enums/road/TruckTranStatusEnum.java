package com.zpmc.ztos.infra.base.business.enums.road;

import org.apache.commons.lang.enums.EnumUtils;

import java.util.EnumSet;
import java.util.List;

public enum TruckTranStatusEnum {
    ExceedThreshold(1, "ExceedThreshold", "", "", "ExceedThreshold"),
    ApproachThreshold(2, "ApproachThreshold", "", "", "ApproachThreshold"),
    UnderThreshold(3, "UnderThreshold", "", "", "UnderThreshold");
    private final int key;
    private final String code;
    private final String desc;
    private final String color;
    private final String displayName;

    TruckTranStatusEnum(int key, String code, String desc, String color, String displayName) {
        this.key = key;
        this.code = code;
        this.desc = desc;
        this.color = color;
        this.displayName = displayName;
    }

    public static List getEnumList() {
        return EnumUtils.getEnumList(TruckTranStatusEnum.class);
    }

    public static EnumSet getEnumSet() {
        return EnumSet.allOf(TruckTranStatusEnum.class);
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