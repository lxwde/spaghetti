package com.zpmc.ztos.infra.base.business.enums.framework;

import org.apache.commons.lang.enums.EnumUtils;

import java.util.EnumSet;
import java.util.List;

public enum LetterCaseEnum {
    UPPER(1, "UPPER", "", "", "UPPER"),
    LOWER(2, "lower", "", "", "LOWER"),
    MIXED(3, "Mixed", "", "", "MIXED");

    private final int key;
    private final String code;
    private final String desc;
    private final String color;
    private final String displayName;

    LetterCaseEnum(int key, String code, String desc, String color, String displayName) {
        this.key = key;
        this.code = code;
        this.desc = desc;
        this.color = color;
        this.displayName = displayName;
    }

    public static List getEnumList() {
        return EnumUtils.getEnumList(DbTypeEnum.class);
    }

    public static EnumSet getEnumSet() {
        return EnumSet.allOf(DbTypeEnum.class);
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

    public String getName() {
        return displayName;
    }
//    public LetterCaseEnum(String inS) {
//        super(inS);
//    }
}
