package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.business.enums.inventory.EqDamageSeverityEnum;
import com.zpmc.ztos.infra.base.business.equipments.EqComponent;
import com.zpmc.ztos.infra.base.business.inventory.EquipDamageType;
import com.zpmc.ztos.infra.base.business.inventory.UnitEquipDamages;
import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;

import java.io.Serializable;
import java.util.Date;

public class UnitEquipDamageItemDO extends DatabaseEntity implements Serializable {
    private Long dmgitemGkey;
    private String dmgitemLocation;
    private Long dmgitemQuantity;
    private Double dmgitemLength;
    private Double dmgitemWidth;
    private Double dmgitemDepth;
    private EqDamageSeverityEnum dmgitemSeverity;
    private Date dmgitemReported;
    private Date dmgitemRepaired;
    private String dmgitemDescription;
    private Date dmgitemCreated;
    private String dmgitemCreator;
    private Date dmgitemChanged;
    private String dmgitemChanger;
    private UnitEquipDamages dmgitemDmgs;
    private EquipDamageType dmgitemType;
    private EqComponent dmgitemComponent;

    public Serializable getPrimaryKey() {
        return this.getDmgitemGkey();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        Long id = this.getDmgitemGkey();
        if (id == null) {
            return false;
        }
        if (!(other instanceof UnitEquipDamageItemDO)) {
            return false;
        }
        UnitEquipDamageItemDO that = (UnitEquipDamageItemDO)other;
        return ((Object)id).equals(that.getDmgitemGkey());
    }

    public int hashCode() {
        Long id = this.getDmgitemGkey();
        return id == null ? System.identityHashCode(this) : ((Object)id).hashCode();
    }

    public Long getDmgitemGkey() {
        return this.dmgitemGkey;
    }

    public void setDmgitemGkey(Long dmgitemGkey) {
        this.dmgitemGkey = dmgitemGkey;
    }

    public String getDmgitemLocation() {
        return this.dmgitemLocation;
    }

    public void setDmgitemLocation(String dmgitemLocation) {
        this.dmgitemLocation = dmgitemLocation;
    }

    public Long getDmgitemQuantity() {
        return this.dmgitemQuantity;
    }

    public void setDmgitemQuantity(Long dmgitemQuantity) {
        this.dmgitemQuantity = dmgitemQuantity;
    }

    public Double getDmgitemLength() {
        return this.dmgitemLength;
    }

    public void setDmgitemLength(Double dmgitemLength) {
        this.dmgitemLength = dmgitemLength;
    }

    public Double getDmgitemWidth() {
        return this.dmgitemWidth;
    }

    public void setDmgitemWidth(Double dmgitemWidth) {
        this.dmgitemWidth = dmgitemWidth;
    }

    public Double getDmgitemDepth() {
        return this.dmgitemDepth;
    }

    public void setDmgitemDepth(Double dmgitemDepth) {
        this.dmgitemDepth = dmgitemDepth;
    }

    public EqDamageSeverityEnum getDmgitemSeverity() {
        return this.dmgitemSeverity;
    }

    public void setDmgitemSeverity(EqDamageSeverityEnum dmgitemSeverity) {
        this.dmgitemSeverity = dmgitemSeverity;
    }

    public Date getDmgitemReported() {
        return this.dmgitemReported;
    }

    public void setDmgitemReported(Date dmgitemReported) {
        this.dmgitemReported = dmgitemReported;
    }

    public Date getDmgitemRepaired() {
        return this.dmgitemRepaired;
    }

    public void setDmgitemRepaired(Date dmgitemRepaired) {
        this.dmgitemRepaired = dmgitemRepaired;
    }

    public String getDmgitemDescription() {
        return this.dmgitemDescription;
    }

    public void setDmgitemDescription(String dmgitemDescription) {
        this.dmgitemDescription = dmgitemDescription;
    }

    public Date getDmgitemCreated() {
        return this.dmgitemCreated;
    }

    protected void setDmgitemCreated(Date dmgitemCreated) {
        this.dmgitemCreated = dmgitemCreated;
    }

    public String getDmgitemCreator() {
        return this.dmgitemCreator;
    }

    protected void setDmgitemCreator(String dmgitemCreator) {
        this.dmgitemCreator = dmgitemCreator;
    }

    public Date getDmgitemChanged() {
        return this.dmgitemChanged;
    }

    protected void setDmgitemChanged(Date dmgitemChanged) {
        this.dmgitemChanged = dmgitemChanged;
    }

    public String getDmgitemChanger() {
        return this.dmgitemChanger;
    }

    protected void setDmgitemChanger(String dmgitemChanger) {
        this.dmgitemChanger = dmgitemChanger;
    }

    public UnitEquipDamages getDmgitemDmgs() {
        return this.dmgitemDmgs;
    }

    public void setDmgitemDmgs(UnitEquipDamages dmgitemDmgs) {
        this.dmgitemDmgs = dmgitemDmgs;
    }

    public EquipDamageType getDmgitemType() {
        return this.dmgitemType;
    }

    public void setDmgitemType(EquipDamageType dmgitemType) {
        this.dmgitemType = dmgitemType;
    }

    public EqComponent getDmgitemComponent() {
        return this.dmgitemComponent;
    }

    public void setDmgitemComponent(EqComponent dmgitemComponent) {
        this.dmgitemComponent = dmgitemComponent;
    }
}
