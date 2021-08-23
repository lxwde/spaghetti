package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.business.enums.argo.CarrierVisitPhaseEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.LocTypeEnum;
import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;
import com.zpmc.ztos.infra.base.common.scopes.Complex;
import com.zpmc.ztos.infra.base.common.scopes.Facility;

import java.io.Serializable;
import java.util.Date;

public abstract class ArchiveCarrierVisitDO extends DatabaseEntity implements Serializable {

    private Long arCvGkey;
    private Date arCvArchiveDate;
    private Long arCvOriginalGkey;
    private String arCvId;
    private String arCvCustomsId;
    private LocTypeEnum arCvCarrierMode;
    private Long arCvVisitNbr;
    private CarrierVisitPhaseEnum arCvVisitPhase;
    private String arCvOperatorId;
    private Long arCvOperatorGkey;
    private Date arCvATA;
    private Date arCvATD;
    private Date arCvCreated;
    private String arCvCreator;
    private Date arCvChanged;
    private String arCvChanger;
    private Complex arCvComplex;
    private Facility arCvFacility;
    private Facility arCvNextFacility;

    public Serializable getPrimaryKey() {
        return this.getArCvGkey();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        Long id = this.getArCvGkey();
        if (id == null) {
            return false;
        }
        if (!(other instanceof ArchiveCarrierVisitDO)) {
            return false;
        }
        ArchiveCarrierVisitDO that = (ArchiveCarrierVisitDO)other;
        return ((Object)id).equals(that.getArCvGkey());
    }

    public int hashCode() {
        Long id = this.getArCvGkey();
        return id == null ? System.identityHashCode(this) : ((Object)id).hashCode();
    }

    public Long getArCvGkey() {
        return this.arCvGkey;
    }

    public void setArCvGkey(Long arCvGkey) {
        this.arCvGkey = arCvGkey;
    }

    public Date getArCvArchiveDate() {
        return this.arCvArchiveDate;
    }

    public void setArCvArchiveDate(Date arCvArchiveDate) {
        this.arCvArchiveDate = arCvArchiveDate;
    }

    public Long getArCvOriginalGkey() {
        return this.arCvOriginalGkey;
    }

    public void setArCvOriginalGkey(Long arCvOriginalGkey) {
        this.arCvOriginalGkey = arCvOriginalGkey;
    }

    public String getArCvId() {
        return this.arCvId;
    }

    public void setArCvId(String arCvId) {
        this.arCvId = arCvId;
    }

    public String getArCvCustomsId() {
        return this.arCvCustomsId;
    }

    public void setArCvCustomsId(String arCvCustomsId) {
        this.arCvCustomsId = arCvCustomsId;
    }

    public LocTypeEnum getArCvCarrierMode() {
        return this.arCvCarrierMode;
    }

    public void setArCvCarrierMode(LocTypeEnum arCvCarrierMode) {
        this.arCvCarrierMode = arCvCarrierMode;
    }

    public Long getArCvVisitNbr() {
        return this.arCvVisitNbr;
    }

    public void setArCvVisitNbr(Long arCvVisitNbr) {
        this.arCvVisitNbr = arCvVisitNbr;
    }

    public CarrierVisitPhaseEnum getArCvVisitPhase() {
        return this.arCvVisitPhase;
    }

    public void setArCvVisitPhase(CarrierVisitPhaseEnum arCvVisitPhase) {
        this.arCvVisitPhase = arCvVisitPhase;
    }

    public String getArCvOperatorId() {
        return this.arCvOperatorId;
    }

    public void setArCvOperatorId(String arCvOperatorId) {
        this.arCvOperatorId = arCvOperatorId;
    }

    public Long getArCvOperatorGkey() {
        return this.arCvOperatorGkey;
    }

    public void setArCvOperatorGkey(Long arCvOperatorGkey) {
        this.arCvOperatorGkey = arCvOperatorGkey;
    }

    public Date getArCvATA() {
        return this.arCvATA;
    }

    public void setArCvATA(Date arCvATA) {
        this.arCvATA = arCvATA;
    }

    public Date getArCvATD() {
        return this.arCvATD;
    }

    public void setArCvATD(Date arCvATD) {
        this.arCvATD = arCvATD;
    }

    public Date getArCvCreated() {
        return this.arCvCreated;
    }

    public void setArCvCreated(Date arCvCreated) {
        this.arCvCreated = arCvCreated;
    }

    public String getArCvCreator() {
        return this.arCvCreator;
    }

    public void setArCvCreator(String arCvCreator) {
        this.arCvCreator = arCvCreator;
    }

    public Date getArCvChanged() {
        return this.arCvChanged;
    }

    public void setArCvChanged(Date arCvChanged) {
        this.arCvChanged = arCvChanged;
    }

    public String getArCvChanger() {
        return this.arCvChanger;
    }

    public void setArCvChanger(String arCvChanger) {
        this.arCvChanger = arCvChanger;
    }

    public Complex getArCvComplex() {
        return this.arCvComplex;
    }

    public void setArCvComplex(Complex arCvComplex) {
        this.arCvComplex = arCvComplex;
    }

    public Facility getArCvFacility() {
        return this.arCvFacility;
    }

    public void setArCvFacility(Facility arCvFacility) {
        this.arCvFacility = arCvFacility;
    }

    public Facility getArCvNextFacility() {
        return this.arCvNextFacility;
    }

    public void setArCvNextFacility(Facility arCvNextFacility) {
        this.arCvNextFacility = arCvNextFacility;
    }
}
