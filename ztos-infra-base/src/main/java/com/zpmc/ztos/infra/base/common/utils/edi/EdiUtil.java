package com.zpmc.ztos.infra.base.common.utils.edi;

import com.zpmc.ztos.infra.base.business.dataobject.EdiSettingDO;
import com.zpmc.ztos.infra.base.business.edi.*;
import com.zpmc.ztos.infra.base.business.enums.argo.EdiMessageClassEnum;
import com.zpmc.ztos.infra.base.business.enums.edi.EdiFilterFieldIdEnum;
import com.zpmc.ztos.infra.base.business.enums.edi.EdiMessageDirectionEnum;
import com.zpmc.ztos.infra.base.business.enums.edi.EdiMessageStandardEnum;
import com.zpmc.ztos.infra.base.business.enums.framework.FileTypeEnum;
import com.zpmc.ztos.infra.base.business.enums.framework.MessageLevelEnum;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.common.configs.*;
import com.zpmc.ztos.infra.base.common.consts.EdiConsts;
import com.zpmc.ztos.infra.base.common.contexts.PortalApplicationContext;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.common.exceptions.BizFailure;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.helps.ContextHelper;
import com.zpmc.ztos.infra.base.common.messages.MessageCollectorFactory;
import com.zpmc.ztos.infra.base.common.model.TransactionParms;
import com.zpmc.ztos.infra.base.common.model.UserContext;
import com.zpmc.ztos.infra.base.common.scopes.Complex;
import com.zpmc.ztos.infra.base.common.utils.ArgoUtils;
import com.zpmc.ztos.infra.base.common.utils.DateUtil;
import com.zpmc.ztos.infra.base.common.utils.MessageCollectorUtils;
import com.zpmc.ztos.infra.base.utils.StringUtils;
import org.apache.commons.io.ByteOrderMark;
import org.apache.commons.io.input.BOMInputStream;
import org.apache.log4j.Logger;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.io.*;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

public class EdiUtil {
    private static final String LEFT_BRACKET = "[";
    private static final String RIGHT_BRACKET = "]";
    private static final String SPACE = " ";
    private static final String NEW_LINE = "\n";
    private static final String CARRIAGE_RETURN = "\r";
    private static final String TAB_SPACE = "\t";
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final SimpleDateFormat FULL_DATE_TIME = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final Logger LOGGER = Logger.getLogger(EdiUtil.class);
    private static final String XML_DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    private static final SimpleDateFormat XML_DATE_TIME_SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    private static final String DEFAULT_ENCODING = System.getProperty("file.encoding");
    private static final String XML_DATE_FORMAT = "yyyy-MM-dd";
    private static final SimpleDateFormat XML_DATE_SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    private EdiUtil() {
    }

    public static EdiMessageClassEnum getMessageClassFromXml(String inXml) {
        int endIndex;
        int index = inXml.indexOf("edi:snx");
        if (index == -1) {
            index = inXml.indexOf("argo:snx");
        }
        if (index != -1) {
            return EdiMessageClassEnum.SNX;
        }
        index = inXml.indexOf("edi:msgClass");
        if (index == -1) {
            index = inXml.indexOf("argo:msgClass");
        }
        if (index < 0) {
            return EdiMessageClassEnum.UNKNOWN;
        }
        if ((index = inXml.indexOf(34, index)) < 0) {
            return EdiMessageClassEnum.UNKNOWN;
        }
        if ((endIndex = inXml.indexOf(34, ++index)) < 0) {
            return EdiMessageClassEnum.UNKNOWN;
        }
        String msgClass = inXml.substring(index, endIndex);
        if ("STOWPLAN".equals(msgClass)) {
            return EdiMessageClassEnum.STOWPLAN;
        }
        if ("BOOKING".equals(msgClass)) {
            return EdiMessageClassEnum.BOOKING;
        }
        if ("PREADVISE".equals(msgClass)) {
            return EdiMessageClassEnum.PREADVISE;
        }
        if ("ACTIVITY".equals(msgClass)) {
            return EdiMessageClassEnum.ACTIVITY;
        }
        if ("ACKNOWLEDGEMENT".equals(msgClass)) {
            return EdiMessageClassEnum.ACKNOWLEDGEMENT;
        }
        if ("RELEASE".equals(msgClass)) {
            return EdiMessageClassEnum.RELEASE;
        }
        if ("MANIFEST".equals(msgClass)) {
            return EdiMessageClassEnum.MANIFEST;
        }
        if ("APPOINTMENT".equals(msgClass)) {
            return EdiMessageClassEnum.APPOINTMENT;
        }
        if ("LOADLIST".equals(msgClass)) {
            return EdiMessageClassEnum.LOADLIST;
        }
        if ("DISCHLIST".equals(msgClass)) {
            return EdiMessageClassEnum.DISCHLIST;
        }
        if ("RAILCONSIST".equals(msgClass)) {
            return EdiMessageClassEnum.RAILCONSIST;
        }
        if ("HAZARD".equals(msgClass)) {
            return EdiMessageClassEnum.HAZARD;
        }
        if ("RAILORDER".equals(msgClass)) {
            return EdiMessageClassEnum.RAILORDER;
        }
        if ("RAILWAYBILL".equals(msgClass)) {
            return EdiMessageClassEnum.RAILWAYBILL;
        }
        if (EdiMessageClassEnum.SAUDIMANIFEST.getKey().equals(msgClass)) {
            return EdiMessageClassEnum.SAUDIMANIFEST;
        }
        if (EdiMessageClassEnum.SAUDILDP.getKey().equals(msgClass)) {
            return EdiMessageClassEnum.SAUDILDP;
        }
        if (EdiMessageClassEnum.MCPVESSELVISIT.getKey().equals(msgClass)) {
            return EdiMessageClassEnum.MCPVESSELVISIT;
        }
        if (EdiMessageClassEnum.VERMAS.getKey().equals(msgClass)) {
            return EdiMessageClassEnum.VERMAS;
        }
        LOGGER.error((Object)("getMessageClassFromXml: unsupported msgClass = " + msgClass));
        return EdiMessageClassEnum.UNKNOWN;
    }

    @Deprecated
    public static String getDelimeter(EdiSession inSession) {
        EdiMessageStandardEnum msgStandard = EdiMessageStandardEnum.EDIFACT;
        if (inSession != null) {
            msgStandard = inSession.getEdisessMsgMap().getEdimapEdiMsgType().getEdimsgStandard();
        }
        return EdiUtil.getDelimeter(msgStandard);
    }

