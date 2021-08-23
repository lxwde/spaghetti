package com.zpmc.ztos.infra.base.common.utils;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.business.interfaces.IFrameworkPropertyKeys;
import com.zpmc.ztos.infra.base.common.exceptions.BizFailure;
import com.zpmc.ztos.infra.base.common.model.UserContext;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.StringTokenizer;
import java.util.TimeZone;

public class DateUtil {
    public static final String XML_DATE_FORMAT = "yyyy-MM-dd";
    public static final String XML_DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String XML_DATE_TIME_ZONE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss Z";
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final long MILLIS_PER_SECOND = 1000L;
    public static final long MILLIS_PER_MINUTE = 60000L;
    public static final long MILLIS_PER_HOUR = 3600000L;
    public static final long MILLIS_PER_DAY = 86400000L;
    public static final String HORIZON_MAX = "9999999999";
    public static final int CENTURY_START_YEAR = 2000;
    private static final String SENTINAL_MARKER = "|";
    private static final java.util.Date DATE_FOR_TIME_ONLY_VALUES;
    private static final Logger LOGGER;

    private DateUtil() {
    }

    @Deprecated
    public static java.util.Date xmlDateStringToDate(String inXmlDateStr) throws ParseException {
        java.util.Date dt;
        try {
            SimpleDateFormat simpleFormat = new SimpleDateFormat(XML_DATE_TIME_ZONE_FORMAT);
            simpleFormat.setLenient(false);
            dt = simpleFormat.parse(inXmlDateStr);
        }
        catch (ParseException pe) {
            try {
                SimpleDateFormat format = new SimpleDateFormat(XML_DATE_TIME_FORMAT);
                format.setLenient(false);
                dt = format.parse(inXmlDateStr);
            }
            catch (ParseException ape) {
                SimpleDateFormat format = new SimpleDateFormat(XML_DATE_FORMAT);
                format.setLenient(false);
                dt = format.parse(inXmlDateStr);
            }
        }
        return dt;
    }

    public static java.util.Date dateStringToDate(String inDateStr) throws ParseException {
        java.util.Date dt;
        try {
            dt = new SimpleDateFormat(DATE_TIME_FORMAT).parse(inDateStr);
        }
        catch (ParseException pe) {
            SimpleDateFormat format = new SimpleDateFormat(XML_DATE_FORMAT);
            format.setLenient(false);
            dt = format.parse(inDateStr);
        }
        return dt;
    }

    @Nullable
    public static java.util.Date parseStringToDate(String inDateStr, UserContext inUc) {
        ArrayList<SimpleDateFormat> allFormats = new ArrayList<SimpleDateFormat>();
        allFormats.add(new SimpleDateFormat("yy-MMM-dd"));
        allFormats.add(new SimpleDateFormat("yy-MM-dd"));
        allFormats.add(new SimpleDateFormat(XML_DATE_FORMAT));
        allFormats.add(new SimpleDateFormat("yyyy-MMM-dd"));
        Calendar calMin = Calendar.getInstance();
        calMin.set(1, 1800);
        Calendar calMax = Calendar.getInstance();
        calMax.set(1, 4000);
        for (SimpleDateFormat format : allFormats) {
            java.util.Date dt = DateUtil.tryToParseStringToDate(format, inDateStr);
            if (dt == null) continue;
            Calendar parsed = Calendar.getInstance();
            parsed.setTime(dt);
            int year = parsed.get(1);
            if (year < calMin.get(1) || dt.getYear() > calMax.get(1)) continue;
            return dt;
        }
        return null;
    }

    @Nullable
    private static java.util.Date tryToParseStringToDate(SimpleDateFormat inFormat, String inString) {
        try {
            return inFormat.parse(inString);
        }
        catch (ParseException e) {
            return null;
        }
    }

    @Deprecated
    public static String date2Str(java.util.Date inDate) {
        SimpleDateFormat format = new SimpleDateFormat(XML_DATE_TIME_FORMAT);
        format.setLenient(false);
        return format.format(inDate);
    }

    public static String formElapsedTimeMsg(long inStartTime, String inMessage) {
        NumberFormat numberFormatter = NumberFormat.getNumberInstance();
        numberFormatter.setMaximumFractionDigits(3);
        double elapsedSecs = (double)(TimeUtils.getCurrentTimeMillis() - inStartTime) / 1000.0;
        return "[ " + numberFormatter.format(elapsedSecs) + " secs] " + inMessage;
    }

