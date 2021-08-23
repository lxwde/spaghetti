package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.common.configs.ScopeAwareConfig;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.model.BizRequest;
import com.zpmc.ztos.infra.base.common.model.BizResponse;
import com.zpmc.ztos.infra.base.common.model.UserContext;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.io.Serializable;
import java.util.Collection;

public interface IConfigProvider {
    public static final String BEAN_ID = "configProvider";
    public static final String CACHE_PROVIDER_CARINA = "CARINA";
    public static final String CACHE_PROVIDER_EHCACHE = "EHCACHE";
    public static final String CACHE_PROVIDER_HAZELCAST = "HAZELCAST";
    public static final String CACHE_PROVIDER_NONE = "NONE";

    public Object getEffectiveSetting(UserContext var1, IConfig var2);

    public ScopeAwareConfig getEffectiveSettingWithScope(UserContext var1, IConfig var2);

    public Collection<IConfig> getAllConfigs();

    public IConfig findConfigById(String var1);

    @Nullable
    public IMessageCollector persistConfigSetting(UserContext var1, IConfig var2, Object var3, Long var4, Serializable var5);

    public void persistConfigSetting(IConfig var1, Object var2, Long var3, Serializable var4);

    public void provideScopeTreeValueObject(BizRequest var1, BizResponse var2);

    public void provideConfigSettings(BizRequest var1, BizResponse var2) throws BizViolation;

    public boolean verifyCache(UserContext var1, IConfig var2);

    public void setCacheProvider(String var1);

    @Nullable
    public IMessageCollector clearConfigSetting(UserContext var1, IConfig var2);

    public void clearCachedValues(IConfig var1);
}
