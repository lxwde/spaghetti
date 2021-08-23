package com.zpmc.ztos.infra.base.business.interfaces;

import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.io.Serializable;
import java.util.Collection;

public interface IScopeNodeEntity {

    public static final String PATH_SEPARATOR = "/";

    public IScopeEnum getScopeEnum();

    @Nullable
    public IScopeNodeEntity getParent();

    public Collection getChildren();

    @Nullable
    public String getId();

    @Nullable
    public String getPathName();

    @Nullable
    public Serializable getPrimaryKey();

}
