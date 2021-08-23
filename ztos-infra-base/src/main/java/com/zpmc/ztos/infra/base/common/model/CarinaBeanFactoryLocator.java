package com.zpmc.ztos.infra.base.common.model;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.access.BeanFactoryLocator;
import org.springframework.beans.factory.access.BeanFactoryReference;
import org.springframework.beans.factory.access.SingletonBeanFactoryLocator;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;
import java.util.*;

public class CarinaBeanFactoryLocator extends SingletonBeanFactoryLocator {
    private static final Map<String,CarinaBeanFactoryLocator> INSTANCES = new HashMap<String,CarinaBeanFactoryLocator>();
    private final List _factoryLocations;
    private static final Logger LOGGER = Logger.getLogger(CarinaBeanFactoryLocator.class);

    protected CarinaBeanFactoryLocator(String inResourceName, List inDependencies) {
        super(inResourceName);
        this._factoryLocations = inDependencies;
        LOGGER.setLevel(Level.INFO);
    }

    protected BeanFactory createDefinition(String inResourceName, String inFactoryKey) throws BeansException {
        LOGGER.info((Object)("Creating Bean Factory " + inResourceName + " key=" + inFactoryKey));
        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader((BeanDefinitionRegistry)factory);
        PathMatchingResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        ArrayList<Resource> beanRefs = new ArrayList<Resource>();
        if (this._factoryLocations != null) {
            for (Object location : this._factoryLocations) {
                try {
                    Resource[] moduleResources = resourcePatternResolver.getResources((String)location);
                    if (moduleResources.length <= 0) continue;
                    beanRefs.add(moduleResources[0]);
                }
                catch (IOException iOException) {}
            }
        }
        if (beanRefs.isEmpty()) {
            Resource[] configResources;
            try {
                configResources = resourcePatternResolver.getResources(inResourceName);
            }
            catch (IOException e) {
                throw new BeanDefinitionStoreException("Error accessing bean definition resource", (Throwable)e);
            }
            Collections.addAll(beanRefs, configResources);
        }
        if (beanRefs.isEmpty()) {
            throw new FatalBeanException("Unable to find resource for specified definition. Group resource name [" + inResourceName + "], factory key [" + inFactoryKey + "]");
        }
        try {
            for (Resource resource : beanRefs) {
                LOGGER.info((Object)("Loading: " + (Object)resource));
                reader.loadBeanDefinitions(resource);
            }
            factory.preInstantiateSingletons();
        }
        catch (Throwable e) {
            throw new FatalBeanException("Unable to load group definition. Group resource name [" + inResourceName + "], factory key [" + inFactoryKey + "] because of \n", e);
        }
        factory.addBeanPostProcessor((BeanPostProcessor)new TopmostBeanFactoryAwareProcessor((ListableBeanFactory)factory));
        return factory;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static BeanFactoryLocator getInstance(String inSelector, List inLocations) throws FatalBeanException {
        if (inSelector.indexOf(58) == -1) {
            inSelector = "classpath:" + inSelector;
        }
        Map map = INSTANCES;
        synchronized (map) {
            CarinaBeanFactoryLocator bfl = (CarinaBeanFactoryLocator)((Object)INSTANCES.get(inSelector));
            if (bfl == null) {
                bfl = new CarinaBeanFactoryLocator(inSelector, inLocations);
                INSTANCES.put(inSelector, bfl);
            }
            return (BeanFactoryLocator) bfl;
        }
    }

}
