package com.zpmc.ztos.infra.base.event;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MQAPI {
    public static final String EVENT_QUEUE = "eventQueue";
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private EventManager eventManager;

    public void post(EventBase event) {
        rabbitTemplate.convertAndSend(EVENT_QUEUE, event);
    }

    @RabbitListener(queues = EVENT_QUEUE)
    public void handleEvent(EventBase event) {
        eventManager.handleEvent(event);
    }


}
