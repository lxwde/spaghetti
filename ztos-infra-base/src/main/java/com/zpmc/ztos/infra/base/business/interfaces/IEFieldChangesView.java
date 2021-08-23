package com.zpmc.ztos.infra.base.business.interfaces;

public interface IEFieldChangesView {
    public int getFieldChangeCount();

    public boolean hasFieldChange(IMetafieldId var1);

    public IEFieldChange findFieldChange(IMetafieldId var1);
}
