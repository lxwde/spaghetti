package com.zpmc.ztos.infra.base.business.enums.framework;

import com.zpmc.ztos.infra.base.common.enums.AtomizedEnum;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class AllLifeCycleStatesEnum extends AtomizedEnum {
    public static final AllLifeCycleStatesEnum ALL = new AllLifeCycleStatesEnum("ALL", "atom.AllLifeCycleStatesEnum.ALL.description", "atom.AllLifeCycleStatesEnum.ALL.code", "", "", "");

    public static AllLifeCycleStatesEnum getEnum(String inName) {
        return (AllLifeCycleStatesEnum) AllLifeCycleStatesEnum.getEnum(AllLifeCycleStatesEnum.class, (String)inName);
    }

    public static Map getEnumMap() {
        return AllLifeCycleStatesEnum.getEnumMap(AllLifeCycleStatesEnum.class);
    }

    public static List getEnumList() {
        return AllLifeCycleStatesEnum.getEnumList(AllLifeCycleStatesEnum.class);
    }

    public static Collection getList() {
        return AllLifeCycleStatesEnum.getEnumList(AllLifeCycleStatesEnum.class);
    }

    public static Iterator iterator() {
        return AllLifeCycleStatesEnum.iterator(AllLifeCycleStatesEnum.class);
    }

    protected AllLifeCycleStatesEnum(String inKey, String inDescriptionResKey, String inCodeResKey, String inBackgroundColor, String inForegroundColor, String inIconIdPath) {
        super(inKey, inDescriptionResKey, inCodeResKey, inBackgroundColor, inForegroundColor, inIconIdPath);
    }

    @Override
    public String getMappingClassName() {
        return "com.navis.framework.persistence.atoms.UserTypeAllLifeCycleStatesEnum";
    }

}
