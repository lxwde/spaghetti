package com.zpmc.ztos.infra.base.common.utils;

import com.zpmc.ztos.infra.base.business.enums.framework.TimePeriodTypeEnum;
import com.zpmc.ztos.infra.base.common.model.IntervalTime;
import com.zpmc.ztos.infra.base.common.model.RelativeTimePeriod;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeUtils {
    public static final long YEAR_IN_MILLIS = 31536000000L;
    public static final long REASONABLE_YEARS = 99L;
    public static final Date EARLIEST_REASONABLE_DATE = new Date(TimeUtils.getCurrentTimeMillis() - 3122064000000L);
    public static final Date LATEST_REASONABLE_DATE = new Date(TimeUtils.getCurrentTimeMillis() + 3122064000000L);
    private static final String RELATIVE_TIME_PERIOD_REGEX = "(\\-|\\+)([1-9][0-9]*)(w|d|h|m)";
    private static final Pattern RELATIVE_TIME_PERIOD_PATTERN = Pattern.compile("(\\-|\\+)([1-9][0-9]*)(w|d|h|m)");
    private static final String MTP_SIGN_REGEX = "(\\-|\\+)?";
    private static final String MTP_TIME_PERIOD_REGEX = "([1-9][0-9]*)(w|d|h|m)";
    private static final String MTP_REGEX = "(\\-|\\+)?\\s*([1-9][0-9]*)(w|d|h|m)(\\s*([1-9][0-9]*)(w|d|h|m))*";
    private static final String TIME_PERIOD_SIGN_REGEX = "\\G(\\-|\\+)";
    private static final String TIME_PERIOD_REGEX = "\\G\\s*([1-9][0-9]*)(w|d|h|m)";
    private static final Pattern MULTI_TIME_PERIOD_PATTERN = Pattern.compile("(\\-|\\+)?\\s*([1-9][0-9]*)(w|d|h|m)(\\s*([1-9][0-9]*)(w|d|h|m))*");
    private static final Pattern SIGN_PATTERN = Pattern.compile("\\G(\\-|\\+)");
    private static final Pattern TIME_PERIOD_PATTERN = Pattern.compile("\\G\\s*([1-9][0-9]*)(w|d|h|m)");

    private TimeUtils() {
    }

    public static Date getCurrentTime() {
        return new Date(TimeUtils.getCurrentTimeMillis());
    }

    public static long getCurrentTimeMillis() {
        return System.currentTimeMillis();
    }

    @Nullable
    public static RelativeTimePeriod parseRelativeTimePeriod(Object inRTPeriodObj) {
        Matcher matcher;
        RelativeTimePeriod rtPeriod = null;
        if (inRTPeriodObj instanceof RelativeTimePeriod) {
            rtPeriod = (RelativeTimePeriod)inRTPeriodObj;
        } else if (inRTPeriodObj instanceof String && (matcher = RELATIVE_TIME_PERIOD_PATTERN.matcher((String)inRTPeriodObj)).matches()) {
            int sign = "-".equals(matcher.group(1)) ? -1 : 1;
            String periodUnitsStr = matcher.group(2);
            TimePeriodTypeEnum periodType = TimePeriodTypeEnum.get(matcher.group(3));
            int periodUnits = Integer.parseInt(periodUnitsStr) * sign;
            rtPeriod = new RelativeTimePeriod(periodType, periodUnits);
        }
        return rtPeriod;
    }

    @Nullable
    public static RelativeTimePeriod parseMultiTimeStringAsRelativeTimePeriod(String inRTPeriodStr) {
        RelativeTimePeriod rtPeriod = null;
        Matcher matcher = MULTI_TIME_PERIOD_PATTERN.matcher(inRTPeriodStr);
        if (matcher.matches()) {
            int sign = 1;
            matcher.region(0, inRTPeriodStr.length());
            if (matcher.usePattern(SIGN_PATTERN).find()) {
                sign = "-".equals(matcher.group(1)) ? -1 : 1;
            }
            long totalMin = 0L;
            while (matcher.usePattern(TIME_PERIOD_PATTERN).find()) {
                String periodUnitsStr = matcher.group(1);
                TimePeriodTypeEnum periodType = TimePeriodTypeEnum.get(matcher.group(2));
                totalMin += (long) Integer.parseInt(periodUnitsStr) * periodType.getFactorToConvertToLowest();
            }
            long periodUnits = totalMin * (long)sign;
            rtPeriod = new RelativeTimePeriod(TimePeriodTypeEnum.MINUTE, (int)periodUnits);
        }
        return rtPeriod;
    }

    public static IntervalTime parseMultiTimeStringAsIntervalTime(String inRTPeriodStr) {
        IntervalTime rtPeriod = null;
        Matcher matcher = MULTI_TIME_PERIOD_PATTERN.matcher(inRTPeriodStr);
        if (matcher.matches()) {
            int sign = 1;
            HashMap<TimePeriodTypeEnum, Integer> values = new HashMap<TimePeriodTypeEnum, Integer>(TimePeriodTypeEnum.getSize());
            matcher.region(0, inRTPeriodStr.length());
            if (matcher.usePattern(SIGN_PATTERN).find()) {
                int n = sign = "-".equals(matcher.group(1)) ? -1 : 1;
            }
            while (matcher.usePattern(TIME_PERIOD_PATTERN).find()) {
                String periodUnitsStr = matcher.group(1);
                TimePeriodTypeEnum periodType = TimePeriodTypeEnum.get(matcher.group(2));
                TimeUtils.addIntervalComponent(values, periodType, Integer.parseInt(periodUnitsStr) * sign);
            }
            rtPeriod = new IntervalTime(values);
        }
        return rtPeriod;
    }

    private static void addIntervalComponent(Map<TimePeriodTypeEnum, Integer> inIntervalComponents, TimePeriodTypeEnum inPeriodType, int inUnits) {
        if (inIntervalComponents.containsKey((Object)inPeriodType)) {
            int value = inIntervalComponents.get((Object)inPeriodType);
            inIntervalComponents.put(inPeriodType, value + inUnits);
        } else {
            inIntervalComponents.put(inPeriodType, inUnits);
        }
    }

    public static boolean validateReasonableRange( Date inDate) {
        return TimeUtils.getCurrentTime().compareTo(inDate) > 0;
    }

}
