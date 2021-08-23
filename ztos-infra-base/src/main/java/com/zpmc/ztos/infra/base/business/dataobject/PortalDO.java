package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.business.enums.framework.LifeCycleStateEnum;
import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;

import java.io.Serializable;

public abstract class PortalDO extends DatabaseEntity implements Serializable {
    private Long prtlGkey;
    private LifeCycleStateEnum prtlLifeCycleState;

    public Serializable getPrimaryKey() {
        return this.getPrtlGkey();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        Long id = this.getPrtlGkey();
        if (id == null) {
            return false;
        }
        if (!(other instanceof PortalDO)) {
            return false;
        }
        PortalDO that = (PortalDO)other;
        return ((Object)id).equals(that.getPrtlGkey());
    }

    public int hashCode() {
        Long id = this.getPrtlGkey();
        return id == null ? System.identityHashCode(this) : ((Object)id).hashCode();
    }

    public Long getPrtlGkey() {
        return this.prtlGkey;
    }

    protected void setPrtlGkey(Long prtlGkey) {
        this.prtlGkey = prtlGkey;
    }

    public LifeCycleStateEnum getPrtlLifeCycleState() {
        return this.prtlLifeCycleState;
    }

    public void setPrtlLifeCycleState(LifeCycleStateEnum prtlLifeCycleState) {
        this.prtlLifeCycleState = prtlLifeCycleState;
    }
}
