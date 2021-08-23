package com.zpmc.ztos.infra.base.business.inventory;

import com.zpmc.ztos.infra.base.business.dataobject.UnitEquipDamageItemDO;
import com.zpmc.ztos.infra.base.business.enums.argo.EquipClassEnum;
import com.zpmc.ztos.infra.base.business.enums.inventory.EqDamageSeverityEnum;
import com.zpmc.ztos.infra.base.business.equipments.EqComponent;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.common.database.HiberCache;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.common.events.AuditEvent;
import com.zpmc.ztos.infra.base.common.exceptions.BizFailure;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.model.FieldChange;
import com.zpmc.ztos.infra.base.common.model.FieldChanges;
import com.zpmc.ztos.infra.base.common.model.Roastery;
import com.zpmc.ztos.infra.base.common.utils.ArgoUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;

public class UnitEquipDamageItem extends UnitEquipDamageItemDO {
    private static final Logger LOGGER = Logger.getLogger(UnitEquipDamageItem.class);

    public UnitEquipDamageItem() {
        this.setDmgitemSeverity(EqDamageSeverityEnum.MINOR);
    }

    protected UnitEquipDamageItem(UnitEquipDamages inOwnerDamages, EqComponent inComponent, EqDamageSeverityEnum inDmgSeverity, EquipDamageType inType) {
        this();
        this.setDmgitemDmgs(inOwnerDamages);
        this.setDmgitemComponent(inComponent);
        this.setDmgitemSeverity(inDmgSeverity);
        this.setDmgitemType(inType);
    }

    public void preProcessInsert(FieldChanges inOutMoreChanges) {
        super.preProcessInsert(inOutMoreChanges);
        if (this.getDmgitemReported() == null) {
            this.setSelfAndFieldChange(IInventoryField.DMGITEM_REPORTED, ArgoUtils.timeNow(), inOutMoreChanges);
        }
    }

    static UnitEquipDamageItem create(UnitEquipDamages inUnitEquipDamages, EquipDamageType inDmgType, EqComponent inDmgComponent, EqDamageSeverityEnum inDmgSeverity, Date inDmgReported, Date inDmgRepaired) {
        UnitEquipDamageItem item = new UnitEquipDamageItem();
        item.setDmgitemDmgs(inUnitEquipDamages);
        item.setDmgitemType(inDmgType);
        item.setDmgitemComponent(inDmgComponent);
        item.setDmgitemSeverity(inDmgSeverity);
        item.setDmgitemReported(inDmgReported);
        item.setDmgitemRepaired(inDmgRepaired);
        Roastery.getHibernateApi().save((Object)item);
        return item;
    }

    public AuditEvent vetAuditEvent(AuditEvent inAuditEvent) {
        return inAuditEvent;
    }

    public String toString() {
        return (this.getDmgitemType() == null ? null : this.getDmgitemType().getEqdmgtypId()) + '/' + (this.getDmgitemComponent() == null ? null : this.getDmgitemComponent().getEqcmpId()) + '/' + (this.getDmgitemSeverity() == null ? null : this.getDmgitemSeverity().getKey());
    }

    public boolean isEquivalent(UnitEquipDamageItem inOther) {
        if (!ObjectUtils.equals((Object)this.getDmgitemComponent(), (Object)inOther.getDmgitemComponent())) {
            return false;
        }
        if (!ObjectUtils.equals((Object)this.getDmgitemType(), (Object)inOther.getDmgitemType())) {
            return false;
        }
        if (!ObjectUtils.equals((Object)((Object)this.getDmgitemSeverity()), (Object)((Object)inOther.getDmgitemSeverity()))) {
            return false;
        }
        if (!ObjectUtils.equals((Object)this.getDmgitemQuantity(), (Object)inOther.getDmgitemQuantity())) {
            return false;
        }
        if (!ObjectUtils.equals((Object)this.getDmgitemLength(), (Object)inOther.getDmgitemLength())) {
            return false;
        }
        if (!ObjectUtils.equals((Object)this.getDmgitemWidth(), (Object)inOther.getDmgitemWidth())) {
            return false;
        }
        return ObjectUtils.equals((Object)this.getDmgitemDepth(), (Object)inOther.getDmgitemDepth());
    }

