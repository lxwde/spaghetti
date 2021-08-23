package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.business.enums.argo.FunctionalAreaEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.LogicalEntityEnum;
import com.zpmc.ztos.infra.base.business.enums.framework.LifeCycleStateEnum;
import com.zpmc.ztos.infra.base.business.model.EntitySet;
import com.zpmc.ztos.infra.base.business.model.ReferenceEntity;
import com.zpmc.ztos.infra.base.business.model.SavedPredicate;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

public abstract class EventTypeDO extends ReferenceEntity implements Serializable {

    private Long evnttypeGkey;
    private String evnttypeId;
    private String evnttypeDescription;
    private Boolean evnttypeIsBuiltInEvent;
    private Boolean evnttypeCanBulkUpdate;
    private Boolean evnttypeIsFacilityService;
    private Boolean evnttypeIsBillable;
    private Boolean evnttypeIsNotifiable;
    private Boolean evnttypeIsAcknowledgeable;
    private FunctionalAreaEnum evnttypeFunctionalArea;
    private Boolean evnttypeIsEventRecorded;
    private LogicalEntityEnum evnttypeAppliesTo;
    private Boolean evnttypeIsAutoExtractable;
    private Date evnttypeCreated;
    private String evnttypeCreator;
    private Date evnttypeChanged;
    private String evnttypeChanger;
    private LifeCycleStateEnum evnttypeLifeCycleState;
    private EntitySet evnttypeScope;
    private SavedPredicate evnttypeFilter;
    private Set evnttypeEventEffects;
    private Set evnttypeAutoUpdateRules;

    public Serializable getPrimaryKey() {
        return this.getEvnttypeGkey();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        Long id = this.getEvnttypeGkey();
        if (id == null) {
            return false;
        }
        if (!(other instanceof EventTypeDO)) {
            return false;
        }
        EventTypeDO that = (EventTypeDO)other;
        return ((Object)id).equals(that.getEvnttypeGkey());
    }

    public int hashCode() {
        Long id = this.getEvnttypeGkey();
        return id == null ? System.identityHashCode(this) : ((Object)id).hashCode();
    }

    public Long getEvnttypeGkey() {
        return this.evnttypeGkey;
    }

    protected void setEvnttypeGkey(Long evnttypeGkey) {
        this.evnttypeGkey = evnttypeGkey;
    }

    public String getEvnttypeId() {
        return this.evnttypeId;
    }

    protected void setEvnttypeId(String evnttypeId) {
        this.evnttypeId = evnttypeId;
    }

    public String getEvnttypeDescription() {
        return this.evnttypeDescription;
    }

    protected void setEvnttypeDescription(String evnttypeDescription) {
        this.evnttypeDescription = evnttypeDescription;
    }

    public Boolean getEvnttypeIsBuiltInEvent() {
        return this.evnttypeIsBuiltInEvent;
    }

    protected void setEvnttypeIsBuiltInEvent(Boolean evnttypeIsBuiltInEvent) {
        this.evnttypeIsBuiltInEvent = evnttypeIsBuiltInEvent;
    }

    public Boolean getEvnttypeCanBulkUpdate() {
        return this.evnttypeCanBulkUpdate;
    }

    protected void setEvnttypeCanBulkUpdate(Boolean evnttypeCanBulkUpdate) {
        this.evnttypeCanBulkUpdate = evnttypeCanBulkUpdate;
    }

    public Boolean getEvnttypeIsFacilityService() {
        return this.evnttypeIsFacilityService;
    }

    protected void setEvnttypeIsFacilityService(Boolean evnttypeIsFacilityService) {
        this.evnttypeIsFacilityService = evnttypeIsFacilityService;
    }

    public Boolean getEvnttypeIsBillable() {
        return this.evnttypeIsBillable;
    }

    protected void setEvnttypeIsBillable(Boolean evnttypeIsBillable) {
        this.evnttypeIsBillable = evnttypeIsBillable;
    }

    public Boolean getEvnttypeIsNotifiable() {
        return this.evnttypeIsNotifiable;
    }

