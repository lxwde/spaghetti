package com.zpmc.ztos.infra.base.common.utils;

import com.zpmc.ztos.infra.base.business.enums.argo.*;
import com.zpmc.ztos.infra.base.business.enums.core.LengthUnitEnum;
import com.zpmc.ztos.infra.base.business.enums.core.MassUnitEnum;
import com.zpmc.ztos.infra.base.business.enums.framework.TemperatureUnitEnum;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.business.model.*;
import com.zpmc.ztos.infra.base.business.predicate.PredicateFactory;
import com.zpmc.ztos.infra.base.common.configs.*;
import com.zpmc.ztos.infra.base.common.contexts.PortalApplicationContext;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.common.enums.AtomizedEnum;
import com.zpmc.ztos.infra.base.common.exceptions.BizFailure;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.helps.ContextHelper;
import com.zpmc.ztos.infra.base.common.model.*;
import com.zpmc.ztos.infra.base.common.scopes.Complex;
import com.zpmc.ztos.infra.base.common.scopes.Facility;
import com.zpmc.ztos.infra.base.utils.QueryUtils;
import com.zpmc.ztos.infra.base.utils.StringUtils;
import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlCalendar;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ArgoEdiUtils {
    private static final Logger LOGGER = Logger.getLogger(ArgoEdiUtils.class);
    private static final Set MESSAGES_ALLOWED_FOR_PRELIMINARY_VISIT = new HashSet();
    private static final Map<String, LogicalEntityEnum> RELEASE_MAP_TYPE = new HashMap<String, LogicalEntityEnum>();
    public static final String TRUE = "true";
    public static final String ENTITY_TYPE_CONTAINER = "CN";
    public static final String ENTITY_TYPE_BL = "BL";
    public static final String ENTITY_TYPE_BL_ITEM = "BLITEM";
    public static final String ENTITY_TYPE_REPLACEMENT_CONTAINER = "REPLACEMENTCN";
    public static final String ENTITY_TYPE_LOAD_PERMIT = "LOADPERMIT";
    public static final String ENTITY_TYPE_LOAD_PERMIT_ITEM = "LOADPERMITITEM";
    public static final String EDI_DATE_FORMAT = "yyyy-MM-dd";
    public static final String EDI_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm";
    public static final String EDI_SHORT_DATE_FORMAT = "yy-MM-dd";
    public static final String XML_DATE_TIME_ZONE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss Z";
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
    private static final String XML_DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    private static final String STR_DATE_TIME_FORMAT = "yyyyMMddHHmm";
    public static final String MSG_FUNCTION_DELETE = "D";
    public static final String MSG_FUNCTION_NEW = "N";
    public static final String MSG_FUNCTION_ORIGINAL = "O";
    public static final String MSG_FUNCTION_UPDATE = "U";

    private ArgoEdiUtils() {
    }

    public static List<ValueObject> createVosForBatches(List inBatches) {
        Iterator it = inBatches.iterator();
        ArrayList<ValueObject> batchesVaoList = new ArrayList<ValueObject>(inBatches.size());
        while (it.hasNext()) {
            IBatch batch = (IBatch)it.next();
            ValueObject vao = new ValueObject("IBatch");
            vao.setPrimaryKeyField(IMetafieldId.PRIMARY_KEY);
            vao.setEntityPrimaryKey(batch.getGkey());
            vao.setFieldValue(IMetafieldId.PRIMARY_KEY, (Object)batch.getGkey());
            vao.setFieldValue(IArgoBizMetafield.EDIBATCH_NBR, (Object)batch.getNbr());
            vao.setFieldValue(IArgoBizMetafield.EDIBATCH_SEGMENT_COUNT, (Object)batch.getSegmentCount());
            vao.setFieldValue(IArgoBizMetafield.EDIBATCH_TRANSACTION_COUNT, (Object)batch.getTransactionCount());
            vao.setFieldValue(IArgoBizMetafield.EDIBATCH_INTERCHANGE_NBR, (Object)batch.getInterchangeNbr());
            vao.setFieldValue(IArgoBizMetafield.EDISESS_NAME, (Object)batch.getEdiSessName());
            vao.setFieldValue(IArgoBizMetafield.EDIBATCH_CREATOR, (Object)batch.getEdibatchCreator());
            vao.setFieldValue(IArgoBizMetafield.EDIBATCH_CREATED, (Object)batch.getEdibatchCreated());
            vao.setFieldValue(IArgoBizMetafield.EDIBATCH_CHANGER, (Object)batch.getEdibatchChanger());
            vao.setFieldValue(IArgoBizMetafield.EDIBATCH_CHANGED, (Object)batch.getEdibatchChanged());
            batchesVaoList.add(vao);
        }
        return batchesVaoList;
    }

    public static String rfrTypeChar2EdiRfrType(char inRfrTypeChar) {
        switch (inRfrTypeChar) {
            case ' ': {
                return "DRY";
            }
            case 'P': {
                return "PHL";
            }
            case 'I': {
                return "INT";
            }
            case 'A': {
                return "ADV";
            }
            case 'W': {
                return "WDV";
            }
            case 'F': {
                return "FAN";
            }
            case 'V': {
                return "WSV";
            }
            case 'X': {
                return "ASV";
            }
        }
        return "DRY";
    }

    public static EquipRfrTypeEnum ediRfrType2RfrTypeEnum(String inEdiRfrType) {
        if ("DRY".equals(inEdiRfrType)) {
            return EquipRfrTypeEnum.NON_RFR;
        }
        if ("PHL".equals(inEdiRfrType)) {
            return EquipRfrTypeEnum.PORTHOLE;
        }
        if ("INT".equals(inEdiRfrType)) {
            return EquipRfrTypeEnum.INTEG_UNK;
        }
        if ("ADV".equals(inEdiRfrType)) {
            return EquipRfrTypeEnum.INTEG_AIR;
        }
        if ("WDV".equals(inEdiRfrType)) {
            return EquipRfrTypeEnum.INTEG_H20;
        }
        if ("FAN".equals(inEdiRfrType)) {
            return EquipRfrTypeEnum.FANTAINER;
        }
        if ("WSV".equals(inEdiRfrType)) {
            return EquipRfrTypeEnum.INTEG_H20_SINGLE;
        }
        return EquipRfrTypeEnum.NON_RFR;
    }

    public static String acryType2EdiAccClassValue(String inAccType) {
        if ("NA".equals(inAccType) || "CU".equals(inAccType)) {
            return "ACCESSORY";
        }
        if ("CH".equals(inAccType) || "HH".equals(inAccType)) {
            return "CHASSIS";
        }
        if ("GS".equals(inAccType)) {
            return "GENSET_NOSE_MOUNT";
        }
        if ("GU".equals(inAccType)) {
            return "GENSET_UNDERSLUNG";
        }
        return "CONTAINER";
    }

    public static String ediRfrVentSettingCode2Value(String inVentSetting) {
        if (inVentSetting == null) {
            return null;
        }
        if ("A".equals(inVentSetting)) {
            return "25";
        }
        if ("B".equals(inVentSetting)) {
            return "50";
        }
        if ("C".equals(inVentSetting)) {
            return "75";
        }
        if (MSG_FUNCTION_DELETE.equals(inVentSetting)) {
            return "100";
        }
        if ("E".equals(inVentSetting)) {
            return "0";
        }
        if ("F".equals(inVentSetting)) {
            return "10";
        }
//        if (StringUtils.isNumeric((String)inVentSetting) || GenericValidator.isFloat((String)inVentSetting) || GenericValidator.isDouble((String)inVentSetting)) {
//            return inVentSetting;
//        }
        return null;
    }

    public static String ediRfrVentSettingValue2Code(String inVentSetting) {
        if ("25".equals(inVentSetting)) {
            return "A";
        }
        if ("50".equals(inVentSetting)) {
            return "B";
        }
        if ("75".equals(inVentSetting)) {
            return "C";
        }
        if ("100".equals(inVentSetting)) {
            return MSG_FUNCTION_DELETE;
        }
        if ("0".equals(inVentSetting)) {
            return "E";
        }
        if ("10".equals(inVentSetting)) {
            return "F";
        }
//        if (StringUtils.isNumeric((String)inVentSetting) || GenericValidator.isFloat((String)inVentSetting) || GenericValidator.isDouble((String)inVentSetting)) {
//            return inVentSetting;
//        }
        return null;
    }

    public static LineOperator getLineOperator(IEdiVesselVisit inEdiVesselVisit) throws BizViolation {
        ScopedBizUnit line = null;
        String lineCode = null;
        String lineCodeAgency = null;
        if (inEdiVesselVisit != null && inEdiVesselVisit.getShippingLine() != null) {
            lineCode = inEdiVesselVisit.getShippingLine().getShippingLineCode();
            lineCodeAgency = inEdiVesselVisit.getShippingLine().getShippingLineCodeAgency();
            if (lineCode != null) {
                line = ScopedBizUnit.resolveScopedBizUnit(lineCode, lineCodeAgency, BizRoleEnum.LINEOP);
            }
        }
        if (line == null) {
            throw BizViolation.create((IPropertyKey) IArgoPropertyKeys.INVALID_OPERATOR_ID, null, lineCode);
        }
        return LineOperator.resolveLineOprFromScopedBizUnit(line);
    }

    @Nullable
    public static LineOperator getLineOperatorNullifyIfNotExist(IEdiVesselVisit inEdiVesselVisit) throws BizViolation {
        if (inEdiVesselVisit != null && inEdiVesselVisit.getShippingLine() != null) {
            String lineCodeAgency;
            String lineCode = inEdiVesselVisit.getShippingLine().getShippingLineCode();
            ScopedBizUnit line = ScopedBizUnit.resolveScopedBizUnit(lineCode, lineCodeAgency = inEdiVesselVisit.getShippingLine().getShippingLineCodeAgency(), BizRoleEnum.LINEOP);
            if (line == null) {
                throw BizViolation.create((IPropertyKey) IArgoPropertyKeys.INVALID_OPERATOR_ID, null, (Object)lineCodeAgency, (Object)lineCode);
            }
            return LineOperator.resolveLineOprFromScopedBizUnit(line);
        }
        return null;
    }

    public static CarrierVisit findVesselVisit(IEdiVesselVisit inEdiVesselVisit) throws BizViolation {
        Complex complex = ContextHelper.getThreadComplex();
        if (complex == null) {
            throw BizFailure.create((IPropertyKey) IArgoPropertyKeys.INVALID_FACILITY_ID, null, null);
        }
        String vvConvention = inEdiVesselVisit.getVesselIdConvention();
        String vvId = inEdiVesselVisit.getVesselId();
        String ibVoyg = inEdiVesselVisit.getInVoyageNbr();
        IVesselVisitFinder vvf = (IVesselVisitFinder) Roastery.getBean((String)"vesselVisitFinder");
        CarrierVisit cv = vvf.findVesselVisitForInboundStow(complex, vvConvention, vvId, ibVoyg, null, null);
        return cv;
    }

    public static RoutingPoint extractRoutingPoint(EdiPostingContext inEdiPostingContext, IPort inPort, IMetafieldId inPointField) throws BizViolation {
        RoutingPoint rp = null;
        if (inPort != null) {
            String portId = inPort.getPortId();
            String portIdConvention = inPort.getPortIdConvention();
            if (portId != null && portIdConvention != null && !StringUtils.isEmpty((String)(portId = portId.trim())) && (rp = RoutingPoint.resolveRoutingPointFromEncoding(portIdConvention, portId)) == null) {
                BizViolation bv = BizViolation.create((IPropertyKey) IArgoPropertyKeys.INVALID_PORT, null, (Object)portId, (Object)portIdConvention);
                if (inEdiPostingContext != null) {
                    inEdiPostingContext.addViolation(bv);
                } else {
                    throw bv;
                }
            }
        }
        return rp;
    }

    public static Facility findFacility(EdiPostingContext inEdiContext, IEdiFacility inEdiFacility) {
        Complex cpx;
        String facilityId;
        Facility facility = null;
        if (inEdiFacility != null && !StringUtils.isEmpty((String)(facilityId = inEdiFacility.getFacilityId())) && (facility = Facility.findFacility(facilityId, cpx = ContextHelper.getThreadComplex())) == null) {
            inEdiContext.addViolation(IArgoPropertyKeys.INVALID_FACILITY_ID, facilityId, cpx.getCpxId());
        }
        return facility;
    }

    public static Facility findActiveFacilityByRoutingPoint(RoutingPoint inPoint) {
        List facilities;
        Facility facility = null;
        if (inPoint != null && (facilities = Facility.findAllActiveFacilitiesByRoutingPoint(inPoint, ContextHelper.getThreadComplex(), null)) != null && facilities.size() == 1) {
            facility = (Facility)facilities.get(0);
        }
        return facility;
    }

    public static boolean validateAutoCreationOfVesselVisit(EdiPostingContext inEdiPostingContext, Facility inFacility) {
        boolean isAutoCreationAllowed = false;
        EdiMessageClassEnum msgClass = inEdiPostingContext.getEdiMessageClass();
        if (inFacility != null && inFacility.getFcyIsNonOperational() != null && inFacility.getFcyIsNonOperational().booleanValue() && MESSAGES_ALLOWED_FOR_PRELIMINARY_VISIT.contains((Object)msgClass)) {
            BooleanConfig sessionConfig = null;
            if (EdiMessageClassEnum.BOOKING.equals((Object)msgClass)) {
                sessionConfig = ArgoConfig.BKG_ALLOW_FOREIGN_VESSEL_VISIT;
            } else if (EdiMessageClassEnum.MANIFEST.equals((Object)msgClass)) {
                sessionConfig = ArgoConfig.MFT_ALLOW_FOREIGN_VESSEL_VISIT;
            } else if (EdiMessageClassEnum.RELEASE.equals((Object)msgClass)) {
                sessionConfig = ArgoConfig.RLS_ALLOW_FOREIGN_VESSEL_VISIT;
            } else if (EdiMessageClassEnum.PREADVISE.equals((Object)msgClass)) {
                sessionConfig = ArgoConfig.PRE_ALLOW_FOREIGN_VESSEL_VISIT;
            } else if (EdiMessageClassEnum.APPOINTMENT.equals((Object)msgClass)) {
                sessionConfig = ArgoConfig.ALLOW_FOREIGN_VESSEL_VISIT;
            } else if (EdiMessageClassEnum.RAILWAYBILL.equals((Object)msgClass)) {
                sessionConfig = ArgoConfig.WAYBILL_AUTO_CREATE_VESSEL_VISIT;
            }
            String sessionConfigStatus = inEdiPostingContext.getConfigValue((AbstractConfig)sessionConfig);
            if (ArgoEdiUtils.getBooleanConfigValue(sessionConfigStatus)) {
                isAutoCreationAllowed = true;
            }
        }
        return isAutoCreationAllowed;
    }

    public static void verifyIfPortInItin(IMetafieldId inField, RoutingPoint inPoint, CarrierVisit inVesselVisit) throws BizViolation {
        if (inVesselVisit == null || inPoint == null) {
            return;
        }
        VisitDetails cvd = inVesselVisit.getCvCvd();
        if (cvd.getCvdItinerary() == null || !cvd.getCvdItinerary().isPointInItinerary(inPoint)) {
            throw BizViolation.createFieldViolation((IPropertyKey) IArgoPropertyKeys.ROUTING_POINT_NOT_IN_ITINERARY, null, (IMetafieldId)inField, (Object)inPoint, (Object)cvd.getCvdCv());
        }
    }

//    public static UnitCategoryEnum determineCategory(ReleaseTransactionDocument.ReleaseTransaction inRelease) {
//        UnitCategoryEnum category = UnitCategoryEnum.IMPORT;
//        String categoryString = inRelease.getReleaseIdentifierCategory();
//        if (categoryString != null && categoryString.length() > 0) {
//            category = UnitCategoryEnum.getEnum(categoryString);
//        }
//        return category;
//    }

    public static boolean hasTimeZoneIncluded(String inDate) {
        Pattern timeZonePattern = Pattern.compile("\\+[0-9]{2}(:)|-[0-9]{2}(:)");
        Matcher matcher = timeZonePattern.matcher(inDate);
        return matcher.find();
    }

    public static Date convertLocalToUtcDate(Object inDate, TimeZone inTimeZone) {
        if (inDate == null) {
            return null;
        }
        XmlCalendar xmlCalDate = null;
        if (inDate instanceof XmlCalendar) {
            xmlCalDate = (XmlCalendar)inDate;
        } else if (inDate instanceof String) {
            xmlCalDate = new XmlCalendar((String)inDate);
        } else {
            LOGGER.debug((Object)("Unable to convert the date :: " + inDate));
            return null;
        }
        if (!ArgoEdiUtils.hasTimeZoneIncluded(inDate.toString())) {
            xmlCalDate.setTimeZone(inTimeZone);
        }
        return xmlCalDate.getTime();
    }

    public static GregorianCalendar convertToLocalDate(Date inDate, TimeZone inLocalTz) {
        if (inDate == null) {
            return null;
        }
        GregorianCalendar localDt = new GregorianCalendar(inLocalTz);
        localDt.setTime(inDate);
        return localDt;
    }

    public static TimeZone resolveTimeZoneFromCarrierVisit(CarrierVisit inCv) {
        TimeZone tz = null;
        if (inCv != null && inCv.getCvFacility() != null) {
            tz = inCv.getCvFacility().getTimeZone();
        }
        return tz != null ? tz : ContextHelper.getThreadUserTimezone();
    }

    public static Calendar mergeDateAndTime(Date inDate, Date inTime) {
        if (inDate == null) {
            return null;
        }
        Calendar convertedDt = Calendar.getInstance();
        convertedDt.setTime(inDate);
        if (inTime != null) {
            Calendar convertedTime = Calendar.getInstance();
            convertedTime.setTime(inTime);
            convertedDt.add(11, convertedTime.get(11));
            convertedDt.add(12, convertedTime.get(12));
        }
        return convertedDt;
    }

    public static Long convertOogToCm(String inOogLength, String inOogUnit, IMetafieldId inField) throws UnitUtils.UnknownUnitException {
        return ArgoEdiUtils.doubletoLong(ArgoEdiUtils.convertTo(inOogLength, inOogUnit, LengthUnitEnum.getEnumList(), (IMeasurementUnit) LengthUnitEnum.CENTIMETERS, inField));
    }

    public static Double convertWtToKg(String inWeight, String inWeighttUnit, IMetafieldId inField) throws UnitUtils.UnknownUnitException {
        return ArgoEdiUtils.convertTo(inWeight, inWeighttUnit, MassUnitEnum.getEnumList(), (IMeasurementUnit) MassUnitEnum.KILOGRAMS, inField);
    }

    public static Double convertTempToCel(String inTemp, String inTempUnit, IMetafieldId inField) throws UnitUtils.UnknownUnitException {
        return ArgoEdiUtils.convertTo(inTemp, inTempUnit, TemperatureUnitEnum.getEnumList(), (IMeasurementUnit) TemperatureUnitEnum.C, inField);
    }

    public static Double convertTo(String inSourceValue, String inSourceUnit, List inPotentialUnits, IMeasurementUnit inDefaultUnit, IMetafieldId inField) throws UnitUtils.UnknownUnitException {
        Double sourceValue = ArgoEdiUtils.safeGetDouble(inSourceValue, inField);
        if (sourceValue == null) {
            return null;
        }
        IMeasurementUnit sourceUnit = UnitUtils.lookupUnit((String)inSourceUnit, (List)inPotentialUnits, (IMeasurementUnit)inDefaultUnit);
        if (sourceUnit == null) {
            LOGGER.warn((Object)"null source unit, conversion not possible. return source value");
            return sourceValue;
        }
        return new Double(UnitUtils.convertTo((double)sourceValue, (IMeasurementUnit)sourceUnit, (IMeasurementUnit)inDefaultUnit));
    }

    public static Long doubletoLong(Double inValueD) {
        return inValueD == null ? null : new Long(inValueD.longValue());
    }

    public static Long safeGetLong(String inNumberString, IMetafieldId inField) {
        Long longObject = null;
        if (!StringUtils.isEmpty((String)inNumberString)) {
            try {
                longObject = new Long(inNumberString);
            }
            catch (NumberFormatException e) {
                LOGGER.error((Object)("safeGetLong: bad value for " + (Object)inField + ": " + inNumberString));
            }
        }
        return longObject;
    }

    public static Double safeGetDouble(String inNumberString, IMetafieldId inField) {
        Double doubleObject = null;
        if (!StringUtils.isEmpty((String)inNumberString)) {
            try {
                doubleObject = new Double(inNumberString);
            }
            catch (NumberFormatException e) {
                LOGGER.error((Object)("safeGetDouble: bad value for " + (Object)inField + ": " + inNumberString));
            }
        }
        return doubleObject;
    }

    public static void validateRequiredField(String inValue, IPropertyKey inMessageKey) throws BizViolation {
        if (StringUtils.isEmpty((String)inValue)) {
            throw BizViolation.create((IPropertyKey)inMessageKey, null);
        }
    }

    public static void validateAndThrowReqAttributeValue(String inAttributeName, String inAttributeValue) throws BizViolation {
        if (StringUtils.isBlank((String)inAttributeValue)) {
            throw BizViolation.create((IPropertyKey) IArgoPropertyKeys.MISSING_ATTRIBUTE, null, (Object)inAttributeName);
        }
    }

    public static void validateReqAttributeValue(EdiPostingContext inEdiPc, String inAttributeName, String inAttributeValue) throws BizViolation {
        if (StringUtils.isBlank((String)inAttributeValue) && inEdiPc != null) {
            inEdiPc.addViolation(BizViolation.create((IPropertyKey) IArgoPropertyKeys.MISSING_ATTRIBUTE, null, (Object)inAttributeName));
        }
    }

    public static RoutingPoint findRoutingPointFromUnLoc(String inUnLocCode) {
        RoutingPoint routingPoint = null;
        UnLocCode un = UnLocCode.findUnLocCode(inUnLocCode);
        if (un != null) {
            IMetafieldId pointUnLocId = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId) IArgoRefField.POINT_UN_LOC, (IMetafieldId) IArgoRefField.UNLOC_ID);
            IDomainQuery dq = QueryUtils.createDomainQuery((String)"RoutingPoint").addDqPredicate(PredicateFactory.eq((IMetafieldId)pointUnLocId, (Object)inUnLocCode));
            dq.setFilter(ObsoletableFilterFactory.createShowActiveFilter());
            List points = HibernateApi.getInstance().findEntitiesByDomainQuery(dq);
            if (points.size() == 1) {
                routingPoint = (RoutingPoint)points.get(0);
            } else if (points.size() > 1) {
                for (Object rp : points) {
                    if (!StringUtils.equals((String)((RoutingPoint)rp).getPointId(), (String)un.getUnlocPlaceCode())) continue;
                    routingPoint = (RoutingPoint)rp;
                }
                if (routingPoint == null) {
                    routingPoint = (RoutingPoint)points.get(0);
                    LOGGER.warn((Object)("findRoutingPointFromUnLoc: guessing routing point <" + routingPoint.getPointId() + "> for <" + inUnLocCode + ">"));
                }
            }
        }
        if (routingPoint == null) {
            LOGGER.info((Object)("findRoutingPointFromUnLoc: could not resolve UnLoc code <" + inUnLocCode + ">"));
        }
        return routingPoint;
    }

