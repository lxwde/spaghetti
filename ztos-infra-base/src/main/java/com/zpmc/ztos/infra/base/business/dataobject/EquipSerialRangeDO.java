package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.business.enums.argo.EquipIsoGroupEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.EquipRfrTypeEnum;
import com.zpmc.ztos.infra.base.business.enums.framework.LifeCycleStateEnum;
import com.zpmc.ztos.infra.base.business.model.EntitySet;
import com.zpmc.ztos.infra.base.business.model.ReferenceEntity;

import java.io.Serializable;
import java.util.Date;

public abstract class EquipSerialRangeDO extends ReferenceEntity implements Serializable {

    private Long eqsrlrngGkey;
    private String eqsrlrngPrefix;
    private String eqsrlrngRegex;
    private String eqsrlrngStartingSerialNbr;
    private String eqsrlrngEndingSerialNbr;
    private Long eqsrlrngLengthMm;
    private Long eqsrlrngHeightMm;
    private Long eqsrlrngWidthMm;
    private Double eqsrlrngTareWeightKg;
    private Double eqsrlrngSafeWeightKg;
    private EquipRfrTypeEnum eqsrlrngRfrType;
    private EquipIsoGroupEnum eqsrlrngIsoGroup;
    private Boolean eqsrlrngIsSuperFreezeReefer;
    private Boolean eqsrlrngIsControlledAtmosphereReefer;
    private Boolean eqsrlrngNoStowOnTopIfEmpty;
    private Boolean eqsrlrngNoStowOnTopIfLaden;
    private Boolean eqsrlrngMustStowBelowDeck;
    private Boolean eqsrlrngMustStowAboveDeck;
    private Boolean eqsrlrngUsesAccessories;
    private Boolean eqsrlrngIsTemperatureControlled;
    private Boolean eqsrlrngOogOk;
    private Boolean eqsrlrngIsUnsealable;
    private Boolean eqsrlrngHasWheels;
    private Boolean eqsrlrngIsOpen;
    private Date eqsrlrngCreated;
    private String eqsrlrngCreator;
    private Date eqsrlrngChanged;
    private String eqsrlrngChanger;
    private String eqsrlrngEqtypeId;
    private LifeCycleStateEnum eqsrlrngLifeCycleState;
    private EntitySet eqsrlrngScope;

    public Serializable getPrimaryKey() {
        return this.getEqsrlrngGkey();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        Long id = this.getEqsrlrngGkey();
        if (id == null) {
            return false;
        }
        if (!(other instanceof EquipSerialRangeDO)) {
            return false;
        }
        EquipSerialRangeDO that = (EquipSerialRangeDO)other;
        return ((Object)id).equals(that.getEqsrlrngGkey());
    }

    public int hashCode() {
        Long id = this.getEqsrlrngGkey();
        return id == null ? System.identityHashCode(this) : ((Object)id).hashCode();
    }

    public Long getEqsrlrngGkey() {
        return this.eqsrlrngGkey;
    }

    protected void setEqsrlrngGkey(Long eqsrlrngGkey) {
        this.eqsrlrngGkey = eqsrlrngGkey;
    }

    public String getEqsrlrngPrefix() {
        return this.eqsrlrngPrefix;
    }

    protected void setEqsrlrngPrefix(String eqsrlrngPrefix) {
        this.eqsrlrngPrefix = eqsrlrngPrefix;
    }

    public String getEqsrlrngRegex() {
        return this.eqsrlrngRegex;
    }

    protected void setEqsrlrngRegex(String eqsrlrngRegex) {
        this.eqsrlrngRegex = eqsrlrngRegex;
    }

    public String getEqsrlrngStartingSerialNbr() {
        return this.eqsrlrngStartingSerialNbr;
    }

    protected void setEqsrlrngStartingSerialNbr(String eqsrlrngStartingSerialNbr) {
        this.eqsrlrngStartingSerialNbr = eqsrlrngStartingSerialNbr;
    }

    public String getEqsrlrngEndingSerialNbr() {
        return this.eqsrlrngEndingSerialNbr;
    }

