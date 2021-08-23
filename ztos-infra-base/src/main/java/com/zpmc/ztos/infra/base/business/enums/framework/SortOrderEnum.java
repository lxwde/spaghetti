package com.zpmc.ztos.infra.base.business.enums.framework;
import org.apache.commons.lang.enums.Enum;

public class SortOrderEnum extends Enum {
    public static final String ASC = "ASC";
    public static final String DESC = "DESC";
    public static final SortOrderEnum ASCENDING = new SortOrderEnum("ASCENDING", "ASC");
    public static final SortOrderEnum DESCENDING = new SortOrderEnum("DESCENDING", "DESC");
    private final String A;

    protected SortOrderEnum(String string, String string2) {
        super(string);
        this.A = string2;
    }

    public String toHqlString() {
        return this.A;
    }

    public static SortOrderEnum invert(SortOrderEnum sortOrder) {
        return sortOrder == ASCENDING ? DESCENDING : ASCENDING;
    }

}
