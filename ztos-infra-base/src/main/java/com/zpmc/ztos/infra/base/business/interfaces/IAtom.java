package com.zpmc.ztos.infra.base.business.interfaces;


import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

public interface IAtom {
    public String getKey();

    public IPropertyKey getDescriptionPropertyKey();

    public IPropertyKey getCodePropertyKey();

    @Deprecated
    public String getDescriptionResKey();

    @Deprecated
    public String getCodeResKey();

    @Nullable
    public IIconKey getIconKey();
}
