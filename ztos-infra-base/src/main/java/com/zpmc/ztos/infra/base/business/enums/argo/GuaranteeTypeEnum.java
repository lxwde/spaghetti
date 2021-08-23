package com.zpmc.ztos.infra.base.business.enums.argo;

import com.zpmc.ztos.infra.base.common.enums.AtomizedEnum;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

//public enum GuaranteeTypeEnum {
//    OAC(1, "OAC", "", "", "OAC"),
//    CREDIT_PREAUTHORIZE(2, "CREDIT_PREAUTHORIZE", "", "", "CREDIT_PREAUTHORIZE"),
//    WAIVER(3, "WAIVER", "", "", "WAIVER"),
//    PAID(4, "PAID", "", "", "PAID"),
//    PRE_PAY(5, "PRE_PAY", "", "", "PRE_PAY");
//    private final int key;
//    private final String code;
//    private final String desc;
//    private final String color;
//    private final String displayName;
//
//    GuaranteeTypeEnum(int key, String code, String desc, String color, String displayName) {
//        this.key = key;
//        this.code = code;
//        this.desc = desc;
//        this.color = color;
//        this.displayName = displayName;
//    }
//
//    public static List getEnumList() {
//        return EnumUtils.getEnumList(GuaranteeTypeEnum.class);
//    }
//
//    public static EnumSet getEnumSet() {
//        return EnumSet.allOf(GuaranteeTypeEnum.class);
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

public class GuaranteeTypeEnum
        extends AtomizedEnum {
    public static final GuaranteeTypeEnum OAC = new GuaranteeTypeEnum("OAC", "atom.GuaranteeTypeEnum.OAC.description", "atom.GuaranteeTypeEnum.OAC.code", "gold", "", "");
    public static final GuaranteeTypeEnum CREDIT_PREAUTHORIZE = new GuaranteeTypeEnum("CREDIT_PREAUTHORIZE", "atom.GuaranteeTypeEnum.CREDIT_PREAUTHORIZE.description", "atom.GuaranteeTypeEnum.CREDIT_PREAUTHORIZE.code", "red", "", "");
    public static final GuaranteeTypeEnum WAIVER = new GuaranteeTypeEnum("WAIVER", "atom.GuaranteeTypeEnum.WAIVER.description", "atom.GuaranteeTypeEnum.WAIVER.code", "green", "", "");
    public static final GuaranteeTypeEnum PAID = new GuaranteeTypeEnum("PAID", "atom.GuaranteeTypeEnum.PAID.description", "atom.GuaranteeTypeEnum.PAID.code", "limegreen", "", "");
    public static final GuaranteeTypeEnum PRE_PAY = new GuaranteeTypeEnum("PRE_PAY", "atom.GuaranteeTypeEnum.PRE_PAY.description", "atom.GuaranteeTypeEnum.PRE_PAY.code", "greenyellow", "", "");

    public static GuaranteeTypeEnum getEnum(String inName) {
        return (GuaranteeTypeEnum) GuaranteeTypeEnum.getEnum(GuaranteeTypeEnum.class, (String)inName);
    }

    public static Map getEnumMap() {
        return GuaranteeTypeEnum.getEnumMap(GuaranteeTypeEnum.class);
    }

    public static List getEnumList() {
        return GuaranteeTypeEnum.getEnumList(GuaranteeTypeEnum.class);
    }

    public static Collection getList() {
        return GuaranteeTypeEnum.getEnumList(GuaranteeTypeEnum.class);
    }

    public static Iterator iterator() {
        return GuaranteeTypeEnum.iterator(GuaranteeTypeEnum.class);
    }

    protected GuaranteeTypeEnum(String inKey, String inDescriptionResKey, String inCodeResKey, String inBackgroundColor, String inForegroundColor, String inIconIdPath) {
        super(inKey, inDescriptionResKey, inCodeResKey, inBackgroundColor, inForegroundColor, inIconIdPath);
    }

    public String getMappingClassName() {
        return "com.navis.argo.persistence.atoms.UserTypeGuaranteeTypeEnum";
    }
}

