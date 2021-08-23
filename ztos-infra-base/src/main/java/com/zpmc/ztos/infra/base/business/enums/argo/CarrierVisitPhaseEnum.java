package com.zpmc.ztos.infra.base.business.enums.argo;

import com.zpmc.ztos.infra.base.common.enums.AtomizedEnum;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

//public enum CarrierVisitPhaseEnum {
//    CREATED(1, "CREATED", "carrier created", "", "CREATED"),
//    INBOUND(2, "INBOUND", "carrier inbound", "", "INBOUND"),
//    ARRIVED(3, "ARRIVED", "carrier arrived", "", "ARRIVED"),
//    WORKING(4, "WORKING", "carrier is working", "", "WORKING"),
//    COMPLETE(5, "COMPLETE", "carrier complete", "", "COMPLETE"),
//    DEPARTED(6, "DEPARTED", "carrier departed", "", "DEPARTED"),
//    CLOSED(7, "CLOSED", "carrier closed", "", "CLOSED"),
//    CANCELED(8, "CANCELED", "carrier canceled", "", "CANCELED"),
//    ARCHIVED(9, "ARCHIVED", "carrier archived", "", "ARCHIVED");
//    private final int key;
//    private final String code;
//    private final String desc;
//    private final String color;
//    private final String displayName;
//
//    CarrierVisitPhaseEnum(int key, String code, String desc, String color, String displayName) {
//        this.key = key;
//        this.code = code;
//        this.desc = desc;
//        this.color = color;
//        this.displayName = displayName;
//    }
//
//    public static List getEnumList() {
//        return EnumUtils.getEnumList(CarrierVisitPhaseEnum.class);
//    }
//
//    public static EnumSet getEnumSet() {
//        return EnumSet.allOf(CarrierVisitPhaseEnum.class);
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

public class CarrierVisitPhaseEnum
        extends AtomizedEnum {
    public static final CarrierVisitPhaseEnum CREATED = new CarrierVisitPhaseEnum("10CREATED", "atom.CarrierVisitPhaseEnum.CREATED.description", "atom.CarrierVisitPhaseEnum.CREATED.code", "cornsilk", "", "");
    public static final CarrierVisitPhaseEnum INBOUND = new CarrierVisitPhaseEnum("20INBOUND", "atom.CarrierVisitPhaseEnum.INBOUND.description", "atom.CarrierVisitPhaseEnum.INBOUND.code", "bisque", "", "");
    public static final CarrierVisitPhaseEnum ARRIVED = new CarrierVisitPhaseEnum("30ARRIVED", "atom.CarrierVisitPhaseEnum.ARRIVED.description", "atom.CarrierVisitPhaseEnum.ARRIVED.code", "sandybrown", "", "");
    public static final CarrierVisitPhaseEnum WORKING = new CarrierVisitPhaseEnum("40WORKING", "atom.CarrierVisitPhaseEnum.WORKING.description", "atom.CarrierVisitPhaseEnum.WORKING.code", "gold", "", "");
    public static final CarrierVisitPhaseEnum COMPLETE = new CarrierVisitPhaseEnum("50COMPLETE", "atom.CarrierVisitPhaseEnum.COMPLETE.description", "atom.CarrierVisitPhaseEnum.COMPLETE.code", "darksalmon", "", "");
    public static final CarrierVisitPhaseEnum DEPARTED = new CarrierVisitPhaseEnum("60DEPARTED", "atom.CarrierVisitPhaseEnum.DEPARTED.description", "atom.CarrierVisitPhaseEnum.DEPARTED.code", "sienna", "", "");
    public static final CarrierVisitPhaseEnum CLOSED = new CarrierVisitPhaseEnum("70CLOSED", "atom.CarrierVisitPhaseEnum.CLOSED.description", "atom.CarrierVisitPhaseEnum.CLOSED.code", "darkgray", "", "");
    public static final CarrierVisitPhaseEnum CANCELED = new CarrierVisitPhaseEnum("80CANCELED", "atom.CarrierVisitPhaseEnum.CANCELED.description", "atom.CarrierVisitPhaseEnum.CANCELED.code", "crimson", "", "");
    public static final CarrierVisitPhaseEnum ARCHIVED = new CarrierVisitPhaseEnum("90ARCHIVED", "atom.CarrierVisitPhaseEnum.ARCHIVED.description", "atom.CarrierVisitPhaseEnum.ARCHIVED.code", "dimgray", "", "");

    public static CarrierVisitPhaseEnum getEnum(String inName) {
        return (CarrierVisitPhaseEnum) CarrierVisitPhaseEnum.getEnum(CarrierVisitPhaseEnum.class, (String)inName);
    }

    public static Map getEnumMap() {
        return CarrierVisitPhaseEnum.getEnumMap(CarrierVisitPhaseEnum.class);
    }

    public static List getEnumList() {
        return CarrierVisitPhaseEnum.getEnumList(CarrierVisitPhaseEnum.class);
    }

    public static Collection getList() {
        return CarrierVisitPhaseEnum.getEnumList(CarrierVisitPhaseEnum.class);
    }

    public static Iterator iterator() {
        return CarrierVisitPhaseEnum.iterator(CarrierVisitPhaseEnum.class);
    }

    protected CarrierVisitPhaseEnum(String inKey, String inDescriptionResKey, String inCodeResKey, String inBackgroundColor, String inForegroundColor, String inIconIdPath) {
        super(inKey, inDescriptionResKey, inCodeResKey, inBackgroundColor, inForegroundColor, inIconIdPath);
    }

    public String getMappingClassName() {
        return "com.navis.argo.persistence.atoms.UserTypeCarrierVisitPhaseEnum";
    }
}