package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.business.enums.framework.ConfigClassEnum;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

public interface IConfig extends IFeatureAware{
    public String getConfigId();

    public String getConfigRawId();

    public String getFeatureId();

    public String getModuleId();

    public int getMinScope();

    public int getMaxScope();

    public IPropertyKey getDescriptionKey();

    public IPropertyKey getDeprecatedExplanationKey();

    public boolean isDeprecated();

    public String getInitialVersion();

    public String getDeprecatedVersion();

    public IConfig getEnablerConfig();

    public Object getConfigDefaultValue();

    public Object str2Value(String var1);

    public String value2Str(Object var1);

    public ConfigClassEnum getConfigClass();

    public Object getKey();

    @Nullable
    public IConfigSettingLifecycle getConfigLifecycle();
}
