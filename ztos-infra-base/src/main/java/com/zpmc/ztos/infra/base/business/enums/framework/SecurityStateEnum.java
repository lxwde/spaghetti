package com.zpmc.ztos.infra.base.business.enums.framework;

import com.zpmc.ztos.infra.base.common.enums.AtomizedEnum;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class SecurityStateEnum extends AtomizedEnum {
    public static final SecurityStateEnum ACTIVE = new SecurityStateEnum("A", "atom.SecurityStateEnum.ACTIVE.description", "atom.SecurityStateEnum.ACTIVE.code", "", "", "");
    public static final SecurityStateEnum INACTIVE = new SecurityStateEnum("N", "atom.SecurityStateEnum.INACTIVE.description", "atom.SecurityStateEnum.INACTIVE.code", "", "", "");

    public static SecurityStateEnum getEnum(String inName) {
        return (SecurityStateEnum) SecurityStateEnum.getEnum(SecurityStateEnum.class, (String)inName);
    }

    public static Map getEnumMap() {
        return SecurityStateEnum.getEnumMap(SecurityStateEnum.class);
    }

    public static List getEnumList() {
        return SecurityStateEnum.getEnumList(SecurityStateEnum.class);
    }

    public static Collection getList() {
        return SecurityStateEnum.getEnumList(SecurityStateEnum.class);
    }

    public static Iterator iterator() {
        return SecurityStateEnum.iterator(SecurityStateEnum.class);
    }

    protected SecurityStateEnum(String inKey, String inDescriptionResKey, String inCodeResKey, String inBackgroundColor, String inForegroundColor, String inIconIdPath) {
        super(inKey, inDescriptionResKey, inCodeResKey, inBackgroundColor, inForegroundColor, inIconIdPath);
    }

    @Override
    public String getMappingClassName() {
        return "com.navis.framework.persistence.atoms.UserTypeSecurityStateEnum";
    }

}
