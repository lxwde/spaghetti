package com.zpmc.ztos.infra.base.common.utils;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.business.enums.framework.AbstractMeasurementUnitEnum;
import com.zpmc.ztos.infra.base.business.interfaces.IFrameworkPropertyKeys;
import com.zpmc.ztos.infra.base.business.interfaces.IMeasurementUnit;
import com.zpmc.ztos.infra.base.business.interfaces.IRequestContext;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.utils.CarinaUtils;
import com.zpmc.ztos.infra.base.utils.StringUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UnitUtils extends UnitUtilsBase{
    private static final Logger LOGGER = Logger.getLogger(UnitUtils.class);
    private static final String MEASURE_NUMBER_REGEX = "^(-?\\d+([\\.|,| ]\\d+)*)[ \\.]*(?:[^0-9\\.][^\\.]*)?\\.?$";
    private static final String MEASURE_UNIT_REGEX = "^(?:-?\\d+([\\.|,| ]\\d+)*)[ \\.]*([^0-9\\.][^\\.]*)?\\.?$";
    private static final Pattern MEASURE_NUMBER_PATTERN = Pattern.compile("^(-?\\d+([\\.|,| ]\\d+)*)[ \\.]*(?:[^0-9\\.][^\\.]*)?\\.?$");
    private static final Pattern MEASURE_UNIT_PATTERN = Pattern.compile("^(?:-?\\d+([\\.|,| ]\\d+)*)[ \\.]*([^0-9\\.][^\\.]*)?\\.?$");

    public static String parseNonNumericPortion(String inS) {
        String s = StringUtils.trimToEmpty((String)inS);
        Matcher matcher = MEASURE_UNIT_PATTERN.matcher(s);
        if (matcher.matches()) {
            String unit = matcher.group(2);
            return unit == null ? "" : unit;
        }
        return "";
    }

    public static Double parseStringAsDouble(String inS) throws ParseException {
        IRequestContext requestContext = PresentationContextUtils.getRequestContext();
        Locale locale = Locale.US;
        if (null != requestContext) {
            locale = requestContext.getLocale();
        }
        return UnitUtils.parseStringAsDouble(inS, locale);
    }

    protected static Double parseStringAsDouble(String inS, Locale inLocale) throws ParseException {
        String s = UnitUtils.parseNumericPortion(inS);
        try {
            return new Double(s);
        }
        catch (NumberFormatException e) {
            if (inLocale.getLanguage().equals(Locale.ENGLISH.getLanguage()) || inLocale.getCountry().equals(Locale.US.getCountry())) {
                throw e;
            }
            LOGGER.debug((Object)e);
            NumberFormat nf = NumberFormat.getInstance(inLocale);
            double myNumber = nf.parse(s).doubleValue();
            return new Double(myNumber);
        }
    }

    private static String parseNumericPortion(String inS) {
        String s = StringUtils.trimToEmpty((String)inS);
        Matcher matcher = MEASURE_NUMBER_PATTERN.matcher(s);
        if (matcher.matches()) {
            String unit = matcher.group(1);
            return unit == null ? "" : unit;
        }
        return "";
    }

    @Nullable
    public static String formatNumber(Object inValue, NumberFormat inNumberFormat) {
        return UnitUtils.formatNumber(inValue, inNumberFormat, ConversionUtils.getDefaultDecimalRoundingMode());
    }

    @Nullable
    public static String formatNumber(Object inValue, NumberFormat inNumberFormat, RoundingMode inRoundingMode) {
        String stringValue = null;
        double dValue = 0.0;
        if (inValue instanceof Double) {
            dValue = (Double)inValue;
        } else if (inValue instanceof Long) {
            dValue = ((Long)inValue).doubleValue();
        } else if (inValue instanceof String) {
            dValue = Double.parseDouble((String)inValue);
        } else if (inValue != null) {
            LOGGER.log((Priority) Level.ERROR, (Object)("Expecting a value of type Long, Double, or String: " + inValue.getClass()));
            stringValue = inNumberFormat.format(0L);
        } else {
            return null;
        }
        if (stringValue == null) {
            int scale = dValue < 10.0 ? 2 : (dValue < 100.0 ? 1 : 0);
            BigDecimal big = new BigDecimal(dValue);
            big = big.setScale(scale, inRoundingMode);
            stringValue = inNumberFormat.format(big.doubleValue());
        }
        return stringValue;
    }

    public static double convertTo(double inSourceValue, IMeasurementUnit inSourceUnit, IMeasurementUnit inDestUnit) {
        if (!UnitUtils.areCompatible(inSourceUnit, inDestUnit)) {
            throw new IllegalUnitException("Incompatible units: " + inSourceUnit.getAbbrevation() + " vs. " + inDestUnit.getAbbrevation());
        }
        double canonUnitsPerSourceUnit = inSourceUnit.getStandardUnits();
        double canonUnitPerDestUnit = inDestUnit.getStandardUnits();
        double sourceValue = inSourceValue + inSourceUnit.getStandardUnitOffset();
        double valueInCanonicalUnits = sourceValue * canonUnitsPerSourceUnit;
        double resultInDestUnits = valueInCanonicalUnits / canonUnitPerDestUnit;
        return resultInDestUnits -= inDestUnit.getStandardUnitOffset();
    }

    public static double convertTo(String inUserString, IMeasurementUnit inDestUnit, IMeasurementUnit inDefaultUnit) throws UnknownUnitException, ParseException {
        List potentialUnits = AtomizedEnumUtils.safeGetEnumList(inDefaultUnit.getClass());
        return UnitUtils.convertTo(inUserString, inDestUnit, inDefaultUnit, potentialUnits);
    }

    public static Object convertToValue(String inUserString, IMeasurementUnit inDestUnit, IMeasurementUnit inDefaultUnit, Class inValueClass) throws BizViolation {
        try {
            double converted = UnitUtils.convertTo(inUserString, inDestUnit, inDefaultUnit);
            if (Long.class.equals((Object)inValueClass)) {
                Long lValue = Math.round(converted);
                return lValue;
            }
            if (Double.class.equals((Object)inValueClass)) {
                return converted;
            }
            if (String.class.equals((Object)inValueClass)) {
                String sValue = String.valueOf(converted);
                return sValue;
            }
            LOGGER.error((Object)"Unsupported value class passed into convertToValue. Defaulting to Double");
            return converted;
        }
        catch (Exception uue) {
            String supportedFormats = UnitUtils.getSupportedUnitsDisplayString(inDefaultUnit);
            throw BizViolation.create(IFrameworkPropertyKeys.FRAMEWORK__UNIT_INVALID_FORMAT, uue, null, null, new Object[]{inUserString, supportedFormats});
        }
    }

    public static String getSupportedUnitsDisplayString(IMeasurementUnit inUnit) {
        StringBuilder buf = new StringBuilder();
        try {
            Class[] parameters = new Class[]{};
            Class<?> c = inUnit.getClass();
            Method m = c.getMethod("getEnumList", parameters);
            Object[] arguments = new Object[]{};
            List enumList = (List)m.invoke(null, arguments);
            for (Object anEnum : enumList) {
                AbstractMeasurementUnitEnum unit = (AbstractMeasurementUnitEnum)anEnum;
                if (buf.length() > 0) {
                    buf.append(", ");
                }
                buf.append(unit.getAbbrevation());
            }
        }
        catch (Throwable t) {
            LOGGER.error((Object)("getStandardUnitsDisplayString failed with: " + t));
        }
        return buf.toString();
    }

    public static Object convertToValue(Object inValue, IMeasurementUnit inDestUnit, IMeasurementUnit inDefaultUnit) throws BizViolation {
        if (inValue == null) {
            return null;
        }
        try {
            double converted = UnitUtils.convertTo(inValue.toString(), inDestUnit, inDefaultUnit);
            if (inValue instanceof Long) {
                Long lValue = Math.round(converted);
                return lValue;
            }
            if (inValue instanceof Double) {
                return converted;
            }
            if (inValue instanceof String) {
                String sValue = String.valueOf(converted);
                return sValue;
            }
            return converted;
        }
        catch (UnknownUnitException uue) {
            String supportedFormats = UnitUtils.getSupportedUnitsDisplayString(inDefaultUnit);
            throw BizViolation.create(IFrameworkPropertyKeys.FRAMEWORK__UNIT_INVALID_FORMAT, uue, null, null, new Object[]{inValue.toString(), supportedFormats});
        }
        catch (ParseException e) {
            String supportedFormats = UnitUtils.getSupportedUnitsDisplayString(inDefaultUnit);
            throw BizViolation.create(IFrameworkPropertyKeys.FRAMEWORK__UNIT_INVALID_FORMAT, e, null, null, new Object[]{inValue.toString(), supportedFormats});
        }
    }

    @Nullable
    public static String convertToValueStringWithUnits(Object inValue, IMeasurementUnit inSourceUnit, IMeasurementUnit inDestUnit, NumberFormat inNumberFormat) {
        String stringValue = null;
        double dValue = 0.0;
        if (inValue instanceof Double) {
            dValue = (Double)inValue;
        } else if (inValue instanceof Long) {
            dValue = ((Long)inValue).doubleValue();
        } else if (inValue instanceof String) {
            dValue = Double.parseDouble((String)inValue);
        } else if (inValue != null) {
            LOGGER.log((Priority) Level.ERROR, (Object)("measured unit value must be of type Long, Double, or String: " + inValue.getClass()));
            stringValue = inNumberFormat.format(0L);
        } else {
            return null;
        }
        if (stringValue == null) {
            double converted = UnitUtils.convertTo(dValue, inSourceUnit, inDestUnit);
            int scale = converted < 10.0 ? 2 : (converted < 100.0 ? 1 : 0);
            BigDecimal big = new BigDecimal(converted);
            big = big.setScale(scale, ConversionUtils.getDefaultDecimalRoundingMode());
            stringValue = inNumberFormat.format(big.doubleValue());
        }
        stringValue = stringValue + " " + inDestUnit.getAbbrevation();
        return stringValue;
    }

    public static String formatMeasuredField(Locale inLocale, @NotNull IMeasurementUnit inSrcUnit, @NotNull IMeasurementUnit inDestUnit, Object inSrcValue) throws NumberFormatException {
        RoundingMode defaultDecimalRoundingMode = ConversionUtils.getDefaultDecimalRoundingMode();
        return UnitUtils.formatMeasuredField(inLocale, inSrcUnit, inDestUnit, inSrcValue, defaultDecimalRoundingMode);
    }

    @NotNull
    public static String formatMeasuredField(Locale inLocale, @NotNull IMeasurementUnit inSrcUnit, @NotNull IMeasurementUnit inDestUnit, Object inSrcValue, RoundingMode inDefaultDecimalRoundingMode) {
        try {
            double absoluteValue;
            if (inSrcValue == null) {
                return "- ";
            }
            double value = ((Number)inSrcValue).doubleValue();
            double converted = inSrcUnit == inDestUnit ? value : UnitUtils.convertTo(value, inSrcUnit, inDestUnit);
            if (0.001 > converted && converted > -0.001) {
                converted = 0.0;
            }
            int scale = (absoluteValue = Math.abs(converted)) < 10.0 ? 2 : (absoluteValue < 100.0 ? 1 : 0);
            NumberFormat numberInstance = NumberFormat.getNumberInstance(inLocale);
            numberInstance.setMinimumFractionDigits(scale);
            numberInstance.setMaximumFractionDigits(scale);
            numberInstance.setRoundingMode(ConversionUtils.getDefaultDecimalRoundingMode());
            return numberInstance.format(converted);
        }
        catch (Exception e) {
            throw new NumberFormatException("Cannot convert numeric value " + inSrcValue);
        }
    }

    private static double convertTo(String inUserString, IMeasurementUnit inDestUnit, IMeasurementUnit inDefaultUnit, List inPotentialUnits) throws UnknownUnitException, ParseException {
        if (!UnitUtils.areCompatible(inDefaultUnit, inDestUnit)) {
            throw new IllegalUnitException();
        }
        String unitStr = UnitUtils.parseNonNumericPortion(inUserString);
        IMeasurementUnit unit = UnitUtils.lookupUnit(unitStr, inPotentialUnits, inDefaultUnit);
        double srcValue = UnitUtils.parseStringAsDouble(inUserString);
        double value = UnitUtils.convertTo(srcValue, unit, inDestUnit);
        return value;
    }

    public static IMeasurementUnit lookupUnit(String inUnitStr, List inPotentialUnits, IMeasurementUnit inDefaultUnit) throws UnknownUnitException {
        if (CarinaUtils.isBlank(inUnitStr)) {
            return inDefaultUnit;
        }
        for (Object inPotentialUnit : inPotentialUnits) {
            IMeasurementUnit iUnit = (IMeasurementUnit)inPotentialUnit;
            if (!iUnit.matches(inUnitStr)) continue;
            return iUnit;
        }
        throw new UnknownUnitException();
    }

    public static boolean areCompatible(IMeasurementUnit inSourceUnit, IMeasurementUnit inDestUnit) {
        return inSourceUnit.getClass().equals(inDestUnit.getClass());
    }

    public static class IllegalUnitException
            extends RuntimeException {
        public IllegalUnitException() {
        }

        public IllegalUnitException(String inMessage) {
            super(inMessage);
        }
    }

    public static class UnknownUnitException
            extends Exception {
        public UnknownUnitException() {
        }

        public UnknownUnitException(String inMessage) {
            super(inMessage);
        }
    }

}
