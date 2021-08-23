package com.zpmc.ztos.infra.base.business.xps;

import com.sun.istack.NotNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class XpsTableId {
    public static final int UNKNOWN = 0;
    public static final int WORK_QUEUE = 1;
    public static final int AF = 2;
    public static final int AG = 3;
    public static final int AR = 4;
    public static final int TRAIN_VISIT = 5;
    public static final int CAR = 6;
    public static final int CAR_GEOMETRY = 7;
    public static final int CAR_TYPE = 8;
    public static final int POOL = 9;
    public static final int POINT_OF_WORK = 10;
    public static final int CHE = 11;
    public static final int SHIP_VISIT = 12;
    public static final int SERVICE = 13;
    public static final int WEIGHT_GROUP = 14;
    public static final int STACK_NOTE = 15;
    public static final int ROUTE_RESTRICTION = 16;
    public static final int EDI_PARTNER = 17;
    public static final int EDI_INTERCHANGE = 18;
    public static final int SERVICE_OR_LINE_PORT_MAPPING = 19;
    public static final int DISCHARGE_MAPPING = 20;
    public static final int GROUP_OR_DESTINATION_MAPPING = 21;
    public static final int PODDEST_MAP = 22;
    public static final int LINE_OPERATOR_MAPPING = 23;
    public static final int HAZ_CARGO = 24;
    public static final int WORK_SHIFT = 25;
    public static final int POD_MAPPING = 26;
    public static final int POL_MAPPING = 27;
    public static final int TRAIN_SERVICE_MAPPING = 28;
    public static final int USER = 29;
    public static final int GROUP = 30;
    public static final int SESSION = 31;
    public static final int WORK_INSTRUCTION = 32;
    public static final int STACK_STATUS = 33;
    public static final int CONTAINER_ARGO = 34;
    public static final int PROJECTIONS = 35;
    public static final int RAIL_STATE = 36;
    public static final int USER_FILTER = 37;
    public static final int PRIORITY_CODE_MAPPING = 38;
    public static final int CHE_ZONE = 39;
    public static final int CRANE_ACTIVITY = 40;
    public static final int NON_CON_CARGO_OBJ = 41;
    public static final int LIST_VIEW_FORMAT = 42;
    public static final int DISCHARGE_HUB_MAPPING = 43;
    public static final int RECAP_VIEW_FORMAT = 44;
    public static final int SHARED_PARAMETERS = 45;
    public static final int LINE_GROUP_MAPPING = 46;
    public static final int REEFER_DETAIL = 47;
    public static final int STOW_FACTOR_FILTER = 48;
    public static final int SECTION_FACTOR_FILTER = 49;
    public static final int SAVED_QUERY = 50;
    public static final int ABB_LOAD_HISTORY = 51;
    public static final int BERTH_ACTIVITY = 52;
    public static final int WS_LIST_VIEW_FORMAT = 53;
    public static final int ROLE = 54;
    public static final int TRUCK_DRIVER = 55;
    public static final int EC_EVENT = 56;
    public static final int EC_USER = 57;
    public static final int WHEELED_MT_FILTER = 58;
    public static final int RAILCAR_DESTINATION_BLOCK_MAPPING = 59;
    public static final int PSOPEN_VESSEL = 60;
    public static final int MENU_HOT_KEY = 61;
    public static final int APP_COLOR_PALETTE_INFO = 62;
    public static final int REPORT_CONFIGURATION = 63;
    public static final int REPORT_PACKAGE = 64;
    public static final int TRANSPORT_ORDER = 65;
    public static final int TRANSPORT_ITEM = 66;
    public static final int TRIP = 67;
    public static final int AUTO_GQ = 68;
    public static final int TRANS_SHIPMENT = 69;
    public static final int TRAIN_MAX_MAPPING = 70;
    public static final int TRANSFER_POINT = 71;
    public static final int WHERENET_ALARM = 72;
    public static final int POOL_EQUIPMENT = 73;
    public static final int DRIVE_TIME = 74;
    public static final int PDS_ALARM = 75;
    public static final int LOCAL_HUB = 76;
    public static final int BERTH_ALLOCATION = 77;
    public static final int SIMULATOR_EVENT = 78;
    public static final int RDT_EMULATOR_EVENT = 79;
    public static final int CHE_TRAILER = 80;
    public static final int MOVE_KIND_ALLOWED = 81;
    public static final int YMF_RANGE = 82;
    public static final int YARD_MOVE_FILTER = 83;
    public static final int POOL_CHE_COUNT = 84;
    public static final int DELIVERY_RANGE = 85;
    public static final int LASHING = 86;
    public static final int SAILING_CONDITION = 87;
    public static final int PWP = 88;
    public static final int BAY_NOTES = 89;
    public static final int SUMMARY_MOMENTS = 90;
    public static final int SHIP_WEIGHTS = 91;
    public static final int REF_DATA_EQUIPMENT_TYPE = 92;
    public static final int REF_DATA_PORT_CODE = 93;
    public static final int REF_DATA_COMMODITY = 94;
    public static final int REF_DATA_GROUP = 95;
    public static final int REF_DATA_LINE_OPERATOR = 96;
    public static final int REF_DATA_SPECIAL_STOW = 97;
    public static final int REF_DATA_VESSEL_CODE = 98;
    public static final int CONTAINER_SPARCS = 99;
    public static final int CONTAINER_POSITION = 100;
    public static final int YARD_MODEL = 101;
    public static final int YARD_BLOCK = 102;
    public static final int TBD_UNIT = 103;
    public static final int YARD_SECTION = 104;
    public static final int YARD_STACK = 105;
    public static final int BERTH_MODEL = 106;
    public static final int CHE_ECSTATE = 107;
    public static final int WORK_EXECUTION = 108;
    public static final int WORK_ASSIGNMENT = 109;
    public static final int HYPERLINK_ASSET = 110;
    public static final int CHE_FILTER = 111;
    public static final int BLOCK_DISPATCH_OPTIONS = 112;
    public static final int FLEX_FIELD = 113;
    public static final int AUTOSTOW_FACTOR_FILTER = 114;
    public static final int EC_ALARM = 115;
    public static final int QUOTA_FILTER = 116;
    public static final int REF_DATA_CRANE_DELAY_TYPE = 117;
    public static final int VALIDATION_CONFIG = 118;
    public static final int POOL_MEMBER = 119;
    public static final int EMPTY_SELECTION_FILTER = 120;
    public static final int LOWEST_VALUE_DEFINED = 0;
    public static final int HIGHEST_VALUE_DEFINED = 120;
    public static final int NUM_VALUES_DEFINED = 121;
    private static final Map<String, Integer> NAME_TO_VALUE_MAP;

    @NotNull
    public static String toString(int inValue) {
        switch (inValue) {
            case 0: {
                return "UNKNOWN";
            }
            case 1: {
                return "WORK_QUEUE";
            }
            case 2: {
                return "AF";
            }
            case 3: {
                return "AG";
            }
            case 4: {
                return "AR";
            }
            case 5: {
                return "TRAIN_VISIT";
            }
            case 6: {
                return "CAR";
            }
            case 7: {
                return "CAR_GEOMETRY";
            }
            case 8: {
                return "CAR_TYPE";
            }
            case 9: {
                return "POOL";
            }
            case 10: {
                return "POINT_OF_WORK";
            }
            case 11: {
                return "CHE";
            }
            case 12: {
                return "SHIP_VISIT";
            }
            case 13: {
                return "SERVICE";
            }
            case 14: {
                return "WEIGHT_GROUP";
            }
            case 15: {
                return "STACK_NOTE";
            }
            case 16: {
                return "ROUTE_RESTRICTION";
            }
            case 17: {
                return "EDI_PARTNER";
            }
            case 18: {
                return "EDI_INTERCHANGE";
            }
            case 19: {
                return "SERVICE_OR_LINE_PORT_MAPPING";
            }
            case 20: {
                return "DISCHARGE_MAPPING";
            }
            case 21: {
                return "GROUP_OR_DESTINATION_MAPPING";
            }
            case 22: {
                return "PODDEST_MAP";
            }
            case 23: {
                return "LINE_OPERATOR_MAPPING";
            }
            case 24: {
                return "HAZ_CARGO";
            }
            case 25: {
                return "WORK_SHIFT";
            }
            case 26: {
                return "POD_MAPPING";
            }
            case 27: {
                return "POL_MAPPING";
            }
            case 28: {
                return "TRAIN_SERVICE_MAPPING";
            }
            case 29: {
                return "USER";
            }
            case 30: {
                return "GROUP";
            }
            case 31: {
                return "SESSION";
            }
            case 32: {
                return "WORK_INSTRUCTION";
            }
            case 33: {
                return "STACK_STATUS";
            }
            case 34: {
                return "CONTAINER_ARGO";
            }
            case 35: {
                return "PROJECTIONS";
            }
            case 36: {
                return "RAIL_STATE";
            }
            case 37: {
                return "USER_FILTER";
            }
            case 38: {
                return "PRIORITY_CODE_MAPPING";
            }
            case 39: {
                return "CHE_ZONE";
            }
            case 40: {
                return "CRANE_ACTIVITY";
            }
            case 41: {
                return "NON_CON_CARGO_OBJ";
            }
            case 42: {
                return "LIST_VIEW_FORMAT";
            }
            case 43: {
                return "DISCHARGE_HUB_MAPPING";
            }
            case 44: {
                return "RECAP_VIEW_FORMAT";
            }
            case 45: {
                return "SHARED_PARAMETERS";
            }
            case 46: {
                return "LINE_GROUP_MAPPING";
            }
            case 47: {
                return "REEFER_DETAIL";
            }
            case 48: {
                return "STOW_FACTOR_FILTER";
            }
            case 49: {
                return "SECTION_FACTOR_FILTER";
            }
            case 50: {
                return "SAVED_QUERY";
            }
            case 51: {
                return "ABB_LOAD_HISTORY";
            }
            case 52: {
                return "BERTH_ACTIVITY";
            }
            case 53: {
                return "WS_LIST_VIEW_FORMAT";
            }
            case 54: {
                return "ROLE";
            }
            case 55: {
                return "TRUCK_DRIVER";
            }
            case 56: {
                return "EC_EVENT";
            }
            case 57: {
                return "EC_USER";
            }
            case 58: {
                return "WHEELED_MT_FILTER";
            }
            case 59: {
                return "RAILCAR_DESTINATION_BLOCK_MAPPING";
            }
            case 60: {
                return "PSOPEN_VESSEL";
            }
            case 61: {
                return "MENU_HOT_KEY";
            }
            case 62: {
                return "APP_COLOR_PALETTE_INFO";
            }
            case 63: {
                return "REPORT_CONFIGURATION";
            }
            case 64: {
                return "REPORT_PACKAGE";
            }
            case 65: {
                return "TRANSPORT_ORDER";
            }
            case 66: {
                return "TRANSPORT_ITEM";
            }
            case 67: {
                return "TRIP";
            }
            case 68: {
                return "AUTO_GQ";
            }
            case 69: {
                return "TRANS_SHIPMENT";
            }
            case 70: {
                return "TRAIN_MAX_MAPPING";
            }
            case 71: {
                return "TRANSFER_POINT";
            }
            case 72: {
                return "WHERENET_ALARM";
            }
            case 73: {
                return "POOL_EQUIPMENT";
            }
            case 74: {
                return "DRIVE_TIME";
            }
            case 75: {
                return "PDS_ALARM";
            }
            case 76: {
                return "LOCAL_HUB";
            }
            case 77: {
                return "BERTH_ALLOCATION";
            }
            case 78: {
                return "SIMULATOR_EVENT";
            }
            case 79: {
                return "RDT_EMULATOR_EVENT";
            }
            case 80: {
                return "CHE_TRAILER";
            }
            case 81: {
                return "MOVE_KIND_ALLOWED";
            }
            case 82: {
                return "YMF_RANGE";
            }
            case 83: {
                return "YARD_MOVE_FILTER";
            }
            case 84: {
                return "POOL_CHE_COUNT";
            }
            case 85: {
                return "DELIVERY_RANGE";
            }
            case 86: {
                return "LASHING";
            }
            case 87: {
                return "SAILING_CONDITION";
            }
            case 88: {
                return "PWP";
            }
            case 89: {
                return "BAY_NOTES";
            }
            case 90: {
                return "SUMMARY_MOMENTS";
            }
            case 91: {
                return "SHIP_WEIGHTS";
            }
            case 92: {
                return "REF_DATA_EQUIPMENT_TYPE";
            }
            case 93: {
                return "REF_DATA_PORT_CODE";
            }
            case 94: {
                return "REF_DATA_COMMODITY";
            }
            case 95: {
                return "REF_DATA_GROUP";
            }
            case 96: {
                return "REF_DATA_LINE_OPERATOR";
            }
            case 97: {
                return "REF_DATA_SPECIAL_STOW";
            }
            case 98: {
                return "REF_DATA_VESSEL_CODE";
            }
            case 99: {
                return "CONTAINER_SPARCS";
            }
            case 100: {
                return "CONTAINER_POSITION";
            }
            case 101: {
                return "YARD_MODEL";
            }
            case 102: {
                return "YARD_BLOCK";
            }
            case 103: {
                return "TBD_UNIT";
            }
            case 104: {
                return "YARD_SECTION";
            }
            case 105: {
                return "YARD_STACK";
            }
            case 106: {
                return "BERTH_MODEL";
            }
            case 107: {
                return "CHE_ECSTATE";
            }
            case 108: {
                return "WORK_EXECUTION";
            }
            case 109: {
                return "WORK_ASSIGNMENT";
            }
            case 110: {
                return "HYPERLINK_ASSET";
            }
            case 111: {
                return "CHE_FILTER";
            }
            case 112: {
                return "BLOCK_DISPATCH_OPTIONS";
            }
            case 113: {
                return "FLEX_FIELD";
            }
            case 114: {
                return "AUTOSTOW_FACTOR_FILTER";
            }
            case 115: {
                return "EC_ALARM";
            }
            case 116: {
                return "QUOTA_FILTER";
            }
            case 117: {
                return "REF_DATA_CRANE_DELAY_TYPE";
            }
            case 118: {
                return "VALIDATION_CONFIG";
            }
            case 119: {
                return "POOL_MEMBER";
            }
            case 120: {
                return "EMPTY_SELECTION_FILTER";
            }
        }
        return Integer.toString(inValue);
    }

    public static int fromString(@NotNull String inIdName) {
        Integer result = NAME_TO_VALUE_MAP.get(inIdName);
        return result == null ? -1 : result;
    }

    private XpsTableId() {
    }

    static {
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        map.put("UNKNOWN", 0);
        map.put("WORK_QUEUE", 1);
        map.put("AF", 2);
        map.put("AG", 3);
        map.put("AR", 4);
        map.put("TRAIN_VISIT", 5);
        map.put("CAR", 6);
        map.put("CAR_GEOMETRY", 7);
        map.put("CAR_TYPE", 8);
        map.put("POOL", 9);
        map.put("POINT_OF_WORK", 10);
        map.put("CHE", 11);
        map.put("SHIP_VISIT", 12);
        map.put("SERVICE", 13);
        map.put("WEIGHT_GROUP", 14);
        map.put("STACK_NOTE", 15);
        map.put("ROUTE_RESTRICTION", 16);
        map.put("EDI_PARTNER", 17);
        map.put("EDI_INTERCHANGE", 18);
        map.put("SERVICE_OR_LINE_PORT_MAPPING", 19);
        map.put("DISCHARGE_MAPPING", 20);
        map.put("GROUP_OR_DESTINATION_MAPPING", 21);
        map.put("PODDEST_MAP", 22);
        map.put("LINE_OPERATOR_MAPPING", 23);
        map.put("HAZ_CARGO", 24);
        map.put("WORK_SHIFT", 25);
        map.put("POD_MAPPING", 26);
        map.put("POL_MAPPING", 27);
        map.put("TRAIN_SERVICE_MAPPING", 28);
        map.put("USER", 29);
        map.put("GROUP", 30);
        map.put("SESSION", 31);
        map.put("WORK_INSTRUCTION", 32);
        map.put("STACK_STATUS", 33);
        map.put("CONTAINER_ARGO", 34);
        map.put("PROJECTIONS", 35);
        map.put("RAIL_STATE", 36);
        map.put("USER_FILTER", 37);
        map.put("PRIORITY_CODE_MAPPING", 38);
        map.put("CHE_ZONE", 39);
        map.put("CRANE_ACTIVITY", 40);
        map.put("NON_CON_CARGO_OBJ", 41);
        map.put("LIST_VIEW_FORMAT", 42);
        map.put("DISCHARGE_HUB_MAPPING", 43);
        map.put("RECAP_VIEW_FORMAT", 44);
        map.put("SHARED_PARAMETERS", 45);
        map.put("LINE_GROUP_MAPPING", 46);
        map.put("REEFER_DETAIL", 47);
        map.put("STOW_FACTOR_FILTER", 48);
        map.put("SECTION_FACTOR_FILTER", 49);
        map.put("SAVED_QUERY", 50);
        map.put("ABB_LOAD_HISTORY", 51);
        map.put("BERTH_ACTIVITY", 52);
        map.put("WS_LIST_VIEW_FORMAT", 53);
        map.put("ROLE", 54);
        map.put("TRUCK_DRIVER", 55);
        map.put("EC_EVENT", 56);
        map.put("EC_USER", 57);
        map.put("WHEELED_MT_FILTER", 58);
        map.put("RAILCAR_DESTINATION_BLOCK_MAPPING", 59);
        map.put("PSOPEN_VESSEL", 60);
        map.put("MENU_HOT_KEY", 61);
        map.put("APP_COLOR_PALETTE_INFO", 62);
        map.put("REPORT_CONFIGURATION", 63);
        map.put("REPORT_PACKAGE", 64);
        map.put("TRANSPORT_ORDER", 65);
        map.put("TRANSPORT_ITEM", 66);
        map.put("TRIP", 67);
        map.put("AUTO_GQ", 68);
        map.put("TRANS_SHIPMENT", 69);
        map.put("TRAIN_MAX_MAPPING", 70);
        map.put("TRANSFER_POINT", 71);
        map.put("WHERENET_ALARM", 72);
        map.put("POOL_EQUIPMENT", 73);
        map.put("DRIVE_TIME", 74);
        map.put("PDS_ALARM", 75);
        map.put("LOCAL_HUB", 76);
        map.put("BERTH_ALLOCATION", 77);
        map.put("SIMULATOR_EVENT", 78);
        map.put("RDT_EMULATOR_EVENT", 79);
        map.put("CHE_TRAILER", 80);
        map.put("MOVE_KIND_ALLOWED", 81);
        map.put("YMF_RANGE", 82);
        map.put("YARD_MOVE_FILTER", 83);
        map.put("POOL_CHE_COUNT", 84);
        map.put("DELIVERY_RANGE", 85);
        map.put("LASHING", 86);
        map.put("SAILING_CONDITION", 87);
        map.put("PWP", 88);
        map.put("BAY_NOTES", 89);
        map.put("SUMMARY_MOMENTS", 90);
        map.put("SHIP_WEIGHTS", 91);
        map.put("REF_DATA_EQUIPMENT_TYPE", 92);
        map.put("REF_DATA_PORT_CODE", 93);
        map.put("REF_DATA_COMMODITY", 94);
        map.put("REF_DATA_GROUP", 95);
        map.put("REF_DATA_LINE_OPERATOR", 96);
        map.put("REF_DATA_SPECIAL_STOW", 97);
        map.put("REF_DATA_VESSEL_CODE", 98);
        map.put("CONTAINER_SPARCS", 99);
        map.put("CONTAINER_POSITION", 100);
        map.put("YARD_MODEL", 101);
        map.put("YARD_BLOCK", 102);
        map.put("TBD_UNIT", 103);
        map.put("YARD_SECTION", 104);
        map.put("YARD_STACK", 105);
        map.put("BERTH_MODEL", 106);
        map.put("CHE_ECSTATE", 107);
        map.put("WORK_EXECUTION", 108);
        map.put("WORK_ASSIGNMENT", 109);
        map.put("HYPERLINK_ASSET", 110);
        map.put("CHE_FILTER", 111);
        map.put("BLOCK_DISPATCH_OPTIONS", 112);
        map.put("FLEX_FIELD", 113);
        map.put("AUTOSTOW_FACTOR_FILTER", 114);
        map.put("EC_ALARM", 115);
        map.put("QUOTA_FILTER", 116);
        map.put("REF_DATA_CRANE_DELAY_TYPE", 117);
        map.put("VALIDATION_CONFIG", 118);
        map.put("POOL_MEMBER", 119);
        map.put("EMPTY_SELECTION_FILTER", 120);
        NAME_TO_VALUE_MAP = Collections.unmodifiableMap(map);
    }
}
