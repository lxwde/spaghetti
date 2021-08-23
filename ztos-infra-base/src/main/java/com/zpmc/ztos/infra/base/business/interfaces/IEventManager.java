package com.zpmc.ztos.infra.base.business.interfaces;

public interface IEventManager {

    public void addEventListener(IEventListener var1);

    public void removeEventListener(IEventListener var1);

    public void postEvent(IEvent var1);
}
