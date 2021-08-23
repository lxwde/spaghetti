package com.zpmc.ztos.infra.base.business.interfaces;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import com.zpmc.ztos.infra.base.business.enums.framework.NodeStatusTypeEnum;

import java.util.Date;

public interface IServerContext {
    public static final String BEAN_ID = "serverContext";
    public static final String JVM_SHUTDOWN_PROPERTY = "navis.jvm.shutdown";
    public static final String JVM_SHUTDOWN_PROPERTY_ENABLED = "true";

    public void initialize(@NotNull IServerConfig var1);

    public void handleFatalError(Exception var1);

    public void destroy();

    @Nullable
    public IServerConfig getServerConfig();

    public NodeStatusTypeEnum getNodeStatus();

    public Date getStartOfInitialization();

    public boolean isServerActive();
}
