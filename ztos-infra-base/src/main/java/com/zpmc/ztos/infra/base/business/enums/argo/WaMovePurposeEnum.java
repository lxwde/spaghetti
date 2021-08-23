package com.zpmc.ztos.infra.base.business.enums.argo;

import com.zpmc.ztos.infra.base.common.enums.AtomizedEnum;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

//public enum WaMovePurposeEnum {
//    UNKNOWN(1, "UNKNOWN", "", "", "UNKNOWN"),
//    TRANSFER_IN(2, "TRANSFER_IN", "", "", "TRANSFER_IN"),
//    TRANSFER_OUT(3, "TRANSFER_OUT", "", "", "TRANSFER_OUT"),
//    INTRASTACK(4, "INTRASTACK", "", "", "INTRASTACK"),
//    SPLIT_TRANSFER_IN(5, "SPLIT_TRANSFER_IN", "", "", "SPLIT_TRANSFER_IN"),
//    SPLIT_TRANSFER_OUT(6, "SPLIT_TRANSFER_OUT", "", "", "SPLIT_TRANSFER_OUT"),
//    SPLIT_INTRASTACK(7, "SPLIT_INTRASTACK", "", "", "SPLIT_INTRASTACK"),
//    REHANDLE(8, "REHANDLE", "", "", "REHANDLE"),
//    PREPOSITION(9, "PREPOSITION", "", "", "PREPOSITION"),
//    HOUSEKEEPING(10, "HOUSEKEEPING", "", "", "HOUSEKEEPING"),
//    ITV_RECEIVE(11, "ITV_RECEIVE", "", "", "ITV_RECEIVE"),
//    ITV_DELIVER(12, "ITV_DELIVER", "", "", "ITV_DELIVER"),
//    ITV_REPOSITION(13, "ITV_REPOSITION", "", "", "ITV_REPOSITION"),
//    QC_MOVE(14, "QC_MOVE", "", "", "QC_MOVE"),
//    ASC_UNORDERED(15, "ASC_UNORDERED", "", "", "ASC_UNORDERED"),
//    LADEN_REDIRECT(16, "LADEN_REDIRECT", "", "", "LADEN_REDIRECT"),
//    ITV_SERVICING(17, "ITV_SERVICING", "", "", "ITV_SERVICING"),
//    CHE_REPOSITION(18, "CHE_REPOSITION", "", "", "CHE_REPOSITION"),
//    AHT_UNORDERED(19, "AHT_UNORDERED", "", "", "AHT_UNORDERED");
//    private final int key;
//    private final String code;
//    private final String desc;
//    private final String color;
//    private final String displayName;
//
//    WaMovePurposeEnum(int key, String code, String desc, String color, String displayName) {
//        this.key = key;
//        this.code = code;
//        this.desc = desc;
//        this.color = color;
//        this.displayName = displayName;
//    }
//
//    public static List getEnumList() {
//        return EnumUtils.getEnumList(WaMovePurposeEnum.class);
//    }
//
//    public static EnumSet getEnumSet() {
//        return EnumSet.allOf(WaMovePurposeEnum.class);
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

