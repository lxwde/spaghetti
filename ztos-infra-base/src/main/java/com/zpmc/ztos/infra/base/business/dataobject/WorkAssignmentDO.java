package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.business.enums.argo.CheInstructionTypeEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.WaMovePurposeEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.WaStatusEnum;
import com.zpmc.ztos.infra.base.business.equipments.Che;
import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;
import com.zpmc.ztos.infra.base.common.scopes.Yard;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

public abstract class WorkAssignmentDO extends DatabaseEntity implements Serializable {

    private Long workassignmentGkey;
    private Long workassignmentPkey;
    private String workassignmentIdentifier;
    private Long workassignmentStatus;
    private WaStatusEnum workassignmentStatusEnum;
    private Date workassignmentLastStatusUpdate;
    private String workassignmentRawStatus;
    private Date workassignmentLastRawStatusUpdate;
    private String workassignmentErrorDetails;
    private Long workassignmentChePkey;
    private WaMovePurposeEnum workassignmentMovePurposeEnum;
    private CheInstructionTypeEnum workassignmentJobType;
    private Boolean workassignmentHasActiveAlarm;
    private Date workassignmentCreated;
    private String workassignmentCreator;
    private Date workassignmentChanged;
    private String workassignmentChanger;
    private Yard workassignmentYard;
    private Che workassignmentChe;
    private Set workassignmentJobStepProjections;
    private List workAssignmentAlarms;

    public Serializable getPrimaryKey() {
        return this.getWorkassignmentGkey();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        Long id = this.getWorkassignmentGkey();
        if (id == null) {
            return false;
        }
        if (!(other instanceof WorkAssignmentDO)) {
            return false;
        }
        WorkAssignmentDO that = (WorkAssignmentDO)other;
        return ((Object)id).equals(that.getWorkassignmentGkey());
    }

    public int hashCode() {
        Long id = this.getWorkassignmentGkey();
        return id == null ? System.identityHashCode(this) : ((Object)id).hashCode();
    }

    public Long getWorkassignmentGkey() {
        return this.workassignmentGkey;
    }

    public void setWorkassignmentGkey(Long workassignmentGkey) {
        this.workassignmentGkey = workassignmentGkey;
    }

    public Long getWorkassignmentPkey() {
        return this.workassignmentPkey;
    }

    public void setWorkassignmentPkey(Long workassignmentPkey) {
        this.workassignmentPkey = workassignmentPkey;
    }

    public String getWorkassignmentIdentifier() {
        return this.workassignmentIdentifier;
    }

    public void setWorkassignmentIdentifier(String workassignmentIdentifier) {
        this.workassignmentIdentifier = workassignmentIdentifier;
    }

    public Long getWorkassignmentStatus() {
        return this.workassignmentStatus;
    }

    public void setWorkassignmentStatus(Long workassignmentStatus) {
        this.workassignmentStatus = workassignmentStatus;
    }

    public WaStatusEnum getWorkassignmentStatusEnum() {
        return this.workassignmentStatusEnum;
    }

    public void setWorkassignmentStatusEnum(WaStatusEnum workassignmentStatusEnum) {
        this.workassignmentStatusEnum = workassignmentStatusEnum;
    }

    public Date getWorkassignmentLastStatusUpdate() {
        return this.workassignmentLastStatusUpdate;
    }

    public void setWorkassignmentLastStatusUpdate(Date workassignmentLastStatusUpdate) {
        this.workassignmentLastStatusUpdate = workassignmentLastStatusUpdate;
    }

    public String getWorkassignmentRawStatus() {
        return this.workassignmentRawStatus;
    }

    public void setWorkassignmentRawStatus(String workassignmentRawStatus) {
        this.workassignmentRawStatus = workassignmentRawStatus;
    }

    public Date getWorkassignmentLastRawStatusUpdate() {
        return this.workassignmentLastRawStatusUpdate;
    }

    public void setWorkassignmentLastRawStatusUpdate(Date workassignmentLastRawStatusUpdate) {
        this.workassignmentLastRawStatusUpdate = workassignmentLastRawStatusUpdate;
    }

    public String getWorkassignmentErrorDetails() {
        return this.workassignmentErrorDetails;
    }

    public void setWorkassignmentErrorDetails(String workassignmentErrorDetails) {
        this.workassignmentErrorDetails = workassignmentErrorDetails;
    }

    public Long getWorkassignmentChePkey() {
        return this.workassignmentChePkey;
    }

    public void setWorkassignmentChePkey(Long workassignmentChePkey) {
        this.workassignmentChePkey = workassignmentChePkey;
    }

    public WaMovePurposeEnum getWorkassignmentMovePurposeEnum() {
        return this.workassignmentMovePurposeEnum;
    }

    public void setWorkassignmentMovePurposeEnum(WaMovePurposeEnum workassignmentMovePurposeEnum) {
        this.workassignmentMovePurposeEnum = workassignmentMovePurposeEnum;
    }

    public CheInstructionTypeEnum getWorkassignmentJobType() {
        return this.workassignmentJobType;
    }

    public void setWorkassignmentJobType(CheInstructionTypeEnum workassignmentJobType) {
        this.workassignmentJobType = workassignmentJobType;
    }

    public Boolean getWorkassignmentHasActiveAlarm() {
        return this.workassignmentHasActiveAlarm;
    }

    public void setWorkassignmentHasActiveAlarm(Boolean workassignmentHasActiveAlarm) {
        this.workassignmentHasActiveAlarm = workassignmentHasActiveAlarm;
    }

    public Date getWorkassignmentCreated() {
        return this.workassignmentCreated;
    }

    public void setWorkassignmentCreated(Date workassignmentCreated) {
        this.workassignmentCreated = workassignmentCreated;
    }

    public String getWorkassignmentCreator() {
        return this.workassignmentCreator;
    }

    public void setWorkassignmentCreator(String workassignmentCreator) {
        this.workassignmentCreator = workassignmentCreator;
    }

    public Date getWorkassignmentChanged() {
        return this.workassignmentChanged;
    }

    public void setWorkassignmentChanged(Date workassignmentChanged) {
        this.workassignmentChanged = workassignmentChanged;
    }

    public String getWorkassignmentChanger() {
        return this.workassignmentChanger;
    }

    public void setWorkassignmentChanger(String workassignmentChanger) {
        this.workassignmentChanger = workassignmentChanger;
    }

    public Yard getWorkassignmentYard() {
        return this.workassignmentYard;
    }

    public void setWorkassignmentYard(Yard workassignmentYard) {
        this.workassignmentYard = workassignmentYard;
    }

    public Che getWorkassignmentChe() {
        return this.workassignmentChe;
    }

    public void setWorkassignmentChe(Che workassignmentChe) {
        this.workassignmentChe = workassignmentChe;
    }

    public Set getWorkassignmentJobStepProjections() {
        return this.workassignmentJobStepProjections;
    }

    public void setWorkassignmentJobStepProjections(Set workassignmentJobStepProjections) {
        this.workassignmentJobStepProjections = workassignmentJobStepProjections;
    }

    public List getWorkAssignmentAlarms() {
        return this.workAssignmentAlarms;
    }

    public void setWorkAssignmentAlarms(List workAssignmentAlarms) {
        this.workAssignmentAlarms = workAssignmentAlarms;
    }
}
