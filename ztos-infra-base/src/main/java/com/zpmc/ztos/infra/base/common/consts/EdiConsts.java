package com.zpmc.ztos.infra.base.common.consts;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class EdiConsts {
    public static final String[] STOWPLAN_KEYWORDS = new String[]{"containerNbr", "vesselId", "vesselIdConvention", "inVoyageNbr"};
    public static final String[] RELEASE_KEYWORDS = new String[]{"releaseIdentifierNbr", "vesselName", "containerOperator", "ediCode"};
    public static final String[] ACTIVITY_KEYWORDS = new String[]{"containerNbr", "containerOperator", "activityType"};
    public static final String[] BOOKING_KEYWORDS = new String[]{"bookingNbr", "vesselName", "containerOperator"};
    public static final String[] PREADVISE_CONTAINER_KEYWORDS = new String[]{"containerNbr", "bookingNbr", "vesselName", "containerOperator"};
    public static final String[] PREADVISE_CHASSIS_KEYWORDS = new String[]{"chassisNbr", "bookingNbr", "vesselName", "chassisISOcode"};
    public static final String[] APPOINTMENT_KEYWORDS = new String[]{"containerId", "orderNbr", "vesselName", "containerOperator"};
    public static final String[] PERFORMANCE_KEYWORDS = new String[]{"vesselName"};
    public static final String[] ACKNOWLEDGEMENT_KEYWORDS = new String[]{"ediAckTranControlNbr", "ediAckInterchangeNbr", "ediAckReferenceNbr"};
    public static final String[] MANIFEST_KEYWORDS = new String[]{"blNbr", "vesselName", "vesselId", "shippingLineCode"};
    public static final String[] MANIFEST_CONTAINER_KEYWORDS = new String[]{"containerNbr", "vesselName", "containerISOcode", "containerStatus"};
    public static final String[] LOADLIST_KEYWORDS = new String[]{"containerId", "vesselName", "containerOperator"};
    public static final String[] DISCHLIST_KEYWORDS = new String[]{"containerId", "vesselName", "containerOperator"};
    public static final String[] RAILCONSIST_KEYWORDS = new String[]{"railCarId", "trainId", "containerNbr"};
    public static final String[] RAILORDER_KEYWORDS = new String[]{"railOrderNbr", "trainId", "containerNbr", "containerOperator"};
    public static final String[] HAZARD_KEYWORDS = new String[]{"containerNbr", "imdgClass", "containerOperator"};
    public static final String[] RAILWAYBILL_KEYWORDS = new String[]{"containerNbr", "wayBillNbr", "trainId", "railRoadId"};
    public static final String[] SAUDILDP_CONTAINER_KEYWORDS = new String[]{"containerNbr", "containerSeqNo", "vesselId", "manifestNbr"};
    public static final String[] SAUDILDP_BL_KEYWORDS = new String[]{"blNumber", "destination", "shippingLineCode", "manifestNbr"};
    public static final String[] SAUDILDP_BL_ITEM_KEYWORDS = new String[]{"bLSeqNbr", "itemSeqNo", "containerSeqNbr", "commodityCode"};
    public static final String[] SAUDILDP_LOAD_PERMIT_ITEM_KEYWORDS = new String[]{"lpNumber", "lpItemMsgFunction", "blNbr", "blItemSeqNbr"};
    public static final String[] MCPVESSELVISIT_KEYWORDS = new String[]{"uniqueVoyageId", "transactionCode"};
    public static final String[] VERMAS_KEYWORDS = new String[]{"containerNbr", "vesselId", "vesselIdConvention", "inVoyageNbr"};
    public static final String[] INVOICE_KEYWORDS = new String[]{"invoiceDraftNbr", "finalNumber", "type", "customerId"};
    public static final String[] SAUDIMNFT_CONTAINER_KEYWORDS = new String[]{"containerNbr", "containerSeqNo", "vesselId", "manifestNbr"};
    public static final String[] SAUDIMNFT_BL_KEYWORDS = new String[]{"blNumber", "vesselId", "shippingLineCode", "manifestNbr"};
    public static final String[] SAUDIMNFT_BL_ITEM_KEYWORDS = new String[]{"itemSeqNo", "bLSeqNbr", "containerSeqNbr", "commodityCode"};
    public static final String[] VESSEL_ACTIVITY_KEYWORDS = new String[]{"vesselId", "shippingLineCode", "facilityId", "eventType"};
    public static final String GOXML_LICENSE_FILE = "serialnum.txt";
    public static final String GOXML_LICENSE_PATH = System.getProperty("user.home") + File.separator + ".GoXml" + File.separator + "serialnum.txt";
    public static final String GOXML_EDIFACTHEADERFILE = "EDIFACTHeader.dic";
    public static final String GOXML_X12HEADERFILE = "X12Header.dic";
    public static final String GOXML_HEADEREXTN = ".dic";
    public static final String GOXML_FLATHEADEREXTN = ".fxd";
    public static final String GOXML_SCHEMAEXTN = ".xsd";
    public static final String BASE_MAPPING_SCHEMA = "baseMapping.xsd";
    public static final String SNX_TYPES_SCHEMA = "snx-types.xsd";
    public static final String EDI_EXTN = ".edi";
    public static final String TMP_EXTN = ".tmp";
    public static final String UNIT_DIRECTION_IN = "IN";
    public static final String UNIT_DIRECTION_OUT = "OUT";
    public static final String MAX_HOUR = "23";
    public static final String MAX_MINUTE = "59";
    public static final String MIN_SECOND = "0";
    public static final String ADDITIONAL_KEYWORD = "AdditionalKeyword";
    public static final String ID = "mg:id";
    public static final String EDI = "EDI_1";
    public static final String TRUE = "true";
    public static final String FALSE = "false";
    public static final String NAME = "mg:name";
    public static final String VALUE = "mg:value";
    public static final String SOURCE = "mg:Source";
    public static final String SEGLINE = "SegmentLine";
    public static final String ATTRIBUTE = "mg:Attribute";
    public static final String PROPERTIES = "mg:Properties";
    public static final String EDIFORMATTER = "EDIFormatter";
    public static final String SEGDELIMITER = "SegmentDelimiter";
    public static final String EDINTESEG = "EDIIgnoreNTESegment";
    public static final String CONFIGURATION = "mg:Configuration";
    public static final String SEPARATORS = "[,:; ]";
    public static final String BAPLIE_LOOPING_SEGMENT = "LOC+147";
    public static final String SESSION_GKEY = "SESSION_GKEY";
    public static final String INTERCHANGE_GKEY = "INTERCHANGE_GKEY";
    public static final String BATCH_GKEY = "BATCH_GKEY";
    public static final String TRANSACTION_GKEY = "TRANSACTION_GKEY";
    public static final String TRANSACTION_CONTROL_NUMBER = "TRANSACTION_CONTROL_NUMBER";
    public static final String SKIP_POSTER = "SKIP_POSTER";
    public static final String REPOST_FAILED_TRANSACTION = "REPOST_FAILED_TRANSACTION";
    private static final String SNX_PREFIX = "argo:";
    private static final String SNX_CONTAINER = "container";

    private EdiConsts() {
    }

    public static String[] getSnxKeywords(String inSnxRootElementName) {
        return EdiConsts.getSnxKeywordsMap().get(inSnxRootElementName);
    }

    private static Map<String, String[]> getSnxKeywordsMap() {
        HashMap<String, String[]> snxKeywordsMap = new HashMap<String, String[]>();
        snxKeywordsMap.put("argo:container", new String[]{"eqid"});
        return snxKeywordsMap;
    }

}
