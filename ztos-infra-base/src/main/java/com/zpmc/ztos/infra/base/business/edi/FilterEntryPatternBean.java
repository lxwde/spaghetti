package com.zpmc.ztos.infra.base.business.edi;

import com.zpmc.ztos.infra.base.common.utils.edi.XmlUtil;

import java.util.regex.Pattern;

public class FilterEntryPatternBean {
    private Pattern _pattern;
    private String _patternExpression;
    private String _fromValue;
    private String _toValue;
    private String _fieldIdValue;

    public FilterEntryPatternBean(String inFieldIdValue, String inFromValue, String inToValue) {
        this._fieldIdValue = inFieldIdValue;
        this._fromValue = inFromValue;
        this._toValue = inToValue;
        this._patternExpression = this.getPatternExpression(inFieldIdValue, inFromValue);
        this._pattern = this.getPattern(inFieldIdValue, inFromValue);
    }

    public String getPatternExpression() {
        return this._patternExpression;
    }

    public String getFromValue() {
        return this._fromValue;
    }

    public String getToValue() {
        return this._toValue;
    }

    public Pattern getPattern() {
        return this._pattern;
    }

    public String getFieldIdValue() {
        return this._fieldIdValue;
    }

    public String getPatternExpression(String inFieldIdValue, String inFromValue) {
        return XmlUtil.getPatternExpression(inFieldIdValue, inFromValue);
    }

    public Pattern getPattern(String inFieldIdValue, String inFromValue) {
        return XmlUtil.getPattern(inFieldIdValue, inFromValue);
    }
}
