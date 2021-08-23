package com.zpmc.ztos.infra.base.common.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.*;

import org.hibernate.connection.ConnectionProvider;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.exception.*;
import org.springframework.core.NamedThreadLocal;
import org.springframework.dao.*;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.orm.hibernate3.*;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import javax.transaction.TransactionManager;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

//import org.springframework.orm.hibernate3.LocalDataSourceConnectionProvider;

public class SessionFactoryUtils {

    public static final int SESSION_SYNCHRONIZATION_ORDER = 900;
    static final Log logger = LogFactory.getLog(SessionFactoryUtils.class);
    private static final ThreadLocal<Map<SessionFactory, Set<Session>>> deferredCloseHolder = new NamedThreadLocal("Hibernate Sessions registered for deferred close");

    public static DataSource getDataSource(SessionFactory sessionFactory) {
        ConnectionProvider cp;
  //      if (sessionFactory instanceof SessionFactoryImplementor && (cp = ((SessionFactoryImplementor)sessionFactory).getConnectionProvider()) instanceof LocalDataSourceConnectionProvider) {
 //           return ((LocalDataSourceConnectionProvider)cp).getDataSource();
  //          return null;
  //      }
        return null;
    }

    public static SQLExceptionTranslator newJdbcExceptionTranslator(SessionFactory sessionFactory) {
        DataSource ds = SessionFactoryUtils.getDataSource(sessionFactory);
        if (ds != null) {
            return new SQLErrorCodeSQLExceptionTranslator(ds);
        }
        return new SQLStateSQLExceptionTranslator();
    }

    public static TransactionManager getJtaTransactionManager(SessionFactory sessionFactory, Session session) {
        SessionFactory internalFactory;
        SessionFactoryImplementor sessionFactoryImpl = null;
        if (sessionFactory instanceof SessionFactoryImplementor) {
            sessionFactoryImpl = (SessionFactoryImplementor)sessionFactory;
        } else if (session != null && (internalFactory = session.getSessionFactory()) instanceof SessionFactoryImplementor) {
            sessionFactoryImpl = (SessionFactoryImplementor)internalFactory;
        }
    //    return sessionFactoryImpl != null ? sessionFactoryImpl.getTransactionManager() : null;
        return null;
    }

    public static Session getSession(SessionFactory sessionFactory, boolean allowCreate) throws DataAccessResourceFailureException, IllegalStateException {
        try {
            return SessionFactoryUtils.doGetSession(sessionFactory, null, null, allowCreate);
        }
        catch (HibernateException ex) {
            throw new DataAccessResourceFailureException("Could not open Hibernate Session", (Throwable)ex);
        }
    }

    public static Session getSession(SessionFactory sessionFactory, Interceptor entityInterceptor, SQLExceptionTranslator jdbcExceptionTranslator) throws DataAccessResourceFailureException {
        try {
            return SessionFactoryUtils.doGetSession(sessionFactory, entityInterceptor, jdbcExceptionTranslator, true);
        }
        catch (HibernateException ex) {
            throw new DataAccessResourceFailureException("Could not open Hibernate Session", (Throwable)ex);
        }
    }

    public static Session doGetSession(SessionFactory sessionFactory, boolean allowCreate) throws HibernateException, IllegalStateException {
        return SessionFactoryUtils.doGetSession(sessionFactory, null, null, allowCreate);
    }

    private static Session doGetSession(SessionFactory sessionFactory, Interceptor entityInterceptor, SQLExceptionTranslator jdbcExceptionTranslator, boolean allowCreate) throws HibernateException, IllegalStateException {
        Session session;
        Assert.notNull((Object)sessionFactory, (String)"No SessionFactory specified");
        Object resource = TransactionSynchronizationManager.getResource((Object)sessionFactory);
        if (resource instanceof Session) {
            return (Session)resource;
        }
        SessionHolder sessionHolder = (SessionHolder)((Object)resource);
  //      if (sessionHolder != null && !sessionHolder.isEmpty())
        {
            session = null;
            session = SessionFactoryUtils.getJtaSynchronizedSession(sessionHolder, sessionFactory, jdbcExceptionTranslator);
            if (session != null) {
                return session;
            }
        }
        logger.debug((Object)"Opening Hibernate Session");
    //    session = entityInterceptor != null ? sessionFactory.openSession(entityInterceptor) : sessionFactory.openSession();
        SessionFactoryUtils.registerJtaSynchronization(session, sessionFactory, jdbcExceptionTranslator, sessionHolder);
        if (!allowCreate && !SessionFactoryUtils.isSessionTransactional(session, sessionFactory)) {
            SessionFactoryUtils.closeSession(session);
            throw new IllegalStateException("No Hibernate Session bound to thread, and configuration does not allow creation of non-transactional one here");
        }
        return session;
    }

