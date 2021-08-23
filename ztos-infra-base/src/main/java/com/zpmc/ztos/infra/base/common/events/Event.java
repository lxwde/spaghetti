package com.zpmc.ztos.infra.base.common.events;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.business.dataobject.EventDO;
import com.zpmc.ztos.infra.base.business.enums.argo.LogicalEntityEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.ServiceQuantityUnitEnum;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.business.predicate.PredicateFactory;
import com.zpmc.ztos.infra.base.common.database.HiberCache;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.common.exceptions.BizFailure;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.model.*;
import com.zpmc.ztos.infra.base.common.scopes.Complex;
import com.zpmc.ztos.infra.base.common.scopes.Facility;
import com.zpmc.ztos.infra.base.common.scopes.Operator;
import com.zpmc.ztos.infra.base.common.scopes.Yard;
import com.zpmc.ztos.infra.base.common.utils.ArgoUtils;
import com.zpmc.ztos.infra.base.utils.QueryUtils;
import org.apache.log4j.Logger;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.io.Serializable;
import java.util.*;

public class Event extends EventDO {

    public static final String EVENT_GKEYS = "EVENT_GKEYS";
    private static final String ARROW = String.valueOf('\u2192');
    private static final Logger LOGGER = Logger.getLogger(Event.class);
    public static final Long BILLING_EXTRACT_NEGATIVE_DEFAULT_VALUE = new Long(-999999999L);

    public Event() {
        this.setEvntCreated(ArgoUtils.timeNow());
        this.setEvntCreator(TransactionParms.getBoundUserId());
    }

    protected Set ensureFieldChangeSet() {
        HashSet set = (HashSet) this.getEvntFieldChanges();
        if (set == null) {
            set = new HashSet();
            this.setEvntFieldChanges(set);
        }
        return set;
    }

    public Object getEventGKey() {
        return this.getEvntGkey();
    }

    public String getEventTypeId() {
        EventType type = this.getEvntEventType();
        return type != null ? type.getId() : null;
    }

    public String getEventTypeDescription() {
        EventType type = this.getEvntEventType();
        return type != null ? type.getDescription() : null;
    }

    public LogicalEntityEnum getEventTypeAppliesTo() {
        EventType type = this.getEvntEventType();
        return type != null ? type.getEvnttypeAppliesTo() : null;
    }

    public LogicalEntityEnum getEventAppliedToClass() {
        return this.getEvntAppliedToClass();
    }

    public String getEventAppliedToNaturalKey() {
        return this.getEvntAppliedToNaturalKey();
    }

    public Serializable getEventAppliedToGkey() {
        return this.getEvntAppliedToPrimaryKey();
    }

    public boolean isEventTypeBillable() {
        return this.getEventTypeIsBillable();
    }

    public String getEventPrincipal() {
        return this.getEvntAppliedBy();
    }

    @Nullable
    public String getEventOperatorId() {
        Operator operator = this.getEvntOperator();
        String operatorId = null;
        if (operator != null) {
            operatorId = operator.getOprId();
        }
        return operatorId;
    }

    @Nullable
    public String getEventComplexId() {
        Complex complex = this.getEvntComplex();
        String complexId = null;
        if (complex != null) {
            complexId = complex.getCpxId();
        }
        return complexId;
    }

    @Nullable
    public String getEventFacilityId() {
        Facility facility = this.getEvntFacility();
        String facilityId = null;
        if (facility != null) {
            facilityId = facility.getFcyId();
        }
        return facilityId;
    }

    @Nullable
    public String getEventYardId() {
        Yard srvevntYard = this.getEvntYard();
        String yardId = null;
        if (srvevntYard != null) {
            yardId = srvevntYard.getYrdId();
        }
        return yardId;
    }

    public Date getEventTime() {
        return this.getEvntAppliedDate();
    }

    public Date getEventCreated() {
        return this.getEvntCreated();
    }

    public String getEventNote() {
        return this.getEvntNote();
    }

    public Double getEventQuantity() {
        return this.getEvntQuantity();
    }

    public ServiceQuantityUnitEnum getEventQuantityUnit() {
        return this.getEvntQuantityUnit();
    }

    public Set getFieldChanges() {
        return this.getEvntFieldChanges();
    }