    @Deprecated
    public static String getDelimeter(EdiMessageStandardEnum inEnum) {
        if (EdiMessageStandardEnum.EDIFACT.equals((Object)inEnum)) {
            return EdifactUtil.EDIFACT_DELIMETERS.substring(4, 5);
        }
        if (inEnum.equals((Object) EdiMessageStandardEnum.X12)) {
            return X12Util.X12_DELIMETERS.substring(1, 2);
        }
        return EdifactUtil.EDIFACT_DELIMETERS.substring(4, 5);
    }

    public static String getSegmentDelimeter(EdiMessageStandardEnum inEnum) {
        if (EdiMessageStandardEnum.EDIFACT.equals((Object)inEnum)) {
            return EdifactUtil.EDIFACT_DELIMETERS.substring(4, 5);
        }
        if (inEnum.equals((Object) EdiMessageStandardEnum.X12)) {
            return X12Util.X12_DELIMETERS.substring(1, 2);
        }
        return EdifactUtil.EDIFACT_DELIMETERS.substring(4, 5);
    }

    public static String getDelimeters(EdiSession inSession) {
        String delimeters;
        if (inSession != null) {
            delimeters = inSession.getEdisessDelimeter();
            if (delimeters == null) {
                EdiMessageStandardEnum msgStandard = inSession.getEdisessMsgMap().getEdimapEdiMsgType().getEdimsgStandard();
                delimeters = EdiUtil.getDelimeters(msgStandard);
            }
        } else {
            EdiMessageStandardEnum msgStandard = EdiMessageStandardEnum.EDIFACT;
            delimeters = EdiUtil.getDelimeters(msgStandard);
        }
        return delimeters;
    }

    public static String getDelimeters(EdiMessageStandardEnum inEnum) {
        String delimeters = EdiMessageStandardEnum.EDIFACT.equals((Object)inEnum) ? EdifactUtil.EDIFACT_DELIMETERS : (inEnum.equals((Object) EdiMessageStandardEnum.X12) ? X12Util.X12_DELIMETERS : EdifactUtil.EDIFACT_DELIMETERS);
        return delimeters;
    }

    public static String getEdiMsgUniqueId(Element inRootElement) {
        return XmlUtil.getAttrValue(inRootElement, "msgUniqueId");
    }

    @Nullable
    public static Date getEdiMsgProducedDateTime(Element inRootElement) throws BizViolation {
        String dateString = XmlUtil.getAttrValue(inRootElement, "msgProducedDateTime");
        if (EdiUtil.isNotEmpty(dateString)) {
            try {
                return DateUtil.xmlDateStringToDate((String)dateString);
            }
            catch (ParseException e) {
                throw BizViolation.create((IPropertyKey) IEdiPropertyKeys.XML_DATE_FORMAT, null, (Object)dateString);
            }
        }
        return null;
    }

    public static String[] getKeywords(String[] inKeywordAttr, Element inRootElement) {
        String[] keywords = new String[inKeywordAttr.length];
        for (int j = 0; j < inKeywordAttr.length; ++j) {
            String keyword = XmlUtil.getAttrValue(inRootElement, inKeywordAttr[j]);
            if (EdiUtil.isNotEmpty(keyword) && keyword.length() > 80) {
                keyword = keyword.substring(0, 80);
            }
            keywords[j] = keyword;
        }
        return keywords;
    }

    public static Element applyFilterEntries(List inEdiFilterEntries, String inTranXml) {
        List filterEntryPatternBeans = EdiUtil.createFilterEntryPatternBeans(inEdiFilterEntries);
        return XmlUtil.getXmlRootElement(EdiUtil.applyFilterEntriesForTranXml(inTranXml, filterEntryPatternBeans));
    }

    public static String applyFilterEntriesForTranXml(String inTranXml, List inFilterEntryPatternBeans) {
        for (Object filterEntryPatternBeanObj : inFilterEntryPatternBeans) {
            FilterEntryPatternBean filterEntryPatternBean = (FilterEntryPatternBean)filterEntryPatternBeanObj;
            String fieldValue = filterEntryPatternBean.getFieldIdValue();
            String fromValue = filterEntryPatternBean.getFromValue();
            String toValue = filterEntryPatternBean.getToValue();
            Pattern pattern = filterEntryPatternBean.getPattern();
            inTranXml = XmlUtil.setXmlAttrElementValue(inTranXml, fieldValue, fromValue, toValue, pattern);
        }
        return inTranXml;
    }

    public static List createFilterEntryPatternBeans(List inEdiFilterEntries) {
        ArrayList<FilterEntryPatternBean> patterns = new ArrayList<FilterEntryPatternBean>();
        for (Object inEdiFilterEntry : inEdiFilterEntries) {
            EdiFilterEntry filterEntry = (EdiFilterEntry)inEdiFilterEntry;
            EdiFilterFieldIdEnum fieldId = filterEntry.getEdifltrenFieldId();
            try {
                String[] fieldIdArray = fieldId.getXmlFields();
                String fromValue = filterEntry.getEdifltrenFromValue();
                String toValue = filterEntry.getEdifltrenToValue();
                if (fieldIdArray.length == 0) {
                    String fieldIdValue = fieldId.getKey();
                    patterns.add(new FilterEntryPatternBean(fieldIdValue, fromValue, toValue));
                    continue;
                }
                for (String fieldIdValue : fieldIdArray) {
                    patterns.add(new FilterEntryPatternBean(fieldIdValue, fromValue, toValue));
                }
            }
            catch (Exception e) {
                throw new BizFailure(IEdiPropertyKeys.UNABLE_TO_PARSE_XML, (Throwable)e, null);
            }
        }
        return patterns;
    }

