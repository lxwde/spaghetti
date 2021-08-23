package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.business.enums.argo.LogicalEntityEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.ServiceQuantityUnitEnum;
import com.zpmc.ztos.infra.base.business.model.ScopedBizUnit;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

public interface IEvent extends Comparable, IValueSource {
    public Object getEventGKey();

    public LogicalEntityEnum getEventAppliedToClass();

    public String getEventAppliedToNaturalKey();

    public Serializable getEventAppliedToGkey();

    public String getEventPrincipal();

    @Nullable
    public String getEventOperatorId();

    @Nullable
    public String getEventComplexId();

    @Nullable
    public String getEventFacilityId();

    @Nullable
    public String getEventYardId();

    public Date getEventTime();

    public String getEventNote();

    public String getEventTypeId();

    public String getEventTypeDescription();

    public LogicalEntityEnum getEventTypeAppliesTo();

    public boolean isEventTypeBillable();

    public Set getFieldChanges();

    public Double getEventQuantity();

    public ServiceQuantityUnitEnum getEventQuantityUnit();

    public String getEvntFieldChangesString();

    public Long getEvntBillingExtractBatchId();

    public ScopedBizUnit getEvntResponsibleParty();

    public String getEvntRelatedEntityClass();

    public String getEvntRelatedEntityId();

    public Long getEvntRelatedEntityGkey();

    public Boolean getEventTypeIsBillable();

    public String getEventFieldChangesString();

    public List getEventFieldChanges();

    public Long getEvntRelatedBatchNbr();
}
