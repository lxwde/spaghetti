package com.zpmc.ztos.infra.base.common.messages;

import com.zpmc.ztos.infra.base.business.interfaces.IErrorOverrides;
import com.zpmc.ztos.infra.base.business.interfaces.IMessageCollector;

public class MessageCollectorFactory {
    private MessageCollectorFactory() {
    }

    public static IMessageCollector createMessageCollector() {
        return new MessageCollectorImpl();
    }

    public static IMessageCollector createMessageCollector(IErrorOverrides inErrorOverrides) {
        return new MessageCollectorImpl(inErrorOverrides);
    }

    public static IErrorOverrides createErrorOverrides(boolean inIgnoreAllOverrideableErrors) {
//        return new DefaultErrorOverrides(inIgnoreAllOverrideableErrors);
        return null;
    }
}
