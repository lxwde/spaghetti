package com.zpmc.ztos.infra.base.common.configs;

import com.zpmc.ztos.infra.base.business.interfaces.IConfig;
import com.zpmc.ztos.infra.base.business.interfaces.IConfigSettingLifecycle;
import com.zpmc.ztos.infra.base.business.interfaces.IFeatureId;
import com.zpmc.ztos.infra.base.business.interfaces.IPropertyKey;
import com.zpmc.ztos.infra.base.business.model.FeatureIdFactory;
import com.zpmc.ztos.infra.base.common.model.PropertyKeyFactory;
import com.zpmc.ztos.infra.base.common.model.UserContext;
import org.apache.log4j.Logger;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.util.*;

public abstract class AbstractConfig implements IConfig {
    private static final Map<String, IConfig> CONFIG_REGISTRY = new HashMap<String, IConfig>();
    private final String _moduleId;
    private final String _featureId;
    private final String _configRawId;
    private final String _configId;
    private final IConfig _enablerConfig;
    private final boolean _isDeprecated;
    private int _maxScope;
    private int _minScope;
    private Set<IFeatureId> _licensedFeatures;
    private final String _initialVersion;
    private final String _deprecatedVersion;
    private final IPropertyKey _descriptionKey;
    private final IPropertyKey _deprecatedExplanationKey;
    private final String _configLifecycleBean;
    private static final Logger LOGGER = Logger.getLogger(AbstractConfig.class);

    protected AbstractConfig(String inModuleId, String inFeatureId, String inConfigRawId, String inConfigSerial, int inMinScope, int inMaxScope, String[] inLicensedFeatures, String inInitialVersion, String inDeprecatedVersion, IConfig inEnabler, String inLifecycleBeanName) {
        this._moduleId = inModuleId;
        this._featureId = inFeatureId;
        this._configRawId = inConfigRawId;
        this._configId = inModuleId + inFeatureId + inConfigSerial;
        this._enablerConfig = inEnabler;
        this._maxScope = inMaxScope;
        this._minScope = inMinScope;
        this.setLicensedFeatures(inLicensedFeatures);
        this._initialVersion = inInitialVersion;
        this._deprecatedVersion = inDeprecatedVersion;
        this._isDeprecated = inDeprecatedVersion != null;
        this._descriptionKey = PropertyKeyFactory.valueOf("config.description." + this._configId);
        this._deprecatedExplanationKey = PropertyKeyFactory.valueOf("config.deprecated." + this._configId);
        this._configLifecycleBean = inLifecycleBeanName;
        if (inMinScope <= 0) {
            LOGGER.error((Object)("AbstractConfig: bad value for minScope, forcing to 1: " + this._configId + " value was: " + inMinScope));
            this._minScope = 1;
        }
        if (inMaxScope <= 0) {
            LOGGER.error((Object)("AbstractConfig: bad value for maxScope, forcing to 1: " + this._configId + " value was: " + inMaxScope));
            this._maxScope = 1;
        }
        CONFIG_REGISTRY.put(this.getConfigId(), this);
    }

    protected Object getEffectiveSetting(UserContext inUserContext) {
//        ConfigProvider configProvider = (ConfigProvider)Roastery.getBean("configProvider");
//        return configProvider.getEffectiveSetting(inUserContext, this);
        return null;
    }

    protected ScopeAwareConfig getEffectiveSettingWithScope(UserContext inUserContext) {
//        ConfigProvider configProvider = (ConfigProvider)Roastery.getBean("configProvider");
//        return configProvider.getEffectiveSettingWithScope(inUserContext, this);
        return null;
    }

    @Override
    public String getConfigId() {
        return this._configId;
    }

    @Override
    public String getConfigRawId() {
        return this._configRawId;
    }

    @Override
    public String getFeatureId() {
        return this._featureId;
    }

    @Override
    public String getModuleId() {
        return this._moduleId;
    }

    @Override
    public int getMinScope() {
        return this._minScope;
    }

    @Override
    public int getMaxScope() {
        return this._maxScope;
    }

    @Override
    public IPropertyKey getDescriptionKey() {
        return this._descriptionKey;
    }

    @Override
    public IPropertyKey getDeprecatedExplanationKey() {
        return this._deprecatedExplanationKey;
    }

    @Override
    public boolean isDeprecated() {
        return this._isDeprecated;
    }

    @Override
    public String getInitialVersion() {
        return this._initialVersion;
    }

    @Override
    public String getDeprecatedVersion() {
        return this._deprecatedVersion;
    }

    @Override
    public IConfig getEnablerConfig() {
        return this._enablerConfig;
    }

    @Override
    @Nullable
    public String value2Str(Object inValue) {
        if (inValue == null) {
            return null;
        }
        return inValue.toString();
    }

    public String toString() {
        return this._configId;
    }

    protected static Map<String, IConfig> getConfigRegistry() {
        return CONFIG_REGISTRY;
    }

    @Override
    public Object getKey() {
        return this._configId;
    }

    public String getConfigLifecycleBean() {
        return this._configLifecycleBean;
    }

    @Override
    public IConfigSettingLifecycle getConfigLifecycle() {
//        if (StringUtils.isNotEmpty((String)this._configLifecycleBean)) {
//            return (IConfigSettingLifecycle)PortalApplicationContext.getBean(this._configLifecycleBean);
//        }
        return null;
    }

    @Override
    public boolean hasNoFeatures() {
        return this._licensedFeatures.isEmpty();
    }

    @Override
    public List<IFeatureId> getFeatureIds() {
        return Collections.unmodifiableList(new ArrayList<IFeatureId>(this._licensedFeatures));
    }

    @Override
    public boolean hasFeature(IFeatureId inFeatureId) {
        return this._licensedFeatures.contains(inFeatureId);
    }

    private void setLicensedFeatures(String[] inLicensedFeatures) {
        if (inLicensedFeatures == null) {
            this._licensedFeatures = Collections.emptySet();
        } else {
            this._licensedFeatures = new HashSet<IFeatureId>();
            for (String feature : inLicensedFeatures) {
                this._licensedFeatures.add(FeatureIdFactory.valueOf(feature));
            }
        }
    }
}
