package com.zpmc.ztos.infra.base.business.interfaces;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

public interface IApiMappingLoader {
    public ConfigurableListableBeanFactory loadApiMappings(ConfigurableListableBeanFactory var1);

}
