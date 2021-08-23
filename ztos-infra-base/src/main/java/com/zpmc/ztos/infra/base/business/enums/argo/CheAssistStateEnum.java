package com.zpmc.ztos.infra.base.business.enums.argo;

import com.zpmc.ztos.infra.base.common.enums.AtomizedEnum;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

//public enum CheAssistStateEnum {
//    NONEED(1, "NONEED", "no need assist", "", "NONEED"),
//    OTHERORDERPOSSIBLE(2, "OTHERORDERPOSSIBLE", "other order possible", "", "OTHERORDERPOSSIBLE"),
//    MANUALASSISTREQUIRED(3, "MANUALASSISTREQUIRED", "manual assist required", "", "MANUALASSISTREQUIRED"),
//    WANTSTORESUME(4, "WANTSTORESUME", "want storesume", "", "WANTSTORESUME"),
//    RESUMESENT(5, "RESUMESENT", "resumsent", "", "RESUMESENT");
//    private final int key;
//    private final String code;
//    private final String desc;
//    private final String color;
//    private final String displayName;
//
//    CheAssistStateEnum(int key, String code, String desc, String color, String displayName) {
//        this.key = key;
//        this.code = code;
//        this.desc = desc;
//        this.color = color;
//        this.displayName = displayName;
//    }
//
//    public static List getEnumList() {
//        return EnumUtils.getEnumList(CheAssistStateEnum.class);
//    }
//
//    public static EnumSet getEnumSet() {
//        return EnumSet.allOf(CheAssistStateEnum.class);
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

public class CheAssistStateEnum
        extends AtomizedEnum {
    public static final CheAssistStateEnum NONEED = new CheAssistStateEnum("NONEED", "atom.CheAssistStateEnum.NONEED.description", "atom.CheAssistStateEnum.NONEED.code", "", "", "");
    public static final CheAssistStateEnum OTHERORDERPOSSIBLE = new CheAssistStateEnum("OTHERORDERPOSSIBLE", "atom.CheAssistStateEnum.OTHERORDERPOSSIBLE.description", "atom.CheAssistStateEnum.OTHERORDERPOSSIBLE.code", "", "", "");
    public static final CheAssistStateEnum MANUALASSISTREQUIRED = new CheAssistStateEnum("MANUALASSISTREQUIRED", "atom.CheAssistStateEnum.MANUALASSISTREQUIRED.description", "atom.CheAssistStateEnum.MANUALASSISTREQUIRED.code", "", "", "");
    public static final CheAssistStateEnum WANTSTORESUME = new CheAssistStateEnum("WANTSTORESUME", "atom.CheAssistStateEnum.WANTSTORESUME.description", "atom.CheAssistStateEnum.WANTSTORESUME.code", "", "", "");
    public static final CheAssistStateEnum RESUMESENT = new CheAssistStateEnum("RESUMESENT", "atom.CheAssistStateEnum.RESUMESENT.description", "atom.CheAssistStateEnum.RESUMESENT.code", "", "", "");

    public static CheAssistStateEnum getEnum(String inName) {
        return (CheAssistStateEnum) CheAssistStateEnum.getEnum(CheAssistStateEnum.class, (String)inName);
    }

    public static Map getEnumMap() {
        return CheAssistStateEnum.getEnumMap(CheAssistStateEnum.class);
    }

    public static List getEnumList() {
        return CheAssistStateEnum.getEnumList(CheAssistStateEnum.class);
    }

    public static Collection getList() {
        return CheAssistStateEnum.getEnumList(CheAssistStateEnum.class);
    }

    public static Iterator iterator() {
        return CheAssistStateEnum.iterator(CheAssistStateEnum.class);
    }

    protected CheAssistStateEnum(String inKey, String inDescriptionResKey, String inCodeResKey, String inBackgroundColor, String inForegroundColor, String inIconIdPath) {
        super(inKey, inDescriptionResKey, inCodeResKey, inBackgroundColor, inForegroundColor, inIconIdPath);
    }

    public String getMappingClassName() {
        return "com.navis.argo.persistence.atoms.UserTypeCheAssistStateEnum";
    }
}

