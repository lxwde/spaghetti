package com.zpmc.ztos.infra.base.common.model;

import com.zpmc.ztos.infra.base.business.enums.framework.LetterCaseEnum;
import com.zpmc.ztos.infra.base.business.enums.framework.ValidationDataTypeEnum;
import com.zpmc.ztos.infra.base.business.interfaces.IMetafieldId;
import com.zpmc.ztos.infra.base.business.interfaces.IValidationModel;
import org.apache.commons.lang.StringUtils;

public class ValidationEntry extends DictionaryKeyedEntry implements IValidationModel {
    private ValidationDataTypeEnum _datatype = ValidationDataTypeEnum.UNSPECIFIED;
    private String _pattern;
    private String _alternatePatternErrorMsg;
    private String _minValue;
    private String _maxValue;
    private int _minLength = -1;
    private int _maxLength = -1;
    private LetterCaseEnum _case;

    public ValidationEntry(IMetafieldId inFieldId) {
        super(inFieldId);
    }

    public void setDataType(ValidationDataTypeEnum inDatatype) {
        this._datatype = inDatatype;
    }

    @Override
    public ValidationDataTypeEnum getDataType() {
        return this._datatype;
    }

    public void setPattern(String inPattern) {
        if (inPattern != null) {
            this._pattern = inPattern;
        }
    }

    @Override
    public String getPattern() {
        return this._pattern;
    }

    public void setMinValue(String inMinValueStr) {
        if (inMinValueStr != null) {
            this._minValue = inMinValueStr;
        }
    }

    @Override
    public String getMinValue() {
        return this._minValue;
    }

    public void setMaxValue(String inMaxValueStr) {
        if (inMaxValueStr != null) {
            this._maxValue = inMaxValueStr;
        }
    }

    @Override
    public String getMaxValue() {
        return this._maxValue;
    }

    public void setMinLength(int inMinLength) {
        if (inMinLength != -1) {
            this._minLength = inMinLength;
        }
    }

    @Override
    public int getMinLength() {
        return this._minLength;
    }

    public void setMaxLength(int inMaxLength) {
        if (inMaxLength != -1) {
            this._maxLength = inMaxLength;
        }
    }

    @Override
    public int getMaxLength() {
        return this._maxLength;
    }

    @Override
    public LetterCaseEnum getCase() {
        return this._case;
    }

    public void setCase(LetterCaseEnum inCase) {
        this._case = inCase;
    }

    @Override
    public String getAlternatePatternErrorMsg() {
        return this._alternatePatternErrorMsg;
    }

    public void setAlternatePatternErrorMsg(String inAlternatePatternErrorMsg) {
        this._alternatePatternErrorMsg = inAlternatePatternErrorMsg;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        try {
            buf.append("ValidationDataTypeEnum=" + (Object)((Object)this._datatype));
            if (this._case != null) {
                buf.append(",case=" + this._case.getName());
            }
            if (StringUtils.isNotEmpty((String)this._pattern)) {
                buf.append(",_pattern=" + this._pattern);
                if (StringUtils.isNotEmpty((String)this._alternatePatternErrorMsg)) {
                    buf.append("[errorMsg=" + this._alternatePatternErrorMsg + "]");
                }
            }
            if (StringUtils.isNotEmpty((String)this._minValue)) {
                buf.append(",_minValue=" + this._minValue);
            }
            if (StringUtils.isNotEmpty((String)this._maxValue)) {
                buf.append(",_maxValue=" + this._maxValue);
            }
        }
        catch (Exception e) {
            buf.append("error getting toString  " + e);
        }
        return buf.toString();
    }
}
