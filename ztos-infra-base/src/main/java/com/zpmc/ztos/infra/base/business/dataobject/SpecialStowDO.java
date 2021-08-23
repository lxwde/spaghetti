package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.business.enums.framework.LifeCycleStateEnum;
import com.zpmc.ztos.infra.base.business.model.EntitySet;
import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;

import java.io.Serializable;
import java.util.Date;

public class SpecialStowDO extends DatabaseEntity implements Serializable {

    private Long stwGkey;
    private String stwId;
    private String stwDescription;
    private Date stwCreated;
    private String stwCreator;
    private Date stwChanged;
    private String stwChanger;
    private LifeCycleStateEnum stwLifeCycleState;
    private EntitySet stwScope;

    public Serializable getPrimaryKey() {
        return this.getStwGkey();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        Long id = this.getStwGkey();
        if (id == null) {
            return false;
        }
        if (!(other instanceof SpecialStowDO)) {
            return false;
        }
        SpecialStowDO that = (SpecialStowDO)other;
        return ((Object)id).equals(that.getStwGkey());
    }

    public int hashCode() {
        Long id = this.getStwGkey();
        return id == null ? System.identityHashCode(this) : ((Object)id).hashCode();
    }

    public Long getStwGkey() {
        return this.stwGkey;
    }

    protected void setStwGkey(Long stwGkey) {
        this.stwGkey = stwGkey;
    }

    public String getStwId() {
        return this.stwId;
    }

    protected void setStwId(String stwId) {
        this.stwId = stwId;
    }

    public String getStwDescription() {
        return this.stwDescription;
    }

    protected void setStwDescription(String stwDescription) {
        this.stwDescription = stwDescription;
    }

    public Date getStwCreated() {
        return this.stwCreated;
    }

    protected void setStwCreated(Date stwCreated) {
        this.stwCreated = stwCreated;
    }

    public String getStwCreator() {
        return this.stwCreator;
    }

    protected void setStwCreator(String stwCreator) {
        this.stwCreator = stwCreator;
    }

    public Date getStwChanged() {
        return this.stwChanged;
    }

    protected void setStwChanged(Date stwChanged) {
        this.stwChanged = stwChanged;
    }

    public String getStwChanger() {
        return this.stwChanger;
    }

    protected void setStwChanger(String stwChanger) {
        this.stwChanger = stwChanger;
    }

    public LifeCycleStateEnum getStwLifeCycleState() {
        return this.stwLifeCycleState;
    }

    public void setStwLifeCycleState(LifeCycleStateEnum stwLifeCycleState) {
        this.stwLifeCycleState = stwLifeCycleState;
    }

    public EntitySet getStwScope() {
        return this.stwScope;
    }

    protected void setStwScope(EntitySet stwScope) {
        this.stwScope = stwScope;
    }
}
