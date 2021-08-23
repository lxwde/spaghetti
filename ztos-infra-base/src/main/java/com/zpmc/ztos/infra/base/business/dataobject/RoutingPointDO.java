package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.business.enums.argo.DataSourceEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.PointTransitToModeEnum;
import com.zpmc.ztos.infra.base.business.enums.framework.LifeCycleStateEnum;
import com.zpmc.ztos.infra.base.business.model.EntitySet;
import com.zpmc.ztos.infra.base.business.model.ReferenceEntity;
import com.zpmc.ztos.infra.base.business.model.RoutingPoint;
import com.zpmc.ztos.infra.base.business.model.UnLocCode;

import java.io.Serializable;
import java.util.Date;

public abstract class RoutingPointDO extends ReferenceEntity implements Serializable {
    private Long pointGkey;
    private String pointId;
    private PointTransitToModeEnum pointUnusedColumn1;
    private String pointScheduleDCode;
    private String pointScheduleKCode;
    private String pointSplcCode;
    private String pointTerminal;
    private Boolean pointIsPlaceholderPoint;
    private Date pointCreated;
    private String pointCreator;
    private Date pointChanged;
    private String pointChanger;
    private DataSourceEnum pointDataSource;
    private LifeCycleStateEnum pointLifeCycleState;
    private EntitySet pointScope;
    private UnLocCode pointUnLoc;
    private RoutingPoint pointActualPOD;

    public Serializable getPrimaryKey() {
        return this.getPointGkey();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        Long id = this.getPointGkey();
        if (id == null) {
            return false;
        }
        if (!(other instanceof RoutingPointDO)) {
            return false;
        }
        RoutingPointDO that = (RoutingPointDO)other;
        return ((Object)id).equals(that.getPointGkey());
    }

    public int hashCode() {
        Long id = this.getPointGkey();
        return id == null ? System.identityHashCode(this) : ((Object)id).hashCode();
    }

    public Long getPointGkey() {
        return this.pointGkey;
    }

    protected void setPointGkey(Long pointGkey) {
        this.pointGkey = pointGkey;
    }

    public String getPointId() {
        return this.pointId;
    }

    protected void setPointId(String pointId) {
        this.pointId = pointId;
    }

    public PointTransitToModeEnum getPointUnusedColumn1() {
        return this.pointUnusedColumn1;
    }

    protected void setPointUnusedColumn1(PointTransitToModeEnum pointUnusedColumn1) {
        this.pointUnusedColumn1 = pointUnusedColumn1;
    }

    public String getPointScheduleDCode() {
        return this.pointScheduleDCode;
    }

    protected void setPointScheduleDCode(String pointScheduleDCode) {
        this.pointScheduleDCode = pointScheduleDCode;
    }

    public String getPointScheduleKCode() {
        return this.pointScheduleKCode;
    }

    protected void setPointScheduleKCode(String pointScheduleKCode) {
        this.pointScheduleKCode = pointScheduleKCode;
    }

    public String getPointSplcCode() {
        return this.pointSplcCode;
    }

    protected void setPointSplcCode(String pointSplcCode) {
        this.pointSplcCode = pointSplcCode;
    }

    public String getPointTerminal() {
        return this.pointTerminal;
    }

    protected void setPointTerminal(String pointTerminal) {
        this.pointTerminal = pointTerminal;
    }

    public Boolean getPointIsPlaceholderPoint() {
        return this.pointIsPlaceholderPoint;
    }

    protected void setPointIsPlaceholderPoint(Boolean pointIsPlaceholderPoint) {
        this.pointIsPlaceholderPoint = pointIsPlaceholderPoint;
    }

    public Date getPointCreated() {
        return this.pointCreated;
    }

    protected void setPointCreated(Date pointCreated) {
        this.pointCreated = pointCreated;
    }

    public String getPointCreator() {
        return this.pointCreator;
    }

    protected void setPointCreator(String pointCreator) {
        this.pointCreator = pointCreator;
    }

    public Date getPointChanged() {
        return this.pointChanged;
    }

    protected void setPointChanged(Date pointChanged) {
        this.pointChanged = pointChanged;
    }

    public String getPointChanger() {
        return this.pointChanger;
    }

    protected void setPointChanger(String pointChanger) {
        this.pointChanger = pointChanger;
    }

    public DataSourceEnum getPointDataSource() {
        return this.pointDataSource;
    }

    protected void setPointDataSource(DataSourceEnum pointDataSource) {
        this.pointDataSource = pointDataSource;
    }

    public LifeCycleStateEnum getPointLifeCycleState() {
        return this.pointLifeCycleState;
    }

    public void setPointLifeCycleState(LifeCycleStateEnum pointLifeCycleState) {
        this.pointLifeCycleState = pointLifeCycleState;
    }

    public EntitySet getPointScope() {
        return this.pointScope;
    }

    protected void setPointScope(EntitySet pointScope) {
        this.pointScope = pointScope;
    }

    public UnLocCode getPointUnLoc() {
        return this.pointUnLoc;
    }

    protected void setPointUnLoc(UnLocCode pointUnLoc) {
        this.pointUnLoc = pointUnLoc;
    }

    public RoutingPoint getPointActualPOD() {
        return this.pointActualPOD;
    }

    protected void setPointActualPOD(RoutingPoint pointActualPOD) {
        this.pointActualPOD = pointActualPOD;
    }

}
