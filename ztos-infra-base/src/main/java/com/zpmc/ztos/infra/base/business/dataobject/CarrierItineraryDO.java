package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.business.enums.framework.LifeCycleStateEnum;
import com.zpmc.ztos.infra.base.business.model.CarrierVisit;
import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 运送工具行程对象
 *
 * @author yejun
 */
@Data
public abstract class CarrierItineraryDO extends DatabaseEntity
implements Serializable {
    private Long itinGkey;
    private String itinId;
    private Date itinIdCreated;
    private String itinIdCreator;
    private Date itinIdChanged;
    private String itinIdChanger;
    private LifeCycleStateEnum itinLifeCycleState;
    private CarrierVisit itinOwnerCv;
    private List itinPoints;

    public Serializable getPrimaryKey() {
        return this.getItinGkey();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        Long id = this.getItinGkey();
        if (id == null) {
            return false;
        }
        if (!(other instanceof CarrierItineraryDO)) {
            return false;
        }
        CarrierItineraryDO that = (CarrierItineraryDO)other;
        return ((Object)id).equals(that.getItinGkey());
    }

    public int hashCode() {
        Long id = this.getItinGkey();
        return id == null ? System.identityHashCode(this) : ((Object)id).hashCode();
    }

    public Long getItinGkey() {
        return this.itinGkey;
    }

    protected void setItinGkey(Long itinGkey) {
        this.itinGkey = itinGkey;
    }

    public String getItinId() {
        return this.itinId;
    }

    protected void setItinId(String itinId) {
        this.itinId = itinId;
    }

    public Date getItinIdCreated() {
        return this.itinIdCreated;
    }

    protected void setItinIdCreated(Date itinIdCreated) {
        this.itinIdCreated = itinIdCreated;
    }

    public String getItinIdCreator() {
        return this.itinIdCreator;
    }

    protected void setItinIdCreator(String itinIdCreator) {
        this.itinIdCreator = itinIdCreator;
    }

    public Date getItinIdChanged() {
        return this.itinIdChanged;
    }

    protected void setItinIdChanged(Date itinIdChanged) {
        this.itinIdChanged = itinIdChanged;
    }

    public String getItinIdChanger() {
        return this.itinIdChanger;
    }

    protected void setItinIdChanger(String itinIdChanger) {
        this.itinIdChanger = itinIdChanger;
    }

    public LifeCycleStateEnum getItinLifeCycleState() {
        return this.itinLifeCycleState;
    }

    public void setItinLifeCycleState(LifeCycleStateEnum itinLifeCycleState) {
        this.itinLifeCycleState = itinLifeCycleState;
    }

    public CarrierVisit getItinOwnerCv() {
        return this.itinOwnerCv;
    }

    protected void setItinOwnerCv(CarrierVisit itinOwnerCv) {
        this.itinOwnerCv = itinOwnerCv;
    }

    public List getItinPoints() {
        return this.itinPoints;
    }

    protected void setItinPoints(List itinPoints) {
        this.itinPoints = itinPoints;
    }

}
