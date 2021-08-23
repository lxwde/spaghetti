package com.zpmc.ztos.infra.base.common.security;

import com.zpmc.ztos.infra.base.business.dataobject.BizGroupDO;
import com.zpmc.ztos.infra.base.business.enums.argo.ScopeEnum;
import com.zpmc.ztos.infra.base.business.enums.framework.LifeCycleStateEnum;
import com.zpmc.ztos.infra.base.business.interfaces.IDomainQuery;
import com.zpmc.ztos.infra.base.business.interfaces.IMetafieldId;
import com.zpmc.ztos.infra.base.business.interfaces.IUserArgoField;
import com.zpmc.ztos.infra.base.business.predicate.PredicateFactory;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.common.model.Roastery;
import com.zpmc.ztos.infra.base.utils.QueryUtils;

import java.io.Serializable;

public class BizGroup extends BizGroupDO {
    public BizGroup() {
        this.setBizgrpLifeCycleState(LifeCycleStateEnum.ACTIVE);
    }

    public void setLifeCycleState(LifeCycleStateEnum inLifeCycleState) {
        this.setBizgrpLifeCycleState(inLifeCycleState);
    }

    public LifeCycleStateEnum getLifeCycleState() {
        return this.getBizgrpLifeCycleState();
    }

    public static BizGroup findOrCreate(String inGroupId, String inGroupName) {
        BizGroup group = BizGroup.find(inGroupId);
        if (group == null) {
            group = new BizGroup();
            group.setBizgrpId(inGroupId);
            group.setBizgrpName(inGroupName);
            Roastery.getHibernateApi().save((Object)group);
        }
        return group;
    }

    public static BizGroup find(String inGroupId) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"BizGroup").addDqPredicate(PredicateFactory.eq((IMetafieldId) IUserArgoField.BIZGRP_ID, (Object)inGroupId));
        return (BizGroup) Roastery.getHibernateApi().getUniqueEntityByDomainQuery(dq);
    }

//    public BizGroupBizUnit addBizUnit(ScopedBizUnit inBizUnit) {
//        return BizGroupBizUnit.findOrCreate(this, inBizUnit);
//    }
//
//    public HashSet getAllBizUnits() {
//        HashSet<ScopedBizUnit> list = new HashSet<ScopedBizUnit>();
//        if (this.getBizgrpBusinessUnits() != null) {
//            for (BizGroupBizUnit groupBizu : this.getBizgrpBusinessUnits()) {
//                Agent agent;
//                if (!LifeCycleStateEnum.ACTIVE.equals((Object)groupBizu.getBizgrpbzuLifeCycleState())) continue;
//                ScopedBizUnit bizu = groupBizu.getBizgrpbzuBizUnit();
//                list.add(bizu);
//                if (!BizRoleEnum.AGENT.equals((Object)bizu.getBzuRole()) || (agent = Agent.resolveAgentFromScopedBizUnit(bizu)) == null) continue;
//                list.addAll(agent.getActiveRepresentedBizUnits());
//            }
//        }
//        return list;
//    }

    public static BizGroup loadByPrimaryKey(Serializable inGkey) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"BizGroup").addDqPredicate(PredicateFactory.eq((IMetafieldId) IUserArgoField.BIZGRP_GKEY, (Object)inGkey));
        return (BizGroup) HibernateApi.getInstance().getUniqueEntityByDomainQuery(dq);
    }

//    public BizGkeysByRole getBizUnitGkeysByRole() {
//        BizGkeysByRole roleList = new BizGkeysByRole();
//        HashSet bizuList = this.getAllBizUnits();
//        if (bizuList != null) {
//            for (ScopedBizUnit bizu : bizuList) {
//                roleList.addBizUnit(bizu);
//            }
//        }
//        return roleList;
//    }

    public IMetafieldId getScopeFieldId() {
        return IUserArgoField.BIZGRP_SCOPE;
    }

    public IMetafieldId getNaturalKeyField() {
        return IUserArgoField.BIZGRP_ID;
    }

    public ScopeEnum getMinimumScope() {
        return ScopeEnum.COMPLEX;
    }

    public Object getBizgrpBizunitTableKey() {
        return this.getBizgrpGkey();
    }

    public Object clone() throws CloneNotSupportedException {
        BizGroup clone = (BizGroup)super.clone();
        clone.setBizgrpBusinessUnits(null);
        return clone;
    }
}
