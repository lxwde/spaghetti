package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.business.enums.argo.CompassDirectionEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.StatusCodeEnum;
import com.zpmc.ztos.infra.base.business.enums.framework.LifeCycleStateEnum;
import com.zpmc.ztos.infra.base.business.model.EntitySet;
import com.zpmc.ztos.infra.base.business.model.RefCountry;
import com.zpmc.ztos.infra.base.business.model.ReferenceEntity;

import java.io.Serializable;
import java.util.Date;

public abstract class UnLocCodeDO extends ReferenceEntity implements Serializable {
    private Long unlocGkey;
    private String unlocId;
    private String unlocPlaceCode;
    private String unlocPlaceName;
    private Boolean unlocIsPort;
    private Boolean unlocIsRailTerminal;
    private Boolean unlocIsRoadTerminal;
    private Boolean unlocIsAirport;
    private Boolean unlocIsMultimodal;
    private Boolean unlocIsFixedTransport;
    private Boolean unlocIsBorderCrossing;
    private Boolean unlocIsFunctionUnknown;
    private StatusCodeEnum unlocStatus;
    private String unlocSubDiv;
    private String unlocLatitude;
    private CompassDirectionEnum unlocLatNorS;
    private String unlocLongitude;
    private CompassDirectionEnum unlocLongEorW;
    private String unlocRemarks;
    private Date unlocCreated;
    private String unlocCreator;
    private Date unlocChanged;
    private String unlocChanger;
    private LifeCycleStateEnum unlocLifeCycleState;
    private EntitySet unlocScope;
    private RefCountry unlocCntry;

    public Serializable getPrimaryKey() {
        return this.getUnlocGkey();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        Long id = this.getUnlocGkey();
        if (id == null) {
            return false;
        }
        if (!(other instanceof UnLocCodeDO)) {
            return false;
        }
        UnLocCodeDO that = (UnLocCodeDO)other;
        return ((Object)id).equals(that.getUnlocGkey());
    }

    public int hashCode() {
        Long id = this.getUnlocGkey();
        return id == null ? System.identityHashCode(this) : ((Object)id).hashCode();
    }

    public Long getUnlocGkey() {
        return this.unlocGkey;
    }

    protected void setUnlocGkey(Long unlocGkey) {
        this.unlocGkey = unlocGkey;
    }

    public String getUnlocId() {
        return this.unlocId;
    }

    protected void setUnlocId(String unlocId) {
        this.unlocId = unlocId;
    }

    public String getUnlocPlaceCode() {
        return this.unlocPlaceCode;
    }

    protected void setUnlocPlaceCode(String unlocPlaceCode) {
        this.unlocPlaceCode = unlocPlaceCode;
    }

    public String getUnlocPlaceName() {
        return this.unlocPlaceName;
    }

    public void setUnlocPlaceName(String unlocPlaceName) {
        this.unlocPlaceName = unlocPlaceName;
    }

    protected Boolean getUnlocIsPort() {
        return this.unlocIsPort;
    }

    protected void setUnlocIsPort(Boolean unlocIsPort) {
        this.unlocIsPort = unlocIsPort;
    }

    protected Boolean getUnlocIsRailTerminal() {
        return this.unlocIsRailTerminal;
    }

    protected void setUnlocIsRailTerminal(Boolean unlocIsRailTerminal) {
        this.unlocIsRailTerminal = unlocIsRailTerminal;
    }

    protected Boolean getUnlocIsRoadTerminal() {
        return this.unlocIsRoadTerminal;
    }

    protected void setUnlocIsRoadTerminal(Boolean unlocIsRoadTerminal) {
        this.unlocIsRoadTerminal = unlocIsRoadTerminal;
    }

    protected Boolean getUnlocIsAirport() {
        return this.unlocIsAirport;
    }

    protected void setUnlocIsAirport(Boolean unlocIsAirport) {
        this.unlocIsAirport = unlocIsAirport;
    }

    protected Boolean getUnlocIsMultimodal() {
        return this.unlocIsMultimodal;
    }

