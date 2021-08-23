package com.zpmc.ztos.infra.base.common.configs;

public class InventoryConfig {
    public static final String BILLING_CREATE_STORAGE_PRIMARY = "PRIMARY";
    public static final String BILLING_CREATE_STORAGE_CARRIAGE = "CARRIAGE";
    public static final String BILLING_CREATE_STORAGE_PAYLOAD = "PAYLOAD";
    public static final String BILLING_CREATE_STORAGE_ACCESSORY = "ACCESSORY";
    public static final String BILLING_CREATE_STORAGE_ACCESSORY_ON_CHS = "ACCESSORY_ON_CHS";
    public static final String BILLING_QUERY_UNIT_CHARGES_CONTRACT_CUSTOMER_DEFAULT_VALUE_UNIT_OPERATOR_ID = "UNIT_OPERATOR_ID";
    public static final String BILLING_CREATE_LINE_STORAGE_PRIMARY = "PRIMARY";
    public static final String BILLING_CREATE_LINE_STORAGE_CARRIAGE = "CARRIAGE";
    public static final String BILLING_CREATE_LINE_STORAGE_PAYLOAD = "PAYLOAD";
    public static final String BILLING_CREATE_LINE_STORAGE_ACCESSORY = "ACCESSORY";
    public static final String BILLING_CREATE_LINE_STORAGE_ACCESSORY_ON_CHS = "ACCESSORY_ON_CHS";
    public static final String DIRECT_DELIVERY_RAIL_ALWAYS = "ALWAYS";
    public static final String DIRECT_DELIVERY_RAIL_NEVER = "NEVER";
    public static final String BARGE_POPULATE_GROSS_WEIGHT_FROM_BKG_ITEM_DO_NOT_POPULATE_GROSS_WEIGHT = "DO_NOT_POPULATE_GROSS_WEIGHT";
    public static final String BARGE_POPULATE_GROSS_WEIGHT_FROM_BKG_ITEM_POPULATE_GROSS_WEIGHT = "POPULATE_GROSS_WEIGHT";
    public static final String BARGE_POPULATE_GROSS_WEIGHT_FROM_BKG_ITEM_POPULATE_EQTYPE_TARE_WEIGHT_IF_OVERWEIGHT = "POPULATE_EQTYPE_TARE_WEIGHT_IF_OVERWEIGHT";
    public static final String INVENTORY_PAGE_FLOW_STAY_ON_SAME_PAGE = "STAY_ON_SAME_PAGE";
    public static final String INVENTORY_PAGE_FLOW_RETURN_TO_PREVIOUS_PAGE = "RETURN_TO_PREVIOUS_PAGE";
    public static final String INBOUND_TRAIN_POPULATE_GROSS_WEIGHT_FROM_BKG_ITEM_DO_NOT_POPULATE_GROSS_WEIGHT = "DO_NOT_POPULATE_GROSS_WEIGHT";
    public static final String INBOUND_TRAIN_POPULATE_GROSS_WEIGHT_FROM_BKG_ITEM_POPULATE_GROSS_WEIGHT = "POPULATE_GROSS_WEIGHT";
    public static final String INBOUND_TRAIN_POPULATE_GROSS_WEIGHT_FROM_BKG_ITEM_POPULATE_EQTYPE_TARE_WEIGHT_IF_OVERWEIGHT = "POPULATE_EQTYPE_TARE_WEIGHT_IF_OVERWEIGHT";
    public static final LongConfig WEIGHT_MARGIN_FOR_UNIT_SWAPPING_TOLERANCE = new LongConfig("INV", "CORE_FEAT", "WEIGHT_MARGIN_FOR_UNIT_SWAPPING_TOLERANCE", "001", 2000L, 0L, 99999L, 5, 1, null, "3.02", null, null, null);
    public static final LongConfig WEIGHT_MARGIN_PERCENTAGE_FOR_UNIT_SWAPPING_TOLERANCE = new LongConfig("INV", "CORE_FEAT", "WEIGHT_MARGIN_PERCENTAGE_FOR_UNIT_SWAPPING_TOLERANCE", "002", 10L, 0L, 100L, 5, 1, null, "3.02", null, null, null);
    public static final XmlConfig BILLING_EXTRACT_MAP = new XmlConfig("INV", "BILLING", "BILLING_EXTRACT_MAP", "001", false, 3, 2, null, "1.2", null, null, null);
    public static final CodedConfig BILLING_CREATE_STORAGE = new CodedConfig("INV", "BILLING", "BILLING_CREATE_STORAGE", "002", true, "", new String[]{"PRIMARY", "CARRIAGE", "PAYLOAD", "ACCESSORY", "ACCESSORY_ON_CHS"}, 4, 2, null, "1.2", null, null, null);
    public static final BooleanConfig BILLING_CREATE_REEFER = new BooleanConfig("INV", "BILLING", "BILLING_CREATE_REEFER", "003", false, 4, 2, null, "1.2", null, null, null);
    public static final LongConfig BILLING_EXTRACT_FREQUENCY_IN_MINUTES = new LongConfig("INV", "BILLING", "BILLING_EXTRACT_FREQUENCY_IN_MINUTES", "004", 1L, 5L, 99999L, 3, 2, null, "1.2", null, null, null);
    public static final BooleanConfig BILLING_EXTRACT_USE_EVENT_TYPE_ID = new BooleanConfig("INV", "BILLING", "BILLING_EXTRACT_USE_EVENT_TYPE_ID", "005", true, 5, 1, null, "1.4", null, null, null);
    public static final XmlConfig BILLING_UPDATE_EXTRACT_ON_ENTITY_UPDATE = new XmlConfig("INV", "BILLING", "BILLING_UPDATE_EXTRACT_ON_ENTITY_UPDATE", "010", false, 5, 1, null, "1.4", null, null, null);
    public static final BooleanConfig BILLING_EXCLUDE_WAIVED_DAYS_FOR_TIER_CALCULATION = new BooleanConfig("INV", "BILLING", "BILLING_EXCLUDE_WAIVED_DAYS_FOR_TIER_CALCULATION", "011", false, 5, 1, null, "1.4", null, null, null);
    public static final XmlConfig BILLING_EXCLUDE_QUERY_CHARGE_PAYMENT_TYPES = new XmlConfig("INV", "BILLING", "BILLING_EXCLUDE_QUERY_CHARGE_PAYMENT_TYPES", "012", false, 5, 1, null, "2.0", null, null, null);
    public static final BooleanConfig BILLING_SKIP_VALIDATION_FOR_OAC_GUARANTEE_OF_SELF_SERVICE_GUARANTEE = new BooleanConfig("INV", "BILLING", "BILLING_SKIP_VALIDATION_FOR_OAC_GUARANTEE_OF_SELF_SERVICE_GUARANTEE", "013", false, 5, 1, null, "1.4", null, null, null);
    public static final XmlConfig BILLING_UPDATE_EXTRACT_FLEX_ON_ENTITY_UPDATE_FLEX = new XmlConfig("INV", "BILLING", "BILLING_UPDATE_EXTRACT_FLEX_ON_ENTITY_UPDATE_FLEX", "014", false, 5, 1, null, "2.1", null, null, null);
    public static final CodedConfig BILLING_QUERY_UNIT_CHARGES_CONTRACT_CUSTOMER_DEFAULT_VALUE = new CodedConfig("INV", "BILLING", "BILLING_QUERY_UNIT_CHARGES_CONTRACT_CUSTOMER_DEFAULT_VALUE", "015", false, "--", new String[]{"UNIT_OPERATOR_ID"}, 5, 1, null, "2.2", null, null, null);
    public static final CodedConfig BILLING_CREATE_LINE_STORAGE = new CodedConfig("INV", "BILLING", "BILLING_CREATE_LINE_STORAGE", "016", true, "", new String[]{"PRIMARY", "CARRIAGE", "PAYLOAD", "ACCESSORY", "ACCESSORY_ON_CHS"}, 4, 2, null, "2.3", null, null, null);
    public static final BooleanConfig BILLING_CREDIT_CARD_PROCESSED_EXTERNALLY = new BooleanConfig("INV", "BILLING", "BILLING_CREDIT_CARD_PROCESSED_EXTERNALLY", "017", false, 5, 1, null, "3.1", null, null, null);
    public static final BooleanConfig UNIT_OWNER_PUSH = new BooleanConfig("INV", "MAINT", "UNIT_OWNER_PUSH", "001", false, 4, 1, null, "1.0", null, null, null);
    public static final LongConfig UNIT_DEPARTED_HISTORY = new LongConfig("INV", "MAINT", "UNIT_DEPARTED_HISTORY", "002", 14L, 0L, 360L, 5, 1, null, "1.0.10", null, null, null);
    public static final BooleanConfig CLEAR_CTR_DAMAGES = new BooleanConfig("INV", "DAMAGES", "CLEAR_CTR_DAMAGES", "001", true, 4, 1, null, "1.5T", null, null, null);
    public static final BooleanConfig CLEAR_CHS_DAMAGES = new BooleanConfig("INV", "DAMAGES", "CLEAR_CHS_DAMAGES", "002", true, 4, 1, null, "1.5T", null, null, null);
    public static final BooleanConfig CLEAR_ACC_DAMAGES = new BooleanConfig("INV", "DAMAGES", "CLEAR_ACC_DAMAGES", "003", true, 4, 1, null, "1.5T", null, null, null);
    public static final LongConfig OUTGATE_RETAIN_DAYS = new LongConfig("INV", "OUTGATE", "OUTGATE_RETAIN_DAYS", "001", 2L, 1L, 30L, 5, 1, null, "1.1.2", null, null, null);
    public static final LongConfig OUTGATE_BATCH = new LongConfig("INV", "OUTGATE", "OUTGATE_BATCH", "002", 5L, 1L, 50L, 5, 1, null, "1.1.2", null, null, null);
    public static final LongConfig OUTGATE_FREQ = new LongConfig("INV", "OUTGATE", "OUTGATE_FREQ", "003", 120L, 20L, 3600L, 5, 1, null, "1.1.2", null, null, null);
    public static final BooleanConfig COPY_UFV_FLEX_FIELDS = new BooleanConfig("INV", "OUTGATE", "COPY_UFV_FLEX_FIELDS", "004", false, 5, 1, null, "1.7.A", null, null, null);
    public static final LongConfig RFRSH_FREQ = new LongConfig("INV", "REFRESH", "RFRSH_FREQ", "001", 30L, 20L, 120L, 5, 1, null, "1.0", null, null, null);
    public static final LongConfig RFRSH_BATCH = new LongConfig("INV", "REFRESH", "RFRSH_BATCH", "002", 3000L, 10L, 5000L, 5, 1, null, "1.0", null, null, null);
    public static final LongConfig RFRSH_STALE = new LongConfig("INV", "REFRESH", "RFRSH_STALE", "003", 100L, 10L, 500L, 5, 1, null, "1.0", null, null, null);
    public static final BooleanConfig LFD_DYNAMIC_CALC = new BooleanConfig("INV", "DEMURRAGE", "LFD_DYNAMIC_CALC", "001", true, 5, 1, null, "1.0", null, null, null);
    public static final StringConfig UNIT_FLAG_1 = new StringConfig("INV", "SERVICESFLAGS", "UNIT_FLAG_1", "001", "", 50, false, 4, 2, null, "1.0.9", null, null, null);
    public static final StringConfig UNIT_FLAG_2 = new StringConfig("INV", "SERVICESFLAGS", "UNIT_FLAG_2", "002", "", 50, false, 4, 2, null, "1.0.9", null, null, null);
    public static final StringConfig UNIT_FLAG_3 = new StringConfig("INV", "SERVICESFLAGS", "UNIT_FLAG_3", "003", "", 50, false, 4, 2, null, "1.0.9", null, null, null);
    public static final StringConfig UNIT_FLAG_4 = new StringConfig("INV", "SERVICESFLAGS", "UNIT_FLAG_4", "004", "", 50, false, 4, 2, null, "1.0.9", null, null, null);
    public static final BooleanConfig STRIP_CHECK_DIGIT = new BooleanConfig("INV", "STRIP", "STRIP_CHECK_DIGIT", "001", false, 5, 1, null, "1.4", null, null, null);
    public static final BooleanConfig TRUE_BILLS_OF_LADING = new BooleanConfig("INV", "BL", "TRUE_BILLS_OF_LADING", "001", true, 5, 1, null, "1.4", null, null, null);
    public static final BooleanConfig BILLS_OF_LADING_FOR_ALL_IMPORT_EMPTIES = new BooleanConfig("INV", "BL", "BILLS_OF_LADING_FOR_ALL_IMPORT_EMPTIES", "002", false, 5, 1, null, "1.5", null, null, null);
    public static final BooleanConfig INCLUDE_PAYLOAD_WEIGHT = new BooleanConfig("INV", "WEIGHT", "INCLUDE_PAYLOAD_WEIGHT", "001", false, 5, 1, null, "1.4", null, null, null);
    public static final BooleanConfig CHASSIS_TRACKING = new BooleanConfig("INV", "CHASSIS", "CHASSIS_TRACKING", "001", false, 5, 1, null, "1.4", null, null, null);
    public static final StringConfig CHASSIS_DISMOUNT_SLOT = new StringConfig("INV", "CHASSIS", "CHASSIS_DISMOUNT_SLOT", "002", "CITC", 7, false, 5, 3, null, "2.1", null, null, null);
    public static final CodedConfig DIRECT_DELIVERY_RAIL = new CodedConfig("INV", "DIRECT_DELIVERY", "DIRECT_DELIVERY_RAIL", "001", false, "NEVER", new String[]{"ALWAYS", "NEVER"}, 5, 1, null, "1.5", null, null, null);
    public static final BooleanConfig UNIT_UPDATE_DLRY_REQUIREMENTS = new BooleanConfig("INV", "UPDATE_DELIVERY", "UNIT_UPDATE_DLRY_REQUIREMENTS", "004", true, 5, 1, null, "1.7", null, null, null);
    public static final LongConfig MAX_DECLARATIONS = new LongConfig("INV", "GOODS", "MAX_DECLARATIONS", "001", 99L, 0L, 99L, 5, 1, null, "1.7.A", null, null, null);
    public static final LongConfig MAX_DOCUMENTS = new LongConfig("INV", "GOODS", "MAX_DOCUMENTS", "002", 99L, 0L, 99L, 5, 1, null, "1.7.A", null, null, null);
    public static final LongConfig NUMBER_OF_DOCUMENTS = new LongConfig("INV", "GOODS", "NUMBER_OF_DOCUMENTS", "003", 999L, 0L, 999L, 5, 1, null, "1.8.D", null, null, null);
    public static final StringConfig CSI_COUNTRIES = new StringConfig("INV", "ROUTING", "CSI_COUNTRIES", "001", "", 256, false, 5, 1, null, "1.8", null, null, null);
    public static final BooleanConfig UNIT_CATEGORY_SYSTEM_DERIVED = new BooleanConfig("INV", "ROUTING", "UNIT_CATEGORY_SYSTEM_DERIVED", "002", false, 5, 1, null, "2.0.C", null, null, null);
    public static final BooleanConfig SUPPORTS_DOMESTIC_CATEGORY = new BooleanConfig("INV", "ROUTING", "SUPPORTS_DOMESTIC_CATEGORY", "003", false, 5, 1, null, "2.0.D", null, null, null);
    public static final BooleanConfig SUPPRESS_NOTICES_IN_DATA_IMPORT_MODE = new BooleanConfig("INV", "SNX", "SUPPRESS_NOTICES_IN_DATA_IMPORT_MODE", "001", true, 5, 1, null, "1.8", null, null, null);
    public static final BooleanConfig SUPPRESS_INTERNAL_UNIT_EVENTS = new BooleanConfig("INV", "SNX", "SUPPRESS_INTERNAL_UNIT_EVENTS", "002", false, 5, 1, null, "1.8", null, null, null);
    public static final BooleanConfig GATE_OPERATIONAL_POSITION_BY_TRUCK = new BooleanConfig("INV", "XPS", "GATE_OPERATIONAL_POSITION_BY_TRUCK", "001", false, 5, 1, null, "1.8", null, null, null);
    public static final BooleanConfig GATE_OPERATIONAL_POSITION_IN_LOC_ID = new BooleanConfig("INV", "XPS", "GATE_OPERATIONAL_POSITION_IN_LOC_ID", "002", false, 5, 1, null, "1.8", null, null, null);
    public static final StringConfig BACKGROUND_WQ_NAME = new StringConfig("INV", "XPS", "BACKGROUND_WQ_NAME", "003", "BACKGROUND", 31, false, 5, 5, null, "3.01", null, null, null);
    public static final StringConfig TRANSFER_WQ_NAME = new StringConfig("INV", "XPS", "TRANSFER_WQ_NAME", "004", "TRANSFER", 31, false, 5, 5, null, "3.01", null, null, null);
    public static final StringConfig RAIL_TRANSFER_WQ_NAME = new StringConfig("INV", "XPS", "RAIL_TRANSFER_WQ_NAME", "005", "RAIL_TRANSFER", 31, false, 5, 5, null, "3.01", null, null, null);
    public static final BooleanConfig AUTOSTOW_ALLOW_REROUTING_EMPTIES = new BooleanConfig("INV", "XPS", "AUTOSTOW_ALLOW_REROUTING_EMPTIES", "006", false, 5, 1, null, "3.02", null, null, null);
    public static final LongConfig DEFAULT_EMT_TIME_HORIZON_IN_MINUTES_FOR_YARD_MOVES_CREATED_BY_XPS_USER = new LongConfig("INV", "XPS", "DEFAULT_EMT_TIME_HORIZON_IN_MINUTES_FOR_YARD_MOVES_CREATED_BY_XPS_USER", "007", 10L, 0L, 480L, 5, 5, null, "3.02", null, null, null);
    public static final CodedConfig BARGE_POPULATE_GROSS_WEIGHT_FROM_BKG_ITEM = new CodedConfig("INV", "BRGE", "BARGE_POPULATE_GROSS_WEIGHT_FROM_BKG_ITEM", "001", false, "DO_NOT_POPULATE_GROSS_WEIGHT", new String[]{"DO_NOT_POPULATE_GROSS_WEIGHT", "POPULATE_GROSS_WEIGHT", "POPULATE_EQTYPE_TARE_WEIGHT_IF_OVERWEIGHT"}, 5, 1, null, "1.8", null, null, null);
    public static final BooleanConfig BARGE_POPULATE_ISO_FROM_BKG_ITEM = new BooleanConfig("INV", "BRGE", "BARGE_POPULATE_ISO_FROM_BKG_ITEM", "002", false, 5, 1, null, "1.8", null, null, null);
    public static final LongConfig POOL_EQUIPMENT_COUNT_LIMIT = new LongConfig("INV", "POOL", "POOL_EQUIPMENT_COUNT_LIMIT", "001", 100L, 0L, 10000L, 5, 3, null, "2.1.M", null, null, null);
    public static final BooleanConfig SECURITY_VESSEL_OPERATOR_VIEW_UNITS = new BooleanConfig("INV", "SECURITY", "SECURITY_VESSEL_OPERATOR_VIEW_UNITS", "001", true, 4, 3, null, "2.1", null, null, null);
    public static final BooleanConfig SECURITY_ENFORCE_EQ_STATE_MODIFY = new BooleanConfig("INV", "SECURITY", "SECURITY_ENFORCE_EQ_STATE_MODIFY", "002", true, 4, 3, null, "2.6", null, null, null);
    public static final LongConfig UNIT_DELETE_BATCH_SIZE = new LongConfig("INV", "BATCH", "UNIT_DELETE_BATCH_SIZE", "001", 500L, 1L, 1000L, 4, 2, null, "2.1", null, null, null);
    public static final BooleanConfig HC_QUERY_XPS_FOR_LOAD_SLOT = new BooleanConfig("INV", "MOBILE_RDT", "HC_QUERY_XPS_FOR_LOAD_SLOT", "001", false, 5, 1, null, "2.2", null, null, null);
    public static final CodedConfig INVENTORY_PAGE_FLOW = new CodedConfig("INV", "MOBILE_RDT", "INVENTORY_PAGE_FLOW", "002", false, "STAY_ON_SAME_PAGE", new String[]{"STAY_ON_SAME_PAGE", "RETURN_TO_PREVIOUS_PAGE"}, 5, 1, null, "2.4", null, null, null);
    public static final BooleanConfig MOBILE_HC_VALIDATE_NEXT_CONTAINER = new BooleanConfig("INV", "MOBILE_RDT", "MOBILE_HC_VALIDATE_NEXT_CONTAINER", "003", true, 5, 1, null, "2.4", null, null, null);
    public static final BooleanConfig HC_LOAD_SEARCH_UNITS_PLANNED_FOR_VESSEL_ONLY = new BooleanConfig("INV", "MOBILE_RDT", "HC_LOAD_SEARCH_UNITS_PLANNED_FOR_VESSEL_ONLY", "004", false, 5, 1, null, "2.6", null, null, null);
    public static final BooleanConfig MOBILE_CARGO_INVENTORY_POPULATE_QTY = new BooleanConfig("INV", "MOBILE_RDT", "MOBILE_CARGO_INVENTORY_POPULATE_QTY", "005", true, 5, 1, null, "2.6", null, null, null);
    public static final CodedConfig INBOUND_TRAIN_POPULATE_GROSS_WEIGHT_FROM_BKG_ITEM = new CodedConfig("INV", "RAIL", "INBOUND_TRAIN_POPULATE_GROSS_WEIGHT_FROM_BKG_ITEM", "001", false, "DO_NOT_POPULATE_GROSS_WEIGHT", new String[]{"DO_NOT_POPULATE_GROSS_WEIGHT", "POPULATE_GROSS_WEIGHT", "POPULATE_EQTYPE_TARE_WEIGHT_IF_OVERWEIGHT"}, 5, 1, null, "1.8", null, null, null);
    public static final BooleanConfig INBOUND_TRAIN_POPULATE_ISO_FROM_BKG_ITEM = new BooleanConfig("INV", "RAIL", "INBOUND_TRAIN_POPULATE_ISO_FROM_BKG_ITEM", "002", false, 5, 1, null, "2.1", null, null, null);
    public static final BooleanConfig DWELL_DAYS_CALC = new BooleanConfig("INV", "DWELLTM", "DWELL_DAYS_CALC", "001", false, 5, 1, null, "2.4", null, null, null);
    public static final LongConfig DISPLAY_COMPLETED_JOBS_AT_WORK_LIST_INTERVAL = new LongConfig("INV", "JOB_LIST", "DISPLAY_COMPLETED_JOBS_AT_WORK_LIST_INTERVAL", "001", 0L, 0L, 240L, 5, 1, null, "3.02", null, null, null);
    public static final BooleanConfig HC_JOBLIST_AUTO_POPULATE_CONTAINER_IN_MULTI_MODE = new BooleanConfig("INV", "JOB_LIST", "HC_JOBLIST_AUTO_POPULATE_CONTAINER_IN_MULTI_MODE", "002", true, 5, 1, null, "2.5", null, null, null);

    private InventoryConfig() {
    }

}
