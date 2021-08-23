package com.zpmc.ztos.infra.base.business.xps;

import com.zpmc.ztos.infra.base.business.enums.argo.EquipBasicLengthEnum;
import com.zpmc.ztos.infra.base.business.interfaces.IXpsYardBlock;
import com.zpmc.ztos.infra.base.business.interfaces.IXpsYardSection;
import com.zpmc.ztos.infra.base.business.interfaces.IXpsYardStack;
import com.zpmc.ztos.infra.base.business.interfaces.IXpscacheBizMetafield;
import com.zpmc.ztos.infra.base.common.model.ValueObject;

import java.io.Serializable;
import java.util.Map;

public abstract class AbstractXpsYardStack extends AbstractXpsYardBin implements IXpsYardStack {
    private AbstractXpsYardSection _parentSection;
    private int _columnIndex;
    private int _firstTier;
    private int _lastTier;

    public AbstractXpsYardStack(AbstractXpsYardSection inParentSection, int inColumnIndex, int inFirstTier, int inLastTier) {
        super(inParentSection);
        this._parentSection = inParentSection;
        this._columnIndex = inColumnIndex;
        this._firstTier = inFirstTier;
        this._lastTier = inLastTier;
    }

    @Override
    public void computeBinNames(Map inUserConvTableMap) {
        this.setCoordinateName20(this.lookupCoordinateName20(inUserConvTableMap, this._columnIndex));
        this.setCoordinateName40(this.lookupCoordinateName40(inUserConvTableMap, this._columnIndex));
    }

    @Override
    public String getRowName(EquipBasicLengthEnum inBasicLength) {
        AbstractXpsYardSection yardSection = (AbstractXpsYardSection)this.getSection();
        return yardSection.getRowName(inBasicLength);
    }

    @Override
    public String getColumnName(EquipBasicLengthEnum inBasicLength) {
        return this.getCoordinateName(inBasicLength);
    }

    public IXpsYardSection getParentSection() {
        return this._parentSection;
    }

    @Override
    public IXpsYardBlock getBlock() {
        return this.getParent().getBlock();
    }

    public IXpsYardSection getSection() {
        return this._parentSection;
    }

    public IXpsYardStack getStack() {
        return this;
    }

    public int getStackColumnIndex() {
        return this._columnIndex;
    }

    public int getFirstTier() {
        return this._firstTier;
    }

    public void setFirstTier(int inFirstTier) {
        this._firstTier = inFirstTier;
    }

    public int getLastTier() {
        return this._lastTier;
    }

    @Override
    public boolean isPositionDisabled() {
        XpsStackBlock block = (XpsStackBlock)this.getBlock();
        return block.isPositionDisabled(this.getSection().getSectionRowIndex(), this.getStackColumnIndex());
    }

    @Override
    public ValueObject getInfoAsVao() {
        ValueObject result = new ValueObject("Stack");
        result.setEntityPrimaryKey((Serializable)((Object)this.getBinName(EquipBasicLengthEnum.BASIC20)));
        result.setFieldValue(IXpscacheBizMetafield.YRDM_BIN_TYPE, (Object)"Stack");
        result.setFieldValue(IXpscacheBizMetafield.YRDM_BIN_BIN_NAME20, (Object)this.getBinName(EquipBasicLengthEnum.BASIC20));
        result.setFieldValue(IXpscacheBizMetafield.YRDM_BIN_BIN_NAME40, (Object)this.getBinName(EquipBasicLengthEnum.BASIC40));
        result.setFieldValue(IXpscacheBizMetafield.YRDM_BIN_UI_FULL_POSITION20, (Object)this.getUiFullPosition(EquipBasicLengthEnum.BASIC20));
        result.setFieldValue(IXpscacheBizMetafield.YRDM_BIN_UI_FULL_POSITION40, (Object)this.getUiFullPosition(EquipBasicLengthEnum.BASIC40));
        result.setFieldValue(IXpscacheBizMetafield.YRDM_STACK_NAME20, (Object)this.getColumnName(EquipBasicLengthEnum.BASIC20));
        result.setFieldValue(IXpscacheBizMetafield.YRDM_STACK_NAME40, (Object)this.getColumnName(EquipBasicLengthEnum.BASIC40));
        result.setFieldValue(IXpscacheBizMetafield.YRDM_STACK_COLUMN, (Object) String.valueOf(this._columnIndex));
        result.setFieldValue(IXpscacheBizMetafield.YRDM_STACK_FIRST_TIER, (Object) String.valueOf(this._firstTier));
        result.setFieldValue(IXpscacheBizMetafield.YRDM_STACK_LAST_TIER, (Object) String.valueOf(this._lastTier));
        return result;
    }
}
