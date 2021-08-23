package com.zpmc.ztos.infra.base.business.predicate;

import com.zpmc.ztos.infra.base.business.interfaces.IMetafieldId;
import com.zpmc.ztos.infra.base.business.interfaces.IValueSource;
import com.zpmc.ztos.infra.base.common.utils.DateUtil;

public class LtPredicate extends SimplePredicate {
    LtPredicate(IMetafieldId inFieldId, Object inValue) {
        super(inFieldId, inValue);
    }

    @Override
    protected String getOp() {
        return " < ";
    }

    @Override
    public boolean isSatisfiedBy(IValueSource inValueSource) {
        Comparable fieldValue = (Comparable) inValueSource.getFieldValue(this.getFieldId());
        Comparable compareValue = (Comparable) this.getFieldValue();
        if (fieldValue == null || compareValue == null) {
            return false;
        }
   //     return (fieldValue = DateUtil.getDateFromTimestamp(fieldValue)).compareTo(compareValue) < 0;
        return compareValue.compareTo(fieldValue = DateUtil.getDateFromTimestamp(fieldValue)) < 0;
    }

}
