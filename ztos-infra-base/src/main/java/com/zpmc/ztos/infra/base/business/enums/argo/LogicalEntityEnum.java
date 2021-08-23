package com.zpmc.ztos.infra.base.business.enums.argo;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

//public enum LogicalEntityEnum {
//    EQ(1, "EQ", "equipment", "", "EQ"),
//    UNIT(2, "UNIT", "unituipment", "", "UNIT"),
//    CTR(3, "CTR", "control", "", "CTR"),
//    CHS(4, "CHS", "che", "", "CHS"),
//    ACC(5, "ACC", "account", "", "ACC"),
//    GOODS(6, "GOODS", "goodsbase", "", "GOODS"),
//    VES(7, "VES", "vessel", "", "VES"),
//    VV(8, "VV", "vesselvisit", "", "VV"),
//    RV(9, "RV", "railvisit", "", "RV"),
//    TV(10, "TV", "truckvisit", "", "TV"),
//    RCARV(11, "RCARV", "rail car visit", "", "RCARV"),
//    BKG(12, "BKG", "booking", "", "BKG"),
//    DO(13, "DO", "delivery order", "", "DO"),
//    LO(14, "LO", "loadout order", "", "LO"),
//    RO(15, "RO", "rail order", "", "RO"),
//    ERO(16, "ERO", "equipment recvive order", "", "ERO"),
//    EDISESS(17, "EDISESS", "edi session", "", "EDISESS"),
//    YARD(18, "YARD", "yard", "", "YARD"),
//    BL(19, "BL", "bill of loading", "", "BL"),
//    SRVO(20, "SRVO", "service order", "", "SRVO"),
//    CVSO(21, "CVSO", "carrier visit service order", "", "CVSO"),
//    GAPPT(22, "GAPPT", "gate appointment", "", "GAPPT"),
//    TAPPT(23, "TAPPT", "truck visit appointment", "", "TAPPT"),
//    CRGSO(24, "CRGSO", "cargo service order", "", "CRGSO"),
//    NA(25, "NA", "na", "", "NA"),
//    CARGOLOT(26, "CARGOLOT", "cargo lot", "", "CARGOLOT"),
//    CV(27, "CV", "carrier visit", "", "CV"),
//    CHE(28, "CHE", "cje", "", "CHE"),
//    AHI(29, "AHI", "application health instrument", "", "AHI"),
//    MBEAN(30, "MBEAN", "Mbean health instrument", "", "MBEAN"),
//    LOGMSG(31, "LOGMSG", "log health instrument", "", "LOGMSG");
//    private final int key;
//    private final String code;
//    private final String desc;
//    private final String color;
//    private final String displayName;
//
//    LogicalEntityEnum(int key, String code, String desc, String color, String displayName) {
//        this.key = key;
//        this.code = code;
//        this.desc = desc;
//        this.color = color;
//        this.displayName = displayName;
//    }
//
//    public static List getEnumList() {
//        return EnumUtils.getEnumList(LogicalEntityEnum.class);
//    }
//
//    public static EnumSet getEnumSet() {
//        return EnumSet.allOf(LogicalEntityEnum.class);
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

