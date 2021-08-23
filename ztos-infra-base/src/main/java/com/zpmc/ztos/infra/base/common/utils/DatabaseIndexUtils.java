package com.zpmc.ztos.infra.base.common.utils;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.business.interfaces.ICarinaIndex;
import com.zpmc.ztos.infra.base.common.helps.DatabaseHelper;
import com.zpmc.ztos.infra.base.common.model.CarinaIndexFactory;
import com.zpmc.ztos.infra.base.common.model.IndexParsingException;
import com.zpmc.ztos.infra.base.common.model.Roastery;
import com.zpmc.ztos.infra.base.common.type.DatabaseType;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.mapping.Column;
import org.hibernate.mapping.Table;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.util.*;

public class DatabaseIndexUtils {
    public static final String DUP_INDEX = "dup_index";
    public static final String DUP_INDEX_NAME = "dup_index_name";
    public static final String TABLE_NAME_KEY = "table_name";
    public static final String INDEX_NAME_KEY = "index_name";
    public static final String COLUMN_NAMES_KEY = "column_names";
    public static final String INCLUDED_COLUMN_NAMES_KEY = "included_column_names";
    protected static final String NAME_TO_BE_GENERATED = "GENERATED_NAME_";
    private static int _indexNameSuffix;
    private static final Logger LOGGER;

    public static List<ICarinaIndex> getAllMetaDefinedIndexes(String inMetaSpecificDefinition) {
        ArrayList<ICarinaIndex> indexes = new ArrayList<ICarinaIndex>();
        SessionFactory sessionFactory = PersistenceUtils.getSessionFactory();
        Configuration configuration = PersistenceUtils.getMappingConfiguration();
        try {
            Set classNames = sessionFactory.getAllClassMetadata().keySet();
            for (Object className : classNames) {
//                PersistentClass classMapping = configuration.getClassMapping(className);
//                Table parentTable = classMapping.getTable();
//                MetaAttribute metaAttribute = classMapping.getMetaAttribute(inMetaSpecificDefinition);
//                if (metaAttribute == null) continue;
//                List<ICarinaIndex> indexesFromMetaAttr = DatabaseIndexUtils.getIndexesFromMetaAttr(metaAttribute.getValue(), parentTable.getName());
//                indexes.addAll(indexesFromMetaAttr);
            }
        }
        catch (HibernateException e) {
            LOGGER.error((Object)("IndexLoader: misconfigured HBM file, exception:  " + (Object)((Object)e)));
        }
        return indexes;
    }

    public static List<ICarinaIndex> getMetaDefinedTableIndexes(Table inTable) {
        SessionFactory hiberFactory = PersistenceUtils.getSessionFactory();
        Configuration configuration = PersistenceUtils.getMappingConfiguration();
        List<ICarinaIndex> hbmMetaDefIndexes = new ArrayList<ICarinaIndex>();
        try {
            Set classNames = hiberFactory.getAllClassMetadata().keySet();
            for (Object className : classNames) {
                String databaseSpecificMetaTag;
//                PersistentClass classMapping = configuration.getClassMapping(className);
//                Table aTable = classMapping.getTable();
//                if (aTable == null || !aTable.getName().equalsIgnoreCase(inTable.getName())) continue;
//                MetaAttribute metaAttribute = classMapping.getMetaAttribute("carina-index");
//                if (metaAttribute != null) {
//                    hbmMetaDefIndexes = DatabaseIndexUtils.getIndexesFromMetaAttr(metaAttribute.getValue(), inTable.getName());
//                    hbmMetaDefIndexes.addAll(hbmMetaDefIndexes);
//                }
//                if ((metaAttribute = classMapping.getMetaAttribute(databaseSpecificMetaTag = DatabaseIndexUtils.getSpecificDatabaseMetaDefinition())) == null) continue;
//                List<ICarinaIndex> hbmSpecificMetaDefIndexes = DatabaseIndexUtils.getIndexesFromMetaAttr(metaAttribute.getValue(), inTable.getName());
//                hbmMetaDefIndexes.addAll(hbmSpecificMetaDefIndexes);
            }
        }
        catch (HibernateException e) {
            LOGGER.error((Object)("IndexLoader: misconfigured HBM file, exception:  " + (Object)((Object)e)));
        }
        return hbmMetaDefIndexes;
    }

