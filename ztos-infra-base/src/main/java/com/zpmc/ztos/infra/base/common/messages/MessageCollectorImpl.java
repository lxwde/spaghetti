package com.zpmc.ztos.infra.base.common.messages;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.business.enums.framework.MessageLevelEnum;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.common.exceptions.BizFailure;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.helps.DatabaseHelper;
import com.zpmc.ztos.infra.base.common.model.PropertyKeyFactory;
import com.zpmc.ztos.infra.base.common.model.Roastery;
import com.zpmc.ztos.infra.base.common.model.UserContext;
import com.zpmc.ztos.infra.base.common.utils.TranslationUtils;
import com.zpmc.ztos.infra.base.utils.CarinaUtils;
import org.apache.log4j.Logger;
import org.hibernate.ObjectNotFoundException;

import java.io.Serializable;
import java.sql.BatchUpdateException;
import java.sql.SQLException;
import java.util.*;

public class MessageCollectorImpl implements IMessageCollector, Serializable {

    private static final String INDENT_1 = "\n   ";
    private List<IUserMessage> _messages;
    private IErrorOverrides _errorOverrides;
    private Map<String, Object> _attributes;
    private static final Logger LOGGER = Logger.getLogger(MessageCollectorImpl.class);
    private final DatabaseHelper _dbHelper = (DatabaseHelper) Roastery.getBean("dbHelper");
    private static final int MAX_MESSAGE_COUNT = 500;

    @Deprecated
    public MessageCollectorImpl() {
        this._messages = new ArrayList<IUserMessage>();
    }

    public MessageCollectorImpl(IErrorOverrides inErrorOverrides) {
        this();
        this._errorOverrides = inErrorOverrides;
    }

    @Override
    public void registerExceptions(Throwable inExceptionChain) {
        if (inExceptionChain == null) {
            LOGGER.error((Object)"null exception passed to registerExceptions (?)");
            return;
        }
        Throwable rootCause = CarinaUtils.unwrap(inExceptionChain);
        if (rootCause == null) {
            LOGGER.error((Object)"empty rootCause returned from CarinaUtils.");
            return;
        }
        if (rootCause instanceof BizViolation) {
            BizViolation bv = (BizViolation)rootCause;
            Iterator it = bv.iterator();
            while (it.hasNext()) {
 //               this.appendMessage(new MetafieldUserMessageImp((BizViolation)it.next()));
            }
        } else if (this._dbHelper.isUniqueConstraintViolation(rootCause)) {
            String exceptionMsg = inExceptionChain.getMessage() + "\n" + rootCause.getMessage();
            BizFailure bizFailure = BizFailure.create(IFrameworkPropertyKeys.UNIQUE_CONSTRAINT_VIOLATION, rootCause);
            this.appendMessage(bizFailure);
            LOGGER.error((Object)("UNCAUGHT EXCEPTION: " + rootCause.getClass() + ": " + exceptionMsg), rootCause);
        } else if (rootCause instanceof BizFailure) {
//            BizFailure bf = (BizFailure)rootCause;
//            this.appendMessage(new MetafieldUserMessageImp(bf));
        }
//        else if (rootCause instanceof MessageCollectorWrapperException)
//        {
//            MessageCollectorWrapperException mcwe = (MessageCollectorWrapperException)rootCause;
//            IMessageCollector mc = mcwe.getMessages();
//            List<IUserMessage> existingMsgs = this.getMessages();
//            for (Object o : mc.getMessages()) {
//                IUserMessage um = (IUserMessage)o;
//                if (existingMsgs.contains(um)) continue;
//                this.appendMessage(um);
//            }
//        }
        else if (rootCause instanceof SQLException && rootCause.getMessage().contains("deadlock")) {
            String exceptionMsg = inExceptionChain.getMessage() + "\n" + rootCause.getMessage();
            BizFailure bizFailure = BizFailure.create(IFrameworkPropertyKeys.DEADLOCK__OCCURRED, null);
            this.appendMessage(bizFailure);
            LOGGER.error((Object)("UNCAUGHT EXCEPTION: " + rootCause.getClass() + ": " + exceptionMsg), rootCause);
        } else if (rootCause instanceof ObjectNotFoundException) {
            String exceptionMsg = inExceptionChain.getMessage() + "\n" + rootCause.getMessage();
            BizFailure bizFailure = BizFailure.create(IFrameworkPropertyKeys.FAILURE__OBJECT_NOT_FOUND, null);
            this.appendMessage(bizFailure);
            LOGGER.error((Object)("UNCAUGHT EXCEPTION: " + rootCause.getClass() + ": " + exceptionMsg), rootCause);
        } else if (this._dbHelper.isDeletionConstraintViolation(rootCause)) {
            String exceptionMsg = inExceptionChain.getMessage() + "\n" + rootCause.getMessage();
            if (rootCause instanceof BatchUpdateException && ((BatchUpdateException)rootCause).getNextException() != null) {
                exceptionMsg = exceptionMsg + "\n" + ((BatchUpdateException)rootCause).getNextException();
            }
            BizFailure bizFailure = BizFailure.create(IFrameworkPropertyKeys.CONSTRAINT_VIOLATION_AT_DELETION, rootCause);
            this.appendMessage(bizFailure);
            LOGGER.error((Object)("UNCAUGHT EXCEPTION: " + rootCause.getClass() + ": " + exceptionMsg), rootCause);
        } else {
            String exceptionMsg = rootCause.getMessage() + "\n" + CarinaUtils.getCompactStackTrace(rootCause);
            BizFailure bizFailure = BizFailure.create(IFrameworkPropertyKeys.FAILURE__SYSTEM, rootCause, rootCause.getClass().toString(), exceptionMsg);
            this.appendMessage(bizFailure);
            LOGGER.error((Object)("UNCAUGHT EXCEPTION: " + rootCause.getClass() + ": " + exceptionMsg), rootCause);
        }
    }

