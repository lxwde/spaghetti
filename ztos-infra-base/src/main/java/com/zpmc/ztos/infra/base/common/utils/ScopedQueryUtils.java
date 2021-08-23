package com.zpmc.ztos.infra.base.common.utils;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.business.model.Conjunction;
import com.zpmc.ztos.infra.base.business.predicate.Disjunction;
import com.zpmc.ztos.infra.base.business.predicate.PredicateFactory;
import com.zpmc.ztos.infra.base.common.model.Ordering;
import com.zpmc.ztos.infra.base.common.model.Roastery;
import com.zpmc.ztos.infra.base.common.model.ScopeCoordinates;
import com.zpmc.ztos.infra.base.common.model.UserContext;
import com.zpmc.ztos.infra.base.utils.QueryUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.io.Serializable;

public class ScopedQueryUtils {
    private static final Logger LOGGER = Logger.getLogger(ScopedQueryUtils.class);

    private ScopedQueryUtils() {
    }

    public static IDomainQuery getEntityQueryForBroadestAllowedScope(@NotNull UserContext inUserContext, String inEntityName, IMetafieldId inScopeLevelFieldId, IMetafieldId inScopeGkeyFieldId) {
        IDomainQuery dq = QueryUtils.createDomainQuery(inEntityName);
        dq.setScopingEnabled(false);
        ScopeCoordinates broadestAllowedScope = inUserContext.getBroadestAllowedScope();
        IEntityScoper entityScoper = (IEntityScoper) Roastery.getBean("entityScoper");
        IPredicate predicate = entityScoper.getPredicateForBroadestAllowedScope(broadestAllowedScope, inScopeLevelFieldId, inScopeGkeyFieldId);
        if (predicate != null) {
            dq.addDqPredicate(predicate);
        }
        return dq;
    }

    public static IDomainQuery getEntityQueryForBroadestViewableScope(@NotNull UserContext inUserContext, String inEntityName, IMetafieldId inScopeLevelFieldId, IMetafieldId inScopeGkeyFieldId) {
        IDomainQuery dq = QueryUtils.createDomainQuery(inEntityName);
        dq.setScopingEnabled(false);
        ScopeCoordinates broadestAllowedScope = inUserContext.getBroadestAllowedScope();
        IEntityScoper entityScoper = (IEntityScoper) Roastery.getBean("entityScoper");
        Disjunction disJunction = PredicateFactory.disjunction();
        IPredicate subtreeScopePredicate = entityScoper.getPredicateForBroadestAllowedScope(broadestAllowedScope, inScopeLevelFieldId, inScopeGkeyFieldId);
        if (subtreeScopePredicate != null) {
            disJunction.add(subtreeScopePredicate);
        }
        if (!broadestAllowedScope.isScopeGlobal()) {
            ScopeCoordinates nextUpperScope = broadestAllowedScope.getParentCoordinate();
            while (!nextUpperScope.isScopeGlobal()) {
                int nextScopeLevel = nextUpperScope.getMostSpecificScopeLevel();
                disJunction.add(PredicateFactory.conjunction().add(PredicateFactory.eq(inScopeLevelFieldId, nextScopeLevel)).add(PredicateFactory.eq(inScopeGkeyFieldId, nextUpperScope.getScopeLevelCoord(nextScopeLevel))));
                nextUpperScope = nextUpperScope.getParentCoordinate();
            }
            disJunction.add(PredicateFactory.eq(inScopeLevelFieldId, ScopeCoordinates.GLOBAL_LEVEL));
        }
        if (disJunction.getPredicateCount() > 0) {
            dq.addDqPredicate(disJunction);
        }
        return dq;
    }

    public static IDomainQuery getEntityQueryForScope(@NotNull ScopeCoordinates inScopeCoordinates, String inEntityName, IMetafieldId inScopeLevelFieldId, IMetafieldId inScopeGkeyFieldId) {
        IDomainQuery dq = QueryUtils.createDomainQuery(inEntityName);
        dq.setScopingEnabled(false);
        IEntityScoper entityScoper = (IEntityScoper) Roastery.getBean("entityScoper");
        IPredicate predicate = entityScoper.getPredicateForBroadestAllowedScope(inScopeCoordinates, inScopeLevelFieldId, inScopeGkeyFieldId);
        if (predicate != null) {
            dq.addDqPredicate(predicate);
        }
        return dq;
    }

