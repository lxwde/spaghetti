package com.zpmc.ztos.infra.base.common.configs;

import com.zpmc.ztos.infra.base.business.enums.framework.ConfigClassEnum;
import com.zpmc.ztos.infra.base.business.interfaces.IConfig;
import com.zpmc.ztos.infra.base.business.interfaces.IConfigValueProvider;
import com.zpmc.ztos.infra.base.common.model.UserContext;

public class LongConfig extends AbstractConfig {

    private final long _minValue;
    private final long _maxValue;
    private final long _defaultValue;

    public LongConfig(String inModuleId, String inFeatureId, String inConfigRawId, String inConfigSerial, long inDefaultValue, long inMinValue, long inMaxValue, int inMinScope, int inMaxScope, String[] inLicensedFeatures, String inInitialVersion, String inDeprecatedVersion, IConfig inEnabler, String inLifecycleBeanName) {
        super(inModuleId, inFeatureId, inConfigRawId, inConfigSerial, inMinScope, inMaxScope, inLicensedFeatures, inInitialVersion, inDeprecatedVersion, inEnabler, inLifecycleBeanName);
        this._minValue = inMinValue;
        this._maxValue = inMaxValue;
        this._defaultValue = inDefaultValue;
    }

    public long getValue(UserContext inUserContext) {
        Long l = (Long)super.getEffectiveSetting(inUserContext);
        if (l != null) {
            return l;
        }
        return this._defaultValue;
    }

    public long getValue(IConfigValueProvider inProvider, UserContext inUserContext) {
        String configValue = inProvider.getConfigValue(this);
        if (configValue == null) {
            return this.getValue(inUserContext);
        }
        Long l = (Long)this.str2Value(configValue);
        return l;
    }

    @Override
    public Object getConfigDefaultValue() {
        return this._defaultValue;
    }

    public long getMinValue() {
        return this._minValue;
    }

    public long getMaxValue() {
        return this._maxValue;
    }

    public long getDefaultValue() {
        return this._defaultValue;
    }

    @Override
    public Object str2Value(String inStrValue) {
        return Long.valueOf(inStrValue);
    }

    @Override
    public ConfigClassEnum getConfigClass() {
        return ConfigClassEnum.NUMERIC;
    }
}
