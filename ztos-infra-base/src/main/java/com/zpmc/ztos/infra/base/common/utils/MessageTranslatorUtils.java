package com.zpmc.ztos.infra.base.common.utils;

import com.sun.istack.Nullable;
import com.zpmc.ztos.infra.base.business.interfaces.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class MessageTranslatorUtils {
    private static final String DEFAULT_DATE_TIME_ZONED_PATTERN = "yyyy-MM-dd HH:mm z";

    private MessageTranslatorUtils() {
    }

    @Nullable
    public static String getMessage(IMessageTranslator inMessageTranslator, IPropertyKey inPropertyKey, Object inParam0) {
        return inMessageTranslator.getMessage(inPropertyKey, new Object[]{inParam0});
    }

    @Nullable
    public static String getMessage(IMessageTranslator inMessageTranslator, IPropertyKey inPropertyKey, Object inParam0, Object inParam1) {
        return inMessageTranslator.getMessage(inPropertyKey, new Object[]{inParam0, inParam1});
    }

    @Nullable
    public static String getMessage(IMessageTranslator inMessageTranslator, IPropertyKey inPropertyKey, Object inParam0, Object inParam1, Object inParam2) {
        return inMessageTranslator.getMessage(inPropertyKey, new Object[]{inParam0, inParam1, inParam2});
    }

    @Nullable
    public static String getMessage(IMessageTranslator inMessageTranslator, IPropertyKey inPropertyKey, Object inParam0, Object inParam1, Object inParam2, Object inParam3) {
        return inMessageTranslator.getMessage(inPropertyKey, new Object[]{inParam0, inParam1, inParam2, inParam3});
    }

    public static String translateParameter(ITranslationContext inContext, Object inObject) {
        if (inObject instanceof Date) {
            String translatedString = inContext.getDateConverter().getDateTimeString((Date)inObject);
            return translatedString;
        }
        Object translatedObject = MessageTranslatorUtils.translateParameter(inContext.getMessageTranslator(), inContext.getIMetafieldDictionary(), inObject);
        return String.valueOf(translatedObject);
    }

    @Nullable
    public static Object translateParameter(IMessageTranslator inTranslator, IMetafieldDictionary inMetafieldDictionary, Object inObject) {
        Object translatedObject = inObject;
        if (inObject instanceof String) {
            return inObject;
        }
//        if (inObject instanceof IMetafieldAwareNumber) {
//            IMetafieldAwareNumber mfnumAw = (IMetafieldAwareNumber)inObject;
//            IMetafieldId metafieldId = mfnumAw.getMetafieldId();
//            IMeasured fldMeasure = inMetafieldDictionary.findMetafield(metafieldId).getMeasured();
//            NumberFormat numberInstance = NumberFormat.getNumberInstance(inTranslator.getLocale());
//            translatedObject = fldMeasure == null ? UnitUtils.formatNumber(mfnumAw.getNumber(), numberInstance) : UnitUtils.convertToValueStringWithUnits(mfnumAw.getNumber(), fldMeasure.getDataUnit(), fldMeasure.getUserUnit(), numberInstance);
//        } else if (inObject instanceof IMetafieldId) {
//            IMetafield field = inMetafieldDictionary.findMetafield((IMetafieldId)inObject);
//            if (field != null) {
//                IPropertyKey longLabelRk;
//                IRequestContext requestContext = PresentationContextUtils.getRequestContext();
//                translatedObject = requestContext != null && requestContext.isMessageTranslatorAvailable() ? MetafieldPresentationUtils.getFieldLongName(requestContext, (IMetafieldId)inObject) : ((longLabelRk = field.getLongLabelKey()) instanceof HardCodedResourceKey ? ((HardCodedResourceKey)longLabelRk).getHardCodedValue() : inTranslator.getMessage(longLabelRk));
//            }
//        } else if (inObject instanceof AtomizedEnum) {
//            AtomizedEnum ae = (AtomizedEnum)inObject;
//            IPropertyKey descriptionRk = ae.getDescriptionPropertyKey();
//            translatedObject = inTranslator.getMessage(descriptionRk);
//        } else if (inObject instanceof IPropertyKey) {
//            IPropertyKey rk = (IPropertyKey)inObject;
//            translatedObject = inTranslator.getMessage(rk);
//        } else if (inObject instanceof Object[]) {
//            Object[] inputObjs = (Object[])inObject;
//            Object[] translatedObjs = new Object[inputObjs.length];
//            for (int i = 0; i < inputObjs.length; ++i) {
//                translatedObjs[i] = MessageTranslatorUtils.translateParameter(inTranslator, inMetafieldDictionary, inputObjs[i]);
//            }
//            translatedObject = StringUtils.arrayToCommaDelimitedString((Object[])translatedObjs);
//        } else if (inObject instanceof Calendar) {
//            Calendar cal = (Calendar)inObject;
//            SimpleDateFormat timeFormat = new SimpleDateFormat(DEFAULT_DATE_TIME_ZONED_PATTERN);
//            timeFormat.setTimeZone(cal.getTimeZone());
//            translatedObject = timeFormat.format(cal.getTime());
//        }
//        return translatedObject;
        return null;
    }

    private static String translateDate(TimeZone inZone, Date inDate) {
        SimpleDateFormat timeFormat = new SimpleDateFormat(DEFAULT_DATE_TIME_ZONED_PATTERN);
        timeFormat.setTimeZone(inZone);
        String translatedDate = timeFormat.format(inDate);
        return translatedDate;
    }

    @Nullable
    public static Object translateParameter(IMessageTranslationContext inTranslationContext, Object inObject) {
        Object translatedObject = inObject instanceof Date ? MessageTranslatorUtils.translateDate(inTranslationContext.getTimeZone(), (Date)inObject) : MessageTranslatorUtils.translateParameter(inTranslationContext.getTranslator(), inTranslationContext.getMetafieldDictionary(), inObject);
        return translatedObject;
    }
}
