package com.zpmc.ztos.infra.base.business.interfaces;

import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

public interface IEFieldChange {

    @Nullable
    public IMetafieldId getMetafieldId();

    @Nullable
    public Object getPriorValue();

    public Object getNewValue();

    public boolean isPriorValueKnown();
}