    protected void setEqsrlrngEndingSerialNbr(String eqsrlrngEndingSerialNbr) {
        this.eqsrlrngEndingSerialNbr = eqsrlrngEndingSerialNbr;
    }

    public Long getEqsrlrngLengthMm() {
        return this.eqsrlrngLengthMm;
    }

    protected void setEqsrlrngLengthMm(Long eqsrlrngLengthMm) {
        this.eqsrlrngLengthMm = eqsrlrngLengthMm;
    }

    public Long getEqsrlrngHeightMm() {
        return this.eqsrlrngHeightMm;
    }

    protected void setEqsrlrngHeightMm(Long eqsrlrngHeightMm) {
        this.eqsrlrngHeightMm = eqsrlrngHeightMm;
    }

    public Long getEqsrlrngWidthMm() {
        return this.eqsrlrngWidthMm;
    }

    protected void setEqsrlrngWidthMm(Long eqsrlrngWidthMm) {
        this.eqsrlrngWidthMm = eqsrlrngWidthMm;
    }

    public Double getEqsrlrngTareWeightKg() {
        return this.eqsrlrngTareWeightKg;
    }

    protected void setEqsrlrngTareWeightKg(Double eqsrlrngTareWeightKg) {
        this.eqsrlrngTareWeightKg = eqsrlrngTareWeightKg;
    }

    public Double getEqsrlrngSafeWeightKg() {
        return this.eqsrlrngSafeWeightKg;
    }

    protected void setEqsrlrngSafeWeightKg(Double eqsrlrngSafeWeightKg) {
        this.eqsrlrngSafeWeightKg = eqsrlrngSafeWeightKg;
    }

    public EquipRfrTypeEnum getEqsrlrngRfrType() {
        return this.eqsrlrngRfrType;
    }

    protected void setEqsrlrngRfrType(EquipRfrTypeEnum eqsrlrngRfrType) {
        this.eqsrlrngRfrType = eqsrlrngRfrType;
    }

    public EquipIsoGroupEnum getEqsrlrngIsoGroup() {
        return this.eqsrlrngIsoGroup;
    }

    protected void setEqsrlrngIsoGroup(EquipIsoGroupEnum eqsrlrngIsoGroup) {
        this.eqsrlrngIsoGroup = eqsrlrngIsoGroup;
    }

    public Boolean getEqsrlrngIsSuperFreezeReefer() {
        return this.eqsrlrngIsSuperFreezeReefer;
    }

    protected void setEqsrlrngIsSuperFreezeReefer(Boolean eqsrlrngIsSuperFreezeReefer) {
        this.eqsrlrngIsSuperFreezeReefer = eqsrlrngIsSuperFreezeReefer;
    }

    public Boolean getEqsrlrngIsControlledAtmosphereReefer() {
        return this.eqsrlrngIsControlledAtmosphereReefer;
    }

    protected void setEqsrlrngIsControlledAtmosphereReefer(Boolean eqsrlrngIsControlledAtmosphereReefer) {
        this.eqsrlrngIsControlledAtmosphereReefer = eqsrlrngIsControlledAtmosphereReefer;
    }

    public Boolean getEqsrlrngNoStowOnTopIfEmpty() {
        return this.eqsrlrngNoStowOnTopIfEmpty;
    }

    protected void setEqsrlrngNoStowOnTopIfEmpty(Boolean eqsrlrngNoStowOnTopIfEmpty) {
        this.eqsrlrngNoStowOnTopIfEmpty = eqsrlrngNoStowOnTopIfEmpty;
    }

    public Boolean getEqsrlrngNoStowOnTopIfLaden() {
        return this.eqsrlrngNoStowOnTopIfLaden;
    }

    protected void setEqsrlrngNoStowOnTopIfLaden(Boolean eqsrlrngNoStowOnTopIfLaden) {
        this.eqsrlrngNoStowOnTopIfLaden = eqsrlrngNoStowOnTopIfLaden;
    }

    public Boolean getEqsrlrngMustStowBelowDeck() {
        return this.eqsrlrngMustStowBelowDeck;
    }

    protected void setEqsrlrngMustStowBelowDeck(Boolean eqsrlrngMustStowBelowDeck) {
        this.eqsrlrngMustStowBelowDeck = eqsrlrngMustStowBelowDeck;
    }

