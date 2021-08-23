package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

public abstract class ClusterServiceDO extends DatabaseEntity implements Serializable {
    private Long clusterServiceGkey;
    private String clusterServiceKey;
    private Long clusterServiceScopeLevel;
    private String clusterServiceScopeGkey;
    private String clusterServiceType;
    private String clusterServiceName;
    private String clusterServiceStatus;
    private String clusterServiceIPAddress;
    private String clusterServiceMacAddress;
    private Integer clusterServicePort;
    private String clusterServiceVersion;
    private String clusterServiceInfo;
    private Date clusterServiceStartupTime;
    private Date clusterServiceHeartBeatTime;
    private Date clusterServiceActivityTime;
    private Date clusterServiceShutdownTime;
    private String clusterServiceUserName;
    private Long clusterServiceMemoryUsed;
    private Long clusterServiceMemoryMax;
    private Long clusterServiceCpuCount;
    private Double clusterServiceCpuLoad;
    private Set clusterServiceGroups;

    @Override
    public Serializable getPrimaryKey() {
        return this.getClusterServiceGkey();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        Long id = this.getClusterServiceGkey();
        if (id == null) {
            return false;
        }
        if (!(other instanceof ClusterServiceDO)) {
            return false;
        }
        ClusterServiceDO that = (ClusterServiceDO)other;
        return ((Object)id).equals(that.getClusterServiceGkey());
    }

    public int hashCode() {
        Long id = this.getClusterServiceGkey();
        return id == null ? System.identityHashCode(this) : ((Object)id).hashCode();
    }

    public Long getClusterServiceGkey() {
        return this.clusterServiceGkey;
    }

    public void setClusterServiceGkey(Long clusterServiceGkey) {
        this.clusterServiceGkey = clusterServiceGkey;
    }

    public String getClusterServiceKey() {
        return this.clusterServiceKey;
    }

    public void setClusterServiceKey(String clusterServiceKey) {
        this.clusterServiceKey = clusterServiceKey;
    }

    public Long getClusterServiceScopeLevel() {
        return this.clusterServiceScopeLevel;
    }

    public void setClusterServiceScopeLevel(Long clusterServiceScopeLevel) {
        this.clusterServiceScopeLevel = clusterServiceScopeLevel;
    }

    public String getClusterServiceScopeGkey() {
        return this.clusterServiceScopeGkey;
    }

    public void setClusterServiceScopeGkey(String clusterServiceScopeGkey) {
        this.clusterServiceScopeGkey = clusterServiceScopeGkey;
    }

    public String getClusterServiceType() {
        return this.clusterServiceType;
    }

    public void setClusterServiceType(String clusterServiceType) {
        this.clusterServiceType = clusterServiceType;
    }

    public String getClusterServiceName() {
        return this.clusterServiceName;
    }

    public void setClusterServiceName(String clusterServiceName) {
        this.clusterServiceName = clusterServiceName;
    }

    public String getClusterServiceStatus() {
        return this.clusterServiceStatus;
    }

    public void setClusterServiceStatus(String clusterServiceStatus) {
        this.clusterServiceStatus = clusterServiceStatus;
    }

    public String getClusterServiceIPAddress() {
        return this.clusterServiceIPAddress;
    }

    public void setClusterServiceIPAddress(String clusterServiceIPAddress) {
        this.clusterServiceIPAddress = clusterServiceIPAddress;
    }

    public String getClusterServiceMacAddress() {
        return this.clusterServiceMacAddress;
    }

    public void setClusterServiceMacAddress(String clusterServiceMacAddress) {
        this.clusterServiceMacAddress = clusterServiceMacAddress;
    }

    public Integer getClusterServicePort() {
        return this.clusterServicePort;
    }

    public void setClusterServicePort(Integer clusterServicePort) {
        this.clusterServicePort = clusterServicePort;
    }

    public String getClusterServiceVersion() {
        return this.clusterServiceVersion;
    }

    public void setClusterServiceVersion(String clusterServiceVersion) {
        this.clusterServiceVersion = clusterServiceVersion;
    }

    public String getClusterServiceInfo() {
        return this.clusterServiceInfo;
    }

    public void setClusterServiceInfo(String clusterServiceInfo) {
        this.clusterServiceInfo = clusterServiceInfo;
    }

    public Date getClusterServiceStartupTime() {
        return this.clusterServiceStartupTime;
    }

    public void setClusterServiceStartupTime(Date clusterServiceStartupTime) {
        this.clusterServiceStartupTime = clusterServiceStartupTime;
    }

    public Date getClusterServiceHeartBeatTime() {
        return this.clusterServiceHeartBeatTime;
    }

    public void setClusterServiceHeartBeatTime(Date clusterServiceHeartBeatTime) {
        this.clusterServiceHeartBeatTime = clusterServiceHeartBeatTime;
    }

    public Date getClusterServiceActivityTime() {
        return this.clusterServiceActivityTime;
    }

    public void setClusterServiceActivityTime(Date clusterServiceActivityTime) {
        this.clusterServiceActivityTime = clusterServiceActivityTime;
    }

    public Date getClusterServiceShutdownTime() {
        return this.clusterServiceShutdownTime;
    }

    public void setClusterServiceShutdownTime(Date clusterServiceShutdownTime) {
        this.clusterServiceShutdownTime = clusterServiceShutdownTime;
    }

    public String getClusterServiceUserName() {
        return this.clusterServiceUserName;
    }

    public void setClusterServiceUserName(String clusterServiceUserName) {
        this.clusterServiceUserName = clusterServiceUserName;
    }

    public Long getClusterServiceMemoryUsed() {
        return this.clusterServiceMemoryUsed;
    }

    public void setClusterServiceMemoryUsed(Long clusterServiceMemoryUsed) {
        this.clusterServiceMemoryUsed = clusterServiceMemoryUsed;
    }

    public Long getClusterServiceMemoryMax() {
        return this.clusterServiceMemoryMax;
    }

    public void setClusterServiceMemoryMax(Long clusterServiceMemoryMax) {
        this.clusterServiceMemoryMax = clusterServiceMemoryMax;
    }

    public Long getClusterServiceCpuCount() {
        return this.clusterServiceCpuCount;
    }

    public void setClusterServiceCpuCount(Long clusterServiceCpuCount) {
        this.clusterServiceCpuCount = clusterServiceCpuCount;
    }

    public Double getClusterServiceCpuLoad() {
        return this.clusterServiceCpuLoad;
    }

    public void setClusterServiceCpuLoad(Double clusterServiceCpuLoad) {
        this.clusterServiceCpuLoad = clusterServiceCpuLoad;
    }

    public Set getClusterServiceGroups() {
        return this.clusterServiceGroups;
    }

    public void setClusterServiceGroups(Set clusterServiceGroups) {
        this.clusterServiceGroups = clusterServiceGroups;
    }
}
