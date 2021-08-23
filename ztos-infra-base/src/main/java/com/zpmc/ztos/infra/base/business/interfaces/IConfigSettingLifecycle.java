package com.zpmc.ztos.infra.base.business.interfaces;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.common.model.FieldChange;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.io.Serializable;

public interface IConfigSettingLifecycle {
    @Nullable
    public FieldChange preprocessUpdate(IConfig var1, @NotNull Object var2, Long var3, Serializable var4);

    public void preprocessDelete(IConfig var1, Long var2, Serializable var3);

    public void validateUpdate(IConfig var1, Object var2, Long var3, Serializable var4);
}
