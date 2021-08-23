package com.zpmc.ztos.infra.base.business.enums.edi;

import com.zpmc.ztos.infra.base.common.enums.AtomizedEnum;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class EdiMessageAgencyEnum extends AtomizedEnum {
    public static final EdiMessageAgencyEnum UN = new EdiMessageAgencyEnum("UN", "atom.EdiMessageAgencyEnum.UN.description", "atom.EdiMessageAgencyEnum.UN.code", "", "", "");
    public static final EdiMessageAgencyEnum ANSI = new EdiMessageAgencyEnum("ANSI", "atom.EdiMessageAgencyEnum.ANSI.description", "atom.EdiMessageAgencyEnum.ANSI.code", "", "", "");
    public static final EdiMessageAgencyEnum ACOS = new EdiMessageAgencyEnum("ACOS", "atom.EdiMessageAgencyEnum.ACOS.description", "atom.EdiMessageAgencyEnum.ACOS.code", "", "", "");
    public static final EdiMessageAgencyEnum NONE = new EdiMessageAgencyEnum("NONE", "atom.EdiMessageAgencyEnum.NONE.description", "atom.EdiMessageAgencyEnum.NONE.code", "", "", "");

    public static EdiMessageAgencyEnum getEnum(String inName) {
        return (EdiMessageAgencyEnum) EdiMessageAgencyEnum.getEnum(EdiMessageAgencyEnum.class, (String)inName);
    }

    public static Map getEnumMap() {
        return EdiMessageAgencyEnum.getEnumMap(EdiMessageAgencyEnum.class);
    }

    public static List getEnumList() {
        return EdiMessageAgencyEnum.getEnumList(EdiMessageAgencyEnum.class);
    }

    public static Collection getList() {
        return EdiMessageAgencyEnum.getEnumList(EdiMessageAgencyEnum.class);
    }

    public static Iterator iterator() {
        return EdiMessageAgencyEnum.iterator(EdiMessageAgencyEnum.class);
    }

    protected EdiMessageAgencyEnum(String inKey, String inDescriptionResKey, String inCodeResKey, String inBackgroundColor, String inForegroundColor, String inIconIdPath) {
        super(inKey, inDescriptionResKey, inCodeResKey, inBackgroundColor, inForegroundColor, inIconIdPath);
    }

    public String getMappingClassName() {
        return "com.navis.edi.persistence.atoms.UserTypeEdiMessageAgencyEnum";
    }
}
