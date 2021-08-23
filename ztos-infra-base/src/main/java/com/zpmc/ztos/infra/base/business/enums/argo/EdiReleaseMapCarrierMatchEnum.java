package com.zpmc.ztos.infra.base.business.enums.argo;

import org.apache.commons.lang.enums.EnumUtils;

import java.util.EnumSet;
import java.util.List;

public enum EdiReleaseMapCarrierMatchEnum {
    NoGuardian(1, "NoGuardian", "no guardian", "", "NoGuardian"),
    MatchByCarrierVisit(2, "MatchByCarrierVisit", "match by carrier visit id", "", "MatchByCarrierVisit"),
    MatchByCarrierId(3, "MatchByCarrierId", "match by carrier id", "", "MatchByCarrierId");
    private final int key;
    private final String code;
    private final String desc;
    private final String color;
    private final String displayName;

    EdiReleaseMapCarrierMatchEnum(int key, String code, String desc, String color, String displayName) {
        this.key = key;
        this.code = code;
        this.desc = desc;
        this.color = color;
        this.displayName = displayName;
    }

    public static List getEnumList() {
        return EnumUtils.getEnumList(EdiReleaseMapCarrierMatchEnum.class);
    }

    public static EnumSet getEnumSet() {
        return EnumSet.allOf(EdiReleaseMapCarrierMatchEnum.class);
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