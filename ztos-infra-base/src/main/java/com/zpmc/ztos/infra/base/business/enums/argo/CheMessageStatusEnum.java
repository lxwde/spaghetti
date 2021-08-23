package com.zpmc.ztos.infra.base.business.enums.argo;

import com.zpmc.ztos.infra.base.common.enums.AtomizedEnum;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

//public enum CheMessageStatusEnum {
//    WANTTALK(1, "WANTTALK", "请求通话", "", "WANTTALK"),
//    NOTALK(2, "NOTALK", "无通话", "", "NOTALK"),
//    NEEDSCONFIRM(3, "NEEDSCONFIRM", "需要二次确认", "", "NEEDSCONFIRM");
//    private final int key;
//    private final String code;
//    private final String desc;
//    private final String color;
//    private final String displayName;
//
//    CheMessageStatusEnum(int key, String code, String desc, String color, String displayName) {
//        this.key = key;
//        this.code = code;
//        this.desc = desc;
//        this.color = color;
//        this.displayName = displayName;
//    }
//
//    public static List getEnumList() {
//        return EnumUtils.getEnumList(CheMessageStatusEnum.class);
//    }
//
//    public static EnumSet getEnumSet() {
//        return EnumSet.allOf(CheMessageStatusEnum.class);
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


public class CheMessageStatusEnum
        extends AtomizedEnum {
    public static final CheMessageStatusEnum WANTTALK = new CheMessageStatusEnum("NOMSG", "atom.CheMessageStatusEnum.WANTTALK.description", "atom.CheMessageStatusEnum.WANTTALK.code", "", "", "");
    public static final CheMessageStatusEnum NOTALK = new CheMessageStatusEnum("MSGSENT", "atom.CheMessageStatusEnum.NOTALK.description", "atom.CheMessageStatusEnum.NOTALK.code", "", "", "");
    public static final CheMessageStatusEnum NEEDSCONFIRM = new CheMessageStatusEnum("MSGACKED", "atom.CheMessageStatusEnum.NEEDSCONFIRM.description", "atom.CheMessageStatusEnum.NEEDSCONFIRM.code", "", "", "");

    public static CheMessageStatusEnum getEnum(String inName) {
        return (CheMessageStatusEnum) CheMessageStatusEnum.getEnum(CheMessageStatusEnum.class, (String)inName);
    }

    public static Map getEnumMap() {
        return CheMessageStatusEnum.getEnumMap(CheMessageStatusEnum.class);
    }

    public static List getEnumList() {
        return CheMessageStatusEnum.getEnumList(CheMessageStatusEnum.class);
    }

    public static Collection getList() {
        return CheMessageStatusEnum.getEnumList(CheMessageStatusEnum.class);
    }

    public static Iterator iterator() {
        return CheMessageStatusEnum.iterator(CheMessageStatusEnum.class);
    }

    protected CheMessageStatusEnum(String inKey, String inDescriptionResKey, String inCodeResKey, String inBackgroundColor, String inForegroundColor, String inIconIdPath) {
        super(inKey, inDescriptionResKey, inCodeResKey, inBackgroundColor, inForegroundColor, inIconIdPath);
    }

    public String getMappingClassName() {
        return "com.navis.argo.persistence.atoms.UserTypeCheMessageStatusEnum";
    }
}
