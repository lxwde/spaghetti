package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;
import com.zpmc.ztos.infra.base.common.security.SecRole;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class SecRoleDO extends DatabaseEntity implements Serializable {
    private Long roleGkey;
    private String roleSecName;
    private String roleDescription;
    private Date roleCreated;
    private String roleCreator;
    private Date roleChanged;
    private String roleChanger;
    private Boolean roleIsDelegated;
    private SecRole roleParent;
    private Set roleBuserList;
    private List rolePrivilegesList;

    @Override
    public Serializable getPrimaryKey() {
        return this.getRoleGkey();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        Long id = this.getRoleGkey();
        if (id == null) {
            return false;
        }
        if (!(other instanceof SecRoleDO)) {
            return false;
        }
        SecRoleDO that = (SecRoleDO)other;
        return ((Object)id).equals(that.getRoleGkey());
    }

    public int hashCode() {
        Long id = this.getRoleGkey();
        return id == null ? System.identityHashCode(this) : ((Object)id).hashCode();
    }

    public Long getRoleGkey() {
        return this.roleGkey;
    }

    public void setRoleGkey(Long roleGkey) {
        this.roleGkey = roleGkey;
    }

    public String getRoleSecName() {
        return this.roleSecName;
    }

    public void setRoleSecName(String roleSecName) {
        this.roleSecName = roleSecName;
    }

    public String getRoleDescription() {
        return this.roleDescription;
    }

    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }

    public Date getRoleCreated() {
        return this.roleCreated;
    }

    public void setRoleCreated(Date roleCreated) {
        this.roleCreated = roleCreated;
    }

    public String getRoleCreator() {
        return this.roleCreator;
    }

    public void setRoleCreator(String roleCreator) {
        this.roleCreator = roleCreator;
    }

    public Date getRoleChanged() {
        return this.roleChanged;
    }

    public void setRoleChanged(Date roleChanged) {
        this.roleChanged = roleChanged;
    }

    public String getRoleChanger() {
        return this.roleChanger;
    }

    public void setRoleChanger(String roleChanger) {
        this.roleChanger = roleChanger;
    }

    public Boolean getRoleIsDelegated() {
        return this.roleIsDelegated;
    }

    public void setRoleIsDelegated(Boolean roleIsDelegated) {
        this.roleIsDelegated = roleIsDelegated;
    }

    public SecRole getRoleParent() {
        return this.roleParent;
    }

    public void setRoleParent(SecRole roleParent) {
        this.roleParent = roleParent;
    }

    public Set getRoleBuserList() {
        return this.roleBuserList;
    }

    public void setRoleBuserList(Set roleBuserList) {
        this.roleBuserList = roleBuserList;
    }

    public List getRolePrivilegesList() {
        return this.rolePrivilegesList;
    }

    public void setRolePrivilegesList(List rolePrivilegesList) {
        this.rolePrivilegesList = rolePrivilegesList;
    }
}
