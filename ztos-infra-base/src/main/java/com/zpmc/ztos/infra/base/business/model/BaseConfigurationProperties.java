package com.zpmc.ztos.infra.base.business.model;

import com.sun.istack.NotNull;
import org.apache.log4j.Logger;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.util.Collections;
import java.util.Map;

public class BaseConfigurationProperties {

    public static final String BEAN_ID = "ConfigurationProperties";
    protected String _connectionManagerJndiName = "ConnectionFactory";
    protected String _lovJmsJndiName = "topic/testTopic";
    protected boolean _isUsingJms;
    protected String _containerType;
    protected String _port = "8080";
    protected String _browserType = "iexplore";
    protected Map<String, String> _clusterPorts;
    private static final Logger LOGGER = Logger.getLogger(BaseConfigurationProperties.class);
    protected static final String JBOSS = "jboss";
    protected static final String TOMCAT = "tomcat";
    protected static final String WEBSPHERE = "websphere";
    protected static final String IEXPLORE = "iexplore";
    protected static final String FIREFOX = "firefox";
    protected static final String CHROME = "chrome";

    public String getContainerType() {
        return this._containerType;
    }

    public void setContainerType(String inContainerType) {
        this._containerType = inContainerType;
    }

    public String getConnectionManagerJndiName() {
        return this._connectionManagerJndiName;
    }

    public void setConnectionManagerJndiName(String inConnectionManagerJndiName) {
        this._connectionManagerJndiName = inConnectionManagerJndiName;
    }

    public String getLovJmsJndiName() {
        return this._lovJmsJndiName;
    }

    public void setLovJmsJndiName(String inLovJmsJndiName) {
        this._lovJmsJndiName = inLovJmsJndiName;
    }

    public void setUsingJms(boolean inUsingJms) {
        this._isUsingJms = inUsingJms;
    }

    public boolean isClustered() {
        return this._clusterPorts != null && !this._clusterPorts.isEmpty();
    }

    public String getPort() {
        if (this.isClustered()) {
            return this._clusterPorts.values().iterator().next();
        }
        return this._port;
    }

    public void setPort(String inPort) {
        this._port = inPort;
    }

    @Nullable
    public String getPort(@NotNull String inNodeName) {
        if (this.isClustered()) {
            return this._clusterPorts.get(inNodeName);
        }
        return null;
    }

    @Nullable
    public String getNode(@NotNull String inPort) {
        if (this.isClustered()) {
            for (Map.Entry<String, String> vals : this._clusterPorts.entrySet()) {
                if (!vals.getValue().equals(inPort)) continue;
                return vals.getKey();
            }
        }
        return null;
    }

    public Map<String, String> getClusterPorts() {
        return Collections.unmodifiableMap(this._clusterPorts);
    }

    public void setClusterPorts(Map<String, String> inClusterPorts) {
        this._clusterPorts = inClusterPorts;
    }

    public String getBrowserType() {
        return this._browserType;
    }

    public void setBrowserType(String inBrowserType) {
        if (inBrowserType != null && inBrowserType.trim() != null && !inBrowserType.trim().isEmpty() && !"${browser.type}".equals(inBrowserType.trim())) {
            LOGGER.info((Object)("Setting Browser Type to: " + inBrowserType));
            this._browserType = inBrowserType;
        }
    }

    public String toString() {
        return "com.navis.framework.util.BaseConfigurationProperties{_connectionManagerJndiName='" + this._connectionManagerJndiName + "', _lovJmsJndiName='" + this._lovJmsJndiName + "', _isUsingJms=" + this._isUsingJms + ", _port=" + this._port + ", _containerType='" + this._containerType + "'}";
    }
}
