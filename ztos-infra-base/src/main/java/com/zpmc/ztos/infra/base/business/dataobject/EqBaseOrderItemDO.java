package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.business.equipments.EqBaseOrder;
import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;

import java.io.Serializable;
import java.util.Date;

public class EqBaseOrderItemDO extends DatabaseEntity implements Serializable {

    private Long eqboiGkey;
    private Date eqboiCreated;
    private String eqboiCreator;
    private Date eqboiChanged;
    private String eqboiChanger;
    private EqBaseOrder eqboiOrder;

    public Serializable getPrimaryKey() {
        return this.getEqboiGkey();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        Long id = this.getEqboiGkey();
        if (id == null) {
            return false;
        }
        if (!(other instanceof EqBaseOrderItemDO)) {
            return false;
        }
        EqBaseOrderItemDO that = (EqBaseOrderItemDO)other;
        return ((Object)id).equals(that.getEqboiGkey());
    }

    public int hashCode() {
        Long id = this.getEqboiGkey();
        return id == null ? System.identityHashCode(this) : ((Object)id).hashCode();
    }

    public Long getEqboiGkey() {
        return this.eqboiGkey;
    }

    protected void setEqboiGkey(Long eqboiGkey) {
        this.eqboiGkey = eqboiGkey;
    }

    public Date getEqboiCreated() {
        return this.eqboiCreated;
    }

    protected void setEqboiCreated(Date eqboiCreated) {
        this.eqboiCreated = eqboiCreated;
    }

    public String getEqboiCreator() {
        return this.eqboiCreator;
    }

    protected void setEqboiCreator(String eqboiCreator) {
        this.eqboiCreator = eqboiCreator;
    }

    public Date getEqboiChanged() {
        return this.eqboiChanged;
    }

    protected void setEqboiChanged(Date eqboiChanged) {
        this.eqboiChanged = eqboiChanged;
    }

    public String getEqboiChanger() {
        return this.eqboiChanger;
    }

    protected void setEqboiChanger(String eqboiChanger) {
        this.eqboiChanger = eqboiChanger;
    }

    public EqBaseOrder getEqboiOrder() {
        return this.eqboiOrder;
    }

    protected void setEqboiOrder(EqBaseOrder eqboiOrder) {
        this.eqboiOrder = eqboiOrder;
    }
}
