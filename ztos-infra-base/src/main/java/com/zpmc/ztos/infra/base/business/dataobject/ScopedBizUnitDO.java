package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.business.enums.argo.BizRoleEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.CreditStatusEnum;
import com.zpmc.ztos.infra.base.business.enums.framework.LifeCycleStateEnum;
import com.zpmc.ztos.infra.base.business.model.ContactInfo;
import com.zpmc.ztos.infra.base.business.model.EntitySet;
import com.zpmc.ztos.infra.base.business.model.MasterBizUnit;
import com.zpmc.ztos.infra.base.business.model.ReferenceEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * 审查过的业务单元
 *
 * @author yejun
 */
@Data
public abstract class ScopedBizUnitDO extends ReferenceEntity
implements Serializable {
    private Long bzuGkey;
    private String bzuId;
    private String bzuName;
    private BizRoleEnum bzuRole;
    private String bzuScac;
    private String bzuBic;
    private Boolean bzuIsEqOwner;
    private Boolean bzuIsEqOperator;
    private LifeCycleStateEnum bzuLifeCycleState;
    private CreditStatusEnum bzuCreditStatus;
    private Double bzuPerUnitGuaranteeLimit;
    private String bzuNotes;
    private Date bzuCreated;
    private String bzuCreator;
    private Date bzuChanged;
    private String bzuChanger;
    private EntitySet bzuScope;
    private MasterBizUnit bzuBizu;
    private Set bzuRepresentions;
    private Set bzuBizgrpBizUnit;
    private Set bzuGrpSet;
    private ContactInfo bzuCtct;

    public Serializable getPrimaryKey() {
        return this.getBzuGkey();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        Long id = this.getBzuGkey();
        if (id == null) {
            return false;
        }
        if (!(other instanceof ScopedBizUnitDO)) {
            return false;
        }
        ScopedBizUnitDO that = (ScopedBizUnitDO)other;
        return ((Object)id).equals(that.getBzuGkey());
    }

    public int hashCode() {
        Long id = this.getBzuGkey();
        return id == null ? System.identityHashCode(this) : ((Object)id).hashCode();
    }

    public Long getBzuGkey() {
        return this.bzuGkey;
    }

    protected void setBzuGkey(Long bzuGkey) {
        this.bzuGkey = bzuGkey;
    }

    public String getBzuId() {
        return this.bzuId;
    }

    protected void setBzuId(String bzuId) {
        this.bzuId = bzuId;
    }

    public String getBzuName() {
        return this.bzuName;
    }

    protected void setBzuName(String bzuName) {
        this.bzuName = bzuName;
    }

    public BizRoleEnum getBzuRole() {
        return this.bzuRole;
    }

    protected void setBzuRole(BizRoleEnum bzuRole) {
        this.bzuRole = bzuRole;
    }

    public String getBzuScac() {
        return this.bzuScac;
    }

    protected void setBzuScac(String bzuScac) {
        this.bzuScac = bzuScac;
    }

    public String getBzuBic() {
        return this.bzuBic;
    }

    protected void setBzuBic(String bzuBic) {
        this.bzuBic = bzuBic;
    }

    public Boolean getBzuIsEqOwner() {
        return this.bzuIsEqOwner;
    }

    protected void setBzuIsEqOwner(Boolean bzuIsEqOwner) {
        this.bzuIsEqOwner = bzuIsEqOwner;
    }

    public Boolean getBzuIsEqOperator() {
        return this.bzuIsEqOperator;
    }

    protected void setBzuIsEqOperator(Boolean bzuIsEqOperator) {
        this.bzuIsEqOperator = bzuIsEqOperator;
    }

    public LifeCycleStateEnum getBzuLifeCycleState() {
        return this.bzuLifeCycleState;
    }

    public void setBzuLifeCycleState(LifeCycleStateEnum bzuLifeCycleState) {
        this.bzuLifeCycleState = bzuLifeCycleState;
    }

    public CreditStatusEnum getBzuCreditStatus() {
        return this.bzuCreditStatus;
    }

    protected void setBzuCreditStatus(CreditStatusEnum bzuCreditStatus) {
        this.bzuCreditStatus = bzuCreditStatus;
    }

    public Double getBzuPerUnitGuaranteeLimit() {
        return this.bzuPerUnitGuaranteeLimit;
    }

    protected void setBzuPerUnitGuaranteeLimit(Double bzuPerUnitGuaranteeLimit) {
        this.bzuPerUnitGuaranteeLimit = bzuPerUnitGuaranteeLimit;
    }

    public String getBzuNotes() {
        return this.bzuNotes;
    }

    protected void setBzuNotes(String bzuNotes) {
        this.bzuNotes = bzuNotes;
    }

    public Date getBzuCreated() {
        return this.bzuCreated;
    }

    protected void setBzuCreated(Date bzuCreated) {
        this.bzuCreated = bzuCreated;
    }

    public String getBzuCreator() {
        return this.bzuCreator;
    }

    protected void setBzuCreator(String bzuCreator) {
        this.bzuCreator = bzuCreator;
    }

    public Date getBzuChanged() {
        return this.bzuChanged;
    }

    protected void setBzuChanged(Date bzuChanged) {
        this.bzuChanged = bzuChanged;
    }

    public String getBzuChanger() {
        return this.bzuChanger;
    }

    protected void setBzuChanger(String bzuChanger) {
        this.bzuChanger = bzuChanger;
    }

    public EntitySet getBzuScope() {
        return this.bzuScope;
    }

    protected void setBzuScope(EntitySet bzuScope) {
        this.bzuScope = bzuScope;
    }

    public MasterBizUnit getBzuBizu() {
        return this.bzuBizu;
    }

    protected void setBzuBizu(MasterBizUnit bzuBizu) {
        this.bzuBizu = bzuBizu;
    }

    public Set getBzuRepresentions() {
        return this.bzuRepresentions;
    }

    public void setBzuRepresentions(Set bzuRepresentions) {
        this.bzuRepresentions = bzuRepresentions;
    }

    public Set getBzuBizgrpBizUnit() {
        return this.bzuBizgrpBizUnit;
    }

    protected void setBzuBizgrpBizUnit(Set bzuBizgrpBizUnit) {
        this.bzuBizgrpBizUnit = bzuBizgrpBizUnit;
    }

    public Set getBzuGrpSet() {
        return this.bzuGrpSet;
    }

    protected void setBzuGrpSet(Set bzuGrpSet) {
        this.bzuGrpSet = bzuGrpSet;
    }

    public ContactInfo getBzuCtct() {
        return this.bzuCtct;
    }

    public void setBzuCtct(ContactInfo bzuCtct) {
        this.bzuCtct = bzuCtct;
    }
}