public class WaMovePurposeEnum
        extends AtomizedEnum {
    public static final WaMovePurposeEnum UNKNOWN = new WaMovePurposeEnum("UNKNOWN", "atom.WaMovePurposeEnum.UNKNOWN.description", "atom.WaMovePurposeEnum.UNKNOWN.code", "", "", "");
    public static final WaMovePurposeEnum TRANSFER_IN = new WaMovePurposeEnum("TRANSFER_IN", "atom.WaMovePurposeEnum.TRANSFER_IN.description", "atom.WaMovePurposeEnum.TRANSFER_IN.code", "", "", "");
    public static final WaMovePurposeEnum TRANSFER_OUT = new WaMovePurposeEnum("TRANSFER_OUT", "atom.WaMovePurposeEnum.TRANSFER_OUT.description", "atom.WaMovePurposeEnum.TRANSFER_OUT.code", "", "", "");
    public static final WaMovePurposeEnum INTRASTACK = new WaMovePurposeEnum("INTRASTACK", "atom.WaMovePurposeEnum.INTRASTACK.description", "atom.WaMovePurposeEnum.INTRASTACK.code", "", "", "");
    public static final WaMovePurposeEnum SPLIT_TRANSFER_IN = new WaMovePurposeEnum("SPLIT_TRANSFER_IN", "atom.WaMovePurposeEnum.SPLIT_TRANSFER_IN.description", "atom.WaMovePurposeEnum.SPLIT_TRANSFER_IN.code", "", "", "");
    public static final WaMovePurposeEnum SPLIT_TRANSFER_OUT = new WaMovePurposeEnum("SPLIT_TRANSFER_OUT", "atom.WaMovePurposeEnum.SPLIT_TRANSFER_OUT.description", "atom.WaMovePurposeEnum.SPLIT_TRANSFER_OUT.code", "", "", "");
    public static final WaMovePurposeEnum SPLIT_INTRASTACK = new WaMovePurposeEnum("SPLIT_INTRASTACK", "atom.WaMovePurposeEnum.SPLIT_INTRASTACK.description", "atom.WaMovePurposeEnum.SPLIT_INTRASTACK.code", "", "", "");
    public static final WaMovePurposeEnum REHANDLE = new WaMovePurposeEnum("REHANDLE", "atom.WaMovePurposeEnum.REHANDLE.description", "atom.WaMovePurposeEnum.REHANDLE.code", "", "", "");
    public static final WaMovePurposeEnum PREPOSITION = new WaMovePurposeEnum("PREPOSITION", "atom.WaMovePurposeEnum.PREPOSITION.description", "atom.WaMovePurposeEnum.PREPOSITION.code", "", "", "");
    public static final WaMovePurposeEnum HOUSEKEEPING = new WaMovePurposeEnum("HOUSEKEEPING", "atom.WaMovePurposeEnum.HOUSEKEEPING.description", "atom.WaMovePurposeEnum.HOUSEKEEPING.code", "", "", "");
    public static final WaMovePurposeEnum ITV_RECEIVE = new WaMovePurposeEnum("ITV_RECEIVE", "atom.WaMovePurposeEnum.ITV_RECEIVE.description", "atom.WaMovePurposeEnum.ITV_RECEIVE.code", "", "", "");
    public static final WaMovePurposeEnum ITV_DELIVER = new WaMovePurposeEnum("ITV_DELIVER", "atom.WaMovePurposeEnum.ITV_DELIVER.description", "atom.WaMovePurposeEnum.ITV_DELIVER.code", "", "", "");
    public static final WaMovePurposeEnum ITV_REPOSITION = new WaMovePurposeEnum("ITV_REPOSITION", "atom.WaMovePurposeEnum.ITV_REPOSITION.description", "atom.WaMovePurposeEnum.ITV_REPOSITION.code", "", "", "");
    public static final WaMovePurposeEnum QC_MOVE = new WaMovePurposeEnum("QC_MOVE", "atom.WaMovePurposeEnum.QC_MOVE.description", "atom.WaMovePurposeEnum.QC_MOVE.code", "", "", "");
    public static final WaMovePurposeEnum ASC_UNORDERED = new WaMovePurposeEnum("ASC_UNORDERED", "atom.WaMovePurposeEnum.ASC_UNORDERED.description", "atom.WaMovePurposeEnum.ASC_UNORDERED.code", "", "", "");
    public static final WaMovePurposeEnum LADEN_REDIRECT = new WaMovePurposeEnum("LADEN_REDIRECT", "atom.WaMovePurposeEnum.LADEN_REDIRECT.description", "atom.WaMovePurposeEnum.LADEN_REDIRECT.code", "", "", "");
    public static final WaMovePurposeEnum ITV_SERVICING = new WaMovePurposeEnum("ITV_SERVICING", "atom.WaMovePurposeEnum.ITV_SERVICING.description", "atom.WaMovePurposeEnum.ITV_SERVICING.code", "", "", "");
    public static final WaMovePurposeEnum CHE_REPOSITION = new WaMovePurposeEnum("CHE_REPOSITION", "atom.WaMovePurposeEnum.CHE_REPOSITION.description", "atom.WaMovePurposeEnum.CHE_REPOSITION.code", "", "", "");
    public static final WaMovePurposeEnum AHT_UNORDERED = new WaMovePurposeEnum("AHT_UNORDERED", "atom.WaMovePurposeEnum.AHT_UNORDERED.description", "atom.WaMovePurposeEnum.AHT_UNORDERED.code", "", "", "");

    public static WaMovePurposeEnum getEnum(String inName) {
        return (WaMovePurposeEnum) WaMovePurposeEnum.getEnum(WaMovePurposeEnum.class, (String)inName);
    }

    public static Map getEnumMap() {
        return WaMovePurposeEnum.getEnumMap(WaMovePurposeEnum.class);
    }

    public static List getEnumList() {
        return WaMovePurposeEnum.getEnumList(WaMovePurposeEnum.class);
    }

    public static Collection getList() {
        return WaMovePurposeEnum.getEnumList(WaMovePurposeEnum.class);
    }

    public static Iterator iterator() {
        return WaMovePurposeEnum.iterator(WaMovePurposeEnum.class);
    }

    protected WaMovePurposeEnum(String inKey, String inDescriptionResKey, String inCodeResKey, String inBackgroundColor, String inForegroundColor, String inIconIdPath) {
        super(inKey, inDescriptionResKey, inCodeResKey, inBackgroundColor, inForegroundColor, inIconIdPath);
    }

    public String getMappingClassName() {
        return "com.navis.argo.persistence.atoms.UserTypeWaMovePurposeEnum";
    }
}
