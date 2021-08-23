package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.business.inventory.UnitFacilityVisit;
import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;
import com.zpmc.ztos.infra.base.common.scopes.Yard;

import java.io.Serializable;
import java.util.List;

public class UnitYardVisitDO extends DatabaseEntity implements Serializable {
    private Long uyvGkey;
    private Long uyvPkey;
    private UnitFacilityVisit uyvUfv;
    private Yard uyvYard;
    private List uyvWiList;

    public Serializable getPrimaryKey() {
        return this.getUyvGkey();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        Long id = this.getUyvGkey();
        if (id == null) {
            return false;
        }
        if (!(other instanceof UnitYardVisitDO)) {
            return false;
        }
        UnitYardVisitDO that = (UnitYardVisitDO)other;
        return ((Object)id).equals(that.getUyvGkey());
    }

    public int hashCode() {
        Long id = this.getUyvGkey();
        return id == null ? System.identityHashCode(this) : ((Object)id).hashCode();
    }

    public Long getUyvGkey() {
        return this.uyvGkey;
    }

    protected void setUyvGkey(Long uyvGkey) {
        this.uyvGkey = uyvGkey;
    }

    public Long getUyvPkey() {
        return this.uyvPkey;
    }

    protected void setUyvPkey(Long uyvPkey) {
        this.uyvPkey = uyvPkey;
    }

    public UnitFacilityVisit getUyvUfv() {
        return this.uyvUfv;
    }

    protected void setUyvUfv(UnitFacilityVisit uyvUfv) {
        this.uyvUfv = uyvUfv;
    }

    public Yard getUyvYard() {
        return this.uyvYard;
    }

    protected void setUyvYard(Yard uyvYard) {
        this.uyvYard = uyvYard;
    }

    public List getUyvWiList() {
        return this.uyvWiList;
    }

    protected void setUyvWiList(List uyvWiList) {
        this.uyvWiList = uyvWiList;
    }

}
