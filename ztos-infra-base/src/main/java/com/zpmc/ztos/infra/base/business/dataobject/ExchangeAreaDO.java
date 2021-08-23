package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.business.enums.argo.LaneAssignmentSequenceEnum;
import com.zpmc.ztos.infra.base.business.enums.framework.LifeCycleStateEnum;
import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;
import com.zpmc.ztos.infra.base.common.scopes.Yard;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 *
 * 交换区域
 * @author yejun
 */

@Data
public class ExchangeAreaDO extends DatabaseEntity implements Serializable {

    private Long xchareaGkey;
    private Date xchareaCreated;
    private String xchareaCreator;
    private Date xchareaChanged;
    private String xchareaChanger;
    private LifeCycleStateEnum xchareaLifeCycleState;
    private String xchareaId;
    private LaneAssignmentSequenceEnum xchareaLaneAssignmentSequence;
    private Integer xchareaMaxTruckLength;
    private Integer xchareaTruckFlowSeqNbr;
    private Integer xchareaBufferSize;
    private Integer xchareaLaneBufferSize;
    private Integer xchareaLaneAssignmentTolerance;
    private Integer xchareaTrucksStaged;
    private Integer xchareaTrucksQueued;
    private Yard xchareaYard;
    private Set xchareaLanes;

    public Serializable getPrimaryKey() {
        return this.getXchareaGkey();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        Long id = this.getXchareaGkey();
        if (id == null) {
            return false;
        }
        if (!(other instanceof ExchangeAreaDO)) {
            return false;
        }
        ExchangeAreaDO that = (ExchangeAreaDO)other;
        return ((Object)id).equals(that.getXchareaGkey());
    }

    public int hashCode() {
        Long id = this.getXchareaGkey();
        return id == null ? System.identityHashCode(this) : ((Object)id).hashCode();
    }

    public Long getXchareaGkey() {
        return this.xchareaGkey;
    }

    public void setXchareaGkey(Long xchareaGkey) {
        this.xchareaGkey = xchareaGkey;
    }

    public Date getXchareaCreated() {
        return this.xchareaCreated;
    }

    public void setXchareaCreated(Date xchareaCreated) {
        this.xchareaCreated = xchareaCreated;
    }

    public String getXchareaCreator() {
        return this.xchareaCreator;
    }

    public void setXchareaCreator(String xchareaCreator) {
        this.xchareaCreator = xchareaCreator;
    }

    public Date getXchareaChanged() {
        return this.xchareaChanged;
    }

    public void setXchareaChanged(Date xchareaChanged) {
        this.xchareaChanged = xchareaChanged;
    }

    public String getXchareaChanger() {
        return this.xchareaChanger;
    }

    public void setXchareaChanger(String xchareaChanger) {
        this.xchareaChanger = xchareaChanger;
    }

    public LifeCycleStateEnum getXchareaLifeCycleState() {
        return this.xchareaLifeCycleState;
    }

    public void setXchareaLifeCycleState(LifeCycleStateEnum xchareaLifeCycleState) {
        this.xchareaLifeCycleState = xchareaLifeCycleState;
    }

    public String getXchareaId() {
        return this.xchareaId;
    }

    public void setXchareaId(String xchareaId) {
        this.xchareaId = xchareaId;
    }

    public LaneAssignmentSequenceEnum getXchareaLaneAssignmentSequence() {
        return this.xchareaLaneAssignmentSequence;
    }

    public void setXchareaLaneAssignmentSequence(LaneAssignmentSequenceEnum xchareaLaneAssignmentSequence) {
        this.xchareaLaneAssignmentSequence = xchareaLaneAssignmentSequence;
    }

    public Integer getXchareaMaxTruckLength() {
        return this.xchareaMaxTruckLength;
    }

    public void setXchareaMaxTruckLength(Integer xchareaMaxTruckLength) {
        this.xchareaMaxTruckLength = xchareaMaxTruckLength;
    }

    public Integer getXchareaTruckFlowSeqNbr() {
        return this.xchareaTruckFlowSeqNbr;
    }

    public void setXchareaTruckFlowSeqNbr(Integer xchareaTruckFlowSeqNbr) {
        this.xchareaTruckFlowSeqNbr = xchareaTruckFlowSeqNbr;
    }

    public Integer getXchareaBufferSize() {
        return this.xchareaBufferSize;
    }

    public void setXchareaBufferSize(Integer xchareaBufferSize) {
        this.xchareaBufferSize = xchareaBufferSize;
    }

    public Integer getXchareaLaneBufferSize() {
        return this.xchareaLaneBufferSize;
    }

    public void setXchareaLaneBufferSize(Integer xchareaLaneBufferSize) {
        this.xchareaLaneBufferSize = xchareaLaneBufferSize;
    }

    public Integer getXchareaLaneAssignmentTolerance() {
        return this.xchareaLaneAssignmentTolerance;
    }

    public void setXchareaLaneAssignmentTolerance(Integer xchareaLaneAssignmentTolerance) {
        this.xchareaLaneAssignmentTolerance = xchareaLaneAssignmentTolerance;
    }

    public Integer getXchareaTrucksStaged() {
        return this.xchareaTrucksStaged;
    }

    public void setXchareaTrucksStaged(Integer xchareaTrucksStaged) {
        this.xchareaTrucksStaged = xchareaTrucksStaged;
    }

    public Integer getXchareaTrucksQueued() {
        return this.xchareaTrucksQueued;
    }

    public void setXchareaTrucksQueued(Integer xchareaTrucksQueued) {
        this.xchareaTrucksQueued = xchareaTrucksQueued;
    }

    public Yard getXchareaYard() {
        return this.xchareaYard;
    }

    public void setXchareaYard(Yard xchareaYard) {
        this.xchareaYard = xchareaYard;
    }

    public Set getXchareaLanes() {
        return this.xchareaLanes;
    }

    public void setXchareaLanes(Set xchareaLanes) {
        this.xchareaLanes = xchareaLanes;
    }


}
