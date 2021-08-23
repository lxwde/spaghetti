package com.zpmc.ztos.infra.base.common.contexts;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.business.interfaces.IExtensionContext;
import com.zpmc.ztos.infra.base.common.model.UserContext;

public abstract class AbstractExtensionContext implements IExtensionContext {
    private final UserContext _userContext;

    public AbstractExtensionContext(@NotNull UserContext inUserContext) {
        this._userContext = inUserContext;
    }

    @Override
    @NotNull
    public UserContext getUserContext() {
        return this._userContext;
    }
}
