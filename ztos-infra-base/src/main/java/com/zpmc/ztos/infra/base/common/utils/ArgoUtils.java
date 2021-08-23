package com.zpmc.ztos.infra.base.common.utils;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.business.dataobject.ComplexDO;
import com.zpmc.ztos.infra.base.business.dataobject.FacilityDO;
import com.zpmc.ztos.infra.base.business.dataobject.OperatorDO;
import com.zpmc.ztos.infra.base.business.dataobject.YardDO;
import com.zpmc.ztos.infra.base.business.enums.argo.DataSourceEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.ScopeEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.WeekdayEnum;
import com.zpmc.ztos.infra.base.business.enums.framework.MessageLevelEnum;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.business.model.Conjunction;
import com.zpmc.ztos.infra.base.business.predicate.PredicateFactory;
import com.zpmc.ztos.infra.base.common.callbacks.CarinaPersistenceCallback;
import com.zpmc.ztos.infra.base.common.configs.ArgoConfig;
import com.zpmc.ztos.infra.base.common.contexts.PortalApplicationContext;
import com.zpmc.ztos.infra.base.common.database.HiberCache;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.common.database.PersistenceTemplate;
import com.zpmc.ztos.infra.base.common.enums.AtomizedEnum;
import com.zpmc.ztos.infra.base.common.exceptions.BizFailure;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.helps.ContextHelper;
import com.zpmc.ztos.infra.base.common.messages.MessageCollectorFactory;
import com.zpmc.ztos.infra.base.common.model.*;
import com.zpmc.ztos.infra.base.common.scopes.Complex;
import com.zpmc.ztos.infra.base.common.scopes.Facility;
import com.zpmc.ztos.infra.base.common.scopes.Operator;
import com.zpmc.ztos.infra.base.common.scopes.Yard;
import com.zpmc.ztos.infra.base.utils.CarinaUtils;
import com.zpmc.ztos.infra.base.utils.QueryUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import javax.swing.text.NumberFormatter;
import java.io.*;
import java.math.RoundingMode;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ArgoUtils {
    private static final String ENCRYPTION = "MD5";
    private static final Logger LOGGER = Logger.getLogger(ArgoUtils.class);
    public static final long MILLIS_PER_SECOND = 1000L;
    public static final long MILLIS_PER_MINUTE = 60000L;
    public static final long MILLIS_PER_HOUR = 3600000L;
    public static final long MIN_PER_HOUR = 60L;
    public static final long MIN_PER_DAY = 1440L;
    public static final long MILLIS_PER_DAY = 86400000L;
    public static final long MILLIS_PER_WEEK = 604800000L;
    public static IMessageTranslator CACHED_TRANSLATOR;
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final SimpleDateFormat FULL_DATE_TIME;
    private static final DecimalFormat MONEY;
    public static final String ARGO_TRANSACTION_CALLBACKS = "ARGO_TRANSACTION_CALLBACKS";
    public static final String INTERNAL_UPDATE_SERVICE = "INTERNAL_UPDATE_SERVICE";
    public static final String EVNT_FLEX_FIELD_PREFIX = "evntFlex";
    public static final String UNIT_FLEX_FIELD_PREFIX = "unitFlex";
    public static final String UFV_FLEX_FIELD_PREFIX = "ufvFlex";
    public static final String UNIT_CUSTOM_FLEX_FIELD_PREFIX = "unitCustomDFF";
    private static ThreadLocal tLocal;

    public static String convertDateToLocalTime(Date inDate) {
        TimeZone timeZone = ContextHelper.getThreadUserContext().getTimeZone();
        return DateUtil.convertDateToLocalTime((Date)inDate, (TimeZone)timeZone);
    }

    @Nullable
    public static Date convertDateToLocalDateTime(Date inDate, TimeZone inLocalTz) {
        Date localDate = null;
        if (inDate == null) {
            return null;
        }
        try {
            localDate = FULL_DATE_TIME.parse(DateUtil.convertDateToLocalTime((Date)inDate, (TimeZone)inLocalTz));
        }
        catch (Exception e) {
            LOGGER.error((Object)("Unable to parse the date : " + inDate.toString() + ". Exception : " + e));
        }
        return localDate;
    }

    public static String convertDateToLocalDateOnly(Date inDate) {
        TimeZone timeZone = ContextHelper.getThreadUserContext().getTimeZone();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        formatter.setTimeZone(timeZone);
        return formatter.format(inDate);
    }

    public static boolean datesInRange(Date inFromDate, Date inToDate, Date inFromRange, Date inToRange, TimeZone inTimeZone) {
        if (inFromDate == null || inToDate == null || inFromRange == null || inToRange == null) {
            return false;
        }
        if (inTimeZone == null) {
            inTimeZone = TimeZone.getDefault();
        }
        Calendar instance = Calendar.getInstance(inTimeZone);
        instance.setTime(inFromDate);
        inFromDate = instance.getTime();
        instance.setTime(inToDate);
        inToDate = instance.getTime();
        instance.setTime(inFromRange);
        inFromRange = instance.getTime();
        instance.setTime(inToRange);
        inToRange = instance.getTime();
        if (inFromDate.after(inToDate) || inToDate.before(inFromDate)) {
            return false;
        }
        return !inFromDate.before(inFromRange) && !inFromDate.after(inToRange) && !inToDate.before(inFromRange) && !inToDate.after(inToRange);
    }

    public static Date timeNow() {
        return new Date(System.currentTimeMillis());
    }

    public static long timeNowMillis() {
        return System.currentTimeMillis();
    }

    public static String timeSpanToString(Long inTimeSpan) {
        String result = null;
        if (inTimeSpan != null) {
            long timeSpan = inTimeSpan;
            if (timeSpan < 0L) {
                timeSpan = -timeSpan;
            }
            long hours = timeSpan / 3600000L;
            long remainder = timeSpan - hours * 3600000L;
            long minutes = remainder / 60000L;
            long seconds = (remainder -= minutes * 60000L) / 1000L;
            long milliseconds = remainder - seconds * 1000L;
            NumberFormat format = NumberFormat.getNumberInstance();
            format.setMinimumIntegerDigits(2);
            format.setMaximumIntegerDigits(3);
            NumberFormatter formatter = new NumberFormatter();
            formatter.setFormat(format);
            try {
                result = formatter.valueToString(new Long(hours)) + ":" + formatter.valueToString(new Long(minutes)) + ":" + formatter.valueToString(new Long(seconds));
                format.setMinimumIntegerDigits(3);
                formatter.setFormat(format);
                result = result + "." + formatter.valueToString(new Long(milliseconds));
                if (inTimeSpan < 0L) {
                    result = "-" + result;
                }
            }
            catch (ParseException e) {
                LOGGER.error((Object)"timeSpanToString: Exception occured when resolving time span", (Throwable)e);
            }
        }
        return result;
    }

    public static void carefulDelete(Object inEntity) {
        ArgoUtils.carefulDelete(inEntity, false);
    }

    public static void carefulDelete(Object inEntity, boolean inDatabaseDelete) {
        if (inEntity != null) {
            DatabaseEntity entity = (DatabaseEntity)inEntity;
            String entityName = entity.getEntityName();
            String humanKey = entity.getHumanReadableKey();
            Serializable primaryKey = entity.getPrimaryKey();
            try {
                HibernateApi hibernateApi = HibernateApi.getInstance();
                hibernateApi.delete(inEntity, inDatabaseDelete);
                hibernateApi.flush();
                String logMessage = ArgoUtils.getDeleteMessage(inDatabaseDelete, entityName, humanKey, primaryKey, true);
                LOGGER.warn((Object)logMessage);
            }
            catch (RuntimeException e) {
                Throwable rootCause = CarinaUtils.unwrap((Throwable)e);
                String logErrorMessage = ArgoUtils.getDeleteMessage(inDatabaseDelete, entityName, humanKey, primaryKey, false);
                String msg = "\n\n" + logErrorMessage + "\n" + CarinaUtils.getCompactStackTrace((Throwable)rootCause);
                LOGGER.error((Object)msg);
                throw e;
            }
        }
    }

    private static String getDeleteMessage(boolean inDatabaseDelete, String inEntityName, String inHumanKey, Serializable inPrimaryKey, boolean inSuccessful) {
        StringBuilder sb = new StringBuilder();
        sb.append("carefulDelete: ");
        if (inDatabaseDelete) {
            sb.append("hard delete ");
        } else {
            sb.append("soft delete ");
        }
        if (inSuccessful) {
            sb.append("successful for [");
        } else {
            sb.append("failed for [");
        }
        sb.append(inEntityName);
        sb.append("/");
        sb.append(inHumanKey);
        sb.append("/");
        sb.append(inPrimaryKey);
        sb.append("]");
        return sb.toString();
    }

    public static <K, V extends Comparable<V>> Map<K, V> sortMapByValue(Map<K, V> inMap) {
        ArrayList<Map.Entry<K, V>> entries = new ArrayList<Map.Entry<K, V>>(inMap.entrySet());
        Collections.sort(entries, new Comparator<Map.Entry<K, V>>(){

            @Override
            public int compare(Map.Entry<K, V> inE1, Map.Entry<K, V> inE2) {
                return ((Comparable)inE1.getValue()).compareTo(inE2.getValue());
            }
        });
        LinkedHashMap result = new LinkedHashMap();
        for (Map.Entry entry : entries) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }

    public static String createRandomId() throws NoSuchAlgorithmException {
        byte[] bytes = new byte[24];
        SecureRandom random = new SecureRandom();
        random.nextBytes(bytes);
        bytes = MessageDigest.getInstance(ENCRYPTION).digest(bytes);
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < bytes.length; ++i) {
            byte b1 = (byte)((bytes[i] & 0xF0) >> 4);
            byte b2 = (byte)(bytes[i] & 0xF);
            result.append(ArgoUtils.toHex(b1));
            result.append(ArgoUtils.toHex(b2));
        }
        return result.toString();
    }

    private static char toHex(byte inB) {
        if (inB < 10) {
            return (char)(48 + inB);
        }
        return (char)(65 + inB - 10);
    }

    public static IScopeEnum getScopeEnum(int inLevel) {
        switch (inLevel) {
            case 1: {
                return ScopeEnum.GLOBAL;
            }
            case 2: {
                return ScopeEnum.OPERATOR;
            }
            case 3: {
                return ScopeEnum.COMPLEX;
            }
            case 4: {
                return ScopeEnum.FACILITY;
            }
            case 5: {
                return ScopeEnum.YARD;
            }
        }
        return ScopeEnum.GLOBAL;
    }

    @Nullable
    public static Serializable getScopeGKey(int inScopeLevel, String inScopeId) {
        if (inScopeId == null || inScopeLevel == ArgoScope.GLOBAL.intValue()) {
            return null;
        }
        if (inScopeLevel == ArgoScope.OPERATOR.intValue()) {
            IDomainQuery dq = QueryUtils.createDomainQuery((String)"Operator").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoField.OPR_ID, (Object)inScopeId));
            dq.setScopingEnabled(false);
            Serializable[] oprGkeys = HibernateApi.getInstance().findPrimaryKeysByDomainQuery(dq);
            return oprGkeys != null && oprGkeys.length > 0 ? oprGkeys[0] : null;
        }
        if (inScopeLevel == ArgoScope.COMPLEX.intValue()) {
            IDomainQuery dq = QueryUtils.createDomainQuery((String)"Complex").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoField.CPX_ID, (Object)inScopeId));
            dq.setScopingEnabled(false);
            Serializable[] cpxGkeys = HibernateApi.getInstance().findPrimaryKeysByDomainQuery(dq);
            return cpxGkeys != null && cpxGkeys.length > 0 ? cpxGkeys[0] : null;
        }
        if (inScopeLevel == ArgoScope.FACILITY.intValue()) {
            IDomainQuery dq = QueryUtils.createDomainQuery((String)"Facility").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoField.FCY_ID, (Object)inScopeId));
            dq.setScopingEnabled(false);
            Serializable[] fcyGkeys = HibernateApi.getInstance().findPrimaryKeysByDomainQuery(dq);
            return fcyGkeys != null && fcyGkeys.length > 0 ? fcyGkeys[0] : null;
        }
        if (inScopeLevel == ArgoScope.YARD.intValue()) {
            IDomainQuery dq = QueryUtils.createDomainQuery((String)"Yard").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoField.YRD_ID, (Object)inScopeId));
            dq.setScopingEnabled(false);
            Serializable[] yrdGkeys = HibernateApi.getInstance().findPrimaryKeysByDomainQuery(dq);
            return yrdGkeys != null && yrdGkeys.length > 0 ? yrdGkeys[0] : null;
        }
        return null;
    }

    public static String getScopeId(int inScopeLevel, Long inScopeGKey) {
        if (inScopeLevel == ArgoScope.GLOBAL.intValue()) {
            return null;
        }
        if (inScopeLevel == ArgoScope.OPERATOR.intValue()) {
            Operator operator = (Operator) HibernateApi.getInstance().load(Operator.class, (Serializable)inScopeGKey);
            return operator.getId();
        }
        if (inScopeLevel == ArgoScope.COMPLEX.intValue()) {
            Complex complex = (Complex) HibernateApi.getInstance().load(Complex.class, (Serializable)inScopeGKey);
            return complex.getId();
        }
        if (inScopeLevel == ArgoScope.FACILITY.intValue()) {
            Facility facility = (Facility) HibernateApi.getInstance().load(Facility.class, (Serializable)inScopeGKey);
            return facility.getId();
        }
        if (inScopeLevel == ArgoScope.YARD.intValue()) {
            Yard yard = (Yard) HibernateApi.getInstance().load(Yard.class, (Serializable)inScopeGKey);
            return yard.getId();
        }
        return null;
    }

    public static String getArgoScopeString(ScopeCoordinates inScopeCoord) {
        StringBuilder buf = new StringBuilder();
        Serializable opScope = inScopeCoord.getScopeLevelCoord(ArgoScope.OPERATOR.intValue());
        OperatorDO operator = null;
        if (opScope != null) {
            operator = (Operator) Roastery.getHibernateApi().get(Operator.class, opScope);
        }
        if (operator == null) {
            buf.append("-/-/-/-");
            return buf.toString();
        }
        buf.append(operator.getOprId());
        buf.append("/");
        Serializable cpxScope = inScopeCoord.getScopeLevelCoord(ArgoScope.COMPLEX.intValue());
        ComplexDO complex = null;
        if (cpxScope != null) {
            complex = (Complex) Roastery.getHibernateApi().get(Complex.class, cpxScope);
        }
        if (complex == null) {
            buf.append("-/-/-");
            return buf.toString();
        }
        buf.append(complex.getCpxId());
        buf.append("/");
        Serializable fcyScope = inScopeCoord.getScopeLevelCoord(ArgoScope.FACILITY.intValue());
        FacilityDO facility = null;
        if (fcyScope != null) {
            facility = (Facility) Roastery.getHibernateApi().get(Facility.class, fcyScope);
        }
        if (facility == null) {
            buf.append("-/-");
            return buf.toString();
        }
        buf.append(facility.getFcyId());
        buf.append("/");
        Serializable yrdScope = inScopeCoord.getScopeLevelCoord(ArgoScope.YARD.intValue());
        YardDO yard = null;
        if (yrdScope != null) {
            yard = (Yard) Roastery.getHibernateApi().get(Yard.class, yrdScope);
        }
        if (yard == null) {
            buf.append("-");
            return buf.toString();
        }
        buf.append(yard.getYrdId());
        return buf.toString();
    }

    public static void deleteByPrimaryKey(UserContext inUserContext, Class inEntityClass, Serializable[] inKeys) {
//        PersistenceTemplate pt = new PersistenceTemplate(inUserContext);
//        DeleteCallback unitJobCallback = new DeleteCallback(inEntityClass);
//        for (int i = 0; i < inKeys.length; ++i) {
//            unitJobCallback.setPrimaryKey(inKeys[i]);
//            pt.invoke((CarinaPersistenceCallback)unitJobCallback);
//        }
    }

    public static boolean isInSet(AtomizedEnum inCandidate, AtomizedEnum[] inMembers) {
        for (int i = 0; i < inMembers.length; ++i) {
            AtomizedEnum member = inMembers[i];
            if (!member.equals((Object)inCandidate)) continue;
            return true;
        }
        return false;
    }

    public static IMessageCollector createMessageCollector(Iterator inMsgs) {
        IMessageCollector mc = MessageCollectorFactory.createMessageCollector();
        ArrayList<String> msgList = new ArrayList<String>();
        while (inMsgs.hasNext()) {
            IMetafieldUserMessage usrmsg = (IMetafieldUserMessage)inMsgs.next();
            if (msgList.contains(usrmsg.toString())) continue;
            mc.appendMessage((IUserMessage)usrmsg);
            msgList.add(usrmsg.toString());
        }
        return mc;
    }

    public static boolean isValidPosSlotOnCarriage(String inLocSlotOnCarriage) {
        return inLocSlotOnCarriage != null && !inLocSlotOnCarriage.equals("0");
    }

    public static double round(double inValue, int inSignificantDigits) {
        double ten = 10.0;
        double logValue = Math.log(Math.abs(inValue)) / Math.log(10.0);
        long exponent = (long)logValue;
        double magnitude = Math.pow(10.0, exponent);
        double mantissa = inValue / magnitude;
        double shift = Math.pow(10.0, inSignificantDigits - 1);
        inValue = (double) Math.round(mantissa * shift) / shift * magnitude;
        return inValue;
    }

    public static double roundDecimalDigits(double inValue, int inDecimalDigits) {
        double shifter = Math.pow(10.0, Math.abs(inDecimalDigits));
        return (double) Math.round(inValue * shifter) / shifter;
    }

    public static double round(double inValue, int inSignificantDigits, RoundingMode inRoundingMode, Locale inLocale) {
        if (inLocale == null) {
            inLocale = Locale.getDefault();
        }
        NumberFormat instance = NumberFormat.getInstance(inLocale);
        instance.setMaximumFractionDigits(inSignificantDigits);
        instance.setRoundingMode(inRoundingMode);
        String value = instance.format(inValue);
        try {
            Number number = instance.parse(value);
            inValue = number.doubleValue();
        }
        catch (ParseException e) {
            LOGGER.error((Object)"Unable to parse the double value", (Throwable)e);
        }
        return inValue;
    }

    public static String format(double inValue, int inSignificantDigits, RoundingMode inRoundingMode, Locale inLocale) {
        if (inLocale == null) {
            inLocale = Locale.getDefault();
        }
        NumberFormat instance = NumberFormat.getInstance(inLocale);
        instance.setMaximumFractionDigits(inSignificantDigits);
        instance.setRoundingMode(inRoundingMode);
        return instance.format(inValue);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static DatabaseEntity reloadProxy(DatabaseEntity inSourceEntity, Class inClass) {
        Serializable primaryKey = inSourceEntity.getPrimaryKey();
        if (primaryKey == null || inClass.isInstance((Object)inSourceEntity)) {
            return inSourceEntity;
        }
        Level prevLevel = LogUtils.pushLogLevel((String)"org.hibernate", (Level) Level.ERROR);
        try {
            DatabaseEntity hibernatingEntity = (DatabaseEntity) HibernateApi.getInstance().load(inClass, primaryKey);
            return hibernatingEntity;
        }
        finally {
            LogUtils.setLogLevel((String)"org.hibernate", (Level)prevLevel);
        }
    }

    public static DataSourceEnum getThreadDataSource() {
        DataSourceEnum datasource = ContextHelper.getThreadDataSource();
        return datasource != null ? datasource : DataSourceEnum.USER_LCL;
    }

    public static IPredicate createDayRangePredicate(IMetafieldId inField, TimeZone inTimeZone, int inDaysAgoStart, int inDaysAgoEnd) {
        return new Conjunction().add(PredicateFactory.ge((IMetafieldId)inField, (Object) ArgoUtils.getTimeForStartOfDay(inTimeZone, inDaysAgoStart))).add(PredicateFactory.le((IMetafieldId)inField, (Object) ArgoUtils.getTimeForEndOfDay(inTimeZone, inDaysAgoEnd)));
    }

    public static WeekdayEnum dayOfWeek2Enum(int inDayOfWeek) {
        switch (inDayOfWeek) {
            case 1: {
                return WeekdayEnum.W7_SUNDAY;
            }
            case 2: {
                return WeekdayEnum.W1_MONDAY;
            }
            case 3: {
                return WeekdayEnum.W2_TUESDAY;
            }
            case 4: {
                return WeekdayEnum.W3_WEDNESDAY;
            }
            case 5: {
                return WeekdayEnum.W4_THURSDAY;
            }
            case 6: {
                return WeekdayEnum.W5_FRIDAY;
            }
            case 7: {
                return WeekdayEnum.W6_SATURDAY;
            }
        }
        return null;
    }

    public static final Date getTimeForStartOfDay(TimeZone inTimeZone, int inDaysAgo) {
        Calendar c = ArgoUtils.getCalendarForDay(inTimeZone, inDaysAgo);
        c.set(11, 0);
        c.set(12, 0);
        c.set(13, 0);
        c.set(14, 0);
        return DateUtil.getDSTSafeCalendarTime((Calendar)c);
    }

    public static final Date getTimeForEndOfDay(TimeZone inTimeZone, int inDaysAgo) {
        Calendar c = ArgoUtils.getCalendarForDay(inTimeZone, inDaysAgo);
        c.set(11, 23);
        c.set(12, 59);
        c.set(13, 59);
        c.set(14, 999);
        return c.getTime();
    }

    public static final Date getTimeForStartOfWeek(TimeZone inTimeZone, int inWeeksAgo) {
        Calendar c = ArgoUtils.getCalendarForWeek(inTimeZone, inWeeksAgo);
        c.set(7, 1);
        c.set(11, 0);
        c.set(12, 0);
        c.set(13, 0);
        c.set(14, 0);
        return DateUtil.getDSTSafeCalendarTime((Calendar)c);
    }

    public static final Date getTimeForEndOfWeek(TimeZone inTimeZone, int inWeeksAgo) {
        Calendar c = ArgoUtils.getCalendarForWeek(inTimeZone, inWeeksAgo);
        c.set(7, 7);
        c.set(11, 23);
        c.set(12, 59);
        c.set(13, 59);
        c.set(14, 999);
        return c.getTime();
    }

    public static final Date getLocalTimeStartOfDay(Date inDate) {
        inDate = inDate == null ? new Date() : inDate;
        Calendar c = Calendar.getInstance(ContextHelper.getThreadUserTimezone());
        c.setTime(inDate);
        c.set(11, 0);
        c.set(12, 0);
        c.set(13, 0);
        c.set(14, 0);
        return DateUtil.getDSTSafeCalendarTime((Calendar)c);
    }

    public static final Date getLocalTimeEndOfDay(Date inDate) {
        inDate = inDate == null ? new Date() : inDate;
        Calendar c = Calendar.getInstance(ContextHelper.getThreadUserTimezone());
        c.setTime(inDate);
        c.set(11, 23);
        c.set(12, 59);
        c.set(13, 59);
        c.set(14, 999);
        return c.getTime();
    }

    public static final WeekdayEnum getDayOfWeek(TimeZone inTimeZone, int inDaysAgo) {
        Calendar c = ArgoUtils.getCalendarForDay(inTimeZone, inDaysAgo);
        return ArgoUtils.dayOfWeek2Enum(c.get(7));
    }

    public static final WeekdayEnum getDayOfWeek(TimeZone inTimeZone, Date inDate) {
        Calendar c = Calendar.getInstance(inTimeZone);
        c.setTimeInMillis(inDate.getTime());
        return ArgoUtils.dayOfWeek2Enum(c.get(7));
    }

    public static final Calendar getCalendarForDay(TimeZone inTimeZone, int inDaysAgo) {
        Calendar c = Calendar.getInstance(inTimeZone);
        c.setTimeInMillis(System.currentTimeMillis() - (long)inDaysAgo * 86400000L);
        return c;
    }

    public static final Calendar getCalendarForWeek(TimeZone inTimeZone, int inWeeksAgo) {
        Calendar c = Calendar.getInstance(inTimeZone);
        c.setTimeInMillis(System.currentTimeMillis() - (long)inWeeksAgo * 604800000L);
        return c;
    }

    public static IMessageTranslator getEnglishMessageTranslator() {
        if (CACHED_TRANSLATOR == null) {
            IMessageTranslatorProvider translatorProvider = (IMessageTranslatorProvider) PortalApplicationContext.getBean((String)"messageTranslatorProvider");
            CACHED_TRANSLATOR = translatorProvider.getMessageTranslator(Locale.ENGLISH);
        }
        return CACHED_TRANSLATOR;
    }

    public static IMessageTranslator getUserMessageTranslator() {
        IMessageTranslator result = null;
        ArgoUserContext userContext = (ArgoUserContext) ContextHelper.getThreadUserContext();
        if (userContext != null) {
            IMessageTranslatorProvider translatorProvider = (IMessageTranslatorProvider) PortalApplicationContext.getBean((String)"messageTranslatorProvider");
            result = translatorProvider.getMessageTranslator(userContext.getUserLocale());
        }
        return result;
    }

    public static String getEnumDefaultLabel(IAtom inAtom) {
        return ArgoUtils.getEnglishMessageTranslator().getMessage(inAtom.getDescriptionPropertyKey());
    }

    public static String getPropertyValueAsUiString(IMetafieldId inFieldId, String inPersistedString) {
        if (inPersistedString != null) {
            Object value = HiberCache.string2Property((IMetafieldId)inFieldId, (String)inPersistedString);
            if (value instanceof Long) {
                DatabaseEntity entity;
                Class propertyClass = HiberCache.getFieldClass((String)inFieldId.getFieldId());
                if (DatabaseEntity.class.isAssignableFrom(propertyClass) && (entity = (DatabaseEntity) HibernateApi.getInstance().get(propertyClass, (Serializable)((Long)value))) != null) {
                    return entity.getHumanReadableKey();
                }
            } else if (value instanceof IAtom) {
                return ArgoUtils.getEnumDefaultLabel((IAtom)value);
            }
        }
        return inPersistedString;
    }

    public static <T> String createStringFromSetValues(Collection<T> inValues, String inSeparator) {
        StringBuffer sb = null;
        if (inValues != null) {
            for (T value : inValues) {
                if (value == null) continue;
                if (sb == null) {
                    sb = new StringBuffer();
                } else {
                    sb.append(inSeparator);
                }
                sb.append(value.toString());
            }
        }
        return sb == null ? null : sb.toString();
    }

    public static IPropertyResolver getPropertyResolver(Class inValueClass) {
        IPropertyResolverRegistry resolverRegistry = (IPropertyResolverRegistry) Roastery.getBean((String)"argoPropertyResolverRegistry");
        IPropertyResolver resolver = resolverRegistry.get(inValueClass);
        if (resolver == null) {
            throw BizFailure.create((String)("Registry does not contain an IPropertyResolver for class <" + inValueClass + ">"));
        }
        return resolver;
    }

    public static InputStream getFileInputStream(String inPathName) {
        DefaultResourceLoader loader = new DefaultResourceLoader();
        Resource resource = loader.getResource(inPathName);
        try {
            return resource.getInputStream();
        }
        catch (IOException e) {
            throw BizFailure.create((String)("could not open file: " + e));
        }
    }

    public static void closeInputStream(InputStream inInputStream) {
        try {
            if (inInputStream != null) {
                inInputStream.close();
            }
        }
        catch (IOException e) {
            LOGGER.error((Object)("closeInputStream failed with:" + e));
        }
    }

    public static String loadClasspathFileAsString(String inPathName) {
        InputStream inputStream = ArgoUtils.getFileInputStream(inPathName);
        BufferedReader in = null;
        try {
            String line;
            in = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder buffer = new StringBuilder();
            while ((line = in.readLine()) != null) {
                buffer.append(line);
                buffer.append(System.getProperty("line.separator"));
            }
            String string = buffer.toString();
            return string;
        }
        catch (IOException e) {
            throw BizFailure.wrap((Throwable)e);
        }
        finally {
            if (in != null) {
                try {
                    in.close();
                }
                catch (IOException iOException) {}
            }
        }
    }

    public static String loadFileAsString(String inPathName) {
        File file = new File(inPathName);
        InputStream inputStream = ArgoUtils.getFileInputStream(inPathName);
        BufferedReader in = null;
        try {
            String line;
            in = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder buffer = new StringBuilder();
            while ((line = in.readLine()) != null) {
                buffer.append(line);
                buffer.append(System.getProperty("line.separator"));
            }
            String string = buffer.toString();
            return string;
        }
        catch (IOException e) {
            throw BizFailure.wrap((Throwable)e);
        }
        finally {
            if (in != null) {
                try {
                    in.close();
                }
                catch (IOException iOException) {}
            }
        }
    }

    public static byte[] loadBinaryFileFromDisk(String inPathName) {
        int n;
        int i;
        int bytesAvailable;
        InputStream inputStream = ArgoUtils.getFileInputStream(inPathName);
        try {
            bytesAvailable = inputStream.available();
            LOGGER.info((Object)("loadBinaryFileFromDisk: file " + inPathName + " contains " + bytesAvailable + " bytes."));
        }
        catch (IOException e) {
            ArgoUtils.closeInputStream(inputStream);
            throw BizFailure.wrap((Throwable)e);
        }
        byte[] filedata = new byte[bytesAvailable + 1];
        int totalBytesRead = 0;
        do {
            try {
                i = inputStream.read(filedata, totalBytesRead, bytesAvailable - totalBytesRead);
                n = i > 0 ? i : 0;
            }
            catch (IOException e) {
                ArgoUtils.closeInputStream(inputStream);
                throw BizFailure.create((String)("could not read file: " + inPathName + " exception:" + e));
            }
        } while (i > -1 && bytesAvailable - (totalBytesRead += n) > 0);
        LOGGER.info((Object)("loadBinaryFileFromDisk: read from file " + inPathName + ". bytes available: " + bytesAvailable + ", bytes read: " + totalBytesRead));
        ArgoUtils.closeInputStream(inputStream);
        return filedata;
    }

    public static IMessageCollector parseSnxFile(String inPathName, UserContext inUserContext) {
        IMessageCollector messageCollector = null;
        InputStream snxInputStream = null;
//        try {
//            snxInputStream = ArgoUtils.getFileInputStream(inPathName);
//            SAXBuilder builder = new SAXBuilder();
//            Document doc = builder.build(snxInputStream);
//            messageCollector = ArgoUtils.importSnxElement(doc, inUserContext);
//        }
//        catch (JDOMException inE) {
//            throw BizFailure.create((String)("Parsing failed: " + (Object)((Object)inE)));
//        }
//        catch (IOException inE) {
//            throw BizFailure.create((String)("File not found: " + inE));
//        }
//        finally {
//            ArgoUtils.closeInputStream(snxInputStream);
//        }
        return messageCollector;
    }

    @Nullable
    public static IMessageCollector importSnxElement(Document inSnx, UserContext inUc) {
        try {
            ISnxImporter importer;
            PersistenceTemplate pt = new PersistenceTemplate(inUc);
            Element rootElement = inSnx.getRootElement();
//            final String userId = rootElement.getAttributeValue("user-id");
//            final String sequenceNumber = rootElement.getAttributeValue("sequence-number");
//            final String eventTime = rootElement.getAttributeValue("event-time");
//            List elementList = rootElement.getChildren();
            final String userId = ("user-id");
            final String sequenceNumber = ("sequence-number");
            final String eventTime = ("event-time");
            List elementList = rootElement.elements();
            final Element element = (Element)elementList.iterator().next();
            String elementName = element.getName();
            try {
                importer = SnxUtil.getSnxImporterForElement(elementName);
            }
            catch (Exception e) {
                throw BizFailure.create((String)("Could not find Importer: " + e));
            }
            CarinaPersistenceCallback callback = new CarinaPersistenceCallback(){

                public void doInTransaction() {
                    block5: {
                        try {
                            importer.setScopeParameters();
                            ContextHelper.setThreadDataSource(DataSourceEnum.SNX);
                            if (userId != null) {
                                ContextHelper.setThreadExternalUser(userId);
                            }
                            if (sequenceNumber != null) {
                                ContextHelper.setThreadExternalSequenceNumber(sequenceNumber);
                            }
                            if (eventTime != null) {
                                ContextHelper.setThreadExternalEventTime(XmlUtil.toDate(eventTime, ContextHelper.getThreadUserTimezone()));
                            }
                            importer.parseElement(element);
                        }
                        catch (Exception inEx) {
                            LOGGER.error((Object)("doInTransaction: " + CarinaUtils.getStackTrace((Throwable)inEx)));
                            TransactionParms parms = TransactionParms.getBoundParms();
                            IMessageCollector msgCollector = parms.getMessageCollector();
                            if (msgCollector == null) break block5;
                            msgCollector.registerExceptions((Throwable)inEx);
                        }
                    }
                }
            };
            return pt.invoke(callback);
        }
        catch (Exception inEx) {
            throw BizFailure.create((String)("SNX Import failed: " + inEx));
        }

    }

    public static void appendMessagesToBizResponse(BizResponse inOutBizResp, List<IMetafieldUserMessage> inMessages) {
        for (IMetafieldUserMessage msg : inMessages) {
            inOutBizResp.appendMessage((IUserMessage)msg);
        }
    }

    public static boolean isEmpty(String inStr) {
        return StringUtils.isBlank((String)inStr);
    }

    @Nullable
    public static String[] splitByLenth(String inStr, int inByLenth) {
        if (ArgoUtils.isEmpty(inStr)) {
            return null;
        }
        String[] segments = inStr.split(String.format("(?<=\\G.{%d})", inByLenth));
        return segments;
    }

    public static boolean isNotEmpty(String inStr) {
        return inStr != null && !inStr.isEmpty();
    }

    public static List<List<Serializable>> divideIntoBuckets(int inNumPerBucket, @NotNull List<Serializable> inEntityGkeys) {
        int totalNumGkeys = inEntityGkeys.size();
        LinkedList<List<Serializable>> buckets = new LinkedList<List<Serializable>>();
        int fromIndex = 0;
        int toIndex = inNumPerBucket;
        while (fromIndex < totalNumGkeys) {
            if (toIndex > totalNumGkeys) {
                toIndex = totalNumGkeys;
            }
            LinkedList<Serializable> bucket = new LinkedList<Serializable>(inEntityGkeys.subList(fromIndex, toIndex));
            buckets.add(bucket);
            fromIndex += inNumPerBucket;
            toIndex += inNumPerBucket;
        }
        return buckets;
    }

    public static BizViolation appendToExceptionChain(@Nullable BizViolation inMasterBv, List inMessages) {
        if (inMessages.isEmpty()) {
            return inMasterBv;
        }
        for (Object msg : inMessages) {
            BizViolation bv = BizViolation.create((IUserMessage)((IMetafieldUserMessage)msg));
            inMasterBv = bv.appendToChain(inMasterBv);
        }
        return inMasterBv;
    }

    public static Object getMoneyValue(Object inValue) {
        Double moneyValue = 0.0;
        if (inValue != null && inValue instanceof Double) {
            moneyValue = (Double)inValue;
            return MONEY.format(moneyValue);
        }
        return moneyValue.toString();
    }

    @Nullable
    public static BizViolation unWrapAsSingleBizViolation(IMessageCollector inCollector, MessageLevelEnum inLevel) {
        List levelMessages = inCollector.getMessages(inLevel);
        BizViolation chain = BizViolation.create((IUserMessage)((IUserMessage)levelMessages.get(0)));
        for (Object message : levelMessages) {
            if (message instanceof BizViolation) {
                Iterator iterator = ((BizViolation)message).iterator();
                while (iterator.hasNext()) {
                    BizViolation violation = (BizViolation)((Object)iterator.next());
                    chain = violation.appendToChain(chain);
                }
                continue;
            }
            BizViolation violation = BizViolation.create((IUserMessage)message);
            chain = violation.appendToChain(chain);
        }
        return chain;
    }

    public static void registerArgoTransactionCallback(IArgoTransactionCallback inTransactionCallback) {
        try {
            ArrayList<IArgoTransactionCallback> callbacksList = (ArrayList<IArgoTransactionCallback>) ContextHelper.getThreadDomainQueryResult(ARGO_TRANSACTION_CALLBACKS);
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug((Object)"succesfully obtained callback list from the per transaction cache");
            }
            if (callbacksList == null) {
                callbacksList = new ArrayList<IArgoTransactionCallback>();
                ContextHelper.setThreadDomainQueryResult(ARGO_TRANSACTION_CALLBACKS, callbacksList);
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug((Object)("succesfully added flags to argo domain query cache: " + callbacksList));
                }
            }
            callbacksList.add(inTransactionCallback);
        }
        catch (Exception e) {
            Object callbacksList = null;
        }
    }

    @Nullable
    public static BizViolation checkStalenessByDirtyCheckForSameValue(BizViolation inBizViolationChain, FieldChanges inFieldChanges, String inEntityName, IMetafieldId inMetafieldId, IMetafieldId inPrimarykeyMetafieldId, Serializable inPrimaryKey, String inEntityRefNbr) {
        BizViolation bv = null;
        if (inPrimaryKey != null) {
            Long currentValue = ArgoUtils.getCurrentFieldValue(inEntityName, inMetafieldId, inPrimarykeyMetafieldId, inPrimaryKey);
            FieldChange fc = inFieldChanges.getFieldChange(inMetafieldId);
            if (fc != null && currentValue != null && fc.isPriorValueKnown()) {
                Object priorValue = fc.getPriorValue();
                Object newValue = fc.getNewValue();
                boolean collision = false;
                if (!ObjectUtils.equals((Object)currentValue, (Object)priorValue)) {
                    collision = ArgoUtils.evaluateCollision(currentValue, newValue);
                }
                if (collision) {
                    DataSourceEnum datasource = ContextHelper.getThreadDataSource();
                    Object[] params = new Object[]{priorValue, newValue, currentValue, inEntityName, inEntityRefNbr, inPrimaryKey, datasource};
                    bv = BizViolation.create((IPropertyKey) IArgoPropertyKeys.ERRKEY_FIELD_UPDATE_STALE, (Throwable)null, null, (IMetafieldId)inMetafieldId, (Object[])params);
                }
            }
        }
        if (bv != null) {
            if (inBizViolationChain != null) {
                bv = inBizViolationChain.appendToChain(bv);
            }
        } else {
            return inBizViolationChain;
        }
        return bv;
    }

    private static Boolean evaluateCollision(Object inCurrentValue, Object inNewValue) {
        Boolean hasCollision = false;
        if (inCurrentValue != null && inNewValue != null) {
            if (inCurrentValue instanceof Long && inNewValue instanceof Long) {
                Long currentValue = (Long)inCurrentValue;
                Long newValue = (Long)inNewValue;
                if (newValue > 0L && currentValue > 0L && !(hasCollision = Boolean.valueOf(ObjectUtils.equals((Object)currentValue, (Object)newValue))).booleanValue()) {
                    hasCollision = currentValue > newValue;
                }
            } else {
                hasCollision = ObjectUtils.equals((Object)inCurrentValue, (Object)inNewValue);
            }
        }
        return hasCollision;
    }

    @Nullable
    private static Long getCurrentFieldValue(String inEntityName, IMetafieldId inMetafieldId, IMetafieldId inPrimarykeyMetafieldId, Serializable inPrimaryKey) {
        Long currentValue = null;
        if (inPrimaryKey != null) {
            IValueHolder vao;
            IDomainQuery dq = QueryUtils.createDomainQuery((String)inEntityName).addDqField(inMetafieldId).addDqPredicate(PredicateFactory.eq((IMetafieldId)inPrimarykeyMetafieldId, (Object)inPrimaryKey));
            IQueryResult qr = Roastery.getHibernateApi().findValuesByDomainQuery(dq);
            if (qr != null && qr.getTotalResultCount() > 0 && (vao = qr.getValueHolder(0)) != null && vao.isFieldPresent(inMetafieldId)) {
                currentValue = (Long)vao.getFieldValue(inMetafieldId);
            }
        }
        return currentValue;
    }

    public static List<IUserMessage> sortMessages(IMessageCollector inMessageCollector) {
        List<IUserMessage> sortedMessages = new ArrayList();
        sortedMessages = MessageCollectorUtils.getUnchainedMessages((IMessageCollector)inMessageCollector, (MessageLevelEnum) MessageLevelEnum.SEVERE);
        sortedMessages.addAll(MessageCollectorUtils.getUnchainedMessages((IMessageCollector)inMessageCollector, (MessageLevelEnum) MessageLevelEnum.WARNING));
        sortedMessages.addAll(MessageCollectorUtils.getUnchainedMessages((IMessageCollector)inMessageCollector, (MessageLevelEnum) MessageLevelEnum.INFO));
        return sortedMessages;
    }

    public static void storeEntityInMessageCollector(DatabaseEntity inEntity) {
        IMessageCollector mc = TransactionParms.getBoundParms().getMessageCollector();
        LinkedHashMap<Serializable, ValueObject> vaoList = (LinkedHashMap<Serializable, ValueObject>)mc.getAttribute((String) IArgoBizMetafield.ENTITIES.getFieldId());
        if (vaoList == null) {
            vaoList = new LinkedHashMap<Serializable, ValueObject>();
            mc.setAttribute((String) IArgoBizMetafield.ENTITIES_TO_PERSIST.getFieldId(), vaoList);
        }
        vaoList.put(inEntity.getPrimaryKey(), inEntity.getValueObject());
    }

    public static void appendMessagesToCollectorAsWarnings(List<IMetafieldUserMessage> inMessages) {
        for (IMetafieldUserMessage msg : inMessages) {
            if (MessageLevelEnum.SEVERE.equals((Object)msg.getSeverity())) {
                msg.setSeverity(MessageLevelEnum.WARNING);
            }
            TransactionParms.getBoundParms().getMessageCollector().appendMessage((IUserMessage)msg);
        }
    }

    public static void appendMessagesToCollector(IMessageCollector inMc, List<IMetafieldUserMessage> inMessages) {
        for (IMetafieldUserMessage msg : inMessages) {
            inMc.appendMessage((IUserMessage)msg);
        }
    }

    public static void appendMessagesToCollector(List<IMetafieldUserMessage> inMessages) {
        ArgoUtils.appendMessagesToCollector(TransactionParms.getBoundParms().getMessageCollector(), inMessages);
    }

