package com.zpmc.ztos.infra.base.business.inventory;

import com.zpmc.ztos.infra.base.business.dataobject.UnitDO;
import com.zpmc.ztos.infra.base.business.enums.argo.*;
import com.zpmc.ztos.infra.base.business.enums.inventory.EqDamageSeverityEnum;
import com.zpmc.ztos.infra.base.business.enums.inventory.EqUnitRoleEnum;
import com.zpmc.ztos.infra.base.business.enums.inventory.UfvTransitStateEnum;
import com.zpmc.ztos.infra.base.business.enums.inventory.UnitVisitStateEnum;
import com.zpmc.ztos.infra.base.business.equipments.*;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.business.model.BizFieldList;
import com.zpmc.ztos.infra.base.business.model.CarrierVisit;
import com.zpmc.ztos.infra.base.business.model.LocPosition;
import com.zpmc.ztos.infra.base.business.model.ScopedBizUnit;
import com.zpmc.ztos.infra.base.business.plans.WorkInstruction;
import com.zpmc.ztos.infra.base.business.predicate.PredicateFactory;
import com.zpmc.ztos.infra.base.common.configs.InventoryConfig;
import com.zpmc.ztos.infra.base.common.contexts.PortalApplicationContext;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.common.events.EventManager;
import com.zpmc.ztos.infra.base.common.exceptions.BizFailure;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.helps.ContextHelper;
import com.zpmc.ztos.infra.base.common.model.*;
import com.zpmc.ztos.infra.base.common.scopes.Complex;
import com.zpmc.ztos.infra.base.common.scopes.Facility;
import com.zpmc.ztos.infra.base.common.utils.ArgoUtils;
import com.zpmc.ztos.infra.base.common.utils.InventoryControlUtils;
import com.zpmc.ztos.infra.base.utils.QueryUtils;
import org.apache.log4j.Logger;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.*;

public class UnitEquipment extends UnitBase {
    private static final BizFieldList BIZ_FIELDS = new BizFieldList();
    private static final Logger LOGGER;
    private static final String STORAGE = "STORAGE";
    private static String SYSTEM_UID;
    private static final int SPARCS_DAMAGE_CODE_MAX_LENGTH = 20;

    public UnitEquipment() {
        this.setUeIsFolded(Boolean.FALSE);
        this.setUeIsReserved(Boolean.FALSE);
        this.setUeDamageSeverity(EqDamageSeverityEnum.NONE);
    }

    public static boolean isDamageGetPropagated(Equipment inEquipment) {
        Boolean propogatePrevUseDamages = false;
        EquipClassEnum eqClass = inEquipment.getEqClass();
        if (EquipClassEnum.CONTAINER.equals((Object)eqClass)) {
            propogatePrevUseDamages = !InventoryConfig.CLEAR_CTR_DAMAGES.isOn(ContextHelper.getThreadUserContext());
        } else if (EquipClassEnum.CHASSIS.equals((Object)eqClass)) {
            propogatePrevUseDamages = !InventoryConfig.CLEAR_CHS_DAMAGES.isOn(ContextHelper.getThreadUserContext());
        } else if (EquipClassEnum.ACCESSORY.equals((Object)eqClass)) {
            propogatePrevUseDamages = !InventoryConfig.CLEAR_ACC_DAMAGES.isOn(ContextHelper.getThreadUserContext());
        }
        return propogatePrevUseDamages;
    }

    public boolean hasNotBeenDetached() {
        return this.getUnitEquipment() != null;
    }

    public Collection<Unit> getUnitsToDetachFrom() {
        if (EqUnitRoleEnum.CARRIAGE.equals((Object)this.getUnitEqRole())) {
            return this.getAttachedUnits(EqUnitRoleEnum.PRIMARY, EqUnitRoleEnum.PAYLOAD);
        }
        if (EqUnitRoleEnum.PRIMARY.equals((Object)this.getUnitEqRole())) {
            Collection<Unit> attachedUnits = this.getAttachedUnits(EqUnitRoleEnum.PAYLOAD);
            if (this.getUnitRelatedUnit() != null) {
                attachedUnits.add(this.getUnitRelatedUnit());
            }
            return attachedUnits;
        }
        return this.getUnitRelatedUnit() == null ? Collections.emptyList() : Arrays.asList(this.getUnitRelatedUnit());
    }

    public void detach() throws BizViolation {
        this.thisUnit().getUnitNode().detach();
    }

    protected void detach(Unit inFromUnit, String inEventNote) throws BizViolation {
        this.detach(inFromUnit, inEventNote, true);
    }

    protected void detach(String inEventNote, Boolean inEventRecordAllowed) throws BizViolation {
        this.thisUnit().getUnitNode().detach(inEventNote, inEventRecordAllowed, true);
    }

    protected void detach(String inEventNote) throws BizViolation {
        this.detach(inEventNote, true);
    }

    public void detachFromInventory(Unit inFromUnit) throws BizViolation {
        if (!UnitVisitStateEnum.ACTIVE.equals((Object)this.getUnitVisitState())) {
            throw BizViolation.create((IPropertyKey)IInventoryPropertyKeys.UNIT_NOT_ACTIVE_CANNOT_DETACH_EQ, null, (Object)this);
        }
        this.detach(inFromUnit, null, true);
    }

    protected void detach(Unit inFromUnit, String inEventNote, Boolean inEventRecordAllowed) throws BizViolation {
        UnitNode fromUnitNode = inFromUnit == null ? null : inFromUnit.getUnitNode();
        this.thisUnit().getUnitNode().detach(new UnlinkContext((INodeLinked)fromUnitNode, inEventNote, inEventRecordAllowed.booleanValue()));
    }

    public Boolean getUnitIsStoragePaid() {
        UnitFacilityVisit ufv;
        Boolean isStoragePaid = Boolean.TRUE;
        Facility facility = ContextHelper.getThreadFacility();
        if (facility == null) {
            Unit unit = this.thisUnit();
            ufv = unit.getUnitActiveUfvNowActive();
        } else {
            ufv = this.thisUnit().getUfvForFacilityLiveOnly(facility);
        }
        if (ufv == null) {
            return Boolean.FALSE;
        }
        IUnitStorageManager usm = (IUnitStorageManager) Roastery.getBean((String)"unitStorageManager");
        try {
            usm.validateIsStorageOwed(ufv, STORAGE);
        }
        catch (BizViolation inBizViolation) {
            isStoragePaid = Boolean.FALSE;
        }
        return isStoragePaid;
    }

    @Deprecated
    public Boolean getUeIsStoragePaid() {
        return this.getUnitIsStoragePaid();
    }

    public Boolean getUnitIsLineStoragePaid() {
        UnitFacilityVisit ufv;
        Boolean isLineStoragePaid = Boolean.TRUE;
        Facility facility = ContextHelper.getThreadFacility();
        if (facility == null) {
            Unit unit = this.thisUnit();
            ufv = unit.getUnitActiveUfvNowActive();
        } else {
            ufv = this.thisUnit().getUfvForFacilityLiveOnly(facility);
        }
        if (ufv == null) {
            return Boolean.FALSE;
        }
        IUnitStorageManager usm = (IUnitStorageManager)Roastery.getBean((String)"unitStorageManager");
        try {
            usm.validateIsStorageOwed(ufv, ChargeableUnitEventTypeEnum.LINE_STORAGE.getKey());
        }
        catch (BizViolation inBizViolation) {
            isLineStoragePaid = Boolean.FALSE;
        }
        return isLineStoragePaid;
    }

    @Deprecated
    public Boolean getUeIsLineStoragePaid() {
        return this.getUnitIsLineStoragePaid();
    }

    public UnitEquipDamageItem addDamageItem(EquipDamageType inDamageType, EqComponent inDmgComponent, EqDamageSeverityEnum inDmgSeverity, Date inDmgReported, Date inDmgRepaired) throws BizViolation {
        UnitEquipDamages damages = this.ensureDamages();
        UnitEquipDamageItem damageItem = damages.addDamageItem(inDamageType, inDmgComponent, inDmgSeverity, inDmgReported, inDmgRepaired);
        this.calculateDenormalizedDamageStatus();
        return damageItem;
    }

    public UnitEquipDamageItem addDamageItem(UnitEquipDamageItem inDamageItem) throws BizViolation {
        UnitEquipDamages damages = this.getUeDamages();
        if (damages == null) {
            damages = UnitEquipDamages.create();
            this.attachDamages(damages);
        }
        UnitEquipDamageItem damageItem = damages.addDamageItem(inDamageItem);
        this.calculateDenormalizedDamageStatus();
        return damageItem;
    }

    public static IMetafieldId getCtrDeclaredIbCarrierMf() {
        return UnitField.getQualifiedField(UnitField.UNIT_DECLARED_IB_CV_DETAIL, "Unit");
    }

    public Object getUnitDamageVao() {
        UnitEquipDamages damages = this.getUeDamages();
        return damages == null ? null : damages.getDmgsDamageItemsVao();
    }

    public Object getUeDamageVao() {
        return this.getUnitDamageVao();
    }

    public Object getMobileDamagesVao() {
        UnitEquipDamages damages = this.getUeDamages();
        return damages == null ? null : damages.getMobileDmgsDamageItemsVao();
    }

