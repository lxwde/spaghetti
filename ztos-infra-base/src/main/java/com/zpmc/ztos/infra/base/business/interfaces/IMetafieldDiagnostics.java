package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.common.model.ValueObject;

public interface IMetafieldDiagnostics {
    public static final String BEAN_ID = "metafieldDiagnostics";

    public ValueObject getStandardAttributes(ITranslationContext var1, IMetafield var2);

    public ValueObject getFullDiagnosticAttributes(ITranslationContext var1, IMetafield var2);

}
