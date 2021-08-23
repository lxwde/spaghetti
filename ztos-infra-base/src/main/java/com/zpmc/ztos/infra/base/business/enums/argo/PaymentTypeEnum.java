package com.zpmc.ztos.infra.base.business.enums.argo;

import org.apache.commons.lang.enums.EnumUtils;

import java.util.EnumSet;
import java.util.List;

public enum PaymentTypeEnum {
    CREDIT(1, "CREDIT", "", "", "CREDIT"),
    CHECK(2, "CHECK", "", "", "CHECK"),
    MONEY_ORDER(3, "MONEY_ORDER", "", "", "MONEY_ORDER"),
    CASH(4, "CASH", "", "", "CASH"),
    FREE(5, "FREE", "", "", "FREE"),
    ON_ACCOUNT(6, "ON_ACCOUNT", "", "", "ON_ACCOUNT"),
    WIRE_TRANSFER(7, "WIRE_TRANSFER", "", "", "WIRE_TRANSFER");
    private final int key;
    private final String code;
    private final String desc;
    private final String color;
    private final String displayName;

    PaymentTypeEnum(int key, String code, String desc, String color, String displayName) {
        this.key = key;
        this.code = code;
        this.desc = desc;
        this.color = color;
        this.displayName = displayName;
    }

    public static List getEnumList() {
        return EnumUtils.getEnumList(PaymentTypeEnum.class);
    }

    public static EnumSet getEnumSet() {
        return EnumSet.allOf(PaymentTypeEnum.class);
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