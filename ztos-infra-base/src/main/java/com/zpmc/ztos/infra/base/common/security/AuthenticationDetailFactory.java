package com.zpmc.ztos.infra.base.common.security;

import com.zpmc.ztos.infra.base.business.enums.core.SessionTypeEnum;
import com.zpmc.ztos.infra.base.business.interfaces.IAuthenticationDetails;

public class AuthenticationDetailFactory {
    private AuthenticationDetailFactory() {
    }

    public static IAuthenticationDetails createAuthenticationDetails(String inIpAddress, SessionTypeEnum inSessionType) {
        return new DefaultAuthenticationDetails(inIpAddress, inSessionType);
    }

}
