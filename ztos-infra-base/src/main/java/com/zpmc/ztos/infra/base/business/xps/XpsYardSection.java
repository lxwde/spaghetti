package com.zpmc.ztos.infra.base.business.xps;

public class XpsYardSection extends AbstractXpsYardSection{
    public XpsYardSection(AbstractXpsYardBlock inParentBlock, int inRow, int inFirstColumn, int inLastColumn, int inColConvTableId20, int inColConvTableId40, int inConvTableOffset, int inRoadwayColumn, boolean inAccessBlockedAtFirstColumn, boolean inAccessBlockedAtLastColumn, int inLengthMask) {
        super(inParentBlock, inRow, inFirstColumn, inLastColumn, inColConvTableId20, inColConvTableId40, inConvTableOffset, inRoadwayColumn, inAccessBlockedAtFirstColumn, inAccessBlockedAtLastColumn, inLengthMask);
    }
}
