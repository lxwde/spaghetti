package com.zpmc.ztos.infra.base.business.edi;

import com.zpmc.ztos.infra.base.business.dataobject.EdiSessionMailboxDO;
import com.zpmc.ztos.infra.base.business.enums.argo.DataSourceEnum;
import com.zpmc.ztos.infra.base.business.enums.edi.EdiMessageDirectionEnum;
import com.zpmc.ztos.infra.base.business.enums.framework.LifeCycleStateEnum;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.business.predicate.PredicateFactory;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.common.exceptions.BizFailure;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.helps.ContextHelper;
import com.zpmc.ztos.infra.base.common.model.FieldChanges;
import com.zpmc.ztos.infra.base.utils.QueryUtils;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.util.List;

public class EdiSessionMailbox extends EdiSessionMailboxDO {
    public EdiSessionMailbox() {
        this.setEdisessmlbxIsPrimary(Boolean.FALSE);
        this.setEdisessmlbxLifeCycleState(LifeCycleStateEnum.ACTIVE);
    }

    public EdiSessionMailbox(EdiSession inSess, EdiMailbox inMlbx, Boolean inPrimary) {
        this.setEdisessmlbxSession(inSess);
        this.setEdisessmlbxMailbox(inMlbx);
        this.setEdisessmlbxIsPrimary(inPrimary);
        this.setEdisessmlbxLifeCycleState(LifeCycleStateEnum.ACTIVE);
    }

    public static EdiSessionMailbox findOrCreateSessionMailbox(EdiSession inSess, EdiMailbox inMlbx, Boolean inPrimary) {
        EdiSessionMailbox sessionMailbox = EdiSessionMailbox.findActiveSessionMailbox(inSess, inMlbx, null);
        if (sessionMailbox == null) {
            sessionMailbox = EdiSessionMailbox.createSessionMailbox(inSess, inMlbx, false);
        } else if (LifeCycleStateEnum.OBSOLETE.equals((Object)sessionMailbox.getLifeCycleState())) {
            sessionMailbox.setLifeCycleState(LifeCycleStateEnum.ACTIVE);
        }
        if (inPrimary != null && inPrimary.booleanValue()) {
            inSess.replacePrimaryMailbox(sessionMailbox);
        }
        return sessionMailbox;
    }

    @Nullable
    public static EdiSessionMailbox findActiveSessionMailbox(EdiSession inSess, EdiMailbox inMlbx, Boolean inPrimaryKey) {
        IDomainQuery sessmlbxdq = QueryUtils.createDomainQuery((String)"EdiSessionMailbox").addDqPredicate(PredicateFactory.eq((IMetafieldId) IEdiField.EDISESSMLBX_SESSION, (Object)inSess.getEdisessGkey())).addDqPredicate(PredicateFactory.eq((IMetafieldId) IEdiField.EDISESSMLBX_MAILBOX, (Object)inMlbx.getEdimlbxGkey())).addDqPredicate(PredicateFactory.eq((IMetafieldId) IEdiField.EDISESSMLBX_LIFE_CYCLE_STATE, (Object) LifeCycleStateEnum.ACTIVE));
        if (inPrimaryKey != null) {
            sessmlbxdq.addDqPredicate(PredicateFactory.eq((IMetafieldId) IEdiField.EDISESSMLBX_IS_PRIMARY, (Object)inPrimaryKey));
        }
        List sessMailList = HibernateApi.getInstance().findEntitiesByDomainQuery(sessmlbxdq);
        EdiSessionMailbox sessionMailbox = null;
        if (sessMailList != null) {
            if (sessMailList.size() == 1) {
                sessionMailbox = (EdiSessionMailbox)sessMailList.get(0);
            } else if (sessMailList.size() > 1) {
                throw BizFailure.wrap((Throwable) BizViolation.create((IPropertyKey) IEdiPropertyKeys.DUP_SESSION_MAILBOX_EXISTS, null, (Object)inSess.getEdisessName(), (Object)inMlbx.getEdimlbxName()));
            }
        }
        return sessionMailbox;
    }