    private static void createChgStorageEvent(UnitFacilityVisit inNewCtrUfv, Unit inNewCtrUnit) {
        IEventType resolvedEventType;
        if (inNewCtrUfv == null || inNewCtrUnit == null) {
            return;
        }
//        boolean allowCreation = UnitEventExtractManager.validateStorageSetting(inNewCtrUfv.getUfvUnit().getUnitEqRole());
//        if (!allowCreation) {
//            return;
//        }
//        EventManager em = (EventManager)Roastery.getBean((String)"eventManager");
//        IEvent event = em.getMostRecentEventByType(resolvedEventType = IEventType.resolveIEventType((IEventType)EventEnum.UNIT_CREATE), (IServiceable)inNewCtrUnit);
//        if (event != null) {
//            ChargeableUnitEvent cue = UnitEventExtractManager.createStorageEventWhenUnitStripOrStuff(inNewCtrUfv, inNewCtrUnit, event, null, null);
//            cue.setBexuEventType(ChargeableUnitEventTypeEnum.STORAGE.getKey());
//            cue.setBexuEventStartTime(event.getEvntAppliedDate());
//            cue.setBexuEventEndTime(null);
//        }
    }

    private static void createChgLineStorageEvent(UnitFacilityVisit inNewCtrUfv, Unit inNewCtrUnit) {
        IEventType resolvedEventType;
        if (inNewCtrUfv == null || inNewCtrUnit == null) {
            return;
        }
        EventManager em = (EventManager)Roastery.getBean((String)"eventManager");
//        IEvent event = em.getMostRecentEventByType(resolvedEventType = IEventType.resolveIEventType((IEventType) EventEnum.UNIT_CREATE), (IServiceable)inNewCtrUnit);
//        if (event != null) {
//            UserContext userContext = ContextHelper.getThreadUserContext();
//            if (UnitEventExtractManager.validateLineStorageSetting(inNewCtrUfv.getUfvUnit().getUnitEqRole())) {
//                ChargeableUnitEvent cue = UnitEventExtractManager.createStorageEventWhenUnitStripOrStuff(inNewCtrUfv, inNewCtrUnit, event, null, null);
//                cue.setBexuEventType(ChargeableUnitEventTypeEnum.LINE_STORAGE.getKey());
//                cue.setBexuEventStartTime(event.getEvntAppliedDate());
//                cue.setBexuEventEndTime(null);
//            }
//        }
    }

    public void removeDamages() {
        UnitEquipDamages damages = this.getUnitDamages();
        if (damages != null) {
            this.setUeDamages(null);
            HibernateApi.getInstance().delete((Object)damages);
            this.setUeDamageSeverity(EqDamageSeverityEnum.NONE);
        }
    }

    public void setUnitDamageVao(@Nullable Object inValue) {
        if (inValue != null && !IValueHolder[].class.isAssignableFrom(inValue.getClass())) {
            throw BizFailure.create((String)("Wrong class sent as input to setUeDamageVao. class = " + inValue.getClass()));
        }
        this.attachUeDamages((IValueHolder[])inValue);
 //       DamageSparcsCodeManager.setSparcsDamageCodesFor(this);
    }

    @Deprecated
    public void setUeDamageVao(Object inValue) {
        this.setUnitDamageVao(inValue);
    }

    public void setMobileDamagesVao(Object inValue) {
        if (inValue != null && !IValueHolder[].class.isAssignableFrom(inValue.getClass())) {
            throw BizFailure.create((String)("Wrong class sent as input to setUeDamageVao. class = " + inValue.getClass()));
        }
        IValueHolder[] allDamageItems = UnitEquipment.concatArrays((IValueHolder[])inValue, this.getRepairedDamageItemsVao());
        this.attachUeDamages(allDamageItems);
//        DamageSparcsCodeManager.setSparcsDamageCodesFor(this);
    }

    private IValueHolder[] getRepairedDamageItemsVao() {
        ArrayList items;
        ArrayList arrayList = items = this.getUeDamages() == null ? null : (ArrayList) this.getUeDamages().getDmgsItems();
        if (items == null) {
            items = new ArrayList();
            if (this.getUeDamages() != null) {
                this.getUeDamages().setDmgsItems(items);
            }
        }
        ArrayList<ValueObject> dmgList = new ArrayList<ValueObject>();
        for (Object damageItem : items) {
            if (((UnitEquipDamageItem)damageItem).getDmgitemRepaired() == null) continue;
            ValueObject vao = ((UnitEquipDamageItem)damageItem).getValueObject();
            vao.setFieldValue(IInventoryField.DMGITEM_TYPE, (Object)((UnitEquipDamageItem)damageItem).getDmgitemType().getEqdmgtypId());
            vao.setFieldValue(UnitField.DMGITEM_TYPE_DESC, (Object)((UnitEquipDamageItem)damageItem).getDmgitemType().getEqdmgtypDescription());
            vao.setFieldValue(IInventoryField.DMGITEM_COMPONENT, (Object)((UnitEquipDamageItem)damageItem).getDmgitemComponent().getEqcmpId());
            vao.setFieldValue(UnitField.DMGITEM_CMP_DESC, (Object)((UnitEquipDamageItem)damageItem).getDmgitemComponent().getEqcmpDescription());
            vao.setFieldValue(IInventoryField.DMGITEM_DESCRIPTION, (Object)((UnitEquipDamageItem)damageItem).getDmgitemDescription());
            vao.setFieldValue(IArgoRefField.EQDMGTYP_GKEY, (Object)((UnitEquipDamageItem)damageItem).getDmgitemType().getEqdmgtypGkey());
            vao.setFieldValue(IArgoRefField.EQCMP_GKEY, (Object)((UnitEquipDamageItem)damageItem).getDmgitemComponent().getEqcmpGkey());
            dmgList.add(vao);
        }
      //  return (IValueHolder[])dmgList.toArray((T[])new ValueObject[dmgList.size()]);
        return null;
    }

    public BizFieldList getBizFieldList() {
        return BIZ_FIELDS;
    }

    public UnitEquipDamages ensureDamages() {
        UnitEquipDamages damages = this.getUeDamages();
        if (damages == null) {
            damages = new UnitEquipDamages();
            this.setUeDamages(damages);
            damages.attachDamagesToEntity(this, IInventoryField.UE_DAMAGES);
        }
        return damages;
    }

    private void attachUeDamages(IValueHolder[] inNewDamageItems) {
        UnitEquipDamages newDamages = new UnitEquipDamages();
        if (inNewDamageItems != null) {
            newDamages.setDmgsDamageItemsVao(inNewDamageItems);
        }
        this.attachDamages(newDamages);
    }

    public void attachDamages(UnitEquipDamages inNewDamages) {
        List newDamageItems;
        EquipClassEnum eqClass;
        if (inNewDamages != null && (eqClass = this.getUeEquipment().getEqClass()) != null && (newDamageItems = inNewDamages.getDmgsItems()) != null && !newDamageItems.isEmpty()) {
            for (Object newDamageItem : newDamageItems) {
                EquipDamageType eqDmgType = ((UnitEquipDamageItem)newDamageItem).getDmgitemType();
                EquipClassEnum dmgItemEqClass = eqDmgType.getEqdmgtypEqClass();
                if (dmgItemEqClass == null || dmgItemEqClass.equals((Object)eqClass)) continue;
                throw BizFailure.create((IPropertyKey)IInventoryPropertyKeys.DAMAGE_CANNOT_BE_ATTACHED_AS_INCOMPATIBLE_DMG_TYPE, null, (Object)dmgItemEqClass, (Object)eqClass);
            }
        }
        UnitEquipDamages currentDamages = this.ensureDamages();
        ArrayList<UnitEquipDamageItem> oldDamageItems = new ArrayList<UnitEquipDamageItem>(currentDamages.getDmgsItems());
        List newDamageItems2 = inNewDamages == null ? null : inNewDamages.getDmgsItems();
        ArrayList<DamageEvent> damageEvents = new ArrayList<DamageEvent>();
        if (newDamageItems2 != null) {
            for (Object newDamageItem : newDamageItems2) {
                if (((UnitEquipDamageItem)newDamageItem).getDmgitemGkey() == null) {
                    currentDamages.addDamageItem(((UnitEquipDamageItem)newDamageItem));
                    damageEvents.add(new DamageInsertEvent(((UnitEquipDamageItem)newDamageItem)));
                    continue;
                }
                UnitEquipDamageItem oldDamageItem = UnitEquipment.findDamageItem(oldDamageItems, ((UnitEquipDamageItem)newDamageItem).getDmgitemGkey());
                if (oldDamageItem == null) continue;
                UnitEquipDamageItem clonedOldDmgItem = oldDamageItem.deepClone();
                if (((UnitEquipDamageItem)newDamageItem).getDmgitemReported() == null) {
                    ((UnitEquipDamageItem)newDamageItem).setDmgitemReported(oldDamageItem.getDmgitemReported());
                }
                if (((UnitEquipDamageItem)newDamageItem).getDmgitemRepaired() == null) {
                    ((UnitEquipDamageItem)newDamageItem).setDmgitemRepaired(oldDamageItem.getDmgitemRepaired());
                }
                if (((UnitEquipDamageItem)newDamageItem).getDmgitemDescription() == null) {
                    ((UnitEquipDamageItem)newDamageItem).setDmgitemDescription(oldDamageItem.getDmgitemDescription());
                }
                oldDamageItem.update(((UnitEquipDamageItem)newDamageItem));
                oldDamageItems.remove(oldDamageItem);
                damageEvents.add(new DamageUpdateEvent(clonedOldDmgItem, ((UnitEquipDamageItem)newDamageItem)));
            }
        }
        for (UnitEquipDamageItem item : oldDamageItems) {
            currentDamages.deleteDamageItem(item);
            damageEvents.add(new DamageDeleteEvent(item));
        }
        if (currentDamages.getDmgsItems().isEmpty()) {
            this.setUeDamages(null);
            this.setUeDamageSeverity(EqDamageSeverityEnum.NONE);
        } else {
            this.setUeDamageSeverity(currentDamages.calculateDamageSeverity());
        }
        for (DamageEvent dmgEvent : damageEvents) {
            dmgEvent.record();
        }
    }

