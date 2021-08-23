package com.zpmc.ztos.infra.base.business.interfaces;

import com.sun.istack.NotNull;

import java.io.Serializable;

public interface IFlexibleScope extends IScopeLevelAware {
    public Serializable getScopeKey();

    @NotNull
    public IMetafieldId getScopeKeyFieldId();

    @NotNull
    public IMetafieldId getScopeLevelFieldId();
}
