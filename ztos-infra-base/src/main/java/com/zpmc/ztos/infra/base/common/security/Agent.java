package com.zpmc.ztos.infra.base.common.security;

import com.zpmc.ztos.infra.base.business.dataobject.AgentDO;
import com.zpmc.ztos.infra.base.business.enums.argo.BizRoleEnum;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.business.model.BizFieldList;
import com.zpmc.ztos.infra.base.business.model.ScopedBizUnit;
import com.zpmc.ztos.infra.base.business.predicate.PredicateFactory;
import com.zpmc.ztos.infra.base.common.configs.ArgoConfig;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.common.exceptions.BizFailure;
import com.zpmc.ztos.infra.base.common.helps.ContextHelper;
import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;
import com.zpmc.ztos.infra.base.utils.QueryUtils;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

public class Agent extends AgentDO implements ISecurable {
    public Agent() {
        this.setBzuRole(BizRoleEnum.AGENT);
    }

    @Override
    public BizFieldList getBizFieldList() {
        BizFieldList list = new BizFieldList();
        list.add(IArgoRefField.BZU_GKEY, BizRoleEnum.AGENT);
        return list;
    }

    public static Agent findOrCreateAgent(String inId) {
        ScopedBizUnit scopedBizUnit = ScopedBizUnit.findScopedBizUnit(inId, BizRoleEnum.AGENT);
        Agent agent = Agent.resolveAgentFromScopedBizUnit(scopedBizUnit);
        if (agent == null) {
            agent = Agent.createAgent(inId);
        }
        return agent;
    }

    public static ScopedBizUnit findOrCreateAgentProxy(String inId) {
        ScopedBizUnit agent = ScopedBizUnit.findScopedBizUnitProxy(inId, BizRoleEnum.AGENT);
        if (agent == null) {
            agent = Agent.createAgent(inId);
        }
        return agent;
    }

    public static Agent findOrCreateAgent(String inId, String inName) {
        ScopedBizUnit scopedBizUnit = ScopedBizUnit.findScopedBizUnit(inId, BizRoleEnum.AGENT);
        Agent agent = Agent.resolveAgentFromScopedBizUnit(scopedBizUnit);
        if (agent == null) {
            agent = Agent.createAgent(inId);
            agent.setBzuName(inName);
        }
        return agent;
    }

    public static Agent createAgent(String inId) {
        Agent agent = new Agent();
        agent.setBzuId(inId);
        agent.initializeDefaultProperties();
        HibernateApi.getInstance().save((Object)agent);
        return agent;
    }

