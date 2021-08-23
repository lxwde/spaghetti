package com.zpmc.ztos.infra.base.common.model;

import com.zpmc.ztos.infra.base.business.interfaces.IMetafieldId;
import com.zpmc.ztos.infra.base.business.model.MetafieldIdFactory;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.io.Serializable;

public class KeyValuePair implements Serializable {
    private IMetafieldId _key;
    private Object _value;

    public String toString() {
        return this._key + " = '" + ValueObject.fieldValue2String(this._value) + "'";
    }

    private KeyValuePair() {
    }

    @Deprecated
    public KeyValuePair(String inKey, Object inValue) {
        this._key = MetafieldIdFactory.valueOf(inKey);
        this._value = inValue;
    }

    public KeyValuePair(IMetafieldId inKey, Object inValue) {
        this._key = inKey;
        this._value = inValue;
    }

    public Object getValue() {
        return this._value;
    }

    @Nullable
    public String getKey() {
        return this._key.getQualifiedId();
    }

    public IMetafieldId getMetafieldId() {
        return this._key;
    }

}
