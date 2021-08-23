package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.business.enums.framework.LifeCycleStateEnum;
import com.zpmc.ztos.infra.base.business.model.CarrierItinerary;
import com.zpmc.ztos.infra.base.business.model.RoutingPoint;
import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public abstract class PointCallDO extends DatabaseEntity implements Serializable {
    private Long callGkey;
    private Long callOrder;
    private String callNumber;
    private String callScanCode;
    private Long callTransitTimeMin;
    private Date callCreated;
    private String callCreator;
    private Date callChanged;
    private String callChanger;
    private LifeCycleStateEnum callLifeCycleState;
    private CarrierItinerary callItinerary;
    private RoutingPoint callPoint;
    private List callDestinations;

    public Serializable getPrimaryKey() {
        return this.getCallGkey();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        Long id = this.getCallGkey();
        if (id == null) {
            return false;
        }
        if (!(other instanceof PointCallDO)) {
            return false;
        }
        PointCallDO that = (PointCallDO)other;
        return ((Object)id).equals(that.getCallGkey());
    }

    public int hashCode() {
        Long id = this.getCallGkey();
        return id == null ? System.identityHashCode(this) : ((Object)id).hashCode();
    }

    public Long getCallGkey() {
        return this.callGkey;
    }

    protected void setCallGkey(Long callGkey) {
        this.callGkey = callGkey;
    }

    public Long getCallOrder() {
        return this.callOrder;
    }

    public void setCallOrder(Long callOrder) {
        this.callOrder = callOrder;
    }

    public String getCallNumber() {
        return this.callNumber;
    }

    public void setCallNumber(String callNumber) {
        this.callNumber = callNumber;
    }

    public String getCallScanCode() {
        return this.callScanCode;
    }

    public void setCallScanCode(String callScanCode) {
        this.callScanCode = callScanCode;
    }

    public Long getCallTransitTimeMin() {
        return this.callTransitTimeMin;
    }

    protected void setCallTransitTimeMin(Long callTransitTimeMin) {
        this.callTransitTimeMin = callTransitTimeMin;
    }

    public Date getCallCreated() {
        return this.callCreated;
    }

    protected void setCallCreated(Date callCreated) {
        this.callCreated = callCreated;
    }

    public String getCallCreator() {
        return this.callCreator;
    }

    protected void setCallCreator(String callCreator) {
        this.callCreator = callCreator;
    }

    public Date getCallChanged() {
        return this.callChanged;
    }

    protected void setCallChanged(Date callChanged) {
        this.callChanged = callChanged;
    }

    public String getCallChanger() {
        return this.callChanger;
    }

    protected void setCallChanger(String callChanger) {
        this.callChanger = callChanger;
    }

    public LifeCycleStateEnum getCallLifeCycleState() {
        return this.callLifeCycleState;
    }

    public void setCallLifeCycleState(LifeCycleStateEnum callLifeCycleState) {
        this.callLifeCycleState = callLifeCycleState;
    }

    public CarrierItinerary getCallItinerary() {
        return this.callItinerary;
    }

    public void setCallItinerary(CarrierItinerary callItinerary) {
        this.callItinerary = callItinerary;
    }

    public RoutingPoint getCallPoint() {
        return this.callPoint;
    }

    public void setCallPoint(RoutingPoint callPoint) {
        this.callPoint = callPoint;
    }

    public List getCallDestinations() {
        return this.callDestinations;
    }

    public void setCallDestinations(List callDestinations) {
        this.callDestinations = callDestinations;
    }

}
