package com.zpmc.ztos.infra.base.business.equipments;

import com.zpmc.ztos.infra.base.business.dataobject.EqComponentDO;
import com.zpmc.ztos.infra.base.business.enums.argo.EquipClassEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.ScopeEnum;
import com.zpmc.ztos.infra.base.business.enums.framework.LifeCycleStateEnum;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.business.model.EntitySet;
import com.zpmc.ztos.infra.base.business.predicate.PredicateFactory;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.model.FieldChanges;
import com.zpmc.ztos.infra.base.common.model.Roastery;
import com.zpmc.ztos.infra.base.utils.QueryUtils;

public class EqComponent extends EqComponentDO {
    public EqComponent(EntitySet inEqdmglocRefSet, String inEqdmglocId) {
        this.setEqcmpScope(inEqdmglocRefSet);
        this.setEqcmpId(inEqdmglocId);
        this.setEqcmpLifeCycleState(LifeCycleStateEnum.ACTIVE);
    }

    public EqComponent() {
        this.setEqcmpLifeCycleState(LifeCycleStateEnum.ACTIVE);
    }

    public void setLifeCycleState(LifeCycleStateEnum inLifeCycleState) {
        this.setEqcmpLifeCycleState(inLifeCycleState);
    }

    public LifeCycleStateEnum getLifeCycleState() {
        return this.getEqcmpLifeCycleState();
    }

    public static EqComponent findEqComponent(String inId, EquipClassEnum inEqClass) {
        if (inEqClass == null) {
            inEqClass = EquipClassEnum.CONTAINER;
        }
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"EqComponent").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoRefField.EQCMP_ID, (Object)inId)).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoRefField.EQCMP_EQ_CLASS, (Object)((Object)inEqClass)));
        return (EqComponent) HibernateApi.getInstance().getUniqueEntityByDomainQuery(dq);
    }

    public static EqComponent createEqComponent(String inId, String inDescription, EquipClassEnum inEqClass) {
        if (inEqClass == null) {
            inEqClass = EquipClassEnum.CONTAINER;
        }
        EqComponent eqDmgComponent = new EqComponent();
        eqDmgComponent.setEqcmpId(inId);
        eqDmgComponent.setEqcmpDescription(inDescription);
        eqDmgComponent.setEqcmpEqClass(inEqClass);
        HibernateApi.getInstance().save((Object)eqDmgComponent);
        return eqDmgComponent;
    }

    public static EqComponent findOrCreateEqComponent(String inId, String inDescription, EquipClassEnum inEqClass) {
        EqComponent eqDmgComponent;
        if (inEqClass == null) {
            inEqClass = EquipClassEnum.CONTAINER;
        }
        if ((eqDmgComponent = EqComponent.findEqComponent(inId, inEqClass)) == null) {
            eqDmgComponent = EqComponent.createEqComponent(inId, inDescription, inEqClass);
        }
        return eqDmgComponent;
    }

    protected BizViolation validateUniqueness(FieldChanges inChanges, BizViolation inBizViolation) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"EqComponent").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoRefField.EQCMP_ID, (Object)this.getEqcmpId())).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoRefField.EQCMP_EQ_CLASS, (Object)((Object)this.getEqcmpEqClass())));
        if (this.getEqcmpGkey() != null) {
            dq.addDqPredicate(PredicateFactory.ne((IMetafieldId) IArgoRefField.EQCMP_GKEY, (Object)this.getEqcmpGkey()));
        }
        if (Roastery.getHibernateApi().existsByDomainQuery(dq)) {
            inBizViolation = BizViolation.create((IPropertyKey) IArgoPropertyKeys.ERROR_DUPLICATE_EQ_COMPONENT_TYPE, (BizViolation)inBizViolation, (Object)((Object)this.getEqcmpEqClass()), (Object)this.getEqcmpId());
        }
        return inBizViolation;
    }

    public String toString() {
        return "EqComponent:" + this.getEqcmpId() + " (" + (Object)((Object)this.getEqcmpEqClass()) + ")";
    }

    public IMetafieldId getScopeFieldId() {
        return IArgoRefField.EQCMP_SCOPE;
    }

    public IMetafieldId getNaturalKeyField() {
        return IArgoRefField.EQCMP_ID;
    }

    public ScopeEnum getMinimumScope() {
        return ScopeEnum.COMPLEX;
    }

    public Object[] getEqcmpDescAndEqClass() {
        return new Object[]{this.getEqcmpDescription(), this.getEqcmpEqClass()};
    }
}
