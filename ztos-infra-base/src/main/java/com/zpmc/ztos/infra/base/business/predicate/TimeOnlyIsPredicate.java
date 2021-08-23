package com.zpmc.ztos.infra.base.business.predicate;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.business.interfaces.IMetafieldId;
import com.zpmc.ztos.infra.base.business.interfaces.IValueSource;
import com.zpmc.ztos.infra.base.common.utils.DateUtil;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.util.Date;
import java.util.TimeZone;

public class TimeOnlyIsPredicate extends TimeOnlyPredicate {
    TimeOnlyIsPredicate(IMetafieldId inFieldId, @Nullable Object inValue, TimeZone inTimeZone) {
        super(inFieldId, inValue, inTimeZone);
    }

    @Override
    protected String getOp() {
        return " casttime ";
    }

    @Override
    @NotNull
    public String toHqlString(String inEntityAlias) {
        return super.toHqlString(inEntityAlias) + " = (?)";
    }

    @Override
    public boolean isSatisfiedBy(IValueSource inValueSource) {
        Comparable fieldValue = (Comparable)inValueSource.getFieldValue(this.getFieldId());
        Date compareTime = DateUtil.getTimeOnlyFromDate((Date)fieldValue, this.getTimeZone());
        Date compareValue = this.getRawDate();
        if (fieldValue == null || compareValue == null) {
            return false;
        }
        return ((Object)compareTime).equals(compareValue);
    }

}