    @Nullable
    public static IDomainQuery getScopedChildrenLovKeysQuery(UserContext inUserContext, int inScopeLevel) {
        IEntityScoper scoper = (IEntityScoper) Roastery.getBean("entityScoper");
        ScopeCoordinates broadestAllowedScopeCoordinates = inUserContext.getBroadestAllowedScope();
        IDomainQuery dq = null;
        if ((long)inScopeLevel > ScopeCoordinates.GLOBAL_LEVEL && (dq = scoper.createScopeNodeEntityLevelQuery(scoper.getScopeEnum(inScopeLevel), broadestAllowedScopeCoordinates)) != null) {
            dq.setScopingEnabled(false);
        }
        return dq;
    }

    @Nullable
    public static String getScopeCoordsName(@NotNull ScopeCoordinates inCoords) {
        if (ScopeCoordinates.GLOBAL_SCOPE == inCoords) {
            return null;
        }
        IEntityScoper scoper = (IEntityScoper) Roastery.getBean("entityScoper");
        return scoper.getScopeNodeEntity(inCoords).getPathName();
    }

    public static Object getScopeLevelName(@NotNull ScopeCoordinates inCoords) {
        IEntityScoper scoper = (IEntityScoper) Roastery.getBean("entityScoper");
        return scoper.getScopeEnum(inCoords.getMaxScopeLevel());
    }

    public static Object getScopeLevelName(@NotNull Long inScopeLevel) {
        IEntityScoper scoper = (IEntityScoper) Roastery.getBean("entityScoper");
        return scoper.getScopeEnum(inScopeLevel.intValue());
    }

    @Deprecated
    public static IDomainQuery getOperatedOnExtensionQueryForBroadestScope(@NotNull UserContext inUserContext) {
        IDomainQuery dq = QueryUtils.createDomainQuery("Extension");
        dq.setScopingEnabled(false);
        ScopeCoordinates broadestAllowedScope = inUserContext.getBroadestAllowedScope();
        if (!broadestAllowedScope.isScopeGlobal()) {
            Disjunction disJunction = PredicateFactory.disjunction();
            disJunction.add(PredicateFactory.eq(IExtensionField.EXT_SCOPE_LEVEL, ScopeCoordinates.GLOBAL_LEVEL));
            disJunction.add(PredicateFactory.conjunction().add(PredicateFactory.eq(IExtensionField.EXT_SCOPE_LEVEL, ScopeCoordinates.SCOPE_LEVEL_1)).add(PredicateFactory.eq(IExtensionField.EXT_SCOPE_GKEY, broadestAllowedScope.getScopeLevelCoord(ScopeCoordinates.SCOPE_LEVEL_1.intValue()))));
            dq.addDqPredicate(disJunction);
        }
        return dq;
    }

    public static IPredicate getEntityPredicateForEffectiveScope(ScopeCoordinates inScopeCoordinates, IMetafieldId inScopeLevelFieldId, IMetafieldId inScopeGkeyFieldId) {
        Disjunction disJunction = PredicateFactory.disjunction();
        int mostSpecificLevel = inScopeCoordinates.getMostSpecificScopeLevel();
        if (mostSpecificLevel > ScopeCoordinates.GLOBAL_LEVEL.intValue()) {
            ScopeCoordinates nextUpperScope = inScopeCoordinates;
            while (!nextUpperScope.isScopeGlobal()) {
                int nextScopeLevel = nextUpperScope.getMostSpecificScopeLevel();
                disJunction.add(PredicateFactory.conjunction().add(PredicateFactory.eq(inScopeLevelFieldId, nextScopeLevel)).add(PredicateFactory.eq(inScopeGkeyFieldId, nextUpperScope.getScopeLevelCoord(nextScopeLevel))));
                nextUpperScope = nextUpperScope.getParentCoordinate();
            }
        }
        disJunction.add(PredicateFactory.eq(inScopeLevelFieldId, ScopeCoordinates.GLOBAL_LEVEL));
        return disJunction;
    }

    @Deprecated
    public static IDomainQuery getScopedQueryExtensionsForType(UserContext inUserContext, String inExtensionType) {
        IDomainQuery dq = ScopedQueryUtils.getOperatedOnExtensionQueryForBroadestScope(inUserContext);
        dq.addDqPredicate(PredicateFactory.conjunction().add(PredicateFactory.eq(IExtensionField.EXT_TYPE, inExtensionType)));
        return dq;
    }

