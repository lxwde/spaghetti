package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.sun.istack.Nullable;

import java.util.Date;
import java.util.Locale;

public interface IFieldValidator {
    public void validateString(IMetafieldId var1, String var2, IMessageTranslator var3) throws BizViolation;

    @Deprecated
    public void validateString(IMetafieldId var1, String var2) throws BizViolation;

    public void validateLong(IMetafieldId var1, Long var2) throws BizViolation;

    public void validateDouble(IMetafieldId var1, Double var2) throws BizViolation;

    public void validateDate(IMetafieldId var1, Date var2) throws BizViolation;

    @Deprecated
    @Nullable
    public Object validateField(IMetafieldId var1, Object var2) throws BizViolation;

    @Nullable
    public Object validateField(IMetafieldId var1, Object var2, IRequestContext var3) throws BizViolation;

    public void validateIsRequired(IMetafieldId var1) throws BizViolation;

    @Nullable
    public Double objectToDouble(IMetafieldId var1, Object var2, Locale var3) throws BizViolation;

    public Long objectToLong(IMetafieldId var1, Object var2, Locale var3) throws BizViolation;

    @Nullable
    public Date stringToDateTime(IMetafieldId var1, String var2, Locale var3) throws BizViolation;

    @Nullable
    public Date stringToDate(IMetafieldId var1, String var2, IRequestContext var3) throws BizViolation;

    @Nullable
    public Date objectToDate(IMetafieldId var1, Object var2, IRequestContext var3) throws BizViolation;

    @Nullable
    public Object stringToValue(IMetafieldId var1, String var2, IRequestContext var3) throws BizViolation;
}
