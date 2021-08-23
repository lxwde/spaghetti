package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.business.model.MetafieldIdList;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

public interface IMetafieldIndexer {
    public static final int METAFIELD_NOT_FOUND = -1;

    @Nullable
    public IMetafieldId getMetafieldIdForIndex(int var1);

    public int getIndexForMetafieldId(IMetafieldId var1);

    public MetafieldIdList getFieldList();

    @Nullable
    public IMetafieldId getPrimaryKeyField();

}