    public static String[] resolveKeywords(String inTransaction) {
        String[] snxKeywords;
        Element rootElement;
        EdiMessageClassEnum msgClassEnum = EdiUtil.getMessageClassFromXml(inTransaction);
        if (msgClassEnum.equals((Object) EdiMessageClassEnum.STOWPLAN)) {
            return EdiConsts.STOWPLAN_KEYWORDS;
        }
        if (msgClassEnum.equals((Object) EdiMessageClassEnum.RELEASE)) {
            return EdiConsts.RELEASE_KEYWORDS;
        }
        if (msgClassEnum.equals((Object) EdiMessageClassEnum.ACKNOWLEDGEMENT)) {
            return EdiConsts.ACKNOWLEDGEMENT_KEYWORDS;
        }
        if (msgClassEnum.equals((Object) EdiMessageClassEnum.ACTIVITY)) {
            return EdiConsts.ACTIVITY_KEYWORDS;
        }
        if (msgClassEnum.equals((Object) EdiMessageClassEnum.BOOKING)) {
            return EdiConsts.BOOKING_KEYWORDS;
        }
        if (msgClassEnum.equals((Object) EdiMessageClassEnum.PERFORMANCE)) {
            return EdiConsts.PERFORMANCE_KEYWORDS;
        }
        if (msgClassEnum.equals((Object) EdiMessageClassEnum.MANIFEST)) {
            if (inTransaction.contains("ediEquipment")) {
                return EdiConsts.MANIFEST_CONTAINER_KEYWORDS;
            }
            return EdiConsts.MANIFEST_KEYWORDS;
        }
        if (msgClassEnum.equals((Object) EdiMessageClassEnum.PREADVISE)) {
            String chassisNbr;
            String containerNbr = EdiUtil.getAttributeValue(inTransaction, "containerNbr");
            if (EdiUtil.isEmpty(containerNbr) && EdiUtil.isNotEmpty(chassisNbr = EdiUtil.getAttributeValue(inTransaction, "chassisNbr"))) {
                return EdiConsts.PREADVISE_CHASSIS_KEYWORDS;
            }
            return EdiConsts.PREADVISE_CONTAINER_KEYWORDS;
        }
        if (msgClassEnum.equals((Object) EdiMessageClassEnum.APPOINTMENT)) {
            return EdiConsts.APPOINTMENT_KEYWORDS;
        }
        if (msgClassEnum.equals((Object) EdiMessageClassEnum.LOADLIST)) {
            return EdiConsts.LOADLIST_KEYWORDS;
        }
        if (msgClassEnum.equals((Object) EdiMessageClassEnum.DISCHLIST)) {
            return EdiConsts.DISCHLIST_KEYWORDS;
        }
        if (msgClassEnum.equals((Object) EdiMessageClassEnum.RAILCONSIST)) {
            return EdiConsts.RAILCONSIST_KEYWORDS;
        }
        if (msgClassEnum.equals((Object) EdiMessageClassEnum.HAZARD)) {
            return EdiConsts.HAZARD_KEYWORDS;
        }
        if (msgClassEnum.equals((Object) EdiMessageClassEnum.RAILORDER)) {
            return EdiConsts.RAILORDER_KEYWORDS;
        }
        if (msgClassEnum.equals((Object) EdiMessageClassEnum.RAILWAYBILL)) {
            return EdiConsts.RAILWAYBILL_KEYWORDS;
        }
        if (EdiMessageClassEnum.SAUDILDP.equals((Object)msgClassEnum)) {
            return EdiUtil.getSaudiLdpKeywords(inTransaction);
        }
        if (EdiMessageClassEnum.SAUDIMANIFEST.equals((Object)msgClassEnum)) {
            return EdiUtil.getSaudiManifestKeywords(inTransaction);
        }
        if (EdiMessageClassEnum.VESSELACTIVITY.equals((Object)msgClassEnum) || EdiMessageClassEnum.VESSELACTIVITY_BY_CV.equals((Object)msgClassEnum)) {
            return EdiConsts.VESSEL_ACTIVITY_KEYWORDS;
        }
        if (msgClassEnum.equals((Object) EdiMessageClassEnum.INVOICE)) {
            return EdiConsts.INVOICE_KEYWORDS;
        }
        if (msgClassEnum.equals((Object) EdiMessageClassEnum.MCPVESSELVISIT)) {
            return EdiConsts.MCPVESSELVISIT_KEYWORDS;
        }
        if (msgClassEnum.equals((Object) EdiMessageClassEnum.VERMAS)) {
            return EdiConsts.VERMAS_KEYWORDS;
        }
        if (msgClassEnum.equals((Object) EdiMessageClassEnum.SNX) && (rootElement = XmlUtil.getXmlRootElement(inTransaction)) != null && rootElement.getFirstChild() != null && (snxKeywords = EdiConsts.getSnxKeywords(rootElement.getFirstChild().getNodeName())) != null && snxKeywords.length > 0) {
            return snxKeywords;
        }
        return EdiConsts.STOWPLAN_KEYWORDS;
    }

    public static String getAttributeValue(String inTransactionXml, String inAttrName) {
        int endIndex;
        int index = inTransactionXml.indexOf("argo:" + inAttrName);
        if (index < 0 && (index = inTransactionXml.indexOf("edi:" + inAttrName)) < 0) {
            return null;
        }
        if ((index = inTransactionXml.indexOf(34, index)) < 0) {
            return null;
        }
        if ((endIndex = inTransactionXml.indexOf(34, ++index)) < 0) {
            return null;
        }
        return inTransactionXml.substring(index, endIndex);
    }

    private static String[] getSaudiLdpKeywords(String inTransactionXml) {
        String entityType = EdiUtil.getEntityType(inTransactionXml);
        if ("REPLACEMENTCN".equals(entityType) || "CN".equals(entityType)) {
            return EdiConsts.SAUDILDP_CONTAINER_KEYWORDS;
        }
        if ("BL".equals(entityType)) {
            return EdiConsts.SAUDILDP_BL_KEYWORDS;
        }
        if ("BLITEM".equals(entityType)) {
            return EdiConsts.SAUDILDP_BL_ITEM_KEYWORDS;
        }
        if ("LOADPERMITITEM".equals(entityType)) {
            return EdiConsts.SAUDILDP_LOAD_PERMIT_ITEM_KEYWORDS;
        }
        return EdiConsts.STOWPLAN_KEYWORDS;
    }

    private static String[] getSaudiManifestKeywords(String inTransactionXml) {
        String entityType = EdiUtil.getEntityType(inTransactionXml);
        if ("CN".equals(entityType)) {
            return EdiConsts.SAUDIMNFT_CONTAINER_KEYWORDS;
        }
        if ("BL".equals(entityType)) {
            return EdiConsts.SAUDIMNFT_BL_KEYWORDS;
        }
        if ("BLITEM".equals(entityType)) {
            return EdiConsts.SAUDIMNFT_BL_ITEM_KEYWORDS;
        }
        return EdiConsts.STOWPLAN_KEYWORDS;
    }

