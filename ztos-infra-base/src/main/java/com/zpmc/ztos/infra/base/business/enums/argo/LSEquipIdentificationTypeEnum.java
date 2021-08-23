package com.zpmc.ztos.infra.base.business.enums.argo;

import org.apache.commons.lang.enums.EnumUtils;

import java.util.EnumSet;
import java.util.List;

public enum LSEquipIdentificationTypeEnum {
    OOG(1, "OOG", "", "", "OOG"),
    RFR(2, "RFR", "", "", "RFR"),
    STD(3, "STD", "", "", "STD"),
    HZD(4, "HZD", "", "", "HZD"),
    RST(5, "RST", "", "", "RST");
    private final int key;
    private final String code;
    private final String desc;
    private final String color;
    private final String displayName;

    LSEquipIdentificationTypeEnum(int key, String code, String desc, String color, String displayName) {
        this.key = key;
        this.code = code;
        this.desc = desc;
        this.color = color;
        this.displayName = displayName;
    }

    public static List getEnumList() {
        return EnumUtils.getEnumList(LSEquipIdentificationTypeEnum.class);
    }

    public static EnumSet getEnumSet() {
        return EnumSet.allOf(LSEquipIdentificationTypeEnum.class);
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