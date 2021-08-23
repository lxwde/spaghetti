package com.zpmc.ztos.infra.base.common.utils;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.business.enums.argo.CarrierVisitPhaseEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.CheKindEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.LocTypeEnum;
import com.zpmc.ztos.infra.base.business.enums.framework.PredicateVerbEnum;
import com.zpmc.ztos.infra.base.business.equipments.Che;
import com.zpmc.ztos.infra.base.business.equipments.ChePool;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.business.model.CarrierVisit;
import com.zpmc.ztos.infra.base.business.model.PointOfWork;
import com.zpmc.ztos.infra.base.business.predicate.PredicateFactory;
import com.zpmc.ztos.infra.base.business.xps.XpsDbManagerId;
import com.zpmc.ztos.infra.base.business.xps.XpsPkeyGenerator;
import com.zpmc.ztos.infra.base.common.callbacks.CarinaPersistenceCallback;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.common.database.PersistenceTemplate;
import com.zpmc.ztos.infra.base.common.database.PersistenceTemplatePropagationRequired;
import com.zpmc.ztos.infra.base.common.exceptions.BizFailure;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.helps.ContextHelper;
import com.zpmc.ztos.infra.base.common.model.*;
import com.zpmc.ztos.infra.base.common.scopes.*;
import com.zpmc.ztos.infra.base.common.security.ArgoUser;
import com.zpmc.ztos.infra.base.utils.CarinaUtils;
import com.zpmc.ztos.infra.base.utils.QueryUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;

public class ArgoTestUtil {
    private static final Logger LOGGER = Logger.getLogger(ArgoTestUtil.class);
    private static final ThreadLocal<Map> INTERNAL_THREADLOCAL = new ThreadLocal();
    public static final String OPR1 = "OPR1";
    protected static final String OPR2 = "OPR2";
    public static final String CPX11 = "CPX11";
    protected static final String CPX12 = "CPX12";
    public static final String FCY111 = "FCY111";
    public static final String FCY112 = "FCY112";
    protected static final String FCY113 = "FCY113";
    public static final String YRD1111 = "YRD1111";
    public static final String YRD1121 = "YRD1121";
    public static final String YRD1131 = "YRD1131";

    public static IMessageCollector purgeCarrierVisit(UserContext inContext, final String inCvId) {
        PersistenceTemplate cb = new PersistenceTemplate(inContext);
        IMessageCollector mc = cb.invoke(new CarinaPersistenceCallback(){

            public void doInTransaction() {
                Serializable complexGkey = ContextHelper.getThreadComplex().getPrimaryKey();
                ArgoTestUtil.purgeCarrierVisit(complexGkey, inCvId);
            }
        });
        return mc;
    }

