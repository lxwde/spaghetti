package com.zpmc.ztos.infra.base.business.interfaces;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.common.model.ScopeCoordinates;
import com.zpmc.ztos.infra.base.common.model.UserContext;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.io.Serializable;
import java.util.List;

public interface IEntityScoper {

    public static final String BEAN_ID = "entityScoper";

    public IScope getEntityScope(String var1, UserContext var2);

    public IDomainQuery enhanceDomainQuery(IDomainQuery var1, UserContext var2);
//
    public IDomainQuery createScopeNodeEntityLevelQuery(IScopeEnum var1, ScopeCoordinates var2);

    @NotNull
    public ScopeCoordinates getScopeCoordinates(IScopeEnum var1, Serializable var2);

    public ScopeCoordinates getScopeCoordinates(String[] var1);

    public IScopeEnum getScopeEnum(int var1);

    public IScopeEnum getScopeEnum(String var1);

    @NotNull
    public List<IScopeEnum> getScopeEnumList();

    @NotNull
    public List<IScopeEnum> getSupportedFlexibleScopeEnumList();

    @NotNull
    public IScopeEnum getMostSpecificSupportedScopeEnum();

    public String getScopeLevelName(int var1);

    public void clearScopeCache();

    @Nullable
    public ScopeCoordinates getEntityScopeCoordinates(String var1, Serializable var2);

    public String[] getUserScopeCoordsAsStringArray(UserContext var1);

    public IScopeNodeEntity getScopeNodeEntity(ScopeCoordinates var1);

    @Nullable
    public IPredicate getPredicateForBroadestAllowedScope(ScopeCoordinates var1, @NotNull IMetafieldId var2, @NotNull IMetafieldId var3);
}
