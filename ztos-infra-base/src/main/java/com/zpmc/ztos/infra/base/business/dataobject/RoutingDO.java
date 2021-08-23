package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.business.model.*;
import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;

import java.io.Serializable;

public abstract class RoutingDO extends DatabaseEntity implements Serializable {
    private String rtgDescription;
    private String rtgExportClearanceNbr;
    private String rtgReturnToLocation;
    private String rtgPinNbr;
    private String rtgBondedDestination;
    private RoutingPoint rtgOPL;
    private RoutingPoint rtgPOL;
    private CarrierVisit rtgDeclaredCv;
    private CarrierService rtgCarrierService;
    private RoutingPoint rtgPOD1;
    private RoutingPoint rtgPOD2;
    private RoutingPoint rtgOPT1;
    private RoutingPoint rtgOPT2;
    private RoutingPoint rtgOPT3;
    private Group rtgGroup;
    private ScopedBizUnit rtgTruckingCompany;
    private ScopedBizUnit rtgBondTruckingCompany;
    private RoutingPoint rtgProjectedPOD;

    public String getRtgDescription() {
        return this.rtgDescription;
    }

    public void setRtgDescription(String rtgDescription) {
        this.rtgDescription = rtgDescription;
    }

    public String getRtgExportClearanceNbr() {
        return this.rtgExportClearanceNbr;
    }

    public void setRtgExportClearanceNbr(String rtgExportClearanceNbr) {
        this.rtgExportClearanceNbr = rtgExportClearanceNbr;
    }

    public String getRtgReturnToLocation() {
        return this.rtgReturnToLocation;
    }

    public void setRtgReturnToLocation(String rtgReturnToLocation) {
        this.rtgReturnToLocation = rtgReturnToLocation;
    }

    public String getRtgPinNbr() {
        return this.rtgPinNbr;
    }

    public void setRtgPinNbr(String rtgPinNbr) {
        this.rtgPinNbr = rtgPinNbr;
    }

    public String getRtgBondedDestination() {
        return this.rtgBondedDestination;
    }

    public void setRtgBondedDestination(String rtgBondedDestination) {
        this.rtgBondedDestination = rtgBondedDestination;
    }

    public RoutingPoint getRtgOPL() {
        return this.rtgOPL;
    }

    public void setRtgOPL(RoutingPoint rtgOPL) {
        this.rtgOPL = rtgOPL;
    }

    public RoutingPoint getRtgPOL() {
        return this.rtgPOL;
    }

    public void setRtgPOL(RoutingPoint rtgPOL) {
        this.rtgPOL = rtgPOL;
    }

    public CarrierVisit getRtgDeclaredCv() {
        return this.rtgDeclaredCv;
    }

    public void setRtgDeclaredCv(CarrierVisit rtgDeclaredCv) {
        this.rtgDeclaredCv = rtgDeclaredCv;
    }

    public CarrierService getRtgCarrierService() {
        return this.rtgCarrierService;
    }

    public void setRtgCarrierService(CarrierService rtgCarrierService) {
        this.rtgCarrierService = rtgCarrierService;
    }

    public RoutingPoint getRtgPOD1() {
        return this.rtgPOD1;
    }

    public void setRtgPOD1(RoutingPoint rtgPOD1) {
        this.rtgPOD1 = rtgPOD1;
    }

    public RoutingPoint getRtgPOD2() {
        return this.rtgPOD2;
    }

    public void setRtgPOD2(RoutingPoint rtgPOD2) {
        this.rtgPOD2 = rtgPOD2;
    }

    public RoutingPoint getRtgOPT1() {
        return this.rtgOPT1;
    }

    public void setRtgOPT1(RoutingPoint rtgOPT1) {
        this.rtgOPT1 = rtgOPT1;
    }

    public RoutingPoint getRtgOPT2() {
        return this.rtgOPT2;
    }

    public void setRtgOPT2(RoutingPoint rtgOPT2) {
        this.rtgOPT2 = rtgOPT2;
    }

    public RoutingPoint getRtgOPT3() {
        return this.rtgOPT3;
    }

    public void setRtgOPT3(RoutingPoint rtgOPT3) {
        this.rtgOPT3 = rtgOPT3;
    }

    public Group getRtgGroup() {
        return this.rtgGroup;
    }

    public void setRtgGroup(Group rtgGroup) {
        this.rtgGroup = rtgGroup;
    }

    public ScopedBizUnit getRtgTruckingCompany() {
        return this.rtgTruckingCompany;
    }

    public void setRtgTruckingCompany(ScopedBizUnit rtgTruckingCompany) {
        this.rtgTruckingCompany = rtgTruckingCompany;
    }

    public ScopedBizUnit getRtgBondTruckingCompany() {
        return this.rtgBondTruckingCompany;
    }

    public void setRtgBondTruckingCompany(ScopedBizUnit rtgBondTruckingCompany) {
        this.rtgBondTruckingCompany = rtgBondTruckingCompany;
    }

    public RoutingPoint getRtgProjectedPOD() {
        return this.rtgProjectedPOD;
    }

    public void setRtgProjectedPOD(RoutingPoint rtgProjectedPOD) {
        this.rtgProjectedPOD = rtgProjectedPOD;
    }
}
