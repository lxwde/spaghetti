package com.zpmc.ztos.infra.base.event;


import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;

@Component
public class ZpmcEventBus {
    @Autowired
    private EventManager eventManager;

    private static final String IDENTIFIER = "zpmc-event-bus";
    private final EventBus eventBus = new AsyncEventBus(IDENTIFIER, Executors.newCachedThreadPool());

    public EventBus getEventBus() {return eventBus;}

    @Autowired
    private MQAPI mqapi;

    private ZpmcEventBus() {}

    public void subscribe(EventListener obj) {
        eventBus.register(obj);
    }

    public void unsubscribe(EventListener obj) {
        eventBus.unregister(obj);
    }

    public void postInternal(EventBase event) {
        eventBus.post(event);
    }
    public void postMQ(EventBase event) {
        mqapi.post(event);
    }

    public void subscribeMQ(EventListener obj) {
        eventManager.register(obj);
    }

    public void unsubscribeMQ(EventListener obj) {
        eventManager.unregister(obj);
    }

    @Subscribe
    public void handleEvent(EventBase event) {
        eventManager.handleEvent(event);
    }
}
