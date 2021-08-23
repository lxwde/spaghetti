package com.zpmc.ztos.infra.base.business.edi;

public class EdiRule {
    private Integer _elementPosition;
    private Integer _segmentPosition;
    private Integer _subElementPosition;
    private Integer _minLength;
    private Integer _maxLength;
    private Integer _segmentOccurences;
    private String _matchValue;
    private String _segmentName;
    private String _ruleName;

    public EdiRule(String inRuleName, String inSegmentName, Integer inSegmentPosition, Integer inElementPosition, Integer inSubElementPosition, Integer inMinLength, Integer inMaxLength, Integer inSegmentOccurences, String inMatchValue) {
        if (inRuleName == null || inSegmentName == null) {
            throw new IllegalArgumentException("Missing: Rule/Segment Name");
        }
        this._ruleName = inRuleName;
        this._segmentName = inSegmentName;
        this._segmentPosition = inSegmentPosition == null ? new Integer(1) : inSegmentPosition;
        this._elementPosition = inElementPosition;
        this._subElementPosition = inSubElementPosition == null || inSubElementPosition == 0 ? new Integer(0) : inSubElementPosition;
        this._minLength = inMinLength;
        this._maxLength = inMaxLength;
        this._segmentOccurences = inSegmentOccurences;
        this._matchValue = inMatchValue;
    }

    public String getSegmentName() {
        return this._segmentName;
    }

    public Integer getElementPosition() {
        return this._elementPosition;
    }

    public Integer getSubElementPosition() {
        return this._subElementPosition;
    }

    public Integer getMinLength() {
        return this._minLength;
    }

    public Integer getSegmentPosition() {
        return this._segmentPosition;
    }

    public String getRuleName() {
        return this._ruleName;
    }

    public Integer getMaxLength() {
        return this._maxLength;
    }

    public String getMatchValue() {
        return this._matchValue;
    }

    public Integer getSegmentOccurences() {
        return this._segmentOccurences;
    }

    public String toString() {
        StringBuffer strValue = new StringBuffer("{ Rule Name : " + this._ruleName);
        if (this._segmentName != null) {
            strValue.append(" | Segment Name : " + this._segmentName);
        }
        if (this._segmentPosition != null) {
            strValue.append(" | Segment Position : " + this._segmentPosition);
        }
        if (this._elementPosition != null) {
            strValue.append(" | Element Position : " + this._elementPosition);
        }
        if (this._subElementPosition != null) {
            strValue.append(" | Sub Element Position : " + this._subElementPosition);
        }
        if (this._matchValue != null) {
            strValue.append(" | Match Value : " + this._matchValue);
        }
        if (this._minLength != null) {
            strValue.append(" | Min. Length : " + this._minLength);
        }
        if (this._maxLength != null) {
            strValue.append(" | Max. Length : " + this._maxLength);
        }
        if (this._segmentOccurences != null) {
            strValue.append(" | Segment Occurrences : " + this._segmentOccurences);
        }
        strValue.append(" }");
        return strValue.toString();
    }
}
