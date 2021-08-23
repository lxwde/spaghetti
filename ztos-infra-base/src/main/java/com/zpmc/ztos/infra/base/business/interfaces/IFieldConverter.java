package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.sun.istack.Nullable;

public interface IFieldConverter {
    @Nullable
    public Object validateAndConvert(ITranslationContext var1, IMetafield var2, IMetafieldId var3, String var4) throws BizViolation;

}
