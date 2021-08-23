package com.zpmc.ztos.infra.base.business.interfaces;

public interface IConfigurationProvider {
    public String getConfigValue(String var1);

    public String getConfigValue(IConfig var1);
}
