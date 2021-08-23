package com.zpmc.ztos.infra.base.utils;

import com.zpmc.ztos.infra.base.config.ApplicationContextProvider;
import com.zpmc.ztos.infra.base.event.EventListener;
import com.zpmc.ztos.infra.base.event.EventSubscriber;
import com.zpmc.ztos.infra.base.event.ZpmcEventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.config.DestructionAwareBeanPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionException;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.util.function.BiConsumer;

public class ZpmcBeanPostProcessor implements DestructionAwareBeanPostProcessor {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    SpelExpressionParser expressionParser = new SpelExpressionParser();

    @Override
    public void postProcessBeforeDestruction(Object bean, String beanName)
            throws BeansException {
        this.process(bean, ZpmcEventBus::unsubscribe, "destruction");
        this.process(bean, ZpmcEventBus::unsubscribeMQ, "destruction");
    }

    @Override
    public boolean requiresDestruction(Object bean) {
        return true;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName)
            throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName)
            throws BeansException {
        this.process(bean, ZpmcEventBus::subscribe, "initialization");
        this.process(bean, ZpmcEventBus::subscribeMQ, "initialization");
        return bean;
    }

    private void process(Object bean, BiConsumer<ZpmcEventBus, EventListener> consumer, String action) {
        Object proxy = this.getTargetObject(bean);
        final EventSubscriber annotation = AnnotationUtils.getAnnotation(proxy.getClass(), EventSubscriber.class);
        if (annotation == null)
            return;
        this.logger.info("{}: processing bean of type {} during {}", this.getClass().getSimpleName(), proxy.getClass().getName(),
                action);
        final String annotationValue = annotation.value();
        try {
            final Expression expression = this.expressionParser.parseExpression(annotationValue);
//            final Object value = expression.getValue();
            final Object value = ApplicationContextProvider.getApplicationContext().getBean(ZpmcEventBus.class);
            if (!(value instanceof ZpmcEventBus)) {
                this.logger.error("{}: expression {} did not evaluate to an instance of EventBus for bean of type {}",
                        this.getClass().getSimpleName(), annotationValue, proxy.getClass().getSimpleName());
                return;
            }
            final ZpmcEventBus eventBus = (ZpmcEventBus)value;
            consumer.accept(eventBus, (EventListener) proxy);
        } catch (ExpressionException ex) {
            this.logger.error("{}: unable to parse/evaluate expression {} for bean of type {}", this.getClass().getSimpleName(),
                    annotationValue, proxy.getClass().getName());
        }
    }

    private Object getTargetObject(Object proxy) throws BeansException {
        if (AopUtils.isJdkDynamicProxy(proxy)) {
            try {
                return ((Advised)proxy).getTargetSource().getTarget();
            } catch (Exception e) {
                throw new FatalBeanException("Error getting target of JDK proxy", e);
            }
        }
        return proxy;
    }
}