    private static Session getJtaSynchronizedSession(SessionHolder sessionHolder, SessionFactory sessionFactory, SQLExceptionTranslator jdbcExceptionTranslator) throws DataAccessResourceFailureException {
   //     TransactionManager jtaTm = SessionFactoryUtils.getJtaTransactionManager(sessionFactory, sessionHolder.getAnySession());
    //    if (jtaTm != null) {
    //        try {
//                int jtaStatus;
//                Transaction jtaTx = jtaTm.getTransaction();
//                if (jtaTx != null && ((jtaStatus = jtaTx.getStatus()) == 0 || jtaStatus == 1)) {
//                    Session session = sessionHolder.getValidatedSession((Object)jtaTx);
//                    if (session == null && !sessionHolder.isSynchronizedWithTransaction() && (session = sessionHolder.getValidatedSession()) != null) {
//                        logger.debug((Object)"Registering JTA transaction synchronization for existing Hibernate Session");
//                        sessionHolder.addSession((Object)jtaTx, session);
//                        jtaTx.registerSynchronization((Synchronization)new SpringJtaSynchronizationAdapter((TransactionSynchronization)new SpringSessionSynchronization(sessionHolder, sessionFactory, jdbcExceptionTranslator, false), jtaTm));
//                        sessionHolder.setSynchronizedWithTransaction(true);
//                        FlushMode flushMode = session.getFlushMode();
//                        if (flushMode.lessThan(FlushMode.COMMIT)) {
//                            session.setFlushMode(FlushMode.AUTO);
//                            sessionHolder.setPreviousFlushMode(flushMode);
//                        }
//                    }
//                    return session;
//                }
//                return sessionHolder.getValidatedSession();
 //           }
//            catch (Throwable ex) {
//                throw new DataAccessResourceFailureException("Could not check JTA transaction", ex);
//            }
//        }
     //   return sessionHolder.getValidatedSession();
        return null;
    }

    private static void registerJtaSynchronization(Session session, SessionFactory sessionFactory, SQLExceptionTranslator jdbcExceptionTranslator, SessionHolder sessionHolder) {
        TransactionManager jtaTm = SessionFactoryUtils.getJtaTransactionManager(sessionFactory, session);
        if (jtaTm != null) {
            try {
                int jtaStatus;
                Transaction jtaTx = (Transaction) jtaTm.getTransaction();
//                if (jtaTx != null && ((jtaStatus = jtaTx.getStatus()) == 0 || jtaStatus == 1)) {
//                    logger.debug((Object)"Registering JTA transaction synchronization for new Hibernate Session");
//                    SessionHolder holderToUse = sessionHolder;
//                    if (holderToUse == null) {
//                        holderToUse = new SessionHolder((Object)jtaTx, session);
//                    } else {
//                        holderToUse.addSession((Object)jtaTx, session);
//                    }
//                    jtaTx.registerSynchronization((Synchronization)new SpringJtaSynchronizationAdapter((TransactionSynchronization)new SpringSessionSynchronization(holderToUse, sessionFactory, jdbcExceptionTranslator, true), jtaTm));
//                    holderToUse.setSynchronizedWithTransaction(true);
//                    if (holderToUse != sessionHolder) {
//                        TransactionSynchronizationManager.bindResource((Object)sessionFactory, (Object)((Object)holderToUse));
//                    }
//                }
            }
            catch (Throwable ex) {
                throw new DataAccessResourceFailureException("Could not register synchronization with JTA TransactionManager", ex);
            }
        }
    }

    public static Session getNewSession(SessionFactory sessionFactory) {
        return SessionFactoryUtils.getNewSession(sessionFactory, null);
    }

