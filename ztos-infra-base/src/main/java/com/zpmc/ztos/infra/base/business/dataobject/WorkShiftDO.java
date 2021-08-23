package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.business.enums.argo.WsResourceAssignmentEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.WsWorkStateEnum;
import com.zpmc.ztos.infra.base.business.model.PointOfWork;
import com.zpmc.ztos.infra.base.business.plans.WorkShift;
import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;
import com.zpmc.ztos.infra.base.common.scopes.Yard;

import java.io.Serializable;
import java.util.Date;

public class WorkShiftDO extends DatabaseEntity implements Serializable {
    private Long workshiftGkey;
    private Long workshiftPkey;
    private String workshiftVisit;
    private Long workshiftOwnerPowPkey;
    private Long workshiftProduction;
    private Long workshiftDualProduction;
    private Long workshiftAboveDeckPenalty;
    private Date workshiftStartTime;
    private Long workshiftDuration;
    private Float workshiftCost;
    private String workshiftCraneOperator;
    private String workshiftClerk1;
    private String workshiftClerk2;
    private String workshiftCraneOperatorLogin;
    private String workshiftClerk1Login;
    private String workshiftRadioId;
    private Long workshiftNextShiftPkey;
    private Long workshiftNextShiftPriority;
    private Long workshiftWorkState;
    private WsWorkStateEnum workshiftWorkStateEnum;
    private Long workshiftBerthLocation;
    private String workshiftShiftName;
    private Date workshiftFirstLiftTime;
    private Date workshiftLastLiftTime;
    private Long workshiftTwinProduction;
    private Long workshiftShiftRTGs;
    private Long workshiftShiftForks;
    private Long workshiftShiftRTGAssignment;
    private WsResourceAssignmentEnum workshiftShiftRTGAssignmentEnum;
    private Long workshiftShiftForkAssignment;
    private WsResourceAssignmentEnum workshiftShiftForkAssignmentEnum;
    private Boolean workshiftShiftRTGFollowQC;
    private Boolean workshiftShiftForkFollowQC;
    private Long workshiftTandemProduction;
    private Long workshiftQuadProduction;
    private Yard workshiftYard;
    private PointOfWork workshiftOwnerPow;
    private WorkShift workshiftNextShift;

    public Serializable getPrimaryKey() {
        return this.getWorkshiftGkey();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        Long id = this.getWorkshiftGkey();
        if (id == null) {
            return false;
        }
        if (!(other instanceof WorkShiftDO)) {
            return false;
        }
        WorkShiftDO that = (WorkShiftDO)other;
        return ((Object)id).equals(that.getWorkshiftGkey());
    }

    public int hashCode() {
        Long id = this.getWorkshiftGkey();
        return id == null ? System.identityHashCode(this) : ((Object)id).hashCode();
    }

    public Long getWorkshiftGkey() {
        return this.workshiftGkey;
    }

    public void setWorkshiftGkey(Long workshiftGkey) {
        this.workshiftGkey = workshiftGkey;
    }

    public Long getWorkshiftPkey() {
        return this.workshiftPkey;
    }

    public void setWorkshiftPkey(Long workshiftPkey) {
        this.workshiftPkey = workshiftPkey;
    }

    public String getWorkshiftVisit() {
        return this.workshiftVisit;
    }

    public void setWorkshiftVisit(String workshiftVisit) {
        this.workshiftVisit = workshiftVisit;
    }

    public Long getWorkshiftOwnerPowPkey() {
        return this.workshiftOwnerPowPkey;
    }

    public void setWorkshiftOwnerPowPkey(Long workshiftOwnerPowPkey) {
        this.workshiftOwnerPowPkey = workshiftOwnerPowPkey;
    }

    public Long getWorkshiftProduction() {
        return this.workshiftProduction;
    }

    public void setWorkshiftProduction(Long workshiftProduction) {
        this.workshiftProduction = workshiftProduction;
    }

    public Long getWorkshiftDualProduction() {
        return this.workshiftDualProduction;
    }

    public void setWorkshiftDualProduction(Long workshiftDualProduction) {
        this.workshiftDualProduction = workshiftDualProduction;
    }

    public Long getWorkshiftAboveDeckPenalty() {
        return this.workshiftAboveDeckPenalty;
    }

