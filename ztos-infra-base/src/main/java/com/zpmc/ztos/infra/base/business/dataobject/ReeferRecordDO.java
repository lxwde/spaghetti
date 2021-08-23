package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.business.enums.argo.DataSourceEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.VentUnitEnum;
import com.zpmc.ztos.infra.base.business.inventory.Unit;
import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;

import java.io.Serializable;
import java.util.Date;

public class ReeferRecordDO extends DatabaseEntity implements Serializable {
    private Long rfrecGkey;
    private Date rfrecTime;
    private Double rfrecReturnTmp;
    private Double rfrecVentSetting;
    private VentUnitEnum rfrecVentUnit;
    private Double rfrecHmdty;
    private Double rfrecO2;
    private Double rfrecCO2;
    private String rfrecFaultCode;
    private DataSourceEnum rfrecDataSource;
    private Boolean rfrecIsPowered;
    private Boolean rfrecHasMalfunction;
    private Boolean rfrecIsBulbOn;
    private Double rfrecDefrostIntervalHours;
    private Double rfrecReeferHours;
    private Boolean rfrecAreDrainsOpen;
    private String rfrecFanSetting;
    private Double rfrecMinMonitoredTmp;
    private Double rfrecMaxMonitoredTmp;
    private Double rfrecDefrostTmp;
    private Double rfrecSetPointTmp;
    private Double rfrecSupplyTmp;
    private Double rfrecSensor1Tmp;
    private Double rfrecSensor2Tmp;
    private Double rfrecSensor3Tmp;
    private Double rfrecSensor4Tmp;
    private Boolean rfrecIsAlarmOn;
    private Double rfrecFuelLevel;
    private String rfrecComputerId;
    private String rfrecTaskId;
    private String rfrecRemark;
    private String rfrecCreationRefId;
    private Unit rfrecUnit;

    public Serializable getPrimaryKey() {
        return this.getRfrecGkey();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        Long id = this.getRfrecGkey();
        if (id == null) {
            return false;
        }
        if (!(other instanceof ReeferRecordDO)) {
            return false;
        }
        ReeferRecordDO that = (ReeferRecordDO)other;
        return ((Object)id).equals(that.getRfrecGkey());
    }

    public int hashCode() {
        Long id = this.getRfrecGkey();
        return id == null ? System.identityHashCode(this) : ((Object)id).hashCode();
    }

    public Long getRfrecGkey() {
        return this.rfrecGkey;
    }

    protected void setRfrecGkey(Long rfrecGkey) {
        this.rfrecGkey = rfrecGkey;
    }

    public Date getRfrecTime() {
        return this.rfrecTime;
    }

    protected void setRfrecTime(Date rfrecTime) {
        this.rfrecTime = rfrecTime;
    }

    public Double getRfrecReturnTmp() {
        return this.rfrecReturnTmp;
    }

    protected void setRfrecReturnTmp(Double rfrecReturnTmp) {
        this.rfrecReturnTmp = rfrecReturnTmp;
    }

    public Double getRfrecVentSetting() {
        return this.rfrecVentSetting;
    }

    protected void setRfrecVentSetting(Double rfrecVentSetting) {
        this.rfrecVentSetting = rfrecVentSetting;
    }

    public VentUnitEnum getRfrecVentUnit() {
        return this.rfrecVentUnit;
    }

    protected void setRfrecVentUnit(VentUnitEnum rfrecVentUnit) {
        this.rfrecVentUnit = rfrecVentUnit;
    }

    public Double getRfrecHmdty() {
        return this.rfrecHmdty;
    }

    protected void setRfrecHmdty(Double rfrecHmdty) {
        this.rfrecHmdty = rfrecHmdty;
    }

    public Double getRfrecO2() {
        return this.rfrecO2;
    }

    protected void setRfrecO2(Double rfrecO2) {
        this.rfrecO2 = rfrecO2;
    }

    public Double getRfrecCO2() {
        return this.rfrecCO2;
    }

    protected void setRfrecCO2(Double rfrecCO2) {
        this.rfrecCO2 = rfrecCO2;
    }

    public String getRfrecFaultCode() {
        return this.rfrecFaultCode;
    }

    protected void setRfrecFaultCode(String rfrecFaultCode) {
        this.rfrecFaultCode = rfrecFaultCode;
    }

    public DataSourceEnum getRfrecDataSource() {
        return this.rfrecDataSource;
    }

    protected void setRfrecDataSource(DataSourceEnum rfrecDataSource) {
        this.rfrecDataSource = rfrecDataSource;
    }

    public Boolean getRfrecIsPowered() {
        return this.rfrecIsPowered;
    }

    protected void setRfrecIsPowered(Boolean rfrecIsPowered) {
        this.rfrecIsPowered = rfrecIsPowered;
    }

    public Boolean getRfrecHasMalfunction() {
        return this.rfrecHasMalfunction;
    }

    protected void setRfrecHasMalfunction(Boolean rfrecHasMalfunction) {
        this.rfrecHasMalfunction = rfrecHasMalfunction;
    }

    public Boolean getRfrecIsBulbOn() {
        return this.rfrecIsBulbOn;
    }

