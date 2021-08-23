package com.zpmc.ztos.infra.base.common.utils;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.business.enums.framework.DatabaseDataTypeEnum;
import com.zpmc.ztos.infra.base.business.interfaces.ICarinaIndex;
import com.zpmc.ztos.infra.base.common.configs.BooleanConfig;
import com.zpmc.ztos.infra.base.common.helps.DatabaseHelper;
import com.zpmc.ztos.infra.base.common.model.CarinaIndexFactory;
import com.zpmc.ztos.infra.base.common.model.EmptyToSpaceCharUserType;
import com.zpmc.ztos.infra.base.common.model.IndexLoader;
import com.zpmc.ztos.infra.base.common.model.Roastery;
import com.zpmc.ztos.infra.base.common.type.DatabaseType;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.cfg.Configuration;
import org.hibernate.mapping.Column;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.type.Type;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

//import java.lang.module.Configuration;

public class DatabaseUtils {

    public static final String COLUMN_NAME = "COLUMN_NAME";
    public static final int MAX_COLUMN_NAME_LENGTH = 30;
    private static final String CONFIG_VALUE_COLUMN = "cnfig_value";
    private static final String CONFIG_TABLE = "config_settings";
    private static final String CONFIG_SETTINGS_QUERY = "select cnfig_value from config_settings where cfg_id='";
    private static final String TYPE_BLOB = DatabaseDataTypeEnum.BLOB.getTypeKey();
    private static final String TYPE_CLOB = DatabaseDataTypeEnum.CLOB.getTypeKey();
    public static final String USER_BLOB = "com.navis.framework.persistence.util.BlobUserType";
    private static final String TYPE_BINARY = DatabaseDataTypeEnum.BINARY.getTypeKey();
    private static final String TYPE_VARBINARY = DatabaseDataTypeEnum.VARBINARY.getTypeKey();
    private static final String TYPE_VARBINARY_MAX = DatabaseDataTypeEnum.VARBINARY_MAX.getTypeKey();
    private static final String TYPE_NVARCHAR = DatabaseDataTypeEnum.NVARCHAR.getTypeKey();
    private static final String TYPE_TEXT = DatabaseDataTypeEnum.TEXT.getTypeKey();
    private static final String STRING = "string";
    private static final String TYPE_VARCHAR = DatabaseDataTypeEnum.VARCHAR.getTypeKey();
    private static final String TYPE_VARCHAR2 = DatabaseDataTypeEnum.VARCHAR2.getTypeKey();
    private static final String TYPE_BYTEA = DatabaseDataTypeEnum.BYTEA.getTypeKey();
    private static final Logger LOGGER = Logger.getLogger(DatabaseUtils.class);

    private DatabaseUtils() {
    }

    @NotNull
    public static String generateIndexName(String inTableName, @Nullable String inIndexName, @NotNull String[] inColumnNames) {
        if (null != inIndexName) {
            if (inIndexName.length() > 30) {
                inIndexName = DatabaseUtils.truncat(inIndexName);
            }
            return inIndexName;
        }
        StringBuilder columnNames = new StringBuilder();
        for (String columnName : inColumnNames) {
            columnNames.append(columnName);
        }
        String indexName = inTableName.toLowerCase() + "_" + columnNames.toString().toLowerCase() + "_idx";
        if (indexName.length() > 30) {
            indexName = DatabaseUtils.truncat(indexName);
        }
        return indexName;
    }

    public static String generateFKIndexName(String inTableName, List<String> inColumnNames) {
        String[] columnNamesArray = new String[inColumnNames.size()];
        String indexName = DatabaseUtils.generateIndexName(inTableName, null, inColumnNames.toArray(columnNamesArray));
        int endOfName = indexName.length() - 4 > 0 ? indexName.length() - 4 : indexName.length();
        return indexName.substring(0, endOfName) + "_fki";
    }

