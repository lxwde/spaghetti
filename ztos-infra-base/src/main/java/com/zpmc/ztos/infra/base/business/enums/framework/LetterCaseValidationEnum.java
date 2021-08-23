package com.zpmc.ztos.infra.base.business.enums.framework;

import com.zpmc.ztos.infra.base.common.enums.AtomizedEnum;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class LetterCaseValidationEnum extends AtomizedEnum {
    public static final LetterCaseValidationEnum UPPER = new LetterCaseValidationEnum("UPPER", "atom.LetterCaseValidationEnum.UPPER.description", "atom.LetterCaseValidationEnum.UPPER.code", "", "", "");
    public static final LetterCaseValidationEnum LOWER = new LetterCaseValidationEnum("lower", "atom.LetterCaseValidationEnum.LOWER.description", "atom.LetterCaseValidationEnum.LOWER.code", "", "", "");
    public static final LetterCaseValidationEnum MIXED = new LetterCaseValidationEnum("Mixed", "atom.LetterCaseValidationEnum.MIXED.description", "atom.LetterCaseValidationEnum.MIXED.code", "", "", "");

    public static LetterCaseValidationEnum getEnum(String inName) {
        return (LetterCaseValidationEnum) LetterCaseValidationEnum.getEnum(LetterCaseValidationEnum.class, (String)inName);
    }

    public static Map getEnumMap() {
        return LetterCaseValidationEnum.getEnumMap(LetterCaseValidationEnum.class);
    }

    public static List getEnumList() {
        return LetterCaseValidationEnum.getEnumList(LetterCaseValidationEnum.class);
    }

    public static Collection getList() {
        return LetterCaseValidationEnum.getEnumList(LetterCaseValidationEnum.class);
    }

    public static Iterator iterator() {
        return LetterCaseValidationEnum.iterator(LetterCaseValidationEnum.class);
    }

    protected LetterCaseValidationEnum(String inKey, String inDescriptionResKey, String inCodeResKey, String inBackgroundColor, String inForegroundColor, String inIconIdPath) {
        super(inKey, inDescriptionResKey, inCodeResKey, inBackgroundColor, inForegroundColor, inIconIdPath);
    }

    @Override
    public String getMappingClassName() {
        return "com.navis.framework.persistence.atoms.UserTypeLetterCaseValidationEnum";
    }

}
