package com.zpmc.ztos.infra.base.business.enums.argo;

import com.zpmc.ztos.infra.base.common.enums.AtomizedEnum;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

//public enum DrayStatusEnum {
//    FORWARD(1, "FORWARD", "", "", "FORWARD"),
//    RETURN(2, "RETURN", "", "", "RETURN"),
//    DRAYIN(3, "DRAYIN", "dray in", "", "DRAYIN"),
//    OFFSITE(4, "OFFSITE", "off site", "", "OFFSITE"),
//    TRANSFER(5, "TRANSFER", "", "", "TRANSFER");
//    private final int key;
//    private final String code;
//    private final String desc;
//    private final String color;
//    private final String displayName;
//
//    DrayStatusEnum(int key, String code, String desc, String color, String displayName) {
//        this.key = key;
//        this.code = code;
//        this.desc = desc;
//        this.color = color;
//        this.displayName = displayName;
//    }
//
//    public static List getEnumList() {
//        return EnumUtils.getEnumList(DrayStatusEnum.class);
//    }
//
//    public static EnumSet getEnumSet() {
//        return EnumSet.allOf(DrayStatusEnum.class);
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

public class DrayStatusEnum
        extends AtomizedEnum {
    public static final DrayStatusEnum FORWARD = new DrayStatusEnum("FORWARD", "atom.DrayStatusEnum.FORWARD.description", "atom.DrayStatusEnum.FORWARD.code", "", "", "");
    public static final DrayStatusEnum RETURN = new DrayStatusEnum("RETURN", "atom.DrayStatusEnum.RETURN.description", "atom.DrayStatusEnum.RETURN.code", "", "", "");
    public static final DrayStatusEnum DRAYIN = new DrayStatusEnum("DRAYIN", "atom.DrayStatusEnum.DRAYIN.description", "atom.DrayStatusEnum.DRAYIN.code", "", "", "");
    public static final DrayStatusEnum OFFSITE = new DrayStatusEnum("OFFSITE", "atom.DrayStatusEnum.OFFSITE.description", "atom.DrayStatusEnum.OFFSITE.code", "", "", "");
    public static final DrayStatusEnum TRANSFER = new DrayStatusEnum("TRANSFER", "atom.DrayStatusEnum.TRANSFER.description", "atom.DrayStatusEnum.TRANSFER.code", "", "", "");

    public static DrayStatusEnum getEnum(String inName) {
        return (DrayStatusEnum) DrayStatusEnum.getEnum(DrayStatusEnum.class, (String)inName);
    }

    public static Map getEnumMap() {
        return DrayStatusEnum.getEnumMap(DrayStatusEnum.class);
    }

    public static List getEnumList() {
        return DrayStatusEnum.getEnumList(DrayStatusEnum.class);
    }

    public static Collection getList() {
        return DrayStatusEnum.getEnumList(DrayStatusEnum.class);
    }

    public static Iterator iterator() {
        return DrayStatusEnum.iterator(DrayStatusEnum.class);
    }

    protected DrayStatusEnum(String inKey, String inDescriptionResKey, String inCodeResKey, String inBackgroundColor, String inForegroundColor, String inIconIdPath) {
        super(inKey, inDescriptionResKey, inCodeResKey, inBackgroundColor, inForegroundColor, inIconIdPath);
    }

    public String getMappingClassName() {
        return "com.navis.argo.persistence.atoms.UserTypeDrayStatusEnum";
    }
}
