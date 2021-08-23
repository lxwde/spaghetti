package com.zpmc.ztos.infra.base.business.interfaces;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.business.enums.framework.ServerModeEnum;
import com.zpmc.ztos.infra.base.common.model.ApplicationModuleSettings;

public interface IServerConfig {
    @NotNull
    public String getNodeName();

    public String getHostAddress();

    public String getMacAddress();

    @NotNull
    public ServerModeEnum getServerMode();

    public ApplicationModuleSettings getApplicationModuleSettings();

    public boolean isPersistenceReadyForUse();

    public void setPersistenceReady(boolean var1);
}
