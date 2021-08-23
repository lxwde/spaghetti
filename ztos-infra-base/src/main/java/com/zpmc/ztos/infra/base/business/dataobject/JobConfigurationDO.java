package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;

import java.io.Serializable;

public class JobConfigurationDO extends DatabaseEntity implements Serializable {
    private Long jobconfigGkey;
    private String jobconfigId;
    private Long jobconfigMinCores;
    private Long jobconfigMinMemory;
    private Long jobconfigRepeatCount;
    private Long jobconfigRepeatInterval;
    private Long jobconfigTimeout;
    private String jobconfigDescription;

    public Serializable getPrimaryKey() {
        return this.getJobconfigGkey();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        Long id = this.getJobconfigGkey();
        if (id == null) {
            return false;
        }
        if (!(other instanceof JobConfigurationDO)) {
            return false;
        }
        JobConfigurationDO that = (JobConfigurationDO)other;
        return ((Object)id).equals(that.getJobconfigGkey());
    }

    public int hashCode() {
        Long id = this.getJobconfigGkey();
        return id == null ? System.identityHashCode(this) : ((Object)id).hashCode();
    }

    public Long getJobconfigGkey() {
        return this.jobconfigGkey;
    }

    public void setJobconfigGkey(Long jobconfigGkey) {
        this.jobconfigGkey = jobconfigGkey;
    }

    public String getJobconfigId() {
        return this.jobconfigId;
    }

    public void setJobconfigId(String jobconfigId) {
        this.jobconfigId = jobconfigId;
    }

    public Long getJobconfigMinCores() {
        return this.jobconfigMinCores;
    }

    public void setJobconfigMinCores(Long jobconfigMinCores) {
        this.jobconfigMinCores = jobconfigMinCores;
    }

    public Long getJobconfigMinMemory() {
        return this.jobconfigMinMemory;
    }

    public void setJobconfigMinMemory(Long jobconfigMinMemory) {
        this.jobconfigMinMemory = jobconfigMinMemory;
    }

    public Long getJobconfigRepeatCount() {
        return this.jobconfigRepeatCount;
    }

    public void setJobconfigRepeatCount(Long jobconfigRepeatCount) {
        this.jobconfigRepeatCount = jobconfigRepeatCount;
    }

    public Long getJobconfigRepeatInterval() {
        return this.jobconfigRepeatInterval;
    }

    public void setJobconfigRepeatInterval(Long jobconfigRepeatInterval) {
        this.jobconfigRepeatInterval = jobconfigRepeatInterval;
    }

    public Long getJobconfigTimeout() {
        return this.jobconfigTimeout;
    }

    public void setJobconfigTimeout(Long jobconfigTimeout) {
        this.jobconfigTimeout = jobconfigTimeout;
    }

    public String getJobconfigDescription() {
        return this.jobconfigDescription;
    }

    public void setJobconfigDescription(String jobconfigDescription) {
        this.jobconfigDescription = jobconfigDescription;
    }

}
