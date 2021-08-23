package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;
import com.zpmc.ztos.infra.base.common.scopes.Yard;

import java.io.Serializable;

public class ChePoolDO extends DatabaseEntity implements Serializable {
    private Long poolGkey;
    private Long poolPkey;
    private String poolOwner;
    private String poolName;
    private Boolean poolLocked;
    private Boolean poolDispatcherLocked;
    private Boolean poolIttPool;
    private Boolean poolPrimeRoutePool;
    private Long poolDispatcherAddress;
    private Yard poolYard;

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
        if (!(other instanceof ChePoolDO)) {
            return false;
        }
        ChePoolDO that = (ChePoolDO)other;
        return ((Object)id).equals(that.getPoolGkey());
    }

    public int hashCode() {
        Long id = this.getPoolGkey();
        return id == null ? System.identityHashCode(this) : ((Object)id).hashCode();
    }

    public Long getPoolGkey() {
        return this.poolGkey;
    }

    public void setPoolGkey(Long poolGkey) {
        this.poolGkey = poolGkey;
    }

    public Long getPoolPkey() {
        return this.poolPkey;
    }

    public void setPoolPkey(Long poolPkey) {
        this.poolPkey = poolPkey;
    }

    public String getPoolOwner() {
        return this.poolOwner;
    }

    public void setPoolOwner(String poolOwner) {
        this.poolOwner = poolOwner;
    }

    public String getPoolName() {
        return this.poolName;
    }

    public void setPoolName(String poolName) {
        this.poolName = poolName;
    }

    public Boolean getPoolLocked() {
        return this.poolLocked;
    }

    public void setPoolLocked(Boolean poolLocked) {
        this.poolLocked = poolLocked;
    }

    public Boolean getPoolDispatcherLocked() {
        return this.poolDispatcherLocked;
    }

    public void setPoolDispatcherLocked(Boolean poolDispatcherLocked) {
        this.poolDispatcherLocked = poolDispatcherLocked;
    }

    public Boolean getPoolIttPool() {
        return this.poolIttPool;
    }

    public void setPoolIttPool(Boolean poolIttPool) {
        this.poolIttPool = poolIttPool;
    }

    public Boolean getPoolPrimeRoutePool() {
        return this.poolPrimeRoutePool;
    }

    public void setPoolPrimeRoutePool(Boolean poolPrimeRoutePool) {
        this.poolPrimeRoutePool = poolPrimeRoutePool;
    }

    public Long getPoolDispatcherAddress() {
        return this.poolDispatcherAddress;
    }

    public void setPoolDispatcherAddress(Long poolDispatcherAddress) {
        this.poolDispatcherAddress = poolDispatcherAddress;
    }

    public Yard getPoolYard() {
        return this.poolYard;
    }

    public void setPoolYard(Yard poolYard) {
        this.poolYard = poolYard;
    }
}
