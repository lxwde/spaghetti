package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.business.enums.framework.LifeCycleStateEnum;
import com.zpmc.ztos.infra.base.business.model.ScopedBizUnit;
import com.zpmc.ztos.infra.base.common.scopes.Complex;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

public class EdiTradingPartnerDO {
    private Long ediptnrGkey;
    private String ediptnrName;
    private Boolean ediptnrIsRetired;
    private Date ediptnrCreated;
    private String ediptnrCreator;
    private Date ediptnrChanged;
    private String ediptnrChanger;
    private LifeCycleStateEnum ediptnrLifeCycleState;
    private ScopedBizUnit ediptnrBusinessUnit;
    private Complex ediptnrComplex;
    private Set ediptnrMailboxes;

    public Serializable getPrimaryKey() {
        return this.getEdiptnrGkey();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        Long id = this.getEdiptnrGkey();
        if (id == null) {
            return false;
        }
        if (!(other instanceof EdiTradingPartnerDO)) {
            return false;
        }
        EdiTradingPartnerDO that = (EdiTradingPartnerDO)other;
        return ((Object)id).equals(that.getEdiptnrGkey());
    }

    public int hashCode() {
        Long id = this.getEdiptnrGkey();
        return id == null ? System.identityHashCode(this) : ((Object)id).hashCode();
    }

    public Long getEdiptnrGkey() {
        return this.ediptnrGkey;
    }

    protected void setEdiptnrGkey(Long ediptnrGkey) {
        this.ediptnrGkey = ediptnrGkey;
    }

    public String getEdiptnrName() {
        return this.ediptnrName;
    }

    protected void setEdiptnrName(String ediptnrName) {
        this.ediptnrName = ediptnrName;
    }

    public Boolean getEdiptnrIsRetired() {
        return this.ediptnrIsRetired;
    }

    protected void setEdiptnrIsRetired(Boolean ediptnrIsRetired) {
        this.ediptnrIsRetired = ediptnrIsRetired;
    }

    public Date getEdiptnrCreated() {
        return this.ediptnrCreated;
    }

    protected void setEdiptnrCreated(Date ediptnrCreated) {
        this.ediptnrCreated = ediptnrCreated;
    }

    public String getEdiptnrCreator() {
        return this.ediptnrCreator;
    }

    protected void setEdiptnrCreator(String ediptnrCreator) {
        this.ediptnrCreator = ediptnrCreator;
    }

    public Date getEdiptnrChanged() {
        return this.ediptnrChanged;
    }

    protected void setEdiptnrChanged(Date ediptnrChanged) {
        this.ediptnrChanged = ediptnrChanged;
    }

    public String getEdiptnrChanger() {
        return this.ediptnrChanger;
    }

    protected void setEdiptnrChanger(String ediptnrChanger) {
        this.ediptnrChanger = ediptnrChanger;
    }

    public LifeCycleStateEnum getEdiptnrLifeCycleState() {
        return this.ediptnrLifeCycleState;
    }

    public void setEdiptnrLifeCycleState(LifeCycleStateEnum ediptnrLifeCycleState) {
        this.ediptnrLifeCycleState = ediptnrLifeCycleState;
    }

    public ScopedBizUnit getEdiptnrBusinessUnit() {
        return this.ediptnrBusinessUnit;
    }

    protected void setEdiptnrBusinessUnit(ScopedBizUnit ediptnrBusinessUnit) {
        this.ediptnrBusinessUnit = ediptnrBusinessUnit;
    }

    public Complex getEdiptnrComplex() {
        return this.ediptnrComplex;
    }

    protected void setEdiptnrComplex(Complex ediptnrComplex) {
        this.ediptnrComplex = ediptnrComplex;
    }

    public Set getEdiptnrMailboxes() {
        return this.ediptnrMailboxes;
    }

    protected void setEdiptnrMailboxes(Set ediptnrMailboxes) {
        this.ediptnrMailboxes = ediptnrMailboxes;
    }

}
