package com.zpmc.ztos.infra.base.common.model;

import com.zpmc.ztos.infra.base.business.interfaces.IScopeEnum;
import com.zpmc.ztos.infra.base.common.callbacks.CarinaPersistenceCallback;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.common.database.PersistenceTemplatePropagationRequired;
import com.zpmc.ztos.infra.base.common.scopes.Complex;
import com.zpmc.ztos.infra.base.common.scopes.Facility;
import com.zpmc.ztos.infra.base.common.security.ArgoUser;
import com.zpmc.ztos.infra.base.common.utils.InternationalizationUtils;
import com.zpmc.ztos.infra.base.common.utils.LogUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.ListableBeanFactory;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public class ArgoUserContextProvider extends AbstractUserContextProvider{
    public static final String BEAN_ID = "userContextProvider";
    private final Map _cachedUserContextMaps = new HashMap(5);
    private static final Logger LOGGER = Logger.getLogger(ArgoUserContextProvider.class);

    public ArgoUserContextProvider() {
        LogUtils.setLogLevel(((Object)((Object)this)).getClass(), (Level) Level.INFO);
    }

    protected UserContext createSystemContext(String inSystemId, ScopeCoordinates inScopeCoordinates) {
        UserContext userContext = inScopeCoordinates.isScopeGlobal() ? new ArgoUserContext(inSystemId, inSystemId, inScopeCoordinates) : this.createUserContext(inSystemId, inSystemId, inScopeCoordinates);
        return userContext;
    }

    public UserContext createUserContext(final Object inUserGkey, String inUserId, final ScopeCoordinates inScopeCoordinates) {
        final ArgoUserContext userContext = new ArgoUserContext(inUserGkey, inUserId, inScopeCoordinates);
        PersistenceTemplatePropagationRequired pt = new PersistenceTemplatePropagationRequired(this.getSystemUserContext());
        pt.invoke(new CarinaPersistenceCallback(){

            public void doInTransaction() {
                Serializable complexGkey;
                Facility facility;
                String timeZoneId;
                Serializable facilityGkey;
                ArgoUser user;
                Locale locale = null;
                long sleepDelaySecs = 1200L;
                if (inUserGkey instanceof Long && (user = ArgoUser.findByPrimaryKey((Serializable)userContext.getUserKey())) != null) {
                    locale = InternationalizationUtils.getLocale((String)user.getBuserLocaleLanguage(), (String)user.getBuserLocaleCountry());
                    Long delaySecs = user.getBuserSleepDelaySecs();
                    sleepDelaySecs = delaySecs == null ? 1200L : delaySecs;
                }
                TimeZone tz = null;
                if (inScopeCoordinates.isValidScopeLevel(ArgoScope.FACILITY.intValue()) && (facilityGkey = inScopeCoordinates.getScopeLevelCoord(ArgoScope.FACILITY.intValue())) != null && (timeZoneId = (facility = (Facility) HibernateApi.getInstance().get(Facility.class, facilityGkey)).getFcyTimeZoneId()) != null) {
                    tz = TimeZone.getTimeZone(timeZoneId);
                }
                if (tz == null && inScopeCoordinates.isValidScopeLevel(ArgoScope.COMPLEX.intValue()) && (complexGkey = inScopeCoordinates.getScopeLevelCoord(ArgoScope.COMPLEX.intValue())) != null) {
                    Complex complex = (Complex) HibernateApi.getInstance().get(Complex.class, complexGkey);
                    timeZoneId = complex.getCpxTimeZoneId();
                    tz = TimeZone.getTimeZone(timeZoneId);
                }
                userContext.setSleepDelaySecs(sleepDelaySecs);
                if (locale != null) {
                    userContext.setUserLocale(locale);
                }
                if (tz != null) {
                    userContext.setTimeZone(tz);
                }
            }
        });
        if (userContext.getTimeZone() == null) {
            LOGGER.error((Object)("createUserContext: id=" + inUserId + ", scope=" + inScopeCoordinates.toString() + ", COULD NOT RESOLVE TIMEZONE"));
        } else {
            LOGGER.info((Object)("createUserContext: id=" + inUserId + ", scope=" + inScopeCoordinates.toString() + ", timezone=" + userContext.getTimeZone().getDisplayName()));
        }
        return userContext;
    }

    public UserContext getSystemUserContextForScope(IScopeEnum inScope, Serializable inScopeNodeKey, ListableBeanFactory inFactory) {
        return this.findOrCreateInternalUserContextForScope("-system-", inScope, inScopeNodeKey, inFactory);
    }

    public UserContext findOrCreateInternalUserContextForScope(final String inUserId, final IScopeEnum inScope, final Serializable inScopeNodeKey, final ListableBeanFactory inFactory) {
        UserContext uc = null;
        Map userContextCache = this.getCacheForUserId(inUserId);
        HashMap<Serializable, UserContext> innerMap = (HashMap<Serializable, UserContext>)userContextCache.get((Object)inScope);
        if (innerMap != null) {
            uc = (UserContext)innerMap.get(inScopeNodeKey);
        }
        if (uc == null) {
            final UserContext[] userContextHolder = new UserContext[1];
//            PersistenceTemplate pt = new PersistenceTemplate(this.getSystemUserContext());
//            pt.invoke(new CarinaPersistenceCallback(){
//
//                protected void doInTransaction() {
//                    N4EntityScoper scoper = (N4EntityScoper)((Object)inFactory.getBean("entityScoper"));
//                    ScopeCoordinates scopeCoordinates = scoper.getScopeCoordinates(inScope, inScopeNodeKey);
//                    userContextHolder[0] = ArgoUserContextProvider.this.createUserContext(new Long(0L), inUserId, scopeCoordinates);
//                }
//            });
            uc = userContextHolder[0];
            if (innerMap == null) {
                innerMap = new HashMap<Serializable, UserContext>();
                userContextCache.put(inScope, innerMap);
            }
            innerMap.put(inScopeNodeKey, uc);
        }
        return uc;
    }

    private Map getCacheForUserId(String inUserId) {
        HashMap map = (HashMap)this._cachedUserContextMaps.get(inUserId);
        if (map == null) {
            map = new HashMap(5);
            this._cachedUserContextMaps.put(inUserId, map);
        }
        return map;
    }

    public UserContext createGuestUserContext() {
        ScopeCoordinates sc = new ScopeCoordinates(new Serializable[0]);
        return new ArgoUserContext(new Long(0L), "guest", sc);
    }
}
