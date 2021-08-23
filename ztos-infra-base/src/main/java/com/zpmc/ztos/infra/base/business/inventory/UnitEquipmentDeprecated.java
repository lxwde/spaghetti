package com.zpmc.ztos.infra.base.business.inventory;

import com.zpmc.ztos.infra.base.business.dataobject.UnitDO;
import com.zpmc.ztos.infra.base.business.enums.argo.EquipClassEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.EventEnum;
import com.zpmc.ztos.infra.base.business.enums.inventory.EqDamageSeverityEnum;
import com.zpmc.ztos.infra.base.business.enums.inventory.EqUnitRoleEnum;
import com.zpmc.ztos.infra.base.business.enums.inventory.PlacardedEnum;
import com.zpmc.ztos.infra.base.business.enums.inventory.UnitEquipmentUseModeEnum;
import com.zpmc.ztos.infra.base.business.equipments.EqBaseOrderItem;
import com.zpmc.ztos.infra.base.business.equipments.EquipGrade;
import com.zpmc.ztos.infra.base.business.equipments.Equipment;
import com.zpmc.ztos.infra.base.business.equipments.EquipmentState;
import com.zpmc.ztos.infra.base.business.interfaces.IEventType;
import com.zpmc.ztos.infra.base.business.interfaces.IServiceable;
import com.zpmc.ztos.infra.base.business.interfaces.IServicesManager;
import com.zpmc.ztos.infra.base.business.interfaces.IUnitFinder;
import com.zpmc.ztos.infra.base.business.model.ScopedBizUnit;
import com.zpmc.ztos.infra.base.common.helps.ContextHelper;
import com.zpmc.ztos.infra.base.common.model.Roastery;
import org.apache.log4j.Logger;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.util.*;

public class UnitEquipmentDeprecated extends UnitDO {
    private Unit _thisUnit = (Unit)this;
    private static final Logger LOGGER = Logger.getLogger(UnitEquipmentDeprecated.class);

    public Long getUeGkey() {
        return this.getUeUnit().getUnitGkey();
    }

    @Nullable
    public ScopedBizUnit getUeObsoleteEqOperator() {
        return this.getUnitEquipmentState() == null ? null : this.getUnitEquipmentState().getEqsEqOperator();
    }

    public EquipGrade getUeObsoleteGradeID() {
        return this.getUnitEquipmentState() == null ? null : this.getUnitEquipmentState().getEqsGradeID();
    }

    public ScopedBizUnit getUeObsoleteEqOwner() {
        return this.getUnitEquipmentState() == null ? null : this.getUnitEquipmentState().getEqsEqOwner();
    }

    public UnitEquipmentUseModeEnum getUeObsoleteEqUseMode() {
        return this.getUnitEquipmentState() == null ? null : this.getUnitEquipmentState().getEqsEqUseMode();
    }

    public Boolean getUeObsoleteIsCurrentUse() {
        IUnitFinder uf = (IUnitFinder) Roastery.getBean((String)"unitFinder");
        Unit unit = uf.findAttachedUnit(ContextHelper.getThreadComplex(), this.getUnitEquipment());
        return unit != null && unit.equals(this.thisUnit());
    }

    public String getUeObsoleteReturnToLocation() {
        return "";
    }

    @Deprecated
    public void markAsAttached() {
        LOGGER.error((Object)"Attach/detach times are not stored any more. The method is deprecated.");
    }

    @Deprecated
    @Nullable
    public Equipment getPrimaryEq() {
        return this.getUnitEquipment();
    }

    @Deprecated
    @Nullable
    public UnitEquipment getUeAccessory() {
        return this.thisUnit().getAccessoryOnCtr();
    }

    @Deprecated
    public EqUnitRoleEnum getUeEqRole() {
        return this.getUnitEqRole();
    }

    @Deprecated
    public EqDamageSeverityEnum getUeDamageSeverity() {
        return this.getUnitDamageSeverity();
    }

    @Deprecated
    public String getUeBadNbr() {
        return this.getUnitBadNbr();
    }

