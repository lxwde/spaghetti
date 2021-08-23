package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.business.enums.argo.LocTypeEnum;
import com.zpmc.ztos.infra.base.business.enums.framework.LifeCycleStateEnum;
import com.zpmc.ztos.infra.base.business.model.CarrierItinerary;
import com.zpmc.ztos.infra.base.business.model.EntitySet;
import com.zpmc.ztos.infra.base.business.model.ReferenceEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 运送工具服务对象
 *
 * @author yejun
 */
@Data
public abstract class CarrierServiceDO extends ReferenceEntity
implements Serializable {
    private Long srvcGkey;
    private String srvcId;
    private String srvcName;
    private LocTypeEnum srvcCarrierMode;
    private Date srvcCreated;
    private String srvcCreator;
    private Date srvcChanged;
    private String srvcChanger;
    private LifeCycleStateEnum srvcLifeCycleState;
    private Long srvcPkey;
    private EntitySet srvcScope;
    private CarrierItinerary srvcItinerary;

    public Serializable getPrimaryKey() {
        return this.getSrvcGkey();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        Long id = this.getSrvcGkey();
        if (id == null) {
            return false;
        }
        if (!(other instanceof CarrierServiceDO)) {
            return false;
        }
        CarrierServiceDO that = (CarrierServiceDO)other;
        return ((Object)id).equals(that.getSrvcGkey());
    }

    public int hashCode() {
        Long id = this.getSrvcGkey();
        return id == null ? System.identityHashCode(this) : ((Object)id).hashCode();
    }

    public Long getSrvcGkey() {
        return this.srvcGkey;
    }

    protected void setSrvcGkey(Long srvcGkey) {
        this.srvcGkey = srvcGkey;
    }

    public String getSrvcId() {
        return this.srvcId;
    }

    protected void setSrvcId(String srvcId) {
        this.srvcId = srvcId;
    }

    public String getSrvcName() {
        return this.srvcName;
    }

    protected void setSrvcName(String srvcName) {
        this.srvcName = srvcName;
    }

    public LocTypeEnum getSrvcCarrierMode() {
        return this.srvcCarrierMode;
    }

    protected void setSrvcCarrierMode(LocTypeEnum srvcCarrierMode) {
        this.srvcCarrierMode = srvcCarrierMode;
    }

    public Date getSrvcCreated() {
        return this.srvcCreated;
    }

    protected void setSrvcCreated(Date srvcCreated) {
        this.srvcCreated = srvcCreated;
    }

    public String getSrvcCreator() {
        return this.srvcCreator;
    }

    protected void setSrvcCreator(String srvcCreator) {
        this.srvcCreator = srvcCreator;
    }

    public Date getSrvcChanged() {
        return this.srvcChanged;
    }

    protected void setSrvcChanged(Date srvcChanged) {
        this.srvcChanged = srvcChanged;
    }

    public String getSrvcChanger() {
        return this.srvcChanger;
    }

    protected void setSrvcChanger(String srvcChanger) {
        this.srvcChanger = srvcChanger;
    }

    public LifeCycleStateEnum getSrvcLifeCycleState() {
        return this.srvcLifeCycleState;
    }

    public void setSrvcLifeCycleState(LifeCycleStateEnum srvcLifeCycleState) {
        this.srvcLifeCycleState = srvcLifeCycleState;
    }

    public Long getSrvcPkey() {
        return this.srvcPkey;
    }

    protected void setSrvcPkey(Long srvcPkey) {
        this.srvcPkey = srvcPkey;
    }

    public EntitySet getSrvcScope() {
        return this.srvcScope;
    }

    protected void setSrvcScope(EntitySet srvcScope) {
        this.srvcScope = srvcScope;
    }

    public CarrierItinerary getSrvcItinerary() {
        return this.srvcItinerary;
    }

    protected void setSrvcItinerary(CarrierItinerary srvcItinerary) {
        this.srvcItinerary = srvcItinerary;
    }


}
