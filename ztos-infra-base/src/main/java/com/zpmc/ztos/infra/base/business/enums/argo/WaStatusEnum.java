package com.zpmc.ztos.infra.base.business.enums.argo;

import com.zpmc.ztos.infra.base.common.enums.AtomizedEnum;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

//public enum WaStatusEnum {
//    UNKNOWN(1, "UNKNOWN", "", "", "UNKNOWN"),
//    SENDING(2, "SENDING", "", "", "SENDING"),
//    SENT(3, "SENT", "", "", "SENT"),
//    ACCEPTED(4, "ACCEPTED", "", "", "ACCEPTED"),
//    COMPLETED(5, "COMPLETED", "", "", "COMPLETED"),
//    REJECTED(6, "REJECTED", "", "", "REJECTED"),
//    PENDING_REJECTION(7, "PENDING_REJECTION", "", "", "PENDING_REJECTION"),
//    PENDING_DISPATCH(8, "PENDING_DISPATCH", "", "", "PENDING_DISPATCH"),
//    ABORTED(9, "ABORTED", "", "", "ABORTED"),
//    PENDING_COMPLETION(10, "PENDING_COMPLETION", "", "", "PENDING_COMPLETION");
//    private final int key;
//    private final String code;
//    private final String desc;
//    private final String color;
//    private final String displayName;
//
//    WaStatusEnum(int key, String code, String desc, String color, String displayName) {
//        this.key = key;
//        this.code = code;
//        this.desc = desc;
//        this.color = color;
//        this.displayName = displayName;
//    }
//
//    public static List getEnumList() {
//        return EnumUtils.getEnumList(WaStatusEnum.class);
//    }
//
//    public static EnumSet getEnumSet() {
//        return EnumSet.allOf(WaStatusEnum.class);
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

public class WaStatusEnum
        extends AtomizedEnum {
    public static final WaStatusEnum UNKNOWN = new WaStatusEnum("UNKNOWN", "atom.WaStatusEnum.UNKNOWN.description", "atom.WaStatusEnum.UNKNOWN.code", "", "", "");
    public static final WaStatusEnum SENDING = new WaStatusEnum("SENDING", "atom.WaStatusEnum.SENDING.description", "atom.WaStatusEnum.SENDING.code", "", "", "");
    public static final WaStatusEnum SENT = new WaStatusEnum("SENT", "atom.WaStatusEnum.SENT.description", "atom.WaStatusEnum.SENT.code", "", "", "");
    public static final WaStatusEnum ACCEPTED = new WaStatusEnum("ACCEPTED", "atom.WaStatusEnum.ACCEPTED.description", "atom.WaStatusEnum.ACCEPTED.code", "", "", "");
    public static final WaStatusEnum COMPLETED = new WaStatusEnum("COMPLETED", "atom.WaStatusEnum.COMPLETED.description", "atom.WaStatusEnum.COMPLETED.code", "", "", "");
    public static final WaStatusEnum REJECTED = new WaStatusEnum("REJECTED", "atom.WaStatusEnum.REJECTED.description", "atom.WaStatusEnum.REJECTED.code", "", "", "");
    public static final WaStatusEnum PENDING_REJECTION = new WaStatusEnum("PENDING_REJECTION", "atom.WaStatusEnum.PENDING_REJECTION.description", "atom.WaStatusEnum.PENDING_REJECTION.code", "", "", "");
    public static final WaStatusEnum PENDING_DISPATCH = new WaStatusEnum("PENDING_DISPATCH", "atom.WaStatusEnum.PENDING_DISPATCH.description", "atom.WaStatusEnum.PENDING_DISPATCH.code", "", "", "");
    public static final WaStatusEnum ABORTED = new WaStatusEnum("ABORTED", "atom.WaStatusEnum.ABORTED.description", "atom.WaStatusEnum.ABORTED.code", "", "", "");
    public static final WaStatusEnum PENDING_COMPLETION = new WaStatusEnum("PENDING_COMPLETION", "atom.WaStatusEnum.PENDING_COMPLETION.description", "atom.WaStatusEnum.PENDING_COMPLETION.code", "", "", "");

    public static WaStatusEnum getEnum(String inName) {
        return (WaStatusEnum) WaStatusEnum.getEnum(WaStatusEnum.class, (String)inName);
    }

    public static Map getEnumMap() {
        return WaStatusEnum.getEnumMap(WaStatusEnum.class);
    }

    public static List getEnumList() {
        return WaStatusEnum.getEnumList(WaStatusEnum.class);
    }

    public static Collection getList() {
        return WaStatusEnum.getEnumList(WaStatusEnum.class);
    }

    public static Iterator iterator() {
        return WaStatusEnum.iterator(WaStatusEnum.class);
    }

    protected WaStatusEnum(String inKey, String inDescriptionResKey, String inCodeResKey, String inBackgroundColor, String inForegroundColor, String inIconIdPath) {
        super(inKey, inDescriptionResKey, inCodeResKey, inBackgroundColor, inForegroundColor, inIconIdPath);
    }

    public String getMappingClassName() {
        return "com.navis.argo.persistence.atoms.UserTypeWaStatusEnum";
    }
}
