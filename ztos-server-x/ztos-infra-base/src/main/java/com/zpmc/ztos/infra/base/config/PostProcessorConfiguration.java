package com.zpmc.ztos.infra.base.config;

import com.zpmc.ztos.infra.base.event.ZpmcEventBus;
import com.zpmc.ztos.infra.base.utils.ZpmcBeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PostProcessorConfiguration {

    @Bean
    public ZpmcBeanPostProcessor zpmcBeanPostProcessor() {
        return new ZpmcBeanPostProcessor();
    }
}