    @Nullable
    private static String getEntityType(String inTransactionXml) {
        int endIndex;
        int index = inTransactionXml.indexOf("edi:entityType");
        if (index < 0) {
            return null;
        }
        if ((index = inTransactionXml.indexOf(34, index)) < 0) {
            return null;
        }
        if ((endIndex = inTransactionXml.indexOf(34, ++index)) < 0) {
            return null;
        }
        return inTransactionXml.substring(index, endIndex);
    }

    public static String findXsdFileName(EdiSession inSession) {
        EdiMessageType msgType = inSession.getEdisessMsgMap().getEdimapEdiMsgType();
        EdiMessageClassEnum msgClassEnum = msgType.getEdimsgClass();
        String xsdFileName = EdiMessageClassEnum.LOADLIST.equals((Object)msgClassEnum) ? (EdiMessageDirectionEnum.S.equals((Object)inSession.getEdisessDirection()) ? EdiMessageClassEnum.STOWPLAN.getKey().toLowerCase() : EdiMessageClassEnum.LOADLIST.getKey().toLowerCase()) : (EdiMessageClassEnum.DISCHLIST.equals((Object)msgClassEnum) ? (EdiMessageDirectionEnum.S.equals((Object)inSession.getEdisessDirection()) ? EdiMessageClassEnum.STOWPLAN.getKey().toLowerCase() : EdiMessageClassEnum.DISCHLIST.getKey().toLowerCase()) : (EdiMessageClassEnum.ACTIVITY_BY_CV.equals((Object)msgClassEnum) ? EdiMessageClassEnum.ACTIVITY.getKey().toLowerCase() : (EdiMessageClassEnum.VESSELACTIVITY_BY_CV.equals((Object)msgClassEnum) ? EdiMessageClassEnum.VESSELACTIVITY.getKey().toLowerCase() : (EdiMessageClassEnum.INVENTORY.equals((Object)msgClassEnum) ? EdiMessageClassEnum.SNX.getKey().toLowerCase() : msgClassEnum.getKey().toLowerCase()))));
        return xsdFileName + ".xsd";
    }

    public static List findXsdFileNames(EdiMessageClassEnum inMsgClassEnum) {
        ArrayList<String> msgClass = new ArrayList<String>();
        if (EdiMessageClassEnum.LOADLIST.equals((Object)inMsgClassEnum)) {
            msgClass.add(EdiMessageClassEnum.STOWPLAN.getKey().toLowerCase() + ".xsd");
            msgClass.add(EdiMessageClassEnum.LOADLIST.getKey().toLowerCase() + ".xsd");
        } else if (EdiMessageClassEnum.DISCHLIST.equals((Object)inMsgClassEnum)) {
            msgClass.add(EdiMessageClassEnum.STOWPLAN.getKey().toLowerCase() + ".xsd");
            msgClass.add(EdiMessageClassEnum.DISCHLIST.getKey().toLowerCase() + ".xsd");
        } else if (EdiMessageClassEnum.ACTIVITY_BY_CV.equals((Object)inMsgClassEnum)) {
            msgClass.add(EdiMessageClassEnum.ACTIVITY.getKey().toLowerCase() + ".xsd");
        } else if (EdiMessageClassEnum.INVENTORY.equals((Object)inMsgClassEnum)) {
            msgClass.add(EdiMessageClassEnum.SNX.getKey().toLowerCase() + ".xsd");
        } else if (EdiMessageClassEnum.VESSELACTIVITY_BY_CV.equals((Object)inMsgClassEnum)) {
            msgClass.add(EdiMessageClassEnum.VESSELACTIVITY.getKey().toLowerCase() + ".xsd");
        } else {
            msgClass.add(inMsgClassEnum.getKey().toLowerCase() + ".xsd");
        }
        return msgClass;
    }

    @Nullable
    public static Date convertToLocalTime(Date inDate) throws BizViolation {
        Date localDate;
        if (inDate == null) {
            return null;
        }
        try {
            localDate = FULL_DATE_TIME.parse(ArgoUtils.convertDateToLocalTime((Date)inDate));
        }
        catch (Exception e) {
            throw BizViolation.create((IPropertyKey) IEdiPropertyKeys.UNABLE_TO_PARSE_DATE, null, (Object)inDate);
        }
        return localDate;
    }

    @Nullable
    public static String convertToLocalDateWithTime(String inStrDate) throws BizViolation {
        if (inStrDate == null) {
            return null;
        }
        try {
            Date date = FULL_DATE_TIME.parse(inStrDate);
            return ArgoUtils.convertDateToLocalTime((Date)date);
        }
        catch (Exception e) {
            throw BizViolation.create((IPropertyKey) IEdiPropertyKeys.UNABLE_TO_PARSE_DATE, null, (Object)inStrDate);
        }
    }

    @Nullable
    public static String getOneSevereError(IMessageCollector inMessages) {
        Iterator it;
        String lastMsgText = null;
        if (inMessages != null && (it = inMessages.getMessages(MessageLevelEnum.SEVERE).iterator()).hasNext()) {
            Object msg = it.next();
            Locale locale = Locale.getDefault();
            if (msg instanceof BizViolation) {
                BizViolation bv = (BizViolation)((Object)msg);
                Iterator itBvChain = bv.iterator();
                if (itBvChain.hasNext()) {
                    BizViolation oneBv = (BizViolation)((Object)itBvChain.next());
                    lastMsgText = MessageCollectorUtils.expandMessage((IPropertyKey)oneBv.getMessageKey(), (Locale)locale, (Object[])oneBv.getParms());
                }
            }
//            else if (msg instanceof MetafieldUserMessageImp) {
//                Object[] params;
//                MetafieldUserMessageImp userMsg = (MetafieldUserMessageImp)msg;
//                Object[] umparams = userMsg.getParms();
//                if (userMsg.getMetafieldId() != null) {
//                    params = new Object[umparams.length + 1];
//                    params[0] = userMsg.getMetafieldId();
//                    System.arraycopy(userMsg.getParms(), 0, params, 1, userMsg.getParms().length);
//                } else {
//                    params = userMsg.getParms();
//                }
//                lastMsgText = MessageCollectorUtils.expandMessage((IPropertyKey)userMsg.getMessageKey(), (Locale)locale, (Object[])params);
//            }
        }
        return lastMsgText;
    }

