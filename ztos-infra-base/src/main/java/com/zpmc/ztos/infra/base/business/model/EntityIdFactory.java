package com.zpmc.ztos.infra.base.business.model;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.business.interfaces.IEntityId;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class EntityIdFactory {

    private static final Map REGISTERY = new HashMap();

    private EntityIdFactory() {
    }

    public static IEntityId valueOf(String inEntityName, String inAlias) {
        return new SimpleEntityId(inEntityName, inAlias);
    }

    public static IEntityId valueOf(String inEntityName) {
        IEntityId entityId = (IEntityId)REGISTERY.get(inEntityName);
        if (entityId == null) {
            entityId = new SimpleEntityId(inEntityName);
            EntityIdFactory.registerNew(entityId);
        }
        return entityId;
    }

    public static String getSimpleNameFromFullName(String inFQN) {
        return StringUtils.substringAfterLast((String)inFQN, (String)".");
    }

    private static synchronized void registerNew(IEntityId inId) {
        REGISTERY.put(inId.getEntityName(), inId);
    }

    private static class SimpleEntityId
            implements IEntityId,
            Comparable {
        final String _entityName;
        final String _alias;

        SimpleEntityId(String inEntityName) {
            this._entityName = inEntityName;
            this._alias = inEntityName;
        }

        SimpleEntityId(String inEntityName, String inAlias) {
            this._alias = inAlias;
            this._entityName = inEntityName;
        }

        @Override
        public String getEntityName() {
            return this._entityName;
        }

        @Override
        public String getAlias() {
            return this._alias;
        }

        public String toString() {
            return this.getEntityName();
        }

        public int compareTo(@NotNull Object inObject) {
            if (inObject instanceof IEntityId) {
                return this._entityName.compareTo(((IEntityId)inObject).getEntityName());
            }
            return 0;
        }
    }
}
