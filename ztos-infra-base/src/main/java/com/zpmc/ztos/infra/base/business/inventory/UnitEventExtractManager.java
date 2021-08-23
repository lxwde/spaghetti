package com.zpmc.ztos.infra.base.business.inventory;

import com.zpmc.ztos.infra.base.business.enums.argo.*;
import com.zpmc.ztos.infra.base.business.enums.inventory.EqUnitRoleEnum;
import com.zpmc.ztos.infra.base.business.enums.inventory.StorageStartEndDayRuleTypeEnum;
import com.zpmc.ztos.infra.base.business.enums.inventory.UfvTransitStateEnum;
import com.zpmc.ztos.infra.base.business.equipments.Che;
import com.zpmc.ztos.infra.base.business.equipments.EquipType;
import com.zpmc.ztos.infra.base.business.equipments.Equipment;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.business.model.*;
import com.zpmc.ztos.infra.base.business.predicate.PredicateFactory;
import com.zpmc.ztos.infra.base.common.configs.InventoryConfig;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.common.events.AuditEvent;
import com.zpmc.ztos.infra.base.common.events.Event;
import com.zpmc.ztos.infra.base.common.events.EventManager;
import com.zpmc.ztos.infra.base.common.exceptions.BizFailure;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.helps.ContextHelper;
import com.zpmc.ztos.infra.base.common.model.*;
import com.zpmc.ztos.infra.base.common.scopes.Complex;
import com.zpmc.ztos.infra.base.common.scopes.Facility;
import com.zpmc.ztos.infra.base.common.scopes.Operator;
import com.zpmc.ztos.infra.base.common.type.ArgoCalendarEventType;
import com.zpmc.ztos.infra.base.utils.DateUtils;
import com.zpmc.ztos.infra.base.utils.QueryUtils;
import org.apache.log4j.Logger;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.io.Serializable;
import java.util.*;

public class UnitEventExtractManager {
    private static final Logger LOGGER = Logger.getLogger(UnitEventExtractManager.class);
    private static final String STATUS_QUEUED = "QUEUED";
    private static final String REEFER = "REEFER";
    private static final String POWER = "POWER";
    static final String EXEMPT = "EXEMPT_DAY";
    static final String GRATIS = "GRATIS_DAY";
    private static final String CMPLXNOTREQD = "CMPLXNOTREQD";
    private static final String INVOICED = "INVOICED";
    private static final String PARTIAL = "PARTIAL";
    public static final String HIPHEN = "-";
    public static final String AT = "@";
    private static final ArgoCalendarEventType[] EXEMPT_TYPES = new ArgoCalendarEventType[1];
    private static final ArgoCalendarEventType[] GRATIS_TYPES = new ArgoCalendarEventType[1];
    private static final Map<String, EqUnitRoleEnum> EQUIPMENT_STORAGE_ROLE_SETTING_MAP = new HashMap<String, EqUnitRoleEnum>(4){
        {
            this.put("PAYLOAD", EqUnitRoleEnum.PAYLOAD);
            this.put("CARRIAGE", EqUnitRoleEnum.CARRIAGE);
            this.put("ACCESSORY", EqUnitRoleEnum.ACCESSORY);
            this.put("ACCESSORY_ON_CHS", EqUnitRoleEnum.ACCESSORY_ON_CHS);
            this.put("PRIMARY", EqUnitRoleEnum.PRIMARY);
        }
    };
    private static final Map<String, EqUnitRoleEnum> EQUIPMENT_LINE_STORAGE_ROLE_SETTING_MAP = new HashMap<String, EqUnitRoleEnum>(4){
        {
            this.put("PAYLOAD", EqUnitRoleEnum.PAYLOAD);
            this.put("CARRIAGE", EqUnitRoleEnum.CARRIAGE);
            this.put("ACCESSORY", EqUnitRoleEnum.ACCESSORY);
            this.put("ACCESSORY_ON_CHS", EqUnitRoleEnum.ACCESSORY_ON_CHS);
            this.put("PRIMARY", EqUnitRoleEnum.PRIMARY);
        }
    };

    private UnitEventExtractManager() {
    }

    public static void createOrUpdateChargeableUnitEvent(Unit inUnit, Event inEvent, Long inBatchId, Map inFlexHashMap) {
        List cceList = UnitEventExtractManager.findExistingQueuedEvents((Long)inEvent.getPrimaryKey());
        if (!cceList.isEmpty()) {
            if (cceList.size() > 1) {
                LOGGER.error((Object)("ChargeableUnitEvent: Event is extracted more than once and in QUEUED status. Ue: " + inUnit + " for Event " + (Object)inEvent));
                return;
            }
            ChargeableUnitEvent ccv = (ChargeableUnitEvent)cceList.get(0);
            if (ccv != null && ccv.getBexuIsLocked().booleanValue()) {
                LOGGER.info((Object)("ChargeableUnitEvent: Event is locked - cannot update. Ue: " + inUnit + " for Event " + (Object)inEvent));
                return;
            }
            UnitEventExtractManager.setFields(inUnit, inEvent, inBatchId, inFlexHashMap, ccv);
        } else {
            Long ufvGkey;
            Facility facility = inEvent.getEvntFacility();
            UnitFacilityVisit ufv = inUnit.getUfvForFacilityNewest(facility);
            if (facility == null) {
                ufv = UnitEventExtractManager.devineEventUfv(inEvent, inUnit);
            }
            Long l = ufvGkey = ufv == null ? null : ufv.getUfvGkey();
            if (ufvGkey == null) {
                LOGGER.error((Object)("createOrUpdateChargeableUnitEvent: can not find UFV for event " + inEvent.getEventTypeId() + " " + inUnit + " assigning null ufvgkey in ChargeableUnitEvent"));
            }
            ChargeableUnitEvent ccv = ChargeableUnitEvent.create((Long)ufvGkey, (Operator)inEvent.getEvntOperator());
            if (ufv != null) {
      //          ccv.setBexuRestowType(ufv.getUfvRestowType() != null ? ufv.getUfvRestowType().getKey() : null);
            }
            UnitEventExtractManager.setFields(inUnit, inEvent, inBatchId, inFlexHashMap, ccv);
        }
        EventManager em = (EventManager)Roastery.getBean((String)"eventManager");
       // em.updateEventBillingExtractBatchId(inEvent.getPrimaryKey(), inBatchId);
    }

    @Nullable
    public static UnitFacilityVisit devineEventUfv(Event inEvent, Unit inUnit) {
        UnitFacilityVisit ufv = null;
        Complex cpx = inEvent.getEvntComplex();
        if (cpx != null && cpx.getCpxFcySet() != null) {
            if (cpx.getCpxFcySet().size() == 1) {
                Facility fcy = (Facility)cpx.getCpxFcySet().toArray()[0];
                ufv = inUnit.getUfvForFacilityAndEventTime(fcy, inEvent.getEventTime());
            } else {
                ufv = inUnit.getUfvForEventTime(inEvent.getEventTime());
            }
        }
        return ufv;
    }

