package com.zpmc.ztos.infra.base.business.model;

import com.zpmc.ztos.infra.base.business.dataobject.MasterBizUnitDO;
import com.zpmc.ztos.infra.base.business.enums.argo.BizRoleEnum;
import com.zpmc.ztos.infra.base.business.enums.framework.LifeCycleStateEnum;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.business.predicate.PredicateFactory;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.common.exceptions.BizFailure;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.model.FieldChanges;
import com.zpmc.ztos.infra.base.utils.QueryUtils;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.io.Serializable;
import java.util.List;

public class MasterBizUnit extends MasterBizUnitDO {
    public MasterBizUnit() {
        this.setBizuIsEqOwner(Boolean.FALSE);
        this.setBizuIsEqOperator(Boolean.FALSE);
        this.setBizuLifeCycleState(LifeCycleStateEnum.ACTIVE);
    }

    public void setLifeCycleState(LifeCycleStateEnum inLifeCycleState) {
        this.setBizuLifeCycleState(inLifeCycleState);
    }

    public LifeCycleStateEnum getLifeCycleState() {
        return this.getBizuLifeCycleState();
    }

    public static MasterBizUnit findOrCreateBizUnit(String inBizUnitId, BizRoleEnum inBizRole, Organization inOrg) {
        return MasterBizUnit.findOrCreateBizUnit(inBizUnitId, inBizRole, inOrg, null);
    }

    public static MasterBizUnit findOrCreateBizUnit(String inBizUnitId, BizRoleEnum inBizRole, Organization inOrg, String inBizuUnitName) {
        MasterBizUnit bu = MasterBizUnit.findBizUnit(inBizUnitId, inBizRole);
        if (bu == null) {
            bu = MasterBizUnit.createBizUnit(inBizUnitId, inBizRole, inOrg, inBizuUnitName);
        }
        return bu;
    }