    public void setWorkshiftAboveDeckPenalty(Long workshiftAboveDeckPenalty) {
        this.workshiftAboveDeckPenalty = workshiftAboveDeckPenalty;
    }

    public Date getWorkshiftStartTime() {
        return this.workshiftStartTime;
    }

    public void setWorkshiftStartTime(Date workshiftStartTime) {
        this.workshiftStartTime = workshiftStartTime;
    }

    public Long getWorkshiftDuration() {
        return this.workshiftDuration;
    }

    public void setWorkshiftDuration(Long workshiftDuration) {
        this.workshiftDuration = workshiftDuration;
    }

    public Float getWorkshiftCost() {
        return this.workshiftCost;
    }

    public void setWorkshiftCost(Float workshiftCost) {
        this.workshiftCost = workshiftCost;
    }

    public String getWorkshiftCraneOperator() {
        return this.workshiftCraneOperator;
    }

    public void setWorkshiftCraneOperator(String workshiftCraneOperator) {
        this.workshiftCraneOperator = workshiftCraneOperator;
    }

    public String getWorkshiftClerk1() {
        return this.workshiftClerk1;
    }

    public void setWorkshiftClerk1(String workshiftClerk1) {
        this.workshiftClerk1 = workshiftClerk1;
    }

    public String getWorkshiftClerk2() {
        return this.workshiftClerk2;
    }

    public void setWorkshiftClerk2(String workshiftClerk2) {
        this.workshiftClerk2 = workshiftClerk2;
    }

    public String getWorkshiftCraneOperatorLogin() {
        return this.workshiftCraneOperatorLogin;
    }

    public void setWorkshiftCraneOperatorLogin(String workshiftCraneOperatorLogin) {
        this.workshiftCraneOperatorLogin = workshiftCraneOperatorLogin;
    }

    public String getWorkshiftClerk1Login() {
        return this.workshiftClerk1Login;
    }

    public void setWorkshiftClerk1Login(String workshiftClerk1Login) {
        this.workshiftClerk1Login = workshiftClerk1Login;
    }

    public String getWorkshiftRadioId() {
        return this.workshiftRadioId;
    }

    public void setWorkshiftRadioId(String workshiftRadioId) {
        this.workshiftRadioId = workshiftRadioId;
    }

    public Long getWorkshiftNextShiftPkey() {
        return this.workshiftNextShiftPkey;
    }

    public void setWorkshiftNextShiftPkey(Long workshiftNextShiftPkey) {
        this.workshiftNextShiftPkey = workshiftNextShiftPkey;
    }

    public Long getWorkshiftNextShiftPriority() {
        return this.workshiftNextShiftPriority;
    }

    public void setWorkshiftNextShiftPriority(Long workshiftNextShiftPriority) {
        this.workshiftNextShiftPriority = workshiftNextShiftPriority;
    }

    public Long getWorkshiftWorkState() {
        return this.workshiftWorkState;
    }

    public void setWorkshiftWorkState(Long workshiftWorkState) {
        this.workshiftWorkState = workshiftWorkState;
    }

    public WsWorkStateEnum getWorkshiftWorkStateEnum() {
        return this.workshiftWorkStateEnum;
    }

    public void setWorkshiftWorkStateEnum(WsWorkStateEnum workshiftWorkStateEnum) {
        this.workshiftWorkStateEnum = workshiftWorkStateEnum;
    }

    public Long getWorkshiftBerthLocation() {
        return this.workshiftBerthLocation;
    }

    public void setWorkshiftBerthLocation(Long workshiftBerthLocation) {
        this.workshiftBerthLocation = workshiftBerthLocation;
    }

    public String getWorkshiftShiftName() {
        return this.workshiftShiftName;
    }

    public void setWorkshiftShiftName(String workshiftShiftName) {
        this.workshiftShiftName = workshiftShiftName;
    }

    public Date getWorkshiftFirstLiftTime() {
        return this.workshiftFirstLiftTime;
    }

    public void setWorkshiftFirstLiftTime(Date workshiftFirstLiftTime) {
        this.workshiftFirstLiftTime = workshiftFirstLiftTime;
    }

    public Date getWorkshiftLastLiftTime() {
        return this.workshiftLastLiftTime;
    }