    private static List<ICarinaIndex> getIndexesFromMetaAttr(String inMetaAttribute, String inTableName) {
        ArrayList<ICarinaIndex> indexes = new ArrayList<ICarinaIndex>();
        inMetaAttribute = inMetaAttribute.replaceAll("[\\r]", "").replaceAll("[\\n]", "").trim();
        StringTokenizer indexTokens = new StringTokenizer(inMetaAttribute, ";");
        while (indexTokens.hasMoreTokens()) {
            String indexToken = indexTokens.nextToken();
            try {
                ICarinaIndex index = indexToken.trim().toLowerCase().startsWith("create") ? DatabaseIndexUtils.parseNativeSqlIndexFormat(indexToken, inTableName) : DatabaseIndexUtils.parseCarinaIndexFormat(indexToken, inTableName);
                indexes.add(index);
            }
            catch (IndexParsingException e) {
                LOGGER.error((Object)("IndexLoader: Carina index: " + indexToken + " was incorrectly defined in HBM file for table: " + inTableName + " ,skipping...\nException: " + e));
            }
            catch (Exception e) {
                LOGGER.error((Object)("IndexLoader: Carina index: " + indexToken + " cannot be parsed. Table: " + inTableName + " ,skipping...\nException: " + e));
            }
        }
        return indexes;
    }

    private static ICarinaIndex parseNativeSqlIndexFormat(String inToken, String inTableName) throws IndexParsingException {
        if (!inToken.toLowerCase().contains(" index ")) {
            throw new IndexParsingException("there is no INDEX keyword");
        }
        if (!inToken.toLowerCase().contains(" on ")) {
            throw new IndexParsingException("there is no ON keyword");
        }
        StringTokenizer sqlWords = new StringTokenizer(inToken, " \n\t\r()");
        String indexName = null;
        String tableName = null;
        StringBuilder carinaIndexFormat = new StringBuilder();
        while (sqlWords.hasMoreTokens()) {
            String nextWord = sqlWords.nextToken().trim();
            if ("on".equalsIgnoreCase(nextWord)) {
                if (!sqlWords.hasMoreTokens()) continue;
                tableName = sqlWords.nextToken().trim();
                break;
            }
            indexName = nextWord;
        }
        if (indexName == null) {
            throw new IndexParsingException("there is no index name");
        }
        if (tableName == null || !tableName.equalsIgnoreCase(inTableName)) {
            throw new IndexParsingException("there is no correct table or column name. Incoming table name is: " + inTableName);
        }
        carinaIndexFormat.append(indexName);
        carinaIndexFormat.append(inToken.substring(inToken.indexOf(40), inToken.indexOf(41) + 1));
        ICarinaIndex index = DatabaseIndexUtils.parseCarinaIndexFormat(carinaIndexFormat.toString(), inTableName);
        index.setNativeSqlQuery(inToken.trim());
        if (inToken.contains(" include ")) {
            String columnNamesString = inToken.substring(inToken.lastIndexOf(40) + 1, inToken.lastIndexOf(41)).trim();
            List<String> includeColumnNames = DatabaseIndexUtils.parseColumnNames(columnNamesString);
            index.setIncludeColumnNameList(includeColumnNames);
        }
        return index;
    }

    private static List<String> parseColumnNames(String inToken) {
        StringTokenizer columnNamesTokens = new StringTokenizer(inToken, ",");
        ArrayList<String> columnNames = new ArrayList<String>();
        while (columnNamesTokens.hasMoreTokens()) {
            columnNames.add(columnNamesTokens.nextToken().trim());
        }
        return columnNames;
    }

    private static ICarinaIndex parseCarinaIndexFormat(String inToken, String inTableName) {
        String indexName = inToken.substring(0, inToken.indexOf(40));
        if ((indexName = indexName.replaceAll("[\\r]", "").replaceAll("[\\n]", "").trim()).isEmpty()) {
            indexName = NAME_TO_BE_GENERATED + _indexNameSuffix++;
        }
        String columnNamesString = inToken.substring(inToken.indexOf(40) + 1, inToken.indexOf(41)).trim();
        List<String> columnNames = DatabaseIndexUtils.parseColumnNames(columnNamesString);
        DatabaseHelper dbHelper = (DatabaseHelper) Roastery.getBean("dbHelper");
        DatabaseType databaseType = dbHelper.databaseType();
        CarinaIndexFactory indexFactory = new CarinaIndexFactory(databaseType);
        return indexFactory.getCarinaIndexObject(inTableName, columnNames, indexName);
    }

