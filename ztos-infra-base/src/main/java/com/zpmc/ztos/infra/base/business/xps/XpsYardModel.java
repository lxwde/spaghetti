package com.zpmc.ztos.infra.base.business.xps;

import com.google.common.base.Joiner;
import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.business.enums.argo.EquipBasicLengthEnum;
import com.zpmc.ztos.infra.base.business.enums.xps.TransferZoneKindEnum;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.common.model.ValueObject;

import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.util.*;

public class XpsYardModel extends AbstractParentXpsYardBin implements IXpsYardModel {
    private final String _yardCode;
    private final String _yardName;
    private boolean _yardContainRailTZ;
    private final Map _binIndexForBlocks;
    private final Map _binIndexForSections;
    private final Map _binIndexForStacks;
    private final IXpsYardBlock _unknownBlock;
    private final List<IXpsYardPoint> _yardPoints;
    private final IXpsTransferZoneAssociationManager _tzAssocManager;
    private final List<IXpsYardElevatedStack> _yardElevatedStacks;
    private final List<IXpsRailTrack> _railTracks;
  //  @Language(value="RegExp")
    private static final String USER_SEPARATION_REGEX = "[.-]";

    public XpsYardModel(String inCode, String inName) {
        super(null, 0, 0, 0);
        this._yardCode = inCode;
        this._yardName = inName;
        this._binIndexForStacks = new HashMap();
        this._binIndexForSections = new HashMap();
        this._binIndexForBlocks = new LinkedHashMap();
        this._unknownBlock = new UnknownXpsYardBlock();
        this._yardPoints = new ArrayList<IXpsYardPoint>();
        this._tzAssocManager = new XpsTransferZoneAssociationManager();
        this._yardElevatedStacks = new ArrayList<IXpsYardElevatedStack>();
        this._railTracks = new ArrayList<IXpsRailTrack>();
    }

    @Override
    @NotNull
    public String getYardCode() {
        return this._yardCode;
    }

    @Override
    @NotNull
    public String getYardName() {
        return this._yardName;
    }

    @Override
    public void computeBinNames(Map inUserConvTableMap) {
        this.setCoordinateName20(this._yardCode);
        this.setCoordinateName40(this._yardCode);
    }

    @Override
    public IXpsYardBlock getBlock() {
        return null;
    }

    @Override
    public String getBlockName() {
        return null;
    }

    @Override
    public String getRowName(EquipBasicLengthEnum inEquipBasicLength) {
        return "";
    }

    @Override
    public String getColumnName(EquipBasicLengthEnum inEquipBasicLength) {
        return "";
    }

    public void addToBinIndex(IXpsYardBin inBin, String inBinName) {
        Map binIndex;
        inBinName = this.removeBlockSchemeSeperator(inBinName);
        if (inBin instanceof AbstractXpsYardStack) {
            binIndex = this._binIndexForStacks;
        } else if (inBin instanceof AbstractXpsYardSection) {
            binIndex = this._binIndexForSections;
        } else {
            return;
        }
        if (binIndex.containsKey(inBinName)) {
            LoggingHelper.DATA_ISSUE_LOGGER.error((Object)("addToBinIndex: duplicate bin name! " + inBinName + " is name of " + inBin + " and " + binIndex.get(inBinName)));
        } else {
            binIndex.put(inBinName, inBin);
        }
    }

    @Override
    public IXpsYardBin getBin(String inBinName) {
        if (StringUtils.isEmpty((String)inBinName)) {
            return this;
        }
        Object bin = this._binIndexForStacks.get(inBinName);
        if (bin == null) {
            bin = this._binIndexForSections.get(inBinName);
        }
        if (bin == null) {
            bin = this._binIndexForBlocks.get(inBinName);
        }
        return (IXpsYardBin)bin;
    }

