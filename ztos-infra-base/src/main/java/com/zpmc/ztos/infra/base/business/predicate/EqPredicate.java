package com.zpmc.ztos.infra.base.business.predicate;

import com.zpmc.ztos.infra.base.business.interfaces.IMetafieldId;
import com.zpmc.ztos.infra.base.business.interfaces.IValueSource;
import com.zpmc.ztos.infra.base.common.enums.AtomizedEnum;

public class EqPredicate extends SimplePredicate {
    EqPredicate(IMetafieldId inFieldId, Object inValue) {
        super(inFieldId, inValue);
    }

    @Override
    protected String getOp() {
        return " = ";
    }

    @Override
    public boolean isSatisfiedBy(IValueSource inValueSource) {
        Object fieldValue = inValueSource.getFieldValue(this.getFieldId());
        if (fieldValue == null) {
            return false;
        }
        Object requiredValue = this.getFieldValue();
        if (fieldValue instanceof Integer && requiredValue instanceof Long) {
            return new Long(((Integer)fieldValue).longValue()).equals(requiredValue);
        }
        if (fieldValue instanceof Short && requiredValue instanceof Long) {
            return new Long(((Short)fieldValue).longValue()).equals(requiredValue);
        }
        if (fieldValue instanceof AtomizedEnum && requiredValue instanceof String) {
            return fieldValue.equals(AtomizedEnum.resolve(fieldValue.getClass(), (String)requiredValue));
        }
        return fieldValue.equals(requiredValue);
    }

}