    public static List<String> getIndexedColumnsForTable(List<ICarinaIndex> inCarinaIndexes, String inTableName) {
        ArrayList<String> allIndexedColumns = new ArrayList<String>();
        for (ICarinaIndex carinaIndex : inCarinaIndexes) {
            if (!carinaIndex.getTableName().equals(inTableName)) continue;
            allIndexedColumns.addAll(carinaIndex.getColumnNames());
        }
        return allIndexedColumns;
    }

    @NotNull
    private static String truncat(String inIndexName) {
        StringBuilder buff = new StringBuilder();
        for (int i = 0; i < 30; ++i) {
            int rawRandomNumber = (int)(Math.random() * (double)inIndexName.length());
            buff.append(inIndexName.charAt(rawRandomNumber));
        }
        buff.setCharAt(0, 'i');
        return buff.toString();
    }

    public static List<ICarinaIndex> convertToCarinaIndexes(List<Map<Object, Object>> inIndexes, DatabaseType inDatabaseType) {
        List<String> tablesToExcludeFormIndexView = DatabaseUtils.getTablesToExcludeFormIndexView();
        ArrayList<ICarinaIndex> carinaIndexes = new ArrayList<ICarinaIndex>();
        for (Map<Object, Object> index : inIndexes) {
            ICarinaIndex carinaIndex2;
            boolean createNew = true;
            String indexName = (String)index.get(ICarinaIndex.INDEX_NAME_MFID);
            String tableName = (String)index.get(ICarinaIndex.TABLE_NAME_MFID);
            if (tablesToExcludeFormIndexView.contains(tableName.toUpperCase())) continue;
            String columnName = (String)index.get(ICarinaIndex.COLUMN_NAME_MFID);
            Boolean includeColumnName = (Boolean)index.get(ICarinaIndex.INCLUDE_COLUMN_NAME_MFID);
            includeColumnName = includeColumnName == null ? false : includeColumnName;
            for (ICarinaIndex carinaIndex1 : carinaIndexes) {
                String carinaIndexName = carinaIndex1.getIndexName();
                String carinaTableName = carinaIndex1.getTableName();
                if (!indexName.equalsIgnoreCase(carinaIndexName) || !tableName.equalsIgnoreCase(carinaTableName) || columnName == null) continue;
                if (includeColumnName.booleanValue()) {
                    List<String> carinaIncludeColumnNames = carinaIndex1.getIncludeColumnNameList();
                    carinaIncludeColumnNames.add(columnName.toUpperCase());
                    carinaIndex1.setIncludeColumnNameList(carinaIncludeColumnNames);
                } else {
                    List<String> carinaColumnNames = carinaIndex1.getColumnNames();
                    carinaColumnNames.add(columnName.toUpperCase());
                    carinaIndex1.setColumnNames(carinaColumnNames);
                }
                createNew = false;
            }
            if (!createNew || !StringUtils.isNotEmpty((String)columnName)) continue;
            CarinaIndexFactory indexFactory = new CarinaIndexFactory(inDatabaseType);
            carinaIndex2 = indexFactory.getCarinaIndexObject(index);
            if (includeColumnName.booleanValue()) {
                carinaIndex2.setIncludeColumnNameList(Arrays.asList(columnName));
            } else {
                carinaIndex2.setColumnNames(Arrays.asList(columnName));
            }
            carinaIndexes.add(carinaIndex2);
        }
        return carinaIndexes;
    }

    public static String getMysqlDbName(String inURL) {
        String dbName = "";
        try {
            int lastSlash = inURL.lastIndexOf(47);
            dbName = inURL.substring(lastSlash + 1);
            int firstQuestion = dbName.indexOf(63);
            if (firstQuestion > 0) {
                dbName = dbName.substring(0, firstQuestion);
            }
        }
        catch (Exception e) {
            LOGGER.error((Object)"DatabaseUtils: incorrect URL");
        }
        return dbName;
    }

