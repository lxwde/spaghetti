package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.business.enums.argo.FunctionalAreaEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.LogicalEntityEnum;

import java.util.List;

public interface IEventType {

    public String getId();

    public String getDescription();

    public boolean isBuiltInEventType();

    public boolean isFacilityService();

    public boolean isBillable();

    public boolean isNotifiable();

    public boolean isAcknowledgeable();

    public boolean isEventRecorded();

    public boolean canBulkUpdate();

    public LogicalEntityEnum getAppliesToEntityType();

    public List getEventEffects();

    public String getExternalId();

    public FunctionalAreaEnum getFunctionalArea();

    public boolean isAutoExtractEvent();
}
