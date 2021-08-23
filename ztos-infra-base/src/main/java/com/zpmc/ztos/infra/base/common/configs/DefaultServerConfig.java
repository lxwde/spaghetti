package com.zpmc.ztos.infra.base.common.configs;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.business.enums.framework.ServerModeEnum;
import com.zpmc.ztos.infra.base.business.interfaces.IServerConfig;
import com.zpmc.ztos.infra.base.common.contexts.PortalApplicationContext;
import com.zpmc.ztos.infra.base.common.model.ApplicationModuleSettings;
import com.zpmc.ztos.infra.base.common.utils.ClusterNodeUtils;
import org.apache.log4j.Logger;

public class DefaultServerConfig implements IServerConfig {
    boolean _persistenceReady;
    ServerModeEnum _serverMode;
    ApplicationModuleSettings _moduleSettings;
    String _nodeName;
    String _ipAddress;
    String _macAddress;
    private static final Logger LOGGER = Logger.getLogger(DefaultServerConfig.class);

    public DefaultServerConfig(ServerModeEnum inServerMode) {
        this._serverMode = inServerMode;
        this._nodeName = ClusterNodeUtils.getUniqueNodeName();
        this._ipAddress = ClusterNodeUtils.getHostAddress();
        this._macAddress = ClusterNodeUtils.getMacAddress();
    }

    @Override
    @NotNull
    public String getNodeName() {
        return this._nodeName;
    }

    @Override
    public String getHostAddress() {
        return this._ipAddress;
    }

    @Override
    public String getMacAddress() {
        return this._macAddress;
    }

    @Override
    @NotNull
    public ServerModeEnum getServerMode() {
        return this._serverMode;
    }

    @Override
    public ApplicationModuleSettings getApplicationModuleSettings() {
        if (this._moduleSettings == null) {
            this._moduleSettings = (ApplicationModuleSettings) PortalApplicationContext.getBean("appModuleSettings");
        }
        return this._moduleSettings;
    }

    @Override
    public boolean isPersistenceReadyForUse() {
        return this._persistenceReady;
    }

    @Override
    public void setPersistenceReady(boolean inPersistenceReady) {
        this._persistenceReady = inPersistenceReady;
    }

}
