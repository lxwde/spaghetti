package com.zpmc.ztos.infra.base.common.consts;

import com.zpmc.ztos.infra.base.common.configs.*;

public class FrameworkConfigConsts {

    public static final String DATE_TIME_REGION_FORMAT_ISO = "ISO";
    public static final String DATE_TIME_REGION_FORMAT_US = "US";
    public static final String DATE_TIME_REGION_FORMAT_EUROPEAN = "EUROPEAN";
    public static final String TABLE_EXPORT_CSV_COL_SEPARATOR_US = "US";
    public static final String TABLE_EXPORT_CSV_COL_SEPARATOR_EUROPEAN = "EUROPEAN";
    public static final String DECIMAL_FIELD_ROUNDING_MODE_CEILING = "CEILING";
    public static final String DECIMAL_FIELD_ROUNDING_MODE_DOWN = "DOWN";
    public static final String DECIMAL_FIELD_ROUNDING_MODE_FLOOR = "FLOOR";
    public static final String DECIMAL_FIELD_ROUNDING_MODE_HALF_DOWN = "HALF_DOWN";
    public static final String DECIMAL_FIELD_ROUNDING_MODE_HALF_EVEN = "HALF_EVEN";
    public static final String DECIMAL_FIELD_ROUNDING_MODE_HALF_UP = "HALF_UP";
    public static final String DECIMAL_FIELD_ROUNDING_MODE_UP = "UP";
    public static final String SYSTEM_AUTHENTICATION_METHOD_INTERNAL = "INTERNAL";
    public static final String SYSTEM_AUTHENTICATION_METHOD_DEPENDS_ON_USER_SETTING = "DEPENDS_ON_USER_SETTING";
    public static final String USER_AUTHENTICATION_METHOD_DEFAULT_INTERNAL = "INTERNAL";
    public static final String USER_AUTHENTICATION_METHOD_DEFAULT_EXTERNAL = "EXTERNAL";
    public static final String MOBILE_LOV_DISPLAY_STYLE_BOTH_CODE_AND_DESCRIPTION = "BOTH_CODE_AND_DESCRIPTION";
    public static final String MOBILE_LOV_DISPLAY_STYLE_CODE_ONLY = "CODE_ONLY";
    public static final String MOBILE_LOV_DISPLAY_STYLE_DESCRIPTION_ONLY = "DESCRIPTION_ONLY";
    public static final BooleanConfig HIBERNATE_SHOW_SQL = new BooleanConfig("FRM", "CARINA", "HIBERNATE_SHOW_SQL", "001", true, 5, 1, null, "1.0", "2.1", null, null);
    public static final LongConfig P1_START = new LongConfig("FRM", "CARINA", "P1_START", "002", 0L, 0L, 2400L, 5, 1, null, "1.0", null, null, null);
    public static final LongConfig P1_END = new LongConfig("FRM", "CARINA", "P1_END", "003", 600L, 0L, 2400L, 5, 1, null, "1.0", null, null, null);
    public static final LongConfig P2_START = new LongConfig("FRM", "CARINA", "P2_START", "004", 600L, 0L, 2400L, 5, 1, null, "1.0", null, null, null);
    public static final LongConfig P2_END = new LongConfig("FRM", "CARINA", "P2_END", "005", 1200L, 0L, 2400L, 5, 1, null, "1.0", null, null, null);
    public static final LongConfig P3_START = new LongConfig("FRM", "CARINA", "P3_START", "006", 1200L, 0L, 2400L, 5, 1, null, "1.0", null, null, null);
    public static final LongConfig P3_END = new LongConfig("FRM", "CARINA", "P3_END", "007", 1800L, 0L, 2400L, 5, 1, null, "1.0", null, null, null);
    public static final LongConfig P4_START = new LongConfig("FRM", "CARINA", "P4_START", "008", 1800L, 0L, 2400L, 5, 1, null, "1.0", null, null, null);
    public static final LongConfig P4_END = new LongConfig("FRM", "CARINA", "P4_END", "009", 2400L, 0L, 2400L, 5, 1, null, "1.0", null, null, null);
    public static final BooleanConfig AUTO_UPGRADE = new BooleanConfig("FRM", "CARINA", "AUTO_UPGRADE", "012", true, 1, 1, null, "2.1", null, null, null);
    public static final LongConfig TABLE_EXPORT_MAX_SIZE = new LongConfig("FRM", "CARINA", "TABLE_EXPORT_MAX_SIZE", "013", 1000L, 0L, 100000L, 5, 1, null, "2.1", null, null, null);
    public static final CodedConfig DATE_TIME_REGION_FORMAT = new CodedConfig("FRM", "CARINA", "DATE_TIME_REGION_FORMAT", "014", false, "ISO", new String[]{"ISO", "US", "EUROPEAN"}, 3, 1, null, "3.2", null, null, null);
    public static final StringConfig APPLICATION_LOG_HOST = new StringConfig("FRM", "CARINA", "APPLICATION_LOG_HOST", "015", "", 9999, false, 5, 1, null, "3.2", null, null, null);
    public static final LongConfig APPLICATION_LOG_PORT = new LongConfig("FRM", "CARINA", "APPLICATION_LOG_PORT", "016", 4446L, 0L, 65535L, 5, 1, null, "3.2", null, null, null);
    public static final BooleanConfig UNIVERSAL_QUERY_REST_ENABLE = new BooleanConfig("FRM", "CARINA", "UNIVERSAL_QUERY_REST_ENABLE", "017", false, 4, 1, null, "3.1", null, null, null);
    public static final StringConfig DATE_TIME_DISPLAY_FORMAT = new StringConfig("FRM", "CARINA", "DATE_TIME_DISPLAY_FORMAT", "018", "yy-MMM-dd HHmm", 9999, false, 5, 1, null, "3.1", null, null, null);
    public static final LongConfig MAX_TABLE_ROWS = new LongConfig("FRM", "CARINA", "MAX_TABLE_ROWS", "019", 10000L, 0L, 10000000L, 5, 1, null, "3.2", null, null, null);
    public static final LongConfig MAX_OPEN_TABS = new LongConfig("FRM", "CARINA", "MAX_OPEN_TABS", "020", 10L, 1L, 15L, 5, 1, null, "3.4", null, null, null);
    public static final BooleanConfig TUNE_QUICK_SEARCH = new BooleanConfig("FRM", "CARINA", "TUNE_QUICK_SEARCH", "021", true, 5, 1, null, "3.5", null, null, null);
    public static final CodedConfig TABLE_EXPORT_CSV_COL_SEPARATOR = new CodedConfig("FRM", "CARINA", "TABLE_EXPORT_CSV_COL_SEPARATOR", "022", false, "US", new String[]{"US", "EUROPEAN"}, 3, 1, null, "3.5", null, null, null);
    public static final BooleanConfig AUTO_ADD_MISSED_INDEXES = new BooleanConfig("FRM", "CARINA", "AUTO_ADD_MISSED_INDEXES", "023", true, 2, 1, null, "4.0", null, null, null);
    public static final BooleanConfig TABLE_AUTOMATIC_REFRESH_ON_CHANGE = new BooleanConfig("FRM", "CARINA", "TABLE_AUTOMATIC_REFRESH_ON_CHANGE", "024", false, 5, 1, null, "3.2", null, null, null);
    public static final LongConfig LIMIT_FETCH_GKEY = new LongConfig("FRM", "CARINA", "LIMIT_FETCH_GKEY", "025", 50000L, 100L, 1000000L, 5, 1, null, "4.1", null, null, null);
    public static final LongConfig DIAGNOSTIC_MAX_CAPTURE_USER_EVENTS = new LongConfig("FRM", "CARINA", "DIAGNOSTIC_MAX_CAPTURE_USER_EVENTS", "026", 10000L, 1000L, 100000L, 1, 1, null, "5.0", null, null, null);
    public static final CodedConfig DECIMAL_FIELD_ROUNDING_MODE = new CodedConfig("FRM", "CARINA", "DECIMAL_FIELD_ROUNDING_MODE", "027", false, "HALF_UP", new String[]{"CEILING", "DOWN", "FLOOR", "HALF_DOWN", "HALF_EVEN", "HALF_UP", "UP"}, 5, 1, null, "5.0", null, null, null);
    public static final LongConfig HYDRATION_QUERY_BATCH_SIZE = new LongConfig("FRM", "CARINA", "HYDRATION_QUERY_BATCH_SIZE", "028", 150L, 100L, 1000L, 5, 1, null, "5.1", null, null, null);
    public static final StringConfig DATE_TIME_DISPLAY_FORMAT_INCLUDES_SECONDS = new StringConfig("FRM", "CARINA", "DATE_TIME_DISPLAY_FORMAT_INCLUDES_SECONDS", "029", "yy-MMM-dd HH:mm:ss.S", 9999, false, 5, 1, null, "5.1", null, null, null);
    public static final LongConfig EXTENSION_STATISTICS_MAX_CAPACITY = new LongConfig("FRM", "CARINA", "EXTENSION_STATISTICS_MAX_CAPACITY", "030", 10000L, 1000L, 100000L, 1, 1, null, "5.1", null, null, null);
    public static final BooleanConfig SHOW_COMMANDS_NOT_GRANTED_BY_SECURITY = new BooleanConfig("FRM", "CARINA", "SHOW_COMMANDS_NOT_GRANTED_BY_SECURITY", "031", true, 5, 1, null, "6.2", null, null, null);
    public static final BooleanConfig CODE_EXTENSION_BY_REST_ENABLE = new BooleanConfig("FRM", "CARINA", "CODE_EXTENSION_BY_REST_ENABLE", "032", false, 4, 1, null, "6.2", null, null, null);
    public static final LongConfig GADGET_REFRESH_PERIOD = new LongConfig("FRM", "CARINA", "GADGET_REFRESH_PERIOD", "033", 180L, 0L, 3000L, 5, 1, null, "1.8", null, null, null);
    public static final BooleanConfig ENABLE_LIVE_DATA_CACHING = new BooleanConfig("FRM", "CARINA", "ENABLE_LIVE_DATA_CACHING", "034", false, 5, 1, null, "3.2", null, null, null);
    public static final StringConfig FORGOT_PASSWORD_FROM_EMAIL = new StringConfig("FRM", "CARINA", "FORGOT_PASSWORD_FROM_EMAIL", "035", "", 9999, false, 1, 1, null, "7.0", null, null, null);
    public static final BooleanConfig YARD_IMAGE_SIZE_SMALL = new BooleanConfig("FRM", "CARINA", "YARD_IMAGE_SIZE_SMALL", "036", false, 5, 1, null, "2.6", null, null, null);
    public static final BooleanConfig AUTHENTICATION_EVENT_TO_DB = new BooleanConfig("FRM", "SECURITY", "AUTHENTICATION_EVENT_TO_DB", "001", false, 5, 1, null, "2.1", null, null, null);
    public static final LongConfig PASSWORD_NO_RECYLCE_COUNT = new LongConfig("FRM", "SECURITY", "PASSWORD_NO_RECYLCE_COUNT", "009", 3L, 1L, 6L, 5, 1, null, "3.0", null, null, null);
    public static final LongConfig PASSWORD_STRENGTH_MINIMUM_LENGTH = new LongConfig("FRM", "SECURITY", "PASSWORD_STRENGTH_MINIMUM_LENGTH", "010", 5L, 5L, 20L, 5, 1, null, "3.0", null, null, null);
    public static final LongConfig PASSWORD_STRENGTH_MINIMUM_LOWER_CASE = new LongConfig("FRM", "SECURITY", "PASSWORD_STRENGTH_MINIMUM_LOWER_CASE", "011", 0L, 0L, 20L, 5, 1, null, "3.0", null, null, null);
    public static final LongConfig PASSWORD_STRENGTH_MINIMUM_UPPER_CASE = new LongConfig("FRM", "SECURITY", "PASSWORD_STRENGTH_MINIMUM_UPPER_CASE", "012", 0L, 0L, 20L, 5, 1, null, "3.0", null, null, null);
    public static final LongConfig PASSWORD_STRENGTH_MINIMUM_NUMERIC = new LongConfig("FRM", "SECURITY", "PASSWORD_STRENGTH_MINIMUM_NUMERIC", "013", 0L, 0L, 10L, 5, 1, null, "3.0", null, null, null);
    public static final LongConfig PASSWORD_STRENGTH_MINIMUM_SPECIAL_CHARACTER = new LongConfig("FRM", "SECURITY", "PASSWORD_STRENGTH_MINIMUM_SPECIAL_CHARACTER", "014", 0L, 0L, 10L, 5, 1, null, "3.0", null, null, null);
    public static final LongConfig PASSWORD_EXPIRY_DAYS = new LongConfig("FRM", "SECURITY", "PASSWORD_EXPIRY_DAYS", "015", 365L, 0L, 1000L, 5, 1, null, "3.0", null, null, null);
    public static final LongConfig PASSWORD_EXPIRY_DAYS_BEFORE_WARN_USER = new LongConfig("FRM", "SECURITY", "PASSWORD_EXPIRY_DAYS_BEFORE_WARN_USER", "016", 5L, 3L, 14L, 5, 1, null, "3.0", null, null, null);
    public static final LongConfig AUTHENTICATION_MAX_LOGIN_FAILURES = new LongConfig("FRM", "SECURITY", "AUTHENTICATION_MAX_LOGIN_FAILURES", "020", 10L, 1L, 50L, 5, 1, null, "3.0", null, null, null);
    public static final LongConfig AUTHENTICATION_LOCKOUT_PERIOD_IN_SECONDS = new LongConfig("FRM", "SECURITY", "AUTHENTICATION_LOCKOUT_PERIOD_IN_SECONDS", "021", 5L, 0L, 100000L, 5, 1, null, "3.0", null, null, null);
    public static final BooleanConfig AUTHENTICATION_NEW_USER_PASSWORD_CHANGE_PROMPT = new BooleanConfig("FRM", "SECURITY", "AUTHENTICATION_NEW_USER_PASSWORD_CHANGE_PROMPT", "022", false, 5, 1, null, "3.4", null, null, null);
    public static final CodedConfig SYSTEM_AUTHENTICATION_METHOD = new CodedConfig("FRM", "SECURITY", "SYSTEM_AUTHENTICATION_METHOD", "023", false, "INTERNAL", new String[]{"INTERNAL", "DEPENDS_ON_USER_SETTING"}, 1, 1, new String[]{"LDAP"}, "5.0", null, null, null);
    public static final CodedConfig USER_AUTHENTICATION_METHOD_DEFAULT = new CodedConfig("FRM", "SECURITY", "USER_AUTHENTICATION_METHOD_DEFAULT", "024", false, "INTERNAL", new String[]{"INTERNAL", "EXTERNAL"}, 1, 1, new String[]{"LDAP"}, "5.0", null, null, null);
    public static final XmlConfig EXTERNAL_AUTHENTICATION_PROVIDERS_CONFIG_XML = new XmlConfig("FRM", "SECURITY", "EXTERNAL_AUTHENTICATION_PROVIDERS_CONFIG_XML", "025", true, 1, 1, new String[]{"LDAP"}, "5.0", null, null, "externalProvidersConfigLifecycle");
    public static final LongConfig AUTHENTICATION_VALID_SSO_TOKEN_PERIOD_IN_SECONDS = new LongConfig("FRM", "SECURITY", "AUTHENTICATION_VALID_SSO_TOKEN_PERIOD_IN_SECONDS", "026", 45L, 20L, 300L, 5, 1, null, "5.1", null, null, null);
    public static final LongConfig PURGE_APPLICATION_LOG_DAYS_TO_RETAIN = new LongConfig("FRM", "PURGE", "PURGE_APPLICATION_LOG_DAYS_TO_RETAIN", "001", 7L, 1L, 3650L, 5, 1, null, "3.1", null, null, null);
    public static final BooleanConfig MOBILE_SHOW_TEK = new BooleanConfig("FRM", "MOBILE", "MOBILE_SHOW_TEK", "001", false, 5, 1, null, "3.1", null, null, null);
    public static final BooleanConfig MOBILE_CONVERT_INPUT_TO_CAPS = new BooleanConfig("FRM", "MOBILE", "MOBILE_CONVERT_INPUT_TO_CAPS", "002", true, 5, 1, null, "3.1", null, null, null);
    public static final CodedConfig MOBILE_LOV_DISPLAY_STYLE = new CodedConfig("FRM", "MOBILE", "MOBILE_LOV_DISPLAY_STYLE", "003", false, "BOTH_CODE_AND_DESCRIPTION", new String[]{"BOTH_CODE_AND_DESCRIPTION", "CODE_ONLY", "DESCRIPTION_ONLY"}, 5, 1, null, "3.2", null, null, null);
    public static final StringConfig MOBILE_BROWSER_SCALING = new StringConfig("FRM", "MOBILE", "MOBILE_BROWSER_SCALING", "004", "width=device_width, initial-scale=2.0, maximum-scale=4.0", 9999, false, 5, 1, null, "7.0", null, null, null);
    public static final LongConfig MOBILE_SESSION_TIMEOUT_IN_MINUTES = new LongConfig("FRM", "MOBILE", "MOBILE_SESSION_TIMEOUT_IN_MINUTES", "005", 20L, 20L, 720L, 5, 1, null, "3.3", null, null, null);
    public static final StringConfig ZK_DEFAULT_SAVED_QUERY_LOV_KEY = new StringConfig("FRM", "ZK", "ZK_DEFAULT_SAVED_QUERY_LOV_KEY", "001", "com.navis.query.SAVED_QUERIES_FOR_USER", 9999, false, 5, 1, null, "4.0", null, null, null);

    private FrameworkConfigConsts() {
    }

}
