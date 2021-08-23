package com.zpmc.ztos.infra.base.business.enums.argo;

import org.apache.commons.lang.enums.EnumUtils;

import java.util.EnumSet;
import java.util.List;

public enum EquipRfrTypeEnum {
    INTEG_AIR(1, "INTEG_AIR", "", "", "INTEG_AIR"),
    INTEG_AIR_SINGLE(2, "INTEG_AIR_SINGLE", "", "", "INTEG_AIR_SINGLE"),
    INTEG_H20(3, "INTEG_H20", "", "", "INTEG_H20"),
    INTEG_H20_SINGLE(4, "INTEG_H20_SINGLE", "", "", "INTEG_H20_SINGLE"),
    INTEG_UNK(5, "INTEG_UNK", "", "", "INTEG_UNK"),
    FANTAINER(6, "FANTAINER", "", "", "FANTAINER"),
    PORTHOLE(7, "PORTHOLE", "", "", "PORTHOLE"),
    NON_RFR(8, "NON_RFR", "", "", "NON_RFR");
    private final int key;
    private final String code;
    private final String desc;
    private final String color;
    private final String displayName;

    EquipRfrTypeEnum(int key, String code, String desc, String color, String displayName) {
        this.key = key;
        this.code = code;
        this.desc = desc;
        this.color = color;
        this.displayName = displayName;
    }

    public static List getEnumList() {
        return EnumUtils.getEnumList(EquipRfrTypeEnum.class);
    }

    public static EnumSet getEnumSet() {
        return EnumSet.allOf(EquipRfrTypeEnum.class);
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