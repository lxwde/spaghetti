package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.business.enums.argo.LogicalEntityEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.ServiceQuantityUnitEnum;
import com.zpmc.ztos.infra.base.business.model.ScopedBizUnit;
import com.zpmc.ztos.infra.base.common.events.Event;
import com.zpmc.ztos.infra.base.common.events.EventType;
import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;
import com.zpmc.ztos.infra.base.common.scopes.Complex;
import com.zpmc.ztos.infra.base.common.scopes.Facility;
import com.zpmc.ztos.infra.base.common.scopes.Operator;
import com.zpmc.ztos.infra.base.common.scopes.Yard;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

public class EventDO extends DatabaseEntity implements Serializable {
    private Long evntGkey;
    private String evntAppliedBy;
    private Date evntAppliedDate;
    private LogicalEntityEnum evntAppliedToClass;
    private Long evntAppliedToPrimaryKey;
    private String evntAppliedToNaturalKey;
    private String evntNote;
    private Long evntBillingExtractBatchId;
    private Double evntQuantity;
    private ServiceQuantityUnitEnum evntQuantityUnit;
    private Long evntRelatedEntityGkey;
    private String evntRelatedEntityId;
    private String evntRelatedEntityClass;
    private Long evntRelatedBatchNbr;
    private Date evntAcknowledged;
    private String evntAcknowledgedBy;
    private String evntFlexString01;
    private String evntFlexString02;
    private String evntFlexString03;
    private String evntFlexString04;
    private String evntFlexString05;
    private Date evntFlexDate01;
    private Date evntFlexDate02;
    private Date evntFlexDate03;
    private Double evntFlexDouble01;
    private Double evntFlexDouble02;
    private Double evntFlexDouble03;
    private Double evntFlexDouble04;
    private Double evntFlexDouble05;
    private Date evntCreated;
    private String evntCreator;
    private Date evntChanged;
    private String evntChanger;
    private Operator evntOperator;
    private Complex evntComplex;
    private Facility evntFacility;
    private Yard evntYard;
    private EventType evntEventType;
    private ScopedBizUnit evntResponsibleParty;
    private Event evntPrimaryEventGkey;
    private Set evntFieldChanges;
    private Set evntSrvFlags;
    private Set evntEventEcAlarmSet;
    private Set subsidiaryEvents;

    public Serializable getPrimaryKey() {
        return this.getEvntGkey();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        Long id = this.getEvntGkey();
        if (id == null) {
            return false;
        }
        if (!(other instanceof EventDO)) {
            return false;
        }
        EventDO that = (EventDO)other;
        return ((Object)id).equals(that.getEvntGkey());
    }

    public int hashCode() {
        Long id = this.getEvntGkey();
        return id == null ? System.identityHashCode(this) : ((Object)id).hashCode();
    }

    public Long getEvntGkey() {
        return this.evntGkey;
    }

    protected void setEvntGkey(Long evntGkey) {
        this.evntGkey = evntGkey;
    }

    public String getEvntAppliedBy() {
        return this.evntAppliedBy;
    }

    protected void setEvntAppliedBy(String evntAppliedBy) {
        this.evntAppliedBy = evntAppliedBy;
    }

    public Date getEvntAppliedDate() {
        return this.evntAppliedDate;
    }

    protected void setEvntAppliedDate(Date evntAppliedDate) {
        this.evntAppliedDate = evntAppliedDate;
    }

    public LogicalEntityEnum getEvntAppliedToClass() {
        return this.evntAppliedToClass;
    }

    protected void setEvntAppliedToClass(LogicalEntityEnum evntAppliedToClass) {
        this.evntAppliedToClass = evntAppliedToClass;
    }

    public Long getEvntAppliedToPrimaryKey() {
        return this.evntAppliedToPrimaryKey;
    }

    protected void setEvntAppliedToPrimaryKey(Long evntAppliedToPrimaryKey) {
        this.evntAppliedToPrimaryKey = evntAppliedToPrimaryKey;
    }

    public String getEvntAppliedToNaturalKey() {
        return this.evntAppliedToNaturalKey;
    }

    protected void setEvntAppliedToNaturalKey(String evntAppliedToNaturalKey) {
        this.evntAppliedToNaturalKey = evntAppliedToNaturalKey;
    }

