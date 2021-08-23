package com.zpmc.ztos.infra.base.common.model;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.business.enums.extension.ExtensionResponseStatusEnum;
import com.zpmc.ztos.infra.base.business.interfaces.IExtensionResponse;
import com.zpmc.ztos.infra.base.business.interfaces.IMessageCollector;
import com.zpmc.ztos.infra.base.business.interfaces.IMetafieldUserMessage;
import com.zpmc.ztos.infra.base.business.interfaces.IUserMessage;

public abstract class AbstractExtensionResponse implements IExtensionResponse {
    private ExtensionResponseStatusEnum _status = ExtensionResponseStatusEnum.UNKNOWN;

    @Override
    public boolean hasStatus(@NotNull ExtensionResponseStatusEnum inStatus) {
        return inStatus.equals((Object)this._status);
    }

    @Override
    public ExtensionResponseStatusEnum getStatus() {
        return this._status;
    }

    public void setStatus(ExtensionResponseStatusEnum inStatus) {
        this._status = inStatus;
    }

    @Override
    public void appendError(IUserMessage inMessage) {
        if (inMessage instanceof IMetafieldUserMessage) {
            this.getMessageCollector().appendMessage(inMessage);
        }
    }

    @Override
    public IMessageCollector getMessageCollector() {
        return TransactionParms.getBoundParms().getMessageCollector();
    }

    public String toString() {
        return super.toString() + "  Status=" + (Object)((Object)this._status);
    }
}