    public UnitEquipDamageItem deepClone() {
        try {
            return (UnitEquipDamageItem)this.clone();
        }
        catch (CloneNotSupportedException e) {
            throw BizFailure.wrap((Throwable)e);
        }
    }

    public void update(IValueHolder inDamageVao) {
        if (inDamageVao == null) {
            return;
        }
        for (Object o : inDamageVao.getFields()) {
            IMetafieldId field = (IMetafieldId)o;
            if (HiberCache.getFieldType((String)field.getFieldId()) == null || inDamageVao.getFieldValue(field) == null) continue;
            this.setFieldValue(field, inDamageVao.getFieldValue(field));
        }
    }

    public void update(UnitEquipDamageItem inNewDamageItem) {
        if (inNewDamageItem == null) {
            return;
        }
        FieldChanges changes = inNewDamageItem.getUpdatedFieldChanges(this);
        Iterator itr = changes.getIterator();
        while (itr.hasNext()) {
            FieldChange fc = (FieldChange)itr.next();
            this.setFieldValue(fc.getMetafieldId(), fc.getNewValue());
        }
     //   this.setCustomFlexFields(inNewDamageItem.getCustomFlexFields());
    }

    public Object clone() throws CloneNotSupportedException {
        UnitEquipDamageItem clonedItem = (UnitEquipDamageItem)super.clone();
        clonedItem.setDmgitemGkey(null);
        return clonedItem;
    }

    public Class getArchiveClass() {
     //   return ArchiveUnitEquipDamageItem.class;
        return null;
    }

    public boolean doArchive() {
        return true;
    }

    protected FieldChanges getUpdatedFieldChanges(UnitEquipDamageItem inOldDamageItem) {
        if (inOldDamageItem == null) {
            return this.getNewFieldChanges();
        }
        FieldChanges fieldChanges = new FieldChanges();
        for (IMetafieldId dmgField : UnitField.UNIT_DAMAGE_EVENT_FIELDS) {
            this.addFieldChange(fieldChanges, dmgField, inOldDamageItem.getFieldValue(dmgField), this.getFieldValue(dmgField));
        }
        return fieldChanges;
    }

    protected FieldChanges getNewFieldChanges() {
        return new FieldChanges(this.getValueObject(UnitField.UNIT_DAMAGE_EVENT_FIELDS));
    }

    protected FieldChanges getDeletedFieldChanges() {
        FieldChanges fieldChanges = new FieldChanges();
        for (IMetafieldId dmgField : UnitField.UNIT_DAMAGE_EVENT_FIELDS) {
            this.addFieldChange(fieldChanges, dmgField, this.getFieldValue(dmgField), null);
        }
        return fieldChanges;
    }

    private void addFieldChange(FieldChanges inOutFieldChanges, IMetafieldId inFieldId, Object inOldValue, Object inNewValue) {
        if (inOldValue != null && inNewValue == null) {
            inOutFieldChanges.setFieldChange(inFieldId, inOldValue, inNewValue);
        } else if (inOldValue == null && inNewValue != null) {
            inOutFieldChanges.setFieldChange(inFieldId, inOldValue, inNewValue);
        } else if (inOldValue != null && !inOldValue.equals(inNewValue)) {
            inOutFieldChanges.setFieldChange(inFieldId, inOldValue, inNewValue);
        }
    }

