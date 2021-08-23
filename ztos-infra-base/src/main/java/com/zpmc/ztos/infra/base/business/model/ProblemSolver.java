package com.zpmc.ztos.infra.base.business.model;

import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.common.contexts.PortalApplicationContext;
import com.zpmc.ztos.infra.base.common.events.EventManager;
import com.zpmc.ztos.infra.base.common.events.ProblemSolverEventListener;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.messages.MessageCollectorFactory;
import com.zpmc.ztos.infra.base.common.model.AbstractConfigurationProvider;
import com.zpmc.ztos.infra.base.common.model.CodeTimer;
import com.zpmc.ztos.infra.base.common.model.ScopeCoordinates;
import com.zpmc.ztos.infra.base.common.model.UserContext;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class ProblemSolver implements IProblemSolver {

    private static final String NONE = "NONE";
    private IEventManager _eventManager;
    private IDataProvider _dataProvider;
    private IExtension _dataProviderExtension;
    private ISolveStrategy _solveStrategy;
    private IExtension _solveStrategyExtension;
    private IConfigurationProvider _configProvider = DEFAULT_PROVIDER;
    private IExtension _configProviderExtension;
    private ISolveStatusHandler _solveStatusHandler;
    private IExtension _solveStatusHandlerExtension;
    private String _problemTypeId;
    private String _problemTypeSolution;
    private UserContext _userContext;
    private ISolveContext _solveContext;
    private IMessageCollector _messageCollector;
    private static final AbstractConfigurationProvider DEFAULT_PROVIDER = new AbstractConfigurationProvider(){};
    private static final Logger LOGGER = Logger.getLogger(ProblemSolver.class);

    public ProblemSolver(UserContext inUserContext, String inProblemTypeId) {
        this._eventManager = new EventManager();
        this._userContext = inUserContext;
        this._problemTypeId = inProblemTypeId;
    }

    @Override
    public IEventManager getEventManager() {
        return this._eventManager;
    }

    @Override
    public String getSolveStrategyName() {
        if (this._solveStrategyExtension != null) {
            return this._solveStrategyExtension.getExtensionName();
        }
        return NONE;
    }

    public void setSolveStrategyExtension(IExtension inSolveStrategyExtension) throws BizViolation {
        this._solveStrategyExtension = inSolveStrategyExtension;
        IExtensionClassProvider provider = (IExtensionClassProvider) PortalApplicationContext.getBean((String)"extensionClassProvider");
        Object solveStrategy = provider.getExtensionClassInstance(inSolveStrategyExtension);
        if (!(solveStrategy instanceof ISolveStrategy)) {
            throw BizViolation.create((IPropertyKey) IOptimizationPropertyKeys.ERROR_SOLVE_STRATEGY_IS_NOT_ISOLVESTRATEGY_TYPE, null);
        }
        this._solveStrategy = (ISolveStrategy)solveStrategy;
        this._solveStrategy.setProblemSolver(this);
    }

    public IExtension getSolveStrategyExtension() {
        return this._solveStrategyExtension;
    }

    @Override
    public ISolveStrategy getSolveStrategy() {
        return this._solveStrategy;
    }

    @Override
    public boolean isSolveStrategyEnabled() {
        return this._solveStrategyExtension != null ? this._solveStrategyExtension.isEnabled() : false;
    }

    @Override
    public String getDataProviderName() {
        if (this._dataProviderExtension != null) {
            return this._dataProviderExtension.getExtensionName();
        }
        return NONE;
    }

    public void setDataProviderExtension(IExtension inDataProviderExtension) throws BizViolation {
        this._dataProviderExtension = inDataProviderExtension;
        IExtensionClassProvider provider = (IExtensionClassProvider)PortalApplicationContext.getBean((String)"extensionClassProvider");
        Object dataProvider = provider.getExtensionClassInstance(inDataProviderExtension);
        if (!(dataProvider instanceof IDataProvider)) {
            throw BizViolation.create((IPropertyKey) IOptimizationPropertyKeys.ERROR_DATA_PROVIDER_IS_NOT_IDATAPROVIDER_TYPE, null);
        }
        this._dataProvider = (IDataProvider)dataProvider;
        this._dataProvider.setProblemSolver(this);
    }

    public IExtension getDataProviderExtension() {
        return this._dataProviderExtension;
    }

    @Override
    public IDataProvider getDataProvider() {
        return this._dataProvider;
    }

    @Override
    public boolean isDataProviderEnabled() {
        return this._dataProviderExtension != null ? this._dataProviderExtension.isEnabled() : false;
    }

    @Override
    public String getConfigProviderName() {
        if (this._configProviderExtension != null) {
            return this._configProviderExtension.getExtensionName();
        }
        return NONE;
    }

    public void setConfigProviderExtension(IExtension inConfigProviderExtension) throws BizViolation {
        this._configProviderExtension = inConfigProviderExtension;
        if (inConfigProviderExtension != null) {
            IExtensionClassProvider provider = (IExtensionClassProvider)PortalApplicationContext.getBean((String)"extensionClassProvider");
            Object configurationProvider = provider.getExtensionClassInstance(inConfigProviderExtension);
            if (configurationProvider instanceof IConfigurationProvider) {
                this._configProvider = (IConfigurationProvider)configurationProvider;
  //              this._configProvider.setProblemSolver(this);
            } else {
                throw BizViolation.create((IPropertyKey) IOptimizationPropertyKeys.ERROR_CONFIGURATION_PROVIDER_IS_NOT_IPROBLEMCONFIGURATIONPROVIDER_TYPE, null);
            }
        }
    }

    public IExtension getConfigProviderExtension() {
        return this._configProviderExtension;
    }

    @Override
    public IConfigurationProvider getConfigurationProvider() {
        return this._configProvider;
    }

    @Override
    public boolean isConfigProviderEnabled() {
        return this._configProviderExtension != null ? this._configProviderExtension.isEnabled() : true;
    }

    @Override
    public String getSolveStatusHandlerName() {
        if (this._solveStatusHandlerExtension != null) {
            return this._solveStatusHandlerExtension.getExtensionName();
        }
        return NONE;
    }

    public void setSolveStatusHandlerExtension(IExtension inSolveStatusHandlerExtension) throws BizViolation {
        this._solveStatusHandlerExtension = inSolveStatusHandlerExtension;
        if (this._solveStatusHandlerExtension != null) {
            IExtensionClassProvider provider = (IExtensionClassProvider)PortalApplicationContext.getBean((String)"extensionClassProvider");
            Object solveStatusHandlerObject = provider.getExtensionClassInstance(inSolveStatusHandlerExtension);
            if (solveStatusHandlerObject instanceof ISolveStatusHandler) {
                this._solveStatusHandler = (ISolveStatusHandler)solveStatusHandlerObject;
  //              this._solveStatusHandler.setProblemSolver(this);
            } else {
                throw BizViolation.create((IPropertyKey) IOptimizationPropertyKeys.ERROR_SOLVE_STATUS_HANDLER_IS_NOT_ISOLVESTATUSHANDLER_TYPE, null);
            }
        }
    }

    public IExtension getSolveStatusHandlerExtension() {
        return this._solveStatusHandlerExtension;
    }

    @Override
    public ISolveStatusHandler getSolveStatusHandler() {
        return this._solveStatusHandler;
    }

    @Override
    public boolean isSolveStatusHandlerEnabled() {
        return this._solveStatusHandlerExtension != null ? this._solveStatusHandlerExtension.isEnabled() : true;
    }

    @Override
    public String getProblemTypeId() {
        return this._problemTypeId;
    }

    public void setProblemTypeId(String inProblemTypeId) {
        this._problemTypeId = inProblemTypeId;
    }

    @Override
    public String getProblemTypeSolution() {
        return this._problemTypeSolution;
    }

    public void setProblemTypeSolution(String inProblemTypeSolution) {
        this._problemTypeSolution = inProblemTypeSolution;
    }

    @Override
    public ISolveContext getSolveContext() {
        return this._solveContext;
    }

    public void setSolveContext(ISolveContext inSolveContext) {
        this._solveContext = inSolveContext;
        this.setOverrideConfigProvider(inSolveContext);
    }

    protected void setOverrideConfigProvider(ISolveContext inSolveContext) {
        Object override;
//        if (inSolveContext instanceof IConfigProviderOverride && (override = ((IConfigProviderOverride)inSolveContext).getConfigProviderOverride()) != null) {
//            this._configProvider = override;
//            this._configProvider.setProblemSolver(this);
//        }
    }

    @Override
    public void addEventListener(ProblemSolverEventListener inListener) {
        this._eventManager.addEventListener(inListener);
    }

    @Override
    public void removeEventListener(ProblemSolverEventListener inListener) {
        this._eventManager.removeEventListener(inListener);
    }

    @Override
    public void postEvent(IProblemSolverEvent inEvent) {
        this._eventManager.postEvent(inEvent);
    }

    @Override
    public UserContext getUserContext() {
        return this._userContext;
    }

    @Override
    public IMessageCollector getMessageCollector() {
        return this._messageCollector;
    }

    private void createMessageCollector() {
        this._messageCollector = MessageCollectorFactory.createMessageCollector();
    }

    @Override
    public ISolveStatusEvent solve(ISolveContext inSolveContext) throws BizViolation {
        this.setSolveContext(inSolveContext);
        this.createMessageCollector();
        return this.solveInternal();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private ISolveStatusEvent solveInternal() throws BizViolation {
        ScopeCoordinates scopeCoordinates;
        String userCoords = "";
        if (this.getUserContext() != null && StringUtils.isEmpty((String)(userCoords = (scopeCoordinates = this.getUserContext().getScopeCoordinate()).getBusinessCoords()))) {
            userCoords = scopeCoordinates.toString();
        }
        String timerMessageHeading = this.getProblemTypeId() + "@" + userCoords;
        CodeTimer timer = new CodeTimer(LOGGER, Level.DEBUG);
        timer.report(timerMessageHeading + ": started");
        try {
            this.initializeDataProvider();
            timer.report(timerMessageHeading + ": DataProvider [" + this.getDataProviderName() + "] initialized");
            ISolveStatusEvent solveStatusEvent = this.executeInternal();
            timer.report(timerMessageHeading + ": SolveStrategy [" + this.getSolveStrategyName() + "] executed");
            String statusHandlerName = this.getSolveStatusHandlerName();
            if (NONE.equals(statusHandlerName)) {
                timer.report(timerMessageHeading + ": No SolveStatusHandler configured");
            } else {
                this.executeStatusHandler(solveStatusEvent);
                timer.report(timerMessageHeading + ": SolveStatusHandler [" + statusHandlerName + "] executed");
            }
            ISolveStatusEvent iSolveStatusEvent = solveStatusEvent;
            return iSolveStatusEvent;
        }
        finally {
            timer.report(timerMessageHeading + ": finished");
        }
    }

    private void executeStatusHandler(ISolveStatusEvent inSolveStatusEvent) throws BizViolation {
//        SolveStatusHandlerExtensionContext context = SolveStatusHandlerExtensionContext.createExtensionContext(this._userContext, this.getSolveStatusHandlerExtension(), this, inSolveStatusEvent);
//        ISolveStatusHandlerExtensionHandler handler = (ISolveStatusHandlerExtensionHandler)PortalApplicationContext.getBean((String)"solveStatusHandlerExtensionHandler");
//        OptimizationExtensionResponse response = (OptimizationExtensionResponse)handler.invoke((IExtensionContext)context);
//        ExtensionResponseStatus responseStatus = response.getStatus();
//        if (response.getMessageCollector() != null && response.getMessageCollector().hasError()) {
//            LOGGER.debug((Object)("Solve Status Handler extension executed (handleSolveStatus) error with response status: " + (Object)responseStatus));
//            throw CarinaUtils.convertToBizViolation((IMessageCollector)response.getMessageCollector());
//        }
//        LOGGER.debug((Object)("Solve Status Handler extension executed (handleSolveStatus) with response status: " + (Object)responseStatus));
    }

    private ISolveStatusEvent executeInternal() {
//        SolveStatusEvent solveStatusEvent;
//        ISolveCompletionEvent solveCompletionEvent;
//        this.postEvent(new IBeforeSolveEvent(){});
//        try {
//            solveCompletionEvent = this.executeStrategy();
//        }
//        catch (BizViolation bv) {
//            if (!this.getMessageCollector().containsMessage(bv.getMessageKey())) {
//                this.getMessageCollector().appendMessage((IUserMessage)bv);
//            }
//            solveCompletionEvent = null;
//        }
//        if (this.getMessageCollector().hasError()) {
//            solveStatusEvent = new SolveStatusEvent(this.getMessageCollector(), ISolveStatusEvent.SolveStatusCode.FAILURE);
//        } else {
//            this.postEvent(solveCompletionEvent);
//            solveStatusEvent = new SolveStatusEvent(this.getMessageCollector(), ISolveStatusEvent.SolveStatusCode.SUCCESS, solveCompletionEvent);
//        }
//        this.postEvent(new IAfterSolveEvent(){});
//        return solveStatusEvent;
        return null;
    }

    protected void initializeDataProvider() throws BizViolation {
//        OptimizationExtensionContext context = OptimizationExtensionContext.createExtensionContext(this._userContext, this._dataProviderExtension, this);
//        IDataProviderExtensionHandler handler = (IDataProviderExtensionHandler)PortalApplicationContext.getBean((String)"dataProviderExtensionHandler");
//        OptimizationExtensionResponse response = (OptimizationExtensionResponse)handler.invoke((IExtensionContext)context);
//        ExtensionResponseStatus responseStatus = response.getStatus();
//        if (response.getMessageCollector() != null && response.getMessageCollector().hasError()) {
//            LOGGER.debug((Object)("Data Provider extension initialize error with response status: " + (Object)responseStatus));
//            throw CarinaUtils.convertToBizViolation((IMessageCollector)response.getMessageCollector());
//        }
//        LOGGER.debug((Object)("Data Provider extension executed (initialize) with response status: " + (Object)responseStatus));
    }

    protected ISolveCompletionEvent executeStrategy() throws BizViolation {
//        OptimizationExtensionContext context = OptimizationExtensionContext.createExtensionContext(this._userContext, this._solveStrategyExtension, this);
//        ISolveStrategyExtensionHandler handler = (ISolveStrategyExtensionHandler)PortalApplicationContext.getBean((String)"solveStrategyExtensionHandler");
//        SolveStrategyExtensionResponse response = (SolveStrategyExtensionResponse)handler.invoke((IExtensionContext)context);
//        ExtensionResponseStatus responseStatus = response.getStatus();
//        if (response.getMessageCollector() != null && response.getMessageCollector().hasError()) {
//            LOGGER.debug((Object)("Solve Strategy extension execute error with response status: " + (Object)responseStatus));
//            throw CarinaUtils.convertToBizViolation((IMessageCollector)response.getMessageCollector());
//        }
//        LOGGER.debug((Object)("Solve Strategy extension executed (executeStrategy) with response status: " + (Object)responseStatus));
//        return response.getSolveCompletionEvent();
        return null;
    }
}
