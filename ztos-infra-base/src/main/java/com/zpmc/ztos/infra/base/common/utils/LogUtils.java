package com.zpmc.ztos.infra.base.common.utils;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

public class LogUtils {

    private static String _jmsQueueAppenderNodeName;
    public static final String HIBERNATE_PROXY_WARNINGS = "org.hibernate.engine.StatefulPersistenceContext.ProxyWarnLog";
    public static final String HIBERNATE = "org.hibernate";
    public static final String CARINA = "com.navis.framework";
    public static final String SPRING = "org.springframework";
    public static final String SPRING_HIBERNATE = "org.springframework.orm";
    public static final String HQL = "com.navis.HQL";
    public static final String DEV = "dev.";
    private static final String DISABLE_LOGUTILS = "disable.logutils";
    private static final Logger LOGGER;
    private static final String SERVER_ID = "serverId";
    private static final String APPENDER_NAME = "NavisRemoteLogAppender";

    private LogUtils() {
    }

    public static Level pushLogLevel(String inPackage, Level inNewLevel) {
        Logger lg = Logger.getLogger((String)inPackage);
        Level oldLevel = lg.getLevel();
        LogUtils.setLogLevel(inPackage, inNewLevel);
        return oldLevel;
    }

    public static void setLogLevel(String inPackage, Level inLevel) {
        Logger lg = Logger.getLogger((String)inPackage);
        if (null == System.getProperty(DISABLE_LOGUTILS)) {
            lg.setLevel(inLevel);
        }
    }

    public static void setFrameworkLogLevel(Level inLevel) {
        LogUtils.setLogLevel(CARINA, inLevel);
        LogUtils.setLogLevel(HIBERNATE, inLevel);
        LogUtils.setLogLevel(SPRING, inLevel);
    }

    public static void setLogLevel(Class inClass, Level inLevel) {
        Logger logger = Logger.getLogger((Class)inClass);
        if (null == System.getProperty(DISABLE_LOGUTILS)) {
            logger.setLevel(inLevel);
        }
    }

    public static void setLogLevelForPackageOfClass(Class inClass, Level inLevel) {
        String fullyQualifiedName = inClass.getName();
        int lastDot = fullyQualifiedName.lastIndexOf(46);
        String packageName = fullyQualifiedName.substring(0, lastDot);
        LogUtils.setLogLevel(packageName, inLevel);
    }

    public static void setShowSql(boolean inShowSql) {
        if (inShowSql) {
            LogUtils.setLogLevel("org.hibernate.SQL", Level.DEBUG);
        } else {
            LogUtils.setLogLevel("org.hibernate.SQL", Level.ERROR);
        }
    }

    public static void setShowHibernateProxyWarning(boolean inShowWarning) {
        if (inShowWarning) {
            LogUtils.setLogLevel(HIBERNATE_PROXY_WARNINGS, Level.WARN);
        } else {
            LogUtils.setLogLevel(HIBERNATE_PROXY_WARNINGS, Level.ERROR);
        }
    }

    public static void forceLogAtDebug(Logger inLogger, Object inMessage) {
        LogUtils.forceLogAtDebug(inLogger, inMessage, null);
    }

    public static void forceLogAtDebug(Logger inLogger, Object inMessage, @Nullable Throwable inThrowable) {
        if (inLogger.isDebugEnabled()) {
            inLogger.log(LogUtils.class.getCanonicalName(), (Priority) Level.DEBUG, inMessage, inThrowable);
        } else {
            Level savedLevel = inLogger.getLevel();
            inLogger.setLevel(Level.DEBUG);
            inLogger.log(LogUtils.class.getCanonicalName(), (Priority) Level.DEBUG, inMessage, inThrowable);
            inLogger.setLevel(savedLevel);
        }
    }

    public static void forceLogAtInfo(Logger inLogger, Object inMessage) {
        LogUtils.forceLogAtInfo(inLogger, inMessage, null);
    }

    public static void forceLogAtInfo(Logger inLogger, Object inMessage, @Nullable Throwable inThrowable) {
        if (inLogger.isInfoEnabled()) {
            inLogger.log(LogUtils.class.getCanonicalName(), (Priority) Level.INFO, inMessage, inThrowable);
        } else {
            Level savedLevel = inLogger.getLevel();
            inLogger.setLevel(Level.INFO);
            inLogger.log(LogUtils.class.getCanonicalName(), (Priority) Level.INFO, inMessage, inThrowable);
            inLogger.setLevel(savedLevel);
        }
    }

    public static boolean isLoggerEnabledFor(Logger inLogger, Priority inLevel) {
        try {
            return inLogger.isEnabledFor(inLevel);
        }
        catch (Throwable t) {
            return false;
        }
    }

    public static void createJmsLogAppender(String inNodeType) throws Exception {
//        JmsQueueAppender appender = JmsQueueAppender.getInstance();
//        appender.setNodeType(inNodeType);
//        if (_jmsQueueAppenderNodeName != null) {
//            appender.setNodeName(_jmsQueueAppenderNodeName);
//        }
//        appender.start();
    }

    public static void createJmsLogAppender(String inNodeType, @Nullable String inNodeName) throws Exception {
        _jmsQueueAppenderNodeName = inNodeName;
        LogUtils.createJmsLogAppender(inNodeType);
    }

    public static void destroyJmsLogAppender() {
//        JmsQueueAppender appender = JmsQueueAppender.getInstance();
//        appender.stop();
    }

    static {
        LOGGER = Logger.getLogger(LogUtils.class);
    }
}
