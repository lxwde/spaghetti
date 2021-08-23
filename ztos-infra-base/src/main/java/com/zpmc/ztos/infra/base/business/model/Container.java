package com.zpmc.ztos.infra.base.business.model;

import com.zpmc.ztos.infra.base.business.dataobject.ContainerDO;
import com.zpmc.ztos.infra.base.business.enums.argo.*;
import com.zpmc.ztos.infra.base.business.enums.framework.MessageLevelEnum;
import com.zpmc.ztos.infra.base.business.equipments.EquipPrefix;
import com.zpmc.ztos.infra.base.business.equipments.EquipSerialRange;
import com.zpmc.ztos.infra.base.business.equipments.EquipType;
import com.zpmc.ztos.infra.base.business.equipments.Equipment;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.business.predicate.PredicateFactory;
import com.zpmc.ztos.infra.base.common.configs.ArgoConfig;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.common.exceptions.BizFailure;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.helps.ContextHelper;
import com.zpmc.ztos.infra.base.common.math.CheckDigitAlgorithm;
import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;
import com.zpmc.ztos.infra.base.common.model.FieldChanges;
import com.zpmc.ztos.infra.base.common.model.Roastery;
import com.zpmc.ztos.infra.base.common.utils.ArgoEdiUtils;
import com.zpmc.ztos.infra.base.common.utils.MessageCollectorUtils;
import com.zpmc.ztos.infra.base.utils.QueryUtils;
import com.zpmc.ztos.infra.base.utils.StringUtils;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.List;

public class Container extends ContainerDO {
    private static final Logger LOGGER = Logger.getLogger(Container.class);

    public Container() {
        this.setEqClass(EquipClassEnum.CONTAINER);
        this.setEqRfrType(EquipRfrTypeEnum.NON_RFR);
        this.setEqIsStarvent(Boolean.FALSE);
        this.setEqIsSuperFreezeReefer(Boolean.FALSE);
        this.setEqIsControlledAtmosphereReefer(Boolean.FALSE);
        this.setEqIsVented(Boolean.FALSE);
        this.setEqDataSource(DataSourceEnum.UNKNOWN);
        this.setEqTankRails(TankRailTypeEnum.UNKNOWN);
    }

    public static Container findOrCreateContainer(String inCtrId, String inEquipTypeId, DataSourceEnum inDataSource) throws BizViolation {
        if (inDataSource == null) {
            throw BizFailure.create((String)"inDataSource is null");
        }
        Container ctr = Container.findContainer(inCtrId);
        if (ctr != null) {
            ctr.upgradeEqType(inEquipTypeId, inDataSource);
        } else {
            ctr = Container.internalCreateContainer(inCtrId, inEquipTypeId, inDataSource, true);
        }
        return ctr;
    }

    public static Container findContainer(String inCtrId) {
        Container ctr = Container.findContainerWithoutValidation(inCtrId);
        if (ctr != null && !ctr.getEqIdFull().equals(inCtrId)) {
            DataSourceEnum dataSource = ContextHelper.getThreadDataSource();
            if (DataSourceEnum.IN_GATE.equals((Object)dataSource) && !ArgoConfig.CHECK_DIGIT_CORRECTION.isOn(ContextHelper.getThreadUserContext())) {
                IMessageCollector mc = MessageCollectorUtils.getMessageCollector();
                if (!mc.containsMessage(IArgoPropertyKeys.EQID_CHECK_DIGIT_MISMATCH_REJECTED)) {
                    MessageCollectorUtils.appendMessage((MessageLevelEnum)MessageLevelEnum.SEVERE, (IPropertyKey) IArgoPropertyKeys.EQID_CHECK_DIGIT_MISMATCH_REJECTED, (Object)inCtrId, (Object)ctr.getEqIdFull());
                }
            } else {
                EquipPrefix eqPrefix = EquipPrefix.findEquipPrefixForId(inCtrId);
                if (eqPrefix == null && !ArgoConfig.CHECK_DIGIT_CORRECTION.isOn(ContextHelper.getThreadUserContext())) {
                    if (DataSourceEnum.SNX.equals((Object)dataSource)) {
                        MessageCollectorUtils.appendMessage((MessageLevelEnum)MessageLevelEnum.WARNING, (IPropertyKey) IArgoPropertyKeys.EQID_CHECK_DIGIT_DIFFERENT, (Object)inCtrId, (Object)ctr.getEqIdFull());
                    } else {
                        MessageCollectorUtils.appendMessage((MessageLevelEnum)MessageLevelEnum.SEVERE, (IPropertyKey) IArgoPropertyKeys.EQID_CHECK_DIGIT_DIFFERENT, (Object)inCtrId, (Object)ctr.getEqIdFull());
                    }
                } else {
                    MessageCollectorUtils.appendMessage((MessageLevelEnum)MessageLevelEnum.WARNING, (IPropertyKey) IArgoPropertyKeys.EQID_CHECK_DIGIT_IGNORED, (Object)inCtrId, (Object)ctr.getEqIdFull());
                }
            }
        }
        return ctr;
    }

