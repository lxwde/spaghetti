package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.business.enums.argo.EquipClassEnum;
import com.zpmc.ztos.infra.base.business.enums.framework.LifeCycleStateEnum;
import com.zpmc.ztos.infra.base.business.model.EntitySet;
import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;

import java.io.Serializable;
import java.util.Date;

public class EquipConditionDO extends DatabaseEntity implements Serializable {
    private Long eqcondGkey;
    private String eqcondId;
    private EquipClassEnum eqcondEqClass;
    private Boolean eqcondIsPersist;
    private String eqcondDescription;
    private Date eqcondCreated;
    private String eqcondCreator;
    private Date eqcondChanged;
    private String eqcondChanger;
    private LifeCycleStateEnum eqcondLifeCycleState;
    private EntitySet eqcondScope;

    public Serializable getPrimaryKey() {
        return this.getEqcondGkey();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        Long id = this.getEqcondGkey();
        if (id == null) {
            return false;
        }
        if (!(other instanceof EquipConditionDO)) {
            return false;
        }
        EquipConditionDO that = (EquipConditionDO)other;
        return ((Object)id).equals(that.getEqcondGkey());
    }

    public int hashCode() {
        Long id = this.getEqcondGkey();
        return id == null ? System.identityHashCode(this) : ((Object)id).hashCode();
    }

    public Long getEqcondGkey() {
        return this.eqcondGkey;
    }

    protected void setEqcondGkey(Long eqcondGkey) {
        this.eqcondGkey = eqcondGkey;
    }

    public String getEqcondId() {
        return this.eqcondId;
    }

    protected void setEqcondId(String eqcondId) {
        this.eqcondId = eqcondId;
    }

    public EquipClassEnum getEqcondEqClass() {
        return this.eqcondEqClass;
    }

    protected void setEqcondEqClass(EquipClassEnum eqcondEqClass) {
        this.eqcondEqClass = eqcondEqClass;
    }

    protected Boolean getEqcondIsPersist() {
        return this.eqcondIsPersist;
    }

    protected void setEqcondIsPersist(Boolean eqcondIsPersist) {
        this.eqcondIsPersist = eqcondIsPersist;
    }

    public String getEqcondDescription() {
        return this.eqcondDescription;
    }

    protected void setEqcondDescription(String eqcondDescription) {
        this.eqcondDescription = eqcondDescription;
    }

    public Date getEqcondCreated() {
        return this.eqcondCreated;
    }

    protected void setEqcondCreated(Date eqcondCreated) {
        this.eqcondCreated = eqcondCreated;
    }

    public String getEqcondCreator() {
        return this.eqcondCreator;
    }

    protected void setEqcondCreator(String eqcondCreator) {
        this.eqcondCreator = eqcondCreator;
    }

    public Date getEqcondChanged() {
        return this.eqcondChanged;
    }

    protected void setEqcondChanged(Date eqcondChanged) {
        this.eqcondChanged = eqcondChanged;
    }

    public String getEqcondChanger() {
        return this.eqcondChanger;
    }

    protected void setEqcondChanger(String eqcondChanger) {
        this.eqcondChanger = eqcondChanger;
    }

    public LifeCycleStateEnum getEqcondLifeCycleState() {
        return this.eqcondLifeCycleState;
    }

    public void setEqcondLifeCycleState(LifeCycleStateEnum eqcondLifeCycleState) {
        this.eqcondLifeCycleState = eqcondLifeCycleState;
    }

    public EntitySet getEqcondScope() {
        return this.eqcondScope;
    }

    protected void setEqcondScope(EntitySet eqcondScope) {
        this.eqcondScope = eqcondScope;
    }
}
