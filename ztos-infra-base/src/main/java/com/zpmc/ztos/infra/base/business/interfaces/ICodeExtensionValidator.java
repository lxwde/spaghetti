package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

public interface ICodeExtensionValidator {

    public static final String BEAN_ID = "codeExtensionValidator";

    @Nullable
    public Class validate(IExtension var1) throws BizViolation;
}