    public static long getLongTimeOut(java.util.Date inUfvTimeOut) {
        long longHorizon;
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(inUfvTimeOut);
            StringBuilder timeFromCal = new StringBuilder();
            String monthString = DateUtil.normalizeCalendarValue(String.valueOf(cal.get(2) + 1));
            String dayString = DateUtil.normalizeCalendarValue(String.valueOf(cal.get(5)));
            String hourString = DateUtil.normalizeCalendarValue(String.valueOf(cal.get(11)));
            String minuteString = DateUtil.normalizeCalendarValue(String.valueOf(cal.get(12)));
            String yearString = cal.get(1) < 2000 ? "00" : String.valueOf(cal.get(1)).substring(2);
            timeFromCal.append(yearString).append(String.valueOf(monthString)).append(String.valueOf(dayString)).append(String.valueOf(hourString)).append(String.valueOf(minuteString));
            longHorizon = Long.parseLong(timeFromCal.toString());
        }
        catch (Exception e) {
            LOGGER.error((Object)"wrong date format ", (Throwable)e);
            longHorizon = new Long(HORIZON_MAX);
        }
        return longHorizon;
    }

    private static String normalizeCalendarValue(String inValue) {
        if (inValue == null || inValue.length() > 2 || inValue.length() < 0 || inValue.isEmpty()) {
            throw new NumberFormatException("cannot cast calendar field:" + inValue);
        }
        return inValue.length() == 2 ? inValue : "0" + inValue;
    }

    public static long getStartTime() {
        return TimeUtils.getCurrentTimeMillis();
    }

    public static java.util.Date getDateForTimeFields() {
        return new java.util.Date(DATE_FOR_TIME_ONLY_VALUES.getTime());
    }

    public static boolean isGoofyDateTimeFormat(String inString) {
        return inString != null && inString.startsWith(SENTINAL_MARKER);
    }

    public static String createGoofyDateTimeFormat(String inDate, String inTime) {
        return SENTINAL_MARKER + (inDate == null ? "" : inDate) + SENTINAL_MARKER + (inTime == null ? "" : inTime);
    }

    @Nullable
    public static String getDateFromGoofyFormat(String inString) {
        if (DateUtil.isGoofyDateTimeFormat(inString)) {
            if (inString.startsWith("||")) {
                return null;
            }
            StringTokenizer st = new StringTokenizer(inString, SENTINAL_MARKER);
            if (st.hasMoreTokens()) {
                return st.nextToken();
            }
        }
        return null;
    }

    @Nullable
    public static String getTimeFromGoofyFormat(String inString) {
        if (StringUtils.isNotEmpty((String)inString) && DateUtil.isGoofyDateTimeFormat(inString)) {
            if (inString.startsWith("||")) {
                String s = inString.substring(2);
                if (StringUtils.isEmpty((String)s)) {
                    return null;
                }
                return s;
            }
            StringTokenizer st = new StringTokenizer(inString, SENTINAL_MARKER);
            if (st.hasMoreTokens()) {
                st.nextToken();
                if (st.hasMoreTokens()) {
                    return st.nextToken();
                }
                return "";
            }
        }
        return null;
    }

    @Nullable
    public static synchronized String convertDateToLocalTime(java.util.Date inDate, TimeZone inTimeZone) {
        if (inDate == null) {
            return null;
        }
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_TIME_FORMAT);
        formatter.setTimeZone(inTimeZone);
        return formatter.format(inDate);
    }

    @Nullable
    public static java.util.Date getUTCTimestampFromLocal(TimeZone inFromZone, java.util.Date inTime) {
        if (inFromZone == null) {
            return inTime;
        }
        if (inTime == null) {
            return null;
        }
        java.util.Date date = DateUtil.convertLocalDateToLocalDateWithTZInfo(inFromZone, inTime);
        int offset = inFromZone.getOffset(date.getTime());
        return new java.util.Date(inTime.getTime() - (long)offset);
    }

    @NotNull
    private static java.util.Date convertLocalDateToLocalDateWithTZInfo(TimeZone inFromZone, java.util.Date inTime) {
        java.util.Date newDate;
        Calendar inCal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        inCal.setLenient(false);
        inCal.setTimeInMillis(inTime.getTime());
        try {
            Calendar calendar = DateUtil.createCalendar(inCal.get(1), inCal.get(2), inCal.get(5), inCal.get(11), inCal.get(12), inFromZone);
            newDate = DateUtil.getDSTSafeCalendarTime(calendar);
        }
        catch (Exception e) {
            if (e.getMessage().contains("HOUR_OF_DAY")) {
                Calendar calendar = DateUtil.createCalendar(inCal.get(1), inCal.get(2), inCal.get(5), inCal.get(11) - 1, inCal.get(12), inFromZone);
                try {
                    newDate = DateUtil.getDSTSafeCalendarTime(calendar);
                }
                catch (Exception e1) {
                    throw new IllegalArgumentException("Illegal date", e);
                }
            }
            throw BizFailure.create(IFrameworkPropertyKeys.VALIDATION__INVALID_DATETIME, e, inTime);
        }
        return newDate;
    }

    private static Calendar createCalendar(int inYear, int inMonth, int inDay, int inHour, int inMinute, TimeZone inTimeZone) {
        Calendar calendar = Calendar.getInstance(inTimeZone);
        calendar.setLenient(false);
        calendar.set(1, inYear);
        calendar.set(2, inMonth);
        calendar.set(5, inDay);
        calendar.set(11, inHour);
        calendar.set(12, inMinute);
        calendar.set(13, 0);
        calendar.set(14, 0);
        return calendar;
    }

    @Nullable
    public static java.util.Date getLocalTimestampFromUTC(TimeZone inToZone, java.util.Date inTime) {
        if (inToZone == null) {
            return inTime;
        }
        if (inTime == null) {
            return null;
        }
        int offset = inToZone.getOffset(inTime.getTime());
        return new java.util.Date(inTime.getTime() + (long)offset);
    }

    public static String getMillisecondFormattedDate(java.util.Date inDate) {
        TimeZone zone = TimeZone.getDefault();
        new SimpleDateFormat(TIMESTAMP_FORMAT).setTimeZone(zone);
        String formattedDate = inDate != null ? new SimpleDateFormat(TIMESTAMP_FORMAT).format(inDate) : "";
        return formattedDate;
    }

    public static boolean isSameDay(java.util.Date inDateA, java.util.Date inDateB, TimeZone inTimeZone) {
        Calendar calendar = Calendar.getInstance(inTimeZone);
        calendar.setTime(inDateA);
        int yearA = calendar.get(1);
        int monthA = calendar.get(2);
        int dayA = calendar.get(5);
        calendar.setTime(inDateB);
        int yearB = calendar.get(1);
        int monthB = calendar.get(2);
        int dayB = calendar.get(5);
        boolean sameDay = yearA == yearB && monthA == monthB && dayA == dayB;
        return sameDay;
    }

    public static java.util.Date getTodaysDate(TimeZone inTimeZone) {
        Calendar nowCal = Calendar.getInstance(inTimeZone);
        nowCal.setLenient(false);
        nowCal.set(11, 0);
        nowCal.set(12, 0);
        nowCal.set(13, 0);
        nowCal.set(14, 0);
        return nowCal.getTime();
    }

    public static double differenceInDays(java.util.Date inStartDate, java.util.Date inEndDate, TimeZone inTimeZone) {
        long endL = inEndDate.getTime() + (long)inTimeZone.getOffset(inEndDate.getTime());
        long startL = inStartDate.getTime() + (long)inTimeZone.getOffset(inStartDate.getTime());
        double days = (double)(endL - startL) * 1.0 / 8.64E7;
        return days;
    }

    public static double differenceInHours(java.util.Date inStartDate, java.util.Date inEndDate) {
        double hours = (double)(inEndDate.getTime() - inStartDate.getTime()) * 1.0 / 3600000.0;
        return hours;
    }

    public static int differenceInTruncatedDays(java.util.Date inStartDate, java.util.Date inEndDate, TimeZone inTimeZone) {
        double days = DateUtil.differenceInDays(inStartDate, inEndDate, inTimeZone);
        int truncatedDays = (int)days;
        return truncatedDays;
    }

    public static double differenceInHoursMinusDays(java.util.Date inStartDate, java.util.Date inEndDate, TimeZone inTimeZone) {
        double days = DateUtil.differenceInDays(inStartDate, inEndDate, inTimeZone);
        double hours = (days - (double)((int)days)) * 24.0;
        return hours;
    }

    public static double differenceInMinutesMinusDaysAndHours(java.util.Date inStartDate, java.util.Date inEndDate, TimeZone inTimeZone) {
        double hours = DateUtil.differenceInHoursMinusDays(inStartDate, inEndDate, inTimeZone);
        double minutes = (hours - (double)((int)hours)) * 60.0;
        return minutes;
    }

    public static double roundDifferenceInMinutesMinusDaysAndHours(java.util.Date inStartDate, java.util.Date inEndDate, TimeZone inTimeZone) {
        return Math.round(DateUtil.differenceInMinutesMinusDaysAndHours(inStartDate, inEndDate, inTimeZone));
    }

    public static long differenceInCalendarDays(@NotNull java.util.Date inDateA, @NotNull java.util.Date inDateB, TimeZone inTimeZone) {
        TimeZone tz = inTimeZone == null ? TimeZone.getDefault() : inTimeZone;
        Calendar calendar = Calendar.getInstance(tz);
        calendar.setLenient(false);
        calendar.setTime(inDateA);
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
        Calendar calendar2 = Calendar.getInstance(tz);
        calendar2.setLenient(false);
        calendar2.setTime(inDateB);
        calendar2.set(11, 0);
        calendar2.set(12, 0);
        calendar2.set(13, 0);
        calendar2.set(14, 0);
        try {
            long offset = inTimeZone.getOffset(calendar2.getTimeInMillis()) - inTimeZone.getOffset(calendar.getTimeInMillis());
            return (calendar2.getTimeInMillis() - calendar.getTimeInMillis() + offset) / 86400000L;
        }
        catch (Exception e) {
            calendar.set(11, 1);
            calendar2.set(11, 1);
            return (calendar2.getTimeInMillis() - calendar.getTimeInMillis()) / 86400000L;
        }
    }

    public static Comparable<?> getDateFromTimestamp(Comparable<?> inFieldValue) {
        if (inFieldValue instanceof Timestamp) {
            inFieldValue = new java.util.Date(((Timestamp)inFieldValue).getTime());
        }
        return inFieldValue;
    }

    public static java.util.Date removeTime(java.util.Date inDate, TimeZone inTimeZone) {
        Calendar calendar = Calendar.getInstance(inTimeZone == null ? TimeZone.getDefault() : inTimeZone);
        calendar.setLenient(false);
        calendar.setTimeInMillis(inDate.getTime());
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
        java.util.Date dateWithoutTime = calendar.getTime();
        return dateWithoutTime;
    }

    public static String addDateToFileName(String inFileName, TimeZone inTimeZone) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyyMMdd_HHmm");
        dateFormatter.setTimeZone(inTimeZone);
        return inFileName + "_" + dateFormatter.format(new java.util.Date());
    }

    @NotNull
    public static java.util.Date getTimeOnlyFromDate(java.util.Date inDate, TimeZone inTimeZone) {
        Calendar cal = Calendar.getInstance(inTimeZone);
        cal.setTime(inDate);
        int hour = cal.get(11);
        int minute = cal.get(12);
        Calendar newCal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        newCal.set(1, 1970);
        newCal.set(2, 0);
        newCal.set(5, 1);
        newCal.set(11, hour);
        newCal.set(12, minute);
        newCal.set(13, 0);
        newCal.set(14, 0);
        return newCal.getTime();
    }

    public static boolean javaOrSqlDateBefore(java.util.Date inDateThis, java.util.Date inDateAnother) {
        if (inDateThis != null && inDateAnother != null) {
            return DateUtil.javaOrSqlDateCompareTo(inDateThis, inDateAnother) < 0;
        }
        return false;
    }

    public static boolean javaOrSqlDateAfter(java.util.Date inDateThis, java.util.Date inDateAnother) {
        if (inDateThis != null && inDateAnother != null) {
            return DateUtil.javaOrSqlDateCompareTo(inDateThis, inDateAnother) > 0;
        }
        return false;
    }

    public static boolean javaOrSqlDateEqual(java.util.Date inDateThis, java.util.Date inDateAnother) {
        if (inDateThis != null && inDateAnother != null) {
            return DateUtil.javaOrSqlDateCompareTo(inDateThis, inDateAnother) == 0;
        }
        return false;
    }

    public static int javaOrSqlDateCompareTo(java.util.Date inDateThis, java.util.Date inDateAnother) {
        if (inDateAnother instanceof Date || inDateAnother instanceof Time || inDateAnother instanceof Timestamp) {
            long millis = inDateAnother.getTime();
            return inDateThis.compareTo(new java.util.Date(millis));
        }
        return inDateThis.compareTo(inDateAnother);
    }

    public static java.util.Date getDSTSafeCalendarTime(Calendar inCalendar) {
        try {
            return inCalendar.getTime();
        }
        catch (Exception e) {
            try {
                inCalendar.set(11, 1);
            }
            catch (Exception e1) {
                inCalendar.set(11, 0);
                return inCalendar.getTime();
            }
            return inCalendar.getTime();
        }
    }

    public static long getDSTSafeCalendarTimeInMs(Calendar inCalendar) {
        try {
            return inCalendar.getTimeInMillis();
        }
        catch (Exception e) {
            try {
                inCalendar.set(11, 1);
            }
            catch (Exception e1) {
                inCalendar.set(11, 0);
                return inCalendar.getTimeInMillis();
            }
            return inCalendar.getTimeInMillis();
        }
    }

    static {
        Calendar twelveAmCal = Calendar.getInstance();
        twelveAmCal.set(1, 1970);
        twelveAmCal.set(2, 0);
        twelveAmCal.set(5, 1);
        twelveAmCal.set(11, 0);
        twelveAmCal.set(12, 0);
        twelveAmCal.set(13, 0);
        twelveAmCal.set(14, 0);
        DATE_FOR_TIME_ONLY_VALUES = twelveAmCal.getTime();
        LOGGER = Logger.getLogger(DateUtil.class);
    }
}
