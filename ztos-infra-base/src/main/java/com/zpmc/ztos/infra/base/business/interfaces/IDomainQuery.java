package com.zpmc.ztos.infra.base.business.interfaces;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.business.model.MetafieldIdList;
import com.zpmc.ztos.infra.base.business.predicate.Join;
import com.zpmc.ztos.infra.base.common.model.KeyValuePair;
import com.zpmc.ztos.infra.base.common.model.Ordering;
import com.zpmc.ztos.infra.base.common.type.AggregateFunctionType;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.util.List;

public interface IDomainQuery extends IDataQuery, IMetaFilter, Cloneable {

    public static final int DEV_COMMENT_SIZE_LIMIT = 100;

    public IDomainQuery setDqMaxResults(int var1);

    public IDomainQuery setDqFirstResult(int var1);

    public IDomainQuery addDqOrdering(Ordering var1);

    public IDomainQuery addDqOrderings(Ordering[] var1);

    public IDomainQuery addDqPredicate(IPredicate var1);

    public IDomainQuery addDqField(IMetafieldId var1);

    public IDomainQuery addDqFields(MetafieldIdList var1);

    public IDomainQuery setDqFields(MetafieldIdList var1);

    public IDomainQuery clearDqFields();

    public IDomainQuery addDqAggregateField(AggregateFunctionType var1, IMetafieldId var2);

    public IDomainQuery addDqJoin(Join var1);

    public IDomainQuery addDqLeftOuterJoin(IMetafieldId var1);

    @Deprecated
    public IDomainQuery setDqFieldsDistinct();

    public IDomainQuery setDqFieldsDistinct(boolean var1);

    public boolean areFieldsDistinct();

    public String toHqlObjectQueryString(String var1);

    public String toHqlCountString(String var1);

    public String toHqlExistsString(String var1);

    public String toHqlRecapString(String var1);

    public String toHqlRecapSumString(String var1);

    public String toHqlSelectString(String var1);

    public boolean hasAggregateField();

    public KeyValuePair[] getFieldValues();

    public void replaceSelectedFields(MetafieldIdList var1);

    public boolean isSelectForUpdate();

    public IDomainQuery setSelectForUpdate(boolean var1);

    public IDomainQuery setSelectForUpdate(boolean var1, int var2);

    public int getSelectTimeOutSecs();

    @Deprecated
    public IDomainQuery addDqFields(String[] var1);

    @Deprecated
    public IDomainQuery addDqFields(List var1);

    @Deprecated
    public IDomainQuery addDqField(String var1);

    @Deprecated
    public IDomainQuery addDqAggregateField(AggregateFunctionType var1, String var2);

    @Deprecated
    public IDomainQuery addDqLeftOuterJoin(String var1);

    @Deprecated
    @Nullable
    public List getMetafields();

    @Deprecated
    public void replaceFields(List var1);

    public IDomainQuery setFullLeftOuterJoin(boolean var1);

    public IDomainQuery setFormatResultsForUI(boolean var1);

    public boolean isFormatResultsForUI();

    @Nullable
    public IPredicate getPredicatesAsSinglePredicate();

    public IQueryFilter getFilter();

    public void setFilter(IQueryFilter var1);

    public void setSecurityFilter(ISecurityFilter var1);

    public ISecurityFilter getSecurityFilter();

    public boolean getScopingEnabled();

    public void setScopingEnabled(boolean var1);

    public boolean isBypassInstanceSecurity();

    public void setBypassInstanceSecurity(boolean var1);

    public boolean isEnforceHorizonScoping();

    public void setEnforceHorizonScoping(boolean var1);

    public int getDqPredicatesSize();

    public void setDomainQueryTuner(IDomainQueryTuner var1);

    public IDomainQueryTuner getDomainQueryTuner();

    public boolean isDistinctReplacement();

    public void setDistinctReplacement(boolean var1);

    @Nullable
    public String getDevComment();

    public void appendDevComment(@NotNull String var1);
}
