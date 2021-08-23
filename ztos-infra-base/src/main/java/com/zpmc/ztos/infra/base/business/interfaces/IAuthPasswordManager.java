package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.common.model.SecuritySessionID;

public interface IAuthPasswordManager {
    public static final String BEAN_ID = "authPasswordManager";

    public IAuthenticationResponse authenticate(String var1, String var2, IAuthenticationDetails var3);

    public IAuthenticationResponse preauthAuthenticate(String var1, IAuthenticationDetails var2);

    public void logout(SecuritySessionID var1);

    public void authenticateSystemUser();
}
