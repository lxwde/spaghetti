package com.zpmc.ztos.infra.base.business.interfaces;

public interface IMeasured {

    public IMeasurementUnit getDataUnit();

    public IMetafieldId getDataUnitField();

    public IMeasurementUnit getUserUnit();

    public IMetafieldId getUserUnitField();
}
