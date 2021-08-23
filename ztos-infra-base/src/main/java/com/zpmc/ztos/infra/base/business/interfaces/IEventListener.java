package com.zpmc.ztos.infra.base.business.interfaces;

public interface IEventListener {
    public Class getEventClass();

    public void onEvent(IEvent var1);

    public boolean isAsynchronous();
}
