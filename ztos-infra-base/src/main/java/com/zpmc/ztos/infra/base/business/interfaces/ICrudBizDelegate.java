package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.business.model.MetafieldIdList;
import com.zpmc.ztos.infra.base.common.model.*;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.io.Serializable;

public interface ICrudBizDelegate extends ICrudBizDelegateLegacy{
    public static final String BEAN_ID = "crudBizDelegate";

    public BizResponse requestCrudOperation(UserContext var1, CrudOperation var2);

    public BizResponse requestCreate(ITranslationContext var1, String var2, Serializable var3, FieldChanges var4);

    public BizResponse requestUpdate(ITranslationContext var1, String var2, Serializable var3, FieldChanges var4);

    public BizResponse requestPopulate(UserContext var1, String var2, FieldChanges var3);

    public BizResponse requestDelete(UserContext var1, String var2, Serializable var3);

    @Deprecated
    public BizResponse requestBulkDelete(String var1, Object[] var2);

    public BizResponse requestBulkDelete(UserContext var1, String var2, Object[] var3);

    public BizResponse requestEntityQuery(UserContext var1, String var2, Serializable var3, MetafieldIdList var4);

    public BizResponse processQuery(ITranslationContext var1, IExecutableQuery var2);

    public int processQueryCount(ITranslationContext var1, IDomainQuery var2);

    @Nullable
    public ValueObject retrieveValueObjectForEntity(ITranslationContext var1, String var2, Serializable var3, MetafieldIdList var4);

    public BizResponse requestQueryAll(String var1, String[] var2, IBounded var3, Ordering[] var4);

}
