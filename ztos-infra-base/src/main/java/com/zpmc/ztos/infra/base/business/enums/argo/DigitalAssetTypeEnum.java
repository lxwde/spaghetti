package com.zpmc.ztos.infra.base.business.enums.argo;

import com.zpmc.ztos.infra.base.common.enums.AtomizedEnum;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class DigitalAssetTypeEnum extends AtomizedEnum {
    public static final DigitalAssetTypeEnum IMAGE = new DigitalAssetTypeEnum("IMAGE", "atom.DigitalAssetTypeEnum.IMAGE.description", "atom.DigitalAssetTypeEnum.IMAGE.code", "whitesmoke", "", "");
    public static final DigitalAssetTypeEnum GROOVY = new DigitalAssetTypeEnum("GROOVY", "atom.DigitalAssetTypeEnum.GROOVY.description", "atom.DigitalAssetTypeEnum.GROOVY.code", "darkslateblue", "", "");

    public static DigitalAssetTypeEnum getEnum(String inName) {
        return (DigitalAssetTypeEnum) DigitalAssetTypeEnum.getEnum(DigitalAssetTypeEnum.class, (String)inName);
    }

    public static Map getEnumMap() {
        return DigitalAssetTypeEnum.getEnumMap(DigitalAssetTypeEnum.class);
    }

    public static List getEnumList() {
        return DigitalAssetTypeEnum.getEnumList(DigitalAssetTypeEnum.class);
    }

    public static Collection getList() {
        return DigitalAssetTypeEnum.getEnumList(DigitalAssetTypeEnum.class);
    }

    public static Iterator iterator() {
        return DigitalAssetTypeEnum.iterator(DigitalAssetTypeEnum.class);
    }

    protected DigitalAssetTypeEnum(String inKey, String inDescriptionResKey, String inCodeResKey, String inBackgroundColor, String inForegroundColor, String inIconIdPath) {
        super(inKey, inDescriptionResKey, inCodeResKey, inBackgroundColor, inForegroundColor, inIconIdPath);
    }

    public String getMappingClassName() {
        return "com.navis.argo.persistence.atoms.UserTypeDigitalAssetTypeEnum";
    }

}
