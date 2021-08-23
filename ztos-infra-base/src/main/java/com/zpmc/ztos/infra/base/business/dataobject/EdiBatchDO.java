package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.business.edi.EdiInterchange;
import com.zpmc.ztos.infra.base.business.edi.EdiSession;
import com.zpmc.ztos.infra.base.business.enums.edi.EdiMessageDirectionEnum;
import com.zpmc.ztos.infra.base.business.enums.edi.EdiProcessEnum;
import com.zpmc.ztos.infra.base.business.enums.edi.EdiStatusEnum;
import com.zpmc.ztos.infra.base.business.model.CarrierVisit;
import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

public class EdiBatchDO extends DatabaseEntity implements Serializable {
    private Long edibatchGkey;
    private Long edibatchNbr;
    private String edibatchMsgRefNbr;
    private EdiMessageDirectionEnum edibatchDirection;
    private EdiProcessEnum edibatchState;
    private EdiStatusEnum edibatchStatus;
    private Long edibatchSegmentCount;
    private Long edibatchReprocessCount;
    private Long edibatchTransactionCount;
    private String edibatchNotes;
    private Boolean edibatchAdditionsOnly;
    private Boolean edibatchPositionsOnly;
    private Boolean edibatchDischargesOnly;
    private Date edibatchCreated;
    private String edibatchCreator;
    private Date edibatchChanged;
    private String edibatchChanger;
    private EdiInterchange edibatchInterchange;
    private EdiSession edibatchSession;
    private CarrierVisit edibatchCarrierVisit;
    private Set ediSegmentSet;
    private Set ediBatchProcessSet;
    private Set ediTransactionSet;

    public Serializable getPrimaryKey() {
        return this.getEdibatchGkey();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        Long id = this.getEdibatchGkey();
        if (id == null) {
            return false;
        }
        if (!(other instanceof EdiBatchDO)) {
            return false;
        }
        EdiBatchDO that = (EdiBatchDO)other;
        return ((Object)id).equals(that.getEdibatchGkey());
    }

    public int hashCode() {
        Long id = this.getEdibatchGkey();
        return id == null ? System.identityHashCode(this) : ((Object)id).hashCode();
    }

    public Long getEdibatchGkey() {
        return this.edibatchGkey;
    }

    protected void setEdibatchGkey(Long edibatchGkey) {
        this.edibatchGkey = edibatchGkey;
    }

    public Long getEdibatchNbr() {
        return this.edibatchNbr;
    }

    protected void setEdibatchNbr(Long edibatchNbr) {
        this.edibatchNbr = edibatchNbr;
    }

    public String getEdibatchMsgRefNbr() {
        return this.edibatchMsgRefNbr;
    }

    protected void setEdibatchMsgRefNbr(String edibatchMsgRefNbr) {
        this.edibatchMsgRefNbr = edibatchMsgRefNbr;
    }

    public EdiMessageDirectionEnum getEdibatchDirection() {
        return this.edibatchDirection;
    }

    protected void setEdibatchDirection(EdiMessageDirectionEnum edibatchDirection) {
        this.edibatchDirection = edibatchDirection;
    }

    public EdiProcessEnum getEdibatchState() {
        return this.edibatchState;
    }

    protected void setEdibatchState(EdiProcessEnum edibatchState) {
        this.edibatchState = edibatchState;
    }

    public EdiStatusEnum getEdibatchStatus() {
        return this.edibatchStatus;
    }

    protected void setEdibatchStatus(EdiStatusEnum edibatchStatus) {
        this.edibatchStatus = edibatchStatus;
    }

    public Long getEdibatchSegmentCount() {
        return this.edibatchSegmentCount;
    }

    protected void setEdibatchSegmentCount(Long edibatchSegmentCount) {
        this.edibatchSegmentCount = edibatchSegmentCount;
    }

    public Long getEdibatchReprocessCount() {
        return this.edibatchReprocessCount;
    }

    protected void setEdibatchReprocessCount(Long edibatchReprocessCount) {
        this.edibatchReprocessCount = edibatchReprocessCount;
    }

    public Long getEdibatchTransactionCount() {
        return this.edibatchTransactionCount;
    }

    protected void setEdibatchTransactionCount(Long edibatchTransactionCount) {
        this.edibatchTransactionCount = edibatchTransactionCount;
    }

    public String getEdibatchNotes() {
        return this.edibatchNotes;
    }

    protected void setEdibatchNotes(String edibatchNotes) {
        this.edibatchNotes = edibatchNotes;
    }

    public Boolean getEdibatchAdditionsOnly() {
        return this.edibatchAdditionsOnly;
    }

    protected void setEdibatchAdditionsOnly(Boolean edibatchAdditionsOnly) {
        this.edibatchAdditionsOnly = edibatchAdditionsOnly;
    }

    public Boolean getEdibatchPositionsOnly() {
        return this.edibatchPositionsOnly;
    }

    protected void setEdibatchPositionsOnly(Boolean edibatchPositionsOnly) {
        this.edibatchPositionsOnly = edibatchPositionsOnly;
    }

    public Boolean getEdibatchDischargesOnly() {
        return this.edibatchDischargesOnly;
    }

    protected void setEdibatchDischargesOnly(Boolean edibatchDischargesOnly) {
        this.edibatchDischargesOnly = edibatchDischargesOnly;
    }

    public Date getEdibatchCreated() {
        return this.edibatchCreated;
    }

    protected void setEdibatchCreated(Date edibatchCreated) {
        this.edibatchCreated = edibatchCreated;
    }

    public String getEdibatchCreator() {
        return this.edibatchCreator;
    }

    protected void setEdibatchCreator(String edibatchCreator) {
        this.edibatchCreator = edibatchCreator;
    }

    public Date getEdibatchChanged() {
        return this.edibatchChanged;
    }

    protected void setEdibatchChanged(Date edibatchChanged) {
        this.edibatchChanged = edibatchChanged;
    }

    public String getEdibatchChanger() {
        return this.edibatchChanger;
    }

    protected void setEdibatchChanger(String edibatchChanger) {
        this.edibatchChanger = edibatchChanger;
    }

    public EdiInterchange getEdibatchInterchange() {
        return this.edibatchInterchange;
    }

    protected void setEdibatchInterchange(EdiInterchange edibatchInterchange) {
        this.edibatchInterchange = edibatchInterchange;
    }

    public EdiSession getEdibatchSession() {
        return this.edibatchSession;
    }

    protected void setEdibatchSession(EdiSession edibatchSession) {
        this.edibatchSession = edibatchSession;
    }

    public CarrierVisit getEdibatchCarrierVisit() {
        return this.edibatchCarrierVisit;
    }

    protected void setEdibatchCarrierVisit(CarrierVisit edibatchCarrierVisit) {
        this.edibatchCarrierVisit = edibatchCarrierVisit;
    }

    public Set getEdiSegmentSet() {
        return this.ediSegmentSet;
    }

    protected void setEdiSegmentSet(Set ediSegmentSet) {
        this.ediSegmentSet = ediSegmentSet;
    }

    public Set getEdiBatchProcessSet() {
        return this.ediBatchProcessSet;
    }

    protected void setEdiBatchProcessSet(Set ediBatchProcessSet) {
        this.ediBatchProcessSet = ediBatchProcessSet;
    }

    public Set getEdiTransactionSet() {
        return this.ediTransactionSet;
    }

    protected void setEdiTransactionSet(Set ediTransactionSet) {
        this.ediTransactionSet = ediTransactionSet;
    }

}
