package com.zpmc.ztos.infra.base.utils;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.business.interfaces.IFrameworkPropertyKeys;
import com.zpmc.ztos.infra.base.business.interfaces.IMessageCollector;
import com.zpmc.ztos.infra.base.business.interfaces.IMetafieldUserMessage;
import com.zpmc.ztos.infra.base.business.interfaces.IRootCause;
import com.zpmc.ztos.infra.base.common.exceptions.BizFailure;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.model.ValueObject;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang.exception.NestableException;
import org.apache.log4j.Logger;
import org.dom4j.Element;
import org.hibernate.CallbackException;
import org.springframework.core.NestedRuntimeException;
import org.springframework.dao.DataAccessException;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;
import org.springframework.orm.hibernate3.HibernateOptimisticLockingFailureException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.UndeclaredThrowableException;
import java.net.URL;
import java.rmi.RemoteException;
import java.security.CodeSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

public class CarinaUtils {
    private static final String[] CAUSE_METHOD_NAMES = new String[]{"getCause", "getNextException", "getTargetException", "getException", "getSourceException", "getRootCause", "getCausedByException", "getNested"};
    private static final Logger LOGGER = Logger.getLogger(CarinaUtils.class);

    private CarinaUtils() {
    }

    @Nullable
    public static Throwable unwrap(Throwable inExceptionChain) {
        Throwable t;
        Throwable priorException = t = inExceptionChain;
        while (t != null) {
//            LOGGER.debug((Object)("unwrap: Exception = " + t.getClass().toString()));
            if (t instanceof IRootCause) {
                return t;
            }
            if (t instanceof InvocationTargetException) {
                priorException = t;
                t = ((InvocationTargetException)t).getTargetException();
                continue;
            }
            if (t instanceof RemoteException) {
                priorException = t;
                t = ((RemoteException)t).detail;
                continue;
            }
            if (t instanceof NestedRuntimeException) {
                BizFailure navisException;
                priorException = t;
//                if (t instanceof DataAccessException && (navisException = CarinaUtils.convertHibernateExceptionToCarinaException((DataAccessException)t)) != null) {
//                    return navisException;
//                }
                t = t.getCause();
                continue;
            }
            if (t instanceof CallbackException) {
                t = t.getCause();
                continue;
            }
            if (t instanceof NestableException) {
                priorException = t;
                t = t.getCause();
                continue;
            }
            if (t instanceof UndeclaredThrowableException) {
                priorException = t;
                t = ((UndeclaredThrowableException)t).getUndeclaredThrowable();
                continue;
            }
            if (t instanceof BizFailure) {
                BizFailure f = (BizFailure)t;
                if (f.getCause() instanceof BizViolation) {
                    return f.getCause();
                }
                return t;
            }
            if (t instanceof SQLException) {
                return t;
            }
//            Throwable nested = CarinaUtils.lookForCause(t);
//            if (nested != null) {
//                priorException = t;
//                t = nested;
//                continue;
//            }
            priorException = t;
            t = null;
        }
        return priorException;
    }



    @Nullable
    private static BizFailure convertHibernateExceptionToCarinaException(DataAccessException inException) {
        if (inException instanceof HibernateOptimisticLockingFailureException) {
            return new BizFailure(IFrameworkPropertyKeys.FAILURE__UPDATE_STALE, null, null);
        }
        return null;
    }

