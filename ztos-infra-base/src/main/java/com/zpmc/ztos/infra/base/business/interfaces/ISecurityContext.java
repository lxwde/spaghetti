package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.common.model.SecuritySessionID;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

public interface ISecurityContext
//        extends SecurityContext
{
    @Nullable
    public SecuritySessionID getSecuritySessionId();

    @Nullable
    public IAuthenticationDetails getAuthenticationDetails();

    @Nullable
    public ICarinaUserDetails getUserDetails();
}
