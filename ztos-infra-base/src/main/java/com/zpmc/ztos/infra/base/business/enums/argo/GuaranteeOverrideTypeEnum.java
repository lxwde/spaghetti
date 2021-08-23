package com.zpmc.ztos.infra.base.business.enums.argo;

import com.zpmc.ztos.infra.base.common.enums.AtomizedEnum;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

//public enum GuaranteeOverrideTypeEnum {
//    FIXED_PRICE(1, "FIXED_PRICE", "", "", "FIXED_PRICE"),
//    FREE_NOCHARGE(2, "FREE_NOCHARGE", "", "", "FREE_NOCHARGE");
//    private final int key;
//    private final String code;
//    private final String desc;
//    private final String color;
//    private final String displayName;
//
//    GuaranteeOverrideTypeEnum(int key, String code, String desc, String color, String displayName) {
//        this.key = key;
//        this.code = code;
//        this.desc = desc;
//        this.color = color;
//        this.displayName = displayName;
//    }
//
//    public static List getEnumList() {
//        return EnumUtils.getEnumList(GuaranteeOverrideTypeEnum.class);
//    }
//
//    public static EnumSet getEnumSet() {
//        return EnumSet.allOf(GuaranteeOverrideTypeEnum.class);
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

public class GuaranteeOverrideTypeEnum
        extends AtomizedEnum {
    public static final GuaranteeOverrideTypeEnum FIXED_PRICE = new GuaranteeOverrideTypeEnum("FIXED_PRICE", "atom.GuaranteeOverrideTypeEnum.FIXED_PRICE.description", "atom.GuaranteeOverrideTypeEnum.FIXED_PRICE.code", "", "", "");
    public static final GuaranteeOverrideTypeEnum FREE_NOCHARGE = new GuaranteeOverrideTypeEnum("FREE_NOCHARGE", "atom.GuaranteeOverrideTypeEnum.FREE_NOCHARGE.description", "atom.GuaranteeOverrideTypeEnum.FREE_NOCHARGE.code", "", "", "");

    public static GuaranteeOverrideTypeEnum getEnum(String inName) {
        return (GuaranteeOverrideTypeEnum) GuaranteeOverrideTypeEnum.getEnum(GuaranteeOverrideTypeEnum.class, (String)inName);
    }

    public static Map getEnumMap() {
        return GuaranteeOverrideTypeEnum.getEnumMap(GuaranteeOverrideTypeEnum.class);
    }

    public static List getEnumList() {
        return GuaranteeOverrideTypeEnum.getEnumList(GuaranteeOverrideTypeEnum.class);
    }

    public static Collection getList() {
        return GuaranteeOverrideTypeEnum.getEnumList(GuaranteeOverrideTypeEnum.class);
    }

    public static Iterator iterator() {
        return GuaranteeOverrideTypeEnum.iterator(GuaranteeOverrideTypeEnum.class);
    }

    protected GuaranteeOverrideTypeEnum(String inKey, String inDescriptionResKey, String inCodeResKey, String inBackgroundColor, String inForegroundColor, String inIconIdPath) {
        super(inKey, inDescriptionResKey, inCodeResKey, inBackgroundColor, inForegroundColor, inIconIdPath);
    }

    public String getMappingClassName() {
        return "com.navis.argo.persistence.atoms.UserTypeGuaranteeOverrideTypeEnum";
    }
}
