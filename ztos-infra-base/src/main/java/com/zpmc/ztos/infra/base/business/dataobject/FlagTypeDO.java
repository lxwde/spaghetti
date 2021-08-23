package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.business.enums.argo.FlagPurposeEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.LogicalEntityEnum;
import com.zpmc.ztos.infra.base.business.enums.framework.LifeCycleStateEnum;
import com.zpmc.ztos.infra.base.business.model.EntitySet;
import com.zpmc.ztos.infra.base.business.model.ReferenceEntity;
import com.zpmc.ztos.infra.base.common.events.EventType;

import java.io.Serializable;
import java.util.Date;

public abstract class FlagTypeDO extends ReferenceEntity implements Serializable {
    private Long flgtypGkey;
    private String flgtypId;
    private String flgtypDescription;
    private FlagPurposeEnum flgtypPurpose;
    private LogicalEntityEnum flgtypAppliesTo;
    private Boolean flgtypIsFlagReferenceIdUnique;
    private Boolean flgtypIsAddFlagReferenceIdRequired;
    private Boolean flgtypIsVetoFlagReferenceIdRequired;
    private Boolean flgtypIsFlagReferenceIdRequired;
    private Boolean flgtypIsBillingHoldRequired;
    private Boolean flgtypIsCovertHoldRequired;
    private Boolean flgtypIsMultipleAllowed;
    private Date flgtypCreated;
    private String flgtypCreator;
    private Date flgtypChanged;
    private String flgtypChanger;
    private LifeCycleStateEnum flgtypLifeCycleState;
    private EntitySet flgtypScope;
    private EventType flgtypApplyEventType;
    private EventType flgtypVetoEventType;
 //   private HoldPermissionView flgtypHoldPermView;

    public Serializable getPrimaryKey() {
        return this.getFlgtypGkey();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        Long id = this.getFlgtypGkey();
        if (id == null) {
            return false;
        }
        if (!(other instanceof FlagTypeDO)) {
            return false;
        }
        FlagTypeDO that = (FlagTypeDO)other;
        return ((Object)id).equals(that.getFlgtypGkey());
    }

    public int hashCode() {
        Long id = this.getFlgtypGkey();
        return id == null ? System.identityHashCode(this) : ((Object)id).hashCode();
    }

    public Long getFlgtypGkey() {
        return this.flgtypGkey;
    }

    protected void setFlgtypGkey(Long flgtypGkey) {
        this.flgtypGkey = flgtypGkey;
    }

    public String getFlgtypId() {
        return this.flgtypId;
    }

    protected void setFlgtypId(String flgtypId) {
        this.flgtypId = flgtypId;
    }

    public String getFlgtypDescription() {
        return this.flgtypDescription;
    }

    protected void setFlgtypDescription(String flgtypDescription) {
        this.flgtypDescription = flgtypDescription;
    }

    public FlagPurposeEnum getFlgtypPurpose() {
        return this.flgtypPurpose;
    }

    protected void setFlgtypPurpose(FlagPurposeEnum flgtypPurpose) {
        this.flgtypPurpose = flgtypPurpose;
    }

    public LogicalEntityEnum getFlgtypAppliesTo() {
        return this.flgtypAppliesTo;
    }

    protected void setFlgtypAppliesTo(LogicalEntityEnum flgtypAppliesTo) {
        this.flgtypAppliesTo = flgtypAppliesTo;
    }

    public Boolean getFlgtypIsFlagReferenceIdUnique() {
        return this.flgtypIsFlagReferenceIdUnique;
    }

    protected void setFlgtypIsFlagReferenceIdUnique(Boolean flgtypIsFlagReferenceIdUnique) {
        this.flgtypIsFlagReferenceIdUnique = flgtypIsFlagReferenceIdUnique;
    }

    public Boolean getFlgtypIsAddFlagReferenceIdRequired() {
        return this.flgtypIsAddFlagReferenceIdRequired;
    }

    protected void setFlgtypIsAddFlagReferenceIdRequired(Boolean flgtypIsAddFlagReferenceIdRequired) {
        this.flgtypIsAddFlagReferenceIdRequired = flgtypIsAddFlagReferenceIdRequired;
    }

