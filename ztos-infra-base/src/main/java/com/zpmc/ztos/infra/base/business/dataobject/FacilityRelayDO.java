package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.business.enums.argo.LocTypeEnum;
import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;
import com.zpmc.ztos.infra.base.common.scopes.Facility;

import java.io.Serializable;

public class FacilityRelayDO extends DatabaseEntity implements Serializable {

    private Long fcyrelayGkey;
    private Long fcyrelayTransitTimeHrs;
    private Long fcyrelayCutoffLeadTimeHrs;
    private LocTypeEnum fcyrelayCarrierModeDefault;
    private Boolean fcyrelayIftOnly;
    private Facility fcyrelayFacility;
    private Facility fcyrelayToFacility;

    public Serializable getPrimaryKey() {
        return this.getFcyrelayGkey();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        Long id = this.getFcyrelayGkey();
        if (id == null) {
            return false;
        }
        if (!(other instanceof FacilityRelayDO)) {
            return false;
        }
        FacilityRelayDO that = (FacilityRelayDO)other;
        return ((Object)id).equals(that.getFcyrelayGkey());
    }

    public int hashCode() {
        Long id = this.getFcyrelayGkey();
        return id == null ? System.identityHashCode(this) : ((Object)id).hashCode();
    }

    public Long getFcyrelayGkey() {
        return this.fcyrelayGkey;
    }

    protected void setFcyrelayGkey(Long fcyrelayGkey) {
        this.fcyrelayGkey = fcyrelayGkey;
    }

    public Long getFcyrelayTransitTimeHrs() {
        return this.fcyrelayTransitTimeHrs;
    }

    protected void setFcyrelayTransitTimeHrs(Long fcyrelayTransitTimeHrs) {
        this.fcyrelayTransitTimeHrs = fcyrelayTransitTimeHrs;
    }

    public Long getFcyrelayCutoffLeadTimeHrs() {
        return this.fcyrelayCutoffLeadTimeHrs;
    }

    protected void setFcyrelayCutoffLeadTimeHrs(Long fcyrelayCutoffLeadTimeHrs) {
        this.fcyrelayCutoffLeadTimeHrs = fcyrelayCutoffLeadTimeHrs;
    }

    public LocTypeEnum getFcyrelayCarrierModeDefault() {
        return this.fcyrelayCarrierModeDefault;
    }

    protected void setFcyrelayCarrierModeDefault(LocTypeEnum fcyrelayCarrierModeDefault) {
        this.fcyrelayCarrierModeDefault = fcyrelayCarrierModeDefault;
    }

    public Boolean getFcyrelayIftOnly() {
        return this.fcyrelayIftOnly;
    }

    protected void setFcyrelayIftOnly(Boolean fcyrelayIftOnly) {
        this.fcyrelayIftOnly = fcyrelayIftOnly;
    }

    public Facility getFcyrelayFacility() {
        return this.fcyrelayFacility;
    }

    protected void setFcyrelayFacility(Facility fcyrelayFacility) {
        this.fcyrelayFacility = fcyrelayFacility;
    }

    public Facility getFcyrelayToFacility() {
        return this.fcyrelayToFacility;
    }

    protected void setFcyrelayToFacility(Facility fcyrelayToFacility) {
        this.fcyrelayToFacility = fcyrelayToFacility;
    }

}
