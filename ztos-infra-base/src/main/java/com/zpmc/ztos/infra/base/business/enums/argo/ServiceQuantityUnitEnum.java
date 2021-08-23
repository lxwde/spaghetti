package com.zpmc.ztos.infra.base.business.enums.argo;

import com.zpmc.ztos.infra.base.common.enums.AtomizedEnum;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

//public enum ServiceQuantityUnitEnum {
//    UNKNOWN(1, "UNKNOWN", "", "", "UNKNOWN"),
//    CELSIUS(2, "CELSIUS", "", "", "CELSIUS"),
//    CENTIMETERS(3, "CENTIMETERS", "", "", "CENTIMETERS"),
//    DAYS(4, "DAYS", "", "", "DAYS"),
//    FAHRENHEIT(5, "FAHRENHEIT", "", "", "FAHRENHEIT"),
//    GALLONS(6, "GALLONS", "", "", "GALLONS"),
//    HOURS(7, "HOURS", "", "", "HOURS"),
//    ITEMS(8, "ITEMS", "", "", "ITEMS"),
//    KILOS(9, "KILOS", "", "", "KILOS"),
//    LITERS(10, "LITERS", "", "", "LITERS"),
//    METERS(11, "METERS", "", "", "METERS"),
//    METRIC_TONNES(12, "METRIC_TONNES", "", "", "METRIC_TONNES"),
//    MINUTES(13, "MINUTES", "", "", "MINUTES"),
//    POUNDS(14, "POUNDS", "", "", "POUNDS"),
//    SECONDS(15, "SECONDS", "", "", "SECONDS"),
//    SHORT_TONS(16, "SHORT_TONS", "", "", "SHORT_TONS"),
//    UNITS(17, "UNITS", "", "", "UNITS"),
//    CUBICMETER(18, "CUBICMETER", "", "", "CUBICMETER"),
//    LONG_TONS(19, "LONG_TONS", "", "", "LONG_TONS"),
//    GRAMS(20, "GRAMS", "", "", "GRAMS");
//    private final int key;
//    private final String code;
//    private final String desc;
//    private final String color;
//    private final String displayName;
//
//    ServiceQuantityUnitEnum(int key, String code, String desc, String color, String displayName) {
//        this.key = key;
//        this.code = code;
//        this.desc = desc;
//        this.color = color;
//        this.displayName = displayName;
//    }
//
//    public static List getEnumList() {
//        return EnumUtils.getEnumList(ServiceQuantityUnitEnum.class);
//    }
//
//    public static EnumSet getEnumSet() {
//        return EnumSet.allOf(ServiceQuantityUnitEnum.class);
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

