package com.zpmc.ztos.infra.base.business.predicate;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.business.enums.framework.JoinTypeEnum;
import com.zpmc.ztos.infra.base.business.enums.framework.LetterCaseEnum;
import com.zpmc.ztos.infra.base.business.enums.framework.PredicateVerbEnum;
import com.zpmc.ztos.infra.base.business.enums.framework.RelativeTimeEnum;
import com.zpmc.ztos.infra.base.business.interfaces.IDomainQuery;
import com.zpmc.ztos.infra.base.business.interfaces.IMetafieldId;
import com.zpmc.ztos.infra.base.business.interfaces.IPredicate;
import com.zpmc.ztos.infra.base.business.interfaces.IValueSource;
import com.zpmc.ztos.infra.base.business.model.Conjunction;
import com.zpmc.ztos.infra.base.common.configs.LongConfig;
import com.zpmc.ztos.infra.base.common.consts.FrameworkConfigConsts;
import com.zpmc.ztos.infra.base.common.database.HiberCache;
import com.zpmc.ztos.infra.base.common.exceptions.BizFailure;
import com.zpmc.ztos.infra.base.common.model.KeyValuePair;
import com.zpmc.ztos.infra.base.common.model.UserContext;
import com.zpmc.ztos.infra.base.common.type.CompareOperationType;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.util.*;

public class PredicateFactory extends PredicateFactoryBase{

    private PredicateFactory() {
    }

    public static IPredicate eq(IMetafieldId inFieldId, Object inValue) {
        if (inValue == null) {
            return PredicateFactory.isNull(inFieldId);
        }
        if (inValue instanceof IMetafieldId) {
            return PredicateFactory.eqProperty(inFieldId, (IMetafieldId)inValue);
        }
        return new EqPredicate(inFieldId, inValue);
    }

    public static IPredicate ne(IMetafieldId inFieldId, Object inValue) {
        if (inValue == null) {
            return PredicateFactory.isNotNull(inFieldId);
        }
        if (inValue instanceof IMetafieldId) {
            return PredicateFactory.not(PredicateFactory.eqProperty(inFieldId, (IMetafieldId)inValue));
        }
        return new NePredicate(inFieldId, inValue);
    }

    public static IPredicate like(IMetafieldId inFieldId, String inValue) {
        return new LikePredicate(inFieldId, (Object)inValue);
    }

    public static IPredicate ilike(IMetafieldId inFieldId, String inValue) {
        return new IlikePredicate(inFieldId, (Object)inValue);
    }

    public static IPredicate ilike(LetterCaseEnum inCase, IMetafieldId inFieldId, String inSearchString) {
        if (LetterCaseEnum.UPPER.equals((Object)inCase)) {
            return PredicateFactory.like(inFieldId, inSearchString.toUpperCase());
        }
        if (LetterCaseEnum.LOWER.equals((Object)inCase)) {
            return PredicateFactory.like(inFieldId, inSearchString.toLowerCase());
        }
        return PredicateFactory.ilike(inFieldId, inSearchString);
    }

    public static IPredicate gt(IMetafieldId inFieldId, Object inValue) {
        if (inValue instanceof IMetafieldId) {
            return new LtPropertyPredicate((IMetafieldId)inValue, inFieldId);
        }
        return new GtPredicate(inFieldId, inValue);
    }

    public static IPredicate lt(IMetafieldId inFieldId, Object inValue) {
        if (inValue instanceof IMetafieldId) {
            return new LtPropertyPredicate(inFieldId, (IMetafieldId)inValue);
        }
        return new LtPredicate(inFieldId, inValue);
    }

    public static IPredicate le(IMetafieldId inFieldId, Object inValue) {
        if (inValue instanceof IMetafieldId) {
            return new LePropertyPredicate(inFieldId, (IMetafieldId)inValue);
        }
        return new LePredicate(inFieldId, inValue);
    }

    public static IPredicate ge(IMetafieldId inFieldId, Object inValue) {
        if (inValue instanceof IMetafieldId) {
            return new LePropertyPredicate((IMetafieldId)inValue, inFieldId);
        }
        return new GePredicate(inFieldId, inValue);
    }

    public static IPredicate between(IMetafieldId inFieldId, Comparable inLoValue, Comparable inHiValue) {
        return new BetweenPredicate(inFieldId, inLoValue, inHiValue);
    }

    public static IPredicate in(IMetafieldId inFieldId, Object[] inValues) {
        return new InPredicate(inFieldId, inValues);
    }

    public static IPredicate in(IMetafieldId inFieldId, Collection inValues) {
        return new InPredicate(inFieldId, inValues.toArray());
    }

