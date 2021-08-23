package com.zpmc.ztos.infra.base.business.enums.argo;

import com.zpmc.ztos.infra.base.business.enums.edi.AbstractEdiMessageClassEnum;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

//public enum EdiMessageClassEnum {
//    ACTIVITY(1, "ACTIVITY", "activity", "", "ACTIVITY"),
//    ACTIVITY_BY_CV(2, "ACTIVITY_BY_CV", "activity by cv", "", "ACTIVITY_BY_CV"),
//    ACKNOWLEDGEMENT(3, "ACKNOWLEDGEMENT", "acknoledgement", "", "ACKNOWLEDGEMENT"),
//    APPOINTMENT(4, "APPOINTMENT", "appointment", "", "APPOINTMENT"),
//    BOOKING(5, "BOOKING", "booking", "", "BOOKING"),
//    CREDIT(6, "CREDIT", "credit", "", "CREDIT"),
//    DISCHLIST(7, "DISCHLIST", "discharge list", "", "DISCHLIST"),
//    HAZARD(8, "HAZARD", "hazardous", "", "HAZARD"),
//    INVENTORY(9, "INVENTORY", "inventory", "", "INVENTORY"),
//    INVOICE(10, "INVOICE", "invoice", "", "INVOICE"),
//    LOADLIST(11, "LOADLIST", "load list", "", "LOADLIST"),
//    MANIFEST(12, "MANIFEST", "manifest", "", "MANIFEST"),
//    MCPVESSELVISIT(13, "MCPVESSELVISIT", "mcp vessel list", "", "MCPVESSELVISIT"),
//    PERFORMANCE(14, "PERFORMANCE", "performance", "", "PERFORMANCE"),
//    PREADVISE(15, "PREADVISE", "preadvise", "", "PREADVISE"),
//    RAILCONSIST(16, "RAILCONSIST", "rail consist", "", "RAILCONSIST"),
//    RAILORDER(17, "RAILORDER", "rail lorder", "", "RAILORDER"),
//    RAILWAYBILL(18, "RAILWAYBILL", "rail way bill", "", "RAILWAYBILL"),
//    RELEASE(19, "RELEASE", "release", "", "RELEASE"),
//    SAUDILDP(20, "SAUDILDP", "saudi ldp", "", "SAUDILDP"),
//    SAUDIMANIFEST(21, "SAUDIMANIFEST", "saudi manifest", "", "SAUDIMANIFEST"),
//    STOWPLAN(22, "STOWPLAN", "stow plan", "", "STOWPLAN"),
//    SNX(23, "SNX", "snx", "", "SNX"),
//    VESSELACTIVITY(24, "VESSELACTIVITY", "vessel activity", "", "VESSELACTIVITY"),
//    VESSELACTIVITY_BY_CV(25, "VESSELACTIVITY_BY_CV", "vessel activity by cv", "", "VESSELACTIVITY_BY_CV"),
//    VERMAS(26, "VERMAS", "vermas", "", "VERMAS"),
//    UNKNOWN(27, "UNKNOWN", "unknown", "", "UNKNOWN");
//    private final int key;
//    private final String code;
//    private final String desc;
//    private final String color;
//    private final String displayName;
//
//    EdiMessageClassEnum(int key, String code, String desc, String color, String displayName) {
//        this.key = key;
//        this.code = code;
//        this.desc = desc;
//        this.color = color;
//        this.displayName = displayName;
//    }
//
//    public static List getEnumList() {
//        return EnumUtils.getEnumList(EdiMessageClassEnum.class);
//    }
//
//    public static EnumSet getEnumSet() {
//        return EnumSet.allOf(EdiMessageClassEnum.class);
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

