package com.zpmc.ztos.infra.base.business.interfaces;

import org.springframework.beans.factory.ListableBeanFactory;

import java.util.Collection;

public interface IUpgradeManager {
    public static final int OLD_SCHEME_CUTOFF_NUMBER = 9999;

    public Integer getCurrentVersionNumber();

    public void setCurrentVersionNumber(Integer var1);

    public Integer getDatabaseVersionNumber();

    public void setDatabaseVersionNumber(Integer var1);

    public boolean isEnabled();

    public void setEnabled(boolean var1);

    public void setInitial(boolean var1);

    public void upgradeToCurrent();

    public void init(ListableBeanFactory var1);

    public void init(Collection<IUpgradeAction> var1);

    public void setAfterRebuild(boolean var1);

    public boolean isSchemaUpgradeRequired();

    public void addModuleVersionManager(IModuleVersionManager var1);
}
