package com.zpmc.ztos.infra.base.business.enums.core;

import org.apache.commons.lang.enums.EnumUtils;

import java.util.EnumSet;
import java.util.List;

public enum SessionTypeEnum {
    ULC(1, "ULC", "", "", "ULC"),
    WEB(2, "WARNINGS", "", "", "WARNINGS"),
    WEB_ZK(3, "WARNINGS", "", "", "WARNINGS"),
    MOBILE(4, "WARNINGS", "", "", "WARNINGS"),
    WEBSERVICE(5, "WARNINGS", "", "", "WARNINGS"),
    REST(6, "WARNINGS", "", "", "WARNINGS"),
    BACKEND(7, "WARNINGS", "", "", "WARNINGS");
    private final int key;
    private final String code;
    private final String desc;
    private final String color;
    private final String displayName;

    SessionTypeEnum(int key, String code, String desc, String color, String displayName) {
        this.key = key;
        this.code = code;
        this.desc = desc;
        this.color = color;
        this.displayName = displayName;
    }

    public static List getEnumList() {
        return EnumUtils.getEnumList(SessionTypeEnum.class);
    }

    public static EnumSet getEnumSet() {
        return EnumSet.allOf(SessionTypeEnum.class);
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