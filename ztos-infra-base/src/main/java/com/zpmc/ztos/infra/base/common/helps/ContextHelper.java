package com.zpmc.ztos.infra.base.common.helps;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.business.enums.argo.DataSourceEnum;
import com.zpmc.ztos.infra.base.business.enums.core.CacheVariantEnum;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.common.consts.ArgoScopeConsts;
import com.zpmc.ztos.infra.base.common.contexts.PortalApplicationContext;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.common.exceptions.BizFailure;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.model.*;
import com.zpmc.ztos.infra.base.common.scopes.Complex;
import com.zpmc.ztos.infra.base.common.scopes.Facility;
import com.zpmc.ztos.infra.base.common.scopes.Operator;
import com.zpmc.ztos.infra.base.common.scopes.Yard;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class ContextHelper {

    protected static final Logger LOGGER = Logger.getLogger(ContextHelper.class);
    private static final String XPS_UID = "-xps-";
    private static final String EDI_UID = "-edi-";
    private static final String SNX_UID = "-snx-";
    private static final String JMS_UID = "-jms-";
    private static final String NOTICES_UID = "-notices-";
    private static final String DATA_SOURCE_KEY = "DATA_SOURCE_KEY";
    private static final String SUPPRESS_NOTICES_KEY = "SUPPRESS_NOTICES_KEY";
    private static final String THREAD_SNX_SUPRESS_INTERNAL_UNIT_EVENT = "THREAD_SNX_SUPRESS_INTERNAL_UNIT_EVENT";
    private static final String EXTERNAL_UPDATER_USER_ID = "EXTERNAL_UPDATER_USER_ID";
    private static final String EDI_POSTING_CONTEXT = "EDI_POSTING_CONTEXT";
    private static final String EXTERNAL_EVENT_TIME = "EXTERNAL_EVENT_TIME";
    private static final String EXTERNAL_SEQUENCE_NUMBER = "EXTERNAL_SEQUENCE_NUMBER";
    private static final String ARGO_TXN_LEVEL_CACHE_LOOKUP_KEY = "ARGO_TXN_LEVEL_CACHE_LOOKUP_KEY";
    private static final String EDI_BATCH_NBR = "EDI_BATCH_GKEY";
    private static final String MANUAL_POSTING = "MANUAL_POSTING";
    private static final String IS_UPGRADE = "IS_UPGRADE";

    @Nullable
    public static UserContext getThreadUserContext() {
        TransactionParms tp = TransactionParms.getBoundParms();
        return tp == null ? null : tp.getUserContext();
    }

    @Nullable
    public static String getThreadUserId() {
        UserContext uc = ContextHelper.getThreadUserContext();
        return uc == null ? null : uc.getUserId();
    }

    @Nullable
    public static String getThreadExternalId() {
        UserContext uc = ContextHelper.getThreadUserContext();
        return uc == null ? null : uc.getExternalId();
    }

    @Nullable
    public static TimeZone getThreadUserTimezone() {
        UserContext uc = ContextHelper.getThreadUserContext();
        return uc == null ? null : uc.getTimeZone();
    }

//    public static String formatTimestamp(Date inDate) {
//        UserContext uc = ContextHelper.getThreadUserContext();
//        if (uc == null) {
//            return inDate.toString();
//        }
//        ITranslationContext translationContext = TranslationUtils.getTranslationContext((UserContext)uc);
//        return translationContext.getDateConverter().getDateTimeString(inDate);
//    }
//
//    public static String formatTimestamp(Date inDate, TimeZone inTimeZone) {
//        UserContext uc = ContextHelper.getThreadUserContext();
//        if (uc == null) {
//            return inDate.toString();
//        }
//        ITranslationContext translationContext = TranslationUtils.getTranslationContext((UserContext)uc);
//        return translationContext.getDateConverter().getDateTimeString(inTimeZone, inDate);
//    }
//
//    public static String formatDate(Date inDate, TimeZone inTimeZone) {
//        UserContext uc = ContextHelper.getThreadUserContext();
//        if (uc == null) {
//            return inDate.toString();
//        }
//        ITranslationContext translationContext = TranslationUtils.getTranslationContext((UserContext)uc);
//        return translationContext.getDateConverter().getDateOnlyString(inTimeZone, inDate);
//    }

    @Nullable
    public static IMessageCollector getThreadMessageCollector() {
        TransactionParms tp = TransactionParms.getBoundParms();
        return tp == null ? null : tp.getMessageCollector();
    }

    @Nullable
    public static Serializable getThreadOperatorKey() {
        return ContextHelper.getThreadScopeKey(ArgoScopeConsts.OPERATOR);
    }

    @Nullable
    public static Serializable getThreadComplexKey() {
        return ContextHelper.getThreadScopeKey(ArgoScopeConsts.COMPLEX);
    }

    @Nullable
    public static Serializable getThreadFacilityKey() {
        return ContextHelper.getThreadScopeKey(ArgoScopeConsts.FACILITY);
    }

    @Nullable
    public static Serializable getThreadYardKey() {
        return ContextHelper.getThreadScopeKey(ArgoScopeConsts.YARD);
    }

    @Nullable
    public static Serializable getThreadCarrierVisitKey() {
        UserContext uc = ContextHelper.getThreadUserContext();
        if (uc == null) {
            return null;
        }
        if (uc instanceof ArgoUserContext) {
            return ((ArgoUserContext)uc).getCurrentCarrierVisitGkey();
        }
        LOGGER.error((Object)("getThreadCarrierKey: UserContext not an ArgoUserContext! " + (Object)uc));
        return null;
    }

    @Nullable
    public static Serializable getThreadGateKey() {
        UserContext uc = ContextHelper.getThreadUserContext();
        if (uc == null) {
            return null;
        }
        if (uc instanceof ArgoUserContext) {
            return ((ArgoUserContext)uc).getCurrentGateGkey();
        }
        LOGGER.error((Object)("getThreadCarrierKey: UserContext not an ArgoUserContext! " + (Object)uc));
        return null;
    }

    @Nullable
    public static Operator getThreadOperator() {
        return (Operator) ContextHelper.getEntity(Operator.class, ContextHelper.getThreadOperatorKey());
    }

    @Nullable
    public static Complex getThreadComplex() {
        return (Complex) ContextHelper.getEntity(Complex.class, ContextHelper.getThreadComplexKey());
    }

    @Nullable
    public static Facility getThreadFacility() {
        return (Facility) ContextHelper.getEntity(Facility.class, ContextHelper.getThreadFacilityKey());
    }

    @Nullable
    public static String getThreadFacilityId() {
        Facility facility = ContextHelper.getThreadFacility();
        return facility == null ? null : facility.getFcyId();
    }

    @Nullable
    public static Yard getThreadYard() {
        return (Yard) ContextHelper.getEntity(Yard.class, ContextHelper.getThreadYardKey());
    }

    @Nullable
    public static String getThreadYardId() {
        Yard yrd = ContextHelper.getThreadYard();
//        return yrd == null ? null : yrd.getYrdId();
        return "none";
    }

    public static Yard getThreadDefaultYard() {
        Yard yard = ContextHelper.getThreadYard();
        if (yard == null) {
            Facility facility = ContextHelper.getThreadFacility();
            if (facility == null) {
                throw BizFailure.create((IPropertyKey) IArgoPropertyKeys.NO_FACILITY_IN_CONTEXT, null);
            }
            try {
                yard = facility.getActiveYard();
            }
            catch (BizViolation inBizViolation) {
                LOGGER.error((Object)"getThreadDefaultYard: Multiple active yards in facility");
            }
        }
        return yard;
    }

    public static void setThreadDataSource(DataSourceEnum inDataSource) {
        TransactionParms tp = TransactionParms.getBoundParms();
        if (tp == null) {
            throw BizFailure.create((String)"attempt to set thread DataSource, but no bound parms");
        }
        tp.putApplicationParm((Object)DATA_SOURCE_KEY, (Object)inDataSource);
    }

    public static void setThreadDomainQueryResult(Object inKey, Object inCachedObject) {
        TransactionParms tp = TransactionParms.getBoundParms();
        if (tp == null) {
            throw BizFailure.create((String)"attempt to get per transaction data, but no bound parms");
        }
        HashMap<Object, Object> map = (HashMap<Object, Object>)tp.getApplicationParm((Object)ARGO_TXN_LEVEL_CACHE_LOOKUP_KEY);
        if (map == null) {
            map = new HashMap<Object, Object>();
            tp.putApplicationParm((Object)ARGO_TXN_LEVEL_CACHE_LOOKUP_KEY, map);
        }
        Object old = map.put(inKey, inCachedObject);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug((Object)("putting object in argo domain query cache: " + inCachedObject + " old value was : " + old));
        }
    }

    public static Object getThreadDomainQueryResult(Object inKey) {
        TransactionParms tp = TransactionParms.getBoundParms();
        if (tp == null) {
            throw BizFailure.create((String)"attempt to get per transaction data, but no bound parms");
        }
        Map map = (Map)tp.getApplicationParm((Object)ARGO_TXN_LEVEL_CACHE_LOOKUP_KEY);
        if (map == null) {
            return null;
        }
        Object result = map.get(inKey);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug((Object)("returning result from argo domain query cache: " + result + " for key: " + inKey));
        }
        return result;
    }

    public static void setThreadSuppressNotices(Boolean inSuppress) {
        TransactionParms tp = TransactionParms.getBoundParms();
        if (tp == null) {
            throw BizFailure.create((String)"attempt to set thread DataSource, but no bound parms");
        }
        tp.putApplicationParm((Object)SUPPRESS_NOTICES_KEY, (Object)inSuppress);
    }

    public static boolean isThreadSuppressNotices() {
        TransactionParms tp = TransactionParms.getBoundParms();
        Boolean b = (Boolean)tp.getApplicationParm((Object)SUPPRESS_NOTICES_KEY);
        return b != null && b != false;
    }

    public static void setThreadSnxSupressInternalUnitEvents(Boolean inSupressRecordingUnitEvents) {
        TransactionParms tp = TransactionParms.getBoundParms();
        if (tp == null) {
            throw BizFailure.create((String)"attempt to set thread DataSource, but no bound parms");
        }
        tp.putApplicationParm((Object)THREAD_SNX_SUPRESS_INTERNAL_UNIT_EVENT, (Object)inSupressRecordingUnitEvents);
    }

    public static boolean isThreadSnxSupressInternalUnitEvents() {
        TransactionParms tp = TransactionParms.getBoundParms();
        Boolean b = (Boolean)tp.getApplicationParm((Object)THREAD_SNX_SUPRESS_INTERNAL_UNIT_EVENT);
        if (b == null) {
            return false;
        }
        return b;
    }

    public static void setThreadExternalUser(String inExtUserId) {
        TransactionParms tp = TransactionParms.getBoundParms();
        if (tp == null) {
            throw BizFailure.create((String)"attempt to set thread ExternalUserId, but no bound parms");
        }
        tp.putApplicationParm((Object)EXTERNAL_UPDATER_USER_ID, (Object)inExtUserId);
    }

    public static void setThreadExternalEventTime(Date inExtEventTime) {
        TransactionParms tp = TransactionParms.getBoundParms();
        if (tp == null) {
            throw BizFailure.create((String)"attempt to set thread EvenTime, but no bound parms");
        }
        tp.putApplicationParm((Object)EXTERNAL_EVENT_TIME, (Object)inExtEventTime);
    }

    public static void setThreadExternalSequenceNumber(String inSequenceNumber) {
        TransactionParms tp = TransactionParms.getBoundParms();
        if (tp == null) {
            throw BizFailure.create((String)"attempt to set thread Sequence Number, but no bound parms");
        }
        tp.putApplicationParm((Object)EXTERNAL_SEQUENCE_NUMBER, (Object)inSequenceNumber);
    }

    public static boolean isUpdateFromHumanUser() {
        DataSourceEnum dataSource = ContextHelper.getThreadDataSource();
        return DataSourceEnum.USER_LCL.equals((Object)dataSource) || DataSourceEnum.USER_WEB.equals((Object)dataSource) || DataSourceEnum.UNKNOWN.equals((Object)dataSource);
    }

    @NotNull
    public static DataSourceEnum getThreadDataSource() {
        TransactionParms tp = TransactionParms.getBoundParms();
        DataSourceEnum ds = (DataSourceEnum)((Object)tp.getApplicationParm((Object)DATA_SOURCE_KEY));
        return ds == null ? DataSourceEnum.USER_LCL : ds;
    }

    @Nullable
    public static Long getThreadEdiBatchNbr() {
        TransactionParms tp = TransactionParms.getBoundParms();
        Long batchNbr = (Long)tp.getApplicationParm((Object)EDI_BATCH_NBR);
        return null == batchNbr ? null : batchNbr;
    }

    public static void setThreadEdiBatchNbr(Long inBatchNbr) {
        TransactionParms tp = TransactionParms.getBoundParms();
        if (tp == null) {
            throw BizFailure.create((String)"attempt to set Edi batch nbr, but no bound parms");
        }
        tp.putApplicationParm((Object)EDI_BATCH_NBR, (Object)inBatchNbr);
    }

    public static void setTransForXPSManualPosting(Boolean inManualPosting) {
        TransactionParms tp = TransactionParms.getBoundParms();
        if (tp == null) {
            throw BizFailure.create((String)"attempt to set Manual posting to XPS, but no bound parms");
        }
        tp.putApplicationParm((Object)MANUAL_POSTING, (Object)inManualPosting);
    }

    @Nullable
    public static String getThreadExternalUserId() {
        TransactionParms tp = TransactionParms.getBoundParms();
        String userId = (String)tp.getApplicationParm((Object)EXTERNAL_UPDATER_USER_ID);
        if (userId != null) {
            userId = StringUtils.substring((String)userId, (int)0, (int)30);
            return userId;
        }
        UserContext context = tp.getUserContext();
        if (context != null) {
            userId = context.getUserId();
            String externalId = context.getExternalId();
            if (StringUtils.isNotEmpty((String)externalId)) {
                userId = StringUtils.substring((String)(externalId + "/" + context.getUserId()), (int)0, (int)30);
            }
        }
        return userId;
    }

    @Nullable
    public static Date getThreadExternalEventTime() {
        TransactionParms tp = TransactionParms.getBoundParms();
        Date time = (Date)tp.getApplicationParm((Object)EXTERNAL_EVENT_TIME);
        return time;
    }

    @Nullable
    public static String getThreadExternalSequenceNumber() {
        TransactionParms tp = TransactionParms.getBoundParms();
        String seq = (String)tp.getApplicationParm((Object)EXTERNAL_SEQUENCE_NUMBER);
        return seq;
    }

    @Nullable
    public static Serializable getOperatorKey(IRequestContext inRequestContext) {
        return ContextHelper.getScopeKey(ArgoScope.OPERATOR, inRequestContext);
    }

    @Nullable
    public static Serializable getComplexKey(IRequestContext inRequestContext) {
        return ContextHelper.getScopeKey(ArgoScope.COMPLEX, inRequestContext);
    }

    @Nullable
    public static Serializable getFacilityKey(IRequestContext inRequestContext) {
        return ContextHelper.getScopeKey(ArgoScope.FACILITY, inRequestContext);
    }

    @Nullable
    public static Serializable getYardKey(IRequestContext inRequestContext) {
        return ContextHelper.getScopeKey(ArgoScope.YARD, inRequestContext);
    }

    @Nullable
    public static Serializable getThreadScopeKey(Long inScopeLevel) {
        UserContext uc = ContextHelper.getThreadUserContext();
        if (uc == null) {
            return null;
        }
        ScopeCoordinates sc = uc.getScopeCoordinate();
        return sc.getScopeLevelCoord(inScopeLevel.intValue());
    }

    @Nullable
    public static Serializable getScopeKey(Long inScopeLevel, IRequestContext inRequestContext) {
        UserContext uc = inRequestContext.getUserContext();
        if (uc == null) {
            return null;
        }
        ScopeCoordinates sc = uc.getScopeCoordinate();
        return sc.getScopeLevelCoord(inScopeLevel.intValue());
    }

    @Nullable
    private static Object getEntity(Class inClass, Serializable inPrimaryKey) {
       return inPrimaryKey != null ? HibernateApi.getInstance().get(inClass, inPrimaryKey) : null;
    }

    public static String getThreadXpsCacheName() {
        Yard yrd = ContextHelper.getThreadYard();
        if (yrd == null) {
            throw BizFailure.create((String)"attempt to get cache name when not logged-in to a yard.");
        }
        Facility fcy = yrd.getYrdFacility();
        Complex cpx = fcy.getFcyComplex();
        Operator opr = cpx.getCpxOperator();
        return CacheVariantEnum.getBaseNameOfCache((String)opr.getOprId(), (String)cpx.getCpxId(), (String)fcy.getFcyId(), (String)yrd.getYrdId());
    }

 //   @NotNull
    public static String getThreadYardScopeString() {
        Yard yrd = ContextHelper.getThreadYard();
        if (yrd == null) {
            throw BizFailure.create((String)"attempt to get thread scope string when not logged-in to a yard.");
        }
        Facility fcy = yrd.getYrdFacility();
        Complex cpx = fcy.getFcyComplex();
        Operator opr = cpx.getCpxOperator();
//        return NetworkNodeCacheManager.buildScopeString((String)opr.getOprId(), (String)cpx.getCpxId(), (String)fcy.getFcyId(), (String)yrd.getYrdId());
        return "not finished";
    }

    @Nullable
    public static Serializable getOperatorKey(UserContext inUserContext) {
        return ContextHelper.getKeyFromUserContext(inUserContext, ArgoScopeConsts.OPERATOR);
    }

    @Nullable
    public static Serializable getComplexKey(UserContext inUserContext) {
        return ContextHelper.getKeyFromUserContext(inUserContext, ArgoScopeConsts.COMPLEX);
    }

    @Nullable
    public static Serializable getFacilityKey(UserContext inUserContext) {
        return ContextHelper.getKeyFromUserContext(inUserContext, ArgoScopeConsts.FACILITY);
    }

    @Nullable
    public static Serializable getYardKey(UserContext inUserContext) {
        return ContextHelper.getKeyFromUserContext(inUserContext, ArgoScopeConsts.YARD);
    }

    @Nullable
    private static Serializable getKeyFromUserContext(UserContext inUserContext, Long inScope) {
        Serializable scopeKey = inUserContext.getScopeCoordinate().getScopeLevelCoord(inScope.intValue());
        return scopeKey;
    }

    public static UserContext getSystemUserContextForScope(IScopeEnum inScope, Serializable inScopeNodeKey) {
        ArgoUserContextProvider contextProvider = (ArgoUserContextProvider)((Object) PortalApplicationContext.getBean((String)"userContextProvider"));
        return contextProvider.getSystemUserContextForScope(inScope, inScopeNodeKey, Roastery.getBeanFactory());
    }

    public static void removeThreadDomainQueryResult() {
        TransactionParms tp = TransactionParms.getBoundParms();
        if (tp == null) {
            throw BizFailure.create((String)"attempt to get per transaction data, but no bound parms");
        }
        Map map = (Map)tp.getApplicationParm((Object)ARGO_TXN_LEVEL_CACHE_LOOKUP_KEY);
        if (map == null) {
            return;
        }
        //LOGGER.debug((Object)"Clearing per transaction application level cache");
        tp.putApplicationParm((Object)ARGO_TXN_LEVEL_CACHE_LOOKUP_KEY, null);
    }

    public static UserContext getXpsUserContextForScope(IScopeEnum inScope, Serializable inScopeNodeKey) {
        ArgoUserContextProvider contextProvider = (ArgoUserContextProvider)((Object)PortalApplicationContext.getBean((String)"userContextProvider"));
        return contextProvider.findOrCreateInternalUserContextForScope(XPS_UID, inScope, inScopeNodeKey, Roastery.getBeanFactory());
    }

    public static boolean isXpsUserContext(UserContext inUserContext) {
        return XPS_UID.equals(inUserContext.getUserId());
    }