    @Nullable
    public static ICarinaIndex buildCarinaIndex(Iterator<Column> inIterator, String inTableName, @Nullable String inIndexName) {
        inIndexName = inIndexName == null ? NAME_TO_BE_GENERATED + _indexNameSuffix++ : inIndexName;
        ArrayList<String> columnNames = new ArrayList<String>();
        while (inIterator.hasNext()) {
            columnNames.add(inIterator.next().getName().trim());
        }
        if (inTableName == null || columnNames.isEmpty()) {
            return null;
        }
        DatabaseHelper dbHelper = (DatabaseHelper) Roastery.getBean("dbHelper");
        DatabaseType databaseType = dbHelper.databaseType();
        CarinaIndexFactory indexFactory = new CarinaIndexFactory(databaseType);
        return indexFactory.getCarinaIndexObject(inTableName, columnNames, inIndexName);
    }

    @Nullable
    public static String getSpecificDatabaseMetaDefinition() {
        DatabaseHelper dbHelper = (DatabaseHelper) Roastery.getBean("dbHelper");
        if (dbHelper.isDatabaseType(DatabaseType.MYSQL)) {
            return "carina-mysql-index";
        }
        if (dbHelper.isDatabaseType(DatabaseType.SQLSERVER)) {
            return "carina-sqlserver-index";
        }
        if (dbHelper.isDatabaseType(DatabaseType.ORACLE)) {
            return "carina-oracle-index";
        }
        if (dbHelper.isDatabaseType(DatabaseType.POSTGRES)) {
            return "carina-postgres-index";
        }
        return null;
    }

    public static void buildDuplicatedIndexesMap(List<Map<String, Object>> inOutDuplicatedIndexesMap, @Nullable ICarinaIndex inGoodIndex, @NotNull ICarinaIndex inOutDuplicateIndex) {
        boolean isReal;
        DatabaseHelper dbHelper = (DatabaseHelper) Roastery.getBean("dbHelper");
        List<String> exceptions = DatabaseIndexUtils.getExcludedTables();
        if (exceptions.contains(inOutDuplicateIndex.getTableName().toLowerCase())) {
            return;
        }
        String goodIndexName = dbHelper.indexExists(inGoodIndex);
        String duplicateIndexName = dbHelper.indexExists(inOutDuplicateIndex);
        String tableName = inOutDuplicateIndex.getTableName();
        String duplicatedIndexColumnName = inOutDuplicateIndex.getColumnNames().get(0);
        boolean bl = isReal = goodIndexName != null && duplicateIndexName != null && !goodIndexName.equalsIgnoreCase(duplicateIndexName);
        if (isReal) {
            boolean exclude;
            boolean isDuplicatedIndexPK = duplicateIndexName.toUpperCase().startsWith("SYS") || duplicateIndexName.toUpperCase().startsWith("PK_") || duplicateIndexName.toUpperCase().endsWith("_PKEY") || duplicateIndexName.toUpperCase().endsWith("PRIMARY");
            boolean isGoodIndexPK = goodIndexName.toUpperCase().startsWith("SYS") || goodIndexName.toUpperCase().startsWith("PK_") || goodIndexName.toUpperCase().endsWith("_PKEY");
            boolean isDuplicatedIndexFK = duplicateIndexName.toUpperCase().endsWith("_FKI") || duplicateIndexName.toUpperCase().startsWith("FK");
            boolean bl2 = exclude = isDuplicatedIndexPK || isDuplicatedIndexFK;
            if (!exclude) {
                HashMap<String, Object> messageMap = new HashMap<String, Object>();
                messageMap.put(DUP_INDEX, inOutDuplicateIndex);
                messageMap.put(DUP_INDEX_NAME, duplicateIndexName);
                messageMap.put(TABLE_NAME_KEY, tableName);
                if (inGoodIndex != null) {
                    messageMap.put(INDEX_NAME_KEY, goodIndexName);
                    messageMap.put(COLUMN_NAMES_KEY, inGoodIndex.getColumnNames());
                    messageMap.put(INCLUDED_COLUMN_NAMES_KEY, inGoodIndex.getIncludeColumnNameList());
                }
                inOutDuplicatedIndexesMap.add(messageMap);
                inOutDuplicateIndex.setDuplicationFlag();
            }
        }
    }

    public static List<String> getExcludedTables() {
        return Arrays.asList("sessioninfo", "workiteminfo", "processinstanceinfo", "processinstancelog", "nodeinstancelog", "variableinstancelog", "eventtypes");
    }

    public static void setIndexNameSuffix(int inIndexNameSuffix) {
        _indexNameSuffix = inIndexNameSuffix;
    }

    static {
        LOGGER = Logger.getLogger(DatabaseIndexUtils.class);
    }
}