//    public static String invokeExternalBasicService(String inIntServiceName, String inXmlPayload) {
//        StringBuilder webServiceResponseBuilder = new StringBuilder();
//        ServiceLocator serviceLocator = new ServiceLocator();
//        UserContext threadUserContext = ContextHelper.getThreadUserContext();
//        if (threadUserContext == null) {
//            throw BizFailure.create((String)"The thread user context is null");
//        }
//        ScopeCoordinates scopeCoordinates = threadUserContext.getScopeCoordinate();
//        if (scopeCoordinates == null) {
//            throw BizFailure.wrap((Throwable)BizViolation.create((IPropertyKey)FrameworkPropertyKeys.FAILURE__INVALID_SCOPE, null));
//        }
//        List<IntegrationService> integrationServiceList = ArgoUtils.getIntegrationServices(inIntServiceName, scopeCoordinates, false);
//        if (integrationServiceList != null && !integrationServiceList.isEmpty()) {
//            IntegrationService integrationService = integrationServiceList.get(0);
//            webServiceResponseBuilder = ArgoUtils.sendOutboundMessage(inXmlPayload, serviceLocator, scopeCoordinates, integrationService);
//        }
//        return webServiceResponseBuilder.toString();
//    }

//    public static List<String> invokeExternalBasicServiceForGroup(String inIntServiceGroupName, String inXmlPayload) {
//        ArrayList<String> webServiceResponseList = new ArrayList<String>();
//        ServiceLocator serviceLocator = new ServiceLocator();
//        UserContext threadUserContext = ContextHelper.getThreadUserContext();
//        if (threadUserContext == null) {
//            throw BizFailure.create((String)"The thread user context is null");
//        }
//        ScopeCoordinates scopeCoordinates = threadUserContext.getScopeCoordinate();
//        if (scopeCoordinates == null) {
//            throw BizFailure.wrap((Throwable)BizViolation.create((IPropertyKey)FrameworkPropertyKeys.FAILURE__INVALID_SCOPE, null));
//        }
//        List<IntegrationService> integrationServiceList = ArgoUtils.getIntegrationServices(inIntServiceGroupName, scopeCoordinates, true);
//        if (integrationServiceList != null && !integrationServiceList.isEmpty()) {
//            for (IntegrationService integrationService : integrationServiceList) {
//                StringBuilder webServiceResponseBuilder = ArgoUtils.sendOutboundMessage(inXmlPayload, serviceLocator, scopeCoordinates, integrationService);
//                webServiceResponseList.add(webServiceResponseBuilder.toString());
//            }
//        }
//        return webServiceResponseList;
//    }

