package com.zpmc.ztos.infra.base.business.predicate;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.business.interfaces.IMetafieldId;
import com.zpmc.ztos.infra.base.business.interfaces.IValueSource;
import com.zpmc.ztos.infra.base.common.model.KeyValuePair;
import com.zpmc.ztos.infra.base.common.utils.FieldUtils;

public class NotNullPredicate extends AbstractFieldPredicate {
    private static final KeyValuePair[] NO_VALUES = new KeyValuePair[0];

    NotNullPredicate(IMetafieldId inFieldId) {
        super(inFieldId);
    }

    @Override
    @NotNull
    public String toHqlString(String inEntityAlias) {
        return FieldUtils.getAliasedField(inEntityAlias, this.getFieldId()) + " is not null";
    }

    public String toString() {
        return this._fieldId + " is not null";
    }

    @Override
    public KeyValuePair[] getFieldValues() {
        return NO_VALUES;
    }

    @Override
    public boolean isSatisfiedBy(IValueSource inValueSource) {
        return inValueSource.getFieldValue(this._fieldId) != null;
    }
}
