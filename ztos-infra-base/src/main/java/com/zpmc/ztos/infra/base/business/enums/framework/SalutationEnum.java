package com.zpmc.ztos.infra.base.business.enums.framework;

import com.zpmc.ztos.infra.base.common.enums.AtomizedEnum;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class SalutationEnum extends AtomizedEnum {
    public static final SalutationEnum MR = new SalutationEnum("MR", "atom.SalutationEnum.MR.description", "atom.SalutationEnum.MR.code", "", "", "");
    public static final SalutationEnum MRS = new SalutationEnum("MRS", "atom.SalutationEnum.MRS.description", "atom.SalutationEnum.MRS.code", "", "", "");
    public static final SalutationEnum MS = new SalutationEnum("MS", "atom.SalutationEnum.MS.description", "atom.SalutationEnum.MS.code", "", "", "");
    public static final SalutationEnum DR = new SalutationEnum("DR", "atom.SalutationEnum.DR.description", "atom.SalutationEnum.DR.code", "", "", "");

    public static SalutationEnum getEnum(String inName) {
        return (SalutationEnum) SalutationEnum.getEnum(SalutationEnum.class, (String)inName);
    }

    public static Map getEnumMap() {
        return SalutationEnum.getEnumMap(SalutationEnum.class);
    }

    public static List getEnumList() {
        return SalutationEnum.getEnumList(SalutationEnum.class);
    }

    public static Collection getList() {
        return SalutationEnum.getEnumList(SalutationEnum.class);
    }

    public static Iterator iterator() {
        return SalutationEnum.iterator(SalutationEnum.class);
    }

    protected SalutationEnum(String inKey, String inDescriptionResKey, String inCodeResKey, String inBackgroundColor, String inForegroundColor, String inIconIdPath) {
        super(inKey, inDescriptionResKey, inCodeResKey, inBackgroundColor, inForegroundColor, inIconIdPath);
    }

    @Override
    public String getMappingClassName() {
        return "com.navis.framework.persistence.atoms.UserTypeSalutationEnum";
    }

}
