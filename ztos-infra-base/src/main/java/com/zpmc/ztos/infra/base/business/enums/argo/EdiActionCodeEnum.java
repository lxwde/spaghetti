package com.zpmc.ztos.infra.base.business.enums.argo;

import com.zpmc.ztos.infra.base.common.enums.AtomizedEnum;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class EdiActionCodeEnum extends AtomizedEnum {
    public static final EdiActionCodeEnum O = new EdiActionCodeEnum("O", "atom.EdiActionCodeEnum.O.description", "atom.EdiActionCodeEnum.O.code", "green", "", "");
    public static final EdiActionCodeEnum U = new EdiActionCodeEnum("U", "atom.EdiActionCodeEnum.U.description", "atom.EdiActionCodeEnum.U.code", "deepskyblue", "", "");
    public static final EdiActionCodeEnum R = new EdiActionCodeEnum("R", "atom.EdiActionCodeEnum.R.description", "atom.EdiActionCodeEnum.R.code", "red", "", "");

    public static EdiActionCodeEnum getEnum(String inName) {
        return (EdiActionCodeEnum) EdiActionCodeEnum.getEnum(EdiActionCodeEnum.class, (String)inName);
    }

    public static Map getEnumMap() {
        return EdiActionCodeEnum.getEnumMap(EdiActionCodeEnum.class);
    }

    public static List getEnumList() {
        return EdiActionCodeEnum.getEnumList(EdiActionCodeEnum.class);
    }

    public static Collection getList() {
        return EdiActionCodeEnum.getEnumList(EdiActionCodeEnum.class);
    }

    public static Iterator iterator() {
        return EdiActionCodeEnum.iterator(EdiActionCodeEnum.class);
    }

    protected EdiActionCodeEnum(String inKey, String inDescriptionResKey, String inCodeResKey, String inBackgroundColor, String inForegroundColor, String inIconIdPath) {
        super(inKey, inDescriptionResKey, inCodeResKey, inBackgroundColor, inForegroundColor, inIconIdPath);
    }

    public String getMappingClassName() {
        return "com.navis.argo.persistence.atoms.UserTypeEdiActionCodeEnum";
    }

}