    public static void checkLobMapping(ClassMetadata inMetaData, StringBuilder inOutBuf) {
        int defaultColumnSize;
        Configuration configuration = PersistenceUtils.getMappingConfiguration();
//        PersistentClass classMapping = configuration.getClassMapping(inMetaData.getEntityName());
//        String tableName = classMapping.getTable().getName();
        int size = defaultColumnSize = 255;
        String[] names = inMetaData.getPropertyNames();
        String[] propertyNames = inMetaData.getPropertyNames();
        Type[] types = inMetaData.getPropertyTypes();
        DatabaseHelper dbHelper = (DatabaseHelper) Roastery.getBean("dbHelper");
        for (int i = 0; i < types.length; ++i) {
            String columnType;
            String columnName;
            Column columnObject;
            Type type = types[i];
            if (TYPE_BINARY.equalsIgnoreCase(type.getName())) {
                inOutBuf.append("Binary type is deprecated. Entity: " + inMetaData.getEntityName() + " property: " + names[i] + " declared as Binary.\n ");
            }
            if (TYPE_BLOB.equalsIgnoreCase(type.getName())) {
                inOutBuf.append("we don't support blob type. Use BlobUserType instead. Entity: " + inMetaData.getEntityName() + " property: " + names[i] + " declared as Blob.\n ");
            }
            if (TYPE_CLOB.equalsIgnoreCase(type.getName())) {
                inOutBuf.append("we don't support clob type. Use 'text' instead. Entity: " + inMetaData.getEntityName() + " property: " + names[i] + " declared as Clob.\n ");
            }
            if (USER_BLOB.equalsIgnoreCase(type.getName())) {
                if (dbHelper.isDatabaseType(DatabaseType.MYSQL)) continue;
                columnObject = HibernateMappingUtils.getColumnObject(configuration, inMetaData, propertyNames[i]);
                columnName = columnObject == null ? null : columnObject.getName();
//                String string = columnType = columnName == null ? null : dbHelper.columnType(tableName, columnName);
//                if (!(!StringUtils.isNotEmpty((String)columnType) || TYPE_BLOB.equalsIgnoreCase(columnType) || TYPE_VARBINARY_MAX.equalsIgnoreCase(columnType) || TYPE_VARBINARY.equalsIgnoreCase(columnType) || TYPE_BYTEA.equalsIgnoreCase(columnType))) {
//                    inOutBuf.append("Entity: " + inMetaData.getEntityName() + " property: " + names[i] + " declared as 'BlobUserType',  but the database stores it as:  " + columnType + "\n");
//                }
            }
            if ("text".equalsIgnoreCase(type.getName())) {
                columnObject = HibernateMappingUtils.getColumnObject(configuration, inMetaData, propertyNames[i]);
                size = HibernateMappingUtils.getColumnSize(columnObject, size);
                if (size <= 4000 && size != defaultColumnSize) {
                    inOutBuf.append("Entity: " + inMetaData.getEntityName() + " property: " + names[i] + " declared as 'text' with length = " + size + ". Type 'text' will be converted to expensive LOB type. If you need less than 4000, use 'string' type\n");
                }
                columnName = columnObject == null ? null : columnObject.getName();
//                String string = columnType = columnName == null ? null : dbHelper.columnType(tableName, columnName);
//                if (StringUtils.isNotEmpty((String)columnType) && !TYPE_CLOB.equalsIgnoreCase(columnType) && !TYPE_NVARCHAR.equalsIgnoreCase(columnType) && !columnType.toLowerCase().contains(TYPE_TEXT)) {
//                    inOutBuf.append("Entity: " + inMetaData.getEntityName() + " property: " + names[i] + " declared as 'text',  but the database stores it as:  " + columnType + "\n");
//                }
            }
            if (STRING.equalsIgnoreCase(type.getName())) {
                columnObject = HibernateMappingUtils.getColumnObject(configuration, inMetaData, propertyNames[i]);
                size = HibernateMappingUtils.getColumnSize(columnObject, size);
                if (size > 4000) {
                    inOutBuf.append("Entity: " + inMetaData.getEntityName() + " property: " + names[i] + " declared as 'string' with length = " + size + ". You MUST use 'text' type.\n");
                }
                columnName = columnObject == null ? null : columnObject.getName();
//                String string = columnType = columnName == null ? null : dbHelper.columnType(tableName, columnName);
//                if (StringUtils.isNotEmpty((String)columnType) && !TYPE_VARCHAR2.equalsIgnoreCase(columnType) && !TYPE_NVARCHAR.equalsIgnoreCase(columnType) && !TYPE_VARCHAR.equalsIgnoreCase(columnType)) {
//                    inOutBuf.append("Entity: " + inMetaData.getEntityName() + " property: " + names[i] + " declared as 'string', but the database stores it as " + columnType);
//                }
            }
            if (!Character.class.equals((Object)type.getReturnedClass()) || EmptyToSpaceCharUserType.class.getName().equals(type.getName())) continue;
            String field = names[i];
            inOutBuf.append(field + "(" + inMetaData.getEntityName() + ") cannot be of Character.Please use type string or EmptyToSpaceCharUserType.\n ");
        }
    }

