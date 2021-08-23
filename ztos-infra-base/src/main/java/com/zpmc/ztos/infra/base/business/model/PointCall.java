package com.zpmc.ztos.infra.base.business.model;

import com.zpmc.ztos.infra.base.business.dataobject.PointCallDO;
import com.zpmc.ztos.infra.base.business.enums.framework.LifeCycleStateEnum;
import com.zpmc.ztos.infra.base.business.interfaces.ICarrierField;
import com.zpmc.ztos.infra.base.business.interfaces.IValueHolder;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.common.events.AuditEvent;
import com.zpmc.ztos.infra.base.common.utils.MathUtils;

import java.io.Serializable;
import java.util.List;

public class PointCall extends PointCallDO {
    public PointCall() {
        this.setCallOrder(new Long(0L));
        this.setCallNumber("0");
        this.setCallLifeCycleState(LifeCycleStateEnum.ACTIVE);
        this.setCallTransitTimeMin(0L);
    }

    public void setLifeCycleState(LifeCycleStateEnum inLifeCycleState) {
        this.setCallLifeCycleState(inLifeCycleState);
    }

    public LifeCycleStateEnum getLifeCycleState() {
        return this.getCallLifeCycleState();
    }

    public String[] getCallDests() {
        List destinations = this.getCallDestinations();
        String[] destArry = new String[destinations.size()];
        int i = 0;
        for (Object dest : destinations) {
            destArry[i++] = ((Destination)dest).getDestPoint().getPointId();
        }
        return destArry;
    }

    public IValueHolder[] getCallDestVaa() {
        List destinations = this.getCallDestinations();
        IValueHolder[] vaoArray = new IValueHolder[destinations.size()];
        MetafieldIdList fields = new MetafieldIdList();
        fields.add(ICarrierField.DEST_POINT_ID);
        fields.add(ICarrierField.DEST_PLACE_NAME);
        fields.add(ICarrierField.DEST_UNLOC_ID);
        int i = 0;
        for (Object destination : destinations) {
            vaoArray[i++] = ((Destination)destination).getValueObject(fields);
        }
        return vaoArray;
    }

    public AuditEvent vetAuditEvent(AuditEvent inAuditEvent) {
        return inAuditEvent;
    }

    public Double getCallTransitDurationHours() {
        Long transitTimeMin = this.getCallTransitTimeMin();
        return MathUtils.round((double)((double)transitTimeMin.longValue() / 60.0), (int)2);
    }

    public void setCallTransitDuration(Double inTransitHrs) {
        Long transitDurationInMin = 0L;
        if (inTransitHrs != null && inTransitHrs > 0.0) {
            Double hours = inTransitHrs * 60.0;
            transitDurationInMin = hours.longValue();
        }
        this.setCallTransitTimeMin(transitDurationInMin);
    }

    public static PointCall hydrate(Serializable inPrimaryKey) {
        return (PointCall) HibernateApi.getInstance().load(PointCall.class, inPrimaryKey);
    }

    public String toString() {
        return this.getEntityName() + ":" + this.getPrimaryKey() + ":" + this.getCallPoint().getPointId();
    }
}