//    public static Commodity validateCommodity(EdiPostingContext inEdiPostingContext, EdiCommodity inEdiCommodity) {
//        String cmdyId;
//        Commodity commodity = null;
//        if (inEdiCommodity != null && ArgoEdiUtils.isNotEmpty(cmdyId = inEdiCommodity.getCommodityCode())) {
//            boolean autoCreationEnabled = ArgoConfig.AUTO_CREATION_OF_COMMODITY_CODE.isOn(ContextHelper.getThreadUserContext());
//            if (autoCreationEnabled) {
//                commodity = Commodity.findOrCreateCommodity(cmdyId);
//            } else {
//                commodity = Commodity.findCommodity(cmdyId);
//                if (commodity == null) {
//                    BizViolation bv = BizViolation.create((IPropertyKey)IArgoPropertyKeys.COMMODITY_NOT_FOUND, null, (Object)cmdyId);
//                    inEdiPostingContext.addViolation(bv);
//                }
//            }
//        }
//        return commodity;
//    }

    public static IMessageCollector getMessageCollector() {
        return TransactionParms.getBoundParms().getMessageCollector();
    }

    public static boolean isNotEmpty(String inStr) {
        return inStr != null && StringUtils.isNotEmpty((String)inStr.trim());
    }

    public static boolean isEmpty(String inStr) {
        return inStr == null || inStr.trim().length() == 0;
    }

    public static boolean getBooleanConfigValue(String inStr) {
        return ArgoEdiUtils.isNotEmpty(inStr) && TRUE.equalsIgnoreCase(inStr);
    }

    public static Object getConfigValue(EdiPostingContext inEdiPc, AbstractConfig inConfig) {
        String settingValueInSession = null;
        if (inEdiPc != null) {
            settingValueInSession = inEdiPc.getConfigValue(inConfig);
        }
        if (inConfig instanceof LongConfig) {
            if (ArgoEdiUtils.isEmpty(settingValueInSession)) {
                return ((LongConfig)inConfig).getValue(ContextHelper.getThreadUserContext());
            }
            return new Long(settingValueInSession);
        }
        if (inConfig instanceof StringConfig) {
            if (ArgoEdiUtils.isEmpty(settingValueInSession)) {
                return ((StringConfig)inConfig).getSetting(ContextHelper.getThreadUserContext());
            }
            return settingValueInSession;
        }
        if (inConfig instanceof BooleanConfig) {
            if (ArgoEdiUtils.isEmpty(settingValueInSession)) {
                return ((BooleanConfig)inConfig).isOn(ContextHelper.getThreadUserContext());
            }
            return Boolean.valueOf(settingValueInSession);
        }
        if (inConfig instanceof CodedConfig) {
            if (ArgoEdiUtils.isEmpty(settingValueInSession)) {
                String[] settings = ((CodedConfig)inConfig).getSettings(ContextHelper.getThreadUserContext());
                if (settings.length > 0) {
                    return settings[0];
                }
                return null;
            }
            return settingValueInSession;
        }
        return null;
    }

    public static Object getConfigValue(IConfigValueProvider inConfigProvider, AbstractConfig inConfig) {
        if (inConfig instanceof LongConfig) {
            if (inConfigProvider == null || ArgoEdiUtils.isEmpty(inConfigProvider.getConfigValue((IConfig)inConfig))) {
                return ((LongConfig)inConfig).getValue(ContextHelper.getThreadUserContext());
            }
            return new Long(inConfigProvider.getConfigValue((IConfig)inConfig));
        }
        if (inConfig instanceof StringConfig) {
            if (inConfigProvider == null || ArgoEdiUtils.isEmpty(inConfigProvider.getConfigValue((IConfig)inConfig))) {
                return ((StringConfig)inConfig).getSetting(ContextHelper.getThreadUserContext());
            }
            return inConfigProvider.getConfigValue((IConfig)inConfig);
        }
        if (inConfig instanceof BooleanConfig) {
            if (inConfigProvider == null || ArgoEdiUtils.isEmpty(inConfigProvider.getConfigValue((IConfig)inConfig))) {
                return ((BooleanConfig)inConfig).isOn(ContextHelper.getThreadUserContext());
            }
            return Boolean.valueOf(inConfigProvider.getConfigValue((IConfig)inConfig));
        }
        return null;
    }

    public static EdiActionCodeEnum translateXmlMessageFunction(String inMsgFunction) {
        if (MSG_FUNCTION_DELETE.equals(inMsgFunction)) {
            return EdiActionCodeEnum.R;
        }
        if (MSG_FUNCTION_ORIGINAL.equals(inMsgFunction) || MSG_FUNCTION_NEW.equals(inMsgFunction)) {
            return EdiActionCodeEnum.O;
        }
        if (MSG_FUNCTION_UPDATE.equals(inMsgFunction)) {
            return EdiActionCodeEnum.U;
        }
        return EdiActionCodeEnum.O;
    }

    public static Date parseStringDateForVermas(Object inDateObj) {
        String ediDateStr;
        Date verifedDate = null;
        if (inDateObj != null && ArgoEdiUtils.isNotEmpty(ediDateStr = inDateObj.toString())) {
            try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(STR_DATE_TIME_FORMAT);
                if (ediDateStr.length() == 12) {
                    simpleDateFormat.setTimeZone(ContextHelper.getThreadUserTimezone());
                    verifedDate = simpleDateFormat.parse(ediDateStr);
                } else if (ediDateStr.length() == 15) {
                    String substring = ediDateStr.substring(12, 15);
                    TimeZone timeZone = TimeZone.getTimeZone(substring);
                    if (timeZone == null) {
                        LOGGER.info((Object)("Timezone given in Verified Date is not valid" + substring));
                        simpleDateFormat.setTimeZone(ContextHelper.getThreadUserTimezone());
                    } else {
                        simpleDateFormat.setTimeZone(timeZone);
                    }
                    verifedDate = simpleDateFormat.parse(ediDateStr.substring(0, 12));
                } else {
                    LOGGER.info((Object)"Verified Date format is Unknown. Currently yyyyMMddHHmm and yyyyMMddHHmmZZZ formats are supported");
                }
            }
            catch (ParseException inE) {
                LOGGER.error((Object)("updateVgmProperties is failed to parse" + inE));
            }
        }
        return verifedDate;
    }

    @Nullable
    public static Date parseStringToDate(String inDateStr) {
        Date dt;
        ArrayList<SimpleDateFormat> xmlFormat = new ArrayList<SimpleDateFormat>();
        if (inDateStr.length() > 10) {
            if (inDateStr.charAt(10) == 'T') {
                xmlFormat.add(new SimpleDateFormat(XML_DATE_TIME_FORMAT));
                xmlFormat.add(new SimpleDateFormat(XML_DATE_TIME_ZONE_FORMAT));
            } else {
                xmlFormat.add(new SimpleDateFormat(DATE_TIME_FORMAT));
                xmlFormat.add(new SimpleDateFormat(TIMESTAMP_FORMAT));
            }
        }
        if ((dt = ArgoEdiUtils.parsingBasedOnFormat(xmlFormat, inDateStr)) == null && inDateStr.length() > 8 && inDateStr.charAt(4) == '-') {
            ArrayList<SimpleDateFormat> ediFormat = new ArrayList<SimpleDateFormat>();
            if (inDateStr.length() > 10) {
                ediFormat.add(new SimpleDateFormat(EDI_DATE_TIME_FORMAT));
            } else {
                ediFormat.add(new SimpleDateFormat(EDI_DATE_FORMAT));
            }
            dt = ArgoEdiUtils.parsingBasedOnFormat(ediFormat, inDateStr);
        } else if (dt == null) {
            try {
                SimpleDateFormat simpleFormat = new SimpleDateFormat(EDI_SHORT_DATE_FORMAT);
                dt = simpleFormat.parse(inDateStr);
            }
            catch (ParseException pe) {
                LOGGER.error((Object)"wrong date format ", (Throwable)pe);
                return null;
            }
        }
        return dt;
    }

    public static Object validateAndConvertDFFValue(IMetafieldId inComponentMetafieldId, String inDFFValue) throws BizViolation {
        IMetafieldId mfidRightMostNode = inComponentMetafieldId.getMfidRightMostNode();
        String errMsg = "";
        if (mfidRightMostNode == null) {
            errMsg = "Invalid ComponenetMetaFieldId :" + (Object)inComponentMetafieldId;
            throw BizViolation.create((IPropertyKey) IArgoPropertyKeys.EDI_POST_DFF_FAILED, null, (Object)errMsg);
        }
        IMetafieldDictionary mfd = Roastery.getMetafieldDictionary();
        IMetafield metafield = mfd.findMetafield(MetafieldIdFactory.valueOf((String)mfidRightMostNode.getFieldId()));
        if (metafield == null) {
            errMsg = "DynamicFlexField[" + mfidRightMostNode.getFieldId() + "] does not exist";
            throw BizViolation.create((IPropertyKey) IArgoPropertyKeys.EDI_POST_DFF_FAILED, null, (Object)errMsg);
        }
        Class fieldClass = metafield.getValueClass();
        if (fieldClass == null) {
            errMsg = "Unknown DFF value class for DFF field [" + mfidRightMostNode.getFieldId() + "]";
            throw BizViolation.create((IPropertyKey) IArgoPropertyKeys.EDI_POST_DFF_FAILED, null, (Object)errMsg);
        }
        if (AtomizedEnum.class.isAssignableFrom(fieldClass)) {
            AtomizedEnum atomizedEnum = AtomizedEnum.resolve((Class)fieldClass, (String)inDFFValue);
            if (atomizedEnum == null) {
                errMsg = "Invalid Field value [" + inDFFValue + "] for a DFF Field [" + mfidRightMostNode.getFieldId() + "].Please provide valid Enum[" + fieldClass.getName() + "].";
                throw BizViolation.create((IPropertyKey) IArgoPropertyKeys.EDI_POST_DFF_FAILED, null, (Object)errMsg);
            }
            return atomizedEnum;
        }
        if (Long.class.isAssignableFrom(fieldClass) || Integer.class.isAssignableFrom(fieldClass)) {
            try {
                long l = Long.parseLong(inDFFValue);
                return new Long(l);
            }
            catch (Exception e) {
                errMsg = "Invalid Field value [" + inDFFValue + "] for a DFF Field [" + mfidRightMostNode.getFieldId() + "]. Long value is expected";
                throw BizViolation.create((IPropertyKey) IArgoPropertyKeys.EDI_POST_DFF_FAILED, null, (Object)errMsg);
            }
        }
        if (Date.class.isAssignableFrom(fieldClass)) {
            try {
                Date date = DateUtil.xmlDateStringToDate((String)inDFFValue);
                return date;
            }
            catch (Exception e) {
                errMsg = "Invalid Field value [" + inDFFValue + "] for a DFF Field [" + mfidRightMostNode.getFieldId() + "]. Valid Date formate is expected";
                throw BizViolation.create((IPropertyKey) IArgoPropertyKeys.EDI_POST_DFF_FAILED, null, (Object)errMsg);
            }
        }
        if (Boolean.class.isAssignableFrom(fieldClass)) {
            try {
                return Boolean.parseBoolean(inDFFValue);
            }
            catch (Exception e) {
                errMsg = "Invalid Field value [" + inDFFValue + "] for a DFF Field [" + mfidRightMostNode.getFieldId() + "]. Valid boolean value is expected";
                throw BizViolation.create((IPropertyKey) IArgoPropertyKeys.EDI_POST_DFF_FAILED, null, (Object)errMsg);
            }
        }
        if (Float.class.isAssignableFrom(fieldClass) || Double.class.isAssignableFrom(fieldClass)) {
            try {
                double v = Double.parseDouble(inDFFValue);
                return new Double(v);
            }
            catch (Exception e) {
                errMsg = "Invalid Field value [" + inDFFValue + "] for a DFF Field [" + mfidRightMostNode.getFieldId() + "]. Valid Doublle value is expected";
                throw BizViolation.create((IPropertyKey) IArgoPropertyKeys.EDI_POST_DFF_FAILED, null, (Object)errMsg);
            }
        }
        if (String.class.isAssignableFrom(fieldClass)) {
            return inDFFValue;
        }
        errMsg = "Invalid Field value [" + inDFFValue + "] for a DFF Field [" + mfidRightMostNode.getFieldId() + "]. Currenlty we don't support [" + fieldClass.toString() + "] for posting DFF";
        throw BizViolation.create((IPropertyKey) IArgoPropertyKeys.EDI_POST_DFF_FAILED, null, (Object)errMsg);
    }

    private static Date parsingBasedOnFormat(List<SimpleDateFormat> inDateFormats, String inDateStr) {
        Date dt = null;
        for (SimpleDateFormat format : inDateFormats) {
            dt = ArgoEdiUtils.tryToParseStringToDate(format, inDateStr);
            if (dt == null) continue;
            return dt;
        }
        return dt;
    }

    @Nullable
    private static Date tryToParseStringToDate(SimpleDateFormat inFormat, String inString) {
        try {
            return inFormat.parse(inString);
        }
        catch (ParseException e) {
            return null;
        }
    }

