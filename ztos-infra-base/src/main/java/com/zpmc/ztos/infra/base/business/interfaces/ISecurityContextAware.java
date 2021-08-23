package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.common.model.UserContext;

public interface ISecurityContextAware extends ISecurityContext{
    public UserContext getUserContext();

    public void setUserContext(UserContext var1);
}