    public IXpsYardBin findBlockBin(String inBinName) {
        IXpsYardBlock responseBin = null;
        if (StringUtils.isEmpty((String)inBinName)) {
            return responseBin;
        }
        for (IXpsYardBlock testBlock : this.getYardBlocks()) {
            String blockName;
            String testblockName = testBlock.getBlockName();
            if (4 == testBlock.getBlockType()) {
                if (!SystemConsole.LOGGER.isTraceEnabled()) continue;
                SystemConsole.LOGGER.trace((Object) String.format("Block name %s is a logical Block, so skipping the Bin retrival from Bin name..", testBlock.getBlockName()));
                continue;
            }
            if (!inBinName.startsWith(testblockName) || (responseBin = this.getYardBlock(blockName = this.removeBlockSchemeSeperator(inBinName))) != null) continue;
            responseBin = testBlock;
        }
        return responseBin;
    }

    @NotNull
    public String removeBlockSchemeSeperator(@NotNull String inString) {
        return this.removeSeperator(inString, "[-]");
    }

    @NotNull
    private String removeSeperator(@NotNull String inString, @NotNull String inRegex) {
        if (StringUtils.isEmpty((String)inString)) {
            return inString;
        }
        Object[] parsedSlot = inString.split(inRegex);
        String returnString = Joiner.on((String)"").join(parsedSlot);
        return returnString;
    }

    public static XpsYardSection getSection(@NotNull IXpsYardBin inCheBin) {
        if (inCheBin instanceof XpsYardStack) {
            XpsYardStack cheStack = (XpsYardStack)inCheBin;
            XpsYardSection cheSection = (XpsYardSection)cheStack.getSection();
            return cheSection;
        }
        if (inCheBin instanceof XpsYardSection) {
            XpsYardSection cheSection = (XpsYardSection)inCheBin;
            return cheSection;
        }
        return null;
    }

    @Override
    @Nullable
    public IXpsYardBin getBinFromSlot(@Nullable String inSlot) {
        if (inSlot == null) {
            return null;
        }
        String strippedSlot = this.removeBlockSchemeSeperator(inSlot);
        IXpsYardBin strippedbin = this.getBin(strippedSlot);
        if (strippedbin != null) {
            return strippedbin;
        }
        String userSepartorStrippedSlot = this.removeSeperator(inSlot, USER_SEPARATION_REGEX);
        IXpsYardBin userSepartorStrippedbin = this.getBin(userSepartorStrippedSlot);
        if (userSepartorStrippedbin != null) {
            return userSepartorStrippedbin;
        }
        String binName = XpsYardModel.getBinNameForSlot(inSlot);
        if (binName == null) {
            return null;
        }
        if (binName.isEmpty() && !inSlot.isEmpty()) {
            return null;
        }
        IXpsYardBin responseBin = this.getBin(binName);
        if (responseBin != null) {
            return responseBin;
        }
        return this.findBlockBin(binName);
    }

    @Nullable
    public static String getBinNameForSlot(@Nullable String inSlot) {
        String[] retVal = XpsYardModel.getBinAndTierFromSlot(inSlot);
        if (retVal == null) {
            return null;
        }
        return retVal[0];
    }

    @Nullable
    public static String getTierNameForSlot(@Nullable String inSlot) {
        String[] retVal = XpsYardModel.getBinAndTierFromSlot(inSlot);
        if (retVal == null) {
            return null;
        }
        return retVal[1];
    }

    public static boolean hasTier(@Nullable String inSlot) {
        String[] retVal = XpsYardModel.getBinAndTierFromSlot(inSlot);
        if (retVal == null) {
            return false;
        }
        return !StringUtils.isBlank((String)retVal[1]);
    }

    @Nullable
    private static String[] getBinAndTierFromSlot(@Nullable String inSlot) {
        Object possibleTierName;
        int possibleTierLength;
        if (inSlot == null) {
            return null;
        }
        String trimmedSlot = inSlot.trim();
        Object[] parsedSlot = trimmedSlot.split(USER_SEPARATION_REGEX, -1);
        if (Arrays.asList(parsedSlot).contains("")) {
            if (LoggingHelper.DATA_ISSUE_LOGGER.isDebugEnabled()) {
                LoggingHelper.DATA_ISSUE_LOGGER.debug((Object) String.format("%s is not a valid PPOS because it contains 2 adjacent separators", inSlot));
            }
            return new String[]{null, null};
        }
        if (parsedSlot.length >= 2 && ((possibleTierLength = ((String)(possibleTierName = parsedSlot[parsedSlot.length - 1])).length()) == 1 || possibleTierLength == 2)) {
            String binName = Joiner.on((String)"").join(Arrays.copyOf(parsedSlot, parsedSlot.length - 1));
            return new String[]{binName, (String) possibleTierName};
        }
        String binName = Joiner.on((String)"").join(parsedSlot);
        return new String[]{binName, null};
    }

