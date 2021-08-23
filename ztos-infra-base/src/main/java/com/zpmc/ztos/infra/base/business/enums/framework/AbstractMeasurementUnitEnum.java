package com.zpmc.ztos.infra.base.business.enums.framework;

import com.zpmc.ztos.infra.base.business.interfaces.IMeasurementUnit;
import com.zpmc.ztos.infra.base.business.interfaces.IPropertyKey;
import com.zpmc.ztos.infra.base.common.enums.AtomizedEnum;
import com.zpmc.ztos.infra.base.utils.StringUtils;

public abstract class AbstractMeasurementUnitEnum extends AtomizedEnum implements IMeasurementUnit {
    private final String[] _acceptableAbbreviations;
    private final double _conversionFactor;
    private final double _standardUnitOffset;

    public AbstractMeasurementUnitEnum(String inAbbrevation, String inDescriptivePropertyKey, String inCodePropertyKey, String inBackgroundColor, String inForegroundColor, String inIconIdPath, String[] inAcceptableAbbreviations, double inRelativeScale) {
        super(inAbbrevation, inDescriptivePropertyKey, inCodePropertyKey, inBackgroundColor, inForegroundColor, inIconIdPath);
        this._acceptableAbbreviations = inAcceptableAbbreviations;
        this._conversionFactor = inRelativeScale;
        this._standardUnitOffset = 0.0;
    }

    public AbstractMeasurementUnitEnum(String inAbbrevation, String inDescriptivePropertyKey, String inCodePropertyKey, String inBackgroundColor, String inForegroundColor, String inIconIdPath, String[] inAcceptableAbbreviations, double inRelativeScale, double inStandardUnitOffset) {
        super(inAbbrevation, inDescriptivePropertyKey, inCodePropertyKey, inBackgroundColor, inForegroundColor, inIconIdPath);
        this._acceptableAbbreviations = inAcceptableAbbreviations;
        this._conversionFactor = inRelativeScale;
        this._standardUnitOffset = inStandardUnitOffset;
    }

    @Override
    public IPropertyKey getUnitName() {
        return this.getDescriptionPropertyKey();
    }

    @Override
    public String getAbbrevation() {
        return this.getKey();
    }

    @Override
    public String[] getAbbreviations() {
        return this._acceptableAbbreviations;
    }

    @Override
    public double getStandardUnits() {
        return this._conversionFactor;
    }

    @Override
    public double getStandardUnitOffset() {
        return this._standardUnitOffset;
    }

    @Override
    public boolean matches(String inUnitStr) {
        if (StringUtils.equals((String)inUnitStr, (String)this.getAbbrevation())) {
            return true;
        }
        for (String acceptableAbbreviation : this._acceptableAbbreviations) {
            if (!StringUtils.equals((String)inUnitStr, (String)acceptableAbbreviation)) continue;
            return true;
        }
        return false;
    }
}
