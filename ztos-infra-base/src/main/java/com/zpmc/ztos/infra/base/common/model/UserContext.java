package com.zpmc.ztos.infra.base.common.model;

import com.zpmc.ztos.infra.base.business.interfaces.ISecureSessionAware;

import com.sun.istack.Nullable;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.util.Locale;
import java.util.TimeZone;

public abstract class UserContext implements ISecureSessionAware, Serializable {

    public static final long DEFAULT_SLEEP_DELAY = 1200L;
    private TimeZone _timeZone;
    private SecuritySessionID _securitySessionId;
    private final Object _userKey;
    private final String _userId;
    private String _externalId;
    private final ScopeCoordinates _scopeCoordinate;
    private ScopeCoordinates _broadestAllowedScope;
    private Locale _userLocale;
    private long _sleepDelaySecs = -1L;
    private static final long serialVersionUID = -3441649823441698364L;

    @Deprecated
    @Nullable
    public static UserContext getThreadUserContext() {
        TransactionParms tp = TransactionParms.getBoundParms();
        return tp == null ? null : tp.getUserContext();
    }

    @Deprecated
    public UserContext(Object inUserGkey, String inUserId) {
        this._userKey = inUserGkey;
        this._userId = inUserId;
        this._scopeCoordinate = new ScopeCoordinates(new Serializable[0]);
    }

    public UserContext(Object inUserGkey, String inUserId, ScopeCoordinates inScopeCoordinates) {
        this._userKey = inUserGkey;
        this._userId = inUserId;
        this._scopeCoordinate = inScopeCoordinates;
    }

    public Object getUserKey() {
        return this._userKey;
    }

    public Serializable getUserGkey() {
        if (this._userKey instanceof String) {
            return new Long((String)this._userKey);
        }
        return (Serializable)this._userKey;
    }


    public String getUserId() {
        return this._userId;
    }

    public TimeZone getBusinessTimeZone() {
        return this._timeZone;
    }


    public ScopeCoordinates getScopeCoordinate() {
        return this._scopeCoordinate;
    }

    public TimeZone getTimeZone() {
        if (this._timeZone == null) {
            this._timeZone = TimeZone.getDefault();
        }
        return this._timeZone;
    }

    public void setTimeZone(TimeZone inTimeZone) {
        this._timeZone = inTimeZone;
    }

    @Override
    public SecuritySessionID getSecuritySessionId() {
        return this._securitySessionId;
    }

    @Override
    public void setSecuritySessionId(SecuritySessionID inSecuritySessionId) {
        this._securitySessionId = inSecuritySessionId;
    }

    public Locale getUserLocale() {
        if (this._userLocale == null) {
            return Locale.getDefault();
        }
        return this._userLocale;
    }

    public void setUserLocale(Locale inUserLocale) {
        this._userLocale = inUserLocale;
    }

    public long getSleepDelaySecs() {
        return this._sleepDelaySecs;
    }

    public void setSleepDelaySecs(long inSleepDelaySecs) {
        this._sleepDelaySecs = inSleepDelaySecs;
    }

    public String getExternalId() {
        return this._externalId;
    }

    public void setExternalId( String inExternalId) {
        this._externalId = inExternalId;
    }

    public String toString() {
        StringBuilder userDumpText = new StringBuilder("\nUser Context for ");
        userDumpText.append(this.getUserId()).append("(gkey=").append(this.getUserKey()).append(")");
        userDumpText.append("\n  SecureSessionId:  ").append(this.getSecuritySessionId());
        ScopeCoordinates scopeCoordinates = this.getScopeCoordinate();
        String userCoords = scopeCoordinates.getBusinessCoords();
        if (StringUtils.isEmpty((String)userCoords)) {
            userCoords = scopeCoordinates.toString();
        }
        userDumpText.append("\n  Scope:  ").append(userCoords);
        userDumpText.append("\n  Locale:  ").append(this.getUserLocale().getDisplayName());
        userDumpText.append("\n  TimeZone:  ").append(this.getTimeZone().getDisplayName());
        return userDumpText.toString();
    }

    @Nullable
    public ScopeCoordinates getBroadestAllowedScope() {
        return this._broadestAllowedScope;
    }

    public void setBroadestAllowedScope(ScopeCoordinates inScopeCoordinates) {
        this._broadestAllowedScope = inScopeCoordinates;
    }

  //  @Override
    public String getBriefDetails() {
        ScopeCoordinates scopeCoordinate;
        StringBuilder buf = new StringBuilder();
        buf.append("[ user id=").append(this.getUserId());
        String externalUserId = this.getExternalId();
        if (externalUserId != null && !externalUserId.isEmpty()) {
            buf.append(", external user id=").append(externalUserId);
        }
        if ((scopeCoordinate = this.getScopeCoordinate()) != null) {
            String scopeStr = scopeCoordinate.getBusinessCoords("/");
            if (scopeStr != null && !scopeStr.isEmpty()) {
                buf.append(", scope=").append(scopeStr);
            } else {
                buf.append(", scope=").append(scopeCoordinate);
            }
        }
        buf.append("]");
        return buf.toString();
    }



}

