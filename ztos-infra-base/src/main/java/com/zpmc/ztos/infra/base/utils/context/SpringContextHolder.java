package com.zpmc.ztos.infra.base.utils.context;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author Java002
 * @Date 2021/6/25 14:35
 */
@Slf4j
@Component
public class SpringContextHolder implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        SpringContextHolder.applicationContext = applicationContext;
//        log.info("get springContext is success");
    }

    /**
     * 获取applicationContext
     */
    public static ApplicationContext getApplicationContext() {
        checkApplicationContext();
        return applicationContext;
    }


    /**
     * 获取spring bean
     */
    public static <T> T getBean(String beanName) {
        checkApplicationContext();
        return (T) applicationContext.getBean(beanName);
    }

    /**
     * 获取spring bean
     */
    public static <T> T getBean(String beanName, Class<T> clazz) {
        checkApplicationContext();
        return applicationContext.getBean(beanName, clazz);
    }

    /**
     * 获取spring bean
     */
    public static <T> T getBean(Class<T> clazz) {
        checkApplicationContext();
        Map<String, T> beans = applicationContext.getBeansOfType(clazz);
        if (beans.isEmpty()) {
            return null;
        }
        return beans.values().iterator().next();
    }

    private static void checkApplicationContext() {
        if (applicationContext == null) {
            throw new IllegalStateException("applicaitonContext为空，请检查配置");
        }
    }
}