    @Nullable
    private static UnitEquipDamageItem findDamageItem(List<UnitEquipDamageItem> inDamageItems, Long inGkey) {
        for (UnitEquipDamageItem damageItem : inDamageItems) {
            if (!inGkey.equals(damageItem.getDmgitemGkey())) continue;
            return damageItem;
        }
        return null;
    }

    public void updateSparcsDamageCode(String inSparcsDamageCodes) {
        if (inSparcsDamageCodes != null && inSparcsDamageCodes.length() > 20) {
            inSparcsDamageCodes = inSparcsDamageCodes.substring(0, 20);
        }
        this.setUeSparcsDamageCode(inSparcsDamageCodes);
    }

    private static IMetafieldId getUnitEquipmentGdsBlMfId() {
        return UnitField.getQualifiedField(IArgoBizMetafield.GDSBL_BILLS_OF_LADING, "Unit");
    }

    public EquipmentState ensureEquipmentState() {
        EquipmentState eqs = this.getUeEquipmentState();
        return eqs != null ? eqs : EquipmentState.createEquipmentStateFromUe(this);
    }

    @Nullable
    public ScopedBizUnit getUeEqOperator() {
        EquipmentState eqs = this.getUeEquipmentState();
        return eqs == null ? null : eqs.getEqsEqOperator();
    }

    @Nullable
    public EquipGrade getUeGradeID() {
        if (this.getUnitGradeID() != null) {
            return this.getUnitGradeID();
        }
        EquipGrade grade = this.getEqsGradeID();
        return grade != null && grade.getGradeIDPersisted() ? grade : null;
    }

    public void switchOrderItem(EqBaseOrderItem inNewEqoi) {
        EqBaseOrderItem oldEqoi = this.getUeDepartureOrderItem();
        if (oldEqoi == null) {
            throw BizFailure.create((String)"expected this UE to currently point to an order item, but it does not.");
        }
        if (inNewEqoi == null) {
            throw BizFailure.create((String)"expected non-null inNewEqoi");
        }
        if (inNewEqoi.getEqboiOrder() != oldEqoi.getEqboiOrder()) {
            throw BizFailure.create((String)"expected inNewEqoi to belong to the UE's existing Order");
        }
        this.setUeOrderItem(inNewEqoi);
    }

    public void setUeOrderItem(EqBaseOrderItem inUeOrderItem) {
        if (inUeOrderItem != null) {
            EquipmentOrderSubTypeEnum subType = inUeOrderItem.getSubType();
            if (EquipmentOrderSubTypeEnum.ERO.equals((Object)subType)) {
                this.setUeArrivalOrderItem(inUeOrderItem);
            } else {
                this.setUeDepartureOrderItem(inUeOrderItem);
            }
        }
    }

    public boolean isAccessoryAttached() {
        return this.getUeAccessory() != null;
    }

    @Override
    @Nullable
    public UnitEquipment getUeAccessory() {
        if (EqUnitRoleEnum.CARRIAGE.equals((Object)this.getUeEqRole())) {
            return this.thisUnit().getUeInRole(EqUnitRoleEnum.ACCESSORY_ON_CHS);
        }
        if (EqUnitRoleEnum.PRIMARY.equals((Object)this.getUeEqRole())) {
            return this.thisUnit().getUeInRole(EqUnitRoleEnum.ACCESSORY);
        }
        return null;
    }

    public void calculateDenormalizedDamageStatus() {
        this.calculateDenormalizedDamageStatus(true);
    }

    private void calculateDenormalizedDamageStatus(boolean inRecordDamageEvent) {
        UnitEquipDamages damages = this.getUeDamages();
        EqDamageSeverityEnum oldDmgSeverity = this.getUeDamageSeverity();
        if (damages != null) {
            this.setUeDamageSeverity(damages.calculateDamageSeverity());
        } else {
            this.setUeDamageSeverity(EqDamageSeverityEnum.NONE);
        }
        EqDamageSeverityEnum newDmgSeverity = this.getUeDamageSeverity();
        if (inRecordDamageEvent) {
            this.recordDamageChangeEvent(oldDmgSeverity, newDmgSeverity);
        }
    }

    private void recordDamageChangeEvent(EqDamageSeverityEnum inOldDmgSeverity, EqDamageSeverityEnum inNewDmgSeverity) {
        boolean oldDamages = this.isDamaged(inOldDmgSeverity);
        boolean newDamages = this.isDamaged(inNewDmgSeverity);
        Date reportedOrRepairDate = null;
        EventEnum eventType = null;
        if (!oldDamages && newDamages) {
            reportedOrRepairDate = this.getFirstDamageItemReportedDate() == null ? reportedOrRepairDate : this.getFirstDamageItemReportedDate();
            eventType = EventEnum.UNIT_DAMAGED;
        } else if (oldDamages && !newDamages) {
            reportedOrRepairDate = this.getFirstDamageItemRepairedDate() == null ? reportedOrRepairDate : this.getFirstDamageItemRepairedDate();
            eventType = EventEnum.UNIT_REPAIRED;
        } else if (oldDamages && newDamages) {
            reportedOrRepairDate = this.getFirstDamageItemReportedDate() == null ? reportedOrRepairDate : this.getFirstDamageItemReportedDate();
            eventType = EventEnum.UNIT_DAMAGES_UPDATED;
        }
        if (eventType != null) {
            this.recordUnitEvent((IEventType)eventType, null, null);
        }
    }

    @Nullable
    private Date getFirstDamageItemRepairedDate() {
        UnitEquipDamages damages = this.getUeDamages();
        if (damages != null && damages.getDamageItemsIterator().hasNext()) {
            UnitEquipDamageItem damageItem = damages.getDamageItemsIterator().next();
            return damageItem.getDmgitemRepaired();
        }
        return null;
    }

    @Nullable
    private Date getFirstDamageItemReportedDate() {
        UnitEquipDamages damages = this.getUeDamages();
        if (damages != null && damages.getDamageItemsIterator().hasNext()) {
            UnitEquipDamageItem damageItem = damages.getDamageItemsIterator().next();
            return damageItem.getDmgitemReported();
        }
        return null;
    }

    public boolean isDamaged(EqDamageSeverityEnum inDamageSeverity) {
        return inDamageSeverity != null && !EqDamageSeverityEnum.NONE.equals((Object)inDamageSeverity) && !EqDamageSeverityEnum.REPAIRED.equals((Object)inDamageSeverity);
    }

