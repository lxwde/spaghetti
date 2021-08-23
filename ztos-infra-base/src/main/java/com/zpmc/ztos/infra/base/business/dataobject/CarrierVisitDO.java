package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.business.enums.argo.CarrierVisitPhaseEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.LocTypeEnum;
import com.zpmc.ztos.infra.base.business.model.ScopedBizUnit;
import com.zpmc.ztos.infra.base.business.model.VisitDetails;
import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;
import com.zpmc.ztos.infra.base.common.scopes.Complex;
import com.zpmc.ztos.infra.base.common.scopes.Facility;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 运送工具访问对象
 *
 * @author yejun
 */
@Data
public abstract class CarrierVisitDO extends DatabaseEntity {

    private Long cvGkey;
    private String cvId;
    private String cvCustomsId;
    private LocTypeEnum cvCarrierMode;
    private Long cvVisitNbr;
    private CarrierVisitPhaseEnum cvVisitPhase;
    private Date cvATA;
    private Date cvATD;
    private Boolean cvSendOnBoardUnitUpdates;
    private Boolean cvSendCraneWorkListUpdates;
    private Date cvCreated;
    private String cvCreator;
    private Date cvChanged;
    private String cvChanger;
    private ScopedBizUnit cvOperator;
    private Complex cvComplex;
    private Facility cvFacility;
    private Facility cvNextFacility;
    private VisitDetails cvCvd;

    public Serializable getPrimaryKey() {
        return this.getCvGkey();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        Long id = this.getCvGkey();
        if (id == null) {
            return false;
        }
        if (!(other instanceof CarrierVisitDO)) {
            return false;
        }
        CarrierVisitDO that = (CarrierVisitDO)other;
        return ((Object)id).equals(that.getCvGkey());
    }

    public int hashCode() {
        Long id = this.getCvGkey();
        return id == null ? System.identityHashCode(this) : ((Object)id).hashCode();
    }

    public Long getCvGkey() {
        return this.cvGkey;
    }

    protected void setCvGkey(Long cvGkey) {
        this.cvGkey = cvGkey;
    }

    public String getCvId() {
        return this.cvId;
    }

    protected void setCvId(String cvId) {
        this.cvId = cvId;
    }

    public String getCvCustomsId() {
        return this.cvCustomsId;
    }

    protected void setCvCustomsId(String cvCustomsId) {
        this.cvCustomsId = cvCustomsId;
    }

    public LocTypeEnum getCvCarrierMode() {
        return this.cvCarrierMode;
    }

    protected void setCvCarrierMode(LocTypeEnum cvCarrierMode) {
        this.cvCarrierMode = cvCarrierMode;
    }

    public Long getCvVisitNbr() {
        return this.cvVisitNbr;
    }

    protected void setCvVisitNbr(Long cvVisitNbr) {
        this.cvVisitNbr = cvVisitNbr;
    }

    public CarrierVisitPhaseEnum getCvVisitPhase() {
        return this.cvVisitPhase;
    }

    protected void setCvVisitPhase(CarrierVisitPhaseEnum cvVisitPhase) {
        this.cvVisitPhase = cvVisitPhase;
    }

    public Date getCvATA() {
        return this.cvATA;
    }

    protected void setCvATA(Date cvATA) {
        this.cvATA = cvATA;
    }

    public Date getCvATD() {
        return this.cvATD;
    }

    protected void setCvATD(Date cvATD) {
        this.cvATD = cvATD;
    }

    public Boolean getCvSendOnBoardUnitUpdates() {
        return this.cvSendOnBoardUnitUpdates;
    }

    protected void setCvSendOnBoardUnitUpdates(Boolean cvSendOnBoardUnitUpdates) {
        this.cvSendOnBoardUnitUpdates = cvSendOnBoardUnitUpdates;
    }

    public Boolean getCvSendCraneWorkListUpdates() {
        return this.cvSendCraneWorkListUpdates;
    }

    protected void setCvSendCraneWorkListUpdates(Boolean cvSendCraneWorkListUpdates) {
        this.cvSendCraneWorkListUpdates = cvSendCraneWorkListUpdates;
    }

    public Date getCvCreated() {
        return this.cvCreated;
    }

    protected void setCvCreated(Date cvCreated) {
        this.cvCreated = cvCreated;
    }

    public String getCvCreator() {
        return this.cvCreator;
    }

    protected void setCvCreator(String cvCreator) {
        this.cvCreator = cvCreator;
    }

    public Date getCvChanged() {
        return this.cvChanged;
    }

    protected void setCvChanged(Date cvChanged) {
        this.cvChanged = cvChanged;
    }

    public String getCvChanger() {
        return this.cvChanger;
    }

    protected void setCvChanger(String cvChanger) {
        this.cvChanger = cvChanger;
    }

    public ScopedBizUnit getCvOperator() {
        return this.cvOperator;
    }

    protected void setCvOperator(ScopedBizUnit cvOperator) {
        this.cvOperator = cvOperator;
    }

    public Complex getCvComplex() {
        return this.cvComplex;
    }

    protected void setCvComplex(Complex cvComplex) {
        this.cvComplex = cvComplex;
    }

    public Facility getCvFacility() {
        return this.cvFacility;
    }

    protected void setCvFacility(Facility cvFacility) {
        this.cvFacility = cvFacility;
    }

    public Facility getCvNextFacility() {
        return this.cvNextFacility;
    }

    protected void setCvNextFacility(Facility cvNextFacility) {
        this.cvNextFacility = cvNextFacility;
    }

    public VisitDetails getCvCvd() {
        return this.cvCvd;
    }

    public void setCvCvd(VisitDetails cvCvd) {
        this.cvCvd = cvCvd;
    }
}
