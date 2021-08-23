package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.common.model.PropertyKeyFactory;

public interface IOptimizationPropertyKeys {
    public static final IPropertyKey ERROR_PROBLEM_DOES_NOT_EXIST = PropertyKeyFactory.valueOf((String)"ERROR_PROBLEM_DOES_NOT_EXIST");
    public static final IPropertyKey ERROR_PROBLEM_SOLVE_STRATEGY = PropertyKeyFactory.valueOf((String)"ERROR_PROBLEM_SOLVE_STRATEGY");
    public static final IPropertyKey ERROR_PROBLEM_DATA_PROVIDER = PropertyKeyFactory.valueOf((String)"ERROR_PROBLEM_DATA_PROVIDER");
    public static final IPropertyKey ERROR_NON_SYS_PROBLEM_ALREADY_EXISTS = PropertyKeyFactory.valueOf((String)"ERROR_NON_SYS_PROBLEM_ALREADY_EXISTS");
    public static final IPropertyKey ERROR_PROBLEM_LOAD_FAILED = PropertyKeyFactory.valueOf((String)"ERROR_PROBLEM_LOAD_FAILED");
    public static final IPropertyKey NON_UNIQUE_PROBLEM_NAME = PropertyKeyFactory.valueOf((String)"NON_UNIQUE_PROBLEM_NAME");
    public static final IPropertyKey NON_UNIQUE_PROBLEM_ID = PropertyKeyFactory.valueOf((String)"NON_UNIQUE_PROBLEM_ID");
    public static final IPropertyKey NON_UNIQUE_PROBLEM_SOLUTION = PropertyKeyFactory.valueOf((String)"NON_UNIQUE_PROBLEM_SOLUTION");
    public static final IPropertyKey NON_UNIQUE_PROBLEM_SOLUTION_ID = PropertyKeyFactory.valueOf((String)"NON_UNIQUE_PROBLEM_SOLUTION_ID");
    public static final IPropertyKey CANNOT_DELETE_PT_AS_IT_HAS_PS = PropertyKeyFactory.valueOf((String)"CANNOT_DELETE_PT_AS_IT_HAS_PS");
    public static final IPropertyKey CANNOT_DELETE_SYSTEM_CREATED_PT = PropertyKeyFactory.valueOf((String)"CANNOT_DELETE_SYSTEM_CREATED_PT");
    public static final IPropertyKey CANNOT_CREATE_UPDATE_PS_AS_IT_EXIST = PropertyKeyFactory.valueOf((String)"CANNOT_CREATE_UPDATE_PS_AS_IT_EXIST");
    public static final IPropertyKey CANNOT_UPDATE_PT_AS_IT_SYSTEM_CREATED = PropertyKeyFactory.valueOf((String)"CANNOT_UPDATE_PT_AS_IT_SYSTEM_CREATED");
    public static final IPropertyKey CANNOT_UPDATE_PS_AS_IT_SYSTEM_CREATED = PropertyKeyFactory.valueOf((String)"CANNOT_UPDATE_PS_AS_IT_SYSTEM_CREATED");
    public static final IPropertyKey CANNOT_OVERRIDE_SYSTEM_CREATED_PS = PropertyKeyFactory.valueOf((String)"CANNOT_OVERRIDE_SYSTEM_CREATED_PS");
    public static final IPropertyKey CANNOT_DELETE_SYSTEM_CREATED_PS = PropertyKeyFactory.valueOf((String)"CANNOT_DELETE_SYSTEM_CREATED_PS");
    public static final IPropertyKey CANNOT_UPLOAD_DEFAULT_PS = PropertyKeyFactory.valueOf((String)"CANNOT_UPLOAD_DEFAULT_PS");
    public static final IPropertyKey ERR_CANNOT_FIND_PROBLEM_TYPE = PropertyKeyFactory.valueOf((String)"ERR_CANNOT_FIND_PROBLEM_TYPE");
    public static final IPropertyKey ERR_DUPLICATE_PROBLEM_TYPE = PropertyKeyFactory.valueOf((String)"ERR_DUPLICATE_PROBLEM_TYPE");
    public static final IPropertyKey ERR_CANNOT_FIND_ACTIVE_PROBLEM_SOLUTION = PropertyKeyFactory.valueOf((String)"ERR_CANNOT_FIND_ACTIVE_PROBLEM_SOLUTION");
    public static final IPropertyKey ERR_CANNOT_FIND_ENTITY_FOR_THE_PROBLEM_SOLUTION = PropertyKeyFactory.valueOf((String)"ERR_CANNOT_FIND_ENTITY_FOR_THE_PROBLEM_SOLUTION");
    public static final IPropertyKey ERR_NOT_VALID_PROBLEM_SOLUTION_FOUND = PropertyKeyFactory.valueOf((String)"ERR_NOT_VALID_PROBLEM_SOLUTION_FOUND");
    public static final IPropertyKey ERROR_PROBLEM_SOLUTION_LOAD_FAILED = PropertyKeyFactory.valueOf((String)"ERROR_PROBLEM_SOLUTION_LOAD_FAILED");
    public static final IPropertyKey CANNOT_CREATE_PROBLEM_SOLUTION = PropertyKeyFactory.valueOf((String)"CANNOT_CREATE_PROBLEM_SOLUTION");
    public static final IPropertyKey CANNOT_CREATE_PROBLEM_SOLVER = PropertyKeyFactory.valueOf((String)"CANNOT_CREATE_PROBLEM_SOLVER");
    public static final IPropertyKey DO_NOT_HAVE_CONFIG_PROVIDER = PropertyKeyFactory.valueOf((String)"DO_NOT_HAVE_CONFIG_PROVIDER");
    public static final IPropertyKey SCOPE_LEVEL = PropertyKeyFactory.valueOf((String)"SCOPE_LEVEL");
    public static final IPropertyKey PROBLEM_TYPE = PropertyKeyFactory.valueOf((String)"PROBLEM_TYPE");
    public static final IPropertyKey ERR_NO_SCOPE = PropertyKeyFactory.valueOf((String)"ERR_NO_SCOPE");
    public static final IPropertyKey DIALOG_PROBLEM_SOLUTION_ERROR = PropertyKeyFactory.valueOf((String)"DIALOG_PROBLEM_SOLUTION_ERROR");
    public static final IPropertyKey ERROR_PROBLEM_SOLUTION_OUT_OF_SCOPE = PropertyKeyFactory.valueOf((String)"ERROR_PROBLEM_SOLUTION_OUT_OF_SCOPE");
    public static final IPropertyKey ERROR_DATA_PROVIDER_IS_NOT_IDATAPROVIDER_TYPE = PropertyKeyFactory.valueOf((String)"ERROR_DATA_PROVIDER_IS_NOT_IDATAPROVIDER_TYPE");
    public static final IPropertyKey ERROR_SOLVE_STRATEGY_IS_NOT_ISOLVESTRATEGY_TYPE = PropertyKeyFactory.valueOf((String)"ERROR_SOLVE_STRATEGY_IS_NOT_ISOLVESTRATEGY_TYPE");
    public static final IPropertyKey ERROR_CONFIGURATION_PROVIDER_IS_NOT_IPROBLEMCONFIGURATIONPROVIDER_TYPE = PropertyKeyFactory.valueOf((String)"ERROR_CONFIGURATION_PROVIDER_IS_NOT_IPROBLEMCONFIGURATIONPROVIDER_TYPE");
    public static final IPropertyKey ERROR_SOLVE_STATUS_HANDLER_IS_NOT_ISOLVESTATUSHANDLER_TYPE = PropertyKeyFactory.valueOf((String)"ERROR_SOLVE_STATUS_HANDLER_IS_NOT_ISOLVESTATUSHANDLER_TYPE");
    public static final IPropertyKey ERROR_PROBLEM_SOLVER_CLIENT_IS_NOT_PROVIDED_CONTEXT = PropertyKeyFactory.valueOf((String)"ERROR_PROBLEM_SOLVER_CLIENT_IS_NOT_PROVIDED_CONTEXT");
    public static final IPropertyKey ERROR_ORIGINAL_PROBLEM_TYPE_CONTEXT_IS_NOT_ASSIGNABLE_FROM_PROVIDED_CONTEXT = PropertyKeyFactory.valueOf((String)"ERROR_ORIGINAL_PROBLEM_TYPE_CONTEXT_IS_NOT_ASSIGNABLE_FROM_PROVIDED_CONTEXT");
    public static final IPropertyKey ERROR_PROVIDED_PROBLEM_TYPE_CONTEXT_CLASS_NOT_FOUND = PropertyKeyFactory.valueOf((String)"ERROR_PROVIDED_PROBLEM_TYPE_CONTEXT_CLASS_NOT_FOUND");
    public static final IPropertyKey ERROR_ORIGINAL_PROBLEM_TYPE_CONTEXT_CLASS_NOT_FOUND = PropertyKeyFactory.valueOf((String)"ERROR_ORIGINAL_PROBLEM_TYPE_CONTEXT_CLASS_NOT_FOUND");
    public static final IPropertyKey ERROR_ORIGINAL_PROBLEM_TYPE_SOLUTION_CLASS_NOT_FOUND = PropertyKeyFactory.valueOf((String)"ERROR_ORIGINAL_PROBLEM_TYPE_SOLUTION_CLASS_NOT_FOUND");
    public static final IPropertyKey ERROR_ORIGINAL_PROBLEM_TYPE_SOLUTION_IS_NOT_ASSIGNABLE_FROM_SOLVED_PROBLEM_TYPE_SOLUTION = PropertyKeyFactory.valueOf((String)"ERROR_ORIGINAL_PROBLEM_TYPE_SOLUTION_IS_NOT_ASSIGNABLE_FROM_SOLVED_PROBLEM_TYPE_SOLUTION");
    public static final IPropertyKey ERROR_SOLVE_COMPLETION_EVENT_RETURNED_FROM_STRATEGY_IS_NULL = PropertyKeyFactory.valueOf((String)"ERROR_SOLVE_COMPLETION_EVENT_RETURNED_FROM_STRATEGY_IS_NULL");
    public static final IPropertyKey ERROR_SOLVE_FAILURE_WITH_MESSAGE = PropertyKeyFactory.valueOf((String)"ERROR_SOLVE_FAILURE_WITH_MESSAGE");
    public static final IPropertyKey OPTIMIZATION_REFRESH_JOBS = PropertyKeyFactory.valueOf((String)"OPTIMIZATION_REFRESH_JOBS");
    public static final IPropertyKey ERROR_STOP_ALL_BACKGROUNDJOBS = PropertyKeyFactory.valueOf((String)"ERROR_STOP_ALL_BACKGROUNDJOBS");

}
