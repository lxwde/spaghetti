package com.zpmc.ztos.infra.base.common.beans;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.business.model.ReferenceEntity;
import com.zpmc.ztos.infra.base.business.predicate.PredicateFactory;
import com.zpmc.ztos.infra.base.common.database.HiberCache;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;
import com.zpmc.ztos.infra.base.common.model.ScopeCoordinates;
import com.zpmc.ztos.infra.base.common.model.UserContext;


import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public abstract class BaseEntityScoper implements IEntityScoper {
    private final Map _scopeCache = Collections.synchronizedMap(new HashMap());
    protected HibernateApi _hibernateApi;
    protected final Long _baseEntitySetKey = 1L;

    @Override
    public IScope getEntityScope(String inEntityClassName, UserContext inUserContext) {
        ScopeCoordinates scopeCoordinate = inUserContext.getScopeCoordinate();
        String scopeHashId = inEntityClassName + scopeCoordinate.getScopeAddress();
        IScope scope = (IScope)this._scopeCache.get(scopeHashId);
        if (scope == null) {
            scope = this.lookupEntityScope(inEntityClassName, scopeCoordinate);
            this._scopeCache.put(scopeHashId, scope);
        }
        return scope;
    }

    @Override
    public void clearScopeCache() {
        this._scopeCache.clear();
    }

    protected abstract IScope lookupEntityScope(String var1, ScopeCoordinates var2);

    @Override
    public IDomainQuery enhanceDomainQuery(IDomainQuery inDomainQuery, UserContext inUserContext) {
        IDomainQuery enhancedQuery = (IDomainQuery)inDomainQuery.createEnhanceableClone();
        String queryClassName = enhancedQuery.getQueryEntityName();
        Class queryEntityClass = HiberCache.entityName2EntityClass(queryClassName);
        if (queryEntityClass == null) {
            throw new IllegalArgumentException("Could not find a Hibernate-mapped class with name <" + queryClassName + ">");
        }
        boolean isReferenceEntity = ReferenceEntity.class.isAssignableFrom(queryEntityClass);
        if (isReferenceEntity) {
            if (inDomainQuery.getScopingEnabled()) {
                Long scopeKey;
                ReferenceEntity scopedEntity = (ReferenceEntity)HiberCache.newInstanceForEntity(queryClassName);
                IScope scope = this.getEntityScope(queryClassName, inUserContext);
                Long l = scopeKey = scope == null ? null : scope.getScopeKey();
                if (scope != null || scope == null && HiberCache.isDbNullable(scopedEntity.getScopeFieldId())) {
                    enhancedQuery.addDqPredicate(PredicateFactory.eq(scopedEntity.getScopeFieldId(), scopeKey));
                }
            }
            this.scopeReferenceEntityQuery(queryEntityClass, enhancedQuery, inUserContext);
        } else {
            this.scopeEntityQuery(queryEntityClass, enhancedQuery, inUserContext);
        }
        return enhancedQuery;
    }

    protected void scopeReferenceEntityQuery(Class inEntityClass, IDomainQuery inOutEnhancedQuery, UserContext inUserContext) {
    }

    protected void scopeEntityQuery(Class inEntityClass, IDomainQuery inOutEnhancedQuery, UserContext inUserContext) {
    }

    @Override
    @Nullable
    public ScopeCoordinates getEntityScopeCoordinates(String inEntityClassName, Serializable inGkey) {
        Class entityClass = HiberCache.entityName2EntityClass(inEntityClassName);
        DatabaseEntity entity = (DatabaseEntity)this._hibernateApi.load(entityClass, inGkey);
        if (entity instanceof IEntityScope) {
            IEntityScope entityscope = (IEntityScope)((Object)entity);
            ScopeCoordinates coords = this.getScopeCoordinates(entityscope.getScopeLevel(), entityscope.getScopeKey());
            return coords;
        }
        return null;
    }

    @Override
    public abstract IScopeNodeEntity getScopeNodeEntity(ScopeCoordinates var1);

    @Override
    @Deprecated
    @Nullable
    public ScopeCoordinates getScopeCoordinates(String[] inScopeHierarchy) {
        if (inScopeHierarchy == null || inScopeHierarchy.length == 0) {
            return ScopeCoordinates.GLOBAL_SCOPE;
        }
        return null;
    }

    public void setHibernateApi(HibernateApi inHibernateApi) {
        this._hibernateApi = inHibernateApi;
    }

    @Override
    @Nullable
    public abstract String getScopeLevelName(int var1);

    @Override
    @Nullable
    public IPredicate getPredicateForBroadestAllowedScope(ScopeCoordinates inCoordinates, @NotNull IMetafieldId inScopeLevelFieldId, @NotNull IMetafieldId inScopeGkeyFieldId) {
        return null;
    }

    @Override
    @Nullable
    public IDomainQuery createScopeNodeEntityLevelQuery(IScopeEnum inScopeLevel, ScopeCoordinates inScopeCoordinates) {
        return null;
    }
}