    @NotNull
    public static StringBuilder checkAndFixSpatialIndexesDefinition() {
        StringBuilder errors = new StringBuilder();
        DatabaseHelper dbHelper = (DatabaseHelper) Roastery.getBean("dbHelper");
        if (dbHelper.isDatabaseType(DatabaseType.ORACLE)) {
            List<ICarinaIndex> mandatorySpatialIndexes = DatabaseUtils.getMissedSpatialIndexes();
            for (ICarinaIndex missedMandatorySpatialIndex : mandatorySpatialIndexes) {
                String tableName = missedMandatorySpatialIndex.getTableName();
                String columnName = missedMandatorySpatialIndex.getColumnNames().get(0);
//                if (dbHelper.reCreateGeometryMetaDataAndIndex(tableName, colmunName, DatabaseUtils.generateIndexName(tableName, null, new String[]{columnName})).booleanValue()) {
//                    LOGGER.error((Object)("Table: " + tableName + " has column: " + columnName + " declared as Geometry, but there is no definition in HBM file. The missed index was created, but HBM file should be updated."));
//                    continue;
//                }
                errors.append("Table: " + tableName + " has column: " + columnName + " declared as Geometry, but there is no index on it\n");
            }
        }
        return errors;
    }

    @NotNull
    public static StringBuilder checkSpatialIndexesDefinition() {
        StringBuilder errors = new StringBuilder();
        List<ICarinaIndex> mandatorySpatialIndexes = DatabaseUtils.getMissedSpatialIndexes();
        for (ICarinaIndex missedMandatorySpatialIndex : mandatorySpatialIndexes) {
            String tableName = missedMandatorySpatialIndex.getTableName();
            String columnName = missedMandatorySpatialIndex.getColumnNames().get(0);
            errors.append("Table: " + tableName + " has column: " + columnName + " declared as Geometry, but there is no index on it\n");
        }
        return errors;
    }

    private static List<ICarinaIndex> getMissedSpatialIndexes() {
        IndexLoader loader = (IndexLoader) Roastery.getBean("dbIndexLoaderFromMapping");
        List<ICarinaIndex> mandatorySpatialIndexes = loader.getMandatorySpatialIndexes();
        List<ICarinaIndex> definedSpecificIndexes = loader.getHbmDatabaseSpecificIndexes();
        mandatorySpatialIndexes.removeAll(definedSpecificIndexes);
        return mandatorySpatialIndexes;
    }

