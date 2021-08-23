package com.zpmc.ztos.infra.base.business.xps;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.business.interfaces.IEntity;
import com.zpmc.ztos.infra.base.business.interfaces.IXpsTableAware;
import com.zpmc.ztos.infra.base.common.database.HiberCache;
import org.apache.log4j.Logger;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class XpsDbManagerId {
    public static final String XPS_SPARCS_SETTINGS_FILE_KEY = "sparcs settings.txt";
    public static final String XPS_SPARCS_YARD_FILE_KEY = "yard.nyd";
    public static final String XPS_BERTH_FILE_KEY = "berths.txt";
    private static final Map<Class<?>, Integer> CLASS_2_TABLE_ID = new HashMap();
    private static final Logger LOGGER = Logger.getLogger(XpsDbManagerId.class);

    public static void registerEntity(Class<?> inEntityClass, int inTableId) {
        if (inEntityClass == null) {
            LOGGER.debug((Object)("registerEntity called with null for table id " + XpsDbManagerId.getTableNameAndId(inTableId) + ". Normal for {ContainerPosition}, {CheEcState}, {ReeferDetail}, {WorkExecution}. Skipping."));
            return;
        }
        if (inTableId < 0) {
            LOGGER.error((Object)("registerEntity called for class " + inEntityClass + " with invalid table id " + inTableId + ". Skipping."));
            return;
        }
        if (inTableId == 0) {
            LOGGER.error((Object)("registerEntity called for class " + inEntityClass + " with table id " + XpsDbManagerId.getTableNameAndId(inTableId) + ". Skipping."));
            return;
        }
        if (CLASS_2_TABLE_ID.containsKey(inEntityClass)) {
            Integer existingTableId = CLASS_2_TABLE_ID.get(inEntityClass);
            if (existingTableId != inTableId) {
                LOGGER.debug((Object)("registerEntity called for class " + inEntityClass + " with changing table id from " + XpsDbManagerId.getTableNameAndId(existingTableId) + " to " + XpsDbManagerId.getTableNameAndId(inTableId) + ". Normal for {UYV-ContainerSparcs}, {CarrierVisit-ShipVisit-TrainVisit}. Skipping."));
            }
            return;
        }
        LOGGER.debug((Object)("registerEntity called first time for class " + inEntityClass + " with table id " + XpsDbManagerId.getTableNameAndId(inTableId) + "."));
        CLASS_2_TABLE_ID.put(inEntityClass, inTableId);
    }

    public static int getTableId(Class<?> inEntityClass) {
        Integer id = CLASS_2_TABLE_ID.get(inEntityClass);
        return id == null ? -1 : id;
    }

    public static String getTableNameAndId(int inTableId) {
        return XpsDbManagerId.getTableName(inTableId) + "(" + inTableId + ")";
    }

    public static int getTableId(String inTableName) {
        int id = XpsTableId.fromString(inTableName);
        if (id != -1) {
            return id;
        }
        id = NonXpsModelTableId.fromString(inTableName);
        if (id != -1) {
            return id;
        }
        id = MiscCacheFileId.fromString(inTableName);
        if (id != -1) {
            return id;
        }
        LOGGER.error((Object)("XpsDbManager did not find the table id for table name " + inTableName + "."));
        return id;
    }

    public static boolean containsTableId(int inTableId) {
        return XpsDbManagerId.isXpsTableId(inTableId) || XpsDbManagerId.isNonXpsTableId(inTableId) || XpsDbManagerId.isMiscCacheTableId(inTableId);
    }

    public static boolean isXpsTableId(int inTableId) {
        return inTableId >= 0 && inTableId <= 120;
    }

    public static boolean isNonXpsTableId(int inTableId) {
        return inTableId >= 1000 && inTableId <= 1000;
    }

    public static boolean isMiscCacheTableId(int inTableId) {
        return inTableId >= 10000 && inTableId <= 10002;
    }

    public static boolean isImplementedInN4(int inTableId) {
        return XpsDbManagerId.isXpsTableId(inTableId) && inTableId != 101 && inTableId != 102 && inTableId != 104 && inTableId != 105;
    }

    @NotNull
    public static String getTableName(int inTableId) {
        if (XpsDbManagerId.isXpsTableId(inTableId)) {
            return XpsTableId.toString(inTableId);
        }
        if (XpsDbManagerId.isNonXpsTableId(inTableId)) {
            return NonXpsModelTableId.toString(inTableId);
        }
        if (XpsDbManagerId.isMiscCacheTableId(inTableId)) {
            return MiscCacheFileId.toString(inTableId);
        }
        return "?" + inTableId + "?";
    }

    public static Map<Class<?>, Integer> getClass2TableIdMap() {
        return Collections.unmodifiableMap(CLASS_2_TABLE_ID);
    }

    public static int getTableId(IEntity inEntity) {
        if (inEntity instanceof IXpsTableAware) {
            return ((IXpsTableAware)inEntity).getTableId();
        }
        Class entityClass = HiberCache.entityName2EntityClass((String)inEntity.getEntityName());
        return XpsDbManagerId.getTableId(entityClass);
    }

    public static boolean isTableImplementedInN4(int inTableId) {
        return inTableId != 101 && inTableId != 102 && inTableId != 104 && inTableId != 105;
    }

    private XpsDbManagerId() {
    }
}