    public Boolean getFlgtypIsVetoFlagReferenceIdRequired() {
        return this.flgtypIsVetoFlagReferenceIdRequired;
    }

    protected void setFlgtypIsVetoFlagReferenceIdRequired(Boolean flgtypIsVetoFlagReferenceIdRequired) {
        this.flgtypIsVetoFlagReferenceIdRequired = flgtypIsVetoFlagReferenceIdRequired;
    }

    public Boolean getFlgtypIsFlagReferenceIdRequired() {
        return this.flgtypIsFlagReferenceIdRequired;
    }

    protected void setFlgtypIsFlagReferenceIdRequired(Boolean flgtypIsFlagReferenceIdRequired) {
        this.flgtypIsFlagReferenceIdRequired = flgtypIsFlagReferenceIdRequired;
    }

    public Boolean getFlgtypIsBillingHoldRequired() {
        return this.flgtypIsBillingHoldRequired;
    }

    protected void setFlgtypIsBillingHoldRequired(Boolean flgtypIsBillingHoldRequired) {
        this.flgtypIsBillingHoldRequired = flgtypIsBillingHoldRequired;
    }

    public Boolean getFlgtypIsCovertHoldRequired() {
        return this.flgtypIsCovertHoldRequired;
    }

    protected void setFlgtypIsCovertHoldRequired(Boolean flgtypIsCovertHoldRequired) {
        this.flgtypIsCovertHoldRequired = flgtypIsCovertHoldRequired;
    }

    public Boolean getFlgtypIsMultipleAllowed() {
        return this.flgtypIsMultipleAllowed;
    }

    protected void setFlgtypIsMultipleAllowed(Boolean flgtypIsMultipleAllowed) {
        this.flgtypIsMultipleAllowed = flgtypIsMultipleAllowed;
    }

    public Date getFlgtypCreated() {
        return this.flgtypCreated;
    }

    protected void setFlgtypCreated(Date flgtypCreated) {
        this.flgtypCreated = flgtypCreated;
    }

    public String getFlgtypCreator() {
        return this.flgtypCreator;
    }

    protected void setFlgtypCreator(String flgtypCreator) {
        this.flgtypCreator = flgtypCreator;
    }

    public Date getFlgtypChanged() {
        return this.flgtypChanged;
    }

    protected void setFlgtypChanged(Date flgtypChanged) {
        this.flgtypChanged = flgtypChanged;
    }

    public String getFlgtypChanger() {
        return this.flgtypChanger;
    }

    protected void setFlgtypChanger(String flgtypChanger) {
        this.flgtypChanger = flgtypChanger;
    }

    public LifeCycleStateEnum getFlgtypLifeCycleState() {
        return this.flgtypLifeCycleState;
    }

    public void setFlgtypLifeCycleState(LifeCycleStateEnum flgtypLifeCycleState) {
        this.flgtypLifeCycleState = flgtypLifeCycleState;
    }

    public EntitySet getFlgtypScope() {
        return this.flgtypScope;
    }

    protected void setFlgtypScope(EntitySet flgtypScope) {
        this.flgtypScope = flgtypScope;
    }

    public EventType getFlgtypApplyEventType() {
        return this.flgtypApplyEventType;
    }

    protected void setFlgtypApplyEventType(EventType flgtypApplyEventType) {
        this.flgtypApplyEventType = flgtypApplyEventType;
    }

    public EventType getFlgtypVetoEventType() {
        return this.flgtypVetoEventType;
    }

    protected void setFlgtypVetoEventType(EventType flgtypVetoEventType) {
        this.flgtypVetoEventType = flgtypVetoEventType;
    }

//    public HoldPermissionView getFlgtypHoldPermView() {
//        return this.flgtypHoldPermView;
//    }
//
//    protected void setFlgtypHoldPermView(HoldPermissionView flgtypHoldPermView) {
//        this.flgtypHoldPermView = flgtypHoldPermView;
//    }
}