    @NotNull
    public static StringBuilder validateSpatialIndexes() {
        StringBuilder errors = new StringBuilder();
        DatabaseHelper dbHelper = (DatabaseHelper) Roastery.getBean("dbHelper");
        if (dbHelper.isDatabaseType(DatabaseType.ORACLE)) {
            for (Map<String, Object> corruptedIndex : dbHelper.getCorruptedGeometryIndexes()) {
                String indexName;
                String columnName;
                String tableName = (String)corruptedIndex.get(ICarinaIndex.TABLE_NAME_MFID);
                if (dbHelper.reCreateGeometryMetaDataAndIndex(tableName, columnName = (String)corruptedIndex.get(ICarinaIndex.COLUMN_NAME_MFID), indexName = (String)corruptedIndex.get(ICarinaIndex.INDEX_NAME_MFID)).booleanValue()) continue;
                errors.append("An attempt to fix spatial index: " + indexName + " on table: " + tableName + " for column: " + columnName + " has failed\n");
            }
            for (Map<String, Object> corruptedIndex : dbHelper.getCorruptedGeometryIndexes()) {
                errors.append("Spatial index: " + corruptedIndex.get(ICarinaIndex.INDEX_NAME_MFID) + " on table: " + corruptedIndex.get(ICarinaIndex.TABLE_NAME_MFID) + " for column: " + corruptedIndex.get(ICarinaIndex.COLUMN_NAME_MFID) + " is corrupted. Please drop and re-create table metadata and index.\n");
            }
        }
        return errors;
    }

    public static String bytesToHexString(byte[] inBytes) {
        StringBuilder result = new StringBuilder();
        for (byte inByte : inBytes) {
            result.append(Integer.toString((inByte & 0xFF) + 256, 16).substring(1));
        }
        return result.toString();
    }

    public static boolean isConfigSettingOn(DatabaseHelper inDbHelper, BooleanConfig inConfig) {
        String val = null;
        if (inDbHelper.tableExists(CONFIG_TABLE)) {
            val = inDbHelper.queryForObject(CONFIG_SETTINGS_QUERY + inConfig.getConfigId() + "'", String.class);
        }
        if (null != val) {
            return Boolean.valueOf(val);
        }
        return (Boolean)inConfig.getConfigDefaultValue();
    }

    public static long objectToLong(Object inValue) {
        long count = 0L;
        if (inValue instanceof BigDecimal) {
            count = ((BigDecimal)inValue).longValue();
        } else if (inValue instanceof Long) {
            count = (Long)inValue;
        }
        return count;
    }

    public static String convertExpectedMinutesToString(long inMinutes, String inTimeString) {
        if (inMinutes > 60L) {
            inTimeString = Math.round(inMinutes / 60L) + " hour";
            if (inMinutes > 120L) {
                inTimeString = inTimeString + "s";
            }
        }
        return inTimeString;
    }

    public static double objectToDouble(Object inValue) {
        double count = 0.0;
        if (inValue instanceof BigDecimal) {
            count = ((BigDecimal)inValue).doubleValue();
        } else if (inValue instanceof Double) {
            count = (Double)inValue;
        }
        return count;
    }

    public static List<String> getTablesToExcludeFormIndexView() {
        ArrayList<String> tableToExclude = new ArrayList<String>();
        tableToExclude.add("FRM_JOB_GROUP_NODE");
        return tableToExclude;
    }

    public static void estimateColumnAlteringTime(String inTableName, String inColumnName) {
        try {
            DatabaseHelper dbHelper = (DatabaseHelper) Roastery.getBean("dbHelper");
            Long tableSize = dbHelper.queryForObject("SELECT COUNT(*) FROM " + inTableName, Long.class);
            if (tableSize == null) {
                return;
            }
            long estimatedLowerTimeInMinutes = Math.round((double)(tableSize / 1000000L) * 1.5 / 3.0);
            String lowerTime = DatabaseUtils.convertExpectedMinutesToString(estimatedLowerTimeInMinutes, " few minutes");
            long estimatedUpperTimeInMinutes = Math.round((double)(tableSize / 1000000L) * 1.5 * 3.0);
            if (estimatedUpperTimeInMinutes == 0L) {
                return;
            }
            String upperTime = DatabaseUtils.convertExpectedMinutesToString(estimatedUpperTimeInMinutes, " one hour");
            LOGGER.warn((Object)("Altering table " + inTableName + " column " + inColumnName + ". The table has " + tableSize + " records and this transaction may take from " + lowerTime + " up to " + upperTime + " to complete"));
        }
        catch (Exception e) {
            LOGGER.error((Object)("Cannot estimate timing " + e));
        }
    }
}
