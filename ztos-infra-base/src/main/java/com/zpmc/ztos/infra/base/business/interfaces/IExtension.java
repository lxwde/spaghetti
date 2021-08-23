package com.zpmc.ztos.infra.base.business.interfaces;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.business.enums.framework.ExtensionLanguageEnum;

public interface IExtension {

    public String getExtensionName();

    public boolean isOfLanguage(ExtensionLanguageEnum var1);

    public boolean isEnabled();

    @NotNull
    public ExtensionLanguageEnum getLanguage();

    @NotNull
    public Long getInternalVersion();

    public byte[] getContents();

    public Long getUniqueKey();

    public IExtensionType getExtensionType();
}
