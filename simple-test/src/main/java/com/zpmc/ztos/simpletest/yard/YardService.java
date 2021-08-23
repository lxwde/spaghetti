package com.zpmc.ztos.simpletest.yard;

import com.zpmc.ztos.infra.base.event.EventBase;
import com.zpmc.ztos.infra.base.event.EventListener;
import com.zpmc.ztos.infra.base.event.EventSubscriber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@EventSubscriber
public class YardService  implements EventListener {
    private static final Logger logger = LoggerFactory.getLogger(YardService.class);

    @Override
    public void handleEvent(EventBase eventBase) {
        logger.info("event received: {}", eventBase);
    }
}
