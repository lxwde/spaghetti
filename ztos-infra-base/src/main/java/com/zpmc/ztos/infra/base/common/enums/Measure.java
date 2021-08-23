package com.zpmc.ztos.infra.base.common.enums;

import com.zpmc.ztos.infra.base.business.interfaces.IMeasurementUnit;

public abstract class Measure
        extends AtomizedEnum {
    private final IMeasurementUnit _storageUnits;
    private final double _value;

    protected Measure(String inKey, String inDescriptionResKey, String inCodeResKey, String inBackgroundColor, String inForegroundColor, String inIconIdPath, IMeasurementUnit inUnits, double inValue) {
        super(inKey, inDescriptionResKey, inCodeResKey, inBackgroundColor, inForegroundColor, inIconIdPath);
        this._storageUnits = inUnits;
        this._value = inValue;
    }

    public double getValueInUnits(IMeasurementUnit inUnits) {
        if (this._storageUnits.equals(inUnits)) {
            return this._value;
        }
    //    return UnitUtils.convertTo(this._value, this._storageUnits, inUnits);
        return 0.0;
    }
}