//    public static UserContext getEdiUserContextForScope(IScopeEnum inScope, Serializable inScopeNodeKey) {
//        ArgoUserContextProvider contextProvider = (ArgoUserContextProvider)((Object)PortalApplicationContext.getBean((String)"userContextProvider"));
//        return contextProvider.findOrCreateInternalUserContextForScope(EDI_UID, inScope, inScopeNodeKey, Roastery.getBeanFactory());
//    }
//
//    public static UserContext getNoticesUserContextForScope(UserContext inOriginalUserContext) {
//        if (!XPS_UID.equals(inOriginalUserContext.getUserId())) {
//            return inOriginalUserContext;
//        }
//        ScopeCoordinates sco = inOriginalUserContext.getScopeCoordinate();
//        N4EntityScoper scoper = (N4EntityScoper)((Object)Roastery.getBean((String)"entityScoper"));
//        IScopeNodeEntity scopeNodeEntity = scoper.getScopeNodeEntity(sco);
//        ArgoUserContextProvider contextProvider = (ArgoUserContextProvider)((Object)PortalApplicationContext.getBean((String)"userContextProvider"));
//        UserContext uc = contextProvider.findOrCreateInternalUserContextForScope(NOTICES_UID, scopeNodeEntity.getScopeEnum(), scopeNodeEntity.getPrimaryKey(), Roastery.getBeanFactory());
//        uc.setSecuritySessionId(inOriginalUserContext.getSecuritySessionId());
//        uc.setSleepDelaySecs(inOriginalUserContext.getSleepDelaySecs());
//        uc.setTimeZone(inOriginalUserContext.getTimeZone());
//        uc.setUserLocale(inOriginalUserContext.getUserLocale());
//        return uc;
//    }