    protected void setEvnttypeIsNotifiable(Boolean evnttypeIsNotifiable) {
        this.evnttypeIsNotifiable = evnttypeIsNotifiable;
    }

    public Boolean getEvnttypeIsAcknowledgeable() {
        return this.evnttypeIsAcknowledgeable;
    }

    protected void setEvnttypeIsAcknowledgeable(Boolean evnttypeIsAcknowledgeable) {
        this.evnttypeIsAcknowledgeable = evnttypeIsAcknowledgeable;
    }

    public FunctionalAreaEnum getEvnttypeFunctionalArea() {
        return this.evnttypeFunctionalArea;
    }

    protected void setEvnttypeFunctionalArea(FunctionalAreaEnum evnttypeFunctionalArea) {
        this.evnttypeFunctionalArea = evnttypeFunctionalArea;
    }

    public Boolean getEvnttypeIsEventRecorded() {
        return this.evnttypeIsEventRecorded;
    }

    protected void setEvnttypeIsEventRecorded(Boolean evnttypeIsEventRecorded) {
        this.evnttypeIsEventRecorded = evnttypeIsEventRecorded;
    }

    public LogicalEntityEnum getEvnttypeAppliesTo() {
        return this.evnttypeAppliesTo;
    }

    protected void setEvnttypeAppliesTo(LogicalEntityEnum evnttypeAppliesTo) {
        this.evnttypeAppliesTo = evnttypeAppliesTo;
    }

    public Boolean getEvnttypeIsAutoExtractable() {
        return this.evnttypeIsAutoExtractable;
    }

    protected void setEvnttypeIsAutoExtractable(Boolean evnttypeIsAutoExtractable) {
        this.evnttypeIsAutoExtractable = evnttypeIsAutoExtractable;
    }

    public Date getEvnttypeCreated() {
        return this.evnttypeCreated;
    }

    protected void setEvnttypeCreated(Date evnttypeCreated) {
        this.evnttypeCreated = evnttypeCreated;
    }

    public String getEvnttypeCreator() {
        return this.evnttypeCreator;
    }

    protected void setEvnttypeCreator(String evnttypeCreator) {
        this.evnttypeCreator = evnttypeCreator;
    }

    public Date getEvnttypeChanged() {
        return this.evnttypeChanged;
    }

    protected void setEvnttypeChanged(Date evnttypeChanged) {
        this.evnttypeChanged = evnttypeChanged;
    }

    public String getEvnttypeChanger() {
        return this.evnttypeChanger;
    }

    protected void setEvnttypeChanger(String evnttypeChanger) {
        this.evnttypeChanger = evnttypeChanger;
    }

    public LifeCycleStateEnum getEvnttypeLifeCycleState() {
        return this.evnttypeLifeCycleState;
    }

    public void setEvnttypeLifeCycleState(LifeCycleStateEnum evnttypeLifeCycleState) {
        this.evnttypeLifeCycleState = evnttypeLifeCycleState;
    }

    public EntitySet getEvnttypeScope() {
        return this.evnttypeScope;
    }

    protected void setEvnttypeScope(EntitySet evnttypeScope) {
        this.evnttypeScope = evnttypeScope;
    }

    public SavedPredicate getEvnttypeFilter() {
        return this.evnttypeFilter;
    }

    protected void setEvnttypeFilter(SavedPredicate evnttypeFilter) {
        this.evnttypeFilter = evnttypeFilter;
    }

    public Set getEvnttypeEventEffects() {
        return this.evnttypeEventEffects;
    }

    protected void setEvnttypeEventEffects(Set evnttypeEventEffects) {
        this.evnttypeEventEffects = evnttypeEventEffects;
    }

    public Set getEvnttypeAutoUpdateRules() {
        return this.evnttypeAutoUpdateRules;
    }

    protected void setEvnttypeAutoUpdateRules(Set evnttypeAutoUpdateRules) {
        this.evnttypeAutoUpdateRules = evnttypeAutoUpdateRules;
    }

}
