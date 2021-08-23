package com.zpmc.ztos.infra.base.business.enums.argo;

import com.zpmc.ztos.infra.base.common.enums.AtomizedEnum;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class TransferLocationEnum extends AtomizedEnum {
    public static final TransferLocationEnum NONE = new TransferLocationEnum("NONE", "atom.TransferLocationEnum.NONE.description", "atom.TransferLocationEnum.NONE.code", "", "", "");
    public static final TransferLocationEnum ROWLOW = new TransferLocationEnum("ROWLOW", "atom.TransferLocationEnum.ROWLOW.description", "atom.TransferLocationEnum.ROWLOW.code", "", "", "");
    public static final TransferLocationEnum ROWHIGH = new TransferLocationEnum("ROWHIGH", "atom.TransferLocationEnum.ROWHIGH.description", "atom.TransferLocationEnum.ROWHIGH.code", "", "", "");
    public static final TransferLocationEnum COLLOW = new TransferLocationEnum("COLLOW", "atom.TransferLocationEnum.COLLOW.description", "atom.TransferLocationEnum.COLLOW.code", "", "", "");
    public static final TransferLocationEnum COLHIGH = new TransferLocationEnum("COLHIGH", "atom.TransferLocationEnum.COLHIGH.description", "atom.TransferLocationEnum.COLHIGH.code", "", "", "");

    public static TransferLocationEnum getEnum(String inName) {
        return (TransferLocationEnum) TransferLocationEnum.getEnum(TransferLocationEnum.class, (String)inName);
    }

    public static Map getEnumMap() {
        return TransferLocationEnum.getEnumMap(TransferLocationEnum.class);
    }

    public static List getEnumList() {
        return TransferLocationEnum.getEnumList(TransferLocationEnum.class);
    }

    public static Collection getList() {
        return TransferLocationEnum.getEnumList(TransferLocationEnum.class);
    }

    public static Iterator iterator() {
        return TransferLocationEnum.iterator(TransferLocationEnum.class);
    }

    protected TransferLocationEnum(String inKey, String inDescriptionResKey, String inCodeResKey, String inBackgroundColor, String inForegroundColor, String inIconIdPath) {
        super(inKey, inDescriptionResKey, inCodeResKey, inBackgroundColor, inForegroundColor, inIconIdPath);
    }

    public String getMappingClassName() {
        return "com.navis.argo.persistence.atoms.UserTypeTransferLocationEnum";
    }

}
