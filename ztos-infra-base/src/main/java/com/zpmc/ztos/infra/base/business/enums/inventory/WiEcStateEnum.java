package com.zpmc.ztos.infra.base.business.enums.inventory;

import org.apache.commons.lang.enums.EnumUtils;

import java.util.EnumSet;
import java.util.List;

public enum WiEcStateEnum {
    NONE(1, "NONE", "", "", "NONE"),
    STANDARD_FETCH(2, "STANDARD_FETCH", "", "", "STANDARD_FETCH"),
    PRIORITY_FETCH(3, "PRIORITY_FETCH", "", "", "PRIORITY_FETCH"),
    PRIORITY_DISPATCH(4, "PRIORITY_DISPATCH", "", "", "PRIORITY_DISPATCH"),
    IMMINENT_MOVE(5, "IMMINENT_MOVE", "", "", "IMMINENT_MOVE"),
    AUTO_FETCH(6, "AUTO_FETCH", "", "", "AUTO_FETCH");
    private final int key;
    private final String code;
    private final String desc;
    private final String color;
    private final String displayName;

    WiEcStateEnum(int key, String code, String desc, String color, String displayName) {
        this.key = key;
        this.code = code;
        this.desc = desc;
        this.color = color;
        this.displayName = displayName;
    }

    public static List getEnumList() {
        return EnumUtils.getEnumList(WiEcStateEnum.class);
    }

    public static EnumSet getEnumSet() {
        return EnumSet.allOf(WiEcStateEnum.class);
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