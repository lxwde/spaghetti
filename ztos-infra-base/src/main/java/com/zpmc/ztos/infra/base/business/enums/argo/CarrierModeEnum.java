package com.zpmc.ztos.infra.base.business.enums.argo;

import com.zpmc.ztos.infra.base.common.enums.AtomizedEnum;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

//public enum CarrierModeEnum {
//    TRAIN(1, "TRAIN", "train", "", "TRAIN"),
//    TRUCK(2, "TRUCK", "truck", "", "TRUCK"),
//    VESSEL(3, "VESSEL", "vessel", "", "VESSEL"),
//    UNKNOWN(4, "UNKNOWN", "unknown", "", "UNKNOWN");
//    private final int key;
//    private final String code;
//    private final String desc;
//    private final String color;
//    private final String displayName;
//
//    CarrierModeEnum(int key, String code, String desc, String color, String displayName) {
//        this.key = key;
//        this.code = code;
//        this.desc = desc;
//        this.color = color;
//        this.displayName = displayName;
//    }
//
//    public static List getEnumList() {
//        return EnumUtils.getEnumList(CarrierModeEnum.class);
//    }
//
//    public static EnumSet getEnumSet() {
//        return EnumSet.allOf(CarrierModeEnum.class);
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

public class CarrierModeEnum
        extends AtomizedEnum {
    public static final CarrierModeEnum TRAIN = new CarrierModeEnum("TRAIN", "atom.CarrierModeEnum.TRAIN.description", "atom.CarrierModeEnum.TRAIN.code", "", "", "");
    public static final CarrierModeEnum TRUCK = new CarrierModeEnum("TRUCK", "atom.CarrierModeEnum.TRUCK.description", "atom.CarrierModeEnum.TRUCK.code", "", "", "");
    public static final CarrierModeEnum VESSEL = new CarrierModeEnum("VESSEL", "atom.CarrierModeEnum.VESSEL.description", "atom.CarrierModeEnum.VESSEL.code", "", "", "");
    public static final CarrierModeEnum UNKNOWN = new CarrierModeEnum("UNKNOWN", "atom.CarrierModeEnum.UNKNOWN.description", "atom.CarrierModeEnum.UNKNOWN.code", "", "", "");

    public static CarrierModeEnum getEnum(String inName) {
        return (CarrierModeEnum) CarrierModeEnum.getEnum(CarrierModeEnum.class, (String)inName);
    }

    public static Map getEnumMap() {
        return CarrierModeEnum.getEnumMap(CarrierModeEnum.class);
    }

    public static List getEnumList() {
        return CarrierModeEnum.getEnumList(CarrierModeEnum.class);
    }

    public static Collection getList() {
        return CarrierModeEnum.getEnumList(CarrierModeEnum.class);
    }

    public static Iterator iterator() {
        return CarrierModeEnum.iterator(CarrierModeEnum.class);
    }

    protected CarrierModeEnum(String inKey, String inDescriptionResKey, String inCodeResKey, String inBackgroundColor, String inForegroundColor, String inIconIdPath) {
        super(inKey, inDescriptionResKey, inCodeResKey, inBackgroundColor, inForegroundColor, inIconIdPath);
    }

    public String getMappingClassName() {
        return "com.navis.argo.persistence.atoms.UserTypeCarrierModeEnum";
    }
}
