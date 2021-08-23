package com.zpmc.ztos.infra.base.business.model;

import com.zpmc.ztos.infra.base.business.dataobject.GroupDO;
import com.zpmc.ztos.infra.base.business.enums.argo.ScopeEnum;
import com.zpmc.ztos.infra.base.business.enums.framework.LifeCycleStateEnum;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.business.predicate.PredicateFactory;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.common.events.AuditEvent;
import com.zpmc.ztos.infra.base.common.exceptions.BizFailure;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.groovy.CustomGroovyInvoker;
import com.zpmc.ztos.infra.base.common.model.FieldChanges;
import com.zpmc.ztos.infra.base.common.utils.ArgoGroovyUtils;
import com.zpmc.ztos.infra.base.utils.QueryUtils;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Group extends GroupDO implements IReleaseDocument {
    private static final Logger LOGGER = Logger.getLogger(Group.class);

    public Group(EntitySet inGrpRefSet, String inGrpId) {
        this();
        this.setGrpScope(inGrpRefSet);
        this.setGrpId(inGrpId);
    }

    public Group() {
        this.setGrpLifeCycleState(LifeCycleStateEnum.ACTIVE);
    }

    public void setLifeCycleState(LifeCycleStateEnum inLifeCycleState) {
        this.setGrpLifeCycleState(inLifeCycleState);
    }

    public LifeCycleStateEnum getLifeCycleState() {
        return this.getGrpLifeCycleState();
    }

    public static Group findGroup(String inGrpId) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"Group").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoRefField.GRP_ID, (Object)inGrpId));
        return (Group) HibernateApi.getInstance().getUniqueEntityByDomainQuery(dq);
    }

    public static Group findGroupProxy(String inGrpId) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"Group").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoRefField.GRP_ID, (Object)inGrpId));
        Serializable[] grpGkey = HibernateApi.getInstance().findPrimaryKeysByDomainQuery(dq);
        if (grpGkey == null || grpGkey.length == 0) {
            return null;
        }
        if (grpGkey.length == 1) {
            return (Group)HibernateApi.getInstance().load(Group.class, grpGkey[0]);
        }
        throw BizFailure.create((IPropertyKey) IFrameworkPropertyKeys.FRAMEWORK__NON_UNIQUE_RESULT, null, (Object)new Long(grpGkey.length), (Object)dq);
    }

    public static Group findOrCreateGroup(String inGroupId) {
        Group group = Group.findGroup(inGroupId);
        if (group == null) {
            group = new Group();
            group.setGrpId(inGroupId);
            group.setGrpDescription("??" + inGroupId + "??");
            HibernateApi.getInstance().saveOrUpdate((Object)group);
        }
        return group;
    }

    public static Group createGroup(String inGrpId, String inGrpDescription) {
        Group grp = new Group();
        grp.setGrpId(inGrpId);
        grp.setGrpDescription(inGrpDescription);
        HibernateApi.getInstance().saveOrUpdate((Object)grp);
        return grp;
    }

    public String toString() {
        return "Group Id:" + this.getGrpId();
    }

    public IMetafieldId getScopeFieldId() {
        return IArgoRefField.GRP_SCOPE;
    }

    public IMetafieldId getNaturalKeyField() {
        return IArgoRefField.GRP_ID;
    }

    public ScopeEnum getMinimumScope() {
        return ScopeEnum.COMPLEX;
    }

    public AuditEvent vetAuditEvent(AuditEvent inAuditEvent) {
        return inAuditEvent;
    }

    @Override
    public String getDocumentNbr() {
        return this.getGrpId();
    }

    public boolean isMember(ScopedBizUnit inBzu) {
        Set bzuSet = this.getGrpBzuSet();
        if (bzuSet == null) {
            return false;
        }
        return bzuSet.contains(inBzu);
    }

    public String getGrpSampleBzuIds() {
        Set bzuSet = this.getGrpBzuSet();
        if (bzuSet == null) {
            return null;
        }
        StringBuffer buf = new StringBuffer();
        for (Object bzu : bzuSet) {
            if (buf.length() > 20) {
                buf.append("\ufffd");
                break;
            }
            if (buf.length() > 0) {
                buf.append(',');
            }
            buf.append(((ScopedBizUnit)bzu).getBzuId());
        }
        return buf.toString();
    }

    public void addMember(ScopedBizUnit inMember) {
        HashSet<ScopedBizUnit> members = (HashSet<ScopedBizUnit>) this.getGrpBzuSet();
        if (members == null) {
            members = new HashSet<ScopedBizUnit>();
            this.setGrpBzuSet(members);
        }
        members.add(inMember);
    }

    public void preProcessInsertOrUpdate(FieldChanges inOutMoreChanges) {
        super.preProcessInsertOrUpdate(inOutMoreChanges);
        this.invokeGroupGroovy(inOutMoreChanges);
    }

    public void invokeGroupGroovy(FieldChanges inOutMoreChanges) {
        String groovyScript = ArgoGroovyUtils.getGroovyScript("GroupGroovyImpl");
        if (groovyScript != null) {
            HashMap<String, Group> args = new HashMap<String, Group>();
            args.put(IEntity.class.getName(), this);
           // args.put(FieldChanges.class.getName(), (Group)inOutMoreChanges);
            try {
                CustomGroovyInvoker.invokeCustomGroovy(groovyScript, new Class[]{Map.class}, new Object[]{args}, "GroupGroovyImpl", "execute");
            }
            catch (BizViolation inBizViolation) {
                LOGGER.error((Object)inBizViolation);
            }
        }
    }

    public boolean isForITT() {
        return this.getGrpDestinationFacility() != null;
    }

}
