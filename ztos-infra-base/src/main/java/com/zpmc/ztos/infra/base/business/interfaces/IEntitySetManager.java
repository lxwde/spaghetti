package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.common.scopes.Operator;

import java.io.Serializable;

public interface IEntitySetManager {
    public static final String BEAN_ID = "entitySetManager";

    public void initializeUpperLevelModules();

    public void ensureEntitySetsForOperator(Operator var1, boolean var2);

    public Serializable[] copyReferenceSet(String var1, Serializable var2);
}
