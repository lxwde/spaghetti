package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.common.database.HibernatingObjRef;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.io.Serializable;
import java.util.List;

public interface IBinCopyable {
    public void shallowCopy(Class var1, Serializable var2, long var3, IBinCopyable var5);

    public List<IMetafieldId> getKeyFields();

    public void addOwnedObjectKeysToListInCopyOrder(List<HibernatingObjRef> var1, IBinOwnedEntitiesFilter var2);

    @Nullable
    public IMetafieldId getTopBinField(long var1);

    public boolean hasNamingScheme();

}
