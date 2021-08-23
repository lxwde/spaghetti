package com.zpmc.ztos.infra.base.business.enums.argo;

import com.zpmc.ztos.infra.base.common.enums.AtomizedEnum;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author java002
 */

//public enum EquipEventsTypeToRecordEnum {
//
//    NONE(1, "NONE", "", "", "NONE"),
//    ALL(2, "ALL", "", "", "ALL");
//
//
//    private final int key;
//    private final String code;
//    private final String desc;
//    private final String color;
//    private final String displayName;
//
//    EquipEventsTypeToRecordEnum(int key, String code, String desc, String color, String displayName) {
//        this.key = key;
//        this.code = code;
//        this.desc = desc;
//        this.color = color;
//        this.displayName = displayName;
//    }
//
//    public static List getEnumList() {
//        return EnumUtils.getEnumList(EquipIsoGroupEnum.class);
//    }
//
//    public static EnumSet getEnumSet() {
//        return EnumSet.allOf(EquipIsoGroupEnum.class);
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

public class EquipEventsTypeToRecordEnum
        extends AtomizedEnum {
    public static final EquipEventsTypeToRecordEnum NONE = new EquipEventsTypeToRecordEnum("NONE", "atom.EquipEventsTypeToRecordEnum.NONE.description", "atom.EquipEventsTypeToRecordEnum.NONE.code", "", "", "");
    public static final EquipEventsTypeToRecordEnum ALL = new EquipEventsTypeToRecordEnum("ALL", "atom.EquipEventsTypeToRecordEnum.ALL.description", "atom.EquipEventsTypeToRecordEnum.ALL.code", "", "", "");

    public static EquipEventsTypeToRecordEnum getEnum(String inName) {
        return (EquipEventsTypeToRecordEnum) EquipEventsTypeToRecordEnum.getEnum(EquipEventsTypeToRecordEnum.class, (String)inName);
    }

    public static Map getEnumMap() {
        return EquipEventsTypeToRecordEnum.getEnumMap(EquipEventsTypeToRecordEnum.class);
    }

    public static List getEnumList() {
        return EquipEventsTypeToRecordEnum.getEnumList(EquipEventsTypeToRecordEnum.class);
    }

    public static Collection getList() {
        return EquipEventsTypeToRecordEnum.getEnumList(EquipEventsTypeToRecordEnum.class);
    }

    public static Iterator iterator() {
        return EquipEventsTypeToRecordEnum.iterator(EquipEventsTypeToRecordEnum.class);
    }

    protected EquipEventsTypeToRecordEnum(String inKey, String inDescriptionResKey, String inCodeResKey, String inBackgroundColor, String inForegroundColor, String inIconIdPath) {
        super(inKey, inDescriptionResKey, inCodeResKey, inBackgroundColor, inForegroundColor, inIconIdPath);
    }

    public String getMappingClassName() {
        return "com.navis.argo.persistence.atoms.UserTypeEquipEventsTypeToRecordEnum";
    }
}