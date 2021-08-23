package com.zpmc.ztos.infra.base.business.enums.core;

import com.zpmc.ztos.infra.base.business.enums.framework.AbstractMeasurementUnitEnum;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

//public enum MassUnitEnum {
//    GRAMS(1, "GRAMS", "", "", "GRAMS"),
//    KILOGRAMS(2, "WARNINGS", "", "", "WARNINGS"),
//    POUNDS(3, "WARNINGS", "", "", "WARNINGS"),
//    QUINTALS(4, "WARNINGS", "", "", "WARNINGS"),
//    LONG_TONS(5, "WARNINGS", "", "", "WARNINGS"),
//    SHORT_TONS(6, "WARNINGS", "", "", "WARNINGS"),
//    TONNES(7, "WARNINGS", "", "", "WARNINGS");
//    private final int key;
//    private final String code;
//    private final String desc;
//    private final String color;
//    private final String displayName;
//
//    MassUnitEnum(int key, String code, String desc, String color, String displayName) {
//        this.key = key;
//        this.code = code;
//        this.desc = desc;
//        this.color = color;
//        this.displayName = displayName;
//    }
//
//    public static List getEnumList() {
//        return EnumUtils.getEnumList(MassUnitEnum.class);
//    }
//
//    public static EnumSet getEnumSet() {
//        return EnumSet.allOf(MassUnitEnum.class);
//    }
//
//    public int getKey() {
//        return key;
//    }
//
//    public String getCode() {
//        return code;
//    }
//
//    public String getDesc() {
//        return desc;
//    }
//
//    public String getColor() {
//        return color;
//    }
//
//    public String getDisplayName() {
//        return displayName;
//    }
//
//}

public class MassUnitEnum
        extends AbstractMeasurementUnitEnum {
    public static final MassUnitEnum GRAMS = new MassUnitEnum("g", "atom.MassUnitEnum.GRAMS.description", "atom.MassUnitEnum.GRAMS.code", "", "", "", new String[]{"G", "g", "gr", "GR"}, 0.001);
    public static final MassUnitEnum KILOGRAMS = new MassUnitEnum("kg", "atom.MassUnitEnum.KILOGRAMS.description", "atom.MassUnitEnum.KILOGRAMS.code", "", "", "", new String[]{"KG", "kg", "kgs", "Kg"}, 1.0);
    public static final MassUnitEnum POUNDS = new MassUnitEnum("lb", "atom.MassUnitEnum.POUNDS.description", "atom.MassUnitEnum.POUNDS.code", "", "", "", new String[]{"LB", "lb", "lbs", "Lb"}, 0.45359237);
    public static final MassUnitEnum QUINTALS = new MassUnitEnum("quintals", "atom.MassUnitEnum.QUINTALS.description", "atom.MassUnitEnum.QUINTALS.code", "", "", "", new String[]{"QT", "qt", "quintals", "qntls"}, 100.0);
    public static final MassUnitEnum LONG_TONS = new MassUnitEnum("long-ton", "atom.MassUnitEnum.LONG_TONS.description", "atom.MassUnitEnum.LONG_TONS.code", "", "", "", new String[]{"LT", "lt", "TONS", "Lt", "long-ton"}, 1016.05);
    public static final MassUnitEnum SHORT_TONS = new MassUnitEnum("short-ton", "atom.MassUnitEnum.SHORT_TONS.description", "atom.MassUnitEnum.SHORT_TONS.code", "", "", "", new String[]{"ST", "st", "St", "short-ton"}, 907.18474);
    public static final MassUnitEnum TONNES = new MassUnitEnum("tonne", "atom.MassUnitEnum.TONNES.description", "atom.MassUnitEnum.TONNES.code", "", "", "", new String[]{"MT", "mt", "TONNES", "T", "Mt", "metric-ton", "tonne"}, 1000.0);

    public static MassUnitEnum getEnum(String inName) {
        return (MassUnitEnum) MassUnitEnum.getEnum(MassUnitEnum.class, (String)inName);
    }

    public static Map getEnumMap() {
        return MassUnitEnum.getEnumMap(MassUnitEnum.class);
    }

    public static List getEnumList() {
        return MassUnitEnum.getEnumList(MassUnitEnum.class);
    }

    public static Collection getList() {
        return MassUnitEnum.getEnumList(MassUnitEnum.class);
    }

    public static Iterator iterator() {
        return MassUnitEnum.iterator(MassUnitEnum.class);
    }

    protected MassUnitEnum(String inKey, String inDescriptionResKey, String inCodeResKey, String inBackgroundColor, String inForegroundColor, String inIconIdPath, String[] inAbbreviations, double inConversionFactor) {
        super(inKey, inDescriptionResKey, inCodeResKey, inBackgroundColor, inForegroundColor, inIconIdPath, inAbbreviations, inConversionFactor);
    }

    @Override
    public String getMappingClassName() {
        return "com.navis.framework.persistence.atoms.UserTypeMassUnitEnum";
    }
}