package com.zpmc.ztos.infra.base.business.predicate;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.business.interfaces.IMetafieldId;
import com.zpmc.ztos.infra.base.common.model.KeyValuePair;
import com.zpmc.ztos.infra.base.common.utils.FieldUtils;

public abstract class GeometryPropertyPredicate extends AbstractFieldPredicate {
    private final IMetafieldId _otherFieldId;
    private static final KeyValuePair[] NO_VALUES = new KeyValuePair[0];

    GeometryPropertyPredicate(IMetafieldId inFieldId, IMetafieldId inOtherFieldId) {
        super(inFieldId);
        this._otherFieldId = inOtherFieldId;
    }

    @Override
    @NotNull
    public String toHqlString(String inEntityAlias) {
        String aliasedField1 = FieldUtils.getAliasedField(inEntityAlias, this.getFieldId());
        String aliasedField2 = FieldUtils.getAliasedField(inEntityAlias, this._otherFieldId);
        return "(" + this.getOp() + '(' + aliasedField1 + ", " + aliasedField2 + ") = true )";
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
