package com.zpmc.ztos.infra.base.business.model;

import com.alibaba.fastjson.support.geo.LineString;
import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.business.enums.argo.EquipBasicLengthEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.LocTypeEnum;
import com.zpmc.ztos.infra.base.business.enums.spatial.BinNameTypeEnum;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.business.space.Position;
import com.zpmc.ztos.infra.base.business.xps.AbstractXpsYardBin;
import com.zpmc.ztos.infra.base.common.contexts.BinContext;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.common.exceptions.BizFailure;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.helps.BinModelHelper;
import com.zpmc.ztos.infra.base.common.helps.ContextHelper;
import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;
import com.zpmc.ztos.infra.base.common.model.RefIDPosition;
import com.zpmc.ztos.infra.base.common.model.Roastery;
import com.zpmc.ztos.infra.base.common.model.ValueObject;
import com.zpmc.ztos.infra.base.common.scopes.Facility;
import com.zpmc.ztos.infra.base.common.scopes.Yard;
import com.zpmc.ztos.infra.base.common.utils.ArgoUtils;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.awt.*;
import java.io.Serializable;

public class LocPosition extends Position {

    private LocTypeEnum _posLocType;
    private String _posLocId;
    private Long _posLocGkey;
    private static final long serialVersionUID = 3183352016882791359L;
    private static final String SKIP_YARD_BLOCK_NAME = "SKPYRD";
    private static final LocPosition UNKNOWN = new LocPosition(LocTypeEnum.UNKNOWN, null, null, null, null, null);
    private static final Logger LOGGER = Logger.getLogger(LocPosition.class);
    public static final String RAMP_ID = "RAMP";
    public static final String TIER_DELIMITER = ".";
    private static final String SLOT_POS_ON_CARRIAGE_ZERO = "0";

    public static LocPosition resolvePosition(Facility inFacility, LocTypeEnum inLocType, String inLocId, String inLocSlot, String inLocOrientation, EquipBasicLengthEnum inEqLength) {
        LocPosition position;
        if (LocTypeEnum.YARD.equals((Object)inLocType)) {
            Yard yard = Yard.findYard(inLocId, inFacility);
            if (yard == null) {
                throw BizFailure.createProgrammingFailure((String)("Could not resolve yard from code <" + inLocId + ">"));
            }
            try {
                position = LocPosition.createYardPosition(yard, inLocSlot, inLocOrientation, inEqLength, false);
            }
            catch (BizViolation bv) {
                throw BizFailure.wrap((Throwable)bv);
            }
        } else if (LocTypeEnum.TRUCK.equals((Object)inLocType)) {
            if (inLocId.length() > 1 && inLocId.charAt(0) == '@') {
                try {
                    String key = inLocId.substring(1);
                    Long gKey = new Long(key);
                    CarrierVisit truckVisit = (CarrierVisit) HibernateApi.getInstance().get(CarrierVisit.class, (Serializable)gKey);
                    position = LocPosition.createTruckPosition(truckVisit, inLocSlot, inLocOrientation, inLocSlot);
                }
                catch (Exception e) {
                    LOGGER.error((Object)("updatePosition: could not convert truck key: " + inLocId));
                    CarrierVisit truckVisit = CarrierVisit.findOrCreateActiveTruckVisit(inFacility, inLocId);
                    position = LocPosition.createLocPosition(truckVisit, inLocSlot, inLocOrientation);
                }
            } else {
                CarrierVisit truckVisit = CarrierVisit.findOrCreateActiveTruckVisit(inFacility, inLocId);
                position = LocPosition.createLocPosition(truckVisit, inLocSlot, inLocOrientation);
            }
        } else if (LocTypeEnum.TRAIN.equals((Object)inLocType)) {
            CarrierVisit cv = CarrierVisit.findOrCreateTrainVisit(inFacility, inLocId);
            VisitDetails trainVisitDetails = cv.getCvCvd();
            position = null;
            if (trainVisitDetails != null) {
                position = (LocPosition)((Object)trainVisitDetails.resolveXpsPosition(inLocSlot, inLocOrientation));
            }
            if (position == null) {
                position = LocPosition.createLocPosition(cv, inLocSlot, inLocOrientation);
            }
        } else if (LocTypeEnum.COMMUNITY.equals((Object)inLocType)) {
            CarrierVisit truckVisit = CarrierVisit.getGenericTruckVisit(ContextHelper.getThreadComplex());
            position = LocPosition.createLocPosition(truckVisit, "", "");
        } else if (LocTypeEnum.VESSEL.equals((Object)inLocType)) {
            CarrierVisit cv = CarrierVisit.findOrCreateVesselVisit(inFacility, inLocId);
            position = LocPosition.createLocPosition(cv, inLocSlot, inLocOrientation);
        } else {
            if (LocTypeEnum.RAILCAR.equals((Object)inLocType)) {
                throw BizFailure.createProgrammingFailure((String)"Full rail not yet supported!");
            }
            throw BizFailure.createProgrammingFailure((String)("Could not resolve position for <" + (Object)((Object)inLocType) + ">"));
        }
        return position;
    }

    public static LocPosition resolvePosition(Facility inFacility, LocTypeEnum inLocType, String inLocId, String inLocSlot, String inTierName, String inLocOrientation, EquipBasicLengthEnum inEqLength) {
        LocPosition locPos;
        block3: {
            locPos = LocPosition.resolvePosition(inFacility, inLocType, inLocId, inLocSlot, inLocOrientation, inEqLength);
            if (locPos != null && inTierName != null && !inTierName.isEmpty()) {
                try {
                    long tierValue = Integer.parseInt(inTierName);
                    locPos.setPosTier(tierValue);
                }
                catch (NumberFormatException e) {
                    if (!LOGGER.isDebugEnabled()) break block3;
                    LOGGER.debug((Object) String.format("Invalid position tier coordinate >%s< : %s", inTierName, e.getMessage()));
                }
            }
        }
        return locPos;
    }

    public static LocPosition resolveYardRailPosition(ILocation inRailcarVisit, String inOperationalPosId, boolean inInsistOnValidBin) {
        return LocPosition.createYardRailTrackPosition(inRailcarVisit, inOperationalPosId, inInsistOnValidBin);
    }

    @Nullable
    public static LocPosition resolvePositionFromRefIDString(@NotNull String inRefIDString, String inOrientation, EquipBasicLengthEnum inCtrLength) throws BizViolation {
        RefIDPosition refID = RefIDPosition.create((String)inRefIDString);
        if (refID == null) {
            LOGGER.error((Object) String.format("Creation of refID object failed for refID string >%s<", inRefIDString));
            return null;
        }
        Character refIDType = refID.getQualifier();
        if (AbstractXpsYardBin.LOC_TYPE_VESSEL.equals(refIDType)) {
            return LocPosition.resolvePositionFromVesselRefID(refID, inOrientation, inCtrLength);
        }
        if (AbstractXpsYardBin.LOC_TYPE_RAIL.equals(refIDType)) {
            return LocPosition.resolvePositionFromRailRefID(refID, inOrientation, inCtrLength);
        }
        if (AbstractXpsYardBin.LOC_TYPE_CHE.equals(refIDType)) {
            return LocPosition.resolvePositionFromTruckRefID(refID, inOrientation, inCtrLength);
        }
        if (AbstractXpsYardBin.LOC_TYPE_YARD.equals(refIDType)) {
            return LocPosition.resolvePositionFromYardRefID(refID, inOrientation, inCtrLength);
        }
        LOGGER.info((Object) String.format("Position not resolved for refID >%s<", refID.getRefID()));
        return null;
    }

    @Nullable
    private static LocPosition resolvePositionFromYardRefID(@NotNull RefIDPosition inYardRefID, String inOrientation, EquipBasicLengthEnum inCtrLength) throws BizViolation {
        Facility facility = ContextHelper.getThreadFacility();
        if (facility == null) {
            LOGGER.error((Object) String.format("Could not determine the facility to resolve YARD refID >%s<", new Object[]{inYardRefID}));
            return null;
        }
        IArgoYardUtils yardUtils = LocPosition.getYardUtils();
        String locSlot = yardUtils.getFormattedInternalYardPositionFromRefID(inYardRefID);
        if (locSlot == null) {
            LOGGER.error((Object) String.format("Could not determine the location slot to resolve YARD refID >%s<", inYardRefID.getRefID()));
            return null;
        }
        String locID = inYardRefID.getLocation();
        return LocPosition.resolvePosition(facility, LocTypeEnum.YARD, locID, locSlot, inOrientation, inCtrLength);
    }

    @Nullable
    private static LocPosition resolvePositionFromVesselRefID(@NotNull RefIDPosition inVesselRefID, String inOrientation, EquipBasicLengthEnum inCtrLength) {
        if (!AbstractXpsYardBin.LOC_TYPE_VESSEL.equals(inVesselRefID.getQualifier())) {
            LOGGER.error((Object) String.format("refID is not for a vessel position >%s<", inVesselRefID.getRefID()));
            return null;
        }
        Facility facility = ContextHelper.getThreadFacility();
        if (facility == null) {
            LOGGER.error((Object) String.format("Could not determine the facility to evaluate VESSEL refID >%s<", inVesselRefID.getRefID()));
            return null;
        }
        String vesselName = inVesselRefID.getLocation();
        String vesselDeck = inVesselRefID.getBlock();
        String vesselBay = inVesselRefID.getRow();
        String vesselRow = inVesselRefID.getColumn();
        String vesselTier = inVesselRefID.getTier();
        String calcVesselDeck = LocPosition.getVesselDeck(vesselTier);
        if (!calcVesselDeck.equals(vesselDeck)) {
            LOGGER.error((Object) String.format("Invalid vessel deck >%s< specified in VESSEL refID >%s<", vesselDeck, inVesselRefID.getRefID()));
            return null;
        }
        String vesselSlot = String.format("%s%s%s", vesselBay, vesselRow, vesselTier);
        return LocPosition.resolvePosition(facility, LocTypeEnum.VESSEL, vesselName, vesselSlot, vesselTier, inOrientation, inCtrLength);
    }

