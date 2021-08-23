package com.zpmc.ztos.infra.base.common.utils.edi;

import com.zpmc.ztos.infra.base.business.enums.argo.EdiMessageClassEnum;

import java.util.HashMap;
import java.util.Map;

public class EdifactUtil {
    public static String EDIFACT_DELIMETERS = ":+.?'";
    public static String EDIFACT_MESSAGEHEADER = "UNH";
    public static String EDIFACT_MESSAGETRAILER = "UNT";
    public static String EDIFACT_INTERCHANGEHEADER = "UNB";
    public static String EDIFACT_INTERCHANGETRAILER = "UNZ";
    public static String EDIFACT_DELIMETERHEADER = "UNA";
    public static String EDIFACT_GROUPHEADER = "UNG";
    public static String EDIFACT_BGMHEADER = "BGM";
    public static char EDIFACT_ESCAPE_CHARACTER = (char)63;
    private static final Map<String, EdiMessageClassEnum> MSGCLASS = new HashMap<String, EdiMessageClassEnum>();

    public static String parse(String inString, int inIdx, int inSubIdx) {
        try {
            return EdifactUtil.parse(inString, inIdx, inSubIdx, EDIFACT_DELIMETERS);
        }
        catch (ArrayIndexOutOfBoundsException e) {
            return "";
        }
    }

    public static String parse(String inString, int inIdx, int inSubIdx, String inDelimeters) {
        String field = StringUtil.parse(inString, inIdx, inDelimeters.substring(1, 2));
        return StringUtil.parse(field, inSubIdx, inDelimeters.substring(0, 1));
    }

    public static Map<String, EdiMessageClassEnum> getMsgclass() {
        return MSGCLASS;
    }

    static {
        MSGCLASS.put("43", EdiMessageClassEnum.DISCHLIST);
        MSGCLASS.put("45", EdiMessageClassEnum.LOADLIST);
    }
}
