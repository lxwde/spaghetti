package com.zpmc.ztos.infra.base.business.interfaces;

import com.sun.istack.NotNull;

public interface IMetafieldGroup {
    public static final String DEFAULT_CATEGORY = "<DEFAULT_CATEGORY>";

    @NotNull
    public String getGroupId();

    @NotNull
    public String getGroupCategory();

    public boolean isMemberOfCategory(@NotNull String var1);

    @NotNull
    public IPropertyKey getLabelKey();
}