    public static Agent findAgent(String inId) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"Agent").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoRefField.BZU_ID, (Object)inId));
        return (Agent) HibernateApi.getInstance().getUniqueEntityByDomainQuery(dq);
    }

    public static Agent findAgentById(String inId) {
        ScopedBizUnit scopedBizUnit = ScopedBizUnit.findScopedBizUnit(inId, BizRoleEnum.AGENT);
        return Agent.resolveAgentFromScopedBizUnit(scopedBizUnit);
    }

    public static Agent findAgentByName(String inName) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"Agent").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoRefField.BZU_NAME, (Object)inName));
        return (Agent) HibernateApi.getInstance().getUniqueEntityByDomainQuery(dq);
    }

    public static Agent findAgentProxyByName(String inName) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"Agent").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoRefField.BZU_NAME, (Object)inName)).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoRefField.BZU_ROLE, (Object)((Object) BizRoleEnum.AGENT)));
        Serializable[] bzuGkey = HibernateApi.getInstance().findPrimaryKeysByDomainQuery(dq);
        if (bzuGkey == null || bzuGkey.length == 0) {
            return null;
        }
        if (bzuGkey.length == 1) {
            return (Agent) HibernateApi.getInstance().load(Agent.class, bzuGkey[0]);
        }
        throw BizFailure.create((IPropertyKey) IFrameworkPropertyKeys.FRAMEWORK__NON_UNIQUE_RESULT, null, (Object)new Long(bzuGkey.length), (Object)dq);
    }

    public static Agent findAgentProxyById(String inId) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"Agent").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoRefField.BZU_ID, (Object)inId)).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoRefField.BZU_ROLE, (Object)((Object) BizRoleEnum.AGENT)));
        Serializable[] bzuGkey = HibernateApi.getInstance().findPrimaryKeysByDomainQuery(dq);
        if (bzuGkey == null || bzuGkey.length == 0) {
            return null;
        }
        if (bzuGkey.length == 1) {
            return (Agent) HibernateApi.getInstance().load(Agent.class, bzuGkey[0]);
        }
        throw BizFailure.create((IPropertyKey) IFrameworkPropertyKeys.FRAMEWORK__NON_UNIQUE_RESULT, null, (Object)new Long(bzuGkey.length), (Object)dq);
    }

    public AgentRepresentation addAgentRepresentation(ScopedBizUnit inBizUnit) {
        return AgentRepresentation.findOrCreate(this, inBizUnit);
    }

    public void updateExpiration(ScopedBizUnit inBizUnit, Date inExpirationDate) {
        AgentRepresentation rep = AgentRepresentation.find(this, inBizUnit);
        if (rep != null) {
            rep.setAgentrepEndDate(inExpirationDate);
        }
    }

    public Collection getActiveRepresentedBizUnits() {
        HashSet<ScopedBizUnit> list = new HashSet<ScopedBizUnit>();
        if (this.getAgentRepresentations() != null) {
            for (Object agentBizu : this.getAgentRepresentations()) {
                if (!((AgentRepresentation)agentBizu).isActive()) continue;
                list.add(((AgentRepresentation)agentBizu).getAgentrepScopedBusinessUnit());
            }
        }
        return list;
    }

    public Object getAgentrepTableKey() {
        return this.getBzuGkey();
    }

    public static Agent resolveAgentFromScopedBizUnit(ScopedBizUnit inScopedBizUnit) {
        Agent agent = null;
        if (inScopedBizUnit != null && BizRoleEnum.AGENT.equals((Object)inScopedBizUnit.getBzuRole())) {
            agent = (Agent) HibernateApi.getInstance().downcast((DatabaseEntity)inScopedBizUnit, Agent.class);
        }
        return agent;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Agent clone = (Agent)super.clone();
        clone.setAgentRepresentations(null);
        return clone;
    }

    public static Agent findOrCreateAgentByIdOrName(String inAgentIdOrName) {
        Agent agent = ArgoConfig.AGENT_USING_NAME.isOn(ContextHelper.getThreadUserContext()) ? Agent.findOrCreateAgentByName(inAgentIdOrName) : Agent.findOrCreateAgent(inAgentIdOrName, null);
        return agent;
    }

    public static Agent findOrCreateAgentProxyByIdOrName(String inAgentIdOrName) {
        Agent agent;
        if (ArgoConfig.AGENT_USING_NAME.isOn(ContextHelper.getThreadUserContext())) {
            agent = Agent.findOrCreateAgentProxyByName(inAgentIdOrName);
        } else {
            agent = Agent.findAgentProxyById(inAgentIdOrName);
            if (agent == null) {
                agent = Agent.createAgent(inAgentIdOrName);
            }
        }
        return agent;
    }

    public static Agent findOrCreateAgentByName(String inAgentName) {
        Agent agent = Agent.findAgentByName(inAgentName);
        if (agent == null && (agent = Agent.findAgent(inAgentName)) == null) {
            agent = Agent.createAgent(inAgentName);
            agent.setBzuName(inAgentName);
        }
        return agent;
    }

    public static Agent findOrCreateAgentProxyByName(String inAgentName) {
        Agent agent = Agent.findAgentProxyByName(inAgentName);
        if (agent == null && (agent = Agent.findAgentProxyById(inAgentName)) == null) {
            agent = Agent.createAgent(inAgentName);
            agent.setBzuName(inAgentName);
        }
        return agent;
    }

    public String getAgentNotes() {
        return super.getBzuNotes();
    }

    public void setAgentNotes(String inAgentNotes) {
        super.setBzuNotes(inAgentNotes);
    }
}
