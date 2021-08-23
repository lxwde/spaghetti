package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.business.enums.core.SessionTypeEnum;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

public interface IECallingContext {
    public SessionTypeEnum getSessionType();

    @Nullable
    public String getPrimaryVariformId();

    public Object getAttribute(String var1);
}
