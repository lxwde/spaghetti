package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.business.enums.argo.DataSourceEnum;
import com.zpmc.ztos.infra.base.business.enums.framework.LifeCycleStateEnum;
import com.zpmc.ztos.infra.base.business.model.CarrierItinerary;
import com.zpmc.ztos.infra.base.business.model.CarrierService;
import com.zpmc.ztos.infra.base.business.model.CarrierVisit;
import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 访问详细信息
 *
 * @author yejun
 */
@Data
public abstract class VisitDetailsDO extends DatabaseEntity
implements Serializable {
    private Long cvdGkey;
    private Date cvdETA;
    private Date cvdETD;
    private Date cvdTimeDischargeComplete;
    private Date cvdTimeFirstAvailability;
    private Date cvdInboundFirstFreeDay;
    private String cvdInCallNumber;
    private String cvdOutCallNumber;
    private DataSourceEnum cvdDataSource;
    private Date cvdTimePeriodicStart;
    private Date cvdTimePeriodicEnd;
    private Long cvdDurationPeriodicRecur;
    private LifeCycleStateEnum cvdLifeCycleState;
    private CarrierVisit cvdCv;
    private CarrierService cvdService;
    private CarrierItinerary cvdItinerary;

    public Serializable getPrimaryKey() {
        return this.getCvdGkey();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        Long id = this.getCvdGkey();
        if (id == null) {
            return false;
        }
        if (!(other instanceof VisitDetailsDO)) {
            return false;
        }
        VisitDetailsDO that = (VisitDetailsDO)other;
        return ((Object)id).equals(that.getCvdGkey());
    }

    public int hashCode() {
        Long id = this.getCvdGkey();
        return id == null ? System.identityHashCode(this) : ((Object)id).hashCode();
    }

    public Long getCvdGkey() {
        return this.cvdGkey;
    }

    protected void setCvdGkey(Long cvdGkey) {
        this.cvdGkey = cvdGkey;
    }

    public Date getCvdETA() {
        return this.cvdETA;
    }

    protected void setCvdETA(Date cvdETA) {
        this.cvdETA = cvdETA;
    }

    public Date getCvdETD() {
        return this.cvdETD;
    }

    protected void setCvdETD(Date cvdETD) {
        this.cvdETD = cvdETD;
    }

    public Date getCvdTimeDischargeComplete() {
        return this.cvdTimeDischargeComplete;
    }

    protected void setCvdTimeDischargeComplete(Date cvdTimeDischargeComplete) {
        this.cvdTimeDischargeComplete = cvdTimeDischargeComplete;
    }

    public Date getCvdTimeFirstAvailability() {
        return this.cvdTimeFirstAvailability;
    }

    protected void setCvdTimeFirstAvailability(Date cvdTimeFirstAvailability) {
        this.cvdTimeFirstAvailability = cvdTimeFirstAvailability;
    }

    public Date getCvdInboundFirstFreeDay() {
        return this.cvdInboundFirstFreeDay;
    }

    protected void setCvdInboundFirstFreeDay(Date cvdInboundFirstFreeDay) {
        this.cvdInboundFirstFreeDay = cvdInboundFirstFreeDay;
    }

    public String getCvdInCallNumber() {
        return this.cvdInCallNumber;
    }

    protected void setCvdInCallNumber(String cvdInCallNumber) {
        this.cvdInCallNumber = cvdInCallNumber;
    }

    public String getCvdOutCallNumber() {
        return this.cvdOutCallNumber;
    }

    protected void setCvdOutCallNumber(String cvdOutCallNumber) {
        this.cvdOutCallNumber = cvdOutCallNumber;
    }

    public DataSourceEnum getCvdDataSource() {
        return this.cvdDataSource;
    }

    protected void setCvdDataSource(DataSourceEnum cvdDataSource) {
        this.cvdDataSource = cvdDataSource;
    }

    public Date getCvdTimePeriodicStart() {
        return this.cvdTimePeriodicStart;
    }

    protected void setCvdTimePeriodicStart(Date cvdTimePeriodicStart) {
        this.cvdTimePeriodicStart = cvdTimePeriodicStart;
    }

    public Date getCvdTimePeriodicEnd() {
        return this.cvdTimePeriodicEnd;
    }

    protected void setCvdTimePeriodicEnd(Date cvdTimePeriodicEnd) {
        this.cvdTimePeriodicEnd = cvdTimePeriodicEnd;
    }

    public Long getCvdDurationPeriodicRecur() {
        return this.cvdDurationPeriodicRecur;
    }

    protected void setCvdDurationPeriodicRecur(Long cvdDurationPeriodicRecur) {
        this.cvdDurationPeriodicRecur = cvdDurationPeriodicRecur;
    }

    public LifeCycleStateEnum getCvdLifeCycleState() {
        return this.cvdLifeCycleState;
    }

    public void setCvdLifeCycleState(LifeCycleStateEnum cvdLifeCycleState) {
        this.cvdLifeCycleState = cvdLifeCycleState;
    }

    public CarrierVisit getCvdCv() {
        return this.cvdCv;
    }

    public void setCvdCv(CarrierVisit cvdCv) {
        this.cvdCv = cvdCv;
    }

    public CarrierService getCvdService() {
        return this.cvdService;
    }

    protected void setCvdService(CarrierService cvdService) {
        this.cvdService = cvdService;
    }

    public CarrierItinerary getCvdItinerary() {
        return this.cvdItinerary;
    }

    protected void setCvdItinerary(CarrierItinerary cvdItinerary) {
        this.cvdItinerary = cvdItinerary;
    }

}
