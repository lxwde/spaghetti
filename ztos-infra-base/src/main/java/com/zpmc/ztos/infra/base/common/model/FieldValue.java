package com.zpmc.ztos.infra.base.common.model;

import com.zpmc.ztos.infra.base.business.interfaces.IMetafieldId;

public class FieldValue {
    final IMetafieldId _fieldId;
    final Object _value;
    final String _uiValue;

    public FieldValue(IMetafieldId inFieldId, Object inValue, String inUiValue) {
        this._fieldId = inFieldId;
        this._value = inValue;
        this._uiValue = inUiValue;
    }

    public IMetafieldId getFieldId() {
        return this._fieldId;
    }

    public String getUiValue() {
        return this._uiValue;
    }

    public Object getValue() {
        return this._value;
    }
}
