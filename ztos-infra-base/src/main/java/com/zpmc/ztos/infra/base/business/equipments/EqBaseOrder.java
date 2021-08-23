package com.zpmc.ztos.infra.base.business.equipments;

import com.zpmc.ztos.infra.base.business.dataobject.EqBaseOrderDO;
import com.zpmc.ztos.infra.base.business.enums.argo.EquipmentOrderSubTypeEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.LogicalEntityEnum;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.business.inventory.Unit;
import com.zpmc.ztos.infra.base.business.model.MetafieldIdList;
import com.zpmc.ztos.infra.base.common.exceptions.BizFailure;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.helps.ContextHelper;
import com.zpmc.ztos.infra.base.common.model.FieldChanges;
import com.zpmc.ztos.infra.base.common.model.Roastery;
import com.zpmc.ztos.infra.base.common.scopes.Complex;
import org.apache.log4j.Logger;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.util.ArrayList;
import java.util.List;

public class EqBaseOrder extends EqBaseOrderDO {
    private static final Logger LOGGER = Logger.getLogger(EqBaseOrder.class);

    public static MetafieldIdList getPredicateFields(LogicalEntityEnum inEntity) {
        MetafieldIdList fields = new MetafieldIdList();
        fields.add(IInventoryField.EQBO_NBR);
        return fields;
    }

    public static MetafieldIdList getUpdateFields(LogicalEntityEnum inEntity) {
        MetafieldIdList fields = new MetafieldIdList();
        return fields;
    }

    public static MetafieldIdList getPathsToGuardians(LogicalEntityEnum inEntity) {
        MetafieldIdList fields = new MetafieldIdList();
        return fields;
    }

    public static MetafieldIdList getPathsToBizUnits(LogicalEntityEnum inEntity) {
        MetafieldIdList fields = new MetafieldIdList();
        return fields;
    }

    public String getHumanReadableKey() {
        return this.getEqboNbr();
    }

    public void updateEqboNbr(String inEqOrderNumber) {
        if (inEqOrderNumber == null) {
            throw BizFailure.create((String)"Update EquipmentOrderNbr is called with null value for new number. This is not a valid entry!");
        }
        this.setEqboNbr(inEqOrderNumber);
    }

    @Nullable
    public LogicalEntityEnum getLogicalEntityType() {
        return null;
    }

    public String getLogEntityId() {
        return this.getEqboNbr();
    }

    public String getLogEntityParentId() {
        return this.getLogEntityId();
    }

    public void calculateFlags() {
    }

    @Nullable
    public BizViolation verifyApplyFlagToEntityAllowed() {
        return null;
    }

    @Nullable
    public Complex getLogEntityComplex() {
        return null;
    }

    @Nullable
    public String getLovKeyNameForPathToGuardian(String inPathToGuardian) {
        return null;
    }

    public Boolean skipEventRecording() {
        return false;
    }

    @Nullable
    public String getLovKeyNameForLogicalEntityType(LogicalEntityEnum inLogicalEntityType) {
        return null;
    }

    public List getGuardians() {
        MetafieldIdList pathsToGuardians = EqBaseOrder.getPathsToGuardians(this.getLogicalEntityType());
        ArrayList<Object> guardians = new ArrayList<Object>();
        for (int i = 0; i < pathsToGuardians.getSize(); ++i) {
            IMetafieldId id = pathsToGuardians.get(i);
            Object relatedObject = this.getField(id);
            if (relatedObject == null) continue;
            if (IGuardian.class.isAssignableFrom(relatedObject.getClass())) {
                guardians.add(relatedObject);
                continue;
            }
            LOGGER.error((Object)("getGuardians: calculated guardian is of wrong class, class=" + relatedObject.getClass() + ", value=" + relatedObject));
        }
        return guardians;
    }

    @Nullable
    public List getSupportedBathToGuardians() {
        return null;
    }

