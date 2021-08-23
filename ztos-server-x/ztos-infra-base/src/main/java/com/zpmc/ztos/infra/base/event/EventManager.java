package com.zpmc.ztos.infra.base.event;

import com.google.common.eventbus.Subscribe;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class EventManager {
    private Set<EventListener> eventListeners = Collections.newSetFromMap(new ConcurrentHashMap<>());

    public void register(EventListener eventListener) {
        eventListeners.add(eventListener);
    }

    public void unregister(EventListener eventListener) {
        eventListeners.remove(eventListener);
    }

    @Subscribe
    public void handleEvent(EventBase event) {
        for (EventListener eventListener : eventListeners) {
            eventListener.handleEvent(event);
        }
    }
}
