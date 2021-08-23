package com.zpmc.ztos.infra.base.business.enums.argo;

import org.apache.commons.lang.enums.EnumUtils;

import java.util.EnumSet;
import java.util.List;

public enum ServiceOrderUnitStatusEnum {
    NEW(1, "NEW", "", "", "NEW"),
    INPROGRESS(2, "INPROGRESS", "", "", "INPROGRESS"),
    REQUESTED(3, "REQUESTED", "", "", "REQUESTED"),
    COMPLETED(4, "COMPLETED", "", "", "COMPLETED"),
    CACNCELLED(5, "CACNCELLED", "", "", "CACNCELLED");
    private final int key;
    private final String code;
    private final String desc;
    private final String color;
    private final String displayName;

    ServiceOrderUnitStatusEnum(int key, String code, String desc, String color, String displayName) {
        this.key = key;
        this.code = code;
        this.desc = desc;
        this.color = color;
        this.displayName = displayName;
    }

    public static List getEnumList() {
        return EnumUtils.getEnumList(ServiceOrderUnitStatusEnum.class);
    }

    public static EnumSet getEnumSet() {
        return EnumSet.allOf(ServiceOrderUnitStatusEnum.class);
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