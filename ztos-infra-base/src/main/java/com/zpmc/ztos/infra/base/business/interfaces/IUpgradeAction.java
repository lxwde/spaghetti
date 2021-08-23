package com.zpmc.ztos.infra.base.business.interfaces;

import com.sun.istack.Nullable;

public interface IUpgradeAction {
    public Integer getBuildNumber();

    public String getDescription();

    @Nullable
    public IMessageCollector upgradeTask();

    public void setAlwaysRun(boolean var1);

    public boolean alwaysRun();

    public void setInitialAction(boolean var1);

    public boolean isInitialAction();

    public String getModuleName();

    public void setModuleName(String var1);
}
