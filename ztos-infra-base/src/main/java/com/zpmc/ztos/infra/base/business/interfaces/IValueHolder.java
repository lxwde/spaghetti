package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.business.model.MetafieldIdList;
import com.sun.istack.Nullable;

import java.io.Serializable;

public interface IValueHolder extends IValueMap {
    public MetafieldIdList getFields();

    public Object getFieldValue(IMetafieldId var1);

    @Nullable
    public Serializable getEntityPrimaryKey();

    @Nullable
    public IMetafieldId getPrimaryKeyField();
}
