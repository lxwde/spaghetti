package com.zpmc.ztos.infra.base.business.enums.argo;

import com.zpmc.ztos.infra.base.common.enums.AtomizedEnum;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class XpsImportStateEnum extends AtomizedEnum {

    public static final XpsImportStateEnum AWAIT_AUTO_IMPORT = new XpsImportStateEnum("AWAIT_AUTO_IMPORT", "atom.XpsImportStateEnum.AWAIT_AUTO_IMPORT.description", "atom.XpsImportStateEnum.AWAIT_AUTO_IMPORT.code", "", "", "");
    public static final XpsImportStateEnum AWAIT_MANUAL_IMPORT = new XpsImportStateEnum("AWAIT_MANUAL_IMPORT", "atom.XpsImportStateEnum.AWAIT_MANUAL_IMPORT.description", "atom.XpsImportStateEnum.AWAIT_MANUAL_IMPORT.code", "", "", "");
    public static final XpsImportStateEnum READY_LOCKED = new XpsImportStateEnum("READY_LOCKED", "atom.XpsImportStateEnum.READY_LOCKED.description", "atom.XpsImportStateEnum.READY_LOCKED.code", "", "", "");
    public static final XpsImportStateEnum READY = new XpsImportStateEnum("READY", "atom.XpsImportStateEnum.READY.description", "atom.XpsImportStateEnum.READY.code", "", "", "");

    public static XpsImportStateEnum getEnum(String string) {
        return (XpsImportStateEnum) XpsImportStateEnum.getEnum(XpsImportStateEnum.class, (String)string);
    }

    public static Map getEnumMap() {
        return XpsImportStateEnum.getEnumMap(XpsImportStateEnum.class);
    }

    public static List getEnumList() {
        return XpsImportStateEnum.getEnumList(XpsImportStateEnum.class);
    }

    public static Collection getList() {
        return XpsImportStateEnum.getEnumList(XpsImportStateEnum.class);
    }

    public static Iterator iterator() {
        return XpsImportStateEnum.iterator(XpsImportStateEnum.class);
    }

    protected XpsImportStateEnum(String string, String string2, String string3, String string4, String string5, String string6) {
        super(string, string2, string3, string4, string5, string6);
    }

    @Override
    public String getMappingClassName() {
        return "com.navis.argo.persistence.atoms.UserTypeXpsImportStateEnum";
    }

}