    public static List<UnitFacilityVisit> getAllUfvInYard() {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"UnitFacilityVisit").addDqPredicate(PredicateFactory.eq((IMetafieldId) UnitField.UFV_CURRENT_POSITION_TYPE, (Object) LocTypeEnum.YARD));
        List visits = HibernateApi.getInstance().findEntitiesByDomainQuery(dq);
        return visits;
    }

    public static int createReeferChargeIfMissing(UnitFacilityVisit inUfv) {
        Unit unit = inUfv.getUfvUnit();
        if (unit.isReefer()) {
            Event firstReeferEvent = UnitEventExtractManager.getFirstPowerConnectEventForUnit(unit);
            if (firstReeferEvent != null) {
                List reeferCueList = UnitEventExtractManager.getCueForReeferEvent(unit, firstReeferEvent);
                if (reeferCueList == null || reeferCueList.isEmpty()) {
                    try {
                        UnitEventExtractManager.createReeferEvent(unit, (IEvent)firstReeferEvent);
                        return 1;
                    }
                    catch (Exception e) {
                        LOGGER.error((Object)("problem recording UnitEventExtractManager.createReeferChargeIfMissing " + unit));
                    }
                }
            } else {
                LOGGER.info((Object)("No " + EventEnum.UNIT_POWER_CONNECT.getId() + " event for unit " + unit.getUnitId() + " found - will not create " + "a REEFER chargeable event for this!"));
            }
        }
        return 0;
    }

    @Nullable
    private static Event getFirstPowerConnectEventForUnit(Unit inUnit) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"Event").addDqPredicate(PredicateFactory.eq((IMetafieldId) IServicesField.EVNT_APPLIED_TO_NATURAL_KEY, (Object)inUnit.getUnitId())).addDqPredicate(PredicateFactory.eq((IMetafieldId) MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId) IServicesField.EVNT_EVENT_TYPE, (IMetafieldId) IServicesField.EVNTTYPE_ID), (Object) EventEnum.UNIT_POWER_CONNECT.getId())).addDqPredicate(PredicateFactory.eq((IMetafieldId) IServicesField.EVNT_APPLIED_TO_PRIMARY_KEY, (Object)inUnit.getPrimaryKey())).addDqOrdering(Ordering.asc((IMetafieldId) IServicesField.EVNT_APPLIED_DATE));
        dq.setMaxResults(1);
        dq.setRequireTotalCount(false);
        Event firstReefer = (Event)HibernateApi.getInstance().getUniqueEntityByDomainQuery(dq);
        return firstReefer;
    }

    private static List getCueForReeferEvent(Unit inUnit, Event inFirstReefer) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"ChargeableUnitEvent").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoExtractField.BEXU_EVENT_TYPE, (Object) ChargeableUnitEventTypeEnum.REEFER)).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoExtractField.BEXU_EQ_ID, (Object)inUnit.getUnitId())).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoExtractField.BEXU_SOURCE_GKEY, (Object)inFirstReefer.getEvntGkey()));
        List reeferCharges = HibernateApi.getInstance().findEntitiesByDomainQuery(dq);
        return reeferCharges;
    }

    private static List getCueForReeferEvent(Facility inFcy, UnitFacilityVisit inUfv, ChargeableUnitEventTypeEnum inEventType) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"ChargeableUnitEvent").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoExtractField.BEXU_UFV_GKEY, (Object)inUfv.getPrimaryKey())).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoExtractField.BEXU_FACILITY, (Object)inUfv.getUfvFacility().getFcyGkey())).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoExtractField.BEXU_EVENT_TYPE, (Object)inEventType.getKey()));
        List reeferCue = HibernateApi.getInstance().findEntitiesByDomainQuery(dq);
        return reeferCue;
    }

    public static int createStorageChargeIfMissing(UnitFacilityVisit inUfv) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"ChargeableUnitEvent").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoExtractField.BEXU_EVENT_TYPE, (Object) ChargeableUnitEventTypeEnum.STORAGE)).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoExtractField.BEXU_UFV_GKEY, (Object)inUfv.getUfvGkey())).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoExtractField.BEXU_EQ_ID, (Object)inUfv.getUfvUnit().getUnitId()));
        List storageCharges = HibernateApi.getInstance().findEntitiesByDomainQuery(dq);
        if (storageCharges.isEmpty()) {
            dq = QueryUtils.createDomainQuery((String)"MoveEvent").addDqPredicate(PredicateFactory.eq((IMetafieldId) IServicesMovesField.MVE_UFV, (Object)inUfv.getUfvGkey())).addDqOrdering(Ordering.asc((IMetafieldId) IServicesMovesField.MVE_TIME_PUT));
            dq.setMaxResults(1);
            dq.setRequireTotalCount(false);
            MoveEvent firstEvent = (MoveEvent)HibernateApi.getInstance().getUniqueEntityByDomainQuery(dq);
            if (firstEvent != null) {
                UnitEventExtractManager.createStorageEvent(firstEvent);
                return 1;
            }
            LOGGER.info((Object)("No MoveEvent for unit " + inUfv.getUfvUnit().getUnitId() + " found - will not create a STORAGE chargeable event for this!"));
        }
        return 0;
    }

    public static int createLineStorageChargeIfMissing(UnitFacilityVisit inUfv) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"ChargeableUnitEvent").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoExtractField.BEXU_EVENT_TYPE, (Object) ChargeableUnitEventTypeEnum.LINE_STORAGE)).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoExtractField.BEXU_UFV_GKEY, (Object)inUfv.getUfvGkey())).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoExtractField.BEXU_EQ_ID, (Object)inUfv.getUfvUnit().getUnitId()));
        List storageCharges = HibernateApi.getInstance().findEntitiesByDomainQuery(dq);
        if (storageCharges.isEmpty()) {
            dq = QueryUtils.createDomainQuery((String)"MoveEvent").addDqPredicate(PredicateFactory.eq((IMetafieldId) IServicesMovesField.MVE_UFV, (Object)inUfv.getUfvGkey())).addDqOrdering(Ordering.asc((IMetafieldId) IServicesMovesField.MVE_TIME_PUT));
            dq.setMaxResults(1);
            dq.setRequireTotalCount(false);
            MoveEvent firstEvent = (MoveEvent)HibernateApi.getInstance().getUniqueEntityByDomainQuery(dq);
            if (firstEvent != null) {
                UnitEventExtractManager.createLineStorageEvent(firstEvent);
                return 1;
            }
            LOGGER.info((Object)("No MoveEvent for unit " + inUfv.getUfvUnit().getUnitId() + " found - will not create a LINE_STORAGE chargeable event for this!"));
        }
        return 0;
    }

    private static ChargeableUnitEvent createChargeableReeferStorageEvent(UnitFacilityVisit inUfv, Unit inUnit, Event inEvent, Long inBatchId, Map inFlexHashMap) {
        ChargeableUnitEvent ccv = null;
        if (inUfv != null) {
            ccv = ChargeableUnitEvent.create((Long)inUfv.getUfvGkey(), (Operator)inEvent.getEvntOperator());
 //           ccv.setBexuRestowType(inUfv.getUfvRestowType() != null ? inUfv.getUfvRestowType().getKey() : null);
            UnitEventExtractManager.setFields(inUnit, inEvent, inBatchId, inFlexHashMap, ccv);
        }
        return ccv;
    }

    public static Map getChargeableEntityUpdatesMap() {
        HashMap map = new HashMap();
        map.put(Unit.class.getSimpleName(), new HashMap());
        map.put(UnitFacilityVisit.class.getSimpleName(), new HashMap());
        map.put(GoodsBase.class.getSimpleName(), new HashMap());
        map.put(LineOperator.class.getSimpleName(), new HashMap());
        return map;
    }

    public static void addChargeableUnitEventTask(AuditEvent inEvent, IEntity inEntity, Map inMap) {
        UserContext userContext = ContextHelper.getThreadUserContext();
        Map subMap = (Map)inMap.get(inEntity.getEntityName());
        if (!(subMap == null || 1 == inEvent.getTask() && "LineOperator".equals(inEntity.getEntityName()))) {
            subMap.put(inEntity.getPrimaryKey(), inEvent);
        }
    }

    public static void updateChargeableUnits(Map inMap) {
        UserContext userContext = ContextHelper.getThreadUserContext();
        Map<String, Set<IMetafieldId>> entityFieldMap = null;
//        for (Map.Entry entry : inMap.entrySet()) {
//            String className = (String)entry.getKey();
//            Map subMap = (Map)entry.getValue();
//            if (subMap.isEmpty()) continue;
//            if (entityFieldMap == null || entityFieldMap.isEmpty()) {
//                entityFieldMap = ChargeableUnitEventUpdater.extractUpdatableFieldsFromBillingExtractMap(userContext);
//            }
//            for (Map.Entry nextEntry : subMap.entrySet()) {
//                Long nextGkey = (Long)nextEntry.getKey();
//                if (className.equals(Unit.class.getSimpleName())) {
//                    ChargeableUnitEventUpdater.updateUnitChargeableEvents(nextGkey, entityFieldMap);
//                    continue;
//                }
//                if (className.equals(UnitFacilityVisit.class.getSimpleName())) {
//                    ChargeableUnitEventUpdater.updateUfvChargeableEvents(nextGkey, entityFieldMap);
//                    continue;
//                }
//                if (className.equals(GoodsBase.class.getSimpleName())) {
//                    ChargeableUnitEventUpdater.updateGoodsChargeableEvents(nextGkey, entityFieldMap);
//                    continue;
//                }
//                if (!className.equals(LineOperator.class.getSimpleName())) continue;
//                AuditEvent nextEvent = (AuditEvent)nextEntry.getValue();
//                FieldChanges fieldChanges = nextEvent.getFieldChanges();
//                FieldChange fieldChange = fieldChanges == null ? null : fieldChanges.getFieldChange(IArgoRefField.BZU_ID);
//                Object oldId = fieldChange == null ? null : fieldChange.getPriorValue();
//                ChargeableUnitEventUpdater.updateLineOpChargeableEvents(nextGkey, (Serializable)oldId, entityFieldMap);
//            }
//        }
    }

    private static void setFields(Unit inUnit, Event inEvent, Long inBatchId, Map inFlexHashMap, ChargeableUnitEvent inCcv) {
        Facility facility = inEvent.getEvntFacility();
        Complex complex = inEvent.getEvntComplex();
        Operator operator = inEvent.getEvntOperator();
        UnitFacilityVisit ufv = inUnit.getUfvForFacilityNewest(facility);
        GoodsBase goods = (GoodsBase)inUnit.getGoods();
        inCcv.setBexuIsLocked(Boolean.valueOf(inCcv.isLocked()));
        MoveEvent moveEvent = null;
        if (inEvent instanceof MoveEvent) {
            moveEvent = (MoveEvent)inEvent;
        }
        inCcv.setBexuBatchId(inBatchId);
        inCcv.setBexuStatus(STATUS_QUEUED);
        inCcv.setBexuTerminalOperator(operator);
        inCcv.setBexuComplexId(complex.getCpxId());
        UnitEventExtractManager.setUnitFields(inCcv, inUnit, moveEvent);
        UnitEventExtractManager.setUnitRoutingFields(inCcv, inUnit.getUnitRouting());
        UnitEventExtractManager.setGoodsFields(inCcv, goods);
        UnitEquipment ue = inUnit.getUnitPrimaryUe();
        if (ufv == null && (ufv = UnitEventExtractManager.devineEventUfv(inEvent, ue.getUeUnit())) != null) {
            facility = ufv.getUfvFacility();
        }
        UnitEventExtractManager.setUfvFields(inCcv, ufv, moveEvent, true);
        inCcv.setBexuFacility(facility);
        UnitEventExtractManager.setGuaranteeFields(inCcv, ufv);
        if (InventoryConfig.BILLING_EXTRACT_USE_EVENT_TYPE_ID.isOn(ContextHelper.getThreadUserContext())) {
            inCcv.setBexuEventType(inEvent.getEventTypeId());
        } else {
            inCcv.setBexuEventType(inEvent.getEventTypeExternalId());
        }
        inCcv.setBexuSourceGkey(inEvent.getEvntGkey());
        if (facility != null) {
            inCcv.setBexuFacilityId(facility.getFcyId());
        }
        if (inEvent.getEventQuantityUnit() != null) {
            inCcv.setBexuQuantity(inEvent.getEventQuantity());
            inCcv.setBexuQuantityUnit(inEvent.getEventQuantityUnit().getKey());
        } else {
            inCcv.setBexuQuantity(new Double(1.0));
            inCcv.setBexuQuantityUnit(ServiceQuantityUnitEnum.ITEMS.getKey());
        }
        if (inEvent.getEvntResponsibleParty() != null) {
//            IArgoServiceOrderManager bean = (IArgoServiceOrderManager)Roastery.getBean((String)"serviceOrderManager");
//            String serviceOrderNbr = bean.getServiceOrderNbr((Serializable)inEvent.getEvntGkey());
//            if (null != serviceOrderNbr) {
//                inCcv.setBexuServiceOrder(serviceOrderNbr);
//            }
            inCcv.setBexuPayeeCustomerId(inEvent.getEvntResponsibleParty().getBzuId());
            inCcv.setBexuPayeeRole(inEvent.getEvntResponsibleParty().getBzuRole());
        } else {
            inCcv.setBexuPayeeCustomerId(null);
            inCcv.setBexuPayeeRole(null);
        }
        if (moveEvent != null && inEvent instanceof MoveEvent) {
            LocPosition mveToPosition;
            MoveEvent mveEvent = (MoveEvent)inEvent;
            ScopedBizUnit mveLineSnapshot = mveEvent.getMveLineSnapshot();
            if (mveLineSnapshot != null) {
                inCcv.setBexuLineOperatorId(mveLineSnapshot.getBzuId());
            } else {
                LOGGER.warn((Object)"Move event does not have a SnapShot Line Operator defined!");
            }
            inCcv.setBexuEventStartTime(mveEvent.getEvntAppliedDate());
            inCcv.setBexuEventEndTime(mveEvent.getEvntAppliedDate());
            Che mveCheQuayCrane = mveEvent.getMveCheQuayCrane();
            if (mveCheQuayCrane != null) {
                inCcv.setBexuQuayCheId(mveCheQuayCrane.getCheShortName());
            }
            CarrierVisit ufvActualIbCv = null;
            if (mveEvent != null && mveEvent.getMveUfv() != null && mveEvent.getMveUfv().getUfvActualIbCv() != null) {
                ufvActualIbCv = mveEvent.getMveUfv().getUfvActualIbCv().getInboundCv();
            }
            UnitEventExtractManager.setInBoundDetails(ufvActualIbCv, inCcv);
            CarrierVisit ufvIntendedIbCv = null;
            if (mveEvent != null && mveEvent.getMveUfv() != null && mveEvent.getMveUfv().getUfvUnit() != null && mveEvent.getMveUfv().getUfvUnit().getUnitDeclaredIbCv() != null) {
                ufvIntendedIbCv = mveEvent.getMveUfv().getUfvUnit().getUnitDeclaredIbCv().getInboundCv();
            }
            UnitEventExtractManager.setIntendedInBoundDetails(ufvIntendedIbCv, inCcv);
            CarrierVisit mveCarrier = mveEvent.getMveCarrier();
            if (mveCarrier != null && mveCarrier.equals((Object)ufvActualIbCv)) {
                inCcv.setBexuIbCarrierATA(mveCarrier.getCvATA());
                inCcv.setBexuIbCarrierATD(mveCarrier.getCvATD());
            }
            CarrierVisit ufvActualObCv = mveEvent.getMveUfv().getUfvActualObCv().getOutboundCv();
            UnitEventExtractManager.setOutBoundDetails(ufvActualObCv, inCcv);
            CarrierVisit ufvIntendedObCv = mveEvent.getMveUfv().getUfvIntendedObCv().getOutboundCv();
            UnitEventExtractManager.setIntendedOutBoundDetails(ufvIntendedObCv, inCcv);
            LocPosition mveFromPosition = mveEvent.getMveFromPosition();
            if (mveFromPosition != null) {
                inCcv.setBexuFmPosLocType(mveFromPosition.getPosLocType().getKey());
                inCcv.setBexuFmPosLocId(mveFromPosition.getPosLocId());
                inCcv.setBexuFmPositionName(mveFromPosition.getPosName());
                inCcv.setBexuFmPositionSlot(mveFromPosition.getPosSlot());
            }
            if ((mveToPosition = mveEvent.getMveToPosition()) != null) {
                inCcv.setBexuToPosLocType(mveToPosition.getPosLocType().getKey());
                inCcv.setBexuToPosLocId(mveToPosition.getPosLocId());
                inCcv.setBexuToPositionName(mveToPosition.getPosName());
                inCcv.setBexuToPositionSlot(mveToPosition.getPosSlot());
            }
            inCcv.setBexuServiceOrder(mveEvent.getMveServiceOrder());
            inCcv.setBexuRestowReason(mveEvent.getMveRestowReason());
            inCcv.setBexuRestowAccount(mveEvent.getMveRestowAccount());
            inCcv.setBexuRehandleCount(mveEvent.getMveRehandleCount());
        } else {
            inCcv.setBexuEventStartTime(inEvent.getEvntAppliedDate());
            inCcv.setBexuEventEndTime(inEvent.getEvntAppliedDate());
            if (ufv != null && ufv.getUfvActualObCv() != null) {
                CarrierVisit ufvActualObCv = ufv.getUfvActualObCv().getOutboundCv();
                UnitEventExtractManager.setOutBoundDetails(ufvActualObCv, inCcv);
            }
        }
        if (!FreightKindEnum.BBK.equals((Object)inUnit.getUnitFreightKind())) {
            if (ue.getUeDepartureOrderItem() != null) {
                inCcv.setBexuBookingNbr(ue.getUeDepartureOrderItem().getEqboiOrder().getEqboNbr());
            }
            UnitEventExtractManager.setUnitEquipmentFields(inCcv, ue);
        } else {
            inCcv.setBexuEqId(inUnit.getUnitId());
        }
        inCcv.setBexuNotes(inEvent.getEventNote());
        UnitEventExtractManager.setUserFlexFieldValues(ufv, inFlexHashMap, inCcv);
    }

    private static void setGuaranteeFields(ChargeableUnitEvent inCcv, UnitFacilityVisit inUfv) {
        if (ChargeableUnitEventTypeEnum.STORAGE.getKey().equals(inCcv.getBexuEventType())) {
            if (inUfv.getUfvGuaranteeParty() != null) {
                inCcv.setBexuGuaranteeParty(inUfv.getUfvGuaranteeParty().getBzuId());
            }
            inCcv.setBexuGuaranteeThruDay(inUfv.getUfvGuaranteeThruDay());
        } else if (ChargeableUnitEventTypeEnum.LINE_STORAGE.getKey().equals(inCcv.getBexuEventType())) {
            if (inUfv.getUfvLineGuaranteeParty() != null) {
                inCcv.setBexuGuaranteeParty(inUfv.getUfvLineGuaranteeParty().getBzuId());
            }
            inCcv.setBexuGuaranteeThruDay(inUfv.getUfvLineGuaranteeThruDay());
        } else if (ChargeableUnitEventTypeEnum.REEFER.getKey().equals(inCcv.getBexuEventType())) {
            if (inUfv.getUfvPowerGuaranteeParty() != null) {
                inCcv.setBexuGuaranteeParty(inUfv.getUfvPowerGuaranteeParty().getBzuId());
            }
            inCcv.setBexuGuaranteeThruDay(inUfv.getUfvPowerGuaranteeThruDay());
        }
    }

    private static void setIntendedOutBoundDetails(CarrierVisit inUfvIntendedObCv, ChargeableUnitEvent inCcv) {
        boolean setAllFieldsNull = false;
        if (inUfvIntendedObCv != null) {
            String carrierMode = inUfvIntendedObCv.getCvCarrierMode().getKey();
            inCcv.setBexuObintendedLocType(carrierMode);
            LocTypeEnum locTypeEnum = inUfvIntendedObCv.getCvCarrierMode();
            inCcv.setBexuObintendedLocType(locTypeEnum.getKey());
            if (!LocTypeEnum.TRUCK.equals((Object)locTypeEnum)) {
                inCcv.setBexuObintendedId(inUfvIntendedObCv.getCvId());
                VisitDetails cvCvd = inUfvIntendedObCv.getCvCvd();
                if (cvCvd != null) {
                    if (locTypeEnum.equals((Object) LocTypeEnum.VESSEL)) {
                        inCcv.setBexuObintendedVesselClassId(cvCvd.getCarrierClassId());
                        String serviceId = cvCvd.getCvdService() != null ? cvCvd.getCvdService().getSrvcId() : null;
                        inCcv.setBexuObintendedServiceId(serviceId);
                        inCcv.setBexuObintendedVesselType(cvCvd.getCarrierTypeId());
                    } else {
                        inCcv.setBexuObintendedVesselClassId(null);
                        inCcv.setBexuObintendedServiceId(null);
                        inCcv.setBexuObintendedVesselType(null);
                    }
                    inCcv.setBexuObintendedCarrierETA(cvCvd.getCvdETA());
                    if (cvCvd.getOutboundCv() != null) {
                        inCcv.setBexuObintendedCarrierATA(cvCvd.getOutboundCv().getCvATA());
                    } else {
                        inCcv.setBexuObintendedCarrierATA(null);
                    }
                    if (cvCvd.getOutboundCv() != null) {
                        inCcv.setBexuObintendedCarrierATD(cvCvd.getOutboundCv().getCvATD());
                    } else {
                        inCcv.setBexuObintendedCarrierATD(null);
                    }
                    inCcv.setBexuObintendedCarrierName(cvCvd.getCarrierVehicleName());
                    inCcv.setBexuObintendedCallNbr(cvCvd.getCarrierObVisitCallNbr());
                    inCcv.setBexuObintendedVisitId(cvCvd.getCarrierObVoyNbrOrTrainId());
                    ScopedBizUnit carrierOperator = cvCvd.getCarrierOperator();
                    String operatorId = carrierOperator != null ? carrierOperator.getBzuId() : null;
                    inCcv.setBexuObintendedCarrierLineId(operatorId);
                } else {
                    setAllFieldsNull = true;
                }
            } else {
                setAllFieldsNull = true;
            }
            if (setAllFieldsNull) {
                inCcv.setBexuObintendedId(null);
                inCcv.setBexuObintendedVesselClassId(null);
                inCcv.setBexuObintendedServiceId(null);
                inCcv.setBexuObintendedVesselType(null);
                inCcv.setBexuObintendedCarrierETA(null);
                inCcv.setBexuObintendedCarrierATA(null);
                inCcv.setBexuObintendedCarrierATD(null);
                inCcv.setBexuObintendedCarrierName(null);
                inCcv.setBexuObintendedCallNbr(null);
                inCcv.setBexuObintendedVisitId(null);
                inCcv.setBexuObintendedCarrierLineId(null);
            }
        }
    }

    private static void setIntendedInBoundDetails(CarrierVisit inUfvIntendedIbCv, ChargeableUnitEvent inCcv) {
        boolean setAllToNull = false;
        if (inUfvIntendedIbCv != null) {
            LocTypeEnum locTypeEnum = inUfvIntendedIbCv.getCvCarrierMode();
            inCcv.setBexuIbintendedLocType(locTypeEnum.getKey());
            if (!LocTypeEnum.TRUCK.equals((Object)locTypeEnum)) {
                inCcv.setBexuIbintendedId(inUfvIntendedIbCv.getCvId());
                VisitDetails cvCvd = inUfvIntendedIbCv.getCvCvd();
                if (cvCvd != null) {
                    if (locTypeEnum.equals((Object) LocTypeEnum.VESSEL)) {
                        inCcv.setBexuIbintendedVesselClassId(cvCvd.getCarrierClassId());
                        String serviceId = cvCvd.getCvdService() != null ? cvCvd.getCvdService().getSrvcId() : null;
                        inCcv.setBexuIbintendedServiceId(serviceId);
                        inCcv.setBexuIbintendedVesselType(cvCvd.getCarrierTypeId());
                    } else {
                        inCcv.setBexuIbintendedVesselClassId(null);
                        inCcv.setBexuIbintendedServiceId(null);
                        inCcv.setBexuIbintendedVesselType(null);
                    }
                    inCcv.setBexuIbintendedCarrierETA(cvCvd.getCvdETA());
                    inCcv.setBexuIbintendedCarrierName(cvCvd.getCarrierVehicleName());
                    inCcv.setBexuIbintendedCallNbr(cvCvd.getCarrierIbVisitCallNbr());
                    inCcv.setBexuIbintendedVisitId(cvCvd.getCarrierIbVoyNbrOrTrainId());
                    ScopedBizUnit carrierOperator = cvCvd.getCarrierOperator();
                    String operatorId = carrierOperator != null ? carrierOperator.getBzuId() : null;
                    inCcv.setBexuIbintendedCarrierLineId(operatorId);
                    inCcv.setBexuFirstAvailability(cvCvd.getCvdTimeFirstAvailability());
                    inCcv.setBexuTimeDischargeComplete(cvCvd.getCvdTimeDischargeComplete());
                    inCcv.setBexuTimeFirstFreeDay(cvCvd.getCvdInboundFirstFreeDay());
                } else {
                    setAllToNull = true;
                }
            } else {
                setAllToNull = true;
            }
            if (setAllToNull) {
                inCcv.setBexuIbintendedId(null);
                inCcv.setBexuIbintendedVesselClassId(null);
                inCcv.setBexuIbintendedServiceId(null);
                inCcv.setBexuIbintendedVesselType(null);
                inCcv.setBexuIbintendedCarrierETA(null);
                inCcv.setBexuIbintendedCarrierName(null);
                inCcv.setBexuIbintendedCallNbr(null);
                inCcv.setBexuIbintendedVisitId(null);
                inCcv.setBexuIbintendedCarrierLineId(null);
                inCcv.setBexuFirstAvailability(null);
                inCcv.setBexuTimeDischargeComplete(null);
                inCcv.setBexuTimeFirstFreeDay(null);
            }
        }
    }

    private static void setUnitEquipmentFields(ChargeableUnitEvent inCcv, UnitEquipment inUe) {
        if (inCcv == null) {
            throw BizFailure.create((String)"inCcv cannot be null");
        }
        if (inUe == null) {
            throw BizFailure.create((String)"inUe cannot be null");
        }
        Equipment equip = inUe.getUeEquipment();
        inCcv.setBexuEqId(equip.getEqIdFull());
        EquipType eqEquipType = equip.getEqEquipType();
        if (eqEquipType != null) {
            inCcv.setBexuIsoCode(eqEquipType.getEqtypId());
            inCcv.setBexuIsoGroup(eqEquipType.getEqtypIsoGroup().getKey());
            inCcv.setBexuEqSubClass(eqEquipType.getEqtypClass().getKey());
            inCcv.setBexuIsoLength(eqEquipType.getEqtypNominalLength().getKey());
            inCcv.setBexuIsoHeight(eqEquipType.getEqtypNominalHeight().getKey());
        } else {
            inCcv.setBexuIsoCode(null);
            inCcv.setBexuIsoGroup(null);
            inCcv.setBexuEqSubClass(null);
            inCcv.setBexuIsoLength(null);
            inCcv.setBexuIsoHeight(null);
        }
    }

    private static void setUnitRoutingFields(ChargeableUnitEvent inCcv, Routing inUnitRouting) {
        if (inUnitRouting != null) {
            RoutingPoint rtgPOD1 = inUnitRouting.getRtgPOD1();
            if (rtgPOD1 != null) {
                inCcv.setBexuPod1(rtgPOD1.getPointId());
            } else {
                inCcv.setBexuPod1(null);
            }
            RoutingPoint rtgPOL = inUnitRouting.getRtgPOL();
            if (rtgPOL != null) {
                inCcv.setBexuPol1(rtgPOL.getPointId());
            } else {
                inCcv.setBexuPol1(null);
            }
            RoutingPoint rtgOPL = inUnitRouting.getRtgOPL();
            if (rtgOPL != null) {
                inCcv.setBexuOpl(rtgOPL.getPointId());
            } else {
                inCcv.setBexuOpl(null);
            }
        }
    }

    private static void setGoodsFields(ChargeableUnitEvent inCcv, GoodsBase inGoods) {
        inCcv.setBexuFinalDestination(inGoods.getGdsDestination());
        if (inGoods.getGdsReeferRqmnts() != null) {
            inCcv.setBexuTempRequiredC(inGoods.getGdsReeferRqmnts().getRfreqTempRequiredC());
        } else {
            inCcv.setBexuTempRequiredC(null);
        }
        if (inGoods.getGdsConsigneeBzu() != null) {
            inCcv.setBexuConsigneeId(inGoods.getGdsConsigneeBzu().getBzuId());
        } else {
            inCcv.setBexuConsigneeId(null);
        }
        if (inGoods.getGdsShipperBzu() != null) {
            inCcv.setBexuShipperId(inGoods.getGdsShipperBzu().getBzuId());
        } else {
            inCcv.setBexuShipperId(null);
        }
        UnitEventExtractManager.setHazardDetails(inGoods, inCcv);
        inCcv.setBexuBlNbr(inGoods.getGdsBlNbr());
        if (inGoods.getGdsCommodity() != null) {
            inCcv.setBexuCommodityId(inGoods.getGdsCommodity().getCmdyId());
        } else {
            inCcv.setBexuCommodityId(null);
        }
    }

    private static void setUfvFields(ChargeableUnitEvent inCcv, UnitFacilityVisit inUfv, MoveEvent inMoveEvent, boolean inIsCreate) {
        if (inCcv.getBexuUfvTimeIn() == null) {
            inCcv.setBexuUfvTimeIn(inUfv.getUfvTimeIn());
        }
        if (inIsCreate || ChargeableUnitEventTypeEnum.STORAGE.getKey().equals(inCcv.getBexuEventType())) {
            if (inCcv.getBexuUfvTimeOut() == null) {
                inCcv.setBexuUfvTimeOut(inUfv.getUfvTimeOut());
            }
            inCcv.setBexuGuaranteeThruDay(inUfv.getUfvGuaranteeThruDay());
            if (inUfv.getUfvGuaranteeParty() != null) {
                inCcv.setBexuGuaranteeParty(inUfv.getUfvGuaranteeParty().getBzuId());
            } else {
                inCcv.setBexuGuaranteeParty(null);
            }
        }
        if (inIsCreate || ChargeableUnitEventTypeEnum.LINE_STORAGE.getKey().equals(inCcv.getBexuEventType())) {
            if (inCcv.getBexuUfvTimeOut() == null) {
                inCcv.setBexuUfvTimeOut(inUfv.getUfvTimeOut());
            }
            inCcv.setBexuGuaranteeThruDay(inUfv.getUfvLineGuaranteeThruDay());
            if (inUfv.getUfvLineGuaranteeParty() != null) {
                inCcv.setBexuGuaranteeParty(inUfv.getUfvLineGuaranteeParty().getBzuId());
            } else {
                inCcv.setBexuGuaranteeParty(null);
            }
        }
        if (ChargeableUnitEventTypeEnum.REEFER.getKey().equals(inCcv.getBexuEventType())) {
            inCcv.setBexuGuaranteeThruDay(inUfv.getUfvPowerGuaranteeThruDay());
            if (inUfv.getUfvPowerGuaranteeParty() != null) {
                inCcv.setBexuGuaranteeParty(inUfv.getUfvPowerGuaranteeParty().getBzuId());
            } else {
                inCcv.setBexuGuaranteeParty(null);
            }
            inCcv.setBexuUfvTimeOut(inUfv.getUfvTimeComplete());
            inCcv.setBexuEventEndTime(inUfv.getUfvTimeComplete());
            CarrierVisit outboundCv = inUfv.getUfvActualObCv();
            String obCvId = null;
            if (outboundCv != null) {
                obCvId = outboundCv.getCvId();
            } else {
                LOGGER.error((Object)("updateReeferEventForDepartingUfv: no outbound carrier(?) for UFV " + inUfv));
            }
            inCcv.setBexuObId(obCvId);
        }
        inCcv.setBexuTimeOfLoading(inUfv.getUfvTimeOfLoading());
//        inCcv.setBexuRestowReason(inUfv.getUfvHandlingReason() != null ? inUfv.getUfvHandlingReason().getKey() : null);
//        if (inMoveEvent == null) {
//            inCcv.setBexuRestowType(inUfv.getUfvRestowType().getKey());
//            CarrierVisit ufvActualIbCv = inUfv.getUfvActualIbCv().getInboundCv();
//            UnitEventExtractManager.setInBoundDetails(ufvActualIbCv, inCcv);
//            CarrierVisit ufvIntendedIbCv = inUfv.getUfvUnit().getUnitDeclaredIbCv().getInboundCv();
//            UnitEventExtractManager.setIntendedInBoundDetails(ufvIntendedIbCv, inCcv);
//        }
    }

    private static void setUnitFields(ChargeableUnitEvent inCcv, Unit inUnit, MoveEvent inMoveEvent) {
        ScopedBizUnit unitLineOperator;
        inCcv.setBexuUnitGkey((Long)inUnit.getPrimaryKey());
        inCcv.setBexuCategory(inUnit.getUnitCategory().getKey());
        inCcv.setBexuVerifiedGrossMass(inUnit.getUnitGoodsAndCtrWtKgVerfiedGross());
        inCcv.setBexuVgmVerifierEntity(inUnit.getUnitVgmEntity());
        inCcv.setBexuFreightKind(inUnit.getUnitFreightKind().getKey());
        DrayStatusEnum unitDrayStatus = inUnit.getUnitDrayStatus();
        inCcv.setBexuCargoQuantity(inUnit.getUnitCargoQuantity());
        if (inUnit.getUnitCargoQuantityUnit() != null) {
            inCcv.setBexuCargoQuantityUnit(inUnit.getUnitCargoQuantityUnit().getKey());
        }
        if (unitDrayStatus != null) {
            inCcv.setBexuDrayStatus(unitDrayStatus.getKey());
        } else {
            inCcv.setBexuDrayStatus(null);
        }
        inCcv.setBexuIsOog(inUnit.getUnitIsOog());
        inCcv.setBexuIsRefrigerated(Boolean.valueOf(inUnit.isReefer()));
        Boolean unitBundled = inUnit.isUnitBundled();
        inCcv.setBexuIsBundle(unitBundled);
        inCcv.setBexuIsHazardous(inUnit.getUnitIsHazard());
        if (inMoveEvent == null && (unitLineOperator = inUnit.getUnitLineOperator()) != null) {
            inCcv.setBexuLineOperatorId(unitLineOperator.getBzuId());
        }
        if (inUnit.getUnitPrimaryUe() != null) {
            Equipment equipment = inUnit.getUnitPrimaryUe().getUeEquipment();
            inCcv.setBexuEqOwnerId(equipment.getEquipmentOwnerId());
        }
        if (inUnit.getUnitSpecialStow() != null) {
            inCcv.setBexuSpecialStow(inUnit.getUnitSpecialStow().getStwId());
        }
        if (inUnit.getUnitEqRole() == EqUnitRoleEnum.PAYLOAD) {
            Unit masterUnit = inUnit.getUnitRelatedUnit();
            inCcv.setBexuBundleUnitId(masterUnit.getUnitId());
            if (masterUnit.getUnitActiveUfv() != null) {
                inCcv.setBexuBundleUfvGkey(masterUnit.getUnitActiveUfv().getUfvGkey());
            }
        }
        String cueRole = inUnit.getUnitEqRole() == null ? null : inUnit.getUnitEqRole().getKey();
        inCcv.setBexuEqRole(cueRole);
    }

    private static void setOutBoundDetails(CarrierVisit inUfvActualObCv, ChargeableUnitEvent inOutCcv) {
        inOutCcv.setBexuObVesselClassId(null);
        inOutCcv.setBexuObServiceId(null);
        inOutCcv.setBexuObVesselType(null);
        inOutCcv.setBexuObCarrierETA(null);
        inOutCcv.setBexuObCarrierATA(null);
        inOutCcv.setBexuObCarrierATD(null);
        inOutCcv.setBexuObCarrierName(null);
        inOutCcv.setBexuObCallNbr(null);
        inOutCcv.setBexuObVisitId(null);
        inOutCcv.setBexuObCarrierLineId(null);
        if (inUfvActualObCv != null) {
            String carrierMode = inUfvActualObCv.getCvCarrierMode().getKey();
            inOutCcv.setBexuObLocType(carrierMode);
            LocTypeEnum locTypeEnum = inUfvActualObCv.getCvCarrierMode();
            inOutCcv.setBexuObLocType(locTypeEnum.getKey());
            inOutCcv.setBexuObId(inUfvActualObCv.getCvId());
            VisitDetails cvCvd = inUfvActualObCv.getCvCvd();
            if (cvCvd != null) {
                ScopedBizUnit carrierOperator = cvCvd.getCarrierOperator();
                String operatorId = carrierOperator != null ? carrierOperator.getBzuId() : null;
                inOutCcv.setBexuObCarrierLineId(operatorId);
                inOutCcv.setBexuObCarrierName(cvCvd.getCarrierVehicleName());
                if (!LocTypeEnum.TRUCK.equals((Object)locTypeEnum)) {
                    inOutCcv.setBexuObVisitId(cvCvd.getCarrierObVoyNbrOrTrainId());
                    if (locTypeEnum.equals((Object) LocTypeEnum.VESSEL)) {
                        inOutCcv.setBexuObVesselClassId(cvCvd.getCarrierClassId());
                        String serviceId = cvCvd.getCvdService() != null ? cvCvd.getCvdService().getSrvcId() : null;
                        inOutCcv.setBexuObServiceId(serviceId);
                        inOutCcv.setBexuObVesselType(cvCvd.getCarrierTypeId());
                        inOutCcv.setBexuObVesselLloydsId(cvCvd.getLloydsId());
                    }
                    inOutCcv.setBexuObCarrierETA(cvCvd.getCvdETA());
                    if (cvCvd.getOutboundCv() != null) {
                        inOutCcv.setBexuObCarrierATA(cvCvd.getOutboundCv().getCvATA());
                    }
                    if (cvCvd.getOutboundCv() != null) {
                        inOutCcv.setBexuObCarrierATD(cvCvd.getOutboundCv().getCvATD());
                    }
                    inOutCcv.setBexuObCallNbr(cvCvd.getCarrierObVisitCallNbr());
                } else {
                    inOutCcv.setBexuObVisitId(inUfvActualObCv.getCvId());
                }
            }
        }
    }

    private static void setInBoundDetails(CarrierVisit inUfvActualIbCv, ChargeableUnitEvent inOutCcv) {
        inOutCcv.setBexuIbId(null);
        inOutCcv.setBexuIbVesselClassId(null);
        inOutCcv.setBexuIbServiceId(null);
        inOutCcv.setBexuIbVesselType(null);
        inOutCcv.setBexuIbCarrierETA(null);
        inOutCcv.setBexuIbCarrierName(null);
        inOutCcv.setBexuIbCallNbr(null);
        inOutCcv.setBexuIbVisitId(null);
        inOutCcv.setBexuIbCarrierLineId(null);
        inOutCcv.setBexuFirstAvailability(null);
        inOutCcv.setBexuTimeDischargeComplete(null);
        inOutCcv.setBexuTimeFirstFreeDay(null);
        if (inUfvActualIbCv != null) {
            LocTypeEnum locTypeEnum = inUfvActualIbCv.getCvCarrierMode();
            inOutCcv.setBexuIbLocType(locTypeEnum.getKey());
            VisitDetails cvCvd = inUfvActualIbCv.getCvCvd();
            if (cvCvd != null) {
                ScopedBizUnit carrierOperator = cvCvd.getCarrierOperator();
                String operatorId = carrierOperator != null ? carrierOperator.getBzuId() : null;
                inOutCcv.setBexuIbCarrierLineId(operatorId);
                inOutCcv.setBexuIbCarrierName(cvCvd.getCarrierVehicleName());
                inOutCcv.setBexuIbId(inUfvActualIbCv.getCvId());
                if (!LocTypeEnum.TRUCK.equals((Object)locTypeEnum)) {
                    inOutCcv.setBexuIbVisitId(cvCvd.getCarrierIbVoyNbrOrTrainId());
                    if (locTypeEnum.equals((Object) LocTypeEnum.VESSEL)) {
                        inOutCcv.setBexuIbVesselClassId(cvCvd.getCarrierClassId());
                        String serviceId = cvCvd.getCvdService() != null ? cvCvd.getCvdService().getSrvcId() : null;
                        inOutCcv.setBexuIbServiceId(serviceId);
                        inOutCcv.setBexuIbVesselType(cvCvd.getCarrierTypeId());
                        inOutCcv.setBexuIbVesselLloydsId(cvCvd.getLloydsId());
                    }
                    inOutCcv.setBexuIbCarrierETA(cvCvd.getCvdETA());
                    inOutCcv.setBexuIbCarrierATA(inUfvActualIbCv.getCvATA());
                    inOutCcv.setBexuIbCarrierATD(inUfvActualIbCv.getCvATD());
                    inOutCcv.setBexuIbCallNbr(cvCvd.getCarrierIbVisitCallNbr());
                    inOutCcv.setBexuFirstAvailability(cvCvd.getCvdTimeFirstAvailability());
                    inOutCcv.setBexuTimeDischargeComplete(cvCvd.getCvdTimeDischargeComplete());
                    inOutCcv.setBexuTimeFirstFreeDay(cvCvd.getCvdInboundFirstFreeDay());
                } else {
                    inOutCcv.setBexuIbVisitId(inUfvActualIbCv.getCvId());
                }
            }
        }
    }

    private static void setHazardDetails(GoodsBase inGoods, ChargeableUnitEvent inCcv) {
        String hazClass = inGoods.getGoodsMostSevereHazardClass();
        if (hazClass != null) {
            inCcv.setBexuImdgClass(hazClass);
        } else {
            inCcv.setBexuImdgClass(null);
        }
        if (inGoods.getGdsHazards() != null && inGoods.getGdsHazards().getHzrdWorstFireCode() != null) {
            inCcv.setBexuFireCode(inGoods.getGdsHazards().getHzrdWorstFireCode().getFirecodeFireCode());
            inCcv.setBexuFireCodeClass(inGoods.getGdsHazards().getHzrdWorstFireCode().getFirecodeFireCodeClass());
        } else {
            inCcv.setBexuFireCode(null);
            inCcv.setBexuFireCodeClass(null);
        }
        inCcv.setBexuIsHazardous(inGoods.getGdsUnit().getUnitIsHazard());
    }

    protected static void setUserFlexFieldValues(UnitFacilityVisit inUfv, Map<String, String> inFlexHashMap, ChargeableUnitEvent inCcv) {
        if (inFlexHashMap == null || inFlexHashMap.isEmpty()) {
            LOGGER.info((Object)"Flex fields are not mapped and is empty...");
            return;
        }
//        ReportManager rm = (ReportManager)Roastery.getBean((String)"bizReportManager");
//        IReportableEntity rp = rm.getReportableEntity("Unit");
//        Map ufvUnitRepFieldMap = rp.getAllReportableFields();
//        Set<String> flexMapKeys = inFlexHashMap.keySet();
//        for (String key : flexMapKeys) {
//            IReportableField reportableField = (IReportableField)ufvUnitRepFieldMap.get(inFlexHashMap.get(key));
//            if (reportableField == null) {
//                throw BizFailure.create((IPropertyKey)IArgoPropertyKeys.REPORT_INVALID_FIELD, null, (Object)rp.getDisplayName(), (Object)key);
//            }
//            IMetafieldId ufvUnitMetafield = reportableField.getMetafieldId();
//            IMetafieldId cueField = MetafieldIdFactory.valueOf((String)key);
//            inCcv.setFieldValue(cueField, inUfv.getFieldValue(ufvUnitMetafield));
//        }
    }

    public static List findExistingRecord(Long inEventGkey) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"ChargeableUnitEvent").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoExtractField.BEXU_SOURCE_GKEY, (Object)inEventGkey));
        return HibernateApi.getInstance().findEntitiesByDomainQuery(dq);
    }

    public static List findExistingQueuedEvents(Long inEventGkey) {
        Object[] excludedEventTypes = new String[]{ChargeableUnitEventTypeEnum.STORAGE.getKey(), ChargeableUnitEventTypeEnum.REEFER.getKey(), ChargeableUnitEventTypeEnum.LINE_STORAGE.getKey()};
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"ChargeableUnitEvent").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoExtractField.BEXU_SOURCE_GKEY, (Object)inEventGkey)).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoExtractField.BEXU_STATUS, (Object)STATUS_QUEUED)).addDqPredicate(PredicateFactory.not((IPredicate) PredicateFactory.in((IMetafieldId) IArgoExtractField.BEXU_EVENT_TYPE, (Object[])excludedEventTypes)));
        dq.setScopingEnabled(Boolean.FALSE.booleanValue());
        return HibernateApi.getInstance().findEntitiesByDomainQuery(dq);
    }

    public static List findChargeableEventsForUfvAndStatuses(Long inUfvGkey, String[] inStatuses) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"ChargeableUnitEvent").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoExtractField.BEXU_UFV_GKEY, (Object)inUfvGkey));
        if (inStatuses != null && inStatuses.length > 0) {
            dq.addDqPredicate(PredicateFactory.in((IMetafieldId) IArgoExtractField.BEXU_STATUS, (Object[])inStatuses));
        }
        return HibernateApi.getInstance().findEntitiesByDomainQuery(dq);
    }

    @Nullable
    public static ChargeableUnitEvent findExistingChargeableUnitStorageEvent(Facility inFcy, UnitFacilityVisit inUfv, ChargeableUnitEventTypeEnum inEventType) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"ChargeableUnitEvent").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoExtractField.BEXU_UFV_GKEY, (Object)inUfv.getPrimaryKey())).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoExtractField.BEXU_FACILITY, (Object)inFcy.getPrimaryKey())).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoExtractField.BEXU_EVENT_TYPE, (Object)inEventType.getKey()));
        ChargeableUnitEvent cue = (ChargeableUnitEvent)HibernateApi.getInstance().getUniqueEntityByDomainQuery(dq);
        return cue;
    }

    @Nullable
    public static ChargeableUnitEvent findExistingChargeableUnitStorageEventEndTimeIsNull(Facility inFcy, UnitFacilityVisit inUfv, ChargeableUnitEventTypeEnum inEventType) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"ChargeableUnitEvent").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoExtractField.BEXU_UFV_GKEY, (Object)inUfv.getPrimaryKey())).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoExtractField.BEXU_FACILITY, (Object)inFcy.getPrimaryKey())).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoExtractField.BEXU_EVENT_TYPE, (Object)inEventType.getKey())).addDqPredicate(PredicateFactory.isNull((IMetafieldId) IArgoExtractField.BEXU_EVENT_END_TIME));
        ChargeableUnitEvent cue = (ChargeableUnitEvent)HibernateApi.getInstance().getUniqueEntityByDomainQuery(dq);
        return cue;
    }

    @Nullable
    public static ChargeableUnitEvent findExistingChargeableUnitStorageEventEndTimeIsNotNull(Facility inFcy, UnitFacilityVisit inUfv, ChargeableUnitEventTypeEnum inEventType) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"ChargeableUnitEvent").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoExtractField.BEXU_UFV_GKEY, (Object)inUfv.getPrimaryKey())).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoExtractField.BEXU_FACILITY, (Object)inFcy.getPrimaryKey())).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoExtractField.BEXU_EVENT_TYPE, (Object)inEventType.getKey())).addDqPredicate(PredicateFactory.isNotNull((IMetafieldId) IArgoExtractField.BEXU_EVENT_END_TIME));
        ChargeableUnitEvent cue = (ChargeableUnitEvent)HibernateApi.getInstance().getUniqueEntityByDomainQuery(dq);
        return cue;
    }

    @Nullable
    private static List<ChargeableUnitEvent> findExistingChargeableUnitEventsTimeOutIsNull(UnitFacilityVisit inUfv) {
        Object[] eventTypes = new ChargeableUnitEventTypeEnum[]{ChargeableUnitEventTypeEnum.STORAGE, ChargeableUnitEventTypeEnum.LINE_STORAGE, ChargeableUnitEventTypeEnum.REEFER};
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"ChargeableUnitEvent").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoExtractField.BEXU_UFV_GKEY, (Object)inUfv.getPrimaryKey())).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoExtractField.BEXU_FACILITY, (Object)inUfv.getUfvFacility().getPrimaryKey())).addDqPredicate(PredicateFactory.not((IPredicate) PredicateFactory.in((IMetafieldId) IArgoExtractField.BEXU_EVENT_TYPE, (Object[])eventTypes))).addDqPredicate(PredicateFactory.isNull((IMetafieldId) IArgoExtractField.BEXU_UFV_TIME_OUT));
        List cueList = HibernateApi.getInstance().findEntitiesByDomainQuery(dq);
        return cueList;
    }

    @Nullable
    public static ChargeableUnitEvent findExistingLatestChargeableUnitStorageEventEndTimeIsNull(Facility inFcy, UnitFacilityVisit inUfv, ChargeableUnitEventTypeEnum inEventType) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"ChargeableUnitEvent").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoExtractField.BEXU_UFV_GKEY, (Object)inUfv.getPrimaryKey())).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoExtractField.BEXU_FACILITY, (Object)inFcy.getPrimaryKey())).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoExtractField.BEXU_EVENT_TYPE, (Object)inEventType.getKey())).addDqPredicate(PredicateFactory.isNull((IMetafieldId) IArgoExtractField.BEXU_EVENT_END_TIME)).addDqOrdering(Ordering.desc((IMetafieldId) IArgoExtractField.BEXU_GKEY));
        List cue = HibernateApi.getInstance().findEntitiesByDomainQuery(dq);
        if (!cue.isEmpty()) {
            return (ChargeableUnitEvent)cue.get(0);
        }
        return null;
    }

    @Nullable
    public static ChargeableUnitEvent findExistingChargeableUnitReeferEvent(Facility inFcy, Equipment inEq, ChargeableUnitEventTypeEnum inEventType, Unit inUnit) {
        UnitFacilityVisit activeUfv = inUnit.getUnitActiveUfv();
        if (activeUfv == null) {
            LOGGER.error((Object)("findExistingChargeableUnitReeferEvent: expected to find an active ufv of  Unit " + inUnit));
            return null;
        }
        return UnitEventExtractManager.findExistingChargeableUnitReeferEvent(inFcy, inEventType, activeUfv);
    }

    public static ChargeableUnitEvent findExistingChargeableUnitReeferEvent(Facility inFcy, ChargeableUnitEventTypeEnum inEventType, UnitFacilityVisit inUfv) {
        List results = UnitEventExtractManager.getCueForReeferEvent(inFcy, inUfv, inEventType);
        ChargeableUnitEvent cue = null;
        if (results.size() > 1) {
            ChargeableUnitEvent cce;
            int i;
            for (i = 0; i < results.size(); ++i) {
                cce = (ChargeableUnitEvent)results.get(i);
                if (cce.getBexuPaidThruDay() == null) continue;
                cue = (ChargeableUnitEvent)results.get(i);
                results.remove(i);
            }
            if (cue == null) {
                cue = (ChargeableUnitEvent)results.get(0);
                results.remove(0);
            }
            for (i = 0; i < results.size(); ++i) {
                cce = (ChargeableUnitEvent)results.get(i);
                HibernateApi.getInstance().delete((Object)cce);
            }
        } else {
            cue = !results.isEmpty() ? (ChargeableUnitEvent)results.get(0) : null;
        }
        return cue;
    }

    public static List getExtractedRecordListByUnitFacilityGkey(Long inUfvGkey) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"ChargeableUnitEvent").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoExtractField.BEXU_UFV_GKEY, (Object)inUfvGkey));
        return HibernateApi.getInstance().findEntitiesByDomainQuery(dq);
    }

    public static List findByUfvGkeyAndSourceGkeyNotNull(Long inUfvGkey) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"ChargeableUnitEvent").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoExtractField.BEXU_UFV_GKEY, (Object)inUfvGkey)).addDqPredicate(PredicateFactory.isNotNull((IMetafieldId) IArgoExtractField.BEXU_SOURCE_GKEY));
        return HibernateApi.getInstance().findEntitiesByDomainQuery(dq);
    }

    @Nullable
    public static ChargeableUnitEvent findByUfvGkeyAndSourceGkey(Long inUfvGkey, Long inSourceGkey) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"ChargeableUnitEvent").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoExtractField.BEXU_UFV_GKEY, (Object)inUfvGkey)).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoExtractField.BEXU_SOURCE_GKEY, (Object)inSourceGkey));
        Serializable[] cueGkey = HibernateApi.getInstance().findPrimaryKeysByDomainQuery(dq);
        if (cueGkey == null || cueGkey.length == 0) {
            return null;
        }
        if (cueGkey.length == 1) {
            return (ChargeableUnitEvent)HibernateApi.getInstance().load(ChargeableUnitEvent.class, cueGkey[0]);
        }
        throw BizFailure.create((IPropertyKey)IFrameworkPropertyKeys.FRAMEWORK__NON_UNIQUE_RESULT, null, (Object)cueGkey.length, (Object)dq);
    }

    @Nullable
    public static ChargeableUnitEvent findLatestByUfvGkeyAndSourceGkey(Long inUfvGkey, Long inSourceGkey) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"ChargeableUnitEvent").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoExtractField.BEXU_UFV_GKEY, (Object)inUfvGkey)).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoExtractField.BEXU_SOURCE_GKEY, (Object)inSourceGkey)).addDqOrdering(Ordering.desc((IMetafieldId) IArgoExtractField.BEXU_CREATED));
        Serializable[] cueGkey = HibernateApi.getInstance().findPrimaryKeysByDomainQuery(dq);
        if (cueGkey == null || cueGkey.length == 0) {
            return null;
        }
        return (ChargeableUnitEvent)HibernateApi.getInstance().load(ChargeableUnitEvent.class, cueGkey[0]);
    }

    public static List findByUfvGkeyForUnitGkey(Long inUnitGkey) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"UnitFacilityVisit").addDqPredicate(PredicateFactory.eq((IMetafieldId) IInventoryField.UFV_UNIT, (Object)inUnitGkey));
        return HibernateApi.getInstance().findEntitiesByDomainQuery(dq);
    }

    @Nullable
    public static ChargeableUnitEvent findPaidThruDayChargeableUnitStorageEvent(Facility inFcy, UnitFacilityVisit inUfv, ChargeableUnitEventTypeEnum inEventType, Date inPtd) {
        Date dateBegin = DateUtils.truncate((Date)inPtd, (int)5);
        Date dateEnd = new Date(dateBegin.getTime() + 86400000L);
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"ChargeableUnitEvent").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoExtractField.BEXU_UFV_GKEY, (Object)inUfv.getPrimaryKey())).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoExtractField.BEXU_FACILITY, (Object)inFcy.getPrimaryKey())).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoExtractField.BEXU_EVENT_TYPE, (Object)inEventType.getKey())).addDqPredicate(PredicateFactory.lt((IMetafieldId) IArgoExtractField.BEXU_EVENT_START_TIME, (Object)dateEnd)).addDqOrdering(Ordering.asc((IMetafieldId) IArgoExtractField.BEXU_EVENT_START_TIME));
        List results = Roastery.getHibernateApi().findEntitiesByDomainQuery(dq);
        return !results.isEmpty() ? (ChargeableUnitEvent)results.get(0) : null;
    }

    @Nullable
    public static ChargeableUnitEvent findPaidThruDayChargeableUnitReefereEvent(Facility inFcy, UnitFacilityVisit inUfv, ChargeableUnitEventTypeEnum inEventType, Date inPtd) {
        Date dateBegin = DateUtils.truncate((Date)inPtd, (int)5);
        Date dateEnd = new Date(dateBegin.getTime() + 86400000L);
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"ChargeableUnitEvent").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoExtractField.BEXU_UFV_GKEY, (Object)inUfv.getPrimaryKey())).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoExtractField.BEXU_FACILITY, (Object)inFcy.getPrimaryKey())).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoExtractField.BEXU_EVENT_TYPE, (Object)inEventType.getKey())).addDqPredicate(PredicateFactory.lt((IMetafieldId) IArgoExtractField.BEXU_EVENT_START_TIME, (Object)dateEnd)).addDqOrdering(Ordering.asc((IMetafieldId) IArgoExtractField.BEXU_EVENT_START_TIME));
        List results = Roastery.getHibernateApi().findEntitiesByDomainQuery(dq);
        return !results.isEmpty() ? (ChargeableUnitEvent)results.get(0) : null;
    }

    public static void createStorageEvent(MoveEvent inSourceMoveEvent) {
        LogicalEntityEnum appliedToClass = inSourceMoveEvent.getEvntAppliedToClass();
        if (!LogicalEntityEnum.UNIT.equals((Object)appliedToClass)) {
            LOGGER.error((Object)("createStorageEvent: passed event not for a Unit: " + inSourceMoveEvent));
            return;
        }
        UnitFacilityVisit ufv = inSourceMoveEvent.getMveUfv();
        Unit unit = ufv.getUfvUnit();
        EqUnitRoleEnum unitRole = unit.getUnitEqRole();
        boolean isPrimary = EqUnitRoleEnum.PRIMARY.equals((Object)unitRole);
        UserContext userContext = ContextHelper.getThreadUserContext();
        boolean allowCreation = UnitEventExtractManager.validateStorageSetting(unitRole);
        if (!allowCreation) {
            return;
        }
        ChargeableUnitEvent cue = UnitEventExtractManager.findExistingChargeableUnitStorageEvent(inSourceMoveEvent.getEvntFacility(), ufv, ChargeableUnitEventTypeEnum.STORAGE);
        if (cue != null) {
            if (cue.getBexuEventEndTime() != null && STATUS_QUEUED.equals(cue.getBexuStatus())) {
                cue.setBexuEventEndTime(null);
            } else {
                LOGGER.error((Object)("createStorageEvent: found unexpected existing ChargeableUnitEventTypeEnum for ue " + unit + " of UFV " + ufv));
            }
        } else {
  //          Map<String, String> flexFieldsMap = UnitEventExtractorPea.getFlexFieldsFromBillingExtractMap(userContext);
  //          cue = UnitEventExtractManager.createChargeableReeferStorageEvent(ufv, unit, inSourceMoveEvent, null, flexFieldsMap);
            cue.setBexuEventType(ChargeableUnitEventTypeEnum.STORAGE.getKey());
            cue.setBexuEventStartTime(inSourceMoveEvent.getEvntAppliedDate());
            cue.setBexuEventEndTime(null);
            cue.setBexuRuleEndDay(null);
            UnitEventExtractManager.setRuleStartDay(cue, ufv);
            UnitEventExtractManager.setCueStatusBasedOnRule(cue, ufv);
        }
    }

    public static void createLineStorageEvent(MoveEvent inSourceMoveEvent) {
        LogicalEntityEnum appliedToClass = inSourceMoveEvent.getEvntAppliedToClass();
        if (!LogicalEntityEnum.UNIT.equals((Object)appliedToClass)) {
            LOGGER.error((Object)("createLineStorageEvent: passed event not for a Unit: " + inSourceMoveEvent));
            return;
        }
        UnitFacilityVisit ufv = inSourceMoveEvent.getMveUfv();
        Unit unit = ufv.getUfvUnit();
        EqUnitRoleEnum unitRole = unit.getUnitEqRole();
        UserContext userContext = ContextHelper.getThreadUserContext();
        boolean allowCreation = UnitEventExtractManager.validateLineStorageSetting(unitRole);
        if (!allowCreation) {
            return;
        }
        ChargeableUnitEvent cue = UnitEventExtractManager.findExistingChargeableUnitStorageEvent(inSourceMoveEvent.getEvntFacility(), ufv, ChargeableUnitEventTypeEnum.LINE_STORAGE);
        if (cue != null) {
            if (cue.getBexuEventEndTime() != null && STATUS_QUEUED.equals(cue.getBexuStatus())) {
                cue.setBexuEventEndTime(null);
            } else {
                LOGGER.error((Object)("createLineStorageEvent: found unexpected existing ChargeableUnitEventTypeEnum for ue " + unit + " of UFV " + ufv));
            }
        } else {
//            Map<String, String> flexFieldsMap = UnitEventExtractorPea.getFlexFieldsFromBillingExtractMap(userContext);
//            cue = UnitEventExtractManager.createChargeableReeferStorageEvent(ufv, unit, inSourceMoveEvent, null, flexFieldsMap);
//            cue.setBexuEventType(ChargeableUnitEventTypeEnum.LINE_STORAGE.getKey());
//            cue.setBexuEventStartTime(inSourceMoveEvent.getEvntAppliedDate());
//            cue.setBexuEventEndTime(null);
//            UnitEventExtractManager.setRuleStartDay(cue, ufv);
//            cue.setBexuRuleEndDay(null);
//            UnitEventExtractManager.setCueStatusBasedOnRule(cue, ufv);
        }
    }

    public static void updateLineStorageEvent(MoveEvent inSourceMoveEvent) {
        CarrierVisit outboundCv;
        LogicalEntityEnum appliedToClass = inSourceMoveEvent.getEvntAppliedToClass();
        if (!LogicalEntityEnum.UNIT.equals((Object)appliedToClass)) {
            LOGGER.error((Object)("updateLineStorageEvent: passed event not for a Unit: " + inSourceMoveEvent));
            return;
        }
        UnitFacilityVisit ufv = inSourceMoveEvent.getMveUfv();
        Unit unit = ufv.getUfvUnit();
        UserContext userContext = ContextHelper.getThreadUserContext();
        boolean allowCreation = UnitEventExtractManager.validateLineStorageSetting(unit.getUnitEqRole());
        if (!allowCreation) {
            return;
        }
        ChargeableUnitEvent cue = UnitEventExtractManager.findExistingChargeableUnitStorageEventEndTimeIsNull(inSourceMoveEvent.getEvntFacility(), ufv, ChargeableUnitEventTypeEnum.LINE_STORAGE);
        if (cue == null) {
            LOGGER.error((Object)("updateLineStorageEvent: expected to find an existing ChargeableUnitEventTypeEnum for ue " + unit + " of UFV " + ufv));
            cue = UnitEventExtractManager.createChargeableReeferStorageEvent(ufv, unit, inSourceMoveEvent, null, null);
            cue.setBexuEventType(ChargeableUnitEventTypeEnum.LINE_STORAGE.getKey());
            cue.setBexuEventStartTime(ufv.getUfvTimeIn());
        }
        if (cue.getBexuIsLocked() != null && cue.getBexuIsLocked().booleanValue()) {
            LOGGER.error((Object)("updateLineStorageEvent: CUE is locked - no update applied " + unit + " of UFV " + ufv));
            return;
        }
        if (ufv.isTransitStateBeyond(UfvTransitStateEnum.S60_LOADED)) {
            cue.setBexuEventEndTime(inSourceMoveEvent.getEvntAppliedDate());
        }
        if ((outboundCv = ufv.getUfvActualObCv()) == null) {
            LOGGER.error((Object)("updateLineStorageEvent: no outbound carrier(?) for UFV " + ufv));
            return;
        }
        cue.setBexuObId(outboundCv.getCvId());
        cue.setBexuObLocType(outboundCv.getCvCarrierMode().getKey());
        VisitDetails visitDetails = outboundCv.getCvCvd();
        if (visitDetails == null) {
            LOGGER.error((Object)("updateLineStorageEvent: no visit details for outbound carrier " + (Object)outboundCv + " of UFV " + ufv));
            return;
        }
        cue.setBexuObCarrierETA(visitDetails.getCvdETA());
        cue.setBexuObCarrierName(visitDetails.getCarrierVehicleName());
        cue.setBexuObCallNbr(visitDetails.getCarrierObVisitCallNbr());
        cue.setBexuObVisitId(visitDetails.getCarrierObVoyNbrOrTrainId());
    }

    private static void setRuleStartAndEndTime(ChargeableUnitEvent inCue, UnitFacilityVisit inUfv) {
        try {
            Date configEndTime;
            Date calcStartTime;
            UnitStorageCalculation storageCalculation = null;
            UnitEventExtractManager.EXEMPT_TYPES[0] = ArgoCalendarEventType.findByName((String)EXEMPT);
            UnitEventExtractManager.GRATIS_TYPES[0] = ArgoCalendarEventType.findByName((String)GRATIS);
            storageCalculation = REEFER.equals(inCue.getBexuEventType()) ? UnitStorageCalculation.calculateStorage(inUfv, EXEMPT_TYPES, GRATIS_TYPES, null, null, POWER) : UnitStorageCalculation.calculateStorage(inUfv, EXEMPT_TYPES, GRATIS_TYPES, null, null, inCue.getBexuEventType());
            Date date = calcStartTime = storageCalculation != null ? storageCalculation.getCalculationStartTime() : null;
            if (null != calcStartTime) {
                inCue.setBexuRuleStartDay(calcStartTime);
            }
            Date date2 = configEndTime = storageCalculation != null ? storageCalculation.getConfiguredEndTime() : null;
            if (null != configEndTime) {
                inCue.setBexuRuleEndDay(configEndTime);
            }
            inCue.setStatusInvoicedOrPartialForStorageReefer(inCue.getEventType());
        }
        catch (Throwable t) {
            LOGGER.error((Object)("problem updating UnitEventExtractManager.setRuleStartAndEndTime " + inUfv + " " + t));
        }
    }

    public static void updateRuleEndTime(UnitFacilityVisit inUfv) {
        try {
            boolean allowCreation = UnitEventExtractManager.validateStorageSetting(inUfv.getUfvUnit().getUnitEqRole());
            if (!allowCreation) {
                return;
            }
            ChargeableUnitEvent cue = UnitEventExtractManager.findExistingChargeableUnitStorageEvent(inUfv.getUfvFacility(), inUfv, ChargeableUnitEventTypeEnum.STORAGE);
            if (cue != null) {
                UnitEventExtractManager.setRuleStartAndEndTime(cue, inUfv);
                cue.setStatusInvoicedOrPartialForStorageReefer(cue.getEventType());
            }
            if ((cue = UnitEventExtractManager.findExistingChargeableUnitStorageEvent(inUfv.getUfvFacility(), inUfv, ChargeableUnitEventTypeEnum.LINE_STORAGE)) != null) {
                UnitEventExtractManager.setRuleStartAndEndTime(cue, inUfv);
                cue.setStatusInvoicedOrPartialForStorageReefer(cue.getEventType());
            }
            if ((cue = UnitEventExtractManager.findExistingChargeableUnitStorageEvent(inUfv.getUfvFacility(), inUfv, ChargeableUnitEventTypeEnum.REEFER)) != null) {
                UnitEventExtractManager.setRuleStartAndEndTime(cue, inUfv);
                cue.setStatusInvoicedOrPartialForStorageReefer(cue.getEventType());
            }
        }
        catch (Throwable t) {
            LOGGER.error((Object)("problem updating UnitEventExtractManager.updateRuleEndTime " + inUfv + " " + t));
        }
    }

    public static void updateStorageEvent(MoveEvent inSourceMoveEvent) {
        CarrierVisit outboundCv;
        LogicalEntityEnum appliedToClass = inSourceMoveEvent.getEvntAppliedToClass();
        if (!LogicalEntityEnum.UNIT.equals((Object)appliedToClass)) {
            LOGGER.error((Object)("updateStorageEvent: passed event not for a Unit: " + inSourceMoveEvent));
            return;
        }
        UnitFacilityVisit ufv = inSourceMoveEvent.getMveUfv();
        Unit unit = ufv.getUfvUnit();
        boolean allowCreation = UnitEventExtractManager.validateStorageSetting(unit.getUnitEqRole());
        if (!allowCreation) {
            return;
        }
        ChargeableUnitEvent cue = UnitEventExtractManager.findExistingLatestChargeableUnitStorageEventEndTimeIsNull(inSourceMoveEvent.getEvntFacility(), ufv, ChargeableUnitEventTypeEnum.STORAGE);
        if (cue == null) {
            LOGGER.error((Object)("updateStorageEvent: expected to find an existing ChargeableUnitEventTypeEnum for ue " + unit + " of UFV " + ufv));
            cue = UnitEventExtractManager.createChargeableReeferStorageEvent(ufv, unit, inSourceMoveEvent, null, null);
            cue.setBexuEventType(ChargeableUnitEventTypeEnum.STORAGE.getKey());
            cue.setBexuEventStartTime(ufv.getUfvTimeIn());
        }
        if (cue.getBexuIsLocked() != null && cue.getBexuIsLocked().booleanValue()) {
            LOGGER.error((Object)("updateStorageEvent: CUE is locked - no update applied " + unit + " of UFV " + ufv));
            return;
        }
        if (ufv.isTransitStateBeyond(UfvTransitStateEnum.S60_LOADED)) {
            cue.setBexuEventEndTime(inSourceMoveEvent.getEvntAppliedDate());
        }
        if ((outboundCv = ufv.getUfvActualObCv()) == null) {
            LOGGER.error((Object)("updateStorageEvent: no outbound carrier(?) for UFV " + ufv));
            return;
        }
        cue.setBexuObId(outboundCv.getCvId());
        cue.setBexuObLocType(outboundCv.getCvCarrierMode().getKey());
        VisitDetails visitDetails = outboundCv.getCvCvd();
        if (visitDetails == null) {
            LOGGER.error((Object)("updateStorageEvent: no visit details for outbound carrier " + (Object)outboundCv + " of UFV " + ufv));
            return;
        }
        cue.setBexuObCarrierETA(visitDetails.getCvdETA());
        cue.setBexuObCarrierName(visitDetails.getCarrierVehicleName());
        cue.setBexuObCallNbr(visitDetails.getCarrierObVisitCallNbr());
        cue.setBexuObVisitId(visitDetails.getCarrierObVoyNbrOrTrainId());
    }

    public static void createReeferEvent(Unit inUnit, IEvent inIEvent) throws BizViolation {
        Event evnt = (Event)inIEvent;
        UserContext userContext = ContextHelper.getThreadUserContext();
        if (!InventoryConfig.BILLING_CREATE_REEFER.isOn(userContext)) {
            return;
        }
        Facility facility = evnt.getEvntFacility();
        UnitEquipment ue = inUnit.getUnitPrimaryUe();
        if (ue == null) {
            return;
        }
        Equipment eq = ue.getUeEquipment();
        UnitFacilityVisit activeUfv = inUnit.getUnitActiveUfv();
        if (activeUfv == null) {
            LOGGER.error((Object)("createReeferEvent: expected to find an active ufv of  Unit " + inUnit));
            return;
        }
        ChargeableUnitEvent cue = UnitEventExtractManager.findExistingChargeableUnitReeferEvent(facility, ChargeableUnitEventTypeEnum.REEFER, activeUfv);
        if (cue != null) {
            if (cue.getBexuIsLocked() != null && cue.getBexuIsLocked().booleanValue()) {
                LOGGER.error((Object)("updateReeferEvent: CUE is locked - no update applied " + ue + " of Unit " + inUnit));
            } else {
                cue.setBexuEventEndTime(null);
                cue.setBexuRuleEndDay(null);
                if (INVOICED.equals(cue.getBexuStatus()) && activeUfv.getUfvTimeOut() == null) {
                    cue.setStatusInvoicedOrPartialForStorageReefer(cue.getEventType());
                }
            }
        } else {
//            Map<String, String> flexFieldsMap = UnitEventExtractorPea.getFlexFieldsFromBillingExtractMap(userContext);
//            cue = UnitEventExtractManager.createChargeableReeferStorageEvent(activeUfv, inUnit, evnt, null, flexFieldsMap);
            cue.setBexuEventType(ChargeableUnitEventTypeEnum.REEFER.getKey());
            cue.setBexuEventStartTime(evnt.getEvntAppliedDate());
            cue.setBexuEventEndTime(null);
            UnitEventExtractManager.setRuleStartDay(cue, activeUfv);
            cue.setBexuRuleEndDay(null);
            UnitEventExtractManager.setCueStatusBasedOnRule(cue, activeUfv);
            ScopedBizUnit unitLineOperator = inUnit.getUnitLineOperator();
            if (unitLineOperator != null) {
                cue.setBexuLineOperatorId(unitLineOperator.getBzuId());
            }
        }
    }

    public static void updateReeferEvent(Unit inUnit, IEvent inIEvent) throws BizViolation {
        UserContext userContext = ContextHelper.getThreadUserContext();
        if (!InventoryConfig.BILLING_CREATE_REEFER.isOn(userContext)) {
            return;
        }
        Event evnt = (Event)inIEvent;
        Facility facility = evnt.getEvntFacility();
        UnitEquipment ue = inUnit.getUnitPrimaryUe();
        Equipment eq = ue.getUeEquipment();
        UnitFacilityVisit activeUfv = inUnit.getUnitActiveUfv();
        if (activeUfv == null) {
            LOGGER.error((Object)("updateReeferEvent: expected to find an active ufv of Unit " + inUnit));
            return;
        }
        ChargeableUnitEvent cue = UnitEventExtractManager.findExistingChargeableUnitReeferEvent(facility, ChargeableUnitEventTypeEnum.REEFER, activeUfv);
        if (cue == null) {
            LOGGER.error((Object)("updateReeferEvent: expected to find an existing ChargeableUnitEventTypeEnum for ue " + ue + " of Unit " + inUnit));
            cue = UnitEventExtractManager.createChargeableReeferStorageEvent(activeUfv, inUnit, evnt, null, null);
            cue.setBexuEventType(ChargeableUnitEventTypeEnum.REEFER.getKey());
            cue.setBexuEventStartTime(evnt.getEvntAppliedDate());
        }
        if (cue.getBexuIsLocked() != null && cue.getBexuIsLocked().booleanValue()) {
            LOGGER.error((Object)("updateReeferEvent: CUE is locked - no update applied " + ue + " of Unit " + inUnit));
            return;
        }
        cue.setBexuEventEndTime(evnt.getEvntAppliedDate());
        String prevStatus = cue.getBexuStatus();
        UnitEventExtractManager.setRuleStartAndEndTime(cue, activeUfv);
        cue.setStatusInvoicedOrPartialForStorageReefer(cue.getEventType());
        if (PARTIAL.equals(prevStatus)) {
            String sruleEndDayType;
            IUnitStorageManager usm = (IUnitStorageManager)Roastery.getBean((String)"unitStorageManager");
            IValueHolder strgeDetVh = (IValueHolder) usm.getStorageCalculationDto(activeUfv, POWER);
            String string = sruleEndDayType = strgeDetVh != null ? (String)strgeDetVh.getFieldValue(UnitField.SRULE_END_DAY) : null;
            if (!StorageStartEndDayRuleTypeEnum.POWER_DISCONNECT.getName().equals(sruleEndDayType) && activeUfv.getUfvTimeOut() == null) {
                cue.setBexuStatus(prevStatus);
            }
        }
    }

    public static void updateChargeableUnitEvent(String inNewEqId, Long inUnitGkey) {
        if (inUnitGkey == null) {
            throw BizFailure.create((String)"inUnitGkey cannot be null");
        }
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"ChargeableUnitEvent").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoExtractField.BEXU_UNIT_GKEY, (Object)inUnitGkey)).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoExtractField.BEXU_STATUS, (Object)STATUS_QUEUED));
        List chargeableUnitEvents = HibernateApi.getInstance().findEntitiesByDomainQuery(dq);
        for (Object chgUnitEvent : chargeableUnitEvents) {
            if (chgUnitEvent == null) continue;
            ((ChargeableUnitEvent)chgUnitEvent).setBexuEqId(inNewEqId);
            List gntes = Guarantee.findGuaranteesForCue((ChargeableUnitEvent)chgUnitEvent);
            if (gntes == null || gntes.isEmpty()) continue;
            for (Object gnte : gntes) {
                ((Guarantee)gnte).setGnteAppliedToNaturalKey(inNewEqId);
            }
        }
    }

    public static void deleteRecordByUnitGkey(Serializable inUfvGkey) {
        List chgList = UnitEventExtractManager.getExtractedRecordListByUnitFacilityGkey((Long)inUfvGkey);
        if (!chgList.isEmpty()) {
            for (Object cce : chgList) {
                HibernateApi.getInstance().delete((Object)cce);
            }
        }
    }

    public static void deleteExtractedEvents(Collection<Serializable> inDeletedEventGkeys) {
        Serializable[] cueGkeys;
        if (inDeletedEventGkeys.isEmpty()) {
            return;
        }
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"ChargeableUnitEvent").addDqPredicate(PredicateFactory.in((IMetafieldId) IArgoExtractField.BEXU_SOURCE_GKEY, inDeletedEventGkeys));
        for (Serializable cueGkey : cueGkeys = HibernateApi.getInstance().findPrimaryKeysByDomainQuery(dq)) {
            ChargeableUnitEvent cue = (ChargeableUnitEvent)HibernateApi.getInstance().load(ChargeableUnitEvent.class, cueGkey);
            HibernateApi.getInstance().delete((Object)cue);
        }
    }

    public static void updatePaidThruDate(UnitFacilityVisit inUfv, UnitEquipment inUe, ChargeableUnitEventTypeEnum inEventType, Date inPaidThrudate) {
        ChargeableUnitEvent cue = null;
        if (ChargeableUnitEventTypeEnum.STORAGE.equals((Object)inEventType)) {
            cue = UnitEventExtractManager.findPaidThruDayChargeableUnitStorageEvent(inUfv.getUfvFacility(), inUfv, inEventType, inPaidThrudate);
        } else if (ChargeableUnitEventTypeEnum.LINE_STORAGE.equals((Object)inEventType)) {
            cue = UnitEventExtractManager.findPaidThruDayChargeableUnitStorageEvent(inUfv.getUfvFacility(), inUfv, inEventType, inPaidThrudate);
        } else if (ChargeableUnitEventTypeEnum.REEFER.equals((Object)inEventType)) {
            cue = UnitEventExtractManager.findPaidThruDayChargeableUnitReefereEvent(inUfv.getUfvFacility(), inUfv, inEventType, inPaidThrudate);
        }
        if (cue != null) {
            cue.setBexuPaidThruDay(inPaidThrudate);
        } else {
            LOGGER.error((Object)("updatePaidThruDate: PTD is not updated. Unable to find an existing ChargeableUnitEventTypeEnum for ue " + inUe + " of UFV " + inUfv));
        }
    }

    public static void updatePaidThruDayByPrimaryGkey(Long inGkey, Date inPaidThrudate) {
        ChargeableUnitEvent cue = (ChargeableUnitEvent)HibernateApi.getInstance().load(ChargeableUnitEvent.class, (Serializable)inGkey);
        if (cue != null) {
            cue.setBexuPaidThruDay(inPaidThrudate);
        } else {
            LOGGER.error((Object)("updatePaidThruDate: PTD is not updated. Unable to find an existing ChargeableUnitEventTypeEnum for  ChargeableUnitEvent gkey " + inGkey));
        }
    }

    public static void updateStorageEventEndTime(UnitFacilityVisit inUfv, ChargeableUnitEventTypeEnum inEventType) {
        ChargeableUnitEvent cue = UnitEventExtractManager.findExistingChargeableUnitStorageEventEndTimeIsNull(inUfv.getUfvFacility(), inUfv, inEventType);
        UnitEventExtractManager.updateStorageEventEndTime(inUfv, cue);
    }

    public static void updateStorageEventEndTime(UnitFacilityVisit inUfv, ChargeableUnitEvent inCue) {
        if (inCue != null) {
            inCue.setBexuUfvTimeOut(inUfv.getUfvTimeComplete());
            inCue.setBexuEventEndTime(inUfv.getUfvTimeComplete());
            UnitEventExtractManager.setRuleStartAndEndTime(inCue, inUfv);
            UnitEventExtractManager.updateEventEndTimeForCmpxCues(inCue, inUfv);
        } else {
            LOGGER.warn((Object)("updateStorageEventEndTime: Event end time is not changed since it is already exists for " + inUfv.getUfvUnit().getUnitId()));
        }
    }

    public static void updateTimeOutForNonTimeBasedEvents(UnitFacilityVisit inUfv) {
        List<ChargeableUnitEvent> cueList = UnitEventExtractManager.findExistingChargeableUnitEventsTimeOutIsNull(inUfv);
        for (ChargeableUnitEvent cue : cueList) {
            cue.setBexuUfvTimeOut(inUfv.getUfvTimeComplete());
        }
    }

    public static ChargeableUnitEvent createStorageEventWhenUnitStripOrStuff(UnitFacilityVisit inUfv, Unit inUnit, Event inEvent, Long inBatchId, Map inFlexHashMap) {
        return UnitEventExtractManager.createChargeableReeferStorageEvent(inUfv, inUnit, inEvent, inBatchId, inFlexHashMap);
    }

    public static List getCUEsFor(String inEventTypeId, String inStatus) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"ChargeableUnitEvent").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoExtractField.BEXU_EVENT_TYPE, (Object)inEventTypeId)).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoExtractField.BEXU_STATUS, (Object)inStatus));
        return Roastery.getHibernateApi().findEntitiesByDomainQuery(dq);
    }

    public static void updateEventOBCarrierVisit(UnitFacilityVisit inUfv) {
        List cueList = UnitEventExtractManager.findExistingEventsByUfv(inUfv);
        if (cueList == null || cueList.isEmpty()) {
            LOGGER.warn((Object)("updateEventOBCarrierVisit: Event Carrier Name is not changed since no CUE'S exist for " + inUfv.getUfvUnit().getUnitId()));
            return;
        }
        CarrierVisit ufvActualObCv = inUfv.getUfvActualObCv().getOutboundCv();
        if (ufvActualObCv != null) {
            for (Object cue : cueList) {
                if (!((ChargeableUnitEvent)cue).isTimeBasedEvent() || !STATUS_QUEUED.equals(((ChargeableUnitEvent)cue).getBexuStatus())) continue;
                ((ChargeableUnitEvent)cue).setBexuObCarrierName(ufvActualObCv.getCarrierVehicleName());
                ((ChargeableUnitEvent)cue).setBexuObId(ufvActualObCv.getCvId());
                ((ChargeableUnitEvent)cue).setBexuObintendedId(ufvActualObCv.getCvId());
                ((ChargeableUnitEvent)cue).setBexuObVisitId(ufvActualObCv.getCarrierObVoyNbrOrTrainId());
            }
        }
    }

    public static List findExistingEventsByUfv(UnitFacilityVisit inUfv) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"ChargeableUnitEvent").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoExtractField.BEXU_UFV_GKEY, (Object)inUfv.getUfvGkey()));
        return HibernateApi.getInstance().findEntitiesByDomainQuery(dq);
    }

    private static void setRuleStartDay(ChargeableUnitEvent inCue, UnitFacilityVisit inUfv) {
        try {
            Date configEndTime;
            Date calcStartTime;
            UnitStorageCalculation storageCalculation = null;
            UnitEventExtractManager.EXEMPT_TYPES[0] = ArgoCalendarEventType.findByName((String)EXEMPT);
            UnitEventExtractManager.GRATIS_TYPES[0] = ArgoCalendarEventType.findByName((String)GRATIS);
            storageCalculation = REEFER.equals(inCue.getBexuEventType()) ? UnitStorageCalculation.calculateStorage(inUfv, EXEMPT_TYPES, GRATIS_TYPES, null, null, POWER) : UnitStorageCalculation.calculateStorage(inUfv, EXEMPT_TYPES, GRATIS_TYPES, null, null, inCue.getBexuEventType());
            Date date = calcStartTime = storageCalculation != null ? storageCalculation.getCalculationStartTime() : null;
            if (null != calcStartTime) {
                inCue.setBexuRuleStartDay(calcStartTime);
            }
            Date date2 = configEndTime = storageCalculation != null ? storageCalculation.getConfiguredEndTime() : null;
            if (null != configEndTime) {
                inCue.setBexuRuleEndDay(configEndTime);
            }
        }
        catch (Throwable t) {
            LOGGER.error((Object)("problem updating UnitEventExtractManager.setRuleStartDay " + inUfv + " " + t));
        }
    }

    public static void updateStorageRuleStartDay(ChargeableUnitEvent inCue, UnitFacilityVisit inUfv) {
        UnitEventExtractManager.setRuleStartDay(inCue, inUfv);
    }

    public static boolean validateStorageSetting(EqUnitRoleEnum inUnitRole) {
        boolean allowCreation = false;
        String[] settings = InventoryConfig.BILLING_CREATE_STORAGE.getSettings(ContextHelper.getThreadUserContext());
        if (settings != null && settings.length > 0) {
            String setting;
            String[] arrstring = settings;
            int n = arrstring.length;
            for (int i = 0; i < n && !(allowCreation = inUnitRole.equals((Object)EQUIPMENT_STORAGE_ROLE_SETTING_MAP.get(setting = arrstring[i]))); ++i) {
            }
        }
        if (!allowCreation) {
            LOGGER.info((Object)("Storage creation for given role " + (Object)((Object)inUnitRole) + " is suspended by setting."));
        }
        return allowCreation;
    }

    public static boolean validateLineStorageSetting(EqUnitRoleEnum inUnitRole) {
        boolean allowCreation = false;
        String[] settings = InventoryConfig.BILLING_CREATE_LINE_STORAGE.getSettings(ContextHelper.getThreadUserContext());
        if (settings != null && settings.length > 0) {
            String setting;
            String[] arrstring = settings;
            int n = arrstring.length;
            for (int i = 0; i < n && !(allowCreation = inUnitRole.equals((Object)EQUIPMENT_LINE_STORAGE_ROLE_SETTING_MAP.get(setting = arrstring[i]))); ++i) {
            }
        }
        if (!allowCreation) {
            LOGGER.info((Object)("Storage creation for given role " + (Object)((Object)inUnitRole) + " is suspended by setting."));
        }
        return allowCreation;
    }

    private static void updateEventEndTimeForCmpxCues(ChargeableUnitEvent inCue, UnitFacilityVisit inUfv) {
        List<ChargeableUnitEvent> cueList;
        if (inCue != null && inUfv.getUfvTimeComplete() != null && CMPLXNOTREQD.equals(inCue.getBexuStatus()) && (cueList = UnitEventExtractManager.getCueRecordAtCmplx(inUfv, inCue.getBexuEventType())) != null && !cueList.isEmpty()) {
            UnitEventExtractManager.updateCueRecordAtCmpx(cueList, inCue.getBexuRuleEndDay(), inCue.getBexuEventEndTime());
            inCue.setBexuStatus(CMPLXNOTREQD);
        }
    }

    private static void setCueStatusBasedOnRule(ChargeableUnitEvent inCue, UnitFacilityVisit inUfv) {
        try {
            List<ChargeableUnitEvent> cueList;
            UnitStorageCalculation storageCalculation = null;
            UnitEventExtractManager.EXEMPT_TYPES[0] = ArgoCalendarEventType.findByName((String)EXEMPT);
            UnitEventExtractManager.GRATIS_TYPES[0] = ArgoCalendarEventType.findByName((String)GRATIS);
            storageCalculation = REEFER.equals(inCue.getBexuEventType()) ? UnitStorageCalculation.calculateStorage(inUfv, EXEMPT_TYPES, GRATIS_TYPES, null, null, POWER) : UnitStorageCalculation.calculateStorage(inUfv, EXEMPT_TYPES, GRATIS_TYPES, null, null, inCue.getBexuEventType());
            if (StorageStartEndDayRuleTypeEnum.COMPLEX_IN.getKey().equals(storageCalculation.getRuleStartDayType()) && StorageStartEndDayRuleTypeEnum.COMPLEX_OUT.getKey().equals(storageCalculation.getRuleEndDayType()) && (cueList = UnitEventExtractManager.getCueRecordAtCmplx(inUfv, inCue.getBexuEventType())) != null && !cueList.isEmpty()) {
                UnitEventExtractManager.updateCueRecordAtCmpx(cueList, null, null);
                inCue.setBexuStatus(CMPLXNOTREQD);
            }
        }
        catch (Throwable t) {
            LOGGER.error((Object)("problem updating UnitEventExtractManager.setBexuStatus for Complex Level Billing " + inUfv + " " + t));
        }
    }

    private static List<ChargeableUnitEvent> getCueRecordAtCmplx(UnitFacilityVisit inUfv, String inEventType) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"ChargeableUnitEvent").addDqPredicate(PredicateFactory.ne((IMetafieldId) IArgoExtractField.BEXU_FACILITY, (Object)inUfv.getUfvFacility())).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoExtractField.BEXU_EVENT_TYPE, (Object)inEventType)).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoExtractField.BEXU_UNIT_GKEY, (Object)inUfv.getUfvUnit().getPrimaryKey()));
        dq.setScopingEnabled(Boolean.FALSE.booleanValue());
        return HibernateApi.getInstance().findEntitiesByDomainQuery(dq);
    }

    private static void updateCueRecordAtCmpx(List<ChargeableUnitEvent> inCueList, Date inRuleEndDay, Date inEventEndTime) {
        if (inCueList != null && !inCueList.isEmpty() && inCueList.get(0) != null) {
            for (ChargeableUnitEvent cue : inCueList) {
                cue.setBexuEventEndTime(inEventEndTime);
                cue.setBexuRuleEndDay(inRuleEndDay);
                if (!INVOICED.equals(cue.getStatus()) || inEventEndTime != null || inRuleEndDay != null) continue;
                cue.setBexuStatus(PARTIAL);
            }
        }
    }
}
