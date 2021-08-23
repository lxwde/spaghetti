package com.zpmc.ztos.infra.base.common.utils;

import com.zpmc.ztos.infra.base.business.interfaces.ITranslationContext;
import com.zpmc.ztos.infra.base.common.model.DefaultTranslationContext;
import com.zpmc.ztos.infra.base.common.model.UserContext;

public class TranslationUtils {
    public static final String DEFAULT_CHARSET_FORMAT = "UTF-8";

    public static ITranslationContext getTranslationContext(UserContext inContext) {
        return new DefaultTranslationContext(inContext);
    }

    private TranslationUtils() {
    }

}