    public void setWorkshiftLastLiftTime(Date workshiftLastLiftTime) {
        this.workshiftLastLiftTime = workshiftLastLiftTime;
    }

    public Long getWorkshiftTwinProduction() {
        return this.workshiftTwinProduction;
    }

    public void setWorkshiftTwinProduction(Long workshiftTwinProduction) {
        this.workshiftTwinProduction = workshiftTwinProduction;
    }

    public Long getWorkshiftShiftRTGs() {
        return this.workshiftShiftRTGs;
    }

    public void setWorkshiftShiftRTGs(Long workshiftShiftRTGs) {
        this.workshiftShiftRTGs = workshiftShiftRTGs;
    }

    public Long getWorkshiftShiftForks() {
        return this.workshiftShiftForks;
    }

    public void setWorkshiftShiftForks(Long workshiftShiftForks) {
        this.workshiftShiftForks = workshiftShiftForks;
    }

    public Long getWorkshiftShiftRTGAssignment() {
        return this.workshiftShiftRTGAssignment;
    }

    public void setWorkshiftShiftRTGAssignment(Long workshiftShiftRTGAssignment) {
        this.workshiftShiftRTGAssignment = workshiftShiftRTGAssignment;
    }

    public WsResourceAssignmentEnum getWorkshiftShiftRTGAssignmentEnum() {
        return this.workshiftShiftRTGAssignmentEnum;
    }

    public void setWorkshiftShiftRTGAssignmentEnum(WsResourceAssignmentEnum workshiftShiftRTGAssignmentEnum) {
        this.workshiftShiftRTGAssignmentEnum = workshiftShiftRTGAssignmentEnum;
    }

    public Long getWorkshiftShiftForkAssignment() {
        return this.workshiftShiftForkAssignment;
    }

    public void setWorkshiftShiftForkAssignment(Long workshiftShiftForkAssignment) {
        this.workshiftShiftForkAssignment = workshiftShiftForkAssignment;
    }

    public WsResourceAssignmentEnum getWorkshiftShiftForkAssignmentEnum() {
        return this.workshiftShiftForkAssignmentEnum;
    }

    public void setWorkshiftShiftForkAssignmentEnum(WsResourceAssignmentEnum workshiftShiftForkAssignmentEnum) {
        this.workshiftShiftForkAssignmentEnum = workshiftShiftForkAssignmentEnum;
    }

    public Boolean getWorkshiftShiftRTGFollowQC() {
        return this.workshiftShiftRTGFollowQC;
    }

    public void setWorkshiftShiftRTGFollowQC(Boolean workshiftShiftRTGFollowQC) {
        this.workshiftShiftRTGFollowQC = workshiftShiftRTGFollowQC;
    }

    public Boolean getWorkshiftShiftForkFollowQC() {
        return this.workshiftShiftForkFollowQC;
    }

    public void setWorkshiftShiftForkFollowQC(Boolean workshiftShiftForkFollowQC) {
        this.workshiftShiftForkFollowQC = workshiftShiftForkFollowQC;
    }

    public Long getWorkshiftTandemProduction() {
        return this.workshiftTandemProduction;
    }

    public void setWorkshiftTandemProduction(Long workshiftTandemProduction) {
        this.workshiftTandemProduction = workshiftTandemProduction;
    }

    public Long getWorkshiftQuadProduction() {
        return this.workshiftQuadProduction;
    }

    public void setWorkshiftQuadProduction(Long workshiftQuadProduction) {
        this.workshiftQuadProduction = workshiftQuadProduction;
    }

    public Yard getWorkshiftYard() {
        return this.workshiftYard;
    }

    public void setWorkshiftYard(Yard workshiftYard) {
        this.workshiftYard = workshiftYard;
    }

    public PointOfWork getWorkshiftOwnerPow() {
        return this.workshiftOwnerPow;
    }

    public void setWorkshiftOwnerPow(PointOfWork workshiftOwnerPow) {
        this.workshiftOwnerPow = workshiftOwnerPow;
    }

    public WorkShift getWorkshiftNextShift() {
        return this.workshiftNextShift;
    }

    public void setWorkshiftNextShift(WorkShift workshiftNextShift) {
        this.workshiftNextShift = workshiftNextShift;
    }
}
