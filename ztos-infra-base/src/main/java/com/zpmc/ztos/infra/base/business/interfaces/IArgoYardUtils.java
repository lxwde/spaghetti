package com.zpmc.ztos.infra.base.business.interfaces;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.business.enums.argo.CompassDirectionEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.EquipBasicLengthEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.SectionDoorDirectionEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.TransferLocationEnum;
import com.zpmc.ztos.infra.base.business.equipments.Che;
import com.zpmc.ztos.infra.base.business.model.AbstractBin;
import com.zpmc.ztos.infra.base.business.model.AbstractBlock;
import com.zpmc.ztos.infra.base.business.model.LocPosition;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.model.MoveKindAllowed;
import com.zpmc.ztos.infra.base.common.model.RefIDPosition;
import com.zpmc.ztos.infra.base.common.model.UserContext;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.awt.*;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IArgoYardUtils {
    public static final String BEAN_ID = "argoYardUtils";

    public void updateOrCreateYardModelFromXpsFiles(UserContext var1, Serializable var2) throws BizViolation;

    public Serializable findOrCreateYardModel(UserContext var1, Serializable var2) throws BizViolation;

    public void updateYardModel(UserContext var1, Serializable var2, Serializable var3);

    public boolean isBlockWheeledBlock(AbstractBlock var1);

    public boolean isBlockGroundedBlock(AbstractBlock var1);

    public boolean isBlockTransferZoneBlock(AbstractBin var1);

    public boolean isBlockGridBlock(AbstractBlock var1);

    public boolean isBlockLogicalBlock(AbstractBin var1);

    public boolean isBlockApron(AbstractBlock var1);

    public boolean isBlockFailToDeck(AbstractBin var1);

    public boolean isBlockHeap(AbstractBlock var1);

    public boolean isBlockEQPC(AbstractBin var1);

    public boolean isBlockIFTBin(AbstractBlock var1);

    public boolean isBlockStraddleBlock(AbstractBlock var1);

    public boolean isBlockStraddleGridBlock(AbstractBlock var1);

    public boolean isBinProtected(AbstractBin var1);

    public boolean isBlockPurposeTruckOrRelated(AbstractBin var1);

    public Boolean isBlockWheeledBasedOnBlockCode(String var1);

    public AbstractBin getPersistentYardBin(UserContext var1, String var2, EquipBasicLengthEnum var3) throws BizViolation;

    public String getFormattedInternalYardPosition(AbstractBlock var1, String var2, String var3);

    public String getFormattedInternalYardPositionFromRefID(@NotNull RefIDPosition var1);

    @Nullable
    public Point getXYPointFromGeoPoint(Double var1, Double var2);

    public double getDistanceAlongRailTrack(String var1, Point var2);

    public List<String> getYardStackPositions(UserContext var1, String var2, EquipBasicLengthEnum var3, Boolean var4) throws BizViolation;

    public List<String> getWheeledYardSectionPositions(UserContext var1, String var2, EquipBasicLengthEnum var3, Boolean var4, Boolean var5) throws BizViolation;

    public Map<String, List<Serializable>> findUfvsByPositionForGroundedYardInv(UserContext var1, List<String> var2, List<String> var3, @Nullable List<String> var4) throws BizViolation;

    public Map<String, Map<Serializable, String>> findUfvsByPositionForWheeledYardInv(UserContext var1, List<String> var2) throws BizViolation;

    @Nullable
    public IQueryResult findIdsForBlocksInYard(Serializable var1);

    public List<String> getRailTrackNamesInActiveYard();

    public AbstractBlock findGridBlockById(AbstractBin var1, String var2);

    public Set<AbstractBlock> findAllGridBlocks(AbstractBin var1);

    public IQueryResult findAllAscBlocks(Serializable var1);

    public IQueryResult findAllTransferZones(Serializable var1);

    public Set<AbstractBlock> findAllWheeledTransferZoneBlocks(AbstractBin var1);

    public Set<AbstractBlock> findAllTranstainerTransferZoneBlocks(AbstractBin var1);

    public AbstractBin findYardStackFromPositionId(AbstractBin var1, String var2);

    public String getBlockFullUiPositionFormat(AbstractBlock var1);

    public String getDefaultYardExchangeAreaFullPositionFormat();

    public boolean hasTransferZoneAssociation(AbstractBin var1);

    public boolean isBlockTransferZone(AbstractBin var1);

    public boolean isBlockQuayCranePlatform(AbstractBin var1);

    public boolean isBlockQuayCraneTransferZone(AbstractBin var1);

    public boolean isBlockQcBackReach(AbstractBin var1);

    public Serializable getQcPlatformBlockGkey(String var1) throws BizViolation;

    public Serializable getQcPlatformBlockGkey(@NotNull Che var1) throws BizViolation;

    public Serializable getQcTransferZoneBlockGkey(String var1) throws BizViolation;

    @NotNull
    public List<Serializable> getQcTransferZoneSectionGkeys(String var1) throws BizViolation;

    public Che getOwnerCheOfQuayCraneBin(AbstractBin var1);

    public boolean isBlockMaintenance(AbstractBin var1);

    public boolean isBlockRecharge(AbstractBin var1);

    public boolean isBlockXray(AbstractBin var1);

    public boolean isBlockOfParallelBuffers(AbstractBin var1);

    public boolean isStackBlockInvertY(AbstractBlock var1);

    public boolean isStackBlockInvertX(AbstractBlock var1);

    public boolean isBinWithinWatersideTransferZone(@NotNull AbstractBin var1);

    public boolean isBlockWatersideTransferZone(AbstractBin var1);

    public boolean isStackWatersideTransferPoint(AbstractBin var1);

    public boolean isStackCantileverTransferPoint(AbstractBin var1);

    public boolean isBinWithinBlockOfParallelBuffers(@NotNull AbstractBin var1);

    public boolean isBinWithinLandsideTransferZone(@NotNull AbstractBin var1);

    public boolean isBinChargingTp(@NotNull AbstractBin var1);

    public boolean isBlockLandsideTransferZone(AbstractBin var1);

    public boolean isStackLandsideTransferPoint(AbstractBin var1);

    public boolean isStackRailTransferPoint(AbstractBin var1);

    @Nullable
    public MoveKindAllowed findMoveKindAllowedForBin(AbstractBin var1);

    public TransferLocationEnum findTransferLocation(AbstractBin var1);

    @Nullable
    public CompassDirectionEnum getStackBlockDirection(AbstractBin var1);

    public Point getTrackSpotCentroid(String var1, String var2);

    public List<String> getRailPrestageBlocks(Serializable var1);

    public List<Serializable> getAllRailPrestageSlots(Serializable var1);

    public List<Serializable> getStackBlockSlots(Serializable var1);

    public Serializable getStackBlockGkey(String var1, AbstractBin var2);

    public String getBinNameFromSlotName(String var1);

    public String findNearestYardStack(Serializable[] var1, Point var2);

    public AbstractBin findRailTrack(String var1, Serializable var2);

    public Point getProjectedPointOnRailTrackForRailTzSlot(String var1, String var2, Serializable var3);

    public AbstractBin findRailTrackSpot(AbstractBin var1, String var2);

    public double getRailTrackStartOffset(Serializable var1);

    public double getTrackPlanMeterMarkOffset(Serializable var1);

   // public Map<String, AffineTransformation> getXYShiftAffineTransformation(Serializable var1, String var2);

    public Map<String, Point> getMapReferencePointsXYAndLongLatPoints(Serializable var1);

    public List<String> getAllTrackNamesInAlphanumericAscOrder(@NotNull Serializable var1);

    @Nullable
    public String getDefaultTrackName(@NotNull Serializable var1);

    public IQueryResult findNameforYardBlock(Serializable var1);

    @Nullable
    public SectionDoorDirectionEnum getYardSectionDirection(AbstractBin var1);

    public String getTrafficDirection(AbstractBin var1);

    public List<Serializable> getTransferZonesWithChargingStacks(@NotNull Serializable var1);

    public boolean arePositionsInDifferentBlocks(@NotNull LocPosition var1, @NotNull LocPosition var2);

    public boolean isYardBlock(AbstractBlock var1);
}