public class EdiMessageClassEnum
        extends AbstractEdiMessageClassEnum {
    public static final EdiMessageClassEnum ACTIVITY = new EdiMessageClassEnum("ACTIVITY", "atom.EdiMessageClassEnum.ACTIVITY.description", "atom.EdiMessageClassEnum.ACTIVITY.code", "", "", "", "Activity", true, true, "com.navis.inventory.business.units.InventoryEvent", true);
    public static final EdiMessageClassEnum ACTIVITY_BY_CV = new EdiMessageClassEnum("ACTIVITY_BY_CV", "atom.EdiMessageClassEnum.ACTIVITY_BY_CV.description", "atom.EdiMessageClassEnum.ACTIVITY_BY_CV.code", "", "", "", "Activity By Carrier", false, true, "com.navis.inventory.business.units.InventoryEvent", false);
    public static final EdiMessageClassEnum ACKNOWLEDGEMENT = new EdiMessageClassEnum("ACKNOWLEDGEMENT", "atom.EdiMessageClassEnum.ACKNOWLEDGEMENT.description", "atom.EdiMessageClassEnum.ACKNOWLEDGEMENT.code", "", "", "", "Acknowledgement", true, true, "com.navis.edi.business.entity.EdiTransaction", true);
    public static final EdiMessageClassEnum APPOINTMENT = new EdiMessageClassEnum("APPOINTMENT", "atom.EdiMessageClassEnum.APPOINTMENT.description", "atom.EdiMessageClassEnum.APPOINTMENT.code", "", "", "", "Appointment", true, false, "", true);
    public static final EdiMessageClassEnum BOOKING = new EdiMessageClassEnum("BOOKING", "atom.EdiMessageClassEnum.BOOKING.description", "atom.EdiMessageClassEnum.BOOKING.code", "", "", "", "Booking", true, false, "", true);
    public static final EdiMessageClassEnum CREDIT = new EdiMessageClassEnum("CREDIT", "atom.EdiMessageClassEnum.CREDIT.description", "atom.EdiMessageClassEnum.CREDIT.code", "", "", "", "Credit", false, true, "com.navis.billing.business.model.Credit", true);
    public static final EdiMessageClassEnum DISCHLIST = new EdiMessageClassEnum("DISCHLIST", "atom.EdiMessageClassEnum.DISCHLIST.description", "atom.EdiMessageClassEnum.DISCHLIST.code", "", "", "", "Discharge List", false, true, "com.navis.inventory.business.units.UnitFacilityVisit", false);
    public static final EdiMessageClassEnum HAZARD = new EdiMessageClassEnum("HAZARD", "atom.EdiMessageClassEnum.HAZARD.description", "atom.EdiMessageClassEnum.HAZARD.code", "", "", "", "Hazard", true, false, "", true);
    public static final EdiMessageClassEnum INVENTORY = new EdiMessageClassEnum("INVENTORY", "atom.EdiMessageClassEnum.INVENTORY.description", "atom.EdiMessageClassEnum.INVENTORY.code", "", "", "", "Inventory", false, true, "com.navis.inventory.business.units.UnitYardVisit", true);
    public static final EdiMessageClassEnum INVOICE = new EdiMessageClassEnum("INVOICE", "atom.EdiMessageClassEnum.INVOICE.description", "atom.EdiMessageClassEnum.INVOICE.code", "", "", "", "Invoice", false, true, "com.navis.billing.business.model.Invoice", true);
    public static final EdiMessageClassEnum LOADLIST = new EdiMessageClassEnum("LOADLIST", "atom.EdiMessageClassEnum.LOADLIST.description", "atom.EdiMessageClassEnum.LOADLIST.code", "", "", "", "Load List", false, true, "com.navis.inventory.business.units.UnitFacilityVisit", false);
    public static final EdiMessageClassEnum MANIFEST = new EdiMessageClassEnum("MANIFEST", "atom.EdiMessageClassEnum.MANIFEST.description", "atom.EdiMessageClassEnum.MANIFEST.code", "", "", "", "Manifest", true, true, "com.navis.cargo.business.model.BillOfLading", true);
    public static final EdiMessageClassEnum MCPVESSELVISIT = new EdiMessageClassEnum("MCPVESSELVISIT", "atom.EdiMessageClassEnum.MCPVESSELVISIT.description", "atom.EdiMessageClassEnum.MCPVESSELVISIT.code", "", "", "", "Mcp Vessel Visit", true, false, "", true);
    public static final EdiMessageClassEnum PERFORMANCE = new EdiMessageClassEnum("PERFORMANCE", "atom.EdiMessageClassEnum.PERFORMANCE.description", "atom.EdiMessageClassEnum.PERFORMANCE.code", "", "", "", "Vessel Stats", false, true, "", false);
    public static final EdiMessageClassEnum PREADVISE = new EdiMessageClassEnum("PREADVISE", "atom.EdiMessageClassEnum.PREADVISE.description", "atom.EdiMessageClassEnum.PREADVISE.code", "", "", "", "Preadvise", true, false, "", true);
    public static final EdiMessageClassEnum RAILCONSIST = new EdiMessageClassEnum("RAILCONSIST", "atom.EdiMessageClassEnum.RAILCONSIST.description", "atom.EdiMessageClassEnum.RAILCONSIST.code", "", "", "", "RailConsist", true, false, "com.navis.inventory.business.units.UnitFacilityVisit", true);
    public static final EdiMessageClassEnum RAILORDER = new EdiMessageClassEnum("RAILORDER", "atom.EdiMessageClassEnum.RAILORDER.description", "atom.EdiMessageClassEnum.RAILORDER.code", "", "", "", "RailOrder", true, false, "", true);
    public static final EdiMessageClassEnum RAILWAYBILL = new EdiMessageClassEnum("RAILWAYBILL", "atom.EdiMessageClassEnum.RAILWAYBILL.description", "atom.EdiMessageClassEnum.RAILWAYBILL.code", "", "", "", "RailWayBill", true, false, "", true);
    public static final EdiMessageClassEnum RELEASE = new EdiMessageClassEnum("RELEASE", "atom.EdiMessageClassEnum.RELEASE.description", "atom.EdiMessageClassEnum.RELEASE.code", "", "", "", "Release", true, false, "", true);
    public static final EdiMessageClassEnum SAUDILDP = new EdiMessageClassEnum("SAUDILDP", "atom.EdiMessageClassEnum.SAUDILDP.description", "atom.EdiMessageClassEnum.SAUDILDP.code", "", "", "", "Saudi Load Permit", true, false, "com.navis.inventory.business.units.UnitFacilityVisit", true);
    public static final EdiMessageClassEnum SAUDIMANIFEST = new EdiMessageClassEnum("SAUDIMANIFEST", "atom.EdiMessageClassEnum.SAUDIMANIFEST.description", "atom.EdiMessageClassEnum.SAUDIMANIFEST.code", "", "", "", "SAUDIMANIFEST", true, false, "", true);
    public static final EdiMessageClassEnum STOWPLAN = new EdiMessageClassEnum("STOWPLAN", "atom.EdiMessageClassEnum.STOWPLAN.description", "atom.EdiMessageClassEnum.STOWPLAN.code", "", "", "", "Stowplan", true, true, "com.navis.inventory.business.units.UnitFacilityVisit", false);
    public static final EdiMessageClassEnum SNX = new EdiMessageClassEnum("SNX", "atom.EdiMessageClassEnum.SNX.description", "atom.EdiMessageClassEnum.SNX.code", "", "", "", "SNX", true, false, "", true);
    public static final EdiMessageClassEnum VESSELACTIVITY = new EdiMessageClassEnum("VESSELACTIVITY", "atom.EdiMessageClassEnum.VESSELACTIVITY.description", "atom.EdiMessageClassEnum.VESSELACTIVITY.code", "", "", "", "Vessel Activity", false, true, "com.navis.apex.business.units.VesselEvent", true);
    public static final EdiMessageClassEnum VESSELACTIVITY_BY_CV = new EdiMessageClassEnum("VESSELACTIVITY_BY_CV", "atom.EdiMessageClassEnum.VESSELACTIVITY_BY_CV.description", "atom.EdiMessageClassEnum.VESSELACTIVITY_BY_CV.code", "", "", "", "Vessel Activity", false, true, "com.navis.apex.business.units.VesselEvent", false);
    public static final EdiMessageClassEnum VERMAS = new EdiMessageClassEnum("VERMAS", "atom.EdiMessageClassEnum.VERMAS.description", "atom.EdiMessageClassEnum.VERMAS.code", "", "", "", "VERMAS", true, false, "com.navis.inventory.business.units.UnitFacilityVisit", true);
    public static final EdiMessageClassEnum UNKNOWN = new EdiMessageClassEnum("UNKNOWN", "atom.EdiMessageClassEnum.UNKNOWN.description", "atom.EdiMessageClassEnum.UNKNOWN.code", "", "", "", "Unknown", false, false, "", false);

    public static EdiMessageClassEnum getEnum(String inName) {
        return (EdiMessageClassEnum) EdiMessageClassEnum.getEnum(EdiMessageClassEnum.class, (String)inName);
    }

    public static Map getEnumMap() {
        return EdiMessageClassEnum.getEnumMap(EdiMessageClassEnum.class);
    }

    public static List getEnumList() {
        return EdiMessageClassEnum.getEnumList(EdiMessageClassEnum.class);
    }

    public static Collection getList() {
        return EdiMessageClassEnum.getEnumList(EdiMessageClassEnum.class);
    }

    public static Iterator iterator() {
        return EdiMessageClassEnum.iterator(EdiMessageClassEnum.class);
    }

    protected EdiMessageClassEnum(String inKey, String inDescriptionResKey, String inCodeResKey, String inBackgroundColor, String inForegroundColor, String inIconIdPath, String inDescription, boolean inIsInbound, boolean inIsOutbound, String inDomainClass, boolean inIsSchedule) {
        super(inKey, inDescriptionResKey, inCodeResKey, inBackgroundColor, inForegroundColor, inIconIdPath, inDescription, inIsInbound, inIsOutbound, inDomainClass, inIsSchedule);
    }

    public String getMappingClassName() {
        return "com.navis.argo.persistence.atoms.UserTypeEdiMessageClassEnum";
    }
}

