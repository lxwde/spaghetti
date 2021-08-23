package com.zpmc.ztos.infra.base.common.events;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.business.dataobject.EventTypeDO;
import com.zpmc.ztos.infra.base.business.enums.argo.*;
import com.zpmc.ztos.infra.base.business.enums.framework.LifeCycleStateEnum;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.business.model.AbstractJunction;
import com.zpmc.ztos.infra.base.business.model.MetafieldIdList;
import com.zpmc.ztos.infra.base.business.model.SavedPredicate;
import com.zpmc.ztos.infra.base.business.predicate.PredicateFactory;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.common.exceptions.BizFailure;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.helps.ContextHelper;
import com.zpmc.ztos.infra.base.common.model.FieldChange;
import com.zpmc.ztos.infra.base.common.model.FieldChanges;
import com.zpmc.ztos.infra.base.common.model.Roastery;
import com.zpmc.ztos.infra.base.common.systems.ServiceRule;
import com.zpmc.ztos.infra.base.common.utils.ArgoUtils;
import com.zpmc.ztos.infra.base.utils.QueryUtils;
import com.zpmc.ztos.infra.base.utils.StringUtils;
import org.apache.log4j.Logger;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

public class EventType extends EventTypeDO {
    private static final Logger LOGGER = Logger.getLogger(EventType.class);

    public EventType() {
        this.setEvnttypeIsBillable(Boolean.FALSE);
        this.setEvnttypeIsBuiltInEvent(Boolean.FALSE);
        this.setEvnttypeCanBulkUpdate(Boolean.FALSE);
        this.setEvnttypeIsFacilityService(Boolean.TRUE);
        this.setEvnttypeIsNotifiable(Boolean.FALSE);
        this.setEvnttypeIsEventRecorded(Boolean.TRUE);
        this.setEvnttypeIsAcknowledgeable(Boolean.FALSE);
        this.setEvnttypeFunctionalArea(null);
        this.setEvnttypeLifeCycleState(LifeCycleStateEnum.ACTIVE);
    }

