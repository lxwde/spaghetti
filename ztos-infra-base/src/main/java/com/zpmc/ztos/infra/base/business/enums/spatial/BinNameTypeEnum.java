package com.zpmc.ztos.infra.base.business.enums.spatial;

import com.zpmc.ztos.infra.base.common.enums.AtomizedEnum;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class BinNameTypeEnum extends AtomizedEnum {
    public static final BinNameTypeEnum STANDARD = new BinNameTypeEnum("STANDARD", "atom.BinNameTypeEnum.STANDARD.description", "atom.BinNameTypeEnum.STANDARD.code", "", "", "");
    public static final BinNameTypeEnum ALTERNATE = new BinNameTypeEnum("ALTERNATE", "atom.BinNameTypeEnum.ALTERNATE.description", "atom.BinNameTypeEnum.ALTERNATE.code", "", "", "");

    public static BinNameTypeEnum getEnum(String inName) {
        return (BinNameTypeEnum) BinNameTypeEnum.getEnum(BinNameTypeEnum.class, (String)inName);
    }

    public static Map getEnumMap() {
        return BinNameTypeEnum.getEnumMap(BinNameTypeEnum.class);
    }

    public static List getEnumList() {
        return BinNameTypeEnum.getEnumList(BinNameTypeEnum.class);
    }

    public static Collection getList() {
        return BinNameTypeEnum.getEnumList(BinNameTypeEnum.class);
    }

    public static Iterator iterator() {
        return BinNameTypeEnum.iterator(BinNameTypeEnum.class);
    }

    protected BinNameTypeEnum(String inKey, String inDescriptionResKey, String inCodeResKey, String inBackgroundColor, String inForegroundColor, String inIconIdPath) {
        super(inKey, inDescriptionResKey, inCodeResKey, inBackgroundColor, inForegroundColor, inIconIdPath);
    }

    public String getMappingClassName() {
        return "com.navis.spatial.persistence.atoms.UserTypeBinNameTypeEnum";
    }

}
