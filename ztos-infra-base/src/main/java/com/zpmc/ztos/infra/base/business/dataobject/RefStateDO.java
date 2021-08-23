package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.business.model.RefCountry;
import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * 州/省
 *
 * @author yejun
 */
@Data
public abstract class RefStateDO extends DatabaseEntity implements Serializable {

    private Long stateGkey;
    private String stateCode;
    private String stateName;
    private RefCountry stateCntry;

    @Override
    public Serializable getPrimaryKey() {
        return this.getStateGkey();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        Long id = this.getStateGkey();
        if (id == null) {
            return false;
        }
        if (!(other instanceof RefStateDO)) {
            return false;
        }
        RefStateDO that = (RefStateDO)other;
        return ((Object)id).equals(that.getStateGkey());
    }

    public int hashCode() {
        Long id = this.getStateGkey();
        return id == null ? System.identityHashCode(this) : ((Object)id).hashCode();
    }

    public Long getStateGkey() {
        return this.stateGkey;
    }

    protected void setStateGkey(Long stateGkey) {
        this.stateGkey = stateGkey;
    }

    public String getStateCode() {
        return this.stateCode;
    }

    protected void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getStateName() {
        return this.stateName;
    }

    protected void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public RefCountry getStateCntry() {
        return this.stateCntry;
    }

    protected void setStateCntry(RefCountry stateCntry) {
        this.stateCntry = stateCntry;
    }
}