    public String getEvntNote() {
        return this.evntNote;
    }

    protected void setEvntNote(String evntNote) {
        this.evntNote = evntNote;
    }

    public Long getEvntBillingExtractBatchId() {
        return this.evntBillingExtractBatchId;
    }

    protected void setEvntBillingExtractBatchId(Long evntBillingExtractBatchId) {
        this.evntBillingExtractBatchId = evntBillingExtractBatchId;
    }

    public Double getEvntQuantity() {
        return this.evntQuantity;
    }

    protected void setEvntQuantity(Double evntQuantity) {
        this.evntQuantity = evntQuantity;
    }

    public ServiceQuantityUnitEnum getEvntQuantityUnit() {
        return this.evntQuantityUnit;
    }

    protected void setEvntQuantityUnit(ServiceQuantityUnitEnum evntQuantityUnit) {
        this.evntQuantityUnit = evntQuantityUnit;
    }

    public Long getEvntRelatedEntityGkey() {
        return this.evntRelatedEntityGkey;
    }

    protected void setEvntRelatedEntityGkey(Long evntRelatedEntityGkey) {
        this.evntRelatedEntityGkey = evntRelatedEntityGkey;
    }

    public String getEvntRelatedEntityId() {
        return this.evntRelatedEntityId;
    }

    protected void setEvntRelatedEntityId(String evntRelatedEntityId) {
        this.evntRelatedEntityId = evntRelatedEntityId;
    }

    public String getEvntRelatedEntityClass() {
        return this.evntRelatedEntityClass;
    }

    protected void setEvntRelatedEntityClass(String evntRelatedEntityClass) {
        this.evntRelatedEntityClass = evntRelatedEntityClass;
    }

    public Long getEvntRelatedBatchNbr() {
        return this.evntRelatedBatchNbr;
    }

    protected void setEvntRelatedBatchNbr(Long evntRelatedBatchNbr) {
        this.evntRelatedBatchNbr = evntRelatedBatchNbr;
    }

    public Date getEvntAcknowledged() {
        return this.evntAcknowledged;
    }

    protected void setEvntAcknowledged(Date evntAcknowledged) {
        this.evntAcknowledged = evntAcknowledged;
    }

    public String getEvntAcknowledgedBy() {
        return this.evntAcknowledgedBy;
    }

    protected void setEvntAcknowledgedBy(String evntAcknowledgedBy) {
        this.evntAcknowledgedBy = evntAcknowledgedBy;
    }

    public String getEvntFlexString01() {
        return this.evntFlexString01;
    }

    protected void setEvntFlexString01(String evntFlexString01) {
        this.evntFlexString01 = evntFlexString01;
    }

    public String getEvntFlexString02() {
        return this.evntFlexString02;
    }

    protected void setEvntFlexString02(String evntFlexString02) {
        this.evntFlexString02 = evntFlexString02;
    }

    public String getEvntFlexString03() {
        return this.evntFlexString03;
    }

    protected void setEvntFlexString03(String evntFlexString03) {
        this.evntFlexString03 = evntFlexString03;
    }

    public String getEvntFlexString04() {
        return this.evntFlexString04;
    }

    protected void setEvntFlexString04(String evntFlexString04) {
        this.evntFlexString04 = evntFlexString04;
    }

    public String getEvntFlexString05() {
        return this.evntFlexString05;
    }

    protected void setEvntFlexString05(String evntFlexString05) {
        this.evntFlexString05 = evntFlexString05;
    }

    public Date getEvntFlexDate01() {
        return this.evntFlexDate01;
    }

    protected void setEvntFlexDate01(Date evntFlexDate01) {
        this.evntFlexDate01 = evntFlexDate01;
    }

    public Date getEvntFlexDate02() {
        return this.evntFlexDate02;
    }

    protected void setEvntFlexDate02(Date evntFlexDate02) {
        this.evntFlexDate02 = evntFlexDate02;
    }

    public Date getEvntFlexDate03() {
        return this.evntFlexDate03;
    }

    protected void setEvntFlexDate03(Date evntFlexDate03) {
        this.evntFlexDate03 = evntFlexDate03;
    }