    @Deprecated
    public Boolean getUeIsFolded() {
        return this.getUnitIsFolded();
    }

    @Deprecated
    public String getUeSparcsDamageCode() {
        return this.getUnitSparcsDamageCode();
    }

    @Deprecated
    public Boolean getUeIsReserved() {
        return this.getUnitIsReserved();
    }

    @Deprecated
    public PlacardedEnum getUePlacarded() {
        return this.getUnitPlacarded();
    }

    @Deprecated
    public Unit getUeUnit() {
        return this.thisUnit();
    }

    @Deprecated
    public Equipment getUeEquipment() {
        return this.getUnitEquipment();
    }

    @Deprecated
    public EquipmentState getUeEquipmentState() {
        return this.getUnitEquipmentState();
    }

//    @Deprecated
//    public EquipCondition getUeConditionID() {
//        return this.getUnitConditionID();
//    }

    @Deprecated
    public UnitEquipDamages getUeDamages() {
        return this.getUnitDamages();
    }

    @Deprecated
    public EqBaseOrderItem getUeArrivalOrderItem() {
        return this.getUnitArrivalOrderItem();
    }

    @Deprecated
    public EqBaseOrderItem getUeDepartureOrderItem() {
        return this.getUnitDepartureOrderItem();
    }

//    @Deprecated
//    public MnrStatus getUeMnrStatus() {
//        return this.getUnitMnrStatus();
//    }

    @Deprecated
    @Nullable
    public UnitEquipment getUnitPrimaryUe() {
        return this.getUnitEquipment() == null ? null : this.thisUnit();
    }

    @Deprecated
    protected void setUnitPrimaryUe(UnitEquipment inUe) {
    }

    @Deprecated
    public UnitEquipment getUnitCarriageUe() {
        return this.thisUnit().getUnitCarriageUnit();
    }

    @Deprecated
    protected void setUnitCarriageUe(UnitEquipment inUe) {
        super.setUnitCarriageUnit(inUe.thisUnit());
    }

    @Deprecated
    public Set getUnitUeSet() {
        return new HashSet<Unit>(this.ensureUnitSet());
    }

    protected Collection<Unit> ensureUnitSet() {
        try {
            if (this.getUnitEquipment() == null) {
                return Collections.emptyList();
            }
//            if (this.getUnitCombo() == null) {
//                return Collections.singletonList(this.thisUnit());
//            }
//            return this.getUnitCombo().getUcUnitsSafe();
        }
        catch (Throwable ex) {
            LOGGER.error((Object)("Error while getting combo units: " + ex));
            return Collections.emptyList();
        }
        return null;
    }

    protected void setUeEqRole(EqUnitRoleEnum inRole) {
        this.getUeUnit().setUnitEqRole(inRole);
    }

    protected void setUeAttachTime(Date inAttachTime) {
    }

    @Nullable
    public Date getUeDetachTime() {
        if (this.getUnitEquipment() == null) {
            return null;
        }
        IServicesManager sm = (IServicesManager)Roastery.getBean((String)"servicesManager");
        if (EquipClassEnum.CHASSIS.equals((Object)this.getUnitEquipment().getEqClass())) {
            return sm.getMostRecentTimeForEvent((IEventType) EventEnum.UNIT_CARRIAGE_DISMOUNT, (IServiceable)this.thisUnit());
        }
        if (EquipClassEnum.ACCESSORY.equals((Object)this.getUnitEquipment().getEqClass())) {
            Date eventDate = sm.getMostRecentTimeForEvent((IEventType)EventEnum.UNIT_CTR_ACCESSORY_DISMOUNT, (IServiceable)this.thisUnit());
            if (eventDate == null) {
                eventDate = sm.getMostRecentTimeForEvent((IEventType)EventEnum.UNIT_CHS_ACCESSORY_DISMOUNT, (IServiceable)this.thisUnit());
            }
            return eventDate;
        }
        if (sm.hasEventTypeBeenRecorded((IEventType)EventEnum.UNIT_BUNDLE_PAYLOAD_ADD, (IServiceable)this.thisUnit())) {
            return sm.getMostRecentTimeForEvent((IEventType)EventEnum.UNIT_BUNDLE_PAYLOAD_SWIPE, (IServiceable)this.thisUnit());
        }
        return null;
    }

