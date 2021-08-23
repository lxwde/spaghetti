package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.common.model.BizResponse;
import com.zpmc.ztos.infra.base.common.model.FieldChanges;
import com.zpmc.ztos.infra.base.common.model.UserContext;
import com.zpmc.ztos.infra.base.common.model.ValueObject;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.io.Serializable;
import java.util.List;

public interface ICrudBizDelegateLegacy {
    public BizResponse requestCreate(UserContext var1, String var2, Serializable var3, FieldChanges var4);

    public BizResponse requestUpdate(UserContext var1, String var2, Serializable var3, FieldChanges var4);

    @Deprecated
    @Nullable
    public ValueObject retrieveValueObjectForEntity(UserContext var1, String var2, Serializable var3, String[] var4);

    @Deprecated
    public BizResponse requestEntityQuery(UserContext var1, String var2, Serializable var3, String[] var4);

    @Deprecated
    public List retrieveValueObjectsForClass(UserContext var1, String var2, String[] var3);

    @Deprecated
    public List retrieveValueObjectsForClass(UserContext var1, String var2, String var3);
}
