package com.zpmc.ztos.infra.base.common.utils;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.business.enums.argo.*;
import com.zpmc.ztos.infra.base.business.enums.inventory.LiftModeEnum;
import com.zpmc.ztos.infra.base.business.equipments.Chassis;
import com.zpmc.ztos.infra.base.business.equipments.Che;
import com.zpmc.ztos.infra.base.business.equipments.EquipType;
import com.zpmc.ztos.infra.base.business.equipments.Equipment;
import com.zpmc.ztos.infra.base.business.interfaces.IEqLinkContext;
import com.zpmc.ztos.infra.base.business.inventory.MoveEvent;
import com.zpmc.ztos.infra.base.business.inventory.Unit;
import com.zpmc.ztos.infra.base.business.inventory.UnitFacilityVisit;
import com.zpmc.ztos.infra.base.business.inventory.UnitNode;
import com.zpmc.ztos.infra.base.business.model.CarrierVisit;
import com.zpmc.ztos.infra.base.business.model.LocPosition;
import com.zpmc.ztos.infra.base.common.configs.ArgoConfig;
import com.zpmc.ztos.infra.base.common.configs.InventoryConfig;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.helps.ContextHelper;
import com.zpmc.ztos.infra.base.common.model.UserContext;
import com.zpmc.ztos.infra.base.common.scopes.Facility;
import com.zpmc.ztos.infra.base.common.scopes.Yard;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.util.HashMap;
import java.util.Map;

public class InventoryControlUtils {
    private static final double PERCENT = 100.0;
    private static final Logger LOGGER = LogManager.getLogger(InventoryControlUtils.class);

    private InventoryControlUtils() {
    }

    @Nullable
    public static Double getWeightLimitForCheKind(@NotNull CheKindEnum inCheKindEnum, @Nullable CheUnitHandlingTypeEnum inHandlingTypeEnum, LiftModeEnum inLiftMode, @Nullable EquipNominalLengthEnum inContainerLength) {
        int convertedLength = InventoryCargoUtils.convertFromEquipmentNominalLength(inContainerLength);
        LOGGER.debug((Object)("Looking for setting with cheKind: " + (Object)inCheKindEnum + " handling: " + (Object)inHandlingTypeEnum + " liftMode: " + (Object)((Object)inLiftMode) + " length: " + convertedLength));
        String weightLimitsSettingXmlStr = "";
        String xmlStringDoc = ArgoConfig.CHE_WEIGHT_LIMITS.getSetting(ContextHelper.getThreadUserContext());
        LOGGER.debug((Object)("N4 Setting for weight limits is: " + xmlStringDoc));
        if (xmlStringDoc == null || xmlStringDoc.isEmpty()) {
            LOGGER.warn((Object)"N4 Settings for weight limits not specified. Could not determine weight limit for CHE");
            return null;
        }
//        Document settings = XmlUtil.parse((String)xmlStringDoc);
//        if (settings == null) {
//            LOGGER.warn((Object)"Could not parse XML settings. Cannot get weight limit.");
//            return null;
//        }
//        Element root = settings.getRootElement();
//        Attribute capacityKg = XmlUtil.evalXpathAttr((Element)root, (String)String.format("//cheWeightLimitEntry[@cheKind='%s']/handling[@name='%s']/mode[@name='%s'][@lengthFt='%s']/@capacityKg", inCheKindEnum.getName(), inHandlingTypeEnum.name(), inLiftMode.getName(), convertedLength));
//        if (capacityKg != null) {
//            String weightValue = capacityKg.getValue();
//            LOGGER.debug((Object)("Found WeightLimit Entry: " + inCheKindEnum.getName() + " " + inHandlingTypeEnum.name() + " " + inLiftMode.getName() + " " + convertedLength + " " + weightValue));
//            return Double.parseDouble(weightValue);
//        }
//        LOGGER.warn((Object)"Could not find N4 Settings for weight limits with specified parameters");
        return null;
    }

    public static Double findMarginWeight(Double inContainerWeightKg) {
        double marginWeight;
        UserContext userContext = ContextHelper.getThreadUserContext();
        assert (userContext != null);
        Long allowedMargin = InventoryConfig.WEIGHT_MARGIN_FOR_UNIT_SWAPPING_TOLERANCE.getValue(userContext);
        if (inContainerWeightKg != null && inContainerWeightKg > 0.0 && (marginWeight = inContainerWeightKg * ((double)InventoryConfig.WEIGHT_MARGIN_PERCENTAGE_FOR_UNIT_SWAPPING_TOLERANCE.getValue(userContext) / 100.0)) > (double)allowedMargin.longValue()) {
            return marginWeight;
        }
        return allowedMargin.doubleValue();
    }

