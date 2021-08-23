package com.zpmc.ztos.infra.base.common.exceptions;

import com.zpmc.ztos.infra.base.business.enums.framework.MessageLevelEnum;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.util.Iterator;
import java.util.NoSuchElementException;


public class BizViolation extends ChainedException implements IMetafieldUserMessage {
    public static final String INVALID_FIELD_VALUE = IFrameworkPropertyKeys.CRUD__INVALID_FIELD_VALUE.getKey();
    public static final String DUPLICATE_NATURAL_KEY = IFrameworkPropertyKeys.CRUD__DUPLICATE_NATURAL_KEY.getKey();
    public static final String FIELD_REQUIRED = IFrameworkPropertyKeys.CRUD__FIELD_REQUIRED.getKey();
    public static final String FIELD_MUST_BE_NULL = IFrameworkPropertyKeys.CRUD__FIELD_MUST_BE_NULL.getKey();
    private IPropertyKey _errkey;
    private MessageLevelEnum _messageLevel = MessageLevelEnum.SEVERE;
    private Object[] _parms;
    private IMetafieldId _metafieldId;
    private BizViolation _nextViolation;

    public static BizViolation create(IPropertyKey inKey, BizViolation inExceptionChain) {
        return new BizViolation(inKey, null, inExceptionChain, null, new Object[0]);
    }

    public static BizViolation create(IPropertyKey inKey, BizViolation inExceptionChain, Object inParm1) {
        return new BizViolation(inKey, null, inExceptionChain, null, new Object[]{inParm1});
    }

    public static BizViolation create(IPropertyKey inKey, BizViolation inExceptionChain, Object inParm1, Object inParm2) {
        return new BizViolation(inKey, null, inExceptionChain, null, new Object[]{inParm1, inParm2});
    }

    public static BizViolation create(IPropertyKey inKey, BizViolation inExceptionChain, Object inParm1, Object inParm2, Object inParm3) {
        return new BizViolation(inKey, null, inExceptionChain, null, new Object[]{inParm1, inParm2, inParm3});
    }

    public static BizViolation create(IUserMessage inUserMessage) {
        return new BizViolation(inUserMessage.getMessageKey(), null, null, null, inUserMessage.getParms());
    }

    public static BizViolation createFieldViolation(IPropertyKey inKey, BizViolation inExceptionChain, IMetafieldId inMetafieldId) {
        return new BizViolation(inKey, null, inExceptionChain, inMetafieldId, new Object[0]);
    }

    public static BizViolation createFieldViolation(IPropertyKey inKey, BizViolation inExceptionChain, IMetafieldId inMetafieldId, Object inParm1) {
        return new BizViolation(inKey, null, inExceptionChain, inMetafieldId, new Object[]{inParm1});
    }

    public static BizViolation createFieldViolation(IPropertyKey inKey, BizViolation inExceptionChain, IMetafieldId inMetafieldId, Object inParm1, Object inParm2) {
        return new BizViolation(inKey, null, inExceptionChain, inMetafieldId, new Object[]{inParm1, inParm2});
    }

    public static BizViolation createFieldViolation(IPropertyKey inKey, BizViolation inExceptionChain, IMetafieldId inMetafieldId, Object inParm1, Object inParm2, Object inParm3) {
        return new BizViolation(inKey, null, inExceptionChain, inMetafieldId, new Object[]{inParm1, inParm2, inParm3});
    }

    public static BizViolation create(IPropertyKey inKey, Throwable inCause, BizViolation inExceptionChain, IMetafieldId inMetafieldId, Object[] inParms) {
        return new BizViolation(inKey, inCause, inExceptionChain, inMetafieldId, inParms);
    }

