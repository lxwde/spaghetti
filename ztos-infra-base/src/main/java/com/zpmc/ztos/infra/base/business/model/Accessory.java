package com.zpmc.ztos.infra.base.business.model;

import com.zpmc.ztos.infra.base.business.dataobject.AccessoryDO;
import com.zpmc.ztos.infra.base.business.enums.argo.DataSourceEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.EquipClassEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.EquipMaterialEnum;
import com.zpmc.ztos.infra.base.business.equipments.EquipType;
import com.zpmc.ztos.infra.base.business.equipments.Equipment;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.business.predicate.PredicateFactory;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;
import com.zpmc.ztos.infra.base.utils.QueryUtils;

public class Accessory extends AccessoryDO {
    public Accessory() {
        this.setEqClass(EquipClassEnum.ACCESSORY);
        this.setEqMaterial(EquipMaterialEnum.UNKNOWN);
    }

    public static Accessory findAccessory(String inAccId) {
        String lookupId = Accessory.getEqLookupKey(inAccId);
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"Accessory").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoRefField.EQ_ID_NO_CHECK_DIGIT, (Object)lookupId));
        try {
            return (Accessory) HibernateApi.getInstance().getUniqueEntityByDomainQuery(dq);
        }
        catch (Exception e) {
            return null;
        }
    }

    public static Accessory resolveAccFromEq(Equipment inEq) {
        Accessory acc = null;
        if (inEq != null && EquipClassEnum.ACCESSORY.equals((Object)inEq.getEqClass())) {
            acc = (Accessory)HibernateApi.getInstance().downcast((DatabaseEntity)inEq, Accessory.class);
        }
        return acc;
    }

    public static Accessory getAccessory(String inAccId) {
        Equipment eq = Equipment.findEquipment(inAccId);
        return Accessory.resolveAccFromEq(eq);
    }

    public static Accessory findOrCreateAccessory(String inAccId, String inEquipTypeId, DataSourceEnum inDataSource) throws BizViolation {
        Accessory acry = Accessory.findAccessory(inAccId);
        if (acry != null) {
            return acry;
        }
        return Accessory.createAccessory(inAccId, inEquipTypeId, inDataSource);
    }

    public static Accessory createAccessory(String inAccId, String inEquipTypeId, DataSourceEnum inDataSource) throws BizViolation {
        Accessory acry = new Accessory();
        acry.setEquipmentIdFields(inAccId);
        acry.setEqAcryDescription("??" + inAccId + "??");
        EquipType equipType = EquipType.findEquipType(inEquipTypeId);
        if (equipType == null) {
            throw BizViolation.createFieldViolation((IPropertyKey) IArgoPropertyKeys.UNKNOWN_ACC_TYPE, null, (IMetafieldId) IArgoRefField.EQTYP_ID, (Object)inEquipTypeId);
        }
        acry.setEqEquipType(equipType);
        acry.updateEquipTypeProperties(equipType);
        acry.setEqDataSource(inDataSource);
        HibernateApi.getInstance().save((Object)acry);
        return acry;
    }

    @Override
    public void setFieldValue(IMetafieldId inFieldId, Object inFieldValue) {
        if (IArgoBizMetafield.ACRY_EQ_TYPE.equals((Object)inFieldId)) {
            super.setFieldValue(IArgoRefField.EQ_EQUIP_TYPE, inFieldValue);
        } else {
            super.setFieldValue(inFieldId, inFieldValue);
        }
    }

    public Object getAcryEqType() {
        return this.getEqEquipType().getPrimaryKey();
    }

    public String toString() {
        String iso = this.getEqEquipType() != null ? this.getEqEquipType().getEqtypId() : "????";
        return "Acry[" + this.getEqIdFull() + ':' + iso + ':' + this.getEqEquipType() + ']';
    }

}