    public static boolean isWeightWithinLimitForHandling(@NotNull CheKindEnum inCheKindEnum, @NotNull CheUnitHandlingTypeEnum inHandlingTypeEnum, @NotNull UnitFacilityVisit inUfv, @Nullable UnitFacilityVisit inTwinUfv) {
        EquipNominalLengthEnum nominalLengthEnum = inUfv.getUfvUnit().getPrimaryEq().getEqEquipType().getEqtypNominalLength();
        Double unitWeight = inUfv.getUfvUnit().getUnitGoodsAndCtrWtKg();
        return InventoryControlUtils.isWeightWithinLimitForHandling(inCheKindEnum, inHandlingTypeEnum, unitWeight, nominalLengthEnum, inTwinUfv);
    }

    public static boolean isWeightWithinLimitForHandling(@NotNull CheKindEnum inCheKindEnum, @NotNull CheUnitHandlingTypeEnum inHandlingTypeEnum, @NotNull Double inWeightKg, @NotNull EquipNominalLengthEnum inNominalLengthEnum, @Nullable UnitFacilityVisit inTwinUfv) {
        if (inHandlingTypeEnum == null) {
            inHandlingTypeEnum = CheUnitHandlingTypeEnum.CARRY;
        }
        LOGGER.debug((Object)("Unit weight: " + inWeightKg));
        Double cheMaxWeightKg = InventoryControlUtils.getWeightLimitForCheKind(inCheKindEnum, inHandlingTypeEnum, LiftModeEnum.SINGLE, inNominalLengthEnum);
        if (cheMaxWeightKg == null) {
            LOGGER.warn((Object)"Could not validate weight because a relevant weight limit setting could not be found");
            return true;
        }
        if (cheMaxWeightKg != null && inWeightKg > cheMaxWeightKg) {
            LOGGER.warn((Object)("Individual unit weight " + inWeightKg + " is beyond CHE capacity " + cheMaxWeightKg + "."));
            return false;
        }
        if (inTwinUfv != null) {
            Double twinWeightKg = inTwinUfv.getUfvUnit().getUnitGoodsAndCtrWtKg();
            LOGGER.debug((Object)("Twin weight: " + twinWeightKg));
            Double combinedWeightKg = inWeightKg + twinWeightKg;
            LOGGER.debug((Object)("Considering combined weight: " + combinedWeightKg));
            Double cheCombinedTwinMaxWeightKg = InventoryControlUtils.getWeightLimitForCheKind(inCheKindEnum, inHandlingTypeEnum, LiftModeEnum.TWIN, inNominalLengthEnum);
            if (cheCombinedTwinMaxWeightKg != null && combinedWeightKg > cheCombinedTwinMaxWeightKg) {
                LOGGER.warn((Object)("Combined unit weight " + combinedWeightKg + " is beyond CHE capacity " + cheCombinedTwinMaxWeightKg + "."));
                return false;
            }
        }
        return true;
    }

