package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.business.enums.argo.EquipmentOrderSubTypeEnum;
import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

public class EqBaseOrderDO extends DatabaseEntity
implements Serializable {

    private Long eqboGkey;
    private EquipmentOrderSubTypeEnum eqboSubType;
    private String eqboNbr;
    private Date eqboCreated;
    private String eqboCreator;
    private Date eqboChanged;
    private String eqboChanger;
    private Set eqboOrderItems;

    public Serializable getPrimaryKey() {
        return this.getEqboGkey();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        Long id = this.getEqboGkey();
        if (id == null) {
            return false;
        }
        if (!(other instanceof EqBaseOrderDO)) {
            return false;
        }
        EqBaseOrderDO that = (EqBaseOrderDO)other;
        return ((Object)id).equals(that.getEqboGkey());
    }

    public int hashCode() {
        Long id = this.getEqboGkey();
        return id == null ? System.identityHashCode(this) : ((Object)id).hashCode();
    }

    public Long getEqboGkey() {
        return this.eqboGkey;
    }

    protected void setEqboGkey(Long eqboGkey) {
        this.eqboGkey = eqboGkey;
    }

    public EquipmentOrderSubTypeEnum getEqboSubType() {
        return this.eqboSubType;
    }

    protected void setEqboSubType(EquipmentOrderSubTypeEnum eqboSubType) {
        this.eqboSubType = eqboSubType;
    }

    public String getEqboNbr() {
        return this.eqboNbr;
    }

    protected void setEqboNbr(String eqboNbr) {
        this.eqboNbr = eqboNbr;
    }

    public Date getEqboCreated() {
        return this.eqboCreated;
    }

    protected void setEqboCreated(Date eqboCreated) {
        this.eqboCreated = eqboCreated;
    }

    public String getEqboCreator() {
        return this.eqboCreator;
    }

    protected void setEqboCreator(String eqboCreator) {
        this.eqboCreator = eqboCreator;
    }

    public Date getEqboChanged() {
        return this.eqboChanged;
    }

    protected void setEqboChanged(Date eqboChanged) {
        this.eqboChanged = eqboChanged;
    }

    public String getEqboChanger() {
        return this.eqboChanger;
    }

    protected void setEqboChanger(String eqboChanger) {
        this.eqboChanger = eqboChanger;
    }

    public Set getEqboOrderItems() {
        return this.eqboOrderItems;
    }

    protected void setEqboOrderItems(Set eqboOrderItems) {
        this.eqboOrderItems = eqboOrderItems;
    }

}