    protected void setUeDetachTime(Date inDetachTime) {
    }

    @Nullable
    public Date getUeAttachTime() {
        if (this.getUnitEquipment() == null) {
            return null;
        }
        IServicesManager sm = (IServicesManager)Roastery.getBean((String)"servicesManager");
        if (EqUnitRoleEnum.CARRIAGE.equals((Object)this.getUnitEqRole())) {
            return sm.getMostRecentTimeForEvent((IEventType)EventEnum.UNIT_CARRIAGE_MOUNT, (IServiceable)this.thisUnit());
        }
        if (EqUnitRoleEnum.ACCESSORY.equals((Object)this.getUnitEqRole())) {
            return sm.getMostRecentTimeForEvent((IEventType)EventEnum.UNIT_CTR_ACCESSORY_MOUNT, (IServiceable)this.thisUnit());
        }
        if (EqUnitRoleEnum.ACCESSORY_ON_CHS.equals((Object)this.getUnitEqRole())) {
            return sm.getMostRecentTimeForEvent((IEventType)EventEnum.UNIT_CHS_ACCESSORY_MOUNT, (IServiceable)this.thisUnit());
        }
        if (EqUnitRoleEnum.PAYLOAD.equals((Object)this.getUnitEqRole())) {
            return sm.getMostRecentTimeForEvent((IEventType)EventEnum.UNIT_BUNDLE_PAYLOAD_ADD, (IServiceable)this.thisUnit());
        }
        return sm.getMostRecentTimeForEvent((IEventType)EventEnum.UNIT_ACTIVATE, (IServiceable)this.thisUnit());
    }

    protected void setUeDamageSeverity(EqDamageSeverityEnum inDamageSeverity) {
        this.getUeUnit().setUnitDamageSeverity(inDamageSeverity);
    }

    protected void setUeBadNbr(String inBadNbr) {
        this.getUeUnit().setUnitBadNbr(inBadNbr);
    }

    protected void setUeIsFolded(Boolean inIsFolded) {
        this.getUeUnit().setUnitIsFolded(inIsFolded);
    }

    protected void setUeSparcsDamageCode(String inSparcsDamageCode) {
        this.getUeUnit().setUnitSparcsDamageCode(inSparcsDamageCode);
    }

    protected void setUeIsReserved(Boolean inIsReserved) {
        this.getUeUnit().setUnitIsReserved(inIsReserved);
    }

    protected void setUePlacarded(PlacardedEnum inPlacarded) {
        this.getUeUnit().setUnitPlacarded(inPlacarded);
    }

    protected void setUeUnit(Unit inUnit) {
    }

    protected void setUeEquipment(Equipment inEquipment) {
        this.getUeUnit().setUnitEquipment(inEquipment);
    }

    public void setUeEquipmentState(EquipmentState inEquipmentState) {
        this.getUeUnit().setUnitEquipmentState(inEquipmentState);
    }

//    protected void setUeConditionID(EquipCondition inConditionID) {
//        this.getUeUnit().setUnitConditionID(inConditionID);
//    }

    protected void setUeDamages(UnitEquipDamages inDamages) {
        this.getUeUnit().setUnitDamages(inDamages);
    }

    public void setUeArrivalOrderItem(EqBaseOrderItem inArrivalOrderItem) {
        this.getUeUnit().setUnitArrivalOrderItem(inArrivalOrderItem);
    }

    public void setUeDepartureOrderItem(EqBaseOrderItem inDepartureOrderItem) {
        this.getUeUnit().setUnitDepartureOrderItem(inDepartureOrderItem);
    }

//    protected void setUeMnrStatus(MnrStatus inMnrStatus) {
//        this.getUeUnit().setUnitMnrStatus(inMnrStatus);
//    }

    public Unit thisUnit() {
        return this._thisUnit;
    }
}
