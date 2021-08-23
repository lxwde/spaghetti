package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.business.model.MetafieldIdList;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

public interface IFieldSynthesizer {
    @Nullable
    public Object synthesizeFieldValue(IMetafieldId var1, MetafieldIdList var2, IValueSource var3);

}
