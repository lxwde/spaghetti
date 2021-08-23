package com.zpmc.ztos.infra.base.business.model;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.business.dataobject.ProblemSolutionDO;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.common.contexts.PortalApplicationContext;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.common.events.AuditEvent;
import com.zpmc.ztos.infra.base.common.exceptions.BizFailure;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.model.*;
import com.zpmc.ztos.infra.base.common.type.FuzzyBoolean;
import com.zpmc.ztos.infra.base.common.type.OptimizationExtensionTypes;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.io.Serializable;
import java.util.List;

public class ProblemSolution extends ProblemSolutionDO {

    public ProblemSolution() {
        this.setSolutionIsActive(Boolean.FALSE);
        this.setSolutionSystemProvided(Boolean.FALSE);
    }

    public ProblemSolution(@NotNull String inSolutionId, @NotNull String inSolutionDescription, @NotNull ProblemType inProblemType, @Nullable Extension inSolutionDataProvider, @Nullable Extension inSolutionSolveStrategy, @Nullable Extension inSolutionConfiguration, @Nullable Extension inSolutionStatusHandler) {
        this.setSolutionId(inSolutionId);
        this.setSolutionDescription(inSolutionDescription);
        this.setSolutionProblemType(inProblemType);
        this.setSolutionDataProvider(inSolutionDataProvider);
        this.setSolutionSolveStrategy(inSolutionSolveStrategy);
        this.setSolutionConfiguration(inSolutionConfiguration);
        this.setSolutionStatusHandler(inSolutionStatusHandler);
    }

    public com.zpmc.ztos.infra.base.common.events.AuditEvent vetAuditEvent(AuditEvent inAuditEvent) {
        return inAuditEvent;
    }

    public Long getGkey() {
        return this.getSolutionGkey();
    }

    public String getHumanReadableKey() {
        return new ProblemType().getHumanReadableKey();
    }

    public static ProblemSolution hydrate(Serializable inPrimaryKey) {
        return (ProblemSolution) HibernateApi.getInstance().get(ProblemSolution.class, inPrimaryKey);
    }

    public void configureProblemSolver(@NotNull IProblemSolver inProblemSolver) throws BizViolation {
        Extension probSolveStatusHandlerExtension;
        ProblemSolver problemSolver = (ProblemSolver)inProblemSolver;
        Extension probSolutionDataProviderExtension = this.getSolutionDataProvider();
        problemSolver.setDataProviderExtension((IExtension)probSolutionDataProviderExtension);
        Extension probSolutionSolveStrategyExtension = this.getSolutionSolveStrategy();
        problemSolver.setSolveStrategyExtension((IExtension)probSolutionSolveStrategyExtension);
        Extension probSolutionConfigurationExtension = this.getSolutionConfiguration();
        if (probSolutionConfigurationExtension != null) {
            problemSolver.setConfigProviderExtension((IExtension)probSolutionConfigurationExtension);
        }
        if ((probSolveStatusHandlerExtension = this.getSolutionStatusHandler()) != null) {
            problemSolver.setSolveStatusHandlerExtension((IExtension)probSolveStatusHandlerExtension);
        }
        problemSolver.setProblemTypeId(this.getSolutionProblemType().getProbtypeId());
        problemSolver.setProblemTypeSolution(this.getSolutionProblemType().getProbtypeSolution());
    }

    public Object getSolutionScopeLevelName() {
        IEntityScoper scoper = (IEntityScoper)Roastery.getBean((String)"entityScoper");
        return scoper.getScopeEnum(this.getScopeLevel());
    }

    @Nullable
    public String getSolutionScopeName() {
        if (this.getSolutionGkey() == null) {
            return null;
        }
        IEntityScoper scoper = (IEntityScoper)Roastery.getBean((String)"entityScoper");
        IScopeEnum scopeEnum = scoper.getScopeEnum(this.getScopeLevel());
        ScopeCoordinates sc = scoper.getScopeCoordinates(scopeEnum, (Serializable)((Object)this.getSolutionScopeGkey()));
        return scoper.getScopeNodeEntity(sc).getPathName();
    }

