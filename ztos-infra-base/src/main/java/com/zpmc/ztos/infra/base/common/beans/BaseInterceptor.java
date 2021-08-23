package com.zpmc.ztos.infra.base.common.beans;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import com.zpmc.ztos.infra.base.business.enums.extension.EntityLifecycleInterceptionPointEnum;
import com.zpmc.ztos.infra.base.business.enums.extension.ExtensionResponseStatusEnum;
import com.zpmc.ztos.infra.base.business.enums.framework.FrameworkDiagnosticEventTypesEnum;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.common.contexts.PortalApplicationContext;
import com.zpmc.ztos.infra.base.common.database.HiberCache;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.common.database.SqlPerformanceInterceptor;
import com.zpmc.ztos.infra.base.common.exceptions.BizFailure;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.messages.MessageCollectorFactory;
import com.zpmc.ztos.infra.base.common.model.*;
import com.zpmc.ztos.infra.base.common.utils.DiagnosticUtils;
import com.zpmc.ztos.infra.base.common.utils.TimeUtils;
import com.zpmc.ztos.infra.base.utils.CarinaUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.*;
import org.hibernate.type.Type;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

public class BaseInterceptor extends SqlPerformanceInterceptor {
    public static final String BEAN_ID = "auditInterceptor";
    private static final String CREATOR = "Creator";
    private static final String CREATED = "Created";
    private static final String CHANGER = "Changer";
    private static final String CHANGED = "Changed";
    private static final Logger LOGGER = Logger.getLogger(BaseInterceptor.class);
    private int _updates;
    private int _creates;
    private IInterceptionListener _interceptionListener;

    public void afterTransactionBegin(Transaction inTransaction) {
        super.afterTransactionBegin(inTransaction);
        try {
            IBusinessContextStatsFacade facade = (IBusinessContextStatsFacade) Roastery.getBean("businessContextStatsBean");
            if (facade.isAvailable()) {
                facade.injectEndToEndMetrics();
            }
        }
        catch (Throwable t) {
            LOGGER.error((Object)("The system was not able to set performance user stats. This will not impact functionality in any way but you will not beable to collect database level statistics. This error was due to:" + t));
        }
    }

    public void beforeTransactionCompletion(Transaction inTransaction) {
        super.beforeTransactionCompletion(inTransaction);
    }

    public void afterTransactionCompletion(Transaction inTransaction) {
        try {
            IBusinessContextStatsFacade facade = (IBusinessContextStatsFacade)Roastery.getBean("businessContextStatsBean");
            if (facade.isAvailable()) {
                facade.clearBusinessContextStats();
            }
        }
        catch (Throwable t) {
            LOGGER.error((Object)("Error clearing user stats map:" + t));
        }
        super.afterTransactionCompletion(inTransaction);
    }

    public boolean onFlushDirty(Object inEntityObject, Serializable inPrimaryKey, Object[] inFieldValues, Object[] inPriorFieldValues, String[] inFieldIds, Type[] inTypes) throws CallbackException {
        boolean fieldsBeingChanged = false;
        IEntity inEntity = (IEntity)inEntityObject;
        ++this._updates;
        String prefix = HiberCache.getEntityAliasUsedForAuditFields(inEntity.getClass());
        String changed = prefix + CHANGED;
        String changer = prefix + CHANGER;
        for (int i = 0; i < inFieldIds.length; ++i) {
            if (StringUtils.equals((String)inFieldIds[i], (String)changed)) {
                inFieldValues[i] = TimeUtils.getCurrentTime();
                fieldsBeingChanged = true;
                continue;
            }
            if (!StringUtils.equals((String)inFieldIds[i], (String)changer)) continue;
            inFieldValues[i] = this.getUserId();
            fieldsBeingChanged = true;
        }
        FieldChanges changes = BaseInterceptor.calculateFieldChanges(inEntity, inFieldIds, inPriorFieldValues, inFieldValues);
        if (changes.getFieldChangeCount() > 0) {
            EntityInterceptor entityIntercept = this.getEntityInterceptor(2, inEntity, changes, inFieldValues, inFieldIds);
            Exception violationChain = entityIntercept.execute();
            fieldsBeingChanged |= entityIntercept.isFieldsBeingChanged();
            if (violationChain != null) {
                this.processViolation(violationChain, "onFlushDirty: Validation");
            }
            this.doAudit(2, inEntity, changes);
        }
        return fieldsBeingChanged;
    }