//    public static UserContext getSnxUserContextForScope(IScopeEnum inScope, Serializable inScopeNodeKey) {
//        ArgoUserContextProvider contextProvider = (ArgoUserContextProvider)((Object)PortalApplicationContext.getBean((String)"userContextProvider"));
//        return contextProvider.findOrCreateInternalUserContextForScope(SNX_UID, inScope, inScopeNodeKey, Roastery.getBeanFactory());
//    }
//
//    public static UserContext getJmsUserContextForScope(IScopeEnum inScope, Serializable inScopeNodeKey) {
//        ArgoUserContextProvider contextProvider = (ArgoUserContextProvider)((Object)PortalApplicationContext.getBean((String)"userContextProvider"));
//        return contextProvider.findOrCreateInternalUserContextForScope(JMS_UID, inScope, inScopeNodeKey, Roastery.getBeanFactory());
//    }

    private ContextHelper() {
    }

    public static String getScopeName() {
        Yard yrd = ContextHelper.getThreadYard();
        return yrd.getScopeName();
    }

    public static void setThreadEdiPostingContext(EdiPostingContext inEdiPostingContext) {
        TransactionParms tp = TransactionParms.getBoundParms();
        if (tp == null) {
            throw BizFailure.create((String)"attempt to set thread ExternalUserId, but no bound parms");
        }
        tp.putApplicationParm((Object)EDI_POSTING_CONTEXT, (Object)inEdiPostingContext);
    }

    public static EdiPostingContext getThreadEdiPostingContext() {
        TransactionParms tp = TransactionParms.getBoundParms();
        EdiPostingContext ediPostingContext = (EdiPostingContext)tp.getApplicationParm((Object)EDI_POSTING_CONTEXT);
        return ediPostingContext;
    }

    public static void setThreadIsFromUpgrade(boolean inIsUpgrade) {
        TransactionParms tp = TransactionParms.getBoundParms();
        if (tp == null) {
            throw BizFailure.create((String)"attempt to set is upgrade, but no bound parms");
        }
        tp.putApplicationParm((Object)IS_UPGRADE, (Object)inIsUpgrade);
    }

    public static boolean getThreadIsFromUpgrade() {
        TransactionParms tp = TransactionParms.getBoundParms();
        Boolean isUpgrade = (Boolean)tp.getApplicationParm((Object)IS_UPGRADE);
        return null == isUpgrade ? false : isUpgrade;
    }
}
