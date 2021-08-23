package com.zpmc.ztos.infra.base.business.interfaces;

import com.sun.istack.NotNull;

public interface ICallingContextManager extends IThreadContextManager<ICallingContext> {
    public static final String BEAN_ID = "callingContextManager";

    @Override
    public ICallingContext getContext();

    @Deprecated
    public ICallingContext getCallingContext();

    @Override
    public void setContext(@NotNull ICallingContext var1);

    @Override
    public void clearContext();
}
