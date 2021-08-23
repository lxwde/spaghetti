package com.zpmc.ztos.infra.base.business.inventory;

import com.zpmc.ztos.infra.base.business.dataobject.UnitEquipDamagesDO;
import com.zpmc.ztos.infra.base.business.enums.argo.EquipClassEnum;
import com.zpmc.ztos.infra.base.business.enums.inventory.EqDamageSeverityEnum;
import com.zpmc.ztos.infra.base.business.equipments.EqComponent;
import com.zpmc.ztos.infra.base.business.equipments.Equipment;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.business.model.EntityIdFactory;
import com.zpmc.ztos.infra.base.common.database.HiberCache;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.common.exceptions.BizFailure;
import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;
import com.zpmc.ztos.infra.base.common.model.FieldChanges;
import com.zpmc.ztos.infra.base.common.model.Roastery;
import com.zpmc.ztos.infra.base.common.model.ValueObject;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.io.Serializable;
import java.util.*;

public class UnitEquipDamages extends UnitEquipDamagesDO {
    public UnitEquipDamages() {
        this.ensureItems();
    }

    static UnitEquipDamages create() {
        UnitEquipDamages damages = new UnitEquipDamages();
        Roastery.getHibernateApi().save((Object)damages);
        return damages;
    }

    public UnitEquipDamageItem addDamageItem(EquipDamageType inDmgType, EqComponent inDmgComponent, EqDamageSeverityEnum inDmgSeverity, Date inDmgReported, Date inDmgRepaired) {
        UnitEquipDamageItem item = new UnitEquipDamageItem();
        item.setDmgitemType(inDmgType);
        item.setDmgitemComponent(inDmgComponent);
        item.setDmgitemSeverity(inDmgSeverity);
        item.setDmgitemReported(inDmgReported);
        item.setDmgitemRepaired(inDmgRepaired);
        return this.addDamageItem(item);
    }

    public UnitEquipDamageItem addDamageItem(UnitEquipDamageItem inDamageItem) {
        Collection<UnitEquipDamageItem> items = this.ensureItems();
        inDamageItem.setDmgitemDmgs(this);
        items.add(inDamageItem);
        return inDamageItem;
    }

    public void deleteDamageItem(UnitEquipDamageItem inItem) {
        Collection<UnitEquipDamageItem> items = this.ensureItems();
        items.remove(inItem);
    }

    public void deleteAllDamageItems() {
        List items = this.getDmgsItems();
        if (items != null) {
            items.clear();
        }
    }

    public Iterator<UnitEquipDamageItem> getDamageItemsIterator() {
        Collection<UnitEquipDamageItem> items = this.ensureItems();
        return items.iterator();
    }

    private Collection<UnitEquipDamageItem> ensureItems() {
        ArrayList items = (ArrayList) this.getDmgsItems();
        if (items == null) {
            items = new ArrayList();
            this.setDmgsItems(items);
        }
        return items;
    }

    public IValueHolder[] getDmgsDamageItemsVao() {
        return this.obtainDmgsDamageItemsVao(false);
    }

    public IValueHolder[] getMobileDmgsDamageItemsVao() {
        return this.obtainDmgsDamageItemsVao(true);
    }