    public static Session getNewSession(SessionFactory sessionFactory, Interceptor entityInterceptor) {
        Assert.notNull((Object)sessionFactory, (String)"No SessionFactory specified");
        try {
            SessionHolder sessionHolder = (SessionHolder)((Object)TransactionSynchronizationManager.getResource((Object)sessionFactory));
            if (sessionHolder != null && !sessionHolder.isEmpty()) {
                if (entityInterceptor != null) {
                    return sessionFactory.openSession(sessionHolder.getAnySession().connection(), entityInterceptor);
                }
                return sessionFactory.openSession(sessionHolder.getAnySession().connection());
            }
            if (entityInterceptor != null) {
                return sessionFactory.openSession(entityInterceptor);
            }
            return sessionFactory.openSession();
        }
        catch (HibernateException ex) {
            throw new DataAccessResourceFailureException("Could not open Hibernate Session", (Throwable)ex);
        }
    }

    public static String toString(Session session) {
        return session.getClass().getName() + "@" + Integer.toHexString(System.identityHashCode((Object)session));
    }

    public static boolean hasTransactionalSession(SessionFactory sessionFactory) {
        if (sessionFactory == null) {
            return false;
        }
        SessionHolder sessionHolder = (SessionHolder)((Object)TransactionSynchronizationManager.getResource((Object)sessionFactory));
       // return sessionHolder != null && !sessionHolder.isEmpty();
        return sessionHolder != null;
    }

    public static boolean isSessionTransactional(Session session, SessionFactory sessionFactory) {
        if (sessionFactory == null) {
            return false;
        }
        SessionHolder sessionHolder = (SessionHolder)((Object)TransactionSynchronizationManager.getResource((Object)sessionFactory));
      //  return sessionHolder != null && sessionHolder.containsSession(session);
        return sessionHolder != null;
    }

    public static void applyTransactionTimeout(Query query, SessionFactory sessionFactory) {
        SessionHolder sessionHolder;
        Assert.notNull((Object)query, (String)"No Query object specified");
        if (sessionFactory != null && (sessionHolder = (SessionHolder)((Object)TransactionSynchronizationManager.getResource((Object)sessionFactory))) != null && sessionHolder.hasTimeout()) {
            query.setTimeout(sessionHolder.getTimeToLiveInSeconds());
        }
    }

    public static void applyTransactionTimeout(Criteria criteria, SessionFactory sessionFactory) {
        Assert.notNull((Object)criteria, (String)"No Criteria object specified");
        SessionHolder sessionHolder = (SessionHolder)((Object)TransactionSynchronizationManager.getResource((Object)sessionFactory));
        if (sessionHolder != null && sessionHolder.hasTimeout()) {
            criteria.setTimeout(sessionHolder.getTimeToLiveInSeconds());
        }
    }

    public static DataAccessException convertHibernateAccessException(HibernateException ex) {
        if (ex instanceof JDBCConnectionException) {
            return new DataAccessResourceFailureException(ex.getMessage(), (Throwable)ex);
        }
        if (ex instanceof SQLGrammarException) {
            SQLGrammarException jdbcEx = (SQLGrammarException)ex;
            return new InvalidDataAccessResourceUsageException(ex.getMessage() + "; SQL [" + jdbcEx.getSQL() + "]", (Throwable)ex);
        }
        if (ex instanceof LockAcquisitionException) {
            LockAcquisitionException jdbcEx = (LockAcquisitionException)ex;
            return new CannotAcquireLockException(ex.getMessage() + "; SQL [" + jdbcEx.getSQL() + "]", (Throwable)ex);
        }
        if (ex instanceof ConstraintViolationException) {
            ConstraintViolationException jdbcEx = (ConstraintViolationException)ex;
            return new DataIntegrityViolationException(ex.getMessage() + "; SQL [" + jdbcEx.getSQL() + "]; constraint [" + jdbcEx.getConstraintName() + "]", (Throwable)ex);
        }
        if (ex instanceof DataException) {
            DataException jdbcEx = (DataException)ex;
            return new DataIntegrityViolationException(ex.getMessage() + "; SQL [" + jdbcEx.getSQL() + "]", (Throwable)ex);
        }
        if (ex instanceof JDBCException) {
            return new HibernateJdbcException((JDBCException)ex);
        }
        if (ex instanceof QueryException) {
            return new HibernateQueryException((QueryException)ex);
        }
        if (ex instanceof NonUniqueResultException) {
            return new IncorrectResultSizeDataAccessException(ex.getMessage(), 1, (Throwable)ex);
        }
        if (ex instanceof NonUniqueObjectException) {
            return new DuplicateKeyException(ex.getMessage(), (Throwable)ex);
        }
        if (ex instanceof PropertyValueException) {
            return new DataIntegrityViolationException(ex.getMessage(), (Throwable)ex);
        }
        if (ex instanceof PersistentObjectException) {
            return new InvalidDataAccessApiUsageException(ex.getMessage(), (Throwable)ex);
        }
        if (ex instanceof TransientObjectException) {
            return new InvalidDataAccessApiUsageException(ex.getMessage(), (Throwable)ex);
        }
        if (ex instanceof ObjectDeletedException) {
            return new InvalidDataAccessApiUsageException(ex.getMessage(), (Throwable)ex);
        }
        if (ex instanceof UnresolvableObjectException) {
            return new HibernateObjectRetrievalFailureException((UnresolvableObjectException)ex);
        }
        if (ex instanceof WrongClassException) {
            return new HibernateObjectRetrievalFailureException((WrongClassException)ex);
        }
        if (ex instanceof StaleObjectStateException) {
            return new HibernateOptimisticLockingFailureException((StaleObjectStateException)ex);
        }
        if (ex instanceof StaleStateException) {
            return new HibernateOptimisticLockingFailureException((StaleStateException)ex);
        }
        return new HibernateSystemException(ex);
    }

