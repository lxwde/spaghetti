package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.business.enums.core.GroupTypeEnum;
import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;

import java.io.Serializable;
import java.util.Set;

public class OrgGroupDO extends DatabaseEntity implements Serializable {

    private Long grpGkey;
    private String grpName;
    private String grpDescription;
    private GroupTypeEnum grpType;
    private Set grpOrgList;
    private Set grpBuserList;

    @Override
    public Serializable getPrimaryKey() {
        return this.getGrpGkey();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        Long id = this.getGrpGkey();
        if (id == null) {
            return false;
        }
        if (!(other instanceof OrgGroupDO)) {
            return false;
        }
        OrgGroupDO that = (OrgGroupDO)other;
        return ((Object)id).equals(that.getGrpGkey());
    }

    public int hashCode() {
        Long id = this.getGrpGkey();
        return id == null ? System.identityHashCode(this) : ((Object)id).hashCode();
    }

    public Long getGrpGkey() {
        return this.grpGkey;
    }

    public void setGrpGkey(Long grpGkey) {
        this.grpGkey = grpGkey;
    }

    public String getGrpName() {
        return this.grpName;
    }

    public void setGrpName(String grpName) {
        this.grpName = grpName;
    }

    public String getGrpDescription() {
        return this.grpDescription;
    }

    public void setGrpDescription(String grpDescription) {
        this.grpDescription = grpDescription;
    }

    public GroupTypeEnum getGrpType() {
        return this.grpType;
    }

    public void setGrpType(GroupTypeEnum grpType) {
        this.grpType = grpType;
    }

    public Set getGrpOrgList() {
        return this.grpOrgList;
    }

    public void setGrpOrgList(Set grpOrgList) {
        this.grpOrgList = grpOrgList;
    }

    public Set getGrpBuserList() {
        return this.grpBuserList;
    }

    public void setGrpBuserList(Set grpBuserList) {
        this.grpBuserList = grpBuserList;
    }
}
