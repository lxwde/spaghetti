package com.zpmc.ztos.infra.base.common.utils;

import com.zpmc.ztos.infra.base.common.callbacks.CarinaPersistenceCallback;
import com.zpmc.ztos.infra.base.common.configs.ArgoConfig;
import com.zpmc.ztos.infra.base.common.database.PersistenceTemplatePropagationRequired;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.groovy.CustomGroovyInvoker;
import com.zpmc.ztos.infra.base.common.helps.ContextHelper;
import com.zpmc.ztos.infra.base.common.model.UserContext;
import org.apache.log4j.Logger;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.util.Map;

public class ArgoGroovyUtils {
    public static final String GENERIC_WS_CUSTOM_GROOVY_TRANSFORM_METHOD = "transform";
    public static final String GENERIC_WS_CUSTOM_GROOVY_POST_INVOKE_METHOD = "postHandlerInvoke";
    public static final String GENERIC_WS_CUSTOM_GROOVY_PRE_INVOKE_METHOD = "preHandlerInvoke";
    private static final Logger LOGGER = Logger.getLogger(ArgoGroovyUtils.class);
    public static final String GENERIC_WS_CUSTOM_GROOVY = "ArgoWebserviceGroovyInterceptorImpl.groovy";
    public static final String GENERIC_WS_CUSTOM_GROOVY_CLASS = "ArgoWebserviceGroovyInterceptorImpl";
    public static final String GENERIC_WS_GROOVY_PRE_INVOKE = "PreInvoke";
    public static final String GENERIC_WS_GROOVY_POST_INVOKE = "PostInvoke";
    public static final String GENERIC_WS_GROOVY_PRE_INVOKE_TX = "PreInvokeInTx";
    public static final String GENERIC_WS_GROOVY_POST_INVOKE_TX = "PostInvokeInTx";
    public static final String RDT_CUSTOM_GROOVY = "RdtCustomGroovyImpl.groovy";
    public static final String RDT_CUSTOM_GROOVY_CLASS = "RdtCustomGroovyImpl";
    public static final String RDT_CUSTOM_GROOVY_METHOD = "validateChanges";
    public static final String WS_RESPONSE_KEY = "ARGO_WS_RESPONSE";
    public static final String WS_MC = "ARGO_WS_MESSAGE_COLLECTOR";
    public static final String WS_HANDLER = "ARGO_WS_HANDLER";
    public static final String WS_UPDATE_MSG = "ARGO_WS_UPDATE_MESSAGE";
    public static final String WS_USER_CONTEXT = "ARGO_WS_USER_CONTEXT";
    public static final String WS_ROOT_ELEMENT = "ARGO_WS_ROOT";
    public static final String WS_RESULT_HOLDER = "ARGO_WS_RESULT_HOLDER";
    public static final String IFT_TRANSFER_GROOVY_FILE_NAME = "IftGroovyImpl.groovy";
    public static final String IFT_TRANSFER_GROOVY_CLASS = "IftGroovyImpl";
    public static final String PRE_IFT_METHOD_NAME = "preTransferValidate";
    public static final String POST_IFT_METHOD_NAME = "postTransferPerformActions";
    public static final String RECORD_GUARANTEE_FILE_NAME = "RecordGuarantee.groovy";
    public static final String RECORD_GUARANTEE_GROOVY_CLASS = "RecordGuarantee";
    public static final String PAID_GUARANTEE_METHOD_NAME = "recordPaidGuarantee";
    public static final String TBD_UNIT_VALIDATE_MERGE_GROOVY_CLASS = "TbdUnitValidationMerge";
    public static final String TBD_UNIT_ALLOWED_TO_LOAD_VESSEL_CARRY_UNDERWAY = "performValidationToLoadAtCarryUnderwayStage";
    public static final String CUSTOM_CALCULATE_STORAGE_DAYS = "CustomCalculateStorageDays";
    public static final String CUSTOM_CALCULATE_STORAGE_START_DATE_METHOD = "calculateStorageStartDate";
    public static final String CUSTOM_CALCULATE_STORAGE_END_DATE_METHOD = "calculateStorageEndDate";
    public static final String E_FIELD_CHANGES_NAME = "EFieldChanges";
    public static final String GROUP_GROOVY_CLASS = "GroupGroovyImpl";
    public static final String GROUP__METHOD_NAME = "execute";
    public static final String UNIT_OFF_HIRE_ON_HIRE_VALIDATION_GROOVY_CLASS = "OffhireOnhireValidation";
    public static final String CHECK_UNIT_DELIVERY_AVAILABILITY_GROOVY_CLASS = "CheckUnitDeliveryAvailability";
    public static final String JOB_LIST_VALIDATOR_GROOVY_CLASS = "JobListValidator";
    public static final String CUSTOM_INVENTORY_CORRECTION_VALIDATOR_GROOVY_CLASS = "CustomInventoryCorrectionValidator";
    public static final String DISPLAY_CONFIG_FIELD_IN_MOBILE_YINV_GROOVY_CLASS = "DisplayConfigFieldInMobileYardInv";
    public static final String DISPLAY_CONFIG_FIELD_IN_MOBILE_RINV_GROOVY_CLASS = "DisplayConfigFieldInMobileRailInv";
    public static final String CUSTOM_UNPLANNED_LOAD_VALIDATOR_GROOVY_CLASS = "CustomUnplannedLoadValidator";
    public static final String CUSTOM_LOAD_EMPTY_SLOT_VALIDATOR_GROOVY_METHOD = "performValidationToLoadToUnplannedSlots";
    public static final String CUSTOM_LOAD_OCCUPIED_SLOT_VALIDATOR_GROOVY_METHOD = "performValidationToLoadToOccupiedSlots";
    public static final String CUSTOM_LOAD_MISMATCH_PLAN_VALIDATOR_GROOVY_METHOD = "performValidationToLoadOnMismatchPlans";
    public static final String INVOICE_GENERATION_POST_INTERCEPTOR = "InvoiceGenerationPostInterceptor";
    public static final String INVOICE_GENERATION_PRE_INTERCEPTOR = "InvoiceGenerationPreInterceptor";
    public static final String ORPHAN_CONTAINER_VALIDATOR_GROOVY_CLASS = "OrphanContainerValidator";
    public static final String INVOICE_DISCOUNT_INTERCEPTOR = "InvoiceDiscountInterceptor";
    public static final String OPTIMAL_TZ_SLOT_FINDER_CLASS = "OptimalTZSlotFinder";
    public static final String OPTIMAL_TZ_SLOT_FINDER_METHOD = "populateOptimalTZSlots";
    public static final String UPDATE_WI_ESTIMATED_MOVE_TIME_GROOVY_CLASS = "UpdateWIEstimatedMoveTime";
    public static final String UPDATE_WI_ESTIMATED_MOVE_TIME_NEW_GROOVY_METHOD = "updateMoveTime";
    public static final String UPDATE_WI_ESTIMATED_MOVE_TIME_GROOVY_METHOD = "updateWIEstimatedMoveTime";
    public static final String UPDATE_WI_ESTIMATED_MOVE_TIME_POW_INPUT_GROOVY_METHOD = "getApplicablePowsForMoveTimeUpdate";
    public static final String UPDATE_WI_ESTIMATED_MOVE_TIME_REPEAT_INTERVAL_GROOVY_METHOD = "getRepeatIntervalInMillis";

