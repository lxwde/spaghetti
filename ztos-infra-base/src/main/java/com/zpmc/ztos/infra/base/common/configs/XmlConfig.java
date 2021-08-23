package com.zpmc.ztos.infra.base.common.configs;

import com.zpmc.ztos.infra.base.business.enums.framework.ConfigClassEnum;
import com.zpmc.ztos.infra.base.business.interfaces.IConfig;
import com.zpmc.ztos.infra.base.business.interfaces.IConfigValueProvider;
import com.zpmc.ztos.infra.base.common.model.UserContext;

public class XmlConfig extends AbstractConfig {
    private final boolean _isContentsEditable;

    public XmlConfig(String inModuleId, String inFeatureId, String inConfigRawId, String inConfigSerial, boolean inIsContentsEditable, int inMinScope, int inMaxScope, String[] inLicensedFeatures, String inInitialVersion, String inDeprecatedVersion, IConfig inEnabler, String inLifecycleBeanName) {
        super(inModuleId, inFeatureId, inConfigRawId, inConfigSerial, inMinScope, inMaxScope, inLicensedFeatures, inInitialVersion, inDeprecatedVersion, inEnabler, inLifecycleBeanName);
        this._isContentsEditable = inIsContentsEditable;
    }

    public String getSetting(UserContext inUserContext) {
        String s = (String)super.getEffectiveSetting(inUserContext);
        if (s != null) {
            return s;
        }
        return "";
    }

    public ScopeAwareConfig getConfigSetting(UserContext inUserContext) {
        return super.getEffectiveSettingWithScope(inUserContext);
    }

    public String getSetting(IConfigValueProvider inProvider, UserContext inUserContext) {
        String configValue = inProvider.getConfigValue(this);
        if (configValue != null) {
            return configValue;
        }
        return this.getSetting(inUserContext);
    }

    public boolean isContentsEditable() {
        return this._isContentsEditable;
    }

    @Override
    public Object getConfigDefaultValue() {
        return "";
    }

    @Override
    public Object str2Value(String inStrValue) {
        return inStrValue;
    }

    @Override
    public ConfigClassEnum getConfigClass() {
        return ConfigClassEnum.XML;
    }

}
