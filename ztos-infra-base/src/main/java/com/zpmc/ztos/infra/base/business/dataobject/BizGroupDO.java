package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.business.enums.framework.LifeCycleStateEnum;
import com.zpmc.ztos.infra.base.business.model.EntitySet;
import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;

import java.io.Serializable;
import java.util.Set;

public class BizGroupDO extends DatabaseEntity implements Serializable {
    private Long bizgrpGkey;
    private String bizgrpId;
    private String bizgrpName;
    private LifeCycleStateEnum bizgrpLifeCycleState;
    private EntitySet bizgrpScope;
    private Set bizgrpBusinessUnits;

    public Serializable getPrimaryKey() {
        return this.getBizgrpGkey();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        Long id = this.getBizgrpGkey();
        if (id == null) {
            return false;
        }
        if (!(other instanceof BizGroupDO)) {
            return false;
        }
        BizGroupDO that = (BizGroupDO)other;
        return ((Object)id).equals(that.getBizgrpGkey());
    }

    public int hashCode() {
        Long id = this.getBizgrpGkey();
        return id == null ? System.identityHashCode(this) : ((Object)id).hashCode();
    }

    public Long getBizgrpGkey() {
        return this.bizgrpGkey;
    }

    protected void setBizgrpGkey(Long bizgrpGkey) {
        this.bizgrpGkey = bizgrpGkey;
    }

    public String getBizgrpId() {
        return this.bizgrpId;
    }

    protected void setBizgrpId(String bizgrpId) {
        this.bizgrpId = bizgrpId;
    }

    public String getBizgrpName() {
        return this.bizgrpName;
    }

    protected void setBizgrpName(String bizgrpName) {
        this.bizgrpName = bizgrpName;
    }

    public LifeCycleStateEnum getBizgrpLifeCycleState() {
        return this.bizgrpLifeCycleState;
    }

    public void setBizgrpLifeCycleState(LifeCycleStateEnum bizgrpLifeCycleState) {
        this.bizgrpLifeCycleState = bizgrpLifeCycleState;
    }

    public EntitySet getBizgrpScope() {
        return this.bizgrpScope;
    }

    protected void setBizgrpScope(EntitySet bizgrpScope) {
        this.bizgrpScope = bizgrpScope;
    }

    public Set getBizgrpBusinessUnits() {
        return this.bizgrpBusinessUnits;
    }

    protected void setBizgrpBusinessUnits(Set bizgrpBusinessUnits) {
        this.bizgrpBusinessUnits = bizgrpBusinessUnits;
    }
}
