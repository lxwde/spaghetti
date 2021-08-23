package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.common.model.UserContext;

import java.util.Locale;

public interface IMessageTranslatorProvider {
    public static final String BEAN_ID = "messageTranslatorProvider";

    public IMessageTranslator getMessageTranslator(Locale var1);

    public IMessageTranslator createMessageTranslator(Locale var1, UserContext var2);

}
