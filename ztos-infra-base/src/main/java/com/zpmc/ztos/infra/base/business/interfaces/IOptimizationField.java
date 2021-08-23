package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.business.model.MetafieldIdFactory;

public interface IOptimizationField {

    public static final IMetafieldId JOBCONFIG_GKEY = MetafieldIdFactory.valueOf((String)"jobconfigGkey");
    public static final IMetafieldId JOBCONFIG_ID = MetafieldIdFactory.valueOf((String)"jobconfigId");
    public static final IMetafieldId JOBCONFIG_MIN_CORES = MetafieldIdFactory.valueOf((String)"jobconfigMinCores");
    public static final IMetafieldId JOBCONFIG_MIN_MEMORY = MetafieldIdFactory.valueOf((String)"jobconfigMinMemory");
    public static final IMetafieldId JOBCONFIG_REPEAT_COUNT = MetafieldIdFactory.valueOf((String)"jobconfigRepeatCount");
    public static final IMetafieldId JOBCONFIG_REPEAT_INTERVAL = MetafieldIdFactory.valueOf((String)"jobconfigRepeatInterval");
    public static final IMetafieldId JOBCONFIG_TIMEOUT = MetafieldIdFactory.valueOf((String)"jobconfigTimeout");
    public static final IMetafieldId JOBCONFIG_DESCRIPTION = MetafieldIdFactory.valueOf((String)"jobconfigDescription");
    public static final IMetafieldId PROBTYPE_GKEY = MetafieldIdFactory.valueOf((String)"probtypeGkey");
    public static final IMetafieldId PROBTYPE_ID = MetafieldIdFactory.valueOf((String)"probtypeId");
    public static final IMetafieldId PROBTYPE_SOLUTION = MetafieldIdFactory.valueOf((String)"probtypeSolution");
    public static final IMetafieldId PROBTYPE_CONTEXT = MetafieldIdFactory.valueOf((String)"probtypeContext");
    public static final IMetafieldId PROBTYPE_DESCRIPTION = MetafieldIdFactory.valueOf((String)"probtypeDescription");
    public static final IMetafieldId PROBTYPE_SYSTEM_PROVIDED = MetafieldIdFactory.valueOf((String)"probtypeSystemProvided");
    public static final IMetafieldId PROBTYPE_CREATOR = MetafieldIdFactory.valueOf((String)"probtypeCreator");
    public static final IMetafieldId PROBTYPE_CREATED = MetafieldIdFactory.valueOf((String)"probtypeCreated");
    public static final IMetafieldId PROBTYPE_CHANGED = MetafieldIdFactory.valueOf((String)"probtypeChanged");
    public static final IMetafieldId PROBTYPE_CHANGER = MetafieldIdFactory.valueOf((String)"probtypeChanger");
    public static final IMetafieldId SOLUTION_GKEY = MetafieldIdFactory.valueOf((String)"solutionGkey");
    public static final IMetafieldId SOLUTION_ID = MetafieldIdFactory.valueOf((String)"solutionId");
    public static final IMetafieldId SOLUTION_SCOPE_LEVEL = MetafieldIdFactory.valueOf((String)"solutionScopeLevel");
    public static final IMetafieldId SOLUTION_PROBLEM_TYPE = MetafieldIdFactory.valueOf((String)"solutionProblemType");
    public static final IMetafieldId SOLUTION_JOB_CONFIGURATION = MetafieldIdFactory.valueOf((String)"solutionJobConfiguration");
    public static final IMetafieldId SOLUTION_SCOPE_GKEY = MetafieldIdFactory.valueOf((String)"solutionScopeGkey");
    public static final IMetafieldId SOLUTION_IS_ACTIVE = MetafieldIdFactory.valueOf((String)"solutionIsActive");
    public static final IMetafieldId SOLUTION_SYSTEM_PROVIDED = MetafieldIdFactory.valueOf((String)"solutionSystemProvided");
    public static final IMetafieldId SOLUTION_DATA_PROVIDER = MetafieldIdFactory.valueOf((String)"solutionDataProvider");
    public static final IMetafieldId SOLUTION_SOLVE_STRATEGY = MetafieldIdFactory.valueOf((String)"solutionSolveStrategy");
    public static final IMetafieldId SOLUTION_CONFIGURATION = MetafieldIdFactory.valueOf((String)"solutionConfiguration");
    public static final IMetafieldId SOLUTION_STATUS_HANDLER = MetafieldIdFactory.valueOf((String)"solutionStatusHandler");
    public static final IMetafieldId SOLUTION_DESCRIPTION = MetafieldIdFactory.valueOf((String)"solutionDescription");
    public static final IMetafieldId SOLUTION_ENTITY = MetafieldIdFactory.valueOf((String)"solutionEntity");
    public static final IMetafieldId SOLUTION_JOB_GROUP = MetafieldIdFactory.valueOf((String)"solutionJobGroup");
    public static final IMetafieldId SOLUTION_PREDICATE = MetafieldIdFactory.valueOf((String)"solutionPredicate");
    public static final IMetafieldId SOLUTION_CREATOR = MetafieldIdFactory.valueOf((String)"solutionCreator");
    public static final IMetafieldId SOLUTION_CREATED = MetafieldIdFactory.valueOf((String)"solutionCreated");
    public static final IMetafieldId SOLUTION_CHANGED = MetafieldIdFactory.valueOf((String)"solutionChanged");
    public static final IMetafieldId SOLUTION_CHANGER = MetafieldIdFactory.valueOf((String)"solutionChanger");
}
