package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.business.model.YrdBlkSupplement;
import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;
import com.zpmc.ztos.infra.base.common.scopes.Yard;

import java.io.Serializable;
import java.util.Date;

public abstract class YrdBlkSharedYardDO extends DatabaseEntity implements Serializable {
    private Long ybyGkey;
    private Date ybyCreated;
    private String ybyCreator;
    private Date ybyChanged;
    private String ybyChanger;
    private Yard ybyYard;
    private YrdBlkSupplement ybyYrdSupl;

    public Serializable getPrimaryKey() {
        return this.getYbyGkey();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        Long id = this.getYbyGkey();
        if (id == null) {
            return false;
        }
        if (!(other instanceof YrdBlkSharedYardDO)) {
            return false;
        }
        YrdBlkSharedYardDO that = (YrdBlkSharedYardDO)other;
        return ((Object)id).equals(that.getYbyGkey());
    }

    public int hashCode() {
        Long id = this.getYbyGkey();
        return id == null ? System.identityHashCode(this) : ((Object)id).hashCode();
    }

    public Long getYbyGkey() {
        return this.ybyGkey;
    }

    protected void setYbyGkey(Long ybyGkey) {
        this.ybyGkey = ybyGkey;
    }

    public Date getYbyCreated() {
        return this.ybyCreated;
    }

    protected void setYbyCreated(Date ybyCreated) {
        this.ybyCreated = ybyCreated;
    }

    public String getYbyCreator() {
        return this.ybyCreator;
    }

    protected void setYbyCreator(String ybyCreator) {
        this.ybyCreator = ybyCreator;
    }

    public Date getYbyChanged() {
        return this.ybyChanged;
    }

    protected void setYbyChanged(Date ybyChanged) {
        this.ybyChanged = ybyChanged;
    }

    public String getYbyChanger() {
        return this.ybyChanger;
    }

    protected void setYbyChanger(String ybyChanger) {
        this.ybyChanger = ybyChanger;
    }

    public Yard getYbyYard() {
        return this.ybyYard;
    }

    protected void setYbyYard(Yard ybyYard) {
        this.ybyYard = ybyYard;
    }

    public YrdBlkSupplement getYbyYrdSupl() {
        return this.ybyYrdSupl;
    }

    protected void setYbyYrdSupl(YrdBlkSupplement ybyYrdSupl) {
        this.ybyYrdSupl = ybyYrdSupl;
    }

}