    @Nullable
    private static LocPosition resolvePositionFromRailRefID(@NotNull RefIDPosition inRailRefID, String inOrientation, EquipBasicLengthEnum inCtrLength) {
        if (!AbstractXpsYardBin.LOC_TYPE_RAIL.equals(inRailRefID.getQualifier())) {
            LOGGER.error((Object) String.format("refID is not for a rail position >%s<", inRailRefID.getRefID()));
            return null;
        }
        Facility facility = ContextHelper.getThreadFacility();
        if (facility == null) {
            LOGGER.error((Object) String.format("Could not determine the facility to evaluate RAIL refID >%s<", inRailRefID.getRefID()));
            return null;
        }
        String railVisitName = inRailRefID.getLocation();
        String railCarName = inRailRefID.getBlock();
        String railCarPlatform = inRailRefID.getRow();
        String railCarSlot = inRailRefID.getColumn();
        String railCarLevel = inRailRefID.getTier();
        String trainSlot = String.format("%s.%s%s%s", railCarName, railCarPlatform, railCarLevel, railCarSlot);
        return LocPosition.resolvePosition(facility, LocTypeEnum.TRAIN, railVisitName, trainSlot, inOrientation, inCtrLength);
    }

    @Nullable
    private static LocPosition resolvePositionFromTruckRefID(@NotNull RefIDPosition inTruckRefID, String inOrientation, EquipBasicLengthEnum inCtrLength) {
        if (!AbstractXpsYardBin.LOC_TYPE_CHE.equals(inTruckRefID.getQualifier())) {
            LOGGER.error((Object) String.format("refID is not for a CHE position >%s<", inTruckRefID.getRefID()));
            return null;
        }
        Facility facility = ContextHelper.getThreadFacility();
        if (facility == null) {
            LOGGER.error((Object) String.format("Could not determine the facility to evaluate CHE refID >%s<", inTruckRefID.getRefID()));
            return null;
        }
        String yardLoc = inTruckRefID.getLocation();
        Yard yard = Yard.findYard(yardLoc, facility);
        if (yard == null) {
            LOGGER.error((Object) String.format("Yard not found - could not format position for TRUCK refID >%s<", inTruckRefID.getRefID()));
            return null;
        }
        String truckLoc = inTruckRefID.getBlock();
        String truckPos = inTruckRefID.getTier();
        if (truckLoc == null || truckLoc.isEmpty()) {
            LOGGER.error((Object) String.format("Invalid truck license to evaluate CHE refID >%s<", inTruckRefID.getRefID()));
            return null;
        }
        String truckSlot = truckPos == null || truckPos.isEmpty() ? truckLoc : String.format("%s.%s", truckLoc, truckPos);
        return LocPosition.resolvePosition(facility, LocTypeEnum.TRUCK, yardLoc, truckSlot, inOrientation, inCtrLength);
    }

    @Nullable
    public String resolveRefIDFromPosition(@Nullable BinNameTypeEnum inBinNameTypeEnum) {
        if (this.isYardPosition()) {
            return this.resolveRefIDFromYardPosition(inBinNameTypeEnum);
        }
        if (this.isVesselPosition()) {
            return this.resolveRefIDFromVesselPosition();
        }
        if (this.isTruckPosition()) {
            return this.resolveRefIDFromTruckPosition();
        }
        if (this.isRailPosition()) {
            return this.resolveRefIDFromRailPosition();
        }
        LOGGER.info((Object) String.format("refID not resolved for position >%s<", this.toString()));
        return null;
    }

    @Nullable
    private String resolveRefIDFromYardPosition(BinNameTypeEnum inBinNameTypeEnum) {
        if (inBinNameTypeEnum == null) {
            LOGGER.error((Object) String.format("Null Bin-name type cannot be used to resolve refID for Position >%s<", this.toString()));
            return null;
        }
        AbstractBin posBin = this.getPosBin();
        if (posBin == null) {
            LOGGER.error((Object) String.format("Position >%s< does not reference a valid bin", this.toString()));
            return null;
        }
        String posLocSlot = this.getPosSlot();
        String locID = this.getPosLocId();
        String blockName = this.getBlockName();
        String rowName = "";
        String colName = "";
        String tierName = "";
        Character locType = AbstractXpsYardBin.LOC_TYPE_YARD;
        if (!AbstractBin.isBinInstanceOf((AbstractBin)posBin, AbstractStack.class) || !AbstractBin.isBinInstanceOf((AbstractBin)posBin.getAbnParentBin(), AbstractSection.class)) {
            LOGGER.info((Object) String.format("RefID conversion currently applies only for YARD stack slots - Position >%s<", this.toString()));
            return null;
        }
        AbstractSection sectPosBin = (AbstractSection)HibernateApi.getInstance().downcast((DatabaseEntity)posBin.getAbnParentBin(), AbstractSection.class);
        AbstractStack stackPosBin = (AbstractStack)HibernateApi.getInstance().downcast((DatabaseEntity)posBin, AbstractStack.class);
        rowName = sectPosBin.getRowName(inBinNameTypeEnum);
        colName = stackPosBin.getColumnName(inBinNameTypeEnum);
        long tierIndex = posBin.getTierIndexFromInternalSlotString(posLocSlot);
        tierName = posBin.getTierName(Long.valueOf(tierIndex));
        return String.format("%c.%s:%s.%s.%s.%s", locType, locID, blockName, rowName, colName, tierName);
    }

    @Nullable
    private String resolveRefIDFromVesselPosition() {
        String posLocSlot = this.getPosSlot();
        String[] vesselCoords = LocPosition.getVesselCoordinates(posLocSlot);
        Character locType = AbstractXpsYardBin.LOC_TYPE_VESSEL;
        String vesselName = this.getPosLocId();
        return String.format("%c.%s:%s.%s.%s.%s", locType, vesselName, vesselCoords[0], vesselCoords[1], vesselCoords[2], vesselCoords[3]);
    }

    @Nullable
    private String resolveRefIDFromTruckPosition() {
        String retVal = null;
        Character locType = AbstractXpsYardBin.LOC_TYPE_CHE;
        String locID = this.getPosLocId();
        String posLocSlot = this.getPosSlot();
        if (posLocSlot.indexOf(46) > 0) {
            String[] components = posLocSlot.split("\\.");
            if (components == null || components.length == 0) {
                LOGGER.error((Object) String.format("Failed to parse truck position for >%s<", posLocSlot));
                return null;
            }
            if (components.length != 2) {
                LOGGER.error((Object) String.format("Truck position has invalid truck locSlot >%s<", posLocSlot));
                return null;
            }
            retVal = String.format("%c.%s:%s...%s", locType, locID, components[0], components[1]);
        } else {
            retVal = String.format("%c.%s:%s...", locType, locID, posLocSlot);
        }
        return retVal;
    }

    @Nullable
    private String resolveRefIDFromRailPosition() {
        String railDetails;
        String railCarName = "";
        String railCarPlatform = "";
        String railCarLevel = "";
        String railCarSlot = "";
        String posLocSlot = this.getPosSlot();
        if (posLocSlot.indexOf(46) > 0) {
            String[] components = posLocSlot.split("\\.");
            if (components == null || components.length == 0) {
                LOGGER.error((Object) String.format("Failed to parse rail position >%s<", posLocSlot));
                return null;
            }
            if (components.length != 2) {
                LOGGER.error((Object) String.format("Rail position has invalid rail locSlot >%s<", posLocSlot));
                return null;
            }
            railCarName = components[0];
            railDetails = components[1];
            if (railDetails == null || railDetails.length() != 3) {
                LOGGER.error((Object) String.format("Invalid rail position rail for locSlot >%s<", posLocSlot));
                return null;
            }
        } else {
            LOGGER.error((Object) String.format("Failed to parse rail position >%s<", posLocSlot));
            return null;
        }
        railCarPlatform = railDetails.substring(0, 1);
        railCarLevel = railDetails.substring(1, 2);
        railCarSlot = railDetails.substring(2, 3);
        Character locType = AbstractXpsYardBin.LOC_TYPE_RAIL;
        String railVisitName = this.getPosLocId();
        return String.format("%c.%s:%s.%s.%s.%s", locType, railVisitName, railCarName, railCarPlatform, railCarSlot, railCarLevel);
    }

    public Class getArchiveClass() {
 //       return ArchiveLocPosition.class;
        return null;
    }

    public boolean doArchive() {
        return true;
    }

    public LocPosition() {
    }

    public void updateBinAndTierFromAncestorAndUserSlotName(LocTypeEnum inLocType, String inSlotName) {
        this.userSlotString2InternalSlotString(inLocType, inSlotName);
    }

    private LocPosition(LocTypeEnum inLocType, String inLocId, Long inLocGkey, String inSlot, String inOrientation, String inSlotOnCarriage) {
        this.setPosLocType(inLocType);
        this.setPosLocId(inLocId);
        this.setPosLocGkey(inLocGkey);
        this.setPosSlot(inSlot);
        this.setPosOrientation(inOrientation);
        this.setPosSlotOnCarriage(inSlotOnCarriage);
        this.resolvePosBinAndTier(inLocType, inSlot);
        this.updatePosName(inLocType, inLocId, inSlot);
    }

