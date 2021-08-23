package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.business.enums.framework.LifeCycleStateEnum;
import com.zpmc.ztos.infra.base.business.model.MasterBizUnit;
import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 * 操作员
 *
 * @author yejun
 */
@Data
public abstract class OperatorDO extends DatabaseEntity
implements Serializable {

    private Long oprGkey;
    private String oprId;
    private String oprName;
    private LifeCycleStateEnum oprLifeCycleState;
    private Boolean oprEnforceSingleEqUse;
    private MasterBizUnit oprBizUnit;
    private Set oprCpxSet;

    @Override
    public Serializable getPrimaryKey() {
        return this.getOprGkey();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        Long id = this.getOprGkey();
        if (id == null) {
            return false;
        }
        if (!(other instanceof OperatorDO)) {
            return false;
        }
        OperatorDO that = (OperatorDO)other;
        return ((Object)id).equals(that.getOprGkey());
    }

    @Override
    public int hashCode() {
        Long id = this.getOprGkey();
        return id == null ? System.identityHashCode(this) : ((Object)id).hashCode();
    }

    public Long getOprGkey() {
        return this.oprGkey;
    }

    protected void setOprGkey(Long oprGkey) {
        this.oprGkey = oprGkey;
    }

    public String getOprId() {
        return this.oprId;
    }

    protected void setOprId(String oprId) {
        this.oprId = oprId;
    }

    public String getOprName() {
        return this.oprName;
    }

    protected void setOprName(String oprName) {
        this.oprName = oprName;
    }

    public LifeCycleStateEnum getOprLifeCycleState() {
        return this.oprLifeCycleState;
    }

    public void setOprLifeCycleState(LifeCycleStateEnum oprLifeCycleState) {
        this.oprLifeCycleState = oprLifeCycleState;
    }

    public Boolean getOprEnforceSingleEqUse() {
        return this.oprEnforceSingleEqUse;
    }

    protected void setOprEnforceSingleEqUse(Boolean oprEnforceSingleEqUse) {
        this.oprEnforceSingleEqUse = oprEnforceSingleEqUse;
    }

    public MasterBizUnit getOprBizUnit() {
        return this.oprBizUnit;
    }

    protected void setOprBizUnit(MasterBizUnit oprBizUnit) {
        this.oprBizUnit = oprBizUnit;
    }

    public Set getOprCpxSet() {
        return this.oprCpxSet;
    }

    protected void setOprCpxSet(Set oprCpxSet) {
        this.oprCpxSet = oprCpxSet;
    }

}
