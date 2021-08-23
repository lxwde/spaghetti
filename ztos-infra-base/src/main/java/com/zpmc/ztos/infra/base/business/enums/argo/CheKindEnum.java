package com.zpmc.ztos.infra.base.business.enums.argo;

import com.zpmc.ztos.infra.base.common.enums.AtomizedEnum;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

//public enum CheKindEnum {
//    UNKN(1, "UNKN", "", "", "UNKN"),
//    SC(2, "SC", "", "", "SC"),
//    RTG(3, "RTG", "", "", "RTG"),
//    ITV(4, "ITV", "", "", "ITV"),
//    QC(5, "QC", "", "", "QC"),
//    FLT(6, "FLT", "", "", "FLT"),
//    MAN(7, "MAN", "", "", "MAN"),
//    RMG(8, "RMG", "", "", "RMG"),
//    MTT(9, "MTT", "", "", "MTT"),
//    MTS(10, "MTS", "", "", "MTS"),
//    RST(11, "RST", "", "", "RST"),
//    AGV(12, "AGV", "", "", "AGV"),
//    TT(13, "TT", "", "", "TT"),
//    ASC(14, "ASC", "", "", "ASC"),
//    WSV(15, "WSV", "", "", "WSV"),
//    ASH(16, "ASH", "", "", "ASH"),
//    LASTPLACEHOLDER(17, "LASTPLACEHOLDER", "", "", "LASTPLACEHOLDER");
//    private final int key;
//    private final String code;
//    private final String desc;
//    private final String color;
//    private final String displayName;
//
//    CheKindEnum(int key, String code, String desc, String color, String displayName) {
//        this.key = key;
//        this.code = code;
//        this.desc = desc;
//        this.color = color;
//        this.displayName = displayName;
//    }
//
//    public static List getEnumList() {
//        return EnumUtils.getEnumList(CheKindEnum.class);
//    }
//
//    public static EnumSet getEnumSet() {
//        return EnumSet.allOf(CheKindEnum.class);
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

public class CheKindEnum
        extends AtomizedEnum {
    public static final CheKindEnum UNKN = new CheKindEnum("UNKN", "atom.CheKindEnum.UNKN.description", "atom.CheKindEnum.UNKN.code", "", "", "");
    public static final CheKindEnum SC = new CheKindEnum("SC", "atom.CheKindEnum.SC.description", "atom.CheKindEnum.SC.code", "", "", "");
    public static final CheKindEnum RTG = new CheKindEnum("RTG", "atom.CheKindEnum.RTG.description", "atom.CheKindEnum.RTG.code", "", "", "");
    public static final CheKindEnum ITV = new CheKindEnum("ITV", "atom.CheKindEnum.ITV.description", "atom.CheKindEnum.ITV.code", "", "", "");
    public static final CheKindEnum QC = new CheKindEnum("QC", "atom.CheKindEnum.QC.description", "atom.CheKindEnum.QC.code", "", "", "");
    public static final CheKindEnum FLT = new CheKindEnum("FLT", "atom.CheKindEnum.FLT.description", "atom.CheKindEnum.FLT.code", "", "", "");
    public static final CheKindEnum MAN = new CheKindEnum("MAN", "atom.CheKindEnum.MAN.description", "atom.CheKindEnum.MAN.code", "", "", "");
    public static final CheKindEnum RMG = new CheKindEnum("RMG", "atom.CheKindEnum.RMG.description", "atom.CheKindEnum.RMG.code", "", "", "");
    public static final CheKindEnum MTT = new CheKindEnum("MTT", "atom.CheKindEnum.MTT.description", "atom.CheKindEnum.MTT.code", "", "", "");
    public static final CheKindEnum MTS = new CheKindEnum("MTS", "atom.CheKindEnum.MTS.description", "atom.CheKindEnum.MTS.code", "", "", "");
    public static final CheKindEnum RST = new CheKindEnum("RST", "atom.CheKindEnum.RST.description", "atom.CheKindEnum.RST.code", "", "", "");
    public static final CheKindEnum AGV = new CheKindEnum("AGV", "atom.CheKindEnum.AGV.description", "atom.CheKindEnum.AGV.code", "", "", "");
    public static final CheKindEnum TT = new CheKindEnum("TT", "atom.CheKindEnum.TT.description", "atom.CheKindEnum.TT.code", "", "", "");
    public static final CheKindEnum ASC = new CheKindEnum("ASC", "atom.CheKindEnum.ASC.description", "atom.CheKindEnum.ASC.code", "", "", "");
    public static final CheKindEnum WSV = new CheKindEnum("WSV", "atom.CheKindEnum.WSV.description", "atom.CheKindEnum.WSV.code", "", "", "");
    public static final CheKindEnum ASH = new CheKindEnum("ASH", "atom.CheKindEnum.ASH.description", "atom.CheKindEnum.ASH.code", "", "", "");
    public static final CheKindEnum LASTPLACEHOLDER = new CheKindEnum("LASTPLACEHOLDER", "atom.CheKindEnum.LASTPLACEHOLDER.description", "atom.CheKindEnum.LASTPLACEHOLDER.code", "", "", "");

    public static CheKindEnum getEnum(String inName) {
        return (CheKindEnum) CheKindEnum.getEnum(CheKindEnum.class, (String)inName);
    }

    public static Map getEnumMap() {
        return CheKindEnum.getEnumMap(CheKindEnum.class);
    }

    public static List getEnumList() {
        return CheKindEnum.getEnumList(CheKindEnum.class);
    }

    public static Collection getList() {
        return CheKindEnum.getEnumList(CheKindEnum.class);
    }

    public static Iterator iterator() {
        return CheKindEnum.iterator(CheKindEnum.class);
    }

    protected CheKindEnum(String inKey, String inDescriptionResKey, String inCodeResKey, String inBackgroundColor, String inForegroundColor, String inIconIdPath) {
        super(inKey, inDescriptionResKey, inCodeResKey, inBackgroundColor, inForegroundColor, inIconIdPath);
    }

    public String getMappingClassName() {
        return "com.navis.argo.persistence.atoms.UserTypeCheKindEnum";
    }
}
