package com.zpmc.ztos.infra.base.business.inventory;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.business.dataobject.MoveEventDO;
import com.zpmc.ztos.infra.base.business.enums.argo.*;
import com.zpmc.ztos.infra.base.business.enums.framework.LifeCycleStateEnum;
import com.zpmc.ztos.infra.base.business.enums.framework.PredicateVerbEnum;
import com.zpmc.ztos.infra.base.business.equipments.Che;
import com.zpmc.ztos.infra.base.business.equipments.Equipment;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.business.model.*;
import com.zpmc.ztos.infra.base.business.predicate.PredicateFactory;
import com.zpmc.ztos.infra.base.common.consts.CheKindConsts;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.common.events.Event;
import com.zpmc.ztos.infra.base.common.events.EventManager;
import com.zpmc.ztos.infra.base.common.events.EventType;
import com.zpmc.ztos.infra.base.common.exceptions.BizFailure;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.helps.ContextHelper;
import com.zpmc.ztos.infra.base.common.model.*;
import com.zpmc.ztos.infra.base.common.scopes.Yard;
import com.zpmc.ztos.infra.base.common.utils.ArgoUtils;
import com.zpmc.ztos.infra.base.common.utils.LogUtils;
import com.zpmc.ztos.infra.base.common.utils.UserContextUtils;
import com.zpmc.ztos.infra.base.utils.QueryUtils;
import org.apache.log4j.Logger;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.*;

public class MoveEvent extends MoveEventDO {
    public static final String XPS_USER_ID_SUFFIX_FOR_COMPLETE_MOVE = ":COMPLETE_MOVE";
    private static final Logger LOGGER = Logger.getLogger(MoveEvent.class);
    public static final IMetafieldId MVE_UFV_UNIT_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId) IServicesMovesField.MVE_UFV, (IMetafieldId) UnitField.UFV_UNIT_ID);
    public static final IMetafieldId MVE_UFV_PRIMARY_EQS_GKEY = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId) IServicesMovesField.MVE_UFV, (IMetafieldId) UnitField.UFV_PRIMARY_EQS_GKEY);
    public static final IMetafieldId MVE_UFV_CARRIAGE_EQS_GKEY = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId) IServicesMovesField.MVE_UFV, (IMetafieldId) UnitField.UFV_CARRIAGE_EQS_GKEY);
    public static final IMetafieldId MVE_UFV_UNIT_GKEY = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId) IServicesMovesField.MVE_UFV, (IMetafieldId) UnitField.UFV_UNIT_GKEY);
    public static final IMetafieldId MVE_TO_POS_LOC_TYPE = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId) IServicesMovesField.MVE_TO_POSITION, (IMetafieldId) IServicesMovesField.POS_LOC_TYPE);
    private static final BizFieldList BIZ_FIELD_LIST = new BizFieldList();
    private static final Map EVENT_SETTERS = new HashMap();
    public static final Map SERVICE_2_MOVE_KIND = new HashMap();
    private static final Map MOVE_KIND_2_SERVICE = new HashMap();
    public static final String EMPTY_STRING = "";

    public MoveEvent() {
        this.setMveExclude(Boolean.FALSE);
    }

    public static MoveEvent hydrate(Serializable inPrimaryKey) {
        return (MoveEvent) HibernateApi.getInstance().load(MoveEvent.class, inPrimaryKey);
    }

    public static MoveEvent recordMoveEvent(UnitFacilityVisit inUfv, LocPosition inOldPos, LocPosition inNewPos, CarrierVisit inCarrierVisit, MoveInfoBean inMoveInfo, EventEnum inCtrService) {
        WiMoveKindEnum moveKind = inMoveInfo.getMoveKind();
        if (WiMoveKindEnum.RailDisch.equals((Object)moveKind) && EventEnum.UNIT_DISCH.equals((Object)inCtrService)) {
            LOGGER.error((Object)"Move Kind and Event Enum Mismatch!");
            LOGGER.error((Object)("Ufv:" + inUfv + ": last Pos:" + (Object)inUfv.getUfvLastKnownPosition() + ": oldpos:" + (Object)inOldPos + ": newpos:" + (Object)inNewPos + ": CvId:" + inCarrierVisit.getCvId() + ": MODE:" + (Object)inCarrierVisit.getCvCarrierMode()));
        }
        if (WiMoveKindEnum.VeslDisch.equals((Object)moveKind) || WiMoveKindEnum.RailDisch.equals((Object)moveKind) || WiMoveKindEnum.VeslLoad.equals((Object)moveKind) || WiMoveKindEnum.RailLoad.equals((Object)moveKind) || WiMoveKindEnum.Receival.equals((Object)moveKind) || WiMoveKindEnum.Delivery.equals((Object)moveKind)) {
            MoveEvent moveEvent;
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info((Object)("Processing request to record move event. With move kind: " + (Object)moveKind + " and event type: " + inCtrService.getId()));
            }
            if (MoveEvent.shouldUpdateExistingEvent(moveEvent = MoveEvent.getLastMoveEvent(inUfv), inNewPos, inMoveInfo, inCtrService)) {
                LogUtils.forceLogAtInfo((Logger)LOGGER, (Object)("In recordMoveEvent looking to update existing event instead of creating new one. Found event: " + moveEvent));
                if ((WiMoveKindEnum.VeslDisch.equals((Object)moveKind) || WiMoveKindEnum.RailDisch.equals((Object)moveKind)) && moveEvent.getMveTimePut() == null) {
                    LogUtils.forceLogAtInfo((Logger)LOGGER, (Object)"Updating existing partial discharge event.");
                    moveEvent.setMveToPosition(inNewPos);
                    moveEvent.setMoveInfo(inMoveInfo);
                    return moveEvent;
                }
                try {
                    LogUtils.forceLogAtInfo((Logger)LOGGER, (Object)("Event already exists for Ufv:" + inUfv + ": last Pos:" + (inUfv.getUfvLastKnownPosition() != null ? inUfv.getUfvLastKnownPosition() : EMPTY_STRING) + ": oldpos:" + (inOldPos != null ? inOldPos : EMPTY_STRING) + ": newpos:" + (inNewPos != null ? inNewPos : EMPTY_STRING) + ": CvId:" + (inCarrierVisit.getCvId() != null ? inCarrierVisit.getCvId() : EMPTY_STRING) + ": MODE:" + (inCarrierVisit.getCvCarrierMode() != null ? inCarrierVisit.getCvCarrierMode() : EMPTY_STRING) + ": existing event time:" + (moveEvent.getEventCreated() != null ? moveEvent.getEventCreated() : EMPTY_STRING) + ": existing event user:" + (moveEvent.getEvntAppliedBy() != null ? moveEvent.getEvntAppliedBy() : EMPTY_STRING) + ": current user:" + ContextHelper.getThreadUserId()));
                }
                catch (Exception ex) {
                    LOGGER.debug((Object)"Error during logging string formulation: ", (Throwable)ex);
                }
                LogUtils.forceLogAtInfo((Logger)LOGGER, (Object)("Not creating a new discharge event because an existing one was found: " + moveEvent));
                return moveEvent;
            }
            if (moveEvent != null) {
                LogUtils.forceLogAtInfo((Logger)LOGGER, (Object)("Creating a new event because an existing one was found but had a different event type: " + moveEvent.getEventTypeId() + "."));
            }
        }
        MoveEvent event = new MoveEvent();
        Unit unit = inUfv.getUfvUnit();
        event.setMveUfv(inUfv);
        event.setMveFromPosition(inOldPos);
        event.setMveToPosition(inNewPos);
        event.setMveLineSnapshot(unit.getUnitLineOperator());
        event.setMveCarrier(inCarrierVisit);
        event.setMoveInfo(inMoveInfo);
        event.setEvntFacility(inUfv.getUfvFacility());
        event.setMveFreightKind(unit.getUnitFreightKind());
        event.setMveCategory(unit.getUnitCategory());
        MoveEvent.setEventYard(event, inOldPos, inNewPos);
        EventManager em = (EventManager) Roastery.getBean((String)"eventManager");
        FieldChanges fcs = new FieldChanges();
        fcs.setFieldChange(IInventoryField.POS_NAME, (Object)inOldPos.getPosName(), (Object)inNewPos.getPosName());
        try {
            if (inCtrService != null) {
                Date eventTime;
                EventType resolvedEventType = EventType.resolveIEventType((IEventType)inCtrService);
                if (MoveEvent.isDischMoveByXpsCompleteMoveOrManualDisch(inUfv, inCtrService)) {
                    eventTime = ArgoUtils.timeNow();
                } else {
                    eventTime = inMoveInfo.getTimePut() != null ? inMoveInfo.getTimePut() : inMoveInfo.getTimeDischarge();
                    Timestamp zeroDate = new Timestamp(0L);
                    eventTime = eventTime != null && !eventTime.equals(zeroDate) ? eventTime : inMoveInfo.getTimeCarryComplete();
                    eventTime = eventTime != null && !eventTime.equals(zeroDate) ? eventTime : ArgoUtils.timeNow();
                }
//                em.persistEventAndPerformRules((Event)event, eventTime, resolvedEventType, null, null, null, (IServiceable)unit, fcs, null);
            }
        }