    public boolean isCorrectEntityType(LogicalEntityEnum inExpectedType) {
        EquipmentOrderSubTypeEnum subType = this.getEqboSubType();
        if (EquipmentOrderSubTypeEnum.BOOK.equals((Object)subType)) {
            return LogicalEntityEnum.BKG.equals((Object)inExpectedType);
        }
        if (EquipmentOrderSubTypeEnum.EDO.equals((Object)subType)) {
            return LogicalEntityEnum.DO.equals((Object)inExpectedType);
        }
        if (EquipmentOrderSubTypeEnum.ELO.equals((Object)subType)) {
            return LogicalEntityEnum.LO.equals((Object)inExpectedType);
        }
        if (EquipmentOrderSubTypeEnum.RAIL.equals((Object)subType)) {
            return LogicalEntityEnum.RO.equals((Object)inExpectedType);
        }
        if (EquipmentOrderSubTypeEnum.ERO.equals((Object)subType)) {
            return LogicalEntityEnum.ERO.equals((Object)inExpectedType);
        }
        return false;
    }

    public IEvent recordOrderEvent(IEventType inEventType, FieldChanges inAlreadyUpdatedFields, String inEventNote) {
        IEvent event = null;
        try {
            if (ContextHelper.getThreadOperator() != null) {
                IServicesManager srvcMgr = (IServicesManager) Roastery.getBean((String)"servicesManager");
                event = srvcMgr.recordEvent(inEventType, inEventNote, null, null, (IServiceable)this, inAlreadyUpdatedFields);
            } else {
                LOGGER.error((Object)("recordOrderEvent: Attempt to record event" + (Object)inEventType + "using Global scope"));
            }
        }
        catch (BizViolation bv) {
            LOGGER.error((Object)("recordOrderEvent: error recording service event: " + (Object)((Object)bv)));
        }
        return event;
    }

    public void setFieldValue(IMetafieldId inMetaFieldId, Object inFieldValue) {
        if (IInventoryField.EQBO_NBR.equals((Object)inMetaFieldId) && inFieldValue != null) {
            String eqboNbr = ((String)inFieldValue).trim();
            super.setFieldValue(inMetaFieldId, (Object)eqboNbr);
        } else {
            super.setFieldValue(inMetaFieldId, inFieldValue);
        }
    }

    @Nullable
    public String getEqboLovDescription() {
        return null;
    }

    public void incrementTallyRcv(Unit inUnit) {
        if (inUnit.isAllowedOrderAssign(this)) {
            this.incrementTallyRcvCount();
        }
    }

    public boolean allowUnitAssign(Unit inUnit) {
        return false;
    }

    protected void incrementTallyRcvCount() {
    }

    public void decrementTallyRcv(Unit inUnit) {
        if (inUnit.isAllowedOrderAssign(this)) {
            this.decrementTallyRcvCount();
        }
    }

    public void decrementTallyRcvCount() {
    }

    public boolean isArrivalOrder() {
        return EquipmentOrderSubTypeEnum.ERO.equals((Object)this.getEqboSubType());
    }

    protected void setEqoTally(Long inEqoiTally) {
    }

    public void updateTally(EqBaseOrderItem inEqboi, Long inNewEqoiTally) {
        Long eqoTally = this.getEqoTally() - inEqboi.getEqoiTally() + inNewEqoiTally;
        eqoTally = eqoTally < 0L ? 0L : eqoTally;
        this.setEqoTally(eqoTally);
        inEqboi.setEqoiTally(inNewEqoiTally);
    }

    public Long getEqoTally() {
        return 0L;
    }

    public void updateQty(EqBaseOrderItem inEqboi, Long inNewEqoiQty) {
        Long eqoQty = this.getEqoQuantity() - inEqboi.getEqoiQty() + inNewEqoiQty;
        eqoQty = eqoQty < 0L ? 0L : eqoQty;
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug((Object)("Updating Quantity to " + eqoQty + " with Eqboi: " + inEqboi.toString() + " old EqoQuantity: " + this.getEqoQuantity() + " and newQuantity: " + inNewEqoiQty));
        }
        this.setEqoQuantity(eqoQty);
        inEqboi.setEqoiQty(inNewEqoiQty);
    }

    public Long getEqoQuantity() {
        return 0L;
    }

    protected void setEqoQuantity(Long inEqoiQty) {
    }

    protected void setEqoUECount(Long inEqoUECount) {
    }

    public void decrementUECount() {
    }

    public void incrementUECount() {
    }

//    @Nullable
//    protected OrderPurpose getEqoOrderPurpose() {
//        return null;
//    }

    public void postEventCreation(IEvent inEventCreated) {
    }

    public Object getOrdEqoEqoiBookingTableKey() {
        return this.getEqboGkey();
    }
}
