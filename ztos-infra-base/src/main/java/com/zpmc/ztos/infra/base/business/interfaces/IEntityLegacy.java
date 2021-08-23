package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.common.model.FieldChanges;
import com.zpmc.ztos.infra.base.common.model.ValueObject;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.util.Date;

public interface IEntityLegacy {
    @Deprecated
    public String getPrimaryKeyFieldId();

    @Deprecated
    public ValueObject getValueObject(String[] var1);

    @Deprecated
    @Nullable
    public Object getFieldValue(String var1);

    @Deprecated
    public void setFieldValue(String var1, Object var2);

    @Deprecated
    @Nullable
    public String getFieldString(String var1);

    @Deprecated
    @Nullable
    public Long getFieldLong(String var1);

    @Nullable
    public Date getFieldDate(String var1);

    @Deprecated
    public void setSelfAndFieldChange(String var1, Object var2, FieldChanges var3);
}
