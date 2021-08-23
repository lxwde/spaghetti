package com.zpmc.ztos.infra.base.business.predicate;

import com.zpmc.ztos.infra.base.business.interfaces.IMetafieldId;
import com.zpmc.ztos.infra.base.business.interfaces.IValueSource;

public class LtPropertyPredicate extends PropertyPredicate {

    LtPropertyPredicate(IMetafieldId inFieldId, IMetafieldId inOtherFieldId) {
        super(inFieldId, inOtherFieldId);
    }

    @Override
    protected String getOp() {
        return " < ";
    }

    @Override
    public boolean isSatisfiedBy(IValueSource inValueSource) {
        Comparable fieldValue = (Comparable)inValueSource.getFieldValue(this.getFieldId());
        Comparable compareValue = (Comparable)inValueSource.getFieldValue(this.getOtherFieldId());
        if (fieldValue == null || compareValue == null) {
            return false;
        }
        return fieldValue.compareTo(compareValue) < 0;
    }

}
