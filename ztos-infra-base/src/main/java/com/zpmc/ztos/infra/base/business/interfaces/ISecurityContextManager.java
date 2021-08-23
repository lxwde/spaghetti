package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.common.model.SecuritySessionID;

public interface ISecurityContextManager {
    public static final String BEAN_ID = "securityContextManager";

    public ISecurityContext getSecureContext(SecuritySessionID var1);

    public void setSecureContext(ISecurityContext var1);

    public void removeSecureContext(SecuritySessionID var1);
}
