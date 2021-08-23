package com.zpmc.ztos.infra.base.common.model;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.business.enums.framework.BeanFactoryKeyEnum;
import com.zpmc.ztos.infra.base.business.interfaces.IBeanFactoryPostInitializer;
import com.zpmc.ztos.infra.base.business.interfaces.IFrameworkPropertyKeys;
import com.zpmc.ztos.infra.base.common.exceptions.BizFailure;
import com.zpmc.ztos.infra.base.common.utils.ApplicationContextUtils;
import com.zpmc.ztos.infra.base.common.utils.BeanFactoryUtil;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.HierarchicalBeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.access.BeanFactoryLocator;
import org.springframework.beans.factory.access.BeanFactoryReference;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.AbstractXmlApplicationContext;
import org.springframework.web.context.WebApplicationContext;

import java.util.*;

public class BeanFactoryBootstrap {

    private static final String BEAN_REF_FACTORY = "META-INF/beanRefFactory.xml";
    private static final String BEANS_REFS_XML_NAME = "classpath*:beanRefFactory.xml";
    private static final String FRAMEWORK_BEANS_REFS_XML_NAME = "classpath*:FrameworkBeanRefFactory.xml";
    private static final Logger LOGGER = Logger.getLogger(BeanFactoryBootstrap.class);
    protected static final String BEAN_REF_FACTORY_LOCATION = "beanRefFactory.xml";
    protected static final String ROASTERY_KEY = "app";
    private static final Map<BeanFactoryKeyEnum, ListableBeanFactory> BEAN_FACTORY_KEYS = new EnumMap<BeanFactoryKeyEnum, ListableBeanFactory>(BeanFactoryKeyEnum.class);
    private static WebApplicationContext _webContext;
    

    protected static ConfigurableApplicationContext initializeBizPortalAppContext(String inBeanFactoryKey) {
        ArrayList<String> factoryLocations = new ArrayList<String>();
        factoryLocations.add(FRAMEWORK_BEANS_REFS_XML_NAME);
        factoryLocations.add(BEAN_REF_FACTORY);
        return BeanFactoryBootstrap.initializeAppContext(BEAN_REF_FACTORY, inBeanFactoryKey, factoryLocations);
    }

    public static ConfigurableApplicationContext initializeAppContext(String inBeanFactoryKey) {
        List<String> factoryLocations = ApplicationContextUtils.getModuleBasedFactoryLocations();
        if (factoryLocations.isEmpty()) {
            factoryLocations = BeanFactoryBootstrap.getDefaultFactoryLocations();
        }
        return BeanFactoryBootstrap.initializeAppContext(BEAN_REF_FACTORY_LOCATION, inBeanFactoryKey, factoryLocations);
    }

    private static ConfigurableApplicationContext initializeAppContext(String inBeanFactoryLocation, String inBeanFactoryKey, List<String> inDependencies) {
        System.setProperty("org.apache.activemq.SERIALIZABLE_PACKAGES", "*");
        LOGGER.info((Object) ("Creating Bean Factory " + inBeanFactoryKey + " @ " + inBeanFactoryLocation));
        try {
            BeanFactoryKeyEnum registeredKey;
            LOGGER.info(("Looking for Singleton BeanFactoryLocator " + inBeanFactoryLocation));
            BeanFactoryLocator bfLocator = CarinaBeanFactoryLocator.getInstance(inBeanFactoryLocation, inDependencies);
            LOGGER.info(("Using BeanFactory identified by key " + inBeanFactoryKey));
            BeanFactoryReference bfReference = bfLocator.useBeanFactory(inBeanFactoryKey);
            LOGGER.info(("Getting the topMostFactory from the BeanFactoryReference " + bfReference));
            BeanFactory topmostBeanFactory = bfReference.getFactory();
//            BeanFactory topmostBeanFactory = (BeanFactory) SpringContextUtil.INSTANCE.getApplicationContext().getBean(inBeanFactoryKey);
//            BeanFactory topmostBeanFactory = SpringUtils.getBean(inBeanFactoryKey);

            ConfigurableApplicationContext configurableAppContext = null;
            if (!(topmostBeanFactory instanceof ConfigurableApplicationContext)) {
                throw BizFailure.createProgrammingFailure("Spring did not return bean factory of type ConfigurableApplicationContext");
            }
            configurableAppContext = (ConfigurableApplicationContext) topmostBeanFactory;
            BeanFactoryBootstrap.connectTopmostBeanFactoryAware((BeanFactory) configurableAppContext);
            if (LOGGER.isDebugEnabled()) {
                ConfigurableListableBeanFactory lbf = configurableAppContext.getBeanFactory();
                String[] strings = BeanFactoryUtil.beanNamesIncludingAncestors((ListableBeanFactory) lbf, Object.class);
                ApplicationContextUtils.dumpRegisteredBeans(lbf, strings);
            }
            if ((registeredKey = BeanFactoryKeyEnum.get(inBeanFactoryKey)) != null) {
                BEAN_FACTORY_KEYS.put(registeredKey, (ListableBeanFactory) configurableAppContext);
            }
            if (registeredKey != BeanFactoryKeyEnum.MOBILE_AND_STRUTS && registeredKey != BeanFactoryKeyEnum.PRESENTATION_FRAMEWORK) {
                BeanFactoryBootstrap.postInitialize((ListableBeanFactory) configurableAppContext);
            }
            return configurableAppContext;
        } catch (Throwable t) {
            String msg = "FAILED TO INITIALIZE Application Context for Multiple Source Bean factory: = " + t.getMessage();
            LOGGER.error((Object) msg);
            throw BizFailure.create(IFrameworkPropertyKeys.FAILURE__SYSTEM, t, msg);
        }
    }

