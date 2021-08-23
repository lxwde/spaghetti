package com.zpmc.ztos.infra.base.business.enums.yard;

import org.apache.commons.lang.enums.EnumUtils;

import java.util.EnumSet;
import java.util.List;

public enum YardProblemTypeEnum {
    TZ_DECKER(1, "TZ_DECKER", "", "", "TZ_DECKER"),
    INTRA_BLOCK_DECKER(2, "INTRA_BLOCK_DECKER", "", "", "INTRA_BLOCK_DECKER"),
    REHANDLE_DECKER(3, "REHANDLE_DECKER", "", "", "REHANDLE_DECKER");
    private final int key;
    private final String code;
    private final String desc;
    private final String color;
    private final String displayName;

    YardProblemTypeEnum(int key, String code, String desc, String color, String displayName) {
        this.key = key;
        this.code = code;
        this.desc = desc;
        this.color = color;
        this.displayName = displayName;
    }

    public static List getEnumList() {
        return EnumUtils.getEnumList(YardProblemTypeEnum.class);
    }

    public static EnumSet getEnumSet() {
        return EnumSet.allOf(YardProblemTypeEnum.class);
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