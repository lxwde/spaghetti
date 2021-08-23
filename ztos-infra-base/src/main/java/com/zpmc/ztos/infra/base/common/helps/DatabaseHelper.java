package com.zpmc.ztos.infra.base.common.helps;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.business.interfaces.ICarinaIndex;
import com.zpmc.ztos.infra.base.business.interfaces.IMessageCollector;
import com.zpmc.ztos.infra.base.common.contexts.PortalApplicationContext;
import com.zpmc.ztos.infra.base.common.exceptions.BizFailure;
import com.zpmc.ztos.infra.base.common.model.*;
import com.zpmc.ztos.infra.base.common.type.DatabaseType;
import com.zpmc.ztos.infra.base.common.type.FuzzyBoolean;
import com.zpmc.ztos.infra.base.common.utils.BeanFactoryUtil;
import com.zpmc.ztos.infra.base.common.utils.DatabaseUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.BatchUpdateException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

public abstract class DatabaseHelper {

    public static final String BEAN_ID = "dbHelper";
    protected final DatabaseType _dbType;
    DatabaseVersion _dbVersion = DatabaseVersion.UNKNOWN;
    protected final DataSource _dataSource;
    protected final JdbcTemplate _jt;
    private static final String CONSTRAINT_NAME = "CONSTRAINT_NAME";
    private static final String POSITION = "POSITION";
    private static final String FK_NAME = "FK_NAME";
    public static final String SPATIAL_DIALECT = "hibernate.spatial.dialect";
    protected String _productName;
    protected String _productVersion;
    private String _driverName;
    private String _driverVersion;
    protected String _url;
    protected int _dbMajorVersion;
    private static final String CONFIG_ID_COLUMN = "cfg_id";
    private static final String CONFIG_VAL_COLUMN = "cnfig_value";
    private static final String CONFIG_TABLE = "config_settings";
    protected final ApplicationContext _applicationContext;
    private static final Logger LOGGER = Logger.getLogger(DatabaseHelper.class);

    public DatabaseHelper(@NotNull DataSource inDataSource, @NotNull DatabaseType inDbType, @NotNull String inProductName, @NotNull String inProductVersion, @NotNull String inDriverName, @NotNull String inDriverVersion, @NotNull String inUrl, int inDdbMajorVersion, ApplicationContext inAppContext) {
        this._dataSource = inDataSource;
        this._dbType = inDbType;
        this._jt = new JdbcTemplate(this._dataSource);
        this._applicationContext = inAppContext;
        this._productName = inProductName;
        this._productVersion = inProductVersion;
        this._driverName = inDriverName;
        this._driverVersion = inDriverVersion;
        this._url = inUrl;
        this._dbMajorVersion = inDdbMajorVersion;
    }

    public void execute(@NotNull String inQueryString) {
        try {
            this._jt.execute(inQueryString);
        }
        catch (DataAccessException e) {
            LOGGER.error((Object)("jtExecute: failed sql = " + inQueryString), (Throwable)e);
            throw BizFailure.wrap(e);
        }
    }

    @Nullable
    public Long getCount(@NotNull String inTableName) {
        String sqlScript = "SELECT COUNT(*) FROM " + inTableName;
        try {
 //           return this._jt.queryForLong(sqlScript);
        }
        catch (Throwable e) {
            LOGGER.error((Object)("getCount for" + inTableName + " failed with " + e));
            return null;
        }
        return null;
    }

    public FuzzyBoolean isSqlServerIdentityColumn(@NotNull String inTableName, @NotNull String inColumnName) {
        return FuzzyBoolean.UNSPECIFIED;
    }

    public final DatabaseType databaseType() {
        return this._dbType;
    }

    public final DatabaseVersion databaseVersion() {
        return this._dbVersion;
    }

    @Deprecated
    public final boolean isDatabaseType(@NotNull DatabaseType inDatabaseType) {
        return inDatabaseType.equals(this._dbType);
    }

    public final void alterLongToDouble(@NotNull String inTableName, @NotNull String inColName) {
        String statement = this.getAlterLongToDoubleStatement(inTableName, inColName);
        DatabaseUtils.estimateColumnAlteringTime(inTableName, inColName);
        this.jtUpdate(statement);
    }

    protected abstract String getAlterLongToDoubleStatement(@NotNull String var1, @NotNull String var2);

