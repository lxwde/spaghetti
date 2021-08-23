package com.zpmc.ztos.infra.base.common.utils.edi;

import com.zpmc.ztos.infra.base.utils.StringUtils;

import java.util.regex.Pattern;

public class StringUtil {
    public static String parse(String inString, int inIdx, String inDelimeter) {
        String[] results = null;
        if (inString != null && inString.trim().length() > 0) {
            results = StringUtils.splitPreserveAllTokens((String)inString, (String)inDelimeter);
            return results[inIdx];
        }
        return "";
    }

    public static boolean isEmpty(String inStr) {
        return inStr == null || inStr.trim().length() == 0;
    }

    public static boolean isNotEmpty(String inStr) {
        return inStr != null && inStr.trim().length() != 0;
    }

    public static String removeLastSpecialChar(String inStr) {
        String lastChar;
        if (StringUtils.isNotEmpty((String)inStr) && inStr.length() > 1 && Pattern.matches("\\W", lastChar = inStr.substring(inStr.length() - 1, inStr.length()))) {
            return inStr.substring(0, inStr.length() - 1);
        }
        return inStr;
    }
}