//    public static Double getEDIVGMWeight(EdiVerifiedGrossMass inEdiVerifiedGrossMass) {
//        Double vgmWt = null;
//        vgmWt = ArgoEdiUtils.getVGMSafeDouble(inEdiVerifiedGrossMass.getVerifiedGrossWt());
//        return vgmWt;
//    }

    private static Double getVGMSafeDouble(String inNumberString) {
        Double doubleObject = null;
        if (!StringUtils.isEmpty((String)inNumberString)) {
            try {
                doubleObject = new Double(inNumberString);
            }
            catch (NumberFormatException e) {
                LOGGER.error((Object)("getVGMSafeDouble: bad value for VGM Weight" + inNumberString));
            }
        }
        return doubleObject;
    }

    public static LogicalEntityEnum resolveEdiReleaseType(String inReleaseIdentifierType) {
        return RELEASE_MAP_TYPE.get(inReleaseIdentifierType);
    }

    static {
        MESSAGES_ALLOWED_FOR_PRELIMINARY_VISIT.add(EdiMessageClassEnum.BOOKING);
        MESSAGES_ALLOWED_FOR_PRELIMINARY_VISIT.add(EdiMessageClassEnum.MANIFEST);
        MESSAGES_ALLOWED_FOR_PRELIMINARY_VISIT.add(EdiMessageClassEnum.PREADVISE);
        MESSAGES_ALLOWED_FOR_PRELIMINARY_VISIT.add(EdiMessageClassEnum.APPOINTMENT);
        MESSAGES_ALLOWED_FOR_PRELIMINARY_VISIT.add(EdiMessageClassEnum.RELEASE);
        MESSAGES_ALLOWED_FOR_PRELIMINARY_VISIT.add(EdiMessageClassEnum.RAILWAYBILL);
        RELEASE_MAP_TYPE.put("UNITRELEASE", LogicalEntityEnum.UNIT);
        RELEASE_MAP_TYPE.put("BLRELEASE", LogicalEntityEnum.BL);
        RELEASE_MAP_TYPE.put("BKGRELEASE", LogicalEntityEnum.BKG);
    }

    public static class VoyageNbr {
        private VoyageEnum _vygEnum;
        private String _vygNbr;

        VoyageNbr(VoyageEnum inVygEnum, String inVygNbr) {
            this._vygEnum = inVygEnum;
            this._vygNbr = inVygNbr;
        }

        public boolean isOperatorInboundVoyage() {
            return VoyageEnum.OPR_INBOUND.equals((Object)this._vygEnum);
        }

        public boolean isOperatorOutboundVoyage() {
            return VoyageEnum.OPR_OUTBOUND.equals((Object)this._vygEnum);
        }

        public String toString() {
            IMessageTranslatorProvider translatorProvider = (IMessageTranslatorProvider) PortalApplicationContext.getBean((String)"messageTranslatorProvider");
            IMessageTranslator mt = translatorProvider.getMessageTranslator(ContextHelper.getThreadUserContext().getUserLocale());
            String voyageNbr = "";
            voyageNbr = ArgoEdiUtils.isEmpty(this._vygNbr) ? mt.getMessage(this._vygEnum.getDesc()) + " [ ] " : mt.getMessage(this._vygEnum.getDesc()) + " [" + this._vygNbr + "]";
            return voyageNbr;
        }

        public String getVoyageNbr() {
            return !ArgoEdiUtils.isEmpty(this._vygNbr) ? this._vygNbr : null;
        }

        public static VoyageNbr resolveInbound(IEdiVesselVisit inEdiVv) {
            VoyageNbr voyageNbr = null;
            if (inEdiVv != null) {
                voyageNbr = ArgoEdiUtils.isNotEmpty(inEdiVv.getInOperatorVoyageNbr()) ? new VoyageNbr(VoyageEnum.OPR_INBOUND, inEdiVv.getInOperatorVoyageNbr()) : new VoyageNbr(VoyageEnum.INBOUND, inEdiVv.getInVoyageNbr());
            }
            return voyageNbr;
        }

        public static VoyageNbr resolveOutbound(IEdiVesselVisit inEdiVv) {
            VoyageNbr voyageNbr = null;
            if (inEdiVv != null) {
                voyageNbr = ArgoEdiUtils.isNotEmpty(inEdiVv.getOutOperatorVoyageNbr()) ? new VoyageNbr(VoyageEnum.OPR_OUTBOUND, inEdiVv.getOutOperatorVoyageNbr()) : new VoyageNbr(VoyageEnum.OUTBOUND, inEdiVv.getOutVoyageNbr());
            }
            return voyageNbr;
        }
    }

    private static enum VoyageEnum {
        INBOUND(IArgoPropertyKeys.INBOUND_VOYAGE),
        OPR_INBOUND(IArgoPropertyKeys.IN_OPERATOR_VOYAGE),
        OUTBOUND(IArgoPropertyKeys.OUTBOUND_VOYAGE),
        OPR_OUTBOUND(IArgoPropertyKeys.OUT_OPERATOR_VOYAGE);

        private IPropertyKey _desc;

        private VoyageEnum(IPropertyKey inDesc) {
            this._desc = inDesc;
        }

        public IPropertyKey getDesc() {
            return this._desc;
        }
    }

}