    public IValueHolder[] obtainDmgsDamageItemsVao(boolean inIgnoreRepaired) {
        Collection<UnitEquipDamageItem> items = this.ensureItems();
        ArrayList<ValueObject> dmgList = new ArrayList<ValueObject>();
        for (UnitEquipDamageItem damageItem : items) {
            if (inIgnoreRepaired && damageItem.getDmgitemRepaired() != null) continue;
            ValueObject vao = damageItem.getValueObject();
            vao.setFieldValue(IInventoryField.DMGITEM_TYPE, (Object)damageItem.getDmgitemType().getEqdmgtypId());
            vao.setFieldValue(UnitField.DMGITEM_TYPE_DESC, (Object)damageItem.getDmgitemType().getEqdmgtypDescription());
            vao.setFieldValue(IInventoryField.DMGITEM_COMPONENT, (Object)damageItem.getDmgitemComponent().getEqcmpId());
            vao.setFieldValue(UnitField.DMGITEM_CMP_DESC, (Object)damageItem.getDmgitemComponent().getEqcmpDescription());
            vao.setFieldValue(IInventoryField.DMGITEM_DESCRIPTION, (Object)damageItem.getDmgitemDescription());
            vao.setFieldValue(IArgoRefField.EQDMGTYP_GKEY, (Object)damageItem.getDmgitemType().getEqdmgtypGkey());
            vao.setFieldValue(IArgoRefField.EQCMP_GKEY, (Object)damageItem.getDmgitemComponent().getEqcmpGkey());
            HashMap customMap = new HashMap();
            if (damageItem.getCustomFlexFields() == null) {
                Set<IMetafieldId> customMfds = this.getEntityDynamicFields(damageItem);
                for (IMetafieldId customMfd : customMfds) {
                    customMap.put(customMfd, null);
                }
                if (!customMap.isEmpty()) {
                    vao.setFieldValue(IDynamicExtensionConsts.CUSTOM_FLEX_FIELDS, customMap);
                }
            }
            dmgList.add(vao);
        }
     //   return (IValueHolder[])dmgList.toArray((T[])new ValueObject[dmgList.size()]);
        return null;
    }

    @Nullable
    private Set<IMetafieldId> getEntityDynamicFields(DatabaseEntity inEntity) {
        if (inEntity.getEntityName() != null) {
            IMetafieldEntity entityEntry = Roastery.getMetafieldDictionary().getEntityEntry(EntityIdFactory.valueOf((String)inEntity.getEntityName()));
            return entityEntry != null ? entityEntry.getDynamicFields() : null;
        }
        return null;
    }

    public void setDmgsDamageItemsVao(IValueHolder[] inDmgsVaos) {
        List existingDamageItems = this.getDmgsItems();
        if (existingDamageItems != null) {
            existingDamageItems.clear();
        }
        for (IValueHolder dmgVao : inDmgsVaos) {
            Object dmgCmpGkey;
            EquipDamageType type;
            Object eqDmgTypeGkey = dmgVao.getFieldValue(IArgoRefField.EQDMGTYP_GKEY);
            EquipClassEnum equipClass = null;
            if (eqDmgTypeGkey != null) {
                type = (EquipDamageType) HibernateApi.getInstance().load(EquipDamageType.class, (Serializable)eqDmgTypeGkey);
            } else {
                Object dmgTypeGkey = dmgVao.getFieldValue(IInventoryField.DMGITEM_TYPE);
                equipClass = this.getOwningEquipmentClass();
                type = dmgTypeGkey instanceof String ? EquipDamageType.findEquipDamageType((String)((String)dmgTypeGkey), (EquipClassEnum)equipClass) : (EquipDamageType)HibernateApi.getInstance().load(EquipDamageType.class, (Serializable)dmgTypeGkey);
            }
            Object eqDmgCompGkey = dmgVao.getFieldValue(IArgoRefField.EQCMP_GKEY);
            EqComponent component = eqDmgCompGkey != null ? (EqComponent)HibernateApi.getInstance().load(EqComponent.class, (Serializable)eqDmgCompGkey) : ((dmgCmpGkey = dmgVao.getFieldValue(IInventoryField.DMGITEM_COMPONENT)) instanceof String ? EqComponent.findEqComponent((String)((String)dmgCmpGkey), (EquipClassEnum)equipClass) : (EqComponent)HibernateApi.getInstance().load(EqComponent.class, (Serializable)dmgCmpGkey));
            EqDamageSeverityEnum severity = (EqDamageSeverityEnum)((Object)dmgVao.getFieldValue(IInventoryField.DMGITEM_SEVERITY));
            Double length = (Double)dmgVao.getFieldValue(IInventoryField.DMGITEM_LENGTH);
            String location = (String)dmgVao.getFieldValue(IInventoryField.DMGITEM_LOCATION);
            Long quantity = (Long)dmgVao.getFieldValue(IInventoryField.DMGITEM_QUANTITY);
            Double width = (Double)dmgVao.getFieldValue(IInventoryField.DMGITEM_WIDTH);
            Double depth = (Double)dmgVao.getFieldValue(IInventoryField.DMGITEM_DEPTH);
            Date reported = (Date)dmgVao.getFieldValue(IInventoryField.DMGITEM_REPORTED);
            Date repaired = (Date)dmgVao.getFieldValue(IInventoryField.DMGITEM_REPAIRED);
            String description = (String)dmgVao.getFieldValue(IInventoryField.DMGITEM_DESCRIPTION);
            UnitEquipDamageItem damageItem = this.addDamageItem(type, component, severity, null, repaired);
            damageItem.setDmgitemLength(length);
            damageItem.setDmgitemLocation(location);
            damageItem.setDmgitemQuantity(quantity);
            damageItem.setDmgitemWidth(width);
            damageItem.setDmgitemDepth(depth);
            damageItem.setDmgitemReported(reported);
            damageItem.setDmgitemRepaired(repaired);
            damageItem.setDmgitemDescription(description);
            if (dmgVao.getFieldValue(IInventoryField.DMGITEM_GKEY) != null) {
                damageItem.setDmgitemGkey((Long)dmgVao.getFieldValue(IInventoryField.DMGITEM_GKEY));
            }
            for (Object metafield : dmgVao.getFields()) {
                IMetafieldId field = (IMetafieldId)metafield;
                IMetafield mfd = Roastery.getMetafieldDictionary().findMetafield(field);
                if (HiberCache.getFieldType((String)field.getFieldId()) == null || !mfd.isCustomField()) continue;
                damageItem.setFieldValue(field, dmgVao.getFieldValue(field));
            }
        }
    }

