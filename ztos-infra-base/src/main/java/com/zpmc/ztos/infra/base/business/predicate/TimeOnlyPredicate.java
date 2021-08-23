package com.zpmc.ztos.infra.base.business.predicate;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.business.interfaces.IMetafieldId;
import com.zpmc.ztos.infra.base.common.utils.FieldUtils;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public abstract class TimeOnlyPredicate extends SimplePredicate {
    private final Date _rawDate;
    private final TimeZone _timeZone;

    TimeOnlyPredicate(IMetafieldId inFieldId, @Nullable Object inValue, TimeZone inTimeZone) {
        super(inFieldId, (Object)new SimpleDateFormat("HH:mm").format(inValue));
        this._rawDate = (Date)inValue;
        this._timeZone = inTimeZone;
    }

    @Override
    @NotNull
    public String toHqlString(String inEntityAlias) {
        String aliasedField = FieldUtils.getAliasedField(inEntityAlias, this.getFieldId());
        return this.getOp() + "(" + aliasedField + ")";
    }

    public Date getRawDate() {
        return this._rawDate;
    }

    public TimeZone getTimeZone() {
        return this._timeZone;
    }

}
