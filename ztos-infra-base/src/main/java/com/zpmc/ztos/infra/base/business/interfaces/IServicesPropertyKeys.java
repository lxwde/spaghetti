package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.common.model.PropertyKeyFactory;

public interface IServicesPropertyKeys {
    public static final IPropertyKey AUTO_UPDATE_FIELD = PropertyKeyFactory.valueOf((String)"AUTO_UPDATE_FIELD");
    public static final IPropertyKey AUTO_UPDATE_FLAGS = PropertyKeyFactory.valueOf((String)"AUTO_UPDATE_FLAGS");
    public static final IPropertyKey AUTO_FLAG_ACTION = PropertyKeyFactory.valueOf((String)"AUTO_FLAG_ACTION");
    public static final IPropertyKey INSPECT_AHI = PropertyKeyFactory.valueOf((String)"INSPECT_AHI");
    public static final IPropertyKey INSPECTOR_AHE = PropertyKeyFactory.valueOf((String)"INSPECTOR_AHE");
    public static final IPropertyKey EDIT_AHI = PropertyKeyFactory.valueOf((String)"EDIT_AHI");
    public static final IPropertyKey DIALOG__HEALTH_INSTRUMENT_UPDATE = PropertyKeyFactory.valueOf((String)"DIALOG__HEALTH_INSTRUMENT_UPDATE");
    public static final IPropertyKey DIALOG__HEALTH_INSTRUMENT_PREFACE = PropertyKeyFactory.valueOf((String)"DIALOG__HEALTH_INSTRUMENT_PREFACE");
    public static final IPropertyKey FILTER = PropertyKeyFactory.valueOf((String)"FILTER");
    public static final IPropertyKey EVENT_EFFECTS = PropertyKeyFactory.valueOf((String)"EVENT_EFFECTS");
    public static final IPropertyKey EVENT_TYPE_NOT_FOR_THIS_ENTITY_CLASS = PropertyKeyFactory.valueOf((String)"EVENT_TYPE_NOT_FOR_THIS_ENTITY_CLASS");
    public static final IPropertyKey TARGET_ENTITY_IS_NOT_DEFINED = PropertyKeyFactory.valueOf((String)"TARGET_ENTITY_IS_NOT_DEFINED");
    public static final IPropertyKey EVENT_TYPE_FILTER_NOT_FOR_THIS_SERVICEABLE = PropertyKeyFactory.valueOf((String)"EVENT_TYPE_FILTER_NOT_FOR_THIS_SERVICEABLE");
    public static final IPropertyKey HOLD_PREVENTS_EVENT_RECORDING = PropertyKeyFactory.valueOf((String)"HOLD_PREVENTS_EVENT_RECORDING");
    public static final IPropertyKey GUARDED_HOLD_PREVENTS_EVENT_RECORDING = PropertyKeyFactory.valueOf((String)"GUARDED_HOLD_PREVENTS_EVENT_RECORDING");
    public static final IPropertyKey NO_PERMISSION_FOR_EVENT_RECORDING = PropertyKeyFactory.valueOf((String)"NO_PERMISSION_FOR_EVENT_RECORDING");
    public static final IPropertyKey NO_GUARDED_PERMISSION_FOR_EVENT_RECORDING = PropertyKeyFactory.valueOf((String)"NO_GUARDED_PERMISSION_FOR_EVENT_RECORDING");
    public static final IPropertyKey REQ_EVENT_TYPE_NOT_RECORDED = PropertyKeyFactory.valueOf((String)"REQ_EVENT_TYPE_NOT_RECORDED");
    public static final IPropertyKey RECORD_EVENT_MISSMATCHING_LOGICAL_ENTITIES_FOR_EVENT_AND_TARGET = PropertyKeyFactory.valueOf((String)"RECORD_EVENT_MISSMATCHING_LOGICAL_ENTITIES_FOR_EVENT_AND_TARGET");
    public static final IPropertyKey NO_PATH_TO_GUARDIAN = PropertyKeyFactory.valueOf((String)"NO_PATH_TO_GUARDIAN");
    public static final IPropertyKey NO_GUARDIAN = PropertyKeyFactory.valueOf((String)"NO_GUARDIAN");
    public static final IPropertyKey UNKNOWN_PERMISSION_TYPE = PropertyKeyFactory.valueOf((String)"UNKNOWN_PERMISSION_TYPE");
    public static final IPropertyKey UNKNOWN_HOLD_TYPE = PropertyKeyFactory.valueOf((String)"UNKNOWN_HOLD_TYPE");
    public static final IPropertyKey VETO_OF_NON_EXISTING_FLAG_NOT_POSSIBLE = PropertyKeyFactory.valueOf((String)"VETO_OF_NON_EXISTING_FLAG_NOT_POSSIBLE");
    public static final IPropertyKey CAN_NOT_DELETE_BUILT_IN_EVENT_TYPE = PropertyKeyFactory.valueOf((String)"CAN_NOT_DELETE_BUILT_IN_EVENT_TYPE");
    public static final IPropertyKey CAN_NOT_CHANGE_BUILT_IN_EVENT = PropertyKeyFactory.valueOf((String)"CAN_NOT_CHANGE_BUILT_IN_EVENT");
    public static final IPropertyKey INCOMPATIBLE_RULETYPE_AND_FLAGTYPE = PropertyKeyFactory.valueOf((String)"INCOMPATIBLE_RULETYPE_AND_FLAGTYPE");
    public static final IPropertyKey UNSUPPORTED_EVENT_FOR_AUTO_FLAG = PropertyKeyFactory.valueOf((String)"UNSUPPORTED_EVENT_FOR_AUTO_FLAG");
    public static final IPropertyKey INCOMPATIBLE_EVENTTYPE_AND_FLAGTYPE = PropertyKeyFactory.valueOf((String)"INCOMPATIBLE_EVENTTYPE_AND_FLAGTYPE");
    public static final IPropertyKey INCOMPATIBLE_PATH_TO_GUARDIAN_AND_FLAGTYPE = PropertyKeyFactory.valueOf((String)"INCOMPATIBLE_PATH_TO_GUARDIAN_AND_FLAGTYPE");
    public static final IPropertyKey RULETYPE_REQUIRES_FIELD = PropertyKeyFactory.valueOf((String)"RULETYPE_REQUIRES_FIELD");
    public static final IPropertyKey EVENT_TYPE_WITH_SAME_ID_ALREADY_EXISTS = PropertyKeyFactory.valueOf((String)"EVENT_TYPE_WITH_SAME_ID_ALREADY_EXISTS");
    public static final IPropertyKey EVENT_EFFECT_REQUIRES_FIELD_VALUE = PropertyKeyFactory.valueOf((String)"EVENT_EFFECT_REQUIRES_FIELD_VALUE");
    public static final IPropertyKey EVENT_EFFECT_WITH_AUTOUPDATE_REQUIRED_FILED_HAS_NO_DEFAULT_VALUE = PropertyKeyFactory.valueOf((String)"EVENT_EFFECT_WITH_AUTOUPDATE_REQUIRED_FILED_HAS_NO_DEFAULT_VALUE");
    public static final IPropertyKey EVENT_EFFECT_NOT_VALID = PropertyKeyFactory.valueOf((String)"EVENT_EFFECT_NOT_VALID");
    public static final IPropertyKey EVENT_EFFECT_NOT_VALID_REQUIRED_NO_UPDATE_OR_AUTOUPDATE = PropertyKeyFactory.valueOf((String)"EVENT_EFFECT_NOT_VALID_REQUIRED_NO_UPDATE_OR_AUTOUPDATE");
    public static final IPropertyKey EVENT_EFFECT_NOT_VALID_REQUIRED_AND_AUTOUPDATE_SET_MISSING_AUTOVALUE = PropertyKeyFactory.valueOf((String)"EVENT_EFFECT_NOT_VALID_REQUIRED_AND_AUTOUPDATE_SET_MISSING_AUTOVALUE");
    public static final IPropertyKey EVENT_EFFECT_NOT_ALLOWED_FOR_BUILT_IN_EVENT_TYPES = PropertyKeyFactory.valueOf((String)"EVENT_EFFECT_NOT_ALLOWED_FOR_BUILT_IN_EVENT_TYPES");
    public static final IPropertyKey EVENT_TYPE_HAS_REFERENCING_SERVICE_RULE = PropertyKeyFactory.valueOf((String)"EVENT_TYPE_HAS_REFERENCING_SERVICE_RULE");
    public static final IPropertyKey EVENT_TYPE_HAS_REFERENCING_EVENT = PropertyKeyFactory.valueOf((String)"EVENT_TYPE_HAS_REFERENCING_EVENT");
    public static final IPropertyKey EVENT_EFFECT_FOR_THE_SELECTED_FIELD_ALREADY_EXISTS = PropertyKeyFactory.valueOf((String)"EVENT_EFFECT_FOR_THE_SELECTED_FIELD_ALREADY_EXISTS");
    public static final IPropertyKey SERVICE_RULE_FOR_FLAG_WITH_DIFFERENT_RULE_TYPE = PropertyKeyFactory.valueOf((String)"SERVICE_RULE_FOR_FLAG_WITH_DIFFERENT_RULE_TYPE");
    public static final IPropertyKey SERVICE_RULE_EVENT_TYPE_IS_OBSOLETE = PropertyKeyFactory.valueOf((String)"SERVICE_RULE_EVENT_TYPE_IS_OBSOLETE");
    public static final IPropertyKey SERVICE_RULE_FLAG_TYPE_IS_OBSOLETE = PropertyKeyFactory.valueOf((String)"SERVICE_RULE_FLAG_TYPE_IS_OBSOLETE");
    public static final IPropertyKey ERROR_SERVICE_RULE_PREREQUISITE_AND_EVENT_TYPE_FOR_DIFFERENT_ENTITIES = PropertyKeyFactory.valueOf((String)"ERROR_SERVICE_RULE_PREREQUISITE_AND_EVENT_TYPE_FOR_DIFFERENT_ENTITIES");
    public static final IPropertyKey AUTO_UPDATE_RULE_WITH_SAME_NAME_ALREADY_EXISTS = PropertyKeyFactory.valueOf((String)"AUTO_UPDATE_RULE_WITH_SAME_NAME_ALREADY_EXISTS");
    public static final IPropertyKey AUTO_UPDATE_FIELD_WITH_SAME_NAME_ALREADY_EXISTS = PropertyKeyFactory.valueOf((String)"AUTO_UPDATE_FIELD_WITH_SAME_NAME_ALREADY_EXISTS");
    public static final IPropertyKey AUTO_UPDATE_FLAG_ALREADY_EXISTS = PropertyKeyFactory.valueOf((String)"AUTO_UPDATE_FLAG_ALREADY_EXISTS");
    public static final IPropertyKey FLAG_TYPE_HAS_REFERENCING_SERVICE_RULE = PropertyKeyFactory.valueOf((String)"FLAG_TYPE_HAS_REFERENCING_SERVICE_RULE");
    public static final IPropertyKey FLAG_TYPE_HAS_REFERENCING_SERVICE_RULE_WITH_DIFF_APPLIES_TO_ENTITY = PropertyKeyFactory.valueOf((String)"FLAG_TYPE_HAS_REFERENCING_SERVICE_RULE_WITH_DIFF_APPLIES_TO_ENTITY");
    public static final IPropertyKey FLAG_TYPE_REQUIRED_FIELD_APPLIES_TO_MISSING = PropertyKeyFactory.valueOf((String)"FLAG_TYPE_REQUIRED_FIELD_APPLIES_TO_MISSING");
    public static final IPropertyKey FLAG_TYPE_COVERT_ONLY_APPLIES_TO_UNIT = PropertyKeyFactory.valueOf((String)"FLAG_TYPE_COVERT_ONLY_APPLIES_TO_UNIT");
    public static final IPropertyKey EVENT_TYPE_HAS_REFERENCING_SERVICE_RULE_WITH_DIFF_FLAG_TYPE_APPLIES_TO_ENTITY = PropertyKeyFactory.valueOf((String)"EVENT_TYPE_HAS_REFERENCING_SERVICE_RULE_WITH_DIFF_FLAG_TYPE_APPLIES_TO_ENTITY");
    public static final IPropertyKey FLAG_TYPE_HAS_REFERENCING_FLAGS = PropertyKeyFactory.valueOf((String)"FLAG_TYPE_HAS_REFERENCING_FLAGS");
    public static final IPropertyKey REFERENCE_ID_REQUIRED_FOR_FLAG = PropertyKeyFactory.valueOf((String)"REFERENCE_ID_REQUIRED_FOR_FLAG");
    public static final IPropertyKey REFERENCE_ID_REQUIRED_FOR_VETO = PropertyKeyFactory.valueOf((String)"REFERENCE_ID_REQUIRED_FOR_VETO");
    public static final IPropertyKey NOTICE_SUBJECT = PropertyKeyFactory.valueOf((String)"NOTICE_SUBJECT");
    public static final IPropertyKey NOTICE_TEXT = PropertyKeyFactory.valueOf((String)"NOTICE_TEXT");
    public static final IPropertyKey NOTICE_NO_EMAIL_ADDRESS_OR_PARTY = PropertyKeyFactory.valueOf((String)"NOTICE_NO_EMAIL_ADDRESS_OR_PARTY");
    public static final IPropertyKey NOTICE_NO_NATURAL_ID_OR_FILTER = PropertyKeyFactory.valueOf((String)"NOTICE_NO_NATURAL_ID_OR_FILTER");
    public static final IPropertyKey NOTICE_DUPLICATE = PropertyKeyFactory.valueOf((String)"NOTICE_DUPLICATE");
    public static final IPropertyKey NOTICE_CANNOT_CHANGE_ID = PropertyKeyFactory.valueOf((String)"NOTICE_CANNOT_CHANGE_ID");
    public static final IPropertyKey NOTICE__NO_PRIV_FOR_GROOVY = PropertyKeyFactory.valueOf((String)"NOTICE__NO_PRIV_FOR_GROOVY");
    public static final IPropertyKey ADD_FLAG_SECURITY_PRIVILEGE_MISSING = PropertyKeyFactory.valueOf((String)"ADD_FLAG_SECURITY_PRIVILEGE_MISSING");
    public static final IPropertyKey VETO_FLAG_SECURITY_PRIVILEGE_MISSING = PropertyKeyFactory.valueOf((String)"VETO_FLAG_SECURITY_PRIVILEGE_MISSING");
    public static final IPropertyKey COPY_FLAGS_ENTITY_MISSMATCH = PropertyKeyFactory.valueOf((String)"COPY_FLAGS_ENTITY_MISSMATCH");
    public static final IPropertyKey CTR_NOTICE_REQUEST_EVENT_DEFINITION = PropertyKeyFactory.valueOf((String)"CTR_NOTICE_REQUEST_EVENT_DEFINITION");
    public static final IPropertyKey NOTICE_REQUEST_EVENT_DEFINITION = PropertyKeyFactory.valueOf((String)"NOTICE_REQUEST_EVENT_DEFINITION");
    public static final IPropertyKey NOTICE_REQUEST_ACTION_TO_PERFORM = PropertyKeyFactory.valueOf((String)"NOTICE_REQUEST_ACTION_TO_PERFORM");
    public static final IPropertyKey ERROR_CREATE_NOTICE_REQUEST = PropertyKeyFactory.valueOf((String)"ERROR_CREATE_NOTICE_REQUEST");
    public static final IPropertyKey ERROR_NOTICE_REQUEST_WITHOUT_CONTAINER = PropertyKeyFactory.valueOf((String)"ERROR_NOTICE_REQUEST_WITHOUT_CONTAINER");
    public static final IPropertyKey ERROR_NOTICE_REQUEST_WITHOUT_ACTION = PropertyKeyFactory.valueOf((String)"ERROR_NOTICE_REQUEST_WITHOUT_ACTION");
    public static final IPropertyKey ERROR_NOTICE_REQUEST_WITHOUT_ACTION_ID = PropertyKeyFactory.valueOf((String)"ERROR_NOTICE_REQUEST_WITHOUT_ACTION_ID");
    public static final IPropertyKey ERROR_NOTICE_REQUEST_ACTION_NOT_FOUND = PropertyKeyFactory.valueOf((String)"ERROR_NOTICE_REQUEST_ACTION_NOT_FOUND");
    public static final IPropertyKey ERROR_NOTICE_REQUEST_WITHOUT_EVENT = PropertyKeyFactory.valueOf((String)"ERROR_NOTICE_REQUEST_WITHOUT_EVENT");
    public static final IPropertyKey ERROR_NOTICE_REQUEST_WITHOUT_EVENT_ID = PropertyKeyFactory.valueOf((String)"ERROR_NOTICE_REQUEST_WITHOUT_EVENT_ID");
    public static final IPropertyKey ERROR_EVENT_TYPE_NOT_FOUND_WITH_SPECIFIED_ID = PropertyKeyFactory.valueOf((String)"ERROR_EVENT_TYPE_NOT_FOUND_WITH_SPECIFIED_ID");
    public static final IPropertyKey ERROR_NOTICE_UNSUPPORTED_NOTIFICATION_ACTION = PropertyKeyFactory.valueOf((String)"ERROR_NOTICE_UNSUPPORTED_NOTIFICATION_ACTION");
    public static final IPropertyKey ERROR_NOTICE_REQUEST_WITH_UNSUPPORTED_PATH_TO_BIZ_UNIT = PropertyKeyFactory.valueOf((String)"ERROR_NOTICE_REQUEST_WITH_UNSUPPORTED_PATH_TO_BIZ_UNIT");
    public static final IPropertyKey ERROR_NOTICE_REQUEST_WITHOUT_EMAIL_ADDRESS = PropertyKeyFactory.valueOf((String)"ERROR_NOTICE_REQUEST_WITHOUT_EMAIL_ADDRESS");
    public static final IPropertyKey ERROR_NOTICE_REQUEST_WITHOUT_PARTY = PropertyKeyFactory.valueOf((String)"ERROR_NOTICE_REQUEST_WITHOUT_PARTY");
    public static final IPropertyKey WARN_EXTRACT_SELECT_NARROWED = PropertyKeyFactory.valueOf((String)"WARN_EXTRACT_SELECT_NARROWED");
    public static final IPropertyKey WARN_EXTRACT_SELECT_MAX_READS = PropertyKeyFactory.valueOf((String)"WARN_EXTRACT_SELECT_MAX_READS");
    public static final IPropertyKey NOTICE_NO_DATA_TO_DISPLAY = PropertyKeyFactory.valueOf((String)"NOTICE_NO_DATA_TO_DISPLAY");
    public static final IPropertyKey ERROR_NOTICE_NO_NATURAL_ID_EVENT_ID_ACTION_ID = PropertyKeyFactory.valueOf((String)"ERROR_NOTICE_NO_NATURAL_ID_EVENT_ID_ACTION_ID");
    public static final IPropertyKey ERROR_DELETING_HOLD_PERM_VIEW = PropertyKeyFactory.valueOf((String)"ERROR_DELETING_HOLD_PERM_VIEW");
    public static final IPropertyKey CUE_STATUS_NOT_ALLOWED = PropertyKeyFactory.valueOf((String)"CUE_STATUS_NOT_ALLOWED");
    public static final IPropertyKey CUE_NOT_QUEUED_STATUS_NOT_ALLOWED = PropertyKeyFactory.valueOf((String)"CUE_NOT_QUEUED_STATUS_NOT_ALLOWED");
    public static final IPropertyKey PREPAYABLE_EVENT_TYPE_MUST_BE_BILLABLE = PropertyKeyFactory.valueOf((String)"PREPAYABLE_EVENT_TYPE_MUST_BE_BILLABLE");
    public static final IPropertyKey WARN_HOLD_ALREADY_EXISTS = PropertyKeyFactory.valueOf((String)"WARN_HOLD_ALREADY_EXISTS");
    public static final IPropertyKey ADDRESS_IS_NULL = PropertyKeyFactory.valueOf((String)"ADDRESS_IS_NULL");
    public static final IPropertyKey ADDRESS_IS_OF_ZERO_LENGTH = PropertyKeyFactory.valueOf((String)"ADDRESS_IS_OF_ZERO_LENGTH");
    public static final IPropertyKey SMS_ADDRESS_IS_NOT_NUMERIC = PropertyKeyFactory.valueOf((String)"SMS_ADDRESS_IS_NOT_NUMERIC");
    public static final IPropertyKey SMS_ADDRESS_IN_FORMAT = PropertyKeyFactory.valueOf((String)"SMS_ADDRESS_IN_FORMAT");
    public static final IPropertyKey SMS_ADDRESS_LENGTH_FAIL = PropertyKeyFactory.valueOf((String)"SMS_ADDRESS_LENGTH_FAIL");
    public static final IPropertyKey SMS_ADDRESS_MAX_LENGTH_EXCEEDS = PropertyKeyFactory.valueOf((String)"SMS_ADDRESS_MAX_LENGTH_EXCEEDS");
    public static final IPropertyKey SMS_ADDRESS_MIN_LENGTH_FAIL = PropertyKeyFactory.valueOf((String)"SMS_ADDRESS_MIN_LENGTH_FAIL");
    public static final IPropertyKey SMS_COUNTRY_CODE_INVALID = PropertyKeyFactory.valueOf((String)"SMS_COUNTRY_CODE_INVALID");
    public static final IPropertyKey SMS_COUNTRY_CODE_REQUIRED = PropertyKeyFactory.valueOf((String)"SMS_COUNTRY_CODE_REQUIRED");
    public static final IPropertyKey EMAIL_ADDRESS_INVALID_FORMAT = PropertyKeyFactory.valueOf((String)"EMAIL_ADDRESS_INVALID_FORMAT");
    public static final IPropertyKey CME_STATUS_NOT_ALLOWED = PropertyKeyFactory.valueOf((String)"CME_STATUS_NOT_ALLOWED");
    public static final IPropertyKey CME_NOT_QUEUED_STATUS_NOT_ALLOWED = PropertyKeyFactory.valueOf((String)"CME_NOT_QUEUED_STATUS_NOT_ALLOWED");
    public static final IPropertyKey UPDATE_INSTRUMENT_EVENT_RATE = PropertyKeyFactory.valueOf((String)"UPDATE_INSTRUMENT_EVENT_RATE");
    public static final IPropertyKey COULD_NOT_RETRIEVE_INSTRUMENT_EVENTS = PropertyKeyFactory.valueOf((String)"COULD_NOT_RETRIEVE_INSTRUMENT_EVENTS");
    public static final IPropertyKey COULD_NOT_CONSTRUCT_MBEANS = PropertyKeyFactory.valueOf((String)"COULD_NOT_CONSTRUCT_MBEANS");
    public static final IPropertyKey MBEANS = PropertyKeyFactory.valueOf((String)"MBEANS");
    public static final IPropertyKey MBEAN_DOMAINS = PropertyKeyFactory.valueOf((String)"MBEAN_DOMAINS");
    public static final IPropertyKey AHI_INSTRUMENT_UPDATE = PropertyKeyFactory.valueOf((String)"AHI_INSTRUMENT_UPDATE");
    public static final IPropertyKey AHI_INSTRUMENT_UPDATE_MSG = PropertyKeyFactory.valueOf((String)"AHI_INSTRUMENT_UPDATE_MSG");

}
