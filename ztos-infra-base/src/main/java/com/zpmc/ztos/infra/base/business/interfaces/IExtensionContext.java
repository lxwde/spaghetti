package com.zpmc.ztos.infra.base.business.interfaces;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.common.model.UserContext;

public interface IExtensionContext {
    @NotNull
    public UserContext getUserContext();

    public String getBriefDescription();
}
