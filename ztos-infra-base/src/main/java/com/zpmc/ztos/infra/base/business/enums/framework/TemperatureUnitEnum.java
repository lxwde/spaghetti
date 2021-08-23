package com.zpmc.ztos.infra.base.business.enums.framework;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class TemperatureUnitEnum extends AbstractMeasurementUnitEnum{
    public static final TemperatureUnitEnum C = new TemperatureUnitEnum("C", "atom.TemperatureUnitEnum.C.description", "atom.TemperatureUnitEnum.C.code", "", "", "", new String[]{"C", "c"}, 1.0, 0.0);
    public static final TemperatureUnitEnum F = new TemperatureUnitEnum("F", "atom.TemperatureUnitEnum.F.description", "atom.TemperatureUnitEnum.F.code", "", "", "", new String[]{"F", "f"}, 0.5555555555555556, -32.0);

    public static TemperatureUnitEnum getEnum(String inName) {
        return (TemperatureUnitEnum) TemperatureUnitEnum.getEnum(TemperatureUnitEnum.class, (String)inName);
    }

    public static Map getEnumMap() {
        return TemperatureUnitEnum.getEnumMap(TemperatureUnitEnum.class);
    }

    public static List getEnumList() {
        return TemperatureUnitEnum.getEnumList(TemperatureUnitEnum.class);
    }

    public static Collection getList() {
        return TemperatureUnitEnum.getEnumList(TemperatureUnitEnum.class);
    }

    public static Iterator iterator() {
        return TemperatureUnitEnum.iterator(TemperatureUnitEnum.class);
    }

    protected TemperatureUnitEnum(String inKey, String inDescriptionResKey, String inCodeResKey, String inBackgroundColor, String inForegroundColor, String inIconIdPath, String[] inAbbreviations, double inConversionFactor, double inStandardUnitOffset) {
        super(inKey, inDescriptionResKey, inCodeResKey, inBackgroundColor, inForegroundColor, inIconIdPath, inAbbreviations, inConversionFactor, inStandardUnitOffset);
    }

    @Override
    public String getMappingClassName() {
        return "com.navis.framework.persistence.atoms.UserTypeTemperatureUnitEnum";
    }

}
