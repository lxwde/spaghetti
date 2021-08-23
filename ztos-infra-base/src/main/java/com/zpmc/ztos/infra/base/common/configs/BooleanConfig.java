package com.zpmc.ztos.infra.base.common.configs;

import com.zpmc.ztos.infra.base.business.enums.framework.ConfigClassEnum;
import com.zpmc.ztos.infra.base.business.interfaces.IConfig;
import com.zpmc.ztos.infra.base.business.interfaces.IConfigValueProvider;
import com.zpmc.ztos.infra.base.common.model.UserContext;

//import java.io.ObjectInputFilter;

public class BooleanConfig extends AbstractConfig {
    private final boolean _defaultValue;

    public BooleanConfig(String inModuleId, String inFeatureId, String inConfigRawId, String inConfigSerial, boolean inDefaultValue, int inMinScope, int inMaxScope, String[] inLicensedFeatures, String inInitialVersion, String inDeprecatedVersion, IConfig inEnabler, String inLifecycleBeanName) {
        super(inModuleId, inFeatureId, inConfigRawId, inConfigSerial, inMinScope, inMaxScope, inLicensedFeatures, inInitialVersion, inDeprecatedVersion, inEnabler, inLifecycleBeanName);
        this._defaultValue = inDefaultValue;
    }

    public boolean isOn(UserContext inUserContext) {
        Boolean b = (Boolean)super.getEffectiveSetting(inUserContext);
        if (b != null) {
            return b;
        }
        return this._defaultValue;
    }

    public boolean isOn(IConfigValueProvider inProvider, UserContext inUserContext) {
        String configValue = inProvider.getConfigValue(this);
        if (configValue == null) {
            return this.isOn(inUserContext);
        }
        Boolean b = (Boolean)this.str2Value(configValue);
        return b;
    }

    @Override
    public Object getConfigDefaultValue() {
        return this._defaultValue;
    }

    @Override
    public Object str2Value(String inStrValue) {
        return Boolean.valueOf(inStrValue);
    }

    @Override
    public ConfigClassEnum getConfigClass() {
        return ConfigClassEnum.BOOLEAN;
    }
}
