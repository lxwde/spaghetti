package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.business.enums.argo.BizRoleEnum;
import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;
import com.zpmc.ztos.infra.base.common.scopes.Operator;

import java.io.Serializable;

public class ArgoBizRolePrivilegeDO extends DatabaseEntity implements Serializable {
    private Long roprvGkey;
    private String roprvPrivId;
    private BizRoleEnum roprvRole;
    private Operator roprvOperator;

    public Serializable getPrimaryKey() {
        return this.getRoprvGkey();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        Long id = this.getRoprvGkey();
        if (id == null) {
            return false;
        }
        if (!(other instanceof ArgoBizRolePrivilegeDO)) {
            return false;
        }
        ArgoBizRolePrivilegeDO that = (ArgoBizRolePrivilegeDO)other;
        return ((Object)id).equals(that.getRoprvGkey());
    }

    public int hashCode() {
        Long id = this.getRoprvGkey();
        return id == null ? System.identityHashCode(this) : ((Object)id).hashCode();
    }

    public Long getRoprvGkey() {
        return this.roprvGkey;
    }

    protected void setRoprvGkey(Long roprvGkey) {
        this.roprvGkey = roprvGkey;
    }

    public String getRoprvPrivId() {
        return this.roprvPrivId;
    }

    protected void setRoprvPrivId(String roprvPrivId) {
        this.roprvPrivId = roprvPrivId;
    }

    public BizRoleEnum getRoprvRole() {
        return this.roprvRole;
    }

    protected void setRoprvRole(BizRoleEnum roprvRole) {
        this.roprvRole = roprvRole;
    }

    public Operator getRoprvOperator() {
        return this.roprvOperator;
    }

    protected void setRoprvOperator(Operator roprvOperator) {
        this.roprvOperator = roprvOperator;
    }

}
