package com.zpmc.ztos.infra.base.business.predicate;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.business.interfaces.IMetafieldId;
import com.zpmc.ztos.infra.base.business.interfaces.IValueSource;
import com.zpmc.ztos.infra.base.common.model.KeyValuePair;
import com.zpmc.ztos.infra.base.common.utils.FieldUtils;

import java.util.Date;

public class OlderThanPredicate extends AbstractFieldPredicate {
    private final long _ageMillis;

    OlderThanPredicate(IMetafieldId inDateField, long inAgeMillis) {
        super(inDateField);
        this._ageMillis = inAgeMillis;
    }

    @Override
    @NotNull
    public String toHqlString(String inEntityAlias) {
        return FieldUtils.getAliasedField(inEntityAlias, this.getFieldId()) + " < ?";
    }

    public String toString() {
        return this._fieldId + " older than " + this._ageMillis + " millis";
    }

    @Override
    public KeyValuePair[] getFieldValues() {
        Date date = new Date(System.currentTimeMillis() - this._ageMillis);
        return new KeyValuePair[]{new KeyValuePair(this._fieldId, (Object)date)};
    }

    @Override
    public boolean isSatisfiedBy(IValueSource inValueSource) {
        Date compareDate = new Date(System.currentTimeMillis() - this._ageMillis);
        Date entityDate = (Date)inValueSource.getFieldValue(this.getFieldId());
        return entityDate != null && entityDate.before(compareDate);
    }

}
