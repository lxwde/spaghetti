package com.zpmc.ztos.infra.base.common.model;

import com.zpmc.ztos.infra.base.business.enums.framework.BeanFactoryKeyEnum;
import com.zpmc.ztos.infra.base.business.interfaces.IFrameworkPropertyKeys;
import com.zpmc.ztos.infra.base.business.interfaces.IMetafieldDictionary;
import com.zpmc.ztos.infra.base.business.interfaces.IMetafieldDictionaryProvider;
import com.zpmc.ztos.infra.base.business.model.BaseConfigurationProperties;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.common.exceptions.BizFailure;
import com.zpmc.ztos.infra.base.utils.CarinaUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

public class Roastery extends BeanFactoryBootstrap {
    private static final String GET_BEAN_UNABLE_TO_GET_BEAN_IN_BEAN_NAME = "getBean: unable to get bean inBeanName = ";
    private static final Logger LOGGER = Logger.getLogger(Roastery.class);
    private static Boolean _useHibernate;
    private static ConfigurableListableBeanFactory _beanFactory;
    private static boolean _isInitializing;
    private static final BaseConfigurationProperties BC;

    public static <T> T getBean(String inBeanName) {
        ListableBeanFactory beanFactory = Roastery.getBeanFactory();
        Object retBean = null;
        try {
            if ("hibernateApi".equals(inBeanName)) {
                Roastery.getBean("hibernateSessionFactory");
            }
            retBean = beanFactory.getBean(inBeanName);
        }
        catch (BeansException e) {
            LOGGER.error((Object)(GET_BEAN_UNABLE_TO_GET_BEAN_IN_BEAN_NAME + inBeanName + "\n" + e.toString() + "\n" + CarinaUtils.getStackTrace(e)));
            throw e;
        }
        return (T)retBean;
    }

    public static <T> T getBean(String inBeanName, Object[] inArgs) {
        ListableBeanFactory beanFactory = Roastery.getBeanFactory();
        Object retBean = null;
        try {
            if ("hibernateApi".equals(inBeanName)) {
                Roastery.getBean("hibernateSessionFactory");
            }
            retBean = beanFactory.getBean(inBeanName, inArgs);
        }
        catch (BeansException e) {
            LOGGER.error((Object)(GET_BEAN_UNABLE_TO_GET_BEAN_IN_BEAN_NAME + inBeanName + "\n" + e.toString() + "\n" + CarinaUtils.getStackTrace(e)));
            throw e;
        }
        return (T)retBean;
    }

    public static <T> T getBean(String inBeanName, Class<T> inClass) {
        ListableBeanFactory beanFactory = Roastery.getBeanFactory();
        Object retBean = null;
        try {
            retBean = beanFactory.getBean(inBeanName, inClass);
        }
        catch (BeansException e) {
            LOGGER.error((Object)(GET_BEAN_UNABLE_TO_GET_BEAN_IN_BEAN_NAME + inBeanName + "\n" + e.toString() + "\n" + BizFailure.create("").getStackTraceString()));
            throw e;
        }
        return (T)retBean;
    }

    public static boolean containsBean(String inBeanName) {
        ListableBeanFactory beanFactory = Roastery.getBeanFactory();
        return beanFactory.containsBean(inBeanName);
    }

    public static ListableBeanFactory getBeanFactory() {
        if (_beanFactory == null) {
            Roastery.initialize();
            _isInitializing = false;
        }
        return _beanFactory;
    }

    private static BaseConfigurationProperties getBaseConfigurationProperties() {
        return BC;
    }

    private static synchronized void initialize() {
        LOGGER.info((Object)"Creating Bean Factory");
        if (_beanFactory != null) {
            return;
        }
        if (_isInitializing) {
            LOGGER.error((Object)"You are causing reentrant calls to the Roastery initialization. Please use dependency or setter injection and not the Roastery directly in your constructors or cause a Roastery via a static method.");
            BizFailure ex = BizFailure.create("");
            LOGGER.error((Object)ex.getStackTraceString());
        }
        _isInitializing = true;
        try {
            _beanFactory = Roastery.initializeBizPortalAppContext(BeanFactoryKeyEnum.ROASTERY.getKey()).getBeanFactory();
        }
        catch (Exception e) {
            String msg = "FAILED TO INITIALIZE ROASTERY: = " + e.getMessage();
            LOGGER.error((Object)msg);
            throw BizFailure.create(IFrameworkPropertyKeys.FAILURE__SYSTEM, e, msg);
        }
    }

    public static IMetafieldDictionary getMetafieldDictionary() {
        IMetafieldDictionaryProvider mfdp = (IMetafieldDictionaryProvider) Roastery.getBean("MetafieldDictionaryProvider");
        return mfdp.getMetafieldDictionary();
    }

    public static HibernateApi getHibernateApi() {
        return (HibernateApi) Roastery.getBean("hibernateApi");
    }

    public static boolean usesHibernate() {
        if (_useHibernate == null) {
            _useHibernate = Roastery.containsBean("hibernateSessionFactory");
        }
        return _useHibernate;
    }

    static {
        BC = (BaseConfigurationProperties) Roastery.getBean("ConfigurationProperties");
    }
}
