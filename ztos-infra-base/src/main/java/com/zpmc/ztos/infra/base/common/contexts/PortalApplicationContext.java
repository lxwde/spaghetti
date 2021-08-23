package com.zpmc.ztos.infra.base.common.contexts;

import com.zpmc.ztos.infra.base.business.enums.framework.BeanFactoryKeyEnum;
import com.zpmc.ztos.infra.base.business.interfaces.IMessageTranslator;
import com.zpmc.ztos.infra.base.business.interfaces.IMessageTranslatorProvider;
import com.zpmc.ztos.infra.base.common.exceptions.BizFailure;
import com.zpmc.ztos.infra.base.common.model.BeanFactoryBootstrap;
import com.zpmc.ztos.infra.base.common.model.Roastery;
import com.zpmc.ztos.infra.base.utils.CarinaUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Locale;

public class PortalApplicationContext extends BeanFactoryBootstrap {

    private static ConfigurableApplicationContext _appContext;
    private static final Logger LOGGER;

    public static <T> T getBean(String inBeanName) {
        Object retBean;
        if (Roastery.containsBean(inBeanName)) {
            throw BizFailure.createProgrammingFailure("Illegal access business tier bean from PortalApplicationContext for bean " + inBeanName);
        }
        ConfigurableApplicationContext beanFactory = PortalApplicationContext.getApplicationContext();
        try {
            retBean = beanFactory.getBean(inBeanName);
        }
        catch (BeansException e) {
            LOGGER.error((Object)("getBean: unable to get bean inBeanName = " + inBeanName + "\n" + e.toString() + "\n Called from: \n" + CarinaUtils.getStackTrace(e)));
            throw e;
        }
        return (T)retBean;
    }

    public static IMessageTranslator getMessageTranslator(Locale inLocale) {
        IMessageTranslatorProvider translatorProvider = (IMessageTranslatorProvider) PortalApplicationContext.getBean("messageTranslatorProvider");
        return translatorProvider.getMessageTranslator(inLocale);
    }

    public static ListableBeanFactory getBeanFactory() {
        return PortalApplicationContext.getApplicationContext().getBeanFactory();
    }

    public static boolean containsBean(String inBeanName) {
        ListableBeanFactory beanFactory = PortalApplicationContext.getBeanFactory();
        return beanFactory.containsBean(inBeanName);
    }

    public static synchronized ConfigurableApplicationContext getApplicationContext() {
        if (_appContext == null) {
            _appContext = PortalApplicationContext.initializeBizPortalAppContext(BeanFactoryKeyEnum.PORTAL.getKey());
        }
        return _appContext;
    }

    static {
        LOGGER = Logger.getLogger(PortalApplicationContext.class);
    }
}
