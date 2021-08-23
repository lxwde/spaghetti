package com.zpmc.ztos.infra.base.event;

import com.google.common.eventbus.Subscribe;
import com.zpmc.ztos.infra.base.event.EventBase;
import com.zpmc.ztos.infra.base.event.EventSubscriber;

@EventSubscriber
public interface EventListener {

    @Subscribe
    void handleEvent(EventBase event);
}