    public Boolean getEqsrlrngMustStowAboveDeck() {
        return this.eqsrlrngMustStowAboveDeck;
    }

    protected void setEqsrlrngMustStowAboveDeck(Boolean eqsrlrngMustStowAboveDeck) {
        this.eqsrlrngMustStowAboveDeck = eqsrlrngMustStowAboveDeck;
    }

    public Boolean getEqsrlrngUsesAccessories() {
        return this.eqsrlrngUsesAccessories;
    }

    protected void setEqsrlrngUsesAccessories(Boolean eqsrlrngUsesAccessories) {
        this.eqsrlrngUsesAccessories = eqsrlrngUsesAccessories;
    }

    public Boolean getEqsrlrngIsTemperatureControlled() {
        return this.eqsrlrngIsTemperatureControlled;
    }

    protected void setEqsrlrngIsTemperatureControlled(Boolean eqsrlrngIsTemperatureControlled) {
        this.eqsrlrngIsTemperatureControlled = eqsrlrngIsTemperatureControlled;
    }

    public Boolean getEqsrlrngOogOk() {
        return this.eqsrlrngOogOk;
    }

    protected void setEqsrlrngOogOk(Boolean eqsrlrngOogOk) {
        this.eqsrlrngOogOk = eqsrlrngOogOk;
    }

    public Boolean getEqsrlrngIsUnsealable() {
        return this.eqsrlrngIsUnsealable;
    }

    protected void setEqsrlrngIsUnsealable(Boolean eqsrlrngIsUnsealable) {
        this.eqsrlrngIsUnsealable = eqsrlrngIsUnsealable;
    }

    public Boolean getEqsrlrngHasWheels() {
        return this.eqsrlrngHasWheels;
    }

    protected void setEqsrlrngHasWheels(Boolean eqsrlrngHasWheels) {
        this.eqsrlrngHasWheels = eqsrlrngHasWheels;
    }

    public Boolean getEqsrlrngIsOpen() {
        return this.eqsrlrngIsOpen;
    }

    protected void setEqsrlrngIsOpen(Boolean eqsrlrngIsOpen) {
        this.eqsrlrngIsOpen = eqsrlrngIsOpen;
    }

    public Date getEqsrlrngCreated() {
        return this.eqsrlrngCreated;
    }

    protected void setEqsrlrngCreated(Date eqsrlrngCreated) {
        this.eqsrlrngCreated = eqsrlrngCreated;
    }

    public String getEqsrlrngCreator() {
        return this.eqsrlrngCreator;
    }

    protected void setEqsrlrngCreator(String eqsrlrngCreator) {
        this.eqsrlrngCreator = eqsrlrngCreator;
    }

    public Date getEqsrlrngChanged() {
        return this.eqsrlrngChanged;
    }

    protected void setEqsrlrngChanged(Date eqsrlrngChanged) {
        this.eqsrlrngChanged = eqsrlrngChanged;
    }

    public String getEqsrlrngChanger() {
        return this.eqsrlrngChanger;
    }

    protected void setEqsrlrngChanger(String eqsrlrngChanger) {
        this.eqsrlrngChanger = eqsrlrngChanger;
    }

    public String getEqsrlrngEqtypeId() {
        return this.eqsrlrngEqtypeId;
    }

    protected void setEqsrlrngEqtypeId(String eqsrlrngEqtypeId) {
        this.eqsrlrngEqtypeId = eqsrlrngEqtypeId;
    }

    public LifeCycleStateEnum getEqsrlrngLifeCycleState() {
        return this.eqsrlrngLifeCycleState;
    }

    public void setEqsrlrngLifeCycleState(LifeCycleStateEnum eqsrlrngLifeCycleState) {
        this.eqsrlrngLifeCycleState = eqsrlrngLifeCycleState;
    }

    public EntitySet getEqsrlrngScope() {
        return this.eqsrlrngScope;
    }

    protected void setEqsrlrngScope(EntitySet eqsrlrngScope) {
        this.eqsrlrngScope = eqsrlrngScope;
    }

}
