package com.zpmc.ztos.infra.base.business.interfaces;

import java.util.TimeZone;

public interface IMessageTranslationContext {
    public IMessageTranslator getTranslator();

    public IMetafieldDictionary getMetafieldDictionary();

    public TimeZone getTimeZone();
}
