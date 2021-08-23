package com.zpmc.ztos.infra.base.common.utils.edi;

public class X12Util {
    public static String X12_DELIMETERS = "*~";
    public static String X12_MESSAGEHEADER = "ST";
    public static String X12_MESSAGETRAILER = "SE";
    public static String X12_INTERCHANGEHEADER = "ISA";
    public static String X12_INTERCHANGETRAILER = "IEA";
    public static String X12_FUNCTIONALGROUPHEADER = "GS";

    public static String parse(String inString, int inIdx) {
        return StringUtil.parse(inString, inIdx, X12_DELIMETERS.substring(0, 1));
    }

    public static String parse(String inString, int inIdx, String inDelimeters) {
        return StringUtil.parse(inString, inIdx, inDelimeters.substring(1, 2));
    }
}