//    private static StringBuilder sendOutboundMessage(String inXmlPayload, ServiceLocator inServiceLocator, ScopeCoordinates inScopeCoordinates, IntegrationService inIntegrationService) {
//        StringBuilder webServiceResponseBuilder = new StringBuilder();
//        String url = inIntegrationService.getIntservUrl();
//        String userId = inIntegrationService.getIntservUserId();
//        String password = inIntegrationService.getIntservPassword();
//        try {
//            ServiceSoap port = inServiceLocator.getServiceSoap(new URL(url));
//            Stub stub = (Stub)port;
//            if (userId != null) {
//                stub._setProperty("javax.xml.rpc.security.auth.username", (Object)userId);
//                if (password != null) {
//                    stub._setProperty("javax.xml.rpc.security.auth.password", (Object)password);
//                }
//            }
//            String scope = ArgoUtils.getArgoScopeString(inScopeCoordinates);
//            webServiceResponseBuilder.append(port.basicInvoke(scope, inXmlPayload)).append("\n");
//        }
//        catch (Throwable th) {
//            String errorMessage = "Exception thrown by web service call to CAS service for : URL:" + url + " UserId:" + userId + " Password: " + password + "\n" + th.getLocalizedMessage();
//            webServiceResponseBuilder.append(errorMessage).append("\n");
//            LOGGER.error((Object)errorMessage);
//        }
//        return webServiceResponseBuilder;
//    }

