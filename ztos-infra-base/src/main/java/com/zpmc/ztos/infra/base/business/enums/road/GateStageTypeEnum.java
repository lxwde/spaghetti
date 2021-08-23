package com.zpmc.ztos.infra.base.business.enums.road;

import org.apache.commons.lang.enums.EnumUtils;

import java.util.EnumSet;
import java.util.List;

public enum GateStageTypeEnum {
    IN(1, "IN", "", "", "IN"),
    OUT(2, "OUT", "", "", "OUT"),
    APPT(3, "APPT", "", "", "APPT"),
    PREADVRAIL(4, "PREADVRAIL", "", "", "PREADVRAIL"),
    PREADVVES(5, "PREADVVES", "", "", "PREADVVES"),
    APPTTV(6, "APPTTV", "", "", "APPTTV"),
    IFT(7, "IFT", "", "", "IFT");


    private final int key;

    private final String code;

    private final String desc;

    private final String color;

    private final String displayName;


    GateStageTypeEnum(int key, String code, String desc, String color, String displayName) {
        this.key = key;
        this.code = code;
        this.desc = desc;
        this.color = color;
        this.displayName = displayName;
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


    public static List getEnumList() {
        return EnumUtils.getEnumList(GateStageTypeEnum.class);
    }

    public static EnumSet getEnumSet() {
        return EnumSet.allOf(GateStageTypeEnum.class);
    }

}
