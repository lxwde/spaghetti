package com.zpmc.ztos.infra.base.common.configs;

import com.zpmc.ztos.infra.base.business.enums.framework.ConfigClassEnum;
import com.zpmc.ztos.infra.base.business.interfaces.IConfig;
import com.zpmc.ztos.infra.base.business.interfaces.IConfigValueProvider;
import com.zpmc.ztos.infra.base.common.model.UserContext;

public class StringConfig extends AbstractConfig {

    private final String _defaultValue;
    private final int _maxLength;
    private final boolean _password;

    public StringConfig(String inModuleId, String inFeatureId, String inConfigRawId, String inConfigSerial, String inDefaultValue, int inMaxLength, boolean inPassword, int inMinScope, int inMaxScope, String[] inLicensedFeatures, String inInitialVersion, String inDeprecatedVersion, IConfig inEnabler, String inLifecycleBeanName) {
        super(inModuleId, inFeatureId, inConfigRawId, inConfigSerial, inMinScope, inMaxScope, inLicensedFeatures, inInitialVersion, inDeprecatedVersion, inEnabler, inLifecycleBeanName);
        this._maxLength = inMaxLength;
        this._password = inPassword;
        this._defaultValue = inDefaultValue;
    }

    public String getSetting(UserContext inUserContext) {
        String s = (String)super.getEffectiveSetting(inUserContext);
        if (s != null) {
            return s;
        }
        return this._defaultValue;
    }

    public String getSetting(IConfigValueProvider inProvider, UserContext inUserContext) {
        String configValue = inProvider.getConfigValue(this);
        if (configValue != null) {
            return configValue;
        }
        return this.getSetting(inUserContext);
    }

    @Override
    public Object getConfigDefaultValue() {
        return this._defaultValue;
    }

    public int getMaxLength() {
        return this._maxLength;
    }

    public boolean isPassword() {
        return this._password;
    }

    @Override
    public Object str2Value(String inStrValue) {
        return inStrValue;
    }

    @Override
    public ConfigClassEnum getConfigClass() {
        return ConfigClassEnum.FREETEXT;
    }

}
