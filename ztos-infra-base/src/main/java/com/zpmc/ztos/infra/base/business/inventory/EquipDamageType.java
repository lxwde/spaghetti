package com.zpmc.ztos.infra.base.business.inventory;

import com.zpmc.ztos.infra.base.business.dataobject.EquipDamageTypeDO;
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

public class EquipDamageType extends EquipDamageTypeDO {
    public EquipDamageType(EntitySet inEqdmgtypRefSet, String inEqdmgtypId) {
        this.setEqdmgtypScope(inEqdmgtypRefSet);
        this.setEqdmgtypId(inEqdmgtypId);
        this.setEqdmgtypLifeCycleState(LifeCycleStateEnum.ACTIVE);
    }

    public EquipDamageType() {
        this.setEqdmgtypLifeCycleState(LifeCycleStateEnum.ACTIVE);
    }

    public void setLifeCycleState(LifeCycleStateEnum inLifeCycleState) {
        this.setEqdmgtypLifeCycleState(inLifeCycleState);
    }

    public LifeCycleStateEnum getLifeCycleState() {
        return this.getEqdmgtypLifeCycleState();
    }

    public static EquipDamageType findEquipDamageType(String inEqdmgtypId, EquipClassEnum inEqClass) {
        if (inEqClass == null) {
            inEqClass = EquipClassEnum.CONTAINER;
        }
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"EquipDamageType").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoRefField.EQDMGTYP_ID, (Object)inEqdmgtypId)).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoRefField.EQDMGTYP_EQ_CLASS, (Object)((Object)inEqClass)));
        return (EquipDamageType) HibernateApi.getInstance().getUniqueEntityByDomainQuery(dq);
    }

    public static EquipDamageType createEquipDamageType(String inEqdmgtypId, String inDescription, EquipClassEnum inEqClass) {
        if (inEqClass == null) {
            inEqClass = EquipClassEnum.CONTAINER;
        }
        EquipDamageType eqdmgtyp = new EquipDamageType();
        eqdmgtyp.setEqdmgtypId(inEqdmgtypId);
        eqdmgtyp.setEqdmgtypDescription(inDescription);
        eqdmgtyp.setEqdmgtypEqClass(inEqClass);
        HibernateApi.getInstance().save((Object)eqdmgtyp);
        return eqdmgtyp;
    }

    public static EquipDamageType findOrCreateEquipDamageType(String inEqdmgtypId, String inDescription, EquipClassEnum inEqClass) {
        EquipDamageType eqdmgtyp;
        if (inEqClass == null) {
            inEqClass = EquipClassEnum.CONTAINER;
        }
        if ((eqdmgtyp = EquipDamageType.findEquipDamageType(inEqdmgtypId, inEqClass)) == null) {
            eqdmgtyp = EquipDamageType.createEquipDamageType(inEqdmgtypId, inDescription, inEqClass);
        }
        return eqdmgtyp;
    }

    protected BizViolation validateUniqueness(FieldChanges inChanges, BizViolation inBizViolation) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"EquipDamageType").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoRefField.EQDMGTYP_ID, (Object)this.getEqdmgtypId())).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoRefField.EQDMGTYP_EQ_CLASS, (Object)((Object)this.getEqdmgtypEqClass())));
        if (this.getEqdmgtypGkey() != null) {
            dq.addDqPredicate(PredicateFactory.ne((IMetafieldId) IArgoRefField.EQDMGTYP_GKEY, (Object)this.getEqdmgtypGkey()));
        }
        if (Roastery.getHibernateApi().existsByDomainQuery(dq)) {
            inBizViolation = BizViolation.create((IPropertyKey) IArgoPropertyKeys.ERROR_DUPLICATE_EQUIP_DAMAGE_TYPE, (BizViolation)inBizViolation, (Object)((Object)this.getEqdmgtypEqClass()), (Object)this.getEqdmgtypId());
        }
        return inBizViolation;
    }

    public String toString() {
        return "EquipDamageType Id:" + this.getEqdmgtypId() + " (" + (Object)((Object)this.getEqdmgtypEqClass()) + ")";
    }

    public IMetafieldId getScopeFieldId() {
        return IArgoRefField.EQDMGTYP_SCOPE;
    }

    public IMetafieldId getNaturalKeyField() {
        return IArgoRefField.EQDMGTYP_ID;
    }

    public ScopeEnum getMinimumScope() {
        return ScopeEnum.COMPLEX;
    }

    public Object[] getEqdmgtypDescAndEqClass() {
        return new Object[]{this.getEqdmgtypDescription(), this.getEqdmgtypEqClass()};
    }

}
