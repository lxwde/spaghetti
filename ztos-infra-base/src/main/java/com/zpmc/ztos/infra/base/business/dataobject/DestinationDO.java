package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.business.enums.framework.LifeCycleStateEnum;
import com.zpmc.ztos.infra.base.business.model.PointCall;
import com.zpmc.ztos.infra.base.business.model.RoutingPoint;
import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;

import java.io.Serializable;
import java.util.Date;

public class DestinationDO extends DatabaseEntity implements Serializable {
    private Long destGkey;
    private Date destCreated;
    private String destCreator;
    private Date destChanged;
    private String destChanger;
    private LifeCycleStateEnum destLifeCycleState;
    private PointCall destCall;
    private RoutingPoint destPoint;

    public Serializable getPrimaryKey() {
        return this.getDestGkey();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        Long id = this.getDestGkey();
        if (id == null) {
            return false;
        }
        if (!(other instanceof DestinationDO)) {
            return false;
        }
        DestinationDO that = (DestinationDO)other;
        return ((Object)id).equals(that.getDestGkey());
    }

    public int hashCode() {
        Long id = this.getDestGkey();
        return id == null ? System.identityHashCode(this) : ((Object)id).hashCode();
    }

    public Long getDestGkey() {
        return this.destGkey;
    }

    protected void setDestGkey(Long destGkey) {
        this.destGkey = destGkey;
    }

    public Date getDestCreated() {
        return this.destCreated;
    }

    protected void setDestCreated(Date destCreated) {
        this.destCreated = destCreated;
    }

    public String getDestCreator() {
        return this.destCreator;
    }

    protected void setDestCreator(String destCreator) {
        this.destCreator = destCreator;
    }

    public Date getDestChanged() {
        return this.destChanged;
    }

    protected void setDestChanged(Date destChanged) {
        this.destChanged = destChanged;
    }

    public String getDestChanger() {
        return this.destChanger;
    }

    protected void setDestChanger(String destChanger) {
        this.destChanger = destChanger;
    }

    public LifeCycleStateEnum getDestLifeCycleState() {
        return this.destLifeCycleState;
    }

    public void setDestLifeCycleState(LifeCycleStateEnum destLifeCycleState) {
        this.destLifeCycleState = destLifeCycleState;
    }

    public PointCall getDestCall() {
        return this.destCall;
    }

    public void setDestCall(PointCall destCall) {
        this.destCall = destCall;
    }

    public RoutingPoint getDestPoint() {
        return this.destPoint;
    }

    public void setDestPoint(RoutingPoint destPoint) {
        this.destPoint = destPoint;
    }

}