    public boolean onSave(Object inEntityObject, Serializable inPrimaryKey, Object[] inFieldValues, String[] inFieldIds, Type[] inTypes) throws CallbackException {
        boolean fieldsBeingChanged = false;
        IEntity inEntity = (IEntity)inEntityObject;
        ++this._creates;
        String prefix = HiberCache.getEntityAliasUsedForAuditFields(inEntity.getClass());
        String created = prefix + CREATED;
        String creator = prefix + CREATOR;
        for (int i = 0; i < inFieldIds.length; ++i) {
            if (StringUtils.equals((String)inFieldIds[i], (String)created)) {
                inFieldValues[i] = TimeUtils.getCurrentTime();
                fieldsBeingChanged = true;
                continue;
            }
            if (!StringUtils.equals((String)inFieldIds[i], (String)creator)) continue;
            inFieldValues[i] = this.getUserId();
            fieldsBeingChanged = true;
        }
        FieldChanges changes = BaseInterceptor.calculateFieldChanges(inEntity, inFieldIds, null, inFieldValues);
        EntityInterceptor updater = this.getEntityInterceptor(1, inEntity, changes, inFieldValues, inFieldIds);
        fieldsBeingChanged |= updater.isFieldsBeingChanged();
        Exception violationChain = updater.execute();
        if (violationChain != null) {
            this.processViolation(violationChain, "onSave");
        }
        this.doAudit(1, inEntity, changes);
        return fieldsBeingChanged;
    }

    public void onDelete(Object inEntityObject, Serializable inPrimaryKey, Object[] inFieldValues, String[] inFieldIds, Type[] inTypes) throws CallbackException {
        IEntity inEntity = (IEntity)inEntityObject;
        FieldChanges fieldChanges = new FieldChanges();
        EntityInterceptor deleteInterceptor = this.getEntityInterceptor(3, inEntity, fieldChanges, inFieldValues, inFieldIds);
        Exception violationChain = deleteInterceptor.execute();
        if (violationChain != null) {
            this.processViolation(violationChain, "onDelete");
        }
        this.doAudit(3, inEntity, fieldChanges);
    }

    public void preFlush(Iterator inIterator) throws CallbackException {
        this._updates = 0;
        this._creates = 0;
    }

