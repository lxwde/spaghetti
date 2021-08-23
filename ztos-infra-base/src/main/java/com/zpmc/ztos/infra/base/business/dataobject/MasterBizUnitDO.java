package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.business.enums.argo.BizRoleEnum;
import com.zpmc.ztos.infra.base.business.enums.framework.LifeCycleStateEnum;
import com.zpmc.ztos.infra.base.business.model.ContactInfo;
import com.zpmc.ztos.infra.base.business.model.Organization;
import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 *
 *
 * 主要业务单元
 *
 * @author yejun
 */
@Data
public abstract class MasterBizUnitDO extends DatabaseEntity
        implements Serializable {
    private Long bizuGkey;
    private String bizuId;
    private LifeCycleStateEnum bizuLifeCycleState;
    private String bizuName;
    private BizRoleEnum bizuRole;
    private String bizuScac;
    private String bizuBic;
    private Boolean bizuIsEqOwner;
    private Boolean bizuIsEqOperator;
    private Date bizuCreated;
    private String bizuCreator;
    private Date bizuChanged;
    private String bizuChanger;
    private Organization bizuOrg;
    private ContactInfo bizuCtct;

    public Serializable getPrimaryKey() {
        return this.getBizuGkey();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        Long id = this.getBizuGkey();
        if (id == null) {
            return false;
        }
        if (!(other instanceof MasterBizUnitDO)) {
            return false;
        }
        MasterBizUnitDO that = (MasterBizUnitDO)other;
        return ((Object)id).equals(that.getBizuGkey());
    }

    public int hashCode() {
        Long id = this.getBizuGkey();
        return id == null ? System.identityHashCode(this) : ((Object)id).hashCode();
    }

    public Long getBizuGkey() {
        return this.bizuGkey;
    }

    protected void setBizuGkey(Long bizuGkey) {
        this.bizuGkey = bizuGkey;
    }

    public String getBizuId() {
        return this.bizuId;
    }

    protected void setBizuId(String bizuId) {
        this.bizuId = bizuId;
    }

    public LifeCycleStateEnum getBizuLifeCycleState() {
        return this.bizuLifeCycleState;
    }

    public void setBizuLifeCycleState(LifeCycleStateEnum bizuLifeCycleState) {
        this.bizuLifeCycleState = bizuLifeCycleState;
    }

    public String getBizuName() {
        return this.bizuName;
    }

    protected void setBizuName(String bizuName) {
        this.bizuName = bizuName;
    }

    public BizRoleEnum getBizuRole() {
        return this.bizuRole;
    }

    protected void setBizuRole(BizRoleEnum bizuRole) {
        this.bizuRole = bizuRole;
    }

    public String getBizuScac() {
        return this.bizuScac;
    }

    protected void setBizuScac(String bizuScac) {
        this.bizuScac = bizuScac;
    }

    public String getBizuBic() {
        return this.bizuBic;
    }

    protected void setBizuBic(String bizuBic) {
        this.bizuBic = bizuBic;
    }

    public Boolean getBizuIsEqOwner() {
        return this.bizuIsEqOwner;
    }

    public void setBizuIsEqOwner(Boolean bizuIsEqOwner) {
        this.bizuIsEqOwner = bizuIsEqOwner;
    }

    public Boolean getBizuIsEqOperator() {
        return this.bizuIsEqOperator;
    }

    public void setBizuIsEqOperator(Boolean bizuIsEqOperator) {
        this.bizuIsEqOperator = bizuIsEqOperator;
    }

    public Date getBizuCreated() {
        return this.bizuCreated;
    }

    protected void setBizuCreated(Date bizuCreated) {
        this.bizuCreated = bizuCreated;
    }

    public String getBizuCreator() {
        return this.bizuCreator;
    }

    protected void setBizuCreator(String bizuCreator) {
        this.bizuCreator = bizuCreator;
    }

    public Date getBizuChanged() {
        return this.bizuChanged;
    }

    protected void setBizuChanged(Date bizuChanged) {
        this.bizuChanged = bizuChanged;
    }

    public String getBizuChanger() {
        return this.bizuChanger;
    }

    protected void setBizuChanger(String bizuChanger) {
        this.bizuChanger = bizuChanger;
    }

    public Organization getBizuOrg() {
        return this.bizuOrg;
    }

    protected void setBizuOrg(Organization bizuOrg) {
        this.bizuOrg = bizuOrg;
    }

    public ContactInfo getBizuCtct() {
        return this.bizuCtct;
    }

    public void setBizuCtct(ContactInfo bizuCtct) {
        this.bizuCtct = bizuCtct;
    }


}
