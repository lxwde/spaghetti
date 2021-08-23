package com.zpmc.ztos.infra.base.common.beans;

import com.zpmc.ztos.infra.base.business.interfaces.ITopmostBeanFactoryAware;
import org.springframework.beans.factory.ListableBeanFactory;

public class BeanFactoryLocation implements ITopmostBeanFactoryAware {
    ListableBeanFactory _beanFactory;

    @Override
    public void setTopmostBeanFactory(ListableBeanFactory inBeanFactory) {
        this._beanFactory = inBeanFactory;
    }

    public ListableBeanFactory getBeanFactory() {
        return this._beanFactory;
    }
}