    public void postFlush(Iterator inIterator) throws CallbackException {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info((Object)("postFlush: Creations: " + this._creates + ", Updates: " + this._updates));
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NotNull
    public static FieldChanges calculateFieldChanges(Object inEntity, String[] inFieldIds, Object[] inPriorFieldValues, Object[] inFieldValues) {
        if (inFieldIds == null || inFieldValues == null) {
            throw new IllegalArgumentException("All three arrays passed to calculate a delta-set must be non-null");
        }
        String prefix = HiberCache.getEntityAliasUsedForAuditFields(inEntity.getClass());
        String created = prefix + CREATED;
        String creator = prefix + CREATOR;
        String changed = prefix + CHANGED;
        String changer = prefix + CHANGER;
        FieldChanges fieldChanges = new FieldChanges();
        try {
            for (int i = 0; i < inFieldIds.length; ++i) {
                boolean isCurrentNull;
                String fieldId = inFieldIds[i];
                if (StringUtils.equals((String)fieldId, (String)changed) || StringUtils.equals((String)fieldId, (String)changer) || StringUtils.equals((String)fieldId, (String)created) || StringUtils.equals((String)fieldId, (String)creator)) continue;
                Object priorValue = inPriorFieldValues == null ? null : inPriorFieldValues[i];
                Object newValue = inFieldValues[i];
                boolean wasPreviousNull = priorValue == null;
                boolean bl = isCurrentNull = newValue == null;
                if (wasPreviousNull && isCurrentNull) continue;
                Class propertyType = null;
                if (!isCurrentNull) {
                    propertyType = newValue instanceof IEntity ? IEntity.class : newValue.getClass();
                } else if (!wasPreviousNull) {
                    propertyType = priorValue instanceof IEntity ? IEntity.class : priorValue.getClass();
                }
                if (propertyType == null) continue;
                boolean priorValueInitialized = Hibernate.isInitialized((Object)priorValue);
                boolean newValueInitialized = Hibernate.isInitialized((Object)newValue);
                if (priorValueInitialized && newValueInitialized) {
                    FieldChange fieldChange = null;
                    if (IEntity.class.isAssignableFrom(propertyType)) {
                        LOGGER.debug((Object)"Encountered property is an association type");
                        Serializable oldId = null;
                        Serializable newId = null;
                        if (priorValue != null) {
                            oldId = ((IEntity)priorValue).getPrimaryKey();
                        }
                        if (newValue != null) {
                            newId = ((IEntity)newValue).getPrimaryKey();
                        }
                        if (!BaseInterceptor.areEqual(oldId, newId)) {
                            fieldChange = new FieldChange(fieldId, priorValue, newValue);
                        }
                    } else if (Collection.class.isAssignableFrom(propertyType)) {
                        LOGGER.debug((Object)"Encountered property is a collection type");
                    } else if (propertyType.isArray()) {
                        if (propertyType.isAssignableFrom(byte[].class)) {
                            if (!ArrayUtils.isEquals((Object)priorValue, (Object)newValue)) {
                                fieldChange = new FieldChange(fieldId, priorValue, newValue);
                            }
                        } else {
                            LOGGER.debug((Object)("Encountered unsupported array type for " + fieldId));
                        }
                    } else {
                        LOGGER.debug((Object)"Property was a simple property");
                        if (!BaseInterceptor.areEqual(priorValue, newValue)) {
                            fieldChange = new FieldChange(fieldId, priorValue, newValue);
                        }
                    }
                    if (fieldChange == null) continue;
                    fieldChanges.setFieldChange(fieldChange);
                    continue;
                }
                if (!LOGGER.isDebugEnabled()) continue;
                LOGGER.debug((Object)(fieldId + " was not added to the field changes because the new or old values are uninitialized proxies: "));
                if (!priorValueInitialized) {
                    LOGGER.debug((Object)"--Prior value is a proxy.");
                }
                if (newValueInitialized) continue;
                LOGGER.debug((Object)"--New value is a proxy.");
            }
        }
        catch (Throwable t) {
            LOGGER.error((Object)"Error determining delta-set", t);
        }
        finally {
            LOGGER.debug((Object)"Done delta-set determination");
        }
        if (LOGGER.isDebugEnabled()) {
            StringBuilder strBuf = new StringBuilder();
            Iterator<FieldChange> iterator = fieldChanges.getIterator();
            while (iterator.hasNext()) {
                FieldChange fc = iterator.next();
                strBuf.append("\n   ");
                strBuf.append(fc);
            }
            LOGGER.debug((Object)("calculateFieldChanges: " + strBuf));
        }
        return fieldChanges;
    }

    public static boolean areEqual(Object inObject1, Object inObject2) {
        if (inObject1 == null && inObject2 == null) {
            LOGGER.debug((Object)"Both were null");
            return true;
        }
        if (inObject1 == null || inObject2 == null) {
            LOGGER.debug((Object)"One or the other were null (but not both)");
            return false;
        }
        if (java.util.Date.class.isAssignableFrom(inObject1.getClass()) || Timestamp.class.isAssignableFrom(inObject1.getClass()) || Date.class.isAssignableFrom(inObject1.getClass()) || Time.class.isAssignableFrom(inObject1.getClass())) {
            java.util.Date d1 = (java.util.Date)inObject1;
            java.util.Date d2 = (java.util.Date)inObject2;
            return d1.equals(d2) || d2.equals(d1);
        }
        LOGGER.debug((Object)("Checking [" + inObject1 + "] against [" + inObject2 + "]"));
        return inObject1.equals(inObject2);
    }

    protected String getUserId() {
        String userId;
        TransactionParms transactionParms = TransactionParms.getBoundParms();
        UserContext context = transactionParms.getUserContext();
        if (context != null) {
            userId = context.getUserId();
            String externalId = context.getExternalId();
            if (StringUtils.isNotEmpty((String)externalId)) {
                userId = StringUtils.substring((String)(externalId + "/" + context.getUserId()), (int)0, (int)30);
            }
        } else {
            return "*unknown*";
        }
        return userId;
    }

    public String getBusinessActionId() {
        TransactionParms parms = TransactionParms.getBoundParms();
        return parms.getBusinessActionId();
    }

    protected void doAudit(int inTask, IEntity inEntity, FieldChanges inFieldChanges) {
//        if (inEntity instanceof Auditable) {
//            AuditEvent event = new AuditEvent(TimeUtils.getCurrentTime(), this.getUserId(), inTask, this.getBusinessActionId(), inEntity, inFieldChanges);
//            if ((event = ((Auditable)((Object)inEntity)).vetAuditEvent(event)) != null) {
//                StringBuilder strBuf = new StringBuilder("audit:");
//                strBuf.append(" act=").append(event.getAction());
//                strBuf.append(", tsk=").append(event.getTaskString());
//                strBuf.append(", cls=").append(event.getEntity().getClass());
//                strBuf.append(", key=").append(event.getEntityHKey());
//                strBuf.append(", usr=").append(event.getUserId());
//                strBuf.append(", msg=").append(event.getMessage());
//                LOGGER.info((Object)strBuf.toString());
//            }
//        }
    }

    private void applyMoreChanges(FieldChanges inMoreChanges, String[] inOutFieldIds, Object[] inOutFieldValues, IEntity inEntity) {
        Iterator<FieldChange> iterator = inMoreChanges.getIterator();
        block0: while (iterator.hasNext()) {
            FieldChange fc = iterator.next();
            inEntity.setFieldValue(fc.getMetafieldId(), fc.getNewValue());
            String fcid = fc.getFieldId();
            for (int i = 0; i < inOutFieldIds.length; ++i) {
                Object field;
                if (StringUtils.equals((String)inOutFieldIds[i], (String)fcid)) {
                    inOutFieldValues[i] = fc.getNewValue();
                    continue block0;
                }
                IMetafieldId mfidLeftMostNode = fc.getMetafieldId().getMfidLeftMostNode();
                if (mfidLeftMostNode == null || !StringUtils.equals((String)inOutFieldIds[i], (String)mfidLeftMostNode.getFieldId()) || !((field = inEntity.getField(mfidLeftMostNode)) instanceof Map)) continue;
                inOutFieldValues[i] = inEntity.getFieldValue(inOutFieldIds[i]);
                continue block0;
            }
        }
    }

    private void processViolation(Exception inViolation, String inMsg) throws CallbackException {
        if (LOGGER.isInfoEnabled()) {
            IMessageCollector mc = MessageCollectorFactory.createMessageCollector();
            mc.registerExceptions(inViolation);
            LOGGER.info((Object)(inMsg + " failed with following errors: " + mc.toString()));
        }
        throw new CallbackException(inViolation);
    }

    private void interceptionLevelStarted() {
        this._interceptionListener.interceptionStarted();
        this._interceptionListener.setCurrentSessionInterceptor((Interceptor)this);
    }

    private void interceptionLevelCompleted() {
        this._interceptionListener.interceptionCompleted();
    }

    private EntityInterceptor getEntityInterceptor(int inEventType, IEntity inEntity, @NotNull FieldChanges inFieldChanges, Object[] inFieldValues, String[] inFieldIds) {
        return new EntityInterceptor(inEventType, inEntity, inFieldChanges, inFieldValues, inFieldIds);
    }

    public void setInterceptionListener(IInterceptionListener inInterceptionListener) {
        this._interceptionListener = inInterceptionListener;
    }

    public IInterceptionListener getInterceptionListener() {
        return this._interceptionListener;
    }

    private class EntityInterceptor {
        protected boolean _allowCodeExtensions;
        protected final int _eventType;
        protected final IEntity _entity;
        @NotNull
        protected final FieldChanges _fieldChanges;
        protected final Object[] _fieldValues;
        protected final String[] _fieldIds;
        protected boolean _fieldsBeingChanged;
        protected Exception _violationChain;

        public boolean isFieldsBeingChanged() {
            return this._fieldsBeingChanged;
        }

        EntityInterceptor(int inEventType, @NotNull IEntity inEntity, FieldChanges inFieldChanges, Object[] inFieldValues, String[] inFieldIds) {
            this._entity = inEntity;
            this._eventType = inEventType;
            this._fieldChanges = inFieldChanges;
            this._fieldValues = inFieldValues;
            this._fieldIds = inFieldIds;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        public Exception execute() {
            HibernateApi hibernateApi = (HibernateApi)Roastery.getBean("hibernateApi");
            Session secondSession = null;
            SessionHolder savedOriginalSession = null;
            try {
                BaseInterceptor.this.interceptionLevelStarted();
                Connection connection = hibernateApi.getCurrentSession().connection();
                savedOriginalSession = hibernateApi.unbindSessionHolder();
                SessionFactory sessionFactory = hibernateApi.getSessionFactory();
                secondSession = sessionFactory.openSession(connection, BaseInterceptor.this._interceptionListener.getNextSessionInterceptor());
                SessionHolder sessionHolder = new SessionHolder(secondSession);
                TransactionSynchronizationManager.bindResource((Object)sessionFactory, (Object)((Object)sessionHolder));
                this.intercept();
            }
            catch (HibernateException t) {
                LOGGER.error((Object)"Error while executing entity interceptor: ", (Throwable)t);
                this._violationChain = BizFailure.create(IFrameworkPropertyKeys.FAILURE__CONNECTION, t);
            }
            finally {
                if (hibernateApi.getCurrentSession() == secondSession) {
                    hibernateApi.closeSession(false);
                    hibernateApi.bindSessionHolder(savedOriginalSession);
                }
                BaseInterceptor.this.interceptionLevelCompleted();
            }
            return this._violationChain;
        }

        private void processMoreChangesFromPreprocessing(FieldChanges inMoreChanges) {
            if (inMoreChanges != null && inMoreChanges.getFieldChangeCount() > 0) {
                this._fieldChanges.addFieldChanges(inMoreChanges);
                BaseInterceptor.this.applyMoreChanges(inMoreChanges, this._fieldIds, this._fieldValues, this._entity);
                this._fieldsBeingChanged = true;
            }
        }

        IDiagnosticEvent getDiagnosticEventIfEnabled() {
            IDiagnosticEvent event = DiagnosticUtils.recordHiearchicalDiagnosticIfEnabled(FrameworkDiagnosticEventTypesEnum.ENTITY_INTERCEPTION.name());
            if (event != null) {
                try {
                    event.setDescription(AuditEvent.getTaskString(this._eventType) + " interception for " + this._entity.getEntityName() + " - " + this._entity.getHumanReadableKey());
                }
                catch (Exception e) {
                    LOGGER.error((Object)("Error forming diagnostic event description due to" + e));
                }
            }
            return event;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        protected void intercept() {
            IDiagnosticEvent event = this.getDiagnosticEventIfEnabled();
            this._allowCodeExtensions = HiberCache.doesEntitySupportCodeExtensions(this._entity.getEntityName());
            IEntityInterceptionHandler extensionHandler = null;
            EntityExtensionContext context = null;
            if (this._allowCodeExtensions) {
                extensionHandler = (IEntityInterceptionHandler) PortalApplicationContext.getBean("entityExternalInterceptionHandler");
                context = new EntityExtensionContext(TransactionParms.getBoundParms().getUserContext(), this._entity, this._fieldChanges);
            }
            FieldChanges moreChanges = new FieldChanges();
            try {
                switch (this._eventType) {
                    case 3: {
                        this._entity.preProcessDelete(moreChanges);
                        if (event != null) {
                            event.appendMessage("preProcessDelete");
                        }
                        this.processMoreChangesFromPreprocessing(moreChanges);
                        this._violationChain = this._entity.validateDeletion();
                        if (!this._allowCodeExtensions) return;
                        if (event != null) {
                            event.appendMessage("code extension- VALIDATE_DELETE - see subevent");
                        }
                        this.validateExternalChanges(extensionHandler, context, EntityLifecycleInterceptionPointEnum.VALIDATE_DELETE);
                        return;
                    }
                    case 1: {
                        if (event != null) {
                            event.appendMessage("preProcessInsert called with " + this._fieldChanges);
                        }
                        this._entity.preProcessInsert(moreChanges);
                        if (event != null && moreChanges.getFieldChangeCount() > 0) {
                            event.appendMessage("preProcessInsert returned with extra" + moreChanges);
                        }
                        if (event != null) {
                            event.appendMessage("preProcessInsertOrUpdate called");
                        }
                        this._entity.preProcessInsertOrUpdate(moreChanges);
                        if (event != null && moreChanges.getFieldChangeCount() > 0) {
                            event.appendMessage("preProcessInsertOrUpdate returned with " + moreChanges);
                        }
                        this.processMoreChangesFromPreprocessing(moreChanges);
                        if (this._allowCodeExtensions) {
                            if (event != null) {
                                event.appendMessage("code extension- PREPROCESS_CREATE - see subevent");
                            }
                            this._violationChain = this.handleExternalCodeExtensions(extensionHandler, context, EntityLifecycleInterceptionPointEnum.PREPROCESS_CREATE);
                            if (this._violationChain != null) {
                                return;
                            }
                        }
                        if (event != null) {
                            event.appendMessage("validateChanges called with " + this._fieldChanges);
                        }
                        this._violationChain = this._entity.validateChanges(this._fieldChanges);
                        if (!this._allowCodeExtensions) return;
                        if (event != null) {
                            event.appendMessage("code extension- VALIDATE_CHANGES - see subevent");
                        }
                        this.validateExternalChanges(extensionHandler, context, EntityLifecycleInterceptionPointEnum.VALIDATE_CHANGES);
                        return;
                    }
                    case 2: {
                        if (event != null) {
                            event.appendMessage("preProcessUpdate called with " + this._fieldChanges);
                        }
                        this._entity.preProcessUpdate(this._fieldChanges, moreChanges);
                        if (event != null && moreChanges.getFieldChangeCount() > 0) {
                            event.appendMessage("preProcessUpdate returned with extra " + moreChanges);
                        }
                        if (event != null) {
                            event.appendMessage("preProcessInsertOrUpdate called");
                        }
                        this._entity.preProcessInsertOrUpdate(moreChanges);
                        if (event != null && moreChanges.getFieldChangeCount() > 0) {
                            event.appendMessage("preProcessInsertOrUpdate returned with " + moreChanges);
                        }
                        this.processMoreChangesFromPreprocessing(moreChanges);
                        if (this._allowCodeExtensions) {
                            if (event != null) {
                                event.appendMessage("code extension PREPROCESS_UPDATE - see subevent");
                            }
                            this._violationChain = this.handleExternalCodeExtensions(extensionHandler, context, EntityLifecycleInterceptionPointEnum.PREPROCESS_UPDATE);
                            if (this._violationChain != null) {
                                return;
                            }
                        }
                        if (event != null) {
                            event.appendMessage("validateChanges called with " + this._fieldChanges);
                        }
                        this._violationChain = this._entity.validateChanges(this._fieldChanges);
                        if (!this._allowCodeExtensions) return;
                        if (event != null) {
                            event.appendMessage("code extension VALIDATE_CHANGES - see subevent");
                        }
                        this.validateExternalChanges(extensionHandler, context, EntityLifecycleInterceptionPointEnum.VALIDATE_CHANGES);
                        return;
                    }
                }
                LOGGER.warn((Object)"Interceptor created without proper event type");
                return;
            }
            catch (BizFailure bf) {
                this._violationChain = bf;
                return;
            }
            catch (Throwable e) {
                BizFailure bizFailure = BizFailure.create(IFrameworkPropertyKeys.VALIDATION__INTERCEPTION__UNCHECKED_EXCEPTION, e);
                this._violationChain = bizFailure;
                LOGGER.error((Object)("Uncaught exception in your validation code: " + bizFailure.getStackTraceString()));
                return;
            }
            finally {
                if (event != null) {
                    DiagnosticUtils.closeLastEventIfNeeded();
                }
            }
        }

        @Nullable
        private BizViolation handleExternalCodeExtensions(IEntityInterceptionHandler inExtensionHandler, EntityExtensionContext inContext, EntityLifecycleInterceptionPointEnum inInterceptionPoint) {
            if (this._allowCodeExtensions) {
                inContext.setInterceptionPoint(inInterceptionPoint);
//                EntityExtensionResponse response = (EntityExtensionResponse)inExtensionHandler.invoke(inContext);
//                if (response.getMessageCollector() != null && response.getMessageCollector().hasError()) {
//                    return CarinaUtils.convertToBizViolation((BizViolation)this._violationChain, response.getMessageCollector());
//                }
//                if (response.getStatus() == ExtensionResponseStatusEnum.OK) {
//                    this.processMoreChangesFromPreprocessing((FieldChanges)response.getFieldChanges());
//                }
            }
            return null;
        }

        private void validateExternalChanges(IEntityInterceptionHandler inExtensionHandler, EntityExtensionContext inContext, EntityLifecycleInterceptionPointEnum inInterceptionPoint) {
            if (this._allowCodeExtensions) {
                inContext.setInterceptionPoint(inInterceptionPoint);
//                EntityExtensionResponse response = (EntityExtensionResponse)inExtensionHandler.invoke(inContext);
//                IMessageCollector collector = response.getMessageCollector();
//                if (collector != null && collector.hasError()) {
//                    if (this._violationChain == null || this._violationChain instanceof BizViolation) {
//                        this._violationChain = CarinaUtils.convertToBizViolation((BizViolation)this._violationChain, collector);
//                    }
//                } else if (this._violationChain != null) {
//                    LOGGER.error((Object)("Programming error - _violationChain not an instanceof BizViolation" + this._violationChain));
//                }
            }
        }
    }

}