    @Nullable
    private EquipClassEnum getOwningEquipmentClass() {
        UnitEquipment ue;
        Long gkey;
        String ownerEntity = this.getDmgsOwnerEntityName();
        if ("Unit".equals(ownerEntity) && (gkey = this.getDmgsOwnerEntityGkey()) != null && (ue = (UnitEquipment)Roastery.getHibernateApi().get(Unit.class, (Serializable)gkey)) != null) {
            Equipment equipment = ue.getUeEquipment();
            return equipment.getEqClass();
        }
        return null;
    }

    public void attachDamagesToEntity(DatabaseEntity inEntity, IMetafieldId inDamagesField) {
        String entityName = inEntity.getEntityName();
        Long entityGkey = (Long)inEntity.getPrimaryKey();
        if (entityName.equals(this.getDmgsOwnerEntityName()) && entityGkey.equals(this.getDmgsOwnerEntityGkey())) {
            return;
        }
        if (this.getDmgsOwnerEntityGkey() != null) {
            throw BizFailure.create((String)("Attempt to attach Damages to an entity of class <" + entityName + "> with gkey <" + entityGkey + "> that is already attached to an entity of class <" + this.getDmgsOwnerEntityName() + "> with gkey <" + this.getDmgsOwnerEntityGkey() + ">"));
        }
        inEntity.setFieldValue(inDamagesField, (Object)this);
        this.setDmgsOwnerEntityName(entityName);
        this.setDmgsOwnerEntityGkey(entityGkey);
    }

    public void detachDamagesFromEntity(DatabaseEntity inEntity, IMetafieldId inDamagesField) {
        String entityName = inEntity.getEntityName();
        Serializable entityGkey = inEntity.getPrimaryKey();
        if (!entityName.equals(this.getDmgsOwnerEntityName()) || !entityGkey.equals(this.getDmgsOwnerEntityGkey())) {
            throw BizFailure.create((String)("Attempt to detach Damages from <" + entityName + "> with gkey <" + entityGkey + "> but damages belong to <" + this.getDmgsOwnerEntityName() + "> with gkey <" + this.getDmgsOwnerEntityGkey() + ">"));
        }
        inEntity.setFieldValue(inDamagesField, null);
        this.setDmgsOwnerEntityName(null);
        this.setDmgsOwnerEntityGkey(null);
    }