    public static String removeEdiDelimeters(EdiSession inEdiSession, String inInterchangeData) {
        if (inEdiSession.getEdisessMsgMap() == null) {
            return inInterchangeData;
        }
        String delimeters = EdiUtil.getDelimeters(inEdiSession);
        String regex = LEFT_BRACKET + delimeters + RIGHT_BRACKET;
        return inInterchangeData.replaceAll(regex, SPACE);
    }

    public static IMessageCollector appendMsgCollector(IMessageCollector inToMessageCollector, IMessageCollector inFromMessageCollector, boolean inFilterDuplicate) {
        if (inToMessageCollector != null && inFromMessageCollector != null) {
            List messages = inFromMessageCollector.getMessages();
            for (Object message : messages) {
                IMetafieldUserMessage metafieldUserMessage = (IMetafieldUserMessage)message;
                if (inFilterDuplicate) {
                    if (message == null || inToMessageCollector.containsMessage(metafieldUserMessage.getMessageKey())) continue;
                    inToMessageCollector.appendMessage((IUserMessage)metafieldUserMessage);
                    continue;
                }
                inToMessageCollector.appendMessage((IUserMessage)metafieldUserMessage);
            }
        } else if (inToMessageCollector == null) {
            return inFromMessageCollector;
        }
        return inToMessageCollector;
    }

    public static Map translateErrorToStrMsg(IMessageCollector inFromMessageCollector, boolean inIsDuplicateAllowed) {
        List messages;
        HashMap<String, String> errorWarningMap = new HashMap<String, String>();
        StringBuilder concatenatedErrStrMsg = new StringBuilder("");
        StringBuilder concatenatedWarningStrMsg = new StringBuilder("");
        if (inFromMessageCollector != null && (messages = inFromMessageCollector.getMessages()) != null) {
            for (Object message : messages) {
                if (message == null || !(message instanceof IMetafieldUserMessage)) continue;
                IMetafieldUserMessage metafieldUserMessage = (IMetafieldUserMessage)message;
                String strMsg = EdiUtil.translateToStrMsg((IUserMessage)metafieldUserMessage);
                if (inIsDuplicateAllowed) {
                    if (MessageLevelEnum.SEVERE.equals((Object)metafieldUserMessage.getSeverity())) {
                        concatenatedErrStrMsg = concatenatedErrStrMsg.append(".\n").append(strMsg);
                        continue;
                    }
                    if (!MessageLevelEnum.WARNING.equals((Object)metafieldUserMessage.getSeverity())) continue;
                    concatenatedWarningStrMsg = concatenatedWarningStrMsg.append(".\n").append(strMsg);
                    continue;
                }
                if (MessageLevelEnum.SEVERE.equals((Object)metafieldUserMessage.getSeverity())) {
                    if (concatenatedErrStrMsg.toString().contains(strMsg)) continue;
                    concatenatedErrStrMsg = concatenatedErrStrMsg.append(".\n").append(strMsg);
                    continue;
                }
                if (!MessageLevelEnum.WARNING.equals((Object)metafieldUserMessage.getSeverity()) || concatenatedWarningStrMsg.toString().contains(strMsg)) continue;
                concatenatedWarningStrMsg = concatenatedWarningStrMsg.append(".\n").append(strMsg);
            }
        }
        if (StringUtils.isNotEmpty((String)concatenatedErrStrMsg.toString())) {
            errorWarningMap.put("BATCH_ERRORS", concatenatedErrStrMsg.toString());
        }
        if (StringUtils.isNotEmpty((String)concatenatedWarningStrMsg.toString())) {
            errorWarningMap.put("BATCH_WARNINGS", concatenatedWarningStrMsg.toString());
        }
        return errorWarningMap;
    }

    @Nullable
    public static String translateToStrMsg(IUserMessage inUserMessage) {
        if (inUserMessage != null) {
            IMessageTranslator messageTranslator = EdiUtil.getMessageTranslator();
            return messageTranslator.getMessage(inUserMessage.getMessageKey(), inUserMessage.getParms());
        }
        return null;
    }

    private static IMessageTranslator getMessageTranslator() {
        IMessageTranslatorProvider translatorProvider = (IMessageTranslatorProvider) PortalApplicationContext.getBean((String)"messageTranslatorProvider");
        return translatorProvider.getMessageTranslator(Locale.ENGLISH);
    }

    public static Map getAdditionalKeywords(Element inRootElement) {
        Element akeyword4;
        Element akeyword3;
        Element akeyword2;
        LinkedHashMap<String, String> additionalKeywords = new LinkedHashMap<String, String>();
        Element akeyword1 = XmlUtil.getXmlElement(inRootElement, "//AdditionalKeywords/AdditionalKeyword1");
        String prefix = XmlUtil.getPrefix(inRootElement) + ":";
        if (akeyword1 != null) {
            Node keywordNameNode = akeyword1.getAttributes().getNamedItem(prefix + "KeywordName");
            Node keywordValueNode = akeyword1.getAttributes().getNamedItem(prefix + "KeywordValue");
            if (keywordNameNode != null && keywordValueNode != null) {
                String keywordName = keywordNameNode.getNodeValue();
                String keywordValue = keywordValueNode.getNodeValue();
                if (EdiUtil.isNotEmpty(keywordName) && EdiUtil.isNotEmpty(keywordValue)) {
                    additionalKeywords.put(keywordName, keywordValue);
                }
            }
        }
        if ((akeyword2 = XmlUtil.getXmlElement(inRootElement, "//AdditionalKeywords/AdditionalKeyword2")) != null) {
            Node keywordNameNode = akeyword2.getAttributes().getNamedItem(prefix + "KeywordName");
            Node keywordValueNode = akeyword2.getAttributes().getNamedItem(prefix + "KeywordValue");
            if (keywordNameNode != null && keywordValueNode != null) {
                String keywordName = keywordNameNode.getNodeValue();
                String keywordValue = keywordValueNode.getNodeValue();
                if (EdiUtil.isNotEmpty(keywordName) && EdiUtil.isNotEmpty(keywordValue)) {
                    additionalKeywords.put(keywordName, keywordValue);
                }
            }
        }
        if ((akeyword3 = XmlUtil.getXmlElement(inRootElement, "//AdditionalKeywords/AdditionalKeyword3")) != null) {
            Node keywordNameNode = akeyword3.getAttributes().getNamedItem(prefix + "KeywordName");
            Node keywordValueNode = akeyword3.getAttributes().getNamedItem(prefix + "KeywordValue");
            if (keywordNameNode != null && keywordValueNode != null) {
                String keywordName = keywordNameNode.getNodeValue();
                String keywordValue = keywordValueNode.getNodeValue();
                if (EdiUtil.isNotEmpty(keywordName) && EdiUtil.isNotEmpty(keywordValue)) {
                    additionalKeywords.put(keywordName, keywordValue);
                }
            }
        }
        if ((akeyword4 = XmlUtil.getXmlElement(inRootElement, "//AdditionalKeywords/AdditionalKeyword4")) != null) {
            Node keywordNameNode = akeyword4.getAttributes().getNamedItem(prefix + "KeywordName");
            Node keywordValueNode = akeyword4.getAttributes().getNamedItem(prefix + "KeywordValue");
            if (keywordNameNode != null && keywordValueNode != null) {
                String keywordName = keywordNameNode.getNodeValue();
                String keywordValue = keywordValueNode.getNodeValue();
                if (EdiUtil.isNotEmpty(keywordName) && EdiUtil.isNotEmpty(keywordValue)) {
                    additionalKeywords.put(keywordName, keywordValue);
                }
            }
        }
        return additionalKeywords;
    }

//    public static boolean isScheduledPost(JobExecutionContext inJobExecutionContext) {
//        Long jobDefGkey = null;
//        JobDataMap dataMap = inJobExecutionContext.getMergedJobDataMap();
//        if (dataMap != null) {
//            jobDefGkey = (Long)dataMap.get((Object)"JobDefinitionGkey");
//        }
//        return jobDefGkey != null;
//    }

