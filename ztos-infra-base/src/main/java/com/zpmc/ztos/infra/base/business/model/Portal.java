package com.zpmc.ztos.infra.base.business.model;

import com.zpmc.ztos.infra.base.business.dataobject.PortalDO;
import com.zpmc.ztos.infra.base.business.enums.framework.LifeCycleStateEnum;
import com.zpmc.ztos.infra.base.business.interfaces.IObsoleteable;

public class Portal extends PortalDO implements IObsoleteable {
    public Portal() {
        this.setPrtlLifeCycleState(LifeCycleStateEnum.ACTIVE);
    }

    @Override
    public void setLifeCycleState(LifeCycleStateEnum lifeCycleStateEnum) {
        this.setPrtlLifeCycleState(lifeCycleStateEnum);
    }

    @Override
    public LifeCycleStateEnum getLifeCycleState() {
        return this.getPrtlLifeCycleState();
    }
}
