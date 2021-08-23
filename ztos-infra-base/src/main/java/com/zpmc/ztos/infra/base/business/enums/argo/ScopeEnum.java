package com.zpmc.ztos.infra.base.business.enums.argo;

import com.zpmc.ztos.infra.base.business.enums.framework.AbstractScopeEnum;
import com.zpmc.ztos.infra.base.common.model.ScopeCoordinates;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ScopeEnum
extends AbstractScopeEnum {

    public static final ScopeEnum GLOBAL = new ScopeEnum("0_GLB", "atom.ScopeEnum.GLOBAL.description", "atom.ScopeEnum.GLOBAL.code", "", "", "", ScopeCoordinates.GLOBAL_LEVEL);
    public static final ScopeEnum OPERATOR = new ScopeEnum("1_OPR", "atom.ScopeEnum.OPERATOR.description", "atom.ScopeEnum.OPERATOR.code", "", "", "", ScopeCoordinates.SCOPE_LEVEL_1);
    public static final ScopeEnum COMPLEX = new ScopeEnum("2_CPX", "atom.ScopeEnum.COMPLEX.description", "atom.ScopeEnum.COMPLEX.code", "", "", "", ScopeCoordinates.SCOPE_LEVEL_2);
    public static final ScopeEnum FACILITY = new ScopeEnum("3_FCY", "atom.ScopeEnum.FACILITY.description", "atom.ScopeEnum.FACILITY.code", "", "", "", ScopeCoordinates.SCOPE_LEVEL_3);
    public static final ScopeEnum YARD = new ScopeEnum("4_YRD", "atom.ScopeEnum.YARD.description", "atom.ScopeEnum.YARD.code", "", "", "", ScopeCoordinates.SCOPE_LEVEL_4);

    public static ScopeEnum getEnum(String inName) {
        return (ScopeEnum) ScopeEnum.getEnum(ScopeEnum.class, (String)inName);
    }

    public static Map getEnumMap() {
        return ScopeEnum.getEnumMap(ScopeEnum.class);
    }

    public static List getEnumList() {
        return ScopeEnum.getEnumList(ScopeEnum.class);
    }

    public static Collection getList() {
        return ScopeEnum.getEnumList(ScopeEnum.class);
    }

    public static Iterator iterator() {
        return ScopeEnum.iterator(ScopeEnum.class);
    }

    protected ScopeEnum(String inKey, String inDescriptionResKey, String inCodeResKey, String inBackgroundColor, String inForegroundColor, String inIconIdPath, Long inScopeLevel) {
        super(inKey, inDescriptionResKey, inCodeResKey, inBackgroundColor, inForegroundColor, inIconIdPath, inScopeLevel);
    }

    public String getMappingClassName() {
        return "com.navis.argo.persistence.atoms.UserTypeScopeEnum";
    }

}


//public enum ScopeEnum {
//    GLOBAL(1, "GLOBAL", "", "", "GLOBAL"),
//    OPERATOR(2, "OPERATOR", "", "", "OPERATOR"),
//    COMPLEX(3, "COMPLEX", "", "", "COMPLEX"),
//    FACILITY(4, "FACILITY", "", "", "FACILITY"),
//    YARD(5, "YARD", "", "", "YARD");
//    private final int key;
//    private final String code;
//    private final String desc;
//    private final String color;
//    private final String displayName;
//
//    ScopeEnum(int key, String code, String desc, String color, String displayName) {
//        this.key = key;
//        this.code = code;
//        this.desc = desc;
//        this.color = color;
//        this.displayName = displayName;
//    }
//
//    public static List getEnumList() {
//        return EnumUtils.getEnumList(ScopeEnum.class);
//    }
//
//    public static EnumSet getEnumSet() {
//        return EnumSet.allOf(ScopeEnum.class);
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