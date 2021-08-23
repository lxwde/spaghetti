package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.common.model.UserContext;

import java.util.Locale;

public interface ITranslationContext {
    public UserContext getUserContext();

    public Locale getLocale();

    public IMessageTranslator getMessageTranslator();

    public boolean isMessageTranslatorAvailable();

    public IMetafieldDictionary getIMetafieldDictionary();

    public IFieldConverter getFieldConverter();

    public IDateConverter getDateConverter();
}
