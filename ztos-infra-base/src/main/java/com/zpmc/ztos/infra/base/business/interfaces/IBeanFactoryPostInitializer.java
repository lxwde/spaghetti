package com.zpmc.ztos.infra.base.business.interfaces;

import org.springframework.beans.factory.ListableBeanFactory;

public interface IBeanFactoryPostInitializer {
    public static final String BEAN_ID = "beanFactoryPostInitializer";

    public void postInitialize(ListableBeanFactory var1);

}
