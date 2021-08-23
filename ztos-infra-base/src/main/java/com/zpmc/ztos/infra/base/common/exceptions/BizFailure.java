package com.zpmc.ztos.infra.base.common.exceptions;

import com.zpmc.ztos.infra.base.business.enums.framework.MessageLevelEnum;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.common.contexts.PortalApplicationContext;
import com.zpmc.ztos.infra.base.utils.CarinaUtils;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;
import org.springframework.util.StringUtils;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Locale;
import java.util.logging.Logger;

public class BizFailure extends ChainedRuntimeException
        implements IMetafieldUserMessage {

    private IMetafieldId _metafieldId;
    private Object[] _parms;
    private String _stackTrace;
    private final IPropertyKey _errkey;
    private static final Logger LOGGER = Logger.getLogger(BizFailure.class.getName());

    public static BizFailure wrap(Throwable inCause) {
        return new BizFailure(IFrameworkPropertyKeys.FAILURE__SYSTEM, inCause, new Object[0]);
    }

    public static BizFailure createProgrammingFailure(String inHardCodedMessage) {
        return new BizFailure(IFrameworkPropertyKeys.ERROR__NULL_MESSAGE, null, new Object[]{inHardCodedMessage});
    }

    public static BizFailure createProgrammingFailure(String inHardCodedMessage, Exception inException) {
        return new BizFailure(IFrameworkPropertyKeys.ERROR__NULL_MESSAGE, inException, new Object[]{inHardCodedMessage});
    }

    public static BizFailure create(String inMessage) {
        return new BizFailure(IFrameworkPropertyKeys.FAILURE__NAVIS, null, new Object[]{inMessage});
    }

    public static BizFailure create(IPropertyKey inKey, @Nullable Throwable inCause) {
        return new BizFailure(inKey, inCause, new Object[0]);
    }

    public static BizFailure create(IPropertyKey inKey, @Nullable Throwable inCause, Object inParm1) {
        return new BizFailure(inKey, inCause, new Object[]{inParm1});
    }

    public static BizFailure create(IPropertyKey inKey, @Nullable Throwable inCause, Object inParm1, Object inParm2) {
        return new BizFailure(inKey, inCause, new Object[]{inParm1, inParm2});
    }

    public static BizFailure create(IPropertyKey inKey, @Nullable Throwable inCause, Object inParm1, Object inParm2, Object inParm3) {
        return new BizFailure(inKey, inCause, new Object[]{inParm1, inParm2, inParm3});
    }

    public BizFailure(IPropertyKey inPropertyKey, @Nullable Throwable inCause, Object[] inParms) {
        super(inPropertyKey.getKey(), CarinaUtils.unwrap(inCause));
        Object[] tempParms;
        Throwable rootCause = this.getCause();
        this._errkey = inPropertyKey;
        Throwable tracer = inCause == null ? this : inCause;
        this._stackTrace = CarinaUtils.getCompactStackTrace(tracer);
        if (rootCause == null) {
            if (inParms != null) {
                tempParms = new Serializable[inParms.length];
                for (int i = 0; i < inParms.length; ++i) {
                    Object parm = inParms[i];
                    tempParms[i] = parm instanceof IMetafieldId ? parm : (parm == null ? null : parm.toString());
                }
            } else {
                tempParms = new Serializable[]{};
            }
        } else if (inParms == null) {
            Object[] parmPlus = new Object[]{rootCause.getClass(), rootCause.getMessage()};
            tempParms = parmPlus;
        } else {
            Object[] parmPlus = new Object[inParms.length + 2];
            int i = 0;
            for ( i = 0; i < inParms.length; ++i) {
                Object parm = inParms[i];
                parmPlus[i] = parm == null ? null : parm.toString();
            }
            parmPlus[i++] = rootCause.getClass();
            parmPlus[i] = rootCause.getMessage();
            tempParms = parmPlus;
        }
        this._parms = tempParms;
    }

    @Override
    public Object[] getParms() {
        return this._parms;
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
    public IMetafieldId getMetafieldId() {
        return this._metafieldId;
    }

    @Override
    public void setMetafieldId(IMetafieldId inMetafieldId) {
        this._metafieldId = inMetafieldId;
    }

    public String getStackTraceString() {
        return this._stackTrace;
    }

    @Override
    public void printStackTrace()
    {
        //LOGGER.error((Object)this._stackTrace);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void printStackTrace(PrintStream inStream) {
        PrintStream printStream = inStream;
        synchronized (printStream) {
            inStream.print(this.getClass().getName() + ": ");
            inStream.print(this._stackTrace);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void printStackTrace(PrintWriter inStream) {
        PrintWriter printWriter = inStream;
        synchronized (printWriter) {
            inStream.print(this.getClass().getName() + ": ");
            inStream.print(this._stackTrace);
        }
    }

    @Override
    public MessageLevelEnum getSeverity() {
        return MessageLevelEnum.SEVERE;
    }

    @Override
    public void setSeverity(MessageLevelEnum inSeverity)
    {
  //      LOGGER.error((Object)"Failure is always SEVERE");
    }

    @Override
    public String getMessage() {
        StringBuilder msg = new StringBuilder("key=" + this.getResourceKey());
        msg.append(" parms=[");
        msg.append(StringUtils.arrayToDelimitedString((Object[])this._parms, (String)":"));
        msg.append("]");
        Throwable t = this.getCause();
        if (t != null) {
            msg.append(" root-cause=").append(t.getClass().toString()).append('/').append(t.getMessage());
        }
        return msg.toString();
    }

    @Override
    public String getLocalizedMessage() {
        IMessageTranslatorProvider translatorProvider = (IMessageTranslatorProvider) PortalApplicationContext.getBean("messageTranslatorProvider");
        IMessageTranslator messageTranslator = translatorProvider.getMessageTranslator(Locale.ENGLISH);
        StringBuilder msg = new StringBuilder(messageTranslator.getMessage(this._errkey, this._parms));
        Throwable t = this.getCause();
        if (t != null) {
            msg.append(" root-cause=").append(t.getClass().toString()).append('/').append(t.getMessage());
        }
        return msg.toString();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