public class LogicalEntityEnum
        extends AbstractLogicalEntityEnum {
    public static final LogicalEntityEnum EQ = new LogicalEntityEnum("EQ", "atom.LogicalEntityEnum.EQ.description", "atom.LogicalEntityEnum.EQ.code", "", "", "", "com.navis.inventory.business.units.EquipmentState");
    public static final LogicalEntityEnum UNIT = new LogicalEntityEnum("UNIT", "atom.LogicalEntityEnum.UNIT.description", "atom.LogicalEntityEnum.UNIT.code", "", "", "", "com.navis.inventory.business.units.Unit");
    public static final LogicalEntityEnum CTR = new LogicalEntityEnum("CTR", "atom.LogicalEntityEnum.CTR.description", "atom.LogicalEntityEnum.CTR.code", "", "", "", "com.navis.inventory.business.units.Unit");
    public static final LogicalEntityEnum CHS = new LogicalEntityEnum("CHS", "atom.LogicalEntityEnum.CHS.description", "atom.LogicalEntityEnum.CHS.code", "", "", "", "com.navis.inventory.business.units.Unit");
    public static final LogicalEntityEnum ACC = new LogicalEntityEnum("ACC", "atom.LogicalEntityEnum.ACC.description", "atom.LogicalEntityEnum.ACC.code", "", "", "", "com.navis.inventory.business.units.Unit");
    public static final LogicalEntityEnum GOODS = new LogicalEntityEnum("GOODS", "atom.LogicalEntityEnum.GOODS.description", "atom.LogicalEntityEnum.GOODS.code", "", "", "", "com.navis.inventory.business.units.GoodsBase");
    public static final LogicalEntityEnum VES = new LogicalEntityEnum("VES", "atom.LogicalEntityEnum.VES.description", "atom.LogicalEntityEnum.VES.code", "", "", "", "com.navis.vessel.business.operation.Vessel");
    public static final LogicalEntityEnum VV = new LogicalEntityEnum("VV", "atom.LogicalEntityEnum.VV.description", "atom.LogicalEntityEnum.VV.code", "", "", "", "com.navis.vessel.business.schedule.VesselVisitDetails");
    public static final LogicalEntityEnum RV = new LogicalEntityEnum("RV", "atom.LogicalEntityEnum.RV.description", "atom.LogicalEntityEnum.RV.code", "", "", "", "com.navis.rail.business.entity.TrainVisitDetails");
    public static final LogicalEntityEnum TV = new LogicalEntityEnum("TV", "atom.LogicalEntityEnum.TV.description", "atom.LogicalEntityEnum.TV.code", "", "", "", "com.navis.road.business.model.TruckVisitDetails");
    public static final LogicalEntityEnum RCARV = new LogicalEntityEnum("RCARV", "atom.LogicalEntityEnum.RCARV.description", "atom.LogicalEntityEnum.RCARV.code", "", "", "", "com.navis.rail.business.entity.RailcarVisit");
    public static final LogicalEntityEnum BKG = new LogicalEntityEnum("BKG", "atom.LogicalEntityEnum.BKG.description", "atom.LogicalEntityEnum.BKG.code", "", "", "", "com.navis.orders.business.eqorders.Booking");
    public static final LogicalEntityEnum DO = new LogicalEntityEnum("DO", "atom.LogicalEntityEnum.DO.description", "atom.LogicalEntityEnum.DO.code", "", "", "", "com.navis.orders.business.eqorders.EquipmentDeliveryOrder");
    public static final LogicalEntityEnum LO = new LogicalEntityEnum("LO", "atom.LogicalEntityEnum.LO.description", "atom.LogicalEntityEnum.LO.code", "", "", "", "com.navis.orders.business.eqorders.EquipmentLoadoutOrder");
    public static final LogicalEntityEnum RO = new LogicalEntityEnum("RO", "atom.LogicalEntityEnum.RO.description", "atom.LogicalEntityEnum.RO.code", "", "", "", "com.navis.orders.business.eqorders.RailOrder");
    public static final LogicalEntityEnum ERO = new LogicalEntityEnum("ERO", "atom.LogicalEntityEnum.ERO.description", "atom.LogicalEntityEnum.ERO.code", "", "", "", "com.navis.orders.business.eqorders.EquipmentReceiveOrder");
    public static final LogicalEntityEnum EDISESS = new LogicalEntityEnum("EDISESS", "atom.LogicalEntityEnum.EDISESS.description", "atom.LogicalEntityEnum.EDISESS.code", "", "", "", "com.navis.edi.business.entity.EdiSession");
    public static final LogicalEntityEnum YARD = new LogicalEntityEnum("YARD", "atom.LogicalEntityEnum.YARD.description", "atom.LogicalEntityEnum.YARD.code", "", "", "", "com.navis.argo.business.model.Yard");
    public static final LogicalEntityEnum BL = new LogicalEntityEnum("BL", "atom.LogicalEntityEnum.BL.description", "atom.LogicalEntityEnum.BL.code", "", "", "", "com.navis.cargo.business.model.BillOfLading");
    public static final LogicalEntityEnum SRVO = new LogicalEntityEnum("SRVO", "atom.LogicalEntityEnum.SRVO.description", "atom.LogicalEntityEnum.SRVO.code", "", "", "", "com.navis.orders.business.serviceorders.ServiceOrder");
    public static final LogicalEntityEnum CVSO = new LogicalEntityEnum("CVSO", "atom.LogicalEntityEnum.CVSO.description", "atom.LogicalEntityEnum.CVSO.code", "", "", "", "com.navis.orders.business.serviceorders.CarrierVisitServiceOrder");
    public static final LogicalEntityEnum GAPPT = new LogicalEntityEnum("GAPPT", "atom.LogicalEntityEnum.GAPPT.description", "atom.LogicalEntityEnum.GAPPT.code", "", "", "", "com.navis.road.business.appointment.model.GateAppointment");
    public static final LogicalEntityEnum TAPPT = new LogicalEntityEnum("TAPPT", "atom.LogicalEntityEnum.TAPPT.description", "atom.LogicalEntityEnum.TAPPT.code", "", "", "", "com.navis.road.business.appointment.model.TruckVisitAppointment");
    public static final LogicalEntityEnum CRGSO = new LogicalEntityEnum("CRGSO", "atom.LogicalEntityEnum.CRGSO.description", "atom.LogicalEntityEnum.CRGSO.code", "", "", "", "com.navis.orders.business.serviceorders.CargoServiceOrder");
    public static final LogicalEntityEnum NA = new LogicalEntityEnum("NA", "atom.LogicalEntityEnum.NA.description", "atom.LogicalEntityEnum.NA.code", "", "", "", "");
    public static final LogicalEntityEnum CARGOLOT = new LogicalEntityEnum("CARGOLOT", "atom.LogicalEntityEnum.CARGOLOT.description", "atom.LogicalEntityEnum.CARGOLOT.code", "", "", "", "com.navis.cargo.business.model.CargoLot");
    public static final LogicalEntityEnum CV = new LogicalEntityEnum("CV", "atom.LogicalEntityEnum.CV.description", "atom.LogicalEntityEnum.CV.code", "", "", "", "com.navis.argo.business.model.CarrierVisit");
    public static final LogicalEntityEnum CHE = new LogicalEntityEnum("CHE", "atom.LogicalEntityEnum.CHE.description", "atom.LogicalEntityEnum.CHE.code", "", "", "", "com.navis.argo.business.xps.model.Che");
    public static final LogicalEntityEnum AHI = new LogicalEntityEnum("AHI", "atom.LogicalEntityEnum.AHI.description", "atom.LogicalEntityEnum.AHI.code", "", "", "", "com.navis.services.business.event.ApplicationHealthInstrument");
    public static final LogicalEntityEnum MBEAN = new LogicalEntityEnum("MBEAN", "atom.LogicalEntityEnum.MBEAN.description", "atom.LogicalEntityEnum.MBEAN.code", "", "", "", "com.navis.services.business.event.MBeanHealthInstrument");
    public static final LogicalEntityEnum LOGMSG = new LogicalEntityEnum("LOGMSG", "atom.LogicalEntityEnum.LOGMSG.description", "atom.LogicalEntityEnum.LOGMSG.code", "", "", "", "com.navis.services.business.event.LogHealthInstrument");

    public static LogicalEntityEnum getEnum(String inName) {
        return (LogicalEntityEnum) LogicalEntityEnum.getEnum(LogicalEntityEnum.class, (String)inName);
    }

    public static Map getEnumMap() {
        return LogicalEntityEnum.getEnumMap(LogicalEntityEnum.class);
    }

    public static List getEnumList() {
        return LogicalEntityEnum.getEnumList(LogicalEntityEnum.class);
    }

    public static Collection getList() {
        return LogicalEntityEnum.getEnumList(LogicalEntityEnum.class);
    }

    public static Iterator iterator() {
        return LogicalEntityEnum.iterator(LogicalEntityEnum.class);
    }

    protected LogicalEntityEnum(String inKey, String inDescriptionResKey, String inCodeResKey, String inBackgroundColor, String inForegroundColor, String inIconIdPath, String inDomainClass) {
        super(inKey, inDescriptionResKey, inCodeResKey, inBackgroundColor, inForegroundColor, inIconIdPath, inDomainClass);
    }

    public String getMappingClassName() {
        return "com.navis.argo.persistence.atoms.UserTypeLogicalEntityEnum";
    }
}
