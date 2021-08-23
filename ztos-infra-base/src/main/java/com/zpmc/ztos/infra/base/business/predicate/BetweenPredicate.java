package com.zpmc.ztos.infra.base.business.predicate;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.business.interfaces.IMetafieldId;
import com.zpmc.ztos.infra.base.business.interfaces.IValueSource;
import com.zpmc.ztos.infra.base.common.model.KeyValuePair;
import com.zpmc.ztos.infra.base.common.utils.FieldUtils;

public class BetweenPredicate extends AbstractFieldPredicate {

    private final Comparable _loValue;
    private final Comparable _hiValue;

    BetweenPredicate(IMetafieldId inPropertyName, Comparable inLoValue, Comparable inHiValue) {
        super(inPropertyName);
        this._loValue = inLoValue;
        this._hiValue = inHiValue;
    }

    @Override
    @NotNull
    public String toHqlString(String inEntityAlias) {
        return FieldUtils.getAliasedField(inEntityAlias, this.getFieldId()) + " between ? and ?";
    }

    @Override
    public KeyValuePair[] getFieldValues() {
        return new KeyValuePair[]{new KeyValuePair(this._fieldId, (Object)this._loValue), new KeyValuePair(this._fieldId, (Object)this._hiValue)};
    }

    public String toString() {
        return this._fieldId + " between " + this._loValue + " and " + this._hiValue;
    }

    @Override
    public boolean isSatisfiedBy(IValueSource inValueSource) {
        Comparable fieldValue = (Comparable)inValueSource.getFieldValue(this._fieldId);
        if (fieldValue == null) {
            return false;
        }
        return this._loValue.compareTo(fieldValue) <= 0 && this._hiValue.compareTo(fieldValue) >= 0;
    }
}
