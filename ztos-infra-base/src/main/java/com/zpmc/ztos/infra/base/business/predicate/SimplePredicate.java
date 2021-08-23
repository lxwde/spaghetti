package com.zpmc.ztos.infra.base.business.predicate;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.business.interfaces.IMetafieldId;
import com.zpmc.ztos.infra.base.business.model.MetafieldIdFactory;
import com.zpmc.ztos.infra.base.common.model.KeyValuePair;
import com.zpmc.ztos.infra.base.common.utils.FieldUtils;

public abstract class SimplePredicate extends AbstractFieldPredicate {

    private final Object _fieldValue;

    public SimplePredicate(IMetafieldId inFieldId, Object inFieldValue) {
        super(inFieldId);
        this._fieldValue = inFieldValue;
    }

    @Deprecated
    SimplePredicate(String inFieldId, Object inFieldValue) {
        super(MetafieldIdFactory.valueOf(inFieldId));
        this._fieldValue = inFieldValue;
    }

    protected Object getFieldValue() {
        return this._fieldValue;
    }

    @Override
    @NotNull
    public String toHqlString(String inEntityAlias) {
        return FieldUtils.getAliasedField(inEntityAlias, this.getFieldId()) + this.getOp() + '?';
    }

    @Override
    public KeyValuePair[] getFieldValues() {
        return new KeyValuePair[]{new KeyValuePair(this._fieldId, this._fieldValue)};
    }

    public String toString() {
        return this._fieldId + this.getOp() + this._fieldValue;
    }

    protected abstract String getOp();

}
