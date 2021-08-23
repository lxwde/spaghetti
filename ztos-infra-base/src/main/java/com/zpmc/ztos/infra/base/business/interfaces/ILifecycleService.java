package com.zpmc.ztos.infra.base.business.interfaces;

public interface ILifecycleService {
    public boolean abortOnInitError();

    public void initialize(IServerConfig var1);

    public boolean hasInitialized();

    public void setInitialized(boolean var1);

    public void dispose(IServerConfig var1);
}
