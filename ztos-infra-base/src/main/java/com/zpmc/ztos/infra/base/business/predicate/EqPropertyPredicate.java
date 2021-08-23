package com.zpmc.ztos.infra.base.business.predicate;

import com.zpmc.ztos.infra.base.business.interfaces.IMetafieldId;
import com.zpmc.ztos.infra.base.business.interfaces.IValueSource;

public class EqPropertyPredicate extends PropertyPredicate {
    EqPropertyPredicate(IMetafieldId inFieldId, IMetafieldId inOtherFieldId) {
        super(inFieldId, inOtherFieldId);
    }

    @Override
    protected String getOp() {
        return " = ";
    }

    @Override
    public boolean isSatisfiedBy(IValueSource inValueSource) {
        Object fieldValue = inValueSource.getFieldValue(this.getFieldId());
        Object compareValue = inValueSource.getFieldValue(this.getOtherFieldId());
        if (fieldValue == null || compareValue == null) {
            return false;
        }
        return fieldValue.equals(compareValue);
    }
}
