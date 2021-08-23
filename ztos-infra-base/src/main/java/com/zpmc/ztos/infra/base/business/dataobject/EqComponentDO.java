package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.business.enums.argo.EquipClassEnum;
import com.zpmc.ztos.infra.base.business.enums.framework.LifeCycleStateEnum;
import com.zpmc.ztos.infra.base.business.model.EntitySet;
import com.zpmc.ztos.infra.base.business.model.ReferenceEntity;

import java.io.Serializable;
import java.util.Date;

public abstract class EqComponentDO extends ReferenceEntity implements Serializable {
    private Long eqcmpGkey;
    private String eqcmpId;
    private String eqcmpDescription;
    private EquipClassEnum eqcmpEqClass;
    private Date eqcmpCreated;
    private String eqcmpCreator;
    private Date eqcmpChanged;
    private String eqcmpChanger;
    private LifeCycleStateEnum eqcmpLifeCycleState;
    private EntitySet eqcmpScope;

    public Serializable getPrimaryKey() {
        return this.getEqcmpGkey();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        Long id = this.getEqcmpGkey();
        if (id == null) {
            return false;
        }
        if (!(other instanceof EqComponentDO)) {
            return false;
        }
        EqComponentDO that = (EqComponentDO)other;
        return ((Object)id).equals(that.getEqcmpGkey());
    }

    public int hashCode() {
        Long id = this.getEqcmpGkey();
        return id == null ? System.identityHashCode(this) : ((Object)id).hashCode();
    }

    public Long getEqcmpGkey() {
        return this.eqcmpGkey;
    }

    protected void setEqcmpGkey(Long eqcmpGkey) {
        this.eqcmpGkey = eqcmpGkey;
    }

    public String getEqcmpId() {
        return this.eqcmpId;
    }

    protected void setEqcmpId(String eqcmpId) {
        this.eqcmpId = eqcmpId;
    }

    public String getEqcmpDescription() {
        return this.eqcmpDescription;
    }

    protected void setEqcmpDescription(String eqcmpDescription) {
        this.eqcmpDescription = eqcmpDescription;
    }

    public EquipClassEnum getEqcmpEqClass() {
        return this.eqcmpEqClass;
    }

    protected void setEqcmpEqClass(EquipClassEnum eqcmpEqClass) {
        this.eqcmpEqClass = eqcmpEqClass;
    }

    public Date getEqcmpCreated() {
        return this.eqcmpCreated;
    }

    protected void setEqcmpCreated(Date eqcmpCreated) {
        this.eqcmpCreated = eqcmpCreated;
    }

    public String getEqcmpCreator() {
        return this.eqcmpCreator;
    }

    protected void setEqcmpCreator(String eqcmpCreator) {
        this.eqcmpCreator = eqcmpCreator;
    }

    public Date getEqcmpChanged() {
        return this.eqcmpChanged;
    }

    protected void setEqcmpChanged(Date eqcmpChanged) {
        this.eqcmpChanged = eqcmpChanged;
    }

    public String getEqcmpChanger() {
        return this.eqcmpChanger;
    }

    protected void setEqcmpChanger(String eqcmpChanger) {
        this.eqcmpChanger = eqcmpChanger;
    }

    public LifeCycleStateEnum getEqcmpLifeCycleState() {
        return this.eqcmpLifeCycleState;
    }

    public void setEqcmpLifeCycleState(LifeCycleStateEnum eqcmpLifeCycleState) {
        this.eqcmpLifeCycleState = eqcmpLifeCycleState;
    }

    public EntitySet getEqcmpScope() {
        return this.eqcmpScope;
    }

    protected void setEqcmpScope(EntitySet eqcmpScope) {
        this.eqcmpScope = eqcmpScope;
    }


}
