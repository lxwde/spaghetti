package com.zpmc.ztos.infra.base.business.enums.edi;

import com.zpmc.ztos.infra.base.business.model.MetafieldIdList;
import com.zpmc.ztos.infra.base.common.enums.AtomizedEnum;
import org.apache.log4j.Logger;

import java.lang.reflect.Method;

public class AbstractEdiMessageClassEnum extends AtomizedEnum {
    private String _domainClassName;
    private Class _domainClass;
    private String _description;
    private boolean _isInbound;
    private boolean _isOutbound;
    private boolean _isSchedule;
    private boolean _resolveAttempted = false;
    private static final Logger LOGGER = Logger.getLogger(AbstractEdiMessageClassEnum.class);

    public AbstractEdiMessageClassEnum(String inAbbrevation, String inDescriptivePropertyKey, String inCodePropertyKey, String inBackgroundColor, String inForegroundColor, String inIconIdPath, String inDescription, boolean inIsInbound, boolean inIsOutbound, String inDomainClass, boolean inIsSchedule) {
        super(inAbbrevation, inDescriptivePropertyKey, inCodePropertyKey, inBackgroundColor, inForegroundColor, inIconIdPath);
        this._description = inDescription;
        this._isInbound = inIsInbound;
        this._isOutbound = inIsOutbound;
        this._domainClassName = inDomainClass;
        this._isSchedule = inIsSchedule;
    }

    public MetafieldIdList getPredicateFields() {
        return (MetafieldIdList)this.invokeClassMethod("getPredicateFields", MetafieldIdList.class);
    }

    public MetafieldIdList getNumericPredicateFields() {
        return (MetafieldIdList)this.invokeClassMethod("getNumericPredicateFields", MetafieldIdList.class);
    }

    private Object invokeClassMethod(String inMethodName, Class inResultClass) {
        Object result = null;
        Class c = this.getDomainClass();
        if (c != null) {
            try {
                Method m = c.getMethod(inMethodName, new Class[0]);
                Object[] arguments = new Object[1];
                try {
                    arguments[0] = this;
                    result = m.invoke(null, new Object[0]);
                    if (result != null && !inResultClass.isAssignableFrom(result.getClass())) {
                        LOGGER.error((Object)("Method invocation returned object of wrong Class " + result + " for Class " + c));
                        result = null;
                    }
                }
                catch (Exception e) {
                    LOGGER.error((Object)("Method invocation failed: " + e + " for Class " + c + "for method " + inMethodName));
                }
            }
            catch (NoSuchMethodException e) {
                LOGGER.error((Object)("Method was not found in Class " + c + "for method " + inMethodName));
            }
        }
        return result;
    }

    public Class getDomainClass() {
        if (!this._resolveAttempted) {
            try {
                this._domainClass = Class.forName(this._domainClassName);
            }
            catch (ClassNotFoundException e) {
                LOGGER.warn((Object)("getDomainClass: problem finding class: " + e + " (expected when running from a submodule)"));
            }
            this._resolveAttempted = true;
        }
        return this._domainClass;
    }

    public boolean getIsInbound() {
        return this._isInbound;
    }

    public boolean getIsOutbound() {
        return this._isOutbound;
    }

    public String getDescription() {
        return this._description;
    }

    public boolean getIsSchedule() {
        return this._isSchedule;
    }
}
