package com.zpmc.ztos.infra.base.common.utils;

import com.zpmc.ztos.infra.base.business.enums.framework.MessageLevelEnum;
import com.zpmc.ztos.infra.base.business.interfaces.IMessageCollector;
import com.zpmc.ztos.infra.base.business.interfaces.IMetafieldUserMessage;
import com.zpmc.ztos.infra.base.business.interfaces.IPropertyKey;
import com.zpmc.ztos.infra.base.business.interfaces.IUserMessage;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.model.TransactionParms;
import org.apache.log4j.Logger;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class MessageCollectorUtils {

    private static final Logger LOGGER = Logger.getLogger(MessageCollectorUtils.class);

    private MessageCollectorUtils() {
    }

    @Nullable
    public static IMessageCollector getMessageCollector() {
        TransactionParms parms = TransactionParms.getBoundParms();
        IMessageCollector messageCollector = parms.getMessageCollector();
        if (messageCollector == null) {
            LOGGER.warn((Object)"getMessageCollector: No IMessageCollector associated with TransactionParms");
        }
        return messageCollector;
    }

    public static void appendMessage(MessageLevelEnum inMessageLevel, IPropertyKey inPropertyKey, Object inParam) {
        IMessageCollector collector = MessageCollectorUtils.getMessageCollector();
        if (collector != null) {
            collector.appendMessage(inMessageLevel, inPropertyKey, null, new Object[]{inParam});
        }
    }

    public static void appendMessage(MessageLevelEnum inMessageLevel, IPropertyKey inPropertyKey, Object inParam1, Object inParam2) {
        IMessageCollector collector = MessageCollectorUtils.getMessageCollector();
        if (collector != null) {
            collector.appendMessage(inMessageLevel, inPropertyKey, null, new Object[]{inParam1, inParam2});
        }
    }

    public static void appendMessage(MessageLevelEnum inMessageLevel, IPropertyKey inPropertyKey, Object inParam1, Object inParam2, Object inParam3) {
        IMessageCollector collector = MessageCollectorUtils.getMessageCollector();
        if (collector != null) {
            collector.appendMessage(inMessageLevel, inPropertyKey, null, new Object[]{inParam1, inParam2, inParam3});
        }
    }

    public static void appendExceptionChain(BizViolation inBizViolation) {
        IMessageCollector collector = MessageCollectorUtils.getMessageCollector();
        if (collector == null) {
            return;
        }
        Iterator it = inBizViolation.iterator();
        while (it.hasNext()) {
            BizViolation e = (BizViolation)it.next();
            collector.appendMessage(e.getSeverity(), e.getMessageKey(), null, e.getParms());
        }
    }

    public static void appendExceptionChainAsWarnings(BizViolation inBizViolation) {
        IMessageCollector collector = MessageCollectorUtils.getMessageCollector();
        if (collector == null) {
            return;
        }
        Iterator it = inBizViolation.iterator();
        while (it.hasNext()) {
            BizViolation e = (BizViolation)it.next();
            collector.appendMessage(MessageLevelEnum.WARNING, e.getMessageKey(), null, e.getParms());
        }
    }

    @Nullable
    public static String expandMessage(IPropertyKey inPropertyKey, Locale inLocale, Object[] inParams) {
//        IMessageTranslatorProvider translatorProvider = (IMessageTranslatorProvider)PortalApplicationContext.getBean("messageTranslatorProvider");
//        IMessageTranslator messageTranslator = translatorProvider.getMessageTranslator(inLocale);
//        return messageTranslator.getMessage(inPropertyKey, inParams);
        return null;
    }

    public static void appendMessagesToThread(IMessageCollector inMessageCollector) {
        IMessageCollector threadCollector = MessageCollectorUtils.getMessageCollector();
        if (inMessageCollector != null && threadCollector != null) {
            MessageCollectorUtils.appendMessages(threadCollector, inMessageCollector);
        }
    }

    public static void appendMessages(IMessageCollector inTargetMesageCollector, IMessageCollector inSourceMesageCollector) {
        if (inSourceMesageCollector == null) {
            LOGGER.error((Object)"appendMessages: Source message collector is null");
        }
        if (inTargetMesageCollector == null) {
            LOGGER.error((Object)"appendMessages: Target message collector is null");
        }
        for (Object msg : inSourceMesageCollector.getMessages()) {
            inTargetMesageCollector.appendMessage((IUserMessage)msg);
        }
    }

    public static void appendMessages(IMessageCollector inTargetMesageCollector, IMessageCollector inSourceMesageCollector, MessageLevelEnum inMessageLevel) {
        for (Object mf : inSourceMesageCollector.getMessages(inMessageLevel)) {
            inTargetMesageCollector.appendMessage((IMetafieldUserMessage)mf);
        }
    }

    public static List getUnchainedMessages(IMessageCollector inMessageCollector, MessageLevelEnum inMessageLevel) {
        if (inMessageCollector == null) {
            LOGGER.error((Object)"getUnchainedMessages: Parameter inMessageCollector is null");
        }
        List levelMessages = new ArrayList();
        ArrayList<IUserMessage> sortedMessages = new ArrayList<IUserMessage>();
        levelMessages = inMessageCollector.getMessages(inMessageLevel);
        for (Object message : levelMessages) {
            if (message instanceof BizViolation) {
                Iterator iterator = ((BizViolation)message).iterator();
                while (iterator.hasNext()) {
                    BizViolation violation = (BizViolation)iterator.next();
                    sortedMessages.add((IUserMessage)violation);
                }
                continue;
            }
            sortedMessages.add((IUserMessage)message);
        }
        return sortedMessages;
    }
}
