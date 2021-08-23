package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;
import com.zpmc.ztos.infra.base.common.scopes.Complex;
import com.zpmc.ztos.infra.base.common.scopes.Yard;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

public abstract class YrdBlkSupplementDO extends DatabaseEntity implements Serializable {
    private Long ybsGkey;
    private String ybsBlockId;
    private Date ybsCreated;
    private String ybsCreator;
    private Date ybsChanged;
    private String ybsChanger;
    private Yard ybsYard;
    private Complex ybsComplex;
    private Set ybsYbySet;

    public Serializable getPrimaryKey() {
        return this.getYbsGkey();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        Long id = this.getYbsGkey();
        if (id == null) {
            return false;
        }
        if (!(other instanceof YrdBlkSupplementDO)) {
            return false;
        }
        YrdBlkSupplementDO that = (YrdBlkSupplementDO)other;
        return ((Object)id).equals(that.getYbsGkey());
    }

    public int hashCode() {
        Long id = this.getYbsGkey();
        return id == null ? System.identityHashCode(this) : ((Object)id).hashCode();
    }

    public Long getYbsGkey() {
        return this.ybsGkey;
    }

    protected void setYbsGkey(Long ybsGkey) {
        this.ybsGkey = ybsGkey;
    }

    public String getYbsBlockId() {
        return this.ybsBlockId;
    }

    protected void setYbsBlockId(String ybsBlockId) {
        this.ybsBlockId = ybsBlockId;
    }

    public Date getYbsCreated() {
        return this.ybsCreated;
    }

    protected void setYbsCreated(Date ybsCreated) {
        this.ybsCreated = ybsCreated;
    }

    public String getYbsCreator() {
        return this.ybsCreator;
    }

    protected void setYbsCreator(String ybsCreator) {
        this.ybsCreator = ybsCreator;
    }

    public Date getYbsChanged() {
        return this.ybsChanged;
    }

    protected void setYbsChanged(Date ybsChanged) {
        this.ybsChanged = ybsChanged;
    }

    public String getYbsChanger() {
        return this.ybsChanger;
    }

    protected void setYbsChanger(String ybsChanger) {
        this.ybsChanger = ybsChanger;
    }

    public Yard getYbsYard() {
        return this.ybsYard;
    }

    protected void setYbsYard(Yard ybsYard) {
        this.ybsYard = ybsYard;
    }

    public Complex getYbsComplex() {
        return this.ybsComplex;
    }

    protected void setYbsComplex(Complex ybsComplex) {
        this.ybsComplex = ybsComplex;
    }

    public Set getYbsYbySet() {
        return this.ybsYbySet;
    }

    protected void setYbsYbySet(Set ybsYbySet) {
        this.ybsYbySet = ybsYbySet;
    }
}