    public static EventType findOrCreateEventType(String inId, String inDescription, LogicalEntityEnum inEntityType, SavedPredicate inPredicate) {
        EventType eventType;
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info((Object)("findOrCreateEventType for id:" + inId));
        }
        if ((eventType = EventType.findEventType(inId)) == null) {
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info((Object)("no event type for id " + inId + " found. Creating new one"));
            }
            eventType = EventType.createEventType(inId, inDescription, inEntityType, inPredicate);
        }
        return eventType;
    }

    public static EventType updateOrCreateEventType(String inId, String inDescription, LogicalEntityEnum inEntityType, SavedPredicate inPredicate) {
        EventType eventType;
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info((Object)("updateOrCreateEventType for id:" + inId));
        }
        if ((eventType = EventType.findEventType(inId)) == null) {
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info((Object)("no event type for id " + inId + " found. Creating new one"));
            }
            eventType = EventType.createEventType(inId, inDescription, inEntityType, inPredicate);
        } else {
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info((Object)("event type for id " + inId + " found. Updating fields."));
            }
            eventType.setEvnttypeDescription(inDescription);
            eventType.setEvnttypeAppliesTo(inEntityType);
            eventType.setEvnttypeFilter(inPredicate);
            eventType.setEvnttypeLifeCycleState(LifeCycleStateEnum.ACTIVE);
            eventType.setEvnttypeFunctionalArea(null);
            HibernateApi.getInstance().update((Object)eventType);
        }
        return eventType;
    }

    static EventType createEventType(String inId, String inDescription, LogicalEntityEnum inEntityType, SavedPredicate inPredicate) {
        EventType eventType = new EventType();
        eventType.setEvnttypeId(inId);
        eventType.setEvnttypeDescription(inDescription);
        eventType.setEvnttypeAppliesTo(inEntityType);
        eventType.setEvnttypeIsBuiltInEvent(Boolean.FALSE);
        eventType.setEvnttypeIsFacilityService(Boolean.TRUE);
        eventType.setEvnttypeIsNotifiable(Boolean.FALSE);
        eventType.setEvnttypeIsAcknowledgeable(Boolean.FALSE);
        eventType.setEvnttypeFunctionalArea(null);
        eventType.setEvnttypeFilter(inPredicate);
        HibernateApi.getInstance().save((Object)eventType);
        return eventType;
    }

    public void setLifeCycleState(LifeCycleStateEnum inLifeCycleState) {
        this.setEvnttypeLifeCycleState(inLifeCycleState);
    }

    public LifeCycleStateEnum getLifeCycleState() {
        return this.getEvnttypeLifeCycleState();
    }

    public IMetafieldId getScopeFieldId() {
        return IServicesField.EVNTTYPE_SCOPE;
    }

    public IMetafieldId getNaturalKeyField() {
        return IServicesField.EVNTTYPE_ID;
    }

    public ScopeEnum getMinimumScope() {
        return ScopeEnum.COMPLEX;
    }

    public boolean isEventRecorded() {
        return this.getEvnttypeIsEventRecorded() != null && this.getEvnttypeIsEventRecorded() != false;
    }

    @Nullable
    public static EventType findEventType(@NotNull String inEventTypeId, @NotNull List<EventType> inPersistedEventTypes) {
        for (EventType et : inPersistedEventTypes) {
            if (!et.getId().equals(inEventTypeId)) continue;
            return et;
        }
        return null;
    }

    @Nullable
    public static EventType findEventType(String inEventTypeId) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"EventType").addDqPredicate(PredicateFactory.eq((IMetafieldId) IServicesField.EVNTTYPE_ID, (Object)inEventTypeId));
        return (EventType) Roastery.getHibernateApi().getUniqueEntityByDomainQuery(dq);
    }

    @Nullable
    public static EventType findEventTypeProxy(String inEventTypeId) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"EventType").addDqPredicate(PredicateFactory.eq((IMetafieldId) IServicesField.EVNTTYPE_ID, (Object)inEventTypeId));
        Serializable[] eventTypeGkey = HibernateApi.getInstance().findPrimaryKeysByDomainQuery(dq);
        if (eventTypeGkey == null || eventTypeGkey.length == 0) {
            return null;
        }
        if (eventTypeGkey.length == 1) {
            return (EventType)HibernateApi.getInstance().load(EventType.class, eventTypeGkey[0]);
        }
        throw BizFailure.create((IPropertyKey) IFrameworkPropertyKeys.FRAMEWORK__NON_UNIQUE_RESULT, null, (Object)eventTypeGkey.length, (Object)dq);
    }

    public static EventType findEventTypeByExternalId(String inExternalId) {
        String eventTypeId = inExternalId;
        EventEnum eventEnum = SystemEventTypeEnum.getEventEnumByExternalId((String)inExternalId);
        if (eventEnum != null) {
            eventTypeId = eventEnum.getId();
        }
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"EventType").addDqPredicate(PredicateFactory.eq((IMetafieldId) IServicesField.EVNTTYPE_ID, (Object)eventTypeId));
        return (EventType) Roastery.getHibernateApi().getUniqueEntityByDomainQuery(dq);
    }

    public boolean filterAppliesToEntity(ILogicalEntity inEntity) {
        SavedPredicate filter = this.getEvnttypeFilter();
        if (filter == null) {
            return true;
        }
        IPredicate predicate = filter.getExecutablePredicate();
        return predicate.isSatisfiedBy((IValueSource)inEntity);
    }

    public String explainFilterFailure(ILogicalEntity inEntity) {
        SavedPredicate filter = this.getEvnttypeFilter();
        if (filter == null) {
            return "no filter(?)";
        }
        StringBuffer buf = new StringBuffer();
        filter.explainMisMatch(ContextHelper.getThreadUserContext(), (IValueSource)inEntity, buf);
        return buf.toString();
    }

    @Nullable
    public static EventType resolveIEventType(IEventType inIEventType) {
        if (inIEventType == null) {
            return null;
        }
        if (inIEventType instanceof EventType) {
            return (EventType)inIEventType;
        }
        EventType eventType = EventType.findEventType(inIEventType.getId());
        if (eventType == null) {
            throw BizFailure.create((String)("No event type exists for id " + inIEventType.getId()));
        }
        return eventType;
    }

    @NotNull
    private static List<EventType> listAllEventTypes() {
        return Roastery.getHibernateApi().findEntitiesByDomainQuery(QueryUtils.createDomainQuery((String)"EventType"));
    }

    public static void resolveAllBuiltInEventTypes() {
        ContextHelper.setThreadDataSource((DataSourceEnum) DataSourceEnum.USER_DBA);
        List<EventType> persistedEventTypes = EventType.listAllEventTypes();
        Iterator iterator = EventEnum.iterator();
        while (iterator.hasNext()) {
            EventEnum eventEnum = (EventEnum)iterator.next();
            EventType.createOrUpdateBuiltInEventType(eventEnum, persistedEventTypes);
        }
    }

    public static void resolveAllBuiltInEdiEventTypes() {
        ContextHelper.setThreadDataSource((DataSourceEnum) DataSourceEnum.USER_DBA);
        List<EventType> persistedEventTypes = EventType.listAllEventTypes();
        Iterator iterator = EventEnum.iterator();
        while (iterator.hasNext()) {
            EventEnum eventEnum = (EventEnum)iterator.next();
            if (!eventEnum.getKey().contains("EDI")) continue;
            EventType.createOrUpdateBuiltInEventType(eventEnum, persistedEventTypes);
        }
    }

    @Nullable
    private static EventType createOrUpdateBuiltInEventType(@NotNull EventEnum inEventTypeEnum, @NotNull List<EventType> inPersistedEventTypes) {
        String eventTypeId = inEventTypeEnum.getId();
        EventType eventType = EventType.findEventType(eventTypeId, inPersistedEventTypes);
        boolean shouldBeFacilityService = inEventTypeEnum.isFacilityService();
        if (eventType == null) {
            if (!inEventTypeEnum.isObsolete()) {
                eventType = new EventType();
                eventType.setEvnttypeId(eventTypeId);
                eventType.setEvnttypeIsFacilityService(shouldBeFacilityService);
                eventType.setEvnttypeDescription(inEventTypeEnum.getDescription());
                eventType.setEvnttypeAppliesTo(inEventTypeEnum.getAppliesToEntityType());
                eventType.setEvnttypeFunctionalArea(inEventTypeEnum.getFunctionalArea());
                eventType.setEvnttypeIsBillable(inEventTypeEnum.isBillable());
                eventType.setEvnttypeIsNotifiable(inEventTypeEnum.isNotifiable());
                eventType.setEvnttypeIsAcknowledgeable(inEventTypeEnum.isAcknowledgeable());
                eventType.setEvnttypeIsBuiltInEvent(Boolean.TRUE);
                eventType.setEvnttypeIsBuiltInEvent(Boolean.TRUE);
                Roastery.getHibernateApi().save((Object)eventType);
                HibernateApi.getInstance().flush();
            }
        } else {
            boolean isFacilityService;
            boolean updated = false;
            boolean bl = isFacilityService = eventType.getEvnttypeIsFacilityService() != null && eventType.getEvnttypeIsFacilityService() != false;
            if (isFacilityService != shouldBeFacilityService) {
                eventType.setEvnttypeIsFacilityService(shouldBeFacilityService);
                LOGGER.warn((Object)("changed event type <" + eventTypeId + ">'s value for isFacilityService to " + shouldBeFacilityService));
                updated = true;
            }
            if (inEventTypeEnum.isObsolete()) {
                if (!LifeCycleStateEnum.OBSOLETE.equals((Object)eventType.getEvnttypeLifeCycleState())) {
                    eventType.setLifeCycleState(LifeCycleStateEnum.OBSOLETE);
                    EventEnum replacedBy = inEventTypeEnum.getReplacedBy();
                    String replacedByKey = replacedBy == null ? "none" : replacedBy.getKey();
                    LOGGER.warn((Object)("made event type <" + eventTypeId + "> obsolete, replaced by " + replacedByKey));
                    updated = true;
                }
            } else if (LifeCycleStateEnum.OBSOLETE.equals((Object)eventType.getEvnttypeLifeCycleState())) {
                eventType.setLifeCycleState(LifeCycleStateEnum.ACTIVE);
                LOGGER.warn((Object)("made event type <" + eventTypeId + "> NOT obsolete"));
                updated = true;
            }
            if (updated) {
                Roastery.getHibernateApi().update((Object)eventType);
                HibernateApi.getInstance().flush();
            }
        }
        return eventType;
    }

    public BizViolation validateChanges(FieldChanges inFieldChanges) {
        Boolean isBillable;
        BizViolation bizViolation = super.validateChanges(inFieldChanges);
        if (this.getEvnttypeGkey() != null) {
            FieldChange fieldChange;
            ServiceRule serviceRule;
            DataSourceEnum dataSource = ContextHelper.getThreadDataSource();
            if (this.getEvnttypeIsBuiltInEvent() != null && this.getEvnttypeIsBuiltInEvent().booleanValue() && !DataSourceEnum.USER_DBA.equals((Object)dataSource)) {
                MetafieldIdList metafieldIdList = inFieldChanges.getFieldIds();
                for (IMetafieldId metafieldId : metafieldIdList) {
                    if (IServicesField.EVNTTYPE_IS_BILLABLE.equals((Object)metafieldId) || IServicesField.EVNTTYPE_IS_NOTIFIABLE.equals((Object)metafieldId) || IServicesField.EVNTTYPE_DESCRIPTION.equals((Object)metafieldId) || IServicesField.EVNTTYPE_IS_BUILT_IN_EVENT.equals((Object)metafieldId) || IServicesField.EVNTTYPE_IS_EVENT_RECORDED.equals((Object)metafieldId) || IServicesField.EVNTTYPE_IS_AUTO_EXTRACTABLE.equals((Object)metafieldId) || IServicesField.EVNTTYPE_IS_ACKNOWLEDGEABLE.equals((Object)metafieldId)) continue;
                    bizViolation = BizViolation.create((IPropertyKey) IServicesPropertyKeys.CAN_NOT_CHANGE_BUILT_IN_EVENT, (BizViolation)bizViolation);
                }
            }
            List serviceRules = this.getReferencingServiceRules();
            FieldChange appliesToChanged = inFieldChanges.getFieldChange(IServicesField.EVNTTYPE_APPLIES_TO);
            if (appliesToChanged != null && !serviceRules.isEmpty()) {
                StringBuilder conflictingServiceRules = new StringBuilder();
                for (Object serviceRule1 : serviceRules) {
                    serviceRule = (ServiceRule)serviceRule1;
                    conflictingServiceRules.append(serviceRule.getSrvrulName());
                    conflictingServiceRules.append("; ");
                }
                bizViolation = BizViolation.create((IPropertyKey) IServicesPropertyKeys.EVENT_TYPE_HAS_REFERENCING_SERVICE_RULE_WITH_DIFF_FLAG_TYPE_APPLIES_TO_ENTITY, (BizViolation)bizViolation, (Object)this.getEvnttypeId(), (Object)conflictingServiceRules.toString());
            }
            if (!DataSourceEnum.USER_DBA.equals((Object) ContextHelper.getThreadDataSource()) && (fieldChange = inFieldChanges.getFieldChange(IServicesField.EVNTTYPE_LIFE_CYCLE_STATE)) != null && LifeCycleStateEnum.OBSOLETE.equals(fieldChange.getNewValue()) && !serviceRules.isEmpty()) {
                StringBuilder conflictingServiceRules = new StringBuilder();
                Iterator iterator = serviceRules.iterator();
                while (iterator.hasNext()) {
                    ServiceRule rule = serviceRule = (ServiceRule) iterator.next();
                    conflictingServiceRules.append(rule.getSrvrulName());
                    conflictingServiceRules.append("; ");
                }
                bizViolation = BizViolation.create((IPropertyKey) IServicesPropertyKeys.EVENT_TYPE_HAS_REFERENCING_SERVICE_RULE, (BizViolation)bizViolation, (Object)this.getEvnttypeId(), (Object)conflictingServiceRules.toString());
            }
        }
        isBillable = (isBillable = this.getEvnttypeIsBillable()) != null && isBillable != false;
        Boolean isPrepayable = this.getEvnttypeIsAutoExtractable();
        isPrepayable = isPrepayable != null && isPrepayable != false;
        if (!isBillable.booleanValue() && isPrepayable.booleanValue()) {
            bizViolation = BizViolation.create((IPropertyKey) IServicesPropertyKeys.PREPAYABLE_EVENT_TYPE_MUST_BE_BILLABLE, (BizViolation)bizViolation);
        }
        return bizViolation;
    }

    public Object getEvnttypeEventEffectsTableKey() {
        return this.getEvnttypeGkey();
    }

    @Nullable
    public IValueHolder getEventTypeFilterVao() {
        SavedPredicate predicate = this.getEvnttypeFilter();
        return predicate == null ? null : predicate.getPredicateVao();
    }

    public void setFieldValue(IMetafieldId inMetafieldId, Object inFieldValue) {
        if (IServicesBizMetafield.EVENT_TYPE_FILTER_VAO.equals((Object)inMetafieldId)) {
            if (inFieldValue == null || inFieldValue instanceof IValueHolder) {
                SavedPredicate predicate = this.getEvnttypeFilter();
                if (predicate != null) {
                    this.setEvnttypeFilter(null);
                    ArgoUtils.carefulDelete((Object)predicate);
                }
                if (inFieldValue == null) {
                    this.setEvnttypeFilter(null);
                } else {
                    predicate = new SavedPredicate((IValueHolder)inFieldValue);
                    this.setEvnttypeFilter(predicate);
                }
                return;
            }
            LOGGER.error((Object)("setFieldValue: invalid input " + inFieldValue));
            return;
        }
        super.setFieldValue(inMetafieldId, inFieldValue);
    }

    public BizViolation validateDeletion() {
        BizViolation bv = super.validateDeletion();
        List serviceRules = this.getReferencingServiceRules();
        if (!serviceRules.isEmpty()) {
            StringBuilder conflictingServiceRules = new StringBuilder();
            for (Object serviceRule : serviceRules) {
                ServiceRule rule = (ServiceRule)serviceRule;
                conflictingServiceRules.append(rule.getSrvrulName());
                conflictingServiceRules.append("; ");
            }
            bv = BizViolation.create((IPropertyKey) IServicesPropertyKeys.EVENT_TYPE_HAS_REFERENCING_SERVICE_RULE, (BizViolation)bv, (Object)this.getEvnttypeId(), (Object)conflictingServiceRules.toString());
        }
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"Event").addDqPredicate(PredicateFactory.eq((IMetafieldId) IServicesField.EVNT_EVENT_TYPE, (Object)this.getEvnttypeGkey()));
        dq.setMaxResults(100);
        dq.setRequireTotalCount(false);
        List events = Roastery.getHibernateApi().findEntitiesByDomainQuery(dq);
        if (!events.isEmpty()) {
            StringBuilder conflictingEvents = new StringBuilder();
            for (Object event : events) {
                conflictingEvents.append(((Event)event).getEvntAppliedToNaturalKey());
                conflictingEvents.append("; ");
            }
            bv = BizViolation.create((IPropertyKey) IServicesPropertyKeys.EVENT_TYPE_HAS_REFERENCING_EVENT, (BizViolation)bv, (Object)this.getEvnttypeId(), (Object)conflictingEvents.toString());
        }
        if (this.getEvnttypeIsBuiltInEvent().booleanValue()) {
            bv = BizViolation.create((IPropertyKey) IServicesPropertyKeys.CAN_NOT_DELETE_BUILT_IN_EVENT_TYPE, (BizViolation)bv, (Object)this.getEvnttypeId());
        }
        return bv;
    }

    private List getReferencingServiceRules() {
        AbstractJunction usesEventtype = PredicateFactory.disjunction().add(PredicateFactory.eq((IMetafieldId) IServicesField.SRVRUL_SERVICE_TYPE, (Object)this.getEvnttypeGkey())).add(PredicateFactory.eq((IMetafieldId) IServicesField.SRVRUL_PREREQ_SERVICE_TYPE, (Object)this.getEvnttypeGkey()));
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"ServiceRule").addDqPredicate((IPredicate)usesEventtype).addDqPredicate(PredicateFactory.eq((IMetafieldId) IServicesField.SRVRUL_LIFE_CYCLE_STATE, (Object) LifeCycleStateEnum.ACTIVE));
        List serviceRules = Roastery.getHibernateApi().findEntitiesByDomainQuery(dq);
        return serviceRules;
    }

    public static List getBillableEventTypes() {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"EventType").addDqPredicate(PredicateFactory.eq((IMetafieldId) IServicesField.EVNTTYPE_IS_BILLABLE, (Object) Boolean.TRUE));
        List billableEventTypeList = Roastery.getHibernateApi().findEntitiesByDomainQuery(dq);
        return billableEventTypeList;
    }

    public String getId() {
        return this.getEvnttypeId();
    }

    public String getDescription() {
        String srvctypeDescription = this.getEvnttypeDescription();
        if (srvctypeDescription != null) {
            return srvctypeDescription;
        }
        return "";
    }

    public boolean isBuiltInEventType() {
        return this.getEvnttypeIsBuiltInEvent();
    }

    public boolean isFacilityService() {
        return this.getEvnttypeIsFacilityService();
    }

    public boolean isBillable() {
        return this.getEvnttypeIsBillable();
    }

    public boolean isNotifiable() {
        return this.getEvnttypeIsNotifiable();
    }

    public boolean isAcknowledgeable() {
        return this.getEvnttypeIsAcknowledgeable();
    }

    public boolean canBulkUpdate() {
        return this.getEvnttypeCanBulkUpdate();
    }

    public LogicalEntityEnum getAppliesToEntityType() {
        return this.getEvnttypeAppliesTo();
    }

    public List getEventEffects() {
       // return EventEffect.getOrderedEventEffectsForEvent(this.getEvnttypeGkey());
        return null;
    }

    public String getExternalId() {
        EventEnum eventEnum = EventEnum.getEnum((String)this.getEvnttypeId());
        if (eventEnum != null && !StringUtils.isEmpty((String)eventEnum.getExternalId())) {
            return eventEnum.getExternalId();
        }
        return this.getEvnttypeId();
    }

    public FunctionalAreaEnum getFunctionalArea() {
        return this.getEvnttypeFunctionalArea();
    }

    public String toString() {
        return "EventType:" + this.getEvnttypeId();
    }

    void updateFilter(SavedPredicate inNewFilter) {
        SavedPredicate oldFilter = this.getEvnttypeFilter();
        this.setEvnttypeFilter(inNewFilter);
        ArgoUtils.carefulDelete((Object)oldFilter);
    }

    public static EventType findOrCreateBillableEventType(String inId, String inDescription, LogicalEntityEnum inEntityType, SavedPredicate inPredicate) {
        EventType eventType = EventType.findOrCreateEventType(inId, inDescription, inEntityType, inPredicate);
        if (!eventType.isBillable()) {
            LOGGER.warn((Object)"Event type is not billable, modifying it to billable!");
        }
        eventType.setEvnttypeIsBillable(true);
        return eventType;
    }

    public boolean isAutoExtractEvent() {
        Boolean isPrePay = this.getEvnttypeIsAutoExtractable();
        return isPrePay != null && isPrePay != false;
    }

    public static EventType hydrate(Serializable inPrimaryKey) {
        return (EventType) HibernateApi.getInstance().load(EventType.class, inPrimaryKey);
    }

}
