package com.zpmc.ztos.infra.base.business.equipments;

import com.zpmc.ztos.infra.base.business.dataobject.EquipSerialRangeDO;
import com.zpmc.ztos.infra.base.business.enums.framework.LifeCycleStateEnum;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.business.model.MetafieldIdFactory;
import com.zpmc.ztos.infra.base.business.model.MetafieldIdList;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.model.FieldChanges;
import com.zpmc.ztos.infra.base.common.model.ValueObject;
import org.apache.log4j.Logger;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class EquipSerialRange extends EquipSerialRangeDO {
    
    private Map _userSelectionsForBooleanProps;
    private static final String APPLY_FIELD_SUFFIX = "Apply";
    private static final Logger LOGGER = Logger.getLogger(EquipSerialRange.class);

    public EquipSerialRange() {
        this.setEqsrlrngLifeCycleState(LifeCycleStateEnum.ACTIVE);
    }

    public void setLifeCycleState(LifeCycleStateEnum inLifeCycleState) {
        this.setEqsrlrngLifeCycleState(inLifeCycleState);
    }

    public LifeCycleStateEnum getLifeCycleState() {
        return this.getEqsrlrngLifeCycleState();
    }

    public static EquipSerialRange findEquipSerialRange(String inEqsrlPrefix, String inStartingSerialNbr, String inEndingSerialNbr) {
//        DomainQuery dq = QueryUtils.createDomainQuery((String)"EquipSerialRange").addDqPredicate(PredicateFactory.eq((IMetafieldId)IArgoRefField.EQSRLRNG_PREFIX, (Object)inEqsrlPrefix)).addDqPredicate(PredicateFactory.eq((IMetafieldId)IArgoRefField.EQSRLRNG_STARTING_SERIAL_NBR, (Object)inStartingSerialNbr)).addDqPredicate(PredicateFactory.eq((IMetafieldId)IArgoRefField.EQSRLRNG_ENDING_SERIAL_NBR, (Object)inEndingSerialNbr));
//        return (EquipSerialRange)HibernateApi.getInstance().getUniqueEntityByDomainQuery(dq);
        return null;
    }

    public static EquipSerialRange createEquipSerialRange(String inEqsrlPrefix, String inStartingSerialNbr, String inEndingSerialNbr) {
        EquipSerialRange esr = new EquipSerialRange();
        esr.setEqsrlrngPrefix(inEqsrlPrefix);
        esr.setEqsrlrngStartingSerialNbr(inStartingSerialNbr);
        esr.setEqsrlrngEndingSerialNbr(inEndingSerialNbr);
//        HibernateApi.getInstance().save((Object)esr);
        return esr;
    }

    public static EquipSerialRange findOrCreateEquipSerialRange(String inEqsrlPrefix, String inStartingSerialNbr, String inEndingSerialNbr) {
        EquipSerialRange esr = EquipSerialRange.findEquipSerialRange(inEqsrlPrefix, inStartingSerialNbr, inEndingSerialNbr);
        if (esr == null) {
            esr = EquipSerialRange.createEquipSerialRange(inEqsrlPrefix, inStartingSerialNbr, inEndingSerialNbr);
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info((Object)("findOrCreateEquipSerialRange: Created EquipSerialRange <" + esr.getEqsrlrngPrefix() + "> for range [" + inStartingSerialNbr + " - " + inEndingSerialNbr + "]"));
            }
            return esr;
        }
        return esr;
    }

    public static EquipSerialRange findEquipSerialRangeForCtrId(String inCtrId) {
        CtrNbrPrefix nbrPrfx = new CtrNbrPrefix(inCtrId);
        return EquipSerialRange.findEquipSerialRange(nbrPrfx.getCtrPrefix(), nbrPrfx.getCtrNbr());
    }

    @Nullable
    private static EquipSerialRange findEquipSerialRange(String inEquipPrefix, String inEquipNumber) {
//        Junction digitBetweenStartEndSerialNbr = PredicateFactory.conjunction().add(PredicateFactory.le((IMetafieldId)IArgoRefField.EQSRLRNG_STARTING_SERIAL_NBR, (Object)inEquipNumber)).add(PredicateFactory.ge((IMetafieldId)IArgoRefField.EQSRLRNG_ENDING_SERIAL_NBR, (Object)inEquipNumber));
//        DomainQuery dq = QueryUtils.createDomainQuery((String)"EquipSerialRange").addDqPredicate(PredicateFactory.eq((IMetafieldId)IArgoRefField.EQSRLRNG_PREFIX, (Object)inEquipPrefix)).addDqPredicate((PredicateIntf)digitBetweenStartEndSerialNbr);
//        Serializable[] primaryKeys = HibernateApi.getInstance().findPrimaryKeysByDomainQuery(dq);
//        return primaryKeys == null || primaryKeys.length == 0 ? null : EquipSerialRange.hydrate(primaryKeys[0]);
        return null;
    }

    public static EquipSerialRange hydrate(Serializable inPrimaryKey) {
//        return (EquipSerialRange)HibernateApi.getInstance().load(EquipSerialRange.class, inPrimaryKey);
        return null;
    }

    public BizViolation validateChanges(FieldChanges inChanges) {
        BizViolation bv = super.validateChanges(inChanges);
//        bv = this.checkRequiredField(bv, IArgoRefField.EQSRLRNG_STARTING_SERIAL_NBR);
//        bv = this.checkRequiredField(bv, IArgoRefField.EQSRLRNG_ENDING_SERIAL_NBR);
        try {
            this.validateSerialNbr(inChanges, IArgoRefField.EQSRLRNG_STARTING_SERIAL_NBR);
        }
        catch (BizViolation inBv) {
            bv = inBv.appendToChain(bv);
        }
        try {
            this.validateSerialNbr(inChanges, IArgoRefField.EQSRLRNG_ENDING_SERIAL_NBR);
        }
        catch (BizViolation inBv) {
            bv = inBv.appendToChain(bv);
        }
        try {
            this.verifyIfRangeOverlaps();
        }
        catch (BizViolation inBv) {
            bv = inBv.appendToChain(bv);
        }
        return bv;
    }

    @Override
    public BizViolation validateDeletion() {
        return null;
    }

    @Override
    public void populate(Set var1, FieldChanges var2) {

    }

    @Override
    public void preProcessInsert(FieldChanges var1) {

    }

    @Override
    public void preProcessUpdate(FieldChanges var1, FieldChanges var2) {

    }

    @Override
    public void preProcessDelete(FieldChanges var1) {

    }

    protected BizViolation validateUniqueness(FieldChanges inChanges, BizViolation inBizViolation) {
        return this.getEqsrlrngGkey() == null ? inBizViolation : super.validateUniqueness(inChanges, inBizViolation);
    }

    private void verifyIfRangeOverlaps() throws BizViolation {
        EquipSerialRange range = EquipSerialRange.findEquipSerialRange(this.getEqsrlrngPrefix(), this.getEqsrlrngStartingSerialNbr());
        if (range == null) {
            range = EquipSerialRange.findEquipSerialRange(this.getEqsrlrngPrefix(), this.getEqsrlrngEndingSerialNbr());
        }
        if (range != null && !range.equals(this)) {
            throw BizViolation.create((IPropertyKey) IArgoPropertyKeys.EQSRLRNG_RANGE_OVERLAPS, (Throwable)null, null, null, (Object[])new Object[]{IArgoRefField.EQSRLRNG_STARTING_SERIAL_NBR, range.getEqsrlrngStartingSerialNbr(), IArgoRefField.EQSRLRNG_ENDING_SERIAL_NBR, range.getEqsrlrngEndingSerialNbr()});
        }
    }

    private void validateSerialNbr(FieldChanges inChanges, IMetafieldId inSerialNbrField) throws BizViolation {
        if (inChanges.hasFieldChange(inSerialNbrField)) {
            String serialNbr = this.getFieldString(inSerialNbrField);
            try {
                Integer.parseInt(serialNbr);
            }
            catch (NumberFormatException e) {
                throw BizViolation.create((IPropertyKey)IArgoPropertyKeys.EQSRLRNG_SERIAL_NBR_MUST_BE_NUMERICAL, null, (Object)serialNbr, (Object)inSerialNbrField);
            }
        }
    }

    public void applyFieldChanges(FieldChanges inFieldChanges) {
        if (this._userSelectionsForBooleanProps == null) {
            this._userSelectionsForBooleanProps = new HashMap();
        }
        this.saveUserSelectionsForBooleanProps();
//        super.applyFieldChanges(inFieldChanges);
        this.updateFalse2NullIfPropertyNotSelected();
    }

    @Override
    public String getFieldString(IMetafieldId var1) {
        return null;
    }

    @Override
    public Long getFieldLong(IMetafieldId var1) {
        return null;
    }

    @Override
    public Date getFieldDate(IMetafieldId var1) {
        return null;
    }

    @Override
    public void setSelfAndFieldChange(IMetafieldId var1, Object var2, FieldChanges var3) {

    }

    @Override
    public IMetafieldId getPrimaryKeyMetaFieldId() {
        return null;
    }

    @Override
    public Object getField(IMetafieldId var1) {
        return null;
    }

    @Override
    public Object[] extractFieldValueArray(MetafieldIdList var1, boolean var2) {
        return new Object[0];
    }

    @Override
    public ValueObject getValueObject(MetafieldIdList var1) {
        return null;
    }

    @Override
    public ValueObject getValueObject() {
        return null;
    }

    @Override
    public ValueObject getDefaultCreateValues(IDefaultCreateValuesContext var1) {
        return null;
    }

    public void setFieldValue(IMetafieldId inFieldId, Object inFieldValue) {
//        Type type = HiberCache.getFieldType((String)inFieldId.getFieldId());
//        if (type == null) {
//            this._userSelectionsForBooleanProps.put(inFieldId, inFieldValue);
//        } else {
//            super.setFieldValue(inFieldId, inFieldValue);
//        }
    }

    public boolean getEqsrlrngLengthMmApply() {
        return this.getEqsrlrngLengthMm() != null;
    }

    public boolean getEqsrlrngHeightMmApply() {
        return this.getEqsrlrngHeightMm() != null;
    }

    public boolean getEqsrlrngWidthMmApply() {
        return this.getEqsrlrngWidthMm() != null;
    }

    public boolean getEqsrlrngTareWeightKgApply() {
        return this.getEqsrlrngTareWeightKg() != null;
    }

    public boolean getEqsrlrngSafeWeightKgApply() {
        return this.getEqsrlrngSafeWeightKg() != null;
    }

    public boolean getEqsrlrngRfrTypeApply() {
        return this.getEqsrlrngRfrType() != null;
    }

    public boolean getEqsrlrngIsoGroupApply() {
        return this.getEqsrlrngIsoGroup() != null;
    }

    public boolean getEqsrlrngIsSuperFreezeReeferApply() {
        return this.getEqsrlrngIsSuperFreezeReefer() != null;
    }

    public boolean getEqsrlrngIsControlledAtmosphereReeferApply() {
        return this.getEqsrlrngIsControlledAtmosphereReefer() != null;
    }

    public boolean getEqsrlrngNoStowOnTopIfEmptyApply() {
        return this.getEqsrlrngNoStowOnTopIfEmpty() != null;
    }

    public boolean getEqsrlrngNoStowOnTopIfLadenApply() {
        return this.getEqsrlrngNoStowOnTopIfLaden() != null;
    }

    public boolean getEqsrlrngMustStowBelowDeckApply() {
        return this.getEqsrlrngMustStowBelowDeck() != null;
    }

    public boolean getEqsrlrngMustStowAboveDeckApply() {
        return this.getEqsrlrngMustStowAboveDeck() != null;
    }

    public boolean getEqsrlrngUsesAccessoriesApply() {
        return this.getEqsrlrngUsesAccessories() != null;
    }

    public boolean getEqsrlrngIsTemperatureControlledApply() {
        return this.getEqsrlrngIsTemperatureControlled() != null;
    }

    public boolean getEqsrlrngOogOkApply() {
        return this.getEqsrlrngOogOk() != null;
    }

    public boolean getEqsrlrngIsUnsealableApply() {
        return this.getEqsrlrngIsUnsealable() != null;
    }

    public boolean getEqsrlrngHasWheelsApply() {
        return this.getEqsrlrngHasWheels() != null;
    }

    public boolean getEqsrlrngIsOpenApply() {
        return this.getEqsrlrngIsOpen() != null;
    }

    public IMetafieldId getScopeFieldId() {
        return IArgoRefField.EQSRLRNG_SCOPE;
    }

    public IMetafieldId getNaturalKeyField() {
        return IArgoRefField.EQSRLRNG_GKEY;
    }

    private void saveUserSelectionsForBooleanProps() {
//        ClassMetadata classMetadata = HiberCache.getClassMetadata((String)this.getEntityName());
//        String[] fieldIds = classMetadata.getPropertyNames();
//        for (int i = 0; i < fieldIds.length; ++i) {
//            String fieldId = fieldIds[i];
//            Type type = HiberCache.getFieldType((String)fieldId);
//            if (type.getReturnedClass() != Boolean.class) continue;
//            IMetafieldId applyMetafieldId = MetafieldIdFactory.valueOf((String)(fieldId + APPLY_FIELD_SUFFIX));
//            Object value = super.getFieldValue(applyMetafieldId);
//            this._userSelectionsForBooleanProps.put(applyMetafieldId, value);
//        }
    }

    private void updateFalse2NullIfPropertyNotSelected() {
        for (Object applyMetafieldId : this._userSelectionsForBooleanProps.keySet()) {
            Object selection = this._userSelectionsForBooleanProps.get((Object)applyMetafieldId);
            if (!selection.equals(Boolean.FALSE)) continue;
            String applyFieldId = applyMetafieldId.toString();
            String fieldId = applyFieldId.substring(0, applyFieldId.indexOf(APPLY_FIELD_SUFFIX));
            IMetafieldId metafieldId = MetafieldIdFactory.valueOf((String)fieldId);
//            super.setFieldValue(metafieldId, null);
        }
    }

    @Override
    public Object getFieldValue(IMetafieldId var1) {
        return null;
    }

    private static class CtrNbrPrefix {
        private final Equipment _temp = new Equipment();

        CtrNbrPrefix(String inCtrId) {
            this._temp.setEquipmentIdFields(inCtrId);
        }

        public String getCtrNbr() {
            return this._temp.getEqIdNbrOnly();
        }

        public String getCtrPrefix() {
            return this._temp.getEqIdPrefix();
        }
    }

}
