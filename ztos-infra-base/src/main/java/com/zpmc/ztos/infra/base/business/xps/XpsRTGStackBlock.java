package com.zpmc.ztos.infra.base.business.xps;

public class XpsRTGStackBlock extends XpsStackBlock {
    int _rtgLaneNameIndex;

    public XpsRTGStackBlock(XpsYardModel inYardModel, int inBlockType, String inBlockCode, int inRowConvId20, int inRowConvId40, int inTierConvId) {
        super(inYardModel, inBlockType, inBlockCode, inRowConvId20, inRowConvId40, inTierConvId);
    }

    public void setRTGLaneNameIdx(int inRTGLaneNameIndex) {
        this._rtgLaneNameIndex = inRTGLaneNameIndex;
    }

}
