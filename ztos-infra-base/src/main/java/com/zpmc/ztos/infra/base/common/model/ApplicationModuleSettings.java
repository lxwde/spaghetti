package com.zpmc.ztos.infra.base.common.model;

import com.zpmc.ztos.infra.base.business.enums.framework.CacheProviderLibraryEnum;
import com.zpmc.ztos.infra.base.business.enums.framework.LetterCaseEnum;
import com.zpmc.ztos.infra.base.business.interfaces.ITopmostBeanFactoryAware;
import com.zpmc.ztos.infra.base.common.exceptions.BizFailure;
import com.zpmc.ztos.infra.base.common.type.FuzzyBoolean;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;

import java.math.RoundingMode;
import java.util.Collections;
import java.util.Map;

public class ApplicationModuleSettings implements InitializingBean, ITopmostBeanFactoryAware {

    public static final String BEAN_ID = "appModuleSettings";
    private static final Logger LOGGER = Logger.getLogger(ApplicationModuleSettings.class);
    private static final String CACHE_PROVIDER_PROPS = "cacheProviderProperties";
    private ListableBeanFactory _beanFactory;
    private final RoundingMode _defaultDecimalRoundingMode = RoundingMode.HALF_UP;
    private CacheProviderLibraryEnum _cacheProviderLibrary = CacheProviderLibraryEnum.CARINA;
    private boolean _usesCacheProvider;
    private boolean _usesSpatial;
    private boolean _isClustered;
    private FuzzyBoolean _isGloballyScoped = FuzzyBoolean.UNSPECIFIED;
    private Map<String, String> _jndiClientConfigProperties = Collections.emptyMap();
    private LetterCaseEnum _defaultLetterCaseMode = LetterCaseEnum.UPPER;

    public CacheProviderLibraryEnum getCacheProviderLibrary() {
        return this._cacheProviderLibrary;
    }

    public boolean usesCacheProvider() {
        return this._usesCacheProvider;
    }

    public void setUsesCacheProvider(boolean inUsesCacheProvider) {
        this._usesCacheProvider = inUsesCacheProvider;
    }

    public boolean usesSpatial() {
        return this._usesSpatial;
    }

    public void setUsesSpatial(boolean inUsesSpatial) {
        this._usesSpatial = inUsesSpatial;
    }

    public FuzzyBoolean isGloballyScoped() {
        return this._isGloballyScoped;
    }

    public void setGloballyScoped(boolean inGloballyScoped) {
        this._isGloballyScoped = FuzzyBoolean.getFuzzyBoolean(inGloballyScoped);
    }

    public boolean isClustered() {
        return this._isClustered;
    }

    public void setClustered(boolean inClustered) {
        this._isClustered = inClustered;
    }

    public Map<String, String> getJndiClientConfigProperties() {
        return Collections.unmodifiableMap(this._jndiClientConfigProperties);
    }

    public void setJndiClientConfigProperties(Map<String, String> inJndiClientConfigProperties) {
        this._jndiClientConfigProperties = inJndiClientConfigProperties;
    }

    public LetterCaseEnum getDefaultLetterCase() {
        return this._defaultLetterCaseMode;
    }

    public void setDefaultLetterCaseMode(String inDefaultLetterCaseMode) {
        if (StringUtils.equals((String)inDefaultLetterCaseMode, (String)"UPPER")) {
            this._defaultLetterCaseMode = LetterCaseEnum.UPPER;
        } else if (StringUtils.equals((String)inDefaultLetterCaseMode, (String)"Mixed")) {
            this._defaultLetterCaseMode = LetterCaseEnum.MIXED;
        } else if (StringUtils.equals((String)inDefaultLetterCaseMode, (String)"lower")) {
            this._defaultLetterCaseMode = LetterCaseEnum.LOWER;
        } else {
            LOGGER.error((Object)("Unknown letterCase property: " + inDefaultLetterCaseMode));
        }
    }

    public RoundingMode getDefaultDecimalRoundingMode() {
        return this._defaultDecimalRoundingMode;
    }

    public void setDefaultDecimalRoundingMode(RoundingMode inDefaultDecimalRoundingMode) {
        if (RoundingMode.UNNECESSARY == inDefaultDecimalRoundingMode) {
            LOGGER.error((Object)("Unsupported Java Rounding mode " + (Object)((Object)inDefaultDecimalRoundingMode) + " Using default " + (Object)((Object)this._defaultDecimalRoundingMode) + "..."));
        }
    }

    @Override
    public void setTopmostBeanFactory(ListableBeanFactory inBeanFactory) {
        this._beanFactory = inBeanFactory;
    }

    public void afterPropertiesSet() throws Exception {
        try {
            Map cacheProviderProps = (Map)this._beanFactory.getBean(CACHE_PROVIDER_PROPS);
            this._cacheProviderLibrary = CacheProviderLibraryEnum.valueOf((String)cacheProviderProps.get("name"));
        }
        catch (NoSuchBeanDefinitionException exc) {
            LOGGER.info((Object)("No bean 'cacheProviderProperties' has been defined. Defauting to " + (Object)((Object) CacheProviderLibraryEnum.CARINA)));
        }
        if (this._usesCacheProvider && (CacheProviderLibraryEnum.CARINA == this._cacheProviderLibrary || CacheProviderLibraryEnum.NONE == this._cacheProviderLibrary)) {
            throw BizFailure.create("Beans cacheProviderProperties and appModuleSettings have inconsistent properties.");
        }
    }
}
