package com.zpmc.ztos.infra.base.business.xps;

import com.sun.istack.NotNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MiscCacheFileId {
    public static final int YARD_FILE = 10000;
    public static final int SPARCS_SETTINGS_FILE = 10001;
    public static final int BERTHS_FILE = 10002;
    public static final int LOWEST_VALUE_DEFINED = 10000;
    public static final int HIGHEST_VALUE_DEFINED = 10002;
    public static final int NUM_VALUES_DEFINED = 3;
    private static final Map<String, Integer> NAME_TO_VALUE_MAP;

    @NotNull
    public static String toString(int inValue) {
        switch (inValue) {
            case 10000: {
                return "YARD_FILE";
            }
            case 10001: {
                return "SPARCS_SETTINGS_FILE";
            }
            case 10002: {
                return "BERTHS_FILE";
            }
        }
        return Integer.toString(inValue);
    }

    public static int fromString(@NotNull String inIdName) {
        Integer result = NAME_TO_VALUE_MAP.get(inIdName);
        return result == null ? -1 : result;
    }

    private MiscCacheFileId() {
    }

    static {
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        map.put("YARD_FILE", 10000);
        map.put("SPARCS_SETTINGS_FILE", 10001);
        map.put("BERTHS_FILE", 10002);
        NAME_TO_VALUE_MAP = Collections.unmodifiableMap(map);
    }
}
