package com.zpmc.ztos.infra.base.business.interfaces;

public interface IPropertyResolverRegistry {
    public static final String BEAN_ID = "argoPropertyResolverRegistry";

    public void put(Class var1, IPropertyResolver var2);

    public IPropertyResolver get(Class var1);

    public void remove(Class var1);
}