    private void resolvePosBinAndTier(LocTypeEnum inLocType, String inSlotName) {
        this.userSlotString2InternalSlotString(inLocType, inSlotName);
    }

    private void userSlotString2InternalSlotString(LocTypeEnum inLocTypeEnum, String inSlotName) {
        CarrierVisit cvTrain;
        Yard yard;
        if (LocTypeEnum.YARD == inLocTypeEnum && (yard = Yard.loadByGkey(this.getPosLocGkey())) != null) {
            this.updateBinAndTierFromAncestorAndInternalSlotName(yard.getYrdBinModel(), inSlotName);
        }
        if (LocTypeEnum.VESSEL == inLocTypeEnum) {
            CarrierVisit cvVessel = this.resolveInboundCarrierVisit();
            VisitDetails vesselVisitDetails = cvVessel != null ? cvVessel.getCvCvd() : null;
            String internalSlotName = "";
            if (vesselVisitDetails != null) {
                try {
                    internalSlotName = vesselVisitDetails.userSlotString2InternalSlotString(inSlotName);
                }
                catch (BizViolation inBizViolation) {
                    LOGGER.error((Object)("Issue found in parsing internal slot String for position " + inSlotName));
                }
            }
            if (cvVessel != null) {
                this.updateBinAndTierFromAncestorAndInternalSlotName((AbstractBin)cvVessel.getCarrierBinModel(), internalSlotName);
            }
        }
        if (LocTypeEnum.RAILCAR == inLocTypeEnum && (cvTrain = this.resolveInboundCarrierVisit()) != null) {
            this.updateBinAndTierFromAncestorAndInternalSlotName((AbstractBin)cvTrain.getCarrierBinModel(), this._posLocId + TIER_DELIMITER + inSlotName);
        }
    }

    private void updatePosName(LocTypeEnum inLocType, String inLocId, String inSlotName) {
        this.setPosName(this.buildInternalPosName(inLocType, inLocId, inSlotName, null, null));
    }

    private void updatePosName() {
        StringBuilder buf = new StringBuilder();
        buf.append(this.buildInternalPosName(this._posLocType, this._posLocId, this.getPosSlot(), this.getPosOrientation(), this.getPosOperationalPosId()));
        this.setPosName(buf.toString());
    }

    public void updatePosTruckSlot(String inPosSlot) {
        if (inPosSlot != null && LocTypeEnum.TRUCK.equals((Object)this.getPosLocType())) {
            this.setPosSlot(inPosSlot);
        }
    }

    public void updatePosTrainSlot(String inPosSlot) {
        if (inPosSlot != null && LocTypeEnum.TRAIN.equals((Object)this.getPosLocType())) {
            this.setPosSlot(inPosSlot);
        }
    }

    public void updateRailcarPosSlot(String inPosSlot) {
        if (LocTypeEnum.YARD.equals((Object)this.getPosLocType())) {
            this.setPosSlot(inPosSlot);
        }
    }

    public void updatePosName(String inName) {
        this.setPosName(inName);
    }

    public void updatePosOperationalPosId(String inOperationalPosId) {
        this.setPosOperationalPosId(inOperationalPosId);
        if (LocTypeEnum.TRUCK.equals((Object)this._posLocType)) {
            this.updatePosName();
        }
    }

    public void updatePosSlotOnCarriage(@Nullable String inSlotOnCarriage) throws BizViolation {
//        if (!Strings.isNullOrEmpty((String)inSlotOnCarriage)) {
//            try {
//                Integer.parseInt(inSlotOnCarriage);
//            }
//            catch (NumberFormatException e) {
//                throw new BizViolation(IArgoPropertyKeys.INVALID_SLOT_ON_CARRIAGE, (Throwable)e, null, null, null);
//            }
//        }
        this.setPosSlotOnCarriage(inSlotOnCarriage);
    }

    public static char getPositionPrefixChar(LocTypeEnum inLocType) {
        if (LocTypeEnum.TRAIN.equals((Object)inLocType)) {
            return 'R';
        }
        if (LocTypeEnum.CONTAINER.equals((Object)inLocType)) {
            return 'U';
        }
        return inLocType.getKey().charAt(0);
    }

