package com.zpmc.ztos.infra.base.business.edi;

import com.zpmc.ztos.infra.base.business.dataobject.EdiSessionDO;
import com.zpmc.ztos.infra.base.business.dataobject.EdiSettingDO;
import com.zpmc.ztos.infra.base.business.enums.argo.EdiMessageClassEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.LogicalEntityEnum;
import com.zpmc.ztos.infra.base.business.enums.edi.EdiMessageDirectionEnum;
import com.zpmc.ztos.infra.base.business.enums.edi.EdiStatusEnum;
import com.zpmc.ztos.infra.base.business.enums.framework.LifeCycleStateEnum;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.business.model.MetafieldIdList;
import com.zpmc.ztos.infra.base.business.model.SavedPredicate;
import com.zpmc.ztos.infra.base.business.predicate.PredicateFactory;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.common.exceptions.BizFailure;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.helps.ContextHelper;
import com.zpmc.ztos.infra.base.common.model.AuditEvent;
import com.zpmc.ztos.infra.base.common.model.FieldChange;
import com.zpmc.ztos.infra.base.common.model.FieldChanges;
import com.zpmc.ztos.infra.base.common.scopes.Complex;
import com.zpmc.ztos.infra.base.common.utils.ArgoUtils;
import com.zpmc.ztos.infra.base.common.utils.edi.EdiUtil;
import com.zpmc.ztos.infra.base.utils.QueryUtils;
import org.apache.log4j.Logger;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class EdiSession extends EdiSessionDO {

    private static final Logger LOGGER = Logger.getLogger(EdiSession.class);

    public EdiSession() {
        this.setEdisessIsAutoPosted(Boolean.FALSE);
        this.setEdisessLifeCycleState(LifeCycleStateEnum.ACTIVE);
        this.setEdisessMessageClass(EdiMessageClassEnum.UNKNOWN);
    }

    public EdiSession(String inSessName, EdiTradingPartner inPartner, String inDesc, EdiMessageDirectionEnum inDirection, EdiStatusEnum inStatus, String inFileExtn, EdiMessageMap inMsgMap) {
        this.setEdisessName(inSessName);
        this.setEdisessTradingPartner(inPartner);
        this.setEdisessDesc(inDesc);
        this.setEdisessDirection(inDirection);
        this.setEdisessLastRunStatus(inStatus);
        this.setEdisessFileExt(inFileExtn);
        this.setEdisessMsgMap(inMsgMap);
        this.setEdisessIsAutoPosted(Boolean.FALSE);
        this.setEdisessComplex(ContextHelper.getThreadComplex());
        this.setEdisessLifeCycleState(LifeCycleStateEnum.ACTIVE);
        if (inMsgMap == null) {
            this.setEdisessMessageClass(EdiMessageClassEnum.UNKNOWN);
        } else {
            this.setEdisessMessageClass(inMsgMap.getEdimapEdiMsgType().getEdimsgClass());
        }
    }

    public void setLifeCycleState(LifeCycleStateEnum inLifeCycleState) {
        this.setEdisessLifeCycleState(inLifeCycleState);
    }

    public LifeCycleStateEnum getLifeCycleState() {
        return this.getEdisessLifeCycleState();
    }

    public void updateEdiMessageClassEnum(EdiMessageClassEnum inEdiMessageClassEnum) {
        this.setEdisessMessageClass(inEdiMessageClassEnum);
    }

    public void updateEdisessDelimeter(String inEdisessDelimeter) {
        this.setEdisessDelimeter(inEdisessDelimeter);
    }

    private static EdiSession create(String inSessName, EdiTradingPartner inPartner, String inDesc, EdiMessageDirectionEnum inDirection, EdiStatusEnum inStatus, String inFileExtn, EdiMessageMap inMsgMap) {
        EdiSession session = new EdiSession(inSessName, inPartner, inDesc, inDirection, inStatus, inFileExtn, inMsgMap);
        HibernateApi.getInstance().save((Object)session);
        return session;
    }

    public static EdiSession findOrCreateEdiSession(String inSessName, EdiTradingPartner inPartner, String inDesc, EdiMessageDirectionEnum inDirection, EdiStatusEnum inStatus, String inFileExtn, EdiMessageMap inMsgMap) {
        EdiSession session = EdiSession.findEdiSession(inSessName);
        if (session == null) {
            session = EdiSession.create(inSessName, inPartner, inDesc, inDirection, inStatus, inFileExtn, inMsgMap);
        }
        return session;
    }

    public static EdiSession findEdiSession(String inSessionName) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"EdiSession").addDqPredicate(PredicateFactory.eq((IMetafieldId)IEdiField.EDISESS_NAME, (Object)inSessionName));
        return (EdiSession)HibernateApi.getInstance().getUniqueEntityByDomainQuery(dq);
    }

    @Nullable
    public static EdiSession findActiveEdiSessionForInboundXml(EdiMessageClassEnum inMessageClass, EdiTradingPartner inPartner, EdiMailbox inMailbox) throws BizViolation {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"EdiSession").addDqPredicate(PredicateFactory.eq((IMetafieldId)IEdiField.EDISESS_DIRECTION, (Object)((Object)EdiMessageDirectionEnum.R))).addDqPredicate(PredicateFactory.eq((IMetafieldId)IEdiField.EDISESS_MESSAGE_CLASS, (Object)inMessageClass)).addDqPredicate(PredicateFactory.eq((IMetafieldId)IEdiField.EDISESS_TRADING_PARTNER, (Object)inPartner.getEdiptnrGkey())).addDqPredicate(PredicateFactory.eq((IMetafieldId)IEdiField.EDISESS_LIFE_CYCLE_STATE, (Object)LifeCycleStateEnum.ACTIVE)).addDqPredicate(PredicateFactory.isNull((IMetafieldId)IEdiField.EDISESS_MSG_MAP));
        List sessionList = HibernateApi.getInstance().findEntitiesByDomainQuery(dq);
        if (sessionList == null || sessionList.isEmpty()) {
            return null;
        }
        if (sessionList.size() == 1) {
            return (EdiSession)sessionList.get(0);
        }
        if (inMailbox != null) {
            return EdiSession.findUniqueSessionByMailboxAndMsgClass(inMessageClass, inPartner, inMailbox, EdiMessageDirectionEnum.R);
        }
        return null;
    }

    @Nullable
    public static EdiSession findUniqueSessionByMailboxAndMsgClass(EdiMessageClassEnum inMessageClass, EdiTradingPartner inPartner, EdiMailbox inMailbox, EdiMessageDirectionEnum inDirection) throws BizViolation {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"EdiSessionMailbox").addDqPredicate(PredicateFactory.eq((IMetafieldId)IEdiField.EDISESSMLBX_MAILBOX, (Object)inMailbox.getEdimlbxGkey())).addDqPredicate(PredicateFactory.eq((IMetafieldId)IEdiField.EDISESSMLBX_IS_PRIMARY, (Object) Boolean.TRUE)).addDqPredicate(PredicateFactory.eq((IMetafieldId)EdiCompoundField.EDISESSMLBX_SESS_DIRECTION, (Object)((Object)inDirection))).addDqPredicate(PredicateFactory.eq((IMetafieldId)EdiCompoundField.EDISESSMLBX_SESS_PARTNER, (Object)inPartner.getEdiptnrGkey())).addDqPredicate(PredicateFactory.eq((IMetafieldId)EdiCompoundField.EDISESSMLBX_SESS_MSGCLASS, (Object)inMessageClass)).addDqPredicate(PredicateFactory.isNull((IMetafieldId)EdiCompoundField.EDISESSMLBX_SESS_MSG_MAP));
        List sessionList = HibernateApi.getInstance().findEntitiesByDomainQuery(dq);
        if (sessionList == null || sessionList.isEmpty()) {
            return null;
        }
        if (sessionList.size() == 1) {
            return ((EdiSessionMailbox)sessionList.get(0)).getEdisessmlbxSession();
        }
        throw new BizViolation(IEdiPropertyKeys.NON_UNIQUE_SESSION_FOR_MSG_CLASS, null, null, null, new Object[]{inMailbox.getEdimlbxDirection().getDescriptionPropertyKey(), inPartner.getEdiptnrName(), inMessageClass, inMailbox.getEdimlbxName()});
    }

    public static EdiSession findUniqueSessionByMailboxAndMsgType(EdiMessageType inMessageType, EdiTradingPartner inPartner, EdiMessageDirectionEnum inDirection, EdiMailbox inMailbox) throws BizViolation {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"EdiSessionMailbox").addDqPredicate(PredicateFactory.eq((IMetafieldId)IEdiField.EDISESSMLBX_MAILBOX, (Object)inMailbox.getEdimlbxGkey())).addDqPredicate(PredicateFactory.eq((IMetafieldId)IEdiField.EDISESSMLBX_IS_PRIMARY, (Object) Boolean.TRUE)).addDqPredicate(PredicateFactory.eq((IMetafieldId)EdiCompoundField.EDISESSMLBX_SESS_PARTNER, (Object)inPartner.getEdiptnrGkey())).addDqPredicate(PredicateFactory.eq((IMetafieldId)EdiCompoundField.EDISESSMLBX_SESS_DIRECTION, (Object)((Object)inDirection))).addDqPredicate(PredicateFactory.eq((IMetafieldId)EdiCompoundField.EDISESSMLBX_SESS_MSG_MAP_TYPE, (Object)inMessageType.getEdimsgGkey()));
       List sessionList = HibernateApi.getInstance().findEntitiesByDomainQuery(dq);
        if (sessionList == null || sessionList.isEmpty()) {
            return null;
        }
        if (sessionList.size() == 1) {
            return ((EdiSessionMailbox)sessionList.get(0)).getEdisessmlbxSession();
        }
        throw new BizViolation(IEdiPropertyKeys.NON_UNIQUE_SESSION, null, null, null, new Object[]{inDirection.getDescriptionPropertyKey(), inPartner.getEdiptnrName(), inMessageType.getEdimsgId(), inMailbox.getEdimlbxName()});
    }

    @Nullable
    public static EdiSession findReceiveEdiSessionByPartnerAndClass(EdiMessageClassEnum inMessageClass, EdiTradingPartner inPartner) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"EdiSession").addDqPredicate(PredicateFactory.eq((IMetafieldId)IEdiField.EDISESS_DIRECTION, (Object)((Object)EdiMessageDirectionEnum.R))).addDqPredicate(PredicateFactory.eq((IMetafieldId)IEdiField.EDISESS_MESSAGE_CLASS, (Object)inMessageClass)).addDqPredicate(PredicateFactory.eq((IMetafieldId)IEdiField.EDISESS_TRADING_PARTNER, (Object)inPartner.getEdiptnrGkey()));
        List sessionsList = HibernateApi.getInstance().findEntitiesByDomainQuery(dq);
        return !sessionsList.isEmpty() ? (EdiSession)sessionsList.get(0) : null;
    }

    public IMetafieldId getScopeFieldId() {
        return IEdiField.EDISESS_COMPLEX;
    }

    public IMetafieldId getNaturalKeyField() {
        return IEdiField.EDISESS_NAME;
    }

    public Object getEdiMlbxTableKey() {
        IDomainQuery sessmlbxdq = QueryUtils.createDomainQuery((String)"EdiSessionMailbox").addDqPredicate(PredicateFactory.eq((IMetafieldId)IEdiField.EDISESSMLBX_IS_PRIMARY, (Object) Boolean.TRUE)).addDqPredicate(PredicateFactory.eq((IMetafieldId)IEdiField.EDISESSMLBX_SESSION, (Object)this.getEdisessGkey()));
        EdiSessionMailbox sessmlbx = (EdiSessionMailbox)HibernateApi.getInstance().getUniqueEntityByDomainQuery(sessmlbxdq);
        return sessmlbx.getEdisessmlbxMailbox().getEdimlbxGkey();
    }

    @Nullable
    public EdiMailbox getEdisessPrimaryMailbox() {
        EdiMailbox mailbox = null;
        EdiSessionMailbox sessionMailbox = this.findEdisessPrimaryMailbox();
        if (sessionMailbox != null) {
            mailbox = sessionMailbox.getEdisessmlbxMailbox();
            return null;
        }
        return mailbox;
    }

    @Nullable
    public EdiSessionMailbox findEdisessPrimaryMailbox() {
        IDomainQuery sessmlbxdq = QueryUtils.createDomainQuery((String)"EdiSessionMailbox").addDqPredicate(PredicateFactory.eq((IMetafieldId)IEdiField.EDISESSMLBX_IS_PRIMARY, (Object) Boolean.TRUE)).addDqPredicate(PredicateFactory.eq((IMetafieldId)IEdiField.EDISESSMLBX_SESSION, (Object)this.getEdisessGkey()));
        return (EdiSessionMailbox)HibernateApi.getInstance().getUniqueEntityByDomainQuery(sessmlbxdq);
    }

    public BizViolation validateChanges(FieldChanges inChanges) {
        String sessionName;
        BizViolation bv = super.validateChanges(inChanges);
        FieldChange fc = inChanges.getFieldChange(IEdiField.EDISESS_NAME);
        String string = sessionName = fc == null ? "" : (String)fc.getNewValue();
        if (inChanges.hasFieldChange(IEdiField.EDISESS_NAME) && !this.isUniqueInClass(IEdiField.EDISESS_NAME)) {
            bv = BizViolation.create((IPropertyKey)IEdiPropertyKeys.NON_UNIQUE_SESSION_NAME, (BizViolation)bv, (Object)sessionName);
        }
        if (!EdiUtil.isValidName(sessionName)) {
            bv = BizViolation.create((IPropertyKey)IEdiPropertyKeys.INVALID_SESSION_NAME, (BizViolation)bv, (Object)sessionName);
        }
        if (bv != null) {
            return bv;
        }
        FieldChange fc1 = inChanges.getFieldChange(IEdiBizMetafield.EDI_MLBX_TABLE_KEY);
        if (fc1 != null) {
            inChanges.removeFieldChange(IEdiBizMetafield.EDI_MLBX_TABLE_KEY);
        }
        if (inChanges.hasFieldChange(IEdiField.EDISESS_MESSAGE_CLASS) || inChanges.hasFieldChange(IEdiField.EDISESS_MSG_MAP)) {
            if (fc1 != null) {
                Serializable mailboxGkey = (Serializable)fc1.getNewValue();
                if (mailboxGkey != null) {
                    EdiMailbox mailbox = (EdiMailbox)HibernateApi.getInstance().load(EdiMailbox.class, mailboxGkey);
                    bv = this.validateInboundSesssionUniqueness(mailbox, Boolean.TRUE);
                }
            } else {
                bv = this.validateInboundSesssionUniqueness(null, Boolean.TRUE);
            }
        }
        return bv;
    }

    public BizViolation validateDeletion() {
        BizViolation bv = super.validateDeletion();
        try {
            this.checkDeletionRules("EdiSettingDO", IEdiField.EDISTNG_SESSION);
            this.checkDeletionRules("EdiSessionMailbox", IEdiField.EDISESSMLBX_SESSION);
            this.checkDeletionRules("EdiBatch", IEdiField.EDIBATCH_SESSION);
            this.checkDeletionRules("EdiJobSchedule", IEdiField.EDIJOBSCH_SESSION);
        }
        catch (BizViolation b) {
            bv = b;
        }
        return bv;
    }

    private void checkDeletionRules(String inEntityName, IMetafieldId inFieldId) throws BizViolation {
        IDomainQuery settingDq = QueryUtils.createDomainQuery((String)inEntityName).addDqPredicate(PredicateFactory.eq((IMetafieldId)inFieldId, (Object)this.getEdisessGkey()));
        if (HibernateApi.getInstance().existsByDomainQuery(settingDq)) {
            throw BizViolation.createFieldViolation((IPropertyKey)IEdiPropertyKeys.ERRKEY__TRYING_TO_DELETE_EDI_SESSION_WITH_CHILDREN, null, null);
        }
    }

    @Nullable
    public IValueHolder getEdisessPredicateVao() {
        SavedPredicate predicate = this.getEdisessPredicate();
        return predicate == null ? null : predicate.getPredicateVao();
    }

    public void setFieldValue(IMetafieldId inMetafieldId, Object inFieldValue) {
        if (IEdiBizMetafield.EDISESS_PREDICATE_VAO.equals((Object)inMetafieldId)) {
            if (inFieldValue == null || inFieldValue instanceof IValueHolder) {
                SavedPredicate predicate = this.getEdisessPredicate();
                if (predicate != null) {
                    this.setEdisessPredicate(null);
                    ArgoUtils.carefulDelete((Object)predicate);
                }
                if (inFieldValue == null) {
                    this.setEdisessPredicate(null);
                } else {
                    predicate = new SavedPredicate((IValueHolder)inFieldValue);
                    this.setEdisessPredicate(predicate);
                }
            } else {
                LOGGER.error((Object)("setFieldValue: invalid input " + inFieldValue));
            }
        } else {
            super.setFieldValue(inMetafieldId, inFieldValue);
        }
    }

    public Object getEdiMsgTypeTableKey() {
        return this.getEdisessMsgMap().getEdimapEdiMsgType().getEdimsgGkey();
    }

    public Object getEdiSessMlbxTableKey() {
        return this.getEdisessGkey();
    }

    public Object getEdiSessSettingTableKey() {
        return this.getEdisessGkey();
    }

    public Object getEdiSessFilterTableKey() {
        return this.getEdisessGkey();
    }

    public Object getEdiPtnrTableKey() {
        return this.getEdisessTradingPartner().getEdiptnrGkey();
    }

    public AuditEvent vetAuditEvent(AuditEvent inAuditEvent) {
        return inAuditEvent;
    }

    @Nullable
    public String getConfigValue(IConfig inConfig) {
        Set settings = this.getEdisessSettings();
        if (settings != null) {
            String configId = inConfig.getConfigId();
            for (Object setting : settings) {
                if (!configId.equals(((EdiSettingDO)setting).getEdistngConfigId())) continue;
                return ((EdiSettingDO)setting).getEdistngValue();
            }
        }
        return null;
    }

    @Nullable
    public String getLovKeyNameForPathToGuardian(String inPathToGuardian) {
        return null;
    }

    @Nullable
    public String getLovKeyNameForLogicalEntityType(LogicalEntityEnum inLogicalEntityType) {
        return null;
    }

    public Boolean skipEventRecording() {
        return false;
    }

    public List getGuardians() {
        return Collections.EMPTY_LIST;
    }

    public String getLogEntityParentId() {
        return this.getEdisessName();
    }

    public String getLogEntityId() {
        return this.getEdisessName();
    }

    public void calculateFlags() {
    }

    public LogicalEntityEnum getLogicalEntityType() {
        return LogicalEntityEnum.EDISESS;
    }

    @Nullable
    public BizViolation verifyApplyFlagToEntityAllowed() {
        return null;
    }

    public Complex getLogEntityComplex() {
        return this.getEdisessComplex();
    }

    public static MetafieldIdList getPathsToBizUnits(LogicalEntityEnum inEntity) {
        MetafieldIdList fields = new MetafieldIdList();
        fields.add(EdiCompoundField.getQualifiedField(EdiCompoundField.EDISESS_BIZU, "EdiSession"));
        return fields;
    }

    public static MetafieldIdList getPredicateFields(LogicalEntityEnum inEntity) {
        MetafieldIdList ids = new MetafieldIdList();
        ids.add(EdiCompoundField.getQualifiedField(IEdiField.EDISESS_NAME, "EdiSession"));
        ids.add(EdiCompoundField.getQualifiedField(IEdiField.EDISESS_TRADING_PARTNER, "EdiSession"));
        ids.add(EdiCompoundField.getQualifiedField(IEdiField.EDISESS_DIRECTION, "EdiSession"));
        return ids;
    }

    public void updateLastRunStatus(EdiStatusEnum inStatus) {
        super.setEdisessLastRunStatus(inStatus);
    }

    public void updateLastRunTimestamp(Date inDate) {
        super.setEdisessLastRunTimestamp(inDate);
    }

    public void updateEdisessMsgSeqNbr(Long inMsgSeqNbr) {
        super.setEdisessMsgSeqNbr(inMsgSeqNbr);
    }

    public void updateTranControlNbr(Long inControlNbr) {
        super.setEdisessTranCntlNbr(inControlNbr);
    }

    public void applyFieldChanges(FieldChanges inFieldChanges) {
        FieldChange fc = inFieldChanges.getFieldChange(IEdiBizMetafield.EDI_MLBX_TABLE_KEY);
        if (fc != null) {
            inFieldChanges.removeFieldChange(IEdiBizMetafield.EDI_MLBX_TABLE_KEY);
        }
        super.applyFieldChanges(inFieldChanges);
        if (inFieldChanges.hasFieldChange(IEdiField.EDISESS_MESSAGE_CLASS) || inFieldChanges.hasFieldChange(IEdiField.EDISESS_MSG_MAP)) {
            BizViolation bv = null;
            if (fc != null) {
                Serializable mailboxGkey = (Serializable)fc.getNewValue();
                if (mailboxGkey != null) {
                    EdiMailbox mailbox = (EdiMailbox)HibernateApi.getInstance().load(EdiMailbox.class, mailboxGkey);
                    bv = this.validateInboundSesssionUniqueness(mailbox, Boolean.TRUE);
                }
            } else {
                bv = this.validateInboundSesssionUniqueness(null, Boolean.TRUE);
            }
            if (bv != null) {
                throw BizFailure.wrap((Throwable)bv);
            }
        }
    }

    public boolean isFutureExtractApplicable() {
        return EdiMessageDirectionEnum.S.equals((Object)this.getEdisessDirection()) && (EdiMessageClassEnum.INVENTORY.equals((Object)this.getEdisessMessageClass()) || EdiMessageClassEnum.STOWPLAN.equals((Object)this.getEdisessMessageClass()) || EdiMessageClassEnum.RAILCONSIST.equals((Object)this.getEdisessMessageClass()));
    }

    void updateFilter(SavedPredicate inNewFilter) {
        SavedPredicate oldFilter = this.getEdisessPredicate();
        this.setEdisessPredicate(inNewFilter);
        ArgoUtils.carefulDelete((Object)oldFilter);
    }

    public boolean isUniqueSession(EdiMailbox inMailbox, Boolean inIsPrimaryMailbox) {
        if (EdiMessageDirectionEnum.S.equals((Object)this.getEdisessDirection())) {
            return true;
        }
        if (inMailbox == null && (inMailbox = EdiSessionMailbox.findPrimarySessionMailboxForSession(this)) == null)
        {
            return true;
        }
        inIsPrimaryMailbox = inIsPrimaryMailbox == null ? Boolean.FALSE : inIsPrimaryMailbox;
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"EdiSessionMailbox").addDqPredicate(PredicateFactory.eq((IMetafieldId)IEdiField.EDISESSMLBX_MAILBOX, (Object)inMailbox.getEdimlbxGkey())).addDqPredicate(PredicateFactory.eq((IMetafieldId)IEdiField.EDISESSMLBX_IS_PRIMARY, (Object)inIsPrimaryMailbox)).addDqPredicate(PredicateFactory.eq((IMetafieldId)EdiCompoundField.EDISESSMLBX_SESS_PARTNER, (Object)this.getEdisessTradingPartner().getEdiptnrGkey())).addDqPredicate(PredicateFactory.eq((IMetafieldId)EdiCompoundField.EDISESSMLBX_SESS_DIRECTION, (Object)((Object)this.getEdisessDirection())));
        EdiMessageMap msgMap = this.getEdisessMsgMap();
        if (msgMap == null) {
            dq.addDqPredicate(PredicateFactory.eq((IMetafieldId)EdiCompoundField.EDISESSMLBX_SESS_MSGCLASS, (Object)this.getEdisessMessageClass()));
            dq.addDqPredicate(PredicateFactory.isNull((IMetafieldId)EdiCompoundField.EDISESSMLBX_SESS_MSG_MAP));
        } else {
            dq.addDqPredicate(PredicateFactory.eq((IMetafieldId)EdiCompoundField.EDISESSMLBX_SESS_MSG_MAP_TYPE, (Object)msgMap.getEdimapEdiMsgType().getEdimsgGkey()));
            dq.addDqPredicate(PredicateFactory.ne((IMetafieldId)EdiCompoundField.EDISESSMLBX_SESSION, (Object)this.getEdisessGkey()));
        }
        return !HibernateApi.getInstance().existsByDomainQuery(dq);
    }

    public BizViolation validateInboundSesssionUniqueness(EdiMailbox inMailbox, Boolean inIsPrimaryMailbox) {
        BizViolation bv = null;
        if (EdiMessageDirectionEnum.S.equals((Object)this.getEdisessDirection())) {
            return null;
        }
        if (inMailbox == null) {
           inMailbox = EdiSessionMailbox.findPrimarySessionMailboxForSession(this);
        }
        if (inMailbox != null && !this.isUniqueSession(inMailbox, inIsPrimaryMailbox)) {
           bv = this.getEdisessMsgMap() != null ? new BizViolation(IEdiPropertyKeys.NON_UNIQUE_SESSION, null, bv, null, new Object[]{this.getEdisessDirection().getDescriptionPropertyKey(), this.getEdisessTradingPartner().getEdiptnrName(), this.getEdisessMsgMap().getEdimapEdiMsgType().getEdimsgId(), inMailbox.getEdimlbxName()}) : new BizViolation(IEdiPropertyKeys.NON_UNIQUE_SESSION_FOR_MSG_CLASS, null, bv, null, new Object[]{this.getEdisessDirection().getDescriptionPropertyKey(), this.getEdisessTradingPartner().getEdiptnrName(), this.getEdisessMessageClass(), inMailbox.getEdimlbxName()});
        }
        return bv;
    }

    void replacePrimaryMailbox(EdiSessionMailbox inSessionMailbox) {
        EdiSessionMailbox primaryMailbox = this.findEdisessPrimaryMailbox();
        if (primaryMailbox != null && !inSessionMailbox.equals(primaryMailbox)) {
           primaryMailbox.setEdisessmlbxIsPrimary(false);
            HibernateApi.getInstance().delete((Object)primaryMailbox);
        }
        inSessionMailbox.setEdisessmlbxIsPrimary(true);
    }

    public void postEventCreation(IEvent inEventCreated) {
    }

    public static EdiSession hydrate(Serializable inPrimaryKey) {
        return (EdiSession) HibernateApi.getInstance().load(EdiSession.class, inPrimaryKey);
    }
}
