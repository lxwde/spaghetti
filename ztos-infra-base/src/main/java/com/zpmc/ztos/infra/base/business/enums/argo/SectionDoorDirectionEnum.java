package com.zpmc.ztos.infra.base.business.enums.argo;

import org.apache.commons.lang.enums.EnumUtils;

import java.util.EnumSet;
import java.util.List;

public enum SectionDoorDirectionEnum {
    UNKNOWN(1, "UNKNOWN", "", "", "UNKNOWN"),
    ANY(2, "ANY", "", "", "ANY"),
    ALT1(3, "ALT1", "", "", "ALT1"),
    ALT2(4, "ALT2", "", "", "ALT2"),
    EVEN(5, "EVEN", "", "", "EVEN"),
    ODD(6, "ODD", "", "", "ODD"),
    OPPOSITE(7, "OPPOSITE", "", "", "OPPOSITE"),
    SAME(8, "SAME", "", "", "SAME");
    private final int key;
    private final String code;
    private final String desc;
    private final String color;
    private final String displayName;

    SectionDoorDirectionEnum(int key, String code, String desc, String color, String displayName) {
        this.key = key;
        this.code = code;
        this.desc = desc;
        this.color = color;
        this.displayName = displayName;
    }

    public static List getEnumList() {
        return EnumUtils.getEnumList(SectionDoorDirectionEnum.class);
    }

    public static EnumSet getEnumSet() {
        return EnumSet.allOf(SectionDoorDirectionEnum.class);
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