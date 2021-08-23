package com.zpmc.ztos.infra.base.common.utils;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.enums.Enum;
import org.apache.commons.lang.enums.EnumUtils;
import org.apache.log4j.Logger;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.util.List;

public class AtomizedEnumUtils {
    private static final String ENUM_SUFFIX = "Enum";
    private static String ATOM_REQUIRED_MATCHSTR = ".atoms.";
    private static final Logger LOGGER = Logger.getLogger(AtomizedEnumUtils.class);

    private AtomizedEnumUtils() {
    }

    public static boolean isClassNameAnAtom(String inDataType) {
        return AtomizedEnumUtils.doesClassNameContainAtom(inDataType);
    }

    public static boolean isClassNameAnAtomizedEnum(String inDataType) {
        return AtomizedEnumUtils.doesClassNameContainAtom(inDataType) && inDataType.indexOf(ENUM_SUFFIX) == inDataType.length() - ENUM_SUFFIX.length();
    }

    @Nullable
    public static String getEnumNameFromPersistedName(String inName) {
        if (StringUtils.isEmpty((String)inName)) {
            return null;
        }
        return inName.replaceFirst("persistence.atoms.UserType", "business.atoms.");
    }

    private static boolean doesClassNameContainAtom(String inClassName) {
        return StringUtils.isNotEmpty((String)inClassName) && inClassName.indexOf(ATOM_REQUIRED_MATCHSTR) > 0;
    }

    @Nullable
    public static Enum safeGetEnum(Class inEnumClass, String inEnumInstanceLabel) {
        if (inEnumInstanceLabel == null) {
            return null;
        }
        return EnumUtils.getEnum((Class)inEnumClass, (String)inEnumInstanceLabel);
    }

    public static List safeGetEnumList(Class inEnumClass) {
        List list = EnumUtils.getEnumList((Class)inEnumClass);
        if (list.isEmpty()) {
            try {
                Class.forName(inEnumClass.getName(), true, inEnumClass.getClassLoader());
            }
            catch (ClassNotFoundException ex) {
                throw new AssertionError((Object)ex);
            }
            list = EnumUtils.getEnumList((Class)inEnumClass);
        }
        return list;
    }

}
