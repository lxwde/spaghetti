package com.zpmc.ztos.infra.base.business.enums.framework;

import com.zpmc.ztos.infra.base.common.enums.AtomizedEnum;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class PredicateVerbEnum extends AtomizedEnum {
    public static final PredicateVerbEnum EQ = new PredicateVerbEnum("EQ", "atom.PredicateVerbEnum.EQ.description", "atom.PredicateVerbEnum.EQ.code", "", "", "");
    public static final PredicateVerbEnum NE = new PredicateVerbEnum("NE", "atom.PredicateVerbEnum.NE.description", "atom.PredicateVerbEnum.NE.code", "", "", "");
    public static final PredicateVerbEnum LT = new PredicateVerbEnum("LT", "atom.PredicateVerbEnum.LT.description", "atom.PredicateVerbEnum.LT.code", "", "", "");
    public static final PredicateVerbEnum GT = new PredicateVerbEnum("GT", "atom.PredicateVerbEnum.GT.description", "atom.PredicateVerbEnum.GT.code", "", "", "");
    public static final PredicateVerbEnum LE = new PredicateVerbEnum("LE", "atom.PredicateVerbEnum.LE.description", "atom.PredicateVerbEnum.LE.code", "", "", "");
    public static final PredicateVerbEnum GE = new PredicateVerbEnum("GE", "atom.PredicateVerbEnum.GE.description", "atom.PredicateVerbEnum.GE.code", "", "", "");
    public static final PredicateVerbEnum NOT = new PredicateVerbEnum("NOT", "atom.PredicateVerbEnum.NOT.description", "atom.PredicateVerbEnum.NOT.code", "", "", "");
    public static final PredicateVerbEnum IN = new PredicateVerbEnum("IN", "atom.PredicateVerbEnum.IN.description", "atom.PredicateVerbEnum.IN.code", "", "", "");
    public static final PredicateVerbEnum TRUE = new PredicateVerbEnum("TRUE", "atom.PredicateVerbEnum.TRUE.description", "atom.PredicateVerbEnum.TRUE.code", "", "", "");
    public static final PredicateVerbEnum FALSE = new PredicateVerbEnum("FALSE", "atom.PredicateVerbEnum.FALSE.description", "atom.PredicateVerbEnum.FALSE.code", "", "", "");
    public static final PredicateVerbEnum SAME = new PredicateVerbEnum("SAME", "atom.PredicateVerbEnum.SAME.description", "atom.PredicateVerbEnum.SAME.code", "", "", "");
    public static final PredicateVerbEnum NOT_NULL = new PredicateVerbEnum("NOT_NULL", "atom.PredicateVerbEnum.NOT_NULL.description", "atom.PredicateVerbEnum.NOT_NULL.code", "", "", "");
    public static final PredicateVerbEnum NULL = new PredicateVerbEnum("NULL", "atom.PredicateVerbEnum.NULL.description", "atom.PredicateVerbEnum.NULL.code", "", "", "");
    public static final PredicateVerbEnum MATCHES = new PredicateVerbEnum("MATCHES", "atom.PredicateVerbEnum.MATCHES.description", "atom.PredicateVerbEnum.MATCHES.code", "", "", "");
    public static final PredicateVerbEnum BEFORE = new PredicateVerbEnum("BEFORE", "atom.PredicateVerbEnum.BEFORE.description", "atom.PredicateVerbEnum.BEFORE.code", "", "", "");
    public static final PredicateVerbEnum AFTER = new PredicateVerbEnum("AFTER", "atom.PredicateVerbEnum.AFTER.description", "atom.PredicateVerbEnum.AFTER.code", "", "", "");
    public static final PredicateVerbEnum OLDER = new PredicateVerbEnum("OLDER", "atom.PredicateVerbEnum.OLDER.description", "atom.PredicateVerbEnum.OLDER.code", "", "", "");
    public static final PredicateVerbEnum NEWER = new PredicateVerbEnum("NEWER", "atom.PredicateVerbEnum.NEWER.description", "atom.PredicateVerbEnum.NEWER.code", "", "", "");
    public static final PredicateVerbEnum TIME_IS = new PredicateVerbEnum("TIME_IS", "atom.PredicateVerbEnum.TIME_IS.description", "atom.PredicateVerbEnum.TIME_IS.code", "", "", "");
    public static final PredicateVerbEnum AND = new PredicateVerbEnum("AND", "atom.PredicateVerbEnum.AND.description", "atom.PredicateVerbEnum.AND.code", "", "", "");
    public static final PredicateVerbEnum OR = new PredicateVerbEnum("OR", "atom.PredicateVerbEnum.OR.description", "atom.PredicateVerbEnum.OR.code", "", "", "");
    public static final PredicateVerbEnum OTHERWISE = new PredicateVerbEnum("OTHERWISE", "atom.PredicateVerbEnum.OTHERWISE.description", "atom.PredicateVerbEnum.OTHERWISE.code", "", "", "");
    public static final PredicateVerbEnum GEO_CONTAINS = new PredicateVerbEnum("GEO_CONTAINS", "atom.PredicateVerbEnum.GEO_CONTAINS.description", "atom.PredicateVerbEnum.GEO_CONTAINS.code", "", "", "");
    public static final PredicateVerbEnum GEO_CROSSES = new PredicateVerbEnum("GEO_CROSSES", "atom.PredicateVerbEnum.GEO_CROSSES.description", "atom.PredicateVerbEnum.GEO_CROSSES.code", "", "", "");
    public static final PredicateVerbEnum GEO_DISJOINT = new PredicateVerbEnum("GEO_DISJOINT", "atom.PredicateVerbEnum.GEO_DISJOINT.description", "atom.PredicateVerbEnum.GEO_DISJOINT.code", "", "", "");
    public static final PredicateVerbEnum GEO_EQUALS = new PredicateVerbEnum("GEO_EQUALS", "atom.PredicateVerbEnum.GEO_EQUALS.description", "atom.PredicateVerbEnum.GEO_EQUALS.code", "", "", "");
    public static final PredicateVerbEnum GEO_INTERSECTS = new PredicateVerbEnum("GEO_INTERSECTS", "atom.PredicateVerbEnum.GEO_INTERSECTS.description", "atom.PredicateVerbEnum.GEO_INTERSECTS.code", "", "", "");
    public static final PredicateVerbEnum GEO_OVERLAPS = new PredicateVerbEnum("GEO_OVERLAPS", "atom.PredicateVerbEnum.GEO_OVERLAPS.description", "atom.PredicateVerbEnum.GEO_OVERLAPS.code", "", "", "");
    public static final PredicateVerbEnum GEO_TOUCHES = new PredicateVerbEnum("GEO_TOUCHES", "atom.PredicateVerbEnum.GEO_TOUCHES.description", "atom.PredicateVerbEnum.GEO_TOUCHES.code", "", "", "");
    public static final PredicateVerbEnum GEO_WITHIN = new PredicateVerbEnum("GEO_WITHIN", "atom.PredicateVerbEnum.GEO_WITHIN.description", "atom.PredicateVerbEnum.GEO_WITHIN.code", "", "", "");
    public static final PredicateVerbEnum TIME_ONLY_BEFORE = new PredicateVerbEnum("TIME_ONLY_BEFORE", "atom.PredicateVerbEnum.TIME_ONLY_BEFORE.description", "atom.PredicateVerbEnum.TIME_ONLY_BEFORE.code", "", "", "");
    public static final PredicateVerbEnum TIME_ONLY_AFTER = new PredicateVerbEnum("TIME_ONLY_AFTER", "atom.PredicateVerbEnum.TIME_ONLY_AFTER.description", "atom.PredicateVerbEnum.TIME_ONLY_AFTER.code", "", "", "");
    public static final PredicateVerbEnum TIME_ONLY_IS = new PredicateVerbEnum("TIME_ONLY_IS", "atom.PredicateVerbEnum.TIME_ONLY_IS.description", "atom.PredicateVerbEnum.TIME_ONLY_IS.code", "", "", "");
    public static final PredicateVerbEnum AFTER_DATE = new PredicateVerbEnum("AFTER_DATE", "atom.PredicateVerbEnum.AFTER_DATE.description", "atom.PredicateVerbEnum.AFTER_DATE.code", "", "", "");
    public static final PredicateVerbEnum EQ_DATE = new PredicateVerbEnum("EQ_DATE", "atom.PredicateVerbEnum.EQ_DATE.description", "atom.PredicateVerbEnum.EQ_DATE.code", "", "", "");

    public static PredicateVerbEnum getEnum(String string) {
        return (PredicateVerbEnum) PredicateVerbEnum.getEnum(PredicateVerbEnum.class, (String)string);
    }

    public static Map getEnumMap() {
        return PredicateVerbEnum.getEnumMap(PredicateVerbEnum.class);
    }

    public static List getEnumList() {
        return PredicateVerbEnum.getEnumList(PredicateVerbEnum.class);
    }

    public static Collection getList() {
        return PredicateVerbEnum.getEnumList(PredicateVerbEnum.class);
    }

    public static Iterator iterator() {
        return PredicateVerbEnum.iterator(PredicateVerbEnum.class);
    }

    protected PredicateVerbEnum(String string, String string2, String string3, String string4, String string5, String string6) {
        super(string, string2, string3, string4, string5, string6);
    }

    @Override
    public String getMappingClassName() {
        return "com.zpmc.ztos.infra.base.business.enums.framework.PredicateVerbEnum";
    }

}
