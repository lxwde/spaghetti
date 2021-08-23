package com.zpmc.ztos.infra.base.business.interfaces;

import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.util.List;
import java.util.Map;

public interface ICarinaIndexFactory {
    public ICarinaIndex getCarinaIndexObject(Map<Object, Object> var1);

    public ICarinaIndex getCarinaIndexObject(String var1, List<String> var2, @Nullable String var3);
}
