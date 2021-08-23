package com.zpmc.ztos.infra.base.business.enums.inventory;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

//public enum EqUnitRoleEnum {
//    PRIMARY(1, "PRIMARY", "", "", "PRIMARY"),
//    CARRIAGE(2, "CARRIAGE", "", "", "CARRIAGE"),
//    ACCESSORY(3, "ACCESSORY", "", "", "ACCESSORY"),
//    ACCESSORY_ON_CHS(4, "ACCESSORY_ON_CHS", "", "", "ACCESSORY_ON_CHS"),
//    PAYLOAD(5, "PAYLOAD", "", "", "PAYLOAD");
//    private final int key;
//    private final String code;
//    private final String desc;
//    private final String color;
//    private final String displayName;
//
//    EqUnitRoleEnum(int key, String code, String desc, String color, String displayName) {
//        this.key = key;
//        this.code = code;
//        this.desc = desc;
//        this.color = color;
//        this.displayName = displayName;
//    }
//
//    public static List getEnumList() {
//        return EnumUtils.getEnumList(EqUnitRoleEnum.class);
//    }
//
//    public static EnumSet getEnumSet() {
//        return EnumSet.allOf(EqUnitRoleEnum.class);
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

public class EqUnitRoleEnum
        extends AbstractEqUnitRoleEnum {
    public static final EqUnitRoleEnum PRIMARY = new EqUnitRoleEnum("PRIMARY", "atom.EqUnitRoleEnum.PRIMARY.description", "atom.EqUnitRoleEnum.PRIMARY.code", "bisque", "", "");
    public static final EqUnitRoleEnum CARRIAGE = new EqUnitRoleEnum("CARRIAGE", "atom.EqUnitRoleEnum.CARRIAGE.description", "atom.EqUnitRoleEnum.CARRIAGE.code", "lightblue", "", "");
    public static final EqUnitRoleEnum ACCESSORY = new EqUnitRoleEnum("ACCESSORY", "atom.EqUnitRoleEnum.ACCESSORY.description", "atom.EqUnitRoleEnum.ACCESSORY.code", "khaki", "", "");
    public static final EqUnitRoleEnum ACCESSORY_ON_CHS = new EqUnitRoleEnum("ACCESSORY_ON_CHS", "atom.EqUnitRoleEnum.ACCESSORY_ON_CHS.description", "atom.EqUnitRoleEnum.ACCESSORY_ON_CHS.code", "lightcoral", "", "");
    public static final EqUnitRoleEnum PAYLOAD = new EqUnitRoleEnum("PAYLOAD", "atom.EqUnitRoleEnum.PAYLOAD.description", "atom.EqUnitRoleEnum.PAYLOAD.code", "mediumvioletred", "", "");

    public static EqUnitRoleEnum getEnum(String inName) {
        return (EqUnitRoleEnum) EqUnitRoleEnum.getEnum(EqUnitRoleEnum.class, (String)inName);
    }

    public static Map getEnumMap() {
        return EqUnitRoleEnum.getEnumMap(EqUnitRoleEnum.class);
    }

    public static List getEnumList() {
        return EqUnitRoleEnum.getEnumList(EqUnitRoleEnum.class);
    }

    public static Collection getList() {
        return EqUnitRoleEnum.getEnumList(EqUnitRoleEnum.class);
    }

    public static Iterator iterator() {
        return EqUnitRoleEnum.iterator(EqUnitRoleEnum.class);
    }

    protected EqUnitRoleEnum(String inKey, String inDescriptionResKey, String inCodeResKey, String inBackgroundColor, String inForegroundColor, String inIconIdPath) {
        super(inKey, inDescriptionResKey, inCodeResKey, inBackgroundColor, inForegroundColor, inIconIdPath);
    }

    public String getMappingClassName() {
        return "com.navis.inventory.persistence.atoms.UserTypeEqUnitRoleEnum";
    }
}