    public static IPredicate inUp(IMetafieldId inFieldId, Collection inValues) {
        return new InUpperPredicate(inFieldId, inValues.toArray());
    }

    public static IPredicate isNull(IMetafieldId inFieldId) {
        return new NullPredicate(inFieldId);
    }

    public static IPredicate alwaysFalse(IMetafieldId inFieldId) {
        return new AlwaysFalsePredicate(inFieldId);
    }

    public static IPredicate eqProperty(IMetafieldId inFieldId, IMetafieldId inOtherPropertyName) {
        return new EqPropertyPredicate(inFieldId, inOtherPropertyName);
    }

    public static IPredicate ltProperty(IMetafieldId inFieldId, IMetafieldId inOtherPropertyName) {
        return new LtPropertyPredicate(inFieldId, inOtherPropertyName);
    }

    public static IPredicate leProperty(IMetafieldId inFieldId, IMetafieldId inOtherPropertyName) {
        return new LePropertyPredicate(inFieldId, inOtherPropertyName);
    }

    public static IPredicate isNotNull(IMetafieldId inFieldId) {
        return new NotNullPredicate(inFieldId);
    }

    public static IPredicate allEq(Map inPropertyNameValues) {
        Conjunction conj = PredicateFactory.conjunction();
        for (Object me : inPropertyNameValues.entrySet()) {
            conj.add(PredicateFactory.eq((IMetafieldId)((Map.Entry)me).getKey(), ((Map.Entry)me).getValue()));
        }
        return conj;
    }

    public static IPredicate newerThan(IMetafieldId inDateFieldId, long inAgeMillis) {
        return new NewerThanPredicate(inDateFieldId, inAgeMillis);
    }

    public static IPredicate olderThan(IMetafieldId inDateFieldId, long inAgeMillis) {
        return new OlderThanPredicate(inDateFieldId, inAgeMillis);
    }

    public static IPredicate geoContains(IMetafieldId inGeometryFieldId, Object inValue) {
        Object convertedValue = PredicateFactory.validateGeometryPredicate(inGeometryFieldId, inValue);
        if (convertedValue == null) {
            return PredicateFactory.isNull(inGeometryFieldId);
        }
        if (convertedValue instanceof IMetafieldId) {
            return PredicateFactory.geoContainsProperty(inGeometryFieldId, (IMetafieldId)convertedValue);
        }
        return PredicateFactory.postProcessGeoPredicate(new ContainsPredicate(inGeometryFieldId, convertedValue), inGeometryFieldId);
    }

    public static IPredicate geoContainsProperty(IMetafieldId inGeometryFieldId, IMetafieldId inOtherGeometryFieldId) {
        return PredicateFactory.postProcessGeoPropertyPredicate(new ContainsPropertyPredicate(inGeometryFieldId, inOtherGeometryFieldId), inGeometryFieldId, inOtherGeometryFieldId);
    }

    public static IPredicate geoCrosses(IMetafieldId inGeometryFieldId, Object inValue) {
        Object convertedValue = PredicateFactory.validateGeometryPredicate(inGeometryFieldId, inValue);
        if (convertedValue == null) {
            return PredicateFactory.isNull(inGeometryFieldId);
        }
        if (convertedValue instanceof IMetafieldId) {
            return PredicateFactory.geoCrossesProperty(inGeometryFieldId, (IMetafieldId)convertedValue);
        }
        return PredicateFactory.postProcessGeoPredicate(new CrossesPredicate(inGeometryFieldId, convertedValue), inGeometryFieldId);
    }

    public static IPredicate geoCrossesProperty(IMetafieldId inGeometryFieldId, IMetafieldId inOtherGeometryFieldId) {
        return PredicateFactory.postProcessGeoPropertyPredicate(new CrossesPropertyPredicate(inGeometryFieldId, inOtherGeometryFieldId), inGeometryFieldId, inOtherGeometryFieldId);
    }

    public static IPredicate geoDisjoint(IMetafieldId inGeometryFieldId, Object inValue) {
        Object convertedValue = PredicateFactory.validateGeometryPredicate(inGeometryFieldId, inValue);
        if (convertedValue == null) {
            return PredicateFactory.isNull(inGeometryFieldId);
        }
        if (convertedValue instanceof IMetafieldId) {
            return PredicateFactory.geoDisjointProperty(inGeometryFieldId, (IMetafieldId)convertedValue);
        }
        return PredicateFactory.postProcessGeoPredicate(new DisjointPredicate(inGeometryFieldId, convertedValue), inGeometryFieldId);
    }

