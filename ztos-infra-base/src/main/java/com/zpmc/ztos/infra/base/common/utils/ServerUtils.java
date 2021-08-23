package com.zpmc.ztos.infra.base.common.utils;

import com.sun.istack.Nullable;
import com.zpmc.ztos.infra.base.business.enums.framework.ServerModeEnum;
import com.zpmc.ztos.infra.base.business.interfaces.IServerConfig;
import com.zpmc.ztos.infra.base.business.interfaces.IServerContext;
import com.zpmc.ztos.infra.base.common.contexts.PortalApplicationContext;

public class ServerUtils {
    private ServerUtils() {
    }

    @Nullable
    public static ServerModeEnum getServerMode() {
        IServerContext application = (IServerContext) PortalApplicationContext.getBean("serverContext");
        IServerConfig serverConfig = application.getServerConfig();
        if (serverConfig != null) {
            return serverConfig.getServerMode();
        }
        return null;
    }
}
