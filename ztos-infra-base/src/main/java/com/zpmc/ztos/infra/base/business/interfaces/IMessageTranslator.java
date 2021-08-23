package com.zpmc.ztos.infra.base.business.interfaces;

import com.sun.istack.Nullable;

import java.util.Locale;

public interface IMessageTranslator {

    @Nullable
    public String getMessage(IPropertyKey var1);

    @Nullable
    public String getMessage(IPropertyKey var1, Object[] var2);

    public boolean isMessageAvailable(IPropertyKey var1);

    public Locale getLocale();
}
