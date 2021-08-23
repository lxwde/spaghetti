package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.business.model.ScopedBizUnit;

import java.io.Serializable;
import java.util.Set;

public class AgentDO extends ScopedBizUnit implements Serializable {
    private String agentAliasId;
    private Set agentRepresentations;

    public String getAgentAliasId() {
        return this.agentAliasId;
    }

    public void setAgentAliasId(String agentAliasId) {
        this.agentAliasId = agentAliasId;
    }

    public Set getAgentRepresentations() {
        return this.agentRepresentations;
    }

    public void setAgentRepresentations(Set agentRepresentations) {
        this.agentRepresentations = agentRepresentations;
    }
}
