package com.zpmc.ztos.infra.base.business.equipments;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;
import com.zpmc.ztos.infra.base.business.dataobject.EquipmentDO;
import com.zpmc.ztos.infra.base.business.enums.argo.*;
import com.zpmc.ztos.infra.base.business.enums.framework.LifeCycleStateEnum;
import com.zpmc.ztos.infra.base.business.enums.framework.PredicateVerbEnum;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.business.model.Container;
import com.zpmc.ztos.infra.base.business.model.MetafieldIdList;
import com.zpmc.ztos.infra.base.business.model.Point3D;
import com.zpmc.ztos.infra.base.business.model.ScopedBizUnit;
import com.zpmc.ztos.infra.base.business.predicate.PredicateFactory;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.exceptions.EqNotFoundException;
import com.zpmc.ztos.infra.base.common.helps.ContextHelper;
import com.zpmc.ztos.infra.base.common.model.*;
import com.zpmc.ztos.infra.base.common.scopes.Facility;
import org.apache.log4j.Logger;


import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class Equipment extends EquipmentDO implements IArgoReferenceEntity {
    private static final String EMPTY = "EMPTY";
    private static final String EMPTY_CHECK_DIGIT = "??";
    private PropertyGroupEnum _propertyGroupEnum;
    private static final Map<DataSourceEnum, Integer> QUALITY = new HashMap<DataSourceEnum, Integer>();
    private static final MetafieldIdList EQUIP_LIST;
    private static final Map<IMetafieldId, PropertyGroupEnum> ID_PROPERTY_GROUP_ENUM_MAP;
    private static final int NUM_GKEYS_PER_BUCKET = 1000;
    private static final Logger LOGGER = null;

    public Equipment() {
        this.setEqNoStowOnTopIfEmpty(Boolean.FALSE);
        this.setEqNoStowOnTopIfLaden(Boolean.FALSE);
        this.setEqMustStowAboveDeck(Boolean.FALSE);
        this.setEqMustStowBelowDeck(Boolean.FALSE);
        this.setEqIsPOS(Boolean.FALSE);
        this.setEqIsInsulated(Boolean.FALSE);
        this.setEqLengthMm(0L);
        this.setEqHeightMm(0L);
        this.setEqWidthMm(0L);
        this.setEqIsoGroup(EquipIsoGroupEnum.GP);
        this.setEqUsesAccessories(Boolean.FALSE);
        this.setEqIsTemperatureControlled(Boolean.FALSE);
        this.setEqOogOk(Boolean.FALSE);
        this.setEqIsUnsealable(Boolean.FALSE);
        this.setEqHasWheels(Boolean.FALSE);
        this.setEqIsOpen(Boolean.FALSE);
        this.setEqTareWeightKg(0.0);
        this.setEqSafeWeightKg(0.0);
        this.setEqMaterial(EquipMaterialEnum.UNKNOWN);
        this.setEqDataSource(DataSourceEnum.UNKNOWN);
        this.setEqLifeCycleState(LifeCycleStateEnum.ACTIVE);
        this.setEqLocPdsCoordinates(new Point3D());
    }

    public void setLifeCycleState(LifeCycleStateEnum inLifeCycleState) {
        this.setEqLifeCycleState(inLifeCycleState);
    }

    public LifeCycleStateEnum getLifeCycleState() {
        return this.getEqLifeCycleState();
    }

    @Override
    public void enhanceFieldChanges(FieldChanges inOutFieldChanges) {
        if (this.getEqGkey() == null && !inOutFieldChanges.hasFieldChange(IArgoRefField.EQ_DATA_SOURCE)) {
//            inOutFieldChanges.setFieldChange(IArgoRefField.EQ_DATA_SOURCE, (Object)ContextHelper.getThreadDataSource());
        }
    }

    @Nullable
    public static Equipment findEquipment(String inEqId) {
        Equipment eq;
        try {
            eq = Equipment.loadEquipment(inEqId);
        }
        catch (EqNotFoundException e) {
            eq = null;
        }
        return eq;
    }

    public static Equipment loadEquipment(String inEqId) throws EqNotFoundException {
        Container ctr = Container.findContainerWithoutValidation(inEqId);
        if (ctr != null) {
            return ctr;
        }
        Chassis chs = Chassis.findChassis(inEqId);
        if (chs != null) {
            return chs;
        }
//        Accessory acc = Accessory.findAccessory(inEqId);
//        if (acc != null) {
//            return acc;
//        }
        throw new EqNotFoundException(inEqId);
    }

    public static String getEqLookupKey(String inEqId) {
        if (inEqId == null) {
            return "";
        }
        String eqIdKey = inEqId.trim();
        if (Equipment.isBicContainerId(eqIdKey)) {
            eqIdKey = eqIdKey.substring(0, 10);
        }
        return eqIdKey;
    }

    public static boolean isBicContainerId(String inTrimmedEqId) {
        CheckDigitAlgorithmEnum checkDigitAlgorithm;
        boolean isBic = false;
        if (inTrimmedEqId.length() == 11 && inTrimmedEqId.charAt(3) == 'U' && !CheckDigitAlgorithmEnum.PARTOFID.equals((Object)(checkDigitAlgorithm = EquipPrefix.findEquipPrefixCheckDigitAlgorithm(inTrimmedEqId)))) {
            isBic = true;
        }
        return isBic;
    }

    public void setEquipmentIdFields(String inEqId) {
        String number;
        String prefix;
        String checkDigits;
        String noCheckDigit;
        String fullId = inEqId.trim();
        if (Equipment.isBicContainerId(fullId)) {
            noCheckDigit = fullId.substring(0, 10);
            checkDigits = fullId.substring(10, fullId.length());
        } else {
            noCheckDigit = fullId;
            checkDigits = EMPTY_CHECK_DIGIT;
        }
        int firstDigit = -1;
        for (int i = 0; i < fullId.length(); ++i) {
            char c = fullId.charAt(i);
            if (!Character.isDigit(c)) continue;
            firstDigit = i;
            break;
        }
        if (firstDigit == -1) {
            prefix = fullId;
            number = EMPTY;
        } else if (firstDigit == 0) {
            prefix = EMPTY;
            number = fullId;
        } else {
            prefix = fullId.substring(0, firstDigit);
            number = fullId.substring(firstDigit);
        }
        this.setEqIdFull(fullId);
        this.setEqIdNoCheckDigit(noCheckDigit);
        this.setEqIdCheckDigit(checkDigits);
        this.setEqIdPrefix(prefix);
        this.setEqIdNbrOnly(number);
    }

    public IMetafieldId getScopeFieldId() {
        return IArgoRefField.EQ_SCOPE;
    }

    public IMetafieldId getNaturalKeyField() {
        return IArgoRefField.EQ_ID_NO_CHECK_DIGIT;
    }

    public static boolean isCheckDigitCorrect(EquipClassEnum inEqClass, String inEqIdFull) {
        boolean isCorrect = EquipClassEnum.CONTAINER.equals((Object)inEqClass) ? Container.isCheckDigitCorrect(inEqIdFull) : false;
        return isCorrect;
    }

    public void upgradeEqType(String inEqTypeId, DataSourceEnum inDataSource) {
        this.upgradeEqType(inEqTypeId, inDataSource, ContextHelper.getThreadFacility());
    }

    public void upgradeEqType(String inEqTypeId, DataSourceEnum inDataSource, Facility inFacility) {
//        if (inEqTypeId != null) {
//            String existingEqTypeId;
//            String string = existingEqTypeId = this.getEqEquipType() == null ? null : this.getEqEquipType().getEqtypId();
//            if (!inEqTypeId.equals(existingEqTypeId)) {
//                if (existingEqTypeId == null || existingEqTypeId.equals("UNKN") || this.shouldAcceptUpdate(inDataSource, this.getEqDataSource(), PropertyGroupEnum.EQUIP_TYPE)) {
//                    EquipType eqType = EquipType.findOrCreateEquipType(inEqTypeId);
//                    this.setEqEquipType(eqType);
//                    this.updateEquipTypeProperties(eqType);
//                    this.setEqDataSource(inDataSource);
//                    LOGGER.warn((Object)("recordUnitEvent: upgraded existing Equipment " + this.getEqIdFull() + " from <" + existingEqTypeId + ">, to <" + inEqTypeId + "> old source <" + this.getEqDataSource().getName() + ">, new source <" + inDataSource.getName() + ">"));
//                } else {
//                    LOGGER.warn((Object)("recordUnitEvent: discrepant eqType for " + this.getEqIdFull() + " in DB: <" + existingEqTypeId + ">, input: <" + inEqTypeId + "> not updated as existing datasource <" + (Object)((Object)this.getEqDataSource()) + ">, is of higher quality than input datasource <" + (Object)((Object)inDataSource) + ">"));
////                    MessageCollector mc = MessageCollectorUtils.getMessageCollector();
////                    if (mc != null) {
////                        mc.appendMessage(MessageLevelEnum.WARNING, ArgoPropertyKeys.WARNING_ISO_NOT_UPDATED, null, new Object[]{this.getEqIdFull(), existingEqTypeId, inEqTypeId, this.getEqDataSource().getName(), inDataSource.getName()});
////                    }
//                }
//            } else {
//                Long existingEqTypeGkey;
//                EquipType eqType = this.findActiveEquipType(inEqTypeId, inFacility);
//                Long l = existingEqTypeGkey = this.getEqEquipType() == null ? null : this.getEqEquipType().getEqtypGkey();
//                if (eqType != null && !eqType.getEqtypGkey().equals(existingEqTypeGkey)) {
//                    if (existingEqTypeId == null || existingEqTypeId.equals("UNKN") || this.shouldAcceptUpdate(inDataSource, this.getEqDataSource(), PropertyGroupEnum.EQUIP_TYPE)) {
//                        this.setEqEquipType(eqType);
//                        this.updateEquipTypeProperties(eqType);
//                        this.setEqDataSource(inDataSource);
//                        LOGGER.warn((Object)("recordUnitEvent: upgraded existing Equipment " + this.getEqIdFull() + " from <" + existingEqTypeId + ">, to <" + inEqTypeId + "> old source <" + this.getEqDataSource().getName() + ">, new source <" + inDataSource.getName() + ">"));
//                    } else {
//                        LOGGER.warn((Object)("recordUnitEvent: discrepant eqType for " + this.getEqIdFull() + " in DB: <" + existingEqTypeId + ">, input: <" + inEqTypeId + "> not updated as existing datasource <" + (Object)((Object)this.getEqDataSource()) + ">, is of higher quality than input datasource <" + (Object)((Object)inDataSource) + ">"));
////                        MessageCollector mc = MessageCollectorUtils.getMessageCollector();
////                        if (mc != null) {
////                            mc.appendMessage(MessageLevelEnum.WARNING, ArgoPropertyKeys.WARNING_ISO_NOT_UPDATED, null, new Object[]{this.getEqIdFull(), existingEqTypeId, inEqTypeId, this.getEqDataSource().getName(), inDataSource.getName()});
////                        }
//                    }
//                }
//            }
//        }
    }

    protected EquipType findActiveEquipType(String inEqIso, Facility inFacility) {
        Long ensetGkey;
        EquipType equipType = null;
//        EntitySetUse entitySetUse = null;
//        if (inFacility != null) {
//            entitySetUse = EntitySetUse.findEntitySetUse("EquipType", ScopeEnum.FACILITY, inFacility.getFcyGkey());
//        } else if (ContextHelper.getThreadComplex() != null) {
//            entitySetUse = EntitySetUse.findEntitySetUse("EquipType", ScopeEnum.COMPLEX, ContextHelper.getThreadComplex().getCpxGkey());
//        } else if (entitySetUse == null) {
//            entitySetUse = EntitySetUse.findEntitySetUse("EquipType", ScopeEnum.OPERATOR, ContextHelper.getThreadOperator().getOprGkey());
//        }
//        if (entitySetUse == null) {
//            equipType = this.findActiveEquipType(inEqIso);
//        }
//        Long l = ensetGkey = entitySetUse != null ? entitySetUse.getEnstuEntitySet().getEnsetGkey() : null;
//        if (equipType == null && ensetGkey != null) {
//            equipType = this.findEquipTypeByEntitySetGkey(inEqIso, ensetGkey);
//        }
        return equipType;
    }

    protected boolean isNotEmpty(String inStr) {
        return inStr != null && !inStr.trim().isEmpty();
    }

    private EquipType findEquipTypeByEntitySetGkey(String inEqIso, Long inEnsetGkey) {
        EquipType equipType = null;
//        if (inEnsetGkey != null) {
//            IDomainQuery dq = QueryUtils.createDomainQuery((String)"EquipType").addDqPredicate(PredicateFactory.eq((IMetafieldId)IArgoRefField.EQTYP_ID, (Object)inEqIso)).addDqPredicate(PredicateFactory.eq((IMetafieldId)IArgoRefField.EQTYP_SCOPE, (Object)inEnsetGkey));
//            dq.setScopingEnabled(false);
//            equipType = (EquipType)HibernateApi.getInstance().getUniqueEntityByDomainQuery(dq);
//        }
        return equipType;
    }

    @Nullable
    protected EquipType findActiveEquipType(String inEqIso) {
        EquipType equipType = null;
        if (this.isNotEmpty(inEqIso)) {
            equipType = EquipType.findEquipType(inEqIso);
        }
        return equipType;
    }

    public void upgradeEqTareWeight(Double inEqtareWeight, DataSourceEnum inDataSource) {
        if (inEqtareWeight != null && inEqtareWeight.longValue() > 0L) {
            Double existingEqTareWeight;
            Double d = existingEqTareWeight = this.getEqTareWeightKg() == null ? null : this.getEqTareWeightKg();
            if (!inEqtareWeight.equals(existingEqTareWeight)) {
                if (this.shouldAcceptUpdate(inDataSource, this.getEqDataSource(), PropertyGroupEnum.TARE_WEIGHT_KG)) {
                    this.setEqTareWeightKg(inEqtareWeight);
                    this.setEqDataSource(inDataSource);
                    LOGGER.warn((Object)("upgradeEqTareWeight: upgraded existing Equipment " + this.getEqIdFull() + " from <" + existingEqTareWeight + ">, to <" + inEqtareWeight + ">"));
                } else {
                    LOGGER.warn((Object)("recordUnitEvent: discrepant eqType for " + this.getEqIdFull() + " in DB: <" + existingEqTareWeight + ">, input: <" + inEqtareWeight + "> not updated as existing datasource <" + (Object)((Object)this.getEqDataSource()) + ">, is of higher quality than input datasource <" + (Object)((Object)inDataSource) + ">"));
                }
            }
        }
    }

    public void upgradeEqHeight(Long inHeight, DataSourceEnum inDataSource) {
        if (inHeight != null && inHeight > 0L) {
            Long existingHeight = this.getEqHeightMm();
            if (existingHeight == null) {
                this.setEqHeightMm(inHeight);
            } else if (!inHeight.equals(existingHeight)) {
                if (this.shouldAcceptUpdate(inDataSource, this.getEqDataSource(), PropertyGroupEnum.HEIGHT_MM)) {
                    this.setEqHeightMm(inHeight);
                    LOGGER.warn((Object)("upgradeEqHeight: upgraded height for existing Equipment " + this.getEqIdFull() + " from <" + existingHeight + ">, to <" + inHeight + ">"));
                } else {
                    LOGGER.warn((Object)("upgradeEqHeight: discrepant height for " + this.getEqIdFull() + " in DB: <" + existingHeight + ">, input: <" + inHeight + "> not updated as existing datasource <" + (Object)((Object)this.getEqDataSource()) + ">, is of higher quality than input datasource <" + (Object)((Object)inDataSource) + ">"));
                }
            }
        }
    }

    public void upgradeEqStrengthCode(String inStrengthCode, DataSourceEnum inDataSource) {
        if (inStrengthCode != null) {
            String existingStrenth = this.getEqStrengthCode();
            if (existingStrenth == null) {
                this.setEqStrengthCode(inStrengthCode);
            } else if (!inStrengthCode.equals(existingStrenth)) {
                if (this.shouldAcceptUpdate(inDataSource, this.getEqDataSource(), PropertyGroupEnum.STRENGTH_CODE)) {
                    this.setEqStrengthCode(inStrengthCode);
                    LOGGER.warn((Object)("upgradeEqStrengthCode: upgraded existing strength code for Equipment " + this.getEqIdFull() + " from <" + existingStrenth + ">, to <" + inStrengthCode + ">"));
                } else {
                    LOGGER.warn((Object)("upgradeEqStrengthCode: discrepant strength code for " + this.getEqIdFull() + " in DB: <" + existingStrenth + ">, input: <" + inStrengthCode + "> not updated as existing datasource <" + (Object)((Object)this.getEqDataSource()) + ">, is of higher quality than input datasource <" + (Object)((Object)inDataSource) + ">"));
                }
            }
        }
    }

    public void upgradeEqCsc(String inEqCsc, DataSourceEnum inDataSource) {
        if (inEqCsc != null) {
            String existingCsc = this.getEqCscExpiration();
            if (existingCsc == null) {
                this.setEqCscExpiration(inEqCsc);
            } else if (!inEqCsc.equals(existingCsc)) {
                if (this.shouldAcceptUpdate(inDataSource, this.getEqDataSource(), PropertyGroupEnum.CSC_EXPIRATION)) {
                    this.setEqCscExpiration(inEqCsc);
                    LOGGER.warn((Object)("upgradeEqCsc: upgraded existing csc date for Equipment " + this.getEqIdFull() + " from <" + existingCsc + ">, to <" + inEqCsc + ">"));
                } else {
                    LOGGER.warn((Object)("upgradeEqCsc: discrepant CSC  for " + this.getEqIdFull() + " in DB: <" + existingCsc + ">, input: <" + inEqCsc + "> not updated as existing datasource <" + (Object)((Object)this.getEqDataSource()) + ">, is of higher quality than input datasource <" + (Object)((Object)inDataSource) + ">"));
                }
            }
        }
    }

    public void upgradeEqBuildDate(Date inEqBuildDate, DataSourceEnum inDataSource) {
        if (inEqBuildDate != null) {
            Date existingBuildDate = this.getEqBuildDate();
            if (existingBuildDate == null) {
                this.setEqBuildDate(inEqBuildDate);
            } else if (!inEqBuildDate.equals(existingBuildDate)) {
                if (this.shouldAcceptUpdate(inDataSource, this.getEqDataSource(), PropertyGroupEnum.BUILD_DATE)) {
                    this.setEqBuildDate(inEqBuildDate);
                    LOGGER.warn((Object)("upgradeEqBuildDate: upgraded existing build date for Equipment " + this.getEqIdFull() + " from <" + existingBuildDate + ">, to <" + inEqBuildDate + ">"));
                } else {
                    LOGGER.warn((Object)("upgradeEqBuildDate: discrepant Build Date  for " + this.getEqIdFull() + " in DB: <" + existingBuildDate + ">, input: <" + inEqBuildDate + "> not updated as existing datasource <" + (Object)((Object)this.getEqDataSource()) + ">, is of higher quality than input datasource <" + (Object)((Object)inDataSource) + ">"));
                }
            }
        }
    }

    protected void updateEquipTypeProperties(EquipType inEquipType) {
        FieldChanges changes = new FieldChanges();
        this.updateEquipTypeProperties(changes, inEquipType);
    }

    protected FieldChanges updateEquipTypeProperties(FieldChanges inChanges, EquipType inEquipType) {
        FieldChanges moreChanges = new FieldChanges();
        this.setDenormalizedEqtypPropValue(inChanges, IArgoRefField.EQ_NO_STOW_ON_TOP_IF_EMPTY, inEquipType.getEqtypNoStowOnTopIfEmpty(), moreChanges);
        this.setDenormalizedEqtypPropValue(inChanges, IArgoRefField.EQ_NO_STOW_ON_TOP_IF_LADEN, inEquipType.getEqtypNoStowOnTopIfLaden(), moreChanges);
        this.setDenormalizedEqtypPropValue(inChanges, IArgoRefField.EQ_MUST_STOW_ABOVE_DECK, inEquipType.getEqtypMustStowAboveDeck(), moreChanges);
        this.setDenormalizedEqtypPropValue(inChanges, IArgoRefField.EQ_MUST_STOW_BELOW_DECK, inEquipType.getEqtypMustStowBelowDeck(), moreChanges);
        this.setDenormalizedEqtypPropValue(inChanges, IArgoRefField.EQ_LENGTH_MM, inEquipType.getEqtypLengthMm(), moreChanges);
        this.setDenormalizedEqtypPropValue(inChanges, IArgoRefField.EQ_HEIGHT_MM, inEquipType.getEqtypHeightMm(), moreChanges);
        this.setDenormalizedEqtypPropValue(inChanges, IArgoRefField.EQ_WIDTH_MM, inEquipType.getEqtypWidthMm(), moreChanges);
        this.setDenormalizedEqtypPropValue(inChanges, IArgoRefField.EQ_ISO_GROUP, (Object)inEquipType.getEqtypIsoGroup(), moreChanges);
        this.setDenormalizedEqtypPropValue(inChanges, IArgoRefField.EQ_USES_ACCESSORIES, inEquipType.getEqtypUsesAccessories(), moreChanges);
        this.setDenormalizedEqtypPropValue(inChanges, IArgoRefField.EQ_IS_TEMPERATURE_CONTROLLED, inEquipType.getEqtypIsTemperatureControlled(), moreChanges);
        this.setDenormalizedEqtypPropValue(inChanges, IArgoRefField.EQ_OOG_OK, inEquipType.getEqtypOogOk(), moreChanges);
        this.setDenormalizedEqtypPropValue(inChanges, IArgoRefField.EQ_IS_UNSEALABLE, inEquipType.getEqtypIsUnsealable(), moreChanges);
        this.setDenormalizedEqtypPropValue(inChanges, IArgoRefField.EQ_HAS_WHEELS, inEquipType.getEqtypHasWheels(), moreChanges);
        this.setDenormalizedEqtypPropValue(inChanges, IArgoRefField.EQ_IS_OPEN, inEquipType.getEqtypIsOpen(), moreChanges);
        this.setDenormalizedEqtypPropValue(inChanges, IArgoRefField.EQ_TARE_WEIGHT_KG, inEquipType.getEqtypTareWeightKg(), moreChanges);
        this.setDenormalizedEqtypPropValue(inChanges, IArgoRefField.EQ_SAFE_WEIGHT_KG, inEquipType.getEqtypSafeWeightKg(), moreChanges);
        return moreChanges;
    }

    protected final void setDenormalizedEqtypPropValue(FieldChanges inChanges, IMetafieldId inEqField, Object inValue, FieldChanges inOutMoreChanges) {
        if (inChanges.hasFieldChange(inEqField)) {
            return;
        }
        this.setSelfAndFieldChange(inEqField, inValue, inOutMoreChanges);
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
        DataSourceEnum curSrc = ContextHelper.getThreadDataSource();
//        if (IArgoBizMetafield.EQUIPMENT_OPERATOR.equals((Object)inFieldId)) {
//            IBizUnitManager bum = (IBizUnitManager)Roastery.getBean((String)"bizUnitManager");
//            ScopedBizUnit bzu = ScopedBizUnit.hydrate((Serializable)inFieldValue);
//            if (this.getEqGkey() == null) {
//                HibernateApi.getInstance().save((Object)this);
//            }
//            bum.upgradeEqOperator(this, bzu, ContextHelper.getThreadDataSource());
//        } else if (IArgoBizMetafield.EQUIPMENT_OWNER.equals((Object)inFieldId)) {
//            IBizUnitManager bum = (IBizUnitManager)Roastery.getBean((String)"bizUnitManager");
//            ScopedBizUnit bzu = ScopedBizUnit.hydrate((Serializable)inFieldValue);
//            if (this.getEqGkey() == null) {
//                HibernateApi.getInstance().save((Object)this);
//            }
//            bum.upgradeEqOwner(this, bzu, ContextHelper.getThreadDataSource());
//        } else if (EQUIP_LIST.contains(inFieldId)) {
//            this._propertyGroupEnum = ID_PROPERTY_GROUP_ENUM_MAP.get((Object)inFieldId);
//            if (this.shouldAcceptUpdate(curSrc, this.getEqDataSource(), this._propertyGroupEnum)) {
//                super.setFieldValue(inFieldId, inFieldValue);
//            }
//        } else {
//            super.setFieldValue(inFieldId, inFieldValue);
//            if (IArgoRefField.EQ_ID_FULL.equals((Object)inFieldId)) {
//                this.setEquipmentIdFields((String)inFieldValue);
//            }
//        }
    }

    @Override
    public void applyFieldChanges(FieldChanges var1) {

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

    @Nullable
    public ScopedBizUnit getEquipmentOwner() {
//        IBizUnitManager bum = (IBizUnitManager)Roastery.getBean((String)"bizUnitManager");
//        return bum.findEqOwner(this);
        return null;
    }

    public String getEquipmentOwnerId() {
//        return this.getEquipmentOwner().getBzuId();
        return "not finished";
    }

    @Nullable
    public ScopedBizUnit getEquipmentOperator() {
//        IBizUnitManager bum = (IBizUnitManager)Roastery.getBean((String)"bizUnitManager");
//        return bum.findEqOperator(this);
        return null;
    }

    public String getEquipmentOperatorId() {
//        return this.getEquipmentOperator().getBzuId();
        return "";
    }

    public static IPredicate formEquipmentOperatorPredicate(IMetafieldId inMetafieldId, PredicateVerbEnum inVerb, Object inValue) {
        ScopedBizUnit equipmentOperator = ScopedBizUnit.loadByPrimaryKey((Serializable)inValue);
        IBizUnitManager bum = (IBizUnitManager)Roastery.getBean((String)"bizUnitManager");
        if (inValue != null && equipmentOperator == null) {
            return null;
        }
        IDomainQuery domainQuery = null;
        IPredicate operatorPredicateIntf = null;
        if (PredicateVerbEnum.EQ.equals((Object)inVerb)) {
            domainQuery = bum.findEqFromEquipmentStateDomainQuery(inMetafieldId, equipmentOperator.getBzuGkey());
            operatorPredicateIntf = PredicateFactory.subQueryIn((IDomainQuery)domainQuery, (IMetafieldId)IArgoRefField.EQ_GKEY);
        } else if (PredicateVerbEnum.NE.equals((Object)inVerb)) {
            domainQuery = bum.findEqFromEquipmentStateDomainQuery(inMetafieldId, equipmentOperator.getBzuGkey());
            operatorPredicateIntf = PredicateFactory.not((IPredicate) PredicateFactory.subQueryIn((IDomainQuery)domainQuery, (IMetafieldId)IArgoRefField.EQ_GKEY));
        } else if (PredicateVerbEnum.NULL.equals((Object)inVerb)) {
            domainQuery = bum.findEqExistsEquipmentStateStateDq();
            operatorPredicateIntf = PredicateFactory.subQueryCountEq((IDomainQuery)domainQuery, (long)0L);
        } else if (PredicateVerbEnum.NOT_NULL.equals((Object)inVerb)) {
            domainQuery = bum.findEqExistsEquipmentStateStateDq();
            operatorPredicateIntf = PredicateFactory.subQueryExists((IDomainQuery)domainQuery);
        } else {
            operatorPredicateIntf = PredicateFactory.not((IPredicate)PredicateFactory.isNull((IMetafieldId)IArgoRefField.EQ_GKEY));
        }
        return operatorPredicateIntf;
    }
//
//    public static IPredicate formEquipmentOwnerPredicate(IMetafieldId inMetafieldId, PredicateVerbEnum inVerb, Object inValue) {
//        ScopedBizUnit equipmentOwner = ScopedBizUnit.loadByPrimaryKey((Serializable)inValue);
//        IBizUnitManager bum = (IBizUnitManager)Roastery.getBean((String)"bizUnitManager");
//        if (inValue != null && equipmentOwner == null) {
//            return null;
//        }
//        IDomainQuery domainQuery = null;
//        IPredicate ownerPredicateIntf = null;
//        if (PredicateVerbEnum.EQ.equals((Object)inVerb)) {
//            domainQuery = bum.findEqFromEquipmentStateDomainQuery(inMetafieldId, equipmentOwner.getBzuGkey());
//            ownerPredicateIntf = PredicateFactory.subQueryIn((IDomainQuery)domainQuery, (IMetafieldId)IArgoRefField.EQ_GKEY);
//        } else if (PredicateVerbEnum.NE.equals((Object)inVerb)) {
//            domainQuery = bum.findEqFromEquipmentStateDomainQuery(inMetafieldId, equipmentOwner.getBzuGkey());
//            ownerPredicateIntf = PredicateFactory.not((IPredicate)PredicateFactory.subQueryIn((IDomainQuery)domainQuery, (IMetafieldId)IArgoRefField.EQ_GKEY));
//        } else if (PredicateVerbEnum.NULL.equals((Object)inVerb)) {
//            domainQuery = bum.findEqExistsEquipmentStateStateDq();
//            ownerPredicateIntf = PredicateFactory.subQueryCountEq((IDomainQuery)domainQuery, (long)0L);
//        } else if (PredicateVerbEnum.NOT_NULL.equals((Object)inVerb)) {
//            domainQuery = bum.findEqExistsEquipmentStateStateDq();
//            ownerPredicateIntf = PredicateFactory.subQueryExists((IDomainQuery)domainQuery);
//        } else {
//            ownerPredicateIntf = PredicateFactory.not((IPredicate)PredicateFactory.isNull((IMetafieldId)IArgoRefField.EQ_GKEY));
//        }
//        return ownerPredicateIntf;
//    }
//
//    public AuditEvent vetAuditEvent(AuditEvent inAuditEvent) {
//        return inAuditEvent;
//    }

    public boolean shouldAcceptUpdate(DataSourceEnum inNewSource, DataSourceEnum inExistingSource, PropertyGroupEnum inGroupEnum) {
        if (this.getEqGkey() == null) {
            return true;
        }
        boolean shouldUpdate = false;
        this._propertyGroupEnum = inGroupEnum;
//        EntityMappingPredicate rule = Equipment.extractQualityRule(inGroupEnum);
//        if (rule != null) {
//            shouldUpdate = this.shouldAcceptUpdate(rule);
//            if (!shouldUpdate) {
//                MessageCollector mc = ContextHelper.getThreadMessageCollector();
//                mc.appendMessage(MessageLevelEnum.WARNING, ArgoPropertyKeys.DATAQUALITY_UPDATE_FAILED, null, new Object[]{this._propertyGroupEnum.getKey(), inNewSource});
//            }
//            return shouldUpdate;
//        }
        Integer q = QUALITY.get((Object)inExistingSource);
        int qExisting = q == null ? 0 : q;
        q = QUALITY.get((Object)inNewSource);
        int qNew = q == null ? 0 : q;
        return qNew >= qExisting;
    }

    public boolean shouldAcceptUpdate(PropertyGroupEnum inGroupEnum) {
        this._propertyGroupEnum = inGroupEnum;
//        Map dqRules = DataQuality.findDataQualityRules(ContextHelper.getThreadComplex(), ContextHelper.getThreadDataSource());
//        PostingActionEnum postingAction = PostingActionEnum.APPLY;
//        IMapper rule = (IMapper)dqRules.get((Object)inGroupEnum);
//        if (rule != null) {
//            FieldValue[] fieldValues;
//            if (rule instanceof EntityMappingPredicate) {
//                EntityMappingPredicate mappingPredicate = (EntityMappingPredicate)rule;
//                rule = (EntityMappingPredicate)HibernateApi.getInstance().get(EntityMappingPredicate.class, (Serializable)mappingPredicate.getEmappGkey());
//            }
//            if (rule != null && (fieldValues = rule.mapEntity((ValueSource)this)) != null && fieldValues.length == 1) {
//                postingAction = (PostingActionEnum)((Object)fieldValues[0].getValue());
//            }
//        }
//        return !PostingActionEnum.IGNORE.equals((Object)postingAction);
        return false;
    }

//    public boolean shouldAcceptUpdate(EntityMappingPredicate inRule) {
//        FieldValue[] fieldValues;
//        PostingActionEnum postingAction = PostingActionEnum.APPLY;
//        if (inRule != null && (fieldValues = inRule.mapEntity((ValueSource)this)) != null && fieldValues.length == 1) {
//            postingAction = (PostingActionEnum)((Object)fieldValues[0].getValue());
//        }
//        return !PostingActionEnum.IGNORE.equals((Object)postingAction);
//    }
//
//    private static EntityMappingPredicate extractQualityRule(PropertyGroupEnum inGroupEnum) {
//        EntityMappingPredicate mappingRule;
//        Map dqRules = DataQuality.findDataQualityRules(ContextHelper.getThreadComplex(), ContextHelper.getThreadDataSource());
//        IMapper rule = (IMapper)dqRules.get((Object)inGroupEnum);
//        EntityMappingPredicate entityMappingPredicate = mappingRule = rule != null && rule instanceof EntityMappingPredicate ? (EntityMappingPredicate)rule : null;
//        if (mappingRule != null) {
//            mappingRule = (EntityMappingPredicate)HibernateApi.getInstance().load(EntityMappingPredicate.class, (Serializable)mappingRule.getEmappGkey());
//        }
//        return mappingRule;
//    }

    public void preProcessUpdate(FieldChanges inChanges, FieldChanges inOutMoreChanges) {
//        super.preProcessUpdate(inChanges, inOutMoreChanges);
//        if (inChanges.hasFieldChange(IArgoRefField.EQ_EQUIP_TYPE) || inChanges.hasFieldChange(IArgoRefField.EQ_TANK_RAILS)) {
//            if (inChanges.hasFieldChange(IArgoRefField.EQ_EQUIP_TYPE)) {
//                FieldChanges moreChanges = this.updateEquipTypeProperties(inChanges, this.getEqEquipType());
//                Iterator itr = moreChanges.getIterator();
//                while (itr.hasNext()) {
//                    inOutMoreChanges.setFieldChange((FieldChange)itr.next());
//                }
//            }
//            IEquipStateManager equipStateManager = (IEquipStateManager)Roastery.getBean((String)"equipStateManager");
//            equipStateManager.recordUnitEvent(this, inChanges);
//        }
    }

    @Override
    public void preProcessDelete(FieldChanges var1) {

    }

    public double getBestTareWeightKq() {
        double wtTare;
        double d = wtTare = this.getEqTareWeightKg() != null ? this.getEqTareWeightKg() : 0.0;
        if (wtTare == 0.0) {
            Double eqType = this.getEqEquipType().getEqtypTareWeightKg();
            wtTare = eqType != null ? eqType : 0.0;
        }
        return wtTare;
    }

    public void updateDataSource(DataSourceEnum inDataSourceEnum) {
        this.setEqDataSource(inDataSourceEnum);
    }

    public void updateEqTareWtKg(Double inTareWtKg) {
        this.setEqTareWeightKg(inTareWtKg);
    }

    public void updateEqSafeWtKg(Double inSafeWtKg) {
        this.setEqSafeWeightKg(inSafeWtKg);
    }

    public void updateEqLocPdsCoordinates(Point3D inLocPdsCoordinates) {
        this.setEqLocPdsCoordinates(inLocPdsCoordinates);
    }

    public void updateEqIsOpen(Boolean inEqIsOpen) {
        this.setEqIsOpen(inEqIsOpen);
    }

    public BizViolation validateDeletion() {
        BizViolation bv = super.validateDeletion();
        IEquipStateManager esm = (IEquipStateManager) Roastery.getBean((String)"equipStateManager");
        try {
            esm.purgeEqState(this);
        }
        catch (BizViolation inBizViolation) {
            bv = inBizViolation.appendToChain(bv);
        }
        return bv;

    }

    @Override
    public void populate(Set var1, FieldChanges var2) {

    }

    @Override
    public void preProcessInsert(FieldChanges var1) {

    }

    public BizViolation validateChanges(FieldChanges inChanges) {
        Equipment eq;
        BizViolation bv = super.validateChanges(inChanges);
//        if (inChanges.hasFieldChange(IArgoRefField.EQ_ID_FULL) && !DataSourceEnum.SNX.equals((Object)ContextHelper.getThreadDataSource()) && (eq = Equipment.findEquipment(this.getEqIdFull())) != null && !ObjectUtils.equals((Object)this.getPrimaryKey(), (Object)eq.getPrimaryKey())) {
//            bv = BizViolation.createFieldViolation((PropertyKey)FrameworkPropertyKeys.CRUD__DUPLICATE_NATURAL_KEY, (BizViolation)bv, null, (Object)IArgoRefField.EQ_ID_NO_CHECK_DIGIT, (Object)this.getEqIdFull());
//        }
        return bv;
    }

    public Object getFieldValue(IMetafieldId inMetafieldId) {
//        if (IArgoBizMetafield.DQ_EXISTING_DATA_SOURCE.equals((Object)inMetafieldId)) {
//            return super.getFieldValue(IArgoRefField.EQ_DATA_SOURCE);
//        }
//        return super.getFieldValue(inMetafieldId);
        return null;
    }

    public boolean isTareWeightUpdateAllowed() {
        return this.isUpdateAllowed(IArgoRefField.EQ_TARE_WEIGHT_KG);
    }

    public boolean isBuildDateUpdateAllowed() {
        return this.isUpdateAllowed(IArgoRefField.EQ_BUILD_DATE);
    }

    public boolean isCscExpirationUpdateAllowed() {
        return this.isUpdateAllowed(IArgoRefField.EQ_CSC_EXPIRATION);
    }

    public boolean isEqTypeUpdateAllowed() {
        return this.isUpdateAllowed(IArgoRefField.EQ_EQUIP_TYPE);
    }

    public boolean isStrengthCodeUpdateAllowed() {
        return this.isUpdateAllowed(IArgoRefField.EQ_STRENGTH_CODE);
    }

    public boolean isHeightMmUpdateAllowed() {
        return this.isUpdateAllowed(IArgoRefField.EQ_HEIGHT_MM);
    }

    public boolean isUpdateAllowed(IMetafieldId inFieldId) {
        if (EQUIP_LIST.contains(inFieldId)) {
            this._propertyGroupEnum = ID_PROPERTY_GROUP_ENUM_MAP.get((Object)inFieldId);
        }
 //       return this.shouldAcceptUpdate(ContextHelper.getThreadDataSource(), this.getEqDataSource(), this._propertyGroupEnum);
        return false;
    }

    public static boolean isAttachedToChe(@NotNull String inEquipmentId) throws BizViolation {
        Equipment equipment = Equipment.findEquipment(inEquipmentId);
        if (inEquipmentId == null || equipment == null) {
            throw BizViolation.create((IPropertyKey)IArgoPropertyKeys.UNKNOWN_EQUIPMENT_ID, null, (Object)inEquipmentId);
        }
//        IDomainQuery dq = QueryUtils.createDomainQuery((String)"Che").addDqPredicate(PredicateFactory.eq((IMetafieldId)ArgoField.CHE_ATTACHED_CHASSIS_ID, (Object)inEquipmentId)).addDqPredicate(PredicateFactory.eq((IMetafieldId)ArgoField.XPE_CHE_LIFE_CYCLE_STATE, (Object)LifeCycleStateEnum.ACTIVE));
//        IEntity cheEntity = HibernateApi.getInstance().getUniqueEntityByDomainQuery(dq);
//        return cheEntity != null;
        return false;
    }

    static {
        Integer q0 = 0;
        Integer q1 = 1;
        Integer q2 = 2;
        Integer q3 = 3;
        Integer q4 = 4;
        Integer q5 = 5;
        Integer q7 = 7;
        Integer q8 = 8;
        QUALITY.put(DataSourceEnum.AUTO_GEN, q0);
        QUALITY.put(DataSourceEnum.TESTING, q0);
        QUALITY.put(DataSourceEnum.UNKNOWN, q0);
        QUALITY.put(DataSourceEnum.REFCON, q1);
//        QUALITY.put(DataSourceEnum.SPARCS_IMPORT, q1);
        QUALITY.put(DataSourceEnum.BUILT_IN, q1);
        QUALITY.put(DataSourceEnum.EDI_BKG, q2);
        QUALITY.put(DataSourceEnum.EDI_CNST, q2);
        QUALITY.put(DataSourceEnum.EDI_HAZRD, q2);
        QUALITY.put(DataSourceEnum.EDI_MNFST, q2);
        QUALITY.put(DataSourceEnum.EDI_RELS, q2);
        QUALITY.put(DataSourceEnum.EDI_TRNPT, q2);
        QUALITY.put(DataSourceEnum.EDI_STOW, q3);
        QUALITY.put(DataSourceEnum.EDI_DCLT, q3);
        QUALITY.put(DataSourceEnum.EDI_LDLT, q3);
        QUALITY.put(DataSourceEnum.USER_WEB, q4);
        QUALITY.put(DataSourceEnum.EQ_SERIAL_RANGE, q5);
        QUALITY.put(DataSourceEnum.SNX, q5);
        QUALITY.put(DataSourceEnum.EDI_SNX, q5);
        QUALITY.put(DataSourceEnum.EDI_INVT, q7);
        QUALITY.put(DataSourceEnum.USER_LCL, q8);
        QUALITY.put(DataSourceEnum.USER_DBA, q8);
        QUALITY.put(DataSourceEnum.UI_DISCHARGELIST, q8);
        QUALITY.put(DataSourceEnum.UI_LOADLIST, q8);
        QUALITY.put(DataSourceEnum.IN_GATE, q8);
        ID_PROPERTY_GROUP_ENUM_MAP = new HashMap<IMetafieldId, PropertyGroupEnum>();
        EQUIP_LIST = new MetafieldIdList();
        EQUIP_LIST.add(IArgoRefField.EQ_TARE_WEIGHT_KG);
        EQUIP_LIST.add(IArgoRefField.EQ_BUILD_DATE);
        EQUIP_LIST.add(IArgoRefField.EQ_CSC_EXPIRATION);
        EQUIP_LIST.add(IArgoRefField.EQ_EQUIP_TYPE);
        EQUIP_LIST.add(IArgoRefField.EQ_STRENGTH_CODE);
        EQUIP_LIST.add(IArgoRefField.EQ_HEIGHT_MM);
        ID_PROPERTY_GROUP_ENUM_MAP.put(IArgoRefField.EQ_TARE_WEIGHT_KG, PropertyGroupEnum.TARE_WEIGHT_KG);
        ID_PROPERTY_GROUP_ENUM_MAP.put(IArgoRefField.EQ_BUILD_DATE, PropertyGroupEnum.BUILD_DATE);
        ID_PROPERTY_GROUP_ENUM_MAP.put(IArgoRefField.EQ_CSC_EXPIRATION, PropertyGroupEnum.CSC_EXPIRATION);
        ID_PROPERTY_GROUP_ENUM_MAP.put(IArgoRefField.EQ_EQUIP_TYPE, PropertyGroupEnum.EQUIP_TYPE);
        ID_PROPERTY_GROUP_ENUM_MAP.put(IArgoRefField.EQ_STRENGTH_CODE, PropertyGroupEnum.STRENGTH_CODE);
        ID_PROPERTY_GROUP_ENUM_MAP.put(IArgoRefField.EQ_HEIGHT_MM, PropertyGroupEnum.HEIGHT_MM);
        //LOGGER = Logger.getLogger(Equipment.class);
    }
}