//        catch (BizViolation bv) {
//            LOGGER.error((Object)("recordMoveEvent: error recording service event: " + (Object)((Object)bv)));
//        }
        catch (Exception e) {
            LOGGER.error((Object)"recordMoveEvent: error recording move event due to duplicate", (Throwable)e);
        }
        UserContext uc = ContextHelper.getThreadUserContext();
//        ILicenseManager licenseManager = SecurityBeanUtils.getLicenseManager();
//        boolean isLicensed = licenseManager.isPrivilegeLicensed(uc.getScopeCoordinate(), InventoryPrivs.VIEW_UFV_MOVE_COUNT.getPrivilegeId(), false);
//        if (isLicensed && inUfv.getUfvVisitState().equals((Object) UnitVisitStateEnum.ACTIVE)) {
//            inUfv.incrementMoveCount();
//        }
        return event;
    }

    public static boolean shouldCreateUnitDERAMPEvent(@NotNull MoveEvent inExistingEvent) {
        boolean result = true;
        Unit inTargetServiceable = inExistingEvent.getMveUfv().getUfvUnit();
        if (inTargetServiceable == null) {
            throw BizFailure.create((String)"shouldCreateUnitDERAMPEvent called with null Serviceable!");
        }
        IDomainQuery dq = null;
        dq = QueryUtils.createDomainQuery((String)"Event").addDqPredicate(PredicateFactory.eq((IMetafieldId)IServicesField.EVNT_APPLIED_TO_CLASS, (Object)inTargetServiceable.getLogicalEntityType())).addDqPredicate(PredicateFactory.eq((IMetafieldId)IServicesField.EVNT_APPLIED_TO_PRIMARY_KEY, (Object)inTargetServiceable.getPrimaryKey())).addDqPredicate(PredicateFactory.eq((IMetafieldId)IServicesField.EVNT_EVENT_TYPE, (Object)EventType.resolveIEventType((IEventType)EventEnum.UNIT_RECTIFY).getEvnttypeGkey())).addDqPredicate(PredicateFactory.ge((IMetafieldId)IServicesField.EVNT_CREATED, (Object)inExistingEvent.getEventTime())).addDqOrdering(Ordering.desc((IMetafieldId)IServicesField.EVNT_CREATED));
        dq.setMaxResults(1);
        dq.setRequireTotalCount(false);
        Event uniqueEntityByDomainQuery = (Event)HibernateApi.getInstance().getUniqueEntityByDomainQuery(dq);
        if (uniqueEntityByDomainQuery != null) {
            result = false;
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug((Object)("Last discharge event : " + inExistingEvent.getEventTypeId() + " : " + inExistingEvent.getEventTime()));
                LOGGER.debug((Object)("Unit was discharged and rectified : " + uniqueEntityByDomainQuery.getEventTypeId() + " : " + uniqueEntityByDomainQuery.getEventCreated()));
            }
        }
        return result;
    }

    private static boolean shouldUpdateExistingEvent(@Nullable MoveEvent inExistingEvent, @NotNull LocPosition inNewPos, @NotNull MoveInfoBean inMoveInfo, @NotNull EventEnum inCtrService) {
        boolean shouldUpdate = false;
        WiMoveKindEnum moveKind = inMoveInfo.getMoveKind();
        if (inExistingEvent != null && moveKind.equals((Object)inExistingEvent.getMveMoveKind()) && inExistingEvent.getEventTypeId() != null && inExistingEvent.getEventTypeId().equals(inCtrService.getId()) && (inNewPos.isSamePosition(inExistingEvent.getMveToPosition()) || inExistingEvent.getMveTimePut() == null)) {
            shouldUpdate = true;
        }
        if (shouldUpdate && WiMoveKindEnum.RailDisch.equals((Object)inExistingEvent.getMveMoveKind()) && EventEnum.UNIT_DERAMP.getId().equals(inExistingEvent.getEventTypeId())) {
            shouldUpdate = MoveEvent.shouldCreateUnitDERAMPEvent(inExistingEvent);
        }
        return shouldUpdate;
    }

    private static void setEventYard(MoveEvent inEvent, LocPosition inOldPos, LocPosition inNewPos) {
        try {
            if (inNewPos.isYardPosition()) {
                inEvent.setEvntYard(inNewPos.resolveYard());
            } else if (inOldPos.isYardPosition()) {
                inEvent.setEvntYard(inOldPos.resolveYard());
            }
        }
        catch (Exception e) {
            LOGGER.error((Object)("Failed to resolve yard for MoveEvent <" + inEvent + "> old pos <" + (Object)inOldPos + "> new pos <" + (Object)inNewPos + ">" + e));
        }
    }

    private static boolean isDischMoveByXpsCompleteMoveOrManualDisch(UnitFacilityVisit inUfv, EventEnum inCtrService) {
        return EventEnum.UNIT_DISCH.equals((Object)inCtrService) && inUfv.getUfvTimeEcIn() == null && !"-system-".equals(ContextHelper.getThreadUserId()) && (ContextHelper.getThreadExternalUserId() != null && ContextHelper.getThreadExternalUserId().endsWith(XPS_USER_ID_SUFFIX_FOR_COMPLETE_MOVE) || DataSourceEnum.USER_LCL.equals((Object)ContextHelper.getThreadDataSource()));
    }

    @Nullable
    public static MoveEvent getLastMoveEvent(UnitFacilityVisit inUfv) {
        EventType shiftOnCarrier = EventType.resolveIEventType((IEventType)EventEnum.UNIT_SHIFT_ON_CARRIER);
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"MoveEvent").addDqPredicate(PredicateFactory.eq((IMetafieldId) IServicesMovesField.MVE_UFV, (Object)inUfv.getUfvGkey())).addDqOrdering(Ordering.desc((IMetafieldId)IServicesField.EVNT_APPLIED_DATE));
        if (shiftOnCarrier != null) {
            dq.addDqPredicate(PredicateFactory.not((IPredicate)PredicateFactory.eq((IMetafieldId)IServicesField.EVNT_EVENT_TYPE, (Object)shiftOnCarrier.getEvnttypeGkey())));
        }
        dq.setMaxResults(1);
        dq.setRequireTotalCount(false);
        return (MoveEvent)HibernateApi.getInstance().getUniqueEntityByDomainQuery(dq);
    }

    @Nullable
    public static MoveEvent getLastRecordedYardMove(UnitFacilityVisit inUfv) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"MoveEvent").addDqPredicate(PredicateFactory.eq((IMetafieldId) IServicesMovesField.MVE_UFV, (Object)inUfv.getUfvGkey())).addDqPredicate(PredicateFactory.eq((IMetafieldId)MVE_TO_POS_LOC_TYPE, (Object) LocTypeEnum.YARD)).addDqOrdering(Ordering.desc((IMetafieldId) IServicesMovesField.MVE_TIME_PUT));
        dq.setMaxResults(1);
        Serializable[] moveGkey = HibernateApi.getInstance().findPrimaryKeysByDomainQuery(dq);
        if (moveGkey != null && moveGkey.length > 0) {
            return (MoveEvent)HibernateApi.getInstance().load(MoveEvent.class, moveGkey[0]);
        }
        return null;
    }

    @Nullable
    public static MoveEvent getLastDeliveryEvent(UnitFacilityVisit inUfv) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"MoveEvent").addDqPredicate(PredicateFactory.eq((IMetafieldId) IServicesMovesField.MVE_UFV, (Object)inUfv.getUfvGkey())).addDqPredicate(PredicateFactory.eq((IMetafieldId) IServicesMovesField.MVE_MOVE_KIND, (Object)WiMoveKindEnum.Delivery)).addDqOrdering(Ordering.desc((IMetafieldId) IServicesMovesField.MVE_TIME_PUT));
        dq.setMaxResults(1);
        dq.setRequireTotalCount(false);
        return (MoveEvent)HibernateApi.getInstance().getUniqueEntityByDomainQuery(dq);
    }

    @Nullable
    public static MoveEvent getLastMoveEvent(UnitFacilityVisit inUfv, EventType inEventType) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"MoveEvent").addDqPredicate(PredicateFactory.eq((IMetafieldId) IServicesMovesField.MVE_UFV, (Object)inUfv.getUfvGkey())).addDqPredicate(PredicateFactory.eq((IMetafieldId)IServicesField.EVNT_EVENT_TYPE, (Object)inEventType.getEvnttypeGkey())).addDqOrdering(Ordering.desc((IMetafieldId)IServicesField.EVNT_APPLIED_DATE));
        dq.setMaxResults(1);
        Serializable[] moveGkey = HibernateApi.getInstance().findPrimaryKeysByDomainQuery(dq);
        if (moveGkey != null && moveGkey.length > 0) {
            return (MoveEvent)HibernateApi.getInstance().load(MoveEvent.class, moveGkey[0]);
        }
        return null;
    }

    @Nullable
    public static List<MoveEvent> getMoveHistory(@NotNull UnitFacilityVisit inUfv) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"MoveEvent").addDqPredicate(PredicateFactory.eq((IMetafieldId) IServicesMovesField.MVE_UFV, (Object)inUfv.getUfvGkey())).addDqOrdering(Ordering.asc((IMetafieldId)IServicesField.EVNT_APPLIED_DATE)).addDqOrdering(Ordering.asc((IMetafieldId)IServicesField.EVNT_GKEY));
        return HibernateApi.getInstance().findEntitiesByDomainQuery(dq);
    }

    public void applyFieldChanges(FieldChanges inFieldChanges) {
        super.applyFieldChanges(inFieldChanges);
        if (inFieldChanges.hasFieldChange(MVE_UFV_UNIT_ID)) {
            this.setMoveEventProps(inFieldChanges);
        }
    }

    public void preProcessInsert(FieldChanges inOutMoreChanges) {
        Equipment equipment;
        super.preProcessInsert(inOutMoreChanges);
        Unit unit = this.getMveUfv() == null ? null : this.getMveUfv().getUfvUnit();
        Equipment equipment2 = equipment = unit == null ? null : unit.getUnitEquipment();
        if (!(equipment == null || !EquipClassEnum.CONTAINER.equals((Object)equipment.getEqClass()) || EventEnum.UNIT_YARD_MOVE.getId().equals(this.getEventTypeId()) || EventEnum.UNIT_SHIFT_ON_CARRIER.getId().equals(this.getEventTypeId()) || EventEnum.UNIT_YARD_SHIFT.getId().equals(this.getEventTypeId()) || DataSourceEnum.TESTING.equals((Object)ContextHelper.getThreadDataSource()))) {
            try {
//                MoveEventUnique mveu = new MoveEventUnique();
//                MoveEventUniqueId uniqueId = new MoveEventUniqueId();
//                uniqueId.setMveuUfv(this.getMveUfv());
//                uniqueId.setMveuMoveKind(this.getMveMoveKind());
//                uniqueId.setMveuEventTypeId(this.getEventTypeId());
//                Date roundedToMinute = DateUtils.round((Date)this.getEventCreated(), (int)12);
//                uniqueId.setMveuAppliedDate(roundedToMinute);
//                mveu.setMveuId(uniqueId);
//                HibernateApi.getInstance().save((Object)mveu);
//                HibernateApi.getInstance().flush();
            }
            catch (Exception e) {
                LOGGER.error((Object)("Duplicate move event creation stopped for MoveKind:" + (Object)this.getMveMoveKind() + ", EventType:" + this.getEventTypeId() + " and UFV:" + this.getMveUfv().getUfvGkey()), (Throwable)e);
                throw e;
            }
        }
    }

    public void preProcessUpdate(FieldChanges inChanges, FieldChanges inOutMoreChanges) {
        super.preProcessUpdate(inChanges, inOutMoreChanges);
        if (!inChanges.hasFieldChange(IServicesMovesField.MVE_PROCESSED)) {
            inOutMoreChanges.setFieldChange(IServicesMovesField.MVE_PROCESSED, (Object) Boolean.FALSE);
        }
    }

    private void setMoveEventProps(FieldChanges inFieldChanges) {
        AbstractMoveEvntSetter handler;
        UnitFacilityVisit ufv;
        String eqId = (String)inFieldChanges.getFieldChange(MVE_UFV_UNIT_ID).getNewValue();
        WiMoveKindEnum moveKind = this.getMveMoveKind();
        if (moveKind == null && (ufv = this.findMostRecentUfv(eqId, null)) == null) {
            throw BizFailure.create((IPropertyKey)IInventoryPropertyKeys.UNIT_UFV_NOT_FOUND, null, (Object)eqId, (Object)ContextHelper.getThreadFacility());
        }
        String fromSlot = null;
        String toSlot = null;
        if (inFieldChanges.hasFieldChange(IInventoryBizMetafield.UNIT_FROM_SLOT)) {
            fromSlot = (String)inFieldChanges.getFieldChange(IInventoryBizMetafield.UNIT_FROM_SLOT).getNewValue();
        }
        if (inFieldChanges.hasFieldChange(IInventoryBizMetafield.UNIT_TO_SLOT)) {
            toSlot = (String)inFieldChanges.getFieldChange(IInventoryBizMetafield.UNIT_TO_SLOT).getNewValue();
        }
        if ((handler = (AbstractMoveEvntSetter)EVENT_SETTERS.get((Object)moveKind)) == null) {
            throw BizFailure.create((String)("setMoveEventProps: no handler for " + (Object)moveKind));
        }
        handler.setMoveEventProperties(this, eqId, fromSlot, toSlot, this.getMveCarrier());
    }

    public void setFieldValue(IMetafieldId inFieldId, Object inFieldValue) {
        if (!(MVE_UFV_UNIT_ID.equals((Object)inFieldId) || IInventoryBizMetafield.UNIT_FROM_SLOT.equals((Object)inFieldId) || IInventoryBizMetafield.UNIT_TO_SLOT.equals((Object)inFieldId))) {
            super.setFieldValue(inFieldId, inFieldValue);
        }
    }

    protected void setMoveInfo(MoveInfoBean inMoveInfo) {
        Che che;
        String newName;
        String currentName;
        Che che2;
        Serializable cheGkey;
        this.setMveMoveKind(inMoveInfo.getMoveKind());
        EdiPostingContext postingContext = ContextHelper.getThreadEdiPostingContext();
        if (postingContext != null && ContextHelper.getThreadDataSource().equals((Object)DataSourceEnum.EDI_ACTIVITY) && postingContext.getActivityDate() != null) {
            inMoveInfo.setTimePut(postingContext.getActivityDate());
        }
        if ((cheGkey = MoveEvent.resolveCheGkeySparcsCheIndex(inMoveInfo.getCheIndexFetch(), inMoveInfo.getYardGkey())) != null) {
            che2 = (Che)HibernateApi.getInstance().get(Che.class, cheGkey);
            this.setMveCheFetch(che2);
            if (che2 != null) {
                currentName = this.getMveCheFetchLoginName();
                newName = che2.getCheLoginName();
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug((Object)("Current Fetch CHE login is " + currentName + " New Value is " + newName));
                }
                if (newName != null && !newName.isEmpty()) {
                    this.setMveCheFetchLoginName(newName);
                    inMoveInfo.setFetchLoginName(newName);
                }
            }
        }
        if ((cheGkey = MoveEvent.resolveCheGkeySparcsCheIndex(inMoveInfo.getCheIndexCarry(), inMoveInfo.getYardGkey())) != null) {
            che2 = (Che)HibernateApi.getInstance().get(Che.class, cheGkey);
            this.setMveCheCarry(che2);
            if (che2 != null) {
                currentName = this.getMveCheCarryLoginName();
                newName = che2.getCheLoginName();
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug((Object)("Current Carry CHE login is " + currentName + " New Value is " + newName));
                }
                if (newName != null && !newName.isEmpty()) {
                    this.setMveCheCarryLoginName(newName);
                    inMoveInfo.setCarryLoginName(newName);
                }
            }
        }
        if ((cheGkey = MoveEvent.resolveCheGkeySparcsCheIndex(inMoveInfo.getCheIndexPut(), inMoveInfo.getYardGkey())) != null) {
            che2 = (Che)HibernateApi.getInstance().get(Che.class, cheGkey);
            this.setMveChePut(che2);
            if (che2 != null) {
                currentName = this.getMveChePutLoginName();
                newName = che2.getCheLoginName();
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug((Object)("Current Put CHE login is " + currentName + " New Value is " + newName));
                }
                if (newName != null && !newName.isEmpty()) {
                    this.setMveChePutLoginName(newName);
                    inMoveInfo.setPutLoginName(newName);
                }
            }
        }
        Che putChe = this.getMveChePut();
        Che fetchChe = this.getMveCheFetch();
        if (WiMoveKindEnum.RailLoad.equals((Object)this.getMveMoveKind())) {
            this.setMveCheQuayCrane(putChe == null ? fetchChe : putChe);
        }
        if (WiMoveKindEnum.RailDisch.equals((Object)this.getMveMoveKind())) {
            this.setMveCheQuayCrane(fetchChe == null ? putChe : fetchChe);
        }
        if (MoveEvent.isQuayCrane(this.getMveCheFetch())) {
            this.setMveCheQuayCrane(this.getMveCheFetch());
        } else if (MoveEvent.isQuayCrane(this.getMveChePut())) {
            this.setMveCheQuayCrane(this.getMveChePut());
        } else if ((WiMoveKindEnum.VeslDisch.equals((Object)this.getMveMoveKind()) || WiMoveKindEnum.VeslLoad.equals((Object)this.getMveMoveKind())) && MoveEvent.isQuayCrane(che = PointOfWork.findCheForPointOfWork((String)inMoveInfo.getWiPow()))) {
            this.setMveCheQuayCrane(che);
        }
        this.setMveTimeDispatch(inMoveInfo.getTimeDispatch());
        this.setMveTimeDischarge(inMoveInfo.getTimeDischarge());
        this.setMveTimeFetch(inMoveInfo.getTimeFetch());
        if (inMoveInfo.isPartialDischarge()) {
            this.setMveTimePut(null);
        } else if (inMoveInfo.getTimePut() == null) {
            this.setMveTimePut(new Date());
        } else {
            this.setMveTimePut(inMoveInfo.getTimePut());
        }
        if (this.getMveTimeDischarge() == null) {
            this.setMveTimeDischarge(inMoveInfo.getTimeDischarge());
        }
        this.setMveTimeCarryCheDispatch(inMoveInfo.getTimeCarryCheDispatch());
        this.setMveTimeCarryCheFetchReady(inMoveInfo.getTimeCarryCheFetchReady());
        this.setMveTimeCarryChePutReady(inMoveInfo.getTimeCarryChePutReady());
        this.setMveTimeCarryComplete(inMoveInfo.getTimeCarryComplete());
        this.setMveTZArrivalTime(inMoveInfo.getTimeTzArrival());
        this.setMveDistOfCarry(inMoveInfo.getDistOfCarry());
        this.setMveDistToStart(inMoveInfo.getDistToStart());
        this.setMveRehandleCount(inMoveInfo.getRehandleCount());
        this.setMveRestowAccount(inMoveInfo.getRestowAccount());
        this.setMveRestowReason(inMoveInfo.getRestowReason());
        this.setMveServiceOrder(inMoveInfo.getServiceOrder());
        this.setMveCheCarryLoginName(inMoveInfo.getCarryLoginName());
        this.setMveCheFetchLoginName(inMoveInfo.getFetchLoginName());
        this.setMveChePutLoginName(inMoveInfo.getPutLoginName());
        this.setMveTwinFetch(inMoveInfo.getTwinFetch());
        this.setMveTwinCarry(inMoveInfo.getTwinCarry());
        this.setMveTwinPut(inMoveInfo.getTwinPut());
        this.setMvePOW(inMoveInfo.getWiPow());
        this.setMveBerth(inMoveInfo.getWiBerth());
    }

    public static boolean isQuayCrane(Che inMveCheFetch) {
        return inMveCheFetch != null && (CheKindConsts.CRANE.equals(inMveCheFetch.getCheKind()) || CheKindConsts.WSV.equals(inMveCheFetch.getCheKind()));
    }

    public static boolean isRailCrane(Che inChe) {
        return inChe != null && CheKindConsts.RMG.equals(inChe.getCheKind());
    }

    @Nullable
    public static Serializable resolveCheGkeySparcsCheIndex(Long inCheIndex, Serializable inYardGkey) {
        if (inCheIndex != null && inYardGkey != null) {
            IDomainQuery dq = QueryUtils.createDomainQuery((String)"Che").addDqField(IArgoField.CHE_GKEY).addDqPredicate(PredicateFactory.eq((IMetafieldId)IArgoField.CHE_YARD, (Object)inYardGkey)).addDqPredicate(PredicateFactory.eq((IMetafieldId)IArgoField.CHE_ID, (Object)inCheIndex));
            IQueryResult result = HibernateApi.getInstance().findValuesByDomainQuery(dq);
            int resultCount = result.getTotalResultCount();
            if (resultCount == 1) {
                return (Serializable)result.getValue(0, 0);
            }
            return null;
        }
        return null;
    }

    @Nullable
    public static Che resolveCheByShortName(String inCheShortName, Serializable inYardGkey) {
        return (Che)HibernateApi.getInstance().getUniqueEntityByDomainQuery(MoveEvent.resolveLiveCheQuery(inCheShortName, inYardGkey));
    }

    @Nullable
    public static Che resolveCheByShortName(String inCheShortName, Yard inYard) {
        return (Che)HibernateApi.getInstance().getUniqueEntityByDomainQuery(MoveEvent.resolveLiveCheQuery(inCheShortName, inYard.getYrdGkey()));
    }

    @Nullable
    public static Che resolveCheOfKindTruckByShortName(String inCheShortName, Serializable inYardGkey) {
        IDomainQuery dq = MoveEvent.resolveLiveCheQuery(inCheShortName, inYardGkey).addDqPredicate(PredicateFactory.eq((IMetafieldId)IArgoField.CHE_KIND, (Object)CheKindConsts.TRUCK));
        return (Che)HibernateApi.getInstance().getUniqueEntityByDomainQuery(dq);
    }

    public static IDomainQuery resolveLiveCheQuery(String inCheShortName, Serializable inYardGkey) {
        return QueryUtils.createDomainQuery((String)"Che").addDqPredicate(PredicateFactory.eq((IMetafieldId)IArgoField.CHE_YARD, (Object)inYardGkey)).addDqPredicate(PredicateFactory.eq((IMetafieldId)IArgoField.CHE_SHORT_NAME, (Object)inCheShortName)).addDqPredicate(PredicateFactory.eq((IMetafieldId)IArgoField.XPE_CHE_LIFE_CYCLE_STATE, (Object)LifeCycleStateEnum.ACTIVE));
    }

    @Nullable
    public static Che resolveCheProxyByShortName(String inCheShortName, Serializable inYardGkey) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"Che").addDqPredicate(PredicateFactory.eq((IMetafieldId)IArgoField.CHE_YARD, (Object)inYardGkey)).addDqPredicate(PredicateFactory.eq((IMetafieldId)IArgoField.CHE_SHORT_NAME, (Object)inCheShortName)).addDqPredicate(PredicateFactory.eq((IMetafieldId)IArgoField.XPE_CHE_LIFE_CYCLE_STATE, (Object) LifeCycleStateEnum.ACTIVE));
        Serializable[] xpeCheGkey = HibernateApi.getInstance().findPrimaryKeysByDomainQuery(dq);
        if (xpeCheGkey == null || xpeCheGkey.length == 0) {
            return null;
        }
        if (xpeCheGkey.length == 1) {
            return (Che)HibernateApi.getInstance().load(Che.class, xpeCheGkey[0]);
        }
        throw BizFailure.create((IPropertyKey)IFrameworkPropertyKeys.FRAMEWORK__NON_UNIQUE_RESULT, null, (Object)xpeCheGkey.length, (Object)dq);
    }

    @Nullable
    private UnitFacilityVisit findMostRecentUfv(String inEqId, MoveEvent inEvent) {
        UnitFacilityVisit ufv;
        if (inEvent != null && inEvent.getMveTimePut() != null) {
            ufv = this.findUfvBasedOnUfvCompletedTime(inEqId, inEvent);
        } else {
            ufv = this.findUfv(inEqId, 1);
            if (ufv == null) {
                ufv = this.findUfv(inEqId, 4);
            }
        }
        return ufv;
    }

    @Nullable
    private UnitFacilityVisit findUfv(String inFullEqId, int inDesiredStates) {
        IQueryResult queryResult = MoveEvent.getUnitFndr().doUfvQuery(inFullEqId, inDesiredStates, false);
        int totalResultCount = queryResult.getTotalResultCount();
        if (totalResultCount == 0) {
            return null;
        }
        Serializable ufvKey = (Serializable)queryResult.getValue(0, UnitField.UFV_GKEY);
        UnitFacilityVisit ufv = (UnitFacilityVisit)HibernateApi.getInstance().get(UnitFacilityVisit.class, ufvKey);
        if (ufv == null) {
            throw BizFailure.create((IPropertyKey)IFrameworkPropertyKeys.FAILURE__FIND_BY_PK, null, (Object)"UnitFacilityVisit", (Object)ufvKey);
        }
        return ufv;
    }

    private UnitFacilityVisit findUfvBasedOnUfvCompletedTime(String inFullEqId, MoveEvent inEvent) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"UnitFacilityVisit").addDqField(UnitField.UFV_GKEY).addDqField(UnitField.UFV_UNIT_ID).addDqField(UnitField.UFV_UNIT_GKEY);
        dq.setFullLeftOuterJoin(true);
        IDomainQuery subQuery = QueryUtils.createDomainQuery((String)"Unit").addDqPredicate(PredicateFactory.isNotNull((IMetafieldId)IInventoryField.UNIT_EQUIPMENT)).addDqPredicate(PredicateFactory.eqProperty((IMetafieldId) UnitField.UNIT_GKEY, (IMetafieldId)OuterQueryMetafieldId.valueOf((IMetafieldId) UnitField.UFV_UNIT_GKEY)));
        subQuery.addDqPredicate(PredicateFactory.eq((IMetafieldId) UnitField.UNIT_PRIMARY_EQ_ID_FULL, (Object)inFullEqId));
        dq.addDqPredicate(PredicateFactory.subQueryExists((IDomainQuery)subQuery));
        IQueryResult queryResult = HibernateApi.getInstance().findValuesByDomainQuery(dq);
        int totalResultCount = queryResult.getTotalResultCount();
        if (totalResultCount == 0) {
            return null;
        }
        Serializable ufvKey = null;
        UnitFacilityVisit bestUfv = null;
        ArrayList<UnitFacilityVisit> ufvList = new ArrayList<UnitFacilityVisit>();
        if (totalResultCount > 1) {
            for (int i = 0; i < totalResultCount; ++i) {
                ufvKey = (Serializable)queryResult.getValue(i, UnitField.UFV_GKEY);
                UnitFacilityVisit ufv = (UnitFacilityVisit)HibernateApi.getInstance().get(UnitFacilityVisit.class, ufvKey);
                ufvList.add(ufv);
            }
        }
        if (!ufvList.isEmpty() && ufvList.size() > 1 && inEvent.getMveTimePut() != null) {
            for (UnitFacilityVisit thisUfv : ufvList) {
                bestUfv = this.getBestUnitFacilityVisitBasedOnTimeComplete(inEvent.getMveTimePut(), bestUfv, new Date(), thisUfv);
            }
            return bestUfv;
        }
        ufvKey = (Serializable)queryResult.getValue(0, UnitField.UFV_GKEY);
        UnitFacilityVisit ufv = (UnitFacilityVisit)HibernateApi.getInstance().get(UnitFacilityVisit.class, ufvKey);
        if (ufv == null) {
            throw BizFailure.create((IPropertyKey)IFrameworkPropertyKeys.FAILURE__FIND_BY_PK, null, (Object)"UnitFacilityVisit", (Object)ufvKey);
        }
        return ufv;
    }

    private UnitFacilityVisit getBestUnitFacilityVisitBasedOnTimeComplete(Date inEventTime, UnitFacilityVisit inBestUfv, Date inNow, UnitFacilityVisit inThisUfv) {
        if (inBestUfv == null) {
            inBestUfv = inThisUfv;
        } else {
            Date tThis;
            Date tBest = inBestUfv.getUfvTimeComplete() == null ? inNow : inBestUfv.getUfvTimeComplete();
            Date date = tThis = inThisUfv.getUfvTimeComplete() == null ? inNow : inThisUfv.getUfvTimeComplete();
            if (tThis.after(inEventTime) && tBest.before(inEventTime)) {
                inBestUfv = inThisUfv;
            }
        }
        return inBestUfv;
    }

    public String getUnitFromSlot() {
        return this.getMveFromPosition().getPosSlot();
    }

    public String getUnitToSlot() {
        return this.getMveToPosition().getPosSlot();
    }

    private static IUnitFinder getUnitFndr() {
        return (IUnitFinder)Roastery.getBean((String)"unitFinder");
    }

    public BizFieldList getBizFieldList() {
        return BIZ_FIELD_LIST;
    }

    public static EventEnum moveKind2EventEnum(WiMoveKindEnum inMoveKind) {
        return (EventEnum)MOVE_KIND_2_SERVICE.get((Object)inMoveKind);
    }

    public static WiMoveKindEnum eventEnum2MoveKind(EventEnum inEventEnum) {
        WiMoveKindEnum moveKind = (WiMoveKindEnum)SERVICE_2_MOVE_KIND.get((Object)inEventEnum);
        if (moveKind == null) {
            LOGGER.error((Object)("eventEnum2MoveKind: could not determine move kind for " + (Object)inEventEnum));
            moveKind = WiMoveKindEnum.Other;
        }
        return moveKind;
    }

    public static WiMoveKindEnum eventEnum2MoveKindForExporting(EventEnum inEventEnum) {
        WiMoveKindEnum moveKind = (WiMoveKindEnum)SERVICE_2_MOVE_KIND.get((Object)inEventEnum);
        if (moveKind == null) {
            moveKind = WiMoveKindEnum.Other;
        }
        return moveKind;
    }

    private static void mapMoveKind(EventEnum inEventEnum, WiMoveKindEnum inKindEnum) {
        SERVICE_2_MOVE_KIND.put(inEventEnum, inKindEnum);
        MOVE_KIND_2_SERVICE.put(inKindEnum, inEventEnum);
    }

    public Boolean getMveIsDischargeForRestow() {
        Boolean fieldValue = Boolean.FALSE;
        EventEnum eventEnum = EventEnum.getEnum((String)this.getEventTypeId());
        if (EventEnum.UNIT_DERAMP.equals((Object)eventEnum) || EventEnum.UNIT_DISCH.equals((Object)eventEnum)) {
            CarrierVisit cv;
            LocPosition pos = this.getMveFromPosition();
            CarrierVisit carrierVisit = cv = pos == null ? null : pos.resolveOutboundCarrierVisit();
            if (this.getMveUfv().getUfvActualObCv().equals((Object)cv)) {
                fieldValue = Boolean.TRUE;
            }
        }
        return fieldValue;
    }

    public void preProcessDelete(FieldChanges inChanges) {
        try {
            if (this.getEventAppliedToNaturalKey() == null || this.getEventGKey() == null) {
                LOGGER.error((Object)"MoveEvent - preProcessDelete: Key Value Null");
                return;
            }
            IDomainQuery dq = QueryUtils.createDomainQuery((String)"Flag").addDqPredicate(PredicateFactory.eq((IMetafieldId)IServicesField.FLAG_APPLIED_TO_NATURAL_KEY, (Object)this.getEventAppliedToNaturalKey())).addDqPredicate(PredicateFactory.eq((IMetafieldId)IServicesField.FLAG_ASSOCIATED_EVENT_GKEY, (Object)this.getEventGKey()));
            HibernateApi.getInstance().deleteByDomainQuery(dq);
            HibernateApi.getInstance().flush();
        }
        catch (Exception e) {
            LOGGER.error((Object)("preProcessDelete: Error while deleting Flag :" + e));
            throw BizFailure.wrap((Throwable)e);
        }
    }

    @Nullable
    public BizViolation validateDeletion() {
        UnitFacilityVisit ufv;
        BizViolation bv = super.validateDeletion();
        UserContext uc = ContextHelper.getThreadUserContext();
//        ILicenseManager licenseManager = SecurityBeanUtils.getLicenseManager();
//        boolean isLicensed = licenseManager.isPrivilegeLicensed(uc.getScopeCoordinate(), InventoryPrivs.VIEW_UFV_MOVE_COUNT.getPrivilegeId(), false);
//        if (isLicensed && (ufv = this.getMveUfv()) != null && ufv.getUfvVisitState().equals((Object)UnitVisitStateEnum.ACTIVE)) {
//            ufv.decrementMoveCount();
//        }
        return bv;
    }

    public static IPredicate formMveUnitHoldsPermissionsAppliedOnPredicate(IMetafieldId inMetafieldId, PredicateVerbEnum inVerb, Object inValue) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"Flag").addDqField(IServicesField.FLAG_APPLIED_TO_PRIMARY_KEY).addDqPredicate(PredicateFactory.eq((IMetafieldId)IServicesField.FLAG_APPLIED_TO_CLASS, (Object)"Unit"));
        IPredicate intf = PredicateFactory.createPredicate((UserContext) UserContextUtils.getSystemUserContext(), (IMetafieldId)IServicesField.FLAG_APPLIED_DATE, (PredicateVerbEnum)inVerb, (Object)inValue);
        dq.addDqPredicate(intf);
        return PredicateFactory.subQueryIn((IDomainQuery)dq, (IMetafieldId)MVE_UFV_UNIT_GKEY);
    }

    public static IPredicate formMveUnitHoldsPermissionsUpdatedOnPredicate(IMetafieldId inMetafieldId, PredicateVerbEnum inVerb, Object inValue) {
        IMetafieldId vetoAppDate = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)IServicesField.FLAG_VETOS, (IMetafieldId)IServicesField.VETO_APPLIED_DATE);
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"Flag").addDqField(IServicesField.FLAG_APPLIED_TO_PRIMARY_KEY).addDqPredicate(PredicateFactory.eq((IMetafieldId)IServicesField.FLAG_APPLIED_TO_CLASS, (Object)"Unit"));
        if (PredicateVerbEnum.NULL.equals((Object)inVerb)) {
            IDomainQuery subDq = QueryUtils.createDomainQuery((String)"Veto").addDqField(IServicesField.VETO_BLOCKED_FLAG).addDqPredicate(PredicateFactory.eqProperty((IMetafieldId)IServicesField.VETO_BLOCKED_FLAG, (IMetafieldId)OuterQueryMetafieldId.valueOf((IMetafieldId)IServicesField.FLAG_GKEY)));
            dq.addDqPredicate(PredicateFactory.not((IPredicate)PredicateFactory.subQueryExists((IDomainQuery)subDq)));
        } else {
            IPredicate intf = PredicateFactory.createPredicate((UserContext)UserContextUtils.getSystemUserContext(), (IMetafieldId)vetoAppDate, (PredicateVerbEnum)inVerb, (Object)inValue);
            dq.addDqPredicate(intf);
        }
        return PredicateFactory.subQueryIn((IDomainQuery)dq, (IMetafieldId)MVE_UFV_UNIT_GKEY);
    }

    public static IPredicate formMveUnitAppliedHoldOrPermNamePredicate(IMetafieldId inMetafieldId, PredicateVerbEnum inVerb, Object inValue) {
        IServicesManager sm = (IServicesManager)Roastery.getBean((String)"servicesManager");
        IPredicate predCtr = sm.createActiveFlagPredicate(inValue, LogicalEntityEnum.EQ, MVE_UFV_PRIMARY_EQS_GKEY);
        IPredicate predChs = sm.createActiveFlagPredicate(inValue, LogicalEntityEnum.EQ, MVE_UFV_CARRIAGE_EQS_GKEY);
        IPredicate predUnit = sm.createActiveFlagPredicate(inValue, LogicalEntityEnum.UNIT, MVE_UFV_UNIT_GKEY);
        if (PredicateVerbEnum.NE.equals((Object)inVerb) || PredicateVerbEnum.NULL.equals((Object)inVerb)) {
            return PredicateFactory.conjunction().add(PredicateFactory.not((IPredicate)predCtr)).add(PredicateFactory.not((IPredicate)predUnit)).add(PredicateFactory.not((IPredicate)predChs));
        }
        return PredicateFactory.disjunction().add(predCtr).add(predUnit).add(predChs);
    }

    public static IPredicate formMveUnitAppliedHoldOrPermViewPredicate(IMetafieldId inMetafieldId, PredicateVerbEnum inVerb, Object inValue) {
        IServicesManager sm = (IServicesManager)Roastery.getBean((String)"servicesManager");
        IPredicate predCtr = sm.createActiveFlagPredicateForHpv(inValue, LogicalEntityEnum.EQ, MVE_UFV_PRIMARY_EQS_GKEY);
        IPredicate predChs = sm.createActiveFlagPredicateForHpv(inValue, LogicalEntityEnum.EQ, MVE_UFV_CARRIAGE_EQS_GKEY);
        IPredicate predUnit = sm.createActiveFlagPredicateForHpv(inValue, LogicalEntityEnum.UNIT, MVE_UFV_UNIT_GKEY);
        if (PredicateVerbEnum.NE.equals((Object)inVerb) || PredicateVerbEnum.NULL.equals((Object)inVerb)) {
            return PredicateFactory.conjunction().add(PredicateFactory.not((IPredicate)predCtr)).add(PredicateFactory.not((IPredicate)predUnit)).add(PredicateFactory.not((IPredicate)predChs));
        }
        return PredicateFactory.disjunction().add(predCtr).add(predUnit).add(predChs);
    }

    public boolean isGateMove() {
        return WiMoveKindEnum.Delivery.equals((Object)this.getMveMoveKind()) || WiMoveKindEnum.Receival.equals((Object)this.getMveMoveKind());
    }

    public boolean isLoadOrDischargeMove() {
        return WiMoveKindEnum.VeslLoad.equals((Object)this.getMveMoveKind()) || WiMoveKindEnum.VeslDisch.equals((Object)this.getMveMoveKind()) || WiMoveKindEnum.RailLoad.equals((Object)this.getMveMoveKind()) || WiMoveKindEnum.RailDisch.equals((Object)this.getMveMoveKind());
    }

    public static boolean isMoveEvent(Event inEvent) {
        if (inEvent == null) {
            return false;
        }
        WiMoveKindEnum moveKind = (WiMoveKindEnum)SERVICE_2_MOVE_KIND.get((Object)EventEnum.getEnum((String)inEvent.getEventTypeId()));
        return moveKind != null && !WiMoveKindEnum.Other.equals((Object)moveKind);
    }

    public static MoveEvent resolveMoveEvent(Event inEvent) {
        if (MoveEvent.isMoveEvent(inEvent)) {
            return (MoveEvent)HibernateApi.getInstance().downcast((DatabaseEntity)inEvent, MoveEvent.class);
        }
        return null;
    }

    static {
        BIZ_FIELD_LIST.add(IServicesMovesField.MVE_LINE_SNAPSHOT, BizRoleEnum.LINEOP);
        IMetafieldId carrierOperatorMfId = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId) IServicesMovesField.MVE_CARRIER, (IMetafieldId)IArgoField.CV_OPERATOR);
        BIZ_FIELD_LIST.add(carrierOperatorMfId, BizRoleEnum.LINEOP);
        BIZ_FIELD_LIST.add(carrierOperatorMfId, BizRoleEnum.RAILROAD);
        BIZ_FIELD_LIST.add(carrierOperatorMfId, BizRoleEnum.HAULIER);
        BIZ_FIELD_LIST.add(MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId) IServicesMovesField.MVE_UFV, (IMetafieldId) UnitField.UFV_RTG_TRUCKING_COMPANY), BizRoleEnum.HAULIER);
        IMetafieldId ufvActualIBCvOperatorMfId = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId) IServicesMovesField.MVE_UFV, (IMetafieldId) UnitField.UFV_ACTUAL_INBOUND_CV_OPERATOR);
        BIZ_FIELD_LIST.add(ufvActualIBCvOperatorMfId, BizRoleEnum.LINEOP);
        BIZ_FIELD_LIST.add(ufvActualIBCvOperatorMfId, BizRoleEnum.RAILROAD);
        BIZ_FIELD_LIST.add(ufvActualIBCvOperatorMfId, BizRoleEnum.HAULIER);
        IMetafieldId ufvIntendedOBCvOperatorMfId = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId) IServicesMovesField.MVE_UFV, (IMetafieldId) UnitField.UFV_INTENDED_OUTBOUND_CV_OPERATOR);
        BIZ_FIELD_LIST.add(ufvIntendedOBCvOperatorMfId, BizRoleEnum.LINEOP);
        BIZ_FIELD_LIST.add(ufvIntendedOBCvOperatorMfId, BizRoleEnum.RAILROAD);
        BIZ_FIELD_LIST.add(ufvIntendedOBCvOperatorMfId, BizRoleEnum.HAULIER);
        EVENT_SETTERS.put(WiMoveKindEnum.VeslLoad, new VesLoadEvntSetter());
        EVENT_SETTERS.put(WiMoveKindEnum.VeslDisch, new VesDischEvntSetter());
        EVENT_SETTERS.put(WiMoveKindEnum.RailLoad, new RailLoadEvntSetter());
        EVENT_SETTERS.put(WiMoveKindEnum.RailDisch, new RailDischEvntSetter());
        EVENT_SETTERS.put(WiMoveKindEnum.Delivery, new DeliveryEvntSetter());
        EVENT_SETTERS.put(WiMoveKindEnum.Receival, new ReceivalEvntSetter());
        EVENT_SETTERS.put(WiMoveKindEnum.ShiftOnBoard, new ShiftOnBoardEvntSetter());
        EVENT_SETTERS.put(WiMoveKindEnum.YardMove, new YardMoveEvntSetter());
        EVENT_SETTERS.put(WiMoveKindEnum.YardShift, new YardShiftEvntSetter());
        MoveEvent.mapMoveKind(EventEnum.UNIT_LOAD, WiMoveKindEnum.VeslLoad);
        MoveEvent.mapMoveKind(EventEnum.UNIT_DISCH, WiMoveKindEnum.VeslDisch);
        MoveEvent.mapMoveKind(EventEnum.UNIT_RAMP, WiMoveKindEnum.RailLoad);
        MoveEvent.mapMoveKind(EventEnum.UNIT_DERAMP, WiMoveKindEnum.RailDisch);
        MoveEvent.mapMoveKind(EventEnum.UNIT_DELIVER, WiMoveKindEnum.Delivery);
        MoveEvent.mapMoveKind(EventEnum.UNIT_RECEIVE, WiMoveKindEnum.Receival);
        MoveEvent.mapMoveKind(EventEnum.UNIT_YARD_SHIFT, WiMoveKindEnum.YardShift);
        MoveEvent.mapMoveKind(EventEnum.UNIT_YARD_MOVE, WiMoveKindEnum.YardMove);
        MoveEvent.mapMoveKind(EventEnum.UNIT_SHIFT_ON_CARRIER, WiMoveKindEnum.ShiftOnBoard);
        MoveEvent.mapMoveKind(EventEnum.UNIT_LOAD, WiMoveKindEnum.VeslLoad);
    }

    private static abstract class AbstractMoveEvntSetter {
        private AbstractMoveEvntSetter() {
        }

        protected abstract void setMoveEventProperties(MoveEvent var1, String var2, String var3, String var4, CarrierVisit var5);

        protected void setMoveEventProperties(MoveEvent inEvent, String inEqId, String inFromSlot, String inToSlot, CarrierVisit inCv, ILocation inFromLocation, ILocation inToLocation, EventEnum inContainerService, WiMoveKindEnum inMoveKind, LocTypeEnum[] inValidCarrierModes) {
            UnitFacilityVisit ufv = inEvent.findMostRecentUfv(inEqId, inEvent);
            if (ufv == null) {
                throw BizFailure.create((IPropertyKey)IInventoryPropertyKeys.UNIT_UFV_NOT_FOUND, null, (Object)inEqId, (Object)ContextHelper.getThreadFacility());
            }
            inEvent.setMveUfv(ufv);
            if (inCv == null && !WiMoveKindEnum.YardMove.getKey().equals(inMoveKind.getKey()) && !WiMoveKindEnum.YardShift.getKey().equals(inMoveKind.getKey())) {
                throw BizFailure.create((IPropertyKey)IInventoryPropertyKeys.MOVE__CARRIER_IS_NULL, null);
            }
            this.validateCarrierMode(inMoveKind, inCv, inValidCarrierModes);
            EventType eventType = EventType.resolveIEventType((IEventType)inContainerService);
            inEvent.setEvntEventType(eventType);
            inEvent.setMveFromPosition(LocPosition.createLocPosition((ILocation)inFromLocation, (String)inFromSlot, null));
            inEvent.setMveToPosition(LocPosition.createLocPosition((ILocation)inToLocation, (String)inToSlot, null));
            EventManager em = (EventManager)Roastery.getBean((String)"eventManager");
            FieldChanges fcs = new FieldChanges();
            fcs.setFieldChange(new FieldChange(IInventoryField.POS_SLOT, (Object)inFromSlot, (Object)inToSlot));
//            try {
//                em.persistEventAndPerformRules((Event)inEvent, inEvent.getEventTime(), inEvent.getEvntEventType(), null, inEvent.getEvntQuantity(), inEvent.getEvntQuantityUnit(), (IServiceable)ufv.getUfvUnit(), fcs, null);
//            }
//            catch (BizViolation e) {
//                throw BizFailure.wrap((Throwable)e);
//            }

        }

        private void validateCarrierMode(WiMoveKindEnum inMoveKind, CarrierVisit inCv, LocTypeEnum[] inValidCarrierModes) {
            if (inValidCarrierModes == null) {
                return;
            }
            boolean validCarrierMode = false;
            for (LocTypeEnum inValidCarrierMode : inValidCarrierModes) {
                if (!inValidCarrierMode.equals((Object)inCv.getCvCarrierMode())) continue;
                validCarrierMode = true;
                break;
            }
            if (!validCarrierMode) {
                throw BizFailure.wrap((Throwable)BizViolation.create((IPropertyKey)IInventoryPropertyKeys.MOVE__INVALID_CARRIER_MODE, null, (Object)inMoveKind.getDescriptionPropertyKey(), (Object)inCv.getCvCarrierMode().getDescriptionPropertyKey()));
            }
        }
    }

    private static class YardShiftEvntSetter
            extends AbstractMoveEvntSetter {
        private YardShiftEvntSetter() {
        }

        @Override
        protected void setMoveEventProperties(MoveEvent inEvent, String inEqId, String inFromSlot, String inToSlot, CarrierVisit inCv) {
            Yard yard = ContextHelper.getThreadYard();
            this.setMoveEventProperties(inEvent, inEqId, inFromSlot, inToSlot, inCv, (ILocation)yard, (ILocation)yard, EventEnum.UNIT_YARD_SHIFT, WiMoveKindEnum.YardShift, null);
        }
    }

    private static class YardMoveEvntSetter
            extends AbstractMoveEvntSetter {
        private YardMoveEvntSetter() {
        }

        @Override
        protected void setMoveEventProperties(MoveEvent inEvent, String inEqId, String inFromSlot, String inToSlot, CarrierVisit inCv) {
            Yard yard = ContextHelper.getThreadYard();
            this.setMoveEventProperties(inEvent, inEqId, inFromSlot, inToSlot, inCv, (ILocation)yard, (ILocation)yard, EventEnum.UNIT_YARD_MOVE, WiMoveKindEnum.YardMove, null);
        }
    }

    private static class ShiftOnBoardEvntSetter
            extends AbstractMoveEvntSetter {
        private ShiftOnBoardEvntSetter() {
        }

        @Override
        protected void setMoveEventProperties(MoveEvent inEvent, String inEqId, String inFromSlot, String inToSlot, CarrierVisit inCv) {
            this.setMoveEventProperties(inEvent, inEqId, inFromSlot, inToSlot, inCv, (ILocation)inCv, (ILocation)inCv, EventEnum.UNIT_SHIFT_ON_CARRIER, WiMoveKindEnum.ShiftOnBoard, new LocTypeEnum[]{LocTypeEnum.VESSEL, LocTypeEnum.RAILCAR});
        }
    }

    private static class ReceivalEvntSetter
            extends AbstractMoveEvntSetter {
        private ReceivalEvntSetter() {
        }

        @Override
        protected void setMoveEventProperties(MoveEvent inEvent, String inEqId, String inFromSlot, String inToSlot, CarrierVisit inCv) {
            this.setMoveEventProperties(inEvent, inEqId, inFromSlot, inToSlot, inCv, (ILocation)inCv, (ILocation)ContextHelper.getThreadYard(), EventEnum.UNIT_RECEIVE, WiMoveKindEnum.Receival, new LocTypeEnum[]{LocTypeEnum.TRUCK});
        }
    }

    private static class DeliveryEvntSetter
            extends AbstractMoveEvntSetter {
        private DeliveryEvntSetter() {
        }

        @Override
        protected void setMoveEventProperties(MoveEvent inEvent, String inEqId, String inFromSlot, String inToSlot, CarrierVisit inCv) {
            this.setMoveEventProperties(inEvent, inEqId, inFromSlot, inToSlot, inCv, (ILocation)ContextHelper.getThreadYard(), (ILocation)inCv, EventEnum.UNIT_DELIVER, WiMoveKindEnum.Delivery, new LocTypeEnum[]{LocTypeEnum.TRUCK});
        }
    }

    private static class RailDischEvntSetter
            extends AbstractMoveEvntSetter {
        private RailDischEvntSetter() {
        }

        @Override
        protected void setMoveEventProperties(MoveEvent inEvent, String inEqId, String inFromSlot, String inToSlot, CarrierVisit inCv) {
            this.setMoveEventProperties(inEvent, inEqId, inFromSlot, inToSlot, inCv, (ILocation)inCv, (ILocation)ContextHelper.getThreadYard(), EventEnum.UNIT_DERAMP, WiMoveKindEnum.RailDisch, new LocTypeEnum[]{LocTypeEnum.RAILCAR});
        }
    }

    private static class RailLoadEvntSetter
            extends AbstractMoveEvntSetter {
        private RailLoadEvntSetter() {
        }

        @Override
        protected void setMoveEventProperties(MoveEvent inEvent, String inEqId, String inFromSlot, String inToSlot, CarrierVisit inCv) {
            this.setMoveEventProperties(inEvent, inEqId, inFromSlot, inToSlot, inCv, (ILocation)ContextHelper.getThreadYard(), (ILocation)inCv, EventEnum.UNIT_RAMP, WiMoveKindEnum.RailLoad, new LocTypeEnum[]{LocTypeEnum.RAILCAR});
        }
    }

    private static class VesDischEvntSetter
            extends AbstractMoveEvntSetter {
        private VesDischEvntSetter() {
        }

        @Override
        protected void setMoveEventProperties(MoveEvent inEvent, String inEqId, String inFromSlot, String inToSlot, CarrierVisit inCv) {
            this.setMoveEventProperties(inEvent, inEqId, inFromSlot, inToSlot, inCv, (ILocation)inCv, (ILocation)ContextHelper.getThreadYard(), EventEnum.UNIT_DISCH, WiMoveKindEnum.VeslDisch, new LocTypeEnum[]{LocTypeEnum.VESSEL});
        }
    }

    private static class VesLoadEvntSetter
            extends AbstractMoveEvntSetter {
        private VesLoadEvntSetter() {
        }

        @Override
        protected void setMoveEventProperties(MoveEvent inEvent, String inEqId, String inFromSlot, String inToSlot, CarrierVisit inCv) {
            this.setMoveEventProperties(inEvent, inEqId, inFromSlot, inToSlot, inCv, (ILocation)ContextHelper.getThreadYard(), (ILocation)inCv, EventEnum.UNIT_LOAD, WiMoveKindEnum.VeslLoad, new LocTypeEnum[]{LocTypeEnum.VESSEL});
        }
    }

}
