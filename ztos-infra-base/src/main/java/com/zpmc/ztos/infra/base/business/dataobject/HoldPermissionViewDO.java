package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;

import java.io.Serializable;
import java.util.Date;

public class HoldPermissionViewDO extends DatabaseEntity implements Serializable {
    private Long hpvGkey;
    private String hpvId;
    private String hpvDescription;
    private Date hpvCreated;
    private String hpvCreator;
    private Date hpvChanged;
    private String hpvChanger;

    public Serializable getPrimaryKey() {
        return this.getHpvGkey();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        Long id = this.getHpvGkey();
        if (id == null) {
            return false;
        }
        if (!(other instanceof HoldPermissionViewDO)) {
            return false;
        }
        HoldPermissionViewDO that = (HoldPermissionViewDO)other;
        return ((Object)id).equals(that.getHpvGkey());
    }

    public int hashCode() {
        Long id = this.getHpvGkey();
        return id == null ? System.identityHashCode(this) : ((Object)id).hashCode();
    }

    public Long getHpvGkey() {
        return this.hpvGkey;
    }

    protected void setHpvGkey(Long hpvGkey) {
        this.hpvGkey = hpvGkey;
    }

    public String getHpvId() {
        return this.hpvId;
    }

    protected void setHpvId(String hpvId) {
        this.hpvId = hpvId;
    }

    public String getHpvDescription() {
        return this.hpvDescription;
    }

    protected void setHpvDescription(String hpvDescription) {
        this.hpvDescription = hpvDescription;
    }

    public Date getHpvCreated() {
        return this.hpvCreated;
    }

    protected void setHpvCreated(Date hpvCreated) {
        this.hpvCreated = hpvCreated;
    }

    public String getHpvCreator() {
        return this.hpvCreator;
    }

    protected void setHpvCreator(String hpvCreator) {
        this.hpvCreator = hpvCreator;
    }

    public Date getHpvChanged() {
        return this.hpvChanged;
    }

    protected void setHpvChanged(Date hpvChanged) {
        this.hpvChanged = hpvChanged;
    }

    public String getHpvChanger() {
        return this.hpvChanger;
    }

    protected void setHpvChanger(String hpvChanger) {
        this.hpvChanger = hpvChanger;
    }
}
