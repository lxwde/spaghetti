package com.zpmc.ztos.infra.base.common.type;

public class FuzzyBoolean {

    public static final FuzzyBoolean FALSE = new FuzzyBoolean("FALSE");
    public static final FuzzyBoolean TRUE = new FuzzyBoolean("TRUE");
    public static final FuzzyBoolean UNSPECIFIED = new FuzzyBoolean("UNSPECIFIED");
    private final String A;

    private FuzzyBoolean(String string) {
        this.A = string;
    }

    public String toString() {
        return this.A;
    }

    public static boolean getValue(FuzzyBoolean fuzzyBoolean, boolean bl) {
        if (fuzzyBoolean == UNSPECIFIED) {
            return bl;
        }
        return fuzzyBoolean == TRUE;
    }

    public boolean getValue(boolean bl) {
        if (this == UNSPECIFIED) {
            return bl;
        }
        return this == TRUE;
    }

    public static FuzzyBoolean getFuzzyBoolean(boolean bl) {
        return bl ? TRUE : FALSE;
    }

    public FuzzyBoolean and(FuzzyBoolean fuzzyBoolean) {
        if (this == TRUE) {
            return fuzzyBoolean;
        }
        if (fuzzyBoolean == FALSE) {
            return FALSE;
        }
        return this;
    }

    public FuzzyBoolean or(FuzzyBoolean fuzzyBoolean) {
        if (this == FALSE) {
            return fuzzyBoolean;
        }
        if (fuzzyBoolean == TRUE) {
            return TRUE;
        }
        return this;
    }

    public FuzzyBoolean xor(FuzzyBoolean fuzzyBoolean) {
        if (this == UNSPECIFIED || fuzzyBoolean == UNSPECIFIED) {
            return UNSPECIFIED;
        }
        if (this == TRUE && fuzzyBoolean == TRUE || this == FALSE && fuzzyBoolean == FALSE) {
            return FALSE;
        }
        return TRUE;
    }

    public FuzzyBoolean not() {
        if (this == TRUE) {
            return FALSE;
        }
        if (this == FALSE) {
            return TRUE;
        }
        return UNSPECIFIED;
    }

}
