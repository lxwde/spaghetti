package com.zpmc.ztos.infra.base.business.enums.edi;

import com.zpmc.ztos.infra.base.common.enums.AtomizedEnum;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class EdiStatusEnum extends AtomizedEnum {
    public static final EdiStatusEnum ERRORS = new EdiStatusEnum("ERRORS", "atom.EdiStatusEnum.ERRORS.description", "atom.EdiStatusEnum.ERRORS.code", "red", "", "");
    public static final EdiStatusEnum WARNINGS = new EdiStatusEnum("WARNINGS", "atom.EdiStatusEnum.WARNINGS.description", "atom.EdiStatusEnum.WARNINGS.code", "orange", "", "");
    public static final EdiStatusEnum MAPPED = new EdiStatusEnum("MAPPED", "atom.EdiStatusEnum.MAPPED.description", "atom.EdiStatusEnum.MAPPED.code", "green", "", "");
    public static final EdiStatusEnum COMPLETE = new EdiStatusEnum("COMPLETE", "atom.EdiStatusEnum.COMPLETE.description", "atom.EdiStatusEnum.COMPLETE.code", "", "", "");
    public static final EdiStatusEnum MODIFIED = new EdiStatusEnum("MODIFIED", "atom.EdiStatusEnum.MODIFIED.description", "atom.EdiStatusEnum.MODIFIED.code", "", "", "");
    public static final EdiStatusEnum CANCELLED = new EdiStatusEnum("CANCELLED", "atom.EdiStatusEnum.CANCELLED.description", "atom.EdiStatusEnum.CANCELLED.code", "", "", "");
    public static final EdiStatusEnum UNKNOWN = new EdiStatusEnum("UNKNOWN", "atom.EdiStatusEnum.UNKNOWN.description", "atom.EdiStatusEnum.UNKNOWN.code", "", "", "");

    public static EdiStatusEnum getEnum(String inName) {
        return (EdiStatusEnum) EdiStatusEnum.getEnum(EdiStatusEnum.class, (String)inName);
    }

    public static Map getEnumMap() {
        return EdiStatusEnum.getEnumMap(EdiStatusEnum.class);
    }

    public static List getEnumList() {
        return EdiStatusEnum.getEnumList(EdiStatusEnum.class);
    }

    public static Collection getList() {
        return EdiStatusEnum.getEnumList(EdiStatusEnum.class);
    }

    public static Iterator iterator() {
        return EdiStatusEnum.iterator(EdiStatusEnum.class);
    }

    protected EdiStatusEnum(String inKey, String inDescriptionResKey, String inCodeResKey, String inBackgroundColor, String inForegroundColor, String inIconIdPath) {
        super(inKey, inDescriptionResKey, inCodeResKey, inBackgroundColor, inForegroundColor, inIconIdPath);
    }

    public String getMappingClassName() {
        return "com.navis.edi.persistence.atoms.UserTypeEdiStatusEnum";
    }

}
