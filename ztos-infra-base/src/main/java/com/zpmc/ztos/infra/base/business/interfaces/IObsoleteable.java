package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.business.enums.framework.LifeCycleStateEnum;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

public interface IObsoleteable {
    public void setLifeCycleState(LifeCycleStateEnum var1);

    @Nullable
    public LifeCycleStateEnum getLifeCycleState();
}