    public void updateDamageItemFromVao(IValueHolder inDmgVao) {
        Object dmgTypeGkey = inDmgVao.getFieldValue(IInventoryField.DMGITEM_TYPE);
        EquipClassEnum equipClass = null;
        if (this.getDmgitemComponent() != null) {
            equipClass = this.getDmgitemComponent().getEqcmpEqClass();
        }
        EquipDamageType type = dmgTypeGkey instanceof String ? EquipDamageType.findEquipDamageType((String)((String)dmgTypeGkey), (EquipClassEnum)equipClass) : (EquipDamageType)HibernateApi.getInstance().load(EquipDamageType.class, (Serializable)dmgTypeGkey);
        Object dmgCmpGkey = inDmgVao.getFieldValue(IInventoryField.DMGITEM_COMPONENT);
        EqComponent component = dmgCmpGkey instanceof String ? EqComponent.findEqComponent((String)((String)dmgCmpGkey), (EquipClassEnum)equipClass) : (EqComponent)HibernateApi.getInstance().load(EqComponent.class, (Serializable)dmgCmpGkey);
        EqDamageSeverityEnum severity = (EqDamageSeverityEnum)((Object)inDmgVao.getFieldValue(IInventoryField.DMGITEM_SEVERITY));
        Double length = (Double)inDmgVao.getFieldValue(IInventoryField.DMGITEM_LENGTH);
        String location = (String)inDmgVao.getFieldValue(IInventoryField.DMGITEM_LOCATION);
        Long quantity = (Long)inDmgVao.getFieldValue(IInventoryField.DMGITEM_QUANTITY);
        Double width = (Double)inDmgVao.getFieldValue(IInventoryField.DMGITEM_WIDTH);
        Double depth = (Double)inDmgVao.getFieldValue(IInventoryField.DMGITEM_DEPTH);
        Date reported = (Date)inDmgVao.getFieldValue(IInventoryField.DMGITEM_REPORTED);
        Date repaired = (Date)inDmgVao.getFieldValue(IInventoryField.DMGITEM_REPAIRED);
        String description = (String)inDmgVao.getFieldValue(IInventoryField.DMGITEM_DESCRIPTION);
        this.setDmgitemType(type);
        this.setDmgitemComponent(component);
        this.setDmgitemSeverity(severity);
        this.setDmgitemLength(length);
        this.setDmgitemLocation(location);
        this.setDmgitemQuantity(quantity);
        this.setDmgitemWidth(width);
        this.setDmgitemDepth(depth);
        this.setDmgitemReported(reported);
        this.setDmgitemRepaired(repaired);
        this.setDmgitemDescription(description);
    }

    public boolean isChassisDamage() {
        return EquipClassEnum.CHASSIS.equals((Object)this.getDmgitemComponent().getEqcmpEqClass());
    }

    public boolean isContainerDamage() {
        return EquipClassEnum.CONTAINER.equals((Object)this.getDmgitemComponent().getEqcmpEqClass());
    }

    public boolean isAccessoryDamage() {
        return EquipClassEnum.ACCESSORY.equals((Object)this.getDmgitemComponent().getEqcmpEqClass());
    }

    public static UnitEquipDamageItem hydrate(Serializable inPrimaryKey) {
        return (UnitEquipDamageItem) HibernateApi.getInstance().load(UnitEquipDamageItem.class, inPrimaryKey);
    }

    public BizViolation validateChanges(FieldChanges inChanges) {
        BizViolation bv = super.validateChanges(inChanges);
        if (inChanges.hasFieldChange(IInventoryField.DMGITEM_REPORTED) || inChanges.hasFieldChange(IInventoryField.DMGITEM_REPAIRED)) {
            bv = ArgoUtils.appendErrorIfDate1AfterDate2((BizViolation)bv, (Date)this.getDmgitemReported(), (Date)ArgoUtils.timeNow(), (IPropertyKey) IInventoryPropertyKeys.REPORTED_AFTER_CURRENT_DATE, (IMetafieldId) IInventoryField.DMGITEM_REPORTED);
            bv = ArgoUtils.appendErrorIfDate1AfterDate2((BizViolation)bv, (Date)this.getDmgitemRepaired(), (Date)ArgoUtils.timeNow(), (IPropertyKey) IInventoryPropertyKeys.REPAIRED_AFTER_CURRENT_DATE, (IMetafieldId) IInventoryField.DMGITEM_REPAIRED);
            bv = ArgoUtils.appendErrorIfDate1AfterDate2((BizViolation)bv, (Date)this.getDmgitemReported(), (Date)this.getDmgitemRepaired(), (IPropertyKey) IInventoryPropertyKeys.REPORTED_AFTER_REPAIRED, (IMetafieldId) IInventoryField.DMGITEM_REPORTED);
        }
        return bv;
    }
}
