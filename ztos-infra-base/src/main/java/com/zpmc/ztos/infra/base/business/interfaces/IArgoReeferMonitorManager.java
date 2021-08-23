package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.business.model.ScopedBizUnit;

public interface IArgoReeferMonitorManager {
    public static final String BEAN_ID = "reeferMonitorManager";

    public void propagateLineDefaultMonitorsToReeferUnits(ScopedBizUnit var1);
}
