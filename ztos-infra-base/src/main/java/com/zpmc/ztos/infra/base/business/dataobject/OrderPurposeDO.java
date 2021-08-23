package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.business.enums.framework.LifeCycleStateEnum;
import com.zpmc.ztos.infra.base.business.model.EntitySet;
import com.zpmc.ztos.infra.base.business.model.ReferenceEntity;

import java.io.Serializable;
import java.util.Date;

public abstract class OrderPurposeDO extends ReferenceEntity implements Serializable {
    private Long orderpurposeGkey;
    private String orderpurposeId;
    private String orderpurposeDescription;
    private Boolean orderpurposeIsBuiltInEvent;
    private Date orderpurposeCreated;
    private String orderpurposeCreator;
    private Date orderpurposeChanged;
    private String orderpurposeChanger;
    private LifeCycleStateEnum orderpurposeLifeCycleState;
    private EntitySet orderpurposeScope;

    public Serializable getPrimaryKey() {
        return this.getOrderpurposeGkey();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        Long id = this.getOrderpurposeGkey();
        if (id == null) {
            return false;
        }
        if (!(other instanceof OrderPurposeDO)) {
            return false;
        }
        OrderPurposeDO that = (OrderPurposeDO)other;
        return ((Object)id).equals(that.getOrderpurposeGkey());
    }

    public int hashCode() {
        Long id = this.getOrderpurposeGkey();
        return id == null ? System.identityHashCode(this) : ((Object)id).hashCode();
    }

    public Long getOrderpurposeGkey() {
        return this.orderpurposeGkey;
    }

    protected void setOrderpurposeGkey(Long orderpurposeGkey) {
        this.orderpurposeGkey = orderpurposeGkey;
    }

    public String getOrderpurposeId() {
        return this.orderpurposeId;
    }

    protected void setOrderpurposeId(String orderpurposeId) {
        this.orderpurposeId = orderpurposeId;
    }

    public String getOrderpurposeDescription() {
        return this.orderpurposeDescription;
    }

    protected void setOrderpurposeDescription(String orderpurposeDescription) {
        this.orderpurposeDescription = orderpurposeDescription;
    }

    public Boolean getOrderpurposeIsBuiltInEvent() {
        return this.orderpurposeIsBuiltInEvent;
    }

    protected void setOrderpurposeIsBuiltInEvent(Boolean orderpurposeIsBuiltInEvent) {
        this.orderpurposeIsBuiltInEvent = orderpurposeIsBuiltInEvent;
    }

    public Date getOrderpurposeCreated() {
        return this.orderpurposeCreated;
    }

    protected void setOrderpurposeCreated(Date orderpurposeCreated) {
        this.orderpurposeCreated = orderpurposeCreated;
    }

    public String getOrderpurposeCreator() {
        return this.orderpurposeCreator;
    }

    protected void setOrderpurposeCreator(String orderpurposeCreator) {
        this.orderpurposeCreator = orderpurposeCreator;
    }

    public Date getOrderpurposeChanged() {
        return this.orderpurposeChanged;
    }

    protected void setOrderpurposeChanged(Date orderpurposeChanged) {
        this.orderpurposeChanged = orderpurposeChanged;
    }

    public String getOrderpurposeChanger() {
        return this.orderpurposeChanger;
    }

    protected void setOrderpurposeChanger(String orderpurposeChanger) {
        this.orderpurposeChanger = orderpurposeChanger;
    }

    public LifeCycleStateEnum getOrderpurposeLifeCycleState() {
        return this.orderpurposeLifeCycleState;
    }

    public void setOrderpurposeLifeCycleState(LifeCycleStateEnum orderpurposeLifeCycleState) {
        this.orderpurposeLifeCycleState = orderpurposeLifeCycleState;
    }

    public EntitySet getOrderpurposeScope() {
        return this.orderpurposeScope;
    }

    protected void setOrderpurposeScope(EntitySet orderpurposeScope) {
        this.orderpurposeScope = orderpurposeScope;
    }
}
