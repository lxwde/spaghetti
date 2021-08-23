package com.zpmc.ztos.infra.base.business.enums.argo;

import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;

import java.io.Serializable;
import java.util.Date;

public class ReeferRqmntsDO extends DatabaseEntity implements Serializable {
    private Double rfreqTempRequiredC;
    private Double rfreqTempLimitMaxC;
    private Double rfreqTempLimitMinC;
    private Boolean rfreqTempShowFahrenheit;
    private Double rfreqVentRequired;
    private VentUnitEnum rfreqVentUnit;
    private Double rfreqHumidityPct;
    private Double rfreqO2Pct;
    private Double rfreqCO2Pct;
    private Date rfreqLatestOnPowerTime;
    private Date rfreqRequestedOffPowerTime;
    private Date rfreqTimeMonitor1;
    private Date rfreqTimeMonitor2;
    private Date rfreqTimeMonitor3;
    private Date rfreqTimeMonitor4;
    private Integer rfreqMinutesBeforeUnplugWarning;
    private Boolean rfreqExtendedTimeMonitors;

    public Double getRfreqTempRequiredC() {
        return this.rfreqTempRequiredC;
    }

    protected void setRfreqTempRequiredC(Double rfreqTempRequiredC) {
        this.rfreqTempRequiredC = rfreqTempRequiredC;
    }

    public Double getRfreqTempLimitMaxC() {
        return this.rfreqTempLimitMaxC;
    }

    protected void setRfreqTempLimitMaxC(Double rfreqTempLimitMaxC) {
        this.rfreqTempLimitMaxC = rfreqTempLimitMaxC;
    }

    public Double getRfreqTempLimitMinC() {
        return this.rfreqTempLimitMinC;
    }

    protected void setRfreqTempLimitMinC(Double rfreqTempLimitMinC) {
        this.rfreqTempLimitMinC = rfreqTempLimitMinC;
    }

    public Boolean getRfreqTempShowFahrenheit() {
        return this.rfreqTempShowFahrenheit;
    }

    protected void setRfreqTempShowFahrenheit(Boolean rfreqTempShowFahrenheit) {
        this.rfreqTempShowFahrenheit = rfreqTempShowFahrenheit;
    }

    public Double getRfreqVentRequired() {
        return this.rfreqVentRequired;
    }

    protected void setRfreqVentRequired(Double rfreqVentRequired) {
        this.rfreqVentRequired = rfreqVentRequired;
    }

    public VentUnitEnum getRfreqVentUnit() {
        return this.rfreqVentUnit;
    }

    protected void setRfreqVentUnit(VentUnitEnum rfreqVentUnit) {
        this.rfreqVentUnit = rfreqVentUnit;
    }

    public Double getRfreqHumidityPct() {
        return this.rfreqHumidityPct;
    }

    protected void setRfreqHumidityPct(Double rfreqHumidityPct) {
        this.rfreqHumidityPct = rfreqHumidityPct;
    }

    public Double getRfreqO2Pct() {
        return this.rfreqO2Pct;
    }

    protected void setRfreqO2Pct(Double rfreqO2Pct) {
        this.rfreqO2Pct = rfreqO2Pct;
    }

    public Double getRfreqCO2Pct() {
        return this.rfreqCO2Pct;
    }

    protected void setRfreqCO2Pct(Double rfreqCO2Pct) {
        this.rfreqCO2Pct = rfreqCO2Pct;
    }

    public Date getRfreqLatestOnPowerTime() {
        return this.rfreqLatestOnPowerTime;
    }

    protected void setRfreqLatestOnPowerTime(Date rfreqLatestOnPowerTime) {
        this.rfreqLatestOnPowerTime = rfreqLatestOnPowerTime;
    }

    public Date getRfreqRequestedOffPowerTime() {
        return this.rfreqRequestedOffPowerTime;
    }

    protected void setRfreqRequestedOffPowerTime(Date rfreqRequestedOffPowerTime) {
        this.rfreqRequestedOffPowerTime = rfreqRequestedOffPowerTime;
    }

    public Date getRfreqTimeMonitor1() {
        return this.rfreqTimeMonitor1;
    }

    protected void setRfreqTimeMonitor1(Date rfreqTimeMonitor1) {
        this.rfreqTimeMonitor1 = rfreqTimeMonitor1;
    }

    public Date getRfreqTimeMonitor2() {
        return this.rfreqTimeMonitor2;
    }

    protected void setRfreqTimeMonitor2(Date rfreqTimeMonitor2) {
        this.rfreqTimeMonitor2 = rfreqTimeMonitor2;
    }

    public Date getRfreqTimeMonitor3() {
        return this.rfreqTimeMonitor3;
    }

    protected void setRfreqTimeMonitor3(Date rfreqTimeMonitor3) {
        this.rfreqTimeMonitor3 = rfreqTimeMonitor3;
    }

    public Date getRfreqTimeMonitor4() {
        return this.rfreqTimeMonitor4;
    }

    protected void setRfreqTimeMonitor4(Date rfreqTimeMonitor4) {
        this.rfreqTimeMonitor4 = rfreqTimeMonitor4;
    }

    public Integer getRfreqMinutesBeforeUnplugWarning() {
        return this.rfreqMinutesBeforeUnplugWarning;
    }

    protected void setRfreqMinutesBeforeUnplugWarning(Integer rfreqMinutesBeforeUnplugWarning) {
        this.rfreqMinutesBeforeUnplugWarning = rfreqMinutesBeforeUnplugWarning;
    }

    public Boolean getRfreqExtendedTimeMonitors() {
        return this.rfreqExtendedTimeMonitors;
    }

    protected void setRfreqExtendedTimeMonitors(Boolean rfreqExtendedTimeMonitors) {
        this.rfreqExtendedTimeMonitors = rfreqExtendedTimeMonitors;
    }
}
