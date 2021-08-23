package com.zpmc.ztos.infra.base.common.model;

import com.zpmc.ztos.infra.base.business.interfaces.IEFieldChanges;
import com.zpmc.ztos.infra.base.business.interfaces.IMessageCollector;
import com.zpmc.ztos.infra.base.common.messages.MessageCollectorFactory;

public class EntityExtensionResponse extends AbstractExtensionResponse{
    private IMessageCollector _messageCollector = MessageCollectorFactory.createMessageCollector();
    private IEFieldChanges _fieldChanges;

    public void setFieldChanges(IEFieldChanges inFieldChanges) {
        this._fieldChanges = inFieldChanges;
    }

    public IEFieldChanges getFieldChanges() {
        return this._fieldChanges;
    }

    @Override
    public IMessageCollector getMessageCollector() {
        return this._messageCollector;
    }

    public void setMessageCollector(IMessageCollector inMessageCollector) {
        this._messageCollector = inMessageCollector;
    }

    @Override
    public String toString() {
        return super.toString() + "\n  " + this._messageCollector + "\nfieldChanges=" + this._fieldChanges;
    }
}