    public static IDomainQuery getScopedQueryExtensionsForScopedTrigger(int inScopeLevel, Serializable inScopeGkey, @NotNull String inExtensionType) {
        IDomainQuery dq = QueryUtils.createDomainQuery("Extension");
        dq.setScopingEnabled(false);
        Conjunction conj = PredicateFactory.conjunction();
        conj.add(PredicateFactory.eq(IExtensionField.EXT_TYPE, inExtensionType));
        conj.add(PredicateFactory.eq(IExtensionField.EXT_ENABLED, Boolean.TRUE));
        if (inScopeLevel == ScopeCoordinates.GLOBAL_LEVEL.intValue()) {
            conj.add(PredicateFactory.eq(IExtensionField.EXT_SCOPE_LEVEL, ScopeCoordinates.GLOBAL_LEVEL));
        } else {
            Disjunction disJunction = PredicateFactory.disjunction();
            disJunction.add(PredicateFactory.eq(IExtensionField.EXT_SCOPE_LEVEL, ScopeCoordinates.GLOBAL_LEVEL));
            if (inScopeGkey == null || inScopeLevel < ScopeCoordinates.GLOBAL_LEVEL.intValue()) {
                LOGGER.error((Object)("Invalid scope level/gkey: " + inScopeLevel + "/" + inScopeGkey));
            } else {
                IEntityScoper entityScoper = (IEntityScoper) Roastery.getBean("entityScoper");
                ScopeCoordinates nextUpperScope = entityScoper.getScopeCoordinates(entityScoper.getScopeEnum(inScopeLevel), inScopeGkey);
                while (!nextUpperScope.isScopeGlobal()) {
                    int nextScopeLevel = nextUpperScope.getMostSpecificScopeLevel();
                    disJunction.add(PredicateFactory.conjunction().add(PredicateFactory.eq(IExtensionField.EXT_SCOPE_LEVEL, nextScopeLevel)).add(PredicateFactory.eq(IExtensionField.EXT_SCOPE_GKEY, String.valueOf(nextUpperScope.getScopeLevelCoord(nextScopeLevel)))));
                    nextUpperScope = nextUpperScope.getParentCoordinate();
                }
            }
            conj.add(disJunction);
        }
        dq.addDqPredicate(conj);
        return dq;
    }

    public static IDomainQuery getScopedTriggersForEntity(ScopeCoordinates inScopeCoordinates, String inEntityname) {
        IDomainQuery dq = QueryUtils.createDomainQuery("ExtensionInjection");
        dq.addDqPredicate(ScopedQueryUtils.getEntityPredicateForEffectiveScope(inScopeCoordinates, IExtensionField.EXTINJ_SCOPE_LEVEL, IExtensionField.EXTINJ_SCOPE_GKEY)).addDqPredicate(PredicateFactory.eq(IExtensionField.EXTINJ_ENABLED, Boolean.TRUE)).addDqPredicate(PredicateFactory.eq(IExtensionField.EXTINJ_ENTITY_NAME, inEntityname)).addDqOrderings(new Ordering[]{Ordering.desc(IExtensionField.EXTINJ_SCOPE_LEVEL), Ordering.desc(IExtensionField.EXTINJ_SCOPE_GKEY)});
        return dq;
    }

    public static IDomainQuery getEffectiveScopedExtension(@NotNull UserContext inUserContext, String inExtensionType, @NotNull String inExtensionName) {
        IDomainQuery dq = QueryUtils.createDomainQuery("Extension");
        dq.addDqPredicate(ScopedQueryUtils.getEntityPredicateForEffectiveScope(inUserContext.getScopeCoordinate(), IExtensionField.EXT_SCOPE_LEVEL, IExtensionField.EXT_SCOPE_GKEY));
        Conjunction conjunction = PredicateFactory.conjunction();
        conjunction.add(PredicateFactory.eq(IExtensionField.EXT_NAME, inExtensionName));
        if (StringUtils.isNotEmpty((String)inExtensionType)) {
            conjunction.add(PredicateFactory.eq(IExtensionField.EXT_TYPE, inExtensionType));
        }
        dq.addDqPredicate(conjunction);
        dq.addDqOrdering(Ordering.desc(IExtensionField.EXT_SCOPE_LEVEL));
        dq.setScopingEnabled(false);
        return dq;
    }

    public static String getScopeName(int inScopeLevel, Serializable inScopeKey) {
        if (inScopeKey == null) {
            return null;
        }
        IEntityScoper scoper = (IEntityScoper) Roastery.getBean("entityScoper");
        IScopeEnum scopeEnum = scoper.getScopeEnum(inScopeLevel);
        ScopeCoordinates sc = scoper.getScopeCoordinates(scopeEnum, inScopeKey);
        if (scopeEnum == null) {
            return null;
        }
        return scoper.getScopeNodeEntity(sc).getPathName();
    }

}
