package com.zpmc.ztos.infra.base.common.utils;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.business.xps.SharedParameters;
import com.zpmc.ztos.infra.base.common.helps.ContextHelper;
import org.apache.log4j.Logger;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.util.Map;

public class ArgoXpsUtils {
    public static final String RTZFZR = "RTZFZR";
    public static final double DEFAULT_FARZONE_DISTANCE = 15000.0;
    public static final String GENCHS = "GENCHS";
    public static final String GENCHS_TRUE = "1";
    public static final String GENCHS_FALSE = "0";
    public static final String RAIL_PIN_TZ = "RLPNTZ";
    public static final String RAIL_PINS = "RLPINS";
    public static final String ECPARAM = "ECPRM1";
    public static final String ASCDRT = "ASCDRT";
    public static final String QCCSCI = "QCCSCI";
    public static final String QCCSCI_TRUE = "1";
    public static final String QCCSCI_FALSE = "0";
    public static final String TRKSSQ = "TRKSSQ";
    public static final String TRKSSQ_SET_TAIL_TO_CAB = "1";
    public static final String TRKSSQ_SET_CAB_TO_TAIL = "0";
    public static final String STRLST = "STRLST";
    private static final Logger LOGGER = Logger.getLogger(ArgoXpsUtils.class);

    private ArgoXpsUtils() {
    }

    @Nullable
    public static String getSharedParameterValue(@NotNull String inParamGroupId, @NotNull String inParamId) {
        String parmNameStr = "";
        LOGGER.debug((Object)("Shared Parameter lookup for param group:" + inParamGroupId + " param id:" + inParamId));
        if (ContextHelper.getThreadYard() != null) {
            Map<String, String> paramMap = SharedParameters.getSharedParameterParmsAsMap(inParamGroupId, "", ContextHelper.getThreadYard().getYrdGkey());
            return paramMap != null ? paramMap.get(inParamId) : null;
        }
        return null;
    }

    public static double getTzDeckerFarZoneDistanceInCentimeters() {
        String farZoneDistanceString = ArgoXpsUtils.getSharedParameterValue(ECPARAM, RTZFZR);
        if (farZoneDistanceString == null) {
            return 15000.0;
        }
        try {
            double farZoneDistance = Double.parseDouble(farZoneDistanceString) * 100.0;
            return farZoneDistance;
        }
        catch (NumberFormatException e) {
            LOGGER.debug((Object)"Far zone distance was not able to be parsed, defaulting to15000.0");
            return 15000.0;
        }
    }

    public static boolean isDecoupledTruckOperation() {
        String paramValue = ArgoXpsUtils.getSharedParameterValue(ECPARAM, GENCHS);
        return "1".equals(paramValue);
    }

    public static String translateDecoupleOperationsEcParameter(boolean inIsDecoupledOperations) {
        return inIsDecoupledOperations ? "1" : "0";
    }

    public static int getOverstowLoadSeqTolerance() {
        String paramValue = ArgoXpsUtils.getSharedParameterValue(ECPARAM, STRLST);
        return paramValue != null ? Integer.parseInt(paramValue) : 0;
    }

    public static boolean isSetOnTTRearFirst() {
        String paramValue = ArgoXpsUtils.getSharedParameterValue(ECPARAM, TRKSSQ);
        return "1".equals(paramValue);
    }

    public static String translateSetOnTTSeqEcParameter(boolean inSetOnTTRearFirst) {
        return inSetOnTTRearFirst ? "1" : "0";
    }

    @Nullable
    public static Map<String, String> getEcParams() {
        String parmNameStr = "";
        if (ContextHelper.getThreadYard() != null) {
            Map<String, String> paramMap = SharedParameters.getSharedParameterParmsAsMap(ECPARAM, "", ContextHelper.getThreadYard().getYrdGkey());
            return paramMap;
        }
        return null;
    }

    public static String getEcParameterValue(@NotNull String inParameterId) {
        return ArgoXpsUtils.getSharedParameterValue(ECPARAM, inParameterId);
    }

    public static boolean isRailPinSettingOn() {
        String railPinSettingValue = ArgoXpsUtils.getEcParameterValue(RAIL_PINS);
        return "1".equals(railPinSettingValue);
    }

    public static boolean isRailPinSendtoTZSettingOn() {
        String railPinSettingValue = ArgoXpsUtils.getEcParameterValue(RAIL_PIN_TZ);
        return "1".equals(railPinSettingValue);
    }

    public static boolean isCreateCraneInstructionSettingOn() {
        String paramValue = ArgoXpsUtils.getEcParameterValue(QCCSCI);
        return "1".equals(paramValue);
    }

}
