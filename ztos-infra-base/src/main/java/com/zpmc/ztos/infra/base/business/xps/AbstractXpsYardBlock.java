package com.zpmc.ztos.infra.base.business.xps;

import com.zpmc.ztos.infra.base.business.enums.argo.EquipBasicLengthEnum;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.common.model.ValueObject;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.io.Serializable;
import java.util.*;

public abstract class AbstractXpsYardBlock extends AbstractParentXpsYardBin implements IXpsYardBlock {
    private static final Map<Integer, String> BLOCK_TYPE_DESCRIPTION;
    private final IXpsYardModel _parentYardModel;
    private final String _blockCode;
    private final int _blockType;
    private final List _sectionList;
    private XpsYardLabelScheme _labelSchemeHost;
    private XpsYardLabelScheme _labelSchemeUIFullPosition;
    private XpsYardLabelScheme _labelSchemeUIRowOnly;
    private XpsYardLabelScheme _labelSchemeUIColumnOnly;
    private XpsYardLabelScheme _labelSchemeUITierOnly;
    private final int _tierConvTableId;
    private int _teuCapacity;
    private Character _trafficFlow;
    private int _anchorHorizontal;
    private int _anchorVertical;
    private int _angle;
    private String _codeChars;

    public AbstractXpsYardBlock(XpsYardModel inYardModel, int inBlockType, String inBlockCode, int inRowConvId20, int inRowConvId40, int inTierConvId) {
        super(inYardModel, inRowConvId20, inRowConvId40, 0);
        XpsYardLabelScheme defaultLabelScheme;
        this._parentYardModel = inYardModel;
        this._sectionList = new ArrayList();
        this._blockCode = inBlockCode;
        this._blockType = inBlockType;
        this._tierConvTableId = inTierConvId;
        this._labelSchemeUITierOnly = defaultLabelScheme = new XpsYardLabelScheme();
        this._labelSchemeUIColumnOnly = defaultLabelScheme;
        this._labelSchemeUIRowOnly = defaultLabelScheme;
        this._labelSchemeUIFullPosition = defaultLabelScheme;
        XpsYardLabelScheme defaultHostLabelScheme = new XpsYardLabelScheme();
        defaultHostLabelScheme.setLabelScheme(3, inBlockCode.length(), "");
        this._labelSchemeHost = defaultHostLabelScheme;
    }

    @Override
    public void computeBinNames(Map inUserConvTableMap) {
        this.setCoordinateName20(this.getBlockName());
        this.setCoordinateName40(this.getBlockName());
    }

    @Override
    public String getBlockName() {
        return this._blockCode;
    }

    @Override
    public String getRowName(EquipBasicLengthEnum inBasicLength) {
        return "";
    }

    @Override
    public String getColumnName(EquipBasicLengthEnum inBasicLength) {
        return "";
    }

    @Override
    public int getBlockType() {
        return this._blockType;
    }

    @Override
    public boolean isWheeled() {
        return 3 == this.getBlockType() || 11 == this.getBlockType();
    }

    @Override
    public boolean isWheeledOrYardTransTZ() {
        return this.isWheeled() || 19 == this.getBlockType();
    }

    @Override
    public boolean isGrounded() {
        switch (this.getBlockType()) {
            case 1: {
                return true;
            }
            case 7: {
                return true;
            }
            case 6: {
                return true;
            }
            case 9: {
                return true;
            }
            case 5: {
                return true;
            }
            case 18: {
                return true;
            }
        }
        return false;
    }

    public IXpsYardModel getYardModel() {
        return this._parentYardModel;
    }

    @Override
    public IXpsYardBlock getBlock() {
        return this;
    }

    public IXpsYardSection getSection() {
        return null;
    }

    public IXpsYardStack getStack() {
        return null;
    }

    public void addSection(AbstractXpsYardSection inSection) {
        this._sectionList.add(inSection);
    }

    public void removeSection(AbstractXpsYardSection inSection) {
        this._sectionList.remove(inSection);
    }

    public List getSections() {
        return Collections.unmodifiableList(this._sectionList);
    }

    @Nullable
    public XpsYardSection getSectionAtRow(Long inRow) {
        for (Object section : this._sectionList) {
            if ((long)((XpsYardSection)section).getSectionRowIndex() != inRow) continue;
            return (XpsYardSection)section;
        }
        return null;
    }

    public int getTEUCapacity() {
        return this._teuCapacity;
    }

    public void setTEUCapacity(int inTEUs) {
        this._teuCapacity = inTEUs;
    }

    @Override
    public Character getTrafficFlow() {
        return this._trafficFlow;
    }

    public void setTrafficFlow(Character inFlow) {
        this._trafficFlow = inFlow;
    }

    @Override
    public boolean isTransferZone() {
        return false;
    }

    public void setLabelSchemeUITierOnly(XpsYardLabelScheme inLabelScheme) {
        this._labelSchemeUITierOnly = inLabelScheme;
    }