    public static void moveDamagesFromEqsToUe(Serializable inOprGkey) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"EquipmentState").addDqField(IInventoryField.EQS_EQUIPMENT).addDqField(IInventoryField.EQS_OBSOLETE_DAMAGES).addDqField(IInventoryField.EQS_OBSOLETE_DAMAGE_SEVERITY).addDqPredicate(PredicateFactory.eq((IMetafieldId)IInventoryField.EQS_OPERATOR, (Object)inOprGkey)).addDqPredicate(PredicateFactory.isNotNull((IMetafieldId)IInventoryField.EQS_OBSOLETE_DAMAGES));
        IQueryResult result = HibernateApi.getInstance().findValuesByDomainQuery(dq);
        IUnitFinder uf = (IUnitFinder)Roastery.getBean((String)"unitFinder");
        for (int i = 0; i < result.getTotalResultCount(); ++i) {
            Serializable eqGkey = (Serializable)result.getValue(i, IInventoryField.EQS_EQUIPMENT);
            Serializable dmgsGkey = (Serializable)result.getValue(i, IInventoryField.EQS_OBSOLETE_DAMAGES);
            EqDamageSeverityEnum severity = (EqDamageSeverityEnum)((Object)result.getValue(i, IInventoryField.EQS_OBSOLETE_DAMAGE_SEVERITY));
            Serializable ueGkey = uf.findMostRecentUeGkey(inOprGkey, eqGkey);
            UnitEquipDamages damages = (UnitEquipDamages)HibernateApi.getInstance().load(UnitEquipDamages.class, dmgsGkey);
            UnitEquipment ue = (UnitEquipment)HibernateApi.getInstance().load(Unit.class, ueGkey);
            if (ue == null) continue;
            damages.setDmgsOwnerEntityGkey(null);
            damages.setDmgsOwnerEntityName(null);
            damages.attachDamagesToEntity(ue, IInventoryField.UE_DAMAGES);
            if (severity == null) {
                severity = damages.calculateDamageSeverity();
            }
            ue.setUeDamageSeverity(severity);
        }
    }

    public boolean isDepartureOrder() {
        return this.getUeDepartureOrderItem() != null;
    }

    private void recordInsertEvent(UnitEquipDamageItem inNewDamageItem) {
        FieldChanges fieldChanges = inNewDamageItem.getNewFieldChanges();
        if (fieldChanges.getFieldChangeCount() != 0) {
            if (inNewDamageItem.getDmgitemRepaired() != null) {
                this.recordUnitEvent((IEventType)EventEnum.UNIT_REPAIRED, fieldChanges, UnitEquipment.getMessage(IInventoryPropertyKeys.DAMAGE_REPAIRED, new String[]{inNewDamageItem.getDmgitemSeverity().getName(), inNewDamageItem.getDmgitemComponent().getEqcmpId(), inNewDamageItem.getDmgitemType().getEqdmgtypId()}));
            } else {
                String notes = UnitEquipment.getMessage(IInventoryPropertyKeys.DAMAGE_INSERTED, new String[]{inNewDamageItem.getDmgitemSeverity().getName(), inNewDamageItem.getDmgitemComponent().getEqcmpId(), inNewDamageItem.getDmgitemType().getEqdmgtypId()});
                this.thisUnit().recordUnitEvent((IEventType)EventEnum.UNIT_DAMAGED, fieldChanges, notes, null, (IEntity)this);
            }
        }
    }

    public void recordDeleteEvent(UnitEquipDamageItem inDeletedDamageItem) {
        FieldChanges fieldChanges = inDeletedDamageItem.getDeletedFieldChanges();
        if (fieldChanges.getFieldChangeCount() != 0) {
            this.recordUnitEvent((IEventType)EventEnum.UNIT_DAMAGES_DELETED, fieldChanges, UnitEquipment.getMessage(IInventoryPropertyKeys.DAMAGE_DELETED, new String[]{inDeletedDamageItem.getDmgitemSeverity().getName(), inDeletedDamageItem.getDmgitemComponent().getEqcmpId(), inDeletedDamageItem.getDmgitemType().getEqdmgtypId()}));
        }
    }

    private void recordUpdateEvent(UnitEquipDamageItem inOldDamageItem, UnitEquipDamageItem inNewDamageItem) {
        FieldChanges fieldChanges = inNewDamageItem.getUpdatedFieldChanges(inOldDamageItem);
        if (fieldChanges.getFieldChangeCount() != 0) {
            if (inOldDamageItem.getDmgitemRepaired() == null && inNewDamageItem.getDmgitemRepaired() != null) {
                this.recordUnitEvent((IEventType)EventEnum.UNIT_REPAIRED, fieldChanges, UnitEquipment.getMessage(IInventoryPropertyKeys.DAMAGE_REPAIRED, new String[]{inNewDamageItem.getDmgitemSeverity().getName(), inNewDamageItem.getDmgitemComponent().getEqcmpId(), inNewDamageItem.getDmgitemType().getEqdmgtypId()}));
            } else {
                this.recordUnitEvent((IEventType)EventEnum.UNIT_DAMAGES_UPDATED, fieldChanges, UnitEquipment.getMessage(IInventoryPropertyKeys.DAMAGE_UPDATED, new String[]{inNewDamageItem.getDmgitemSeverity().getName(), inNewDamageItem.getDmgitemComponent().getEqcmpId(), inNewDamageItem.getDmgitemType().getEqdmgtypId()}));
            }
        }
    }

    private static <T> T[] concatArrays(@Nullable T[] inArr1, @Nullable T[] inArr2) {
        if (inArr1 != null) {
            if (inArr2 != null) {
                Object[] concat = (Object[]) Array.newInstance(inArr1.getClass().getComponentType(), inArr1.length + inArr2.length);
                System.arraycopy(inArr1, 0, concat, 0, inArr1.length);
                System.arraycopy(inArr2, 0, concat, inArr1.length, inArr2.length);
                return (T[]) concat;
            }
            return inArr1;
        }
        return inArr2;
    }

    public boolean hasOneUnitInPayloadRole() {
        return this.getPayloadCount() == 1;
    }

    public Collection<Unit> getPayloadUnits() {
        return this.getAttachedUnits(EqUnitRoleEnum.PAYLOAD);
    }

    public int getPayloadCount() {
        return this.getPayloadUnits().size();
    }

    public int getCtrCount() {
        return this.getCtrUnits().size();
    }

    public int getAccessoryCount() {
        return this.getAccessoryUnits().size();
    }

    public int getAccessoryOnChsCount() {
        return this.getAccessoryOnChsUnits().size();
    }

    public Collection<Unit> getAccessoryUnits() {
        return this.isChassis() ? this.getAttachedUnits(EqUnitRoleEnum.ACCESSORY_ON_CHS) : this.getAttachedUnits(EqUnitRoleEnum.ACCESSORY);
    }

    public Collection<Unit> getCtrUnits() {
        return this.getAttachedUnits(EquipClassEnum.CONTAINER);
    }

    public Collection<Unit> getAttachedUnits(EquipClassEnum inEqClass) {
        Collection<Unit> units = this.ensureUnitSet();
        HashSet<Unit> relatedUnits = new HashSet<Unit>();
        for (Unit unit : units) {
            if (unit.getUnitRelatedUnit() == null || !unit.getUnitRelatedUnit().equals(this) || unit.getUnitEquipment() == null || !inEqClass.equals((Object)unit.getUnitEquipment().getEqClass())) continue;
            relatedUnits.add(unit);
        }
        return relatedUnits;
    }

    public Collection<Unit> getAccessoryOnChsUnits() {
        if (this.isChassis()) {
            return this.getAttachedUnits(EqUnitRoleEnum.ACCESSORY_ON_CHS);
        }
        Unit chsUnit = this.getCarriageUnit();
        if (chsUnit == null) {
            return Collections.emptyList();
        }
        return chsUnit.getAttachedUnits(EqUnitRoleEnum.ACCESSORY_ON_CHS);
    }

    public Unit getAttachedUnit(Equipment inEquipment) {
        if (inEquipment == null) {
            return null;
        }
        if (this.getUnitEquipment() != null && inEquipment.equals((Object)this.getUnitEquipment())) {
            return this.thisUnit();
        }
        Collection<Unit> units = this.ensureUnitSet();
        UnitDO eqUnit = null;
        for (Unit unit : units) {
            if (unit.getUnitEquipment() == null || !inEquipment.equals((Object)unit.getUnitEquipment())) continue;
            if (EqUnitRoleEnum.CARRIAGE.equals((Object)unit.getUnitEqRole())) {
                if (this.getUnitRelatedUnit() == null || !this.getUnitRelatedUnit().equals(unit)) continue;
                eqUnit = unit;
                break;
            }
            if (unit.getUnitRelatedUnit() == null || !unit.getUnitRelatedUnit().equals(this)) continue;
            eqUnit = unit;
            break;
        }
        if (eqUnit != null && UnitVisitStateEnum.DEPARTED.equals((Object)eqUnit.getUnitVisitState())) {
            eqUnit = null;
        }
        return (Unit) eqUnit;
    }

    public boolean isChassis() {
        Equipment primaryEq = this.getUnitEquipment();
        return primaryEq != null && EquipClassEnum.CHASSIS.equals((Object)primaryEq.getEqClass());
    }

    public boolean isInternalChassis() {
        Equipment primaryEq = this.getUnitEquipment();
        return primaryEq != null && primaryEq.getEqEquipType() != null && (primaryEq.getEqEquipType().isBombCart() || primaryEq.getEqEquipType().isCassette());
    }

    public int getAttachedUnitsCount() {
        return this.getAttachedUnits(new EqUnitRoleEnum[0]).size();
    }

    public int getAttachedUnitsCount(EqUnitRoleEnum inRole) {
        return this.getAttachedUnits(inRole).size();
    }

    public int getAttachedUnitsCount(EqUnitRoleEnum[] inRoles) {
        return this.getAttachedUnits(inRoles).size();
    }

    public Collection<Unit> getAttachedUnits(EqUnitRoleEnum... inRoles) {
        Collection<Unit> units = this.ensureUnitSet();
        HashSet<Unit> relatedUnits = new HashSet<Unit>();
        for (Unit unit : units) {
            if (unit.getUnitRelatedUnit() == null || !unit.getUnitRelatedUnit().equals(this.thisUnit())) continue;
            if (inRoles != null && inRoles.length > 0) {
                for (EqUnitRoleEnum role : inRoles) {
                    if (!role.equals((Object)unit.getUnitEqRole())) continue;
                    relatedUnits.add(unit);
                }
                continue;
            }
            relatedUnits.add(unit);
        }
        return relatedUnits;
    }

    protected String getEventNotes() {
        String unitId = this.getUnitEquipment() == null ? this.getUnitId() : this.getUnitEquipment().getEqIdFull();
        return UnitEquipment.getMessage(IArgoPropertyKeys.RELATED_ENTITY_EVENT_NOTES, new Object[]{unitId, this.getUnitEqRole()});
    }

    public static String getMessage(IPropertyKey inPropertyKey, Object[] inParms) {
        IMessageTranslatorProvider translatorProvider = (IMessageTranslatorProvider) PortalApplicationContext.getBean((String)"messageTranslatorProvider");
        IMessageTranslator translator = translatorProvider.getMessageTranslator(ContextHelper.getThreadUserContext().getUserLocale());
        return translator.getMessage(inPropertyKey, inParms);
    }

    @Nullable
    public UnitNode findUnitByEquipment(IEqLinkContext<UnitNode, Unit, Equipment> inLinkContext, Equipment inEquipment) {
        Collection<Unit> units;
        IUnitFinder uf = (IUnitFinder)Roastery.getBean((String)"unitFinder");
        HashSet<UnitVisitStateEnum> states = new HashSet<UnitVisitStateEnum>(Arrays.asList(new UnitVisitStateEnum[]{this.getUnitVisitState()}));
        if (inLinkContext.isMatchActiveUnit()) {
            states.add(UnitVisitStateEnum.ACTIVE);
        }
//        return (units = uf.findAllUnitsUsingEqInAnyRole(this.getUnitComplex(), inEquipment, states.toArray((T[])new UnitVisitStateEnum[states.size()]))) == null || units.isEmpty() ? null : units.iterator().next().getUnitNode();
        return null;
    }

    protected void validateUnitAttach(IEqLinkContext<UnitNode, Unit, Equipment> inContext) throws BizViolation {
        EqUnitRoleEnum role = inContext.getRole();
        if (inContext.isStrictlyValidated()) {
            if (inContext.validateLocation()) {
                if (EqUnitRoleEnum.CARRIAGE.equals((Object)role)) {
                    this.validateChsInSameLocType(inContext);
                } else if (EqUnitRoleEnum.PAYLOAD.equals((Object)role)) {
                    this.validatePayloadLocation(inContext);
                }
            }
            this.validateUnitState(inContext.getReturnNode().getEquipment());
        }
        this.validateUnitsAreInSameFacility(inContext.getReturnNode().getUnit());
        if (EqUnitRoleEnum.CARRIAGE.equals((Object)role)) {
            if (inContext.isStrictlyValidated()) {
                InventoryControlUtils.canAttachEquipment(inContext, ((UnitNode)inContext.getNode1()).getUnit(), ((UnitNode)inContext.getNode2()).getEquipment());
            }
            this.validateSlotOnCarriage(inContext);
        }
    }

    private void validatePayloadLocation(IEqLinkContext<UnitNode, Unit, Equipment> inContext) throws BizViolation {
        if (!this.isActive()) {
            throw BizViolation.create((IPropertyKey)IInventoryPropertyKeys.UNITS_NOT_ACTIVE_IN_FACILITY, null, (Object)this.getUnitEquipment().getEqIdFull(), (Object)ContextHelper.getThreadFacility().getFcyId());
        }
        Complex complex = ContextHelper.getThreadComplex();
        Equipment equipment = inContext.getReturnNode().getEquipment();
        Unit bundleUnit = UnitEquipment.getFndr().findActiveUnit(complex, equipment);
        String eqId = equipment.getEqIdFull();
        UnitFacilityVisit ufv = this.getUnitActiveUfvNowActive();
        if (bundleUnit == null) {
            throw BizViolation.create((IPropertyKey)IInventoryPropertyKeys.ATTACH_UNIT_NOT_IN_YARD, null, (Object)eqId, (Object)this.getUnitId());
        }
        UnitFacilityVisit bundleUfv = bundleUnit.getUnitActiveUfvNowActive();
        if (bundleUfv == null) {
            throw BizViolation.create((IPropertyKey)IInventoryPropertyKeys.UNITS_NOT_ACTIVE_IN_FACILITY, null, (Object)eqId, (Object)ContextHelper.getThreadFacility().getFcyId());
        }
        if (ufv != null && !bundleUfv.getUfvTransitState().equals((Object)ufv.getUfvTransitState())) {
            throw BizViolation.create((IPropertyKey)IInventoryPropertyKeys.MASTER_SLAVE_NOT_IN_SAME_TRANSIT_STATE, null, (Object)eqId, (Object)this.getUnitId());
        }
    }

    private void validateChsInSameLocType(IEqLinkContext<UnitNode, Unit, Equipment> inContext) throws BizViolation {
        Equipment equipment = inContext.getReturnNode().getEquipment();
        Unit chsUnit = UnitEquipment.getFndr().findActiveUnit(ContextHelper.getThreadComplex(), equipment);
        UnitFacilityVisit ufv = this.getUnitActiveUfvNowActive();
        if (chsUnit != null) {
            LocPosition eqCurrentPos = chsUnit.findCurrentPosition();
            LocPosition unitCurrentPos = this.findCurrentPosition();
            boolean attachValid = unitCurrentPos.getPosLocType().equals((Object)eqCurrentPos.getPosLocType());
            if (inContext.isHatchClerkValidation()) {
                LocTypeEnum unitCurrentLocType;
                if (!attachValid) {
                    attachValid = WorkInstruction.wiKindAllowsMarriage(ufv) && eqCurrentPos.isYardPosition();
                }
                boolean allowDischargeToChassis = (LocTypeEnum.RAILCAR.equals((Object)(unitCurrentLocType = unitCurrentPos.getPosLocType())) || LocTypeEnum.TRAIN.equals((Object)unitCurrentLocType) || LocTypeEnum.VESSEL.equals((Object)unitCurrentLocType)) && LocTypeEnum.YARD.equals((Object)eqCurrentPos.getPosLocType());
                boolean bl = attachValid = attachValid || allowDischargeToChassis;
            }
            if (!attachValid) {
                throw BizViolation.create((IPropertyKey)IInventoryPropertyKeys.CANNOT_ATTACH_POS_LOC_TYPE_INVALID, null, (Object)chsUnit, (Object)this, (Object)((Object)eqCurrentPos + "/" + (Object)unitCurrentPos));
            }
        }
    }

    void validateSlotOnCarriage(IEqLinkContext<UnitNode, Unit, Equipment> inContext) throws BizViolation {
        Unit ctrUnit = inContext.getMasterUnit();
        Equipment equipment = inContext.getReturnNode().getEquipment();
        String slotOnCarriage = inContext.getSlotOnCarriage();
    }

    void validateUnitsAreInSameFacility(Unit inEqUnit) throws BizViolation {
        if (inEqUnit == null) {
            return;
        }
        UnitFacilityVisit masterUfv = this.getUnitActiveUfv();
        UnitFacilityVisit eqUfv = inEqUnit.getUnitActiveUfv();
        if (masterUfv != null && eqUfv != null && !masterUfv.getUfvFacility().equals((Object)eqUfv.getUfvFacility())) {
            throw BizViolation.create((IPropertyKey)IInventoryPropertyKeys.CANNOT_ATTACH_UNIT_NOT_IN_SAME_FACILITY_AS_MASTER_UNIT, null, (Object)inEqUnit, (Object)eqUfv.getUfvFacility());
        }
    }

    void validateUnitState(Equipment inEq) throws BizViolation {
        UnitFacilityVisit ufv = this.getUnitActiveUfvNowActive();
        if (ufv != null && ufv.isTransitStateBeyond(UfvTransitStateEnum.S60_LOADED)) {
            throw BizViolation.create((IPropertyKey)IInventoryPropertyKeys.ATTACH_NOT_IN_YARD, null, (Object)inEq.getEqIdFull(), (Object)this.getUnitId());
        }
        UnitVisitStateEnum visitState = this.getUnitVisitState();
        if (!UnitVisitStateEnum.ACTIVE.equals((Object)visitState) && !UnitVisitStateEnum.ADVISED.equals((Object)visitState)) {
            throw BizViolation.create((IPropertyKey)IInventoryPropertyKeys.UNITS__WRONG_UNIT_STATE, null, (Object)this.getUnitId(), (Object)"attach");
        }
    }

    @Nullable
    protected Unit createUnitForAttachedEq(Equipment inEquipment) throws BizViolation {
        UnitFacilityVisit masterUfv = this.getUfvForAttachPurpose();
        if (masterUfv == null) {
            LOGGER.error((Object)("Error in fetching master UFV! Equipment to be attached <" + (Object)inEquipment + ">, master unit <" + this + ">"));
            return null;
        }
        if (UfvTransitStateEnum.S99_RETIRED.equals((Object)masterUfv.getUfvTransitState())) {
            LOGGER.error((Object)("Cannot create unit as master ufv is retired<" + (Object)inEquipment + ">, master unit <" + this + ">"));
            return null;
        }
        Complex complex = this.getUnitComplex();
        LocPosition pos = masterUfv.getUfvUnit().findCurrentPosition();
        CarrierVisit outboundCarrier = masterUfv.getUfvIntendedObCv();
        CarrierVisit inboundCarrier = pos.isCarrierPosition() ? masterUfv.getUfvActualIbCv() : CarrierVisit.getGenericCarrierVisit((Complex)complex);
        ScopedBizUnit lineOperator = this.getUnitLineOperator();
        Unit attachedUnit = Unit.createContainerizedUnit("Attach equipment", complex, inboundCarrier, outboundCarrier, inEquipment, lineOperator);
        EquipClassEnum equipClass = inEquipment.getEqClass();
        if (EquipClassEnum.CHASSIS.equals((Object)equipClass) || EquipClassEnum.ACCESSORY.equals((Object)equipClass)) {
            attachedUnit.updateFreightKind(FreightKindEnum.MTY);
        } else {
            attachedUnit.updateFreightKind(this.getUnitFreightKind());
            attachedUnit.updateCategory(this.getUnitCategory());
        }
        if (this.getUnitEqRole() == null) {
            this.setUnitEqRole(EqUnitRoleEnum.PRIMARY);
        }
        this.createUfvForAttachedEq(masterUfv, attachedUnit);
        return attachedUnit;
    }

    @Nullable
    private UnitFacilityVisit getUfvForAttachPurpose() {
        Facility facility;
        UnitFacilityVisit masterUfv = this.getUnitActiveUfv();
        if (masterUfv == null) {
            masterUfv = this.getUnitVisitState() == UnitVisitStateEnum.DEPARTED ? this.findMostRecentHistoryUfv() : this.findProbableInitialUfv();
        }
        Facility facility2 = facility = masterUfv == null ? ContextHelper.getThreadFacility() : masterUfv.getUfvFacility();
        if (facility == null) {
            LOGGER.error((Object)("Unable to determine the facility for unit <" + this + ">, position <" + (Object)this.findCurrentPosition() + ">" + ", master UFV <" + this.getUnitActiveUfv() + ">"));
            return null;
        }
        if (masterUfv == null) {
            masterUfv = this.getUfvForFacilityNewest(facility);
        }
        return masterUfv;
    }

    @Nullable
    private UnitFacilityVisit createUfvForAttachedEq(UnitFacilityVisit inMasterUfv, Unit inAttachedUnit) throws BizViolation {
        UnitFacilityVisit ufv = inAttachedUnit.getUfvForFacilityNewest(inMasterUfv.getUfvFacility());
        if (ufv != null) {
            return ufv;
        }
        CarrierVisit outboundCarrier = inMasterUfv.getUfvIntendedObCv();
        CarrierVisit inboundCarrier = inMasterUfv.getUfvActualIbCv();
        LocPosition inboundPosition = LocPosition.createLocPosition((ILocation)inboundCarrier, null, null);
        Facility fcy = inMasterUfv.getUfvFacility();
        ufv = UnitFacilityVisit.createUnitFacilityVisit(inAttachedUnit, fcy, inboundPosition, outboundCarrier);
        LocPosition pos = this.findCurrentPosition();
        RectifyParms parms = new RectifyParms();
        UfvTransitStateEnum transitState = inMasterUfv.getUfvTransitState();
        parms.setUfvTransitState(transitState);
        if (UfvTransitStateEnum.S70_DEPARTED.equals((Object)transitState) || UfvTransitStateEnum.S60_LOADED.equals((Object)transitState)) {
            Date timeIn = inMasterUfv.getUfvTimeIn();
            Date timeOut = inMasterUfv.getUfvTimeOut();
            if (timeIn != null) {
                if (timeOut != null && timeIn.after(timeOut)) {
                    Date timeCreated = inMasterUfv.getUfvCreateTime();
                    LOGGER.warn((Object) String.format("UFV [%s] has timeIn [%s] after timeOut[%s]. Will use timeCreated[%s] as timeIn value for new UFV", inMasterUfv, timeIn.toString(), timeOut.toString(), timeCreated.toString()));
                    parms.setTimeIn(timeCreated);
                } else {
                    parms.setTimeIn(timeIn);
                }
            }
            parms.setTimeOut(timeOut);
            parms.setTimeComplete(inMasterUfv.getUfvTimeComplete());
            parms.setTimeOfLastMove(inMasterUfv.getUfvTimeOfLastMove());
        }
        if (UfvTransitStateEnum.S60_LOADED.equals((Object)transitState)) {
            parms.setTimeOfLoading(inMasterUfv.getUfvTimeOfLoading());
        }
        if (UfvTransitStateEnum.S30_ECIN.equals((Object)transitState) || UfvTransitStateEnum.S40_YARD.equals((Object)transitState) || UfvTransitStateEnum.S50_ECOUT.equals((Object)transitState)) {
            parms.setTimeOfLastMove(inMasterUfv.getUfvTimeOfLastMove());
        }
        parms.setUnitVisitState(this.getUnitVisitState());
        if (!inAttachedUnit.getUnitEquipment().getEqEquipType().isChassis()) {
            parms.setVisibleInSparcs(Boolean.FALSE);
        }
        parms.setPosition(pos);
        parms.setGenerateCue(false);
        if (UnitFacilityVisit.getTransitStateValue(inMasterUfv.getUfvTransitState()) < 3) {
            parms.setIbCv(inboundCarrier);
        }
        if (UfvTransitStateEnum.S70_DEPARTED.equals((Object)transitState)) {
            parms.setObCv(inMasterUfv.getUfvActualObCv());
        }
        ufv.rectify(parms);
        if (ufv.getUfvTimeIn() != null && ufv.getUfvTimeOut() != null && ufv.getUfvTimeIn().after(ufv.getUfvTimeOut())) {
            ufv.setUfvTimeIn(null);
        }
        return ufv;
    }

    @Nullable
    UnitFacilityVisit createUfvIfDoesnExist(Unit inEqUnit) {
        if (inEqUnit.getUnitActiveUfv() != null) {
            return inEqUnit.getUnitActiveUfv();
        }
        UnitFacilityVisit masterUfv = this.getUfvForAttachPurpose();
        if (masterUfv == null) {
            LOGGER.error((Object)("Error in fetching master UFV! Unit to be attached <" + inEqUnit + ">, master unit <" + this + ">"));
            return null;
        }
        try {
            return this.createUfvForAttachedEq(masterUfv, this.thisUnit());
        }
        catch (BizViolation inBizViolation) {
            LOGGER.error((Object)("Error while creating UFV for an unit to be attached: " + inBizViolation.getLocalizedMessage()));
            return null;
        }
    }

    protected void doOtherUpdatesPostAttach(IEqLinkContext<UnitNode, Unit, Equipment> inContext) throws BizViolation {
        if (this.getUnitEqRole() == EqUnitRoleEnum.PRIMARY) {
            if (this.getUnitRelatedUnit() == null) {
                this.setUnitEquipmentState(EquipmentState.findOrCreateEquipmentState(this.getUnitEquipment(), this.getUnitComplex().getCpxOperator()));
                this.setUnitId(this.getUnitEquipment().getEqIdFull());
                if (FreightKindEnum.BBK == this.getUnitFreightKind()) {
                    this.updateStuffedUnitPropertiesPostAttach(inContext);
                }
            } else if (this.getUnitRelatedUnit().getUnitEqRole() == EqUnitRoleEnum.CARRIAGE) {
                this.updateCarriageUnitPropertiesPostAttach(inContext);
            }
        } else if (this.getUnitEqRole() == EqUnitRoleEnum.PAYLOAD) {
            this.updatePayloadUnitPropertiesPostAttach(inContext);
        } else {
            this.updateAcryUnitPropertiesPostAttach(inContext);
        }
    }

    private void updateStuffedUnitPropertiesPostAttach(IEqLinkContext<UnitNode, Unit, Equipment> inContext) {
        this.setUnitFreightKind(FreightKindEnum.FCL);
        double tareKg = this.getUnitEquipment().getEqTareWeightKg();
        double unitWt = this.getUnitGoodsAndCtrWtKg() == null ? 0.0 : this.getUnitGoodsAndCtrWtKg();
        double weight = unitWt + tareKg;
        this.setUnitGoodsAndCtrWtKg(weight);
    }

    private void updatePayloadUnitPropertiesPostAttach(IEqLinkContext<UnitNode, Unit, Equipment> inContext) throws BizViolation {
        if (this.getUnitCarriageUnit() != null) {
            this.setUnitCarriageUnit(null);
        }
        this.copyRoutingFromMaster();
        this.updateIsBundlePropertyPostAttach();
        this.hideInSparcsPostAttach();
        this.adjustMasterUnitGrossWtPostSlaveAttach();
        this.syncPosWithRelatedUnitPos();
    }

    private void updateIsBundlePropertyPostAttach() {
        Boolean isBundle;
        Unit masterUnit = this.getUnitRelatedUnit();
        if (masterUnit.hasOneUnitInPayloadRole() && !(isBundle = masterUnit.getUnitIsBundle()).booleanValue()) {
            masterUnit.setUnitIsBundle(Boolean.TRUE);
        }
    }

    private void hideInSparcsPostAttach() {
        UnitFacilityVisit ufv = this.getUnitActiveUfv();
        if (ufv != null && ufv.getUfvVisibleInSparcs().booleanValue()) {
            ufv.setUfvVisibleInSparcs(false);
        }
    }

    private void adjustMasterUnitGrossWtPostSlaveAttach() {
        boolean includePayLoadWeight = InventoryConfig.INCLUDE_PAYLOAD_WEIGHT.isOn(ContextHelper.getThreadUserContext());
        if (includePayLoadWeight) {
            Unit masterUnit = this.getUnitRelatedUnit();
            Double newWt = this.getUnitGoodsAndCtrWtKg() + masterUnit.getUnitGoodsAndCtrWtKg();
            masterUnit.setUnitGoodsAndCtrWtKg(newWt);
        }
    }

    private void updateCarriageUnitPropertiesPostAttach(IEqLinkContext<UnitNode, Unit, Equipment> inContext) throws BizViolation {
        if (this.getUnitRelatedUnit() == null || EqUnitRoleEnum.CARRIAGE != this.getUnitRelatedUnit().getUnitEqRole()) {
            return;
        }
        this.setUnitCarriageUnit(this.getUnitRelatedUnit());
        if (!ArgoUtils.isSystemInternalUpdateTransaction()) {
            UnitFacilityVisit ufv;
            if (this.thisUnit().getUnitActiveUfv() == null) {
                return;
            }
            if (inContext.getSlotOnCarriage() != null && !inContext.getSlotOnCarriage().isEmpty() && (ufv = this.thisUnit().getUnitActiveUfv()) != null) {
                LocPosition curPos = ufv.getUfvLastKnownPosition();
                curPos.updatePosSlotOnCarriage(inContext.getSlotOnCarriage());
                ufv.updateXpsLastKnownPosition(curPos, null);
            }
            LOGGER.warn((Object)("DataSource Enum is " + (Object)ContextHelper.getThreadDataSource()));
            if (Equipment.isAttachedToChe((String)this.getUnitRelatedUnit().getUnitId()) || this.getUnitRelatedUnit().isCoupledOperation().booleanValue()) {
                LOGGER.debug((Object)("No Position synch will be performed as Carriage " + this.getUnitRelatedUnit().getUnitId() + "is attached to CHE"));
                return;
            }
            if (this.getUnitCombo() != null && this.getUnitCombo().getUcUnits() != null) {
                Set unitsAttachedToChs = this.getUnitCombo().getUcUnits();
                for (Object attachedUnit : unitsAttachedToChs) {
//                    if (this.thisUnit().equals((Unit)attachedUnit) || ((Unit)attachedUnit).getUnitRelatedUnit() != null && this.thisUnit().equals(attachedUnit.getUnitRelatedUnit())) continue;
//                    ((Unit)attachedUnit).syncNonPrimaryWithRelatedUnitPos(this.thisUnit());
                }
            }
            UnitFacilityVisit activeUfv = this.getUnitCarriageUnit().getUnitActiveUfv();
            UnitEquipment.createChgStorageEvent(activeUfv, this.getUnitCarriageUnit());
            UnitEquipment.createChgLineStorageEvent(activeUfv, this.getUnitCarriageUnit());
        } else {
            ArgoUtils.clearSystemInternalUpdateInProgress();
        }
    }

    private void updateAcryUnitPropertiesPostAttach(IEqLinkContext<UnitNode, Unit, Equipment> inContext) throws BizViolation {
        if (EqUnitRoleEnum.ACCESSORY == this.getUnitEqRole() || EqUnitRoleEnum.ACCESSORY_ON_CHS == this.getUnitEqRole()) {
            UnitFacilityVisit ufv = this.getUnitActiveUfv();
            if (ufv != null) {
                if (ufv.getUfvVisibleInSparcs().booleanValue()) {
                    ufv.setUfvVisibleInSparcs(false);
                }
                UnitEquipment.createChgStorageEvent(ufv, ufv.getUfvUnit());
                UnitEquipment.createChgLineStorageEvent(ufv, ufv.getUfvUnit());
            }
            this.syncPosWithRelatedUnitPos();
            this.updateDenormalizedUnitAcryEquipIds();
        }
    }

    private void updateDenormalizedUnitAcryEquipIds() {
        String acryEquipIds = this.getUnitRelatedUnit().getUnitAcryEquipIds();
        String acryId = this.getUnitEquipment().getEqIdFull();
        acryEquipIds = acryEquipIds == null ? acryId : acryEquipIds + ", " + acryId;
        this.getUnitRelatedUnit().setUnitAcryEquipIds(acryEquipIds);
    }

    void syncPosWithRelatedUnitPos() throws BizViolation {
        Unit unit;
        Unit masterUnit;
        if (this.getUnitEqRole() == EqUnitRoleEnum.PRIMARY && this.getUnitRelatedUnit().getUnitEqRole() == EqUnitRoleEnum.CARRIAGE) {
            masterUnit = this.thisUnit();
            unit = this.getUnitRelatedUnit();
        } else {
            masterUnit = this.getUnitRelatedUnit();
            unit = this.thisUnit();
        }
        if (unit.getUnitActiveUfv() == null || masterUnit.getUnitActiveUfv() == null) {
            return;
        }
        unit.correctPos(masterUnit);
    }

    protected void syncNonPrimaryWithRelatedUnitPos(Unit inMasterUnit) throws BizViolation {
        Unit masterUnit = inMasterUnit;
        Unit unit = this.thisUnit();
        if (unit.getUnitActiveUfv() == null || masterUnit.getUnitActiveUfv() == null) {
            return;
        }
        UnitFacilityVisit masterUfv = masterUnit.getUnitActiveUfv();
        UnitFacilityVisit unitUfv = unit.getUnitActiveUfv();
        int thisState = UnitFacilityVisit.getTransitStateValue(unitUfv.getUfvTransitState());
        int masterState = UnitFacilityVisit.getTransitStateValue(masterUfv.getUfvTransitState());
        if (masterState <= 1 && thisState > 1 && thisState <= 4) {
            return;
        }
        if (masterState < 4 && masterState > 1 && thisState >= 4) {
            return;
        }
        unit.correctPos(masterUnit);
    }

    void correctPos(Unit inMasterUnit) throws BizViolation {
        LocPosition newPos;
        LocPosition oldPos = this.findCurrentPosition();
        if (!oldPos.isCorePositionChanged(newPos = inMasterUnit.findCurrentPosition())) {
            return;
        }
        RectifyParms parms = new RectifyParms();
        parms.setPosition(newPos);
        UnitFacilityVisit masterUfv = inMasterUnit.getUnitActiveUfv();
        parms.setUfvTransitState(masterUfv.getUfvTransitState());
        if (UnitFacilityVisit.getTransitStateValue(masterUfv.getUfvTransitState()) < 3) {
            parms.setIbCv(masterUfv.getInboundCarrierVisit());
        }
        this.getUnitActiveUfv().rectify(parms);
        FieldChanges fcs = new FieldChanges();
        fcs.setFieldChange(new FieldChange(IInventoryField.POS_NAME, (Object)oldPos.getPosName(), (Object)newPos.getPosName()));
        this.recordUnitEvent((IEventType)EventEnum.UNIT_POSITION_CORRECTION, fcs, UnitEquipment.getMessage(IInventoryPropertyKeys.UNIT_POS_CORRECTION, new String[0]));
    }

    protected void recordAttachEvent(EventEnum inEvent, UnitNode inRelatedNode) {
        this.recordUnitEvent((IEventType)inEvent, null, inRelatedNode.getUnit().getEventNotes(), null, (IEntity)inRelatedNode.getUnit());
    }

    protected void recordDetachEvent(EventEnum inEvent, UnitNode inRelatedNode, EqUnitRoleEnum inRelatedUnitOldRole, String inEventNotes) {
        Unit relatedUnit = inRelatedNode.getUnit();
        if (this.getUnitEquipment() == null || relatedUnit.getUnitEquipment() == null) {
            return;
        }
        if (inEventNotes == null) {
            inEventNotes = UnitEquipment.getMessage(IInventoryPropertyKeys.UNIT_DETACH_NOTES, new Object[]{this.getUnitEquipment().getEqEquipType().getEqtypClass(), this.getUnitEquipment().getEqIdFull(), inRelatedUnitOldRole, inRelatedNode.getUnit().getUnitEquipment().getEqIdFull()});
        }
        this.recordUnitEvent((IEventType)inEvent, null, inEventNotes, null, (IEntity)relatedUnit);
    }

    protected void updateUnitPropertiesPostDetach(EqUnitRoleEnum inOldRole, @Nullable Unit inRelatedUnit) throws BizViolation {
        if (EqUnitRoleEnum.CARRIAGE.equals((Object)inOldRole)) {
            this.updateCarriageUnitPropertiesPostDetach(inRelatedUnit);
        } else if (EqUnitRoleEnum.PAYLOAD.equals((Object)inOldRole)) {
            this.updatePayloadUnitPropertiesPostDetach(inRelatedUnit);
        } else if (EqUnitRoleEnum.ACCESSORY_ON_CHS.equals((Object)inOldRole)) {
            this.updateAcryOnChsUnitPropertiesPosDetach(inRelatedUnit);
        } else if (EqUnitRoleEnum.ACCESSORY.equals((Object)inOldRole)) {
            this.updateAcryUnitPropertiesPostDetach(inRelatedUnit);
        }
        UnitFacilityVisit eqUfv = this.getUnitActiveUfv();
        if (eqUfv != null && UfvTransitStateEnum.S70_DEPARTED.equals((Object)eqUfv.getUfvTransitState())) {
            eqUfv.getUfvUnit().makeDeparted();
        }
    }

    private void updateCarriageUnitPropertiesPostDetach(Unit inRelatedUnit) throws BizViolation {
        Facility facility = this.determineFacility();
        UnitFacilityVisit ufv = this.determineUfv(facility);
        if (EqUnitRoleEnum.PRIMARY.equals((Object)this.getUnitEqRole())) {
            this.updateCarrierDetailsToGenTruckPostDetach(ufv);
            this.updateChassisSlotPostDetach(ufv);
            this.updateLineOperatorBackToEqOprOrOwnerPostDetach();
            UnitEquipment.nullifyCarriageDenormalizedFieldPostDetach(inRelatedUnit);
            this.releaseGuardianHoldCreatedByChsEqPostDetach(inRelatedUnit);
        }
        if (!ArgoUtils.isSystemInternalUpdateTransaction() && inRelatedUnit != null && inRelatedUnit.getUnitActiveUfv() != null) {
            inRelatedUnit.getUnitActiveUfv().updateSlotOnCarriage(null);
        }
    }

    private void updateChassisSlotPostDetach(UnitFacilityVisit inUfv) throws BizViolation {
        if (this.isChassis() && !this.isInternalChassis() && inUfv.isTransitStateAtLeast(UfvTransitStateEnum.S30_ECIN) && inUfv.isTransitStateAtMost(UfvTransitStateEnum.S60_LOADED)) {
            String chssSlot = InventoryConfig.CHASSIS_DISMOUNT_SLOT.getSetting(ContextHelper.getThreadUserContext());
            if ("GEN_YARD".equals(chssSlot) || this.isInternalChassis()) {
                chssSlot = null;
            }
            LOGGER.info((Object)("Setting Location Slot of Chassis " + this.getUnitId() + " to chssSlot"));
            RectifyParms parms = new RectifyParms();
            parms.setUfvTransitState(UfvTransitStateEnum.S40_YARD);
            parms.setSlot(chssSlot);
            inUfv.rectify(parms);
        }
    }

    private void updateCarrierDetailsToGenTruckPostDetach(UnitFacilityVisit inUfv) {
        CarrierVisit genericTruckVisit = CarrierVisit.getGenericTruckVisit((Complex)inUfv.getUfvFacility().getFcyComplex());
        inUfv.updateActualIbCv(genericTruckVisit);
        inUfv.updateObCv(genericTruckVisit);
        Routing newRouting = new Routing();
        newRouting.setRtgDeclaredCv(genericTruckVisit);
        this.setUnitRouting(newRouting);
    }

    private static void nullifyCarriageDenormalizedFieldPostDetach(Unit inRelatedUnit) {
        if (inRelatedUnit != null) {
            inRelatedUnit.setUnitCarriageUnit(null);
        }
    }

    private void releaseGuardianHoldCreatedByChsEqPostDetach(Unit inRelatedUnit) throws BizViolation {
        IServicesManager servicesMgr;
        Collection impediments;
        if (inRelatedUnit != null && !(impediments = (servicesMgr = (IServicesManager)Roastery.getBean((String)"servicesManager")).getImpedimentsForEntity((ILogicalEntity)inRelatedUnit)).isEmpty()) {
            for (Object impediment : impediments) {
                IImpediment unitImpedimnent = (IImpediment)impediment;
                if (!unitImpedimnent.getAppliedToClass().equals((Object)LogicalEntityEnum.EQ) || !unitImpedimnent.getAppliedToNaturalKey().equals(this.getUnitId())) continue;
                servicesMgr.applyPermission(unitImpedimnent.getFlagType().getId(), (ILogicalEntity)inRelatedUnit, null, null, "Chassis detached - Guardian hold released");
            }
        }
    }

    private void updateLineOperatorBackToEqOprOrOwnerPostDetach() {
        if (this.getUeEquipmentState() != null) {
            if (this.getUeEquipmentState().getEqsEqOperator() != null) {
                this.setUnitLineOperator(this.getUeEquipmentState().getEqsEqOperator());
            } else {
                this.setUnitLineOperator(this.getUeEquipmentState().getEqsEqOwner());
            }
        }
    }

    private void updatePayloadUnitPropertiesPostDetach(Unit inRelatedUnit) throws BizViolation {
        UnitEquipment.updateIsBundlePropertyPostDetach(inRelatedUnit);
        this.adjustMasterUnitGrossWtPostSlaveDetach(inRelatedUnit);
        this.showInSparcsPostDetach(inRelatedUnit);
        if (this.getUnitActiveUfv() != null) {
            this.getUnitActiveUfv().updateSlotOnCarriage(null);
        }
    }

    private static void updateIsBundlePropertyPostDetach(Unit inRelatedUnit) {
        inRelatedUnit.setUnitIsBundle(!inRelatedUnit.hasNoneInPayloadRole());
    }

    private void adjustMasterUnitGrossWtPostSlaveDetach(Unit inRelatedUnit) {
        boolean includePayLoadWeight = InventoryConfig.INCLUDE_PAYLOAD_WEIGHT.isOn(ContextHelper.getThreadUserContext());
        if (includePayLoadWeight) {
            double newGrossWt = Math.max(0.0, inRelatedUnit.getUnitGoodsAndCtrWtKg() - this.getUnitGoodsAndCtrWtKg());
            inRelatedUnit.setUnitGoodsAndCtrWtKg(newGrossWt);
        }
    }

    private void showInSparcsPostDetach(Unit inRelatedUnit) {
        UnitFacilityVisit ufv = this.getUnitActiveUfv();
        if (ufv != null && !ufv.getUfvVisibleInSparcs().booleanValue() && inRelatedUnit.getUnitActiveUfv() != null) {
            ufv.setUfvVisibleInSparcs(inRelatedUnit.getUnitActiveUfv().getUfvVisibleInSparcs());
        }
    }

    private void updateAcryOnChsUnitPropertiesPosDetach(Unit inRelatedUnit) {
        this.showInSparcsPostDetach(inRelatedUnit);
        this.setUnitCarriageUnit(null);
        UnitFacilityVisit activeUfv = this.thisUnit().getUnitActiveUfv();
        if (activeUfv != null) {
            UnitEquipment.createChgStorageEvent(activeUfv, this.thisUnit());
            UnitEquipment.createChgLineStorageEvent(activeUfv, this.thisUnit());
            this.updateAcryUnitObCvPostDetach(activeUfv);
        }
        this.updateDenormalizedUnitAcryEquipIdsPostDetach(inRelatedUnit);
    }

    private void updateAcryUnitPropertiesPostDetach(Unit inRelatedUnit) {
        this.showInSparcsPostDetach(inRelatedUnit);
        UnitFacilityVisit activeUfv = this.thisUnit().getUnitActiveUfv();
        if (activeUfv != null) {
            UnitEquipment.createChgStorageEvent(activeUfv, this.thisUnit());
            UnitEquipment.createChgLineStorageEvent(activeUfv, this.thisUnit());
            this.updateAcryUnitObCvPostDetach(activeUfv);
        }
        this.updateDenormalizedUnitAcryEquipIdsPostDetach(inRelatedUnit);
    }

    private void updateDenormalizedUnitAcryEquipIdsPostDetach(Unit inRelatedUnit) {
        StringBuilder sb = new StringBuilder();
        Iterator<Unit> itr = inRelatedUnit.getAccessoryUnits().iterator();
        while (itr.hasNext()) {
            Unit acryUnit = itr.next();
            sb.append(acryUnit.getUnitEquipment().getEqIdFull());
            if (!itr.hasNext()) continue;
            sb.append(", ");
        }
        inRelatedUnit.setUnitAcryEquipIds(sb.toString());
    }

    private void updateAcryUnitObCvPostDetach(UnitFacilityVisit inActiveUfv) {
        CarrierVisit genericCarrierVisit = CarrierVisit.getGenericCarrierVisit((Complex)ContextHelper.getThreadComplex());
        inActiveUfv.updateObCv(genericCarrierVisit);
    }

    private UnitFacilityVisit determineUfv(Facility inFacility) throws BizViolation {
        UnitFacilityVisit ufv = this.getUnitActiveUfv();
        if (ufv == null) {
            ufv = this.getUfvForFacilityNewest(inFacility);
        }
        if (ufv == null) {
            throw BizViolation.create((IPropertyKey)IInventoryPropertyKeys.UNIT_CANNOT_DETACH_UFV_NOT_FOUND, null, (Object)this, (Object)inFacility.getFcyId());
        }
        return ufv;
    }

    private Facility determineFacility() throws BizViolation {
        ILocation location;
        LocPosition currentPos = this.findCurrentPosition();
        UnitFacilityVisit ufv = this.getUnitActiveUfv();
        Facility facility = null;
        if (ufv == null && (location = currentPos.resolveLocation()) != null) {
            facility = location.getLocFacility();
        }
        if (facility == null && (facility = ContextHelper.getThreadFacility()) == null) {
            throw BizViolation.create((IPropertyKey)IInventoryPropertyKeys.UNIT_UNABLE_TO_DETERMINE_FACILITY, null, (Object)this, (Object)this.findCurrentPosition());
        }
        return facility;
    }

    public void detachRelatedUnits() {
        Unit[] units;
        Collection<Unit> ucUnitSet = this.ensureUnitSet();
        for (Unit ucUnit : units = this.ensureUnitSet().toArray(new Unit[ucUnitSet.size()])) {
            if (ucUnit.getUnitRelatedUnit() != null && ucUnit.getUnitRelatedUnit().equals(this)) {
                try {
                    ucUnit.detach();
                }
                catch (BizViolation inBizViolation) {
                    LOGGER.error((Object)("Error while detaching " + ucUnit + ", Error: " + (Object)((Object)inBizViolation)));
                }
                continue;
            }
            if (this.getUnitRelatedUnit() == null || !this.getUnitRelatedUnit().equals(ucUnit)) continue;
            try {
                this.detach(ucUnit, null);
            }
            catch (BizViolation inBizViolation) {
                LOGGER.error((Object)("Error while detaching " + ucUnit + ", Error: " + (Object)((Object)inBizViolation)));
            }
        }
    }

    public Collection<Unit> getRelatedUnits() {
        Collection<Unit> ucUnitSet = this.ensureUnitSet();
        if (ucUnitSet.isEmpty()) {
            return ucUnitSet;
        }
        Unit[] units = this.ensureUnitSet().toArray(new Unit[ucUnitSet.size()]);
        HashSet<Unit> relatedUnits = new HashSet<Unit>();
        for (Unit ucUnit : units) {
            if (ucUnit.getUnitRelatedUnit() == null || !ucUnit.getUnitRelatedUnit().equals(this)) continue;
            relatedUnits.add(ucUnit);
        }
        return relatedUnits;
    }

    public void clearDamagesIfSettingAllows() {
        EquipClassEnum eqClass = this.getUnitEquipment().getEqClass();
        boolean allowRemove = false;
        if (EquipClassEnum.CONTAINER.equals((Object)eqClass) && InventoryConfig.CLEAR_CTR_DAMAGES.isOn(ContextHelper.getThreadUserContext()) || EquipClassEnum.CHASSIS.equals((Object)eqClass) && InventoryConfig.CLEAR_CHS_DAMAGES.isOn(ContextHelper.getThreadUserContext())) {
            allowRemove = true;
        }
        if (allowRemove) {
            this.removeDamages();
        }
    }

    static {
        BIZ_FIELDS.add(UnitField.UE_LINE_OPERATOR, BizRoleEnum.LINEOP);
        BIZ_FIELDS.add(UnitField.UE_EQ_OWNER, BizRoleEnum.LINEOP);
        BIZ_FIELDS.add(UnitField.UE_SHIPPER_BZU, BizRoleEnum.SHIPPER);
        BIZ_FIELDS.add(UnitField.UE_CONSIGNEE_BZU, BizRoleEnum.SHIPPER);
        BIZ_FIELDS.add(UnitField.UE_AGENT1, BizRoleEnum.AGENT);
        LOGGER = Logger.getLogger(UnitEquipment.class);
        SYSTEM_UID = "-system-";
    }

    @Override
    protected void updateDenormalizedFields() {

    }

    private abstract class DamageEvent {
        private UnitEquipDamageItem _oldItem;
        private UnitEquipDamageItem _newItem;

        private DamageEvent(UnitEquipDamageItem inOldItem, UnitEquipDamageItem inNewItem) {
            this._oldItem = inOldItem;
            this._newItem = inNewItem;
        }

        protected void recordDmgInsertEvent() {
            UnitEquipment.this.recordInsertEvent(this._newItem);
        }

        protected void recordDmgUpdateEvent() {
            UnitEquipment.this.recordUpdateEvent(this._oldItem, this._newItem);
        }

        protected void recordDmgDeleteEvent() {
            UnitEquipment.this.recordDeleteEvent(this._oldItem);
        }

        protected abstract void record();
    }

    private class DamageDeleteEvent
            extends DamageEvent {
        private DamageDeleteEvent(UnitEquipDamageItem inOldItem) {
            super(inOldItem, null);
        }

        @Override
        protected void record() {
            this.recordDmgDeleteEvent();
        }
    }

    private class DamageUpdateEvent
            extends DamageEvent {
        private DamageUpdateEvent(UnitEquipDamageItem inOldItem, UnitEquipDamageItem inNewItem) {
            super(inOldItem, inNewItem);
        }

        @Override
        protected void record() {
            this.recordDmgUpdateEvent();
        }
    }

    private class DamageInsertEvent
            extends DamageEvent {
        private DamageInsertEvent(UnitEquipDamageItem inNewItem) {
            super(null, inNewItem);
        }

        @Override
        protected void record() {
            this.recordDmgInsertEvent();
        }
    }

}
