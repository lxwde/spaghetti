package com.zpmc.ztos.infra.base.business.inventory;

import java.io.Serializable;

public class ImpedimentsBean {
    private Serializable _unitGkey;
    private String _impedimentVessel;
    private String _impedimentRoad;
    private String _impedimentRail;
    public static final String NOT_APPLICABLE = "n/a";

    public ImpedimentsBean(Serializable inUnitGkey, String inImpedimentVessel, String inImpedimentRoad, String inImpedimentRail) {
        this._impedimentVessel = inImpedimentVessel;
        this._impedimentRoad = inImpedimentRoad;
        this._impedimentRail = inImpedimentRail;
        this._unitGkey = inUnitGkey;
    }

    public Serializable getUnitGkey() {
        return this._unitGkey;
    }

    public boolean isStopVessel() {
        return this._impedimentVessel != null && !NOT_APPLICABLE.equals(this._impedimentVessel);
    }

    public boolean isStopRoad() {
        return this._impedimentRoad != null && !NOT_APPLICABLE.equals(this._impedimentRoad);
    }

    public boolean isStopRail() {
        return this._impedimentRail != null && !NOT_APPLICABLE.equals(this._impedimentRail);
    }

    public String getImpedimentVessel() {
        return this._impedimentVessel;
    }

    public String getImpedimentRoad() {
        return this._impedimentRoad;
    }

    public String getImpedimentRail() {
        return this._impedimentRail;
    }
}
