package com.zpmc.ztos.infra.base.business.enums.argo;

import org.apache.commons.lang.enums.EnumUtils;

import java.util.EnumSet;
import java.util.List;

public enum BillingSupportedPaymentTypeEnum {
    CREDIT(1, "CREDIT", "credit card", "", "CREDIT"),
    CHECK(2, "CHECK", "check", "", "CHECK"),
    CASH(3, "CASH", "cash", "", "CASH"),
    ONLINE(4, "ONLINE", "online payment", "", "WIRE_TRANSFER");
    private final int key;
    private final String code;
    private final String desc;
    private final String color;
    private final String displayName;

    BillingSupportedPaymentTypeEnum(int key, String code, String desc, String color, String displayName) {
        this.key = key;
        this.code = code;
        this.desc = desc;
        this.color = color;
        this.displayName = displayName;
    }

    public static List getEnumList() {
        return EnumUtils.getEnumList(BillingSupportedPaymentTypeEnum.class);
    }

    public static EnumSet getEnumSet() {
        return EnumSet.allOf(BillingSupportedPaymentTypeEnum.class);
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