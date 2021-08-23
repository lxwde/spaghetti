package com.zpmc.ztos.infra.base.business.enums.argo;

import org.apache.commons.lang.enums.EnumUtils;

import java.util.EnumSet;
import java.util.List;

public enum CargoServiceOrderAddressTypeEnu {
    BILL_TO(1, "BILL_TO", "账单地址", "", "BILL_TO"),
    DELIVERY_TO(2, "DELIVERY_TO", "收货地址", "", "DELIVERY_TO"),
    FORWARDING_AGENT(3, "FORWARDING_AGENT", "代理", "", "FORWARDING_AGENT"),
    NOTIFY_PARTY(4, "NOTIFY_PARTY", "通知组织", "", "NOTIFY_PARTY");
    private final int key;
    private final String code;
    private final String desc;
    private final String color;
    private final String displayName;

    CargoServiceOrderAddressTypeEnu(int key, String code, String desc, String color, String displayName) {
        this.key = key;
        this.code = code;
        this.desc = desc;
        this.color = color;
        this.displayName = displayName;
    }

    public static List getEnumList() {
        return EnumUtils.getEnumList(CargoServiceOrderAddressTypeEnu.class);
    }

    public static EnumSet getEnumSet() {
        return EnumSet.allOf(CargoServiceOrderAddressTypeEnu.class);
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