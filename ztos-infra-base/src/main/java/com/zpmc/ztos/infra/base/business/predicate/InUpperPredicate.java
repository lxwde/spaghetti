package com.zpmc.ztos.infra.base.business.predicate;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.business.interfaces.IMetafieldId;
import com.zpmc.ztos.infra.base.common.utils.FieldUtils;

public class InUpperPredicate extends InPredicate{
    InUpperPredicate(IMetafieldId inFieldId, Object[] inValues) {
        super(inFieldId, inValues);
    }

    @Override
    @NotNull
    public String toHqlString(String inEntityAlias) {
        StringBuilder buf = new StringBuilder().append(" UPPER(").append(FieldUtils.getAliasedField(inEntityAlias, this.getFieldId())).append(")").append(" in (");
        for (int i = 0; i < this._values.length; ++i) {
            buf.append('?');
            if (i >= this._values.length - 1) continue;
            buf.append(',');
        }
        buf.append(')');
        return buf.toString();
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder().append(" UPPER(").append(this._fieldId).append(")").append(" in (");
        for (int i = 0; i < this._values.length; ++i) {
            buf.append(this._values[i]);
            if (i >= this._values.length - 1) continue;
            buf.append(',');
        }
        buf.append(')');
        return buf.toString();
    }
}
