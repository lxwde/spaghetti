package com.zpmc.ztos.infra.base.business.enums.yard;

import org.apache.commons.lang.enums.EnumUtils;

import java.util.EnumSet;
import java.util.List;

public enum YardBlockTypeEnum {
    LOGICAL(1, "LOGICAL", "", "", "LOGICAL"),
    BUILDING(2, "BUILDING", "", "", "BUILDING"),
    HEAP(3, "HEAP", "", "", "HEAP"),
    LIST(4, "LIST", "", "", "LIST"),
    WHEELED_HEAP(5, "WHEELED_HEAP", "", "", "WHEELED_HEAP"),
    CHASSIS(6, "CHASSIS", "", "", "CHASSIS"),
    FORKLIFT(7, "FORKLIFT", "", "", "FORKLIFT"),
    STRADDLE(8, "STRADDLE", "", "", "STRADDLE"),
    GRID(9, "GRID", "", "", "GRID"),
    TRANSTAINER(10, "TRANSTAINER", "", "", "TRANSTAINER"),
    ASC(11, "ASC", "", "", "ASC"),
    RAIL(12, "RAIL", "", "", "RAIL"),
    BUFFER_HEAP(13, "BUFFER_HEAP", "", "", "BUFFER_HEAP"),
    WAREHOUSE_STACKED_INSIDE(14, "WAREHOUSE_STACKED_INSIDE", "", "", "WAREHOUSE_STACKED_INSIDE"),
    WAREHOUSE_STACKED_OUTSIDE(15, "WAREHOUSE_STACKED_OUTSIDE", "", "", "WAREHOUSE_STACKED_OUTSIDE"),
    WAREHOUSE_HEAP_INSIDE(16, "WAREHOUSE_HEAP_INSIDE", "", "", "WAREHOUSE_HEAP_INSIDE"),
    WAREHOUSE_HEAP_OUTSIDE(17, "WAREHOUSE_HEAP_OUTSIDE", "", "", "WAREHOUSE_HEAP_OUTSIDE"),
    BUFFER_PARALLEL(18, "BUFFER_PARALLEL", "", "", "BUFFER_PARALLEL"),
    BUFFER_SERIAL(19, "BUFFER_SERIAL", "", "", "BUFFER_SERIAL"),
    TRANSFER_ZONE_WHEELED(20, "TRANSFER_ZONE_WHEELED", "", "", "TRANSFER_ZONE_WHEELED"),
    TRANSFER_ZONE_STRADDLE(21, "TRANSFER_ZONE_STRADDLE", "", "", "TRANSFER_ZONE_STRADDLE"),
    TRANSFER_ZONE_TRANSTAINER(22, "TRANSFER_ZONE_TRANSTAINER", "", "", "TRANSFER_ZONE_TRANSTAINER"),
    UNKNOWN(23, "UNKNOWN", "", "", "UNKNOWN");
    private final int key;
    private final String code;
    private final String desc;
    private final String color;
    private final String displayName;

    YardBlockTypeEnum(int key, String code, String desc, String color, String displayName) {
        this.key = key;
        this.code = code;
        this.desc = desc;
        this.color = color;
        this.displayName = displayName;
    }

    public static List getEnumList() {
        return EnumUtils.getEnumList(YardBlockTypeEnum.class);
    }

    public static EnumSet getEnumSet() {
        return EnumSet.allOf(YardBlockTypeEnum.class);
    }

    public int getKey() {
        return key;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public String getColor() {
        return color;
    }

    public String getDisplayName() {
        return displayName;
    }

}