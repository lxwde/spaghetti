package com.zpmc.ztos.infra.base.business.model;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.business.dataobject.ClusterServiceDO;
import com.zpmc.ztos.infra.base.business.enums.framework.JoinTypeEnum;
import com.zpmc.ztos.infra.base.business.enums.framework.NodeStatusEnum;
import com.zpmc.ztos.infra.base.business.enums.framework.NodeStatusTypeEnum;
import com.zpmc.ztos.infra.base.business.enums.framework.PredicateVerbEnum;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.business.predicate.PredicateFactory;
import com.zpmc.ztos.infra.base.common.model.*;
import com.zpmc.ztos.infra.base.common.utils.ClusterNodeUtils;
import com.zpmc.ztos.infra.base.utils.QueryUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.io.Serializable;
import java.util.*;

public class ClusterService extends ClusterServiceDO {

    public static final String CLUSTER_NODE = "ClusterNode";
    public static final String CENTER_NODE = "CenterNode";
    public static final String BRIDGE_NODE = "BridgeDaemon";
    public static final String KEY_SEPERATOR = "/";
    private static final Logger LOGGER = Logger.getLogger(ClusterService.class);

    public static ClusterService findClusterService(@NotNull Long inScopeLevel, @Nullable String inScopeGkey, @NotNull String inKey) {
        return ClusterService.findClusterService(inScopeLevel, inScopeGkey, inKey, null);
    }

    public static ClusterService findClusterService(@NotNull Long inScopeLevel, @Nullable String inScopeGkey, @NotNull String inKey, @Nullable String inType) {
        IDomainQuery dq = QueryUtils.createDomainQuery("ClusterService").addDqPredicate(PredicateFactory.eq(ISystemField.CLUSTER_SERVICE_SCOPE_LEVEL, inScopeLevel)).addDqPredicate(PredicateFactory.eq(ISystemField.CLUSTER_SERVICE_KEY, inKey));
        if (!StringUtils.isEmpty((String)inScopeGkey)) {
            dq.addDqPredicate(PredicateFactory.eq(ISystemField.CLUSTER_SERVICE_SCOPE_GKEY, inScopeGkey));
        }
        if (!StringUtils.isEmpty((String)inType)) {
            dq.addDqPredicate(PredicateFactory.eq(ISystemField.CLUSTER_SERVICE_TYPE, inType));
        }
        return (ClusterService) Roastery.getHibernateApi().getUniqueEntityByDomainQuery(dq);
    }

    public static ClusterService findClusterServiceByGkey(@NotNull Long inScopeLevel, @Nullable String inScopeGkey, @NotNull Serializable inKey, @Nullable String inType) {
        IDomainQuery dq = QueryUtils.createDomainQuery("ClusterService").addDqPredicate(PredicateFactory.eq(ISystemField.CLUSTER_SERVICE_SCOPE_LEVEL, inScopeLevel)).addDqPredicate(PredicateFactory.eq(ISystemField.CLUSTER_SERVICE_GKEY, inKey));
        if (!StringUtils.isEmpty((String)inScopeGkey)) {
            dq.addDqPredicate(PredicateFactory.eq(ISystemField.CLUSTER_SERVICE_SCOPE_GKEY, inScopeGkey));
        }
        if (!StringUtils.isEmpty((String)inType)) {
            dq.addDqPredicate(PredicateFactory.eq(ISystemField.CLUSTER_SERVICE_TYPE, inType));
        }
        return (ClusterService)Roastery.getHibernateApi().getUniqueEntityByDomainQuery(dq);
    }

