package com.zpmc.ztos.infra.base.business.interfaces;

public interface IModuleVersionManager {
    public Integer getCurrentVersionNumber();

    public Integer getDbVersionNumber();

    public void setDbVersionNumber(Integer var1);

    public void setCurrentVersionNumber(Integer var1);

    public String getModuleName();

    public void setModuleName(String var1);

    public boolean isSchemaUpgradeRequired();
}