    public static CarrierVisit getEffectiveInboundCarrierVisitForDischarge(UnitFacilityVisit inUnitFacilityVisit) throws BizViolation {
        CarrierVisit ibCv;
        if (inUnitFacilityVisit == null) {
            LOGGER.error((Object)"UFV is null. Cannot determine Inbound CarrierVisit.");
            return null;
        }
        LocPosition ufvLastKnownPosition = inUnitFacilityVisit.getUfvLastKnownPosition();
        if (ufvLastKnownPosition != null && ufvLastKnownPosition.isVesselPosition()) {
            String cvId = ufvLastKnownPosition.getPosLocId();
            LOGGER.debug((Object)("Using CarrierVisit " + cvId + " obtained from UFV LastKnownPosition."));
            ibCv = CarrierVisit.resolveCv((Facility)ContextHelper.getThreadFacility(), (CarrierModeEnum) CarrierModeEnum.VESSEL, (String)cvId);
        } else {
            LOGGER.debug((Object)"Using ActualIbCv from Ufv.");
            ibCv = inUnitFacilityVisit.getUfvActualIbCv();
        }
        if (!LocTypeEnum.VESSEL.equals((Object)ibCv.getCvCarrierMode()) && !LocTypeEnum.SEA.equals((Object)ibCv.getCvCarrierMode())) {
            LOGGER.debug((Object)"Last known position of unit being discharged is not a vessel...looking for partial discharge move event...");
            MoveEvent lastMoveEvent = MoveEvent.getLastMoveEvent(inUnitFacilityVisit);
            if (lastMoveEvent != null && WiMoveKindEnum.VeslDisch.equals((Object)lastMoveEvent.getMveMoveKind())) {
                String cvId = lastMoveEvent.getMveFromPosition().getPosLocId();
                LOGGER.debug((Object)("Using CarrierVisit " + cvId + " from last move event."));
                ibCv = CarrierVisit.resolveCv((Facility)ContextHelper.getThreadFacility(), (CarrierModeEnum) CarrierModeEnum.VESSEL, (String)cvId);
            } else {
                LOGGER.error((Object)("Last move event " + lastMoveEvent + " for " + inUnitFacilityVisit + " was not a discharge event. " + "Cannot infer carrier visit."));
                return null;
            }
        }
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug((Object)("Effective inbound CarrierVisit for " + inUnitFacilityVisit + " is " + (Object)ibCv));
        }
        return ibCv;
    }

    @Nullable
    public static Integer getPositionIndexFromSlotString(@NotNull String inPositionSlotOnCarriage, @NotNull EquipType inCarriageEquipmentType) {
        if (inPositionSlotOnCarriage == null || inCarriageEquipmentType == null) {
            LOGGER.error((Object)("Could not determine position index from position slot: " + inPositionSlotOnCarriage + " with allowed slots: " + (Object)inCarriageEquipmentType));
            return null;
        }
        LOGGER.debug((Object)("Getting index for position on carriage: " + inPositionSlotOnCarriage));
        String allowedPositionSlotLabels = inCarriageEquipmentType.getEqtypSlotLabels();
        String[] allowedPositionSlots = allowedPositionSlotLabels.split(",");
        for (int i = 0; i < allowedPositionSlots.length; ++i) {
            String slotLabel = allowedPositionSlots[i];
            if (!slotLabel.equalsIgnoreCase(inPositionSlotOnCarriage)) continue;
            return i;
        }
        LOGGER.error((Object)("Could not determine position index from position slot: " + inPositionSlotOnCarriage + " with allowed slots: " + allowedPositionSlotLabels));
        return null;
    }

    @Nullable
    public static String getPositionIndexFromSlotString(@NotNull String inPositionSlotOnCarriage, @NotNull String inChassisId) {
        Chassis chassis = Chassis.findChassis((String)inChassisId);
        if (chassis != null) {
            return chassis.getEqEquipType().getChsSlotIndex(inPositionSlotOnCarriage);
        }
        return null;
    }

    @Nullable
    public static String getPositionSlotStringFromPositionIndex(@NotNull Integer inPositionIndexOnCarriage, @NotNull EquipType inCarriageEquipmentType) {
        Integer positionOnCarriage = inPositionIndexOnCarriage - 1;
        if (positionOnCarriage == null || inCarriageEquipmentType == null) {
            LOGGER.error((Object)("Could not determine position index from position slot: " + positionOnCarriage + " with allowed slots: " + (Object)inCarriageEquipmentType));
            return null;
        }
        String allowedPositionSlotLabels = inCarriageEquipmentType.getEqtypSlotLabels();
        String[] allowedPositionSlots = allowedPositionSlotLabels.split(",");
        if (allowedPositionSlots.length <= positionOnCarriage) {
            LOGGER.error((Object)("Could not determine position index from position index: " + positionOnCarriage + " with allowed slots: " + allowedPositionSlotLabels));
            return null;
        }
        return allowedPositionSlots[positionOnCarriage];
    }

    @NotNull
    public static Map<String, Integer> getAllowedPositionSlots(@Nullable Che inChe) {
        String allowedSlotLabels;
        EquipType attachedEquipType;
        HashMap<String, Integer> allowedPositionSlots = new HashMap<String, Integer>();
        if (inChe != null && (attachedEquipType = InventoryControlUtils.getEquipmentTypeOfAttachedCarriage(inChe)) != null && (allowedSlotLabels = attachedEquipType.getEqtypSlotLabels()) != null) {
            String[] allowedLabels = allowedSlotLabels.split(",");
            for (int i = 0; i < allowedLabels.length; ++i) {
                if (allowedLabels[i].isEmpty()) continue;
                allowedPositionSlots.put(allowedLabels[i], i + 1);
            }
        }
        return allowedPositionSlots;
    }

    @NotNull
    public static Map<String, Integer> getAllowedPositionSlots(String inCheId) {
        Che che = Che.findCheByShortName((String)inCheId, (Yard)ContextHelper.getThreadYard());
        return InventoryControlUtils.getAllowedPositionSlots(che);
    }

    @Nullable
    public static EquipType getEquipmentTypeOfAttachedCarriage(@NotNull Che inChe) {
        EquipType carriageEquipType = null;
        String equipmentId = inChe.getCheAttachedChassisId();
        if (inChe.getHasTrailer() != null) {
//            if ((long)HasTrailer.HAS_SPECIFIC_TRAILER.toSerialValue() == inChe.getHasTrailer()) {
//                Equipment attachedCarriageEquipment = Equipment.findEquipment((String)equipmentId);
//                carriageEquipType = attachedCarriageEquipment.getEqEquipType();
//            } else if ((long)HasTrailer.HAS_A_TRAILER.toSerialValue() == inChe.getHasTrailer()) {
//                IEquipmentFinder equipmentFinder = (IEquipmentFinder) Roastery.getBean((String)"equipmentFinder");
//                carriageEquipType = equipmentFinder.findEquipmentTypeForBombcart();
//            }
        }
        if (carriageEquipType == null) {
            LOGGER.error((Object)("Cannot calculate position slot on carriage as the equipment type (for equipment id: " + equipmentId + ") is " + "indeterminate or Che " + (Object)inChe + " does not have an attached carriage."));
        }
        return carriageEquipType;
    }

    public static boolean canAttachEquipment(@NotNull IEqLinkContext<UnitNode, Unit, Equipment> inAttachmentContext, @NotNull Unit inUnitToBeAttached, @NotNull Equipment inEquipmentToAttachTo) throws BizViolation {
//        UnitAttachValidationExtensionContext context;
//        BizViolation bv = null;
//        if (inAttachmentContext == null) {
//            bv = BizViolation.create((IPropertyKey)IInventoryPropertyKeys.CANNOT_ATTACH_UNIT_INVALID_ARGUMENTS, null, (Object)inUnitToBeAttached, (Object)inEquipmentToAttachTo);
//            LOGGER.error((Object)("Throwing BizViolation: " + bv.getMessage()));
//            throw bv;
//        }
//        if (!EqUnitRoleEnum.CARRIAGE.equals((Object)inAttachmentContext.getRole())) {
//            return true;
//        }
//        if (inAttachmentContext == null || inUnitToBeAttached == null || inEquipmentToAttachTo == null) {
//            bv = BizViolation.create((IPropertyKey) IInventoryPropertyKeys.CANNOT_ATTACH_UNIT_INVALID_ARGUMENTS, null, (Object)inUnitToBeAttached, (Object)inEquipmentToAttachTo);
//            LOGGER.error((Object)("Throwing BizViolation: " + bv.getMessage()));
//            throw bv;
//        }
//        boolean canAttach = true;
//        boolean isSwipeAllowed = inAttachmentContext.isSwipeAllowed();
//        boolean isMultipleAllowed = inAttachmentContext.isMultipleNodeAllowed();
//        UnitNode unitNode = inUnitToBeAttached.findUnitByEquipment(inAttachmentContext, inEquipmentToAttachTo);
//        UnitCombo unitCombo = unitNode == null ? null : unitNode.getUnit().getUnitCombo();
//        IUnitAttachValidationHandler unitAttachValidationHandler = (IUnitAttachValidationHandler) PortalApplicationContext.getBean((String)"unitAttachValidationHandler");
//        UnitAttachValidationExtensionResponse response = (UnitAttachValidationExtensionResponse)unitAttachValidationHandler.invoke((IExtensionContext)(context = new UnitAttachValidationExtensionContext(ContextHelper.getThreadUserContext(), unitCombo, inUnitToBeAttached, inEquipmentToAttachTo, isMultipleAllowed, isSwipeAllowed)));
//        if (response.hasStatus(ExtensionResponseStatus.NO_EXTENSION) || response.hasStatus(ExtensionResponseStatus.EXTENSION_DISABLED)) {
//            LOGGER.debug((Object)"No Extension Available or Extension Disabled for UnitAttachValidator, assuming attach valid.");
//            return true;
//        }
//        if (!response.getValidationPassReult().booleanValue() && response.getCauseOfValidationFailure() != null) {
//            throw response.getCauseOfValidationFailure();
//        }
//        return response.getValidationPassReult();
        return false;
    }

    public static CarrierModeEnum getEquivalentCarrierModeEnum(LocTypeEnum inLocTypeEnum) {
        switch (inLocTypeEnum.getName()) {
            case "VESSEL": {
                return CarrierModeEnum.VESSEL;
            }
            case "SEA": {
                return CarrierModeEnum.VESSEL;
            }
            case "TRAIN": {
                return CarrierModeEnum.TRAIN;
            }
            case "RAILCAR": {
                return CarrierModeEnum.TRAIN;
            }
            case "TRUCK": {
                return CarrierModeEnum.TRUCK;
            }
        }
        return CarrierModeEnum.UNKNOWN;
    }

    public static LocTypeEnum getEquivalentLocTypeEnum(CarrierModeEnum inCarrierModeEnum) {
        switch (inCarrierModeEnum.getName()) {
            case "VESSEL": {
                return LocTypeEnum.VESSEL;
            }
            case "TRAIN": {
                return LocTypeEnum.TRAIN;
            }
            case "TRUCK": {
                return LocTypeEnum.TRUCK;
            }
        }
        return LocTypeEnum.UNKNOWN;
    }
}
