package com.zpmc.ztos.infra.base.business.predicate;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.business.interfaces.IMetafieldId;
import com.zpmc.ztos.infra.base.business.interfaces.IValueSource;
import com.zpmc.ztos.infra.base.common.model.KeyValuePair;
import com.zpmc.ztos.infra.base.common.utils.FieldUtils;

public class PkEqPredicate extends AbstractFieldPredicate {
    private final Object _fieldValue;

    PkEqPredicate(Object inValue) {
        super(IMetafieldId.PRIMARY_KEY);
        this._fieldValue = inValue;
    }

    @Override
    @NotNull
    public String toHqlString(String inEntityAlias) {
        return FieldUtils.getAliasedField(inEntityAlias, this.getFieldId()) + " = ?";
    }

    public String toString() {
        return "primary key = " + this._fieldValue;
    }

    @Override
    public KeyValuePair[] getFieldValues() {
        return new KeyValuePair[]{new KeyValuePair(IMetafieldId.PRIMARY_KEY, this._fieldValue)};
    }

    @Override
    public boolean isSatisfiedBy(IValueSource inValueSource) {
        Object pk = inValueSource.getFieldValue(IMetafieldId.PRIMARY_KEY);
        if (this._fieldValue == null || pk == null) {
            return false;
        }
        return this._fieldValue.equals(pk);
    }

}