    public static Container findOrCreateContainerWithoutValidation(String inCtrId, String inEquipTypeId, DataSourceEnum inDataSource) throws BizViolation {
        if (inDataSource == null) {
            throw BizFailure.create((String)"inDataSource is null");
        }
        Container ctr = Container.findContainerWithoutValidation(inCtrId);
        if (ctr != null) {
            ctr.upgradeEqType(inEquipTypeId, inDataSource);
        } else {
            ctr = Container.internalCreateContainer(inCtrId, inEquipTypeId, inDataSource, true);
        }
        return ctr;
    }

    public static Container findContainerWithoutValidation(String inCtrId) {
        String lookupId = Container.getEqLookupKey(inCtrId);
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"Container").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoRefField.EQ_ID_NO_CHECK_DIGIT, (Object)lookupId));
        Container ctr = (Container) HibernateApi.getInstance().getUniqueEntityByDomainQuery(dq);
        if (ctr == null) {
            CheckDigitAlgorithmEnum checkDigitAlgorithm = EquipPrefix.findEquipPrefixCheckDigitAlgorithm(lookupId);
            dq = CheckDigitAlgorithmEnum.PARTOFID.equals((Object)checkDigitAlgorithm) ? QueryUtils.createDomainQuery((String)"Container").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoRefField.EQ_ID_FULL, (Object)inCtrId)) : QueryUtils.createDomainQuery((String)"Container").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoRefField.EQ_ID_NO_CHECK_DIGIT, (Object)inCtrId));
            ctr = (Container)HibernateApi.getInstance().getUniqueEntityByDomainQuery(dq);
        }
        return ctr;
    }

    public static Container findContainerByFullId(String inCtrId) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"Container").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoRefField.EQ_ID_FULL, (Object)inCtrId));
        return (Container)HibernateApi.getInstance().getUniqueEntityByDomainQuery(dq);
    }

    static Container internalCreateContainer(String inCtrId, String inEquipTypeId, DataSourceEnum inDataSource, boolean inUseSerialRangeData) throws BizViolation {
        EquipType equipType;
        String rangeId;
        EquipSerialRange esr = null;
        if (inUseSerialRangeData) {
            esr = EquipSerialRange.findEquipSerialRangeForCtrId(inCtrId);
        }
        if (inEquipTypeId == null) {
            if (esr != null) {
                inEquipTypeId = esr.getEqsrlrngEqtypeId();
            }
            if (inEquipTypeId == null) {
                EquipType unknownType = EquipType.getUnknownEquipType();
                inEquipTypeId = unknownType.getEqtypId();
            }
        } else if (esr != null && (rangeId = esr.getEqsrlrngEqtypeId()) != null && !StringUtils.equals((String)inEquipTypeId, (String)rangeId)) {
            LOGGER.warn((Object)("internalCreateContainer: provided ISO <" + inEquipTypeId + "> is different from Range Data <" + rangeId + "> for Ctr " + inCtrId));
            Equipment equipment = Equipment.findEquipment(inCtrId);
            if (equipment != null && equipment.shouldAcceptUpdate(inDataSource, equipment.getEqDataSource(), PropertyGroupEnum.SERIAL_RANGE)) {
                inEquipTypeId = rangeId;
            }
        }
        if ((equipType = EquipType.findEquipType(inEquipTypeId)) == null) {
            equipType = EquipType.createEquipType(inEquipTypeId, EquipClassEnum.CONTAINER);
        } else if (equipType.getEqtypClass() != EquipClassEnum.CONTAINER) {
            throw BizViolation.create((IPropertyKey) IArgoPropertyKeys.INVALID_EQTYPE, null, (Object)((Object)equipType.getEqtypClass()), (Object)((Object)EquipClassEnum.CONTAINER));
        }
        Container ctr = new Container();
        ctr.setEquipmentIdFields(inCtrId);
        ctr.setEqEquipType(equipType);
        ctr.updateEquipTypeProperties(equipType);
        if (esr != null) {
            ctr.updateEquipTypePropertiesWithRangeData(esr);
        }
        ctr.setEqDataSource(inDataSource);
        HibernateApi.getInstance().save((Object)ctr);
        return ctr;
    }

    public static String calcCheckDigit(String inCtrId) {
        String checkDigit = null;
        if (inCtrId != null) {
            String prefixString = inCtrId.substring(0, 4);
            EquipPrefix eqPrefix = EquipPrefix.findEquipPrefix(prefixString);
            checkDigit = eqPrefix == null ? CheckDigitAlgorithm.calc(CheckDigitAlgorithmEnum.STANDARD, inCtrId) : (eqPrefix.getEqpfxCheckDigitAlgm() == null ? CheckDigitAlgorithm.calc(CheckDigitAlgorithmEnum.STANDARD, inCtrId) : CheckDigitAlgorithm.calc(eqPrefix.getEqpfxCheckDigitAlgm(), inCtrId));
        }
        return checkDigit;
    }

    public static boolean isCheckDigitCorrect(String inCtrId) {
        String calcedCheckDigit;
        String providedCheckDigit;
        boolean correct = false;
        if (inCtrId != null && inCtrId.length() > 10 && (providedCheckDigit = inCtrId.substring(10)).equals(calcedCheckDigit = Container.calcCheckDigit(inCtrId))) {
            correct = true;
        }
        return correct;
    }

    public static String findFullIdOrPadCheckDigit(String inCtrId) {
        Container ctr = Container.findContainerWithoutValidation(inCtrId);
        String ctrIdFull = ctr == null ? Container.padCheckDigit(inCtrId) : ctr.getEqIdFull();
        return ctrIdFull;
    }

    public static String padCheckDigit(String inCtrId) {
        String checkDigit;
        String padedId = inCtrId;
        if (inCtrId != null && inCtrId.length() == 10 && (checkDigit = Container.calcCheckDigit(inCtrId)) != null) {
            padedId = inCtrId + checkDigit;
        }
        return padedId;
    }

    @Override
    protected FieldChanges updateEquipTypeProperties(FieldChanges inChanges, EquipType inEquipType) {
        FieldChanges moreChanges = super.updateEquipTypeProperties(inChanges, inEquipType);
        this.setDenormalizedEqtypPropValue(inChanges, IArgoRefField.EQ_RFR_TYPE, (Object)inEquipType.getEqtypRfrType(), moreChanges);
        this.setDenormalizedEqtypPropValue(inChanges, IArgoRefField.EQ_IS_SUPER_FREEZE_REEFER, inEquipType.getEqtypIsSuperFreezeReefer(), moreChanges);
        this.setDenormalizedEqtypPropValue(inChanges, IArgoRefField.EQ_IS_CONTROLLED_ATMOSPHERE_REEFER, inEquipType.getEqtypIsControlledAtmosphereReefer(), moreChanges);
        return moreChanges;
    }

    private void updateEquipTypePropertiesWithRangeData(EquipSerialRange inEsr) {
        if (inEsr.getEqsrlrngRfrTypeApply()) {
            this.setEqRfrType(inEsr.getEqsrlrngRfrType());
        }
        if (inEsr.getEqsrlrngHeightMmApply()) {
            this.setEqHeightMm(inEsr.getEqsrlrngHeightMm());
        }
        if (inEsr.getEqsrlrngWidthMmApply()) {
            this.setEqWidthMm(inEsr.getEqsrlrngWidthMm());
        }
        if (inEsr.getEqsrlrngLengthMmApply()) {
            this.setEqLengthMm(inEsr.getEqsrlrngLengthMm());
        }
        if (inEsr.getEqsrlrngTareWeightKgApply()) {
            this.setEqTareWeightKg(inEsr.getEqsrlrngTareWeightKg());
        }
        if (inEsr.getEqsrlrngSafeWeightKgApply()) {
            this.setEqSafeWeightKg(inEsr.getEqsrlrngSafeWeightKg());
        }
        if (inEsr.getEqsrlrngIsoGroupApply()) {
            this.setEqIsoGroup(inEsr.getEqsrlrngIsoGroup());
        }
        if (inEsr.getEqsrlrngIsSuperFreezeReeferApply()) {
            this.setEqIsSuperFreezeReefer(inEsr.getEqsrlrngIsSuperFreezeReefer());
        }
        if (inEsr.getEqsrlrngIsControlledAtmosphereReeferApply()) {
            this.setEqIsControlledAtmosphereReefer(inEsr.getEqsrlrngIsControlledAtmosphereReefer());
        }
        if (inEsr.getEqsrlrngNoStowOnTopIfEmptyApply()) {
            this.setEqNoStowOnTopIfEmpty(inEsr.getEqsrlrngNoStowOnTopIfEmpty());
        }
        if (inEsr.getEqsrlrngNoStowOnTopIfLadenApply()) {
            this.setEqNoStowOnTopIfLaden(inEsr.getEqsrlrngNoStowOnTopIfLaden());
        }
        if (inEsr.getEqsrlrngMustStowBelowDeckApply()) {
            this.setEqMustStowBelowDeck(inEsr.getEqsrlrngMustStowBelowDeck());
        }
        if (inEsr.getEqsrlrngMustStowAboveDeckApply()) {
            this.setEqMustStowAboveDeck(inEsr.getEqsrlrngMustStowAboveDeck());
        }
        if (inEsr.getEqsrlrngUsesAccessoriesApply()) {
            this.setEqUsesAccessories(inEsr.getEqsrlrngUsesAccessories());
        }
        if (inEsr.getEqsrlrngIsTemperatureControlledApply()) {
            this.setEqIsTemperatureControlled(inEsr.getEqsrlrngIsTemperatureControlled());
        }
        if (inEsr.getEqsrlrngOogOkApply()) {
            this.setEqOogOk(inEsr.getEqsrlrngOogOk());
        }
        if (inEsr.getEqsrlrngIsUnsealableApply()) {
            this.setEqIsUnsealable(inEsr.getEqsrlrngIsUnsealable());
        }
        if (inEsr.getEqsrlrngHasWheelsApply()) {
            this.setEqHasWheels(inEsr.getEqsrlrngHasWheels());
        }
        if (inEsr.getEqsrlrngIsOpenApply()) {
            this.setEqIsOpen(inEsr.getEqsrlrngIsOpen());
        }
    }

    public String toString() {
        String iso = this.getEqEquipType() != null ? this.getEqEquipType().getEqtypId() : "????";
        return "Ctr[" + this.getEqIdFull() + ':' + iso + ']';
    }

    public static List getContainersByCtrNbrOnly(String inCtrId) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"Container").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoRefField.EQ_ID_NBR_ONLY, (Object)inCtrId));
        return HibernateApi.getInstance().findEntitiesByDomainQuery(dq);
    }

    public void setReeferTypeFromEdiCode(String inRfrType) {
        EquipRfrTypeEnum rfrType = ArgoEdiUtils.ediRfrType2RfrTypeEnum(inRfrType);
        this.setEqRfrType(rfrType);
    }

    public void updateEqTankRails(TankRailTypeEnum inEqTankRails) {
        this.setEqTankRails(inEqTankRails);
    }

    public static Container resolveCtrFromEq(Equipment inEq) {
        Container ctr = null;
        if (inEq != null && EquipClassEnum.CONTAINER.equals((Object)inEq.getEqClass())) {
            ctr = (Container)HibernateApi.getInstance().downcast((DatabaseEntity)inEq, Container.class);
        }
        return ctr;
    }

    public static Container findOrCreateContainer(String inCtrId, DataSourceEnum inDataSource) throws BizViolation {
        if (inDataSource == null) {
            throw BizFailure.create((String)"inDataSource is null");
        }
        Container ctr = Container.findContainer(inCtrId);
        if (ctr == null) {
            ctr = Container.internalCreateContainer(inCtrId, null, inDataSource, true);
        }
        return ctr;
    }

    @Override
    public BizViolation validateChanges(FieldChanges inChanges) {
        BizViolation bv = super.validateChanges(inChanges);
        if ((inChanges.hasFieldChange(IArgoRefField.EQ_SAFE_WEIGHT_KG) || inChanges.hasFieldChange(IArgoRefField.EQ_TARE_WEIGHT_KG)) && ContextHelper.isUpdateFromHumanUser()) {
            Double eqTare = this.getEqTareWeightKg();
            Double eqSafe = this.getEqSafeWeightKg();
            if (eqTare != 0.0 && eqSafe != 0.0 && eqTare > eqSafe) {
                bv = BizViolation.create((IPropertyKey) IArgoPropertyKeys.ERROR_CONTAINER_SAFEWT_CANT_BE_LESSER_THAN_TAREWT, (BizViolation)bv, (Object)eqSafe, (Object)eqTare);
            }
        }
        IArgoFieldUpdateProtection argoFieldUpdateProtection = (IArgoFieldUpdateProtection) Roastery.getBean((String)"fieldUpdateProtection");
        try {
            argoFieldUpdateProtection.checkForProtectedFieldUpdate(inChanges, this);
        }
        catch (BizViolation inBizViolation) {
            bv = inBizViolation.appendToChain(bv);
        }
        return bv;
    }

    public void preProcessInsert(FieldChanges inOutMoreChanges) {
        super.preProcessInsert(inOutMoreChanges);
        EquipNominalLengthEnum length = this.getEqEquipType().getEqtypNominalLength();
        if (this.getEqIdFull().startsWith("MATU8")) {
            this.setSelfAndFieldChange(IArgoRefField.EQ_STRENGTH_CODE, "M", inOutMoreChanges);
        } else if (EquipNominalLengthEnum.NOM20.equals((Object)length)) {
            this.setSelfAndFieldChange(IArgoRefField.EQ_STRENGTH_CODE, "TB", inOutMoreChanges);
        } else if (EquipNominalLengthEnum.NOM24.equals((Object)length)) {
            this.setSelfAndFieldChange(IArgoRefField.EQ_STRENGTH_CODE, "SB", inOutMoreChanges);
        } else if (EquipNominalLengthEnum.NOM40.equals((Object)length)) {
            this.setSelfAndFieldChange(IArgoRefField.EQ_STRENGTH_CODE, "FB", inOutMoreChanges);
        } else if (EquipNominalLengthEnum.NOM45.equals((Object)length)) {
            this.setSelfAndFieldChange(IArgoRefField.EQ_STRENGTH_CODE, "V", inOutMoreChanges);
        }
    }

    public static Container hydrate(Serializable inPrimaryKey) {
        return (Container)HibernateApi.getInstance().load(Container.class, inPrimaryKey);
    }
}
