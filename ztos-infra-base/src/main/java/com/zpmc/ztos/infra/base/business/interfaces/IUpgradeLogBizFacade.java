package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.common.model.UserContext;

import java.util.Collection;

public interface IUpgradeLogBizFacade {
    public static final String BEAN_ID = "upgradeLogBizFacade";

    public void logStateFailure(UserContext var1, IMessageCollector var2);

    public void logStateSuccess(UserContext var1, IMessageCollector var2, String var3);

    public void lockForUpgrade(UserContext var1, IMessageCollector var2);

    public void logEvents(UserContext var1, IMessageCollector var2, Collection var3);

    public void logSchemaUpgrade(UserContext var1, IMessageCollector var2, IUpgradeManager var3);
}