    public static BigInteger validateInterchangeNumber(String inInterchangeNumber) throws BizViolation {
        if (StringUtils.isEmpty((String)inInterchangeNumber)) {
            throw BizViolation.create((IPropertyKey) IEdiPropertyKeys.INTERCHANGE_NUMBER_IS_MISSING, null);
        }
        try {
            return new BigInteger(inInterchangeNumber);
        }
        catch (Exception e) {
            throw BizViolation.create((IPropertyKey) IEdiPropertyKeys.INTERCHANGE_NUMBER_IS_INVALID, null, (Object)inInterchangeNumber);
        }
    }

    public static boolean isValidName(String inName) {
        char[] str = new char[inName.length()];
        inName.getChars(0, inName.length(), str, 0);
        for (int i = 0; i < inName.length(); ++i) {
            char ch = inName.charAt(i);
            if (Character.isLetterOrDigit(ch) || ch == '_' || ch == '-' || ch == ' ') continue;
            return false;
        }
        return true;
    }

    @Nullable
    public static BizViolation convertMetafieldUserMessage(IMetafieldUserMessage inUserMessage) {
        if (inUserMessage == null) {
            return null;
        }
        BizViolation bv = new BizViolation(inUserMessage.getMessageKey(), null, null, null, inUserMessage.getParms());
        bv.setSeverity(inUserMessage.getSeverity());
        return bv;
    }

    public static boolean isEmpty(String inStr) {
        return inStr == null || StringUtils.isEmpty((String)inStr.trim());
    }

    public static boolean isNotEmpty(String inStr) {
        return inStr != null && StringUtils.isNotEmpty((String)inStr.trim());
    }

    public static IMessageCollector getMessageCollector() {
        TransactionParms parms = TransactionParms.getBoundParms();
        IMessageCollector msgCollector = parms.getMessageCollector();
        if (msgCollector == null) {
            msgCollector = MessageCollectorFactory.createMessageCollector();
        }
        return msgCollector;
    }

    public static boolean isXmlDocument(String inFilename) {
        boolean isXml = false;
        if (inFilename != null) {
            try {
                FileTypeEnum fileType = FileTypeEnum.getFileType((String)inFilename);
                if (FileTypeEnum.XML.equals((Object)fileType)) {
                    isXml = true;
                }
            }
            catch (IllegalArgumentException illegalArgumentException) {
                // empty catch block
            }
        }
        return isXml;
    }

    public static Object getConfigValue(EdiSession inSession, AbstractConfig inConfig) {
        String settingValueInSession = inSession.getConfigValue((IConfig)inConfig);
        if (inConfig instanceof LongConfig) {
            if (EdiUtil.isEmpty(settingValueInSession)) {
                return ((LongConfig)inConfig).getValue(ContextHelper.getThreadUserContext());
            }
            return new Long(settingValueInSession);
        }
        if (inConfig instanceof StringConfig) {
            if (EdiUtil.isEmpty(settingValueInSession)) {
                return ((StringConfig)inConfig).getSetting(ContextHelper.getThreadUserContext());
            }
            return settingValueInSession;
        }
        if (inConfig instanceof BooleanConfig) {
            if (EdiUtil.isEmpty(settingValueInSession)) {
                return ((BooleanConfig)inConfig).isOn(ContextHelper.getThreadUserContext());
            }
            return Boolean.valueOf(settingValueInSession);
        }
        return null;
    }

    public static void createOrUpdateEdiSetting(EdiSession inSession, AbstractConfig inConfig, Object inCofigValue) {
        EdiSettingDO ediSetting = null;
        Set settings = inSession.getEdisessSettings();
        if (settings != null) {
            String configId = inConfig.getConfigId();
            for (Object setting : settings) {
                if (!configId.equals(((EdiSettingDO)setting).getEdistngConfigId())) continue;
                ediSetting = (EdiSettingDO)setting;
            }
        }
        if (ediSetting == null) {
            ediSetting = new EdiSettingDO();
            ediSetting.setFieldValue(IEdiField.EDISTNG_CONFIG_ID, inConfig.getConfigId());
            ediSetting.setFieldValue(IEdiField.EDISTNG_SESSION, inSession);
        }
        if (inConfig instanceof BooleanConfig) {
            ediSetting.setFieldValue(IEdiField.EDISTNG_VALUE, inCofigValue.toString());
        } else {
            ediSetting.setFieldValue(IEdiField.EDISTNG_VALUE, inCofigValue);
        }
        HibernateApi.getInstance().save((Object)ediSetting);
    }

//    @Nullable
//    public static String getGoXmlDelimiter(EdiMessageMap inMessageMap, EdiExtractDao inOutEdiExtractDao) {
//        String goXmlDelimiter = null;
//        if (inOutEdiExtractDao != null && EdiUtil.isEmpty(goXmlDelimiter = inOutEdiExtractDao.getGoXmlDelimiter()) && inMessageMap != null) {
//            goXmlDelimiter = EdiUtil.getStandardDelimiter(inMessageMap);
//        }
//        return goXmlDelimiter;
//    }

