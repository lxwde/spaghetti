package com.zpmc.ztos.infra.base.business.xps;

import com.zpmc.ztos.infra.base.business.enums.argo.EquipBasicLengthEnum;
import com.zpmc.ztos.infra.base.business.interfaces.IXpsYardBin;
import com.zpmc.ztos.infra.base.business.interfaces.IXpsYardBlock;
import org.apache.commons.collections4.map.MultiKeyMap;
import org.apache.log4j.Logger;

import java.util.Map;

public abstract class AbstractXpsYardBin implements IXpsYardBin {
    protected AbstractParentXpsYardBin _parentBin;
    private String _coordinateName20;
    private String _coordinateName40;
    public static final Character LOC_TYPE_YARD = Character.valueOf('Y');
    public static final Character LOC_TYPE_VESSEL = Character.valueOf('V');
    public static final Character LOC_TYPE_RAIL = Character.valueOf('R');
    public static final Character LOC_TYPE_TRUCK = Character.valueOf('T');
    public static final Character LOC_TYPE_COMMUNITY = Character.valueOf('C');
    public static final Character LOC_TYPE_CUSTOMS_ORPHANS = Character.valueOf('O');
    public static final Character LOC_TYPE_CHE = Character.valueOf('E');
    public static final Character LOC_TYPE_UNKNOWN = Character.valueOf(' ');
    private static final Logger LOGGER = Logger.getLogger(AbstractXpsYardBin.class);

    public AbstractXpsYardBin(AbstractParentXpsYardBin inParentBin) {
        this._parentBin = inParentBin;
    }

    protected void setCoordinateName20(String inCoordinateName20) {
        this._coordinateName20 = inCoordinateName20;
    }

    protected void setCoordinateName40(String inCoordinateName40) {
        this._coordinateName40 = inCoordinateName40;
    }

    public String getCoordinateName(EquipBasicLengthEnum inBasicLength) {
        if (inBasicLength.equals((Object)EquipBasicLengthEnum.BASIC20)) {
            return this._coordinateName20;
        }
        return this._coordinateName40;
    }

    public void addBinNamesToBinIndex(XpsYardModel inYardModel) {
        String binName20 = this.getBinName(EquipBasicLengthEnum.BASIC20);
        String binName40 = this.getBinName(EquipBasicLengthEnum.BASIC40);
        inYardModel.addToBinIndex(this, binName20);
        if (!binName20.equals(binName40)) {
            inYardModel.addToBinIndex(this, binName40);
        }
    }

    public AbstractParentXpsYardBin getParent() {
        return this._parentBin;
    }

    public String getBlockName() {
        AbstractXpsYardBlock block = (AbstractXpsYardBlock)this.getBlock();
        return block.getBlockName();
    }

    public abstract String getRowName(EquipBasicLengthEnum var1);

    public abstract String getColumnName(EquipBasicLengthEnum var1);

    public String getBinName(EquipBasicLengthEnum inBasicLength) {
        XpsYardLabelScheme labelScheme;
        String result = "";
        AbstractXpsYardBlock block = (AbstractXpsYardBlock)this.getBlock();
        if (block != null && (labelScheme = block.getLabelSchemeBinName()) != null) {
            result = inBasicLength.equals((Object)EquipBasicLengthEnum.BASIC20) ? labelScheme.getFormattedYardPosition(this.getSubYardName(), this.getBlockName(), this.getRowName(EquipBasicLengthEnum.BASIC20), this.getColumnName(EquipBasicLengthEnum.BASIC20), "") : labelScheme.getFormattedYardPosition(this.getSubYardName(), this.getBlockName(), this.getRowName(EquipBasicLengthEnum.BASIC40), this.getColumnName(EquipBasicLengthEnum.BASIC40), "");
        }
        return result;
    }

    @Override
    public String getUiFullPosition(EquipBasicLengthEnum inBasicLength) {
        return this.getUiFullPositionWithTier(inBasicLength, "x");
    }

    @Override
    public String getUiFullPositionWithTier(EquipBasicLengthEnum inBasicLength, String inTierName) {
        AbstractXpsYardBlock block = (AbstractXpsYardBlock)this.getBlock();
        if (block == null) {
            String result = this.getSubYardName() + "-?" + this.getClass().getSimpleName() + "?-" + inTierName;
            LOGGER.error((Object)("Averted SEVERE error from getUiFullPositionWithTier in AbstractXpsYardBin by returning " + result));
            return result;
        }
        XpsYardLabelScheme labelScheme = block.getLabelSchemeUiFullPosition();
        String result = EquipBasicLengthEnum.BASIC20.equals((Object)inBasicLength) ? labelScheme.getFormattedYardPosition(this.getSubYardName(), this.getBlockName(), this.getRowName(EquipBasicLengthEnum.BASIC20), this.getColumnName(EquipBasicLengthEnum.BASIC20), inTierName) : labelScheme.getFormattedYardPosition(this.getSubYardName(), this.getBlockName(), this.getRowName(EquipBasicLengthEnum.BASIC40), this.getColumnName(EquipBasicLengthEnum.BASIC40), inTierName);
        return result;
    }

