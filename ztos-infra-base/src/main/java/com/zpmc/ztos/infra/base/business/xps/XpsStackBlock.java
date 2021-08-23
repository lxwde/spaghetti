package com.zpmc.ztos.infra.base.business.xps;

import com.zpmc.ztos.infra.base.business.interfaces.IXpscacheBizMetafield;
import com.zpmc.ztos.infra.base.common.model.ValueObject;
import org.apache.commons.collections4.map.MultiKeyMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XpsStackBlock extends AbstractXpsYardBlock{
    private int _firstRow;
    private int _lastRow;
    private int _firstColumn;
    private int _lastColumn;
    private int _maxHeight;
    private int _maxWeight;
    private int _tierCount;
    private int _rowConvID20;
    private int _rowConvID40;
    private int _tierConvID;
    private boolean _invertX;
    private boolean _invertY;
    private boolean _rotate90;
    private boolean _invertScanX;
    private boolean _invertScanY;
    private boolean _compressScan;
    private int _stackLengthPixels;
    private int _stackWidthPixels;
    private int _stackSpaceBetweenPixels;
    private Character _direction;
    private MultiKeyMap _disabledPositionsRegistry = new MultiKeyMap();
    private List _convertedTierNamesList;
    private Map _tierLookUpTable;

    public XpsStackBlock(XpsYardModel inYardModel, int inBlockType, String inBlockCode, int inRowConvId20, int inRowConvId40, int inTierConvId) {
        super(inYardModel, inBlockType, inBlockCode, inRowConvId20, inRowConvId40, inTierConvId);
    }

    @Override
    public void computeBinNames(Map inUserConvTableMap) {
        super.computeBinNames(inUserConvTableMap);
        MultiKeyMap multiKeyMap = (MultiKeyMap)inUserConvTableMap;
        this._convertedTierNamesList = new ArrayList();
        this._tierLookUpTable = new HashMap();
        for (int tierNum = 1; tierNum <= this._tierCount; ++tierNum) {
            String convertedTierName = (String)multiKeyMap.get((Object)this._tierConvID, (Object)tierNum);
            if (convertedTierName == null) {
                convertedTierName = String.valueOf(tierNum);
            }
            this._convertedTierNamesList.add(convertedTierName);
            this._tierLookUpTable.put(convertedTierName, new Integer(tierNum));
        }
    }

    @Override
    public String getConvertedTierName(int inTierNumber) {
        String result = (String)this._convertedTierNamesList.get(inTierNumber);
        if (result == null) {
            result = String.valueOf(inTierNumber);
        }
        return result;
    }

    @Override
    public boolean isValidTierName(String inTierName) {
        return this._tierLookUpTable.containsKey(inTierName);
    }

    @Override
    public int convertTierNameToTier(String inTierName) {
        Object tierEntry = this._tierLookUpTable.get(inTierName);
        return tierEntry != null ? (Integer)tierEntry : 0;
    }

    public int getFirstRow() {
        return this._firstRow;
    }

    public void setFirstRow(int inRow) {
        this._firstRow = inRow;
    }

    public int getLastRow() {
        return this._lastRow;
    }

    public void setLastRow(int inRow) {
        this._lastRow = inRow;
    }

    public int getTierCount() {
        return this._tierCount;
    }

    public void setTierCount(int inTierCount) {
        this._tierCount = inTierCount;
    }

    public int getFirstColumn() {
        return this._firstColumn;
    }

    public void setFirstColumn(int inFirstColumn) {
        this._firstColumn = inFirstColumn;
    }

    public int getLastColumn() {
        return this._lastColumn;
    }

    public void setLastColumn(int inLastColumn) {
        this._lastColumn = inLastColumn;
    }

    public int getMaxHeight() {
        return this._maxHeight;
    }

    public void setMaxHeight(int inHeight) {
        this._maxHeight = inHeight;
    }

    public int getMaxWeight() {
        return this._maxWeight;
    }

    public void setMaxWeight(int inWeight) {
        this._maxWeight = inWeight;
    }

    public int getRowConvID20() {
        return this._rowConvID20;
    }

    public void setRowConvID20(int inConvID20) {
        this._rowConvID20 = inConvID20;
    }

    public int getRowConvID40() {
        return this._rowConvID40;
    }

    public void setRowConvID40(int inConvID40) {
        this._rowConvID40 = inConvID40;
    }

    public int getTierConvID() {
        return this._tierConvID;
    }

    public void setTierConvID(int inConvID) {
        this._tierConvID = inConvID;
    }

    public boolean isInvertX() {
        return this._invertX;
    }

    public void setInvertX(boolean inInvertX) {
        this._invertX = inInvertX;
    }

    public boolean isInvertY() {
        return this._invertY;
    }

    public void setInvertY(boolean inInvertY) {
        this._invertY = inInvertY;
    }

    public boolean isRotate90() {
        return this._rotate90;
    }

    public void setRotate90(boolean inRotate90) {
        this._rotate90 = inRotate90;
    }

    public boolean isInvertScanX() {
        return this._invertScanX;
    }

    public void setInvertScanX(boolean inInvertScanX) {
        this._invertScanX = inInvertScanX;
    }

    public boolean isInvertScanY() {
        return this._invertScanY;
    }

    public void setInvertScanY(boolean inInvertScanY) {
        this._invertScanY = inInvertScanY;
    }

    public boolean isCompressScan() {
        return this._compressScan;
    }

    public void setCompressScan(boolean inCompressScan) {
        this._compressScan = inCompressScan;
    }

    public int getStackLengthPixels() {
        return this._stackLengthPixels;
    }

    public void setStackLengthPixels(int inStackLengthPixels) {
        this._stackLengthPixels = inStackLengthPixels;
    }

    public int getStackWidthPixels() {
        return this._stackWidthPixels;
    }

    public void setStackWidthPixels(int inStackWidthPixels) {
        this._stackWidthPixels = inStackWidthPixels;
    }

    public int getStackSpaceBetweenPixels() {
        return this._stackSpaceBetweenPixels;
    }

    public void setStackSpaceBetweenPixels(int inStackSpaceBetweenPixels) {
        this._stackSpaceBetweenPixels = inStackSpaceBetweenPixels;
    }

    public Character getDirection() {
        return this._direction;
    }

    public void setDirection(Character inDirection) {
        this._direction = inDirection;
    }

    @Override
    public ValueObject getInfoAsVao() {
        ValueObject result = super.getInfoAsVao();
        result.setFieldValue(IXpscacheBizMetafield.YRDM_BLOCK_FIRST_ROW, (Object) String.valueOf(this._firstRow));
        result.setFieldValue(IXpscacheBizMetafield.YRDM_BLOCK_LAST_ROW, (Object) String.valueOf(this._lastRow));
        result.setFieldValue(IXpscacheBizMetafield.YRDM_BLOCK_MAX_HEIGHT, (Object) String.valueOf(this._maxHeight));
        result.setFieldValue(IXpscacheBizMetafield.YRDM_BLOCK_MAX_WEIGHT, (Object) String.valueOf(this._maxWeight));
        result.setFieldValue(IXpscacheBizMetafield.YRDM_BLOCK_TIER_COUNT, (Object) String.valueOf(this._tierCount));
        result.setFieldValue(IXpscacheBizMetafield.YRDM_BLOCK_ROW_CONV_ID20, (Object) String.valueOf(this._rowConvID20));
        result.setFieldValue(IXpscacheBizMetafield.YRDM_BLOCK_ROW_CONV_ID40, (Object) String.valueOf(this._rowConvID40));
        result.setFieldValue(IXpscacheBizMetafield.YRDM_BLOCK_TIER_CONV_ID, (Object) String.valueOf(this._tierConvID));
        result.setFieldValue(IXpscacheBizMetafield.YRDM_BLOCK_INVERT_X, (Object) String.valueOf(this._invertX));
        result.setFieldValue(IXpscacheBizMetafield.YRDM_BLOCK_INVERT_Y, (Object) String.valueOf(this._invertY));
        result.setFieldValue(IXpscacheBizMetafield.YRDM_BLOCK_ROTATE90, (Object) String.valueOf(this._rotate90));
        return result;
    }

    public void disableRegion(String inReason, int inStartRow, int inEndRow, int inStartColumn, int inEndColumn) {
        for (int row = inStartRow; row <= inEndRow; ++row) {
            for (int column = inStartColumn; column <= inEndColumn; ++column) {
                this._disabledPositionsRegistry.put((Object)row, (Object)column, (Object)inReason);
            }
        }
    }

    public boolean isPositionDisabled(int inRow, int inColumn) {
        String result = (String)this._disabledPositionsRegistry.get((Object)inRow, (Object)inColumn);
        return result != null;
    }

    public String getPositionDisabledCode(int inRow, int inColumn) {
        return (String)this._disabledPositionsRegistry.get((Object)inRow, (Object)inColumn);
    }
}
