package com.zpmc.ztos.infra.base.business.interfaces;

import org.springframework.beans.factory.ListableBeanFactory;

public interface ISessionFactoryPostInitializer {
    public void postInitialize(ListableBeanFactory var1);
}
