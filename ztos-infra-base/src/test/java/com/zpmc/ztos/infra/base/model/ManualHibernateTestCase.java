package com.zpmc.ztos.infra.base.model;

import com.zpmc.ztos.infra.base.business.interfaces.IDomainQuery;
import com.zpmc.ztos.infra.base.business.interfaces.IMessageCollector;
import com.zpmc.ztos.infra.base.business.interfaces.IPropertyKey;
import com.zpmc.ztos.infra.base.common.callbacks.CarinaPersistenceCallback;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.common.database.PersistenceTemplate;
import com.zpmc.ztos.infra.base.common.exceptions.BizFailure;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.model.*;
import com.zpmc.ztos.infra.base.common.utils.*;
import com.zpmc.ztos.infra.base.utils.CarinaUtils;
import com.zpmc.ztos.infra.base.utils.StringUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.xmlbeans.impl.xb.ltgfmt.TestCase;
import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.annotations.Persister;
import org.hibernate.stat.EntityStatistics;
import org.hibernate.stat.Statistics;

import java.io.Serializable;

public class ManualHibernateTestCase extends BaseTestCase{
    protected Transaction _transaction;
    protected final Persister _persister;
    protected final HibernateApi _hibernateApi;
    protected static Serializable _adminGkey;
    private static UserContext _defaultTestUserContext;
  //  protected final CrudBizDelegate _crudDelegate = new BizPortalBizDelegate();
    private static final Logger LOGGER;