    public static boolean isDeferredCloseActive(SessionFactory sessionFactory) {
        Assert.notNull((Object)sessionFactory, (String)"No SessionFactory specified");
        Map<SessionFactory, Set<Session>> holderMap = deferredCloseHolder.get();
        return holderMap != null && holderMap.containsKey((Object)sessionFactory);
    }

    public static void initDeferredClose(SessionFactory sessionFactory) {
        Assert.notNull((Object)sessionFactory, (String)"No SessionFactory specified");
        logger.debug((Object)"Initializing deferred close of Hibernate Sessions");
        Map<SessionFactory, Set<Session>> holderMap = deferredCloseHolder.get();
        if (holderMap == null) {
            holderMap = new HashMap<SessionFactory, Set<Session>>();
            deferredCloseHolder.set(holderMap);
        }
        holderMap.put(sessionFactory, new LinkedHashSet(4));
    }

    public static void processDeferredClose(SessionFactory sessionFactory) {
        Assert.notNull((Object)sessionFactory, (String)"No SessionFactory specified");
        Map<SessionFactory, Set<Session>> holderMap = deferredCloseHolder.get();
        if (holderMap == null || !holderMap.containsKey((Object)sessionFactory)) {
            throw new IllegalStateException("Deferred close not active for SessionFactory [" + (Object)sessionFactory + "]");
        }
        logger.debug((Object)"Processing deferred close of Hibernate Sessions");
        Set<Session> sessions = holderMap.remove((Object)sessionFactory);
        for (Session session : sessions) {
            SessionFactoryUtils.closeSession(session);
        }
        if (holderMap.isEmpty()) {
            deferredCloseHolder.remove();
        }
    }

    public static void releaseSession(Session session, SessionFactory sessionFactory) {
        if (session == null) {
            return;
        }
        if (!SessionFactoryUtils.isSessionTransactional(session, sessionFactory)) {
            SessionFactoryUtils.closeSessionOrRegisterDeferredClose(session, sessionFactory);
        }
    }

    static void closeSessionOrRegisterDeferredClose(Session session, SessionFactory sessionFactory) {
        Map<SessionFactory, Set<Session>> holderMap = deferredCloseHolder.get();
        if (holderMap != null && sessionFactory != null && holderMap.containsKey((Object)sessionFactory)) {
            logger.debug((Object)"Registering Hibernate Session for deferred close");
            session.setFlushMode(FlushMode.MANUAL);
            Set<Session> sessions = holderMap.get((Object)sessionFactory);
            sessions.add(session);
        } else {
            SessionFactoryUtils.closeSession(session);
        }
    }

    public static void closeSession(Session session) {
        if (session != null) {
            logger.debug((Object)"Closing Hibernate Session");
            try {
                session.close();
            }
            catch (HibernateException ex) {
                logger.debug((Object)"Could not close Hibernate Session", (Throwable)ex);
            }
            catch (Throwable ex) {
                logger.debug((Object)"Unexpected exception on closing Hibernate Session", ex);
            }
        }
    }
}
