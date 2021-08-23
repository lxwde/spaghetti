package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.business.model.MetafieldIdList;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.model.FieldChanges;
import com.zpmc.ztos.infra.base.common.model.ValueObject;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

public interface IEntity extends IEntityLegacy, IPrimaryKey, IValueSource, IEntityLifeCycle, IEntityNameAware{
    public void setPrimaryKey(Serializable var);

    @Nullable
    public IMetafieldId getPrimaryKeyMetaFieldId();

    @Nullable
    public String getHumanReadableKey();

    public Object getField(IMetafieldId var1);

    public Object[] extractFieldValueArray(MetafieldIdList var1, boolean var2);

    public ValueObject getValueObject(MetafieldIdList var1);

    public ValueObject getValueObject();

    public ValueObject getDefaultCreateValues(IDefaultCreateValuesContext var1);

    public void setFieldValue(IMetafieldId var1, Object var2);

    public void applyFieldChanges(FieldChanges var1);

    @Nullable
    public String getFieldString(IMetafieldId var1);

    @Nullable
    public Long getFieldLong(IMetafieldId var1);

    @Nullable
    public Date getFieldDate(IMetafieldId var1);

    @Deprecated
    public void setSelfAndFieldChange(IMetafieldId var1, Object var2, FieldChanges var3);

    @Nullable
    public BizViolation validateChanges(FieldChanges var1);

    @Nullable
    public BizViolation validateDeletion();

    public void populate(Set var1, FieldChanges var2);

    public void preProcessInsert(FieldChanges var1);

    public void preProcessUpdate(FieldChanges var1, FieldChanges var2);

    public void preProcessDelete(FieldChanges var1);

    public void preProcessInsertOrUpdate(FieldChanges var1);

}
