package com.zpmc.ztos.infra.base.business.enums.argo;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

//public enum BizRoleEnum {
//    LINEOP(1, "LINEOP", "line operator", "", "LINEOP"),
//    VESSELOP(2, "VESSELOP", "vessel operator", "", "VESSELOP"),
//    RAILROAD(3, "RAILROAD", "rail and road", "", "RAILROAD"),
//    HAULIER(4, "HAULIER", "haulier", "", "HAULIER"),
//    SHIPPER(5, "SHIPPER", "shipper", "", "SHIPPER"),
//    AGENT(6, "AGENT", "agent", "", "AGENT"),
//    MISC(7, "MISC", "", "", "MISC"),
//    STEVEDORE(8, "STEVEDORE", "stevedore", "", "STEVEDORE"),
//    SURVEYOR(9, "SURVEYOR", "surveyor", "", "SURVEYOR"),
//    DCOP(10, "DCOP", "", "", "DCOP"),
//    DEPOT(11, "DEPOT", "depot management", "", "DEPOT"),
//    CUSTOMS(12, "CUSTOMS", "customs", "", "CUSTOMS"),
//    GOVAGENCY(13, "GOVAGENCY", "", "", "GOVAGENCY"),
//    FRGTHFWDER(14, "FRGTHFWDER", "", "", "FRGTHFWDER"),
//    LEASINGCO(15, "LEASINGCO", "leasing company", "", "LEASINGCO"),
//    TERMINAL(16, "TERMINAL", "terminal", "", "TERMINAL");
//    private final int key;
//    private final String code;
//    private final String desc;
//    private final String color;
//    private final String displayName;
//
//    BizRoleEnum(int key, String code, String desc, String color, String displayName) {
//        this.key = key;
//        this.code = code;
//        this.desc = desc;
//        this.color = color;
//        this.displayName = displayName;
//    }
//
//    public static List getEnumList() {
//        return EnumUtils.getEnumList(BizRoleEnum.class);
//    }
//
//    public static EnumSet getEnumSet() {
//        return EnumSet.allOf(BizRoleEnum.class);
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

public class BizRoleEnum
        extends SystemBizRoleEnum {
    public static final BizRoleEnum LINEOP = new BizRoleEnum("LINEOP", "atom.BizRoleEnum.LINEOP.description", "atom.BizRoleEnum.LINEOP.code", "", "", "", true);
    public static final BizRoleEnum VESSELOP = new BizRoleEnum("VESSELOP", "atom.BizRoleEnum.VESSELOP.description", "atom.BizRoleEnum.VESSELOP.code", "", "", "", true);
    public static final BizRoleEnum RAILROAD = new BizRoleEnum("RAILROAD", "atom.BizRoleEnum.RAILROAD.description", "atom.BizRoleEnum.RAILROAD.code", "", "", "", true);
    public static final BizRoleEnum HAULIER = new BizRoleEnum("HAULIER", "atom.BizRoleEnum.HAULIER.description", "atom.BizRoleEnum.HAULIER.code", "", "", "", true);
    public static final BizRoleEnum SHIPPER = new BizRoleEnum("SHIPPER", "atom.BizRoleEnum.SHIPPER.description", "atom.BizRoleEnum.SHIPPER.code", "", "", "", true);
    public static final BizRoleEnum AGENT = new BizRoleEnum("AGENT", "atom.BizRoleEnum.AGENT.description", "atom.BizRoleEnum.AGENT.code", "", "", "", true);
    public static final BizRoleEnum MISC = new BizRoleEnum("MISC", "atom.BizRoleEnum.MISC.description", "atom.BizRoleEnum.MISC.code", "", "", "", false);
    public static final BizRoleEnum STEVEDORE = new BizRoleEnum("STEVEDORE", "atom.BizRoleEnum.STEVEDORE.description", "atom.BizRoleEnum.STEVEDORE.code", "", "", "", false);
    public static final BizRoleEnum SURVEYOR = new BizRoleEnum("SURVEYOR", "atom.BizRoleEnum.SURVEYOR.description", "atom.BizRoleEnum.SURVEYOR.code", "", "", "", false);
    public static final BizRoleEnum DCOP = new BizRoleEnum("DCOP", "atom.BizRoleEnum.DCOP.description", "atom.BizRoleEnum.DCOP.code", "", "", "", false);
    public static final BizRoleEnum DEPOT = new BizRoleEnum("DEPOT", "atom.BizRoleEnum.DEPOT.description", "atom.BizRoleEnum.DEPOT.code", "", "", "", false);
    public static final BizRoleEnum CUSTOMS = new BizRoleEnum("CUSTOMS", "atom.BizRoleEnum.CUSTOMS.description", "atom.BizRoleEnum.CUSTOMS.code", "", "", "", false);
    public static final BizRoleEnum GOVAGENCY = new BizRoleEnum("GOVAGENCY", "atom.BizRoleEnum.GOVAGENCY.description", "atom.BizRoleEnum.GOVAGENCY.code", "", "", "", false);
    public static final BizRoleEnum FRGTHFWDER = new BizRoleEnum("FRGTHFWDER", "atom.BizRoleEnum.FRGTHFWDER.description", "atom.BizRoleEnum.FRGTHFWDER.code", "", "", "", false);
    public static final BizRoleEnum LEASINGCO = new BizRoleEnum("LEASINGCO", "atom.BizRoleEnum.LEASINGCO.description", "atom.BizRoleEnum.LEASINGCO.code", "", "", "", false);
    public static final BizRoleEnum TERMINAL = new BizRoleEnum("TERMINAL", "atom.BizRoleEnum.TERMINAL.description", "atom.BizRoleEnum.TERMINAL.code", "", "", "", false);

    public static BizRoleEnum getEnum(String inName) {
        return (BizRoleEnum) BizRoleEnum.getEnum(BizRoleEnum.class, (String)inName);
    }

    public static Map getEnumMap() {
        return BizRoleEnum.getEnumMap(BizRoleEnum.class);
    }

    public static List getEnumList() {
        return BizRoleEnum.getEnumList(BizRoleEnum.class);
    }

    public static Collection getList() {
        return BizRoleEnum.getEnumList(BizRoleEnum.class);
    }

    public static Iterator iterator() {
        return BizRoleEnum.iterator(BizRoleEnum.class);
    }

    protected BizRoleEnum(String inKey, String inDescriptionResKey, String inCodeResKey, String inBackgroundColor, String inForegroundColor, String inIconIdPath, boolean hasCustomUI) {
        super(inKey, inDescriptionResKey, inCodeResKey, inBackgroundColor, inForegroundColor, inIconIdPath, hasCustomUI);
    }

    public String getMappingClassName() {
        return "com.navis.argo.persistence.atoms.UserTypeBizRoleEnum";
    }
}

