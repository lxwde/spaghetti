package com.zpmc.ztos.infra.base.business.interfaces;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import com.zpmc.ztos.infra.base.business.enums.extension.ExtensionResponseStatusEnum;

public interface IExtensionResponse {
    public ExtensionResponseStatusEnum getStatus();

    public boolean hasStatus(@NotNull ExtensionResponseStatusEnum var1);

    public void appendError(IUserMessage var1);

    @Nullable
    public IMessageCollector getMessageCollector();
}