    @Override
    public boolean isInterFacilityTransferBin() {
        return false;
    }

    @Override
    public boolean isProtectedBin() {
        IXpsYardBlock block = this.getBlock();
        return block != null && block.getBlockType() == 7;
    }

    @Override
    public boolean isChassisBin() {
        IXpsYardBlock block = this.getBlock();
        if (block == null) {
            return false;
        }
        return block.getBlockType() == 3 || block.getBlockType() == 11;
    }

    @Override
    public boolean isLogicalBlock() {
        return false;
    }

    @Override
    public boolean isMatchBlock(IXpsYardBin inYardBin) {
        IXpsYardBlock block2;
        IXpsYardBlock block1 = this.getBlock();
        IXpsYardBlock iXpsYardBlock = block2 = inYardBin == null ? null : inYardBin.getBlock();
        if (block1 == null || block2 == null) {
            return false;
        }
        return block1.getBlockName().equals(block2.getBlockName());
    }

    public String getUiRowLabel() {
        XpsYardLabelScheme labelScheme;
        String result = "";
        AbstractXpsYardBlock block = (AbstractXpsYardBlock)this.getBlock();
        if (block != null && (labelScheme = block.getLabelSchemeUiRowOnly()) != null) {
            String posStr40;
            String posStr20 = labelScheme.getFormattedYardPosition("", "", this.getRowName(EquipBasicLengthEnum.BASIC20), "", "");
            result = posStr20.equals(posStr40 = labelScheme.getFormattedYardPosition("", "", this.getRowName(EquipBasicLengthEnum.BASIC40), "", "")) ? posStr20 : posStr20 + " (" + posStr40 + ")";
        }
        return result;
    }

    public String getUiColumnLabel() {
        XpsYardLabelScheme labelScheme;
        String result = "";
        AbstractXpsYardBlock block = (AbstractXpsYardBlock)this.getBlock();
        if (block != null && (labelScheme = block.getLabelSchemeUiColumnOnly()) != null) {
            String posStr40;
            String posStr20 = labelScheme.getFormattedYardPosition("", "", "", this.getColumnName(EquipBasicLengthEnum.BASIC20), "");
            result = posStr20.equals(posStr40 = labelScheme.getFormattedYardPosition("", "", "", this.getColumnName(EquipBasicLengthEnum.BASIC40), "")) ? posStr20 : posStr20 + " (" + posStr40 + ")";
        }
        return result;
    }

    public String getSubYardName() {
        return "";
    }

    public String getTierName(int inTierNumber) {
        String result = "";
        IXpsYardBlock block = this.getBlock();
        if (block != null) {
            result = block.getConvertedTierName(inTierNumber);
        }
        return result;
    }

    protected String lookupCoordinateName20(Map inUserConvTableMap, int inIndex) {
        return this.innerLookupBinName(inUserConvTableMap, this.getParent().getCoordConvId20(), inIndex);
    }

    protected String lookupCoordinateName40(Map inUserConvTableMap, int inIndex) {
        return this.innerLookupBinName(inUserConvTableMap, this.getParent().getCoordConvId40(), inIndex);
    }

    private String innerLookupBinName(Map inUserConvTableMap, Object inTableId, int inIndex) {
        String binName = (String)((MultiKeyMap)inUserConvTableMap).get(inTableId, (Object)(inIndex + this.getParent().getConvTableOffset()));
        if (binName == null) {
            if (!this.isPositionDisabled()) {
                LoggingHelper.DATA_ISSUE_LOGGER.warn((Object)("lookupBinName: could not find binName for " + this + " with parent " + this.getParent().getUiFullPosition(EquipBasicLengthEnum.BASIC20) + " table-id: " + inTableId + " index: " + inIndex));
            }
            return "?";
        }
        return binName;
    }

    public boolean isPositionDisabled() {
        return false;
    }

    public Integer getCoordConvId20() {
        return null;
    }

    public Integer getCoordConvId40() {
        return null;
    }

    public int getConvTableOffset() {
        return 0;
    }

    public String toString() {
        String fullClassName = this.getClass().getName();
        int lastDot = fullClassName.lastIndexOf(46);
        return fullClassName.substring(lastDot + 1, fullClassName.length()) + ':' + this._coordinateName20;
    }

    public int hashCode() {
        return this.toString().hashCode();
    }

    public boolean equals(Object inAbstractYardBin) {
        if (!(inAbstractYardBin instanceof AbstractXpsYardBin)) {
            return false;
        }
        AbstractXpsYardBin abstractYardBin = (AbstractXpsYardBin)inAbstractYardBin;
        if (!abstractYardBin.toString().equals(this.toString())) {
            return false;
        }
        AbstractParentXpsYardBin abstractParentXpsYardBin = abstractYardBin.getParent();
        if (abstractParentXpsYardBin == null) {
            return this.getParent() == null;
        }
        return abstractParentXpsYardBin.equals(this.getParent());
    }

    abstract void computeBinNames(Map var1);

}