    public Double getEvntFlexDouble01() {
        return this.evntFlexDouble01;
    }

    protected void setEvntFlexDouble01(Double evntFlexDouble01) {
        this.evntFlexDouble01 = evntFlexDouble01;
    }

    public Double getEvntFlexDouble02() {
        return this.evntFlexDouble02;
    }

    protected void setEvntFlexDouble02(Double evntFlexDouble02) {
        this.evntFlexDouble02 = evntFlexDouble02;
    }

    public Double getEvntFlexDouble03() {
        return this.evntFlexDouble03;
    }

    protected void setEvntFlexDouble03(Double evntFlexDouble03) {
        this.evntFlexDouble03 = evntFlexDouble03;
    }

    public Double getEvntFlexDouble04() {
        return this.evntFlexDouble04;
    }

    protected void setEvntFlexDouble04(Double evntFlexDouble04) {
        this.evntFlexDouble04 = evntFlexDouble04;
    }

    public Double getEvntFlexDouble05() {
        return this.evntFlexDouble05;
    }

    protected void setEvntFlexDouble05(Double evntFlexDouble05) {
        this.evntFlexDouble05 = evntFlexDouble05;
    }

    public Date getEvntCreated() {
        return this.evntCreated;
    }

    protected void setEvntCreated(Date evntCreated) {
        this.evntCreated = evntCreated;
    }

    public String getEvntCreator() {
        return this.evntCreator;
    }

    protected void setEvntCreator(String evntCreator) {
        this.evntCreator = evntCreator;
    }

    public Date getEvntChanged() {
        return this.evntChanged;
    }

    protected void setEvntChanged(Date evntChanged) {
        this.evntChanged = evntChanged;
    }

    public String getEvntChanger() {
        return this.evntChanger;
    }

    protected void setEvntChanger(String evntChanger) {
        this.evntChanger = evntChanger;
    }

    public Operator getEvntOperator() {
        return this.evntOperator;
    }

    protected void setEvntOperator(Operator evntOperator) {
        this.evntOperator = evntOperator;
    }

    public Complex getEvntComplex() {
        return this.evntComplex;
    }

    protected void setEvntComplex(Complex evntComplex) {
        this.evntComplex = evntComplex;
    }

    public Facility getEvntFacility() {
        return this.evntFacility;
    }

    protected void setEvntFacility(Facility evntFacility) {
        this.evntFacility = evntFacility;
    }

    public Yard getEvntYard() {
        return this.evntYard;
    }

    protected void setEvntYard(Yard evntYard) {
        this.evntYard = evntYard;
    }

    public EventType getEvntEventType() {
        return this.evntEventType;
    }

    protected void setEvntEventType(EventType evntEventType) {
        this.evntEventType = evntEventType;
    }

    public ScopedBizUnit getEvntResponsibleParty() {
        return this.evntResponsibleParty;
    }

    protected void setEvntResponsibleParty(ScopedBizUnit evntResponsibleParty) {
        this.evntResponsibleParty = evntResponsibleParty;
    }

    public Event getEvntPrimaryEventGkey() {
        return this.evntPrimaryEventGkey;
    }

    protected void setEvntPrimaryEventGkey(Event evntPrimaryEventGkey) {
        this.evntPrimaryEventGkey = evntPrimaryEventGkey;
    }

    public Set getEvntFieldChanges() {
        return this.evntFieldChanges;
    }

    protected void setEvntFieldChanges(Set evntFieldChanges) {
        this.evntFieldChanges = evntFieldChanges;
    }

    public Set getEvntSrvFlags() {
        return this.evntSrvFlags;
    }

    protected void setEvntSrvFlags(Set evntSrvFlags) {
        this.evntSrvFlags = evntSrvFlags;
    }

    public Set getEvntEventEcAlarmSet() {
        return this.evntEventEcAlarmSet;
    }

    protected void setEvntEventEcAlarmSet(Set evntEventEcAlarmSet) {
        this.evntEventEcAlarmSet = evntEventEcAlarmSet;
    }

    public Set getSubsidiaryEvents() {
        return this.subsidiaryEvents;
    }

    protected void setSubsidiaryEvents(Set subsidiaryEvents) {
        this.subsidiaryEvents = subsidiaryEvents;
    }

}
