package com.zpmc.ztos.infra.base.common.utils;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.common.model.Roastery;
import org.apache.log4j.Logger;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

public class ClusterNodeUtils {

    public static final String SPARCS_MESSAGE_CHANNEL = "Sparcs_MessageChannel";
    public static final String A4_CONSUMER_PREFIX = "ForwardingJmsPollingTask_a4_";
    public static final String A4_CONSUMER_OLD_PREFIX = "JmsPollingTask_a4_";
    public static final String N4_CONSUMER_PREFIX = "SyncPollingJob_n4_";
    public static final String TCP_PROTOCOL = "tcp://";
    public static final String UNKNOWN_HOST_IP = "UNKNOWN";
    public static final String CONTAINER_NODE_ID = "environment.container.identity";
    private static final Logger LOGGER = Logger.getLogger(ClusterNodeUtils.class);
    private static final String UNKNOWN_NODE_NAME = "!UnknownNodeName!";
    public static final String LOOP_BACK_ADDRESS = "127.0.0.1";

    private ClusterNodeUtils() {
    }

    @Deprecated
    public static String getExecutionNode() {
        String defined = ClusterNodeUtils.getDefinedExecutionNode();
        if (defined != null) {
            return defined;
        }
        return UNKNOWN_NODE_NAME;
    }

    @Nullable
    public static String getDefinedExecutionNode() {
        String identity = System.getProperty(CONTAINER_NODE_ID);
        if (identity != null) {
            return identity;
        }
        return null;
    }

    @NotNull
    public static String getUniqueNodeName() {
        String nodeName = ClusterNodeUtils.getDefinedExecutionNode();
        try {
            if (nodeName == null) {
                RuntimeMXBean bean = ManagementFactory.getRuntimeMXBean();
                nodeName = "(" + bean.getName() + ")";
                LOGGER.info((Object)"Node name NOT defined, using jvm name.");
            }
        }
        catch (Exception e) {
            LOGGER.error((Object)("Error getting node name due to: " + e));
            nodeName = UNKNOWN_NODE_NAME;
        }
        return nodeName;
    }

    @NotNull
    public static String getUniqueConstantNodeName() {
        String nodeName = ClusterNodeUtils.getDefinedExecutionNode();
        try {
            if (nodeName == null) {
                RuntimeMXBean bean = ManagementFactory.getRuntimeMXBean();
                nodeName = bean.getName();
                int begin = nodeName.indexOf(64);
                if (begin > -1) {
                    nodeName = nodeName.substring(begin + 1);
                }
                LOGGER.info((Object)"Node name NOT defined, using jvm name.");
            }
        }
        catch (Exception e) {
            LOGGER.error((Object)("Error getting node name due to: " + e));
            nodeName = UNKNOWN_NODE_NAME;
        }
        return nodeName;
    }

    @Nullable
    public static String getHostAddress() {
        try {
            InetAddress addr = InetAddress.getLocalHost();
            return addr.getHostAddress();
        }
        catch (UnknownHostException e) {
            LOGGER.error((Object)("Failed to get local server IP Address: " + e));
            return null;
        }
    }

    @Nullable
    public static String getMacAddress() {
        String macAddress;
        try {
            InetAddress addr = InetAddress.getLocalHost();
            NetworkInterface network = NetworkInterface.getByInetAddress(addr);
            byte[] mac = network.getHardwareAddress();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < mac.length; ++i) {
                sb.append(String.format("%02X%s", mac[i], i < mac.length - 1 ? "-" : ""));
            }
            macAddress = sb.toString();
        }
        catch (UnknownHostException e) {
            LOGGER.error((Object)("Failed to get local server Mac Address: " + e));
            macAddress = "Not Available";
        }
        catch (SocketException se) {
            LOGGER.error((Object)("Failed to get local server Mac Address: " + se));
            macAddress = "Not Available";
        }
        catch (NullPointerException npe) {
            LOGGER.error((Object)("Failed to get local server Mac Address due to insufficient permissions: " + npe));
            macAddress = "Not Available";
        }
        return macAddress;
    }

    @Nullable
    public static String getHostFQName() {
        String name = null;
        try {
            InetAddress addr = InetAddress.getLocalHost();
            name = addr.getCanonicalHostName();
            if (name == null || name.equals(addr.getHostAddress())) {
                name = addr.getHostName();
            }
        }
        catch (UnknownHostException e) {
            LOGGER.error((Object)("Failed to get local server Name: " + e));
        }
        return name;
    }

    public static String[] getHostNameAndIP() {
        String[] ret = new String[3];
        try {
            InetAddress addr = InetAddress.getLocalHost();
            ret[0] = addr.getCanonicalHostName();
            ret[1] = addr.getHostAddress();
            ret[2] = LOOP_BACK_ADDRESS;
        }
        catch (UnknownHostException e) {
            LOGGER.error((Object)("Failed to get local server Name: " + e));
        }
        return ret;
    }

