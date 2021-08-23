package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.business.enums.argo.CarrierVisitPhaseEnum;
import com.zpmc.ztos.infra.base.business.model.CarrierVisit;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.scopes.Facility;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.util.Date;

public interface ICarrierManager {
    public static final String BEAN_ID = "carrierManager";
    public static final String ARRIVE_CARRIER_JOB_PREFIX = "Arrive-";
    public static final String DEPART_CARRIER_JOB_PREFIX = "Depart-";
    public static final String CLOSE_CARRIER_JOB_PREFIX = "Close-";
    public static final String CANCEL_CARRIER_JOB_PREFIX = "Cancel-";
    public static final String ACTIVATE_CARRIER_JOB_PREFIX = "Activate-";
    public static final String RECALL_DEPART_PHASE_JOB_PREFIX = "RecallDepart-";
    public static final String RECALL_INBOUND_PHASE_JOB_PREFIX = "RecallInbound-";
    public static final String REROUTE_JOB_PREFIX = "Reroute-";

    @Nullable
    public String transitionCarrierAndUnitsOnboard(CarrierVisitPhaseEnum var1, CarrierVisit var2, Date var3, boolean var4) throws BizViolation;

    @Nullable
    public String transitionCarrierAndUnitsOnboard(CarrierVisitPhaseEnum var1, CarrierVisit var2, Date var3, boolean var4, CarrierVisit var5) throws BizViolation;

    @Nullable
    public String rerouteUnitsOnboard(CarrierVisit var1, Facility var2, boolean var3) throws BizViolation;

}
