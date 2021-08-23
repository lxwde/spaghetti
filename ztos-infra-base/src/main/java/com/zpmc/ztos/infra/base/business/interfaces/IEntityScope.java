package com.zpmc.ztos.infra.base.business.interfaces;

import java.io.Serializable;

public interface IEntityScope {
    public IMetafieldId getScopeFieldId();

    public IScopeEnum getScopeLevel();

    public Serializable getScopeKey();
}
