package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.business.model.BusinessContextStats;
import com.zpmc.ztos.infra.base.common.model.UserContext;

public interface IBusinessContextStatsFacade {
    public static final String END_TO_END_CLIENTID_INDEX = "END_TO_END_CLIENTID_INDEX";
    public static final String END_TO_END_ECID_INDEX = "END_TO_END_ECID_INDEX";
    public static final String END_TO_END_MODULE_INDEX = "END_TO_END_MODULE_INDEX";
    public static final String END_TO_END_ACTION_INDEX = "END_TO_END_ACTION_INDEX";
    public static final String END_TO_END_STATE_INDEX_MAX = "END_TO_END_STATE_INDEX_MAX";
    public static final String SET_END_TO_END = "setEndToEndMetrics";
    public static final String GET_END_TO_END = "getEndToEndMetrics";
    public static final String JVM_PARAMETER = "com.navis.performance.CollectBusinessStats";

    public void injectEndToEndMetrics();

    public void clearBusinessContextStats();

    public boolean isAvailable();

    public boolean setAvailable(boolean var1);

    public boolean injectStatsIntoConnection(BusinessContextStats var1);

    public String getDefaultClientIdentifier(UserContext var1);

    public BusinessContextStats getBusinessContextMetrics();

    public boolean applyBusinessContextMetrics(BusinessContextStats var1);

    public String[] getEndToEndConnectionMetrics();
}
