package com.zpmc.ztos.infra.base.business.model;

import com.zpmc.ztos.infra.base.business.interfaces.IBusinessContextStats;
import com.zpmc.ztos.infra.base.common.utils.StringUtil;

public class BusinessContextStats implements IBusinessContextStats {
    private String _actionInfo;
    private String _clientIdentifier;
    private String _executionContextId;
    private String _moduleInfo;
    private int _actionValueLimit = 32;
    private int _clientIdValueLimit = 64;
    private int _ecidValueLlimt = 64;
    private int _moduleValueLimit = 48;

    public String getActionInfo() {
        return this._actionInfo;
    }

    public String getClientIdentifier() {
        return this._clientIdentifier;
    }

    public String getExecutionContextId() {
        return this._executionContextId;
    }

    public String getModuleInfo() {
        return this._moduleInfo;
    }

    @Override
    public void setClientIdentifier(String inClientIdentifier) {
        this._clientIdentifier = StringUtil.truncate(inClientIdentifier, this._clientIdValueLimit);
    }

    @Override
    public void setExecutionContext(String inExecutionContextId) {
        this._executionContextId = StringUtil.truncate(inExecutionContextId, this._ecidValueLlimt);
    }

    @Override
    public void setModuleInfo(String inModuleInfo) {
        this._moduleInfo = StringUtil.truncate(inModuleInfo, this._moduleValueLimit);
    }

    @Override
    public void setActionInfo(String inActionInfo) {
        this._actionInfo = StringUtil.truncate(inActionInfo, this._actionValueLimit);
    }
}
