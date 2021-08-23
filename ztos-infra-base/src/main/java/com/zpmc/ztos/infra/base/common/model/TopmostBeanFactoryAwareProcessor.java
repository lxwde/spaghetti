package com.zpmc.ztos.infra.base.common.model;

import com.zpmc.ztos.infra.base.business.interfaces.ITopmostBeanFactoryAware;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class TopmostBeanFactoryAwareProcessor implements BeanPostProcessor {

    private final ListableBeanFactory _topmostBeanFactory;

    public TopmostBeanFactoryAwareProcessor(ListableBeanFactory inTopmostBeanFactory) {
        this._topmostBeanFactory = inTopmostBeanFactory;
    }

    public Object postProcessBeforeInitialization(Object inO, String inS) throws BeansException {
        if (inO instanceof ITopmostBeanFactoryAware) {
            ((ITopmostBeanFactoryAware)inO).setTopmostBeanFactory(this._topmostBeanFactory);
        }
        return inO;
    }

    public Object postProcessAfterInitialization(Object inO, String inS) throws BeansException {
        return inO;
    }

}
