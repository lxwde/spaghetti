package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.business.model.*;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.model.BizRequest;
import com.zpmc.ztos.infra.base.common.model.BizResponse;
import com.zpmc.ztos.infra.base.common.scopes.Complex;
import com.zpmc.ztos.infra.base.common.scopes.Facility;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public interface IVesselVisitFinder {
    public static final String BEAN_ID = "vesselVisitFinder";
    public static final String CONVENTION_VISITREF = "VISITREF";
    public static final String CONVENTION_VES_NAME = "VESNAME";
    public static final String CONVENTION_CUSTOMS_VISITREF = "CUSTOMSVISITREF";
    public static final String CONVENTION_CALL_SIGN = "CALLSIGN";

    public CarrierVisit findVesselVisitForInboundStow(Complex var1, String var2, String var3, String var4, RoutingPoint var5, LineOperator var6) throws BizViolation;

    public CarrierVisit findVesselVisitForReleaseEdi(Complex var1, Facility var2, String var3, String var4, String var5, boolean var6, LineOperator var7, boolean var8) throws BizViolation;

    public IGuardian findVesselByEncoding(String var1, String var2);

    public CarrierVisit findVesselVisitForReleaseEdi(Complex var1, String var2) throws BizViolation;

    public void addVesselVisitPartnerLine(CarrierVisit var1, LineOperator var2);

    public CarrierVisit createTestVesselVisit(Facility var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9, String var10, String var11, Date var12);

    public void getDomainQueryForSharedVvLineList(BizRequest var1, BizResponse var2);

    public boolean isDummyImplementation();

    public void getDomainQueryForActiveVesselVisits(BizRequest var1, BizResponse var2);

    public List getSharedLineList(CarrierVisit var1);

    public CarrierVisit findVesselVisitForOutboundStow(Complex var1, String var2, String var3, String var4) throws BizViolation;

    public CarrierVisit findOutboundVesselVisit(Complex var1, String var2, String var3, String var4, LineOperator var5, RoutingPoint var6) throws BizViolation;

    public boolean isLineAllowed(CarrierVisit var1, ScopedBizUnit var2);

    public CarrierVisit findVesselVisitByLineAndFinalPod(RoutingPoint var1, ScopedBizUnit var2, boolean var3, boolean var4);

    public CarrierVisit findVesselVisitByLineFinalPodAndService(RoutingPoint var1, ScopedBizUnit var2, CarrierService var3, boolean var4, boolean var5);

    public CarrierVisit createPreliminaryVesselVisit(Complex var1, String var2, String var3, String var4, RoutingPoint var5, Facility var6, String var7, boolean var8) throws BizViolation;

    public CarrierVisit findUniqueVesselVisitForEdi(Complex var1, Facility var2, String var3, String var4, String var5, LineOperator var6, boolean var7, boolean var8) throws BizViolation;

    public CarrierVisit findUniqueVesselVisitForEdi(Complex var1, Facility var2, String var3, String var4, String var5, LineOperator var6, boolean var7, boolean var8, boolean var9) throws BizViolation;

    public CarrierVisit findUniqueVesselVisitForEdi(Complex var1, Facility var2, String var3, String var4, String var5, LineOperator var6, boolean var7, boolean var8, boolean var9, boolean var10) throws BizViolation;

    public CarrierVisit findUniqueVesselVisitAllPhasesForEdi(Complex var1, Facility var2, String var3, String var4, String var5, LineOperator var6, boolean var7) throws BizViolation;

    public CarrierVisit createPreliminaryVesselVisitDetails(Facility var1, String var2, String var3, String var4, String var5) throws BizViolation;

    public List<CarrierVisit> findVesselVisitByIdAndVoyage(Complex var1, Facility var2, String var3, String var4, String var5, LineOperator var6, boolean var7) throws BizViolation;

    public int loadListDiscrepancyExistsforVesselVisit(Serializable var1, String var2);

 //   public void createLineLoadListFromTransaction(LoadlistTransactionDocument.LoadlistTransaction var1, CarrierVisit var2) throws BizViolation;

    public void createLineLoadListWithNullValues(String var1, CarrierVisit var2) throws BizViolation;

    public void deleteLoadListUnitByEDI(Serializable var1);
}
