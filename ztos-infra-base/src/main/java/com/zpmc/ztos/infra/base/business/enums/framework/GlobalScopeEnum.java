package com.zpmc.ztos.infra.base.business.enums.framework;

import com.zpmc.ztos.infra.base.common.model.ScopeCoordinates;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class GlobalScopeEnum extends AbstractScopeEnum{
    public static final GlobalScopeEnum GLOBAL = new GlobalScopeEnum("GLB", "atom.GlobalScopeEnum.GLOBAL.description", "atom.GlobalScopeEnum.GLOBAL.code", "", "", "", ScopeCoordinates.GLOBAL_LEVEL);

    public static GlobalScopeEnum getEnum(String inName) {
        return (GlobalScopeEnum)GlobalScopeEnum.getEnum(GlobalScopeEnum.class, (String)inName);
    }

    public static Map getEnumMap() {
        return GlobalScopeEnum.getEnumMap(GlobalScopeEnum.class);
    }

    public static List getEnumList() {
        return GlobalScopeEnum.getEnumList(GlobalScopeEnum.class);
    }

    public static Collection getList() {
        return GlobalScopeEnum.getEnumList(GlobalScopeEnum.class);
    }

    public static Iterator iterator() {
        return GlobalScopeEnum.iterator(GlobalScopeEnum.class);
    }

    protected GlobalScopeEnum(String inKey, String inDescriptionResKey, String inCodeResKey, String inBackgroundColor, String inForegroundColor, String inIconIdPath, Long inScopeLevel) {
        super(inKey, inDescriptionResKey, inCodeResKey, inBackgroundColor, inForegroundColor, inIconIdPath, inScopeLevel);
    }

    @Override
    public String getMappingClassName() {
        return "com.navis.framework.persistence.atoms.UserTypeGlobalScopeEnum";
    }

}
