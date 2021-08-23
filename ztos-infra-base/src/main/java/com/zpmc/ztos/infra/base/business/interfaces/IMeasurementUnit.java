package com.zpmc.ztos.infra.base.business.interfaces;

public interface IMeasurementUnit {

    public String getAbbrevation();

    public String[] getAbbreviations();

    public IPropertyKey getUnitName();

    public boolean matches(String var1);

    public double getStandardUnits();

    public double getStandardUnitOffset();

}
