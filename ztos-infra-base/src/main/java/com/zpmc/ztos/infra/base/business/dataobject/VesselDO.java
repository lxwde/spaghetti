package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.business.enums.argo.TempUnitEnum;
import com.zpmc.ztos.infra.base.business.enums.framework.LifeCycleStateEnum;
import com.zpmc.ztos.infra.base.business.enums.vessel.StowageSchemeEnum;
import com.zpmc.ztos.infra.base.business.model.EntitySet;
import com.zpmc.ztos.infra.base.business.model.RefCountry;
import com.zpmc.ztos.infra.base.business.model.ReferenceEntity;
import com.zpmc.ztos.infra.base.business.model.ScopedBizUnit;

import java.io.Serializable;
import java.util.Date;

public abstract class VesselDO extends ReferenceEntity implements Serializable {

    private Long vesGkey;
    private String vesId;
    private String vesLloydsId;
    private String vesName;
    private String vesCaptain;
    private String vesRadioCallSign;
    private Boolean vesIsActive;
 //   private UnitSystemEnum vesUnitSystem;
    private TempUnitEnum vesTemperatureUnits;
    private String vesNotes;
    private Date vesCreated;
    private String vesCreator;
    private Date vesChanged;
    private String vesChanger;
    private StowageSchemeEnum vesStowageScheme;
    private String vesDocumentationNbr;
    private String vesServiceRegistryNbr;
    private LifeCycleStateEnum vesLifeCycleState;
    private EntitySet vesScope;
 //   private VesselClass vesVesselClass;
    private ScopedBizUnit vesOwner;
    private RefCountry vesCountry;

    public Serializable getPrimaryKey() {
        return this.getVesGkey();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        Long id = this.getVesGkey();
        if (id == null) {
            return false;
        }
        if (!(other instanceof VesselDO)) {
            return false;
        }
        VesselDO that = (VesselDO)other;
        return ((Object)id).equals(that.getVesGkey());
    }

    public int hashCode() {
        Long id = this.getVesGkey();
        return id == null ? System.identityHashCode(this) : ((Object)id).hashCode();
    }

    public Long getVesGkey() {
        return this.vesGkey;
    }

    protected void setVesGkey(Long vesGkey) {
        this.vesGkey = vesGkey;
    }

    public String getVesId() {
        return this.vesId;
    }

    protected void setVesId(String vesId) {
        this.vesId = vesId;
    }

    public String getVesLloydsId() {
        return this.vesLloydsId;
    }

    protected void setVesLloydsId(String vesLloydsId) {
        this.vesLloydsId = vesLloydsId;
    }

    public String getVesName() {
        return this.vesName;
    }

    protected void setVesName(String vesName) {
        this.vesName = vesName;
    }

    public String getVesCaptain() {
        return this.vesCaptain;
    }

    protected void setVesCaptain(String vesCaptain) {
        this.vesCaptain = vesCaptain;
    }

    public String getVesRadioCallSign() {
        return this.vesRadioCallSign;
    }

    protected void setVesRadioCallSign(String vesRadioCallSign) {
        this.vesRadioCallSign = vesRadioCallSign;
    }

    public Boolean getVesIsActive() {
        return this.vesIsActive;
    }

    protected void setVesIsActive(Boolean vesIsActive) {
        this.vesIsActive = vesIsActive;
    }

//    public UnitSystemEnum getVesUnitSystem() {
//        return this.vesUnitSystem;
//    }
//
//    protected void setVesUnitSystem(UnitSystemEnum vesUnitSystem) {
//        this.vesUnitSystem = vesUnitSystem;
//    }

    public TempUnitEnum getVesTemperatureUnits() {
        return this.vesTemperatureUnits;
    }

    protected void setVesTemperatureUnits(TempUnitEnum vesTemperatureUnits) {
        this.vesTemperatureUnits = vesTemperatureUnits;
    }

    public String getVesNotes() {
        return this.vesNotes;
    }

    protected void setVesNotes(String vesNotes) {
        this.vesNotes = vesNotes;
    }

    public Date getVesCreated() {
        return this.vesCreated;
    }

    protected void setVesCreated(Date vesCreated) {
        this.vesCreated = vesCreated;
    }

    public String getVesCreator() {
        return this.vesCreator;
    }

    protected void setVesCreator(String vesCreator) {
        this.vesCreator = vesCreator;
    }

    public Date getVesChanged() {
        return this.vesChanged;
    }

    protected void setVesChanged(Date vesChanged) {
        this.vesChanged = vesChanged;
    }

    public String getVesChanger() {
        return this.vesChanger;
    }

    protected void setVesChanger(String vesChanger) {
        this.vesChanger = vesChanger;
    }

    public StowageSchemeEnum getVesStowageScheme() {
        return this.vesStowageScheme;
    }

    protected void setVesStowageScheme(StowageSchemeEnum vesStowageScheme) {
        this.vesStowageScheme = vesStowageScheme;
    }

    public String getVesDocumentationNbr() {
        return this.vesDocumentationNbr;
    }

    protected void setVesDocumentationNbr(String vesDocumentationNbr) {
        this.vesDocumentationNbr = vesDocumentationNbr;
    }

    public String getVesServiceRegistryNbr() {
        return this.vesServiceRegistryNbr;
    }

    protected void setVesServiceRegistryNbr(String vesServiceRegistryNbr) {
        this.vesServiceRegistryNbr = vesServiceRegistryNbr;
    }

    public LifeCycleStateEnum getVesLifeCycleState() {
        return this.vesLifeCycleState;
    }

    public void setVesLifeCycleState(LifeCycleStateEnum vesLifeCycleState) {
        this.vesLifeCycleState = vesLifeCycleState;
    }

    public EntitySet getVesScope() {
        return this.vesScope;
    }

    protected void setVesScope(EntitySet vesScope) {
        this.vesScope = vesScope;
    }

//    public VesselClass getVesVesselClass() {
//        return this.vesVesselClass;
//    }
//
//    protected void setVesVesselClass(VesselClass vesVesselClass) {
//        this.vesVesselClass = vesVesselClass;
//    }

    public ScopedBizUnit getVesOwner() {
        return this.vesOwner;
    }

    protected void setVesOwner(ScopedBizUnit vesOwner) {
        this.vesOwner = vesOwner;
    }

    public RefCountry getVesCountry() {
        return this.vesCountry;
    }

    protected void setVesCountry(RefCountry vesCountry) {
        this.vesCountry = vesCountry;
    }
}