    public BizViolation validateChanges(FieldChanges inChanges) {
        boolean isBadScope;
        BizViolation violation = super.validateChanges(inChanges);
        if (!this.isUniqueInClass(IOptimizationField.SOLUTION_ID)) {
            return BizViolation.createFieldViolation((IPropertyKey) IOptimizationPropertyKeys.NON_UNIQUE_PROBLEM_SOLUTION_ID, null, null, (Object)("[" + this.getSolutionId() + "]"));
        }
        Long scopeLevel = this.getSolutionScopeLevel();
        if (ScopeCoordinates.GLOBAL_LEVEL.equals(scopeLevel)) {
            isBadScope = this.getSolutionScopeGkey() != null;
        } else {
            boolean bl = isBadScope = this.getSolutionScopeGkey() == null || scopeLevel == null;
        }
        if (isBadScope) {
            violation = BizViolation.createFieldViolation((IPropertyKey)IFrameworkPropertyKeys.CRUD__FIELD_REQUIRED, (BizViolation)violation, (IMetafieldId) IOptimizationField.SOLUTION_SCOPE_GKEY);
        } else if (!ScopeCoordinates.GLOBAL_LEVEL.equals(scopeLevel)) {
            boolean isIllegalScope = false;
            ApplicationModuleSettings appSettings = (ApplicationModuleSettings) PortalApplicationContext.getBean((String)"appModuleSettings");
            if (appSettings.isGloballyScoped() == FuzzyBoolean.TRUE) {
                isIllegalScope = true;
            } else {
                IEntityScoper scoper = (IEntityScoper)Roastery.getBean((String)"entityScoper");
                if (scopeLevel < ScopeCoordinates.GLOBAL_LEVEL || scopeLevel > scoper.getMostSpecificSupportedScopeEnum().getScopeLevel()) {
                    isIllegalScope = true;
                }
            }
            if (isIllegalScope) {
                violation = BizViolation.createFieldViolation((IPropertyKey)IFrameworkPropertyKeys.FAILURE__INVALID_SCOPE_LEVEL, (BizViolation)violation, (IMetafieldId) IOptimizationField.SOLUTION_SCOPE_LEVEL, (Object)scopeLevel);
            }
        }
        violation = this.validateUniqueKeys(violation);
        if (!this.getSolutionSystemProvided().booleanValue() && this.isOverridingSystemSeededProblemSolution()) {
            violation = BizViolation.create((IPropertyKey) IOptimizationPropertyKeys.CANNOT_OVERRIDE_SYSTEM_CREATED_PS, (BizViolation)violation, (Object)new Object[]{this.getSolutionProblemType().getProbtypeId()});
        }
        return violation;
    }

    public void preProcessDelete(FieldChanges inFieldChanges) {
        super.preProcessDelete(inFieldChanges);
        if (this.getSolutionSystemProvided().booleanValue() && !this.isOverridingSystemSeededProblemSolution()) {
            throw BizFailure.create((IPropertyKey) IOptimizationPropertyKeys.CANNOT_DELETE_SYSTEM_CREATED_PS, null, null, null);
        }
    }

    public int getScopeLevel() {
        return this.getSolutionScopeLevel().intValue();
    }

    public Serializable getScopeKey() {
        return this.getSolutionScopeGkey();
    }

    @NotNull
    public IMetafieldId getScopeKeyFieldId() {
        return IOptimizationField.SOLUTION_SCOPE_GKEY;
    }

    @NotNull
    public IMetafieldId getScopeLevelFieldId() {
        return IOptimizationField.SOLUTION_SCOPE_LEVEL;
    }

    private boolean isOverridingSystemSeededProblemSolution() {
        IProblemSolutionFinder problemSolutionFinder = (IProblemSolutionFinder)Roastery.getBean((String)"problemSolutionFinder");
        List<ProblemSolution> solutionList = problemSolutionFinder.findProblemSolutions(this.getSolutionProblemType().getGkey(), this.getScopeLevel());
        for (ProblemSolution problemSolution : solutionList) {
            if (problemSolution.getGkey().equals(this.getGkey()) || !problemSolution.getSolutionSystemProvided().booleanValue()) continue;
            return true;
        }
        return false;
    }

