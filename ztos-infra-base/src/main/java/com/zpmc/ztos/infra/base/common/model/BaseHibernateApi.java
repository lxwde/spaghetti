package com.zpmc.ztos.infra.base.common.model;

import com.zpmc.ztos.infra.base.business.interfaces.IDynamicHibernatingEntity;
import com.zpmc.ztos.infra.base.business.interfaces.IEntity;
import com.zpmc.ztos.infra.base.business.interfaces.IFrameworkPropertyKeys;
import com.zpmc.ztos.infra.base.business.model.BaseConfigurationProperties;
import com.zpmc.ztos.infra.base.common.exceptions.BizFailure;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.utils.LogUtils;
import com.zpmc.ztos.infra.base.common.utils.SessionFactoryUtils;
import com.zpmc.ztos.infra.base.utils.CarinaUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.hibernate.*;

import org.hibernate.type.Type;
import com.sun.istack.Nullable;
import org.hibernate.util.ArrayHelper;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.io.Serializable;
import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public abstract class BaseHibernateApi {

    private String _sessionScopedInterceptorId;
    private static final String CONFIGURATION_PROPERTIES = "ConfigurationProperties";
    private SessionFactory _sessionFactory;
    private static final Logger LOGGER = Logger.getLogger(BaseHibernateApi.class);
    private static final Logger DEV_LOGGER = Logger.getLogger((String)("dev." + LOGGER.getName()));
    private BaseConfigurationProperties _baseConfig;

    public void flush() {
        Session session = SessionFactoryUtils.getSession(this.getSessionFactory(), false);
        try {
            session.flush();
        }
        catch (HibernateException ex) {
            throw this.convertHibernateException(ex);
        }
    }

    public void setFlushMode(FlushMode inFlushMode) {
        Session session = SessionFactoryUtils.getSession(this.getSessionFactory(), false);
        session.setFlushMode(inFlushMode);
    }

    public FlushMode getFlushMode() {
        Session session = SessionFactoryUtils.getSession(this.getSessionFactory(), false);
        return session.getFlushMode();
    }

    public Connection disconnect() {
        Session session = SessionFactoryUtils.getSession(this.getSessionFactory(), false);
        try {
            return session.disconnect();
        }
        catch (HibernateException ex) {
            throw this.convertHibernateException(ex);
        }
    }

    public void reconnect() {
        Session session = SessionFactoryUtils.getSession(this.getSessionFactory(), false);
        try {
            session.reconnect();
        }
        catch (HibernateException ex) {
            throw this.convertHibernateException(ex);
        }
    }

    public void reconnect(Connection inConnection) {
        Session session = SessionFactoryUtils.getSession(this.getSessionFactory(), false);
        try {
            session.reconnect(inConnection);
        }
        catch (HibernateException ex) {
            throw this.convertHibernateException(ex);
        }
    }

    public Connection close() {
        Session session = SessionFactoryUtils.getSession(this.getSessionFactory(), false);
        try {
            //return session.close();
            return session.disconnect();
        }
        catch (HibernateException ex) {
            throw this.convertHibernateException(ex);
        }
    }

    public boolean isOpen() {
        Session session = SessionFactoryUtils.getSession(this.getSessionFactory(), false);
        return session.isOpen();
    }

    public boolean isConnected() {
        Session session = SessionFactoryUtils.getSession(this.getSessionFactory(), false);
        return session.isConnected();
    }

    public Serializable getIdentifier(Object inObject) {
        Session session = SessionFactoryUtils.getSession(this.getSessionFactory(), false);
        try {
            return session.getIdentifier(inObject);
        }
        catch (HibernateException ex) {
            throw this.convertHibernateException(ex);
        }
    }

    public boolean contains(Object inObject) {
        Session session = SessionFactoryUtils.getSession(this.getSessionFactory(), false);
        return session.contains(inObject);
    }

    public void evict(Object inObject) {
        Session session = SessionFactoryUtils.getSession(this.getSessionFactory(), false);
        try {
            session.evict(inObject);
        }
        catch (HibernateException ex) {
            throw this.convertHibernateException(ex);
        }
    }

    public Object load(Class inClass, Serializable inSerializable, LockMode inLockMode) {
        Session session = SessionFactoryUtils.getSession(this.getSessionFactory(), false);
        try {
            return session.load(inClass, inSerializable, inLockMode);
        }
        catch (HibernateException ex) {
            throw this.convertHibernateException(ex);
        }
    }

    public Object load(Class inClass, Serializable inPrimaryKey) {
        if (inPrimaryKey instanceof String) {
            if (StringUtils.isEmpty((String)((String)((Object)inPrimaryKey)))) {
                inPrimaryKey = null;
            } else if (DatabaseEntity.getPrimaryKeyClass(inClass) == Long.class) {
                try {
                    inPrimaryKey = new Long((String)((Object)inPrimaryKey));
                }
                catch (NumberFormatException e) {
                    throw BizFailure.create(IFrameworkPropertyKeys.CRUD__INVALID_FIELD_VALUE, e, inPrimaryKey);
                }
            }
        }
        if (inPrimaryKey == null) {
            return null;
        }
        Session session = SessionFactoryUtils.getSession(this.getSessionFactory(), false);
        try {
            return session.load(inClass, inPrimaryKey);
        }
        catch (HibernateException ex) {
            throw this.convertHibernateException(ex);
        }
    }

    public void load(Object inObject, Serializable inSerializable) {
        Session session = SessionFactoryUtils.getSession(this.getSessionFactory(), false);
        try {
            session.load(inObject, inSerializable);
        }
        catch (HibernateException ex) {
            throw this.convertHibernateException(ex);
        }
    }

    public Object load(String inEntityName, Serializable inPrimaryKey) {
        if (inPrimaryKey == null) {
            return null;
        }
        Session session = SessionFactoryUtils.getSession(this.getSessionFactory(), false);
        try {
            Object o = session.load(inEntityName, inPrimaryKey);
            if (o instanceof IDynamicHibernatingEntity) {
                ((IDynamicHibernatingEntity)o).setEntityName(inEntityName);
            }
            return o;
        }
        catch (HibernateException ex) {
            throw this.convertHibernateException(ex);
        }
    }

    public Object get(Class inClass, Serializable inSerializable) {
        Session session = SessionFactoryUtils.getSession(this.getSessionFactory(), false);
        try {
            return session.get(inClass, inSerializable);
        }
        catch (HibernateException ex) {
            throw this.convertHibernateException(ex);
        }
    }

    public Serializable save(Object inObject) {
        Session session = SessionFactoryUtils.getSession(this.getSessionFactory(), false);
        try {
            if (session.contains(inObject)) {
                BaseHibernateApi.logUnnecessaryCall();
                return ((IEntity)inObject).getPrimaryKey();
            }
            if (inObject instanceof IDynamicHibernatingEntity) {
                String entityName = ((IDynamicHibernatingEntity)inObject).getEntityName();
                if (StringUtils.isEmpty((String)entityName)) {
                    throw BizFailure.create("IEntity name was not set for object(" + inObject + "which cannot be saved. Please make sure you have set the object type with IDynamicHibernatingEntity.setEntityName.This can be a framework bug or an end customer integration bug depending on context.");
                }
                return session.save(entityName, inObject);
            }
            return session.save(inObject);
        }
        catch (HibernateException ex) {
            throw this.convertHibernateException(ex);
        }
    }

    public void save(Object inObject, Serializable inSerializable) {
        Session session = SessionFactoryUtils.getSession(this.getSessionFactory(), false);
        try {
            //session.save(inObject, inSerializable);
            session.save((String) inObject, inSerializable);
        }
        catch (HibernateException ex) {
            throw this.convertHibernateException(ex);
        }
    }

    @Deprecated
    public void saveOrUpdate(Object inObject) {
        Session session = SessionFactoryUtils.getSession(this.getSessionFactory(), false);
        try {
            if (session.contains(inObject)) {
                BaseHibernateApi.logUnnecessaryCall();
                return;
            }
            session.saveOrUpdate(inObject);
        }
        catch (HibernateException ex) {
            throw this.convertHibernateException(ex);
        }
    }

    @Deprecated
    public Object saveOrUpdateCopy(Object inObject, Serializable inSerializable) {
        Session session = SessionFactoryUtils.getSession(this.getSessionFactory(), false);
        try {
            //return session.saveOrUpdateCopy(inObject, inSerializable);
            session.saveOrUpdate((String) inObject, inSerializable);
            return session.get((String) inObject, inSerializable);
        }
        catch (HibernateException ex) {
            throw this.convertHibernateException(ex);
        }
    }

    @Deprecated
    public void update(Object inObject) {
        Session session = SessionFactoryUtils.getSession(this.getSessionFactory(), false);
        try {
            if (session.contains(inObject)) {
                BaseHibernateApi.logUnnecessaryCall();
                return;
            }
            session.update(inObject);
        }
        catch (HibernateException ex) {
            throw this.convertHibernateException(ex);
        }
    }

    @Deprecated
    public void update(Object inObject, Serializable inSerializable) {
        Session session = SessionFactoryUtils.getSession(this.getSessionFactory(), false);
        try {
            session.update(inObject);
        }
        catch (HibernateException ex) {
            throw this.convertHibernateException(ex);
        }
    }

    public List find(String inString) {
        Session session = SessionFactoryUtils.getSession(this.getSessionFactory(), false);
        try {
           // return session.find(inString);
            return null;
        }
        catch (HibernateException ex) {
            throw this.convertHibernateException(ex);
        }
    }

    public List find(String inString, Object inObject, Type inType) {
        Session session = SessionFactoryUtils.getSession(this.getSessionFactory(), false);
        try {
           // return session.find(inString, inObject, inType);
            return null;
        }
        catch (HibernateException ex) {
            throw this.convertHibernateException(ex);
        }
    }

    public List find(String inString, Object[] inObjects, Type[] inTypes) {
        Session session = SessionFactoryUtils.getSession(this.getSessionFactory(), false);
        try {
          //  return session.find(inString, inObjects, inTypes);
            return null;
        }
        catch (HibernateException ex) {
            throw this.convertHibernateException(ex);
        }
    }

    public Iterator iterate(String inString) {
        Session session = SessionFactoryUtils.getSession(this.getSessionFactory(), false);
        try {
        //    return session.iterate(inString);
            return null;
        }
        catch (HibernateException ex) {
            throw this.convertHibernateException(ex);
        }
    }

    public Iterator iterate(String inString, Object inObject, Type inType) {
        Session session = SessionFactoryUtils.getSession(this.getSessionFactory(), false);
        try {
        //    return session.iterate(inString, inObject, inType);
            return null;
        }
        catch (HibernateException ex) {
            throw this.convertHibernateException(ex);
        }
    }

    public Iterator iterate(String inString, Object[] inObjects, Type[] inTypes) {
        Session session = SessionFactoryUtils.getSession(this.getSessionFactory(), false);
        try {
          //  return session.iterate(inString, inObjects, inTypes);
            return null;
        }
        catch (HibernateException ex) {
            throw this.convertHibernateException(ex);
        }
    }

    public Collection filter(Object inObject, String inString) {
        Session session = SessionFactoryUtils.getSession(this.getSessionFactory(), false);
        try {
         //   return session.filter(inObject, inString);
            return null;
        }
        catch (HibernateException ex) {
            throw this.convertHibernateException(ex);
        }
    }

    public Collection filter(Object inObject, String inString, Object inObject2, Type inType) {
        Session session = SessionFactoryUtils.getSession(this.getSessionFactory(), false);
        try {
         //   return session.filter(inObject, inString, inObject2, inType);
            return null;
        }
        catch (HibernateException ex) {
            throw this.convertHibernateException(ex);
        }
    }

    public Collection filter(Object inObject, String inString, Object[] inObjects, Type[] inTypes) {
        Session session = SessionFactoryUtils.getSession(this.getSessionFactory(), false);
        try {
        //    return session.filter(inObject, inString, inObjects, inTypes);
            return null;
        }
        catch (HibernateException ex) {
            throw this.convertHibernateException(ex);
        }
    }

    public int delete(String inString) {
        return this.delete(inString, ArrayHelper.EMPTY_OBJECT_ARRAY, ArrayHelper.EMPTY_TYPE_ARRAY);
    }

    public int delete(String inString, Object inObject, Type inType) {
        return this.delete(inString, new Object[]{inObject}, new Type[]{inType});
    }

    public int delete(String inString, Object[] inObjects, Type[] inTypes) {
        Session session = SessionFactoryUtils.getSession(this.getSessionFactory(), false);
        try {
            //return session.delete(inString, inObjects, inTypes);
            session.delete(inString, inObjects);
            return 1;
        }
        catch (HibernateException ex) {
            throw this.convertHibernateException(ex);
        }
    }

    public void lock(Object inObject, LockMode inLockMode) {
        Session session = SessionFactoryUtils.getSession(this.getSessionFactory(), false);
        try {
            session.lock(inObject, inLockMode);
        }
        catch (HibernateException ex) {
            throw this.convertHibernateException(ex);
        }
    }

    public void refresh(Object inObject) {
        Session session = SessionFactoryUtils.getSession(this.getSessionFactory(), false);
        try {
            session.refresh(inObject);
        }
        catch (HibernateException ex) {
            throw this.convertHibernateException(ex);
        }
    }

    public void refresh(Object inObject, LockMode inLockMode) {
        Session session = SessionFactoryUtils.getSession(this.getSessionFactory(), false);
        try {
            session.refresh(inObject, inLockMode);
        }
        catch (HibernateException ex) {
            throw this.convertHibernateException(ex);
        }
    }

    public LockMode getCurrentLockMode(Object inObject) {
        Session session = SessionFactoryUtils.getSession(this.getSessionFactory(), false);
        try {
            return session.getCurrentLockMode(inObject);
        }
        catch (HibernateException ex) {
            throw this.convertHibernateException(ex);
        }
    }

    public Transaction beginTransaction() {
        Session session = SessionFactoryUtils.getSession(this.getSessionFactory(), false);
        try {
            return session.beginTransaction();
        }
        catch (HibernateException ex) {
            throw this.convertHibernateException(ex);
        }
    }

    public Criteria createCriteria(Class inClass) {
        Session session = SessionFactoryUtils.getSession(this.getSessionFactory(), false);
        return session.createCriteria(inClass);
    }

    public Query createQuery(String inString) {
        Session session = SessionFactoryUtils.getSession(this.getSessionFactory(), false);
        try {
            return session.createQuery(inString);
        }
        catch (HibernateException ex) {
            throw this.convertHibernateException(ex);
        }
    }

    public Query createFilter(Object inObject, String inString) {
        Session session = SessionFactoryUtils.getSession(this.getSessionFactory(), false);
        try {
            return session.createFilter(inObject, inString);
        }
        catch (HibernateException ex) {
            throw this.convertHibernateException(ex);
        }
    }

    public Query getNamedQuery(String inString) {
        Session session = SessionFactoryUtils.getSession(this.getSessionFactory(), false);
        try {
            return session.getNamedQuery(inString);
        }
        catch (HibernateException ex) {
            throw this.convertHibernateException(ex);
        }
    }

    @Nullable
    public Session getCurrentSession() {
        Session session = null;
        if (TransactionSynchronizationManager.hasResource((Object)this.getSessionFactory())) {
            session = SessionFactoryUtils.getSession(this.getSessionFactory(), false);
        }
        return session;
    }

    @Nullable
    public Interceptor getEntityInterceptor() {
        if (this._sessionScopedInterceptorId == null) {
            return null;
        }
        return (Interceptor)Roastery.getBean(this._sessionScopedInterceptorId);
    }

    private Session createAndBindNewSession() {
        SessionFactory sessionFactory = this.getSessionFactory();
        LOGGER.info((Object)"Trying to creating new session");
        Session outSession = SessionFactoryUtils.getNewSession(sessionFactory, this.getEntityInterceptor());
        LOGGER.info((Object)("Created new session: " + (Object)outSession));
        if (!TransactionSynchronizationManager.hasResource((Object)sessionFactory)) {
            SessionHolder sessionHolder = new SessionHolder(outSession);
            TransactionSynchronizationManager.bindResource((Object)sessionFactory, (Object)((Object)sessionHolder));
        }
        return outSession;
    }

    public boolean createSessionIfNecessary() {
        boolean outCreated = false;
        if (this.getCurrentSession() == null) {
            this.createAndBindNewSession();
            outCreated = true;
        }
        return outCreated;
    }

    public Session forceCreateNewSession() {
        if (this.getCurrentSession() != null) {
            BizFailure ex = BizFailure.create("");
            LOGGER.error((Object)("Expected no prior session, force closing with flush!" + ex.getStackTraceString()));
            this.closeSession(true);
        }
        return this.createAndBindNewSession();
    }

    public SessionHolder unbindSessionHolder() {
        SessionFactory sessionFactory = this.getSessionFactory();
        SessionHolder holder = (SessionHolder)((Object) TransactionSynchronizationManager.getResource((Object)sessionFactory));
        TransactionSynchronizationManager.unbindResource((Object)sessionFactory);
        return holder;
    }

    public void bindSessionHolder(SessionHolder inHolder) {
        SessionFactory sessionFactory = this.getSessionFactory();
        TransactionSynchronizationManager.bindResource((Object)sessionFactory, (Object)((Object)inHolder));
    }

    public void closeSession(boolean inFlush) {
        Session currentSession = this.getCurrentSession();
        if (currentSession != null) {
            try {
                if (inFlush && currentSession.getFlushMode() != FlushMode.NEVER)
              //  if (inFlush && currentSession.getHibernateFlushMode() != FlushMode.MANUAL)
                {
                    currentSession.flush();
                }
            }
            catch (HibernateException ex) {
                LOGGER.error((Object)ex);
                throw this.convertHibernateException(ex);
            }
            finally {
                LOGGER.info((Object)"Removing current session from the current thread.");
                TransactionSynchronizationManager.unbindResource((Object)this.getSessionFactory());
                currentSession.close();
            }
        }
    }

    public void closeSessionIfNecessary(boolean inKillSession) {
        Session currentSession = this.getCurrentSession();
        if (currentSession != null) {
            try {
                if (currentSession.getFlushMode() != FlushMode.NEVER)
                {
                    currentSession.flush();
                }
            }
            catch (HibernateException ex) {
                LOGGER.error((Object)ex);
                throw this.convertHibernateException(ex);
            }
            finally {
                if (inKillSession) {
                    TransactionSynchronizationManager.unbindResource((Object)this.getSessionFactory());
                    SessionFactoryUtils.releaseSession(currentSession, this.getSessionFactory());
                }
            }
        }
    }

    protected RuntimeException convertHibernateException(HibernateException inEx) {
        Throwable cause = inEx.getCause();
        if (cause instanceof BizViolation) {
            BizFailure result = BizFailure.wrap(cause);
            LOGGER.debug((Object)result.getStackTraceString());
            return result;
        }
        LOGGER.error((Object) CarinaUtils.getCompactStackTrace(inEx));
 //       return PersistenceUtils.convertHibernateAccessException(inEx);
        return null;
    }

    public SessionFactory getSessionFactory() {
        return this._sessionFactory;
    }

    public void setSessionFactory(SessionFactory inSessionFactory) {
        this._sessionFactory = inSessionFactory;
    }

    public void setBaseConfig(BaseConfigurationProperties inBaseConfig) {
        this._baseConfig = inBaseConfig;
    }

    protected BaseConfigurationProperties getBaseConfig() {
        if (this._baseConfig == null) {
            this._baseConfig = (BaseConfigurationProperties)Roastery.getBean(CONFIGURATION_PROPERTIES);
        }
        return this._baseConfig;
    }

    public void setSessionScopedInterceptorId(String inSessionScopedInterceptorId) {
        this._sessionScopedInterceptorId = inSessionScopedInterceptorId;
    }

    private static void logUnnecessaryCall() {
        if (LogUtils.isLoggerEnabledFor(DEV_LOGGER, (Priority) Level.ERROR) && Boolean.valueOf(System.getProperty("com.navis.persistence.logging.saveUpdate")).booleanValue()) {
            DEV_LOGGER.error((Object)("Detected unnecessary call for already attached entity. Please see stacktrace below: \n" + CarinaUtils.getCurrentFormattedThreadStackTrace()));
        }
    }
}
