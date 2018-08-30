package com.lxwde.spaghetti.aspectj;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DummyScheduler {

    private static final Logger logger = LoggerFactory.getLogger(DummyScheduler.class);

    private int counter = 1;

    @Scheduled(fixedRate = 1000)
    public void doSomething() {
        logger.debug("doSomething. counter={}", counter++);
    }
}
