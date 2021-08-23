package com.zpmc.ztos.infra.base.business.inventory;

import com.zpmc.ztos.infra.base.business.enums.argo.ReeferRqmntsDO;
import com.zpmc.ztos.infra.base.business.enums.argo.VentUnitEnum;
import com.zpmc.ztos.infra.base.common.exceptions.BizFailure;
import com.zpmc.ztos.infra.base.common.helps.ContextHelper;
import com.zpmc.ztos.infra.base.common.utils.ArgoUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class ReeferRqmnts extends ReeferRqmntsDO {
    public ReeferRqmnts() {
        this.setRfreqTempShowFahrenheit(Boolean.FALSE);
        this.setRfreqExtendedTimeMonitors(Boolean.FALSE);
    }

    public ReeferRqmnts makeCopy() {
        try {
            return (ReeferRqmnts)this.clone();
        }
        catch (CloneNotSupportedException e) {
            throw BizFailure.wrap((Throwable)e);
        }
    }

    @Override
    public void setRfreqCO2Pct(Double inRfreqCO2Pct) {
        super.setRfreqCO2Pct(inRfreqCO2Pct);
    }

    @Override
    public void setRfreqO2Pct(Double inRfreqO2Pct) {
        super.setRfreqO2Pct(inRfreqO2Pct);
    }

    @Override
    public void setRfreqVentRequired(Double inRfreqVentPct) {
        super.setRfreqVentRequired(inRfreqVentPct);
    }

    @Override
    public void setRfreqVentUnit(VentUnitEnum inVentUnit) {
        super.setRfreqVentUnit(inVentUnit);
    }

    @Override
    public void setRfreqHumidityPct(Double inRfreqHumidityPct) {
        super.setRfreqHumidityPct(inRfreqHumidityPct);
    }

    @Override
    public void setRfreqTempRequiredC(Double inRfreqTempRequiredC) {
        super.setRfreqTempRequiredC(inRfreqTempRequiredC);
    }

    @Override
    public void setRfreqLatestOnPowerTime(Date inRfreqLatestOnPowerTime) {
        super.setRfreqLatestOnPowerTime(inRfreqLatestOnPowerTime);
    }

    @Override
    public void setRfreqRequestedOffPowerTime(Date inRfreqLatestOnPowerTime) {
        super.setRfreqRequestedOffPowerTime(inRfreqLatestOnPowerTime);
    }

    @Override
    public void setRfreqTempLimitMaxC(Double inRfreqTempLimitMaxC) {
        super.setRfreqTempLimitMaxC(inRfreqTempLimitMaxC);
    }

    @Override
    public void setRfreqTempLimitMinC(Double inRfreqTempLimitMinC) {
        super.setRfreqTempLimitMinC(inRfreqTempLimitMinC);
    }

    @Override
    public void setRfreqTempShowFahrenheit(Boolean inRfreqTempShowFahrenheit) {
        super.setRfreqTempShowFahrenheit(inRfreqTempShowFahrenheit);
    }

    @Override
    public void setRfreqTimeMonitor1(Date inRfreqTimeMonitor1) {
        super.setRfreqTimeMonitor1(inRfreqTimeMonitor1);
    }

    @Override
    public void setRfreqTimeMonitor2(Date inRfreqTimeMonitor2) {
        super.setRfreqTimeMonitor2(inRfreqTimeMonitor2);
    }

    @Override
    public void setRfreqTimeMonitor3(Date inRfreqTimeMonitor3) {
        super.setRfreqTimeMonitor3(inRfreqTimeMonitor3);
    }

    @Override
    public void setRfreqTimeMonitor4(Date inRfreqTimeMonitor4) {
        super.setRfreqTimeMonitor4(inRfreqTimeMonitor4);
    }

    @Override
    public void setRfreqMinutesBeforeUnplugWarning(Integer inRfreqMinutesBeforeUnplugWarning) {
        super.setRfreqMinutesBeforeUnplugWarning(inRfreqMinutesBeforeUnplugWarning);
    }

    @Override
    public void setRfreqExtendedTimeMonitors(Boolean inIsRfreqExtendedTimeMonitors) {
        super.setRfreqExtendedTimeMonitors(inIsRfreqExtendedTimeMonitors);
    }

    public boolean equals(Object inObject) {
        if (this == inObject) {
            return true;
        }
        if (inObject == null || this.getClass() != inObject.getClass()) {
            return false;
        }
        ReeferRqmntsDO that = (ReeferRqmntsDO)inObject;
        if (this.getRfreqCO2Pct() != null ? !this.getRfreqCO2Pct().equals(that.getRfreqCO2Pct()) : that.getRfreqCO2Pct() != null) {
            return false;
        }
        if (this.getRfreqHumidityPct() != null ? !this.getRfreqHumidityPct().equals(that.getRfreqHumidityPct()) : that.getRfreqHumidityPct() != null) {
            return false;
        }
        if (this.getRfreqLatestOnPowerTime() != null ? !this.getRfreqLatestOnPowerTime().equals(that.getRfreqLatestOnPowerTime()) : that.getRfreqLatestOnPowerTime() != null) {
            return false;
        }
        if (this.getRfreqRequestedOffPowerTime() != null ? !this.getRfreqRequestedOffPowerTime().equals(that.getRfreqRequestedOffPowerTime()) : that.getRfreqRequestedOffPowerTime() != null) {
            return false;
        }
        if (this.getRfreqO2Pct() != null ? !this.getRfreqO2Pct().equals(that.getRfreqO2Pct()) : that.getRfreqO2Pct() != null) {
            return false;
        }
        if (this.getRfreqTempLimitMaxC() != null ? !this.getRfreqTempLimitMaxC().equals(that.getRfreqTempLimitMaxC()) : that.getRfreqTempLimitMaxC() != null) {
            return false;
        }
        if (this.getRfreqTempLimitMinC() != null ? !this.getRfreqTempLimitMinC().equals(that.getRfreqTempLimitMinC()) : that.getRfreqTempLimitMinC() != null) {
            return false;
        }
        if (this.getRfreqTempRequiredC() != null ? !this.getRfreqTempRequiredC().equals(that.getRfreqTempRequiredC()) : that.getRfreqTempRequiredC() != null) {
            return false;
        }
        if (this.getRfreqTempShowFahrenheit() != null ? !this.getRfreqTempShowFahrenheit().equals(that.getRfreqTempShowFahrenheit()) : that.getRfreqTempShowFahrenheit() != null) {
            return false;
        }
        if (this.getRfreqTimeMonitor1() != null ? !this.getRfreqTimeMonitor1().equals(that.getRfreqTimeMonitor1()) : that.getRfreqTimeMonitor1() != null) {
            return false;
        }
        if (this.getRfreqTimeMonitor2() != null ? !this.getRfreqTimeMonitor2().equals(that.getRfreqTimeMonitor2()) : that.getRfreqTimeMonitor2() != null) {
            return false;
        }
        if (this.getRfreqTimeMonitor3() != null ? !this.getRfreqTimeMonitor3().equals(that.getRfreqTimeMonitor3()) : that.getRfreqTimeMonitor3() != null) {
            return false;
        }
        if (this.getRfreqTimeMonitor4() != null ? !this.getRfreqTimeMonitor4().equals(that.getRfreqTimeMonitor4()) : that.getRfreqTimeMonitor4() != null) {
            return false;
        }
        if (this.getRfreqVentRequired() != null ? !this.getRfreqVentRequired().equals(that.getRfreqVentRequired()) : that.getRfreqVentRequired() != null) {
            return false;
        }
        return !(this.getRfreqVentUnit() != null ? !this.getRfreqVentUnit().equals((Object)that.getRfreqVentUnit()) : that.getRfreqVentUnit() != null);
    }

    public int hashCode() {
        int result = this.getRfreqTempRequiredC() != null ? this.getRfreqTempRequiredC().hashCode() : 0;
        result = 29 * result + (this.getRfreqTempLimitMaxC() != null ? this.getRfreqTempLimitMaxC().hashCode() : 0);
        result = 29 * result + (this.getRfreqTempLimitMinC() != null ? this.getRfreqTempLimitMinC().hashCode() : 0);
        result = 29 * result + (this.getRfreqTempShowFahrenheit() != null ? this.getRfreqTempShowFahrenheit().hashCode() : 0);
        result = 29 * result + (this.getRfreqVentRequired() != null ? this.getRfreqVentRequired().hashCode() : 0);
        result = 29 * result + (this.getRfreqVentUnit() != null ? this.getRfreqVentUnit().hashCode() : 0);
        result = 29 * result + (this.getRfreqHumidityPct() != null ? this.getRfreqHumidityPct().hashCode() : 0);
        result = 29 * result + (this.getRfreqO2Pct() != null ? this.getRfreqO2Pct().hashCode() : 0);
        result = 29 * result + (this.getRfreqCO2Pct() != null ? this.getRfreqCO2Pct().hashCode() : 0);
        result = 29 * result + (this.getRfreqLatestOnPowerTime() != null ? this.getRfreqLatestOnPowerTime().hashCode() : 0);
        result = 29 * result + (this.getRfreqRequestedOffPowerTime() != null ? this.getRfreqRequestedOffPowerTime().hashCode() : 0);
        result = 29 * result + (this.getRfreqTimeMonitor1() != null ? this.getRfreqTimeMonitor1().hashCode() : 0);
        result = 29 * result + (this.getRfreqTimeMonitor2() != null ? this.getRfreqTimeMonitor2().hashCode() : 0);
        result = 29 * result + (this.getRfreqTimeMonitor3() != null ? this.getRfreqTimeMonitor3().hashCode() : 0);
        result = 29 * result + (this.getRfreqTimeMonitor4() != null ? this.getRfreqTimeMonitor4().hashCode() : 0);
        return result;
    }

    public void updateReeferMonitors(Date inTimeMonitor1, Date inTimeMonitor2, Date inTimeMonitor3, Date inTimeMonitor4, Integer inOffPowerTimeLimit) {
        this.setRfreqTimeMonitor1(inTimeMonitor1);
        this.setRfreqTimeMonitor2(inTimeMonitor2);
        this.setRfreqTimeMonitor3(inTimeMonitor3);
        this.setRfreqTimeMonitor4(inTimeMonitor4);
        this.setRfreqMinutesBeforeUnplugWarning(inOffPowerTimeLimit);
    }

    public Date[] getReeferMonitorTimes() {
        Date rfreqTimeMonitor4;
        Date rfreqTimeMonitor3;
        Date rfreqTimeMonitor2;
        Date[] monitorTimes = new Date[4];
        int i = 0;
        TimeZone tz = ContextHelper.getThreadUserContext().getTimeZone();
        Calendar cal = Calendar.getInstance(tz);
        Date rfreqTimeMonitor1 = this.getRfreqTimeMonitor1();
        if (rfreqTimeMonitor1 != null) {
            cal.setTime(rfreqTimeMonitor1);
            monitorTimes[i] = ArgoUtils.getCurrentDateWithHoursAndTime((int)cal.get(11), (int)cal.get(12));
            ++i;
        }
        if ((rfreqTimeMonitor2 = this.getRfreqTimeMonitor2()) != null) {
            cal.setTime(rfreqTimeMonitor2);
            monitorTimes[i] = ArgoUtils.getCurrentDateWithHoursAndTime((int)cal.get(11), (int)cal.get(12));
            ++i;
        }
        if ((rfreqTimeMonitor3 = this.getRfreqTimeMonitor3()) != null) {
            cal.setTime(rfreqTimeMonitor3);
            monitorTimes[i] = ArgoUtils.getCurrentDateWithHoursAndTime((int)cal.get(11), (int)cal.get(12));
            ++i;
        }
        if ((rfreqTimeMonitor4 = this.getRfreqTimeMonitor4()) != null) {
            cal.setTime(rfreqTimeMonitor4);
            monitorTimes[i] = ArgoUtils.getCurrentDateWithHoursAndTime((int)cal.get(11), (int)cal.get(12));
            ++i;
        }
        return monitorTimes;
    }
}
