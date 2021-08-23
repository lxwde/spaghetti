package com.zpmc.ztos.infra.base.common.model;

import com.zpmc.ztos.infra.base.business.interfaces.IFrameworkPropertyKeys;
import com.zpmc.ztos.infra.base.business.interfaces.IMessageCollector;
import com.zpmc.ztos.infra.base.business.model.BusinessContextStats;
import com.zpmc.ztos.infra.base.common.exceptions.BizFailure;
import org.apache.log4j.Logger;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class TransactionParms {

    private UserContext _userContext;
    private String _businessActionId;
    private Map _requestParms;
    private Map _applicationParms;
    private BusinessContextStats _businessContextMetrics;
    private BizResponse _response;
    private IMessageCollector _messageCollector;
    private boolean _scopingEnabled = true;
    private RequestUuid _uuid;
    public static final String UNKNOWN_USER = "-unknown-";
    private static final Logger LOGGER = Logger.getLogger(TransactionParms.class);

    public void bind() {
        TransactionSynchronizationManager.bindResource(TransactionParms.class, (Object)this);
    }

    public static void unbind() {
        TransactionSynchronizationManager.unbindResource(TransactionParms.class);
    }

    public static void unbindIfBound() {
        TransactionParms parms = (TransactionParms)TransactionSynchronizationManager.getResource(TransactionParms.class);
        if (parms != null) {
            TransactionParms.unbind();
        }
    }

    public static boolean isBound() {
        TransactionParms parms = (TransactionParms)TransactionSynchronizationManager.getResource(TransactionParms.class);
        return parms != null;
    }

    public static TransactionParms getBoundParms() {
        TransactionParms parms = (TransactionParms)TransactionSynchronizationManager.getResource(TransactionParms.class);
        if (parms == null) {
            BizFailure ex = BizFailure.create("");
            LOGGER.warn((Object)("getBoundParms: NO TRANSACTION PARMS!\n" + ex.getStackTraceString()));
            parms = new TransactionParms();
        }
        return parms;
    }

    @Nullable
    public Serializable getUserKey() {
        return this._userContext != null ? (Serializable)this._userContext.getUserKey() : null;
    }

    public String getUserId() {
        return this._userContext != null ? this._userContext.getUserId() : UNKNOWN_USER;
    }

    public static String getBoundUserId() {
        TransactionParms parms = TransactionParms.getBoundParms();
        if (parms != null && parms.getUserContext() != null) {
            return parms.getUserId();
        }
        return UNKNOWN_USER;
    }

    @Nullable
    public static UserContext getBoundUserContext() {
        TransactionParms parms = TransactionParms.getBoundParms();
        if (parms != null) {
            return parms.getUserContext();
        }
        return null;
    }

    public String getBusinessActionId() {
        return this._businessActionId;
    }

    public void setBusinessActionId(String inBusinessActionId) {
        this._businessActionId = inBusinessActionId;
    }

    public Map getRequestParms() {
        return this._requestParms;
    }

    public void setRequestParms(Map inRequestParms) {
        this._requestParms = inRequestParms;
    }

    @Nullable
    public String getRequiredRequestParm(String inKey) {
        String parmStr = (String)this._requestParms.get(inKey);
        if (parmStr == null && !this._requestParms.containsKey(inKey)) {
            throw BizFailure.create(IFrameworkPropertyKeys.FRAMEWORK__REQUIRED_PARM_NOT_FOUND, null, inKey);
        }
        return parmStr;
    }

    @Deprecated
    public BizResponse getResponse() {
        return this._response;
    }

    public void setResponse(BizResponse inResponse) {
        this._response = inResponse;
        this._messageCollector = inResponse;
    }

    public void setMessageCollector(IMessageCollector inMessageCollector) {
        this._messageCollector = inMessageCollector;
    }

    public UserContext getUserContext() {
        return this._userContext;
    }

    public void setUserContext(UserContext inUserContext) {
        this._userContext = inUserContext;
    }

    @Nullable
    public IMessageCollector getMessageCollector() {
        return this._messageCollector != null ? this._messageCollector : null;
    }

    public boolean isScopingEnabled() {
        return this._scopingEnabled;
    }

    public void setScopingEnabled(boolean inScopingEnabled) {
        this._scopingEnabled = inScopingEnabled;
    }

    public void putApplicationParm(Object inKey, Object inValue) {
        if (this._applicationParms == null) {
            this._applicationParms = new HashMap();
        }
        this._applicationParms.put(inKey, inValue);
    }

    @Nullable
    public Object getApplicationParm(Object inKey) {
        if (this._applicationParms == null) {
            return null;
        }
        return this._applicationParms.get(inKey);
    }

    public boolean containsApplicationParm(Object inKey) {
        if (this._applicationParms == null) {
            return false;
        }
        return this._applicationParms.containsKey(inKey);
    }

    public RequestUuid getUniqueId() {
        if (this._uuid == null) {
            this._uuid = new RequestUuid();
        }
        return this._uuid;
    }

    public String toString() {
        return super.toString(); //+ DiagnosticUtils.describeGenericBeanProperties(this);
    }

    public BusinessContextStats getBusinessContextMetrics() {
        return this._businessContextMetrics == null ? new BusinessContextStats() : this._businessContextMetrics;
    }

    public void setBusinessContextMetrics(BusinessContextStats inBusinessContextStats) {
        this._businessContextMetrics = inBusinessContextStats;
    }


}
