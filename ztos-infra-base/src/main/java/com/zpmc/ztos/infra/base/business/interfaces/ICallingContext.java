package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.common.model.BizRequest;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

public interface ICallingContext extends IECallingContext {
    public void setPrimaryVariformId(String var1);

    public void setAttribute(String var1, Object var2);

    @Nullable
    public BizRequest getBizRequest();

    public void setBizRequest(BizRequest var1);
}
