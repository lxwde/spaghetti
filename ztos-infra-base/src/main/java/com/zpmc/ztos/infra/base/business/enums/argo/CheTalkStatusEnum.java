package com.zpmc.ztos.infra.base.business.enums.argo;

import com.zpmc.ztos.infra.base.common.enums.AtomizedEnum;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

//public enum CheTalkStatusEnum {
//    WANTTALK(1, "WANTTALK", "want talk", "", "WANTTALK"),
//    NOTALK(2, "NOTALK", "no talk", "", "NOTALK"),
//    NEEDSCONFIRM(3, "NEEDSCONFIRM", "need second confirm", "", "NEEDSCONFIRM");
//    private final int key;
//    private final String code;
//    private final String desc;
//    private final String color;
//    private final String displayName;
//
//    CheTalkStatusEnum(int key, String code, String desc, String color, String displayName) {
//        this.key = key;
//        this.code = code;
//        this.desc = desc;
//        this.color = color;
//        this.displayName = displayName;
//    }
//
//    public static List getEnumList() {
//        return EnumUtils.getEnumList(CheTalkStatusEnum.class);
//    }
//
//    public static EnumSet getEnumSet() {
//        return EnumSet.allOf(CheTalkStatusEnum.class);
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

public class CheTalkStatusEnum
        extends AtomizedEnum {
    public static final CheTalkStatusEnum WANTTALK = new CheTalkStatusEnum("WANTTALK", "atom.CheTalkStatusEnum.WANTTALK.description", "atom.CheTalkStatusEnum.WANTTALK.code", "", "", "");
    public static final CheTalkStatusEnum NOTALK = new CheTalkStatusEnum("NOTALK", "atom.CheTalkStatusEnum.NOTALK.description", "atom.CheTalkStatusEnum.NOTALK.code", "", "", "");
    public static final CheTalkStatusEnum NEEDSCONFIRM = new CheTalkStatusEnum("NEEDSCONFIRM", "atom.CheTalkStatusEnum.NEEDSCONFIRM.description", "atom.CheTalkStatusEnum.NEEDSCONFIRM.code", "", "", "");

    public static CheTalkStatusEnum getEnum(String inName) {
        return (CheTalkStatusEnum) CheTalkStatusEnum.getEnum(CheTalkStatusEnum.class, (String)inName);
    }

    public static Map getEnumMap() {
        return CheTalkStatusEnum.getEnumMap(CheTalkStatusEnum.class);
    }

    public static List getEnumList() {
        return CheTalkStatusEnum.getEnumList(CheTalkStatusEnum.class);
    }

    public static Collection getList() {
        return CheTalkStatusEnum.getEnumList(CheTalkStatusEnum.class);
    }

    public static Iterator iterator() {
        return CheTalkStatusEnum.iterator(CheTalkStatusEnum.class);
    }

    protected CheTalkStatusEnum(String inKey, String inDescriptionResKey, String inCodeResKey, String inBackgroundColor, String inForegroundColor, String inIconIdPath) {
        super(inKey, inDescriptionResKey, inCodeResKey, inBackgroundColor, inForegroundColor, inIconIdPath);
    }

    public String getMappingClassName() {
        return "com.navis.argo.persistence.atoms.UserTypeCheTalkStatusEnum";
    }
}
