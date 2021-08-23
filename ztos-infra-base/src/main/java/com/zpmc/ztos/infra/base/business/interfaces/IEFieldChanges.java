package com.zpmc.ztos.infra.base.business.interfaces;

public interface IEFieldChanges {
    public void setFieldChange(IMetafieldId var1, Object var2);

    public int getFieldChangeCount();

    public boolean hasFieldChange(IMetafieldId var1);

    public IEFieldChange findFieldChange(IMetafieldId var1);
}
