package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.common.model.FieldChanges;

public interface IArgoTransactionLifeCycleInterceptor {
    public void doAdditionalAudit(int var1, IEntity var2, FieldChanges var3);
}
