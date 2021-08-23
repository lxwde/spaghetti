package com.zpmc.ztos.infra.base.business.enums.argo;

import com.zpmc.ztos.infra.base.common.enums.AtomizedEnum;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

//public enum DataSourceEnum {
//    UNKNOWN(1, "UNKNOWN", "", "", "UNKNOWN"),
//    TESTING(2, "TESTING", "", "", "TESTING"),
//    BUILT_IN(3, "BUILT_IN", "", "", "BUILT_IN"),
//    IN_GATE(4, "IN_GATE", "", "", "IN_GATE"),
//    DATA_IMPORT(5, "DATA_IMPORT", "", "", "DATA_IMPORT"),
//    SNX(6, "SNX", "", "", "SNX"),
//    AUTO_GEN(7, "AUTO_GEN", "", "", "AUTO_GEN"),
//    USER_LCL(8, "USER_LCL", "", "", "USER_LCL"),
//    USER_WEB(9, "USER_WEB", "", "", "USER_WEB"),
//    USER_DBA(10, "USER_DBA", "", "", "USER_DBA"),
//    EDI_ACTIVITY(11, "EDI_ACTIVITY", "", "", "EDI_ACTIVITY"),
//    EDI_APPOINTMENT(12, "EDI_APPOINTMENT", "", "", "EDI_APPOINTMENT"),
//    EDI_PREADVISE(13, "EDI_PREADVISE", "", "", "EDI_PREADVISE"),
//    EDI_STOW(14, "EDI_STOW", "", "", "EDI_STOW"),
//    EDI_LDLT(15, "EDI_LDLT", "", "", "EDI_LDLT"),
//    EDI_DCLT(16, "EDI_DCLT", "", "", "EDI_DCLT"),
//    EDI_BKG(17, "EDI_BKG", "", "", "EDI_BKG"),
//    EDI_MNFST(18, "EDI_MNFST", "", "", "EDI_MNFST"),
//    EDI_HAZRD(19, "EDI_HAZRD", "", "", "EDI_HAZRD"),
//    EDI_RELS(20, "EDI_RELS", "", "", "EDI_RELS"),
//    EDI_CNST(21, "EDI_CNST", "", "", "EDI_CNST"),
//    EDI_INVT(22, "EDI_INVT", "", "", "EDI_INVT"),
//    EDI_TRNPT(23, "EDI_TRNPT", "", "", "EDI_TRNPT"),
//    EDI_ACK(24, "EDI_ACK", "", "", "EDI_ACK"),
//    EDI_RLORD(25, "EDI_RLORD", "", "", "EDI_RLORD"),
//    EDI_RLWAYBILL(26, "EDI_RLWAYBILL", "", "", "EDI_RLWAYBILL"),
//    EDI_SNX(27, "EDI_SNX", "", "", "EDI_SNX"),
//    EDI_SAUDILDP(28, "EDI_SAUDILDP", "", "", "EDI_SAUDILDP"),
//    EDI_MCPVESSELVISIT(29, "EDI_MCPVESSELVISIT", "", "", "EDI_MCPVESSELVISIT"),
//    EDI_VERMAS(30, "EDI_VERMAS", "", "", "EDI_VERMAS"),
//    REFCON(31, "REFCON", "", "", "REFCON"),
//    XPS(32, "XPS", "", "", "XPS"),
//    EQ_SERIAL_RANGE(33, "EQ_SERIAL_RANGE", "", "", "EQ_SERIAL_RANGE"),
//    PURGE_JOB(34, "PURGE_JOB", "", "", "PURGE_JOB"),
//    EDI_SAUDIMANIFEST(35, "EDI_SAUDIMANIFEST", "", "", "EDI_SAUDIMANIFEST"),
//    UI_LOADLIST(36, "UI_LOADLIST", "", "", "UI_LOADLIST"),
//    UI_DISCHARGELIST(37, "UI_DISCHARGELIST", "", "", "UI_DISCHARGELIST"),
//    GATE_CLERK(38, "GATE_CLERK", "", "", "GATE_CLERK"),
//    AUTO_GATE(39, "AUTO_GATE", "", "", "AUTO_GATE"),
//    AUTO_ASSIGNED(40, "AUTO_ASSIGNED", "", "", "AUTO_ASSIGNED"),
//    LANE_FIX(41, "LANE_FIX", "", "", "LANE_FIX"),
//    AUTOMATION(42, "AUTOMATION", "", "", "AUTOMATION"),
//    CRANE_AUTOMATION_SYSTEM(43, "CRANE_AUTOMATION_SYSTEM", "", "", "CRANE_AUTOMATION_SYSTEM");
//    private final int key;
//    private final String code;
//    private final String desc;
//    private final String color;
//    private final String displayName;
//
//    DataSourceEnum(int key, String code, String desc, String color, String displayName) {
//        this.key = key;
//        this.code = code;
//        this.desc = desc;
//        this.color = color;
//        this.displayName = displayName;
//    }
//
//    public static List getEnumList() {
//        return EnumUtils.getEnumList(DataSourceEnum.class);
//    }
//
//    public static EnumSet getEnumSet() {
//        return EnumSet.allOf(DataSourceEnum.class);
//    }
//
//    public int getKey() {
//        return key;
//    }
//
//    public String getCode() {
//        return code;
//    }
//
//    public String getDesc() {
//        return desc;
//    }
//
//    public String getColor() {
//        return color;
//    }
//
//    public String getDisplayName() {
//        return displayName;
//    }
//
//}