    public static IPredicate geoDisjointProperty(IMetafieldId inGeometryFieldId, IMetafieldId inOtherGeometryFieldId) {
        return PredicateFactory.postProcessGeoPropertyPredicate(new DisjointPropertyPredicate(inGeometryFieldId, inOtherGeometryFieldId), inGeometryFieldId, inOtherGeometryFieldId);
    }

    public static IPredicate geoEquals(IMetafieldId inGeometryFieldId, Object inValue) {
        Object convertedValue = PredicateFactory.validateGeometryPredicate(inGeometryFieldId, inValue);
        if (convertedValue == null) {
            return PredicateFactory.isNull(inGeometryFieldId);
        }
        if (convertedValue instanceof IMetafieldId) {
            return PredicateFactory.geoEqualsProperty(inGeometryFieldId, (IMetafieldId)convertedValue);
        }
        return PredicateFactory.postProcessGeoPredicate(new EqualsPredicate(inGeometryFieldId, convertedValue), inGeometryFieldId);
    }

    public static IPredicate geoEqualsProperty(IMetafieldId inGeometryFieldId, IMetafieldId inOtherGeometryFieldId) {
        return PredicateFactory.postProcessGeoPropertyPredicate(new EqualsPropertyPredicate(inGeometryFieldId, inOtherGeometryFieldId), inGeometryFieldId, inOtherGeometryFieldId);
    }

    public static IPredicate geoIntersects(IMetafieldId inGeometryFieldId, Object inValue) {
        Object convertedValue = PredicateFactory.validateGeometryPredicate(inGeometryFieldId, inValue);
        if (convertedValue == null) {
            return PredicateFactory.isNull(inGeometryFieldId);
        }
        if (convertedValue instanceof IMetafieldId) {
            return PredicateFactory.geoIntersectsProperty(inGeometryFieldId, (IMetafieldId)convertedValue);
        }
        return PredicateFactory.postProcessGeoPredicate(new IntersectsPredicate(inGeometryFieldId, convertedValue), inGeometryFieldId);
    }

    public static IPredicate geoIntersectsProperty(IMetafieldId inGeometryFieldId, IMetafieldId inOtherGeometryFieldId) {
        return PredicateFactory.postProcessGeoPropertyPredicate(new IntersectsPropertyPredicate(inGeometryFieldId, inOtherGeometryFieldId), inGeometryFieldId, inOtherGeometryFieldId);
    }

    public static IPredicate geoOverlaps(IMetafieldId inGeometryFieldId, Object inValue) {
        Object convertedValue = PredicateFactory.validateGeometryPredicate(inGeometryFieldId, inValue);
        if (convertedValue == null) {
            return PredicateFactory.isNull(inGeometryFieldId);
        }
        if (convertedValue instanceof IMetafieldId) {
            return PredicateFactory.geoOverlapsProperty(inGeometryFieldId, (IMetafieldId)convertedValue);
        }
        return PredicateFactory.postProcessGeoPredicate(new OverlapsPredicate(inGeometryFieldId, convertedValue), inGeometryFieldId);
    }

    public static IPredicate geoOverlapsProperty(IMetafieldId inGeometryFieldId, IMetafieldId inOtherGeometryFieldId) {
        return PredicateFactory.postProcessGeoPropertyPredicate(new OverlapsPropertyPredicate(inGeometryFieldId, inOtherGeometryFieldId), inGeometryFieldId, inOtherGeometryFieldId);
    }

    public static IPredicate geoTouches(IMetafieldId inGeometryFieldId, Object inValue) {
        Object convertedValue = PredicateFactory.validateGeometryPredicate(inGeometryFieldId, inValue);
        if (convertedValue == null) {
            return PredicateFactory.isNull(inGeometryFieldId);
        }
        if (convertedValue instanceof IMetafieldId) {
            return PredicateFactory.geoTouchesProperty(inGeometryFieldId, (IMetafieldId)convertedValue);
        }
        return PredicateFactory.postProcessGeoPredicate(new TouchesPredicate(inGeometryFieldId, convertedValue), inGeometryFieldId);
    }

    public static IPredicate geoTouchesProperty(IMetafieldId inGeometryFieldId, IMetafieldId inOtherGeometryFieldId) {
        return PredicateFactory.postProcessGeoPropertyPredicate(new TouchesPropertyPredicate(inGeometryFieldId, inOtherGeometryFieldId), inGeometryFieldId, inOtherGeometryFieldId);
    }