    public BizViolation validateChanges(FieldChanges inChanges) {
        BizViolation bv = super.validateChanges(inChanges);
        if (this.getEvntQuantity() != null && this.getEvntQuantityUnit() == null) {
            bv = BizViolation.create((IPropertyKey) IArgoPropertyKeys.EVENT_QUANTITY_WITHOUT_UNIT_DEFINED, (BizViolation)bv);
        }
        if (this.getEvntGkey() != null && BILLING_EXTRACT_NEGATIVE_DEFAULT_VALUE.equals(this.getEvntBillingExtractBatchId())) {
            String[] allowedStates = new String[]{"DRAFT", "GUARANTEED", "INVOICED", "PARTIAL"};
//            if (ChargeableUnitEvent.isCUEExistsByStatus((Serializable)this.getEvntGkey(), (String)this.getEventTypeId(), (String[])allowedStates).booleanValue()) {
//                bv = BizViolation.create((IPropertyKey)IServicesPropertyKeys.CUE_STATUS_NOT_ALLOWED, (BizViolation)bv);
//            }
        }
        return bv;
    }

    public void preProcessInsert(FieldChanges inOutMoreChanges) {
        super.preProcessInsert(inOutMoreChanges);
        if (this.getEvntEventType().getEvnttypeIsBillable().booleanValue()) {
            this.setSelfAndFieldChange(IServicesField.EVNT_BILLING_EXTRACT_BATCH_ID, BILLING_EXTRACT_NEGATIVE_DEFAULT_VALUE, inOutMoreChanges);
        }
    }

    public void preProcessInsertOrUpdate(FieldChanges inOutMoreChanges) {
        super.preProcessInsertOrUpdate(inOutMoreChanges);
        if (this.getEvntQuantity() == null) {
            this.setSelfAndFieldChange(IServicesField.EVNT_QUANTITY_UNIT, null, inOutMoreChanges);
        }
        if (inOutMoreChanges.hasFieldChange(IArgoBizMetafield.EVENT_TYPE_IS_BILLABLE)) {
            inOutMoreChanges.removeFieldChange(IArgoBizMetafield.EVENT_TYPE_IS_BILLABLE);
        }
    }

    public void setFieldValue(IMetafieldId inFieldId, Object inFieldValue) {
        if (IArgoBizMetafield.EVENT_TYPE_IS_BILLABLE.equals((Object)inFieldId)) {
            this.setEventTypeIsBillable((Boolean)inFieldValue);
        } else {
            super.setFieldValue(inFieldId, inFieldValue);
        }
    }

    public int compareTo(Object inObject) {
        if (inObject == null || !(inObject instanceof Event)) {
            return -1;
        }
        Date thisEvTime = this.getEventTime();
        Date inEvTime = ((Event)inObject).getEventTime();
        if (thisEvTime == null) {
            if (inEvTime == null) {
                return 0;
            }
            return 1;
        }
        int value = -thisEvTime.compareTo(inEvTime);
        return value;
    }

    public String getEvntFieldChangesString() {
//        IFieldSynthesizer servicesSynthesizer = (IFieldSynthesizer) Roastery.getBean((String)ServicesSynthesizer.BEAN_ID);
//        return (String)servicesSynthesizer.synthesizeFieldValue(IArgoBizMetafield.EVENT_FIELD_CHANGES_STRING, new MetafieldIdList(), (ValueSource)this);
        return null;
    }

