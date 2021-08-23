package com.zpmc.ztos.infra.base.business.enums.framework;

import com.zpmc.ztos.infra.base.common.enums.AtomizedEnum;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ServerModeEnum extends AtomizedEnum {
    public static final ServerModeEnum SERVLET = new ServerModeEnum("SERVLET", "atom.ServerModeEnum.SERVLET.description", "atom.ServerModeEnum.SERVLET.code", "", "", "");
    public static final ServerModeEnum STANDALONE_ULC = new ServerModeEnum("STANDALONE_ULC", "atom.ServerModeEnum.STANDALONE_ULC.description", "atom.ServerModeEnum.STANDALONE_ULC.code", "", "", "");
    public static final ServerModeEnum STANDALONE_FACELESS = new ServerModeEnum("STANDALONE_FACELESS", "atom.ServerModeEnum.STANDALONE_FACELESS.description", "atom.ServerModeEnum.STANDALONE_FACELESS.code", "", "", "");
    public static final ServerModeEnum MICROSERVICE = new ServerModeEnum("MICROSERVICE", "atom.ServerModeEnum.MICROSERVICE.description", "atom.ServerModeEnum.MICROSERVICE.code", "", "", "");

    public static ServerModeEnum getEnum(String inName) {
        return (ServerModeEnum) ServerModeEnum.getEnum(ServerModeEnum.class, (String)inName);
    }

    public static Map getEnumMap() {
        return ServerModeEnum.getEnumMap(ServerModeEnum.class);
    }

    public static List getEnumList() {
        return ServerModeEnum.getEnumList(ServerModeEnum.class);
    }

    public static Collection getList() {
        return ServerModeEnum.getEnumList(ServerModeEnum.class);
    }

    public static Iterator iterator() {
        return ServerModeEnum.iterator(ServerModeEnum.class);
    }

    protected ServerModeEnum(String inKey, String inDescriptionResKey, String inCodeResKey, String inBackgroundColor, String inForegroundColor, String inIconIdPath) {
        super(inKey, inDescriptionResKey, inCodeResKey, inBackgroundColor, inForegroundColor, inIconIdPath);
    }

    @Override
    public String getMappingClassName() {
        return "com.navis.framework.persistence.atoms.UserTypeServerModeEnum";
    }

}
