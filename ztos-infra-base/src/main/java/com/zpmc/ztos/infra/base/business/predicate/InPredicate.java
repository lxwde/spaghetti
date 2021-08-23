package com.zpmc.ztos.infra.base.business.predicate;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.business.interfaces.IMetafieldId;
import com.zpmc.ztos.infra.base.business.interfaces.IValueSource;
import com.zpmc.ztos.infra.base.common.exceptions.BizFailure;
import com.zpmc.ztos.infra.base.common.model.KeyValuePair;
import com.zpmc.ztos.infra.base.common.utils.FieldUtils;

public class InPredicate extends AbstractFieldPredicate {

    protected final Object[] _values;
    private static final int LIMIT_IN_PREDICATE = 1000;

    InPredicate(IMetafieldId inFieldId, Object inValues) {
        super(inFieldId);
        InPredicate.checkValues(inValues);
        this._values = inValues instanceof Object[] ? (Object[])inValues : new Object[]{inValues};
    }

    InPredicate(IMetafieldId inFieldId, Object[] inValues) {
        super(inFieldId);
        InPredicate.checkValues(inValues);
        this._values = inValues;
    }

    private static void checkValues(Object inValues) {
        if (inValues == null) {
            throw BizFailure.create("null passed as value of In Predicate");
        }
        if (inValues instanceof Object[] && ((Object[])inValues).length > 1000) {
            throw BizFailure.create("Number of values in the predicate can not exceed 1000");
        }
    }

    @Override
    @NotNull
    public String toHqlString(String inEntityAlias) {
        StringBuilder buf = new StringBuilder().append(FieldUtils.getAliasedField(inEntityAlias, this.getFieldId())).append(" in (");
        for (int i = 0; i < this._values.length; ++i) {
            buf.append('?');
            if (i >= this._values.length - 1) continue;
            buf.append(',');
        }
        buf.append(')');
        return buf.toString();
    }

    public String toString() {
        StringBuilder buf = new StringBuilder().append(this._fieldId).append(" in (");
        for (int i = 0; i < this._values.length; ++i) {
            buf.append(this._values[i]);
            if (i >= this._values.length - 1) continue;
            buf.append(',');
        }
        buf.append(')');
        return buf.toString();
    }

    @Override
    public KeyValuePair[] getFieldValues() {
        KeyValuePair[] kvp = new KeyValuePair[this._values.length];
        for (int i = 0; i < kvp.length; ++i) {
            kvp[i] = new KeyValuePair(this._fieldId, this._values[i]);
        }
        return kvp;
    }

    @Override
    public boolean isSatisfiedBy(IValueSource inValueSource) {
        Comparable fieldValue = (Comparable)inValueSource.getFieldValue(this._fieldId);
        if (fieldValue == null) {
            return false;
        }
        for (int i = 0; i < this._values.length; ++i) {
            boolean match = fieldValue.equals(this._values[i]);
            if (!match) continue;
            return true;
        }
        return false;
    }
}
