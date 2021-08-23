package com.zpmc.ztos.infra.base.business.enums.argo;

import com.zpmc.ztos.infra.base.common.enums.AtomizedEnum;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

//public enum LocTypeEnum {
//    YARD(1, "YARD", "", "", "YARD"),
//    VESSEL(2, "VESSEL", "", "", "VESSEL"),
//    RAILCAR(3, "RAILCAR", "", "", "RAILCAR"),
//    TRAIN(4, "TRAIN", "", "", "TRAIN"),
//    TRUCK(5, "TRUCK", "", "", "TRUCK"),
//    COMMUNITY(6, "COMMUNITY", "", "", "COMMUNITY"),
//    SEA(7, "SEA", "", "", "SEA"),
//    UNKNOWN(8, "UNKNOWN", "", "", "UNKNOWN"),
//    CONTAINER(9, "CONTAINER", "", "", "CONTAINER");
//    private final int key;
//    private final String code;
//    private final String desc;
//    private final String color;
//    private final String displayName;
//
//    LocTypeEnum(int key, String code, String desc, String color, String displayName) {
//        this.key = key;
//        this.code = code;
//        this.desc = desc;
//        this.color = color;
//        this.displayName = displayName;
//    }
//
//    public static List getEnumList() {
//        return EnumUtils.getEnumList(LocTypeEnum.class);
//    }
//
//    public static EnumSet getEnumSet() {
//        return EnumSet.allOf(LocTypeEnum.class);
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


public class LocTypeEnum
        extends AtomizedEnum {
    public static final LocTypeEnum YARD = new LocTypeEnum("YARD", "atom.LocTypeEnum.YARD.description", "atom.LocTypeEnum.YARD.code", "sienna", "", "");
    public static final LocTypeEnum VESSEL = new LocTypeEnum("VESSEL", "atom.LocTypeEnum.VESSEL.description", "atom.LocTypeEnum.VESSEL.code", "darkslateblue", "", "");
    public static final LocTypeEnum RAILCAR = new LocTypeEnum("RAILCAR", "atom.LocTypeEnum.RAILCAR.description", "atom.LocTypeEnum.RAILCAR.code", "navajowhite", "", "");
    public static final LocTypeEnum TRAIN = new LocTypeEnum("TRAIN", "atom.LocTypeEnum.TRAIN.description", "atom.LocTypeEnum.TRAIN.code", "moccasin", "", "");
    public static final LocTypeEnum TRUCK = new LocTypeEnum("TRUCK", "atom.LocTypeEnum.TRUCK.description", "atom.LocTypeEnum.TRUCK.code", "olivedrab", "", "");
    public static final LocTypeEnum COMMUNITY = new LocTypeEnum("COMMUNITY", "atom.LocTypeEnum.COMMUNITY.description", "atom.LocTypeEnum.COMMUNITY.code", "khaki", "", "");
    public static final LocTypeEnum SEA = new LocTypeEnum("SEA", "atom.LocTypeEnum.SEA.description", "atom.LocTypeEnum.SEA.code", "lightblue", "", "");
    public static final LocTypeEnum UNKNOWN = new LocTypeEnum("UNKNOWN", "atom.LocTypeEnum.UNKNOWN.description", "atom.LocTypeEnum.UNKNOWN.code", "lightslategray", "", "");
    public static final LocTypeEnum CONTAINER = new LocTypeEnum("CONTAINER", "atom.LocTypeEnum.CONTAINER.description", "atom.LocTypeEnum.CONTAINER.code", "red", "", "");

    public static LocTypeEnum getEnum(String inName) {
        return (LocTypeEnum) LocTypeEnum.getEnum(LocTypeEnum.class, (String)inName);
    }

    public static Map getEnumMap() {
        return LocTypeEnum.getEnumMap(LocTypeEnum.class);
    }

    public static List getEnumList() {
        return LocTypeEnum.getEnumList(LocTypeEnum.class);
    }

    public static Collection getList() {
        return LocTypeEnum.getEnumList(LocTypeEnum.class);
    }

    public static Iterator iterator() {
        return LocTypeEnum.iterator(LocTypeEnum.class);
    }

    protected LocTypeEnum(String inKey, String inDescriptionResKey, String inCodeResKey, String inBackgroundColor, String inForegroundColor, String inIconIdPath) {
        super(inKey, inDescriptionResKey, inCodeResKey, inBackgroundColor, inForegroundColor, inIconIdPath);
    }

    public String getMappingClassName() {
        return "com.navis.argo.persistence.atoms.UserTypeLocTypeEnum";
    }
}

