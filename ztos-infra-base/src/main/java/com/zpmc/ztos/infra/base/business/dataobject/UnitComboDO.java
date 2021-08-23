package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;

import java.io.Serializable;
import java.util.Set;

public class UnitComboDO extends DatabaseEntity implements Serializable {
    private Long ucGkey;
    private Set ucUnits;

    public Serializable getPrimaryKey() {
        return this.getUcGkey();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        Long id = this.getUcGkey();
        if (id == null) {
            return false;
        }
        if (!(other instanceof UnitComboDO)) {
            return false;
        }
        UnitComboDO that = (UnitComboDO)other;
        return ((Object)id).equals(that.getUcGkey());
    }

    public int hashCode() {
        Long id = this.getUcGkey();
        return id == null ? System.identityHashCode(this) : ((Object)id).hashCode();
    }

    public Long getUcGkey() {
        return this.ucGkey;
    }

    protected void setUcGkey(Long ucGkey) {
        this.ucGkey = ucGkey;
    }

    public Set getUcUnits() {
        return this.ucUnits;
    }

    protected void setUcUnits(Set ucUnits) {
        this.ucUnits = ucUnits;
    }
}
