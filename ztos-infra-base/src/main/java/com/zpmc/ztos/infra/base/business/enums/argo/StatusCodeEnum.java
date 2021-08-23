package com.zpmc.ztos.infra.base.business.enums.argo;

import org.apache.commons.lang.enums.EnumUtils;

import java.util.EnumSet;
import java.util.List;

public enum StatusCodeEnum {
    UNKNOWN(1, "UNKNOWN", "", "", "UNKNOWN"),
    AA(2, "AA", "", "", "AA"),
    AC(3, "AC", "", "", "AC"),
    AF(4, "AF", "", "", "AF"),
    AI(5, "AI", "", "", "AI"),
    AL(6, "AL", "", "", "AL"),
    AM(7, "AM", "", "", "AM"),
    AS(8, "AS", "", "", "AS");


    private final int key;

    private final String code;

    private final String desc;

    private final String color;

    private final String displayName;


    StatusCodeEnum(int key, String code, String desc, String color, String displayName) {
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
        return EnumUtils.getEnumList(StatusCodeEnum.class);
    }

    public static EnumSet getEnumSet() {
        return EnumSet.allOf(StatusCodeEnum.class);
    }

}
