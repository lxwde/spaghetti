package com.zpmc.ztos.infra.base.business.enums.framework;

import com.zpmc.ztos.infra.base.common.enums.AtomizedEnum;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ConfigClassEnum extends AtomizedEnum {
    public static final ConfigClassEnum FREETEXT = new ConfigClassEnum("FREETEXT", "atom.ConfigClassEnum.FREETEXT.description", "atom.ConfigClassEnum.FREETEXT.code", "", "", "");
    public static final ConfigClassEnum BOOLEAN = new ConfigClassEnum("BOOLEAN", "atom.ConfigClassEnum.BOOLEAN.description", "atom.ConfigClassEnum.BOOLEAN.code", "", "", "");
    public static final ConfigClassEnum NUMERIC = new ConfigClassEnum("NUMERIC", "atom.ConfigClassEnum.NUMERIC.description", "atom.ConfigClassEnum.NUMERIC.code", "", "", "");
    public static final ConfigClassEnum CODE = new ConfigClassEnum("CODE", "atom.ConfigClassEnum.CODE.description", "atom.ConfigClassEnum.CODE.code", "", "", "");
    public static final ConfigClassEnum CODESET = new ConfigClassEnum("CODESET", "atom.ConfigClassEnum.CODESET.description", "atom.ConfigClassEnum.CODESET.code", "", "", "");
    public static final ConfigClassEnum XML = new ConfigClassEnum("XML", "atom.ConfigClassEnum.XML.description", "atom.ConfigClassEnum.XML.code", "", "", "");

    public static ConfigClassEnum getEnum(String string) {
        return (ConfigClassEnum) ConfigClassEnum.getEnum(ConfigClassEnum.class, (String)string);
    }

    public static Map getEnumMap() {
        return ConfigClassEnum.getEnumMap(ConfigClassEnum.class);
    }

    public static List getEnumList() {
        return ConfigClassEnum.getEnumList(ConfigClassEnum.class);
    }

    public static Collection getList() {
        return ConfigClassEnum.getEnumList(ConfigClassEnum.class);
    }

    public static Iterator iterator() {
        return ConfigClassEnum.iterator(ConfigClassEnum.class);
    }

    protected ConfigClassEnum(String string, String string2, String string3, String string4, String string5, String string6) {
        super(string, string2, string3, string4, string5, string6);
    }

    @Override
    public String getMappingClassName() {
        return "com.zpmc.ztos.infra.base.business.enums.framework.ConfigClassEnum";
    }

}
