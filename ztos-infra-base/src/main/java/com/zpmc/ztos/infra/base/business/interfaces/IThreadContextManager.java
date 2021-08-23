package com.zpmc.ztos.infra.base.business.interfaces;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

public interface IThreadContextManager <T>{
    @Nullable
    public T getContext();

    public void setContext(@NotNull T var1);

    public void clearContext();
}
