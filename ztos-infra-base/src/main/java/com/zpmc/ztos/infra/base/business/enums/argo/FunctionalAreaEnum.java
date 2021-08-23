package com.zpmc.ztos.infra.base.business.enums.argo;

import com.zpmc.ztos.infra.base.common.enums.AtomizedEnum;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class FunctionalAreaEnum extends AtomizedEnum {

    public static final FunctionalAreaEnum CARGO = new FunctionalAreaEnum("CARGO", "atom.FunctionalAreaEnum.CARGO.description", "atom.FunctionalAreaEnum.CARGO.code", "", "", "");

    public static FunctionalAreaEnum getEnum(String inName) {
        return (FunctionalAreaEnum) FunctionalAreaEnum.getEnum(FunctionalAreaEnum.class, (String)inName);
    }

    public static Map getEnumMap() {
        return FunctionalAreaEnum.getEnumMap(FunctionalAreaEnum.class);
    }

    public static List getEnumList() {
        return FunctionalAreaEnum.getEnumList(FunctionalAreaEnum.class);
    }

    public static Collection getList() {
        return FunctionalAreaEnum.getEnumList(FunctionalAreaEnum.class);
    }

    public static Iterator iterator() {
        return FunctionalAreaEnum.iterator(FunctionalAreaEnum.class);
    }

    protected FunctionalAreaEnum(String inKey, String inDescriptionResKey, String inCodeResKey, String inBackgroundColor, String inForegroundColor, String inIconIdPath) {
        super(inKey, inDescriptionResKey, inCodeResKey, inBackgroundColor, inForegroundColor, inIconIdPath);
    }

    public String getMappingClassName() {
        return "com.navis.argo.persistence.atoms.UserTypeFunctionalAreaEnum";
    }

}
