package com.zpmc.ztos.infra.base.common.model;

import com.zpmc.ztos.infra.base.business.enums.framework.TimePeriodTypeEnum;
import com.zpmc.ztos.infra.base.common.utils.TimeUtils;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class IntervalTime {
    private final Map<TimePeriodTypeEnum, Integer> _values;

    public IntervalTime( Map<TimePeriodTypeEnum, Integer> inTimeComponents) {
        this._values = new HashMap<TimePeriodTypeEnum, Integer>(inTimeComponents);
    }

    public IntervalTime(TimePeriodTypeEnum inPeriodType, int inUnits) {
        this._values = new HashMap<TimePeriodTypeEnum, Integer>(TimePeriodTypeEnum.getSize());
        this._values.put(inPeriodType, inUnits);
    }

    public int getTimeComponent( TimePeriodTypeEnum inPeriodType) {
        return this._values.containsKey((Object)inPeriodType) ? this._values.get((Object)inPeriodType) : 0;
    }

    public Map<TimePeriodTypeEnum, Integer> getTimeComponents() {
        return Collections.unmodifiableMap(this._values);
    }

    public long getInterval() {
        long total = 0L;
        for (Map.Entry<TimePeriodTypeEnum, Integer> entry : this._values.entrySet()) {
            total += entry.getKey().getFactorToConvertToLowest() * (long)entry.getValue().intValue();
        }
        return total;
    }

    public long getInterval(TimePeriodTypeEnum inTargetTimePeriod) {
        long total = this.getInterval();
        return total / inTargetTimePeriod.getFactorToConvertToLowest();
    }

    @Nullable
    public static IntervalTime parse(String inStrValue) {
        return TimeUtils.parseMultiTimeStringAsIntervalTime(inStrValue);
    }
}
