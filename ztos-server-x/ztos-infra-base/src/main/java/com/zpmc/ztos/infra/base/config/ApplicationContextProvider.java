package com.zpmc.ztos.infra.base.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextProvider implements ApplicationContextAware {

    public static ApplicationContext APPLICATION_CONTEXT;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        APPLICATION_CONTEXT = applicationContext;
    }

    public ApplicationContext getContext() {
        return APPLICATION_CONTEXT;
    }

    public static ApplicationContext getApplicationContext() {
        return APPLICATION_CONTEXT;
    }
}
