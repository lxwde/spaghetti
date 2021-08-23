package com.zpmc.ztos.infra.base.business.enums.inventory;

import org.apache.commons.lang.enums.EnumUtils;

import java.util.EnumSet;
import java.util.List;

public enum RouteResolverActionEnum {
    FAIL(1, "FAIL", "", "", "FAIL"),
    SEARCH_ALL(2, "SEARCH_ALL", "", "", "SEARCH_ALL"),
    SEARCH_FOR_A_SERVICE(3, "SEARCH_FOR_A_SERVICE", "", "", "SEARCH_FOR_A_SERVICE"),
    SELECT_POD(4, "SELECT_POD", "", "", "SELECT_POD"),
    FIND_POD_FROM_SERVICE(5, "FIND_POD_FROM_SERVICE", "", "", "FIND_POD_FROM_SERVICE");
    private final int key;
    private final String code;
    private final String desc;
    private final String color;
    private final String displayName;

    RouteResolverActionEnum(int key, String code, String desc, String color, String displayName) {
        this.key = key;
        this.code = code;
        this.desc = desc;
        this.color = color;
        this.displayName = displayName;
    }

    public static List getEnumList() {
        return EnumUtils.getEnumList(RouteResolverActionEnum.class);
    }

    public static EnumSet getEnumSet() {
        return EnumSet.allOf(RouteResolverActionEnum.class);
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