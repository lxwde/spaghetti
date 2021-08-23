package com.zpmc.ztos.infra.base.business.model;

import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.common.exceptions.BizFailure;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;
import com.zpmc.ztos.infra.base.common.model.FieldChanges;
import com.zpmc.ztos.infra.base.common.model.TransactionParms;
import com.zpmc.ztos.infra.base.common.model.UserContext;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;
import org.springframework.util.StringUtils;

public abstract class ReferenceEntity extends DatabaseEntity
implements INaturallyKeyedEntity, Cloneable {
    @Override
    public void preProcessInsertOrUpdate(FieldChanges inOutMoreChanges) {
        IEntityScoper scoper;
        UserContext uc;
        TransactionParms parms = TransactionParms.getBoundParms();
        UserContext userContext = uc = parms == null ? null : parms.getUserContext();
        if (uc == null) {
            throw BizFailure.create(IFrameworkPropertyKeys.FRAMEWORK__NO_TRANSACTION_PARMS, null, this.getClass());
        }
//        if (parms.isScopingEnabled() && (scoper = (EntityScoper)Roastery.getBean("entityScoper")) != null) {
//            IScope imputedScope = scoper.getEntityScope(this.getEntityName(), uc);
//            if (imputedScope == null) {
//                throw BizFailure.create(IFrameworkPropertyKeys.FRAMEWORK__NO_SCOPE_DEFINED, null, uc.getUserId(), this.getEntityName());
//            }
//            IScope currentScope = this.getScope();
//            if (currentScope == null) {
//                this.setSelfAndFieldChange(this.getScopeFieldId(), (Object)imputedScope, inOutMoreChanges);
//            } else if (!ObjectUtils.equals((Object)imputedScope.getScopeKey(), (Object)currentScope.getScopeKey())) {
//                throw BizFailure.create(IFrameworkPropertyKeys.FRAMEWORK__ENTITY_NOT_IN_SCOPE, null, uc.getUserId(), this.getEntityName());
//            }
//        }
    }

    @Nullable
    public IScope getScope() {
        return (IScope)this.getField(this.getScopeFieldId());
    }

    @Override
    public BizViolation validateChanges(FieldChanges inChanges) {
        BizViolation bv = super.validateChanges(inChanges);
        bv = this.validateUniqueness(inChanges, bv);
        return bv;
    }

    protected BizViolation validateUniqueness(FieldChanges inChanges, BizViolation inBizViolation) {
        IMetafieldId scopeFieldId = this.getScopeFieldId();
        IMetafieldId naturalKeyId = this.getNaturalKeyField();
        if (inChanges.hasFieldChange(naturalKeyId) || inChanges.hasFieldChange(scopeFieldId)) {
            IScope set = (IScope)this.getField(scopeFieldId);
            String natkey = this.getFieldString(naturalKeyId);
            if (StringUtils.isEmpty((String)natkey)) {
                throw BizFailure.create("attempt to create reference entity with null/empty id for entity: " + this.getEntityName());
            }
//            DomainQuery dq = QueryUtils.createDomainQuery(this.getEntityName()).addDqPredicate(PredicateFactory.eq(scopeFieldId, set.getScopeKey())).addDqPredicate(PredicateFactory.eq(naturalKeyId, natkey));
//            Serializable pkValue = this.getPrimaryKey();
//            if (pkValue != null) {
//                dq.addDqPredicate(PredicateFactory.not(PredicateFactory.pkEq(pkValue)));
//            }
//            if (HibernateApi.getInstance().existsByDomainQuery(dq)) {
//                inBizViolation = BizViolation.createFieldViolation(IFrameworkPropertyKeys.CRUD__DUPLICATE_NATURAL_KEY, inBizViolation, naturalKeyId, this.getFieldValue(naturalKeyId));
//            }
        }
        return inBizViolation;
    }

    public abstract IMetafieldId getScopeFieldId();

    public abstract IMetafieldId getNaturalKeyField();

    @Override
    @Nullable
    public String getHumanReadableKey() {
        IMetafieldId naturalKeyMfid = this.getNaturalKeyField();
        if (naturalKeyMfid != null) {
            Object fieldValue = this.getFieldValue(naturalKeyMfid);
            if (fieldValue != null && !(fieldValue instanceof String)) {
                fieldValue = fieldValue.toString();
            }
            return (String)fieldValue;
        }
        return null;
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    @Nullable
    public ReferenceEntity findByNaturalKey(String inNaturalKey) {
//        DomainQuery dq = QueryUtils.createDomainQuery(this.getEntityName()).addDqPredicate(PredicateFactory.eq(this.getNaturalKeyField(), inNaturalKey));
//        return (ReferenceEntity)HibernateApi.getInstance().getUniqueEntityByDomainQuery(dq);
        return null;
    }

    @Override
    @Nullable
    public String getNaturalKey() {
        return (String)this.getFieldValue(this.getNaturalKeyField());
    }


}
