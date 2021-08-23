package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.business.enums.argo.LaneStatusEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.LaneTruckStatusEnum;
import com.zpmc.ztos.infra.base.business.enums.framework.LifeCycleStateEnum;
import com.zpmc.ztos.infra.base.business.model.CarrierVisit;
import com.zpmc.ztos.infra.base.business.model.ScanSetBase;
import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;
import com.zpmc.ztos.infra.base.common.scopes.Facility;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 *
 * 车道
 * @author yejun
 */
@Data
public class LaneDO extends DatabaseEntity implements Serializable {
    private Long laneGkey;
    private Date laneCreated;
    private String laneCreator;
    private Date laneChanged;
    private String laneChanger;
    private LifeCycleStateEnum laneLifeCycleState;
    private String laneId;
    private String laneClass;
    private String lanePedestalId;
    private String laneDescription;
    private String laneGosStatus;
    private Long laneGosTvKey;
    private LaneStatusEnum laneStatus;
    private Long laneAssignmentPriority;
    private Date laneAssignmentTime;
    private Long laneTimeSinceAssignment;
    private Date laneClearedTime;
    private Long laneClearanceDelaySecs;
    private Boolean laneHasLoopDetector;
    private Date laneInLaneTime;
    private Long laneTimeSinceInLane;
    private String laneMission;
    private LaneTruckStatusEnum laneTruckStatus;
    private Facility laneFacility;
    private CarrierVisit laneCarrierVisit;
    private ScanSetBase laneScanSet;
    private Set lanePrinters;
    private Set laneConsoles;

    @Override
    public Serializable getPrimaryKey() {
        return this.getLaneGkey();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        Long id = this.getLaneGkey();
        if (id == null) {
            return false;
        }
        if (!(other instanceof LaneDO)) {
            return false;
        }
        LaneDO that = (LaneDO)other;
        return ((Object)id).equals(that.getLaneGkey());
    }

    public int hashCode() {
        Long id = this.getLaneGkey();
        return id == null ? System.identityHashCode(this) : ((Object)id).hashCode();
    }

    public Long getLaneGkey() {
        return this.laneGkey;
    }

    public void setLaneGkey(Long laneGkey) {
        this.laneGkey = laneGkey;
    }

    public Date getLaneCreated() {
        return this.laneCreated;
    }

    public void setLaneCreated(Date laneCreated) {
        this.laneCreated = laneCreated;
    }

    public String getLaneCreator() {
        return this.laneCreator;
    }

    public void setLaneCreator(String laneCreator) {
        this.laneCreator = laneCreator;
    }

    public Date getLaneChanged() {
        return this.laneChanged;
    }

    public void setLaneChanged(Date laneChanged) {
        this.laneChanged = laneChanged;
    }

    public String getLaneChanger() {
        return this.laneChanger;
    }

    public void setLaneChanger(String laneChanger) {
        this.laneChanger = laneChanger;
    }

    public LifeCycleStateEnum getLaneLifeCycleState() {
        return this.laneLifeCycleState;
    }

    public void setLaneLifeCycleState(LifeCycleStateEnum laneLifeCycleState) {
        this.laneLifeCycleState = laneLifeCycleState;
    }

    public String getLaneId() {
        return this.laneId;
    }

    public void setLaneId(String laneId) {
        this.laneId = laneId;
    }

    public String getLaneClass() {
        return this.laneClass;
    }

    public void setLaneClass(String laneClass) {
        this.laneClass = laneClass;
    }

    public String getLanePedestalId() {
        return this.lanePedestalId;
    }

    public void setLanePedestalId(String lanePedestalId) {
        this.lanePedestalId = lanePedestalId;
    }

    public String getLaneDescription() {
        return this.laneDescription;
    }

    public void setLaneDescription(String laneDescription) {
        this.laneDescription = laneDescription;
    }

    public String getLaneGosStatus() {
        return this.laneGosStatus;
    }

