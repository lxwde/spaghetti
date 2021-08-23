package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.common.model.SecuritySessionID;

public interface ISecureSessionAware {
    public SecuritySessionID getSecuritySessionId();

    public void setSecuritySessionId(SecuritySessionID var1);
}
