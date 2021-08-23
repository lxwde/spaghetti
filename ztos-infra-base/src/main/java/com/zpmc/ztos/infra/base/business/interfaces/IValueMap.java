package com.zpmc.ztos.infra.base.business.interfaces;

public interface IValueMap extends IValueSource {

    public Iterable<IMetafieldId> getFields();

    public boolean isFieldPresent(IMetafieldId var1);

    public int getFieldCount();

}