    @NotNull
    public static String[] getColAndTierFromPosition(@NotNull String inEnteredPosition) {
        return XpsYardModel.getBinAndTierFromSlot(inEnteredPosition);
    }

    @Override
    public Set<String> getAllBinNames() {
        TreeSet binNames = new TreeSet();
        binNames.addAll(this._binIndexForBlocks.keySet());
        binNames.addAll(this._binIndexForSections.keySet());
        binNames.addAll(this._binIndexForStacks.keySet());
        return Collections.unmodifiableSet(binNames);
    }

    public void addYardBlock(AbstractXpsYardBlock inBlock) {
        if (this._binIndexForBlocks.containsKey(inBlock.getBlockName())) {
            LoggingHelper.DATA_ISSUE_LOGGER.error((Object)("addYardBlock: duplicate block name! " + inBlock.getBlockName() + " is name of " + inBlock + " and " + this._binIndexForBlocks.get(inBlock.getBlockName())));
        } else {
            this._binIndexForBlocks.put(inBlock.getBlockName(), inBlock);
        }
    }

    @Override
    public IXpsYardBlock getYardBlock(String inBlockCode) {
        return (AbstractXpsYardBlock)this._binIndexForBlocks.get(inBlockCode);
    }

    @Override
    @NotNull
    public List<IXpsYardBlock> getYardBlocks() {
        return new ArrayList<IXpsYardBlock>(this._binIndexForBlocks.values());
    }

    public ValueObject getInfoAsVao() {
        ValueObject result = new ValueObject("XpsYardModel");
        result.setEntityPrimaryKey((Serializable)((Object)this._yardCode));
        result.setFieldValue(IXpscacheBizMetafield.YRDM_YARD_CODE, (Object)this._yardCode);
        result.setFieldValue(IXpscacheBizMetafield.YRDM_YARD_NAME, (Object)this._yardName);
        return result;
    }

    public List getBlocksAsVaos() {
        ArrayList<ValueObject> result = new ArrayList<ValueObject>();
        for (Object block : this._binIndexForBlocks.values()) {
            ValueObject blockVao = ((AbstractXpsYardBlock)block).getInfoAsVao();
            result.add(blockVao);
        }
        return result;
    }

    public void addRailTrack(@NotNull IXpsRailTrack inIXpsRailTrack) {
        this._railTracks.add(inIXpsRailTrack);
    }

    @Override
    @NotNull
    public List<IXpsRailTrack> getRailTracks() {
        return Collections.unmodifiableList(this._railTracks);
    }

    @Override
    @Nullable
    public IXpsRailTrack getRailTrack(@NotNull String inRailBlock, @NotNull String inRailTrack) {
        for (IXpsRailTrack railTrack : this._railTracks) {
            if (!inRailBlock.equalsIgnoreCase(railTrack.getTrackOwnerPlanName()) || !inRailTrack.equalsIgnoreCase(railTrack.getTrackName())) continue;
            return railTrack;
        }
        return null;
    }

    public void addYardElevatedStack(@NotNull IXpsYardElevatedStack inIXpsYardElevatedStack) {
        this._yardElevatedStacks.add(inIXpsYardElevatedStack);
    }

    @NotNull
    public List<IXpsYardElevatedStack> getYardElevatedStacks() {
        return Collections.unmodifiableList(this._yardElevatedStacks);
    }

    public void addYardPoint(@NotNull IXpsYardPoint inYardPoint) {
        this._yardPoints.add(inYardPoint);
    }

    @Override
    @NotNull
    public List<IXpsYardPoint> getYardPoints() {
        return Collections.unmodifiableList(this._yardPoints);
    }

    @Override
    public List<IXpsYardPoint> getYardPointsForBlock(@NotNull String inBlockCode) {
        ArrayList<IXpsYardPoint> pointList = new ArrayList<IXpsYardPoint>();
        for (IXpsYardPoint thePoint : this._yardPoints) {
            if (!thePoint.getBlockCode().equals(inBlockCode)) continue;
            pointList.add(thePoint);
        }
        Collections.sort(pointList, new YardPointComparator());
        return pointList;
    }

