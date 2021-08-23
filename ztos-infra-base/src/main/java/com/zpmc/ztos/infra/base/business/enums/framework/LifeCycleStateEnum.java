package com.zpmc.ztos.infra.base.business.enums.framework;

import com.zpmc.ztos.infra.base.common.enums.AtomizedEnum;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class LifeCycleStateEnum extends AtomizedEnum {
    public static final LifeCycleStateEnum ACTIVE = new LifeCycleStateEnum("ACT", "atom.LifeCycleStateEnum.ACTIVE.description", "atom.LifeCycleStateEnum.ACTIVE.code", "", "", "");
    public static final LifeCycleStateEnum OBSOLETE = new LifeCycleStateEnum("OBS", "atom.LifeCycleStateEnum.OBSOLETE.description", "atom.LifeCycleStateEnum.OBSOLETE.code", "", "", "");

    public static LifeCycleStateEnum getEnum(String inName) {
        return (LifeCycleStateEnum) LifeCycleStateEnum.getEnum(LifeCycleStateEnum.class, (String)inName);
    }

    public static Map getEnumMap() {
        return LifeCycleStateEnum.getEnumMap(LifeCycleStateEnum.class);
    }

    public static List getEnumList() {
        return LifeCycleStateEnum.getEnumList(LifeCycleStateEnum.class);
    }

    public static Collection getList() {
        return LifeCycleStateEnum.getEnumList(LifeCycleStateEnum.class);
    }

    public static Iterator iterator() {
        return LifeCycleStateEnum.iterator(LifeCycleStateEnum.class);
    }

    protected LifeCycleStateEnum(String inKey, String inDescriptionResKey, String inCodeResKey, String inBackgroundColor, String inForegroundColor, String inIconIdPath) {
        super(inKey, inDescriptionResKey, inCodeResKey, inBackgroundColor, inForegroundColor, inIconIdPath);
    }

    @Override
    public String getMappingClassName() {
        return "com.navis.framework.persistence.atoms.UserTypeLifeCycleStateEnum";
    }

}