    public ManualHibernateTestCase(String inS) {
        super(inS);
        LOGGER.debug((Object)("ManualHibernateTestCase: getting session factory = " + inS));
        this.getSessionFactory();
        LOGGER.debug((Object)("ManualHibernateTestCase: got session factory = " + inS));
        this._persister = (Persister) Roastery.getBean("persister");
        this._hibernateApi = HibernateApi.getInstance();
        Logger logger = Logger.getLogger(ManualHibernateTestCase.class);
        logger.setLevel(Level.WARN);
        if (_adminGkey == null) {
   //         _adminGkey = SecurityAdministratorDelegate.getUserGkey(UserContextUtils.getSystemUserContext(), "admin");
        }
    }

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        LogUtils.setShowSql(false);
        LogUtils.setLogLevel("org.hibernate", Level.WARN);
        if (this._transaction != null || this._hibernateApi.getCurrentSession() != null) {
            this.endHibernate();
        }
        super.tearDown();
    }

    protected void startHibernateWithUser(UserContext inUserContext) {
        this.endHibernate();
        TransactionParms parms = new TransactionParms();
        parms.setUserContext(inUserContext);
        parms.setResponse(new BizResponse());
        parms.bind();
        this._hibernateApi.createSessionIfNecessary();
        String uid = inUserContext != null ? inUserContext.getUserId() : "null";
        LOGGER.info((Object)("startHibernateWithUser: uid='" + uid + "'"));
    }

    protected void startHibernate() {
        this.startHibernateWithUser(this.getTestUserContext());
    }

    protected void startTransaction() {
        try {
            this._transaction = this._hibernateApi.getCurrentSession().beginTransaction();
        }
        catch (HibernateException ex) {
            throw SessionFactoryUtils.convertHibernateAccessException(ex);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void endTransactionAndSession(boolean inCommitFlag) {
        if (this._transaction == null) {
            ManualHibernateTestCase.fail((String)"endTransaction: Unexpected state transaction is null");
        }
        if (!inCommitFlag) {
            try {
                LOGGER.warn((Object)"endTransaction: About to rollback transaction.");
                this._transaction.rollback();
            }
            catch (HibernateException hiberE) {
                LOGGER.error((Object)("completeTransaction: Unable to commit or rollback transaction" + (Object)((Object)hiberE)), (Throwable)hiberE);
            }
            finally {
                this.cleanupSession();
            }
        } else {
            try {
                this._transaction.commit();
            }
            catch (Exception e) {
                LOGGER.warn((Object)"endTransaction: About to rollback transaction.");
                try {
                    this._transaction.rollback();
                }
                catch (HibernateException hiberE) {
                    LOGGER.error((Object)("completeTransaction: Unable to commit or rollback transaction" + (Object)((Object)hiberE)), (Throwable)hiberE);
                    ManualHibernateTestCase.fail((String)("completeTransaction: Unable to commit or rollback transaction" + (Object)((Object)hiberE)));
                }
            }
            finally {
                this.cleanupSession();
            }
        }
    }

    private void cleanupSession() {
        this._hibernateApi.closeSession(false);
        this._transaction = null;
        TransactionParms.unbindIfBound();
    }

    protected void endTransactionAndSession() {
        this.endTransactionAndSession(true);
    }

    protected void endHibernate() {
        this._hibernateApi.closeSessionIfNecessary(true);
        TransactionParms.unbindIfBound();
    }

    protected void flushHibernate() {
        this._hibernateApi.flush();
    }

    public UserContext getTestUserContext() {
        if (_defaultTestUserContext == null) {
            _defaultTestUserContext = UserContextUtils.createUserContext(_adminGkey, "admin");
        }
        return _defaultTestUserContext;
    }

    public void doCrudDelegateTest(String inEntity, FieldChanges inCreateSpec, FieldChanges inUpdateSpec) {
//        BizResponse response = this._crudDelegate.requestCreate(this.getTestUserContext(), inEntity, null, inCreateSpec);
//        Serializable createdKey = response.getCreatedPrimaryKey();
//        LOGGER.info((Object)("doCrudDelegateTest: createdKey = " + createdKey));
//        this.assertTrueResponseSuccess(response);
//        response = this._crudDelegate.requestUpdate(this.getTestUserContext(), inEntity, createdKey, inUpdateSpec);
//        this.assertTrueResponseSuccess(response);
//        response = this._crudDelegate.requestDelete(this.getTestUserContext(), inEntity, createdKey);
//        this.assertTrueResponseSuccess(response);
    }

    public SessionFactory getSessionFactory() {
        return PersistenceUtils.getSessionFactory();
    }

    protected void assertException(Exception inCaughtException, IPropertyKey inExpectedKey) {
        this.assertException(inCaughtException, inExpectedKey.getKey());
    }

    protected void assertException(Exception inCaughtException, String inExpectedMessage) {
        String hiber = "convertHibernateAccessException";
        if (inCaughtException == null) {
            ManualHibernateTestCase.fail((String)("expected an exception with message: <" + inExpectedMessage + "> but got none"));
        } else {
            Throwable t;
            boolean failedInCallback = false;
            StackTraceElement[] elements = inCaughtException.getStackTrace();
            int n = elements.length;
            for (int i = 0; i < n; ++i) {
                String method = elements[i].getMethodName();
                if (!StringUtils.equals((String)method, (String)"convertHibernateAccessException")) continue;
                failedInCallback = true;
                break;
            }
            String msg = (t = CarinaUtils.unwrap(inCaughtException)) instanceof BizViolation ? ((BizViolation)t).getResourceKey() : (t instanceof BizFailure ? ((BizFailure)t).getResourceKey() : t.getMessage());
            LOGGER.info((Object)("assertException: exception of class: " + t.getClass() + " with message: " + msg));
            ManualHibernateTestCase.assertEquals((String)"did not get the expected exception", (String)inExpectedMessage, (String)msg);
            if (failedInCallback) {
                LOGGER.info((Object)"assertException: Exception occurred within Hibernate.  This Session is Toast! ");
             //   this._hibernateApi.setFlushMode(FlushMode.NEVER);
                this._hibernateApi.setFlushMode(FlushMode.MANUAL);
                this.endHibernate();
            }
        }
    }

    protected void failOnException(String inComment, Exception inCaughtException) {
        TestUtils.failOnException(inComment, inCaughtException, (TestCase) this);
    }

    protected void enableStatistics() {
        this.getSessionFactory().getStatistics().setStatisticsEnabled(true);
    }

    protected void printGlobalStatistics() {
        LOGGER.info((Object)"printGlobalStatistics: ");
        Statistics stats = this.getSessionFactory().getStatistics();
        LOGGER.info((Object)("  stats = " + (Object)stats));
        double queryCacheHitCount = stats.getQueryCacheHitCount();
        double queryCacheMissCount = stats.getQueryCacheMissCount();
        double queryCacheHitRatio = queryCacheHitCount / (queryCacheHitCount + queryCacheMissCount);
        LOGGER.info((Object)("  Query Hit ratio:" + queryCacheHitRatio));
        String[] queries = stats.getQueries();
        for (int i = 0; i < queries.length; ++i) {
            String query = queries[i];
            LOGGER.info((Object)("  query = " + query));
        }
        String[] collectionRoleNames = stats.getCollectionRoleNames();
        for (int i = 0; i < collectionRoleNames.length; ++i) {
            String collectionRoleName = collectionRoleNames[i];
            LOGGER.info((Object)("  collectionRoleName = " + collectionRoleName));
        }
        String[] entityNames = stats.getEntityNames();
        for (int i = 0; i < entityNames.length; ++i) {
            String entityName = entityNames[i];
            LOGGER.info((Object)("  entityName = " + entityName));
        }
    }

    protected void printStatistics(Class inClass) {
        Statistics stats = this.getSessionFactory().getStatistics();
        if (inClass != null) {
            EntityStatistics entityStats = stats.getEntityStatistics(inClass.getName());
            long changes = entityStats.getInsertCount() + entityStats.getUpdateCount() + entityStats.getDeleteCount();
            LOGGER.info((Object)(inClass.getName() + " changed " + changes + "times"));
        }
    }

    protected int findCount(UserContext inContext, final IDomainQuery inDq) {
        final int[] counts = new int[1];
        PersistenceTemplate template = new PersistenceTemplate(inContext);
        IMessageCollector mc = template.invoke(new CarinaPersistenceCallback(){

            @Override
            public void doInTransaction() {
                counts[0] = ManualHibernateTestCase.this._hibernateApi.findCountByDomainQuery(inDq);
            }
        });
        this.assertTrueResponseSuccess(mc);
        return counts[0];
    }

    static {
        LOGGER = Logger.getLogger(ManualHibernateTestCase.class);
    }

}
