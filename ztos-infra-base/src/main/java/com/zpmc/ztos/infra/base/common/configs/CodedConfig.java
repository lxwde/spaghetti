package com.zpmc.ztos.infra.base.common.configs;

import com.zpmc.ztos.infra.base.business.enums.framework.ConfigClassEnum;
import com.zpmc.ztos.infra.base.business.interfaces.IConfig;
import com.zpmc.ztos.infra.base.business.interfaces.IConfigValueProvider;
import com.zpmc.ztos.infra.base.common.model.UserContext;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class CodedConfig extends AbstractConfig {
    private final boolean _isListable;
    private final String[] _defaultSettings;
    private final String[] _allowedSettings;
    private static final String COMMA = ",";

    public CodedConfig(String inModuleId, String inFeatureId, String inConfigRawId, String inConfigSerial, boolean inIsListable, String inDefaultSetting, String[] inAllowedSettings, int inMinScope, int inMaxScope, String[] inLicensedFeatures, String inInitialVersion, String inDeprecatedVersion, IConfig inEnabler, String inLifecycleBeanName) {
        super(inModuleId, inFeatureId, inConfigRawId, inConfigSerial, inMinScope, inMaxScope, inLicensedFeatures, inInitialVersion, inDeprecatedVersion, inEnabler, inLifecycleBeanName);
        this._isListable = inIsListable;
        this._defaultSettings = new String[]{inDefaultSetting};
        this._allowedSettings = inAllowedSettings;
    }

    public boolean isSetTo(String inTestSetting, UserContext inUserContext) {
        return this.internalIsSetTo(inTestSetting, (String[])super.getEffectiveSetting(inUserContext));
    }

    public boolean isSetTo(String inTestSetting, IConfigValueProvider inProvider, UserContext inUserContext) {
        String configValue = inProvider.getConfigValue(this);
        if (configValue == null) {
            return this.isSetTo(inTestSetting, inUserContext);
        }
        String[] strings = (String[])this.str2Value(configValue);
        return this.internalIsSetTo(inTestSetting, strings);
    }

    public String[] getSettings(UserContext inUserContext) {
        return this.internalGetSettings((String[])super.getEffectiveSetting(inUserContext));
    }

    private boolean internalIsSetTo(String inTestSetting, String[] inSettings) {
        for (String inSetting : inSettings) {
            if (!StringUtils.equals((String)inTestSetting, (String)inSetting)) continue;
            return true;
        }
        return false;
    }

    public String[] internalGetSettings(String[] inSettings) {
        if (inSettings != null) {
            return inSettings;
        }
        return this._defaultSettings;
    }

    @Override
    public Object getConfigDefaultValue() {
        return this._defaultSettings;
    }

    public String[] getAllowedSettings() {
        return this._allowedSettings;
    }

    public boolean isListable() {
        return this._isListable;
    }

    public int getMaxLength() {
        int maxLength = 0;
        for (String allowedSetting : this._allowedSettings) {
            if (allowedSetting.length() <= maxLength) continue;
            maxLength = allowedSetting.length();
        }
        return maxLength;
    }

    @Override
    public Object str2Value(String inStrValue) {
        StringTokenizer parser = new StringTokenizer(inStrValue, COMMA, false);
        ArrayList<String> temp = new ArrayList<String>();
        while (parser.hasMoreTokens()) {
            temp.add(parser.nextToken());
        }
        String[] result = new String[temp.size()];
        return temp.toArray(result);
    }

    @Override
    public String value2Str(Object inValue) {
        if (inValue instanceof Serializable[]) {
            Serializable[] strs = (Serializable[])inValue;
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < strs.length; ++i) {
                if (i > 0) {
                    result.append(COMMA);
                }
                result.append(String.valueOf(strs[i]));
            }
            return result.toString();
        }
        return super.value2Str(inValue);
    }

    @Override
    public ConfigClassEnum getConfigClass() {
        if (this._isListable) {
            return ConfigClassEnum.CODESET;
        }
        return ConfigClassEnum.CODE;
    }

}
