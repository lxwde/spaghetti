package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.business.model.MetafieldIdList;
import com.sun.istack.Nullable;

import java.util.List;

public interface IBasicQuery {
    @Deprecated
    @Nullable
    public List getDefaultDisplayFields();

    public MetafieldIdList getMetafieldIds();

    @Nullable
    public IQueryCriteria getQueryCriteria();
}
