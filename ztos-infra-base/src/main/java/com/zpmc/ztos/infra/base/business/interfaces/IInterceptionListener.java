package com.zpmc.ztos.infra.base.business.interfaces;

import com.sun.istack.NotNull;
import org.hibernate.Interceptor;

public interface IInterceptionListener {
    public static final String BEAN_ID = "interceptionListener";

    public void interceptionStarted();

    public void interceptionCompleted();

    public boolean setCurrentSessionInterceptor(@NotNull Interceptor var1);

    @NotNull
    public Interceptor getNextSessionInterceptor();

    public boolean isMaximumInterceptionDepthHit();

    public int getInterceptionDepth();
}
