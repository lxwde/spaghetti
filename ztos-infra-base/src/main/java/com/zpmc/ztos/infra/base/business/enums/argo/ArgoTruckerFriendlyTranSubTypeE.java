package com.zpmc.ztos.infra.base.business.enums.argo;

import org.apache.commons.lang.enums.EnumUtils;

import java.util.EnumSet;
import java.util.List;

public enum ArgoTruckerFriendlyTranSubTypeE {
    PUC(1, "PUC", "", "", "PUC"),
    PUE(2, "PUE", "", "", "PUE"),
    PUI(3, "PUI", "", "", "PUI"),
    PUM(4, "PUM", "", "", "PUM"),
    DOC(5, "DOC", "", "", "DOC"),
    DOE(6, "DOE", "", "", "DOE"),
    DOI(7, "DOI", "", "", "DOI"),
    DOM(8, "DOM", "", "", "DOM"),
    TC(9, "TC", "", "", "TC"),
    UNK(10, "UNK", "", "", "UNK");
    private final int key;
    private final String code;
    private final String desc;
    private final String color;
    private final String displayName;

    ArgoTruckerFriendlyTranSubTypeE(int key, String code, String desc, String color, String displayName) {
        this.key = key;
        this.code = code;
        this.desc = desc;
        this.color = color;
        this.displayName = displayName;
    }

    public static List getEnumList() {
        return EnumUtils.getEnumList(ArgoTruckerFriendlyTranSubTypeE.class);
    }

    public static EnumSet getEnumSet() {
        return EnumSet.allOf(ArgoTruckerFriendlyTranSubTypeE.class);
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