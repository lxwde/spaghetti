package com.zpmc.ztos.infra.base.common.utils;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.business.interfaces.IMessageCollector;
import com.zpmc.ztos.infra.base.business.interfaces.IPersistenceEventRecorder;
import com.zpmc.ztos.infra.base.business.interfaces.IReloadableSessionFactory;
import com.zpmc.ztos.infra.base.business.interfaces.ISecondLevelCacheConfigurationAware;
import com.zpmc.ztos.infra.base.common.contexts.PortalApplicationContext;
import com.zpmc.ztos.infra.base.common.exceptions.BizFailure;
import com.zpmc.ztos.infra.base.common.exceptions.SevereErrorsInResponseException;
import com.zpmc.ztos.infra.base.common.model.Roastery;
import com.zpmc.ztos.infra.base.common.model.TransactionParms;
import com.zpmc.ztos.infra.base.common.model.UserContext;
import com.zpmc.ztos.infra.base.utils.CarinaUtils;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.annotations.Persister;
import org.hibernate.cfg.Configuration;
import org.hibernate.exception.GenericJDBCException;
import org.hibernate.exception.SQLGrammarException;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.type.EntityType;
import org.hibernate.type.Type;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.orm.hibernate3.LocalSessionFactoryBean;


public class PersistenceUtils {
    public static final String PERSISTER_BEAN_ID = "persister";
    public static final String HIBERNATE_SESSION_FACTORY = "hibernateSessionFactory";
    private static final Logger LOGGER = Logger.getLogger(PersistenceUtils.class);
    private static final int SQL_ERROR_TOO_MUCH_DATA = 17026;

    private PersistenceUtils() {
    }

    public static void handleRollback(Throwable inException, Transaction inTransaction, IMessageCollector inCollector) {
        if (!(inException instanceof SevereErrorsInResponseException)) {
            inCollector.registerExceptions(inException);
        }
        LogUtils.forceLogAtInfo(LOGGER, "endTransaction: rolling-back: " + inCollector.toString());
        try {
            if (inTransaction != null) {
                inTransaction.rollback();
                LOGGER.info((Object)"endTransaction: Transaction rolled back \n");
            }
        }
        catch (Throwable t) {
            LOGGER.error((Object)("completeTransaction: Unable to commit or rollback transaction" + t), t);
            inCollector.registerExceptions(t);
        }
    }

    public static boolean isDatabaseAlive(UserContext inUserContext) {
//        PersistenceTemplate pt = new PersistenceTemplate(inUserContext);
//        IMessageCollector collector = pt.invoke(new CarinaPersistenceCallback(){
//
//            @Override
//            protected void doInTransaction() {
//            }
//        });
//        return !collector.containsMessage();
        return false;
    }

    public static TransactionParms createAndBindTransactionParmeters(UserContext inContext, boolean inNullContextFailure) {
        TransactionParms transactionParms = new TransactionParms();
        if (inContext == null) {
            if (inNullContextFailure) {
                throw BizFailure.create("Your application must have a usercontext!");
            }
            LOGGER.warn((Object)"processRequest: NO UserContext PROVIDED!");
//            BizPortalPrefs prefs = (BizPortalPrefs) Roastery.getBean("bizPortalPrefs");
//            if (prefs.requiresUserContext()) {
//                LOGGER.error((Object)"processRequest: You MUST provide a UserContext for your application.");
//                LOGGER.error((Object) CarinaUtils.getCompactStackTrace(BizFailure.create("Missing UserContext")));
//            }
        } else {
            transactionParms.setUserContext(inContext);
        }
        transactionParms.bind();
        return transactionParms;
    }

    public static DataAccessException convertHibernateAccessException(HibernateException inEx) {
        if (inEx instanceof SQLGrammarException) {
            SQLGrammarException jdbcEx = (SQLGrammarException)inEx;
            String error = inEx.getMessage() + "\n with  " + jdbcEx.getSQLException() + "\n SQL STATE=" + jdbcEx.getSQLState() + " SQL ERROR CODE=" + jdbcEx.getErrorCode() + "\n" + CarinaUtils.getCompactStackTrace(inEx) + "\n";
            return new InvalidDataAccessResourceUsageException(error, (Throwable)inEx);
        }
        if (inEx instanceof GenericJDBCException) {
            GenericJDBCException jdbcEx = (GenericJDBCException)inEx;
            int errorCode = jdbcEx.getErrorCode();
            String error = inEx.getMessage() + "\n with  " + jdbcEx.getSQLException() + "\n SQL STATE=" + jdbcEx.getSQLState() + " SQL ERROR CODE=" + errorCode + "\n" + CarinaUtils.getCompactStackTrace(inEx) + "\n";
            if (errorCode == 17026) {
   //             return new CustomDataAccessException("", inEx, IFrameworkPropertyKeys.DATAACCESS__TOO_MANY_COLUMNS);
            }
            return new InvalidDataAccessResourceUsageException(error, (Throwable)inEx);
        }
        return SessionFactoryUtils.convertHibernateAccessException(inEx);
    }

    public static Configuration getMappingConfiguration() {
        return PersistenceUtils.getLocalSessionFactoryBean().getConfiguration();
    }

    public static PersistentClass getPersistentClass(String inFullyQualifiedName) {
 //       return PersistenceUtils.getMappingConfiguration().getClassMapping(inFullyQualifiedName);
        return null;
    }

    public static Persister getPersister() {
        return (Persister) Roastery.getBean(PERSISTER_BEAN_ID);
    }

    public static SessionFactory getSessionFactory() {
        return (SessionFactory) Roastery.getBean(HIBERNATE_SESSION_FACTORY);
    }

    public static LocalSessionFactoryBean getLocalSessionFactoryBean() {
        return (LocalSessionFactoryBean)((Object) Roastery.getBean("&hibernateSessionFactory"));
    }

    public static IPersistenceEventRecorder getPersistenceEventRecorder() {
        return (IPersistenceEventRecorder) PortalApplicationContext.getBean("persistenceEventRecorder");
    }

    public static boolean isSecondLevelCacheEnabled() {
        LocalSessionFactoryBean bean = PersistenceUtils.getLocalSessionFactoryBean();
        if (bean instanceof ISecondLevelCacheConfigurationAware) {
            return ((ISecondLevelCacheConfigurationAware)((Object)bean)).isSecondLevelCacheEnabled().getValue(false);
        }
        return false;
    }

    @NotNull
    public static IReloadableSessionFactory getReloadableSessionFactory() {
        LocalSessionFactoryBean localSessionFactoryBean = PersistenceUtils.getLocalSessionFactoryBean();
        if (localSessionFactoryBean instanceof IReloadableSessionFactory) {
            return (IReloadableSessionFactory)((Object)localSessionFactoryBean);
        }
        throw BizFailure.create("Session factory was configured to be not reloadable!");
    }

    public static Long getJvmExecutionSequence() {
        IReloadableSessionFactory factory = PersistenceUtils.getReloadableSessionFactory();
        return factory.getJvmExecutionSequence();
    }

    public static int getSessionFactoryReloadSequenceForJvm() {
        LocalSessionFactoryBean localSessionFactoryBean = PersistenceUtils.getLocalSessionFactoryBean();
        if (localSessionFactoryBean instanceof IReloadableSessionFactory) {
            return ((IReloadableSessionFactory)((Object)localSessionFactoryBean)).getSessionFactoryReloadSequenceForJvm();
        }
        return 0;
    }

    public static boolean isOneToOneType(Type inType) {
        if (inType instanceof EntityType) {
            return ((EntityType)inType).isOneToOne();
        }
        return false;
    }
}
