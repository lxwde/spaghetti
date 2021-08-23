package com.zpmc.ztos.infra.base.common.utils;

import com.zpmc.ztos.infra.base.business.enums.framework.LetterCaseEnum;
import com.zpmc.ztos.infra.base.business.interfaces.IMetafield;
import com.zpmc.ztos.infra.base.business.interfaces.IMetafieldId;

public class FieldUtils {
    private FieldUtils() {
    }

    public static String getAliasedField(String inAlias, IMetafieldId inMetafield) {
        if (inMetafield.isEntityAware()) {
            inAlias = inMetafield.getEntityId().getAlias();
        }
        return inAlias + "." + inMetafield.getQualifiedId();
    }

//    public static LetterCaseEnum getFieldLetterCase(IMetafieldDictionary inMetafieldDictionary, IMetafieldId inMetafieldId) {
//        IMetafield metafield = inMetafieldDictionary.findMetafield(inMetafieldId);
//        return FieldUtils.getFieldLetterCase(metafield);
//    }

    public static LetterCaseEnum getFieldLetterCase(IMetafield inField) {
//        LetterCaseEnum fieldCase;
//        ValidationEntry validation = inField.getValidationEntry();
//        LetterCaseEnum letterCase = fieldCase = validation == null ? null : validation.getCase();
//        if (fieldCase == null) {
//            ApplicationModuleSettings appSettings = (ApplicationModuleSettings)PortalApplicationContext.getBean("appModuleSettings");
//            fieldCase = appSettings.getDefaultLetterCase();
//            fieldCase = fieldCase == null ? LetterCaseEnum.MIXED : fieldCase;
//        }
//        return fieldCase;
          return null;
    }

}
