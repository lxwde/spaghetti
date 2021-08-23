package com.zpmc.ztos.infra.base.business.enums.edi;

import com.zpmc.ztos.infra.base.common.enums.AtomizedEnum;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class EdiMessageStandardEnum extends AtomizedEnum {
    public static final EdiMessageStandardEnum X12 = new EdiMessageStandardEnum("X12", "atom.EdiMessageStandardEnum.X12.description", "atom.EdiMessageStandardEnum.X12.code", "", "", "");
    public static final EdiMessageStandardEnum EDIFACT = new EdiMessageStandardEnum("EDIFACT", "atom.EdiMessageStandardEnum.EDIFACT.description", "atom.EdiMessageStandardEnum.EDIFACT.code", "", "", "");
    public static final EdiMessageStandardEnum XML = new EdiMessageStandardEnum("XML", "atom.EdiMessageStandardEnum.XML.description", "atom.EdiMessageStandardEnum.XML.code", "", "", "");
    public static final EdiMessageStandardEnum FLATFILE = new EdiMessageStandardEnum("FLATFILE", "atom.EdiMessageStandardEnum.FLATFILE.description", "atom.EdiMessageStandardEnum.FLATFILE.code", "", "", "");
    public static final EdiMessageStandardEnum OTHER = new EdiMessageStandardEnum("OTHER", "atom.EdiMessageStandardEnum.OTHER.description", "atom.EdiMessageStandardEnum.OTHER.code", "", "", "");

    public static EdiMessageStandardEnum getEnum(String inName) {
        return (EdiMessageStandardEnum) EdiMessageStandardEnum.getEnum(EdiMessageStandardEnum.class, (String)inName);
    }

    public static Map getEnumMap() {
        return EdiMessageStandardEnum.getEnumMap(EdiMessageStandardEnum.class);
    }

    public static List getEnumList() {
        return EdiMessageStandardEnum.getEnumList(EdiMessageStandardEnum.class);
    }

    public static Collection getList() {
        return EdiMessageStandardEnum.getEnumList(EdiMessageStandardEnum.class);
    }

    public static Iterator iterator() {
        return EdiMessageStandardEnum.iterator(EdiMessageStandardEnum.class);
    }

    protected EdiMessageStandardEnum(String inKey, String inDescriptionResKey, String inCodeResKey, String inBackgroundColor, String inForegroundColor, String inIconIdPath) {
        super(inKey, inDescriptionResKey, inCodeResKey, inBackgroundColor, inForegroundColor, inIconIdPath);
    }

    public String getMappingClassName() {
        return "com.navis.edi.persistence.atoms.UserTypeEdiMessageStandardEnum";
    }

}
