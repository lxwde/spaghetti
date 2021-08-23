package com.zpmc.ztos.infra.base.common.model;

import com.zpmc.ztos.infra.base.business.enums.framework.TimePeriodTypeEnum;
import com.zpmc.ztos.infra.base.common.utils.TimeUtils;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

public class RelativeTimePeriod {
    private final int _units;
    private final TimePeriodTypeEnum _periodType;

    public RelativeTimePeriod(TimePeriodTypeEnum inPeriodType, int inUnits) {
        this._periodType = inPeriodType;
        this._units = inUnits;
    }

    public int getUnits() {
        return this._units;
    }

    public TimePeriodTypeEnum getPeriodType() {
        return this._periodType;
    }

    @Nullable
    public static RelativeTimePeriod parse(String inValue) {
        return TimeUtils.parseRelativeTimePeriod(inValue);
    }

}
