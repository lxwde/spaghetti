package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.common.model.ScopeCoordinates;
import com.zpmc.ztos.infra.base.common.model.UserContext;

public interface IUserContextProvider {
    public static final String BEAN_ID = "userContextProvider";
    public static final String GUEST_UID = "guest";
    public static final String SYSTEM_UID = "-system-";

    public UserContext createUserContext(Object var1, String var2, ScopeCoordinates var3);

    public UserContext createGuestUserContext();

    public UserContext getSystemUserContext();

    public UserContext getSystemUserContext(ScopeCoordinates var1);

}
