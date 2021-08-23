package com.zpmc.ztos.infra.base.business.enums.framework;

import com.zpmc.ztos.infra.base.common.enums.AtomizedEnum;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class NodeStatusTypeEnum extends AtomizedEnum {
    public static final NodeStatusTypeEnum STARTING = new NodeStatusTypeEnum("STARTING", "atom.NodeStatusTypeEnum.STARTING.description", "atom.NodeStatusTypeEnum.STARTING.code", "lightgreen", "", "");
    public static final NodeStatusTypeEnum ACTIVE = new NodeStatusTypeEnum("ACTIVE", "atom.NodeStatusTypeEnum.ACTIVE.description", "atom.NodeStatusTypeEnum.ACTIVE.code", "green", "", "");
    public static final NodeStatusTypeEnum SHUTDOWN = new NodeStatusTypeEnum("SHUTDOWN", "atom.NodeStatusTypeEnum.SHUTDOWN.description", "atom.NodeStatusTypeEnum.SHUTDOWN.code", "yellow", "", "");
    public static final NodeStatusTypeEnum SHUTDOWN_ABNORMAL = new NodeStatusTypeEnum("SHUTDOWN_ABNORMAL", "atom.NodeStatusTypeEnum.SHUTDOWN_ABNORMAL.description", "atom.NodeStatusTypeEnum.SHUTDOWN_ABNORMAL.code", "red", "", "");
    public static final NodeStatusTypeEnum DEACTIVATED = new NodeStatusTypeEnum("DEACTIVATED", "atom.NodeStatusTypeEnum.DEACTIVATED.description", "atom.NodeStatusTypeEnum.DEACTIVATED.code", "lightgrey", "", "");
    public static final NodeStatusTypeEnum ERROR = new NodeStatusTypeEnum("ERROR", "atom.NodeStatusTypeEnum.ERROR.description", "atom.NodeStatusTypeEnum.ERROR.code", "salmon", "", "");
    public static final NodeStatusTypeEnum DISCONNECTED = new NodeStatusTypeEnum("DISCONNECTED", "atom.NodeStatusTypeEnum.DISCONNECTED.description", "atom.NodeStatusTypeEnum.DISCONNECTED.code", "darksalmon", "", "");

    public static NodeStatusTypeEnum getEnum(String inName) {
        return (NodeStatusTypeEnum) NodeStatusTypeEnum.getEnum(NodeStatusTypeEnum.class, (String)inName);
    }

    public static Map getEnumMap() {
        return NodeStatusTypeEnum.getEnumMap(NodeStatusTypeEnum.class);
    }

    public static List getEnumList() {
        return NodeStatusTypeEnum.getEnumList(NodeStatusTypeEnum.class);
    }

    public static Collection getList() {
        return NodeStatusTypeEnum.getEnumList(NodeStatusTypeEnum.class);
    }

    public static Iterator iterator() {
        return NodeStatusTypeEnum.iterator(NodeStatusTypeEnum.class);
    }

    protected NodeStatusTypeEnum(String inKey, String inDescriptionResKey, String inCodeResKey, String inBackgroundColor, String inForegroundColor, String inIconIdPath) {
        super(inKey, inDescriptionResKey, inCodeResKey, inBackgroundColor, inForegroundColor, inIconIdPath);
    }

    @Override
    public String getMappingClassName() {
        return "com.navis.framework.persistence.atoms.UserTypeNodeStatusTypeEnum";
    }
}
