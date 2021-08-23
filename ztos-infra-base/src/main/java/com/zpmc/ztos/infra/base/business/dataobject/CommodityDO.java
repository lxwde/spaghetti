package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.business.enums.framework.LifeCycleStateEnum;
import com.zpmc.ztos.infra.base.business.model.Commodity;
import com.zpmc.ztos.infra.base.business.model.EntitySet;
import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;

import java.io.Serializable;
import java.util.Date;

public class CommodityDO extends DatabaseEntity implements Serializable {
    private Long cmdyGkey;
    private String cmdyId;
    private String cmdyShortName;
    private String cmdyDescription;
    private String cmdyOneCharCode;
    private Long cmdyIcon1;
    private Long cmdyIcon2;
    private Boolean cmdyIsFood;
    private Boolean cmdyIsFilm;
    private Boolean cmdyIsOils;
    private Boolean cmdyIsTaintable;
    private Boolean cmdyIsTainting;
    private Boolean cmdyIsExclusiveReeferTower;
    private Boolean cmdyIsNonReefer;
    private Boolean cmdyTempValidated;
    private Boolean cmdyTempRangeValidated;
    private Boolean cmdyHazValidated;
    private Double cmdyHumidityRequired;
    private Boolean cmdyIsValuables;
    private Boolean cmdyIsHeatSensitive;
    private Double cmdyVcgRatio;
    private Double cmdyTempMin;
    private Double cmdyTempMax;
    private Double cmdyTempIdeal;
    private Double cmdyVentPct;
    private String cmdyUnNbr;
    private Date cmdyCreated;
    private String cmdyCreator;
    private Date cmdyChanged;
    private String cmdyChanger;
    private LifeCycleStateEnum cmdyLifeCycleState;
    private Boolean cmdyIsArchetype;
    private EntitySet cmdyScope;
//    private DigitalAsset cmdyIcon;
    private Commodity cmdyArchetype;

    public Serializable getPrimaryKey() {
        return this.getCmdyGkey();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        Long id = this.getCmdyGkey();
        if (id == null) {
            return false;
        }
        if (!(other instanceof CommodityDO)) {
            return false;
        }
        CommodityDO that = (CommodityDO)other;
        return ((Object)id).equals(that.getCmdyGkey());
    }

    public int hashCode() {
        Long id = this.getCmdyGkey();
        return id == null ? System.identityHashCode(this) : ((Object)id).hashCode();
    }

    public Long getCmdyGkey() {
        return this.cmdyGkey;
    }

    protected void setCmdyGkey(Long cmdyGkey) {
        this.cmdyGkey = cmdyGkey;
    }

    public String getCmdyId() {
        return this.cmdyId;
    }

    protected void setCmdyId(String cmdyId) {
        this.cmdyId = cmdyId;
    }

    public String getCmdyShortName() {
        return this.cmdyShortName;
    }

    protected void setCmdyShortName(String cmdyShortName) {
        this.cmdyShortName = cmdyShortName;
    }

    public String getCmdyDescription() {
        return this.cmdyDescription;
    }

    protected void setCmdyDescription(String cmdyDescription) {
        this.cmdyDescription = cmdyDescription;
    }

    public String getCmdyOneCharCode() {
        return this.cmdyOneCharCode;
    }

    protected void setCmdyOneCharCode(String cmdyOneCharCode) {
        this.cmdyOneCharCode = cmdyOneCharCode;
    }

    protected Long getCmdyIcon1() {
        return this.cmdyIcon1;
    }

    protected void setCmdyIcon1(Long cmdyIcon1) {
        this.cmdyIcon1 = cmdyIcon1;
    }

    protected Long getCmdyIcon2() {
        return this.cmdyIcon2;
    }

    protected void setCmdyIcon2(Long cmdyIcon2) {
        this.cmdyIcon2 = cmdyIcon2;
    }

    protected Boolean getCmdyIsFood() {
        return this.cmdyIsFood;
    }

    protected void setCmdyIsFood(Boolean cmdyIsFood) {
        this.cmdyIsFood = cmdyIsFood;
    }

    protected Boolean getCmdyIsFilm() {
        return this.cmdyIsFilm;
    }

    protected void setCmdyIsFilm(Boolean cmdyIsFilm) {
        this.cmdyIsFilm = cmdyIsFilm;
    }

    protected Boolean getCmdyIsOils() {
        return this.cmdyIsOils;
    }

    protected void setCmdyIsOils(Boolean cmdyIsOils) {
        this.cmdyIsOils = cmdyIsOils;
    }

    protected Boolean getCmdyIsTaintable() {
        return this.cmdyIsTaintable;
    }

    protected void setCmdyIsTaintable(Boolean cmdyIsTaintable) {
        this.cmdyIsTaintable = cmdyIsTaintable;
    }

    protected Boolean getCmdyIsTainting() {
        return this.cmdyIsTainting;
    }

    protected void setCmdyIsTainting(Boolean cmdyIsTainting) {
        this.cmdyIsTainting = cmdyIsTainting;
    }

    protected Boolean getCmdyIsExclusiveReeferTower() {
        return this.cmdyIsExclusiveReeferTower;
    }

    protected void setCmdyIsExclusiveReeferTower(Boolean cmdyIsExclusiveReeferTower) {
        this.cmdyIsExclusiveReeferTower = cmdyIsExclusiveReeferTower;
    }

