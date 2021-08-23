package com.zpmc.ztos.infra.base.business.enums.vessel;

import org.apache.commons.lang.enums.EnumUtils;

import java.util.EnumSet;
import java.util.List;

public enum CheDelayCodeEnum {
    LIA(1, "LIA", "", "", "LIA"),
    LOT(2, "LOT", "", "", "LOT"),
    WEA(3, "WEA", "", "", "WEA"),
    CAE(4, "CAE", "", "", "CAE"),
    DIN(5, "DIN", "", "", "DIN"),
    PLT(6, "PLT", "", "", "PLT"),
    HLD(7, "HLD", "", "", "HLD"),
    LAS(8, "LAS", "", "", "LAS"),
    AIP(9, "AIP", "", "", "AIP"),
    SPE(10, "SPE", "", "", "SPE"),
    UCC(11, "UCC", "", "", "UCC"),
    FTE(12, "FTE", "", "", "FTE"),
    BLT(13, "BLT", "", "", "BLT"),
    MSC(14, "MSC", "", "", "MSC");
    private final int key;
    private final String code;
    private final String desc;
    private final String color;
    private final String displayName;

    CheDelayCodeEnum(int key, String code, String desc, String color, String displayName) {
        this.key = key;
        this.code = code;
        this.desc = desc;
        this.color = color;
        this.displayName = displayName;
    }

    public static List getEnumList() {
        return EnumUtils.getEnumList(CheDelayCodeEnum.class);
    }

    public static EnumSet getEnumSet() {
        return EnumSet.allOf(CheDelayCodeEnum.class);
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