    public static IPredicate geoWithin(IMetafieldId inGeometryFieldId, Object inValue) {
        Object convertedValue = PredicateFactory.validateGeometryPredicate(inGeometryFieldId, inValue);
        if (convertedValue == null) {
            return PredicateFactory.isNull(inGeometryFieldId);
        }
        if (convertedValue instanceof IMetafieldId) {
            return PredicateFactory.geoWithinProperty(inGeometryFieldId, (IMetafieldId)convertedValue);
        }
        return PredicateFactory.postProcessGeoPredicate(new WithinPredicate(inGeometryFieldId, convertedValue), inGeometryFieldId);
    }

    public static IPredicate geoWithinProperty(IMetafieldId inGeometryFieldId, IMetafieldId inOtherGeometryFieldId) {
        return PredicateFactory.postProcessGeoPropertyPredicate(new WithinPropertyPredicate(inGeometryFieldId, inOtherGeometryFieldId), inGeometryFieldId, inOtherGeometryFieldId);
    }

    public static IPredicate hql(String inHql, KeyValuePair[] inValuePair) {
        return new HqlPredicate(inHql, inValuePair);
    }

    public static IPredicate subQueryAggregateOp(IDomainQuery inSubQuery, CompareOperationType inCompareOperation, IMetafieldId inFieldId) {
        return new SubQueryAggregatePredicate(inSubQuery, inCompareOperation, inFieldId);
    }

