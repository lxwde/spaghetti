package com.zpmc.ztos.infra.base.common.utils;

import com.zpmc.ztos.infra.base.business.enums.framework.AbstractMeasurementUnitEnum;
import com.zpmc.ztos.infra.base.utils.StringUtils;

import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

public class UnitUtilsBase {
    @Nullable
    public static AbstractMeasurementUnitEnum lookupUnit(String inUnitStr) {
        if (StringUtils.isBlank((String)inUnitStr)) {
            return null;
        }
        AbstractMeasurementUnitEnum atomizedEnum = null;
        if (atomizedEnum == null) {
 //           atomizedEnum = (AbstractMeasurementUnitEnum)AtomizedEnumUtils.safeGetEnum(TemperatureUnit.class, inUnitStr);
        }
        if (atomizedEnum == null) {
//            atomizedEnum = (AbstractMeasurementUnitEnum)AtomizedEnumUtils.safeGetEnum(LengthUnit.class, inUnitStr);
        }
        if (atomizedEnum == null) {
//            atomizedEnum = (AbstractMeasurementUnitEnum)AtomizedEnumUtils.safeGetEnum(AreaUnit.class, inUnitStr);
        }
        if (atomizedEnum == null) {
//            atomizedEnum = (AbstractMeasurementUnitEnum)AtomizedEnumUtils.safeGetEnum(MassUnit.class, inUnitStr);
        }
        if (atomizedEnum == null) {
//            atomizedEnum = (AbstractMeasurementUnitEnum)AtomizedEnumUtils.safeGetEnum(VolumeUnit.class, inUnitStr);
        }
        return atomizedEnum;
    }
}
