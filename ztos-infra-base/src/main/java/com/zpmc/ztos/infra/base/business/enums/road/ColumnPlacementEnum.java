package com.zpmc.ztos.infra.base.business.enums.road;

import org.apache.commons.lang.enums.EnumUtils;

import java.util.EnumSet;
import java.util.List;

public enum ColumnPlacementEnum {
    LEFT(1, "LEFT", "", "", "LEFT"),
    MIDDLE(2, "MIDDLE", "", "", "MIDDLE"),
    RIGHT(3, "RIGHT", "", "", "RIGHT");
    private final int key;
    private final String code;
    private final String desc;
    private final String color;
    private final String displayName;

    ColumnPlacementEnum(int key, String code, String desc, String color, String displayName) {
        this.key = key;
        this.code = code;
        this.desc = desc;
        this.color = color;
        this.displayName = displayName;
    }

    public static List getEnumList() {
        return EnumUtils.getEnumList(ColumnPlacementEnum.class);
    }

    public static EnumSet getEnumSet() {
        return EnumSet.allOf(ColumnPlacementEnum.class);
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