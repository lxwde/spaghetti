package com.zpmc.ztos.infra.base.common.model;

import com.zpmc.ztos.infra.base.business.interfaces.IApiMappingLoader;
import com.zpmc.ztos.infra.base.business.interfaces.ITopmostBeanFactoryAware;
import com.zpmc.ztos.infra.base.common.utils.BeanFactoryUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;

public class ApiMappings implements ITopmostBeanFactoryAware {

    static final String BEAN_ID = "apiMappings";
    protected ListableBeanFactory _applicationContext;
    private static final Logger LOGGER = Logger.getLogger(ApiMappings.class);
    private ConfigurableListableBeanFactory _apiMappingBeanFactory;
    private String _mappingsXmlFilePath;

    public static ApiMapping getApiMapping(String inApiName) {
        ApiMappings instance = (ApiMappings) Roastery.getBean(BEAN_ID);
        return instance.resolveApiMapping(inApiName);
    }

    public ApiMapping resolveApiMapping(String inApiName) {
        if (this._apiMappingBeanFactory == null) {
            LOGGER.info((Object)"resolveApiMapping: Loading API mapping bean factory");
            this.initalizeMappings();
        }
        ApiMapping mapping = (ApiMapping)this._apiMappingBeanFactory.getBean(inApiName, ApiMapping.class);
        return mapping;
    }

    private synchronized void initalizeMappings() {
        XmlBeanFactory childFactory;
        if (this._apiMappingBeanFactory != null) {
            return;
        }
        String[] sources = BeanFactoryUtil.beanNamesIncludingAncestors(this._applicationContext, IApiMappingLoader.class);
        ConfigurableListableBeanFactory currentFactory = null;
        for (int i = 0; i < sources.length; ++i) {
            IApiMappingLoader loader = (IApiMappingLoader)this._applicationContext.getBean(sources[i]);
            currentFactory = loader.loadApiMappings(currentFactory);
        }
        this._apiMappingBeanFactory = currentFactory;
        if (StringUtils.isNotEmpty((String)this.getMappingsXmlFilePath()) && (childFactory = BeanFactoryUtil.loadAsBeanFactory(this.getMappingsXmlFilePath())) != null && currentFactory != null) {
            childFactory.setParentBeanFactory((BeanFactory)currentFactory);
            this._apiMappingBeanFactory = childFactory;
        }
        this.dumpRegisteredMappings(this._apiMappingBeanFactory, BeanFactoryUtils.beanNamesIncludingAncestors((ListableBeanFactory)this._apiMappingBeanFactory));
    }

    private void dumpRegisteredMappings(ConfigurableListableBeanFactory inBeanFactory, String[] inBeanNames) {
        LOGGER.info((Object)("Listing all API mappings from " + inBeanFactory.toString()));
        for (int i = 0; i < inBeanNames.length; ++i) {
            String beanName = inBeanNames[i];
            ApiMapping mapping = this.resolveApiMapping(beanName);
            LOGGER.info((Object)("Bean #" + i + " : " + beanName + " " + mapping.getHandlerClass() + "," + mapping.getHandlerMethod()));
        }
    }

    private String getMappingsXmlFilePath() {
        return this._mappingsXmlFilePath;
    }

    public void setMappingsXmlFilePath(String inMappingsXmlFilePath) {
        this._mappingsXmlFilePath = inMappingsXmlFilePath;
    }

    @Override
    public void setTopmostBeanFactory(ListableBeanFactory inBeanFactory) {
        this._applicationContext = inBeanFactory;
    }

}
