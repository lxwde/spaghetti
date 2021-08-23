package com.zpmc.ztos.infra.base.common.security;

import com.zpmc.ztos.infra.base.business.enums.core.SessionTypeEnum;
import com.zpmc.ztos.infra.base.business.interfaces.IAuthenticationDetails;

public class DefaultAuthenticationDetails implements IAuthenticationDetails {
    private SessionTypeEnum _sessionType;
    private final String _ipAddress;
    private boolean _authExternal;

    DefaultAuthenticationDetails(String inIpAddress, SessionTypeEnum inSessionType) {
        this._ipAddress = inIpAddress;
        this._sessionType = inSessionType;
    }

    @Override
    public String getIpAddress() {
        return this._ipAddress;
    }

    @Override
    public SessionTypeEnum getSessionType() {
        return this._sessionType;
    }

    public void setSessionType(SessionTypeEnum inSessionType) {
        this._sessionType = inSessionType;
    }

    @Override
    public boolean isAuthExternal() {
        return this._authExternal;
    }

    @Override
    public void setAuthExternal(boolean inAuthExternal) {
        this._authExternal = inAuthExternal;
    }

    public String toString() {
        StringBuilder buf = new StringBuilder("\nAuthentication Details: ");
        buf.append("  SessionType=  ").append(this._sessionType);
        if (this._ipAddress != null) {
            buf.append(", IP Address=  ").append(this.getIpAddress());
        }
        return buf.toString();
    }
}
