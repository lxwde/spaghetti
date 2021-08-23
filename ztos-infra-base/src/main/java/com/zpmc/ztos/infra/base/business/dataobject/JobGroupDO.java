package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;

import java.io.Serializable;
import java.util.Set;

public class JobGroupDO extends DatabaseEntity implements Serializable {
    private Long jobgroupGkey;
    private String jobgroupName;
    private String jobgroupDescription;
 //   private JobGroupLoadBalancerSchemeEnum jobgroupLoadBalanceScheme;
    private Set jobgroupNodes;

    @Override
    public Serializable getPrimaryKey() {
        return this.getJobgroupGkey();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        Long id = this.getJobgroupGkey();
        if (id == null) {
            return false;
        }
        if (!(other instanceof JobGroupDO)) {
            return false;
        }
        JobGroupDO that = (JobGroupDO)other;
        return ((Object)id).equals(that.getJobgroupGkey());
    }

    public int hashCode() {
        Long id = this.getJobgroupGkey();
        return id == null ? System.identityHashCode(this) : ((Object)id).hashCode();
    }

    public Long getJobgroupGkey() {
        return this.jobgroupGkey;
    }

    public void setJobgroupGkey(Long jobgroupGkey) {
        this.jobgroupGkey = jobgroupGkey;
    }

    public String getJobgroupName() {
        return this.jobgroupName;
    }

    public void setJobgroupName(String jobgroupName) {
        this.jobgroupName = jobgroupName;
    }

    public String getJobgroupDescription() {
        return this.jobgroupDescription;
    }

    public void setJobgroupDescription(String jobgroupDescription) {
        this.jobgroupDescription = jobgroupDescription;
    }

//    public JobGroupLoadBalancerSchemeEnum getJobgroupLoadBalanceScheme() {
//        return this.jobgroupLoadBalanceScheme;
//    }
//
//    public void setJobgroupLoadBalanceScheme(JobGroupLoadBalancerSchemeEnum jobgroupLoadBalanceScheme) {
//        this.jobgroupLoadBalanceScheme = jobgroupLoadBalanceScheme;
//    }

    public Set getJobgroupNodes() {
        return this.jobgroupNodes;
    }

    public void setJobgroupNodes(Set jobgroupNodes) {
        this.jobgroupNodes = jobgroupNodes;
    }
}
