package com.zpmc.ztos.infra.base.business.interfaces;

import com.sun.istack.Nullable;

public interface IValueSource {
    @Nullable
    public Object getFieldValue(IMetafieldId var1);
}
