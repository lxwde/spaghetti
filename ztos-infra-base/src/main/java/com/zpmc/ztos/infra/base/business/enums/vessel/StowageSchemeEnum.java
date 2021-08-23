package com.zpmc.ztos.infra.base.business.enums.vessel;

import org.apache.commons.lang.enums.EnumUtils;

import java.util.EnumSet;
import java.util.List;

public enum StowageSchemeEnum {
    UNKNOWN(1, "UNKNOWN", "", "", "UNKNOWN"),
    BBRRTTB(2, "BBRRTTB", "", "", "BBRRTTB"),
    DECKHOLD(3, "DECKHOLD", "", "", "DECKHOLD"),
    ISO(4, "ISO", "", "", "ISO"),
    RUSSIAN(5, "RUSSIAN", "", "", "RUSSIAN");
    private final int key;
    private final String code;
    private final String desc;
    private final String color;
    private final String displayName;

    StowageSchemeEnum(int key, String code, String desc, String color, String displayName) {
        this.key = key;
        this.code = code;
        this.desc = desc;
        this.color = color;
        this.displayName = displayName;
    }

    public static List getEnumList() {
        return EnumUtils.getEnumList(StowageSchemeEnum.class);
    }

    public static EnumSet getEnumSet() {
        return EnumSet.allOf(StowageSchemeEnum.class);
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