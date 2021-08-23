package com.zpmc.ztos.infra.base.business.interfaces;

import com.sun.istack.NotNull;

public interface IMessageIdMapper {
    @NotNull
    public String messageIdToString(int var1);
}
