package com.zpmc.ztos.infra.base.business.equipments;

import com.zpmc.ztos.infra.base.business.dataobject.EquipGradeDO;
import com.zpmc.ztos.infra.base.business.enums.argo.EquipClassEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.ScopeEnum;
import com.zpmc.ztos.infra.base.business.enums.framework.LifeCycleStateEnum;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.business.model.EntitySet;
import com.zpmc.ztos.infra.base.business.predicate.PredicateFactory;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.common.exceptions.BizFailure;
import com.zpmc.ztos.infra.base.utils.QueryUtils;

import java.io.Serializable;

public class EquipGrade extends EquipGradeDO {
    public EquipGrade(EntitySet inEqgrdRefSet, String inEqgrdId, EquipClassEnum inEqtypClass) {
        this();
        this.setEqgrdScope(inEqgrdRefSet);
        this.setEqgrdId(inEqgrdId);
        this.setEqgrdEqClass(inEqtypClass);
    }

    public EquipGrade() {
        this.setEqgrdEqClass(EquipClassEnum.CONTAINER);
        this.setEqgrdIsPersist(Boolean.FALSE);
        this.setEqgrdLifeCycleState(LifeCycleStateEnum.ACTIVE);
    }

    public static EquipGrade findEquipGrade(String inEqgrdId) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"EquipGrade").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoRefField.EQGRD_ID, (Object)inEqgrdId));
        return (EquipGrade) HibernateApi.getInstance().getUniqueEntityByDomainQuery(dq);
    }

    public static EquipGrade findEquipGradeProxy(String inEqgrdId) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"EquipGrade").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoRefField.EQGRD_ID, (Object)inEqgrdId));
        Serializable[] eqGrdGkey = HibernateApi.getInstance().findPrimaryKeysByDomainQuery(dq);
        if (eqGrdGkey == null || eqGrdGkey.length == 0) {
            return null;
        }
        if (eqGrdGkey.length == 1) {
            return (EquipGrade)HibernateApi.getInstance().load(EquipGrade.class, eqGrdGkey[0]);
        }
        throw BizFailure.create((IPropertyKey) IFrameworkPropertyKeys.FRAMEWORK__NON_UNIQUE_RESULT, null, (Object)new Long(eqGrdGkey.length), (Object)dq);
    }

    public static EquipGrade createEquipGrade(String inEqgrdId) {
        EquipGrade eqgrd = new EquipGrade();
        eqgrd.setEqgrdId(inEqgrdId);
        eqgrd.setEqgrdDescription(inEqgrdId);
        HibernateApi.getInstance().saveOrUpdate((Object)eqgrd);
        return eqgrd;
    }

    public static EquipGrade findOrCreateEquipGrade(String inEqgrdId) {
        EquipGrade eqgrd = EquipGrade.findEquipGrade(inEqgrdId);
        if (eqgrd == null) {
            eqgrd = EquipGrade.createEquipGrade(inEqgrdId);
        }
        return eqgrd;
    }

    public static EquipGrade findOrCreateEquipGradeProxy(String inEqgrdId) {
        EquipGrade eqgrd = EquipGrade.findEquipGradeProxy(inEqgrdId);
        if (eqgrd == null) {
            eqgrd = EquipGrade.createEquipGrade(inEqgrdId);
        }
        return eqgrd;
    }

    public String toString() {
        return "EquipGrade Id:" + this.getEqgrdId();
    }

    public String getHumanReadableKey() {
        String desc = this.getEqgrdDescription();
        return desc == null ? this.getEqgrdId() : this.getEqgrdId() + ":" + desc;
    }

    public IMetafieldId getScopeFieldId() {
        return IArgoRefField.EQGRD_SCOPE;
    }

    public IMetafieldId getNaturalKeyField() {
        return IArgoRefField.EQGRD_ID;
    }

    public void setLifeCycleState(LifeCycleStateEnum inLifeCycleState) {
        this.setEqgrdLifeCycleState(inLifeCycleState);
    }

    public LifeCycleStateEnum getLifeCycleState() {
        return this.getEqgrdLifeCycleState();
    }

    public ScopeEnum getMinimumScope() {
        return ScopeEnum.COMPLEX;
    }

    public boolean getGradeIDPersisted() {
        return this.getEqgrdIsPersist();
    }



}
