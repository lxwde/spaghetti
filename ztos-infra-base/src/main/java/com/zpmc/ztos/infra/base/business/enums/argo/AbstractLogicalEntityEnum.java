package com.zpmc.ztos.infra.base.business.enums.argo;

import com.zpmc.ztos.infra.base.business.model.MetafieldIdList;
import com.zpmc.ztos.infra.base.common.enums.AtomizedEnum;
import org.apache.log4j.Logger;

import java.lang.reflect.Method;

public class AbstractLogicalEntityEnum extends AtomizedEnum {
    private String _domainClassName;
    private transient Class _domainClass;
    private transient boolean _resolveAttempted = false;
    private static final Logger LOGGER = Logger.getLogger(AbstractLogicalEntityEnum.class);

    public AbstractLogicalEntityEnum(String inAbbrevation, String inDescriptivePropertyKey, String inCodePropertyKey, String inBackgroundColor, String inForegroundColor, String inIconIdPath, String inDomainClass) {
        super(inAbbrevation, inDescriptivePropertyKey, inCodePropertyKey, inBackgroundColor, inForegroundColor, inIconIdPath);
        this._domainClassName = inDomainClass;
    }

    public MetafieldIdList getPredicateFields() {
        return (MetafieldIdList)this.invokeClassMethod("getPredicateFields", MetafieldIdList.class);
    }

    public MetafieldIdList getPathsToGuardians() {
        return (MetafieldIdList)this.invokeClassMethod("getPathsToGuardians", MetafieldIdList.class);
    }

    public MetafieldIdList getPathsToBizUnits() {
        return (MetafieldIdList)this.invokeClassMethod("getPathsToBizUnits", MetafieldIdList.class);
    }

    public MetafieldIdList getUpdateFields() {
        return (MetafieldIdList)this.invokeClassMethod("getUpdateFields", MetafieldIdList.class);
    }

    private Object invokeClassMethod(String inMethodName, Class inResultClass) {
        Object result = null;
        Class c = this.getDomainClass();
        if (c != null) {
            Class[] parameters = new Class[]{LogicalEntityEnum.class};
            try {
                Method m = c.getMethod(inMethodName, parameters);
                Object[] arguments = new Object[1];
                try {
                    arguments[0] = this;
                    result = m.invoke(null, arguments);
                    if (result != null && !inResultClass.isAssignableFrom(result.getClass())) {
                        LOGGER.error((Object)("Method invocation returned object of wrong Class " + result + " for Class " + c));
                        result = null;
                    }
                }
                catch (Exception e) {
                    LOGGER.error((Object)("Method invocation failed: for Class " + c + "for method " + inMethodName), (Throwable)e);
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
                LOGGER.warn((Object)"getDomainClass: problem finding class: (expected when running from a submodule)", (Throwable)e);
            }
            this._resolveAttempted = true;
        }
        return this._domainClass;
    }

}
