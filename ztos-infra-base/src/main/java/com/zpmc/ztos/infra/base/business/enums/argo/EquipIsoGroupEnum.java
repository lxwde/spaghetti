package com.zpmc.ztos.infra.base.business.enums.argo;

import com.zpmc.ztos.infra.base.common.enums.AtomizedEnum;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

//public enum EquipIsoGroupEnum {
//    GP(1, "GP", "GP", "", "GP"),
//    VH(2, "VH", "VH", "", "VH"),
//    BU(3, "BU", "BU", "", "BU"),
//    BK(4, "BK", "BK", "", "BK"),
//    SN(5, "SN", "SN", "", "SN"),
//    RE(6, "RE", "RE", "", "RE"),
//    RT(7, "RT", "RT", "", "RT"),
//    RS(8, "RS", "RS", "", "RS"),
//    RC(9, "RC", "RC", "", "RC"),
//    HR(10, "HR", "HR", "", "HR"),
//    HI(11, "HI", "HI", "", "HI"),
//    UT(12, "UT", "UT", "", "UT"),
//    UP(13, "UP", "UP", "", "UP"),
//    PL(14, "PL", "PL", "", "PL"),
//    PF(15, "PF", "PF", "", "PF"),
//    PC(16, "PC", "PC", "", "PC"),
//    PS(17, "PS", "PS", "", "PS"),
//    PW(18, "PW", "PW", "", "PW"),
//    TN(19, "TN", "TN", "", "TN"),
//    TD(20, "TD", "TD", "", "TD"),
//    TG(21, "TG", "TG", "", "TG"),
//    AS(22, "AS", "AS", "", "AS"),
//    NA(23, "NA", "NA", "", "NA"),
//    CH(24, "CH", "CH", "", "CH"),
//    HH(25, "HH", "HH", "", "HH"),
//    GS(26, "GS", "GS", "", "GS"),
//    GU(27, "GU", "GU", "", "GU"),
//    CU(28, "CU", "CU", "", "CU"),
//    CR(29, "CR", "CR", "", "CR");
//    private final int key;
//    private final String code;
//    private final String desc;
//    private final String color;
//    private final String displayName;
//
//    EquipIsoGroupEnum(int key, String code, String desc, String color, String displayName) {
//        this.key = key;
//        this.code = code;
//        this.desc = desc;
//        this.color = color;
//        this.displayName = displayName;
//    }
//
//    public static List getEnumList() {
//        return EnumUtils.getEnumList(EquipIsoGroupEnum.class);
//    }
//
//    public static EnumSet getEnumSet() {
//        return EnumSet.allOf(EquipIsoGroupEnum.class);
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

