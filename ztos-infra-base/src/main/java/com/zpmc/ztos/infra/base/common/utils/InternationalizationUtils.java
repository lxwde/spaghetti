package com.zpmc.ztos.infra.base.common.utils;

import org.springframework.util.StringUtils;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class InternationalizationUtils {
    public static Locale getLocale(String inLanguage, String inCountry) {
        Locale locale = null;
        locale = StringUtils.isEmpty((String)inLanguage) ? Locale.getDefault() : (StringUtils.isEmpty((String)inCountry) ? new Locale(inLanguage) : new Locale(inLanguage, inCountry));
        return locale;
    }

    public static TimeZone getTimeZone(String inZoneID) {
        TimeZone timeZone = null;
        timeZone = StringUtils.isEmpty((String)inZoneID) ? TimeZone.getDefault() : TimeZone.getTimeZone(inZoneID);
        return timeZone;
    }

    public Calendar getCalendar(String inLanguage, String inCountry, String inZoneID) {
        Locale locale = InternationalizationUtils.getLocale(inLanguage, inCountry);
        TimeZone timeZone = InternationalizationUtils.getTimeZone(inZoneID);
        return Calendar.getInstance(timeZone, locale);
    }

}
