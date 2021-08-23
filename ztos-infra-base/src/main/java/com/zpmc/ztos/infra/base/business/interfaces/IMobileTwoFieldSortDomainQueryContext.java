package com.zpmc.ztos.infra.base.business.interfaces;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.util.List;

public interface IMobileTwoFieldSortDomainQueryContext {
    public void onChangeFieldsBeingSorted(IMetafieldId var1, boolean var2);

    public String getVariformId();

    @Nullable
    public IMetafieldId getMobileFirstSortField();

    @Nullable
    public IMetafieldId getEntityFirstSortField();

    public boolean isFirstSortAscendingOrder();

    @Nullable
    public IMetafieldId getMobileSecondSortField();

    @Nullable
    public IMetafieldId getEntitySecondSortField();

    public boolean isSecondSortAscendingOrder();

    @NotNull
    public List<IMetafieldId> getAdditionalFieldsToRetrieve();

    public void setDomainQueryResultLastExecuted(@Nullable IQueryResult var1);

    @Nullable
    public IQueryResult getDomainQueryResultLastExecuted();
}