    public static void purgeCarrierVisit(Serializable inComplexGkey, String inCvId) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"CarrierVisit").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoField.CV_COMPLEX, (Object)inComplexGkey)).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoField.CV_ID, (Object)inCvId));
        List cvList = Roastery.getHibernateApi().findEntitiesByDomainQuery(dq);
        for (Object cv : cvList) {
            if (LocTypeEnum.VESSEL.equals((Object)((CarrierVisit)cv).getCvCarrierMode()) || LocTypeEnum.TRAIN.equals((Object)((CarrierVisit)cv).getCvCarrierMode())) {
                ((CarrierVisit)cv).setCvVisitPhase(CarrierVisitPhaseEnum.CREATED);
            }
            ArgoUtils.carefulDelete(cv);
        }
    }

    public static Serializable addOrUpdateUser(final String inUserId, final String inLastName, final String inOpr, final String inCpx, final String inFcy, final String inYrd) {
        final Serializable[] userGkey = new Serializable[1];
        PersistenceTemplatePropagationRequired template = new PersistenceTemplatePropagationRequired(UserContextUtils.getSystemUserContext());
        IMessageCollector mc = template.invoke(new CarinaPersistenceCallback(){

            public void doInTransaction() {
                ArgoUser user = ArgoUser.findOrCreateArgoUser(inUserId, inUserId, inUserId, inLastName);
                user.setScope(inOpr, inCpx, inFcy, inYrd);
                user.setBuserRoleList(null);
                userGkey[0] = user.getPrimaryKey();
            }
        });
        if (mc.hasError()) {
            throw BizFailure.create((String)("Error creating user " + inUserId + ":" + (Object)mc));
        }
        return userGkey[0];
    }

    public static ScopeCoordinates getScopeCoordinates(final String inOpr, final String inCpx, final String inFcy, final String inYrd) {
        final ScopeCoordinates[] results = new ScopeCoordinates[1];
        PersistenceTemplatePropagationRequired template = new PersistenceTemplatePropagationRequired(UserContextUtils.getSystemUserContext());
        IMessageCollector mc = template.invoke(new CarinaPersistenceCallback(){

            public void doInTransaction() {
//                ArgoManager argoManager = (ArgoManager)Roastery.getBean((String)"argoManager");
//                results[0] = argoManager.getScopeCoordinates(inOpr, inCpx, inFcy, inYrd);
            }
        });
        if (mc.hasError()) {
            throw BizFailure.create((String)("creating scope coordinates failed:" + (Object)mc));
        }
        return results[0];
    }

    @NotNull
    public static String getBusinessCoordinatesAsString(@NotNull ScopeCoordinates inScopeCoordinates) {
        return StringUtils.arrayToDelimitedString((Object[]) ArgoTestUtil.getBusinessCoordsAsArray(inScopeCoordinates), (String)"/");
    }

    @NotNull
    public static String[] getBusinessCoordsAsArray(final @NotNull ScopeCoordinates inScopeCoordinates) {
        if (inScopeCoordinates.isScopeGlobal()) {
            return new String[0];
        }
        final ArrayList businessCoords = new ArrayList();
        CarinaPersistenceCallback carinaPersistenceCallback = new CarinaPersistenceCallback(){

            public void doInTransaction() {
                Serializable yardGkey;
                Serializable facilityGkey;
                Serializable complexGkey;
                Serializable operatorGkey = inScopeCoordinates.getScopeLevelCoord(ScopeCoordinates.SCOPE_LEVEL_1.intValue());
                if (operatorGkey != null) {
                    businessCoords.add(ArgoTestUtil.getIdFromKey((Class<? extends IEntity>) Operator.class, operatorGkey, IArgoField.OPR_ID));
                }
                if ((complexGkey = inScopeCoordinates.getScopeLevelCoord(ScopeCoordinates.SCOPE_LEVEL_2.intValue())) != null) {
                    businessCoords.add(ArgoTestUtil.getIdFromKey(Complex.class, complexGkey, IArgoField.CPX_ID));
                }
                if ((facilityGkey = inScopeCoordinates.getScopeLevelCoord(ScopeCoordinates.SCOPE_LEVEL_3.intValue())) != null) {
                    businessCoords.add(ArgoTestUtil.getIdFromKey(Facility.class, facilityGkey, IArgoField.FCY_ID));
                }
                if ((yardGkey = inScopeCoordinates.getScopeLevelCoord(ScopeCoordinates.SCOPE_LEVEL_4.intValue())) != null) {
                    businessCoords.add(ArgoTestUtil.getIdFromKey(Yard.class, yardGkey, IArgoField.YRD_ID));
                }
            }
        };
        PersistenceTemplatePropagationRequired template = new PersistenceTemplatePropagationRequired(UserContextUtils.getSystemUserContext());
        IMessageCollector messageCollector = template.invoke(carinaPersistenceCallback);
        if (messageCollector.hasError()) {
            throw BizFailure.wrap((Throwable) CarinaUtils.convertToBizViolation((IMessageCollector)messageCollector));
        }
        return (String[]) businessCoords.toArray(new String[businessCoords.size()]);
    }

    @NotNull
    public static ScopeCoordinateIdsWsType getScopeCoordinatesWSType(@NotNull ScopeCoordinates inScopeCoordinates) {
        String[] businessCoordinates = ArgoTestUtil.getBusinessCoordsAsArray(inScopeCoordinates);
        ScopeCoordinateIdsWsType wsScopeCoordinates = new ScopeCoordinateIdsWsType();
        if (businessCoordinates.length < 1) {
            return wsScopeCoordinates;
        }
        wsScopeCoordinates.setOperatorId(businessCoordinates[0]);
        if (businessCoordinates.length < 2) {
            return wsScopeCoordinates;
        }
        wsScopeCoordinates.setComplexId(businessCoordinates[1]);
        if (businessCoordinates.length < 3) {
            return wsScopeCoordinates;
        }
        wsScopeCoordinates.setFacilityId(businessCoordinates[2]);
        if (businessCoordinates.length < 4) {
            return wsScopeCoordinates;
        }
        wsScopeCoordinates.setYardId(businessCoordinates[3]);
        return wsScopeCoordinates;
    }

    public static ScopeCoordinateIdsWsType getScopeCoordinatesWSType(String inOpr, String inCpx, String inFcy, String inYrd) {
        ScopeCoordinateIdsWsType scopeCoordinates = new ScopeCoordinateIdsWsType();
        scopeCoordinates.setOperatorId(inOpr);
        scopeCoordinates.setComplexId(inCpx);
        scopeCoordinates.setFacilityId(inFcy);
        scopeCoordinates.setYardId(inYrd);
        return scopeCoordinates;
    }

    public static ScopeCoordinateIdsWsType getScopeCoordinatesWSType() {
        return ArgoTestUtil.getScopeCoordinatesWSType(OPR1, CPX11, FCY111, YRD1111);
    }

    public static String getScopeCoordinatesAsString() {
        ScopeCoordinateIdsWsType scopeCoordinates = ArgoTestUtil.getScopeCoordinatesWSType();
        return scopeCoordinates.getOperatorId() + "/" + scopeCoordinates.getComplexId() + "/" + scopeCoordinates.getFacilityId() + "/" + scopeCoordinates.getYardId();
    }

    public static void setThreadLocal(Map inMap) {
        INTERNAL_THREADLOCAL.set(inMap);
    }

    public static Map getThreadLocal() {
        return INTERNAL_THREADLOCAL.get();
    }

    public static UserContext getSystemUserContext(String inOpr, String inCpx, String inFcy, String inYard) {
        ScopeCoordinates scopeCoordinates = ArgoTestUtil.getScopeCoordinates(inOpr, inCpx, inFcy, inYard);
        UserContext context = UserContextUtils.getSystemUserContext((ScopeCoordinates)scopeCoordinates);
        return context;
    }

    public static String getUniqueId(String inId) {
        return ArgoTestUtil.getUniqueId(inId, 7);
    }

    public static String getUniqueId(String inId, int inOffset) {
        String strToApnd = String.valueOf(System.currentTimeMillis());
        return inId + strToApnd.substring(strToApnd.length() - inOffset, strToApnd.length());
    }

    public static IMessageTranslator getMessageTranslator() {
        return TranslationUtils.getTranslationContext((UserContext)ContextHelper.getThreadUserContext()).getMessageTranslator();
    }

    public static IMessageTranslator getMessageTranslator(UserContext inContext) {
        return TranslationUtils.getTranslationContext((UserContext)inContext).getMessageTranslator();
    }