    public BizViolation(IPropertyKey inKey, Throwable inCause, BizViolation inExceptionChain, IMetafieldId inMetafieldId, Object[] inParms) {
        super(inKey.getKey(), inCause);
        this._errkey = inKey;
        this._nextViolation = inExceptionChain;
        this._metafieldId = inMetafieldId;
        if (inParms == null) {
            this._parms = new Object[0];
        } else {
            this._parms = new Object[inParms.length];
            for (int i = 0; i < inParms.length; ++i) {
                Object parm = inParms[i];
                this._parms[i] = parm instanceof IEntity ? parm.toString() : parm;
            }
        }
    }

    public static BizViolation appendViolations(@Nullable BizViolation inSourceViolation, @Nullable BizViolation inSecondViolation) {
        if (inSourceViolation == null) {
            return inSecondViolation;
        }
        if (inSecondViolation != null) {
            return inSourceViolation.appendToChain(inSecondViolation);
        }
        return inSourceViolation;
    }

    public BizViolation appendToChain(BizViolation inChain) {
        if (inChain == null) {
            return this;
        }
        BizViolation priorLink = inChain;
        for (BizViolation thisLink = inChain; thisLink != null; thisLink = thisLink.getNextViolation()) {
            priorLink = thisLink;
        }
        priorLink.setNextViolation(this);
        return inChain;
    }

    public void setNextViolation(BizViolation inViolation) {
        this._nextViolation = inViolation;
    }

    public BizViolation getNextViolation() {
        return this._nextViolation;
    }

    public Iterator iterator() {
        final BizViolation bv = this;
        Iterator it = new Iterator(){
            private BizViolation _currBv;
            {
                this._currBv = bv;
            }

            @Override
            public boolean hasNext() {
                return this._currBv != null;
            }

            public Object next() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                BizViolation myBv = this._currBv;
                this._currBv = this._currBv._nextViolation;
                return myBv;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
        return it;
    }

    @Override
    public String getResourceKey() {
        return this._errkey.getKey();
    }

    @Override
    public IPropertyKey getMessageKey() {
        return this._errkey;
    }

    @Override
    public Object[] getParms() {
        return this._parms;
    }

    @Override
    public IMetafieldId getMetafieldId() {
        return this._metafieldId;
    }

    @Override
    public void setMetafieldId(IMetafieldId inMetafieldId) {
        this._metafieldId = inMetafieldId;
    }

    @Override
    public String getMessage() {
        StringBuilder msg = new StringBuilder("key=" + this.getResourceKey());
        msg.append(" parms=[");
        if (this._parms != null) {
            for (int i = 0; i < this._parms.length; ++i) {
                if (i > 0) {
                    msg.append(":");
                }
                msg.append(this._parms[i]);
            }
        }
        msg.append("]");
        if (this.getMetafieldId() != null) {
            msg.append(",  offending field=").append(this.getMetafieldId().getFieldId());
        }
        return msg.toString();
    }

    @Override
    public String getLocalizedMessage() {
        Object[] parmsPlus;
//        IMessageTranslatorProvider translatorProvider = (IMessageTranslatorProvider)PortalApplicationContext.getBean("messageTranslatorProvider");
//        IMessageTranslator messageTranslator = translatorProvider.getMessageTranslator(Locale.ENGLISH);
//        if (this._metafieldId == null) {
//            parmsPlus = this._parms;
//        } else {
//            parmsPlus = new Object[this._parms.length + 1];
//            parmsPlus[0] = this._metafieldId;
//            System.arraycopy(this._parms, 0, parmsPlus, 1, this._parms.length);
//        }
//        StringBuilder msg = new StringBuilder(messageTranslator.getMessage(this._errkey, parmsPlus));
//        if (this.getMetafieldId() != null) {
//            msg.append(",  offending field: ").append(this.getMetafieldId().getFieldId());
//        }
//        return msg.toString();
        return null;
    }

    @Override
    public MessageLevelEnum getSeverity() {
        return this._messageLevel;
    }

    @Override
    public void setSeverity(MessageLevelEnum inSeverity) {
        this._messageLevel = inSeverity;
    }

    @Override
    public String toString() {
        return this.getMessage();
    }

}
