package com.zpmc.ztos.infra.base.business.enums.inventory;

import org.apache.commons.lang.enums.EnumUtils;

import java.util.EnumSet;
import java.util.List;

public enum CraneOperationModeEnum {
    SINGLE(1, "SINGLE", "", "", "SINGLE"),
    TWIN(2, "TWIN", "", "", "TWIN"),
    TANDEM(3, "TANDEM", "", "", "TANDEM"),
    QUAD(4, "QUAD", "", "", "QUAD");
    private final int key;
    private final String code;
    private final String desc;
    private final String color;
    private final String displayName;

    CraneOperationModeEnum(int key, String code, String desc, String color, String displayName) {
        this.key = key;
        this.code = code;
        this.desc = desc;
        this.color = color;
        this.displayName = displayName;
    }

    public static List getEnumList() {
        return EnumUtils.getEnumList(CraneOperationModeEnum.class);
    }

    public static EnumSet getEnumSet() {
        return EnumSet.allOf(CraneOperationModeEnum.class);
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