    protected static void postInitialize(ListableBeanFactory inFactory) {
        if (inFactory.containsBean("beanFactoryPostInitializer")) {
            IBeanFactoryPostInitializer postInitializer = (IBeanFactoryPostInitializer) inFactory.getBean("beanFactoryPostInitializer");
            postInitializer.postInitialize(inFactory);
        }
    }

    public static void connectTopmostBeanFactoryAware(BeanFactory inTopmostBeanFactory) {
        if (inTopmostBeanFactory instanceof ConfigurableApplicationContext) {
            ConfigurableApplicationContext axac = (ConfigurableApplicationContext) inTopmostBeanFactory;
            ConfigurableListableBeanFactory lbf = axac.getBeanFactory();
            TopmostBeanFactoryAwareProcessor bfpp = new TopmostBeanFactoryAwareProcessor((ListableBeanFactory) lbf);
            ConfigurableListableBeanFactory f = lbf;
            while (f != null) {
                ConfigurableBeanFactory h = null;
                if (f instanceof ConfigurableBeanFactory) {
                    h = (ConfigurableBeanFactory) f;
                } else if (f instanceof AbstractXmlApplicationContext) {
                    AbstractXmlApplicationContext aac = (AbstractXmlApplicationContext) f;
                    h = aac.getBeanFactory();
                }
                if (h != null) {
                    h.addBeanPostProcessor((BeanPostProcessor) bfpp);
                } else {
                    LOGGER.error((Object) ("Unable to instrument factory " + f.toString()));
                }
                if (f instanceof HierarchicalBeanFactory) {
                    f = (ConfigurableListableBeanFactory) ((HierarchicalBeanFactory)f).getParentBeanFactory();
                    continue;
                }
                f = null;
            }
        }
    }

    @NotNull
    public static Map<BeanFactoryKeyEnum, ListableBeanFactory> getRegisteredBeanFactoryKeys() {
        return Collections.unmodifiableMap(BEAN_FACTORY_KEYS);
    }

    protected static List<String> getDefaultFactoryLocations() {
        ArrayList<String> factoryLocations = new ArrayList<String>();
        factoryLocations.add(FRAMEWORK_BEANS_REFS_XML_NAME);
        factoryLocations.add(BEANS_REFS_XML_NAME);
        return factoryLocations;
    }

    public static WebApplicationContext getWebApplicationContext() {
        return _webContext;
    }

    protected static void setWebApplicationContext(WebApplicationContext inWebContext) {
        _webContext = inWebContext;
    }

    protected static void putBeanFactoryKey(BeanFactoryKeyEnum inBeanFactoryKeyEnum, ListableBeanFactory inListableBeanFactory) {
        BEAN_FACTORY_KEYS.put(inBeanFactoryKeyEnum, inListableBeanFactory);
    }

    static void removeBeanFactoryKey(BeanFactoryKeyEnum inBeanFactoryKeyEnum) {
        BEAN_FACTORY_KEYS.remove((Object) inBeanFactoryKeyEnum);
    }
}
