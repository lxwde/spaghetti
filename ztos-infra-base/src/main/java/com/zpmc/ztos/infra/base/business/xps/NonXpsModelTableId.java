package com.zpmc.ztos.infra.base.business.xps;

import com.sun.istack.NotNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class NonXpsModelTableId {
    public static final int CONTAINER_MOVEMENT = 1000;
    public static final int LOWEST_VALUE_DEFINED = 1000;
    public static final int HIGHEST_VALUE_DEFINED = 1000;
    public static final int NUM_VALUES_DEFINED = 1;
    private static final Map<String, Integer> NAME_TO_VALUE_MAP;

    @NotNull
    public static String toString(int inValue) {
        switch (inValue) {
            case 1000: {
                return "CONTAINER_MOVEMENT";
            }
        }
        return Integer.toString(inValue);
    }

    public static int fromString(@NotNull String inIdName) {
        Integer result = NAME_TO_VALUE_MAP.get(inIdName);
        return result == null ? -1 : result;
    }

    private NonXpsModelTableId() {
    }

    static {
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        map.put("CONTAINER_MOVEMENT", 1000);
        NAME_TO_VALUE_MAP = Collections.unmodifiableMap(map);
    }
}