    @Override
    public List<IXpsYardPoint> getYardPointsForYard() {
        ArrayList<IXpsYardPoint> pointList = new ArrayList<IXpsYardPoint>();
        for (IXpsYardPoint thePoint : this._yardPoints) {
            if (!"YRD".equals(thePoint.getBlockCode())) continue;
            pointList.add(thePoint);
        }
        Collections.sort(pointList, new YardPointComparator());
        return pointList;
    }

    @Override
    public boolean isValidYardSlot(@Nullable String inPPOS) {
        IXpsYardBin yardBin = this.getBinFromSlot(inPPOS);
        IXpsYardBlock yardBlock = yardBin != null ? yardBin.getBlock() : null;
        boolean result = true;
        if (yardBlock == null) {
            result = false;
        } else if (yardBlock instanceof XpsStackBlock || yardBlock instanceof XpsRTGStackBlock || yardBlock instanceof XpsTransferZoneBlock) {
            if (!(yardBin instanceof XpsYardStack)) {
                result = false;
            }
            if (!yardBlock.isWheeled() && !yardBlock.isChassisBin()) {
                String tierName = XpsYardModel.getTierNameForSlot(inPPOS);
                if (yardBlock.getTierConvTableId() != 0 && !yardBlock.isValidTierName(tierName)) {
                    result = false;
                }
            }
        } else if (yardBlock instanceof UnknownXpsYardBlock) {
            result = false;
        }
        return result;
    }

    @Override
    public boolean isYardContainRailTZ() {
        return this._yardContainRailTZ;
    }

    public void setContainsRailTransferZone(boolean inContainsRailTransferZone) {
        this._yardContainRailTZ = inContainsRailTransferZone;
    }

    @Override
    @NotNull
    public IXpsTransferZoneAssociationManager getTZAssociationManager() {
        return this._tzAssocManager;
    }

    @Override
    public IXpsYardBin createUnknownBin(String inSlotName) {
        return new UnknownXpsYardBin(inSlotName, this._unknownBlock);
    }

    @Override
    @NotNull
    public TransferZoneKindEnum getTransferZoneKind(@Nullable String inSlot) {
        if (inSlot == null) {
            if (SystemConsole.LOGGER.isDebugEnabled()) {
                SystemConsole.LOGGER.debug((Object)"Passed argument is not a transfer zone because it is null");
            }
            return TransferZoneKindEnum.NOT_A_TRANSFERZONE;
        }
        IXpsYardBin bin = this.getBinFromSlot(inSlot);
        if (bin == null) {
            if (SystemConsole.LOGGER.isDebugEnabled()) {
                SystemConsole.LOGGER.debug((Object) String.format("%s is not a transfer zone because it could not be resolved to a bin", inSlot));
            }
            return TransferZoneKindEnum.NOT_A_TRANSFERZONE;
        }
        return this.getTransferZoneKind(bin);
    }

