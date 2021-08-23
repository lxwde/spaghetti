package com.zpmc.ztos.infra.base.business.predicate;

import com.zpmc.ztos.infra.base.business.interfaces.IMetafieldId;
import com.zpmc.ztos.infra.base.business.interfaces.IValueSource;

public class NePredicate extends SimplePredicate{
    NePredicate(IMetafieldId inFieldId, Object inValue) {
        super(inFieldId, inValue);
    }

    @Override
    protected String getOp() {
        return " != ";
    }

    @Override
    public boolean isSatisfiedBy(IValueSource inValueSource) {
        Object fieldValue = inValueSource.getFieldValue(this.getFieldId());
        Object compareValue = this.getFieldValue();
        if (fieldValue == null || compareValue == null) {
            return false;
        }
        return !fieldValue.equals(compareValue);
    }

}
