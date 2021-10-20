package com.zpmc.ztos.infra.base.helper;

import java.util.UUID;

public abstract class ZpmcAbstractRunnable implements ZpmcRunnable{
    private String taskId;
    @Override
    public void beforeExecute() {
        // TODO: create timer event
        registerRabbitQMEvents();
    }

    public ZpmcAbstractRunnable(String taskId) {
        this.taskId = taskId;
    }

    public abstract void registerRabbitQMEvents();

    @Override
    public void execute() {
        // TODO: wait for a rabbitmq event or timer event
        // TODO: add a stop event
        while (true) {
            process();
        }
    }

    public abstract void process();

    @Override
    public void afterExecute() {
        // TODO: stop timer
        // TODO: post a rabbit mq event, define a new Event
    }
}
