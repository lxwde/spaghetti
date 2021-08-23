package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.business.enums.framework.LifeCycleStateEnum;
import com.zpmc.ztos.infra.base.business.enums.service.ServiceRuleTypeEnum;
import com.zpmc.ztos.infra.base.business.model.EntitySet;
import com.zpmc.ztos.infra.base.business.model.ReferenceEntity;
import com.zpmc.ztos.infra.base.business.model.SavedPredicate;
import com.zpmc.ztos.infra.base.common.events.EventType;
import com.zpmc.ztos.infra.base.common.systems.FlagType;

import java.io.Serializable;
import java.util.Date;

public abstract class ServiceRuleDO extends ReferenceEntity implements Serializable {
    private Long srvrulGkey;
    private String srvrulName;
    private ServiceRuleTypeEnum srvrulRuleType;
    private String srvrulPathToGuardian;
    private Date srvrulCreated;
    private String srvrulCreator;
    private Date srvrulChanged;
    private String srvrulChanger;
    private LifeCycleStateEnum srvrulLifeCycleState;
    private EntitySet srvrulScope;
    private EventType srvrulServiceType;
    private EventType srvrulPrereqServiceType;
    private SavedPredicate srvrulFilter;
    private FlagType srvrulFlagType;

    public Serializable getPrimaryKey() {
        return this.getSrvrulGkey();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        Long id = this.getSrvrulGkey();
        if (id == null) {
            return false;
        }
        if (!(other instanceof ServiceRuleDO)) {
            return false;
        }
        ServiceRuleDO that = (ServiceRuleDO)other;
        return ((Object)id).equals(that.getSrvrulGkey());
    }

    public int hashCode() {
        Long id = this.getSrvrulGkey();
        return id == null ? System.identityHashCode(this) : ((Object)id).hashCode();
    }

    public Long getSrvrulGkey() {
        return this.srvrulGkey;
    }

    protected void setSrvrulGkey(Long srvrulGkey) {
        this.srvrulGkey = srvrulGkey;
    }

    public String getSrvrulName() {
        return this.srvrulName;
    }

    protected void setSrvrulName(String srvrulName) {
        this.srvrulName = srvrulName;
    }

    public ServiceRuleTypeEnum getSrvrulRuleType() {
        return this.srvrulRuleType;
    }

    protected void setSrvrulRuleType(ServiceRuleTypeEnum srvrulRuleType) {
        this.srvrulRuleType = srvrulRuleType;
    }

    public String getSrvrulPathToGuardian() {
        return this.srvrulPathToGuardian;
    }

    protected void setSrvrulPathToGuardian(String srvrulPathToGuardian) {
        this.srvrulPathToGuardian = srvrulPathToGuardian;
    }

    public Date getSrvrulCreated() {
        return this.srvrulCreated;
    }

    protected void setSrvrulCreated(Date srvrulCreated) {
        this.srvrulCreated = srvrulCreated;
    }

    public String getSrvrulCreator() {
        return this.srvrulCreator;
    }

    protected void setSrvrulCreator(String srvrulCreator) {
        this.srvrulCreator = srvrulCreator;
    }

    public Date getSrvrulChanged() {
        return this.srvrulChanged;
    }

    protected void setSrvrulChanged(Date srvrulChanged) {
        this.srvrulChanged = srvrulChanged;
    }

    public String getSrvrulChanger() {
        return this.srvrulChanger;
    }

    protected void setSrvrulChanger(String srvrulChanger) {
        this.srvrulChanger = srvrulChanger;
    }

    public LifeCycleStateEnum getSrvrulLifeCycleState() {
        return this.srvrulLifeCycleState;
    }

    public void setSrvrulLifeCycleState(LifeCycleStateEnum srvrulLifeCycleState) {
        this.srvrulLifeCycleState = srvrulLifeCycleState;
    }

    public EntitySet getSrvrulScope() {
        return this.srvrulScope;
    }

    protected void setSrvrulScope(EntitySet srvrulScope) {
        this.srvrulScope = srvrulScope;
    }

    public EventType getSrvrulServiceType() {
        return this.srvrulServiceType;
    }

    protected void setSrvrulServiceType(EventType srvrulServiceType) {
        this.srvrulServiceType = srvrulServiceType;
    }

    public EventType getSrvrulPrereqServiceType() {
        return this.srvrulPrereqServiceType;
    }

    protected void setSrvrulPrereqServiceType(EventType srvrulPrereqServiceType) {
        this.srvrulPrereqServiceType = srvrulPrereqServiceType;
    }

    public SavedPredicate getSrvrulFilter() {
        return this.srvrulFilter;
    }

    protected void setSrvrulFilter(SavedPredicate srvrulFilter) {
        this.srvrulFilter = srvrulFilter;
    }

    public FlagType getSrvrulFlagType() {
        return this.srvrulFlagType;
    }

    protected void setSrvrulFlagType(FlagType srvrulFlagType) {
        this.srvrulFlagType = srvrulFlagType;
    }

}
