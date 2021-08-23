package com.zpmc.ztos.infra.base.common.model;

import com.zpmc.ztos.infra.base.business.interfaces.IMetafieldId;
import com.zpmc.ztos.infra.base.business.model.MetafieldIdFactory;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

public class DictionaryKeyedEntry {

    private final IMetafieldId _fieldId;

    public DictionaryKeyedEntry(String inFieldId) {
        this._fieldId = MetafieldIdFactory.valueOf(inFieldId);
    }

    public DictionaryKeyedEntry(IMetafieldId inFieldId) {
        this._fieldId = inFieldId;
    }

    @Nullable
    public String getFieldId() {
        return this._fieldId.getFieldId();
    }

    public IMetafieldId getMetafieldId() {
        return this._fieldId;
    }

    @Nullable
    public String toString() {
        return this._fieldId.getFieldId();
    }

    public boolean equals(Object inO) {
        if (this == inO) {
            return true;
        }
        if (!(inO instanceof DictionaryKeyedEntry)) {
            return false;
        }
        DictionaryKeyedEntry dictionaryKeyedEntry = (DictionaryKeyedEntry)inO;
        return this._fieldId.equals(dictionaryKeyedEntry._fieldId);
    }

    public int hashCode() {
        return this._fieldId.hashCode();
    }
}