    public static IPredicate createPredicate(UserContext inUserContext, IMetafieldId inMetafieldId, PredicateVerbEnum inVerb, Object inValue) {
        TimeZone timeZone;
        IPredicate predicate;
        boolean isSynthetic;
        boolean bl = isSynthetic = inMetafieldId != null && HiberCache.isSyntheticField(inMetafieldId.getFieldId());
        if (isSynthetic && (predicate = PredicateSynthesizer.createPredicate(inUserContext, inMetafieldId, inVerb, inValue)) != null) {
            return predicate;
        }
        if (inVerb.equals(PredicateVerbEnum.EQ)) {
            return PredicateFactory.buildEqPredicate(inUserContext, inMetafieldId, inValue);
        }
        if (inVerb.equals(PredicateVerbEnum.NE)) {
            return PredicateFactory.ne(inMetafieldId, inValue);
        }
        if (inVerb.equals(PredicateVerbEnum.LT)) {
            return PredicateFactory.lt(inMetafieldId, inValue);
        }
        if (inVerb.equals(PredicateVerbEnum.GT)) {
            return PredicateFactory.gt(inMetafieldId, inValue);
        }
        if (inVerb.equals(PredicateVerbEnum.LE)) {
            return PredicateFactory.le(inMetafieldId, inValue);
        }
        if (inVerb.equals(PredicateVerbEnum.GE)) {
            return PredicateFactory.ge(inMetafieldId, inValue);
        }
        if (inVerb.equals(PredicateVerbEnum.IN)) {
            return new InPredicate(inMetafieldId, inValue);
        }
        if (inVerb.equals(PredicateVerbEnum.NULL)) {
            return PredicateFactory.isNull(inMetafieldId);
        }
        if (inVerb.equals(PredicateVerbEnum.NOT_NULL)) {
            return PredicateFactory.isNotNull(inMetafieldId);
        }
        if (inVerb.equals(PredicateVerbEnum.TRUE)) {
            return PredicateFactory.eq(inMetafieldId, Boolean.TRUE);
        }
        if (inVerb.equals(PredicateVerbEnum.FALSE)) {
            return PredicateFactory.eq(inMetafieldId, Boolean.FALSE);
        }
        if (inVerb.equals(PredicateVerbEnum.MATCHES)) {
            if (inValue instanceof String && !isSynthetic) {
    //            inValue = QueryUtils.convertToAnsiWildCards((String)inValue);
    //            IMetafieldDictionary metafieldDictionary = Roastery.getMetafieldDictionary();
    //            IMetafield metaField = metafieldDictionary.findMetafield(inMetafieldId);
    //            LetterCaseEnum fieldCase = FieldUtils.getFieldLetterCase(metaField);
    //            return PredicateFactory.ilike(fieldCase, inMetafieldId, (String)inValue);
            }
            return new IlikePredicate(inMetafieldId, inValue);
        }
        if (inVerb.equals(PredicateVerbEnum.AFTER)) {
            return PredicateFactory.gt(inMetafieldId, inValue);
        }
        if (inVerb.equals(PredicateVerbEnum.AFTER_DATE)) {
            timeZone = inUserContext.getTimeZone();
            Calendar cal = Calendar.getInstance(timeZone);
            cal.setTime((Date)inValue);
            return PredicateFactory.gt(inMetafieldId, PredicateFactory.getCalendarEndOfDayNoMsec(cal));
        }
        if (inVerb.equals(PredicateVerbEnum.EQ_DATE)) {
            timeZone = inUserContext.getTimeZone();
            Calendar cal = Calendar.getInstance(timeZone);
            cal.setTime((Date)inValue);
            Date loValue = cal.getTime();
            cal.setTime((Date)inValue);
            Date hiValue = PredicateFactory.getCalendarEndOfDayNoMsec(cal);
            return PredicateFactory.between(inMetafieldId, loValue, hiValue);
        }
        if (inVerb.equals(PredicateVerbEnum.BEFORE)) {
            return PredicateFactory.lt(inMetafieldId, inValue);
        }
        if (inVerb.equals(PredicateVerbEnum.TIME_ONLY_BEFORE)) {
            timeZone = inUserContext.getBusinessTimeZone();
            return new BeforeTimeOnlyPredicate(inMetafieldId, inValue, timeZone);
        }
        if (inVerb.equals(PredicateVerbEnum.TIME_ONLY_AFTER)) {
            timeZone = inUserContext.getBusinessTimeZone();
            return new AfterTimeOnlyPredicate(inMetafieldId, inValue, timeZone);
        }
        if (inVerb.equals(PredicateVerbEnum.TIME_ONLY_IS)) {
            timeZone = inUserContext.getBusinessTimeZone();
            return new TimeOnlyIsPredicate(inMetafieldId, inValue, timeZone);
        }
        if (inVerb.equals(PredicateVerbEnum.AND)) {
            return PredicateFactory.conjunction();
        }
        if (inVerb.equals(PredicateVerbEnum.OR)) {
            return PredicateFactory.disjunction();
        }
        if (inVerb.equals(PredicateVerbEnum.NOT)) {
            return new NotPredicate();
        }
        if (inVerb.equals(PredicateVerbEnum.TIME_IS)) {
            if (!(inValue instanceof RelativeTimeEnum)) {
                Class<?> c = inValue == null ? null : inValue.getClass();
                throw BizFailure.create("createPredicate: invalid class for TIME_IS " + c);
            }
            RelativeTimeEnum time = (RelativeTimeEnum)inValue;
            return PredicateFactory.createRelativeTimePredicate(inUserContext, inMetafieldId, time);
        }
        if (inVerb.equals(PredicateVerbEnum.OTHERWISE)) {
            return PredicateFactory.createOtherwisePredicate();
        }
        if (inVerb.equals(PredicateVerbEnum.GEO_CONTAINS)) {
            return PredicateFactory.geoContains(inMetafieldId, inValue);
        }
        if (inVerb.equals(PredicateVerbEnum.GEO_CROSSES)) {
            return PredicateFactory.geoCrosses(inMetafieldId, inValue);
        }
        if (inVerb.equals(PredicateVerbEnum.GEO_DISJOINT)) {
            return PredicateFactory.geoDisjoint(inMetafieldId, inValue);
        }
        if (inVerb.equals(PredicateVerbEnum.GEO_EQUALS)) {
            return PredicateFactory.geoEquals(inMetafieldId, inValue);
        }
        if (inVerb.equals(PredicateVerbEnum.GEO_INTERSECTS)) {
            return PredicateFactory.geoIntersects(inMetafieldId, inValue);
        }
        if (inVerb.equals(PredicateVerbEnum.GEO_OVERLAPS)) {
            return PredicateFactory.geoOverlaps(inMetafieldId, inValue);
        }
        if (inVerb.equals(PredicateVerbEnum.GEO_TOUCHES)) {
            return PredicateFactory.geoTouches(inMetafieldId, inValue);
        }
        if (inVerb.equals(PredicateVerbEnum.GEO_WITHIN)) {
            return PredicateFactory.geoWithin(inMetafieldId, inValue);
        }
        throw new IllegalArgumentException("Cannot handle verb " + inVerb);
    }

    private static IPredicate createOtherwisePredicate() {
        return new IPredicate(){

            @Override
            public KeyValuePair[] getFieldValues() {
                return new KeyValuePair[0];
            }

            @Override
            @NotNull
            public String toHqlString(String inEntityAlias) {
                return "";
            }

            @Override
            public boolean isSatisfiedBy(IValueSource inValueSource) {
                return true;
            }
        };
    }

    private static Date getCalendarStartOfDay(Calendar inCalendar) {
        inCalendar.set(11, 0);
        inCalendar.set(12, 0);
        inCalendar.set(13, 0);
        inCalendar.set(14, 0);
        return inCalendar.getTime();
    }