    public static ClusterService findOrCreateClusterService(@NotNull Long inScopeLevel, @Nullable String inScopeGkey, @NotNull FieldChanges inChanges) {
        String type = (String)inChanges.getFieldChange(ISystemField.CLUSTER_SERVICE_TYPE).getNewValue();
        String name = (String)inChanges.getFieldChange(ISystemField.CLUSTER_SERVICE_NAME).getNewValue();
        String key = (String)inChanges.getFieldChange(ISystemField.CLUSTER_SERVICE_KEY).getNewValue();
        ClusterService service = ClusterService.findClusterService(inScopeLevel, inScopeGkey, key, type);
        Date heartTimestamp = null;
        boolean isNewService = false;
        if (service == null) {
            isNewService = true;
            service = new ClusterService();
            service.setClusterServiceKey(key);
            service.setClusterServiceScopeLevel(inScopeLevel);
            service.setClusterServiceScopeGkey(inScopeGkey);
            service.setClusterServiceType(type);
            service.setClusterServiceName(name);
        }
        if (inChanges.hasFieldChange(ISystemField.CLUSTER_SERVICE_STATUS)) {
            service.setClusterServiceStatus((String)inChanges.getFieldChange(ISystemField.CLUSTER_SERVICE_STATUS).getNewValue());
        }
        if (inChanges.hasFieldChange(ISystemField.CLUSTER_SERVICE_I_P_ADDRESS)) {
            service.setClusterServiceIPAddress((String)inChanges.getFieldChange(ISystemField.CLUSTER_SERVICE_I_P_ADDRESS).getNewValue());
        }
        if (inChanges.hasFieldChange(ISystemField.CLUSTER_SERVICE_MAC_ADDRESS)) {
            service.setClusterServiceMacAddress((String)inChanges.getFieldChange(ISystemField.CLUSTER_SERVICE_MAC_ADDRESS).getNewValue());
        }
        if (inChanges.hasFieldChange(ISystemField.CLUSTER_SERVICE_PORT)) {
            service.setClusterServicePort((Integer)inChanges.getFieldChange(ISystemField.CLUSTER_SERVICE_PORT).getNewValue());
        }
        if (inChanges.hasFieldChange(ISystemField.CLUSTER_SERVICE_VERSION)) {
            service.setClusterServiceVersion((String)inChanges.getFieldChange(ISystemField.CLUSTER_SERVICE_VERSION).getNewValue());
        }
        if (inChanges.hasFieldChange(ISystemField.CLUSTER_SERVICE_INFO)) {
            service.setClusterServiceInfo((String)inChanges.getFieldChange(ISystemField.CLUSTER_SERVICE_INFO).getNewValue());
        }
        if (inChanges.hasFieldChange(ISystemField.CLUSTER_SERVICE_STARTUP_TIME)) {
            service.setClusterServiceStartupTime((Date)inChanges.getFieldChange(ISystemField.CLUSTER_SERVICE_STARTUP_TIME).getNewValue());
        }
        if (inChanges.hasFieldChange(ISystemField.CLUSTER_SERVICE_HEART_BEAT_TIME)) {
            heartTimestamp = (Date)inChanges.getFieldChange(ISystemField.CLUSTER_SERVICE_HEART_BEAT_TIME).getNewValue();
            service.setClusterServiceHeartBeatTime(heartTimestamp);
        }
        if (inChanges.hasFieldChange(ISystemField.CLUSTER_SERVICE_ACTIVITY_TIME)) {
            service.setClusterServiceActivityTime((Date)inChanges.getFieldChange(ISystemField.CLUSTER_SERVICE_ACTIVITY_TIME).getNewValue());
        }
        if (inChanges.hasFieldChange(ISystemField.CLUSTER_SERVICE_SHUTDOWN_TIME)) {
            service.setClusterServiceShutdownTime((Date)inChanges.getFieldChange(ISystemField.CLUSTER_SERVICE_SHUTDOWN_TIME).getNewValue());
        }
        if (inChanges.hasFieldChange(ISystemField.CLUSTER_SERVICE_USER_NAME)) {
            service.setClusterServiceUserName((String)inChanges.getFieldChange(ISystemField.CLUSTER_SERVICE_USER_NAME).getNewValue());
        }
        if (inChanges.hasFieldChange(ISystemField.CLUSTER_SERVICE_MEMORY_USED)) {
            service.setClusterServiceMemoryUsed((Long)inChanges.getFieldChange(ISystemField.CLUSTER_SERVICE_MEMORY_USED).getNewValue());
        }
        if (inChanges.hasFieldChange(ISystemField.CLUSTER_SERVICE_MEMORY_MAX)) {
            service.setClusterServiceMemoryMax((Long)inChanges.getFieldChange(ISystemField.CLUSTER_SERVICE_MEMORY_MAX).getNewValue());
        }
        if (inChanges.hasFieldChange(ISystemField.CLUSTER_SERVICE_CPU_COUNT)) {
            service.setClusterServiceCpuCount((Long)inChanges.getFieldChange(ISystemField.CLUSTER_SERVICE_CPU_COUNT).getNewValue());
        }
        if (inChanges.hasFieldChange(ISystemField.CLUSTER_SERVICE_CPU_LOAD)) {
            service.setClusterServiceCpuLoad((Double)inChanges.getFieldChange(ISystemField.CLUSTER_SERVICE_CPU_LOAD).getNewValue());
        }
        if (isNewService) {
            Roastery.getHibernateApi().save(service);
        }
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug((Object)("Updating CluserService " + name + ":" + type + ":" + key + ":" + heartTimestamp));
        }
        return service;
    }

    public static List<ClusterService> findActiveClusterNodes() {
        List<ClusterService> clusterServiceList = ClusterService.findAllClusterNodes();
        List<ClusterService> activeClusterServiceList = ClusterService.filterOutStaleNodes(clusterServiceList);
        return activeClusterServiceList;
    }

    public static List<ClusterService> findAllClusterNodes() {
        IDomainQuery dq = QueryUtils.createDomainQuery("ClusterService").addDqPredicate(PredicateFactory.eq(ISystemField.CLUSTER_SERVICE_SCOPE_LEVEL, ScopeCoordinates.GLOBAL_LEVEL)).addDqPredicate(PredicateFactory.eq(ISystemField.CLUSTER_SERVICE_TYPE, CLUSTER_NODE));
        List clusterServiceList = Roastery.getHibernateApi().findEntitiesByDomainQuery(dq);
        if (clusterServiceList == null || clusterServiceList.isEmpty()) {
            return Collections.emptyList();
        }
        return clusterServiceList;
    }

    private static List<ClusterService> filterOutStaleNodes(List<ClusterService> inClusterServiceList) {
        Date minuteAgo = new Date(new Date().getTime() - ClusterNodeUtils.getMaximumStaleHeartBeatDuration());
        ArrayList<ClusterService> activeClusterServiceList = new ArrayList<ClusterService>();
//        IClusterNodeInactivityPolicy inactivityPolicy = (IClusterNodeInactivityPolicy)PortalApplicationContext.getBean("clusterNodeInactivityPolicy");
//        String currentNode = ClusterNodeUtils.getUniqueConstantNodeName();
//        for (ClusterService clusterService : inClusterServiceList) {
//            if (clusterService.getClusterServiceHeartBeatTime() != null && clusterService.getClusterServiceHeartBeatTime().before(minuteAgo)) continue;
//            if (clusterService.getClusterServiceName().equals(currentNode)) {
//                activeClusterServiceList.add(clusterService);
//                continue;
//            }
//            if (clusterService.getClusterServiceHeartBeatTime() == null) continue;
//            long elapsed = System.currentTimeMillis() - clusterService.getClusterServiceHeartBeatTime().getTime();
//            if (!inactivityPolicy.isInActive(elapsed)) {
//                activeClusterServiceList.add(clusterService);
//                continue;
//            }
//            if (!LOGGER.isInfoEnabled()) continue;
//            LOGGER.info((Object)("Not adding node " + clusterService.getClusterServiceName() + " because heartbeat is " + elapsed / 1000L + " seconds old"));
//        }
        return activeClusterServiceList;
    }

    public static List<ClusterService> findInactiveClusterNodes() {
        IDomainQuery dq = QueryUtils.createDomainQuery("ClusterService").addDqPredicate(PredicateFactory.eq(ISystemField.CLUSTER_SERVICE_SCOPE_LEVEL, ScopeCoordinates.GLOBAL_LEVEL)).addDqPredicate(PredicateFactory.eq(ISystemField.CLUSTER_SERVICE_TYPE, CLUSTER_NODE));
        List clusterServiceList = Roastery.getHibernateApi().findEntitiesByDomainQuery(dq);
        if (clusterServiceList == null || clusterServiceList.isEmpty()) {
            return Collections.emptyList();
        }
//        ArrayList<ClusterService> inactiveClusterServiceList = new ArrayList<ClusterService>();
//        IClusterNodeInactivityPolicy inactivityPolicy = (IClusterNodeInactivityPolicy)PortalApplicationContext.getBean("clusterNodeInactivityPolicy");
//        String currentNode = ClusterNodeUtils.getUniqueConstantNodeName();
//        for (ClusterService clusterService : clusterServiceList) {
//            if (clusterService.getClusterServiceName().equals(currentNode)) continue;
//            if (NodeStatusTypeEnum.ACTIVE.getKey().equals(clusterService.getClusterServiceStatus())) {
//                if (clusterService.getClusterServiceHeartBeatTime() == null) {
//                    inactiveClusterServiceList.add(clusterService);
//                    continue;
//                }
//                long elapsed = System.currentTimeMillis() - clusterService.getClusterServiceHeartBeatTime().getTime();
//                if (!inactivityPolicy.isInActive(elapsed)) continue;
//                inactiveClusterServiceList.add(clusterService);
//                continue;
//            }
//            inactiveClusterServiceList.add(clusterService);
//        }
//        return inactiveClusterServiceList;
        return null;
    }

    public static ClusterService findClusterNode(@NotNull String inNodeName) {
        return ClusterService.findClusterService(ScopeCoordinates.GLOBAL_LEVEL, null, inNodeName, ClusterService.getClusterNodeType());
    }

    public static ClusterService findClusterByGkey(@NotNull Serializable inGkey) {
        return ClusterService.findClusterServiceByGkey(ScopeCoordinates.GLOBAL_LEVEL, null, inGkey, ClusterService.getClusterNodeType());
    }

    public static ClusterService findOrCreateClusterNode(String inNodeName, String inIpAddress, String inMacAddress, Integer inJmsTcpPort) {
//        BooleanTriStateEnum isCenter = ESBServerHelper.isCurrentNodeAsCenter();
//        FieldChanges fcs = new FieldChanges();
//        fcs.setFieldChange(ISystemField.CLUSTER_SERVICE_TYPE, ClusterService.getClusterNodeType());
//        fcs.setFieldChange(ISystemField.CLUSTER_SERVICE_KEY, ClusterService.getClusterNodeKey(ClusterNodeUtils.getUniqueConstantNodeName()));
//        fcs.setFieldChange(ISystemField.CLUSTER_SERVICE_NAME, ClusterNodeUtils.getUniqueConstantNodeName());
//        fcs.setFieldChange(ISystemField.CLUSTER_SERVICE_I_P_ADDRESS, inIpAddress);
//        fcs.setFieldChange(ISystemField.CLUSTER_SERVICE_MAC_ADDRESS, inMacAddress);
//        fcs.setFieldChange(ISystemField.CLUSTER_SERVICE_STARTUP_TIME, ClusterService.now());
//        if (inJmsTcpPort != null) {
//            fcs.setFieldChange(ISystemField.CLUSTER_SERVICE_PORT, inJmsTcpPort);
//        }
//        if (isCenter == null || BooleanTriStateEnum.TRUE.equals(isCenter)) {
//            fcs.setFieldChange(ISystemField.CLUSTER_SERVICE_HEART_BEAT_TIME, ClusterService.now());
//            fcs.setFieldChange(ISystemField.CLUSTER_SERVICE_STATUS, NodeStatusTypeEnum.ACTIVE.getKey());
//        } else {
//            fcs.setFieldChange(ISystemField.CLUSTER_SERVICE_STATUS, NodeStatusTypeEnum.DISCONNECTED.getKey());
//        }
//        fcs.setFieldChange(ISystemField.CLUSTER_SERVICE_VERSION, CarinaUtils.getVersionStringFromManifest(ClusterService.class));
//        fcs.setFieldChange(ISystemField.CLUSTER_SERVICE_CPU_COUNT, ClusterNodeUtils.getCpuCount());
//        fcs.setFieldChange(ISystemField.CLUSTER_SERVICE_CPU_LOAD, ClusterNodeUtils.getCpuLoad());
//        return ClusterService.findOrCreateClusterService(ScopeCoordinates.GLOBAL_LEVEL, null, fcs);
        return null;
    }

    public static Collection<ClusterService> findClusterNodesForGroup(String inJobGroupName) {
        String alias = "group";
        IDomainQuery dq = QueryUtils.createDomainQuery("ClusterService");
        dq.addDqJoin(PredicateFactory.createJoin(JoinTypeEnum.LEFT_OUTER_JOIN, ISystemField.CLUSTER_SERVICE_GROUPS, alias));
        IEntityId nodeListEntityId = EntityIdFactory.valueOf(ISystemField.CLUSTER_SERVICE_GROUPS.getFieldId(), alias);
  //      IMetafieldId entityAwareGroupName = MetafieldIdFactory.getEntityAwareMetafieldId(nodeListEntityId, QuartzField.JOBGROUP_NAME);
 //       dq.addDqPredicate(PredicateFactory.eq(entityAwareGroupName, inJobGroupName));
        dq.addDqPredicate(PredicateFactory.eq(ISystemField.CLUSTER_SERVICE_TYPE, CLUSTER_NODE));
        dq.addDqPredicate(PredicateFactory.eq(ISystemField.CLUSTER_SERVICE_SCOPE_LEVEL, ScopeCoordinates.GLOBAL_LEVEL));
        dq.addDqPredicate(PredicateFactory.eq(ISystemField.CLUSTER_SERVICE_STATUS, NodeStatusTypeEnum.ACTIVE.getKey()));
        List resultList = Roastery.getHibernateApi().findEntitiesByDomainQuery(dq);
        if (resultList == null || resultList.isEmpty()) {
            return Collections.emptyList();
        }
        ArrayList<ClusterService> clusterServiceList = new ArrayList<ClusterService>();
        for (Object o : resultList) {
            clusterServiceList.add((ClusterService)((Object[])o)[0]);
        }
        if (clusterServiceList == null || clusterServiceList.isEmpty()) {
            return Collections.emptyList();
        }
        List<ClusterService> activeClusterServiceList = ClusterService.filterOutStaleNodes(clusterServiceList);
        return activeClusterServiceList;
    }

    public static String getClusterNodeKey(String inNodeName) {
        return ClusterService.getClusterNodeType() + KEY_SEPERATOR + inNodeName;
    }

    public static String getCurrentClusterNodeKey() {
        return ClusterService.getClusterNodeKey(ClusterNodeUtils.getUniqueConstantNodeName());
    }

    public void updateHeartbeat() {
        this.setClusterServiceHeartBeatTime(ClusterService.now());
    }

    public void updateCpuLoad(double inCpuLoad) {
        this.setClusterServiceCpuLoad(inCpuLoad);
    }

    public String getClusterServiceAckDelay() {
//        NavisActiveMQConsumerStatistics consumerStatistics;
//        String average;
//        if (CLUSTER_NODE.equals(this.getClusterServiceType()) && (average = (consumerStatistics = (NavisActiveMQConsumerStatistics)PortalApplicationContext.getBean("consumerStatistics")).getConsumerStatistics(this.getClusterServiceIPAddress(), this.getClusterServicePort())) != null) {
//            return average;
//        }
        return "";
    }

    public static void shutdownClusterNode(String inNodeName) {
        ClusterService clusterNode = ClusterService.findClusterNode(inNodeName);
        if (clusterNode != null) {
            clusterNode.shutdown();
        }
    }

    public void shutdown() {
        this.setClusterServiceStatus(NodeStatusTypeEnum.SHUTDOWN.getKey());
        this.setClusterServiceShutdownTime(ClusterService.now());
    }

    public static void purgeServices() {
        IDomainQuery clusterServiceQuery = QueryUtils.createDomainQuery("ClusterService").addDqPredicate(PredicateFactory.not(PredicateFactory.in(ISystemField.CLUSTER_SERVICE_TYPE, new Object[]{CLUSTER_NODE, CENTER_NODE})));
        Roastery.getHibernateApi().deleteByDomainQuery(clusterServiceQuery);
    }

    public static void purgeInactiveClusterNodes() {
        IDomainQuery dq = QueryUtils.createDomainQuery("ClusterService").addDqPredicate(PredicateFactory.in(ISystemField.CLUSTER_SERVICE_TYPE, new String[]{CLUSTER_NODE, CENTER_NODE})).addDqPredicate(PredicateFactory.ne(ISystemField.CLUSTER_SERVICE_STATUS, NodeStatusTypeEnum.ACTIVE));
        List clusterServices = Roastery.getHibernateApi().findEntitiesByDomainQuery(dq);
        for (Object inactiveNode : clusterServices) {
            if (((ClusterService)inactiveNode).getClusterServiceGroups() != null && !((ClusterService)inactiveNode).getClusterServiceGroups().isEmpty()) continue;
            Roastery.getHibernateApi().delete(inactiveNode);
        }
    }

    public static IPredicate formClusterServiceStatusEnumPredicate(IMetafieldId inMetafieldId, PredicateVerbEnum inVerb, Object inValue) {
        String status = inValue == null ? null : ((NodeStatusEnum)inValue).getKey();
        TransactionParms tp = TransactionParms.getBoundParms();
        UserContext userContext = tp == null ? null : tp.getUserContext();
        return PredicateFactory.createPredicate(userContext, ISystemField.CLUSTER_SERVICE_STATUS, inVerb, ((NodeStatusEnum)inValue).getKey());
    }

    private static Date now() {
        return new Date(System.currentTimeMillis());
    }

    private static String getClusterNodeType() {
  //      return BooleanTriStateEnum.TRUE.equals(ESBServerHelper.isCurrentNodeAsCenter()) ? CENTER_NODE : CLUSTER_NODE;
        return "";
    }
}
