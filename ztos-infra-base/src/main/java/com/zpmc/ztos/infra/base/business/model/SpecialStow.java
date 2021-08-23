package com.zpmc.ztos.infra.base.business.model;

import com.zpmc.ztos.infra.base.business.dataobject.SpecialStowDO;
import com.zpmc.ztos.infra.base.business.enums.argo.ScopeEnum;
import com.zpmc.ztos.infra.base.business.enums.framework.LifeCycleStateEnum;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.business.predicate.PredicateFactory;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.common.events.AuditEvent;
import com.zpmc.ztos.infra.base.common.exceptions.BizFailure;
import com.zpmc.ztos.infra.base.utils.QueryUtils;

import java.io.Serializable;

public class SpecialStow extends SpecialStowDO {

    public SpecialStow(EntitySet inStwRefSet, String inStwId) {
        this();
        this.setStwScope(inStwRefSet);
        this.setStwId(inStwId);
    }

    public SpecialStow() {
        this.setStwLifeCycleState(LifeCycleStateEnum.ACTIVE);
    }

    public void setLifeCycleState(LifeCycleStateEnum inLifeCycleState) {
        this.setStwLifeCycleState(inLifeCycleState);
    }

    public LifeCycleStateEnum getLifeCycleState() {
        return this.getStwLifeCycleState();
    }

    public static SpecialStow findSpecialStow(String inStwId) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"SpecialStow").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoRefField.STW_ID, (Object)inStwId));
        return (SpecialStow)HibernateApi.getInstance().getUniqueEntityByDomainQuery(dq);
    }

    public static SpecialStow findSpecialStowProxy(String inStwId) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"SpecialStow").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoRefField.STW_ID, (Object)inStwId));
        Serializable[] stwGkey = HibernateApi.getInstance().findPrimaryKeysByDomainQuery(dq);
        if (stwGkey == null || stwGkey.length == 0) {
            return null;
        }
        if (stwGkey.length == 1) {
            return (SpecialStow)HibernateApi.getInstance().load(SpecialStow.class, stwGkey[0]);
        }
        throw BizFailure.create((IPropertyKey) IFrameworkPropertyKeys.FRAMEWORK__NON_UNIQUE_RESULT, null, (Object)new Long(stwGkey.length), (Object)dq);
    }

    public static SpecialStow createSpecialStow(String inStwId) {
        SpecialStow stw = new SpecialStow();
        stw.setStwId(inStwId);
        stw.setStwDescription("??" + inStwId + "??");
        HibernateApi.getInstance().save((Object)stw);
        return stw;
    }

    public static SpecialStow findOrCreateSpecialStow(String inStwId) {
        SpecialStow stw = SpecialStow.findSpecialStow(inStwId);
        if (stw == null) {
            stw = SpecialStow.createSpecialStow(inStwId);
            HibernateApi.getInstance().saveOrUpdate((Object)stw);
        }
        return stw;
    }

    public String toString() {
        return "SpecialStow Id:" + this.getStwId();
    }

    public IMetafieldId getScopeFieldId() {
        return IArgoRefField.STW_SCOPE;
    }

    public IMetafieldId getNaturalKeyField() {
        return IArgoRefField.STW_ID;
    }

    public ScopeEnum getMinimumScope() {
        return ScopeEnum.COMPLEX;
    }

    public AuditEvent vetAuditEvent(AuditEvent inAuditEvent) {
        return inAuditEvent;
    }
}
