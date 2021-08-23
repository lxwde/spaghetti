package com.zpmc.ztos.infra.base.business.enums.edi;

import com.zpmc.ztos.infra.base.common.enums.AtomizedEnum;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class EdiMessageDirectionEnum extends AtomizedEnum {
    public static final EdiMessageDirectionEnum R = new EdiMessageDirectionEnum("R", "atom.EdiMessageDirectionEnum.R.description", "atom.EdiMessageDirectionEnum.R.code", "", "", "");
    public static final EdiMessageDirectionEnum S = new EdiMessageDirectionEnum("S", "atom.EdiMessageDirectionEnum.S.description", "atom.EdiMessageDirectionEnum.S.code", "", "", "");

    public static EdiMessageDirectionEnum getEnum(String inName) {
        return (EdiMessageDirectionEnum) EdiMessageDirectionEnum.getEnum(EdiMessageDirectionEnum.class, (String)inName);
    }

    public static Map getEnumMap() {
        return EdiMessageDirectionEnum.getEnumMap(EdiMessageDirectionEnum.class);
    }

    public static List getEnumList() {
        return EdiMessageDirectionEnum.getEnumList(EdiMessageDirectionEnum.class);
    }

    public static Collection getList() {
        return EdiMessageDirectionEnum.getEnumList(EdiMessageDirectionEnum.class);
    }

    public static Iterator iterator() {
        return EdiMessageDirectionEnum.iterator(EdiMessageDirectionEnum.class);
    }

    protected EdiMessageDirectionEnum(String inKey, String inDescriptionResKey, String inCodeResKey, String inBackgroundColor, String inForegroundColor, String inIconIdPath) {
        super(inKey, inDescriptionResKey, inCodeResKey, inBackgroundColor, inForegroundColor, inIconIdPath);
    }

    public String getMappingClassName() {
        return "com.navis.edi.persistence.atoms.UserTypeEdiMessageDirectionEnum";
    }

}