//    private static List<IntegrationService> getIntegrationServices(String inIntServiceName, ScopeCoordinates inScopeCoordinates, boolean inIsGroup) {
//        MetafieldIdList metafieldIdList = new MetafieldIdList(3);
//        metafieldIdList.add(IntegrationServiceField.INTSERV_URL);
//        metafieldIdList.add(IntegrationServiceField.INTSERV_USER_ID);
//        metafieldIdList.add(IntegrationServiceField.INTSERV_PASSWORD);
//        IPredicate namePredicate = inIsGroup ? PredicateFactory.eq((IMetafieldId)IntegrationServiceField.INTSERV_GROUP, (Object)inIntServiceName) : PredicateFactory.eq((IMetafieldId)IntegrationServiceField.INTSERV_NAME, (Object)inIntServiceName);
//        IDomainQuery dq = QueryUtils.createDomainQuery((String)"IntegrationService").addDqFields(metafieldIdList).addDqPredicate(namePredicate).addDqPredicate(PredicateFactory.eq((IMetafieldId)IntegrationServiceField.INTSERV_TYPE, (Object)IntegrationServiceTypeEnum.WEB_SERVICE)).addDqPredicate(PredicateFactory.eq((IMetafieldId)IntegrationServiceField.INTSERV_ACTIVE, (Object)Boolean.TRUE)).addDqPredicate(PredicateFactory.eq((IMetafieldId)IntegrationServiceField.INTSERV_DIRECTION, (Object)IntegrationServiceDirectionEnum.OUTBOUND)).addDqPredicate(ScopedQueryUtils.getEntityPredicateForEffectiveScope((ScopeCoordinates)inScopeCoordinates, (IMetafieldId)IntegrationServiceField.INTSERV_SCOPE_LEVEL, (IMetafieldId)IntegrationServiceField.INTSERV_SCOPE_GKEY));
//        dq.addDqOrdering(Ordering.desc((IMetafieldId)IntegrationServiceField.INTSERV_SCOPE_LEVEL));
//        dq.setScopingEnabled(false);
//        return Roastery.getHibernateApi().findEntitiesByDomainQuery(dq);
//    }

    public static Date getCurrentDateWithHoursAndTime(int inHours, int inMinutes) {
        TimeZone tz = ContextHelper.getThreadUserTimezone();
        Calendar currentDateCal = Calendar.getInstance(tz);
        currentDateCal.set(10, inHours);
        currentDateCal.set(12, inMinutes);
        currentDateCal.set(9, 0);
        return currentDateCal.getTime();
    }

    public static void setThreadLocal(Map inMap) {
        tLocal.set(inMap);
    }

    public static Map getThreadLocal() {
        return (Map)tLocal.get();
    }

    public static void markSystemInternalUpdateInProgress() {
        HashMap<String, Boolean> map = (HashMap<String, Boolean>) ArgoUtils.getThreadLocal();
        if (map == null) {
            map = new HashMap<String, Boolean>();
            ArgoUtils.setThreadLocal(map);
        }
        map.put(INTERNAL_UPDATE_SERVICE, Boolean.TRUE);
    }

    public static void clearSystemInternalUpdateInProgress() {
        Map map = ArgoUtils.getThreadLocal();
        if (map == null) {
            return;
        }
        map.remove(INTERNAL_UPDATE_SERVICE);
    }

    public static boolean isSystemInternalUpdateTransaction() {
        Map map = ArgoUtils.getThreadLocal();
        return map != null && map.get(INTERNAL_UPDATE_SERVICE) != null;
    }

    public static boolean isFieldValueChanged(String inNewValue, String inOldValue) {
        return StringUtils.isNotBlank((String)inNewValue) && StringUtils.isNotBlank((String)inOldValue) ? !inNewValue.equals(inOldValue) : StringUtils.isNotBlank((String)inNewValue) || StringUtils.isNotBlank((String)inOldValue);
    }

    public static Date removeTime(Date inDate, TimeZone inTimeZone) {
        Calendar calendar = Calendar.getInstance(inTimeZone == null ? TimeZone.getDefault() : inTimeZone);
        calendar.setLenient(false);
        calendar.setTimeInMillis(inDate.getTime());
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
        Date dateWithoutTime = DateUtil.getDSTSafeCalendarTime((Calendar)calendar);
        return dateWithoutTime;
    }

    public static final BizViolation appendErrorIfDate1AfterDate2(BizViolation inBv, Date inDate1, Date inDate2, IPropertyKey inErrKey, IMetafieldId inOffendingField) {
        if (inDate1 != null && inDate2 != null && inDate1.after(inDate2)) {
            BizViolation bv = BizViolation.createFieldViolation((IPropertyKey)inErrKey, (BizViolation)inBv, (IMetafieldId)inOffendingField, null);
            return bv;
        }
        return inBv;
    }

    public static HashMap<String, Integer> getDeviationEntitiesPurgeSetting(UserContext inUserContext) {
        HashMap<String, Integer> entityValueMap = new HashMap<String, Integer>();
        String xmlStringDoc = ArgoConfig.PURGE_MAX_SIZE_DEVIATION_ENTITY.getSetting(inUserContext);
        if (xmlStringDoc == null || xmlStringDoc.isEmpty()) {
            return null;
        }
        Document settings = null;
//        try {
//            settings = XmlUtil.parse(xmlStringDoc);
//        }
//        catch (Exception e) {
//            LOGGER.error((Object)"Parse error while reading the setting PURGE_MAX_SIZE_DEVIATION_ENTITY");
//        }
//        if (settings == null) {
//            LOGGER.warn((Object)"Could not parse XML settings. Unable to get xml purge setting.");
//            return null;
//        }
//        Element root = settings.getRootElement();
//        if (root != null) {
//            List purgeEntries = root.getChildren();
//            for (Object purgeEntry : purgeEntries) {
//                if (!(purgeEntry instanceof Element)) continue;
//                try {
//                    String purgeEntityName = ((Element)purgeEntry).getAttributeValue("entity");
//                    int value = Integer.parseInt(((Element)purgeEntry).getAttributeValue("records-to-delete"));
//                    entityValueMap.put(purgeEntityName, value);
//                }
//                catch (Exception ex) {
//                    LOGGER.error((Object)"Wrong entity/value in the setting");
//                }
//            }
//        }
        return entityValueMap;
    }

    public static Map getEventFlexFields(FieldChanges inFieldChanges) {
        HashMap<IMetafieldId, FieldChange> eventFlexFields = new HashMap<IMetafieldId, FieldChange>();
        if (inFieldChanges != null) {
            Iterator flexEvent = inFieldChanges.getIterator();
            while (flexEvent.hasNext()) {
                FieldChange flextField = (FieldChange)flexEvent.next();
                IMetafieldId fieldId = flextField.getMetafieldId();
                String fieldIdString = fieldId.getMfidLeftMostNode().toString();
                if (!fieldIdString.startsWith(EVNT_FLEX_FIELD_PREFIX)) continue;
                eventFlexFields.put(fieldId, flextField);
            }
        }
        return eventFlexFields;
    }

    public static List<FieldChange> getFlexFieldChanges(FieldChanges inFieldChanges, String inFlexFieldPrefix) {
        ArrayList<FieldChange> flexFields = new ArrayList<FieldChange>();
        if (inFieldChanges != null) {
            Iterator flexFieldItr = inFieldChanges.getIterator();
            while (flexFieldItr.hasNext()) {
                FieldChange fieldChange = (FieldChange)flexFieldItr.next();
                IMetafieldId metaFieldId = fieldChange.getMetafieldId();
                String fieldIdString = metaFieldId.getMfidRightMostNode().toString();
                if (!fieldIdString.startsWith(inFlexFieldPrefix)) continue;
                flexFields.add(fieldChange);
            }
        }
        return flexFields;
    }

    @NotNull
    public static String getQueryTextWithoutDatePars(@NotNull IDomainQuery inQuery) {
        KeyValuePair[] parValues;
        String hqlText = inQuery.toHqlObjectQueryString("e");
        for (KeyValuePair par : parValues = inQuery.getFieldValues()) {
            Object parValueObject = par.getValue();
            if (parValueObject != null && parValueObject instanceof Date) continue;
            String parValue = par.getValue() == null ? "" : par.getValue().toString();
            hqlText = hqlText.replaceFirst("\\?", " " + parValue + " ");
        }
        if (hqlText.length() > 4000) {
            hqlText = hqlText.substring(0, 3999);
        }
        return hqlText;
    }

    public static String getCpxName() {
        Complex complex = ContextHelper.getThreadComplex();
        return complex == null ? "Unknown" : complex.getCpxName();
    }

    public static Long safeGetLong(String inNumberString, String inField) {
        Long longObject = null;
        if (!StringUtils.isEmpty((String)inNumberString)) {
            try {
                longObject = new Long(inNumberString);
            }
            catch (NumberFormatException e) {
                LOGGER.error((Object)("safeGetLong: bad value for " + inField + ": " + inNumberString));
            }
        }
        return longObject;
    }

