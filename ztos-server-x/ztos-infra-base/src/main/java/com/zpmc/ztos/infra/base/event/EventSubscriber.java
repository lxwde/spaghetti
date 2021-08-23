package com.zpmc.ztos.infra.base.event;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface EventSubscriber {
    String value() default "T(com.zpmc.ztos.infra.base.event.ZpmcEventBus).getEventBus()";
    String[] eventIds = new String[]{};
}