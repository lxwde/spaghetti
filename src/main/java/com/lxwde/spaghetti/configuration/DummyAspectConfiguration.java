package com.lxwde.spaghetti.configuration;

import com.lxwde.spaghetti.aspectj.DummyAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class DummyAspectConfiguration {

    @Bean
    public DummyAspect loggingAspect() {
        return new DummyAspect();
    }
}