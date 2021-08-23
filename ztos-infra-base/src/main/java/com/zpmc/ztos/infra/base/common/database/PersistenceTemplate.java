package com.zpmc.ztos.infra.base.common.database;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.business.enums.framework.FrameworkDiagnosticEventTypesEnum;
import com.zpmc.ztos.infra.base.business.enums.framework.MessageLevelEnum;
import com.zpmc.ztos.infra.base.business.interfaces.IDiagnosticEvent;
import com.zpmc.ztos.infra.base.business.interfaces.IMessageCollector;
import com.zpmc.ztos.infra.base.common.callbacks.CarinaPersistenceCallback;
import com.zpmc.ztos.infra.base.common.exceptions.SevereErrorsInResponseException;
import com.zpmc.ztos.infra.base.common.messages.MessageCollectorFactory;
import com.zpmc.ztos.infra.base.common.model.TransactionParms;
import com.zpmc.ztos.infra.base.common.model.UserContext;
import com.zpmc.ztos.infra.base.common.utils.DiagnosticUtils;
import com.zpmc.ztos.infra.base.common.utils.HibernateStatisticsUtil;
import com.zpmc.ztos.infra.base.common.utils.PersistenceUtils;
import org.apache.log4j.Logger;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.sql.Connection;


public class PersistenceTemplate {

    private Object _result;
    boolean _parmsAlreadyBound;
    SessionHolder _originalSessionHolder;
    IMessageCollector _originalMessageCollector;
    private final UserContext _userContext;
    private boolean _rollbackOnError = true;
    private static final Logger LOGGER = Logger.getLogger(PersistenceTemplate.class);

    public PersistenceTemplate(UserContext inUserContext) {
        this._userContext = inUserContext;
    }

    public void setRollbackOnSevereErrors(boolean inFlag) {
        this._rollbackOnError = inFlag;
    }

    private IMessageCollector setUpTransactionParms() {
        IMessageCollector messageCollector = MessageCollectorFactory.createMessageCollector();
        this._parmsAlreadyBound = TransactionParms.isBound();
        if (this._parmsAlreadyBound) {
            TransactionParms transactionParms = TransactionParms.getBoundParms();
            this._originalMessageCollector = transactionParms.getMessageCollector();
            transactionParms.setMessageCollector(messageCollector);
        } else {
            TransactionParms transactionParms = new TransactionParms();
            transactionParms.setMessageCollector(messageCollector);
            transactionParms.setUserContext(this._userContext);
            transactionParms.bind();
        }
        return messageCollector;
    }

    private Session getNewSession() {
        Session newSession;
        HibernateApi hibernateApi = HibernateApi.getInstance();
        Session currentSession = hibernateApi.getCurrentSession();
        if (currentSession != null) {
//            Connection connection = currentSession.connection();
            Connection connection = currentSession.disconnect();
            this._originalSessionHolder = hibernateApi.unbindSessionHolder();
            SessionFactory sessionFactory = PersistenceUtils.getSessionFactory();
//            Session secondSession = sessionFactory.openSession(connection, hibernateApi.getEntityInterceptor());
            Session secondSession = sessionFactory.openSession();
            SessionHolder sessionHolder = new SessionHolder(secondSession);
            TransactionSynchronizationManager.bindResource((Object)sessionFactory, (Object)((Object)sessionHolder));
            newSession = secondSession;
        } else {
            newSession = hibernateApi.forceCreateNewSession();
        }
        newSession.setFlushMode(FlushMode.COMMIT);
        return newSession;
    }

    protected IDiagnosticEvent recordHiearchicalDiagnosticIfEnabled(CarinaPersistenceCallback inCallback) {
        IDiagnosticEvent event = DiagnosticUtils.recordHiearchicalDiagnosticIfEnabled(FrameworkDiagnosticEventTypesEnum.PERSISTENCE_TEMPLATE.name());
        if (event != null) {
            try {
                event.setDescription(inCallback.getClass().getName());
                event.appendStackTrace("recordHiearchicalDiagnosticIfEnabled");
            }
            catch (Throwable t) {
                LOGGER.error((Object)("Trouble getting method name " + t));
            }
        }
        return event;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NotNull
    public IMessageCollector invoke(CarinaPersistenceCallback inCallback) {
        IMessageCollector messageCollector = null;
        HibernateApi hibernateApi = HibernateApi.getInstance();
        Transaction transaction = null;
        IDiagnosticEvent event = this.recordHiearchicalDiagnosticIfEnabled(inCallback);
        try {
            messageCollector = this.setUpTransactionParms();
            Session session = this.getNewSession();
            transaction = session.beginTransaction();
            inCallback.doInTransaction();
            if (messageCollector.containsMessageLevel(MessageLevelEnum.SEVERE) && this._rollbackOnError) {
                throw new SevereErrorsInResponseException();
            }
            if (this._originalSessionHolder != null) {
                HibernateStatisticsUtil.writeStatsForBigSessions();
            }
            transaction.commit();
        }
        catch (Throwable t) {
            if (messageCollector == null) {
                messageCollector = MessageCollectorFactory.createMessageCollector();
            }
            PersistenceUtils.handleRollback(this.preprocessThrownError(t), transaction, messageCollector);
        }
        finally {
            try {
                if (!this._parmsAlreadyBound) {
                    TransactionParms.unbind();
                } else {
                    TransactionParms.getBoundParms().setMessageCollector(this._originalMessageCollector);
                }
                hibernateApi.closeSession(false);
                if (this._originalSessionHolder != null) {
                    hibernateApi.bindSessionHolder(this._originalSessionHolder);
                }
                if (event != null) {
                    DiagnosticUtils.closeLastEventIfNeeded();
                }
                if (inCallback.getResult() != null && this.getResult() == null) {
                    this.setResult(inCallback.getResult());
                }
            }
            catch (Throwable e) {
                messageCollector.registerExceptions(e);
            }
        }
        return messageCollector;
    }

    protected Throwable preprocessThrownError(@NotNull Throwable inT) {
        return inT;
    }

    public Object getResult() {
        return this._result;
    }

    public void setResult(Object inResult) {
        this._result = inResult;
    }

}
