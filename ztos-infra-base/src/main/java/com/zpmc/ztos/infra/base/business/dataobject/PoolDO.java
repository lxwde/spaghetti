package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.business.enums.framework.LifeCycleStateEnum;
import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;
import com.zpmc.ztos.infra.base.common.scopes.Complex;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

public class PoolDO extends DatabaseEntity implements Serializable {
    private Long poolGkey;
    private String poolId;
    private String poolName;
    private String poolTag;
    private String poolAdminName;
    private String poolAdminPhone;
    private String poolAdminMobile;
    private String poolAdminFax;
    private String poolAdminEmail;
    private LifeCycleStateEnum poolAdminLifeCycleState;
    private Date poolCreated;
    private String poolCreator;
    private Date poolChanged;
    private String poolChanger;
    private Complex poolComplex;
    private Set poolMembers;
    private Set poolEquipment;
    private Set poolTruckCos;

    public Serializable getPrimaryKey() {
        return this.getPoolGkey();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        Long id = this.getPoolGkey();
        if (id == null) {
            return false;
        }
        if (!(other instanceof PoolDO)) {
            return false;
        }
        PoolDO that = (PoolDO)other;
        return ((Object)id).equals(that.getPoolGkey());
    }

    public int hashCode() {
        Long id = this.getPoolGkey();
        return id == null ? System.identityHashCode(this) : ((Object)id).hashCode();
    }

    public Long getPoolGkey() {
        return this.poolGkey;
    }

    protected void setPoolGkey(Long poolGkey) {
        this.poolGkey = poolGkey;
    }

    public String getPoolId() {
        return this.poolId;
    }

    protected void setPoolId(String poolId) {
        this.poolId = poolId;
    }

    public String getPoolName() {
        return this.poolName;
    }

    protected void setPoolName(String poolName) {
        this.poolName = poolName;
    }

    public String getPoolTag() {
        return this.poolTag;
    }

    protected void setPoolTag(String poolTag) {
        this.poolTag = poolTag;
    }

    public String getPoolAdminName() {
        return this.poolAdminName;
    }

    protected void setPoolAdminName(String poolAdminName) {
        this.poolAdminName = poolAdminName;
    }

    public String getPoolAdminPhone() {
        return this.poolAdminPhone;
    }

    protected void setPoolAdminPhone(String poolAdminPhone) {
        this.poolAdminPhone = poolAdminPhone;
    }

    public String getPoolAdminMobile() {
        return this.poolAdminMobile;
    }

    protected void setPoolAdminMobile(String poolAdminMobile) {
        this.poolAdminMobile = poolAdminMobile;
    }

    public String getPoolAdminFax() {
        return this.poolAdminFax;
    }

    protected void setPoolAdminFax(String poolAdminFax) {
        this.poolAdminFax = poolAdminFax;
    }

    public String getPoolAdminEmail() {
        return this.poolAdminEmail;
    }

    protected void setPoolAdminEmail(String poolAdminEmail) {
        this.poolAdminEmail = poolAdminEmail;
    }

    public LifeCycleStateEnum getPoolAdminLifeCycleState() {
        return this.poolAdminLifeCycleState;
    }

    public void setPoolAdminLifeCycleState(LifeCycleStateEnum poolAdminLifeCycleState) {
        this.poolAdminLifeCycleState = poolAdminLifeCycleState;
    }

    public Date getPoolCreated() {
        return this.poolCreated;
    }

    protected void setPoolCreated(Date poolCreated) {
        this.poolCreated = poolCreated;
    }

    public String getPoolCreator() {
        return this.poolCreator;
    }

    protected void setPoolCreator(String poolCreator) {
        this.poolCreator = poolCreator;
    }

    public Date getPoolChanged() {
        return this.poolChanged;
    }

    protected void setPoolChanged(Date poolChanged) {
        this.poolChanged = poolChanged;
    }

    public String getPoolChanger() {
        return this.poolChanger;
    }

    protected void setPoolChanger(String poolChanger) {
        this.poolChanger = poolChanger;
    }

    public Complex getPoolComplex() {
        return this.poolComplex;
    }

    protected void setPoolComplex(Complex poolComplex) {
        this.poolComplex = poolComplex;
    }

    public Set getPoolMembers() {
        return this.poolMembers;
    }

    protected void setPoolMembers(Set poolMembers) {
        this.poolMembers = poolMembers;
    }

    public Set getPoolEquipment() {
        return this.poolEquipment;
    }

    protected void setPoolEquipment(Set poolEquipment) {
        this.poolEquipment = poolEquipment;
    }

    public Set getPoolTruckCos() {
        return this.poolTruckCos;
    }

    protected void setPoolTruckCos(Set poolTruckCos) {
        this.poolTruckCos = poolTruckCos;
    }
}