    public UnitEquipDamages cloneDamages() {
        UnitEquipDamages damages = new UnitEquipDamages();
        Iterator<UnitEquipDamageItem> it = this.getDamageItemsIterator();
        while (it.hasNext()) {
            UnitEquipDamageItem tranDmgItem = it.next();
            UnitEquipDamageItem item = new UnitEquipDamageItem();
            FieldChanges flds = new FieldChanges(tranDmgItem.getValueObject());
            flds.removeFieldChange(IInventoryField.DMGITEM_GKEY);
            flds.setFieldChange(IInventoryField.DMGITEM_COMPONENT, (Object)tranDmgItem.getDmgitemComponent().getPrimaryKey());
            flds.setFieldChange(IInventoryField.DMGITEM_TYPE, (Object)tranDmgItem.getDmgitemType().getPrimaryKey());
            item.applyFieldChanges(flds);
            damages.addDamageItem(item);
        }
        return damages;
    }

    public UnitEquipDamages cloneDamages(DatabaseEntity inOwner) {
        UnitEquipDamages cloned = this.cloneDamages();
        cloned.setDmgsOwnerEntityGkey((Long)inOwner.getPrimaryKey());
        cloned.setDmgsOwnerEntityName(inOwner.getEntityName());
        return cloned;
    }

    public EqDamageSeverityEnum calculateDamageSeverity() {
        EqDamageSeverityEnum result = EqDamageSeverityEnum.NONE;
        boolean haveDamages = false;
        Iterator<UnitEquipDamageItem> iterator = this.getDamageItemsIterator();
        while (iterator.hasNext()) {
            haveDamages = true;
            UnitEquipDamageItem dmgitem = iterator.next();
            if (dmgitem.getDmgitemRepaired() != null) continue;
            if (EqDamageSeverityEnum.MAJOR.equals((Object)dmgitem.getDmgitemSeverity())) {
                result = EqDamageSeverityEnum.MAJOR;
                continue;
            }
            if (!EqDamageSeverityEnum.MINOR.equals((Object)dmgitem.getDmgitemSeverity()) || !EqDamageSeverityEnum.NONE.equals((Object)result)) continue;
            result = EqDamageSeverityEnum.MINOR;
        }
        if (EqDamageSeverityEnum.NONE.equals((Object)result) && haveDamages) {
            result = EqDamageSeverityEnum.REPAIRED;
        }
        return result;
    }

    public UnitEquipDamages createPersistentCopy() {
        UnitEquipDamages damagesClone = this.cloneDamages();
        HibernateApi.getInstance().save((Object)damagesClone);
        return damagesClone;
    }

    @Nullable
    public UnitEquipDamageItem addDamageItem(IValueHolder inDmgVao) {
        Object dmgTypeGkey = inDmgVao.getFieldValue(IInventoryField.DMGITEM_TYPE);
        EquipClassEnum equipClass = this.getOwningEquipmentClass();
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
        UnitEquipDamageItem damageItem = this.addDamageItem(type, component, severity, null, repaired);
        damageItem.setDmgitemLength(length);
        damageItem.setDmgitemLocation(location);
        damageItem.setDmgitemQuantity(quantity);
        damageItem.setDmgitemWidth(width);
        damageItem.setDmgitemDepth(depth);
        damageItem.setDmgitemReported(reported);
        damageItem.setDmgitemRepaired(repaired);
        damageItem.setDmgitemDescription(description);
        return damageItem;
    }

    public static UnitEquipDamages hydrate(Serializable inPrimaryKey) {
        return (UnitEquipDamages)HibernateApi.getInstance().load(UnitEquipDamages.class, inPrimaryKey);
    }

    public Class getArchiveClass() {
     //   return ArchiveUnitEquipDamages.class;
        return null;
    }

    public boolean doArchive() {
        return true;
    }

}