    @Override
    public void appendMessage(IUserMessage inUserMessage) {
        if (this._messages.size() > 500) {
            LOGGER.error((Object)"Too many errors for messsage collector.");
            return;
        }
        this._messages.add(inUserMessage);
    }

    @Override
    public void appendMessage(MessageLevelEnum inSeverity, IPropertyKey inKey, String inDefaultMessage, Object[] inParms) {
//        MetafieldUserMessageImp msg = new MetafieldUserMessageImp(inKey, null, inParms);
//        msg.setDefaultMessage(inDefaultMessage);
//        msg.setSeverity(inSeverity);
//        this.appendMessage(msg);
    }

    public void appendMessage(MessageLevelEnum inSeverity, String inKey, String inDefaultMessage, String[] inParms) {
        this.appendMessage(inSeverity, PropertyKeyFactory.valueOf(inKey), inDefaultMessage, (Object[])inParms);
    }

    @Override
    public List<IUserMessage> getMessages() {
        ArrayList<IUserMessage> msgs = new ArrayList<IUserMessage>();
        if (!this._messages.isEmpty()) {
            msgs.addAll(this._messages);
        }
        return msgs;
    }

    @Override
    public int getMessageCount() {
        return this._messages.size();
    }

    @Override
    public int getMessageCount(MessageLevelEnum inSeverity) {
        return this.getMessages(inSeverity).size();
    }

    @Override
    public boolean containsMessage() {
        return !this._messages.isEmpty();
    }

    @Override
    public boolean containsMessage(IPropertyKey inMessageKey) {
        for (IUserMessage um : this._messages) {
            if (!um.getMessageKey().equals(inMessageKey)) continue;
            return true;
        }
        return false;
    }

    @Override
    public boolean hasError() {
        return this.containsMessageLevel(MessageLevelEnum.SEVERE);
    }

    @Override
    public List getMessages(MessageLevelEnum inSeverity) {
        ArrayList<IUserMessage> msgs = new ArrayList<IUserMessage>();
        if (inSeverity == null) {
            return this.getMessages();
        }
        for (IUserMessage um : this._messages) {
            if (um.getSeverity() != inSeverity || this.shouldOverrideError(um)) continue;
            msgs.add(um);
        }
        return msgs;
    }

