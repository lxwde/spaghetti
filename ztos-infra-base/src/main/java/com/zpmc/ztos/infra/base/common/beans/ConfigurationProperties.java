package com.zpmc.ztos.infra.base.common.beans;

import com.zpmc.ztos.infra.base.business.model.BaseConfigurationProperties;

public class ConfigurationProperties extends BaseConfigurationProperties {
    protected String _webServicesLocation;

    public String getWebServicesLocation() {
        return this._webServicesLocation;
    }

    public void setWebServicesLocation(String inWebServicesLocation) {
        this._webServicesLocation = inWebServicesLocation;
    }

    public String toString() {
        return "com.zpmc.ztos.infra.base.common.beans.ConfigurationProperties{webServicesLocation='" + this._webServicesLocation + "', _containerType='" + this._containerType + "'}";
    }

}
