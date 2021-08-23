package com.zpmc.ztos.infra.base.common.contexts;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.common.model.UserContext;

import java.util.HashMap;
import java.util.Map;

public class ArgoExtensionContext extends AbstractExtensionContext {
    private Map _parameters = new HashMap();
    private String _className;
    private String _methodName;

    public static ArgoExtensionContext createExtensionContext(@NotNull UserContext inUserContext, String inClassName, String inMethodName) {
        return new ArgoExtensionContext(inUserContext, inClassName, inMethodName);
    }

    ArgoExtensionContext(@NotNull UserContext inUserContext, String inClassName, String inMethodName) {
        super(inUserContext);
        this._className = inClassName;
        this._methodName = inMethodName;
    }

    public String getClassName() {
        return this._className;
    }

    public String getMethodName() {
        return this._methodName;
    }

    public Map getParameters() {
        return this._parameters;
    }

    public void setParameters(Map inParameters) {
        this._parameters = inParameters;
    }

    public void setParameter(Object inKey, Object inParameter) {
        this._parameters.put(inKey, inParameter);
    }

    public String getBriefDescription() {
        return this._className + "." + this._methodName;
    }
}
