package com.zpmc.ztos.infra.base.common.model;

import com.zpmc.ztos.infra.base.business.interfaces.IUserContextProvider;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractUserContextProvider implements IUserContextProvider {
    private final Map _systemUserContexts = new HashMap();
    private UserContext _globalSystemUserContext;

    @Override
    public UserContext getSystemUserContext() {
        if (this._globalSystemUserContext == null) {
            this._globalSystemUserContext = this.createSystemContext("-system-", ScopeCoordinates.GLOBAL_SCOPE);
        }
        return this._globalSystemUserContext;
    }

    protected UserContext createSystemContext(String inSystemId, ScopeCoordinates inScopeCoordinates) {
        return this.createUserContext(inSystemId, inSystemId, inScopeCoordinates);
    }

    @Override
    public UserContext getSystemUserContext(ScopeCoordinates inScopeCoordinates) {
        if (inScopeCoordinates == null) {
            return this.getSystemUserContext();
        }
        String key = inScopeCoordinates.getScopeAddress();
        if (this._systemUserContexts.containsKey(key)) {
            return (UserContext)this._systemUserContexts.get(key);
        }
        UserContext scopedContext = this.createSystemContext("-system-", inScopeCoordinates);
        this._systemUserContexts.put(key, scopedContext);
        return scopedContext;
    }
}