    protected void setUnlocIsMultimodal(Boolean unlocIsMultimodal) {
        this.unlocIsMultimodal = unlocIsMultimodal;
    }

    protected Boolean getUnlocIsFixedTransport() {
        return this.unlocIsFixedTransport;
    }

    protected void setUnlocIsFixedTransport(Boolean unlocIsFixedTransport) {
        this.unlocIsFixedTransport = unlocIsFixedTransport;
    }

    protected Boolean getUnlocIsBorderCrossing() {
        return this.unlocIsBorderCrossing;
    }

    protected void setUnlocIsBorderCrossing(Boolean unlocIsBorderCrossing) {
        this.unlocIsBorderCrossing = unlocIsBorderCrossing;
    }

    protected Boolean getUnlocIsFunctionUnknown() {
        return this.unlocIsFunctionUnknown;
    }

    protected void setUnlocIsFunctionUnknown(Boolean unlocIsFunctionUnknown) {
        this.unlocIsFunctionUnknown = unlocIsFunctionUnknown;
    }

    public StatusCodeEnum getUnlocStatus() {
        return this.unlocStatus;
    }

    protected void setUnlocStatus(StatusCodeEnum unlocStatus) {
        this.unlocStatus = unlocStatus;
    }

    public String getUnlocSubDiv() {
        return this.unlocSubDiv;
    }

    protected void setUnlocSubDiv(String unlocSubDiv) {
        this.unlocSubDiv = unlocSubDiv;
    }

    public String getUnlocLatitude() {
        return this.unlocLatitude;
    }

    protected void setUnlocLatitude(String unlocLatitude) {
        this.unlocLatitude = unlocLatitude;
    }

    public CompassDirectionEnum getUnlocLatNorS() {
        return this.unlocLatNorS;
    }

    protected void setUnlocLatNorS(CompassDirectionEnum unlocLatNorS) {
        this.unlocLatNorS = unlocLatNorS;
    }

    public String getUnlocLongitude() {
        return this.unlocLongitude;
    }

    protected void setUnlocLongitude(String unlocLongitude) {
        this.unlocLongitude = unlocLongitude;
    }

    public CompassDirectionEnum getUnlocLongEorW() {
        return this.unlocLongEorW;
    }

    protected void setUnlocLongEorW(CompassDirectionEnum unlocLongEorW) {
        this.unlocLongEorW = unlocLongEorW;
    }

    public String getUnlocRemarks() {
        return this.unlocRemarks;
    }

    protected void setUnlocRemarks(String unlocRemarks) {
        this.unlocRemarks = unlocRemarks;
    }

    public Date getUnlocCreated() {
        return this.unlocCreated;
    }

    protected void setUnlocCreated(Date unlocCreated) {
        this.unlocCreated = unlocCreated;
    }

    public String getUnlocCreator() {
        return this.unlocCreator;
    }

    protected void setUnlocCreator(String unlocCreator) {
        this.unlocCreator = unlocCreator;
    }

    public Date getUnlocChanged() {
        return this.unlocChanged;
    }

    protected void setUnlocChanged(Date unlocChanged) {
        this.unlocChanged = unlocChanged;
    }

    public String getUnlocChanger() {
        return this.unlocChanger;
    }

    protected void setUnlocChanger(String unlocChanger) {
        this.unlocChanger = unlocChanger;
    }

    public LifeCycleStateEnum getUnlocLifeCycleState() {
        return this.unlocLifeCycleState;
    }

    public void setUnlocLifeCycleState(LifeCycleStateEnum unlocLifeCycleState) {
        this.unlocLifeCycleState = unlocLifeCycleState;
    }

    public EntitySet getUnlocScope() {
        return this.unlocScope;
    }

    protected void setUnlocScope(EntitySet unlocScope) {
        this.unlocScope = unlocScope;
    }

    public RefCountry getUnlocCntry() {
        return this.unlocCntry;
    }

    protected void setUnlocCntry(RefCountry unlocCntry) {
        this.unlocCntry = unlocCntry;
    }

}
