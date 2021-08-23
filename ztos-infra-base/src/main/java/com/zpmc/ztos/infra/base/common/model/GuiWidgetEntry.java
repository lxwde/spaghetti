package com.zpmc.ztos.infra.base.common.model;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import com.zpmc.ztos.infra.base.business.enums.framework.GuiWidgetSubtypeEnum;
import com.zpmc.ztos.infra.base.business.enums.framework.GuiWidgetTypeEnum;
import com.zpmc.ztos.infra.base.business.interfaces.ILovKey;
import com.zpmc.ztos.infra.base.business.interfaces.IMetafieldId;
import com.zpmc.ztos.infra.base.business.model.MetafieldIdFactory;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class GuiWidgetEntry extends DictionaryKeyedEntry{
    private GuiWidgetTypeEnum _type;
    private GuiWidgetSubtypeEnum _subType;
    private int _widthInChars = -1;
    private int _height = 1;
    private ILovKey _lovKey;
    private LovExtraEntry _lovExtraEntry;
    private ChartInfo _chartInfo;
    private String _bean;
    private String _variformId;
    private String _imagePath;
    private int _fractionalDigitsMin;
    private int _fractionalDigitsMax;
    private Map _attributes;

    public GuiWidgetEntry(String inFieldId) {
        super(inFieldId);
    }

    public GuiWidgetEntry(IMetafieldId inFieldId) {
        super(inFieldId);
    }

    public GuiWidgetTypeEnum getType() {
        return this._type;
    }

    public void setType(GuiWidgetTypeEnum inType) {
        this._type = inType;
    }

    public GuiWidgetSubtypeEnum getSubType() {
        return this._subType;
    }

    public void setSubType(GuiWidgetSubtypeEnum inSubType) {
        this._subType = inSubType;
    }

    public int getWidthInChars() {
        return this._widthInChars;
    }

    public void setWidthInChars(int inWidthInChars) {
        this._widthInChars = inWidthInChars;
    }

    public int getHeight() {
        return this._height;
    }

    public void setHeight(int inHeight) {
        this._height = inHeight;
    }

    @Deprecated
    @Nullable
    public String getLovCollectionKey() {
        if (this._lovKey != null) {
            return this._lovKey.getKey();
        }
        return null;
    }

    public void setLovCollectionKey(String inLovCollectionKey) {
        this._lovKey = LovKeyFactory.valueOf(inLovCollectionKey);
    }

    public ILovKey getLovKey() {
        return this._lovKey;
    }

    @Nullable
    public String getLovExtraEntry() {
        if (this._lovExtraEntry == null) {
            return null;
        }
        return this._lovExtraEntry.toString();
    }

    public void setLovExtraEntry(String inLovExtraEntry) {
        this._lovExtraEntry = LovExtraEntry.getExtraEntry(inLovExtraEntry);
    }

    public void setLovExtraEntry(LovExtraEntry inLovExtraEntry) {
        this._lovExtraEntry = inLovExtraEntry;
    }

    public void setFractionalDigits(int inFractionalDigits) {
        this._fractionalDigitsMin = inFractionalDigits;
        this._fractionalDigitsMax = inFractionalDigits;
    }

    public int getFractionalDigitsMax() {
        return this._fractionalDigitsMax;
    }

    public void setFractionalDigitsMax(int inFractionalDigitsMax) {
        this._fractionalDigitsMax = inFractionalDigitsMax;
    }

    public int getFractionalDigitsMin() {
        return this._fractionalDigitsMin;
    }

    public void setFractionalDigitsMin(int inFractionalDigitsMin) {
        this._fractionalDigitsMin = inFractionalDigitsMin;
    }

    public String getBean() {
        return this._bean;
    }

    public void setBean(String inBean) {
        this._bean = inBean;
    }

    public String getVariformId() {
        return this._variformId;
    }

    public void setVariformId(String inVariformId) {
        this._variformId = inVariformId;
    }

    public String getImagePath() {
        return this._imagePath;
    }

    public void setImagePath(String inImagePath) {
        this._imagePath = inImagePath;
    }

    @Nullable
    public ChartInfo getChartInfo() {
        return this._chartInfo;
    }

    public void setChartInfo(ChartInfo inChartInfo) {
        this._chartInfo = inChartInfo;
    }

    public void merge(GuiWidgetEntry inGuiWidget) {
        if (inGuiWidget.getType() != null) {
            this._type = inGuiWidget.getType();
        }
        if (inGuiWidget.getSubType() != null) {
            this._subType = inGuiWidget.getSubType();
        }
        if (inGuiWidget.getWidthInChars() >= 0) {
            this._widthInChars = inGuiWidget.getWidthInChars();
        }
        if (inGuiWidget.getHeight() > 1) {
            this._height = inGuiWidget.getHeight();
        }
        if (inGuiWidget.getLovKey() != null) {
            this._lovKey = inGuiWidget.getLovKey();
        }
        if (inGuiWidget.getLovExtraEntry() != null) {
            this._lovExtraEntry = inGuiWidget._lovExtraEntry;
        }
        if (inGuiWidget.getFractionalDigitsMin() != 0) {
            this._fractionalDigitsMin = inGuiWidget.getFractionalDigitsMin();
        }
        if (inGuiWidget.getFractionalDigitsMax() != 0) {
            this._fractionalDigitsMax = inGuiWidget.getFractionalDigitsMax();
        }
        if (inGuiWidget.getBean() != null) {
            this._bean = inGuiWidget.getBean();
        }
        if (inGuiWidget.getVariformId() != null) {
            this._variformId = inGuiWidget.getVariformId();
        }
        if (inGuiWidget.getImagePath() != null) {
            this._imagePath = inGuiWidget.getImagePath();
        }
    }

    public void setAttribute(String inAttribute, String inValue) {
        if (this._attributes == null) {
            this._attributes = new HashMap();
        }
        this._attributes.put(inAttribute, inValue);
    }

    @Nullable
    public String getAttribute(String inAttribute) {
        if (this._attributes != null) {
            return (String)this._attributes.get(inAttribute);
        }
        return null;
    }

    @NotNull
    public Map<String, String> getAttributes() {
        if (this._attributes == null) {
            this._attributes = new HashMap();
        }
        return this._attributes;
    }

    public String getAttributeAsString(String inAttribute, String inDefaultValue) {
        String value;
        if (this._attributes != null && (value = (String)this._attributes.get(inAttribute)) != null) {
            return value;
        }
        return inDefaultValue;
    }

    @Nullable
    public IMetafieldId getAttributeAsMetafield(String inAttribute) {
        String value = this.getAttribute(inAttribute);
        if (StringUtils.isNotEmpty((String)value)) {
            return MetafieldIdFactory.valueOf(value);
        }
        return null;
    }

    public int getAttributeAsInt(String inAttribute, int inDefaultValue) {
        String value;
        if (this._attributes != null && (value = (String)this._attributes.get(inAttribute)) != null) {
            return Integer.parseInt(value);
        }
        return inDefaultValue;
    }

    public boolean getAttributeAsBoolean(String inAttribute, boolean inDefaultValue) {
        String value;
        if (this._attributes != null && (value = (String)this._attributes.get(inAttribute)) != null) {
            return Boolean.valueOf(value);
        }
        return inDefaultValue;
    }

    @Override
    public String toString() {
        return "widget=" + (Object)((Object)this._type) + ",subtype=" + (Object)((Object)this._subType);
    }

    public static class ChartInfo {
        private String _variformId;
        private int _width;
        private int _height;
        private Map<IMetafieldId, IMetafieldId> _filter;
        private Map<String, String> _attributes;

        public ChartInfo(String inVariformId) {
            this._variformId = inVariformId;
        }

        public String getVariformId() {
            return this._variformId;
        }

        public Map<IMetafieldId, IMetafieldId> getFilter() {
            return this._filter;
        }

        public void setFilter(Map<IMetafieldId, IMetafieldId> inFilter) {
            this._filter = inFilter;
        }

        public int getWidth() {
            return this._width;
        }

        public void setWidth(int inWidth) {
            this._width = inWidth;
        }

        public int getHeight() {
            return this._height;
        }

        public void setHeight(int inHeight) {
            this._height = inHeight;
        }

        public Map<String, String> getAttributes() {
            if (this._attributes == null) {
                this._attributes = new HashMap<String, String>();
            }
            return this._attributes;
        }

        public void setAttributes(Map<String, String> inAttributes) {
            this._attributes = inAttributes;
        }

        @Nullable
        public String getAttribute(String inAttribute) {
            if (this._attributes != null) {
                return this._attributes.get(inAttribute);
            }
            return null;
        }
    }
}