    public XpsYardLabelScheme getLabelSchemeUiTierOnly() {
        return this._labelSchemeUITierOnly;
    }

    public void setLabelSchemeUIColumnOnly(XpsYardLabelScheme inLabelScheme) {
        this._labelSchemeUIColumnOnly = inLabelScheme;
    }

    public XpsYardLabelScheme getLabelSchemeUiColumnOnly() {
        return this._labelSchemeUIColumnOnly;
    }

    public void setLabelSchemeUIRowOnly(XpsYardLabelScheme inLabelScheme) {
        this._labelSchemeUIRowOnly = inLabelScheme;
    }

    public XpsYardLabelScheme getLabelSchemeUiRowOnly() {
        return this._labelSchemeUIRowOnly;
    }

    public void setLabelSchemeUIFullPosition(XpsYardLabelScheme inLabelScheme) {
        this._labelSchemeUIFullPosition = inLabelScheme;
    }

    public XpsYardLabelScheme getLabelSchemeUiFullPosition() {
        return this._labelSchemeUIFullPosition;
    }

    public XpsYardLabelScheme getLabelSchemeBinName() {
        return this._labelSchemeHost;
    }

    public void setLabelSchemeBinName(XpsYardLabelScheme inLabelScheme) {
        this._labelSchemeHost = inLabelScheme;
    }

    @Override
    public int getTierConvTableId() {
        return this._tierConvTableId;
    }

    @Override
    public String getConvertedTierName(int inTierNumber) {
        return String.valueOf(inTierNumber);
    }

    public int getAnchorHorizontal() {
        return this._anchorHorizontal;
    }

    public void setAnchorHorizontal(int inAnchorHorizontal) {
        this._anchorHorizontal = inAnchorHorizontal;
    }

    public int getAnchorVertical() {
        return this._anchorVertical;
    }

    public void setAnchorVertical(int inAnchorVertical) {
        this._anchorVertical = inAnchorVertical;
    }

    public int getAngle() {
        return this._angle;
    }

    public void setAngle(int inAngle) {
        this._angle = inAngle;
    }

    public String getCodeChars() {
        return this._codeChars;
    }

    public void setCodeChars(String inCodeChars) {
        this._codeChars = inCodeChars;
    }

    public ValueObject getInfoAsVao() {
        ValueObject result = new ValueObject("Block");
        result.setEntityPrimaryKey((Serializable)((Object)this.getBinName(EquipBasicLengthEnum.BASIC20)));
        result.setFieldValue(IXpscacheBizMetafield.YRDM_BIN_TYPE, (Object)"Block");
        result.setFieldValue(IXpscacheBizMetafield.YRDM_BIN_BIN_NAME20, (Object)this.getBinName(EquipBasicLengthEnum.BASIC20));
        result.setFieldValue(IXpscacheBizMetafield.YRDM_BIN_BIN_NAME40, (Object)this.getBinName(EquipBasicLengthEnum.BASIC40));
        result.setFieldValue(IXpscacheBizMetafield.YRDM_BIN_UI_FULL_POSITION20, (Object)this.getUiFullPosition(EquipBasicLengthEnum.BASIC20));
        result.setFieldValue(IXpscacheBizMetafield.YRDM_BIN_UI_FULL_POSITION40, (Object)this.getUiFullPosition(EquipBasicLengthEnum.BASIC40));
        result.setFieldValue(IXpscacheBizMetafield.YRDM_BLOCK_CODE, (Object)this._blockCode);
        result.setFieldValue(IXpscacheBizMetafield.YRDM_BLOCK_TYPE, (Object) AbstractXpsYardBlock.yardBlockTypeToString(this._blockType));
        result.setFieldValue(IXpscacheBizMetafield.YRDM_BLOCK_SECTIONS, (Object)this.getSectionsAsVaos());
        result.setFieldValue(IXpscacheBizMetafield.YRDM_LABEL_SCHEME_UI_COLUMN_ONLY, (Object)this._labelSchemeUIColumnOnly.toString());
        result.setFieldValue(IXpscacheBizMetafield.YRDM_LABEL_SCHEME_UI_ROW_ONLY, (Object)this._labelSchemeUIRowOnly.toString());
        result.setFieldValue(IXpscacheBizMetafield.YRDM_LABEL_SCHEME_UI_FULL_POSITION, (Object)this._labelSchemeUIFullPosition.toString());
        result.setFieldValue(IXpscacheBizMetafield.YRDM_LABEL_SCHEME_UI_TIER_ONLY, (Object)this._labelSchemeUITierOnly.toString());
        result.setFieldValue(IXpscacheBizMetafield.YRDM_LABEL_SCHEME_BIN_NAME, (Object)this._labelSchemeHost.toString());
        result.setFieldValue(IXpscacheBizMetafield.YRDM_BLOCK_TEU_CAPACITY, (Object) String.valueOf(this._teuCapacity));
        result.setFieldValue(IXpscacheBizMetafield.YRDM_BLOCK_TRAFFIC_FLOW, (Object) String.valueOf(this._trafficFlow));
        result.setFieldValue(IXpscacheBizMetafield.YRDM_BLOCK_FIRST_ROW, (Object)"");
        result.setFieldValue(IXpscacheBizMetafield.YRDM_BLOCK_LAST_ROW, (Object)"");
        result.setFieldValue(IXpscacheBizMetafield.YRDM_BLOCK_MAX_HEIGHT, (Object)"");
        result.setFieldValue(IXpscacheBizMetafield.YRDM_BLOCK_MAX_WEIGHT, (Object)"");
        result.setFieldValue(IXpscacheBizMetafield.YRDM_BLOCK_TIER_COUNT, (Object)"");
        result.setFieldValue(IXpscacheBizMetafield.YRDM_BLOCK_ROW_CONV_ID20, (Object)"");
        result.setFieldValue(IXpscacheBizMetafield.YRDM_BLOCK_ROW_CONV_ID40, (Object)"");
        result.setFieldValue(IXpscacheBizMetafield.YRDM_BLOCK_TIER_CONV_ID, (Object)"");
        result.setFieldValue(IXpscacheBizMetafield.YRDM_BLOCK_INVERT_X, (Object)"");
        result.setFieldValue(IXpscacheBizMetafield.YRDM_BLOCK_INVERT_Y, (Object)"");
        result.setFieldValue(IXpscacheBizMetafield.YRDM_BLOCK_ROTATE90, (Object)"");
        result.setFieldValue(IXpscacheBizMetafield.YRDM_BLOCK_ANCHOR_HORIZONTAL, (Object) String.valueOf(this._anchorHorizontal));
        result.setFieldValue(IXpscacheBizMetafield.YRDM_BLOCK_ANCHOR_VERTICAL, (Object) String.valueOf(this._anchorVertical));
        result.setFieldValue(IXpscacheBizMetafield.YRDM_BLOCK_ANGLE, (Object) String.valueOf(this._angle));
        result.setFieldValue(IXpscacheBizMetafield.YRDM_BLOCK_CODE_CHARS, (Object) String.valueOf(this._codeChars));
        return result;
    }

