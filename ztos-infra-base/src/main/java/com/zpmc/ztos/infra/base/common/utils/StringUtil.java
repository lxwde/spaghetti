package com.zpmc.ztos.infra.base.common.utils;

import org.apache.commons.lang.StringUtils;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

public class StringUtil {
    private static final Logger LOGGER = Logger.getLogger(StringUtil.class.getName());

    private StringUtil() {
    }

    public static String join(Iterator<String> inIterator, @Nullable String inSeparator) {
        if (inIterator == null) {
            return "";
        }
        StringBuilder buf = new StringBuilder();
        while (inIterator.hasNext()) {
            String obj = inIterator.next();
            if (obj != null) {
                buf.append((Object)obj);
            }
            if (inSeparator == null || !inIterator.hasNext()) continue;
            buf.append(inSeparator);
        }
        return buf.toString();
    }

    @Nullable
    public static String truncate(@Nullable String inString, int inLimit) {
        if (inString != null && inString.length() > inLimit) {
          //  LOGGER.info((Object)("The string cannot exceed " + inLimit + " characters, truncated"));
            inString = inString.substring(0, inLimit);
        }
        return inString;
    }

    public static int calculateWrappedTextLines(String inText, String inNewLineIndicator, int inMaxLineWidth) {
        int endIdx;
        int lineCount = 0;
        int startIdx = 0;
        do {
            if ((endIdx = inText.indexOf(inNewLineIndicator, startIdx)) < 0) {
                ++lineCount;
                break;
            }
            int charCount = endIdx - startIdx + 1;
            if (charCount > inMaxLineWidth) {
                lineCount = (int)((double)lineCount + Math.ceil((double)charCount * 1.0 / (double)inMaxLineWidth));
                continue;
            }
            ++lineCount;
        } while ((startIdx = endIdx + 1) <= inText.length());
        return lineCount;
    }

    @Nullable
    public static List<Object> convertToNumericList(String[] inArray) {
        ArrayList<Object> numValues = new ArrayList<Object>();
        for (String token : inArray) {
            if (token == null || !StringUtils.isNumeric((String)token.trim()) || !StringUtil.isConvertibleToLong(token.trim())) {
                return null;
            }
            numValues.add(Long.valueOf(token.trim()));
        }
        return numValues;
    }

    @Nullable
    public static List<Object> convertToDoubleList(String[] inArray) {
        ArrayList<Object> numValues = new ArrayList<Object>();
        for (String token : inArray) {
            if (!StringUtil.isDouble(token.trim())) {
                return null;
            }
            numValues.add(Double.valueOf(token.trim()));
        }
        return numValues;
    }

    public static List<Object> convertToStringList(String[] inArray) {
        ArrayList<Object> stringValues = new ArrayList<Object>();
        for (String token : inArray) {
            stringValues.add(token.trim());
        }
        return stringValues;
    }

    public static boolean isDouble(Object inStr) {
        if (inStr == null) {
            return false;
        }
        if (inStr instanceof Double) {
            return true;
        }
        if (inStr instanceof String) {
            String string = (String)inStr;
            if (string.length() < 2) {
                return false;
            }
            int sz = string.length();
            boolean existDecimal = false;
            for (int i = 0; i < sz; ++i) {
                if ('.' == string.charAt(i)) {
                    if (existDecimal) {
                        return false;
                    }
                    existDecimal = true;
                    continue;
                }
                if (Character.isDigit(string.charAt(i))) continue;
                return false;
            }
            return true;
        }
        return false;
    }

    public static boolean isConvertibleToLong(Object inStr) {
        if (inStr == null) {
            return false;
        }
        if (inStr instanceof Long) {
            return true;
        }
        if (inStr instanceof Integer) {
            return true;
        }
        if (inStr instanceof String) {
            String string = (String)inStr;
            if (StringUtils.isEmpty((String)string)) {
                return false;
            }
            try {
                Long.valueOf(string);
            }
            catch (NumberFormatException e) {
                return false;
            }
            return true;
        }
        return false;
    }

    public static boolean isLong(Object inStr) {
        if (inStr == null) {
            return false;
        }
        if (inStr instanceof Long) {
            return true;
        }
        if (inStr instanceof String) {
            String string = (String)inStr;
            if (StringUtils.isEmpty((String)string)) {
                return false;
            }
            int sz = string.length();
            for (int i = 0; i < sz; ++i) {
                if (Character.isDigit(string.charAt(i))) continue;
                return false;
            }
            return true;
        }
        return false;
    }
}