    @Override
    public boolean containsMessageLevel(MessageLevelEnum inSeverity) {
        for (IUserMessage um : this._messages) {
            if (um.getSeverity() != inSeverity || this.shouldOverrideError(um)) continue;
            return true;
        }
        return false;
    }

    private boolean shouldOverrideError(@NotNull IUserMessage inMessage) {
        return this._errorOverrides != null && this._errorOverrides.shouldOverrideError(inMessage);
    }

    @Override
    @NotNull
    public Collection<IUserMessage> getOverriddenErrors() {
        ArrayList<IUserMessage> errs = new ArrayList<IUserMessage>();
        if (this._errorOverrides != null && this._errorOverrides.ignoreAllOverrideableErrors()) {
            for (IUserMessage um : this._messages) {
                if (!this.shouldOverrideError(um)) continue;
                errs.add(um);
            }
        }
        return errs;
    }

    @Override
    public Collection<IUserMessage> getOverrideableErrors() {
        ArrayList<IUserMessage> errs = new ArrayList<IUserMessage>();
        if (this._errorOverrides != null) {
            for (IUserMessage um : this._messages) {
                if (!this._errorOverrides.canOverrideError(um)) continue;
                errs.add(um);
            }
        }
        return errs;
    }

    @Override
    public IErrorOverrides getErrorOverrides() {
        return this._errorOverrides;
    }

    @Override
    public Map<String, Object> getAttributes() {
        if (this._attributes == null) {
            return Collections.emptyMap();
        }
        return Collections.unmodifiableMap(this._attributes);
    }

    @Override
    public void setAttributes(Map<String, Object> inMap) {
        if (this._attributes == null) {
            this._attributes = new HashMap<String, Object>();
        }
        this._attributes.putAll(inMap);
    }

    @Override
    public Object getAttribute(String inKey) {
        if (this._attributes != null) {
            return this._attributes.get(inKey);
        }
        return null;
    }

    @Override
    public void setAttribute(String inKey, Object inValue) {
        if (this._attributes == null) {
            this._attributes = new HashMap<String, Object>();
        }
        this._attributes.put(inKey, inValue);
    }

    @Override
    public String toLoggableString(@NotNull UserContext inUc) {
        List<IUserMessage> messages = this.getMessages();
        StringBuilder sb = new StringBuilder().append("IMessageCollector has ").append(this.getMessageCount()).append(" message(s)");
        for (IUserMessage message : messages) {
            String translatedMessage = "";
            if (message.getMessageKey() != null) {
                IMessageTranslator messageTranslator = TranslationUtils.getTranslationContext(inUc).getMessageTranslator();
                translatedMessage = messageTranslator.getMessage(message.getMessageKey(), message.getParms());
            } else {
                translatedMessage = message.toString();
            }
            sb.append(INDENT_1).append(message.getSeverity().getName()).append(":").append(translatedMessage).append("\n");
            if (!(message instanceof Throwable)) continue;
            String stackTrace = CarinaUtils.getStackTrace((Throwable)((Object)message));
            sb.append(stackTrace).append("\n");
        }
        return sb.toString();
    }

    @Override
    public String toCompactString() {
        return null;
    }

    public String toString() {
        StringBuilder strBuf = null;
        try {
            strBuf = new StringBuilder().append("IMessageCollector has ").append(this.getMessageCount()).append(" message(s)");
            if (this._errorOverrides == null) {
                for (IUserMessage um : this.getMessages()) {
                    strBuf.append(INDENT_1).append(um.getSeverity().getName()).append(":").append(um);
                }
            } else {
                for (IUserMessage um : this.getMessages()) {
                    strBuf.append(INDENT_1);
                    if (this.shouldOverrideError(um)) {
                        strBuf.append("OVERRIDDEN: ");
                    } else if (this._errorOverrides.canOverrideError(um)) {
                        strBuf.append("CAN OVERRIDE: ");
                    } else {
                        strBuf.append(um.getSeverity().getName()).append(": ");
                    }
                    strBuf.append(um);
                }
            }
        }
        catch (Throwable inE) {
            LOGGER.error((Object)("toString: Could not translate the message " + inE));
        }
        return strBuf.toString();
    }

}