//    @NotNull
//    public static Class getGroovyClass(@NotNull String inPath) throws IOException, BizViolation {
//        GroovyClassCache cache = (GroovyClassCache)Roastery.getBean((String)"generalGroovyClassCache");
//        return ArgoTestUtil.getGroovyClass(cache, inPath);
//    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
//    @NotNull
//    public static Class getGroovyClass(@NotNull GroovyClassCache inClassCache, @NotNull String inPath) throws IOException, BizViolation {
//        String groovyScript;
//        Resource groovyFile = ResourceUtils.loadResource((String)inPath);
//        StringBuilder fileContents = new StringBuilder();
//        try (BufferedReader myReader = new BufferedReader(new InputStreamReader(groovyFile.getInputStream()));){
//            String line;
//            while ((line = myReader.readLine()) != null) {
//                fileContents.append(line).append('\n');
//            }
//            groovyScript = fileContents.toString();
//        }
//        Class groovyClass = inClassCache.getGroovyClass(groovyScript);
//        if (groovyClass == null) {
//            throw BizViolation.create((IPropertyKey)IArgoPropertyKeys.COULD_NOT_COMPILE_CLASS, null, (Object)inPath);
//        }
//        return groovyClass;
//    }

//    public static <T> T createGroovyClassInstance(@NotNull GroovyClassCache inClassCache, @NotNull String inPath) throws IOException, BizViolation {
//        Class groovyClass = ArgoTestUtil.getGroovyClass(inClassCache, inPath);
//        try {
//            Object groovyInstance = groovyClass.newInstance();
//            return groovyInstance;
//        }
//        catch (Exception ex) {
//            throw BizViolation.create((IPropertyKey)IArgoPropertyKeys.INSTANTIATION_ERROR, null, (Object)inPath, (Object)ex.getMessage());
//        }
//    }

    public static <T> T createGroovyClassInstance(String inPath) throws IOException, BizViolation {
//        GroovyClassCache cache = (GroovyClassCache)Roastery.getBean((String)"generalGroovyClassCache");
//        return ArgoTestUtil.createGroovyClassInstance(cache, inPath);
        return null;
    }

    public static String invokeBasicWS(UserContext inUserContext, String inScopeString, String inRequestXml) {
//        ArgoWebServicesFacade ws = new ArgoWebServicesFacade(inUserContext);
//        LOGGER.info((Object)inRequestXml);
//        String xmlResponse = ws.basicInvoke(inScopeString, inRequestXml);
//        LOGGER.info((Object)xmlResponse);
//        return xmlResponse;
        return null;
    }

    public static ChePool findOrCreateChePool(String inPoolName) {
        Yard yard = ContextHelper.getThreadDefaultYard();
        if (yard == null) {
            throw BizFailure.create((String)"Unable to determine the yard for current context");
        }
        Long yrdGkey = yard.getYrdGkey();
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"ChePool").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoField.POOL_NAME, (Object)inPoolName)).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoField.POOL_YARD, (Object)yrdGkey));
        List chePoolList = HibernateApi.getInstance().findEntitiesByDomainQuery(dq);
        ChePool chePool = chePoolList == null || chePoolList.isEmpty() ? ArgoTestUtil.createChePool(inPoolName, yard) : (ChePool)chePoolList.get(0);
        return chePool;
    }

    private static ChePool createChePool(String inPoolName, Yard inYard) {
        ChePool pool = new ChePool();
        pool.setPoolYard(inYard);
        pool.setPoolPkey(ArgoTestUtil.getChePoolPkey(inYard.getYrdGkey()));
        pool.setPoolName(inPoolName);
        HibernateApi.getInstance().save((Object)pool);
        return pool;
    }

    private static Long getChePoolPkey(Long inYrdGkey) {
        return XpsPkeyGenerator.generate((int)9, (String)"ChePool", (IMetafieldId) IArgoField.POOL_PKEY, (IMetafieldId) IArgoField.POOL_YARD, (Serializable)inYrdGkey);
    }

    public static Che findOrCreateChe(String inCheShortName, CheKindEnum inCheKind, ChePool inChePool, @Nullable PointOfWork inPow) {
        Yard yard = ContextHelper.getThreadYard();
        Che che = Che.findCheByShortNameProxy(inCheShortName);
        if (che == null) {
            XpsDbManagerId.registerEntity(Che.class, (int)11);
            che = Che.findOrCreateChe(inCheShortName, yard, true);
        }
        assert (che != null);
        che.updateChePoolEntity(inChePool);
        che.setCheKindEnum(inCheKind);
        if (inPow != null) {
            che.setChePowPkey(inPow.getPointofworkPkey());
        }
        HibernateApi.getInstance().save((Object)che);
        return che;
    }

    private ArgoTestUtil() {
    }

    @NotNull
    private static String getIdFromKey(@NotNull Class<? extends IEntity> inEntityClass, @NotNull Serializable inEntityGkey, @NotNull IMetafieldId inIdMetafieldId) {
        IEntity entity = (IEntity) HibernateApi.getInstance().load(inEntityClass, inEntityGkey);
        String id = entity.getFieldString(inIdMetafieldId);
        assert (id != null) : String.format("null identifier for entity(%s)", new Object[]{entity});
        return id;
    }

    public static Date incrementByMinute(Date inTime, int inMinutes) {
        Calendar now = Calendar.getInstance();
        now.setTime(inTime);
        now.add(12, inMinutes);
        return now.getTime();
    }

    public static BizResponse executeBizRequest(BizRequest inBizRequest, String inApiName) {
//        BizResponse response = CrudDelegate.executeBizRequest((BizRequest)inBizRequest, (String)inApiName);
//        ArgoDelegate.storeEntitiesOnError(inBizRequest.getUserContext(), (IMessageCollector)response);
//        return response;
        return null;
    }

    public static IValueHolder createEntityMappingPredicateValueHolder(@NotNull IMetafieldId inMfid, @NotNull PredicateVerbEnum inVerb, @Nullable Object inValue) {
        return ArgoTestUtil.createEntityMappingPredicateValueHolder(inMfid, inVerb, inValue, null, null);
    }

    public static IValueHolder createEntityMappingPredicateValueHolder(@NotNull IMetafieldId inMfid, @NotNull PredicateVerbEnum inVerb, @Nullable Object inValue, @Nullable IValueHolder inNextPredicate, @Nullable IValueHolder inSubPredicate) {
        ValueObject predicateMapping = new ValueObject("EntityMappingPredicate");
        predicateMapping.setFieldValue(IArgoField.EMAPP_METAFIELD, (Object)inMfid);
        predicateMapping.setFieldValue(IArgoField.EMAPP_VERB, (Object)inVerb);
        predicateMapping.setFieldValue(IArgoField.EMAPP_VALUE, inValue);
        predicateMapping.setFieldValue(IArgoField.EMAPP_UI_VALUE, (Object)(inValue == null ? "" : inValue.toString()));
        predicateMapping.setFieldValue(IArgoField.EMAPP_NEGATED, (Object)false);
        predicateMapping.setFieldValue(IArgoField.EMAPP_NEXT_PREDICATE, (Object)inNextPredicate);
        predicateMapping.setFieldValue(IArgoField.EMAPP_SUB_PREDICATE, (Object)inSubPredicate);
        return predicateMapping;
    }

    static {
        LOGGER.setLevel(Level.INFO);
    }
}
