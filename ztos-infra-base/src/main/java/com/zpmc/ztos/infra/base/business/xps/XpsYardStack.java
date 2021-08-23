package com.zpmc.ztos.infra.base.business.xps;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.business.enums.xps.EquipmentBasicLengthSetTypeEnum;
import com.zpmc.ztos.infra.base.business.interfaces.IXpsYardBin;
import com.zpmc.ztos.infra.base.business.interfaces.IXpsYardBlock;
import com.zpmc.ztos.infra.base.business.interfaces.IXpsYardModel;
import com.zpmc.ztos.infra.base.business.interfaces.IXpsYardStack;
import org.apache.log4j.Logger;

public class XpsYardStack extends AbstractXpsYardStack {
    private static final Logger LOGGER = Logger.getLogger(XpsYardStack.class);

    public XpsYardStack(@NotNull AbstractXpsYardSection inParentSection, int inColumnIndex, int inFirstTier, int inLastTier) {
        super(inParentSection, inColumnIndex, inFirstTier, inLastTier);
    }

    public static boolean isSlotFullyQualifiedStack(@NotNull IXpsYardModel inModel, @NotNull String inSlot) {
        IXpsYardBin yardBin = inModel.getBinFromSlot(inSlot);
        return yardBin != null && yardBin instanceof IXpsYardStack;
    }

    public EquipmentBasicLengthSetTypeEnum getBasicLengthsAllowed() {
        EquipmentBasicLengthSetTypeEnum equipmentBasicLengthSetType;
        XpsYardSection parentSection = (XpsYardSection)this.getParentSection();
        int lengthMask = parentSection.getLengthMask();
        IXpsYardBlock block = parentSection.getBlock();
        if (block == null) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug((Object) String.format("...Stack Validation for Equipment Basic Length : returning NONE sine Block is null", new Object[0]));
            }
            return EquipmentBasicLengthSetTypeEnum.BASIC_LENGTH_SET_NONE;
        }
        String blockName = block.getBlockName();
        switch (lengthMask) {
            case -2: {
                int spot;
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug((Object) String.format("...Stack Validation for %s Equipment Basic Length : Length MasK is EVEN %s ", blockName, lengthMask));
                }
                if (block.isBlockTypeStraddle()) {
                    spot = this.getStackColumnIndex();
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug((Object) String.format("...Stack Validation for %s Equipment Basic Length : EVEN Straddle Grid Setting Spot %s  Column index", blockName, spot));
                    }
                } else {
                    spot = parentSection.getSectionRowIndex();
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug((Object) String.format("...Stack Validation for %s Equipment Basic Length : EVEN Not a Straddle Grid Setting Spot %s Row index", blockName, spot));
                    }
                }
                if ((long)spot % 2L == 0L) {
                    equipmentBasicLengthSetType = EquipmentBasicLengthSetTypeEnum.BASIC_LENGTH_20s_40s;
                    break;
                }
                equipmentBasicLengthSetType = EquipmentBasicLengthSetTypeEnum.BASIC_LENGTH_20s_ONLY;
                break;
            }
            case -1: {
                int spot;
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug((Object) String.format("...Stack Validation for Equipment Basic Length : Length MasK is ODD %s ", lengthMask));
                }
                if (block.isBlockTypeStraddle()) {
                    spot = this.getStackColumnIndex();
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug((Object) String.format("...Stack Validation for Equipment Basic Length : ODD Straddle Grid Setting Spot %s Column index", spot));
                    }
                } else {
                    spot = parentSection.getSectionRowIndex();
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug((Object) String.format("...Stack Validation for  %s Equipment Basic Length : ODD Straddle Grid Setting Spot %s Row index", blockName, spot));
                    }
                }
                if ((long)spot % 2L == 1L) {
                    equipmentBasicLengthSetType = EquipmentBasicLengthSetTypeEnum.BASIC_LENGTH_20s_40s;
                    break;
                }
                equipmentBasicLengthSetType = EquipmentBasicLengthSetTypeEnum.BASIC_LENGTH_20s_ONLY;
                break;
            }
            default: {
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug((Object) String.format("...Stack Validation for  %s  Equipment Basic Length : length mask is not ODD or EVEN %s", blockName, lengthMask));
                }
                equipmentBasicLengthSetType = this.slotAttrs2BasicLenSetForYard(lengthMask, blockName);
            }
        }
        return equipmentBasicLengthSetType;
    }

    public EquipmentBasicLengthSetTypeEnum slotAttrs2BasicLenSetForYard(int inLengthMask, String inBlockName) {
        EquipmentBasicLengthSetTypeEnum result = EquipmentBasicLengthSetTypeEnum.BASIC_LENGTH_SET_NONE;
        if ((inLengthMask & 0x221) != 0) {
            result = EquipmentBasicLengthSetTypeEnum.BASIC_LENGTH_20s_ONLY;
            if ((inLengthMask & 0x42) != 0) {
                result = EquipmentBasicLengthSetTypeEnum.BASIC_LENGTH_20s_40s;
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug((Object) String.format("...Stack Validation for  %s  Equipment Basic Length : for length mask %s returning 20s 40s", inBlockName, inLengthMask));
                }
            } else if (LOGGER.isDebugEnabled()) {
                LOGGER.debug((Object) String.format("...Stack Validation for  %s  Equipment Basic Length : for length mask %s returning 20s", inBlockName, inLengthMask));
            }
        } else if ((inLengthMask & 0x42) != 0) {
            result = EquipmentBasicLengthSetTypeEnum.BASIC_LENGTH_40s_ONLY;
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug((Object) String.format("...Stack Validation for  %s  Equipment Basic Length : for length mask %s returning 40s", inBlockName, inLengthMask));
            }
        }
        return result;
    }

}
