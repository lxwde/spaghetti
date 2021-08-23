package com.zpmc.ztos.infra.base.business.enums.framework;

import org.apache.commons.lang.enums.EnumUtils;

import java.util.EnumSet;
import java.util.List;

public enum FieldImportanceEnum {
    NOT_USED(1, "NOT_USED", "", "", "NOT_USED"),
    OPTIONAL(2, "OPTIONAL", "", "", "OPTIONAL"),
    REQUIRED(3, "REQUIRED", "", "", "REQUIRED");
    private final int key;
    private final String code;
    private final String desc;
    private final String color;
    private final String displayName;

    FieldImportanceEnum(int key, String code, String desc, String color, String displayName) {
        this.key = key;
        this.code = code;
        this.desc = desc;
        this.color = color;
        this.displayName = displayName;
    }

    public static List getEnumList() {
        return EnumUtils.getEnumList(FieldImportanceEnum.class);
    }

    public static EnumSet getEnumSet() {
        return EnumSet.allOf(FieldImportanceEnum.class);
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