    public static MasterBizUnit loadByPrimaryKey(Serializable inGkey) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"MasterBizUnit").addDqPredicate(PredicateFactory.eq((IMetafieldId)IArgoRefField.BIZU_GKEY, (Object)inGkey));
        return (MasterBizUnit)HibernateApi.getInstance().getUniqueEntityByDomainQuery(dq);
    }

    public static MasterBizUnit findBizUnit(String inBizUnitId, BizRoleEnum inBizRole) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"MasterBizUnit").addDqPredicate(PredicateFactory.eq((IMetafieldId)IArgoRefField.BIZU_ID, (Object)inBizUnitId)).addDqPredicate(PredicateFactory.eq((IMetafieldId)IArgoRefField.BIZU_ROLE, (Object)((Object)inBizRole)));
        return (MasterBizUnit)HibernateApi.getInstance().getUniqueEntityByDomainQuery(dq);
    }

    @Nullable
    public static MasterBizUnit findEquipmentOwner(String inBizUnitId) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"MasterBizUnit").addDqPredicate(PredicateFactory.eq((IMetafieldId)IArgoRefField.BIZU_ID, (Object)inBizUnitId)).addDqPredicate(PredicateFactory.eq((IMetafieldId)IArgoRefField.BIZU_IS_EQ_OWNER, (Object) Boolean.TRUE));
        Serializable[] primaryKeys = HibernateApi.getInstance().findPrimaryKeysByDomainQuery(dq);
        return primaryKeys == null || primaryKeys.length == 0 ? null : MasterBizUnit.hydrate(primaryKeys[0]);
    }

    public static MasterBizUnit hydrate(Serializable inPrimaryKey) {
        return (MasterBizUnit)HibernateApi.getInstance().load(MasterBizUnit.class, inPrimaryKey);
    }

    @Nullable
    public static MasterBizUnit findEquipmentOperator(String inBizUnitId) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"MasterBizUnit").addDqPredicate(PredicateFactory.eq((IMetafieldId)IArgoRefField.BIZU_ID, (Object)inBizUnitId)).addDqPredicate(PredicateFactory.eq((IMetafieldId)IArgoRefField.BIZU_IS_EQ_OPERATOR, (Object) Boolean.TRUE));
        List operators = HibernateApi.getInstance().findEntitiesByDomainQuery(dq);
        return operators.isEmpty() ? null : (MasterBizUnit)operators.get(0);
    }

    public static MasterBizUnit createBizUnit(String inBizUnitId, BizRoleEnum inBizRole, Organization inOrg) {
        return MasterBizUnit.createBizUnit(inBizUnitId, inBizRole, inOrg, null);
    }

    public static MasterBizUnit createBizUnit(String inBizUnitId, BizRoleEnum inBizRole, Organization inOrg, String inBizUnitName) {
        MasterBizUnit bu = new MasterBizUnit();
        bu.setBizuId(inBizUnitId);
        if (inBizUnitName != null) {
            bu.setBizuName(inBizUnitName);
        } else {
            bu.setBizuName(inBizUnitId);
        }
        bu.setBizuRole(inBizRole);
        bu.setBizuOrg(inOrg);
        if (inBizRole.equals((Object)BizRoleEnum.LINEOP)) {
            bu.setBizuIsEqOwner(Boolean.TRUE);
            bu.setBizuIsEqOperator(Boolean.TRUE);
        }
        HibernateApi.getInstance().saveOrUpdate((Object)bu);
        return bu;
    }

    public ScopedBizUnit findScopedBizUnit() {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"ScopedBizUnit").addDqPredicate(PredicateFactory.eq((IMetafieldId)IArgoRefField.BZU_BIZU, (Object)this.getBizuGkey())).addDqPredicate(PredicateFactory.eq((IMetafieldId)IArgoRefField.BZU_ID, (Object)this.getBizuId()));
        return (ScopedBizUnit)HibernateApi.getInstance().getUniqueEntityByDomainQuery(dq);
    }

    @Nullable
    public ScopedBizUnit findScopedBizUnitProxy() {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"ScopedBizUnit").addDqPredicate(PredicateFactory.eq((IMetafieldId)IArgoRefField.BZU_BIZU, (Object)this.getBizuGkey())).addDqPredicate(PredicateFactory.eq((IMetafieldId)IArgoRefField.BZU_ID, (Object)this.getBizuId()));
        Serializable[] bzuGkey = HibernateApi.getInstance().findPrimaryKeysByDomainQuery(dq);
        if (bzuGkey == null || bzuGkey.length == 0) {
            return null;
        }
        if (bzuGkey.length == 1) {
            return (ScopedBizUnit)HibernateApi.getInstance().load(ScopedBizUnit.class, bzuGkey[0]);
        }
        throw BizFailure.create((IPropertyKey)IFrameworkPropertyKeys.FRAMEWORK__NON_UNIQUE_RESULT, null, (Object)new Long(bzuGkey.length), (Object)dq);
    }

    public String getHumanReadableKey() {
        String name = this.getBizuName();
        return name == null ? this.getBizuId() : this.getBizuId() + ":" + name;
    }

    public BizViolation validateChanges(FieldChanges inChanges) {
        BizViolation inBv = super.validateChanges(inChanges);
        inBv = this.checkIfRoleChanged(inBv, inChanges);
        inBv = this.checkScopeBzuIdRoleUniquness(inBv);
        return inBv;
    }

    private BizViolation checkIfRoleChanged(BizViolation inBv, FieldChanges inChanges) {
        BizRoleEnum oldValue;
        if (inChanges.hasFieldChange(IArgoRefField.BIZU_ROLE) && (oldValue = (BizRoleEnum)((Object)inChanges.getFieldChange(IArgoRefField.BIZU_ROLE).getPriorValue())) != null && oldValue.equals((Object)BizRoleEnum.LINEOP)) {
            IDomainQuery dq = QueryUtils.createDomainQuery((String)"ScopedBizUnit").addDqPredicate(PredicateFactory.eq((IMetafieldId)IArgoRefField.BZU_BIZU, (Object)this.getBizuGkey()));
            if (HibernateApi.getInstance().existsByDomainQuery(dq)) {
                inBv = BizViolation.createFieldViolation((IPropertyKey) IArgoPropertyKeys.INVALID_ROLE_CHANGE, (BizViolation)inBv, (IMetafieldId)IArgoRefField.BIZU_ROLE);
            }
        }
        return inBv;
    }

    private BizViolation checkScopeBzuIdRoleUniquness(BizViolation inBv) {
        IMetafieldId roleFieldId = IArgoRefField.BIZU_ROLE;
        IMetafieldId naturalKeyId = IArgoRefField.BIZU_ID;
        BizRoleEnum role = (BizRoleEnum)((Object)this.getField(roleFieldId));
        String natkey = this.getFieldString(naturalKeyId);
        IDomainQuery dq = QueryUtils.createDomainQuery((String)this.getEntityName()).addDqPredicate(PredicateFactory.eq((IMetafieldId)roleFieldId, (Object)role.getKey())).addDqPredicate(PredicateFactory.eq((IMetafieldId)naturalKeyId, (Object)natkey));
        Serializable pkValue = this.getPrimaryKey();
        if (pkValue != null) {
            dq.addDqPredicate(PredicateFactory.not((IPredicate)PredicateFactory.pkEq((Object)pkValue)));
        }
        if (HibernateApi.getInstance().existsByDomainQuery(dq)) {
            inBv = inBv != null ? BizViolation.createFieldViolation((IPropertyKey)IArgoPropertyKeys.NON_UNIQUE_MASTER_ID_AND_ROLE, (BizViolation)inBv, null, (Object)natkey, (Object)role.getDescriptionPropertyKey()) : BizViolation.create((IPropertyKey)IArgoPropertyKeys.NON_UNIQUE_MASTER_ID_AND_ROLE, null, (Object)natkey, (Object)role.getDescriptionPropertyKey());
        }
        return inBv;
    }

}
