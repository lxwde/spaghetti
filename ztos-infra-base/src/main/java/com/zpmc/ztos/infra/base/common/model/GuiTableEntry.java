package com.zpmc.ztos.infra.base.common.model;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import com.zpmc.ztos.infra.base.business.enums.road.ColumnPlacementEnum;
import com.zpmc.ztos.infra.base.business.interfaces.IMetafieldId;

import java.util.HashMap;
import java.util.Map;

public class GuiTableEntry extends DictionaryKeyedEntry{
    private ColumnPlacementEnum _hAlignment;
    private Integer _maxFractionalDigits;
    private boolean _allowCustomDecimalRounding;
    private Map<String, String> _attributes;
    private Map<String, String> _imageAttributes;
    private boolean _isDisplayedAsImage;

    public GuiTableEntry(String inFieldId) {
        super(inFieldId);
    }

    public GuiTableEntry(IMetafieldId inFieldId) {
        super(inFieldId);
    }

    public ColumnPlacementEnum getHorizontalAlignment() {
        return this._hAlignment;
    }

    public void setHorizontalAlignment(ColumnPlacementEnum inAlignment) {
        this._hAlignment = inAlignment;
    }

    public Integer getMaxFractionalDigits() {
        return this._maxFractionalDigits;
    }

    public void setMaxFractionalDigits(Integer inDigits) {
        this._maxFractionalDigits = inDigits;
    }

    public boolean isAllowCustomDecimalRounding() {
        return this._allowCustomDecimalRounding;
    }

    public void setAllowCustomDecimalRounding(boolean inAllowCustomDecimalRounding) {
        this._allowCustomDecimalRounding = inAllowCustomDecimalRounding;
    }

    public void merge(GuiTableEntry inGuiTable) {
        if (inGuiTable.getHorizontalAlignment() != null) {
            this._hAlignment = inGuiTable.getHorizontalAlignment();
        }
        if (inGuiTable.getMaxFractionalDigits() != null) {
            this._maxFractionalDigits = inGuiTable.getMaxFractionalDigits();
        }
    }

    public void setAttribute(String inAttribute, String inValue) {
        if (this._attributes == null) {
            this._attributes = new HashMap<String, String>();
        }
        this._attributes.put(inAttribute, inValue);
    }

    @Nullable
    public String getAttribute(String inAttribute) {
        if (this._attributes != null) {
            return this._attributes.get(inAttribute);
        }
        return null;
    }

    @NotNull
    public Map<String, String> getAttributes() {
        if (this._attributes == null) {
            this._attributes = new HashMap<String, String>();
        }
        return this._attributes;
    }

    public Map<String, String> getImageAttributes() {
        if (this._imageAttributes == null) {
            this._imageAttributes = new HashMap<String, String>();
        }
        return this._imageAttributes;
    }

    public void setImageAttribute(String inAttribute, String inValue) {
        if (this._imageAttributes == null) {
            this._imageAttributes = new HashMap<String, String>();
        }
        this._imageAttributes.put(inAttribute, inValue);
    }

    public boolean isDisplayedAsImage() {
        return this._isDisplayedAsImage;
    }

    public void setDisplayedAsImage(boolean inDisplayedAsImage) {
        this._isDisplayedAsImage = inDisplayedAsImage;
    }
}