    private ArgoGroovyUtils() {
    }

    @Nullable
    public static String getGroovyScript(String inString) {
        return ArgoGroovyUtils.getGroovyScript(UserContextUtils.getSystemUserContext(), inString);
    }

    @Nullable
    public static String getGroovyScript(UserContext inUserContext, final String inString) {
        final String[] groovyStr = new String[]{null};
        PersistenceTemplatePropagationRequired pt = new PersistenceTemplatePropagationRequired(inUserContext);
        pt.invoke(new CarinaPersistenceCallback(){

            public void doInTransaction() {
                if (!ArgoConfig.DISABLE_GROOVY_EXECUTION.isOn(ContextHelper.getThreadUserContext())) {
   //                 groovyStr[0] = DigitalAsset.findGroovyClassCode(inString);
                } else {
                    groovyStr[0] = null;
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug((Object)("N4 Groovy, Non-deployed Groovy or Groovy Support is Disabled:" + inString));
                    }
                }
            }
        });
        return groovyStr[0];
    }

    public static String invokeWebservicesPreInvokeInterceptor(String inXmlStr, String inGenericWsCustomGroovyClass) throws BizViolation {
        if (inGenericWsCustomGroovyClass == null || inGenericWsCustomGroovyClass.isEmpty()) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug((Object)"Groovy code is of size 0 length, ignoring it");
            }
            return inXmlStr;
        }
        String groovyScript = ArgoGroovyUtils.getGroovyScript(inGenericWsCustomGroovyClass);
        if (groovyScript == null) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug((Object)"no groovy code found for doing custom validation");
            }
            return inXmlStr;
        }
        return (String) CustomGroovyInvoker.invokeCustomGroovy(groovyScript, new Class[]{String.class}, new Object[]{inXmlStr}, inGenericWsCustomGroovyClass, GENERIC_WS_CUSTOM_GROOVY_TRANSFORM_METHOD);
    }

    public static void invokeWebservicesPostInvokeInterceptor(Map inOutResponse, String inGenericWsCustomGroovyClass, String inGenericWsCustomGroovyPostInvokeMethod) throws BizViolation {
        if (inGenericWsCustomGroovyClass == null || inGenericWsCustomGroovyClass.isEmpty()) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug((Object)"Groovy code is of size 0 length, ignoring it");
            }
            return;
        }
        String groovyScript = ArgoGroovyUtils.getGroovyScript(inGenericWsCustomGroovyClass);
        if (groovyScript == null) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug((Object)("no groovy code found for doing custom validation: " + inGenericWsCustomGroovyClass));
            }
        } else {
            CustomGroovyInvoker.invokeCustomGroovy(groovyScript, new Class[]{Map.class}, new Object[]{inOutResponse}, inGenericWsCustomGroovyClass, inGenericWsCustomGroovyPostInvokeMethod);
        }
    }

    public static void validateChangesUsingCustomCode(Map inMap) throws BizViolation {
        String groovyScript = ArgoGroovyUtils.getGroovyScript(RDT_CUSTOM_GROOVY_CLASS);
        if (groovyScript == null) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug((Object)"no groovy code found for doing custom validation");
            }
        } else {
            UserContext userContext = null;
            try {
       //         userContext = N4Mobile.getInstance().getRequestContext().getUserContext();
            }
            catch (Exception inException) {
                userContext = null;
            }
            if (userContext != null) {
                CustomGroovyInvoker.invokeCustomGroovy(groovyScript, new Class[]{Map.class}, new Object[]{inMap}, RDT_CUSTOM_GROOVY_CLASS, RDT_CUSTOM_GROOVY_METHOD, userContext);
            } else {
                CustomGroovyInvoker.invokeCustomGroovy(groovyScript, new Class[]{Map.class}, new Object[]{inMap}, RDT_CUSTOM_GROOVY_CLASS, RDT_CUSTOM_GROOVY_METHOD);
            }
        }
    }

}
