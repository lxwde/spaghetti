package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.business.enums.framework.AuthenticationStatusEnum;

import java.io.Serializable;

public interface IAuthenticationResponse {
    public Serializable getUserGkey();

    public ISecurityContext getSecurityContext();

    public IMessageCollector getMessageCollector();

    public boolean isAuthenticated();

    public boolean isAuthExternal();

    public AuthenticationStatusEnum getStatus();
}
