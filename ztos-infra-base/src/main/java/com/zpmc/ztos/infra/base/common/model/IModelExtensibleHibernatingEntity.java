package com.zpmc.ztos.infra.base.common.model;

import com.sun.istack.Nullable;

import java.util.Map;

public interface IModelExtensibleHibernatingEntity {
    public boolean isModelExtensible();

    @Nullable
    public Map getCustomFlexFields();

    public void setCustomFlexFields(Map var1);
}