    public void setLaneGosStatus(String laneGosStatus) {
        this.laneGosStatus = laneGosStatus;
    }

    public Long getLaneGosTvKey() {
        return this.laneGosTvKey;
    }

    public void setLaneGosTvKey(Long laneGosTvKey) {
        this.laneGosTvKey = laneGosTvKey;
    }

    public LaneStatusEnum getLaneStatus() {
        return this.laneStatus;
    }

    public void setLaneStatus(LaneStatusEnum laneStatus) {
        this.laneStatus = laneStatus;
    }

    public Long getLaneAssignmentPriority() {
        return this.laneAssignmentPriority;
    }

    public void setLaneAssignmentPriority(Long laneAssignmentPriority) {
        this.laneAssignmentPriority = laneAssignmentPriority;
    }

    public Date getLaneAssignmentTime() {
        return this.laneAssignmentTime;
    }

    public void setLaneAssignmentTime(Date laneAssignmentTime) {
        this.laneAssignmentTime = laneAssignmentTime;
    }

    public Long getLaneTimeSinceAssignment() {
        return this.laneTimeSinceAssignment;
    }

    public void setLaneTimeSinceAssignment(Long laneTimeSinceAssignment) {
        this.laneTimeSinceAssignment = laneTimeSinceAssignment;
    }

    public Date getLaneClearedTime() {
        return this.laneClearedTime;
    }

    public void setLaneClearedTime(Date laneClearedTime) {
        this.laneClearedTime = laneClearedTime;
    }

    public Long getLaneClearanceDelaySecs() {
        return this.laneClearanceDelaySecs;
    }

    public void setLaneClearanceDelaySecs(Long laneClearanceDelaySecs) {
        this.laneClearanceDelaySecs = laneClearanceDelaySecs;
    }

    protected Boolean getLaneHasLoopDetector() {
        return this.laneHasLoopDetector;
    }

    public void setLaneHasLoopDetector(Boolean laneHasLoopDetector) {
        this.laneHasLoopDetector = laneHasLoopDetector;
    }

    public Date getLaneInLaneTime() {
        return this.laneInLaneTime;
    }

    public void setLaneInLaneTime(Date laneInLaneTime) {
        this.laneInLaneTime = laneInLaneTime;
    }

    public Long getLaneTimeSinceInLane() {
        return this.laneTimeSinceInLane;
    }

    public void setLaneTimeSinceInLane(Long laneTimeSinceInLane) {
        this.laneTimeSinceInLane = laneTimeSinceInLane;
    }

    public String getLaneMission() {
        return this.laneMission;
    }

    public void setLaneMission(String laneMission) {
        this.laneMission = laneMission;
    }

    public LaneTruckStatusEnum getLaneTruckStatus() {
        return this.laneTruckStatus;
    }

    public void setLaneTruckStatus(LaneTruckStatusEnum laneTruckStatus) {
        this.laneTruckStatus = laneTruckStatus;
    }

    public Facility getLaneFacility() {
        return this.laneFacility;
    }

    public void setLaneFacility(Facility laneFacility) {
        this.laneFacility = laneFacility;
    }

    public CarrierVisit getLaneCarrierVisit() {
        return this.laneCarrierVisit;
    }

    public void setLaneCarrierVisit(CarrierVisit laneCarrierVisit) {
        this.laneCarrierVisit = laneCarrierVisit;
    }

    public ScanSetBase getLaneScanSet() {
        return this.laneScanSet;
    }

    public void setLaneScanSet(ScanSetBase laneScanSet) {
        this.laneScanSet = laneScanSet;
    }

    public Set getLanePrinters() {
        return this.lanePrinters;
    }

    public void setLanePrinters(Set lanePrinters) {
        this.lanePrinters = lanePrinters;
    }

    public Set getLaneConsoles() {
        return this.laneConsoles;
    }

    public void setLaneConsoles(Set laneConsoles) {
        this.laneConsoles = laneConsoles;
    }

}
