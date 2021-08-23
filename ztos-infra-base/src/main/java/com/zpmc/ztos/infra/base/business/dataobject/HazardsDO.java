package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.business.inventory.HazardFireCode;
import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;

import java.io.Serializable;
import java.util.List;

public class HazardsDO extends DatabaseEntity implements Serializable {

    private Long hzrdGkey;
    private String hzrdOwnerEntityName;
    private Long hzrdOwnerEntityGkey;
    private HazardFireCode hzrdWorstFireCode;
    private List hzrdItems;

    public Serializable getPrimaryKey() {
        return this.getHzrdGkey();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        Long id = this.getHzrdGkey();
        if (id == null) {
            return false;
        }
        if (!(other instanceof HazardsDO)) {
            return false;
        }
        HazardsDO that = (HazardsDO)other;
        return ((Object)id).equals(that.getHzrdGkey());
    }

    public int hashCode() {
        Long id = this.getHzrdGkey();
        return id == null ? System.identityHashCode(this) : ((Object)id).hashCode();
    }

    public Long getHzrdGkey() {
        return this.hzrdGkey;
    }

    protected void setHzrdGkey(Long hzrdGkey) {
        this.hzrdGkey = hzrdGkey;
    }

    public String getHzrdOwnerEntityName() {
        return this.hzrdOwnerEntityName;
    }

    protected void setHzrdOwnerEntityName(String hzrdOwnerEntityName) {
        this.hzrdOwnerEntityName = hzrdOwnerEntityName;
    }

    public Long getHzrdOwnerEntityGkey() {
        return this.hzrdOwnerEntityGkey;
    }

    protected void setHzrdOwnerEntityGkey(Long hzrdOwnerEntityGkey) {
        this.hzrdOwnerEntityGkey = hzrdOwnerEntityGkey;
    }

    public HazardFireCode getHzrdWorstFireCode() {
        return this.hzrdWorstFireCode;
    }

    protected void setHzrdWorstFireCode(HazardFireCode hzrdWorstFireCode) {
        this.hzrdWorstFireCode = hzrdWorstFireCode;
    }

    public List getHzrdItems() {
        return this.hzrdItems;
    }

    protected void setHzrdItems(List hzrdItems) {
        this.hzrdItems = hzrdItems;
    }
}
