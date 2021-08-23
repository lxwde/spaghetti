package com.zpmc.ztos.infra.base.business.enums.framework;

import com.zpmc.ztos.infra.base.common.enums.AtomizedEnum;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class NodeStatusEnum extends AtomizedEnum {
    public static final NodeStatusEnum UNKNOWN = new NodeStatusEnum("UNKNOWN", "atom.NodeStatusEnum.UNKNOWN.description", "atom.NodeStatusEnum.UNKNOWN.code", "", "", "");
    public static final NodeStatusEnum ACTIVE = new NodeStatusEnum("ACTIVE", "atom.NodeStatusEnum.ACTIVE.description", "atom.NodeStatusEnum.ACTIVE.code", "green", "", "");
    public static final NodeStatusEnum CONNECTED = new NodeStatusEnum("CONNECTED", "atom.NodeStatusEnum.CONNECTED.description", "atom.NodeStatusEnum.CONNECTED.code", "lightgrey", "", "");
    public static final NodeStatusEnum DISCONNECTED = new NodeStatusEnum("DISCONNECTED", "atom.NodeStatusEnum.DISCONNECTED.description", "atom.NodeStatusEnum.DISCONNECTED.code", "red", "", "");
    public static final NodeStatusEnum FAILED = new NodeStatusEnum("FAILED", "atom.NodeStatusEnum.FAILED.description", "atom.NodeStatusEnum.FAILED.code", "lightgrey", "", "");
    public static final NodeStatusEnum INITIALIZING = new NodeStatusEnum("INITIALIZING", "atom.NodeStatusEnum.INITIALIZING.description", "atom.NodeStatusEnum.INITIALIZING.code", "lightgrey", "", "");
    public static final NodeStatusEnum LOADING = new NodeStatusEnum("LOADING", "atom.NodeStatusEnum.LOADING.description", "atom.NodeStatusEnum.LOADING.code", "lightgreen", "", "");
    public static final NodeStatusEnum NETWORK_FAILURE = new NodeStatusEnum("NETWORK_FAILURE", "atom.NodeStatusEnum.NETWORK_FAILURE.description", "atom.NodeStatusEnum.NETWORK_FAILURE.code", "lightgrey", "", "");
    public static final NodeStatusEnum NETWORK_RESYNC = new NodeStatusEnum("NETWORK_RESYNC", "atom.NodeStatusEnum.NETWORK_RESYNC.description", "atom.NodeStatusEnum.NETWORK_RESYNC.code", "lightgrey", "", "");
    public static final NodeStatusEnum RECOVERING = new NodeStatusEnum("RECOVERING", "atom.NodeStatusEnum.RECOVERING.description", "atom.NodeStatusEnum.RECOVERING.code", "lightgrey", "", "");
    public static final NodeStatusEnum SHUTDOWN = new NodeStatusEnum("SHUTDOWN", "atom.NodeStatusEnum.SHUTDOWN.description", "atom.NodeStatusEnum.SHUTDOWN.code", "red", "", "");
    public static final NodeStatusEnum STARTING = new NodeStatusEnum("STARTING", "atom.NodeStatusEnum.STARTING.description", "atom.NodeStatusEnum.STARTING.code", "lightgrey", "", "");
    public static final NodeStatusEnum WAITING = new NodeStatusEnum("WAITING", "atom.NodeStatusEnum.WAITING.description", "atom.NodeStatusEnum.WAITING.code", "yellow", "", "");
    public static final NodeStatusEnum INACTIVE = new NodeStatusEnum("INACTIVE", "atom.NodeStatusEnum.INACTIVE.description", "atom.NodeStatusEnum.INACTIVE.code", "orange", "", "");

    public static NodeStatusEnum getEnum(String inName) {
        return (NodeStatusEnum) NodeStatusEnum.getEnum(NodeStatusEnum.class, (String)inName);
    }

    public static Map getEnumMap() {
        return NodeStatusEnum.getEnumMap(NodeStatusEnum.class);
    }

    public static List getEnumList() {
        return NodeStatusEnum.getEnumList(NodeStatusEnum.class);
    }

    public static Collection getList() {
        return NodeStatusEnum.getEnumList(NodeStatusEnum.class);
    }

    public static Iterator iterator() {
        return NodeStatusEnum.iterator(NodeStatusEnum.class);
    }

    protected NodeStatusEnum(String inKey, String inDescriptionResKey, String inCodeResKey, String inBackgroundColor, String inForegroundColor, String inIconIdPath) {
        super(inKey, inDescriptionResKey, inCodeResKey, inBackgroundColor, inForegroundColor, inIconIdPath);
    }

    @Override
    public String getMappingClassName() {
        return "com.zpmc.ztos.infra.base.business.enums.framework.UserTypeNodeStatusEnum";
    }
}