    private static Throwable lookForCause(Throwable inThrowable) {
        for (String causeMethodName : CAUSE_METHOD_NAMES) {
            Method method;
            block9: {
                method = null;
                try {
                    method = inThrowable.getClass().getMethod(causeMethodName, new Class[0]);
                }
                catch (NoSuchMethodException noSuchMethodEx) {
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug((Object)noSuchMethodEx);
                    }
                }
                catch (SecurityException securityEx) {
                    if (!LOGGER.isDebugEnabled()) break block9;
                    LOGGER.debug((Object)securityEx);
                }
            }
            if (method == null || !Throwable.class.isAssignableFrom(method.getReturnType())) continue;
            try {
                return (Throwable)method.invoke(inThrowable, new Object[0]);
            }
            catch (IllegalAccessException illegalAccessEx) {
                if (!LOGGER.isDebugEnabled()) continue;
                LOGGER.debug((Object)illegalAccessEx);
            }
            catch (IllegalArgumentException illegalArgumentEx) {
                if (!LOGGER.isDebugEnabled()) continue;
                LOGGER.debug((Object)illegalArgumentEx);
            }
            catch (InvocationTargetException invocationTargetException) {
                if (!LOGGER.isDebugEnabled()) continue;
                LOGGER.debug((Object)invocationTargetException);
            }
        }
        return null;
    }

    public static String dotCat(String inString1, String inString2) {
        return inString1 + '.' + inString2;
    }

    public static String dotCat(String inString1, String inString2, String inString3) {
        return inString1 + '.' + inString2 + '.' + inString3;
    }

    public static String[] toStringArray(List inList) {
       // return inList.toArray(new String[inList.size()]);
        return null;
    }

    @Nullable
    public static String[] toStringArray(Object[] inArray) {
        if (inArray == null) {
            return null;
        }
        String[] result = new String[inArray.length];
        System.arraycopy(inArray, 0, result, 0, inArray.length);
        return result;
    }

    public static List convertXmlSourceToVaoList(Element inXmlSource) {
        ArrayList<ValueObject> vaoList = new ArrayList<ValueObject>();
//        List rowList = inXmlSource.getChildren("row");
//        for (Object rowElement : rowList) {
//            ValueObject vao = new ValueObject("?");
//            String id = ((Element)rowElement).getAttributeValue("primary-key");
//            vao.setEntityPrimaryKey((Serializable)((Object)id));
//            List elemList = rowElement.getChildren("field");
//            for (Element fieldElement : elemList) {
//                String fid = fieldElement.getAttributeValue("id");
//                vao.setFieldValue(fid, (Object)fieldElement.getText());
//            }
//            vaoList.add(vao);
//        }
        return vaoList;
    }

    public static boolean isBlank(String inString) {
        return StringUtils.isBlank((String)inString);
    }

    public static String wrap(String inString, int inMaxCharsPerLine) {
        String[] words = StringUtils.split((String)inString);
        StringBuilder buf = new StringBuilder();
        int charCount = 0;
        for (int i = 0; i < words.length; ++i) {
            String word = words[i];
            if (charCount + word.length() > inMaxCharsPerLine) {
                buf.append('\n');
                charCount = 0;
            } else if (charCount > 0) {
                buf.append(' ');
                ++charCount;
            }
            buf.append(word);
            charCount += word.length();
        }
        return buf.toString();
    }

    public static String getStackTrace(Throwable inThrowable) {
        return ExceptionUtils.getFullStackTrace((Throwable)inThrowable);
    }

    @NotNull
    public static String getFormattedThreadStackTrace(@NotNull StackTraceElement[] inStackTraceElements) {
        StringBuilder stackTraceBuilder = new StringBuilder();
        for (StackTraceElement line : inStackTraceElements) {
            stackTraceBuilder.append("\t").append(line).append("\n");
        }
        return stackTraceBuilder.toString();
    }

    @NotNull
    public static String getCurrentFormattedThreadStackTrace() {
        return CarinaUtils.getFormattedThreadStackTrace(Thread.currentThread().getStackTrace());
    }

    public static String getCompactStackTrace(Throwable inThrowable) {
        String message = inThrowable.getMessage();
        if (message == null) {
            message = inThrowable.toString();
        }
        if (message == null) {
            message = "no message";
        }
        StringBuilder compactTrace = new StringBuilder(message);
        compactTrace.append(" (compact stack trace follows)");
        StackTraceElement[] stackTraceElements = inThrowable.getStackTrace();
        for (int i = 0; i < stackTraceElements.length; ++i) {
            StackTraceElement stackTraceElement = stackTraceElements[i];
            boolean isNavisClass = stackTraceElement.getClassName().startsWith("com.navis");
            if (i != 0 && !isNavisClass) continue;
            compactTrace.append('\n').append(stackTraceElement.toString());
        }
        return compactTrace.toString();
    }

    @Nullable
    public static BizViolation convertToBizViolation(IMessageCollector inMessageCollector) {
        BizViolation bv = null;
        List msgs = inMessageCollector.getMessages();
        for (Object um : msgs) {
            bv = new BizViolation(((IMetafieldUserMessage)um).getMessageKey(), null, bv, ((IMetafieldUserMessage)um).getMetafieldId(), ((IMetafieldUserMessage)um).getParms());
        }
        return bv;
    }

    public static BizViolation convertToBizViolation(BizViolation inViolation, IMessageCollector inMessageCollector) {
        BizViolation bv = inViolation;
        List msgs = inMessageCollector.getMessages();
//        for (UserMessage um : msgs) {
//            bv = new BizViolation(um.getMessageKey(), null, bv, null, um.getParms());
//        }
        return bv;
    }

    @Nullable
    public static Manifest getManifest(@NotNull Class<?> inClazz) {
        Manifest defaultManifest = null;
        CodeSource codeSource = inClazz.getProtectionDomain().getCodeSource();
        if (codeSource == null) {
            return defaultManifest;
        }
        try {
            String classContainer = codeSource.getLocation().toString();
            if (classContainer.endsWith(".jar")) {
                URL manifestUrl = new URL("jar:" + classContainer + "!/META-INF/MANIFEST.MF");
                Manifest manifest = new Manifest(manifestUrl.openStream());
                return manifest;
            }
        }
        catch (Exception e) {
            throw new IllegalArgumentException(String.format("Error getting manifest from jar file containing class >%s<", inClazz.getName()), e);
        }
        return defaultManifest;
    }

    @NotNull
    public static String extractVersionInfoString(@NotNull Manifest inManifest) {
        Attributes attributes = inManifest.getMainAttributes();
        String implementationTitle = attributes.getValue(Attributes.Name.IMPLEMENTATION_TITLE);
        String implementationVersion = attributes.getValue(Attributes.Name.SPECIFICATION_VERSION);
        String buildNumber = attributes.getValue("Build-Number");
        String builtBy = attributes.getValue("Build-By");
        String buildDate = attributes.getValue("Build-Date");
        String revision = attributes.getValue(Attributes.Name.IMPLEMENTATION_VERSION);
        String versionInfo = String.format("%S Version: %s TC Build Number: %s By: %s Date: %s Revision: %s", implementationTitle, implementationVersion, buildNumber, builtBy, buildDate, revision);
        return versionInfo;
    }

    @NotNull
    public static String getVersionStringFromManifest(Class inClazz) {
        Manifest manifest = CarinaUtils.getManifest(inClazz);
        return manifest == null ? "(no manifest)" : CarinaUtils.extractVersionInfoString(manifest);
    }

    @Nullable
    public static String getPropertyWithErrorAsNull(Object inBean, String inName) {
        String value = null;
        try {
            value = BeanUtils.getProperty((Object)inBean, (String)inName);
        }
        catch (Throwable t) {
            LOGGER.error((Object)("Error getting attribute: " + t));
        }
        return value;
    }
}
