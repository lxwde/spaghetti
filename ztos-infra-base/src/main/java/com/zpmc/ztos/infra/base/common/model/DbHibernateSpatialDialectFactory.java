package com.zpmc.ztos.infra.base.common.model;

import com.zpmc.ztos.infra.base.common.type.DatabaseType;

import java.util.HashMap;
import java.util.Map;

public class DbHibernateSpatialDialectFactory {

    public static final String BEAN_ID = "dbHibernateSpatialDialectFactory";
    private Map<String, String> _dbHibernateSpatialDialects;

    public void setDbHibernateDialects(Map<String, String> inDbHibernateSpatialDialects) {
        this._dbHibernateSpatialDialects = new HashMap<String, String>(inDbHibernateSpatialDialects);
    }

    public String getDbHibernateDialect(DatabaseType inDatabaseType) {
        return this._dbHibernateSpatialDialects.get(inDatabaseType.getName());
    }

}
