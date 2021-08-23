package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.business.enums.argo.PowerChargeEnum;
import com.zpmc.ztos.infra.base.business.model.EntitySet;
import com.zpmc.ztos.infra.base.business.model.Extension;
import com.zpmc.ztos.infra.base.common.model.ArgoCalendar;
import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;

import java.io.Serializable;
import java.util.Date;

public class StorageRuleDO extends DatabaseEntity implements Serializable {
    private Long sruleGkey;
    private String sruleId;
    private String sruleStartDay;
    private Boolean sruleIsStartDayIncluded;
    private String sruleEndDay;
    private Boolean sruleIsEndDayIncluded;
    private Boolean sruleIsFreedaysIncluded;
    private Boolean sruleIsGratisIncluded;
    private Long sruleRoundUpHours;
    private Long sruleRoundUpMinutes;
    private Boolean sruleIsRuleForPower;
    private Long sruleStartDayCuttOffHours;
    private Long sruleEndDayCuttOffHours;
    private PowerChargeEnum srulePowerChargeBy;
    private Long srulePowerFirstTierRounding;
    private Long srulePowerOtherTierRounding;
    private Boolean sruleIsFreeTimeChgedIfExceeded;
    private Date sruleCreated;
    private String sruleCreator;
    private Date sruleChanged;
    private String sruleChanger;
    private EntitySet sruleScope;
    private Extension sruleStartDayExtension;
    private Extension sruleEndDayExtension;
    private ArgoCalendar sruleCalendar;
    private Extension sruleCalculationExtension;

    public Serializable getPrimaryKey() {
        return this.getSruleGkey();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        Long id = this.getSruleGkey();
        if (id == null) {
            return false;
        }
        if (!(other instanceof StorageRuleDO)) {
            return false;
        }
        StorageRuleDO that = (StorageRuleDO)other;
        return ((Object)id).equals(that.getSruleGkey());
    }

    public int hashCode() {
        Long id = this.getSruleGkey();
        return id == null ? System.identityHashCode(this) : ((Object)id).hashCode();
    }

    public Long getSruleGkey() {
        return this.sruleGkey;
    }

    protected void setSruleGkey(Long sruleGkey) {
        this.sruleGkey = sruleGkey;
    }

    public String getSruleId() {
        return this.sruleId;
    }

    protected void setSruleId(String sruleId) {
        this.sruleId = sruleId;
    }

    public String getSruleStartDay() {
        return this.sruleStartDay;
    }

    protected void setSruleStartDay(String sruleStartDay) {
        this.sruleStartDay = sruleStartDay;
    }

    public Boolean getSruleIsStartDayIncluded() {
        return this.sruleIsStartDayIncluded;
    }

    protected void setSruleIsStartDayIncluded(Boolean sruleIsStartDayIncluded) {
        this.sruleIsStartDayIncluded = sruleIsStartDayIncluded;
    }

    public String getSruleEndDay() {
        return this.sruleEndDay;
    }

    protected void setSruleEndDay(String sruleEndDay) {
        this.sruleEndDay = sruleEndDay;
    }

    public Boolean getSruleIsEndDayIncluded() {
        return this.sruleIsEndDayIncluded;
    }

    protected void setSruleIsEndDayIncluded(Boolean sruleIsEndDayIncluded) {
        this.sruleIsEndDayIncluded = sruleIsEndDayIncluded;
    }

    public Boolean getSruleIsFreedaysIncluded() {
        return this.sruleIsFreedaysIncluded;
    }

    protected void setSruleIsFreedaysIncluded(Boolean sruleIsFreedaysIncluded) {
        this.sruleIsFreedaysIncluded = sruleIsFreedaysIncluded;
    }

    public Boolean getSruleIsGratisIncluded() {
        return this.sruleIsGratisIncluded;
    }

    protected void setSruleIsGratisIncluded(Boolean sruleIsGratisIncluded) {
        this.sruleIsGratisIncluded = sruleIsGratisIncluded;
    }

    public Long getSruleRoundUpHours() {
        return this.sruleRoundUpHours;
    }

    protected void setSruleRoundUpHours(Long sruleRoundUpHours) {
        this.sruleRoundUpHours = sruleRoundUpHours;
    }

    public Long getSruleRoundUpMinutes() {
        return this.sruleRoundUpMinutes;
    }

