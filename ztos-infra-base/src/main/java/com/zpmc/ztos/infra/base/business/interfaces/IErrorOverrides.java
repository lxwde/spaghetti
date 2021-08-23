package com.zpmc.ztos.infra.base.business.interfaces;

import com.sun.istack.NotNull;

import java.util.Collection;

public interface IErrorOverrides {
    public void registerOverridableError(IPropertyKey var1);

    public Collection<IPropertyKey> getOverrideableErrorKeys();

    public void setIgnoreAllOverrideableErrors(boolean var1);

    public boolean ignoreAllOverrideableErrors();

    public boolean shouldOverrideError(@NotNull IUserMessage var1);

    public boolean canOverrideError(@NotNull IUserMessage var1);
}
