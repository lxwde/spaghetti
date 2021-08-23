package com.zpmc.ztos.infra.base.business.enums.framework;

import com.zpmc.ztos.infra.base.common.model.ScopeCoordinates;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class GeoScopeEnum extends AbstractScopeEnum{
    public static final GeoScopeEnum GLOBAL = new GeoScopeEnum("GLB", "atom.GeoScopeEnum.GLOBAL.description", "atom.GeoScopeEnum.GLOBAL.code", "", "", "", ScopeCoordinates.GLOBAL_LEVEL);
    public static final GeoScopeEnum COUNTRY = new GeoScopeEnum("CTRY", "atom.GeoScopeEnum.COUNTRY.description", "atom.GeoScopeEnum.COUNTRY.code", "", "", "", ScopeCoordinates.SCOPE_LEVEL_1);
    public static final GeoScopeEnum STATE = new GeoScopeEnum("ST", "atom.GeoScopeEnum.STATE.description", "atom.GeoScopeEnum.STATE.code", "", "", "", ScopeCoordinates.SCOPE_LEVEL_2);
    public static final GeoScopeEnum CITY = new GeoScopeEnum("CTY", "atom.GeoScopeEnum.CITY.description", "atom.GeoScopeEnum.CITY.code", "", "", "", ScopeCoordinates.SCOPE_LEVEL_3);

    public static GeoScopeEnum getEnum(String inName) {
        return (GeoScopeEnum) GeoScopeEnum.getEnum(GeoScopeEnum.class, (String)inName);
    }

    public static Map getEnumMap() {
        return GeoScopeEnum.getEnumMap(GeoScopeEnum.class);
    }

    public static List getEnumList() {
        return GeoScopeEnum.getEnumList(GeoScopeEnum.class);
    }

    public static Collection getList() {
        return GeoScopeEnum.getEnumList(GeoScopeEnum.class);
    }

    public static Iterator iterator() {
        return GeoScopeEnum.iterator(GeoScopeEnum.class);
    }

    protected GeoScopeEnum(String inKey, String inDescriptionResKey, String inCodeResKey, String inBackgroundColor, String inForegroundColor, String inIconIdPath, Long inScopeLevel) {
        super(inKey, inDescriptionResKey, inCodeResKey, inBackgroundColor, inForegroundColor, inIconIdPath, inScopeLevel);
    }

    @Override
    public String getMappingClassName() {
        return "com.navis.framework.persistence.atoms.UserTypeGeoScopeEnum";
    }


}