    @Override
    @NotNull
    public TransferZoneKindEnum getTransferZoneKind(@NotNull IXpsYardBlock inBlock) {
        IXpsYardBlock assocBlock;
        IXpsTransferZoneAssociationManager tzAssocManager = this.getTZAssociationManager();
        String associatedBlockName = tzAssocManager.getAssociatedBlockName(inBlock, this);
        if (associatedBlockName != null && associatedBlockName.startsWith("VSSL")) {
            if (SystemConsole.LOGGER.isDebugEnabled()) {
                SystemConsole.LOGGER.debug((Object) String.format("The TZ associated block for %s is hard coded VSSL - assuming this is vessel TZ", inBlock));
            }
            return TransferZoneKindEnum.VESSEL;
        }
        IXpsYardBin assocBin = this.findBlockBin(associatedBlockName);
        IXpsYardBlock iXpsYardBlock = assocBlock = assocBin != null ? assocBin.getBlock() : null;
        if (assocBlock != null && assocBlock.getBlockType() == 8) {
            if (SystemConsole.LOGGER.isDebugEnabled()) {
                SystemConsole.LOGGER.debug((Object) String.format("The TZ associated block for %s is RAIL block - this is a rail TZ", inBlock));
            }
            return TransferZoneKindEnum.RAIL;
        }
        if (inBlock.isTransferZone()) {
            if (assocBin == null) {
                if (SystemConsole.LOGGER.isDebugEnabled()) {
                    SystemConsole.LOGGER.debug((Object) String.format("%s is associated to null block and is OtherTZ", inBlock));
                }
                return TransferZoneKindEnum.OTHER;
            }
            if (assocBin.isProtectedBin()) {
                if (inBlock.isWheeled()) {
                    return TransferZoneKindEnum.LANDSIDE;
                }
                if (inBlock.getBlockType() == 5) {
                    return TransferZoneKindEnum.WATERSIDE;
                }
                if (inBlock.getBlockType() == 19) {
                    return TransferZoneKindEnum.CARMG;
                }
                SystemConsole.LOGGER.warn((Object) String.format("%s is associated with ASC stack but is not wheeled or straddle assuming not a TZ", inBlock));
                return TransferZoneKindEnum.NOT_A_TRANSFERZONE;
            }
            if (SystemConsole.LOGGER.isDebugEnabled()) {
                SystemConsole.LOGGER.debug((Object) String.format("%s is a transfer zone but not wheeled and not rail/ws/ls - assuming Other", inBlock));
            }
            return TransferZoneKindEnum.OTHER;
        }
        return TransferZoneKindEnum.NOT_A_TRANSFERZONE;
    }

    @Override
    @NotNull
    public TransferZoneKindEnum getTransferZoneKind(@NotNull IXpsYardBin inBin) {
        IXpsYardBlock stackBlock = inBin.getBlock();
        if (stackBlock == null) {
            if (SystemConsole.LOGGER.isDebugEnabled()) {
                SystemConsole.LOGGER.debug((Object) String.format("%s is not a transfer zone because it is not a stack block", inBin));
            }
            return TransferZoneKindEnum.NOT_A_TRANSFERZONE;
        }
        return this.getTransferZoneKind(stackBlock);
    }

    @Override
    @NotNull
    public List<IXpsYardBlock> getAssociatedTransferZones(@NotNull String inPPOS) {
        IXpsYardBin bin = this.getBinFromSlot(inPPOS);
        if (bin == null) {
            if (SystemConsole.LOGGER.isDebugEnabled()) {
                SystemConsole.LOGGER.debug((Object) String.format("%s does not resolve to a bin", inPPOS));
            }
            return Collections.emptyList();
        }
        return this.getAssociatedTransferZones(bin);
    }

    @Override
    @Nullable
    public IXpsYardBlock getFirstAssociatedTransferZone(@NotNull String inPPOS, @NotNull TransferZoneKindEnum inTransferZoneKind) {
        List<IXpsYardBlock> associatedTransferZones = this.getAssociatedTransferZones(inPPOS);
        List<IXpsYardBlock> filteredTransferZones = this.filterForTransferZonesOfType(associatedTransferZones, inTransferZoneKind);
        return !filteredTransferZones.isEmpty() ? filteredTransferZones.get(0) : null;
    }

    @Override
    @NotNull
    public List<IXpsYardBlock> getAssociatedTransferZones(@NotNull IXpsYardBin inBin) {
        IXpsYardBlock ascBlock = inBin.getBlock();
        if (ascBlock == null) {
            if (SystemConsole.LOGGER.isDebugEnabled()) {
                SystemConsole.LOGGER.debug((Object) String.format("%s is not a block", inBin));
            }
            return Collections.emptyList();
        }
        IXpsTransferZoneAssociationManager tzAssocManager = this.getTZAssociationManager();
        return tzAssocManager.getAssociatedTransferZoneBlocks(ascBlock, this);
    }

    @Override
    @NotNull
    public List<IXpsYardBlock> getAssociatedTransferZones(@NotNull IXpsYardBin inBin, @NotNull TransferZoneKindEnum inTransferZoneKind) {
        List<IXpsYardBlock> transferZoneBlocks = this.getAssociatedTransferZones(inBin);
        return this.filterForTransferZonesOfType(transferZoneBlocks, inTransferZoneKind);
    }

