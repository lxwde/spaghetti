package com.zpmc.ztos.infra.base.business.interfaces;

import java.util.Map;

public interface ILovKey extends IELovKey{

    public static final String EMPTY_LOV_MESSAGE = "emptyLovMessage";

    public IQueryFilter getFilter();

    public void setFilter(IQueryFilter var1);

    public Map<Object, Object> getParameterMap();

    public void setParameterMap(Map<Object, Object> var1);

}
