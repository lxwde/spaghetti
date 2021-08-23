package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.business.enums.argo.EquipClassEnum;
import com.zpmc.ztos.infra.base.business.enums.framework.LifeCycleStateEnum;
import com.zpmc.ztos.infra.base.business.model.EntitySet;
import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;

import java.io.Serializable;
import java.util.Date;

public class EquipGradeDO extends DatabaseEntity implements Serializable {
    private Long eqgrdGkey;
    private String eqgrdId;
    private EquipClassEnum eqgrdEqClass;
    private Boolean eqgrdIsPersist;
    private String eqgrdDescription;
    private Date eqgrdCreated;
    private String eqgrdCreator;
    private Date eqgrdChanged;
    private String eqgrdChanger;
    private LifeCycleStateEnum eqgrdLifeCycleState;
    private EntitySet eqgrdScope;

    public Serializable getPrimaryKey() {
        return this.getEqgrdGkey();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        Long id = this.getEqgrdGkey();
        if (id == null) {
            return false;
        }
        if (!(other instanceof EquipGradeDO)) {
            return false;
        }
        EquipGradeDO that = (EquipGradeDO)other;
        return ((Object)id).equals(that.getEqgrdGkey());
    }

    public int hashCode() {
        Long id = this.getEqgrdGkey();
        return id == null ? System.identityHashCode(this) : ((Object)id).hashCode();
    }

    public Long getEqgrdGkey() {
        return this.eqgrdGkey;
    }

    protected void setEqgrdGkey(Long eqgrdGkey) {
        this.eqgrdGkey = eqgrdGkey;
    }

    public String getEqgrdId() {
        return this.eqgrdId;
    }

    protected void setEqgrdId(String eqgrdId) {
        this.eqgrdId = eqgrdId;
    }

    public EquipClassEnum getEqgrdEqClass() {
        return this.eqgrdEqClass;
    }

    protected void setEqgrdEqClass(EquipClassEnum eqgrdEqClass) {
        this.eqgrdEqClass = eqgrdEqClass;
    }

    protected Boolean getEqgrdIsPersist() {
        return this.eqgrdIsPersist;
    }

    protected void setEqgrdIsPersist(Boolean eqgrdIsPersist) {
        this.eqgrdIsPersist = eqgrdIsPersist;
    }

    public String getEqgrdDescription() {
        return this.eqgrdDescription;
    }

    protected void setEqgrdDescription(String eqgrdDescription) {
        this.eqgrdDescription = eqgrdDescription;
    }

    public Date getEqgrdCreated() {
        return this.eqgrdCreated;
    }

    protected void setEqgrdCreated(Date eqgrdCreated) {
        this.eqgrdCreated = eqgrdCreated;
    }

    public String getEqgrdCreator() {
        return this.eqgrdCreator;
    }

    protected void setEqgrdCreator(String eqgrdCreator) {
        this.eqgrdCreator = eqgrdCreator;
    }

    public Date getEqgrdChanged() {
        return this.eqgrdChanged;
    }

    protected void setEqgrdChanged(Date eqgrdChanged) {
        this.eqgrdChanged = eqgrdChanged;
    }

    public String getEqgrdChanger() {
        return this.eqgrdChanger;
    }

    protected void setEqgrdChanger(String eqgrdChanger) {
        this.eqgrdChanger = eqgrdChanger;
    }

    public LifeCycleStateEnum getEqgrdLifeCycleState() {
        return this.eqgrdLifeCycleState;
    }

    public void setEqgrdLifeCycleState(LifeCycleStateEnum eqgrdLifeCycleState) {
        this.eqgrdLifeCycleState = eqgrdLifeCycleState;
    }

    public EntitySet getEqgrdScope() {
        return this.eqgrdScope;
    }

    protected void setEqgrdScope(EntitySet eqgrdScope) {
        this.eqgrdScope = eqgrdScope;
    }

}
