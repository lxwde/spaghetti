package com.zpmc.ztos.infra.base.common.scopes;

import com.zpmc.ztos.infra.base.business.dataobject.OperatorDO;
import com.zpmc.ztos.infra.base.business.enums.argo.ScopeEnum;
import com.zpmc.ztos.infra.base.business.enums.framework.LifeCycleStateEnum;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.business.predicate.PredicateFactory;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.helps.ContextHelper;
import com.zpmc.ztos.infra.base.common.model.FieldChanges;
import com.zpmc.ztos.infra.base.common.model.Roastery;
import com.zpmc.ztos.infra.base.common.model.ScopeCoordinates;
import com.zpmc.ztos.infra.base.utils.QueryUtils;

import java.io.Serializable;
import java.util.Collection;

public class Operator extends OperatorDO {

    public Operator() {
        this.setOprLifeCycleState(LifeCycleStateEnum.ACTIVE);
    }

    public void setLifeCycleState(LifeCycleStateEnum inLifeCycleState) {
        this.setOprLifeCycleState(inLifeCycleState);
    }

    public LifeCycleStateEnum getLifeCycleState() {
        return this.getOprLifeCycleState();
    }

    public String toString() {
        return "opr-" + this.getOprId();
    }

    public static Operator loadByGkey(Long inGkey) {
        return (Operator) Roastery.getHibernateApi().load(Operator.class, (Serializable)inGkey);
    }

    public static Operator findOperator(String inOprId) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"Operator").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoField.OPR_ID, (Object)inOprId));
        return (Operator)HibernateApi.getInstance().getUniqueEntityByDomainQuery(dq);
    }

    public static Operator findOrCreateOperator(String inOperatorId, String inOprName) {
        Operator operator = Operator.findOperator(inOperatorId);
        if (operator == null) {
            operator = new Operator();
            operator.setOprId(inOperatorId);
            operator.setOprName(inOprName);
            HibernateApi.getInstance().save((Object)operator);
            HibernateApi.getInstance().flush();
        } else {
            operator.setOprName(inOprName);
        }
        return operator;
    }

    public BizViolation validateChanges(FieldChanges inFieldChanges) {
        int entityLevel;
        ScopeCoordinates scope;
        int maxLevel;
        BizViolation bizViol = null;
        super.validateChanges(inFieldChanges);
        if (this.getPrimaryKey() == null && (maxLevel = (scope = ContextHelper.getThreadUserContext().getScopeCoordinate()).getMaxScopeLevel()) >= (entityLevel = this.getScopeEnum().getScopeLevel().intValue())) {
            IPropertyKey currentScope = ((ScopeEnum)((Object) ScopeEnum.getEnumList().get(maxLevel - 1))).getDescriptionPropertyKey();
            IPropertyKey requiredScope = ((ScopeEnum)((Object) ScopeEnum.getEnumList().get(entityLevel - 2))).getDescriptionPropertyKey();
            bizViol = BizViolation.create((IPropertyKey) IArgoPropertyKeys.NO_PRIVILEGE_TO_CHANGE_TOPOLOGY, null, (Object)currentScope, (Object)requiredScope);
        }
        if (inFieldChanges.hasFieldChange(IArgoField.OPR_ID)) {
            bizViol = this.checkIfOperatorAlreadyExists(bizViol);
        }
        return bizViol;
    }

    private BizViolation checkIfOperatorAlreadyExists(BizViolation inBv) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"Operator").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoField.OPR_ID, (Object)this.getOprId()));
        Serializable pkValue = this.getPrimaryKey();
        if (pkValue != null) {
            dq.addDqPredicate(PredicateFactory.not((IPredicate) PredicateFactory.pkEq((Object)pkValue)));
        }
        if (HibernateApi.getInstance().existsByDomainQuery(dq)) {
            inBv = BizViolation.createFieldViolation((IPropertyKey) IArgoPropertyKeys.DUPLICATE_OPERATOR, (BizViolation)inBv, (IMetafieldId) IArgoField.OPR_ID, (Object)this.getOprId());
        }
        return inBv;
    }

    public IScopeEnum getScopeEnum() {
        return ScopeEnum.OPERATOR;
    }

    public IScopeNodeEntity getParent() {
        return Global.getInstance();
    }

    public Collection getChildren() {
        return this.getOprCpxSet();
    }

    public String getId() {
        return this.getOprId();
    }

    public String getPathName() {
        return this.getOprId();
    }

    public String getOprPathName() {
        return this.getPathName();
    }

    public String getHumanReadableKey() {
        return this.getOprId();
    }

    public static Operator hydrate(Serializable inPrimaryKey) {
        return (Operator)HibernateApi.getInstance().load(Operator.class, inPrimaryKey);
    }
}
