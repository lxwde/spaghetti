package com.zpmc.ztos.infra.base.common.model;

public class ApiMapping {
    private boolean _requiresDatabase = true;
    private boolean _readOnly;
    private String _handlerClass;
    private String _handlerMethod;

    public String getHandlerClass() {
        return this._handlerClass;
    }

    public void setHandlerClass(String inHandlerClass) {
        this._handlerClass = inHandlerClass;
    }

    public String getHandlerMethod() {
        return this._handlerMethod;
    }

    public void setHandlerMethod(String inHandlerMethod) {
        this._handlerMethod = inHandlerMethod;
    }

    public boolean isReadOnly() {
        return this._readOnly;
    }

    public void setReadOnly(boolean inReadOnly) {
        this._readOnly = inReadOnly;
    }

    public boolean requiresDatabase() {
        return this._requiresDatabase;
    }

    public void setRequiresDatabase(boolean inRequiresDatabase) {
        this._requiresDatabase = inRequiresDatabase;
    }

}
