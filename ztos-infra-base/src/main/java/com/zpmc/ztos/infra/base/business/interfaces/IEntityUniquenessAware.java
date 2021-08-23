package com.zpmc.ztos.infra.base.business.interfaces;

public interface IEntityUniquenessAware {
    public IMetafieldId[] getUniqueKeyFieldIds();

    public IMetafieldId getPrimaryBusinessKey();
}
