package com.zpmc.ztos.infra.base.business.enums.framework;

import com.zpmc.ztos.infra.base.common.enums.AtomizedEnum;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ExtensionLanguageEnum extends AtomizedEnum {

    public static final ExtensionLanguageEnum GROOVY = new ExtensionLanguageEnum("GROOVY", "atom.ExtensionLanguageEnum.GROOVY.description", "atom.ExtensionLanguageEnum.GROOVY.code", "", "", "");
    public static final ExtensionLanguageEnum NONE = new ExtensionLanguageEnum("NONE", "atom.ExtensionLanguageEnum.NONE.description", "atom.ExtensionLanguageEnum.NONE.code", "", "", "");

    public static ExtensionLanguageEnum getEnum(String inName) {
        return (ExtensionLanguageEnum) ExtensionLanguageEnum.getEnum(ExtensionLanguageEnum.class, (String)inName);
    }

    public static Map getEnumMap() {
        return ExtensionLanguageEnum.getEnumMap(ExtensionLanguageEnum.class);
    }

    public static List getEnumList() {
        return ExtensionLanguageEnum.getEnumList(ExtensionLanguageEnum.class);
    }

    public static Collection getList() {
        return ExtensionLanguageEnum.getEnumList(ExtensionLanguageEnum.class);
    }

    public static Iterator iterator() {
        return ExtensionLanguageEnum.iterator(ExtensionLanguageEnum.class);
    }

    protected ExtensionLanguageEnum(String inKey, String inDescriptionResKey, String inCodeResKey, String inBackgroundColor, String inForegroundColor, String inIconIdPath) {
        super(inKey, inDescriptionResKey, inCodeResKey, inBackgroundColor, inForegroundColor, inIconIdPath);
    }

    @Override
    public String getMappingClassName() {
        return "com.navis.framework.persistence.atoms.UserTypeExtensionLanguageEnum";
    }
}