    public static LocPosition createYardPosition(Yard inYard, String inYardSlot, String inOrientation, EquipBasicLengthEnum inCtrLength, boolean inInsistOnValidBin) throws BizViolation {
        String formattedYardSlotName;
        String tierName = "";
        if (inYard == null) {
            throw BizViolation.create((IPropertyKey) IArgoPropertyKeys.NO_YARD_IN_CONTEXT, null);
        }
        AbstractBin yardModel = inYard.getYrdBinModel();
        if (yardModel == null || inYardSlot == null) {
            formattedYardSlotName = inYardSlot;
        } else {
            AbstractBlock block;
            String binName = LocPosition.getBinNameFromSlotName(inYardSlot);
            tierName = LocPosition.getTierNameFromSlotName(inYardSlot);
            AbstractBin bin = yardModel.findDescendantBinFromInternalSlotString(binName, BinContext.findBinContext((String)"STOWAGE_CONTAINERS"));
            if (bin == null) {
                if (inInsistOnValidBin) {
                    throw BizViolation.create((IPropertyKey) IArgoPropertyKeys.INVALID_YARD_BIN, null, (Object)inYardSlot, (Object)inYard.getYrdName());
                }
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug((Object) String.format("unrecognized position: '%s' is not a recognized position within '%s'", inYardSlot, inYard.getYrdName()));
                }
                formattedYardSlotName = inYardSlot;
            } else if (BinModelHelper.getBlockFromModelBin((AbstractBin)bin) != null) {
                formattedYardSlotName = bin.getUiFullPositionWithTier(LocPosition.getBinNameTypeEnumFromEquipBasicLengthEnum(inCtrLength), tierName);
            } else {
                formattedYardSlotName = binName + TIER_DELIMITER + tierName;
                LOGGER.error((Object)("Averted SEVERE error from Position createYardPosition by returning " + formattedYardSlotName));
            }
            if (inInsistOnValidBin && !tierName.isEmpty() && !(block = BinModelHelper.getBlockFromModelBin((AbstractBin)bin)).isValidTierName(tierName)) {
                String blockName = block.getUiFullPositionWithTier(BinNameTypeEnum.STANDARD, null);
                throw BizViolation.create((IPropertyKey) IArgoPropertyKeys.INVALID_TIER, null, (Object)tierName, (Object)blockName, (Object)inYard.getYrdName());
            }
        }
        LocPosition position = LocPosition.createLocPosition(inYard, inYardSlot, inOrientation);
        position.updatePosName(LocTypeEnum.YARD, inYard.getLocId(), formattedYardSlotName);
        return position;
    }

    public static LocPosition createYardPosition(@NotNull Yard inYard, @NotNull AbstractBin inBin, @NotNull String inOrientation, @NotNull EquipBasicLengthEnum inBasicLength, @Nullable Long inTier) {
        String posSlot;
        String binName;
        String string = binName = EquipBasicLengthEnum.BASIC20.equals((Object)inBasicLength) ? inBin.getAbnName() : inBin.getAbnNameAlt();
        if (inTier == null || inTier == 0L) {
            posSlot = binName;
        } else {
            String tierName = inBin.getTierName(inTier);
            posSlot = binName + TIER_DELIMITER + tierName;
        }
        LocPosition p = new LocPosition(LocTypeEnum.YARD, inYard.getLocId(), inYard.getLocGkey(), posSlot, inOrientation, null);
        p.setPosBin(inBin);
        p.setPosTier(inTier);
        p.updatePosName(LocTypeEnum.YARD, inYard.getLocId(), posSlot);
        return p;
    }

    @Deprecated
    public static LocPosition createTrackPosition(@NotNull Yard inYard, @NotNull String inYardSlot, boolean inInsistOnValidBin) throws BizViolation {
        String formattedYardSlotName;
        AbstractBin yardModel = inYard.getYrdBinModel();
        if (yardModel == null) {
            formattedYardSlotName = inYardSlot;
        } else {
            String binName = LocPosition.getBinNameFromSlotName(inYardSlot);
            AbstractBin bin = yardModel.findDescendantBinFromInternalSlotString(binName, BinContext.findBinContext((String)"STOWAGE_RAILCARS"));
            if (bin == null) {
                if (inInsistOnValidBin) {
                    throw BizViolation.create((IPropertyKey) IArgoPropertyKeys.INVALID_YARD_BIN, null, (Object)inYardSlot, (Object)inYard.getYrdName());
                }
                if (!SKIP_YARD_BLOCK_NAME.equals(inYardSlot)) {
                    LOGGER.warn((Object) String.format("unrecognized position: '%s' is not a recognized position within '%s'", inYardSlot, inYard.getYrdName()));
                }
                formattedYardSlotName = inYardSlot;
            } else {
                formattedYardSlotName = bin.getAbnName();
            }
        }
        LocPosition position = LocPosition.createLocPosition(inYard, inYardSlot, null);
        position.updatePosName(LocTypeEnum.YARD, inYard.getLocId(), formattedYardSlotName);
        return position;
    }

    public static LocPosition createYardRailTrackPosition(ILocation inRailcarVisit, String inOperationalPosId, boolean inInsistOnValidBin) {
        Facility facility = inRailcarVisit.getLocFacility();
        Yard yard = null;
        try {
            yard = facility.getActiveYard();
        }
        catch (BizViolation inBizViolation) {
            throw BizFailure.wrap((Throwable)inBizViolation);
        }
        AbstractBin yardModel = yard.getYrdBinModel();
        AbstractBin bin = null;
        Point anchorPoint = null;
        String trackName = null;
        String spotIndex = null;
        if (yardModel != null && StringUtils.isNotBlank((String)inOperationalPosId)) {
            String gpsPointOffsetInCm;
            String[] railLocation = inOperationalPosId.split("\\-");
            trackName = railLocation.length > 0 ? railLocation[0] : null;
            spotIndex = railLocation.length > 1 ? railLocation[1] : null;
            String platformSeq = railLocation.length > 2 ? railLocation[2] : null;
            String tzSlot = railLocation.length > 3 ? railLocation[3] : null;
            String meterMark = railLocation.length > 4 ? railLocation[4] : null;
            String string = gpsPointOffsetInCm = railLocation.length > 5 ? railLocation[5] : null;
            if (StringUtils.isNotBlank((String)trackName)) {
                bin = LocPosition.getYardUtils().findRailTrack(trackName, yardModel.getAbnGkey());
            }
            AbstractBin tzSlotBin = null;
            if (bin == null) {
                if (inInsistOnValidBin) {
                    throw BizFailure.wrap((Throwable)BizViolation.create((IPropertyKey) IArgoPropertyKeys.INVALID_YARD_BIN, null, (Object)inOperationalPosId, (Object)yard.getYrdName()));
                }
                if (!SKIP_YARD_BLOCK_NAME.equals(inOperationalPosId)) {
                    LOGGER.warn((Object) String.format("unrecognized position: '%s' is not a recognized position within '%s'", inOperationalPosId, yard.getYrdName()));
                }
            } else if (StringUtils.isNotBlank((String)platformSeq) && StringUtils.isNumeric((String)platformSeq)) {
                LineString trackCenterLine = (LineString)bin.getAbnCenterline();
//                double trackLength = trackCenterLine.getLength();
//                Point startPoint = trackCenterLine.getStartPoint();
//                AbstractBin trackPlan = bin.getAbnParentBin();
//                if (trackCenterLine != null && trackPlan != null) {
//                    if (StringUtils.isNotBlank((String)tzSlot)) {
//                        String tzSlotBinName = LocPosition.getBinNameFromSlotName(tzSlot);
//                        tzSlotBin = yardModel.findDescendantBinFromInternalSlotString(tzSlotBinName, BinContext.findBinContext((String)"STOWAGE_CONTAINERS"));
//                        if (tzSlotBin != null && tzSlotBin.getAbnPolygon() != null && tzSlotBin.getAbnPolygon().getCentroid() != null) {
//                            Point point = JTSUtils.getProjectionPoint((LineString)trackCenterLine, (Point)tzSlotBin.getAbnPolygon().getCentroid());
//                            if (JTSUtils.isValidPoint((Point)point)) {
//                                double d = JTSUtils.getDistanceAlongLineString((LineString)trackCenterLine, (Point)point);
//                                double railcarCentroid = LocPosition.getRailUtils().getRailcarCentroidOffset(inRailcarVisit.getLocGkey(), Integer.parseInt(platformSeq), d);
//                                anchorPoint = JTSUtils.pointAlongLineString((LineString)trackCenterLine, (Point)startPoint, (Double)railcarCentroid);
//                                LocPosition.validateAnchorPointAndLogMessage(bin, anchorPoint, railcarCentroid, trackLength);
//                            } else {
//                                LOGGER.warn((Object)String.format("Transfer Point [%s] could not be projected on RailTrack [%s]", tzSlotBinName, trackName));
//                            }
//                        }
//                    } else if (StringUtils.isNotBlank((String)meterMark) && StringUtils.isNumeric((String)meterMark)) {
//                        double meterMarkStart = LocPosition.getYardUtils().getTrackPlanMeterMarkOffset(trackPlan.getAbnGkey());
//                        double railTrackStartOffset = LocPosition.getYardUtils().getRailTrackStartOffset(bin.getAbnGkey());
//                        double meterMarkOffsetOnTrack = Math.abs((double)Integer.parseInt(meterMark) + meterMarkStart - railTrackStartOffset);
//                        double railcarCentroid = LocPosition.getRailUtils().getRailcarCentroidOffset(inRailcarVisit.getLocGkey(), Integer.parseInt(platformSeq), UnitUtils.convertTo((double)meterMarkOffsetOnTrack, (MeasurementUnit)LengthUnit.METERS, (MeasurementUnit)LengthUnit.CENTIMETERS));
//                        anchorPoint = JTSUtils.pointAlongLineString((LineString)trackCenterLine, (Point)startPoint, (Double)railcarCentroid);
//                        LocPosition.validateAnchorPointAndLogMessage(bin, anchorPoint, railcarCentroid, trackLength);
//                    } else if (StringUtils.isNotBlank((String)gpsPointOffsetInCm) && StringUtils.isNumeric((String)gpsPointOffsetInCm)) {
//                        double offsetInCmOnTrack = Math.abs(Integer.parseInt(gpsPointOffsetInCm));
//                        double railcarCentroid = LocPosition.getRailUtils().getRailcarCentroidOffset(inRailcarVisit.getLocGkey(), Integer.parseInt(platformSeq), offsetInCmOnTrack);
//                        anchorPoint = JTSUtils.pointAlongLineString((LineString)trackCenterLine, (Point)startPoint, (Double)railcarCentroid);
//                        LocPosition.validateAnchorPointAndLogMessage(bin, anchorPoint, railcarCentroid, trackLength);
//                    }
//                }
            }
        }
        StringBuilder posName = null;
        if (trackName != null) {
            posName = new StringBuilder();
            posName.append(trackName);
            posName.append("-");
            if (spotIndex != null) {
                posName.append(spotIndex);
            }
        }
        String slotName = posName != null ? posName.toString() : null;
        LocPosition position = LocPosition.createLocPosition(yard, slotName, null);
        position.updatePosName(LocTypeEnum.YARD, yard.getLocId(), slotName);
        position.updatePosOperationalPosId(inOperationalPosId);
        position.setPosBin(bin);
//        if (JTSUtils.isValidPoint(anchorPoint)) {
//            position.setPosAnchor(anchorPoint);
//        }
        return position;
    }

    public static String getTierNameFromSlotName(String inYardSlot) throws BizViolation {
        String tierName;
        int lastDot = inYardSlot.lastIndexOf(46);
        if (lastDot > 0) {
            tierName = inYardSlot.substring(lastDot + 1, inYardSlot.length());
            if (StringUtils.isEmpty((String)tierName)) {
                throw BizViolation.create((IPropertyKey) IArgoPropertyKeys.INVALID_YARD_TIER, null, (Object)inYardSlot);
            }
        } else {
            tierName = "";
        }
        return tierName;
    }

    public static String getBinNameFromSlotName(String inYardSlot) {
        int lastDot = inYardSlot.lastIndexOf(46);
        String binName = lastDot > 0 ? inYardSlot.substring(0, lastDot) : inYardSlot;
        return binName;
    }

    public static boolean hasValidTierName(String inYardPositionSlot, @Nullable AbstractBin inOwningBlock) throws BizViolation {
        boolean isValid = false;
        String tierName = LocPosition.getTierNameFromSlotName(inYardPositionSlot);
        if (StringUtils.isNotEmpty((String)tierName)) {
            if (inOwningBlock != null && !inOwningBlock.isValidTierName(tierName)) {
                throw BizViolation.create((IPropertyKey) IArgoPropertyKeys.INVALID_TIER_FOR_BLOCK, null, (Object)tierName, (Object)inOwningBlock.getAbnName());
            }
            isValid = true;
        }
        return isValid;
    }

    public static LocPosition createVesselPosition(ILocation inVesselVisit, String inSlot, String inDoorDirection) {
        LocPosition.verifyMode(inVesselVisit, LocTypeEnum.VESSEL);
        return LocPosition.createLocPosition(inVesselVisit, inSlot, inDoorDirection);
    }

    public static LocPosition createVesselPosition(CarrierVisit inVesselVisit, String inSlot, String inDoorDirection, boolean inValidatePosition) {
        LocPosition.verifyMode(inVesselVisit, LocTypeEnum.VESSEL);
        return LocPosition.createLocPosition(inVesselVisit, inSlot, inDoorDirection);
    }

    public static LocPosition createTruckPosition(ILocation inTruckVisit, String inPosOnTruck, String inDoorDirection) {
        return LocPosition.createTruckPosition(inTruckVisit, inPosOnTruck, inDoorDirection, null);
    }

    public static LocPosition createTruckPosition(ILocation inTruckVisit, String inPosOnTruck, String inDoorDirection, String inOperationalPosId) {
        LocPosition.verifyMode(inTruckVisit, LocTypeEnum.TRUCK);
        LocPosition pos = LocPosition.createLocPosition(inTruckVisit, inPosOnTruck, inDoorDirection);
        if (inOperationalPosId != null) {
            pos.setPosOperationalPosId(inOperationalPosId);
        }
        try {
            pos.updatePosSlotOnCarriage(inPosOnTruck);
        }
        catch (BizViolation bizViolation) {
            // empty catch block
        }
        pos.updatePosName();
        return pos;
    }

    public static LocPosition createRailcarPosition(ILocation inRailcarVisit, String inPosOnCar, String inDoorDirection) {
        LocPosition.verifyMode(inRailcarVisit, LocTypeEnum.RAILCAR);
        if (inPosOnCar != null) {
            inPosOnCar = inPosOnCar.trim();
        }
        return LocPosition.createLocPosition(inRailcarVisit, inPosOnCar, inDoorDirection);
    }

    public static LocPosition createTrainPosition(ILocation inTrainVisit, String inCarId, String inPosOnCar, String inDoorDirection) {
        LocPosition.verifyMode(inTrainVisit, LocTypeEnum.TRAIN);
        if (inPosOnCar != null) {
            inPosOnCar = inPosOnCar.trim();
        }
        if (inCarId != null) {
            inCarId = inCarId.trim();
        }
        if (inCarId != null && !inCarId.isEmpty() && inPosOnCar != null && !inPosOnCar.isEmpty()) {
            inCarId = inCarId + TIER_DELIMITER + inPosOnCar;
        }
        return LocPosition.createLocPosition(inTrainVisit, inCarId, inDoorDirection);
    }

    public static LocPosition createContainerizedPosition(String inUnitId, Long inUnitGkey) {
        return new LocPosition(LocTypeEnum.CONTAINER, inUnitId, inUnitGkey, null, null, null);
    }

    private static void verifyMode(ILocation inCv, LocTypeEnum inRequiredMode) {
        if (inCv != null && inCv.getLocType() != inRequiredMode) {
            throw new IllegalArgumentException("invalid carrier <" + inCv + ">, expected mode is <" + (Object)((Object)inRequiredMode) + '>');
        }
    }

    public static LocPosition createLocPosition(ILocation inCv, @Nullable String inSlot, @Nullable String inOrientation) {
        return LocPosition.createLocPosition(inCv.getLocType(), inCv.getLocId(), inCv.getLocGkey(), inSlot, inOrientation);
    }

    public static LocPosition createLocPosition(ILocation inCv, @Nullable String inSlot, @Nullable String inOrientation, @Nullable String inSlotOnCarriage) {
        return LocPosition.createLocPosition(inCv.getLocType(), inCv.getLocId(), inCv.getLocGkey(), inSlot, inOrientation, inSlotOnCarriage);
    }

    public static LocPosition createLocPosition(LocTypeEnum inLocType, String inLocId, Long inLocGkey, String inSlot, String inOrientation) {
        return new LocPosition(inLocType, inLocId, inLocGkey, inSlot, inOrientation, null);
    }

    public static LocPosition createLocPosition(LocTypeEnum inLocType, String inLocId, Long inLocGkey, String inSlot, String inOrientation, String inSlotOnCarriage) {
        return new LocPosition(inLocType, inLocId, inLocGkey, inSlot, inOrientation, inSlotOnCarriage);
    }

    public static LocPosition getUnknownPosition() {
        return UNKNOWN;
    }

    public static String formPosChangeString(String inUnitId, LocPosition inOldPos, LocPosition inNewPos) {
        return "Unit: " + inUnitId + ", from: " + (Object)((Object)inOldPos) + ", to: " + (Object)((Object)inNewPos);
    }

    @Nullable
    public CarrierVisit resolveInboundCarrierVisit() {
        return this.resolveCarrierVisit(true);
    }

    @Nullable
    public CarrierVisit resolveOutboundCarrierVisit() {
        return this.resolveCarrierVisit(false);
    }

    @Nullable
    private CarrierVisit resolveCarrierVisit(boolean inWantInbound) {
        if (this.isCarrierPosition() && this.getPosLocGkey() != null) {
//            ILocationLoader cl = (ILocationLoader)Roastery.getBean((String)"carrierLoader");
//            ILocation cv = cl.loadCarrierByGkey(this.getPosLocType(), this.getPosLocGkey());
//            if (inWantInbound) {
//                return cv.getInboundCv();
//            }
//            return cv.getOutboundCv();
        }
        return null;
    }

    @Nullable
    public Yard resolveYard() {
        if (this.isYardPosition()) {
            return Yard.loadByGkey(this.getPosLocGkey());
        }
        return null;
    }

    public Facility resolveFacility() {
        if (this.resolveLocation() != null) {
            return this.resolveLocation().getLocFacility();
        }
        return null;
    }

    public ILocation resolveLocation() {
//        ILocationLoader cl = (ILocationLoader)Roastery.getBean((String)"carrierLoader");
//        return cl.loadCarrierByGkey(this.getPosLocType(), this.getPosLocGkey());
        return null;
    }

    public boolean isYardPosition() {
        return LocTypeEnum.YARD.equals((Object)this.getPosLocType());
    }

    public boolean isCarrierPosition() {
        return this.getPosLocType() == LocTypeEnum.VESSEL || this.getPosLocType() == LocTypeEnum.TRUCK || this.getPosLocType() == LocTypeEnum.RAILCAR || this.getPosLocType() == LocTypeEnum.TRAIN || this.getPosLocType() == LocTypeEnum.CONTAINER;
    }

    public String toString() {
        return this.getPosName();
    }

    public boolean isSamePosition(@Nullable LocPosition inOtherPosition) {
        if (!this.isSameLocation(inOtherPosition)) {
            return false;
        }
        if (!StringUtils.equals((String)this.getPosSlot(), (String)inOtherPosition.getPosSlot())) {
            LOGGER.trace((Object)(this.toString() + " does not have the same pos slot as " + (Object)((Object)inOtherPosition) + " [this=" + this.getPosSlot() + ", other=" + inOtherPosition.getPosSlot() + "]"));
            return false;
        }
        LocTypeEnum locTypeEnum = this.getPosLocType();
        if (locTypeEnum != null && locTypeEnum.equals((Object)LocTypeEnum.TRUCK)) {
            if (!StringUtils.equals((String)this.getPosOperationalPosId(), (String)inOtherPosition.getPosOperationalPosId())) {
                LOGGER.trace((Object)(this.toString() + " does not have the same operational pos id as " + (Object)((Object)inOtherPosition) + " [loc_type is TRUCK; this=" + this.getPosOperationalPosId() + ", other=" + inOtherPosition.getPosOperationalPosId() + "]"));
                return false;
            }
        } else if (this.getPosOperationalPosId() != null && inOtherPosition.getPosOperationalPosId() != null && !StringUtils.equals((String)this.getPosOperationalPosId(), (String)inOtherPosition.getPosOperationalPosId())) {
            LOGGER.trace((Object)(this.toString() + " does not have the same operational pos id as " + (Object)((Object)inOtherPosition) + " [loc_type not TRUCK; this=" + this.getPosOperationalPosId() + ", other=" + inOtherPosition.getPosOperationalPosId() + "]"));
            return false;
        }
        return true;
    }

    public boolean isSamePositionAndOrientation(LocPosition inOtherPosition) {
        return this.isSamePosition(inOtherPosition) && this.isSameOrientation(inOtherPosition);
    }

    public boolean isSamePositionAndSlotOnCarriage(LocPosition inOtherPosition) {
        if (this.isSamePositionAndOrientation(inOtherPosition)) {
            if ((ArgoUtils.isValidPosSlotOnCarriage(this.getPosSlotOnCarriage()) || ArgoUtils.isValidPosSlotOnCarriage(inOtherPosition.getPosSlotOnCarriage())) && !StringUtils.equals((String)this.getPosSlotOnCarriage(), (String)inOtherPosition.getPosSlotOnCarriage())) {
                LOGGER.trace((Object)(this.toString() + " does not have the same slot on carriage as " + (Object)((Object)inOtherPosition) + " [this=" + this.getPosSlotOnCarriage() + ", other=" + inOtherPosition.getPosSlotOnCarriage() + "]"));
                return false;
            }
            return true;
        }
        return false;
    }

    public boolean isSameOrientation(LocPosition inOtherPosition) {
        return inOtherPosition != null && StringUtils.equals((String)(StringUtils.isEmpty((String)inOtherPosition.getPosOrientation()) ? "" : inOtherPosition.getPosOrientation()), (String)(StringUtils.isEmpty((String)this.getPosOrientation()) ? "" : this.getPosOrientation()));
    }

    public boolean isSameLocation(@Nullable LocPosition inOtherPosition) {
        if (inOtherPosition == null) {
            LOGGER.trace((Object)(this.toString() + " does not have the same location as another, because the other position is null"));
            return false;
        }
        if (this.getPosLocType() != inOtherPosition.getPosLocType()) {
            LOGGER.trace((Object)(this.toString() + " does not have the same location as " + (Object)((Object)inOtherPosition) + ", because the loc types are different [this=" + (Object)((Object)this.getPosLocType()) + ", other=" + (Object)((Object)inOtherPosition.getPosLocType()) + "]"));
            return false;
        }
        if (!StringUtils.equals((String)this.getPosLocId(), (String)inOtherPosition.getPosLocId())) {
            LOGGER.trace((Object)(this.toString() + " does not have the same location as " + (Object)((Object)inOtherPosition) + ", because the loc ids are different [this=" + this.getPosLocId() + ", other=" + inOtherPosition.getPosLocId() + "]"));
            return false;
        }
        if (this.getPosLocGkey() == null) {
            LOGGER.trace((Object)(this.toString() + " does not have the same location as " + (Object)((Object)inOtherPosition) + ", because this loc gkey is null"));
            return inOtherPosition.getPosLocGkey() == null;
        }
        if (!this.getPosLocGkey().equals(inOtherPosition.getPosLocGkey())) {
            LOGGER.trace((Object)(this.toString() + " does not have the same location as " + (Object)((Object)inOtherPosition) + ", because the loc gkeys are different [this=" + this.getPosLocGkey() + ", other=" + inOtherPosition.getPosLocGkey() + "]"));
            return false;
        }
        return true;
    }

    public boolean isSameBlock(LocPosition inOtherPosition) {
        if (!this.isSameLocation(inOtherPosition)) {
            return false;
        }
        if (this.getPosBin() == null) {
            LOGGER.trace((Object)(this.toString() + " does not have the same location as " + (Object)((Object)inOtherPosition) + ", because this pos bin is null"));
            return false;
        }
        if (inOtherPosition.getPosBin() == null) {
            LOGGER.trace((Object)(this.toString() + " does not have the same location as " + (Object)((Object)inOtherPosition) + ", because the other location pos bin is null"));
            return false;
        }
        AbstractBin posBlock = this.getPosBin().findAncestorBinAtLevel(Long.valueOf(2L));
        AbstractBin otherPosBlock = inOtherPosition.getPosBin().findAncestorBinAtLevel(Long.valueOf(2L));
        return posBlock != null && otherPosBlock != null && posBlock.equals((Object)otherPosBlock);
    }

    public boolean isCorePositionChanged(LocPosition inOtherPosition) {
        if (LocTypeEnum.TRUCK.equals((Object)inOtherPosition.getPosLocType())) {
            return !this.isSameLocation(inOtherPosition);
        }
        if (this.isSameLocation(inOtherPosition)) {
            if (this.getPosSlot() == null) {
                return inOtherPosition.getPosSlot() != null;
            }
            return !this.getPosSlot().equals(inOtherPosition.getPosSlot());
        }
        return true;
    }

    public boolean isIndividualPositionChanged(LocPosition inLocPosition) {
        boolean changed = false;
//        changed = this.getPosAnchor() != null ? (changed |= !this.getPosAnchor().equals((Geometry)inLocPosition.getPosAnchor())) : (changed |= inLocPosition.getPosAnchor() != null);
        changed = !changed && this.getPosBin() != null ? (changed |= !this.getPosBin().equals((Object)inLocPosition.getPosBin())) : (changed |= inLocPosition.getPosBin() != null);
        changed = !changed && this.getPosOrientation() != null ? (changed |= !this.getPosOrientation().equals(inLocPosition.getPosOrientation())) : (changed |= inLocPosition.getPosOrientation() != null);
        changed = !changed && this.getPosOrientationDegrees() != null ? (changed |= !this.getPosOrientationDegrees().equals(inLocPosition.getPosOrientationDegrees())) : (changed |= inLocPosition.getPosOrientationDegrees() != null);
        changed = !changed && this.getPosTier() != null ? (changed |= !this.getPosTier().equals(inLocPosition.getPosTier())) : (changed |= inLocPosition.getPosTier() != null);
        changed = !changed && this.getPosSlotOnCarriage() != null ? (changed |= !this.getPosSlotOnCarriage().equals(inLocPosition.getPosSlotOnCarriage())) : (changed |= inLocPosition.getPosSlotOnCarriage() != null);
        return changed;
    }

    public boolean isTruckPosition() {
        return LocTypeEnum.TRUCK.equals((Object)this.getPosLocType());
    }

    public boolean isTruckPositionInExchangeLane() {
        return LocTypeEnum.TRUCK.equals((Object)this.getPosLocType()) && this.getPosBin() != null;
    }

    public boolean isRailPosition() {
        return LocTypeEnum.TRAIN.equals((Object)this.getPosLocType()) || LocTypeEnum.RAILCAR.equals((Object)this.getPosLocType());
    }

    public boolean isVesselPosition() {
        return LocTypeEnum.VESSEL.equals((Object)this.getPosLocType());
    }

    public boolean isContainerPosition() {
        return LocTypeEnum.CONTAINER.equals((Object)this.getPosLocType());
    }

    public boolean equals(Object inOther) {
        return inOther instanceof LocPosition && this.isSamePositionAndSlotOnCarriage((LocPosition)((Object)inOther));
    }

    public int hashCode() {
        String s = this.getPosLocId() + this.getPosSlot();
        return s.hashCode();
    }

    public LocTypeEnum getPosLocType() {
        LocTypeEnum result = this._posLocType;
        if (this._posLocType == null) {
            LOGGER.error((Object)("Position LocType should never be null. location=" + (Object)((Object)this)));
        }
        return result;
    }

    public void setPosLocType(LocTypeEnum inPosLocType) {
        if (inPosLocType == null) {
            LOGGER.error((Object)("Position LocType should never be null. location=" + (Object)((Object)this)));
        }
        this._posLocType = inPosLocType;
    }

    public String getPosLocId() {
        return this._posLocId;
    }

    public void setPosLocId(String inPosLocId) {
        this._posLocId = inPosLocId;
    }

    public Long getPosLocGkey() {
        return this._posLocGkey;
    }

    public void setPosLocGkey(Long inPosLocGkey) {
        this._posLocGkey = inPosLocGkey;
    }

    public String getPosSlot() {
        return super.getPosSlot();
    }

    public void setPosSlot(String inPosSlot) {
        super.setPosSlot(inPosSlot);
    }

    public String getPosOrientation() {
        return super.getPosOrientation();
    }

    public void setPosOrientation(String inPosOrientation) {
        super.setPosOrientation(inPosOrientation);
    }

    public String getPosName() {
        return super.getPosName();
    }

    public void setPosName(String inPosName) {
        super.setPosName(inPosName);
    }

    public String getPosOperationalPosId() {
        return super.getPosOperationalPosId();
    }

    public void setPosOperationalPosId(String inPosOperationalPosId) {
        super.setPosOperationalPosId(inPosOperationalPosId);
    }

    public Long getPosTier() {
        return super.getPosTier();
    }

    public void setPosTier(Long inPosTier) {
        super.setPosTier(inPosTier);
    }

    public void setPosBin(AbstractBin inPosBin) {
        super.setPosBin(inPosBin);
    }

    public Point getPosAnchor() {
        return super.getPosAnchor();
    }

    public void setPosAnchor(Point inAnchor) {
        super.setPosAnchor(inAnchor);
    }

    public Double getPosOrientationDegrees() {
        return super.getPosOrientationDegrees();
    }

    public void setPosOrientationDegrees(Double inOrientationDegrees) {
        super.setPosOrientationDegrees(inOrientationDegrees);
    }

    public String getPosSlotOnCarriage() {
        return super.getPosSlotOnCarriage();
    }

    public void setPosSlotOnCarriage(@Nullable String inPosSlotOnCarriage) {
        super.setPosSlotOnCarriage(inPosSlotOnCarriage);
    }

    public boolean isWheeled() {
        AbstractBlock block;
        AbstractBin bin;
        if (this.isYardPosition() && (bin = this.getPosBin()) != null && (block = BinModelHelper.getBlockFromModelBin((AbstractBin)bin)) != null) {
            return LocPosition.getYardUtils().isBlockWheeledBlock(block);
        }
        return false;
    }

    public boolean isParallelBuffer() {
        AbstractBin bin;
        if (this.isYardPosition() && (bin = this.getPosBin()) != null) {
            return LocPosition.getYardUtils().isBinWithinBlockOfParallelBuffers(bin);
        }
        return false;
    }

    public boolean isWheeledHeap() {
        if (this.getPosSlot() != null) {
            return LocPosition.getYardUtils().isBlockWheeledBasedOnBlockCode(this.getPosSlot().trim());
        }
        return false;
    }

    public boolean isGrounded() {
        AbstractBlock block;
        AbstractBin bin;
        if (this.isYardPosition() && (bin = this.getPosBin()) != null && (block = BinModelHelper.getBlockFromModelBin((AbstractBin)bin)) != null) {
            return LocPosition.getYardUtils().isBlockGroundedBlock(block);
        }
        return false;
    }

    public boolean isApron() {
        AbstractBlock block;
        AbstractBin bin;
        if (this.isYardPosition() && (bin = this.getPosBin()) != null && (block = BinModelHelper.getBlockFromModelBin((AbstractBin)bin)) != null) {
            return LocPosition.getYardUtils().isBlockApron(block);
        }
        return false;
    }

    public boolean isBackReach() {
        AbstractBlock block;
        AbstractBin bin;
        if (this.isYardPosition() && (bin = this.getPosBin()) != null && (block = BinModelHelper.getBlockFromModelBin((AbstractBin)bin)) != null) {
            return LocPosition.getYardUtils().isBlockQcBackReach((AbstractBin)block);
        }
        return false;
    }

    public boolean isFailToDeck() {
        AbstractBin bin;
        if (this.isYardPosition() && (bin = this.getPosBin()) != null) {
            return LocPosition.getYardUtils().isBlockFailToDeck(bin);
        }
        return false;
    }

    public boolean isEQPC() {
        AbstractBin bin;
        if (this.isYardPosition() && (bin = this.getPosBin()) != null) {
            return LocPosition.getYardUtils().isBlockEQPC(bin);
        }
        return false;
    }

    public void synchronizePosNameWithBin(EquipBasicLengthEnum inCntrLength) {
        AbstractBin posBin = this.getPosBin();
        if (posBin != null) {
            if (BinModelHelper.getBlockFromModelBin((AbstractBin)posBin) != null) {
                String tierName = posBin.getTierName(this.getPosTier());
                String binName = EquipBasicLengthEnum.BASIC20.equals((Object)inCntrLength) ? posBin.getAbnName() : posBin.getAbnNameAlt();
                this.setPosSlot(binName + TIER_DELIMITER + tierName);
                String formattedYardSlotName = posBin.getUiFullPositionWithTier(LocPosition.getBinNameTypeEnumFromEquipBasicLengthEnum(inCntrLength), tierName);
                this.updatePosName(this.getPosLocType(), this.getPosLocId(), formattedYardSlotName);
            } else {
                this.setPosSlot(posBin.getAbnName());
                this.updatePosName(this.getPosLocType(), this.getPosLocId(), posBin.getAbnName());
            }
        }
    }

    public boolean isProtectedYardPosition() {
        IArgoYardUtils yardUtils;
        AbstractBin yardBin;
        if ((this.isYardPosition() || this.isTruckPositionInExchangeLane()) && (yardBin = this.getPosBin()) != null && (yardUtils = LocPosition.getYardUtils()) != null) {
            return yardUtils.isBinProtected(yardBin);
        }
        return false;
    }

    public boolean isProtectedYardPositionForInboundTruckMoveCancellation() {
        IArgoYardUtils yardUtils;
        AbstractBin yardBin;
        if (this.isYardPosition() && (yardBin = this.getPosBin()) != null && (yardUtils = LocPosition.getYardUtils()) != null) {
            return yardUtils.isBinProtected(yardBin);
        }
        return false;
    }

    @Nullable
    private static String[] getVesselCoordinates(@NotNull String inLocSlot) {
        if (inLocSlot.indexOf(46) > 0) {
            String[] components = inLocSlot.split("\\.");
            if (components == null || components.length == 0) {
                LOGGER.error((Object) String.format("Failed to get the bay position of slot string >%s<", inLocSlot));
                return null;
            }
            if (components.length == 3) {
                return new String[]{LocPosition.getVesselDeck(components[2]), components[0], components[1], components[2]};
            }
            inLocSlot = StringUtils.remove((String)inLocSlot, (char)'.');
        }
        switch (inLocSlot.length()) {
            case 4: {
                return new String[]{"", inLocSlot.substring(0, 2), inLocSlot.substring(2, 4), ""};
            }
            case 6: {
                return new String[]{LocPosition.getVesselDeck(inLocSlot.substring(4, 6)), inLocSlot.substring(0, 2), inLocSlot.substring(2, 4), inLocSlot.substring(4, 6)};
            }
            case 5: {
                return new String[]{LocPosition.getVesselDeck(inLocSlot.substring(3, 5)), inLocSlot.substring(0, 1), inLocSlot.substring(1, 3), inLocSlot.substring(3, 5)};
            }
        }
        if (inLocSlot.length() >= 7) {
            return new String[]{LocPosition.getVesselDeck(inLocSlot.substring(5, 7)), inLocSlot.substring(0, 3), inLocSlot.substring(3, 5), inLocSlot.substring(5, 7)};
        }
        if (inLocSlot.length() >= 1) {
            return new String[]{"", inLocSlot.substring(0, 1), "", ""};
        }
        LOGGER.error((Object) String.format("Completely failed to get the vessel position of slot string >%s<", inLocSlot));
        return null;
    }

    @NotNull
    private static String getVesselDeck(String inTierName) {
        String deckName;
        block2: {
            deckName = "";
            try {
                int tierValue = Integer.parseInt(inTierName);
                deckName = tierValue >= 80 ? "A" : "B";
            }
            catch (NumberFormatException e) {
                if (!LOGGER.isDebugEnabled()) break block2;
                LOGGER.debug((Object) String.format("Vessel tier coordinate >%s< is not an integer value message %s", inTierName, e.getMessage()));
            }
        }
        return deckName;
    }

    public boolean isRampBin() {
        if (!this.isYardPosition()) {
            return false;
        }
        return RAMP_ID.equals(this.getPosSlot());
    }

    public static boolean isApron(String inSlot) {
        AbstractBlock block;
        AbstractBin bin;
        AbstractBin yardModel;
        Yard yard;
        if (inSlot != null && !inSlot.isEmpty() && (yard = ContextHelper.getThreadYard()) != null && (yardModel = yard.getYrdBinModel()) != null && (bin = yardModel.findDescendantBinFromInternalSlotString(inSlot, null)) != null && (block = BinModelHelper.getBlockFromModelBin((AbstractBin)bin)) != null) {
            return LocPosition.getYardUtils().isBlockApron(block);
        }
        return false;
    }

    public static boolean isApronBlock(AbstractBlock inBlock) {
        boolean isApronBlock = false;
        if (inBlock != null) {
            return LocPosition.getYardUtils().isBlockApron(inBlock);
        }
        return isApronBlock;
    }

    public static boolean isHeapBlock(AbstractBlock inBlock) {
        boolean isHeapArea = false;
        if (inBlock != null) {
            return LocPosition.getYardUtils().isBlockHeap(inBlock);
        }
        return isHeapArea;
    }

    public boolean isLandsideTransferPoint() {
        boolean isLstp = false;
        if (LocTypeEnum.YARD.equals((Object)this.getPosLocType()) && this.getPosBin() != null) {
            isLstp = LocPosition.getYardUtils().isStackLandsideTransferPoint(this.getPosBin());
        }
        return isLstp;
    }

    public boolean isWatersideTransferPoint() {
        boolean isWstp = false;
        if (LocTypeEnum.YARD.equals((Object)this.getPosLocType()) && this.getPosBin() != null) {
            isWstp = LocPosition.getYardUtils().isStackWatersideTransferPoint(this.getPosBin());
        }
        return isWstp;
    }

    public boolean isCantileverTransferPoint() {
        boolean isCltp = false;
        if (LocTypeEnum.YARD.equals((Object)this.getPosLocType()) && this.getPosBin() != null) {
            isCltp = LocPosition.getYardUtils().isStackCantileverTransferPoint(this.getPosBin());
        }
        return isCltp;
    }

    public boolean isRailTransferPoint() {
        boolean isRctp = false;
        if (LocTypeEnum.YARD.equals((Object)this.getPosLocType()) && this.getPosBin() != null) {
            isRctp = LocPosition.getYardUtils().isStackRailTransferPoint(this.getPosBin());
        }
        return isRctp;
    }

    public boolean isStradExchangeGrid() {
        AbstractBlock block;
        AbstractBin bin;
        if (LocTypeEnum.YARD.equals((Object)this._posLocType) && (bin = this.getPosBin()) != null && (block = BinModelHelper.getBlockFromModelBin((AbstractBin)bin)) != null) {
            return LocPosition.getYardUtils().isBlockStraddleGridBlock(block);
        }
        return false;
    }

    public boolean isRack() {
        Yard yard = ContextHelper.getThreadYard();
        if (yard == null) {
            String complaint = String.format("no thread yard available; can't determine isRack(%s)", this.toString());
            LOGGER.error((Object)complaint);
            throw BizFailure.createProgrammingFailure((String)complaint);
        }
        if (!LocTypeEnum.YARD.equals((Object)this._posLocType) || this.getPosBin() == null) {
            return false;
        }
//        String stackStatusChar = StackStatus.findStackStatusChar(this.getPosBin(), yard.getYrdGkey());
//        return StackStatusUtils.isTpTransferTypeRack(stackStatusChar);
        return false;
    }

    public boolean isAsynchronousTp() {
        Yard yard = ContextHelper.getThreadYard();
        if (yard == null) {
            String complaint = String.format("no thread yard available; can't determine isGroundedTp(%s)", this.toString());
            LOGGER.error((Object)complaint);
            throw BizFailure.createProgrammingFailure((String)complaint);
        }
        if (!LocTypeEnum.YARD.equals((Object)this._posLocType) || this.getPosBin() == null) {
            return false;
        }
        if (!this.isTransferZone()) {
            return false;
        }
//        String stackStatusChar = StackStatus.findStackStatusChar(this.getPosBin(), yard.getYrdGkey());
//        return StackStatusUtils.isTpTransferTypeAsync(stackStatusChar);
        return false;
    }

    public static EquipBasicLengthEnum getEquipBasicLengthEnumFromBinNameTypeEnum(BinNameTypeEnum inBinNameTypeEnum) {
        if (BinNameTypeEnum.STANDARD.equals((Object)inBinNameTypeEnum)) {
            return EquipBasicLengthEnum.BASIC20;
        }
        return EquipBasicLengthEnum.BASIC40;
    }

    public static BinNameTypeEnum getBinNameTypeEnumFromEquipBasicLengthEnum(EquipBasicLengthEnum inEquipBasicLengthEnum) {
        if (EquipBasicLengthEnum.BASIC20.equals((Object)inEquipBasicLengthEnum)) {
            return BinNameTypeEnum.STANDARD;
        }
        return BinNameTypeEnum.ALTERNATE;
    }

    public boolean isQcPlatformPosition() {
        AbstractBin bin = this.getPosBin();
        if (bin == null) {
            LOGGER.debug((Object)"bin is null for");
            return false;
        }
        AbstractBin block = bin.findAncestorBinAtLevel(Long.valueOf(2L));
        return LocPosition.getYardUtils().isBlockQuayCranePlatform(block);
    }

    public boolean isQcTransferZonePosition() {
        AbstractBlock block;
        AbstractBin bin;
        if (this.isYardPosition() && (bin = this.getPosBin()) != null && (block = BinModelHelper.getBlockFromModelBin((AbstractBin)bin)) != null) {
            return LocPosition.getYardUtils().isBlockQuayCraneTransferZone((AbstractBin)block);
        }
        return false;
    }

    public boolean isQcAccessiblePosition() {
        return this.isVesselPosition() || this.isQcPlatformPosition() || this.isQcTransferZonePosition() || this.isApron();
    }

    private static IArgoYardUtils getYardUtils() {
        return (IArgoYardUtils)Roastery.getBean((String)"argoYardUtils");
    }