//    public static Collection<String> getAllClusterNodes() {
//        PersistenceTemplatePropagationRequired persistenceTemplatePropagationRequired = new PersistenceTemplatePropagationRequired(UserContextUtils.getSystemUserContext());
//        persistenceTemplatePropagationRequired.invoke(new CarinaPersistenceCallback(){
//
//            @Override
//            protected void doInTransaction() {
//                DefaultClusterServiceBizFacadeImpl clusterServiceBizFacade = new DefaultClusterServiceBizFacadeImpl();
//                Collection<String> activeClusterNodeNames = clusterServiceBizFacade.findActiveClusterNodeNames(UserContextUtils.getSystemUserContext(), MessageCollectorFactory.createMessageCollector());
//                String currentNode = ClusterNodeUtils.getUniqueConstantNodeName();
//                if (activeClusterNodeNames.isEmpty() || !activeClusterNodeNames.contains(currentNode)) {
//                    activeClusterNodeNames.add(currentNode);
//                }
//                this.setResult(activeClusterNodeNames);
//            }
//        });
//        return (Collection)persistenceTemplatePropagationRequired.getResult();
//    }
//
//    public static Map<String, String> getAllClusterNodesWithGkeys() {
//        PersistenceTemplatePropagationRequired persistenceTemplatePropagationRequired = new PersistenceTemplatePropagationRequired(UserContextUtils.getSystemUserContext());
//        persistenceTemplatePropagationRequired.invoke(new CarinaPersistenceCallback(){
//
//            @Override
//            protected void doInTransaction() {
//                DefaultClusterServiceBizFacadeImpl clusterServiceBizFacade = new DefaultClusterServiceBizFacadeImpl();
//                Map<String, String> activeClusterNodeNames = clusterServiceBizFacade.findAllClusterNodeDisplayNamesWithGkeys(UserContextUtils.getSystemUserContext(), MessageCollectorFactory.createMessageCollector());
//                this.setResult(activeClusterNodeNames);
//            }
//        });
//        return (Map)persistenceTemplatePropagationRequired.getResult();
//    }
//
//    public static Map<String, String> getAllClusterNodesWithStatuses() {
//        PersistenceTemplatePropagationRequired persistenceTemplatePropagationRequired = new PersistenceTemplatePropagationRequired(UserContextUtils.getSystemUserContext());
//        persistenceTemplatePropagationRequired.invoke(new CarinaPersistenceCallback(){
//
//            @Override
//            protected void doInTransaction() {
//                DefaultClusterServiceBizFacadeImpl clusterServiceBizFacade = new DefaultClusterServiceBizFacadeImpl();
//                MessageCollector mc = MessageCollectorFactory.createMessageCollector();
//                Map<String, String> activeClusterNodeNames = clusterServiceBizFacade.findAllClusterNodeDisplayNamesWithStatuses(UserContextUtils.getSystemUserContext(), mc);
//                if (mc.hasError()) {
//                    LOGGER.error((Object)("Error retieving cluster nodes:" + mc.toLoggableString(UserContextUtils.getSystemUserContext())));
//                }
//                this.setResult(activeClusterNodeNames);
//            }
//        });
//        return (Map)persistenceTemplatePropagationRequired.getResult();
//    }

    public static long getCpuCount() {
        return Runtime.getRuntime().availableProcessors();
    }

    public static double getCpuLoad() {
        return ManagementFactory.getOperatingSystemMXBean().getSystemLoadAverage();
    }

    public static Long getMaximumStaleHeartBeatDuration() {
        return (Long) Roastery.getBean("maxStaleHeartBeanDuration");
    }

//    public static Collection<String> getInactiveClusterNodes() {
//        PersistenceTemplatePropagationRequired persistenceTemplatePropagationRequired = new PersistenceTemplatePropagationRequired(UserContextUtils.getSystemUserContext());
//        persistenceTemplatePropagationRequired.invoke(new CarinaPersistenceCallback(){
//
//            @Override
//            protected void doInTransaction() {
//                DefaultClusterServiceBizFacadeImpl clusterServiceBizFacade = new DefaultClusterServiceBizFacadeImpl();
//                Collection<String> activeClusterNodeNames = clusterServiceBizFacade.findInactiveClusterNodeNames(UserContextUtils.getSystemUserContext(), MessageCollectorFactory.createMessageCollector());
//                this.setResult(activeClusterNodeNames);
//            }
//        });
//        return (Collection)persistenceTemplatePropagationRequired.getResult();
//    }
//
//    public static NodeConfig getNodeInfo() {
//        final NodeConfig[] nodeConfig = new NodeConfig[1];
//        final String nodeName = ClusterNodeUtils.getUniqueNodeName();
//        PersistenceTemplate cb = new PersistenceTemplate(UserContextUtils.getSystemUserContext());
//        MessageCollector messageCollector = cb.invoke(new CarinaPersistenceCallback(){
//
//            @Override
//            protected void doInTransaction() {
//                try {
//                    IDomainQuery dq = QueryUtils.createDomainQuery("NodeConfig").addDqPredicate(PredicateFactory.eq(ISystemField.NODECFG_NODE_NAME, nodeName));
//                    nodeConfig[0] = (NodeConfig) HibernateApi.getInstance().getUniqueEntityByDomainQuery(dq);
//                }
//                catch (Exception ex) {
//                    LOGGER.error((Object)("Could not find node info for " + nodeName), (Throwable)ex);
//                }
//            }
//        });
//        if (messageCollector.hasError()) {
//            LOGGER.error((Object)messageCollector);
//        } else if (nodeConfig[0] == null) {
//            LOGGER.error((Object)("Could not find node info for " + nodeName));
//        }
//        return nodeConfig[0];
//    }
}