    public List getSectionsAsVaos() {
        ArrayList<ValueObject> result = new ArrayList<ValueObject>();
        for (Object section : this._sectionList) {
            ValueObject sectionVao = ((IXpsYardSection)section).getInfoAsVao();
            result.add(sectionVao);
        }
        return result;
    }

    @Override
    public boolean isValidTierName(String inTierName) {
        return true;
    }

    @Override
    public int convertTierNameToTier(String inTierName) {
        return 0;
    }

    public static String yardBlockTypeToString(int inBlockType) {
        String result = BLOCK_TYPE_DESCRIPTION.get(inBlockType);
        return result != null ? result : "unknown";
    }

    @Override
    public boolean isBlockTypeAscOrCarmg() {
        return 19 == this.getBlockType() || 7 == this.getBlockType();
    }

    @Override
    public boolean isBlockTypeStraddle() {
        return 5 == this.getBlockType() || 18 == this.getBlockType();
    }

    static {
        HashMap<Integer, String> blockTypeDescriptionMap = new HashMap<Integer, String>();
        blockTypeDescriptionMap.put(0, "Unknown");
        blockTypeDescriptionMap.put(1, "Fork");
        blockTypeDescriptionMap.put(2, "Bldg");
        blockTypeDescriptionMap.put(3, "Wheeled");
        blockTypeDescriptionMap.put(4, "Logical");
        blockTypeDescriptionMap.put(5, "Strad");
        blockTypeDescriptionMap.put(6, "RTG");
        blockTypeDescriptionMap.put(7, "ASC");
        blockTypeDescriptionMap.put(8, "Rail");
        blockTypeDescriptionMap.put(9, "Stk Heap");
        blockTypeDescriptionMap.put(10, "Buf Heap");
        blockTypeDescriptionMap.put(11, "Whl Heap");
        blockTypeDescriptionMap.put(12, "Whse In");
        blockTypeDescriptionMap.put(13, "Whse Out");
        blockTypeDescriptionMap.put(14, "Whsr Heap In");
        blockTypeDescriptionMap.put(15, "Whse Heap Out");
        blockTypeDescriptionMap.put(16, "Buf Para");
        blockTypeDescriptionMap.put(17, "Buf Serial");
        blockTypeDescriptionMap.put(18, "Straddle Grid");
        blockTypeDescriptionMap.put(19, "Yard Trans TZ");
        BLOCK_TYPE_DESCRIPTION = Collections.unmodifiableMap(blockTypeDescriptionMap);
    }
}
