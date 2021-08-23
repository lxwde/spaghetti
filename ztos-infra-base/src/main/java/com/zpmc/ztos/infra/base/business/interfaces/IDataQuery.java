package com.zpmc.ztos.infra.base.business.interfaces;

import com.sun.istack.Nullable;
import com.zpmc.ztos.infra.base.business.model.MetafieldIdList;
import com.zpmc.ztos.infra.base.common.model.Ordering;
//import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.io.Serializable;
import java.util.List;

public interface IDataQuery extends Serializable, IExecutableQuery {

    @Nullable
    public IDataQuery createEnhanceableClone();

    @Nullable
    public IDataQuery createEnhanceableCloneWithoutPredicates();

    public void setMaxResults(int var1);

    public void setFirstResult(int var1);

    @Deprecated
    public void setOrderings(Ordering[] var1);

    public void setOrderings(List var1);

    @Deprecated
    public void addFields(String[] var1);

    public void addMetafieldIds(MetafieldIdList var1);

    public void setMetafieldIds(MetafieldIdList var1);

    @Deprecated
    public String[] getFields();

    public void setQueryEntityName(String var1);

    public boolean isTotalCountRequired();

    public void setRequireTotalCount(boolean var1);
}