    @Nullable
    public static String getStandardDelimiter(EdiMessageMap inMessageMap) {
        String standardDelimiter = null;
        if (inMessageMap != null) {
            standardDelimiter = EdiUtil.getStandardDelimiter(inMessageMap.getEdimapEdiMsgType().getEdimsgStandard());
        }
        return standardDelimiter;
    }

    @Nullable
    public static String getStandardDelimiter(EdiMessageStandardEnum inMessageStandard) {
        String standardDelimiter = null;
        if (EdiMessageStandardEnum.EDIFACT.equals((Object)inMessageStandard)) {
            standardDelimiter = "'";
        } else if (EdiMessageStandardEnum.X12.equals((Object)inMessageStandard)) {
            standardDelimiter = "~";
        } else if (EdiMessageStandardEnum.FLATFILE.equals((Object)inMessageStandard) || EdiMessageStandardEnum.XML.equals((Object)inMessageStandard)) {
            standardDelimiter = NEW_LINE;
        }
        return standardDelimiter;
    }

    public static String getDelimiter(String inSessionDelimiter, String inGoxmlDelimiter, EdiMessageMap inMessageMap) {
        String delimiter = "";
        if (EdiUtil.isNotEmpty(inSessionDelimiter)) {
            delimiter = inSessionDelimiter;
        } else if (EdiUtil.isNotEmpty(inGoxmlDelimiter)) {
            delimiter = inGoxmlDelimiter;
        } else if (inMessageMap != null) {
            delimiter = EdiUtil.getStandardDelimiter(inMessageMap);
        }
        delimiter = EdiUtil.resolveDelimiter(delimiter);
        return delimiter;
    }

    public static String resolveDelimiter(String inDelimiter) {
        if ("\\n".equals(inDelimiter)) {
            inDelimiter = NEW_LINE;
        } else if ("\\r".equals(inDelimiter)) {
            inDelimiter = CARRIAGE_RETURN;
        } else if ("\\t".equals(inDelimiter)) {
            inDelimiter = TAB_SPACE;
        } else if (inDelimiter.startsWith("\\") && !inDelimiter.equals("\\")) {
            inDelimiter = inDelimiter.substring(0, 2);
        }
        return inDelimiter;
    }

    @Nullable
    public static EdiSession getEdiSessionForEdiInterchangeGkey(Serializable inEdiintgkey) {
        Set batchSet;
        EdiSession session = null;
        EdiInterchange ediint = (EdiInterchange)HibernateApi.getInstance().load(EdiInterchange.class, inEdiintgkey);
        if (ediint != null && (batchSet = ediint.getEdiBatchSet()) != null && !batchSet.isEmpty()) {
            EdiBatch ediBatch = (EdiBatch)batchSet.iterator().next();
            session = ediBatch.getEdibatchSession();
        }
        return session;
    }

    public static UserContext determineEdiUserContext(EdiSession inSession) {
        Complex complex = inSession.getEdisessComplex();
//        return ContextHelper.getEdiUserContextForScope((IScopeEnum) ScopeEnum.COMPLEX, (Serializable)complex.getCpxGkey());
        return null;
    }

    public static String translateDateToXmlFormat(Date inValue, TimeZone inTz) {
        XML_DATE_TIME_SIMPLE_DATE_FORMAT.setTimeZone(inTz);
        return XML_DATE_TIME_SIMPLE_DATE_FORMAT.format(inValue);
    }

    public static String translateDateToXmlDateFormat(Date inDate) {
        return XML_DATE_SIMPLE_DATE_FORMAT.format(inDate);
    }

    public static String resolveDelimiter(String inFile, EdiBatch inBatch) {
        String delimiterStr;
        String headerSegment = "";
        EdiSession session = inBatch.getEdibatchSession();
        EdiMessageStandardEnum msgStandard = session.getEdisessMsgMap().getEdimapEdiMsgType().getEdimsgStandard();
        if (!EdiMessageStandardEnum.FLATFILE.equals((Object)msgStandard)) {
            headerSegment = StringUtils.mid((String)inFile, (int)0, (int)120).trim();
        }
        if ((delimiterStr = EdiUtil.resolveDelimiterStrByStandard(inBatch, headerSegment)) == null) {
            throw new BizFailure(IEdiPropertyKeys.UNABLE_TO_PARSE_EDI, null, null);
        }
        return String.valueOf((char) EdiUtil.findDelimiterByte(delimiterStr));
    }

    public static String resolveDelimiter(byte[] inFile, EdiBatch inBatch) {
        String headerSegment;
        String delimiterStr;
        EdiSession session = inBatch.getEdibatchSession();
        EdiInterchange interchange = inBatch.getEdibatchInterchange();
        StringBuilder sb = new StringBuilder();
        EdiMessageStandardEnum msgStandard = session.getEdisessMsgMap().getEdimapEdiMsgType().getEdimsgStandard();
        if (!EdiMessageStandardEnum.FLATFILE.equals((Object)msgStandard)) {
            for (int i = 0; i < 120; ++i) {
                byte b = inFile[i];
                sb.append((char)b);
            }
        }
        if ((delimiterStr = EdiUtil.resolveDelimiterStrByStandard(inBatch, headerSegment = sb.toString().trim())) == null) {
            throw new BizFailure(IEdiPropertyKeys.UNABLE_TO_PARSE_EDI, null, null);
        }
        interchange.setFieldValue(IEdiField.EDIINT_DELIMITERS, delimiterStr);
        HibernateApi.getInstance().update((Object)interchange);
        return delimiterStr;
    }

    private static byte findDelimiterByte(String inDelimiterStr) {
        int delimiterByte = 0;
        if (inDelimiterStr.equals("\\n") || inDelimiterStr.equals(NEW_LINE)) {
            delimiterByte = 10;
        } else if (inDelimiterStr.equals("\\r") || inDelimiterStr.equals(CARRIAGE_RETURN)) {
            delimiterByte = 13;
        } else {
            try {
                delimiterByte = inDelimiterStr.getBytes(DEFAULT_ENCODING)[0];
            }
            catch (UnsupportedEncodingException e) {
                LOGGER.error((Object)("Unsupported Encoding format: " + e.getStackTrace()));
            }
        }
        return (byte)delimiterByte;
    }

