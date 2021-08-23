package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.common.events.ProblemSolverEventListener;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.model.UserContext;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

public interface IProblemSolver {

    public IEventManager getEventManager();

    public String getSolveStrategyName();

    public ISolveStrategy getSolveStrategy();

    public boolean isSolveStrategyEnabled();

    public String getDataProviderName();

    public IDataProvider getDataProvider();

    public boolean isDataProviderEnabled();

    public String getConfigProviderName();

    @Nullable
    public IConfigurationProvider getConfigurationProvider() throws BizViolation;

    public boolean isConfigProviderEnabled();

    public String getSolveStatusHandlerName();

    @Nullable
    public ISolveStatusHandler getSolveStatusHandler();

    public boolean isSolveStatusHandlerEnabled();

    public String getProblemTypeId();

    public String getProblemTypeSolution();

    public UserContext getUserContext();

    public IMessageCollector getMessageCollector();

    public ISolveContext getSolveContext();

    public void addEventListener(ProblemSolverEventListener var1);

    public void removeEventListener(ProblemSolverEventListener var1);

    public void postEvent(IProblemSolverEvent var1);

    public ISolveStatusEvent solve(ISolveContext var1) throws BizViolation;
}