//    public static ComponentEventMessageResponse broadcastReportEntityRefresh(@NotNull UserContext inUserContext, boolean inLocalOnly, Map<String, Serializable> inMapParams) {
//        IFrameworkComponentRefresher componentRefresher = ComponentEventBeanUtils.getComponentRefresher();
//        if (inLocalOnly) {
//            return componentRefresher.refreshFrameworkComponentLocally(inUserContext, ArgoClusterableEvents.CLUSTERABLE_REPORT_ENTITY_REFRESH, inMapParams);
//        }
//        return componentRefresher.refreshFrameworkComponentForCluster(inUserContext, ArgoClusterableEvents.CLUSTERABLE_REPORT_ENTITY_REFRESH, inMapParams);
//    }

    public static boolean isFieldChanged(IMetafieldId inFieldId, FieldChanges inFieldChanges) {
        if (!inFieldChanges.hasFieldChange(inFieldId)) {
            return false;
        }
        FieldChange fieldChange = inFieldChanges.getFieldChange(inFieldId);
        Object oldValue = fieldChange.getPriorValue();
        Object newValue = fieldChange.getNewValue();
        if (oldValue == null && newValue == null) {
            return false;
        }
        if (oldValue == null) {
            return true;
        }
        return !oldValue.equals(newValue);
    }

    static {
        FULL_DATE_TIME = new SimpleDateFormat(DATE_TIME_FORMAT);
        MONEY = new DecimalFormat("#0.00");
        tLocal = new ThreadLocal();
    }

    private static class DeleteCallback
            extends CarinaPersistenceCallback {
        private Class _entityClass;
        private Serializable _primaryKey;

        public DeleteCallback(Class inEntityClass) {
            this._entityClass = inEntityClass;
        }

        public void setPrimaryKey(Serializable inPrimaryKey) {
            this._primaryKey = inPrimaryKey;
        }

        public void doInTransaction() {
            Object entity = null;
            try {
                entity = HibernateApi.getInstance().load(this._entityClass, this._primaryKey);
            }
            catch (Exception e) {
                LOGGER.error((Object)("doInTransaction: could not load " + this._entityClass + "instance with key " + this._primaryKey), (Throwable)e);
            }
            if (entity != null) {
                try {
                    HibernateApi.getInstance().delete(entity);
                }
                catch (Exception e) {
                    LOGGER.error((Object)("doInTransaction: could not delete " + this._entityClass + "instance with key " + this._primaryKey), (Throwable)e);
                }
            }
        }
    }
}
