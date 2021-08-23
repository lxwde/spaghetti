package com.zpmc.ztos.infra.base.business.enums.argo;



import org.apache.commons.lang.enums.EnumUtils;

import java.util.EnumSet;
import java.util.List;

/**
 * @author java002
 */

public enum CreditStatusEnum {
    CASH(1, "CASH", "", "", "CASH"),
    CHECK(2, "CHECK", "", "", "CHECK"),
    OAC(3, "OAC", "", "", "OAC");

    private final int key;

    private final String code;

    private final String desc;

    private final String color;

    private final String displayName;


    CreditStatusEnum(int key, String code, String desc, String color, String displayName) {
        this.key = key;
        this.code = code;
        this.desc = desc;
        this.color = color;
        this.displayName = displayName;
    }

    public static List getEnumList() {
        return EnumUtils.getEnumList(CreditStatusEnum.class);
    }

    public static EnumSet getEnumSet() {
        return EnumSet.allOf(CreditStatusEnum.class);
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
