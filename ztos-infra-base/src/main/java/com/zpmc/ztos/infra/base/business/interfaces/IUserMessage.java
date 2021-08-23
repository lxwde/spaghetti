package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.business.enums.framework.MessageLevelEnum;

public interface IUserMessage extends IParameterAware{
    @Deprecated
    public String getResourceKey();

    public IPropertyKey getMessageKey();

    public MessageLevelEnum getSeverity();

    public void setSeverity(MessageLevelEnum var1);
}
