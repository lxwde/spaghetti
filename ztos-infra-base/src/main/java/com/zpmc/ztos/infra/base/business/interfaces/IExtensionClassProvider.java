package com.zpmc.ztos.infra.base.business.interfaces;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;

public interface IExtensionClassProvider {
    public static final String BEAN_ID = "extensionClassProvider";

    @NotNull
    public Object getExtensionClassInstance(IExtension var1) throws BizViolation;

    @NotNull
    public IExtensionClassCache getExtensionClassCache();
}
