package com.zpmc.ztos.infra.base.common.utils;

import com.zpmc.ztos.infra.base.common.model.ModulesDependency;
import com.zpmc.ztos.infra.base.common.model.Roastery;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ApplicationContextUtils {
    private static final Logger LOGGER = Logger.getLogger(ApplicationContextUtils.class);

    private ApplicationContextUtils() {
    }

    @Nullable
    public static BeanDefinition getBeanDefinition(ConfigurableListableBeanFactory inBeanFactory, String inBeanName) {
        if (inBeanFactory.containsBeanDefinition(inBeanName)) {
            return inBeanFactory.getBeanDefinition(inBeanName);
        }
        ConfigurableListableBeanFactory targetBF = (ConfigurableListableBeanFactory)inBeanFactory.getParentBeanFactory();
        if (targetBF != null) {
            return ApplicationContextUtils.getBeanDefinition(targetBF, inBeanName);
        }
        return null;
    }

    public static void dumpRegisteredBeans(ConfigurableListableBeanFactory inBeanFactory, String[] inBeanNames) {
        LOGGER.setLevel(Level.INFO);
        LOGGER.info((Object)("Listing " + inBeanFactory.toString()));
        for (int i = 0; i < inBeanNames.length; ++i) {
            BeanDefinition bf;
            String beanName = inBeanNames[i];
            if (beanName.startsWith("&")) {
                beanName = beanName.substring(1);
            }
            if ((bf = ApplicationContextUtils.getBeanDefinition(inBeanFactory, beanName)) != null) {
                LOGGER.info((Object)("Bean #" + i + " : " + beanName + " " + bf.getResourceDescription()));
                continue;
            }
            LOGGER.info((Object)("Bean #" + i + " : " + beanName + " : unable to load bean definition, probably defined in a different context"));
        }
    }

    public static void dumpRegisteredBeansByHierarchy(ConfigurableListableBeanFactory inBeanFactory) {
        ConfigurableListableBeanFactory beanFactory = inBeanFactory;
        while (beanFactory != null) {
            ApplicationContextUtils.dumpRegisteredBeans(beanFactory, beanFactory.getBeanDefinitionNames());
            BeanFactory parent = beanFactory.getParentBeanFactory();
            beanFactory = (ConfigurableListableBeanFactory)parent;
            LOGGER.info((Object)"Listing Parent Framework Factory, beans may be overridden");
        }
    }

    public static List<String> getModuleBasedFactoryLocations() {
        ArrayList<String> factoryLocations = new ArrayList<String>();
        if (Roastery.containsBean("moduleDependency")) {
            ModulesDependency dependency = (ModulesDependency)Roastery.getBean("moduleDependency");
            Iterator<String> iterator = dependency.getModules().iterator();
            while (iterator.hasNext()) {
                String obj;
                String module = obj = iterator.next();
                String location = "classpath*:" + StringUtils.capitalise((String)module) + "BeanRefFactory.xml";
                factoryLocations.add(location);
            }
        }
        return factoryLocations;
    }
}
