package com.zpmc.ztos.infra.base.business.xps;

public class XpsTransferZoneBlock extends XpsStackBlock{
    public XpsTransferZoneBlock(XpsYardModel inYardModel, int inBlockType, String inBlockCode, int inRowConvId20, int inRowConvId40, int inTierConvId) {
        super(inYardModel, inBlockType, inBlockCode, inRowConvId20, inRowConvId40, inTierConvId);
    }

    @Override
    public boolean isTransferZone() {
        return true;
    }
}