    protected Boolean getCmdyIsNonReefer() {
        return this.cmdyIsNonReefer;
    }

    protected void setCmdyIsNonReefer(Boolean cmdyIsNonReefer) {
        this.cmdyIsNonReefer = cmdyIsNonReefer;
    }

    protected Boolean getCmdyTempValidated() {
        return this.cmdyTempValidated;
    }

    protected void setCmdyTempValidated(Boolean cmdyTempValidated) {
        this.cmdyTempValidated = cmdyTempValidated;
    }

    protected Boolean getCmdyTempRangeValidated() {
        return this.cmdyTempRangeValidated;
    }

    protected void setCmdyTempRangeValidated(Boolean cmdyTempRangeValidated) {
        this.cmdyTempRangeValidated = cmdyTempRangeValidated;
    }

    protected Boolean getCmdyHazValidated() {
        return this.cmdyHazValidated;
    }

    protected void setCmdyHazValidated(Boolean cmdyHazValidated) {
        this.cmdyHazValidated = cmdyHazValidated;
    }

    protected Double getCmdyHumidityRequired() {
        return this.cmdyHumidityRequired;
    }

    protected void setCmdyHumidityRequired(Double cmdyHumidityRequired) {
        this.cmdyHumidityRequired = cmdyHumidityRequired;
    }

    protected Boolean getCmdyIsValuables() {
        return this.cmdyIsValuables;
    }

    protected void setCmdyIsValuables(Boolean cmdyIsValuables) {
        this.cmdyIsValuables = cmdyIsValuables;
    }

    protected Boolean getCmdyIsHeatSensitive() {
        return this.cmdyIsHeatSensitive;
    }

    protected void setCmdyIsHeatSensitive(Boolean cmdyIsHeatSensitive) {
        this.cmdyIsHeatSensitive = cmdyIsHeatSensitive;
    }

    public Double getCmdyVcgRatio() {
        return this.cmdyVcgRatio;
    }

    protected void setCmdyVcgRatio(Double cmdyVcgRatio) {
        this.cmdyVcgRatio = cmdyVcgRatio;
    }

    public Double getCmdyTempMin() {
        return this.cmdyTempMin;
    }

    protected void setCmdyTempMin(Double cmdyTempMin) {
        this.cmdyTempMin = cmdyTempMin;
    }

    public Double getCmdyTempMax() {
        return this.cmdyTempMax;
    }

    protected void setCmdyTempMax(Double cmdyTempMax) {
        this.cmdyTempMax = cmdyTempMax;
    }

    public Double getCmdyTempIdeal() {
        return this.cmdyTempIdeal;
    }

    protected void setCmdyTempIdeal(Double cmdyTempIdeal) {
        this.cmdyTempIdeal = cmdyTempIdeal;
    }

    public Double getCmdyVentPct() {
        return this.cmdyVentPct;
    }

    protected void setCmdyVentPct(Double cmdyVentPct) {
        this.cmdyVentPct = cmdyVentPct;
    }

    public String getCmdyUnNbr() {
        return this.cmdyUnNbr;
    }

    protected void setCmdyUnNbr(String cmdyUnNbr) {
        this.cmdyUnNbr = cmdyUnNbr;
    }

    public Date getCmdyCreated() {
        return this.cmdyCreated;
    }

    protected void setCmdyCreated(Date cmdyCreated) {
        this.cmdyCreated = cmdyCreated;
    }

    public String getCmdyCreator() {
        return this.cmdyCreator;
    }

    protected void setCmdyCreator(String cmdyCreator) {
        this.cmdyCreator = cmdyCreator;
    }

    public Date getCmdyChanged() {
        return this.cmdyChanged;
    }

    protected void setCmdyChanged(Date cmdyChanged) {
        this.cmdyChanged = cmdyChanged;
    }

    public String getCmdyChanger() {
        return this.cmdyChanger;
    }

    protected void setCmdyChanger(String cmdyChanger) {
        this.cmdyChanger = cmdyChanger;
    }

    public LifeCycleStateEnum getCmdyLifeCycleState() {
        return this.cmdyLifeCycleState;
    }

    public void setCmdyLifeCycleState(LifeCycleStateEnum cmdyLifeCycleState) {
        this.cmdyLifeCycleState = cmdyLifeCycleState;
    }

    public Boolean getCmdyIsArchetype() {
        return this.cmdyIsArchetype;
    }

    protected void setCmdyIsArchetype(Boolean cmdyIsArchetype) {
        this.cmdyIsArchetype = cmdyIsArchetype;
    }

    public EntitySet getCmdyScope() {
        return this.cmdyScope;
    }

    protected void setCmdyScope(EntitySet cmdyScope) {
        this.cmdyScope = cmdyScope;
    }

//    public DigitalAsset getCmdyIcon() {
//        return this.cmdyIcon;
//    }
//
//    protected void setCmdyIcon(DigitalAsset cmdyIcon) {
//        this.cmdyIcon = cmdyIcon;
//    }

    public Commodity getCmdyArchetype() {
        return this.cmdyArchetype;
    }

    protected void setCmdyArchetype(Commodity cmdyArchetype) {
        this.cmdyArchetype = cmdyArchetype;
    }

}
