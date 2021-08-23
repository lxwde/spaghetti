package com.zpmc.ztos.infra.base.common.model;

import com.zpmc.ztos.infra.base.common.type.DatabaseType;

import java.util.HashMap;
import java.util.Map;

public class DbHibernateDialectFactory {
    public static final String BEAN_ID = "dbHibernateDialectFactory";
    private Map<String, String> _dbHibernateDialects;

    public void setDbHibernateDialects(Map<String, String> inDbHibernateDialects) {
        this._dbHibernateDialects = new HashMap<String, String>(inDbHibernateDialects);
    }

    public String getDbHibernateDialect(DatabaseType inDatabaseType) {
        return this._dbHibernateDialects.get(inDatabaseType.getName());
    }

}
