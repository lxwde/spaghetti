package com.zpmc.ztos.infra.base.business.enums.argo;

import com.zpmc.ztos.infra.base.common.enums.AtomizedEnum;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

//public enum FreightKindEnum {
//    MTY(1, "MTY", "", "", "MTY"),
//    FCL(2, "FCL", "", "", "FCL"),
//    LCL(3, "LCL", "", "", "LCL"),
//    BBK(4, "BBK", "", "", "BBK");
//    private final int key;
//    private final String code;
//    private final String desc;
//    private final String color;
//    private final String displayName;
//
//    FreightKindEnum(int key, String code, String desc, String color, String displayName) {
//        this.key = key;
//        this.code = code;
//        this.desc = desc;
//        this.color = color;
//        this.displayName = displayName;
//    }
//
//    public static List getEnumList() {
//        return EnumUtils.getEnumList(FreightKindEnum.class);
//    }
//
//    public static EnumSet getEnumSet() {
//        return EnumSet.allOf(FreightKindEnum.class);
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

public class FreightKindEnum
        extends AtomizedEnum {
    public static final FreightKindEnum MTY = new FreightKindEnum("MTY", "atom.FreightKindEnum.MTY.description", "atom.FreightKindEnum.MTY.code", "whitesmoke", "", "");
    public static final FreightKindEnum FCL = new FreightKindEnum("FCL", "atom.FreightKindEnum.FCL.description", "atom.FreightKindEnum.FCL.code", "darkslateblue", "", "");
    public static final FreightKindEnum LCL = new FreightKindEnum("LCL", "atom.FreightKindEnum.LCL.description", "atom.FreightKindEnum.LCL.code", "brown", "", "");
    public static final FreightKindEnum BBK = new FreightKindEnum("BBK", "atom.FreightKindEnum.BBK.description", "atom.FreightKindEnum.BBK.code", "orangered", "", "");

    public static FreightKindEnum getEnum(String inName) {
        return (FreightKindEnum) FreightKindEnum.getEnum(FreightKindEnum.class, (String)inName);
    }

    public static Map getEnumMap() {
        return FreightKindEnum.getEnumMap(FreightKindEnum.class);
    }

    public static List getEnumList() {
        return FreightKindEnum.getEnumList(FreightKindEnum.class);
    }

    public static Collection getList() {
        return FreightKindEnum.getEnumList(FreightKindEnum.class);
    }

    public static Iterator iterator() {
        return FreightKindEnum.iterator(FreightKindEnum.class);
    }

    protected FreightKindEnum(String inKey, String inDescriptionResKey, String inCodeResKey, String inBackgroundColor, String inForegroundColor, String inIconIdPath) {
        super(inKey, inDescriptionResKey, inCodeResKey, inBackgroundColor, inForegroundColor, inIconIdPath);
    }

    public String getMappingClassName() {
        return "com.navis.argo.persistence.atoms.UserTypeFreightKindEnum";
    }
}
