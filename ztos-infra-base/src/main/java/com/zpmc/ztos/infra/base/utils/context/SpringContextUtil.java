package com.zpmc.ztos.infra.base.utils.context;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public enum SpringContextUtil {
    INSTANCE;
    ApplicationContext context;

    public ApplicationContext getApplicationContext() {
        if (context == null)
            context = new ClassPathXmlApplicationContext("classpath*:beanRefContext.xml");
        return context;
    }

}