    private static Date getCalendarEndOfDay(Calendar inCalendar) {
        inCalendar.set(11, 23);
        inCalendar.set(12, 59);
        inCalendar.set(13, 59);
        inCalendar.set(14, 999);
        return inCalendar.getTime();
    }

    public static Date getCalendarEndOfDayNoMsec(Calendar inCalendar) {
        inCalendar.set(11, 23);
        inCalendar.set(12, 59);
        inCalendar.set(13, 59);
        return inCalendar.getTime();
    }

    private static Date getTimePeriodStart(UserContext inUserContext, Calendar inCalendar, LongConfig inPeriodStart) {
        PredicateFactory.setPerConfig(inUserContext, inCalendar, inPeriodStart);
        inCalendar.set(13, 0);
        inCalendar.set(14, 0);
        return inCalendar.getTime();
    }

    private static Date getTimePeriodEnd(UserContext inUserContext, Calendar inCalendar, LongConfig inPeriodEnd) {
        PredicateFactory.setPerConfig(inUserContext, inCalendar, inPeriodEnd);
        return inCalendar.getTime();
    }

    private static void setPerConfig(UserContext inUserContext, Calendar inCal, LongConfig inConfig) {
        long hhmm = inConfig.getValue(inUserContext);
        int hh = Math.round(hhmm / 100L);
        int mm = (int)hhmm - hh * 100;
        inCal.set(11, hh);
        inCal.set(12, mm);
    }

