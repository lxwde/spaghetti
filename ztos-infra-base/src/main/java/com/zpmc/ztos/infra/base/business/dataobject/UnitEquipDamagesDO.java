package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class UnitEquipDamagesDO extends DatabaseEntity implements Serializable {
    private Long dmgsGkey;
    private String dmgsOwnerEntityName;
    private Long dmgsOwnerEntityGkey;
    private Date dmgsCreated;
    private String dmgsCreator;
    private Date dmgsChanged;
    private String dmgsChanger;
    private List dmgsItems;

    public Serializable getPrimaryKey() {
        return this.getDmgsGkey();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        Long id = this.getDmgsGkey();
        if (id == null) {
            return false;
        }
        if (!(other instanceof UnitEquipDamagesDO)) {
            return false;
        }
        UnitEquipDamagesDO that = (UnitEquipDamagesDO)other;
        return ((Object)id).equals(that.getDmgsGkey());
    }

    public int hashCode() {
        Long id = this.getDmgsGkey();
        return id == null ? System.identityHashCode(this) : ((Object)id).hashCode();
    }

    public Long getDmgsGkey() {
        return this.dmgsGkey;
    }

    protected void setDmgsGkey(Long dmgsGkey) {
        this.dmgsGkey = dmgsGkey;
    }

    public String getDmgsOwnerEntityName() {
        return this.dmgsOwnerEntityName;
    }

    public void setDmgsOwnerEntityName(String dmgsOwnerEntityName) {
        this.dmgsOwnerEntityName = dmgsOwnerEntityName;
    }

    public Long getDmgsOwnerEntityGkey() {
        return this.dmgsOwnerEntityGkey;
    }

    public void setDmgsOwnerEntityGkey(Long dmgsOwnerEntityGkey) {
        this.dmgsOwnerEntityGkey = dmgsOwnerEntityGkey;
    }

    public Date getDmgsCreated() {
        return this.dmgsCreated;
    }

    protected void setDmgsCreated(Date dmgsCreated) {
        this.dmgsCreated = dmgsCreated;
    }

    public String getDmgsCreator() {
        return this.dmgsCreator;
    }

    protected void setDmgsCreator(String dmgsCreator) {
        this.dmgsCreator = dmgsCreator;
    }

    public Date getDmgsChanged() {
        return this.dmgsChanged;
    }

    protected void setDmgsChanged(Date dmgsChanged) {
        this.dmgsChanged = dmgsChanged;
    }

    public String getDmgsChanger() {
        return this.dmgsChanger;
    }

    protected void setDmgsChanger(String dmgsChanger) {
        this.dmgsChanger = dmgsChanger;
    }

    public List getDmgsItems() {
        return this.dmgsItems;
    }

    public void setDmgsItems(List dmgsItems) {
        this.dmgsItems = dmgsItems;
    }
}