    public static EdiSessionMailbox createSessionMailbox(EdiSession inSess, EdiMailbox inMlbx, Boolean inPrimary) {
        EdiSessionMailbox sessionMailbox = new EdiSessionMailbox(inSess, inMlbx, inPrimary);
        HibernateApi.getInstance().save((Object)sessionMailbox);
        return sessionMailbox;
    }

    public void setLifeCycleState(LifeCycleStateEnum inLifeCycleState) {
        this.setEdisessmlbxLifeCycleState(inLifeCycleState);
    }

    public LifeCycleStateEnum getLifeCycleState() {
        return this.getEdisessmlbxLifeCycleState();
    }

    public Object getEdiPtnrTableKey() {
        return this.getEdisessmlbxMailbox().getEdimlbxTradingPartner().getEdiptnrGkey();
    }

    public Object getEdiMlbxTableKey() {
        return this.getEdisessmlbxMailbox().getEdimlbxGkey();
    }

    public void preProcessInsert(FieldChanges inOutMoreChanges) {
        super.preProcessInsert(inOutMoreChanges);
        if (EdiSessionMailbox.findActiveSessionMailbox(this.getEdisessmlbxSession(), this.getEdisessmlbxMailbox(), null) != null && !DataSourceEnum.SNX.equals((Object) ContextHelper.getThreadDataSource())) {
            throw BizFailure.wrap((Throwable)BizViolation.create((IPropertyKey) IEdiPropertyKeys.NON_UNIQUE_SESSION_MAILBOX, null, (Object)this.getEdisessmlbxMailbox().getEdimlbxName(), (Object)this.getEdisessmlbxSession().getEdisessName()));
        }
    }

    public void preProcessUpdate(FieldChanges inChanges, FieldChanges inOutMoreChanges) {
        super.preProcessUpdate(inChanges, inOutMoreChanges);
        if (inChanges.hasFieldChange(IEdiField.EDISESSMLBX_MAILBOX) && EdiSessionMailbox.findActiveSessionMailbox(this.getEdisessmlbxSession(), this.getEdisessmlbxMailbox(), null) != null) {
            throw BizFailure.wrap((Throwable)BizViolation.create((IPropertyKey) IEdiPropertyKeys.NON_UNIQUE_SESSION_MAILBOX, null, (Object)this.getEdisessmlbxMailbox().getEdimlbxName(), (Object)this.getEdisessmlbxSession().getEdisessName()));
        }
    }

    public BizViolation validateChanges(FieldChanges inChanges) {
        BizViolation bv = super.validateChanges(inChanges);
        if ((inChanges.hasFieldChange(IEdiField.EDISESSMLBX_SESSION) || inChanges.hasFieldChange(IEdiField.EDISESSMLBX_MAILBOX) || inChanges.hasFieldChange(IEdiField.EDISESSMLBX_IS_PRIMARY)) && this.getEdisessmlbxIsPrimary() != null && this.getEdisessmlbxIsPrimary().booleanValue()) {
            BizViolation localbv;
            EdiSession session = this.getEdisessmlbxSession();
            EdiMailbox mailbox = this.getEdisessmlbxMailbox();
            if (EdiMessageDirectionEnum.R.equals((Object)session.getEdisessDirection()) && (localbv = session.validateInboundSesssionUniqueness(mailbox, this.getEdisessmlbxIsPrimary())) != null) {
                bv = localbv.appendToChain(bv);
            }
        }
        return bv;
    }

    @Nullable
    public static EdiMailbox findPrimarySessionMailboxForSession(EdiSession inSession) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"EdiSessionMailbox").addDqPredicate(PredicateFactory.eq((IMetafieldId) IEdiField.EDISESSMLBX_SESSION, (Object)inSession.getEdisessGkey())).addDqPredicate(PredicateFactory.eq((IMetafieldId) IEdiField.EDISESSMLBX_IS_PRIMARY, (Object) Boolean.TRUE));
        EdiSessionMailbox sessionMailbox = (EdiSessionMailbox)HibernateApi.getInstance().getUniqueEntityByDomainQuery(dq);
        if (sessionMailbox != null) {
            return sessionMailbox.getEdisessmlbxMailbox();
        }
        return null;
    }
}
