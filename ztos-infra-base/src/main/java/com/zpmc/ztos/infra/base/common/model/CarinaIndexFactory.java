package com.zpmc.ztos.infra.base.common.model;

import com.zpmc.ztos.infra.base.business.interfaces.ICarinaIndex;
import com.zpmc.ztos.infra.base.business.interfaces.ICarinaIndexFactory;
import com.zpmc.ztos.infra.base.common.type.DatabaseType;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.util.List;
import java.util.Map;

public class CarinaIndexFactory implements ICarinaIndexFactory {
    private DatabaseType _databaseType;

    public CarinaIndexFactory(DatabaseType inDatabaseType) {
        this._databaseType = inDatabaseType;
    }

    @Override
    @Nullable
    public ICarinaIndex getCarinaIndexObject(Map<Object, Object> inIndex) {
        if (this._databaseType == DatabaseType.SQLSERVER) {
            return new CarinaSqlserverIndex(inIndex);
        }
        if (this._databaseType == DatabaseType.ORACLE) {
            return new CarinaOracleIndex(inIndex);
        }
        if (this._databaseType == DatabaseType.MYSQL) {
            return new CarinaMysqlIndex(inIndex);
        }
        if (this._databaseType == DatabaseType.POSTGRES) {
            return new CarinaPostgresIndex(inIndex);
        }
        return null;
    }

    @Override
    @Nullable
    public ICarinaIndex getCarinaIndexObject(String inTableName, List<String> inColumnNames, @Nullable String inIndexName) {
        if (this._databaseType == DatabaseType.SQLSERVER) {
            return new CarinaSqlserverIndex(inTableName, inColumnNames, inIndexName);
        }
        if (this._databaseType == DatabaseType.ORACLE) {
            return new CarinaOracleIndex(inTableName, inColumnNames, inIndexName);
        }
        if (this._databaseType == DatabaseType.MYSQL) {
            return new CarinaMysqlIndex(inTableName, inColumnNames, inIndexName);
        }
        if (this._databaseType == DatabaseType.POSTGRES) {
            return new CarinaPostgresIndex(inTableName, inColumnNames, inIndexName);
        }
        return null;
    }
}
