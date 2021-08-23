package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.business.model.ScopedBizUnit;
import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;
import com.zpmc.ztos.infra.base.common.security.Agent;

import java.io.Serializable;
import java.util.Date;

public class AgentRepresentationDO extends DatabaseEntity implements Serializable {
    private Long agentrepGkey;
    private Date agentrepStartDate;
    private Date agentrepEndDate;
    private Date agentrepCreated;
    private String agentrepCreator;
    private Date agentrepChanged;
    private String agentrepChanger;
    private Agent agentrepAgent;
    private ScopedBizUnit agentrepScopedBusinessUnit;

    public Serializable getPrimaryKey() {
        return this.getAgentrepGkey();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        Long id = this.getAgentrepGkey();
        if (id == null) {
            return false;
        }
        if (!(other instanceof AgentRepresentationDO)) {
            return false;
        }
        AgentRepresentationDO that = (AgentRepresentationDO)other;
        return ((Object)id).equals(that.getAgentrepGkey());
    }

    public int hashCode() {
        Long id = this.getAgentrepGkey();
        return id == null ? System.identityHashCode(this) : ((Object)id).hashCode();
    }

    public Long getAgentrepGkey() {
        return this.agentrepGkey;
    }

    protected void setAgentrepGkey(Long agentrepGkey) {
        this.agentrepGkey = agentrepGkey;
    }

    public Date getAgentrepStartDate() {
        return this.agentrepStartDate;
    }

    protected void setAgentrepStartDate(Date agentrepStartDate) {
        this.agentrepStartDate = agentrepStartDate;
    }

    public Date getAgentrepEndDate() {
        return this.agentrepEndDate;
    }

    public void setAgentrepEndDate(Date agentrepEndDate) {
        this.agentrepEndDate = agentrepEndDate;
    }

    public Date getAgentrepCreated() {
        return this.agentrepCreated;
    }

    protected void setAgentrepCreated(Date agentrepCreated) {
        this.agentrepCreated = agentrepCreated;
    }

    public String getAgentrepCreator() {
        return this.agentrepCreator;
    }

    protected void setAgentrepCreator(String agentrepCreator) {
        this.agentrepCreator = agentrepCreator;
    }

    public Date getAgentrepChanged() {
        return this.agentrepChanged;
    }

    protected void setAgentrepChanged(Date agentrepChanged) {
        this.agentrepChanged = agentrepChanged;
    }

    public String getAgentrepChanger() {
        return this.agentrepChanger;
    }

    protected void setAgentrepChanger(String agentrepChanger) {
        this.agentrepChanger = agentrepChanger;
    }

    public Agent getAgentrepAgent() {
        return this.agentrepAgent;
    }

    protected void setAgentrepAgent(Agent agentrepAgent) {
        this.agentrepAgent = agentrepAgent;
    }

    public ScopedBizUnit getAgentrepScopedBusinessUnit() {
        return this.agentrepScopedBusinessUnit;
    }

    protected void setAgentrepScopedBusinessUnit(ScopedBizUnit agentrepScopedBusinessUnit) {
        this.agentrepScopedBusinessUnit = agentrepScopedBusinessUnit;
    }
}