    public void alterColumnToStringType(@NotNull String inTableName, @NotNull String inColumnName, int inSize) {
        if (inSize < 2 || inSize > 4000) {
            throw BizFailure.create("Cannot modify column: " + inColumnName + " in table: " + inTableName + " with size of: " + inSize);
        }
        if (!this.columnExists(inTableName, inColumnName)) {
            throw BizFailure.create("Cannot find column: " + inColumnName + " in table: " + inTableName);
        }
        String indexName = this.indexExists(inTableName, new String[]{inColumnName});
        if (indexName != null && !this.dropIndex(inTableName, indexName)) {
            throw BizFailure.create("Cannot drop index: " + indexName + " on column: " + inColumnName + " in table: " + inTableName);
        }
        String constraintName = this.isAnyConstraintExists(inTableName, inColumnName);
        if (constraintName != null) {
            if (indexName != null) {
                this.addIndex(inTableName, inColumnName, indexName, false);
            }
            throw BizFailure.create("Constraint: " + constraintName + " exists on column: " + inColumnName + " in table: " + inTableName);
        }
        String alterQuery = this.getAlterColumnToStringStatement(inTableName, inColumnName, inSize);
        this.jtUpdate(alterQuery);
        if (indexName != null) {
            this.addIndex(inTableName, inColumnName, indexName, false);
        }
    }

    protected abstract String getAlterColumnToStringStatement(@NotNull String var1, @NotNull String var2, int var3);

    @Nullable
    public String isAnyConstraintExists(@NotNull String inTableName, @NotNull String inColName) {
        String pk = this.findPKConstraintNameByColumnNames(inTableName, new String[]{inColName});
        if (pk != null) {
            return pk;
        }
        String fk = this.findFKConstraintNameByColumnNames(inTableName, new String[]{inColName});
        if (fk != null) {
            return fk;
        }
        String uk = this.findUniqueConstraintNameByColumnNames(inTableName, new String[]{inColName});
        if (uk != null) {
            return uk;
        }
        return null;
    }

    public final boolean isDeletionConstraintViolation(@NotNull Throwable inThrowable) {
        SQLException ex;
        String message = inThrowable.getMessage();
        if (message == null) {
            return false;
        }
        if (message.contains("Call getNextException") && inThrowable instanceof BatchUpdateException && (ex = ((BatchUpdateException)inThrowable).getNextException()) != null) {
            message = ex.getMessage();
        }
        String expectedMessage = this.getDeleteConstraintViolationExpectedMessage();
        return message.toLowerCase().contains(expectedMessage.toLowerCase());
    }

    protected abstract String getDeleteConstraintViolationExpectedMessage();

    public final boolean isUniqueConstraintViolation(@NotNull Throwable inThrowable) {
        String message = inThrowable.getMessage();
        if (message == null) {
            return false;
        }
        String expectedMessage = this.getUniqueConstraintViolationExpectedMessage();
        return message.toLowerCase().contains(expectedMessage.toLowerCase());
    }

    protected abstract String getUniqueConstraintViolationExpectedMessage();

    public final void makeColumnNullable(@NotNull String inTableName, @NotNull String inColumnName) {
        this.setColumnNullability(inTableName, inColumnName, true);
    }

    public final void makeColumnNotNullable(@NotNull String inTableName, @NotNull String inColumnName, @NotNull String inDefaultValue) {
        String query = "select 1 from frm_carina_dual where exists (select 1 from " + inTableName + " where " + inColumnName + " is null)";
        String updateQuery = "update " + inTableName + " set " + inColumnName + " = " + inDefaultValue + " where " + inColumnName + " is null ";
        if (Integer.valueOf(1).equals(this.queryForObject(query, Integer.class))) {
            this.updateForObject(updateQuery);
        }
        this.setColumnNullability(inTableName, inColumnName, false);
    }

    public int jtUpdate(@NotNull String inUpdateString) {
        try {
            return this._jt.update(inUpdateString);
        }
        catch (DataAccessException e) {
            LOGGER.warn((Object)("jtUpdate: failed sql = " + inUpdateString), (Throwable)e);
            throw e;
        }
    }

    public final boolean dropIndex(@NotNull String inTableName, @NotNull String inIndexName) {
        return this.safeJtUpdate(this.getDropIndexStatement(inTableName, inIndexName));
    }

    protected abstract String getDropIndexStatement(@NotNull String var1, @NotNull String var2);