    protected void setRfrecIsBulbOn(Boolean rfrecIsBulbOn) {
        this.rfrecIsBulbOn = rfrecIsBulbOn;
    }

    public Double getRfrecDefrostIntervalHours() {
        return this.rfrecDefrostIntervalHours;
    }

    protected void setRfrecDefrostIntervalHours(Double rfrecDefrostIntervalHours) {
        this.rfrecDefrostIntervalHours = rfrecDefrostIntervalHours;
    }

    public Double getRfrecReeferHours() {
        return this.rfrecReeferHours;
    }

    protected void setRfrecReeferHours(Double rfrecReeferHours) {
        this.rfrecReeferHours = rfrecReeferHours;
    }

    public Boolean getRfrecAreDrainsOpen() {
        return this.rfrecAreDrainsOpen;
    }

    protected void setRfrecAreDrainsOpen(Boolean rfrecAreDrainsOpen) {
        this.rfrecAreDrainsOpen = rfrecAreDrainsOpen;
    }

    public String getRfrecFanSetting() {
        return this.rfrecFanSetting;
    }

    protected void setRfrecFanSetting(String rfrecFanSetting) {
        this.rfrecFanSetting = rfrecFanSetting;
    }

    public Double getRfrecMinMonitoredTmp() {
        return this.rfrecMinMonitoredTmp;
    }

    protected void setRfrecMinMonitoredTmp(Double rfrecMinMonitoredTmp) {
        this.rfrecMinMonitoredTmp = rfrecMinMonitoredTmp;
    }

    public Double getRfrecMaxMonitoredTmp() {
        return this.rfrecMaxMonitoredTmp;
    }

    protected void setRfrecMaxMonitoredTmp(Double rfrecMaxMonitoredTmp) {
        this.rfrecMaxMonitoredTmp = rfrecMaxMonitoredTmp;
    }

    public Double getRfrecDefrostTmp() {
        return this.rfrecDefrostTmp;
    }

    protected void setRfrecDefrostTmp(Double rfrecDefrostTmp) {
        this.rfrecDefrostTmp = rfrecDefrostTmp;
    }

    public Double getRfrecSetPointTmp() {
        return this.rfrecSetPointTmp;
    }

    protected void setRfrecSetPointTmp(Double rfrecSetPointTmp) {
        this.rfrecSetPointTmp = rfrecSetPointTmp;
    }

    public Double getRfrecSupplyTmp() {
        return this.rfrecSupplyTmp;
    }

    protected void setRfrecSupplyTmp(Double rfrecSupplyTmp) {
        this.rfrecSupplyTmp = rfrecSupplyTmp;
    }

    public Double getRfrecSensor1Tmp() {
        return this.rfrecSensor1Tmp;
    }

    protected void setRfrecSensor1Tmp(Double rfrecSensor1Tmp) {
        this.rfrecSensor1Tmp = rfrecSensor1Tmp;
    }

    public Double getRfrecSensor2Tmp() {
        return this.rfrecSensor2Tmp;
    }

    protected void setRfrecSensor2Tmp(Double rfrecSensor2Tmp) {
        this.rfrecSensor2Tmp = rfrecSensor2Tmp;
    }

    public Double getRfrecSensor3Tmp() {
        return this.rfrecSensor3Tmp;
    }

    protected void setRfrecSensor3Tmp(Double rfrecSensor3Tmp) {
        this.rfrecSensor3Tmp = rfrecSensor3Tmp;
    }

    public Double getRfrecSensor4Tmp() {
        return this.rfrecSensor4Tmp;
    }

    protected void setRfrecSensor4Tmp(Double rfrecSensor4Tmp) {
        this.rfrecSensor4Tmp = rfrecSensor4Tmp;
    }

    public Boolean getRfrecIsAlarmOn() {
        return this.rfrecIsAlarmOn;
    }

    protected void setRfrecIsAlarmOn(Boolean rfrecIsAlarmOn) {
        this.rfrecIsAlarmOn = rfrecIsAlarmOn;
    }

    public Double getRfrecFuelLevel() {
        return this.rfrecFuelLevel;
    }

    protected void setRfrecFuelLevel(Double rfrecFuelLevel) {
        this.rfrecFuelLevel = rfrecFuelLevel;
    }

    public String getRfrecComputerId() {
        return this.rfrecComputerId;
    }

    protected void setRfrecComputerId(String rfrecComputerId) {
        this.rfrecComputerId = rfrecComputerId;
    }

    public String getRfrecTaskId() {
        return this.rfrecTaskId;
    }

    protected void setRfrecTaskId(String rfrecTaskId) {
        this.rfrecTaskId = rfrecTaskId;
    }

    public String getRfrecRemark() {
        return this.rfrecRemark;
    }

    protected void setRfrecRemark(String rfrecRemark) {
        this.rfrecRemark = rfrecRemark;
    }

    public String getRfrecCreationRefId() {
        return this.rfrecCreationRefId;
    }

    protected void setRfrecCreationRefId(String rfrecCreationRefId) {
        this.rfrecCreationRefId = rfrecCreationRefId;
    }

    public Unit getRfrecUnit() {
        return this.rfrecUnit;
    }

    protected void setRfrecUnit(Unit rfrecUnit) {
        this.rfrecUnit = rfrecUnit;
    }
}
