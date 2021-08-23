package com.zpmc.ztos.infra.base.common.groovy;

import com.zpmc.ztos.infra.base.business.interfaces.IArgoPropertyKeys;
import com.zpmc.ztos.infra.base.business.interfaces.IMessageCollector;
import com.zpmc.ztos.infra.base.business.interfaces.IPropertyKey;
import com.zpmc.ztos.infra.base.common.callbacks.CarinaPersistenceCallback;
import com.zpmc.ztos.infra.base.common.database.PersistenceTemplatePropagationRequired;
import com.zpmc.ztos.infra.base.common.exceptions.BizFailure;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.model.CodeTimer;
import com.zpmc.ztos.infra.base.common.model.UserContext;
import com.zpmc.ztos.infra.base.common.utils.UserContextUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.lang.reflect.Method;

public class CustomGroovyInvoker {
    public static final String EXECUTE_METHOD_NAME = "execute";
    private static final Logger LOGGER = Logger.getLogger(CustomGroovyInvoker.class);

    private CustomGroovyInvoker() {
    }

    public static Object invokeCustomGroovy(String inGroovyScript, Class[] inObjectTypes, Object[] inArgs, String inGroovyClass, String inMethodName) throws BizViolation {
        return CustomGroovyInvoker.invokeCustomGroovy(inGroovyScript, inObjectTypes, inArgs, inGroovyClass, inMethodName, UserContextUtils.getSystemUserContext());
    }

    public static Object invokeCustomGroovy(String inGroovyScript, final Class[] inObjectTypes, final Object[] inArgs, final String inGroovyClass, final String inMethodName, UserContext inUserContext) throws BizViolation {
        Object groovyInstance;
//        final Class groovyClass = CustomGroovyInvoker.getGroovyClassCache().getGroovyClass(inGroovyScript);
//        if (groovyClass == null) {
//            throw BizViolation.create((IPropertyKey) IArgoPropertyKeys.COULD_NOT_COMPILE_CLASS, null, (Object)inGroovyClass);
//        }
        try {
//            groovyInstance = groovyClass.newInstance();
        }
        catch (Exception e) {
            throw BizViolation.create((IPropertyKey) IArgoPropertyKeys.INSTANTIATION_ERROR, null, (Object)inGroovyClass, (Object)e.getMessage());
        }
        final Method[] executeMethod = new Method[1];
        final Object[] groovyResult = new Object[1];
        final Throwable[] gvyException = new Throwable[1];
        PersistenceTemplatePropagationRequired pt = new PersistenceTemplatePropagationRequired(inUserContext);
        IMessageCollector collector = pt.invoke(new CarinaPersistenceCallback(){

            public void doInTransaction() {
 //               try {
 //                   executeMethod[0] = groovyClass.getMethod(inMethodName, inObjectTypes);
//                }
//                catch (NoSuchMethodException e) {
//                    throw BizFailure.create((IPropertyKey)IArgoPropertyKeys.EXECUTE_METHOD_NOT_FOUND, null, (Object)(inGroovyClass + "." + inMethodName));
//                }
//                String className = groovyClass.getName();
                try {
                    CodeTimer codeTimer = new CodeTimer(LOGGER, Level.WARN);
                    codeTimer.enableDiagnostic("N4 Groovy");
//                    GroovyApi.logCustomCodeExecution("START", groovyClass.getName(), "", "", null);
//                    groovyResult[0] = executeMethod[0].invoke(groovyInstance, inArgs);
//                    GroovyApi.logCustomCodeExecution("END", groovyClass.getName(), "", "", codeTimer.getTotalMillis());
//                    ExtensionStatisticsUtils.recordExtensionIfEnabled((String)className, (String)inMethodName, (CodeTimer)codeTimer);
//                    codeTimer.report("Groovy for " + className + " executed in ");
//                    if (LOGGER.isDebugEnabled()) {
//                        LOGGER.debug((Object)("Groovy returned the result : " + groovyResult[0]));
//                    }
                }
//                catch (InvocationTargetException ex) {
//                    Throwable cause = ex.getTargetException();
//                    if (cause instanceof BizViolation) {
//                        BizViolation bv = (BizViolation)cause;
//                        gvyException[0] = bv;
//                    } else {
//                        gvyException[0] = BizFailure.create((IPropertyKey)IArgoPropertyKeys.GROOVY_EXECUTION_FAILURE, null, (Object)(className + " " + cause.getLocalizedMessage()));
//                    }
//                    LOGGER.error((Object)("Groovy Error from " + className + ": " + cause.getLocalizedMessage()), cause);
//                }
                catch (Throwable e) {
//                    LOGGER.error((Object)("Groovy Error from " + className + ": " + e.getLocalizedMessage()), e);
//                    BizFailure gvyBizFailure = BizFailure.create((IPropertyKey)IArgoPropertyKeys.GROOVY_EXECUTION_FAILURE, null, (Object)(className + " " + e.getLocalizedMessage()));
//                    gvyException[0] = gvyBizFailure;
                }
            }
        });
        if (gvyException[0] != null) {
            if (gvyException[0] instanceof BizViolation) {
                LOGGER.error((Object)("Groovy BizViolation : " + gvyException[0].getLocalizedMessage()), gvyException[0]);
                throw (BizViolation)gvyException[0];
            }
            if (gvyException[0] instanceof BizFailure) {
                LOGGER.error((Object)("Groovy BizFailure : " + gvyException[0].getLocalizedMessage()), gvyException[0]);
                throw (BizFailure)gvyException[0];
            }
        }
        return groovyResult[0];
    }

//    public static GroovyClassCache getGroovyClassCache() {
//        return (GroovyClassCache) Roastery.getBean((String)"generalGroovyClassCache");
//    }

}