    public String getEventTypeExternalId() {
        EventType eventType = this.getEvntEventType();
        return eventType.getExternalId();
    }

    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("Event: ");
        String eventTypeId = this.getEventTypeId();
        buf.append(eventTypeId != null ? eventTypeId : "unknown");
        buf.append(", on: ");
        String appliedToNaturalKey = this.getEventAppliedToNaturalKey();
        buf.append(appliedToNaturalKey != null ? appliedToNaturalKey : "unknown");
        buf.append(", at complex: ");
        String complexId = this.getEventComplexId();
        buf.append(complexId != null ? complexId : "unknown");
        return buf.toString();
    }

    public AuditEvent vetAuditEvent(AuditEvent inAuditEvent) {
        return inAuditEvent;
    }

    public static String getEventMetaFieldLastRecordedBy(IServiceable inServiceable, String inMetafieldId, Serializable inEventTypeGkey) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"Event").addDqField(IServicesField.EVNT_CREATOR).addDqField(IServicesField.EVNT_GKEY).addDqPredicate(PredicateFactory.eq((IMetafieldId) IServicesField.EVNT_APPLIED_TO_PRIMARY_KEY, (Object)inServiceable.getPrimaryKey())).addDqPredicate(PredicateFactory.eq((IMetafieldId) IServicesField.EVNT_APPLIED_TO_CLASS, (Object)inServiceable.getLogicalEntityType())).addDqPredicate(PredicateFactory.eq((IMetafieldId) IServicesField.EVNT_EVENT_TYPE, (Object)inEventTypeGkey)).addDqOrdering(Ordering.desc((IMetafieldId) IServicesField.EVNT_CREATED));
        dq.setMaxResults(1);
        IQueryResult result = HibernateApi.getInstance().findValuesByDomainQuery(dq);
        if (result == null || result.getTotalResultCount() == 0) {
            return null;
        }
        String creator = (String)result.getValue(0, IServicesField.EVNT_CREATOR);
        Serializable eventGkey = (Serializable)result.getValue(0, IServicesField.EVNT_GKEY);
        IDomainQuery fieldChangeDq = QueryUtils.createDomainQuery((String)"EventFieldChange").addDqPredicate(PredicateFactory.eq((IMetafieldId) IServicesField.EVNTFC_EVENT, (Object)eventGkey)).addDqPredicate(PredicateFactory.eq((IMetafieldId) IServicesField.EVNTFC_METAFIELD_ID, (Object)inMetafieldId));
        return HibernateApi.getInstance().existsByDomainQuery(fieldChangeDq) ? creator : null;
    }

    public void preProcessDelete(FieldChanges inChanges) {
        try {
            if (this.getEventAppliedToNaturalKey() == null || this.getEventGKey() == null) {
                LOGGER.error((Object)"MoveEvent - preProcessDelete: Key Value Null");
                return;
            }
            IDomainQuery dq = QueryUtils.createDomainQuery((String)"Flag").addDqPredicate(PredicateFactory.eq((IMetafieldId) IServicesField.FLAG_APPLIED_TO_NATURAL_KEY, (Object)this.getEventAppliedToNaturalKey())).addDqPredicate(PredicateFactory.eq((IMetafieldId) IServicesField.FLAG_ASSOCIATED_EVENT_GKEY, (Object)this.getEventGKey()));
            HibernateApi.getInstance().deleteByDomainQuery(dq);
            HibernateApi.getInstance().flush();
        }
        catch (Exception e) {
            LOGGER.error((Object)("preProcessDelete: Error while deleting Flag :" + e));
            throw BizFailure.wrap((Throwable)e);
        }
    }

    public Boolean getEventTypeIsBillable() {
        return this.getEvntBillingExtractBatchId() != null;
    }

    public String getEventFieldChangesString() {
        return this.getEvntFieldChangesString();
    }

    public void setEventTypeIsBillable(Boolean inIsBillable) {
        if (inIsBillable != null) {
            if (!inIsBillable.booleanValue()) {
                this.setEvntBillingExtractBatchId(null);
            } else {
                this.setEvntBillingExtractBatchId(BILLING_EXTRACT_NEGATIVE_DEFAULT_VALUE);
            }
        }
    }

    public List getEventFieldChanges() {
        ArrayList<ValueObject> fieldChangesVaoList = new ArrayList<ValueObject>();
        Set changes = this.getFieldChanges();
        if (changes != null) {
//            for (IServiceEventFieldChange fc : changes) {
//                ValueObject fcVao = new ValueObject("IServiceEventFieldChange");
//                IMetafieldId metafieldId = MetafieldIdFactory.valueOf((String)fc.getMetafieldId());
//                fcVao.setFieldValue(IArgoBizMetafield.EVENT_FIELD_CHANGE_METAFIELD_ID, (Object)metafieldId);
//                fcVao.setFieldValue(IArgoBizMetafield.EVENT_FIELD_CHANGE_PREV_VALUE, Event.getFieldChangeValue(metafieldId, fc.getPrevVal()));
//                fcVao.setFieldValue(IArgoBizMetafield.EVENT_FIELD_CHANGE_NEW_VALUE, Event.getFieldChangeValue(metafieldId, fc.getNewVal()));
//                fieldChangesVaoList.add(fcVao);
//            }
        }
        return fieldChangesVaoList;
    }

    public static Event resolveIEvent(@NotNull IEvent inIEvent) {
        if (inIEvent == null) {
            return null;
        }
        if (inIEvent instanceof Event) {
            return (Event)inIEvent;
        }
        return Event.hydrate((Serializable)inIEvent.getEventGKey());
    }

    public static Event hydrate(Serializable inPrimaryKey) {
        return (Event)HibernateApi.getInstance().load(Event.class, inPrimaryKey);
    }

    private static Object getFieldChangeValue(IMetafieldId inMetafieldId, String inPersistedString) {
        DatabaseEntity entity;
        Class propertyClass;
        Object value = HiberCache.string2Property((IMetafieldId)inMetafieldId, (String)inPersistedString);
        if (value instanceof Long && DatabaseEntity.class.isAssignableFrom(propertyClass = HiberCache.getFieldClass((String)inMetafieldId.getFieldId())) && (entity = (DatabaseEntity)HibernateApi.getInstance().get(propertyClass, (Serializable)((Long)value))) != null) {
            return entity.getHumanReadableKey();
        }
        return value;
    }

    @Nullable
    public static Event findEventByGkey(Serializable inEventGkey) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"Event").addDqPredicate(PredicateFactory.eq((IMetafieldId) IServicesField.EVNT_GKEY, (Object)inEventGkey));
        return (Event)HibernateApi.getInstance().getUniqueEntityByDomainQuery(dq);
    }

    public void updateEventAcknowledged(String inAcknowledgedBy) {
        this.setEvntAcknowledged(new Date());
        this.setEvntAcknowledgedBy(inAcknowledgedBy);
    }

    public void updateEvenApplied(@Nullable Date inDate) {
        this.setEvntAppliedDate(inDate);
    }

    public void updateRelatedEvent(Event inEvent) {
        this.setEvntPrimaryEventGkey(inEvent);
        HashSet<Event> subEvents = (HashSet<Event>) inEvent.getSubsidiaryEvents();
        if (subEvents == null) {
            subEvents = new HashSet<Event>();
            inEvent.setSubsidiaryEvents(subEvents);
        }
        subEvents.add(this);
    }

    public void updateRelatedEntity(IEntity inEntity) {
        this.setEvntRelatedEntityClass(inEntity.getEntityName());
        this.setEvntRelatedEntityGkey((Long)inEntity.getPrimaryKey());
        this.setEvntRelatedEntityId(inEntity.getHumanReadableKey());
    }

    public void updateEvntFlexString01(String inString) {
        this.setEvntFlexString01(inString);
    }

    public void updateEvntFlexString02(String inString) {
        this.setEvntFlexString02(inString);
    }

    public void updateEvntEventType(EventType inEventType) {
        this.setEvntEventType(inEventType);
    }

    public void updateEvntGkey(Long inGkey) {
        this.setEvntGkey(inGkey);
    }

    public void updateEvntAppliedToClass(LogicalEntityEnum inLogicalEntityEnum) {
        this.setEvntAppliedToClass(inLogicalEntityEnum);
    }

    public void updateEvntAppliedToPrimaryKey(Long inEvntAppliedToPrimaryKey) {
        this.setEvntAppliedToPrimaryKey(inEvntAppliedToPrimaryKey);
    }

    public void updateEvntAppliedToNaturalKey(String inEvntAppliedToNaturalKey) {
        this.setEvntAppliedToNaturalKey(inEvntAppliedToNaturalKey);
    }

    public void updateEvntAppliedBy(String inEvntAppliedBy) {
        this.setEvntAppliedBy(inEvntAppliedBy);
    }

    public void updateEvntComplex(Complex inComplex) {
        this.setEvntComplex(inComplex);
    }

    public void updateEvntRelatedEntityId(String inEvntRelatedEntityId) {
        this.setEvntRelatedEntityId(inEvntRelatedEntityId);
    }

    public void updateEvntRelatedEntityGkey(Long inEvntRelatedEntityGkey) {
        this.setEvntRelatedEntityGkey(inEvntRelatedEntityGkey);
    }

    public void updateEvntRelatedEntityClass(String inEvntRelatedEntityClass) {
        this.setEvntRelatedEntityClass(inEvntRelatedEntityClass);
    }

}
