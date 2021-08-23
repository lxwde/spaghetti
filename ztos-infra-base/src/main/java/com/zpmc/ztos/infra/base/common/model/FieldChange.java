package com.zpmc.ztos.infra.base.common.model;

import com.zpmc.ztos.infra.base.business.interfaces.IEFieldChange;
import com.zpmc.ztos.infra.base.business.interfaces.IMetafieldId;
import com.zpmc.ztos.infra.base.business.model.MetafieldIdFactory;
//import org.apache.commons.lang.StringUtils;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;

public class FieldChange implements Serializable, IEFieldChange {

    private final IMetafieldId _fieldId;
    private final Object _priorValue;
    private final Object _newValue;
    private final boolean _priorValueKnown;

    @Deprecated
    public FieldChange(String inFieldId, Object inNewValue) {
        this._fieldId = MetafieldIdFactory.valueOf(inFieldId);
        this._newValue = inNewValue;
        this._priorValue = null;
        this._priorValueKnown = false;
    }

    @Deprecated
    public FieldChange(String inFieldId, Object inPriorValue, Object inNewValue) {
        this._fieldId = MetafieldIdFactory.valueOf(inFieldId);
        this._priorValue = inPriorValue;
        this._newValue = inNewValue;
        this._priorValueKnown = true;
    }

    public FieldChange(IMetafieldId inFieldId, Object inNewValue) {
        this._fieldId = inFieldId;
        this._newValue = inNewValue;
        this._priorValue = null;
        this._priorValueKnown = false;
    }

    public FieldChange(IMetafieldId inFieldId, Object inPriorValue, Object inNewValue) {
        this._fieldId = inFieldId;
        this._priorValue = inPriorValue;
        this._newValue = inNewValue;
        this._priorValueKnown = true;
    }

    public String toString() {
        if (this._priorValueKnown) {
            return this._fieldId + " = '" + ValueObject.fieldValue2String(this._priorValue) + "' => '" + ValueObject.fieldValue2String(this._newValue) + "'";
        }
        return this._fieldId + " = '" + ValueObject.fieldValue2String(this._newValue) + "'";
    }

    @Deprecated
    @Nullable
    public String getFieldId() {
        return this._fieldId.getFieldId();
    }

    @Override
    @Nullable
    public IMetafieldId getMetafieldId() {
        if (StringUtils.isEmpty((String)this._fieldId.getFieldId())) {
            return null;
        }
        return this._fieldId;
    }

    @Override
    public Object getPriorValue() {
        return this._priorValue;
    }

    @Override
    public Object getNewValue() {
        return this._newValue;
    }

    @Override
    public boolean isPriorValueKnown() {
        return this._priorValueKnown;
    }

}
