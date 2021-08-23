package com.zpmc.ztos.infra.base.business.interfaces;

import com.sun.istack.NotNull;

import java.util.Map;

public interface IExecutedDDL {
    public String getExecutedDDL();

    @NotNull
    public Map<String, Throwable> getExceptions();

    public boolean hasExceptions();
}
