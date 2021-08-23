package com.zpmc.ztos.infra.base.common.security;

import com.zpmc.ztos.infra.base.business.dataobject.AgentRepresentationDO;
import com.zpmc.ztos.infra.base.business.interfaces.IArgoRefField;
import com.zpmc.ztos.infra.base.business.interfaces.IDomainQuery;
import com.zpmc.ztos.infra.base.business.interfaces.IMetafieldId;
import com.zpmc.ztos.infra.base.business.model.ScopedBizUnit;
import com.zpmc.ztos.infra.base.business.predicate.PredicateFactory;
import com.zpmc.ztos.infra.base.common.model.Roastery;
import com.zpmc.ztos.infra.base.common.utils.TimeUtils;
import com.zpmc.ztos.infra.base.utils.QueryUtils;

import java.util.Date;
import java.util.HashSet;

public class AgentRepresentation extends AgentRepresentationDO {
    public static AgentRepresentation findOrCreate(Agent inAgent, ScopedBizUnit inBizUnit) {
        AgentRepresentation rep = AgentRepresentation.find(inAgent, inBizUnit);
        if (rep == null) {
            rep = AgentRepresentation.create(inAgent, inBizUnit);
        }
        return rep;
    }

    public static AgentRepresentation find(Agent inAgent, ScopedBizUnit inBizUnit) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"AgentRepresentation").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoRefField.AGENTREP_AGENT, (Object)inAgent.getBzuGkey())).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoRefField.AGENTREP_SCOPED_BUSINESS_UNIT, (Object)inBizUnit.getBzuGkey()));
        return (AgentRepresentation) Roastery.getHibernateApi().getUniqueEntityByDomainQuery(dq);
    }

    public static AgentRepresentation create(Agent inAgent, ScopedBizUnit inBizUnit) {
        AgentRepresentation rep = new AgentRepresentation();
        rep.setAgentrepAgent(inAgent);
        rep.setAgentrepScopedBusinessUnit(inBizUnit);
        Roastery.getHibernateApi().save((Object)rep);
        if (inAgent.getAgentRepresentations() == null) {
            inAgent.setAgentRepresentations(new HashSet());
        }
        if (inBizUnit.getBzuRepresentions() == null) {
            inBizUnit.setBzuRepresentions(new HashSet());
        }
        inAgent.getAgentRepresentations().add(rep);
        inBizUnit.getBzuRepresentions().add(rep);
        return rep;
    }

    public boolean isActive() {
        boolean active = true;
        if (this.getAgentrepStartDate() != null && this.getAgentrepStartDate().after(new Date(TimeUtils.getCurrentTimeMillis()))) {
            active = false;
        }
        if (this.getAgentrepEndDate() != null && this.getAgentrepEndDate().before(new Date(TimeUtils.getCurrentTimeMillis()))) {
            active = false;
        }
        return active;
    }
}