    protected abstract void initDatabaseVersion();

    public abstract boolean isDatabaseEnterpriseEdition();

    protected boolean safeJtUpdate(@NotNull String inQuery) {
        try {
            this.jtUpdate(inQuery);
            return true;
        }
        catch (Exception e) {
            LOGGER.error((Object)(" query " + inQuery + " has failed with message " + e));
            return false;
        }
    }

    public final boolean dropIndexIfExists(@NotNull String inTableName, @NotNull String[] inColumnNames) {
        CarinaIndexFactory indexFactory = new CarinaIndexFactory(this._dbType);
        ICarinaIndex carinaIndex = indexFactory.getCarinaIndexObject(inTableName, Arrays.asList(inColumnNames), null);
        String indexName = this.indexExists(carinaIndex);
        if (indexName == null) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug((Object)"dropIndexIfExists: index doesn't exist");
            }
            return false;
        }
        return this.dropIndex(inTableName, indexName);
    }

    @Deprecated
    public boolean dropConstraintByColumnNames(@NotNull String inTableName, @NotNull String[] inColumnNames) {
        String constraintName = this.findConstraintNameByColumnNames(inTableName, inColumnNames);
        return this.dropConstraint(inTableName, constraintName);
    }

    @Deprecated
    @Nullable
    private String findConstraintNameByColumnNames(@NotNull String inTableName, @NotNull String[] inColumnNames) {
        String query = this.getFindConstraintByColunNamesQuery();
        if (StringUtils.isNotEmpty((String)query)) {
            try {
                List constraints = this.queryForList(query + inTableName.toUpperCase() + "' order by CONSTRAINT_NAME, POSITION ");
                return DatabaseHelper.getConstraintNameByColumnNames(inColumnNames, constraints);
            }
            catch (Exception e) {
                LOGGER.error((Object)("Problem to find constraint: " + e));
            }
        }
        return null;
    }

    @Deprecated
    protected abstract String getFindConstraintByColunNamesQuery();

    public final boolean dropFKConstraintByColumnNames(@NotNull String inTableName, @NotNull String[] inColumnNames) {
        String constraintName = this.findFKConstraintNameByColumnNames(inTableName, inColumnNames);
        return this.dropConstraint(inTableName, constraintName);
    }

    @Nullable
    public final String findFKConstraintNameByColumnNames(@NotNull String inTableName, @NotNull String[] inColumnNames) {
        String query = this.getFindTableFKConstraintQuery(inTableName);
        if (StringUtils.isNotBlank((String)query)) {
            try {
                List constraints = this.queryForList(query);
                return DatabaseHelper.getConstraintNameByColumnNames(inColumnNames, constraints);
            }
            catch (Exception e) {
                LOGGER.error((Object)("Problem finding constraint: " + e));
            }
        }
        return null;
    }

    protected abstract String getFindTableFKConstraintQuery(@NotNull String var1);

    @Nullable
    public abstract String getDisableFKStatement(@NotNull String var1, @NotNull String var2, boolean var3);

    public boolean disableTableFK(@NotNull String inTableName, boolean inDisable) {
        String query = this.getFindTableFKConstraintQuery(inTableName);
        if (StringUtils.isNotBlank((String)query)) {
            try {
                List constraints = this.queryForList(query);
                for (Object constraint : constraints) {
                    String constraintName = (String)((Map)constraint).get("CONSTRAINT_NAME");
                    String alterQuery = this.getDisableFKStatement(inTableName, constraintName, inDisable);
                    if (alterQuery == null) continue;
                    this.execute(alterQuery);
                }
            }
            catch (Exception e) {
                LOGGER.error((Object)("Problem disabling/enabling constraints: " + e));
                return false;
            }
        }
        return true;
    }

    public final boolean dropFKConstraintByReferencedTable(@NotNull String inTableName, @NotNull String inReferencedTableName) {
        String constraintName = this.findFKConstraintNameByReferencedTable(inTableName, inReferencedTableName);
        return this.dropConstraint(inTableName, constraintName);
    }

    @Nullable
    public final String findFKConstraintNameByReferencedTable(@Nullable String inTableName, @Nullable String inReferencedTableName) {
        String query = this.getFindFKConstraintByReferencedTableQuery(inTableName, inReferencedTableName);
        if (inTableName == null || inReferencedTableName == null || query == null) {
            return null;
        }
        try {
            return this.queryForObject(query, String.class);
        }
        catch (Exception e) {
            LOGGER.error((Object)("Problem to find constraint: " + e));
            return null;
        }
    }

    protected abstract String getFindFKConstraintByReferencedTableQuery(@NotNull String var1, @NotNull String var2);

    public boolean dropUniqueConstraintByColumnNames(@NotNull String inTableName, @NotNull String[] inColumnNames) {
        String constraintName = this.findUniqueConstraintNameByColumnNames(inTableName, inColumnNames);
        return this.dropConstraint(inTableName, constraintName);
    }

    @Nullable
    public String findUniqueConstraintNameByColumnNames(@NotNull String inTableName, @NotNull String[] inColumnNames) {
        String query = this.getFindUniqueConstraintByColunNamesQuery(inTableName);
        if (StringUtils.isNotBlank((String)query)) {
            try {
                List constraints = this.queryForList(query);
                return DatabaseHelper.getConstraintNameByColumnNames(inColumnNames, constraints);
            }
            catch (Exception e) {
                LOGGER.error((Object)("Problem to find constraint: " + e));
            }
        }
        return null;
    }

    protected abstract String getFindUniqueConstraintByColunNamesQuery(@NotNull String var1);

    public boolean dropCheckConstraintByColumnNames(@NotNull String inTableName, @NotNull String[] inColumnNames) {
        String constraintName = this.findCheckConstraintNameByColumnNames(inTableName, inColumnNames);
        return this.dropConstraint(inTableName, constraintName);
    }

    @Nullable
    public String findCheckConstraintNameByColumnNames(@NotNull String inTableName, @NotNull String[] inColumnNames) {
        String query = this.getFindCheckConstraintByColunNamesQuery(inTableName);
        if (StringUtils.isNotBlank((String)query)) {
            try {
                List constraints = this.queryForList(query);
                return DatabaseHelper.getConstraintNameByColumnNames(inColumnNames, constraints);
            }
            catch (Exception e) {
                LOGGER.error((Object)("Problem to find constraint: " + e));
            }
        }
        return null;
    }

    @Nullable
    protected abstract String getFindCheckConstraintByColunNamesQuery(@NotNull String var1);

    @Nullable
    public final String findPKConstraintNameByColumnNames(@NotNull String inTableName, @NotNull String[] inColumnNames) {
        String query = this.getFindPKConstraintByColunNamesQuery(inTableName);
        List constraints = this.queryForList(query);
        return DatabaseHelper.getConstraintNameByColumnNames(inColumnNames, constraints);
    }

    @Nullable
    protected abstract String getFindPKConstraintByColunNamesQuery(@NotNull String var1);

    @Nullable
    protected static String getConstraintNameByColumnNames(@NotNull String[] inColumnNames, @NotNull List<Map<Object, Object>> inConstraints) {
        String inColumnNamesConcatenated = StringUtils.join((Object[])inColumnNames);
        ArrayList<Map<Object, Object>> constraintsConcatenatedColumnNames = new ArrayList<Map<Object, Object>>();
        for (Map<Object, Object> constraint : inConstraints) {
            String columnName = constraint.get("COLUMN_NAME").toString();
            String positionString = constraint.get("POSITION") == null ? "1" : constraint.get("POSITION").toString();
            int position = Integer.parseInt(positionString);
            if (position > 1) {
                int lastElementIndex = constraintsConcatenatedColumnNames.size() - 1;
                String concatenatedColumnName = ((Map)constraintsConcatenatedColumnNames.get(lastElementIndex)).get("COLUMN_NAME").toString();
                constraint.put("COLUMN_NAME", concatenatedColumnName.concat(columnName));
                constraintsConcatenatedColumnNames.set(lastElementIndex, constraint);
                continue;
            }
            constraintsConcatenatedColumnNames.add(constraint);
        }
        for (Map<Object, Object> constraintsConcatenetedColumnName : constraintsConcatenatedColumnNames) {
            if (!constraintsConcatenetedColumnName.get("COLUMN_NAME").toString().equalsIgnoreCase(inColumnNamesConcatenated)) continue;
            return constraintsConcatenetedColumnName.get("CONSTRAINT_NAME").toString();
        }
        return null;
    }

    protected boolean dropConstraint(@Nullable String inTableName, @Nullable String inConstraintToRemove) {
        if (inConstraintToRemove == null || inTableName == null) {
            LOGGER.info((Object)("The constraint " + inConstraintToRemove + " for table " + inTableName + " not found"));
            return false;
        }
        return this.safeJtUpdate("ALTER TABLE " + inTableName + " DROP CONSTRAINT " + inConstraintToRemove);
    }

    public final void addUniqueConstraint(@Nullable String inTableName, @Nullable String[] inColumnNames, @Nullable String inConstraintName) {
        if (inConstraintName == null || inColumnNames == null || inTableName == null) {
            return;
        }
        StringBuilder sql = new StringBuilder("ALTER TABLE " + inTableName + " ADD CONSTRAINT " + inConstraintName + " UNIQUE (");
        for (int i = 0; i < inColumnNames.length; ++i) {
            if (i > 0) {
                sql.append(", ");
            }
            sql.append(inColumnNames[i]);
        }
        sql.append(")");
        try {
            this.jtUpdate(sql.toString());
        }
        catch (Throwable t) {
            LOGGER.error((Object)("Unable to create unique constraint " + inConstraintName + " on table " + inTableName));
            throw BizFailure.wrap(t);
        }
    }

    public final void addFKConstraint(String inTableName, String[] inColumnNames, String inReferencedTable, String[] inReferencedColumnNames, String inConstraintName) {
        if (inColumnNames == null || inTableName == null || inReferencedTable == null || inReferencedColumnNames == null) {
            return;
        }
        String constrainName = "";
        if (inConstraintName != null) {
            constrainName = " CONSTRAINT " + inConstraintName + " ";
        }
        StringBuilder columns = new StringBuilder();
        for (int i = 0; i < inColumnNames.length; ++i) {
            if (i > 0) {
                columns.append(", ");
            }
            columns.append(inColumnNames[i]);
        }
        StringBuilder referencedColumns = new StringBuilder();
        for (int i = 0; i < inReferencedColumnNames.length; ++i) {
            if (i > 0) {
                referencedColumns.append(", ");
            }
            referencedColumns.append(inReferencedColumnNames[i]);
        }
        String sql = "ALTER TABLE " + inTableName + " ADD " + constrainName + " FOREIGN KEY (" + columns + ") REFERENCES " + inReferencedTable + "(" + referencedColumns + ")";
        try {
            this.jtUpdate(sql);
        }
        catch (Throwable t) {
            LOGGER.error((Object)("Unable to create FK constraint " + inConstraintName + " on table " + inTableName));
            throw BizFailure.wrap(t);
        }
    }

    protected boolean addIndex(String inTableName, String inColumnName, String inIndexName, boolean inIsIndexForDescOrder) {
        String[] colNames = new String[]{inColumnName};
        boolean[] isDesc = new boolean[]{inIsIndexForDescOrder};
        return this.addIndex(inTableName, inIndexName, colNames, isDesc);
    }

    protected boolean addIndex(String inTableName, String inIndexName, String[] inColumnNames, boolean[] inIsDescending) {
        if (inColumnNames == null || inTableName == null || inColumnNames[0] == null) {
            return false;
        }
        try {
            String indexName = DatabaseUtils.generateIndexName(inTableName, inIndexName, inColumnNames);
            if (StringUtils.isEmpty((String)indexName)) {
                return false;
            }
            if (this.indexExists(inTableName, indexName)) {
                LOGGER.warn((Object)("Method addIndex called but index already exists: " + inIndexName));
                return false;
            }
            CarinaIndexFactory indexFactory = new CarinaIndexFactory(this._dbType);
            ICarinaIndex carinaIndex = indexFactory.getCarinaIndexObject(inTableName, Arrays.asList(inColumnNames), null);
            if (this.indexExists(carinaIndex) != null) {
                LOGGER.warn((Object)("Method addIndex called but index already exists for columns: " + Arrays.toString(inColumnNames)));
                return false;
            }
            boolean[] isDescending = new boolean[inColumnNames.length];
            for (int i = 0; i < inColumnNames.length; ++i) {
                isDescending[i] = inIsDescending != null && i < inIsDescending.length && inIsDescending[i];
            }
            StringBuilder sql = new StringBuilder("CREATE INDEX " + indexName + " ON  " + inTableName + " (");
            for (int i = 0; i < inColumnNames.length; ++i) {
                if (i > 0) {
                    sql.append(", ");
                }
                sql.append(inColumnNames[i]);
                String order = isDescending[i] ? " DESC" : " ASC";
                sql.append(order);
            }
            sql.append(") ");
            LOGGER.info((Object)("addIndex: sql - " + sql));
            this.jtUpdate(sql.toString());
        }
        catch (DataAccessException e) {
            LOGGER.warn((Object)"Upgrade will continue despite the fact the index couldn't be added (exception above)");
            return false;
        }
        catch (Exception e) {
            LOGGER.warn((Object)("addIndex: Problem creating index: " + e));
            throw BizFailure.wrap(e);
        }
        return true;
    }

    public abstract boolean tableExists(@NotNull String var1);

    public abstract boolean sequenceExists(@NotNull String var1);

    public abstract void dropSequenceIfExists(@NotNull String var1);

    public abstract String getNextSequenceValue(@NotNull String var1);

    @Deprecated
    public void dropForeignKeyIfExists(@NotNull String inTableName, @NotNull String inFKName) {
        if (!this.fkExists(inTableName, inFKName)) {
            LOGGER.info((Object)("dropForeignKey: unable to drop <" + inTableName + ":" + inFKName + "> as it does not exist"));
            return;
        }
        this.jtUpdate("alter table " + inTableName + " drop constraint " + inFKName);
    }

    public void dropTableIfExists(String inTableName) {
        if (this.tableExists(inTableName)) {
            this._jt.execute("DROP TABLE " + inTableName);
        }
    }

    public abstract boolean columnExists(String var1, String var2);

    public abstract boolean indexExists(String var1, String var2);

    @Nullable
    public final String indexExists(@NotNull ICarinaIndex inCarinaIndex) {
        String tableName = inCarinaIndex.getTableName();
        List<ICarinaIndex> databaseIndexes = this.getIndexesForTable(tableName);
        if (databaseIndexes == null) {
            return null;
        }
        for (ICarinaIndex dbIndex : databaseIndexes) {
            if (!dbIndex.equals(inCarinaIndex)) continue;
            return dbIndex.getIndexName();
        }
        return null;
    }

    @Nullable
    public final String indexExists(String inTableName, String[] inColumnNames) {
        if (inColumnNames == null || inColumnNames.length < 1) {
            return null;
        }
        CarinaIndexFactory indexFactory = new CarinaIndexFactory(this._dbType);
        ICarinaIndex carinaIndex = indexFactory.getCarinaIndexObject(inTableName, Arrays.asList(inColumnNames), null);
        return this.indexExists(carinaIndex);
    }

    public abstract boolean fkExists(@NotNull String var1, @NotNull String var2);

    @Nullable
    public <T> T queryForObject(String inQuery, Class<? extends T> inClass) {
        Object val;
        try {
            val = this._jt.queryForObject(inQuery, inClass);
        }
        catch (IncorrectResultSizeDataAccessException e) {
            if (0 == e.getActualSize()) {
                return null;
            }
            LOGGER.error((Object)("Expected size of 0 or 1 for: " + inQuery));
            throw BizFailure.wrap(e);
        }
        return (T)val;
    }

    public List queryForList(String inQueryString) {
        List result;
        try {
            result = this._jt.queryForList(inQueryString);
        }
        catch (DataAccessException e) {
            LOGGER.error((Object)("jtQueryForLIst: failed sql = " + inQueryString), (Throwable)e);
            throw BizFailure.wrap(e);
        }
        return result;
    }

    @Deprecated
    public void updateForObject(@NotNull String inUpdate) {
        this.jtUpdate(inUpdate);
    }

    public abstract boolean isColumnNullable(@NotNull String var1, @NotNull String var2);

    public boolean isColumnNotNullable(@NotNull String inTableName, @NotNull String inColumnName) {
        return !this.isColumnNullable(inTableName, inColumnName);
    }

    public abstract Integer getVarcharTypeColumnSize(@NotNull String var1, @NotNull String var2);

    @Nullable
    public abstract String columnType(@NotNull String var1, @NotNull String var2);

    public abstract void modifyBinaryToBlob(@NotNull String var1, @NotNull String var2);

    public abstract String getDecodeBlobFunction(String var1);

    @Nullable
    public abstract List<ICarinaIndex> getIndexesForTable(@NotNull String var1);

    public void modifyToNvarchar(String inTableName, String inColumnName, int inLength) {
    }

    public final boolean dropColumn(String inTableName, String inColumnName) {
        try {
            this._jt.execute("ALTER TABLE " + inTableName + " DROP COLUMN " + inColumnName);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public final boolean truncateTable(String inTableName) {
        try {
            this._jt.execute("TRUNCATE TABLE " + inTableName);
            return true;
        }
        catch (Exception e) {
            LOGGER.error((Object)("Unable to truncate table " + inTableName), (Throwable)e);
            return false;
        }
    }

    @Nullable
    public String getDbHibernateDialect() {
        DbHibernateSpatialDialectFactory dbHibernateSpatialDialectFactory;
        String dialect;
        ApplicationModuleSettings applModuleSettings = (ApplicationModuleSettings) PortalApplicationContext.getBean("appModuleSettings");
        if (applModuleSettings.usesSpatial() && (dialect = (dbHibernateSpatialDialectFactory = (DbHibernateSpatialDialectFactory) Roastery.getBean("dbHibernateSpatialDialectFactory")).getDbHibernateDialect(this._dbType)) != null) {
            System.setProperty("hibernate.spatial.dialect", dialect);
            return dialect;
        }
        DbHibernateDialectFactory dbHibernateDialectFactory = (DbHibernateDialectFactory) Roastery.getBean("dbHibernateDialectFactory");
        return dbHibernateDialectFactory.getDbHibernateDialect(this._dbType);
    }

    public List<Map<String, Object>> getCorruptedGeometryIndexes() {
        return Collections.emptyList();
    }

    public boolean isGeometryMetadataExists(String inTableName, String inColumnName) {
        return false;
    }

    public void deleteGeometryMetadataIfExists(String inTableName, String inColumnName) {
    }

    @Nullable
    public Boolean createGeometryMetadataIfNotExists(String inTableName, String inColumnName) {
        return null;
    }

    @NotNull
    public Boolean reCreateGeometryMetaDataAndIndex(String inTableName, String inColumnName, String inIndexName) {
        return false;
    }

    public abstract String getBoolValueString(@NotNull Boolean var1);

    public String getDatabaseHostname() {
        return this.getUrlFromDatabaseConnection() == null ? "" : this.getUrlFromDatabaseConnection().getHost();
    }

    public int getDatabasePort() {
        if (this.getUrlFromDatabaseConnection() == null) {
            return -1;
        }
        return this.getUrlFromDatabaseConnection().getPort() < 0 ? this.getDefaultDatabasePort() : this.getUrlFromDatabaseConnection().getPort();
    }

    protected abstract int getDefaultDatabasePort();

    @Nullable
    protected abstract URL getUrlFromDatabaseConnection();

    public abstract String buildCountBytesInTableQuery();

    public DataSource getDataSource() {
        return this._dataSource;
    }

    public String getProductName() {
        return this._productName;
    }

    public String getProductVersion() {
        return this._productVersion;
    }

    public String getDriverName() {
        return this._driverName;
    }

    public String getDriverVersion() {
        return this._driverVersion;
    }

    public String getUrl() {
        return this._url;
    }

    public int getDbMajorVersion() {
        return this._dbMajorVersion;
    }

    public String getDatabaseCollation() {
        return "";
    }

    @Nullable
    public String getBootstrapFrameworkConfigAsString(String inFrameworkIDStr) {
        String queryStr = "select cnfig_value from config_settings where cfg_id='" + inFrameworkIDStr + "'";
        return this.queryForObject(queryStr, String.class);
    }

    public void setBootstrapFrameworkConfigAsString(String inFrameworkIDStr, String inNewConfigValue) {
        String updateStr = "insert into config_settings(cfg_id, cnfig_value) values ('" + inFrameworkIDStr + "', '" + inNewConfigValue + "');";
        this.updateForObject(updateStr);
    }

    protected abstract void setColumnNullability(@NotNull String var1, @NotNull String var2, boolean var3);

    protected abstract String getNullableNotNullableString(boolean var1);

    @Nullable
    protected String checkAndGetColumnType(String inTableName, String inColumnName, boolean inSetNullable) {
        if (inSetNullable && this.isColumnNullable(inTableName, inColumnName) || !inSetNullable && this.isColumnNotNullable(inTableName, inColumnName)) {
            String nullableStringMsg = inSetNullable ? "nullable" : "not nullable";
            LOGGER.info((Object)("Column <" + inTableName + ":" + inColumnName + "> is already " + nullableStringMsg));
            return null;
        }
        String typeString = this.columnType(inTableName, inColumnName);
        if (typeString == null) {
            String message = "Cannot get type of column <" + inTableName + ":" + inColumnName + ">";
            throw BizFailure.create(message);
        }
        return typeString.toLowerCase();
    }

    @NotNull
    protected String validateColumnType(@NotNull String inTableName, @NotNull String inColumnName, @NotNull String inType) {
        return inType;
    }

    public void alterVarcharColumnLength(String inTableName, String inColName, int inNewLength) {
        Integer currentSize = this.getVarcharTypeColumnSize(inTableName, inColName);
        if (currentSize == null) {
            LOGGER.warn((Object)("Cannot resize " + inTableName + "." + inColName + " to " + inNewLength + ". No current size info available."));
            return;
        }
        if (inNewLength <= currentSize) {
            LOGGER.warn((Object)("Attempting to resize " + inTableName + "." + inColName + " to " + inNewLength + " when it is already that size or greater."));
            return;
        }
        DatabaseUtils.estimateColumnAlteringTime(inTableName, inColName);
        String statement = this.getAlterVarcharColumnLengthStatement(inTableName, inColName, inNewLength);
        LOGGER.info((Object)(" Executing SQL statement : " + statement));
        this._jt.execute(statement);
    }

    protected abstract String getAlterVarcharColumnLengthStatement(String var1, String var2, int var3);

    public abstract boolean isSupportedVersion();

    public abstract boolean isOracleRacSystem();

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Integer getTransactionIsolationLevel() {
        try (Connection conn = this._dataSource.getConnection();){
            Integer n = conn.getTransactionIsolation();
            return n;
        }
        catch (SQLException e) {
            LOGGER.error((Object)("cannot get connection: " + e.getMessage()));
            return 0;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public IMessageCollector processSql(IMessageCollector inOutMessageCollector, String inSqlFile) {
        Resource sqlInput = this._applicationContext.getResource(BeanFactoryUtil.getClassPathFileName(inSqlFile));
        LineIterator in = null;
        try {
            in = IOUtils.lineIterator((InputStream)sqlInput.getInputStream(), (String)null);
        }
        catch (IOException e) {
            try {
                if (in != null) {
                    in.close();
                }
            }
            finally {
                LOGGER.error((Object)"cannot close iterator");
            }
            inOutMessageCollector.registerExceptions(e);
            return inOutMessageCollector;
        }
        while (in.hasNext()) {
            String line = in.nextLine();
            boolean runSetIdentityCommand = false;
            if (this.isDatabaseType(DatabaseType.SQLSERVER) && line.trim().toUpperCase().contains("SET IDENTITY_INSERT")) {
                line = line.substring(line.indexOf("SET IDENTITY_INSERT"), line.length() - 1);
                runSetIdentityCommand = true;
            }
            if (!line.trim().toUpperCase().startsWith("INSERT") && !runSetIdentityCommand) continue;
            try {
                if (line.trim().lastIndexOf(59) + 1 == line.trim().length()) {
                    line = line.trim().substring(0, line.lastIndexOf(59));
                }
                this.updateForObject(line.trim());
            }
            catch (DataAccessException e) {
                LOGGER.error((Object)e.getMessage());
            }
        }
        in.close();
        return inOutMessageCollector;
    }

    @NotNull
    public String getDatasourceUserName() {
        DataSource ds = this.getDataSource();
        if (ds instanceof DataSource) {
 //           return ((DataSource)ds).getUsername();
        }
        LOGGER.warn((Object)("Unsuported datasource: " + ds.getClass().getName()));
        return "";
    }

    @Deprecated
    public String concatSQLString(String[] inStrings, DatabaseType inDatabaseType) {
        return this.concatSQLString(inStrings);
    }

    protected abstract String concatSQLString(String[] var1);

    protected String concatSQLString(String[] inStrings, String inPrefix, String inSuffix, String inSeparator) {
        StringBuilder sql = new StringBuilder(inPrefix);
        for (int i = 0; i < inStrings.length; ++i) {
            if (i > 0) {
                sql.append(inSeparator);
            }
            sql.append(inStrings[i]);
        }
        sql.append(inSuffix);
        return sql.toString();
    }

    public final ApplicationContext getApplicationContext() {
        return this._applicationContext;
    }

}
