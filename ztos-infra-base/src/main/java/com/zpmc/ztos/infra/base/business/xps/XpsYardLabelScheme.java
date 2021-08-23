package com.zpmc.ztos.infra.base.business.xps;

import com.zpmc.ztos.infra.base.utils.StringUtils;

public class XpsYardLabelScheme {
    public static final int LABEL_FORMAT_TYPE_TIER = 0;
    public static final int LABEL_FORMAT_TYPE_COLUMN = 1;
    public static final int LABEL_FORMAT_TYPE_ROW = 2;
    public static final int LABEL_FORMAT_TYPE_BLOCK = 3;
    public static final int LABEL_FORMAT_TYPE_NONE = 4;
    public static final int LABEL_FORMAT_TYPE_SUBYARD = 5;
    public static final char AXIS_TIER = 'T';
    public static final char AXIS_COLUMN = 'C';
    public static final char AXIS_ROW = 'R';
    public static final char AXIS_BLOCK = 'B';
    public static final char AXIS_NONE = ' ';
    public static final char AXIS_SUBYARD = 'S';
    private static final char[] AXIS_CODE = new char[]{'T', 'C', 'R', 'B', ' ', 'S'};
    private StringBuffer _scheme = new StringBuffer(6);

    public void setLabelScheme(int inLableFormatType, int inCharCount, String inDelim) {
        char code = AXIS_CODE[inLableFormatType];
        if (code != ' ') {
            this._scheme.append(code);
            this._scheme.append(inCharCount);
            if (!StringUtils.isEmpty((String)inDelim)) {
                this._scheme.append(inDelim);
            }
        }
    }

    public String getFormattedYardPosition(String inSubYard, String inBlockName, String inRowName, String inColumnName, String inTierName) {
        StringBuffer buf = new StringBuffer();
        char[] separator = new char[1];
        int i = 0;
        block7: while (i < this._scheme.length()) {
            char formatChar = this._scheme.charAt(i++);
            switch (formatChar) {
                case 'S': {
                    this.add(inSubYard, i++, buf, separator);
                    continue block7;
                }
                case 'B': {
                    this.add(inBlockName, i++, buf, separator);
                    continue block7;
                }
                case 'R': {
                    this.add(inRowName, i++, buf, separator);
                    continue block7;
                }
                case 'C': {
                    this.add(inColumnName, i++, buf, separator);
                    continue block7;
                }
                case 'T': {
                    this.add(inTierName, i++, buf, separator);
                    continue block7;
                }
            }
            separator[0] = formatChar;
        }
        return buf.toString();
    }

    private void add(String inName, int inIndex, StringBuffer inBuf, char[] inSeparator) {
        if (StringUtils.isEmpty((String)inName)) {
            return;
        }
        if (inSeparator[0] != '\u0000') {
            inBuf.append(inSeparator[0]);
            inSeparator[0] = '\u0000';
        }
        int charCount = this._scheme.charAt(inIndex) - 48;
        for (int i = 0; i < charCount; ++i) {
            if (i >= inName.length()) continue;
            inBuf.append(inName.charAt(i));
        }
    }

    public String toString() {
        return "<" + this._scheme + ">";
    }
}