    private static String resolveDelimiterStrByStandard(EdiBatch inBatch, String inHeaderSegment) {
        String delimiterStr;
        EdiSession session = inBatch.getEdibatchSession();
        EdiInterchange interchange = inBatch.getEdibatchInterchange();
        if (inHeaderSegment.length() > 7 && inHeaderSegment.startsWith(EdifactUtil.EDIFACT_DELIMETERHEADER)) {
            delimiterStr = String.valueOf(inHeaderSegment.charAt(8));
        } else if (inHeaderSegment.length() > 103 && inHeaderSegment.startsWith(X12Util.X12_INTERCHANGEHEADER)) {
            delimiterStr = String.valueOf(inHeaderSegment.charAt(105));
        } else {
            delimiterStr = session.getEdisessDelimeter();
            if (delimiterStr == null || delimiterStr.length() == 0) {
                EdiMessageStandardEnum msgStandard = session.getEdisessMsgMap().getEdimapEdiMsgType().getEdimsgStandard();
                if (EdiMessageStandardEnum.EDIFACT.equals((Object)msgStandard)) {
                    delimiterStr = "'";
                } else if (EdiMessageStandardEnum.X12.equals((Object)msgStandard)) {
                    delimiterStr = "~";
                }
            }
        }
        return delimiterStr;
    }

//    public static AbstractEdiInboundFlatFileBatchProcessor resolveFlatFileProcessor(EdiBatch inBatch) throws BizViolation {
//        AbstractConfig setting = EdiUtil.resolveFlatFileSessionSetting(inBatch);
//        if (setting == null) {
//            return null;
//        }
//        if (setting.getConfigId().equals(ArgoConfig.EDI_FLAT_FILE_IDENTIFY_SEGMENTS_BY_VALUE.getConfigId())) {
//            return new EdiFlatFileBatchProcessorIdentifyByValue(inBatch, setting);
//        }
//        if (setting.getConfigId().equals(ArgoConfig.EDI_FLAT_FILE_IDENTIFY_SEGMENTS_BY_LINE.getConfigId())) {
//            return new EdiFlatFileBatchProcessorIdentifyByLine(inBatch, setting);
//        }
//        return new EdiFlatFileBatchProcessorIdentifyByRegex(inBatch, setting);
//    }

    public static boolean hasEmptyExtractMessage(IMessageCollector inCltr) {
        return inCltr.containsMessage(IEdiPropertyKeys.NO_EVENTS_EXTRACT_FAILURE) || inCltr.containsMessage(IEdiPropertyKeys.NO_INVENTORY_DATA_FOR_PREDICATE) || inCltr.containsMessage(IEdiPropertyKeys.NO_DATA_FOR_INVOICE) || inCltr.containsMessage(IEdiPropertyKeys.NO_DATA_FOR_CV) || inCltr.containsMessage(IEdiPropertyKeys.NO_DATA_FOR_CREDIT) || inCltr.containsMessage(IEdiPropertyKeys.NO_DATA_FOR_MANIFEST);
    }

//    public static ComponentEventMessageResponse broadcastEdiPostingRuleRefresh(@NotNull UserContext inUserContext, Map<String, Serializable> inMapParams) {
//        IFrameworkComponentRefresher componentRefresher = ComponentEventBeanUtils.getComponentRefresher();
//        return componentRefresher.refreshFrameworkComponentForCluster(inUserContext, ArgoClusterableEvents.CLUSTERABLE_EDI_POSTING_RULE_REFRESH, inMapParams);
//    }

    private static AbstractConfig resolveFlatFileSessionSetting(EdiBatch inBatch) {
        String segmentIdentifierByValue = (String) EdiUtil.getConfigValue(inBatch.getEdibatchSession(), (AbstractConfig)ArgoConfig.EDI_FLAT_FILE_IDENTIFY_SEGMENTS_BY_VALUE);
        String segmentIdentifierByLine = (String) EdiUtil.getConfigValue(inBatch.getEdibatchSession(), (AbstractConfig)ArgoConfig.EDI_FLAT_FILE_IDENTIFY_SEGMENTS_BY_LINE);
        String segmentIdentifierByRegex = (String) EdiUtil.getConfigValue(inBatch.getEdibatchSession(), (AbstractConfig)ArgoConfig.EDI_FLAT_FILE_IDENTIFY_SEGMENTS_BY_REGEX);
        StringConfig setting = null;
        if (StringUtils.isNotEmpty((String)segmentIdentifierByValue)) {
            setting = ArgoConfig.EDI_FLAT_FILE_IDENTIFY_SEGMENTS_BY_VALUE;
        } else if (StringUtils.isNotEmpty((String)segmentIdentifierByLine)) {
            setting = ArgoConfig.EDI_FLAT_FILE_IDENTIFY_SEGMENTS_BY_LINE;
        } else if (StringUtils.isNotEmpty((String)segmentIdentifierByRegex)) {
            setting = ArgoConfig.EDI_FLAT_FILE_IDENTIFY_SEGMENTS_BY_REGEX;
        }
        return setting;
    }

    public static InputStreamReader filterBOMFromInputStream(InputStream inStream) {
        BOMInputStream bOMInputStream = new BOMInputStream(inStream);
        ByteOrderMark bom = null;
        InputStreamReader reader = null;
        try {
            bom = bOMInputStream.getBOM();
        }
        catch (IOException inE) {
            LOGGER.error((Object)((Object) IEdiPropertyKeys.UNABLE_TO_READ_XML + inE.getMessage()));
            throw BizFailure.create((IPropertyKey) IEdiPropertyKeys.UNABLE_TO_READ_XML, null);
        }
        String charsetName = bom == null ? DEFAULT_ENCODING : bom.getCharsetName();
        try {
            reader = new InputStreamReader((InputStream)new BufferedInputStream((InputStream)bOMInputStream), charsetName);
        }
        catch (UnsupportedEncodingException inE) {
            LOGGER.error((Object)((Object) IEdiPropertyKeys.UNABLE_TO_READ_XML + inE.getMessage()));
            throw BizFailure.create((IPropertyKey) IEdiPropertyKeys.UNABLE_TO_READ_XML, null);
        }
        return reader;
    }

}