    @NotNull
    private List<IXpsYardBlock> filterForTransferZonesOfType(@NotNull List<IXpsYardBlock> inTransferZones, final @NotNull TransferZoneKindEnum inTransferZoneKind) {
//        return ImmutableList.copyOf((Iterable) Iterables.filter(inTransferZones, (Predicate)new Predicate()
//        {
//
//            public boolean apply(IXpsYardBlock inIXpsYardBlock) {
//                TransferZoneKindEnum transferZoneKind = XpsYardModel.this.getTransferZoneKind(inIXpsYardBlock);
//                return transferZoneKind.equals((Object)inTransferZoneKind);
//            }
//        }));
        return null;
    }

    private static class UnknownXpsYardBlock
            implements IXpsYardBlock {
        private UnknownXpsYardBlock() {
        }

        @Override
        public String getUiFullPositionWithTier(EquipBasicLengthEnum inBasicLength, String inTierName) {
            return "?unknown?";
        }

        @Override
        public String getUiFullPosition(EquipBasicLengthEnum inBasicLength) {
            return this.getUiFullPositionWithTier(inBasicLength, "x");
        }

        @Override
        public IXpsYardBlock getBlock() {
            return this;
        }

        @Override
        public boolean isInterFacilityTransferBin() {
            return false;
        }

        @Override
        public boolean isProtectedBin() {
            return false;
        }

        @Override
        public boolean isChassisBin() {
            return false;
        }

        @Override
        public boolean isLogicalBlock() {
            return false;
        }

        @Override
        public boolean isMatchBlock(IXpsYardBin inYardBin) {
            return false;
        }

        @Override
        public int getTierConvTableId() {
            return 0;
        }

        @Override
        public String getConvertedTierName(int inTierNumber) {
            return "?";
        }

        @Override
        public boolean isValidTierName(String inTierName) {
            return false;
        }

        @Override
        public int convertTierNameToTier(String inTierName) {
            return 0;
        }

        @Override
        public int getBlockType() {
            return 0;
        }

        @Override
        public String getBlockName() {
            return "??";
        }

        @Override
        public boolean isWheeled() {
            return false;
        }

        @Override
        public boolean isWheeledOrYardTransTZ() {
            return false;
        }

        @Override
        public boolean isGrounded() {
            return false;
        }

        @Override
        public Character getTrafficFlow() {
            return Character.valueOf(' ');
        }

        @Override
        public boolean isTransferZone() {
            return false;
        }

        @Override
        public boolean isBlockTypeAscOrCarmg() {
            return false;
        }

        @Override
        public boolean isBlockTypeStraddle() {
            return false;
        }
    }

    private static class UnknownXpsYardBin
            implements IXpsYardBin {
        private final String _slotName;
        private final IXpsYardBlock _unknownBlock;

        private UnknownXpsYardBin(String inSlotName, IXpsYardBlock inUnknownBlock) {
            this._slotName = inSlotName;
            this._unknownBlock = inUnknownBlock;
        }

        @Override
        public String getUiFullPosition(EquipBasicLengthEnum inBasicLength) {
            return this.getUiFullPositionWithTier(inBasicLength, "x");
        }

        @Override
        public String getUiFullPositionWithTier(EquipBasicLengthEnum inBasicLength, String inTierName) {
            return "?" + this._slotName + "?";
        }

        @Override
        public IXpsYardBlock getBlock() {
            return this._unknownBlock;
        }

        @Override
        public boolean isInterFacilityTransferBin() {
            return false;
        }

        @Override
        public boolean isProtectedBin() {
            return false;
        }

        @Override
        public boolean isChassisBin() {
            return false;
        }

        @Override
        public boolean isLogicalBlock() {
            return false;
        }

        @Override
        public boolean isMatchBlock(IXpsYardBin inYardBin) {
            return false;
        }
    }

    private static class YardPointComparator
            implements Comparator<IXpsYardPoint> {
        private YardPointComparator() {
        }

        @Override
        public int compare(IXpsYardPoint inPoint1, IXpsYardPoint inPoint2) {
            if (inPoint1.getSequence() < inPoint2.getSequence()) {
                return -1;
            }
            if (inPoint1.getSequence() > inPoint2.getSequence()) {
                return 1;
            }
            return 0;
        }
    }
}