    protected void setSruleRoundUpMinutes(Long sruleRoundUpMinutes) {
        this.sruleRoundUpMinutes = sruleRoundUpMinutes;
    }

    public Boolean getSruleIsRuleForPower() {
        return this.sruleIsRuleForPower;
    }

    protected void setSruleIsRuleForPower(Boolean sruleIsRuleForPower) {
        this.sruleIsRuleForPower = sruleIsRuleForPower;
    }

    public Long getSruleStartDayCuttOffHours() {
        return this.sruleStartDayCuttOffHours;
    }

    protected void setSruleStartDayCuttOffHours(Long sruleStartDayCuttOffHours) {
        this.sruleStartDayCuttOffHours = sruleStartDayCuttOffHours;
    }

    public Long getSruleEndDayCuttOffHours() {
        return this.sruleEndDayCuttOffHours;
    }

    protected void setSruleEndDayCuttOffHours(Long sruleEndDayCuttOffHours) {
        this.sruleEndDayCuttOffHours = sruleEndDayCuttOffHours;
    }

    public PowerChargeEnum getSrulePowerChargeBy() {
        return this.srulePowerChargeBy;
    }

    protected void setSrulePowerChargeBy(PowerChargeEnum srulePowerChargeBy) {
        this.srulePowerChargeBy = srulePowerChargeBy;
    }

    public Long getSrulePowerFirstTierRounding() {
        return this.srulePowerFirstTierRounding;
    }

    protected void setSrulePowerFirstTierRounding(Long srulePowerFirstTierRounding) {
        this.srulePowerFirstTierRounding = srulePowerFirstTierRounding;
    }

    public Long getSrulePowerOtherTierRounding() {
        return this.srulePowerOtherTierRounding;
    }

    protected void setSrulePowerOtherTierRounding(Long srulePowerOtherTierRounding) {
        this.srulePowerOtherTierRounding = srulePowerOtherTierRounding;
    }

    public Boolean getSruleIsFreeTimeChgedIfExceeded() {
        return this.sruleIsFreeTimeChgedIfExceeded;
    }

    protected void setSruleIsFreeTimeChgedIfExceeded(Boolean sruleIsFreeTimeChgedIfExceeded) {
        this.sruleIsFreeTimeChgedIfExceeded = sruleIsFreeTimeChgedIfExceeded;
    }

    public Date getSruleCreated() {
        return this.sruleCreated;
    }

    protected void setSruleCreated(Date sruleCreated) {
        this.sruleCreated = sruleCreated;
    }

    public String getSruleCreator() {
        return this.sruleCreator;
    }

    protected void setSruleCreator(String sruleCreator) {
        this.sruleCreator = sruleCreator;
    }

    public Date getSruleChanged() {
        return this.sruleChanged;
    }

    protected void setSruleChanged(Date sruleChanged) {
        this.sruleChanged = sruleChanged;
    }

    public String getSruleChanger() {
        return this.sruleChanger;
    }

    protected void setSruleChanger(String sruleChanger) {
        this.sruleChanger = sruleChanger;
    }

    public EntitySet getSruleScope() {
        return this.sruleScope;
    }

    protected void setSruleScope(EntitySet sruleScope) {
        this.sruleScope = sruleScope;
    }

    public Extension getSruleStartDayExtension() {
        return this.sruleStartDayExtension;
    }

    protected void setSruleStartDayExtension(Extension sruleStartDayExtension) {
        this.sruleStartDayExtension = sruleStartDayExtension;
    }

    public Extension getSruleEndDayExtension() {
        return this.sruleEndDayExtension;
    }

    protected void setSruleEndDayExtension(Extension sruleEndDayExtension) {
        this.sruleEndDayExtension = sruleEndDayExtension;
    }

    public ArgoCalendar getSruleCalendar() {
        return this.sruleCalendar;
    }

    protected void setSruleCalendar(ArgoCalendar sruleCalendar) {
        this.sruleCalendar = sruleCalendar;
    }

    public Extension getSruleCalculationExtension() {
        return this.sruleCalculationExtension;
    }

    protected void setSruleCalculationExtension(Extension sruleCalculationExtension) {
        this.sruleCalculationExtension = sruleCalculationExtension;
    }
}
