package com.zpmc.ztos.infra.business.controller;

import com.zpmc.ztos.infra.business.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketController.class);
    @MessageMapping("/messages")
    public void received(Message message) {
        logger.info("web socket message received: {}", message);
    }
}
