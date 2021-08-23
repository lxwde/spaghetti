package com.zpmc.ztos.infra.base.business.interfaces;

import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

public interface INaturallyKeyedEntity extends IEntity {
    @Nullable
    public INaturallyKeyedEntity findByNaturalKey(String var1);

    @Nullable
    public String getNaturalKey();
}
