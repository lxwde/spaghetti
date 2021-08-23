package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.business.enums.argo.EquipClassEnum;
import com.zpmc.ztos.infra.base.business.enums.framework.LifeCycleStateEnum;
import com.zpmc.ztos.infra.base.business.model.EntitySet;
import com.zpmc.ztos.infra.base.business.model.ReferenceEntity;

import java.io.Serializable;
import java.util.Date;

public abstract class EquipDamageTypeDO extends ReferenceEntity implements Serializable {
    private Long eqdmgtypGkey;
    private String eqdmgtypId;
    private String eqdmgtypDescription;
    private EquipClassEnum eqdmgtypEqClass;
    private Date eqdmgtypCreated;
    private String eqdmgtypCreator;
    private Date eqdmgtypChanged;
    private String eqdmgtypChanger;
    private LifeCycleStateEnum eqdmgtypLifeCycleState;
    private EntitySet eqdmgtypScope;

    public Serializable getPrimaryKey() {
        return this.getEqdmgtypGkey();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        Long id = this.getEqdmgtypGkey();
        if (id == null) {
            return false;
        }
        if (!(other instanceof EquipDamageTypeDO)) {
            return false;
        }
        EquipDamageTypeDO that = (EquipDamageTypeDO)other;
        return ((Object)id).equals(that.getEqdmgtypGkey());
    }

    public int hashCode() {
        Long id = this.getEqdmgtypGkey();
        return id == null ? System.identityHashCode(this) : ((Object)id).hashCode();
    }

    public Long getEqdmgtypGkey() {
        return this.eqdmgtypGkey;
    }

    protected void setEqdmgtypGkey(Long eqdmgtypGkey) {
        this.eqdmgtypGkey = eqdmgtypGkey;
    }

    public String getEqdmgtypId() {
        return this.eqdmgtypId;
    }

    protected void setEqdmgtypId(String eqdmgtypId) {
        this.eqdmgtypId = eqdmgtypId;
    }

    public String getEqdmgtypDescription() {
        return this.eqdmgtypDescription;
    }

    protected void setEqdmgtypDescription(String eqdmgtypDescription) {
        this.eqdmgtypDescription = eqdmgtypDescription;
    }

    public EquipClassEnum getEqdmgtypEqClass() {
        return this.eqdmgtypEqClass;
    }

    protected void setEqdmgtypEqClass(EquipClassEnum eqdmgtypEqClass) {
        this.eqdmgtypEqClass = eqdmgtypEqClass;
    }

    public Date getEqdmgtypCreated() {
        return this.eqdmgtypCreated;
    }

    protected void setEqdmgtypCreated(Date eqdmgtypCreated) {
        this.eqdmgtypCreated = eqdmgtypCreated;
    }

    public String getEqdmgtypCreator() {
        return this.eqdmgtypCreator;
    }

    protected void setEqdmgtypCreator(String eqdmgtypCreator) {
        this.eqdmgtypCreator = eqdmgtypCreator;
    }

    public Date getEqdmgtypChanged() {
        return this.eqdmgtypChanged;
    }

    protected void setEqdmgtypChanged(Date eqdmgtypChanged) {
        this.eqdmgtypChanged = eqdmgtypChanged;
    }

    public String getEqdmgtypChanger() {
        return this.eqdmgtypChanger;
    }

    protected void setEqdmgtypChanger(String eqdmgtypChanger) {
        this.eqdmgtypChanger = eqdmgtypChanger;
    }

    public LifeCycleStateEnum getEqdmgtypLifeCycleState() {
        return this.eqdmgtypLifeCycleState;
    }

    public void setEqdmgtypLifeCycleState(LifeCycleStateEnum eqdmgtypLifeCycleState) {
        this.eqdmgtypLifeCycleState = eqdmgtypLifeCycleState;
    }

    public EntitySet getEqdmgtypScope() {
        return this.eqdmgtypScope;
    }

    protected void setEqdmgtypScope(EntitySet eqdmgtypScope) {
        this.eqdmgtypScope = eqdmgtypScope;
    }
}