    private void inactivateOtherProblemSolutions() {
        IProblemSolutionFinder problemSolutionFinder = (IProblemSolutionFinder)Roastery.getBean((String)"problemSolutionFinder");
        List<ProblemSolution> solutionList = problemSolutionFinder.findProblemSolutions(this.getSolutionProblemType().getGkey(), this.getScopeLevel());
        for (ProblemSolution problemSolution : solutionList) {
            if (problemSolution.getGkey().equals(this.getGkey())) continue;
            problemSolution.setSolutionIsActive(false);
            HibernateApi.getInstance().saveOrUpdate((Object)problemSolution);
            HibernateApi.getInstance().flush();
        }
    }

    @Nullable
    public IValueHolder getSolutionPredicateVao() {
        return this.getSolutionPredicate() != null ? this.getSolutionPredicate().getPredicateVao() : null;
    }

    public void updateFilterPredicate(IValueHolder inPredicate) {
        HibernateApi hibernateApi = Roastery.getHibernateApi();
        SavedPredicate existingPredicate = this.getSolutionPredicate();
        if (existingPredicate != null) {
            this.setSolutionPredicate(null);
            hibernateApi.delete((Object)existingPredicate);
        }
        if (inPredicate != null) {
            SavedPredicate newFilterPredicate = new SavedPredicate(inPredicate);
            hibernateApi.save((Object)newFilterPredicate);
            this.setSolutionPredicate(newFilterPredicate);
        }
    }

    public Extension getExtension(@NotNull IExtensionTypeId inExtensionTypeId) {
        if (OptimizationExtensionTypes.OPT_SOLVE_STRATEGY.getTypeId().equals(inExtensionTypeId.getTypeId())) {
            return this.getSolutionSolveStrategy();
        }
        if (OptimizationExtensionTypes.OPT_DATA_PROVIDER.getTypeId().equals(inExtensionTypeId.getTypeId())) {
            this.getSolutionDataProvider();
        } else if (OptimizationExtensionTypes.OPT_CONFIGURATION_PROVIDER.getTypeId().equals(inExtensionTypeId.getTypeId())) {
            this.getSolutionConfiguration();
        } else if (OptimizationExtensionTypes.OPT_RESULT_HANDLER.getTypeId().equals(inExtensionTypeId.getTypeId())) {
            this.getSolutionStatusHandler();
        }
        return null;
    }

    public void setExtension(@NotNull Extension inExtension, @NotNull IExtensionTypeId inExtensionTypeId) {
        if (!ProblemSolution.allowsNullExtension(inExtensionTypeId) && inExtension == null) {
            return;
        }
        if (OptimizationExtensionTypes.OPT_SOLVE_STRATEGY.getTypeId().equals(inExtensionTypeId.getTypeId())) {
            this.setSolutionSolveStrategy(inExtension);
        } else if (OptimizationExtensionTypes.OPT_DATA_PROVIDER.getTypeId().equals(inExtensionTypeId.getTypeId())) {
            this.setSolutionDataProvider(inExtension);
        } else if (OptimizationExtensionTypes.OPT_CONFIGURATION_PROVIDER.getTypeId().equals(inExtensionTypeId.getTypeId())) {
            this.setSolutionConfiguration(inExtension);
        } else if (OptimizationExtensionTypes.OPT_RESULT_HANDLER.getTypeId().equals(inExtensionTypeId.getTypeId())) {
            this.setSolutionStatusHandler(inExtension);
        }
    }

    private static boolean allowsNullExtension(@NotNull IExtensionTypeId inExtensionTypeId) {
        return OptimizationExtensionTypes.OPT_CONFIGURATION_PROVIDER.getTypeId().equals(inExtensionTypeId.getTypeId()) || OptimizationExtensionTypes.OPT_RESULT_HANDLER.getTypeId().equals(inExtensionTypeId.getTypeId());
    }
}
