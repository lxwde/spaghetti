package com.zpmc.ztos.infra.base.common.utils;

import com.zpmc.ztos.infra.base.business.interfaces.IUserContextProvider;
import com.zpmc.ztos.infra.base.common.contexts.PortalApplicationContext;
import com.zpmc.ztos.infra.base.common.model.ScopeCoordinates;
import com.zpmc.ztos.infra.base.common.model.UserContext;

public class UserContextUtils {
    public static UserContext createUserContext(Object inUserGkey, String inUserId, ScopeCoordinates inScopeCoordinates) {
        IUserContextProvider contextProvider = (IUserContextProvider) PortalApplicationContext.getBean("userContextProvider");
        return contextProvider.createUserContext(inUserGkey, inUserId, inScopeCoordinates);
    }

    public static UserContext createUserContext(Object inUserGkey, String inUserId) {
        return UserContextUtils.createUserContext(inUserGkey, inUserId, ScopeCoordinates.GLOBAL_SCOPE);
    }

    public static UserContext createGuestContext() {
        IUserContextProvider contextProvider = (IUserContextProvider)PortalApplicationContext.getBean("userContextProvider");
        return contextProvider.createGuestUserContext();
    }

    public static UserContext getSystemUserContext() {
        IUserContextProvider contextProvider = (IUserContextProvider)PortalApplicationContext.getBean("userContextProvider");
        return contextProvider.getSystemUserContext();
    }

    public static UserContext getSystemUserContext(ScopeCoordinates inCoordinates) {
        IUserContextProvider contextProvider = (IUserContextProvider)PortalApplicationContext.getBean("userContextProvider");
        return contextProvider.getSystemUserContext(inCoordinates);
    }

    private UserContextUtils() {
    }

}
