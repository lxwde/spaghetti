package com.zpmc.ztos.infra.base.business.enums.argo;

import org.apache.commons.lang.enums.EnumUtils;

import java.util.EnumSet;
import java.util.List;

public enum SnxImportEnum {
    LABEL__XML_LOADER_PROCESS_ELEMENTS(1, "LABEL__XML_LOADER_PROCESS_ELEMENTS", "", "", "LABEL__XML_LOADER_PROCESS_ELEMENTS"),
    LABEL__XML_LOADER_PROCESS_FILES(2, "LABEL__XML_LOADER_PROCESS_FILES", "", "", "LABEL__XML_LOADER_PROCESS_FILES");
    private final int key;
    private final String code;
    private final String desc;
    private final String color;
    private final String displayName;

    SnxImportEnum(int key, String code, String desc, String color, String displayName) {
        this.key = key;
        this.code = code;
        this.desc = desc;
        this.color = color;
        this.displayName = displayName;
    }

    public static List getEnumList() {
        return EnumUtils.getEnumList(SnxImportEnum.class);
    }

    public static EnumSet getEnumSet() {
        return EnumSet.allOf(SnxImportEnum.class);
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