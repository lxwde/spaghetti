package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.business.enums.argo.CheckDigitAlgorithmEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.EquipClassEnum;
import com.zpmc.ztos.infra.base.business.enums.framework.LifeCycleStateEnum;
import com.zpmc.ztos.infra.base.business.model.EntitySet;
import com.zpmc.ztos.infra.base.business.model.ReferenceEntity;

import java.io.Serializable;
import java.util.Date;

public abstract class EquipPrefixDO extends ReferenceEntity implements Serializable {

    private Long eqpfxGkey;
    private String eqpfxPrefix;
    private EquipClassEnum eqpfxEqClass;
    private String eqpfxOwnerId;
    private CheckDigitAlgorithmEnum eqpfxCheckDigitAlgm;
    private Date eqpfxCreated;
    private String eqpfxCreator;
    private Date eqpfxChanged;
    private String eqpfxChanger;
    private LifeCycleStateEnum eqpfxLifeCycleState;
    private EntitySet eqpfxScope;

    public Serializable getPrimaryKey() {
        return this.getEqpfxGkey();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        Long id = this.getEqpfxGkey();
        if (id == null) {
            return false;
        }
        if (!(other instanceof EquipPrefixDO)) {
            return false;
        }
        EquipPrefixDO that = (EquipPrefixDO)other;
        return ((Object)id).equals(that.getEqpfxGkey());
    }

    public int hashCode() {
        Long id = this.getEqpfxGkey();
        return id == null ? System.identityHashCode(this) : ((Object)id).hashCode();
    }

    public Long getEqpfxGkey() {
        return this.eqpfxGkey;
    }

    protected void setEqpfxGkey(Long eqpfxGkey) {
        this.eqpfxGkey = eqpfxGkey;
    }

    public String getEqpfxPrefix() {
        return this.eqpfxPrefix;
    }

    protected void setEqpfxPrefix(String eqpfxPrefix) {
        this.eqpfxPrefix = eqpfxPrefix;
    }

    public EquipClassEnum getEqpfxEqClass() {
        return this.eqpfxEqClass;
    }

    protected void setEqpfxEqClass(EquipClassEnum eqpfxEqClass) {
        this.eqpfxEqClass = eqpfxEqClass;
    }

    public String getEqpfxOwnerId() {
        return this.eqpfxOwnerId;
    }

    protected void setEqpfxOwnerId(String eqpfxOwnerId) {
        this.eqpfxOwnerId = eqpfxOwnerId;
    }

    public CheckDigitAlgorithmEnum getEqpfxCheckDigitAlgm() {
        return this.eqpfxCheckDigitAlgm;
    }

    protected void setEqpfxCheckDigitAlgm(CheckDigitAlgorithmEnum eqpfxCheckDigitAlgm) {
        this.eqpfxCheckDigitAlgm = eqpfxCheckDigitAlgm;
    }

    public Date getEqpfxCreated() {
        return this.eqpfxCreated;
    }

    protected void setEqpfxCreated(Date eqpfxCreated) {
        this.eqpfxCreated = eqpfxCreated;
    }

    public String getEqpfxCreator() {
        return this.eqpfxCreator;
    }

    protected void setEqpfxCreator(String eqpfxCreator) {
        this.eqpfxCreator = eqpfxCreator;
    }

    public Date getEqpfxChanged() {
        return this.eqpfxChanged;
    }

    protected void setEqpfxChanged(Date eqpfxChanged) {
        this.eqpfxChanged = eqpfxChanged;
    }

    public String getEqpfxChanger() {
        return this.eqpfxChanger;
    }

    protected void setEqpfxChanger(String eqpfxChanger) {
        this.eqpfxChanger = eqpfxChanger;
    }

    public LifeCycleStateEnum getEqpfxLifeCycleState() {
        return this.eqpfxLifeCycleState;
    }

    public void setEqpfxLifeCycleState(LifeCycleStateEnum eqpfxLifeCycleState) {
        this.eqpfxLifeCycleState = eqpfxLifeCycleState;
    }

    public EntitySet getEqpfxScope() {
        return this.eqpfxScope;
    }

    protected void setEqpfxScope(EntitySet eqpfxScope) {
        this.eqpfxScope = eqpfxScope;
    }

}
