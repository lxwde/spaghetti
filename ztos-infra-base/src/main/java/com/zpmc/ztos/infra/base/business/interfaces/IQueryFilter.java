package com.zpmc.ztos.infra.base.business.interfaces;

import java.io.Serializable;

public interface IQueryFilter extends Serializable {

    public String getFilterDefinition();

    public String getFilterParameterName();

    public void setFilterParameterName(String var1);

    public Object getFilteredValue();

    public void setFilteredValue(Object var1);
}
