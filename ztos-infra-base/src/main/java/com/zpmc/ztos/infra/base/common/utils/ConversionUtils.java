package com.zpmc.ztos.infra.base.common.utils;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.business.enums.framework.GuiWidgetTypeEnum;
import com.zpmc.ztos.infra.base.business.interfaces.IMetafield;
import com.zpmc.ztos.infra.base.common.contexts.PortalApplicationContext;
import com.zpmc.ztos.infra.base.common.enums.AtomizedEnum;
import com.zpmc.ztos.infra.base.common.exceptions.BizFailure;
import com.zpmc.ztos.infra.base.common.model.*;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class ConversionUtils {
    private static final Logger LOGGER = Logger.getLogger(ConversionUtils.class);

    private ConversionUtils() {
    }

    @Nullable
    public static Double convertNumberToDouble(@NotNull UserContext inUserContext, Object inValue, Integer inMaxFractionalDigits, RoundingMode inRoundingMode) {
        return ConversionUtils.convertNumberToDouble(inUserContext, inValue, null, inMaxFractionalDigits, inRoundingMode);
    }

    @Nullable
    private static Double convertNumberToDouble(@NotNull UserContext inUserContext, Object inValue, IMetafield inMetafield, Integer inMaxFractionalDigits, RoundingMode inRoundingMode) {
        Double dValue = null;
        if (inValue instanceof Double) {
            dValue = (Double)inValue;
        } else if (inValue instanceof Long) {
            dValue = ((Long)inValue).doubleValue();
        } else if (inValue instanceof String) {
            dValue = Double.parseDouble((String)inValue);
        } else {
            if (inValue != null) {
                LOGGER.log((Priority) Level.ERROR, (Object)("Expecting a value of type Long, Double, or String: " + inValue.getClass()));
            }
            return dValue;
        }
        NumberFormat numberFmt = NumberFormat.getNumberInstance(inUserContext.getUserLocale());
        BigDecimal big = new BigDecimal(dValue);
        int decDigits = inMaxFractionalDigits == null ? ConversionUtils.determineDecimalDigits(inMetafield) : inMaxFractionalDigits;
        RoundingMode rMode = inRoundingMode == null ? ConversionUtils.determineRoundingMode(inUserContext, inMetafield) : inRoundingMode;
        numberFmt.setMinimumFractionDigits(decDigits);
        numberFmt.setMaximumFractionDigits(decDigits);
        numberFmt.setRoundingMode(rMode);
        try {
            Number num = numberFmt.parse(numberFmt.format(big.doubleValue()));
            dValue = num.doubleValue();
        }
        catch (Throwable thr) {
            LOGGER.error((Object)("Parsing numeric string failed for value " + inValue));
        }
        return dValue;
    }

    private static int determineDecimalDigits(IMetafield inMetafield) {
        Integer maxFractionalDigits;
        GuiTableEntry guiTable;
        int fractionalDigits = 3;
        if (inMetafield != null && (guiTable = inMetafield.getGuiTable()) != null && (maxFractionalDigits = guiTable.getMaxFractionalDigits()) != null) {
            fractionalDigits = maxFractionalDigits;
        }
        return fractionalDigits;
    }

    private static RoundingMode determineRoundingMode(UserContext inUserContext, IMetafield inMetafield) {
        if (inMetafield != null) {
            GuiWidgetEntry guiEntry = inMetafield.getGuiWidget();
            GuiTableEntry guiTable = inMetafield.getGuiTable();
            if (guiEntry != null && guiTable != null && GuiWidgetTypeEnum.DECIMAL == guiEntry.getType() && guiTable.isAllowCustomDecimalRounding()) {
                return ConversionUtils.getContextCustomDecimalRoundingMode(inUserContext);
            }
        }
        return ConversionUtils.getDefaultDecimalRoundingMode();
    }

    public static RoundingMode getContextCustomDecimalRoundingMode(UserContext inUserContext) {
        String[] rMode = FrameworkConfig.DECIMAL_FIELD_ROUNDING_MODE.getSettings(inUserContext == null ? UserContextUtils.getSystemUserContext() : inUserContext);
        return RoundingMode.valueOf(rMode[0]);
    }

    public static RoundingMode getDefaultDecimalRoundingMode() {
        ApplicationModuleSettings applModuleSettings = (ApplicationModuleSettings) PortalApplicationContext.getBean("appModuleSettings");
        return applModuleSettings.getDefaultDecimalRoundingMode();
    }

    public static boolean isSimpleConversionType(Class inPropertyClass) {
        return String.class.equals((Object)inPropertyClass) || AtomizedEnum.class.isAssignableFrom(inPropertyClass) || Long.class.equals((Object)inPropertyClass) || Double.class.equals((Object)inPropertyClass) || Integer.class.equals((Object)inPropertyClass) || Boolean.class.equals((Object)inPropertyClass) || inPropertyClass.equals(byte[].class);
    }

    public static Object convertSimpleTypeFromString(Class inPropertyClass, String inPersistedString) {
        if (String.class == inPropertyClass) {
            return inPersistedString;
        }
        if (AtomizedEnum.class.isAssignableFrom(inPropertyClass)) {
            return AtomizedEnumUtils.safeGetEnum(inPropertyClass, inPersistedString);
        }
        if (Long.class == inPropertyClass) {
            return Long.valueOf(inPersistedString);
        }
        if (Double.class == inPropertyClass) {
            return Double.valueOf(inPersistedString);
        }
        if (Integer.class == inPropertyClass) {
            return Integer.valueOf(inPersistedString);
        }
        if (Boolean.class == inPropertyClass) {
            return Boolean.valueOf(inPersistedString);
        }
        if (byte[].class == inPropertyClass) {
            return inPersistedString.getBytes();
        }
        throw BizFailure.createProgrammingFailure("Called for unsupported type: " + inPropertyClass);
    }

    public static List<Object> convertToObjectList(Class inClass, String[] inArray) {
        ArrayList<Object> values = new ArrayList<Object>();
        for (String token : inArray) {
            Object value = ConversionUtils.convertSimpleTypeFromString(inClass, token.trim());
            values.add(value);
        }
        return values;
    }
}