public class EquipIsoGroupEnum
        extends AtomizedEnum {
    public static final EquipIsoGroupEnum GP = new EquipIsoGroupEnum("GP", "atom.EquipIsoGroupEnum.GP.description", "atom.EquipIsoGroupEnum.GP.code", "", "", "");
    public static final EquipIsoGroupEnum VH = new EquipIsoGroupEnum("VH", "atom.EquipIsoGroupEnum.VH.description", "atom.EquipIsoGroupEnum.VH.code", "", "", "");
    public static final EquipIsoGroupEnum BU = new EquipIsoGroupEnum("BU", "atom.EquipIsoGroupEnum.BU.description", "atom.EquipIsoGroupEnum.BU.code", "", "", "");
    public static final EquipIsoGroupEnum BK = new EquipIsoGroupEnum("BK", "atom.EquipIsoGroupEnum.BK.description", "atom.EquipIsoGroupEnum.BK.code", "", "", "");
    public static final EquipIsoGroupEnum SN = new EquipIsoGroupEnum("SN", "atom.EquipIsoGroupEnum.SN.description", "atom.EquipIsoGroupEnum.SN.code", "", "", "");
    public static final EquipIsoGroupEnum RE = new EquipIsoGroupEnum("RE", "atom.EquipIsoGroupEnum.RE.description", "atom.EquipIsoGroupEnum.RE.code", "", "", "");
    public static final EquipIsoGroupEnum RT = new EquipIsoGroupEnum("RT", "atom.EquipIsoGroupEnum.RT.description", "atom.EquipIsoGroupEnum.RT.code", "", "", "");
    public static final EquipIsoGroupEnum RS = new EquipIsoGroupEnum("RS", "atom.EquipIsoGroupEnum.RS.description", "atom.EquipIsoGroupEnum.RS.code", "", "", "");
    public static final EquipIsoGroupEnum RC = new EquipIsoGroupEnum("RC", "atom.EquipIsoGroupEnum.RC.description", "atom.EquipIsoGroupEnum.RC.code", "", "", "");
    public static final EquipIsoGroupEnum HR = new EquipIsoGroupEnum("HR", "atom.EquipIsoGroupEnum.HR.description", "atom.EquipIsoGroupEnum.HR.code", "", "", "");
    public static final EquipIsoGroupEnum HI = new EquipIsoGroupEnum("HI", "atom.EquipIsoGroupEnum.HI.description", "atom.EquipIsoGroupEnum.HI.code", "", "", "");
    public static final EquipIsoGroupEnum UT = new EquipIsoGroupEnum("UT", "atom.EquipIsoGroupEnum.UT.description", "atom.EquipIsoGroupEnum.UT.code", "", "", "");
    public static final EquipIsoGroupEnum UP = new EquipIsoGroupEnum("UP", "atom.EquipIsoGroupEnum.UP.description", "atom.EquipIsoGroupEnum.UP.code", "", "", "");
    public static final EquipIsoGroupEnum PL = new EquipIsoGroupEnum("PL", "atom.EquipIsoGroupEnum.PL.description", "atom.EquipIsoGroupEnum.PL.code", "", "", "");
    public static final EquipIsoGroupEnum PF = new EquipIsoGroupEnum("PF", "atom.EquipIsoGroupEnum.PF.description", "atom.EquipIsoGroupEnum.PF.code", "", "", "");
    public static final EquipIsoGroupEnum PC = new EquipIsoGroupEnum("PC", "atom.EquipIsoGroupEnum.PC.description", "atom.EquipIsoGroupEnum.PC.code", "", "", "");
    public static final EquipIsoGroupEnum PS = new EquipIsoGroupEnum("PS", "atom.EquipIsoGroupEnum.PS.description", "atom.EquipIsoGroupEnum.PS.code", "", "", "");
    public static final EquipIsoGroupEnum PW = new EquipIsoGroupEnum("PW", "atom.EquipIsoGroupEnum.PW.description", "atom.EquipIsoGroupEnum.PW.code", "", "", "");
    public static final EquipIsoGroupEnum TN = new EquipIsoGroupEnum("TN", "atom.EquipIsoGroupEnum.TN.description", "atom.EquipIsoGroupEnum.TN.code", "", "", "");
    public static final EquipIsoGroupEnum TD = new EquipIsoGroupEnum("TD", "atom.EquipIsoGroupEnum.TD.description", "atom.EquipIsoGroupEnum.TD.code", "", "", "");
    public static final EquipIsoGroupEnum TG = new EquipIsoGroupEnum("TG", "atom.EquipIsoGroupEnum.TG.description", "atom.EquipIsoGroupEnum.TG.code", "", "", "");
    public static final EquipIsoGroupEnum AS = new EquipIsoGroupEnum("AS", "atom.EquipIsoGroupEnum.AS.description", "atom.EquipIsoGroupEnum.AS.code", "", "", "");
    public static final EquipIsoGroupEnum NA = new EquipIsoGroupEnum("NA", "atom.EquipIsoGroupEnum.NA.description", "atom.EquipIsoGroupEnum.NA.code", "", "", "");
    public static final EquipIsoGroupEnum CH = new EquipIsoGroupEnum("CH", "atom.EquipIsoGroupEnum.CH.description", "atom.EquipIsoGroupEnum.CH.code", "", "", "");
    public static final EquipIsoGroupEnum HH = new EquipIsoGroupEnum("HH", "atom.EquipIsoGroupEnum.HH.description", "atom.EquipIsoGroupEnum.HH.code", "", "", "");
    public static final EquipIsoGroupEnum GS = new EquipIsoGroupEnum("GS", "atom.EquipIsoGroupEnum.GS.description", "atom.EquipIsoGroupEnum.GS.code", "", "", "");
    public static final EquipIsoGroupEnum GU = new EquipIsoGroupEnum("GU", "atom.EquipIsoGroupEnum.GU.description", "atom.EquipIsoGroupEnum.GU.code", "", "", "");
    public static final EquipIsoGroupEnum CU = new EquipIsoGroupEnum("CU", "atom.EquipIsoGroupEnum.CU.description", "atom.EquipIsoGroupEnum.CU.code", "", "", "");
    public static final EquipIsoGroupEnum CR = new EquipIsoGroupEnum("CR", "atom.EquipIsoGroupEnum.CR.description", "atom.EquipIsoGroupEnum.CR.code", "", "", "");

    public static EquipIsoGroupEnum getEnum(String inName) {
        return (EquipIsoGroupEnum) EquipIsoGroupEnum.getEnum(EquipIsoGroupEnum.class, (String)inName);
    }

    public static Map getEnumMap() {
        return EquipIsoGroupEnum.getEnumMap(EquipIsoGroupEnum.class);
    }

    public static List getEnumList() {
        return EquipIsoGroupEnum.getEnumList(EquipIsoGroupEnum.class);
    }

    public static Collection getList() {
        return EquipIsoGroupEnum.getEnumList(EquipIsoGroupEnum.class);
    }

    public static Iterator iterator() {
        return EquipIsoGroupEnum.iterator(EquipIsoGroupEnum.class);
    }

    protected EquipIsoGroupEnum(String inKey, String inDescriptionResKey, String inCodeResKey, String inBackgroundColor, String inForegroundColor, String inIconIdPath) {
        super(inKey, inDescriptionResKey, inCodeResKey, inBackgroundColor, inForegroundColor, inIconIdPath);
    }

    public String getMappingClassName() {
        return "com.navis.argo.persistence.atoms.UserTypeEquipIsoGroupEnum";
    }
}