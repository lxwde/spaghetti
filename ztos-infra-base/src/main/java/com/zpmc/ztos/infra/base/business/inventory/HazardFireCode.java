package com.zpmc.ztos.infra.base.business.inventory;

import com.zpmc.ztos.infra.base.business.dataobject.HazardFireCodeDO;
import com.zpmc.ztos.infra.base.business.enums.inventory.ImdgClassEnum;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.business.model.MetafieldIdList;
import com.zpmc.ztos.infra.base.business.predicate.PredicateFactory;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.helps.ContextHelper;
import com.zpmc.ztos.infra.base.common.model.FieldChanges;
import com.zpmc.ztos.infra.base.utils.QueryUtils;

public class HazardFireCode extends HazardFireCodeDO {
    public static HazardFireCode findOrCreate(ImdgClassEnum inImdgClass, String inUnNbr, String inFireCode, String inFireCodeClass, String inDescription) {
        HazardFireCode fireCode = HazardFireCode.findByImdgClassAndUnNbr(inImdgClass, inUnNbr);
        if (fireCode == null) {
            fireCode = HazardFireCode.create(inImdgClass, inUnNbr, inFireCode, inFireCodeClass, inDescription);
        }
        return fireCode;
    }

    public static HazardFireCode create(ImdgClassEnum inImdgClass, String inUnNbr, String inFireCode, String inFireCodeClass, String inDescription) {
        HazardFireCode fireCode = new HazardFireCode();
        fireCode.setFirecodeImdgClass(inImdgClass);
        fireCode.setFirecodeUnNbr(inUnNbr);
        fireCode.setFirecodeFireCode(inFireCode);
        fireCode.setFirecodeFireCodeClass(inFireCodeClass);
        fireCode.setFirecodeDescription(inDescription);
        HibernateApi.getInstance().saveOrUpdate((Object)fireCode);
        return fireCode;
    }

    public static HazardFireCode findByImdgClassAndUnNbr(ImdgClassEnum inImdgClass, String inUnNbr) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"HazardFireCode").addDqPredicate(PredicateFactory.eq((IMetafieldId) IInventoryField.FIRECODE_IMDG_CLASS, (Object)((Object)inImdgClass))).addDqPredicate(PredicateFactory.eq((IMetafieldId) IInventoryField.FIRECODE_UN_NBR, (Object)inUnNbr));
        return (HazardFireCode)HibernateApi.getInstance().getUniqueEntityByDomainQuery(dq);
    }

    public void preProcessInsert(FieldChanges inOutMoreChanges) {
        super.preProcessInsert(inOutMoreChanges);
        if (this.getFirecodeComplex() == null) {
            this.setSelfAndFieldChange(IInventoryField.FIRECODE_COMPLEX, (Object) ContextHelper.getThreadComplex(), inOutMoreChanges);
        }
    }

    public BizViolation validateChanges(FieldChanges inChanges) {
        BizViolation bv = super.validateChanges(inChanges);
        MetafieldIdList uniqueFields = new MetafieldIdList();
        uniqueFields.add(IInventoryField.FIRECODE_IMDG_CLASS);
        uniqueFields.add(IInventoryField.FIRECODE_UN_NBR);
        if (!this.isUniqueInClass(uniqueFields)) {
            bv = BizViolation.createFieldViolation((IPropertyKey) IInventoryPropertyKeys.FIRECODE_NOT_UNIQUE, (BizViolation)bv, null, (Object)((Object)this.getFirecodeImdgClass()), (Object)this.getFirecodeUnNbr());
        }
        return bv;
    }

    public String toString() {
        return this.getFirecodeFireCode() + ": " + this.getFirecodeFireCodeClass();
    }

}