public class DataSourceEnum
        extends AtomizedEnum {
    public static final DataSourceEnum UNKNOWN = new DataSourceEnum("UNKNOWN", "atom.DataSourceEnum.UNKNOWN.description", "atom.DataSourceEnum.UNKNOWN.code", "", "", "");
    public static final DataSourceEnum TESTING = new DataSourceEnum("TESTING", "atom.DataSourceEnum.TESTING.description", "atom.DataSourceEnum.TESTING.code", "", "", "");
    public static final DataSourceEnum BUILT_IN = new DataSourceEnum("BUILT_IN", "atom.DataSourceEnum.BUILT_IN.description", "atom.DataSourceEnum.BUILT_IN.code", "", "", "");
    public static final DataSourceEnum IN_GATE = new DataSourceEnum("IN_GATE", "atom.DataSourceEnum.IN_GATE.description", "atom.DataSourceEnum.IN_GATE.code", "", "", "");
    public static final DataSourceEnum SPARCS_IMPORT = new DataSourceEnum("SPARCS_IMPORT", "atom.DataSourceEnum.SPARCS_IMPORT.description", "atom.DataSourceEnum.SPARCS_IMPORT.code", "", "", "");
    public static final DataSourceEnum DATA_IMPORT = new DataSourceEnum("DATA_IMPORT", "atom.DataSourceEnum.DATA_IMPORT.description", "atom.DataSourceEnum.DATA_IMPORT.code", "", "", "");
    public static final DataSourceEnum SNX = new DataSourceEnum("SNX", "atom.DataSourceEnum.SNX.description", "atom.DataSourceEnum.SNX.code", "", "", "");
    public static final DataSourceEnum AUTO_GEN = new DataSourceEnum("AUTO_GEN", "atom.DataSourceEnum.AUTO_GEN.description", "atom.DataSourceEnum.AUTO_GEN.code", "", "", "");
    public static final DataSourceEnum USER_LCL = new DataSourceEnum("USER_LCL", "atom.DataSourceEnum.USER_LCL.description", "atom.DataSourceEnum.USER_LCL.code", "", "", "");
    public static final DataSourceEnum USER_WEB = new DataSourceEnum("USER_WEB", "atom.DataSourceEnum.USER_WEB.description", "atom.DataSourceEnum.USER_WEB.code", "", "", "");
    public static final DataSourceEnum USER_DBA = new DataSourceEnum("USER_DBA", "atom.DataSourceEnum.USER_DBA.description", "atom.DataSourceEnum.USER_DBA.code", "", "", "");
    public static final DataSourceEnum EDI_ACTIVITY = new DataSourceEnum("EDI_ACTIVITY", "atom.DataSourceEnum.EDI_ACTIVITY.description", "atom.DataSourceEnum.EDI_ACTIVITY.code", "", "", "");
    public static final DataSourceEnum EDI_APPOINTMENT = new DataSourceEnum("EDI_APPOINTMENT", "atom.DataSourceEnum.EDI_APPOINTMENT.description", "atom.DataSourceEnum.EDI_APPOINTMENT.code", "", "", "");
    public static final DataSourceEnum EDI_PREADVISE = new DataSourceEnum("EDI_PREADVISE", "atom.DataSourceEnum.EDI_PREADVISE.description", "atom.DataSourceEnum.EDI_PREADVISE.code", "", "", "");
    public static final DataSourceEnum EDI_STOW = new DataSourceEnum("EDI_STOW", "atom.DataSourceEnum.EDI_STOW.description", "atom.DataSourceEnum.EDI_STOW.code", "", "", "");
    public static final DataSourceEnum EDI_LDLT = new DataSourceEnum("EDI_LDLT", "atom.DataSourceEnum.EDI_LDLT.description", "atom.DataSourceEnum.EDI_LDLT.code", "", "", "");
    public static final DataSourceEnum EDI_DCLT = new DataSourceEnum("EDI_DCLT", "atom.DataSourceEnum.EDI_DCLT.description", "atom.DataSourceEnum.EDI_DCLT.code", "", "", "");
    public static final DataSourceEnum EDI_BKG = new DataSourceEnum("EDI_BKG", "atom.DataSourceEnum.EDI_BKG.description", "atom.DataSourceEnum.EDI_BKG.code", "", "", "");
    public static final DataSourceEnum EDI_MNFST = new DataSourceEnum("EDI_MNFST", "atom.DataSourceEnum.EDI_MNFST.description", "atom.DataSourceEnum.EDI_MNFST.code", "", "", "");
    public static final DataSourceEnum EDI_HAZRD = new DataSourceEnum("EDI_HAZRD", "atom.DataSourceEnum.EDI_HAZRD.description", "atom.DataSourceEnum.EDI_HAZRD.code", "", "", "");
    public static final DataSourceEnum EDI_RELS = new DataSourceEnum("EDI_RELS", "atom.DataSourceEnum.EDI_RELS.description", "atom.DataSourceEnum.EDI_RELS.code", "", "", "");
    public static final DataSourceEnum EDI_CNST = new DataSourceEnum("EDI_CNST", "atom.DataSourceEnum.EDI_CNST.description", "atom.DataSourceEnum.EDI_CNST.code", "", "", "");
    public static final DataSourceEnum EDI_INVT = new DataSourceEnum("EDI_INVT", "atom.DataSourceEnum.EDI_INVT.description", "atom.DataSourceEnum.EDI_INVT.code", "", "", "");
    public static final DataSourceEnum EDI_TRNPT = new DataSourceEnum("EDI_TRNPT", "atom.DataSourceEnum.EDI_TRNPT.description", "atom.DataSourceEnum.EDI_TRNPT.code", "", "", "");
    public static final DataSourceEnum EDI_ACK = new DataSourceEnum("EDI_ACK", "atom.DataSourceEnum.EDI_ACK.description", "atom.DataSourceEnum.EDI_ACK.code", "", "", "");
    public static final DataSourceEnum EDI_RLORD = new DataSourceEnum("EDI_RLORD", "atom.DataSourceEnum.EDI_RLORD.description", "atom.DataSourceEnum.EDI_RLORD.code", "", "", "");
    public static final DataSourceEnum EDI_RLWAYBILL = new DataSourceEnum("EDI_RLWAYBILL", "atom.DataSourceEnum.EDI_RLWAYBILL.description", "atom.DataSourceEnum.EDI_RLWAYBILL.code", "", "", "");
    public static final DataSourceEnum EDI_SNX = new DataSourceEnum("EDI_SNX", "atom.DataSourceEnum.EDI_SNX.description", "atom.DataSourceEnum.EDI_SNX.code", "", "", "");
    public static final DataSourceEnum EDI_SAUDILDP = new DataSourceEnum("EDI_SAUDILDP", "atom.DataSourceEnum.EDI_SAUDILDP.description", "atom.DataSourceEnum.EDI_SAUDILDP.code", "", "", "");
    public static final DataSourceEnum EDI_MCPVESSELVISIT = new DataSourceEnum("EDI_MCPVESSELVISIT", "atom.DataSourceEnum.EDI_MCPVESSELVISIT.description", "atom.DataSourceEnum.EDI_MCPVESSELVISIT.code", "", "", "");
    public static final DataSourceEnum EDI_VERMAS = new DataSourceEnum("EDI_VERMAS", "atom.DataSourceEnum.EDI_VERMAS.description", "atom.DataSourceEnum.EDI_VERMAS.code", "", "", "");
    public static final DataSourceEnum REFCON = new DataSourceEnum("REFCON", "atom.DataSourceEnum.REFCON.description", "atom.DataSourceEnum.REFCON.code", "", "", "");
    public static final DataSourceEnum XPS = new DataSourceEnum("XPS", "atom.DataSourceEnum.XPS.description", "atom.DataSourceEnum.XPS.code", "", "", "");
    public static final DataSourceEnum EQ_SERIAL_RANGE = new DataSourceEnum("EQ_SERIAL_RANGE", "atom.DataSourceEnum.EQ_SERIAL_RANGE.description", "atom.DataSourceEnum.EQ_SERIAL_RANGE.code", "", "", "");
    public static final DataSourceEnum PURGE_JOB = new DataSourceEnum("PURGE_JOB", "atom.DataSourceEnum.PURGE_JOB.description", "atom.DataSourceEnum.PURGE_JOB.code", "", "", "");
    public static final DataSourceEnum EDI_SAUDIMANIFEST = new DataSourceEnum("EDI_SAUDIMANIFEST", "atom.DataSourceEnum.EDI_SAUDIMANIFEST.description", "atom.DataSourceEnum.EDI_SAUDIMANIFEST.code", "", "", "");
    public static final DataSourceEnum UI_LOADLIST = new DataSourceEnum("UI_LOADLIST", "atom.DataSourceEnum.UI_LOADLIST.description", "atom.DataSourceEnum.UI_LOADLIST.code", "", "", "");
    public static final DataSourceEnum UI_DISCHARGELIST = new DataSourceEnum("UI_DISCHARGELIST", "atom.DataSourceEnum.UI_DISCHARGELIST.description", "atom.DataSourceEnum.UI_DISCHARGELIST.code", "", "", "");
    public static final DataSourceEnum GATE_CLERK = new DataSourceEnum("GATE_CLERK", "atom.DataSourceEnum.GATE_CLERK.description", "atom.DataSourceEnum.GATE_CLERK.code", "", "", "");
    public static final DataSourceEnum AUTO_GATE = new DataSourceEnum("AUTO_GATE", "atom.DataSourceEnum.AUTO_GATE.description", "atom.DataSourceEnum.AUTO_GATE.code", "", "", "");
    public static final DataSourceEnum AUTO_ASSIGNED = new DataSourceEnum("AUTO_ASSIGNED", "atom.DataSourceEnum.AUTO_ASSIGNED.description", "atom.DataSourceEnum.AUTO_ASSIGNED.code", "", "", "");
    public static final DataSourceEnum LANE_FIX = new DataSourceEnum("LANE_FIX", "atom.DataSourceEnum.LANE_FIX.description", "atom.DataSourceEnum.LANE_FIX.code", "", "", "");
    public static final DataSourceEnum AUTOMATION = new DataSourceEnum("AUTOMATION", "atom.DataSourceEnum.AUTOMATION.description", "atom.DataSourceEnum.AUTOMATION.code", "", "", "");
    public static final DataSourceEnum CRANE_AUTOMATION_SYSTEM = new DataSourceEnum("CRANE_AUTOMATION_SYSTEM", "atom.DataSourceEnum.CRANE_AUTOMATION_SYSTEM.description", "atom.DataSourceEnum.CRANE_AUTOMATION_SYSTEM.code", "", "", "");

    public static DataSourceEnum getEnum(String inName) {
        return (DataSourceEnum) DataSourceEnum.getEnum(DataSourceEnum.class, (String)inName);
    }

    public static Map getEnumMap() {
        return DataSourceEnum.getEnumMap(DataSourceEnum.class);
    }

    public static List getEnumList() {
        return DataSourceEnum.getEnumList(DataSourceEnum.class);
    }

    public static Collection getList() {
        return DataSourceEnum.getEnumList(DataSourceEnum.class);
    }

    public static Iterator iterator() {
        return DataSourceEnum.iterator(DataSourceEnum.class);
    }

    protected DataSourceEnum(String inKey, String inDescriptionResKey, String inCodeResKey, String inBackgroundColor, String inForegroundColor, String inIconIdPath) {
        super(inKey, inDescriptionResKey, inCodeResKey, inBackgroundColor, inForegroundColor, inIconIdPath);
    }

    public String getMappingClassName() {
        return "com.navis.argo.persistence.atoms.UserTypeDataSourceEnum";
    }
}
