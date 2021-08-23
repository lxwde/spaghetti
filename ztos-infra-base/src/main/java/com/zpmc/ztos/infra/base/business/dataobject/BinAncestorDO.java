package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.business.enums.framework.LifeCycleStateEnum;
import com.zpmc.ztos.infra.base.business.model.AbstractBin;
import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;

import java.io.Serializable;

public class BinAncestorDO extends DatabaseEntity implements Serializable {
    private Long banGkey;
    private Long banBinLevel;
    private Long banAncestorLevel;
    private LifeCycleStateEnum banLifeCycleState;
    private AbstractBin banBin;
    private AbstractBin banAncestorBin;

    public Serializable getPrimaryKey() {
        return this.getBanGkey();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        Long id = this.getBanGkey();
        if (id == null) {
            return false;
        }
        if (!(other instanceof BinAncestorDO)) {
            return false;
        }
        BinAncestorDO that = (BinAncestorDO)other;
        return ((Object)id).equals(that.getBanGkey());
    }

    public int hashCode() {
        Long id = this.getBanGkey();
        return id == null ? System.identityHashCode(this) : ((Object)id).hashCode();
    }

    public Long getBanGkey() {
        return this.banGkey;
    }

    protected void setBanGkey(Long banGkey) {
        this.banGkey = banGkey;
    }

    public Long getBanBinLevel() {
        return this.banBinLevel;
    }

    protected void setBanBinLevel(Long banBinLevel) {
        this.banBinLevel = banBinLevel;
    }

    public Long getBanAncestorLevel() {
        return this.banAncestorLevel;
    }

    protected void setBanAncestorLevel(Long banAncestorLevel) {
        this.banAncestorLevel = banAncestorLevel;
    }

    public LifeCycleStateEnum getBanLifeCycleState() {
        return this.banLifeCycleState;
    }

    public void setBanLifeCycleState(LifeCycleStateEnum banLifeCycleState) {
        this.banLifeCycleState = banLifeCycleState;
    }

    public AbstractBin getBanBin() {
        return this.banBin;
    }

    protected void setBanBin(AbstractBin banBin) {
        this.banBin = banBin;
    }

    public AbstractBin getBanAncestorBin() {
        return this.banAncestorBin;
    }

    protected void setBanAncestorBin(AbstractBin banAncestorBin) {
        this.banAncestorBin = banAncestorBin;
    }

}
