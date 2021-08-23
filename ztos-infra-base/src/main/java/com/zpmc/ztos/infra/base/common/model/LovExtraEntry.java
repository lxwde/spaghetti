package com.zpmc.ztos.infra.base.common.model;

import org.apache.commons.lang.StringUtils;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

public class LovExtraEntry {

    private static final String EXTRA_ENTRY_NONE_STRING = "none";
    private static final String EXTRA_ENTRY_ALL_STRING = "wildcard";
    public static final LovExtraEntry STANDARD = new LovExtraEntry("");
    public static final LovExtraEntry EXTRA_ENTRY_NONE = new LovExtraEntry("none");
    public static final LovExtraEntry EXTRA_ENTRY_WILDCARD = new LovExtraEntry("wildcard");
    private final String _name;

    private LovExtraEntry(String inName) {
        this._name = inName;
    }

    public String toString() {
        return this._name;
    }

    @Nullable
    public static LovExtraEntry getExtraEntry(String inStr) {
        LovExtraEntry ee = null;
        if (StringUtils.equals((String)EXTRA_ENTRY_NONE_STRING, (String)inStr)) {
            ee = EXTRA_ENTRY_NONE;
        } else if (StringUtils.equals((String)EXTRA_ENTRY_ALL_STRING, (String)inStr)) {
            ee = EXTRA_ENTRY_WILDCARD;
        }
        return ee;
    }
}