    private static IPredicate createRelativeTimePredicate(UserContext inUserContext, IMetafieldId inField, RelativeTimeEnum inTimeEnum) {
        TimeZone timeZone = inUserContext.getTimeZone();
        Calendar cal = Calendar.getInstance(timeZone);
        GePredicate startTimePredicate = null;
        LePredicate endTimePredicate = null;
        if (RelativeTimeEnum.TODAY.equals(inTimeEnum)) {
            startTimePredicate = new GePredicate(inField, (Object) PredicateFactory.getCalendarStartOfDay(cal));
            endTimePredicate = new LePredicate(inField, (Object) PredicateFactory.getCalendarEndOfDay(cal));
        } else if (RelativeTimeEnum.YESTERDAY.equals(inTimeEnum)) {
            cal.add(6, -1);
            startTimePredicate = new GePredicate(inField, (Object) PredicateFactory.getCalendarStartOfDay(cal));
            endTimePredicate = new LePredicate(inField, (Object) PredicateFactory.getCalendarEndOfDay(cal));
        } else if (RelativeTimeEnum.WITHIN_10M.equals(inTimeEnum)) {
            endTimePredicate = new LePredicate(inField, (Object)cal.getTime());
            cal.add(12, -10);
            startTimePredicate = new GePredicate(inField, (Object)cal.getTime());
        } else if (RelativeTimeEnum.WITHIN_1H.equals(inTimeEnum)) {
            endTimePredicate = new LePredicate(inField, (Object)cal.getTime());
            cal.add(10, -1);
            startTimePredicate = new GePredicate(inField, (Object)cal.getTime());
        } else if (RelativeTimeEnum.TODAY_PERIOD1.equals(inTimeEnum)) {
            startTimePredicate = new GePredicate(inField, (Object) PredicateFactory.getTimePeriodStart(inUserContext, cal, FrameworkConfigConsts.P1_START));
            endTimePredicate = new LePredicate(inField, (Object) PredicateFactory.getTimePeriodEnd(inUserContext, cal, FrameworkConfigConsts.P1_END));
        } else if (RelativeTimeEnum.TODAY_PERIOD2.equals(inTimeEnum)) {
            startTimePredicate = new GePredicate(inField, (Object) PredicateFactory.getTimePeriodStart(inUserContext, cal, FrameworkConfigConsts.P2_START));
            endTimePredicate = new LePredicate(inField, (Object) PredicateFactory.getTimePeriodEnd(inUserContext, cal, FrameworkConfigConsts.P2_END));
        } else if (RelativeTimeEnum.TODAY_PERIOD3.equals(inTimeEnum)) {
            startTimePredicate = new GePredicate(inField, (Object) PredicateFactory.getTimePeriodStart(inUserContext, cal, FrameworkConfigConsts.P3_START));
            endTimePredicate = new LePredicate(inField, (Object) PredicateFactory.getTimePeriodEnd(inUserContext, cal, FrameworkConfigConsts.P3_END));
        } else if (RelativeTimeEnum.TODAY_PERIOD4.equals(inTimeEnum)) {
            startTimePredicate = new GePredicate(inField, (Object) PredicateFactory.getTimePeriodStart(inUserContext, cal, FrameworkConfigConsts.P4_START));
            endTimePredicate = new LePredicate(inField, (Object) PredicateFactory.getTimePeriodEnd(inUserContext, cal, FrameworkConfigConsts.P4_END));
        } else if (RelativeTimeEnum.YESTERDAY_PERIOD1.equals(inTimeEnum)) {
            cal.add(6, -1);
            startTimePredicate = new GePredicate(inField, (Object) PredicateFactory.getTimePeriodStart(inUserContext, cal, FrameworkConfigConsts.P1_START));
            endTimePredicate = new LePredicate(inField, (Object) PredicateFactory.getTimePeriodEnd(inUserContext, cal, FrameworkConfigConsts.P1_END));
        } else if (RelativeTimeEnum.YESTERDAY_PERIOD2.equals(inTimeEnum)) {
            cal.add(6, -1);
            startTimePredicate = new GePredicate(inField, (Object) PredicateFactory.getTimePeriodStart(inUserContext, cal, FrameworkConfigConsts.P2_START));
            endTimePredicate = new LePredicate(inField, (Object) PredicateFactory.getTimePeriodEnd(inUserContext, cal, FrameworkConfigConsts.P2_END));
        } else if (RelativeTimeEnum.YESTERDAY_PERIOD3.equals(inTimeEnum)) {
            cal.add(6, -1);
            startTimePredicate = new GePredicate(inField, (Object) PredicateFactory.getTimePeriodStart(inUserContext, cal, FrameworkConfigConsts.P3_START));
            endTimePredicate = new LePredicate(inField, (Object) PredicateFactory.getTimePeriodEnd(inUserContext, cal, FrameworkConfigConsts.P3_END));
        } else if (RelativeTimeEnum.YESTERDAY_PERIOD4.equals(inTimeEnum)) {
            cal.add(6, -1);
            startTimePredicate = new GePredicate(inField, (Object) PredicateFactory.getTimePeriodStart(inUserContext, cal, FrameworkConfigConsts.P4_START));
            endTimePredicate = new LePredicate(inField, (Object) PredicateFactory.getTimePeriodEnd(inUserContext, cal, FrameworkConfigConsts.P4_END));
        } else if (RelativeTimeEnum.LAST_SEVEN_DAYS.equals(inTimeEnum)) {
            endTimePredicate = new LePredicate(inField, (Object)cal.getTime());
            cal.add(6, -7);
            startTimePredicate = new GePredicate(inField, (Object) PredicateFactory.getCalendarStartOfDay(cal));
        } else if (RelativeTimeEnum.CURRENT_MONTH.equals(inTimeEnum)) {
            endTimePredicate = new LePredicate(inField, (Object)cal.getTime());
            cal.set(5, 1);
            startTimePredicate = new GePredicate(inField, (Object) PredicateFactory.getCalendarStartOfDay(cal));
        } else if (RelativeTimeEnum.AFT_NOW.equals(inTimeEnum)) {
            startTimePredicate = new GePredicate(inField, (Object)cal.getTime());
        } else if (RelativeTimeEnum.OLD_1D.equals(inTimeEnum)) {
            cal.add(6, -1);
            endTimePredicate = new LePredicate(inField, (Object)cal.getTime());
        } else if (RelativeTimeEnum.OLD_10D.equals(inTimeEnum)) {
            cal.add(6, -10);
            endTimePredicate = new LePredicate(inField, (Object)cal.getTime());
        } else if (RelativeTimeEnum.OLD_100D.equals(inTimeEnum)) {
            cal.add(6, -100);
            endTimePredicate = new LePredicate(inField, (Object)cal.getTime());
        } else if (RelativeTimeEnum.BEF_NOW.equals(inTimeEnum)) {
            endTimePredicate = new LePredicate(inField, (Object)cal.getTime());
        } else if (RelativeTimeEnum.NEW_10M.equals(inTimeEnum)) {
            cal.add(12, -10);
            startTimePredicate = new GePredicate(inField, (Object)cal.getTime());
        } else if (RelativeTimeEnum.NEW_1H.equals(inTimeEnum)) {
            cal.add(10, -1);
            startTimePredicate = new GePredicate(inField, (Object)cal.getTime());
        } else if (RelativeTimeEnum.NEW_1D.equals(inTimeEnum)) {
            cal.add(6, -1);
            startTimePredicate = new GePredicate(inField, (Object)cal.getTime());
        } else if (RelativeTimeEnum.NEW_10D.equals(inTimeEnum)) {
            cal.add(6, -10);
            startTimePredicate = new GePredicate(inField, (Object)cal.getTime());
        }
        Conjunction conjunction = new Conjunction();
        if (startTimePredicate != null) {
            conjunction.add(startTimePredicate);
        }
        if (endTimePredicate != null) {
            conjunction.add(endTimePredicate);
        }
        return conjunction;
    }

