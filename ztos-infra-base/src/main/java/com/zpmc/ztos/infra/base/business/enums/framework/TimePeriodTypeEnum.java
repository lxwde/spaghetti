package com.zpmc.ztos.infra.base.business.enums.framework;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum TimePeriodTypeEnum {
    WEEK("w", 10080L),
    DAY("d", 1440L),
    HOUR("h", 60L),
    MINUTE("m", 1L);

    private static final Map<String, TimePeriodTypeEnum> LOOK_UP;
    private final String _shortKey;
    private final long _factorToConvertToLowestUnit;

    private TimePeriodTypeEnum(String inShortKey, long inFactorToConvertToUnit) {
        this._shortKey = inShortKey;
        this._factorToConvertToLowestUnit = inFactorToConvertToUnit;
    }

    public String getShortKey() {
        return this._shortKey;
    }

    public long getFactorToConvertToLowest() {
        return this._factorToConvertToLowestUnit;
    }

    public static int getSize() {
        return LOOK_UP.size();
    }

    public static TimePeriodTypeEnum get(String inShortKey) {
        return LOOK_UP.get(inShortKey);
    }

    static {
        LOOK_UP = new HashMap<String, TimePeriodTypeEnum>();
        for (TimePeriodTypeEnum se : EnumSet.allOf(TimePeriodTypeEnum.class)) {
            LOOK_UP.put(se.getShortKey(), se);
        }
    }
}
