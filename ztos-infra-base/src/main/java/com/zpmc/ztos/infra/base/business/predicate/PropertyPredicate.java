package com.zpmc.ztos.infra.base.business.predicate;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.business.interfaces.IMetafieldId;
import com.zpmc.ztos.infra.base.common.model.KeyValuePair;
import com.zpmc.ztos.infra.base.common.utils.FieldUtils;

public abstract class PropertyPredicate extends AbstractFieldPredicate {

    protected final IMetafieldId _otherFieldId;
    private static final KeyValuePair[] NO_VALUES = new KeyValuePair[0];

    PropertyPredicate(IMetafieldId inFieldId, IMetafieldId inOtherFieldId) {
        super(inFieldId);
        this._otherFieldId = inOtherFieldId;
    }

    @Override
    @NotNull
    public String toHqlString(String inEntityAlias) {
        return FieldUtils.getAliasedField(inEntityAlias, this.getFieldId()) + this.getOp() + FieldUtils.getAliasedField(inEntityAlias, this._otherFieldId);
    }

    public String toString() {
        return this._fieldId + this.getOp() + this._otherFieldId;
    }

    @Override
    public KeyValuePair[] getFieldValues() {
        return NO_VALUES;
    }

    protected abstract String getOp();

    protected IMetafieldId getOtherFieldId() {
        return this._otherFieldId;
    }
}
