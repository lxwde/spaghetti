package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.business.enums.core.SessionTypeEnum;

public interface IAuthenticationDetails {
    public String getIpAddress();

    public SessionTypeEnum getSessionType();

    public boolean isAuthExternal();

    public void setAuthExternal(boolean var1);
}
