package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;

import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public interface IDateConverter {
    public String getDateTimeString(TimeZone var1, Date var2);

    public String getDateOnlyString(TimeZone var1, Date var2);

    public String getDateTimeString(Date var1);

    public Date parseDateTimeString(Locale var1, String var2) throws BizViolation;

    public String getDateTimePattern();

    public String getDateTimeString(TimeZone var1, String var2, Date var3);

    public String getDateTimeString(String var1, Date var2);
}