    private static IPredicate buildEqPredicate(@NotNull UserContext inUserContext, @NotNull IMetafieldId inMetafieldId, Object inValue) {
        if (inValue == null || inValue instanceof IMetafieldId) {
            return PredicateFactory.eq(inMetafieldId, inValue);
        }
//        IMetafieldDictionary metafieldDictionary = Roastery.getMetafieldDictionary();
//        IMetafield mFld = metafieldDictionary.findMetafield(inMetafieldId);
//        if (!Date.class.equals((Object)mFld.getValueClass())) {
//            return PredicateFactory.eq(inMetafieldId, inValue);
//        }
//        ValidationEntry valEntry = mFld.getValidationEntry();
//        if (valEntry == null || ValidationDataTypeEnum.DATE.equals((Object)valEntry.getDataType())) {
//            return PredicateFactory.eq(inMetafieldId, inValue);
//        }
        TimeZone timeZone = inUserContext.getTimeZone();
        Calendar cal = Calendar.getInstance(timeZone);
        cal.setTime((Date)inValue);
        cal.set(14, 0);
        cal.set(13, 0);
        GePredicate startTimePredicate = new GePredicate(inMetafieldId, (Object)cal.getTime());
        cal.add(12, 1);
        LtPredicate endTimePredicate = new LtPredicate(inMetafieldId, (Object)cal.getTime());
        Conjunction conjunction = new Conjunction();
        conjunction.add(startTimePredicate);
        conjunction.add(endTimePredicate);
        return conjunction;
    }

    @Nullable
    private static Object validateGeometryPredicate(@NotNull IMetafieldId inMetafieldId, Object inValue) {
//        IMetafieldDictionary metafieldDictionary = Roastery.getMetafieldDictionary();
//        IMetafield mFld = metafieldDictionary.findMetafield(inMetafieldId);
//        Class fldClass = mFld.getValueClass();
//        if (!Geometry.class.isAssignableFrom(mFld.getValueClass())) {
//            throw BizFailure.create("createPredicate: invalid class for SPATIAL predicate field " + fldClass);
//        }
//        if (inValue != null) {
//            if (inValue instanceof IMetafieldId) {
//                mFld = metafieldDictionary.findMetafield((IMetafieldId)inValue);
//                fldClass = mFld.getValueClass();
//                if (!Geometry.class.isAssignableFrom(mFld.getValueClass())) {
//                    throw BizFailure.create("createPredicate: invalid class for SPATIAL predicate field " + fldClass);
//                }
//            } else {
//                if (inValue instanceof String) {
////                    WKTReader fromText = new WKTReader();
////                    try {
////                        return fromText.read((String)inValue);
////                    }
////                    catch (ParseException pe) {
////                        throw BizFailure.create("createPredicate: invalid String representation for SPATIAL predicate field " + inMetafieldId.getFieldId());
////                    }
//                }
//                if (!(inValue instanceof Geometry)) {
//                    throw BizFailure.create("createPredicate: invalid class for SPATIAL predicate field " + fldClass);
//                }
//            }
//        }
//        return inValue;
        return null;
    }

    private static IPredicate postProcessGeoPredicate(IPredicate inPredicate, IMetafieldId inGeometryFieldId) {
        return PredicateFactory.postProcessGeoPropertyPredicate(inPredicate, inGeometryFieldId, null);
    }

    private static IPredicate postProcessGeoPropertyPredicate(@NotNull IPredicate inPredicate, @NotNull IMetafieldId inGeometryFieldId, @Nullable IMetafieldId inOtherGeometryFieldId) {
        Conjunction andPredicate = new Conjunction();
        andPredicate.add(inPredicate);
        andPredicate.add(new NotNullPredicate(inGeometryFieldId));
        if (inOtherGeometryFieldId != null) {
            andPredicate.add(new NotNullPredicate(inOtherGeometryFieldId));
        }
        return andPredicate;
    }

    public static Join createJoin(JoinTypeEnum inJoinType, IMetafieldId inFieldId, String inAlias) {
        return new Join(inJoinType, inFieldId, inAlias);
    }
}
