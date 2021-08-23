package com.zpmc.ztos.infra.base.business.model;

import com.zpmc.ztos.infra.base.business.dataobject.EquipConditionDO;
import com.zpmc.ztos.infra.base.business.enums.argo.EquipClassEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.ScopeEnum;
import com.zpmc.ztos.infra.base.business.enums.framework.LifeCycleStateEnum;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.business.predicate.PredicateFactory;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.common.exceptions.BizFailure;
import com.zpmc.ztos.infra.base.utils.QueryUtils;

import java.io.Serializable;

public class EquipCondition extends EquipConditionDO {
    public EquipCondition(EntitySet inEqcondRefSet, String inEqcondId, EquipClassEnum inEqtypClass) {
        this();
        this.setEqcondScope(inEqcondRefSet);
        this.setEqcondId(inEqcondId);
        this.setEqcondEqClass(inEqtypClass);
    }

    public EquipCondition() {
        this.setEqcondEqClass(EquipClassEnum.CONTAINER);
        this.setEqcondIsPersist(Boolean.FALSE);
        this.setEqcondLifeCycleState(LifeCycleStateEnum.ACTIVE);
    }

    public boolean isPersist() {
        return this.getEqcondIsPersist() == null ? false : this.getEqcondIsPersist();
    }

    public static EquipCondition findEquipCondition(String inEqcondId) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"EquipCondition").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoRefField.EQCOND_ID, (Object)inEqcondId));
        return (EquipCondition) HibernateApi.getInstance().getUniqueEntityByDomainQuery(dq);
    }

    public static EquipCondition findEquipConditionProxy(String inEqcondId) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"EquipCondition").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoRefField.EQCOND_ID, (Object)inEqcondId));
        Serializable[] eqCondGkey = HibernateApi.getInstance().findPrimaryKeysByDomainQuery(dq);
        if (eqCondGkey == null || eqCondGkey.length == 0) {
            return null;
        }
        if (eqCondGkey.length == 1) {
            return (EquipCondition)HibernateApi.getInstance().load(EquipCondition.class, eqCondGkey[0]);
        }
        throw BizFailure.create((IPropertyKey) IFrameworkPropertyKeys.FRAMEWORK__NON_UNIQUE_RESULT, null, (Object)new Long(eqCondGkey.length), (Object)dq);
    }

    public static EquipCondition createEquipCondition(String inEqcondId) {
        EquipCondition eqcond = new EquipCondition();
        eqcond.setEqcondId(inEqcondId);
        eqcond.setEqcondDescription(inEqcondId);
        HibernateApi.getInstance().saveOrUpdate((Object)eqcond);
        return eqcond;
    }

    public static EquipCondition findOrCreateEquipCondition(String inEqcondId) {
        EquipCondition eqcond = EquipCondition.findEquipCondition(inEqcondId);
        if (eqcond == null) {
            eqcond = EquipCondition.createEquipCondition(inEqcondId);
        }
        return eqcond;
    }

    public String toString() {
        return "EquipCondition Id:" + this.getEqcondId();
    }

    public String getHumanReadableKey() {
        String desc = this.getEqcondDescription();
        return desc == null ? this.getEqcondId() : this.getEqcondId() + ":" + desc;
    }

    public IMetafieldId getScopeFieldId() {
        return IArgoRefField.EQCOND_SCOPE;
    }

    public IMetafieldId getNaturalKeyField() {
        return IArgoRefField.EQCOND_ID;
    }

    public void setLifeCycleState(LifeCycleStateEnum inLifeCycleState) {
        this.setEqcondLifeCycleState(inLifeCycleState);
    }

    public LifeCycleStateEnum getLifeCycleState() {
        return this.getEqcondLifeCycleState();
    }

    public ScopeEnum getMinimumScope() {
        return ScopeEnum.COMPLEX;
    }
}
