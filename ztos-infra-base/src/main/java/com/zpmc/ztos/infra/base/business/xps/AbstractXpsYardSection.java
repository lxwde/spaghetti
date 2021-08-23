package com.zpmc.ztos.infra.base.business.xps;

import com.zpmc.ztos.infra.base.business.enums.argo.EquipBasicLengthEnum;
import com.zpmc.ztos.infra.base.business.interfaces.IXpsYardBlock;
import com.zpmc.ztos.infra.base.business.interfaces.IXpsYardSection;
import com.zpmc.ztos.infra.base.business.interfaces.IXpsYardStack;
import com.zpmc.ztos.infra.base.business.interfaces.IXpscacheBizMetafield;
import com.zpmc.ztos.infra.base.common.model.ValueObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractXpsYardSection extends AbstractParentXpsYardBin implements IXpsYardSection {

    private IXpsYardBlock _parentBlock;
    private Map _stackMap;
    private int _rowIndex;
    private int _firstColumn;
    private int _lastColumn;
    private int _firstTier;
    private int _lastTier;
    private int _roadwayColumn;
    private int _lengthMask;
    private boolean _isAccessBlockedAtFirstColumn;
    private boolean _isAccessBlockedAtLastColumn;

    public AbstractXpsYardSection(AbstractXpsYardBlock inParentBlock, int inRow, int inFirstColumn, int inLastColumn, int inColumnConvTableId20, int inColumnConvTableId40, int inConvTableOffset, int inRoadwayColumn, boolean inAccessBlockedAtFirstColumn, boolean inAccessBlockedAtLastColumn, int inLengthMask) {
        super(inParentBlock, inColumnConvTableId20, inColumnConvTableId40, inConvTableOffset);
        this._parentBlock = inParentBlock;
        this._stackMap = new LinkedHashMap();
        this._rowIndex = inRow;
        this._firstColumn = inFirstColumn;
        this._lastColumn = inLastColumn;
        this._roadwayColumn = inRoadwayColumn;
        this._isAccessBlockedAtFirstColumn = inAccessBlockedAtFirstColumn;
        this._isAccessBlockedAtLastColumn = inAccessBlockedAtLastColumn;
        this._lengthMask = inLengthMask;
    }

    @Override
    public IXpsYardBlock getBlock() {
        return this._parentBlock;
    }

    public IXpsYardSection getSection() {
        return this;
    }

    public IXpsYardStack getStack() {
        return null;
    }

    @Override
    public void computeBinNames(Map inUserConvTableMap) {
        this.setCoordinateName20(this.lookupCoordinateName20(inUserConvTableMap, this._rowIndex));
        this.setCoordinateName40(this.lookupCoordinateName40(inUserConvTableMap, this._rowIndex));
    }

    @Override
    public String getRowName(EquipBasicLengthEnum inBasicLength) {
        return this.getCoordinateName(inBasicLength);
    }

    @Override
    public String getColumnName(EquipBasicLengthEnum inBasicLength) {
        return "";
    }

    @Override
    public int getSectionRowIndex() {
        return this._rowIndex;
    }

    public void setSectionRowIndex(int inRowIndex) {
        this._rowIndex = inRowIndex;
    }

    public int getFirstColumn() {
        return this._firstColumn;
    }

    public void setFirstColumn(int inColumn) {
        this._firstColumn = inColumn;
    }

    public int getLastColumn() {
        return this._lastColumn;
    }

    public void setLastColumn(int inColumn) {
        this._lastColumn = inColumn;
    }

    public int getFirstTier() {
        return this._firstTier;
    }

    public void setFirstTier(int inTier) {
        this._firstTier = inTier;
    }

    public int getLastTier() {
        return this._lastTier;
    }

    public void setLastTier(int inTier) {
        this._lastTier = inTier;
    }

    public int getRoadwayColumn() {
        return this._roadwayColumn;
    }

    public int getLengthMask() {
        return this._lengthMask;
    }

    public boolean isAccessBlockedAtFirstColumn() {
        return this._isAccessBlockedAtFirstColumn;
    }

    public boolean isAccessBlockedAtLastColumn() {
        return this._isAccessBlockedAtLastColumn;
    }

    public void addStack(int inColumn, IXpsYardStack inStack) {
        this._stackMap.put(new Integer(inColumn), inStack);
    }

    public IXpsYardStack getStack(int inColumn) {
        return (IXpsYardStack)this._stackMap.get(new Integer(inColumn));
    }

    public List getStacks() {
        return new ArrayList(this._stackMap.values());
    }

    @Override
    public ValueObject getInfoAsVao() {
        ValueObject result = new ValueObject("Section");
        result.setEntityPrimaryKey((Serializable)((Object)this.getBinName(EquipBasicLengthEnum.BASIC20)));
        result.setFieldValue(IXpscacheBizMetafield.YRDM_BIN_TYPE, (Object)"Section");
        result.setFieldValue(IXpscacheBizMetafield.YRDM_BIN_BIN_NAME20, (Object)this.getBinName(EquipBasicLengthEnum.BASIC20));
        result.setFieldValue(IXpscacheBizMetafield.YRDM_BIN_BIN_NAME40, (Object)this.getBinName(EquipBasicLengthEnum.BASIC40));
        result.setFieldValue(IXpscacheBizMetafield.YRDM_BIN_UI_FULL_POSITION20, (Object)this.getUiFullPosition(EquipBasicLengthEnum.BASIC20));
        result.setFieldValue(IXpscacheBizMetafield.YRDM_BIN_UI_FULL_POSITION40, (Object)this.getUiFullPosition(EquipBasicLengthEnum.BASIC40));
        result.setFieldValue(IXpscacheBizMetafield.YRDM_SECTION_NAME20, (Object)this.getRowName(EquipBasicLengthEnum.BASIC20));
        result.setFieldValue(IXpscacheBizMetafield.YRDM_SECTION_NAME40, (Object)this.getRowName(EquipBasicLengthEnum.BASIC40));
        result.setFieldValue(IXpscacheBizMetafield.YRDM_SECTION_ROW, (Object) String.valueOf(this._rowIndex));
        result.setFieldValue(IXpscacheBizMetafield.YRDM_SECTION_COLUMN_CONV_ID, (Object)this.getCoordConvId20());
        result.setFieldValue(IXpscacheBizMetafield.YRDM_SECTION_OTHER_COLUMN_CONV_ID, (Object)this.getCoordConvId40());
        result.setFieldValue(IXpscacheBizMetafield.YRDM_SECTION_STACKS, (Object)this.getStacksAsVaos());
        result.setFieldValue(IXpscacheBizMetafield.YRDM_SECTION_STACKS, null);
        result.setFieldValue(IXpscacheBizMetafield.YRDM_SECTION_FIRST_COLUMN, (Object) String.valueOf(this._firstColumn));
        result.setFieldValue(IXpscacheBizMetafield.YRDM_SECTION_LAST_COLUMN, (Object) String.valueOf(this._lastColumn));
        result.setFieldValue(IXpscacheBizMetafield.YRDM_SECTION_FIRST_TIER, (Object) String.valueOf(this._firstTier));
        result.setFieldValue(IXpscacheBizMetafield.YRDM_SECTION_LAST_TIER, (Object) String.valueOf(this._lastTier));
        result.setFieldValue(IXpscacheBizMetafield.YRDM_SECTION_ROADWAY_COLUMN, (Object) String.valueOf(this._roadwayColumn));
        result.setFieldValue(IXpscacheBizMetafield.YRDM_SECTION_ACCESS_BLOCKED_FIRST, (Object) String.valueOf(this._isAccessBlockedAtFirstColumn));
        result.setFieldValue(IXpscacheBizMetafield.YRDM_SECTION_ACCESS_BLOCKED_LAST, (Object) String.valueOf(this._isAccessBlockedAtLastColumn));
        return result;
    }

    @Override
    public List getStacksAsVaos() {
        ArrayList<ValueObject> result = new ArrayList<ValueObject>();
        for (Object stack : this._stackMap.values()) {
            ValueObject stackVao = ((AbstractXpsYardStack)stack).getInfoAsVao();
            result.add(stackVao);
        }
        return result;
    }

}
