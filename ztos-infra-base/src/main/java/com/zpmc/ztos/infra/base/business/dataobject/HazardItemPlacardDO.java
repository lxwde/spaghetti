package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.business.inventory.HazardItem;
import com.zpmc.ztos.infra.base.business.model.Placard;
import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;

import java.io.Serializable;

public class HazardItemPlacardDO extends DatabaseEntity implements Serializable {
    private Long hzrdipGkey;
    private String hzrdipDescription;
    private HazardItem hzrdipHazardItem;
    private Placard hzrdipPlacard;

    public Serializable getPrimaryKey() {
        return this.getHzrdipGkey();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        Long id = this.getHzrdipGkey();
        if (id == null) {
            return false;
        }
        if (!(other instanceof HazardItemPlacardDO)) {
            return false;
        }
        HazardItemPlacardDO that = (HazardItemPlacardDO)other;
        return ((Object)id).equals(that.getHzrdipGkey());
    }

    public int hashCode() {
        Long id = this.getHzrdipGkey();
        return id == null ? System.identityHashCode(this) : ((Object)id).hashCode();
    }

    public Long getHzrdipGkey() {
        return this.hzrdipGkey;
    }

    protected void setHzrdipGkey(Long hzrdipGkey) {
        this.hzrdipGkey = hzrdipGkey;
    }

    public String getHzrdipDescription() {
        return this.hzrdipDescription;
    }

    public void setHzrdipDescription(String hzrdipDescription) {
        this.hzrdipDescription = hzrdipDescription;
    }

    public HazardItem getHzrdipHazardItem() {
        return this.hzrdipHazardItem;
    }

    public void setHzrdipHazardItem(HazardItem hzrdipHazardItem) {
        this.hzrdipHazardItem = hzrdipHazardItem;
    }

    public Placard getHzrdipPlacard() {
        return this.hzrdipPlacard;
    }

    public void setHzrdipPlacard(Placard hzrdipPlacard) {
        this.hzrdipPlacard = hzrdipPlacard;
    }
}
