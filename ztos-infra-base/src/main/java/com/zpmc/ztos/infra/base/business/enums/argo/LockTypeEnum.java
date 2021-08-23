package com.zpmc.ztos.infra.base.business.enums.argo;

import org.apache.commons.lang.enums.EnumUtils;

import java.util.EnumSet;
import java.util.List;

public enum LockTypeEnum {
    YARD_PLAN(1, "YARD_PLAN", "", "", "YARD_PLAN"),
    SELECT_EMPTY(2, "SELECT_EMPTY", "", "", "SELECT_EMPTY"),
    SELECT_IMPORT_BL(3, "SELECT_IMPORT_BL", "", "", "SELECT_IMPORT_BL"),
    SELECT_BKG_DRAYOFF(4, "SELECT_BKG_DRAYOFF", "", "", "SELECT_BKG_DRAYOFF"),
    SELECT_CHASSIS(5, "SELECT_CHASSIS", "", "", "SELECT_CHASSIS"),
    TZ_DECKING(6, "TZ_DECKING", "", "", "TZ_DECKING"),
    APPT_TIME_SLOT(7, "APPT_TIME_SLOT", "", "", "APPT_TIME_SLOT"),
    UPDATE_JOB_STEP_PROJECTION(8, "UPDATE_JOB_STEP_PROJECTION", "", "", "UPDATE_JOB_STEP_PROJECTION"),
    INV_FINAL_NBR(9, "INV_FINAL_NBR", "", "", "INV_FINAL_NBR"),
    WORK_QUEUE_INIT(10, "WORK_QUEUE_INIT", "", "", "WORK_QUEUE_INIT"),
    EXCHANGE_LANE(11, "EXCHANGE_LANE", "", "", "EXCHANGE_LANE"),
    GATE_APPT(12, "GATE_APPT", "", "", "GATE_APPT"),
    QC_DSCH_WA_DISPATCH(13, "QC_DSCH_WA_DISPATCH", "", "", "QC_DSCH_WA_DISPATCH"),
    UNIT_DISMOUNT(14, "UNIT_DISMOUNT", "", "", "UNIT_DISMOUNT");
    private final int key;
    private final String code;
    private final String desc;
    private final String color;
    private final String displayName;

    LockTypeEnum(int key, String code, String desc, String color, String displayName) {
        this.key = key;
        this.code = code;
        this.desc = desc;
        this.color = color;
        this.displayName = displayName;
    }

    public static List getEnumList() {
        return EnumUtils.getEnumList(LockTypeEnum.class);
    }

    public static EnumSet getEnumSet() {
        return EnumSet.allOf(LockTypeEnum.class);
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