//    private static ArgoRailManager getRailUtils() {
//        return (ArgoRailManager) Roastery.getBean((String)"argoRailManager");
//    }

    public static String getPhonyYardSlotId() {
        return SKIP_YARD_BLOCK_NAME;
    }

    public boolean isPhonyYardPosition() {
        return SKIP_YARD_BLOCK_NAME.equals(this.getPosSlot());
    }

    public String getBlockName() {
        AbstractBlock block;
        String blockName = null;
        if (this.getPosBin() != null && (block = BinModelHelper.getBlockFromModelBin((AbstractBin)this.getPosBin())) != null) {
            blockName = block.getBlockName();
        }
        return blockName;
    }

    public boolean requiresTransferZoneAccess() {
        boolean required = false;
        if (this.getPosBin() != null) {
            required = LocPosition.getYardUtils().hasTransferZoneAssociation(this.getPosBin());
        }
        return required;
    }

    public boolean isTransferZone() {
        boolean isTransferZone = false;
        if ((LocTypeEnum.YARD.equals((Object)this.getPosLocType()) || this.isTruckPositionInExchangeLane()) && this.getPosBin() != null) {
            AbstractBin block = this.getPosBin().findAncestorBinAtLevel(Long.valueOf(2L));
            isTransferZone = LocPosition.getYardUtils().isBlockTransferZone(block);
        }
        return isTransferZone;
    }

    public static void checkPosition(Yard inYard, String inYardSlot) throws BizViolation {
        String binName = LocPosition.getBinNameFromSlotName(inYardSlot);
        String tierName = LocPosition.getTierNameFromSlotName(inYardSlot);
        AbstractBin yardModel = inYard.getYrdBinModel();
        AbstractBin bin = yardModel.findDescendantBinFromInternalSlotString(binName, BinContext.findBinContext((String)"STOWAGE_CONTAINERS"));
        if (bin == null) {
            throw BizViolation.create((IPropertyKey) IArgoPropertyKeys.INVALID_YARD_BIN, null, (Object)inYardSlot, (Object)inYard.getYrdName());
        }
        AbstractBlock block = BinModelHelper.getBlockFromModelBin((AbstractBin)bin);
        if (StringUtils.isNotEmpty((String)tierName) && block != null && !block.isValidTierName(tierName)) {
            String blockName = block.getUiFullPositionWithTier(BinNameTypeEnum.STANDARD, null);
            throw BizViolation.create((IPropertyKey) IArgoPropertyKeys.INVALID_TIER, null, (Object)tierName, (Object)blockName, (Object)inYard.getYrdName());
        }
    }

    public ValueObject getValueObject() {
        ValueObject vao = new ValueObject("LocPosition");
        vao.setEntityPrimaryKey(null);
        vao.setFieldValue(IArgoField.POS_LOC_TYPE, (Object)this._posLocType);
        vao.setFieldValue(IArgoField.POS_LOC_ID, (Object)this._posLocId);
        vao.setFieldValue(IArgoField.POS_LOC_GKEY, (Object)this._posLocGkey);
        vao.setFieldValue(IArgoField.POS_SLOT, (Object)this.getPosSlot());
        vao.setFieldValue(IArgoField.POS_TIER, (Object)this.getPosTier());
        vao.setFieldValue(IArgoField.POS_ORIENTATION, (Object)this.getPosOrientation());
        vao.setFieldValue(IArgoField.POS_ORIENTATION_DEGREES, (Object)this.getPosOrientationDegrees());
        vao.setFieldValue(IArgoField.POS_NAME, (Object)this.getPosName());
        vao.setFieldValue(IArgoBizMetafield.LOC_POSITION_BIN_GKEY, (Object)this._posLocGkey);
        return vao;
    }

    public boolean isXray() {
        if (this.isTransferZone()) {
            return LocPosition.getYardUtils().isBlockXray(this.getPosBin().findAncestorBinAtLevel(Long.valueOf(2L)));
        }
        return false;
    }

    public boolean isMaintenance() {
        if (this.isTransferZone()) {
            return LocPosition.getYardUtils().isBlockMaintenance(this.getPosBin().findAncestorBinAtLevel(Long.valueOf(2L)));
        }
        return false;
    }

    public boolean isRecharge() {
        if (this.isTransferZone()) {
            return LocPosition.getYardUtils().isBlockRecharge(this.getPosBin().findAncestorBinAtLevel(Long.valueOf(2L)));
        }
        return false;
    }

    public boolean isPosFirstTier() {
        AbstractBin posBin = this.getPosBin();
        Long tier = this.getPosTier();
        return posBin != null && tier != null && (tier < posBin.getAbnZIndexMin() || tier.equals(posBin.getAbnZIndexMin()));
    }

    public boolean isTransferZoneWithAssociations() {
        return this.isTransferZone() && this.requiresTransferZoneAccess();
    }

    public static LocPosition copyUpdatePosition(LocPosition inCurrPosition, LocPosition inNewPosition, boolean inUseIndividualFields, boolean inHasCarriageinCombo, boolean inIsAllFieldsClone) throws BizViolation {
        LocPosition newPosition = LocPosition.createLocPosition(inNewPosition.getPosLocType(), inNewPosition.getPosLocId(), inNewPosition.getPosLocGkey(), inNewPosition.getPosSlot(), null);
        newPosition.setPosName(inNewPosition.getPosName());
        if (inUseIndividualFields) {
            if (inNewPosition.getPosAnchor() != null && inIsAllFieldsClone) {
                newPosition.setPosAnchor(inNewPosition.getPosAnchor());
            } else if (inCurrPosition != null && inHasCarriageinCombo) {
                newPosition.setPosAnchor(inCurrPosition.getPosAnchor());
            }
            if (inNewPosition.getPosBin() != null && inIsAllFieldsClone) {
                newPosition.setPosBin(inNewPosition.getPosBin());
            } else if (inCurrPosition != null && inHasCarriageinCombo) {
                newPosition.setPosBin(inCurrPosition.getPosBin());
            }
            if (inNewPosition.getPosOrientation() != null && inIsAllFieldsClone) {
                newPosition.setPosOrientation(inNewPosition.getPosOrientation());
            } else if (inCurrPosition != null && inHasCarriageinCombo) {
                newPosition.setPosOrientation(inCurrPosition.getPosOrientation());
            }
            if (inNewPosition.getPosOrientationDegrees() != null && inIsAllFieldsClone) {
                newPosition.setPosOrientationDegrees(inNewPosition.getPosOrientationDegrees());
            } else if (inCurrPosition != null && inHasCarriageinCombo) {
                newPosition.setPosOrientationDegrees(inCurrPosition.getPosOrientationDegrees());
            }
            if (inNewPosition.getPosTier() != null && inIsAllFieldsClone) {
                newPosition.setPosTier(inNewPosition.getPosTier());
            } else if (inCurrPosition != null && inHasCarriageinCombo) {
                newPosition.setPosTier(inCurrPosition.getPosTier());
            }
            if (inNewPosition.getPosSlotOnCarriage() != null && inIsAllFieldsClone) {
                newPosition.updatePosSlotOnCarriage(inNewPosition.getPosSlotOnCarriage());
            } else if (inCurrPosition != null && inHasCarriageinCombo) {
                newPosition.updatePosSlotOnCarriage(inCurrPosition.getPosSlotOnCarriage());
            }
        }
        return newPosition;
    }

    public boolean hasValidSlotPositionOnCarriage() {
//        return !Strings.isNullOrEmpty((String)this.getPosSlotOnCarriage()) && !SLOT_POS_ON_CARRIAGE_ZERO.equals(this.getPosSlotOnCarriage());
        return false;
    }

    private static void validateAnchorPointAndLogMessage(AbstractBin inBin, Point inAnchorPoint, double inRailcarCentroidOffset, double inRailTrackLength) {
        if (inAnchorPoint == null) {
            LOGGER.warn((Object)"LocPosition Anchor Pos is null.");
        }
//        else if (inAnchorPoint.isEmpty()) {
//            LOGGER.warn((Object)String.format("LocPosition Anchor Pos geometry is empty. Rail Track Center Line Geometry[%s]. RailcarCentroid Offset[%s] in cm. RailTrack Length[%s] in cm", new Object[]{inBin.getAbnCenterline(), inRailcarCentroidOffset, inRailTrackLength}));
//        }
    }

    public String buildPosName() {
        return this.buildInternalPosName(this.getPosLocType(), this.getPosLocId(), this.getPosSlot(), null, null);
    }

    private String buildPositionName() {
        return this.buildInternalPosName(this.getPosLocType(), this.getPosLocId(), this.getPosSlot(), this.getPosOrientation(), this.getPosOperationalPosId());
    }

    private String buildInternalPosName(LocTypeEnum inLocType, String inLocId, String inSlot, String inOrientation, String inOperationalPosId) {
        StringBuilder posName = new StringBuilder();
        posName.append(LocPosition.getPositionPrefixChar(inLocType));
        if (inLocId != null && StringUtils.isNotEmpty((String)inLocId)) {
            posName.append('-').append(inLocId);
            if (StringUtils.isNotEmpty((String)inSlot)) {
                posName.append('-').append(inSlot);
            }
            if (inOrientation != null && !inOrientation.isEmpty()) {
                posName.append('-').append(inOrientation);
            }
            if (inOperationalPosId != null && !inOperationalPosId.isEmpty() && !inOperationalPosId.equals(inSlot)) {
                posName.append(" (").append(inOperationalPosId).append(')');
            }
        }
        return posName.toString();
    }
}
