package com.zpmc.ztos.infra.base.business.enums.argo;

import org.apache.commons.lang.enums.EnumUtils;

import java.util.EnumSet;
import java.util.List;

public enum EcAlarmSubjectTypeEnum {
    POINT_OF_WORK(1, "POINT_OF_WORK", "pow", "", "POINT_OF_WORK"),
    UNIT(2, "UNIT", "unit", "", "UNIT"),
    CHE(3, "CHE", "che", "", "CHE"),
    POSITION(4, "POSITION", "position", "", "POSITION");
    private final int key;
    private final String code;
    private final String desc;
    private final String color;
    private final String displayName;

    EcAlarmSubjectTypeEnum(int key, String code, String desc, String color, String displayName) {
        this.key = key;
        this.code = code;
        this.desc = desc;
        this.color = color;
        this.displayName = displayName;
    }

    public static List getEnumList() {
        return EnumUtils.getEnumList(EcAlarmSubjectTypeEnum.class);
    }

    public static EnumSet getEnumSet() {
        return EnumSet.allOf(EcAlarmSubjectTypeEnum.class);
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