public class ServiceQuantityUnitEnum
        extends AtomizedEnum {
    public static final ServiceQuantityUnitEnum UNKNOWN = new ServiceQuantityUnitEnum("UNKNOWN", "atom.ServiceQuantityUnitEnum.UNKNOWN.description", "atom.ServiceQuantityUnitEnum.UNKNOWN.code", "", "", "");
    public static final ServiceQuantityUnitEnum CELSIUS = new ServiceQuantityUnitEnum("CELSIUS", "atom.ServiceQuantityUnitEnum.CELSIUS.description", "atom.ServiceQuantityUnitEnum.CELSIUS.code", "", "", "");
    public static final ServiceQuantityUnitEnum CENTIMETERS = new ServiceQuantityUnitEnum("CENTIMETERS", "atom.ServiceQuantityUnitEnum.CENTIMETERS.description", "atom.ServiceQuantityUnitEnum.CENTIMETERS.code", "", "", "");
    public static final ServiceQuantityUnitEnum DAYS = new ServiceQuantityUnitEnum("DAYS", "atom.ServiceQuantityUnitEnum.DAYS.description", "atom.ServiceQuantityUnitEnum.DAYS.code", "", "", "");
    public static final ServiceQuantityUnitEnum FAHRENHEIT = new ServiceQuantityUnitEnum("FAHRENHEIT", "atom.ServiceQuantityUnitEnum.FAHRENHEIT.description", "atom.ServiceQuantityUnitEnum.FAHRENHEIT.code", "", "", "");
    public static final ServiceQuantityUnitEnum GALLONS = new ServiceQuantityUnitEnum("GALLONS", "atom.ServiceQuantityUnitEnum.GALLONS.description", "atom.ServiceQuantityUnitEnum.GALLONS.code", "", "", "");
    public static final ServiceQuantityUnitEnum HOURS = new ServiceQuantityUnitEnum("HOURS", "atom.ServiceQuantityUnitEnum.HOURS.description", "atom.ServiceQuantityUnitEnum.HOURS.code", "", "", "");
    public static final ServiceQuantityUnitEnum ITEMS = new ServiceQuantityUnitEnum("ITEMS", "atom.ServiceQuantityUnitEnum.ITEMS.description", "atom.ServiceQuantityUnitEnum.ITEMS.code", "", "", "");
    public static final ServiceQuantityUnitEnum KILOS = new ServiceQuantityUnitEnum("KILOS", "atom.ServiceQuantityUnitEnum.KILOS.description", "atom.ServiceQuantityUnitEnum.KILOS.code", "", "", "");
    public static final ServiceQuantityUnitEnum LITERS = new ServiceQuantityUnitEnum("LITERS", "atom.ServiceQuantityUnitEnum.LITERS.description", "atom.ServiceQuantityUnitEnum.LITERS.code", "", "", "");
    public static final ServiceQuantityUnitEnum METERS = new ServiceQuantityUnitEnum("METERS", "atom.ServiceQuantityUnitEnum.METERS.description", "atom.ServiceQuantityUnitEnum.METERS.code", "", "", "");
    public static final ServiceQuantityUnitEnum METRIC_TONNES = new ServiceQuantityUnitEnum("METRIC_TONNES", "atom.ServiceQuantityUnitEnum.METRIC_TONNES.description", "atom.ServiceQuantityUnitEnum.METRIC_TONNES.code", "", "", "");
    public static final ServiceQuantityUnitEnum MINUTES = new ServiceQuantityUnitEnum("MINUTES", "atom.ServiceQuantityUnitEnum.MINUTES.description", "atom.ServiceQuantityUnitEnum.MINUTES.code", "", "", "");
    public static final ServiceQuantityUnitEnum POUNDS = new ServiceQuantityUnitEnum("POUNDS", "atom.ServiceQuantityUnitEnum.POUNDS.description", "atom.ServiceQuantityUnitEnum.POUNDS.code", "", "", "");
    public static final ServiceQuantityUnitEnum SECONDS = new ServiceQuantityUnitEnum("SECONDS", "atom.ServiceQuantityUnitEnum.SECONDS.description", "atom.ServiceQuantityUnitEnum.SECONDS.code", "", "", "");
    public static final ServiceQuantityUnitEnum SHORT_TONS = new ServiceQuantityUnitEnum("SHORT_TONS", "atom.ServiceQuantityUnitEnum.SHORT_TONS.description", "atom.ServiceQuantityUnitEnum.SHORT_TONS.code", "", "", "");
    public static final ServiceQuantityUnitEnum UNITS = new ServiceQuantityUnitEnum("UNITS", "atom.ServiceQuantityUnitEnum.UNITS.description", "atom.ServiceQuantityUnitEnum.UNITS.code", "", "", "");
    public static final ServiceQuantityUnitEnum CUBICMETER = new ServiceQuantityUnitEnum("CUBICMETER", "atom.ServiceQuantityUnitEnum.CUBICMETER.description", "atom.ServiceQuantityUnitEnum.CUBICMETER.code", "", "", "");
    public static final ServiceQuantityUnitEnum LONG_TONS = new ServiceQuantityUnitEnum("LONG_TONS", "atom.ServiceQuantityUnitEnum.LONG_TONS.description", "atom.ServiceQuantityUnitEnum.LONG_TONS.code", "", "", "");
    public static final ServiceQuantityUnitEnum GRAMS = new ServiceQuantityUnitEnum("GRAMS", "atom.ServiceQuantityUnitEnum.GRAMS.description", "atom.ServiceQuantityUnitEnum.GRAMS.code", "", "", "");

    public static ServiceQuantityUnitEnum getEnum(String inName) {
        return (ServiceQuantityUnitEnum) ServiceQuantityUnitEnum.getEnum(ServiceQuantityUnitEnum.class, (String)inName);
    }

    public static Map getEnumMap() {
        return ServiceQuantityUnitEnum.getEnumMap(ServiceQuantityUnitEnum.class);
    }

    public static List getEnumList() {
        return ServiceQuantityUnitEnum.getEnumList(ServiceQuantityUnitEnum.class);
    }

    public static Collection getList() {
        return ServiceQuantityUnitEnum.getEnumList(ServiceQuantityUnitEnum.class);
    }

    public static Iterator iterator() {
        return ServiceQuantityUnitEnum.iterator(ServiceQuantityUnitEnum.class);
    }

    protected ServiceQuantityUnitEnum(String inKey, String inDescriptionResKey, String inCodeResKey, String inBackgroundColor, String inForegroundColor, String inIconIdPath) {
        super(inKey, inDescriptionResKey, inCodeResKey, inBackgroundColor, inForegroundColor, inIconIdPath);
    }

    public String getMappingClassName() {
        return "com.navis.argo.persistence.atoms.UserTypeServiceQuantityUnitEnum";
    }
}
