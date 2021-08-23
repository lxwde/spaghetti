package com.zpmc.ztos.infra.base.business.model;

import com.zpmc.ztos.infra.base.business.dataobject.LaneDO;
import com.zpmc.ztos.infra.base.business.enums.argo.LaneStatusEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.LaneTruckStatusEnum;
import com.zpmc.ztos.infra.base.business.enums.framework.LifeCycleStateEnum;

public class Lane extends LaneDO {
    public Lane() {
        this.setLaneLifeCycleState(LifeCycleStateEnum.ACTIVE);
        this.setLaneStatus(LaneStatusEnum.OPEN);
        this.setLaneTruckStatus(LaneTruckStatusEnum.EMPTY);
    }
}
